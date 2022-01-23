/**
 * 文件名：lInitializer<br/>
 * CopyRight (c) 2019-2030：<br/>
 * 创建人：peixiaoqing
 * 日期：2021/12/29
 * 修改人：
 * 日期：
 * 描述：
 * 版本号：1.0.0
 */
package pxq.daisy.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pxq.daisy.web.config.ServerConfig;
import pxq.daisy.web.core.ControllerFactory;
import pxq.daisy.web.core.HttpInitializer;

/**
 * http服务启动类
 *
 * @author peixiaoqing
 * @date 2021/12/29
 */
public class HttpServer {

    private static final String DEFAULT_SCAN_PATH = "pxq.daisy.web.spring";

    private ServerConfig config;
    private Class primarySource;

    public HttpServer(ServerConfig config, Class <?> primarySource) {
        this.config = config;
        this.primarySource = primarySource;
    }

    /**
     * 启动服务入口
     */
    public static ApplicationContext run(Class <?> primarySource, String... args) {
        // 读取properties文件
        ServerConfig config = ConfigFactory.create(ServerConfig.class);

        HttpServer httpServer = new HttpServer(config, primarySource);

        ApplicationContext context = httpServer.initContext();
        httpServer.startHttpServer();

        return context;
    }

    /**
     * 初始化spring 上下文
     *
     * @return
     */
    private ApplicationContext initContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // 1 扫描启动类的类路径初始化上下文
        String basePackage = primarySource.getPackage().getName();
        ctx.scan(DEFAULT_SCAN_PATH,basePackage);
        ctx.refresh();

        // 2 初始化Controller工厂类
        ControllerFactory.init(ctx);

        //  扫描jar包的daisy.factories初始化上下文


        return ctx;
    }

    /**
     * 启动http服务
     */
    private void startHttpServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childOption(ChannelOption.SO_REUSEADDR, true);
            b.handler(new LoggingHandler(this.getLogLevel()));
            b.childHandler(new HttpInitializer());

            Channel ch = b.bind(config.port()).sync().channel();

            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            System.exit(-1);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private LogLevel getLogLevel() {
        String level = config.level();
        return LogLevel.valueOf(level.toUpperCase());
    }
}
