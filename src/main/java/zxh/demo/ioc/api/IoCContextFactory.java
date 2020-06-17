package zxh.demo.ioc.api;

import zxh.demo.ioc.api.internal.DefaultIoCContext;
import java.util.Objects;

/**
 * IoCContextFactory:
 * @author zhangxuhai
 * @date 2020/6/17
*/
public class IoCContextFactory {
    private static IoCContext instance = null;

    public static IoCContext create() {
        if (Objects.isNull(instance)) {
            instance = new DefaultIoCContext();
        }

        return instance;
    }
}
