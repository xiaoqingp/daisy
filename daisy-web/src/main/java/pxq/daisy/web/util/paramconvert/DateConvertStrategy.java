package pxq.daisy.web.util.paramconvert;

import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 时间转换类
 *
 * @author peixiaoqing
 * @date 2022/01/25
 * @since 1.0.0
 */
public class DateConvertStrategy implements ParamConvertStrategy {

    private static final Logger log = LoggerFactory.getLogger(DateConvertStrategy.class);

    @Override
    public Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        return this.convert(this.getValue(queryParams, methodParameter), methodParameter);
    }

    @Override
    public Object convert(JSONObject json, MethodParameter methodParameter) {
        return this.convert(json.getString(methodParameter.getParameterName()), methodParameter);
    }

    private Date convert(String value, MethodParameter methodParameter) {
        if (StrUtil.isNotBlank(value)) {
            DateTimeFormat annotation = methodParameter.getParameterAnnotation(DateTimeFormat.class);
            String pattern = "yyyy-MM-dd HH:mm:ss";
            if (null != annotation) {
                pattern = annotation.pattern();
            }
            FastDateFormat format = FastDateFormat.getInstance(pattern);
            try {
                return format.parse(value);
            } catch (Exception e) {
                log.error("日期字符串{}解析失败", value);
                throw new IllegalArgumentException("日期字符串" + value + "解析失败");
            }
        }
        return null;
    }

    @Override
    public boolean support(MethodParameter methodParameter) {
        return Date.class == methodParameter.getParameterType();
    }
}
