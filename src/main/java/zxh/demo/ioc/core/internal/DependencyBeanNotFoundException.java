package zxh.demo.ioc.core.internal;

/**
 * TheDependencyBeanNotFound:
 * @author zhangxuhai
 * @date 2020/6/13
*/
public class DependencyBeanNotFoundException extends RuntimeException {
    public DependencyBeanNotFoundException(String message) {
        super(message);
    }
}
