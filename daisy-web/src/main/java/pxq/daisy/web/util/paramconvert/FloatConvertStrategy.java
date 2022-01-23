package pxq.daisy.web.util.paramconvert;

import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 单精度浮点数转换器
 *
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class FloatConvertStrategy implements ParamConvertStrategy {
    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        List<String> values = queryParams.get(methodParameter.getParameterName());
        if (null == values || values.size() == 0) {
            if (float.class == methodParameter.getParameterType()) {
                // 基本类型
                return (float) 0;
            } else {
                return null;
            }
        }

        return Float.valueOf(values.get(0));
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        Class type = methodParameter.getParameterType();
        if (Float.class == type || float.class == type) {
            return true;
        }
        return false;
    }
}
