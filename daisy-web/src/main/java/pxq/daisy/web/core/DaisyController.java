package pxq.daisy.web.core;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import pxq.daisy.web.spring.SpringAppContext;
import pxq.daisy.web.util.paramconvert.ParamConvertContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * Controller方法类
 *
 * @author peixiaoqing
 * @date 2021/12/31
 * @since 1.0.0
 */
public class DaisyController {
    private final Object target;
    private final Method targetMethod;
    private final MethodParameter[] parameters;

    private SimpleController controller;

    public DaisyController(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
        this.parameters = initMethodParameters();

        // 生成代理类被netty的handler处理
        InvocationHandler handler = new SimpleControllerHandler(this);
        this.controller = (SimpleController) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                new Class[]{SimpleController.class},
                handler);
    }

    private MethodParameter[] initMethodParameters() {
        ParameterNameDiscoverer discoverer = SpringAppContext.getBean(ParameterNameDiscoverer.class);

        int count = this.targetMethod.getParameterCount();
        MethodParameter[] result = new MethodParameter[count];
        for (int i = 0; i < count; i++) {
            MethodParameter parameter = new MethodParameter(targetMethod, i);
            parameter.initParameterNameDiscovery(discoverer);
            result[i] = parameter;
        }
        return result;
    }

    public SimpleController getController() {
        return controller;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * 执行实际的Controller的method方法
     *
     * @param args
     * @return
     */
    public Object invoke(Object[] args) throws InvocationTargetException, IllegalAccessException {
        HttpRequest httpRequest = (HttpRequest) args[0];

        HttpMethod httpMethod = httpRequest.method();
        // 从url中获取参数
        QueryStringDecoder queryDecoder = new QueryStringDecoder(httpRequest.uri(), CharsetUtil.UTF_8);
        Map<String, List<String>> queryParams = queryDecoder.parameters();

        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            params[i] = ParamConvertContext.convert(queryParams, parameters[i]);
        }

        // 执行实际Controller的方法
        return targetMethod.invoke(target, params);
    }
}
