package pxq.daisy.web.util.paramconvert;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义bean类转换
 *
 * @author peixiaoqing
 * @date 2022/01/23
 * @since 1.0.0
 */
public class CustomizeBeanConvertStrategy implements ParamConvertStrategy {
    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        // 如果controller method的入参是自定义Bean 则使用fastjson去转换为Bean
        Map<String, Object> map2 = new HashMap<>();
        for (String key : queryParams.keySet()) {
            List<String> value = queryParams.get(key);
            if (value.size() == 1) {
                // 对 param=1,2,3,4 这种格式进行转换
                String tmp = value.get(0);
                String[] arr = tmp.split(",");
                if (arr.length == 1) {
                    map2.put(key, tmp);
                } else {
                    map2.put(key, Arrays.asList(arr));
                }
            } else {
                map2.put(key, value);
            }
        }
        JSONObject json = new JSONObject(map2);

        return JSON.toJavaObject(json, methodParameter.getParameterType());
    }

    @Override
    public Object convert(JSONObject json, MethodParameter methodParameter) {
        return JSON.toJavaObject(json, methodParameter.getParameterType());
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        Class<?> type = methodParameter.getParameterType();
        String className = type.getName();
        if (!className.startsWith("java")
                && short.class != type
                && int.class != type
                && float.class != type
                && double.class != type
                && long.class != type
                && boolean.class != type) {
            return true;
        }

        return false;
    }
}
