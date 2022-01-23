package pxq.daisy.web.util.paramconvert;

import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数组转换器
 * 只实现List类型的数组
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class ListConvertStrategy implements ParamConvertStrategy {

    private static final String SHORT = "java.util.List<java.lang.Short>";
    private static final String INTEGER = "java.util.List<java.lang.Integer>";
    private static final String LONG = "ava.util.List<java.lang.Long>";
    private static final String FLOAT = "ava.util.List<java.lang.Float>";
    private static final String DOUBLE = "ava.util.List<java.lang.Double>";
    private static final String STRING = "ava.util.List<java.lang.String>";

    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        List<String> values = queryParams.get(methodParameter.getParameterName());
        if (null == values || values.size() == 0) {
            return null;
        }

        String genericType = methodParameter.getGenericParameterType().toString();
        if (values.size() == 1) {
            // 对 param=1,2,3,4 这种格式进行转换
            String tmp = values.get(0);
            return Stream.of(tmp.split(","))
                    .map(s -> this.convert(s, genericType))
                    .collect(Collectors.toList());
        } else {
            return values.stream()
                    .map(s -> this.convert(s, genericType))
                    .collect(Collectors.toList());
        }
    }

    // 根据泛型转换参数
    private Object convert(String s, String genericType) {
        Object value = null;
        switch (genericType) {
            case SHORT:
                value = Short.valueOf(s);
                break;
            case INTEGER:
                value = Integer.valueOf(s);
                break;
            case FLOAT:
                value = Float.valueOf(s);
                break;
            case DOUBLE:
                value = Double.valueOf(s);
                break;
            case LONG:
                value = Long.valueOf(s);
                break;
            default:
                value = s;
        }

        return value;
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        Class type = methodParameter.getParameterType();
        if (List.class == type) {
            return true;
        }
        return false;
    }
}
