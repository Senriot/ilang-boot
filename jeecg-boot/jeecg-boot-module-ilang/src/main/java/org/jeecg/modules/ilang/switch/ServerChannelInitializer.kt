package org.jeecg.modules.ilang.switch

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.FixedLengthFrameDecoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleStateHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ServerChannelInitializer : ChannelInitializer<SocketChannel>()
{
    @Autowired
    private lateinit var serverChannelHandler: ServerChannelHandler


    override fun initChannel(ch: SocketChannel?)
    {
        val pipeline = ch?.pipeline()
        pipeline?.addLast(LoggingHandler(LogLevel.INFO))
        pipeline?.addLast("idleStateHandler", IdleStateHandler(15, 0, 0, TimeUnit.MINUTES))
        pipeline?.addLast(FixedLengthFrameDecoder(23))
        pipeline?.addLast(MessageDecoder(), MessageEncoder())
        pipeline?.addLast("serverChannelHandler", serverChannelHandler)
    }
}
