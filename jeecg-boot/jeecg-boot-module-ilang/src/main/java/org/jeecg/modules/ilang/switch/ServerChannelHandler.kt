package org.jeecg.modules.ilang.switch

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.jeecg.common.util.RedisUtil
import org.jeecg.modules.ilang.entity.Switch
import org.jeecg.modules.ilang.service.SwitchService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.InetSocketAddress

@Component
@ChannelHandler.Sharable
class ServerChannelHandler(private val service: SwitchService, private val redisUtil: RedisUtil) :
    SimpleChannelInboundHandler<Message>()
{
    private val log = LoggerFactory.getLogger(this.javaClass)


    override fun channelRead0(ctx: ChannelHandlerContext, msg: Message)
    {
        val switch = service.getOne(QueryWrapper<Switch>().eq("code", msg.deviceId))
        if (switch != null)
        {
            val insocket = ctx.channel().remoteAddress() as InetSocketAddress
            switch.ip = insocket.address.hostAddress
            switch.status = msg.state
            service.saveOrUpdate(switch)
            SessionManager.setSession(msg.deviceId, ctx.channel())
        }
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext)
    {
        super.channelUnregistered(ctx)
        log.info(String.format("# client unregistered... : %s", ctx.channel()))
    }

    override fun channelRegistered(ctx: ChannelHandlerContext)
    {
        super.channelRegistered(ctx)
        log.info(String.format("# client registered...：   %s ...", ctx.channel()))
    }
}
