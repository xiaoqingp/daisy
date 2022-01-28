package pxq.daisy.web.util.paramconvert;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 参数转换器上下文
 *
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 */
public class ParamConvertContext {

    private static final List<ParamConvertStrategy> strategyList;

    static {
        strategyList = new ArrayList<>();
        strategyList.add(new BooleanConvertStrategy());
        strategyList.add(new CustomizeBeanConvertStrategy());
        strategyList.add(new DateConvertStrategy());
        strategyList.add(new DoubleConvertStrategy());
        strategyList.add(new FloatConvertStrategy());
        strategyList.add(new IntegerConvertStrategy());
        strategyList.add(new ListConvertStrategy());
        strategyList.add(new LongConvertStrategy());
        strategyList.add(new ShotConvertStrategy());
        strategyList.add(new StringConvertStrategy());
    }

    /**
     * TODO 增加一个缓存记录 保存每个方法对应的转换策略
     * 从url中获取参数，并按照要求转换成对应的类型
     *
     * @param queryParams     url中的参数
     * @param methodParameter controller中method的参数类型
     * @return method需要的入参的对象
     */
    public static Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter) {
        for (ParamConvertStrategy convertStrategy : strategyList) {
            if (convertStrategy.support(methodParameter)) {
                return convertStrategy.convert(queryParams, methodParameter);
            }
        }

        return null;
    }

    /**
     * 从json中获取参数
     *
     * @param json http content中的请求
     * @param methodParameter 参数类型
     * @return method需要的入参的对象
     */
    public static Object convert(JSONObject json, MethodParameter methodParameter) {
        for (ParamConvertStrategy convertStrategy : strategyList) {
            if (convertStrategy.support(methodParameter)) {
                return convertStrategy.convert(json, methodParameter);
            }
        }

        return null;
    }
}
