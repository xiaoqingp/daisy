package pxq.daisy.test;

import pxq.daisy.test.web.HelloController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMethodInvoke {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        HelloController controller = new HelloController();
        Method method = HelloController.class.getMethod("init1", String.class, int.class);
        Object[] params = new Object[2];
        params[0] = "12345";
        params[1] = 0;

        method.invoke(controller,params);

        Method method2 = HelloController.class.getMethod("init2", String.class, int.class,
                Integer.class, short.class,
                Short.class, long.class,
                Long.class);
        params = new Object[7];
        params[0] = null;
        params[1] = 0;
        params[2] = null;
        params[3] = (short)0;
        params[4] = null;
        params[5] = (long)0;
        params[6] = null;
        method2.invoke(controller,params);
    }
}
