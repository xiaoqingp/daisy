package pxq.daisy.web.core;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import pxq.daisy.web.annotation.DeleteMapping;
import pxq.daisy.web.annotation.GetMapping;
import pxq.daisy.web.annotation.PostMapping;
import pxq.daisy.web.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peixiaoqing
 * @date 2021/12/30
 * @since 1.0.0
 */
public class ControllerFactory {

    private static final Map<String, DaisyController> controllerMap = new ConcurrentHashMap<>();
    private static final SimpleController staticResourceController = new StaticResourceController();

    /**
     * 从缓存中获取代理类
     *
     * @param request http请求
     * @return controller的method生成的代理类
     */
    public static SimpleController create(FullHttpRequest request) {
        String uri = request.uri();
        HttpMethod method = request.method();

        int index = uri.indexOf("?");
        DaisyController controller;
        if (index > -1) {
            controller = controllerMap.get(method.toString() + ":" + uri.substring(0, index));
        } else {
            controller = controllerMap.get(method.toString() + ":" + uri);
        }

        if (null == controller) {
            if (uri.endsWith(".css") || uri.endsWith(".js")) {
                return staticResourceController;
            }
            return null;
        }
        return controller.getControllerProxy();
    }

    /**
     * 初始化工厂 解析使用Controller注解的java类，
     * 并把其中使用GetMapping、PostMapping、PutMapping、DeleteMapping注解的方法生成SimpleController的代理类，
     * 并缓存uri和代理类
     *
     * @param ctx spring 上下文
     */
    public static void init(ApplicationContext ctx) {
        Map<String, Object> controllerMap = ctx.getBeansWithAnnotation(Controller.class);
        for (String key : controllerMap.keySet()) {
            Object controller = controllerMap.get(key);
            Class<?> controllerClazz = controller.getClass();
            Method[] methods = controllerClazz.getMethods();
            for (Method method : methods) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof GetMapping) {
                        String[] uris = ((GetMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method), HttpMethod.GET);
                        break;
                    }

                    if (annotation instanceof PostMapping) {
                        String[] uris = ((PostMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method), HttpMethod.POST);
                        break;
                    }

                    if (annotation instanceof PutMapping) {
                        String[] uris = ((PutMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method), HttpMethod.PUT);
                        break;
                    }

                    if (annotation instanceof DeleteMapping) {
                        String[] uris = ((DeleteMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method), HttpMethod.DELETE);
                        break;
                    }
                }
            }
        }
    }

    private static void putMapping(String[] uris, DaisyController controller, HttpMethod method) {
        for (String uri : uris) {
            DaisyController value = controllerMap.get(uri);
            if (null != value) {
                throw new RuntimeException("处理方法" + controller.getTargetMethod().toString() + "时发现uri=" + uri + "已经存在，"
                        + value.getTargetMethod().toString());
            }
            controllerMap.put(method.toString() + ":" + uri, controller);
        }
    }
}
