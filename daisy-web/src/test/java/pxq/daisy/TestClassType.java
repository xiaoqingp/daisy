package pxq.daisy;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import pxq.daisy.web.spring.SpringAppContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClassType {
    public static void main(String[] args) throws NoSuchMethodException {
        TestClassType test = new TestClassType();
        Method targetMethod = TestClassType.class.getMethod("test1",List.class);

        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        int count = targetMethod.getParameterCount();
        MethodParameter[] result = new MethodParameter[count];
        for (int i = 0; i < count; i++) {
            MethodParameter parameter = new MethodParameter(targetMethod, i);
            parameter.initParameterNameDiscovery(discoverer);

            result[i] = parameter;
            Class type = parameter.getParameterType();
            System.out.println(type);
            System.out.println(parameter.getGenericParameterType());



        }

        System.out.println(StudentTestBO.class.getName());

    }

    public String test1(List<String> arr){
        return "111";
    }
}
