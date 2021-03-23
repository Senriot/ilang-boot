package org.jeecg.modules.ilang.mqtt

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.greenrobot.eventbus.EventBus
import org.jeecg.common.util.UUIDGenerator
import org.jeecg.modules.ilang.utils.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessageProducer
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import java.nio.charset.Charset


@ConfigurationProperties(prefix = "mqtt", ignoreUnknownFields = true)
@Configuration
@IntegrationComponentScan
class MqttConfig
{
    var userName: String = "server"
    var password: String = "jiandan88"
    var clientId: String = UUIDGenerator.generate()
    var url: String = "tcp://47.103.89.201:1883"

    val tipics = arrayOf("/device/bgm/getDeviceInfo", "/device/bgm/saveDeviceInfo", "device/langdu/wxLogin")

    companion object
    {
        /**
         * 订阅的bean名称
         */
        const val CHANNEL_NAME_IN = "mqttInboundChannel"

        /**
         * 发布的bean名称
         */
        const val CHANNEL_NAME_OUT = "mqttOutboundChannel"
    }


    @Bean
    fun myEventBus(): EventBus = EventBus()


    @Bean
    fun mqttConnectOptions(): MqttConnectOptions
    {
        return MqttConnectOptions().apply {
            isCleanSession = false
            userName = this@MqttConfig.userName
            password = this@MqttConfig.password.toCharArray()
            serverURIs = arrayOf(url)
            connectionTimeout = 10
            keepAliveInterval = 20
            setWill("willTopic", "offline".toByteArray(Charset.defaultCharset()), 2, false)
        }
    }

    /**
     * MQTT客户端
     *
     * @return {@link org.springframework.integration.mqtt.core.MqttPahoClientFactory}
     */
    @Bean
    fun mqttClientFactory(opt: MqttConnectOptions): MqttPahoClientFactory
    {
        return DefaultMqttPahoClientFactory().apply {
            connectionOptions = opt
        }
    }

    /**
     * MQTT信息通道（生产者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
     */
    @Bean(name = [CHANNEL_NAME_OUT])
    fun mqttOutboundChannel(): MessageChannel = DirectChannel()

    /**
     * MQTT消息处理器（生产者）
     *
     * @return [org.springframework.messaging.MessageHandler]
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    fun mqttOutbound(opt: MqttConnectOptions): MessageHandler?
    {
        val messageHandler = MqttPahoMessageHandler(
                "topic1",
                mqttClientFactory(opt))
        messageHandler.setAsync(true)
        messageHandler.setDefaultTopic("testtoppic")
        return messageHandler
    }

    @Bean
    fun inbound(factory: MqttPahoClientFactory): MessageProducer
    {
        return MqttPahoMessageDrivenChannelAdapter(clientId, factory, *tipics).apply {
            setQos(1)
            outputChannel = mqttInboundChannel()
            setConverter(DefaultPahoMessageConverter())
        }
    }

    /**
     * MQTT信息通道（消费者）
     *
     * @return [org.springframework.messaging.MessageChannel]
     */
    @Bean(name = [CHANNEL_NAME_IN])
    fun mqttInboundChannel(): MessageChannel?
    {
        return DirectChannel()
    }

    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    fun handler(): MessageHandler
    {
        return MessageHandler {
            val topic = it.headers["mqtt_receivedTopic"].toString()
            when (topic)
            {
                "/device/bgm/getDeviceInfo" -> myEventBus().post(GetDeviceInfo(it.payload.toString()))
                "/device/bgm/saveDeviceInfo" -> myEventBus().post(SaveDeviceInfoEvent(it.payload.toString()))
                "device/langdu/wxLogin" -> myEventBus().post(WinxinLoginEvent(it.payload.toString()))
            }
            log().info(it.headers["mqtt_receivedTopic"].toString())
            log().info(it.payload.toString())
        }
    }
}
