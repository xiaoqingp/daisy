package pxq.daisy.web.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * netty 初始器
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class HttpInitializer extends ChannelInitializer <SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());

        // Remove the following line if you don't want automatic content compression.
        pipeline.addLast(new HttpContentCompressor());

        pipeline.addLast(new HttpHandler());
    }
}
