package pxq.daisy.web.core;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

/**
 * web请求上下文
 *
 * @author peixiaoqing
 * @date 2022/01/23
 * @since 1.0.0
 */
public class WebContext {
    private static ThreadLocal<FullHttpRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<DaisyResponse> responseThreadLocal = new ThreadLocal<>();

    public static void putRequest(FullHttpRequest request) {
        requestThreadLocal.set(request);
    }

    public static void putResponse(DaisyResponse response) {
        responseThreadLocal.set(response);
    }

    public static FullHttpRequest getRequest() {
        return requestThreadLocal.get();
    }

    public static DaisyResponse getResponse() {
        return responseThreadLocal.get();
    }

    public static void clean() {
        requestThreadLocal.remove();
        responseThreadLocal.remove();
    }
}
