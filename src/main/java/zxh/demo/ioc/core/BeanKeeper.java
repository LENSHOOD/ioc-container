package zxh.demo.ioc.core;

import java.util.Optional;

/**
 * BeanKeeper:
 * @author zhangxuhai
 * @date 2020/6/12
*/
public interface BeanKeeper {
    /**
     * Register class as a bean
     *
     * @param fullyQualifiedClassName class full name
     */
    void register(String fullyQualifiedClassName);

    /**
     * Fetch bean by bean name
     *
     * @param beanName bean name
     * @param cls class name
     * @param <T> bean type
     * @return bean
     */
    <T> Optional<Bean<T>> get(String beanName, Class<T> cls);

    /**
     * Un-register a bean
     *
     * @param beanName bean name
     */
    void unRegister(String beanName);
}
