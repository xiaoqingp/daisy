package pxq.daisy.web.core;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty 业务处理器
 * @author peixiaoqing
 * @date 2021/12/29
 * @since 1.0.0
 */
public class HttpHandler extends SimpleChannelInboundHandler <HttpObject> {
    private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

    private static final String FAVICON_ICO = "/favicon.ico";

    private FullHttpRequest request;
    private HttpHeaders headers;

    static {
        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
        // on exit (in normal exit)
        DiskFileUpload.baseDirectory = null; // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
        // exit (in normal exit)
        DiskAttribute.baseDirectory = null; // system temp directory
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 这个是单线程的吗
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            try {
                request = (FullHttpRequest) msg;
                headers = request.headers();

                String uri = request.uri();
                //去除浏览器"/favicon.ico"的干扰
                if (uri.equals(FAVICON_ICO)) {
                    return;
                }
                
                SimpleController controller = ControllerFactory.create(request);
                HttpResponse response = controller.doService(request);

                writeResponse(ctx.channel(), response, false);

            } catch (Exception e) {
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.INTERNAL_SERVER_ERROR);
                writeResponse(ctx.channel(), response, true);
                log.error("netty http handler错误",e);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        } else {
            //discard request...
            ReferenceCountUtil.release(msg);
        }
    }


    private void writeResponse(Channel channel, HttpResponse response, boolean forceClose) {

        // Decide whether to close the connection or not.
        boolean keepAlive = HttpUtil.isKeepAlive(request) && !forceClose;

//        // Build the response object.
//        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
//        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

        if (!keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        } else if (request.protocolVersion().equals(HttpVersion.HTTP_1_0)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        // Write the response.
        ChannelFuture future = channel.writeAndFlush(response);
        // Close the connection after the write operation is done if necessary.
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //log.warn("连接异常", cause);
        ctx.channel().close();
    }
}