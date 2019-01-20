package ru.blabox.testbox.delivery;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Просто получаем и отправляем сообщение обратно
        ByteBuf mes = (ByteBuf) msg;
        System.out.print(mes.toString(io.netty.util.CharsetUtil.US_ASCII));
        ctx.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Закрываем соединеие при возникновении исключения.
        cause.printStackTrace();
        ctx.close();
    }
}
