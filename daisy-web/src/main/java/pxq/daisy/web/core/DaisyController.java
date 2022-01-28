package pxq.daisy.web.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
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

    private SimpleController controllerProxy;

    public DaisyController(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
        this.parameters = initMethodParameters();

        // 生成代理类被netty的handler处理
        InvocationHandler handler = new SimpleControllerHandler(this);
        this.controllerProxy = (SimpleController) Proxy.newProxyInstance(target.getClass().getClassLoader(),
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

    public SimpleController getControllerProxy() {
        return controllerProxy;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * 执行实际的Controller的method方法
     *
     * @param httpRequest
     * @return
     */
    public Object invoke(FullHttpRequest httpRequest) throws InvocationTargetException, IllegalAccessException {
        HttpHeaders headers = httpRequest.headers();
        HttpMethod httpMethod = httpRequest.method();

        Object[] params = new Object[parameters.length];

        // 从url中获取参数
        QueryStringDecoder queryDecoder = new QueryStringDecoder(httpRequest.uri(), CharsetUtil.UTF_8);
        Map<String, List<String>> queryParams = queryDecoder.parameters();
        if (queryParams.size() > 0) {
            for (int i = 0; i < parameters.length; i++) {
                params[i] = ParamConvertContext.convert(queryParams, parameters[i]);
            }
        }

        ByteBuf content = httpRequest.content();
        if (null != content && content.isReadable()) {
            String body = content.toString(CharsetUtil.UTF_8);
            if (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString().equals(headers.get(HttpHeaderNames.CONTENT_TYPE))) {
                // form表单提交
                queryDecoder = new QueryStringDecoder(httpRequest.uri() + "?" + body, CharsetUtil.UTF_8);
                queryParams = queryDecoder.parameters();
                for (int i = 0; i < parameters.length; i++) {
                    params[i] = ParamConvertContext.convert(queryParams, parameters[i]);
                }
            } else if (HttpHeaderValues.APPLICATION_JSON.toString().equals(headers.get(HttpHeaderNames.CONTENT_TYPE))) {
                // json 字符串提交
                JSONObject json = JSON.parseObject(body);
                for (int i = 0; i < parameters.length; i++) {
                    params[i] = ParamConvertContext.convert(json, parameters[i]);
                }
            }

        }

        // 执行实际Controller的方法
        return targetMethod.invoke(target, params);
    }
}
