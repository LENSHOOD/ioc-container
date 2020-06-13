package zxh.demo.ioc.core.internal;

/**
 * ClassNotFoundException:
 *
 * @author zhangxuhai
 * @date 2020/6/13
 */
public class IllegalClassForBean extends RuntimeException {

    public IllegalClassForBean(String message) {
        super(message);
    }
}
