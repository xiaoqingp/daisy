package pxq.daisy.web.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author peixiaoqing
 * @date 2021/12/31
 */
@Component
public class SpringAppContext implements ApplicationContextAware {

    private static ApplicationContext context = null;

    /**
     * 类获取Bean对象
     *
     * @param clazz Bean类
     * @param <T>   Bean类型
     * @return Bean对象
     */
    public static <T> T getBean(Class <T> clazz) {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }
}
