package org.jeecg.modules.ilang.mqtt

import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.mqtt.support.MqttHeaders
import org.springframework.messaging.handler.annotation.Header


@MessagingGateway(defaultRequestChannel = MqttConfig.CHANNEL_NAME_OUT)
interface MqttGateway
{
    fun send(payload: String)

    fun send(@Header(MqttHeaders.TOPIC) topic: String, payload: String)

    fun send(@Header(MqttHeaders.TOPIC) topic: String, @Header(MqttHeaders.QOS) qos: Int, payload: String)
}
