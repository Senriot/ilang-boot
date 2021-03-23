package org.jeecg.modules.ilang.switch

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import org.jeecg.modules.ilang.utils.log
import java.nio.charset.Charset

class MessageDecoder : MessageToMessageDecoder<ByteBuf>()
{
    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>)
    {
        val m = Message()
        msg.readCharSequence(5, Charset.defaultCharset())
        m.deviceId = msg.readCharSequence(14, Charset.defaultCharset()).toString()
        msg.readCharSequence(1, Charset.defaultCharset())
        m.state = msg.readCharSequence(1, Charset.defaultCharset()).toString()
        log().info(m.toString())
        out.add(m)
    }
}
