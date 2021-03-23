package org.jeecg.modules.ilang.controller

import cn.binarywang.wx.miniapp.api.WxMaService
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.chanjar.weixin.common.api.WxConsts
import me.chanjar.weixin.common.bean.WxJsapiSignature
import me.chanjar.weixin.mp.api.WxMpService
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.modules.ilang.mqtt.MqttGateway
import org.jeecg.modules.ilang.utils.log
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Api(tags = ["微信"])
@RestController
@RequestMapping("/ilang/wx")
class WxController(private val wxMaService: WxMaService, private val wxMpService: WxMpService, private val mqttGateway: MqttGateway, private val objectMapper: ObjectMapper)
{
    @AutoLog(value = "微信-登录")
    @ApiOperation(value = "微信-登录", notes = "微信-登录")
    @PostMapping("login")
    fun login(@RequestParam jsCode: String): Result<WxMaJscode2SessionResult>?
    {
        val result = wxMaService.userService.getSessionInfo(jsCode)
        return Result.OK(result)
    }

    @AutoLog(value = "微信-设备扫描登录")
    @GetMapping("device/login/{deviceId}")
    fun wxAuth(@PathVariable deviceId: String): Result<String>?
    {
        val url = "https://ilang.senriot.com/sery/ilang/wx/device/auth/$deviceId"
        val r = wxMpService.oAuth2Service.buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null)
        return Result.OK(r)
    }

    @RequestMapping("device/auth/{deviceId}")
    fun wxAuthRedirectUri(@RequestParam code: String, @PathVariable deviceId: String): ModelAndView
    {
//        log().debug("设备登录 $code $deviceId")
        val token = wxMpService.oAuth2Service.getAccessToken(code)
        val user = wxMpService.oAuth2Service.getUserInfo(token, "zh_CN")
        val r = mapOf(Pair("accessToken", token), Pair("user", user))
        mqttGateway.send("device/langdu/onLogin/$deviceId", objectMapper.writeValueAsString(r))
        return ModelAndView("redirect:https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIzMTk1OTcyMQ==#wechat_redirect")
    }

    @GetMapping("accessToken")
    fun getAccessToken(@RequestParam code: String): Result<*>
    {
        val token = wxMpService.oAuth2Service.getAccessToken(code)
        val user = wxMpService.oAuth2Service.getUserInfo(token, "zh_CN")
        val r = mapOf(Pair("accessToken", token), Pair("user", user))
        return Result.OK(r)
    }

    @GetMapping("jsapiSignature")
    fun jsapiSignature(@RequestParam url: String): Result<WxJsapiSignature>?
    {
        return Result.OK(wxMpService.createJsapiSignature(url))
    }
}
