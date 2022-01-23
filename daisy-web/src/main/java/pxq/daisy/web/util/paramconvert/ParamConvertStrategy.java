package pxq.daisy.web.util.paramconvert;

import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * http 请求参数转换器
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public interface ParamConvertStrategy {

    /**
     * 从url中获取参数，并按照要求转换成对应的类型
     * @param queryParams url后面的参数
     * @param methodParameter 参数类型
     * @return
     */
    Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter);

    /**
     * 判断本策略是否支持这个参数类型
     *
     * @param methodParameter 参数类型
     * @return
     */
    boolean support(MethodParameter methodParameter);
}
