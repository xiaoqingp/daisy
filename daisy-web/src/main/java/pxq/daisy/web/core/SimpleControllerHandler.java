package pxq.daisy.web.core;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pxq.daisy.web.spring.SpringAppContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * SimpleController的处理器
 * 实际代理类在HttpServer启动时生成
 * @see ControllerFactory
 *
 * @author peixiaoqing
 * @date 2021/12/31
 * @since 1.0.0
 */
public class SimpleControllerHandler implements InvocationHandler {

    private DaisyController daisyController;

    public SimpleControllerHandler(DaisyController daisyController) {
        this.daisyController = daisyController;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            FullHttpRequest httpRequest = (FullHttpRequest) args[0];
            WebContext.put(httpRequest);
            result = daisyController.invoke(httpRequest);

            if (result instanceof String) {
                // 如果是字符串默认为地址，使用thymeleaf解析
                TemplateEngine engine = SpringAppContext.getBean(TemplateEngine.class);
                String htmlTxt = engine.process(result.toString(), new Context());
                return writePage(htmlTxt);
            } else {
                return writeJson(result);
            }

        } finally {
            WebContext.clean();
        }
    }

    private HttpResponse writePage(String htmlTxt) {
        ByteBuf buf = copiedBuffer(htmlTxt, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

        return response;
    }

    private HttpResponse writeJson(Object result) {
        ByteBuf buf = copiedBuffer(JSON.toJSONString(result), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

        return response;
    }
}
