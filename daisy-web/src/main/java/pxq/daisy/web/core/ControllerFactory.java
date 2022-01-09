package pxq.daisy.web.core;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pxq.daisy.web.annotation.DeleteMapping;
import pxq.daisy.web.annotation.GetMapping;
import pxq.daisy.web.annotation.PostMapping;
import pxq.daisy.web.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peixiaoqing
 * @date 2021/12/30
 */
public class ControllerFactory {

    private static final Map <String, DaisyControllerMethod> controllerMethodMap = new ConcurrentHashMap <>();

    /**
     * 从缓存中获取代理类
     * @param uri
     * @return
     */
    public static SimpleController create(String uri) {
        int index = uri.indexOf("?");
        DaisyControllerMethod method = controllerMethodMap.get(uri.substring(0,index));
        return method.getController();
    }

    /**
     * 初始化工厂
     * 解析使用Controller注解的java类，
     * 并把其中使用GetMapping、PostMapping、PutMapping、DeleteMapping注解的方法生成SimpleController的代理类，
     * 并缓存uri和代理类
     * @param ctx
     */
    public static void init(ApplicationContext ctx) {
        Map <String, Object> controllerMap = ctx.getBeansWithAnnotation(Controller.class);
        for (String key : controllerMap.keySet()) {
            Object controller = controllerMap.get(key);
            Class controllerClazz = controller.getClass();
            Method[] methods = controllerClazz.getMethods();
            for (Method method : methods) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof GetMapping) {
                        String[] uris = ((GetMapping) annotation).value();
                        putMapping(uris, new DaisyControllerMethod(controller, method));
                        break;
                    }

                    if (annotation instanceof PostMapping) {
                        String[] uris = ((PostMapping) annotation).value();
                        putMapping(uris, new DaisyControllerMethod(controller, method));
                        break;
                    }

                    if (annotation instanceof PutMapping) {
                        String[] uris = ((PutMapping) annotation).value();
                        putMapping(uris, new DaisyControllerMethod(controller, method));
                        break;
                    }

                    if (annotation instanceof DeleteMapping) {
                        String[] uris = ((DeleteMapping) annotation).value();
                        putMapping(uris, new DaisyControllerMethod(controller, method));
                        break;
                    }
                }
            }
        }
    }

    private static void putMapping(String[] uris, DaisyControllerMethod daisyMethod) {
        for (String uri : uris) {
            controllerMethodMap.put(uri, daisyMethod);
        }
    }
}
