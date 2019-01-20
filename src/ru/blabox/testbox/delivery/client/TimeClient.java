package ru.blabox.testbox.delivery.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class TimeClient {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();                                          // (1)
            b.group(workerGroup)                                                    // (2)
             .channel(NioSocketChannel.class)                                       // (3)
             .option(ChannelOption.SO_KEEPALIVE, true)                        // (4)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // Запускаем клиент
            ChannelFuture f = b.connect(host, port).sync();                         // (5)

            // Ожидаем пока соединение закроется
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
