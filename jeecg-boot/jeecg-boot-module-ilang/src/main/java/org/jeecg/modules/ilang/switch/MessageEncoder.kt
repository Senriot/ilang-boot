package org.jeecg.modules.ilang.switch

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.nio.charset.Charset

class MessageEncoder : MessageToByteEncoder<String>()
{
    override fun encode(ctx: ChannelHandlerContext, msg: String, out: ByteBuf)
    {
        out.writeBytes(msg.toByteArray(Charset.defaultCharset()))
    }
}
