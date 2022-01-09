/**
 * 文件名：SimpleControllerHandler<br/>
 * CopyRight (c) 2019-2030：<br/>
 * 创建人：peixiaoqing
 * 日期：2021/12/31
 * 修改人：
 * 日期：
 * 描述：
 * 版本号：2.5.4
 */
package pxq.daisy.web.core;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pxq.daisy.web.HttpServer;
import pxq.daisy.web.spring.SpringAppContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * SimpleController的处理器
 * 实际代理类在HttpServer启动时生成
 * @see HttpServer#initContext()
 */
public class SimpleControllerHandler implements InvocationHandler {

    private DaisyControllerMethod daisyControllerMethod;

    public SimpleControllerHandler(DaisyControllerMethod daisyMethod) {
        this.daisyControllerMethod = daisyMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = daisyControllerMethod.invoke(args);

        if (result instanceof String) {
            // 如果是字符串默认为地址，使用thymeleaf解析
            TemplateEngine engine = SpringAppContext.getBean(TemplateEngine.class);
            String htmlTxt = engine.process(result.toString(), new Context());
            return writePage(htmlTxt);
        } else {
            return writeJson(result);
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
