package pxq.daisy.web.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于向页面返回数据
 *
 * @author peixiaoqing
 * @date 2022-01-29
 */
public class DaisyResponse {
    private Map<String, Object> variables = new HashMap<>();

    public void addAttribute(String key, Object value) {
        variables.put(key, value);
    }

    public Map<String, Object> getVariables() {
        return variables;
    }
}
