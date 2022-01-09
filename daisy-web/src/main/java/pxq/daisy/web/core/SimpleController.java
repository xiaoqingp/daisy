package pxq.daisy.web.core;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 处理http请求的接口
 * 使用jdk生成代理类处理实际请求
 * @see SimpleControllerHandler
 */
public interface SimpleController {
    /**
     * 处理http请求
     *
     * @param request
     */
    HttpResponse doService(HttpRequest request);
}
