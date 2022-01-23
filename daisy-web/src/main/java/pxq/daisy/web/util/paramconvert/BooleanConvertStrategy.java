package pxq.daisy.web.util.paramconvert;

import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 比尔值转换器
 * @author peixiaoqing
 * @date 2022/01/23
 * @since 1.0.0
 */
public class BooleanConvertStrategy implements ParamConvertStrategy{
    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        List<String> values = queryParams.get(methodParameter.getParameterName());
        if (null == values || values.size() == 0) {
            if (boolean.class == methodParameter.getParameterType()) {
                // 基本类型
                return false;
            } else {
                return null;
            }
        }
        return Boolean.valueOf(values.get(0));
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        Class type = methodParameter.getParameterType();
        if (Boolean.class == type || boolean.class == type) {
            return true;
        }
        return false;
    }
}
