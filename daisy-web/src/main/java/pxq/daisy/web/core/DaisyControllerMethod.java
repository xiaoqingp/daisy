/**
 * 文件名：DaisyMethod<br/>
 * CopyRight (c) 2019-2030：<br/>
 * 创建人：peixiaoqing
 * 日期：2021/12/31
 * 修改人：
 * 日期：
 * 描述：
 * 版本号：2.5.4
 */
package pxq.daisy.web.core;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import pxq.daisy.web.spring.SpringAppContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * Controller方法类
 *
 * @author peixiaoqing
 * @date 2021/12/31
 */
public class DaisyControllerMethod {
    private final Object target;
    private final Method targetMethod;
    private final MethodParameter[] parameters;

    private SimpleController controller;

    public DaisyControllerMethod(Object target, Method targetMethod) {
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
        int count = this.targetMethod.getParameterCount();
        MethodParameter[] result = new MethodParameter[count];
        for (int i = 0; i < count; i++) {
            MethodParameter parameter = new MethodParameter(targetMethod, i);
            ParameterNameDiscoverer discoverer = SpringAppContext.getBean(ParameterNameDiscoverer.class);
            parameter.initParameterNameDiscovery(discoverer);

            result[i] = parameter;
        }
        return result;
    }

    public SimpleController getController() {
        return controller;
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
        Map <String, List <String>> queryParams = queryDecoder.parameters();

        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];

            if (HttpMethod.GET == httpMethod) {
                params[0] = this.getParam(queryParams, parameter.getParameterName(), parameter.getParameterType());
            }
        }

        // 执行实际Controller的方法
        return targetMethod.invoke(target, params);
    }

    // 从queryParams获取参数
    private Object getParam(Map <String, List <String>> queryParams, String name, Class type) {
        List <String> values = queryParams.get(name);
        if (null == values || values.size() == 0) {
            return null;
        }

        Object param = null;
        if (values.size() == 1) {
            String value = values.get(0);
            param = convert(value, type);
        } else {
            // 数组
        }

        return param;
    }

    private Object convert(String value, Class type) {
        if (type == String.class) {
            return value;
        }

        if (type == Integer.class) {
            return Integer.valueOf(value);
        }
        if (type == Long.class) {
            return Long.valueOf(value);
        }

        if (type == Float.class) {
            return Float.valueOf(value);
        }
        if (type == Double.class) {
            return Double.valueOf(value);
        }

        return value;
    }
}
