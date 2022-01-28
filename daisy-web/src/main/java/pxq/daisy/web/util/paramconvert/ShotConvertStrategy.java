package pxq.daisy.web.util.paramconvert;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 短整型
 *
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class ShotConvertStrategy implements ParamConvertStrategy {
    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        return this.convert(this.getValue(queryParams, methodParameter), methodParameter);
    }

    @Override
    public Object convert(JSONObject json, MethodParameter methodParameter) {
        return this.convert(json.getString(methodParameter.getParameterName()), methodParameter);
    }

    private Object convert(String value, MethodParameter methodParameter) {
        if (StrUtil.isBlank(value)) {
            if (short.class == methodParameter.getParameterType()) {
                // 基本类型
                return (short) 0;
            } else {
                return null;
            }
        }

        return Short.valueOf(value);
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        Class<?> type = methodParameter.getParameterType();
        return Short.class == type || short.class == type;
    }
}
