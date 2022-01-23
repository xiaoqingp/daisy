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
 * @author peixiaoqing
 * @date 2021/12/30
 * @since 1.0.0
 */
public class ControllerFactory {

    private static final Map<String, DaisyController> controllerMap = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取代理类
     *
     * @param uri
     * @return
     */
    public static SimpleController create(String uri) {
        int index = uri.indexOf("?");
        DaisyController controller = controllerMap.get(uri.substring(0, index));
        return controller.getController();
    }

    /**
     * 初始化工厂
     * 解析使用Controller注解的java类，
     * 并把其中使用GetMapping、PostMapping、PutMapping、DeleteMapping注解的方法生成SimpleController的代理类，
     * 并缓存uri和代理类
     *
     * @param ctx
     */
    public static void init(ApplicationContext ctx) {
        Map<String, Object> controllerMap = ctx.getBeansWithAnnotation(Controller.class);
        for (String key : controllerMap.keySet()) {
            Object controller = controllerMap.get(key);
            Class controllerClazz = controller.getClass();
            Method[] methods = controllerClazz.getMethods();
            for (Method method : methods) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof GetMapping) {
                        String[] uris = ((GetMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method));
                        break;
                    }

                    if (annotation instanceof PostMapping) {
                        String[] uris = ((PostMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method));
                        break;
                    }

                    if (annotation instanceof PutMapping) {
                        String[] uris = ((PutMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method));
                        break;
                    }

                    if (annotation instanceof DeleteMapping) {
                        String[] uris = ((DeleteMapping) annotation).value();
                        putMapping(uris, new DaisyController(controller, method));
                        break;
                    }
                }
            }
        }
    }

    private static void putMapping(String[] uris, DaisyController controller) {
        for (String uri : uris) {
            DaisyController value = controllerMap.get(uri);
            if (null != value) {
                throw new RuntimeException("处理方法" + controller.getTargetMethod().toString() + "时发现uri=" + uri + "已经存在，" + value.getTargetMethod().toString());
            }
            controllerMap.put(uri, controller);
        }
    }
}
