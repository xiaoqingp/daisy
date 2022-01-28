package pxq.daisy.web.util.paramconvert;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 字符串转换器
 *
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class StringConvertStrategy implements ParamConvertStrategy {
    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        String value = getValue(queryParams, methodParameter);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return value;
    }

    @Override
    public Object convert(JSONObject json, MethodParameter methodParameter) {
        return json.getString(methodParameter.getParameterName());
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        return String.class == methodParameter.getParameterType();
    }
}
