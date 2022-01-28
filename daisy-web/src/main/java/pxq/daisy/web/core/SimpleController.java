package pxq.daisy.web.core;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 处理http请求的接口
 * 使用jdk生成代理类处理实际请求
 * @see SimpleControllerHandler
 *
 * @author peixiaoqing
 * @date 2021/12/31
 * @since 1.0.0
 */
public interface SimpleController {
    /**
     * 处理http请求
     *
     * @param request http请求参数
     */
    HttpResponse doService(FullHttpRequest request);
}
