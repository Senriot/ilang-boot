package org.jeecg.modules.ilang.switch

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class SwitchTcpServer : ApplicationRunner
{
    private val log = LoggerFactory.getLogger(this.javaClass)


    @Value("\${nio.server.port:8999}")
    private var port = 8999

    /** server bootstrap  */
    private val bootstrap: ServerBootstrap by lazy { ServerBootstrap() }


    private val boss by lazy { NioEventLoopGroup(1) }


    private val worker by lazy { NioEventLoopGroup() }

    @Autowired
    private lateinit var serverChannelInitializer: ServerChannelInitializer

    fun start(): ChannelFuture?
    {
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(serverChannelInitializer)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
        val channelFuture = bootstrap.bind(port).syncUninterruptibly()
        if (channelFuture.isSuccess)
        {
            log.info("智能开关TCP服务启动完成 端口：$port")
        }
        return channelFuture
    }

    override fun run(args: ApplicationArguments?)
    {
        log.info("正在启动智能开关服务...")
        start()
    }
}
