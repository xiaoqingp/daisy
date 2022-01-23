package pxq.daisy.web.util.paramconvert;

import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 字符串转换器
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class StringConvertStrategy implements ParamConvertStrategy{
    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        List<String> values = queryParams.get(methodParameter.getParameterName());
        if (null == values || values.size() == 0) {
            return null;
        }
        return values.get(0);
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        if (String.class == methodParameter.getParameterType()) {
            return true;
        }
        return false;
    }
}
