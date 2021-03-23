package org.jeecg.modules.ilang.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import io.swagger.annotations.Api
import me.chanjar.weixin.mp.api.WxMpDeviceService
import org.jeecg.modules.ilang.entity.Device
import org.jeecg.modules.ilang.mqtt.MqttGateway
import org.jeecg.modules.ilang.service.IDeviceService
import org.jeecg.modules.ilang.utils.log
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@Api(tags = ["mqtt"])
@RestController
@RequestMapping("/ilang/mqtt")
class MqttController(private val deviceService: IDeviceService, private val mqttGateway: MqttGateway)
{
    @PostMapping("webhook")
    fun mqWebHook(@RequestBody body: Map<String, Any>)
    {
//        log().info(body.toString())
        when (body["action"] as String)
        {
            "client_connected" ->
            {
                val clientid = body["clientid"] as String
                val device = deviceService.getOne(QueryWrapper<Device>().eq("dev_id", clientid))
                device?.let {
                    it.address = body["ipaddress"] as String?
                    it.onlineDate = Date()
                    it.onlineStatus = "online"
                    deviceService.saveOrUpdate(it)
                    mqttGateway.send("/device/bgm/deviceStatus/$clientid","{\"onlineStatus\":\"online\"}")
                }
            }
            "client_disconnected" ->
            {
                val clientid = body["clientid"] as String
                val device = deviceService.getOne(QueryWrapper<Device>().eq("dev_id", clientid))
                device?.let {
                    it.onlineStatus = "offline"
                    deviceService.saveOrUpdate(it)
                    mqttGateway.send("/device/bgm/deviceStatus/$clientid","{\"onlineStatus\":\"offline\"}")
                }
            }
        }
    }
}
