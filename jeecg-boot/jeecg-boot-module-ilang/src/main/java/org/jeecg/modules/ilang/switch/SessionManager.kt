package org.jeecg.modules.ilang.switch

import io.netty.channel.Channel

object SessionManager
{
    private val sessionMap = mutableMapOf<String, Channel>()


    fun setSession(deviceId: String, channel: Channel)
    {
        sessionMap[deviceId] = channel
    }

    fun sendMessage(message: Message)
    {
        if (sessionMap.contains(message.deviceId))
        {
            val session = sessionMap.getValue(message.deviceId)
            session.writeAndFlush(message)
        }
    }

    fun sendMessage(key: String, msg: String)
    {
        if (sessionMap.contains(key))
        {
            val session = sessionMap.getValue(key)
            session.writeAndFlush(msg)
        }
    }
}
