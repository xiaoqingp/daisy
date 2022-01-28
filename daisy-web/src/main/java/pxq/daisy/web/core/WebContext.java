package pxq.daisy.web.core;

import io.netty.handler.codec.http.HttpRequest;

/**
 * web请求上下文
 *
 * @author peixiaoqing
 * @date 2022/01/23
 * @since 1.0.0
 */
public class WebContext {
    private static ThreadLocal<HttpRequest> requestThreadLocal = new ThreadLocal<>();

    public static void put(HttpRequest request) {
        requestThreadLocal.set(request);
    }

    public static HttpRequest getRequest() {
        return requestThreadLocal.get();
    }

    public static void clean() {
        requestThreadLocal.remove();
    }
}
