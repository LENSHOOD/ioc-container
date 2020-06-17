package zxh.demo.ioc.api;

import java.util.Optional;

/**
 * IoCContext:
 * @author zhangxuhai
 * @date 2020/6/12
*/
public interface IoCContext {
    /**
     * Start up IoC context
     */
    void startup();

    /**
     * Get bean instance by name and class
     *
     * @param beanName bean name
     * @param cls class
     * @param <T> class type
     * @return bean instance or empty if not exist
     */
    <T> Optional<T> getBeanInstance(String beanName, Class<T> cls);
}
