package pxq.daisy.web.util.paramconvert;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * http 请求参数转换器
 *
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public interface ParamConvertStrategy {

    /**
     * 从queryParams中获取一个value
     *
     * @param queryParams http请求参数
     * @param methodParameter controller的method 入参
     * @return 从请求参数中获取的字符串
     */
    default String getValue(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        String value = "";
        List<String> values = queryParams.get(methodParameter.getParameterName());
        if (null != values && values.size() >= 1) {
            value = values.get(0);
        }

        return value;
    }

    /**
     * 从url中获取参数，并按照要求转换成对应的类型
     *
     * @param queryParams     url后面的参数
     * @param methodParameter controller的method 入参
     * @return controller的method 入参的实例化对象
     */
    Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter);

    /**
     * 从http content中获取参数
     * @param json  http post 的json请求
     * @param methodParameter controller的method 入参
     * @return controller的method 入参的实例化对象
     */
    Object convert(JSONObject json, MethodParameter methodParameter);

    /**
     * 判断本策略是否支持这个参数类型
     *
     * @param methodParameter 参数类型
     * @return 布尔值
     */
    boolean support(MethodParameter methodParameter);
}
