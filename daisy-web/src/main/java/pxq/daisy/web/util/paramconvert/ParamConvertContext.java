package pxq.daisy.web.util.paramconvert;

import org.springframework.core.MethodParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 参数转换器上下文
 * @author peixiaoqing
 * @date 2022/01/22
 * @since 1.0.0
 *
 */
public class ParamConvertContext {

    private static List<ParamConvertStrategy> strategyList;

    static {
        strategyList = new ArrayList<>();
        strategyList.add(new ShotConvertStrategy());
        strategyList.add(new IntegerConvertStrategy());
        strategyList.add(new LongConvertStrategy());
        strategyList.add(new FloatConvertStrategy());
        strategyList.add(new DoubleConvertStrategy());
        strategyList.add(new StringConvertStrategy());
        strategyList.add(new ListConvertStrategy());
    }

    /**
     * 从url中获取参数，并按照要求转换成对应的类型
     * @param queryParams
     * @param methodParameter
     * @return
     */
    public static Object convert(Map<String, List<String>> queryParams, MethodParameter methodParameter){
        for (ParamConvertStrategy convertStrategy : strategyList) {
            if(convertStrategy.support(methodParameter)){
                return convertStrategy.convert(queryParams,methodParameter);
            }
        }

        return null;
    }
}
