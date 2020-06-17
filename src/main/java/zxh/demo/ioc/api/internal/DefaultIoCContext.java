package zxh.demo.ioc.api.internal;

import static java.util.Objects.isNull;

import zxh.demo.ioc.api.IoCContext;
import zxh.demo.ioc.core.Bean;
import zxh.demo.ioc.core.BeanFinder;
import zxh.demo.ioc.core.BeanKeeper;
import zxh.demo.ioc.core.DependencyTreeNode;
import zxh.demo.ioc.core.internal.BeanFinderImpl;
import zxh.demo.ioc.core.internal.BeanKeeperImpl;
import java.util.Optional;

/**
 * DefaultIoCContext:
 * @author zhangxuhai
 * @date 2020/6/17
*/
public class DefaultIoCContext implements IoCContext {
    private final BeanFinder beanFinder = new BeanFinderImpl();
    private final BeanKeeper beanKeeper = new BeanKeeperImpl();

    @Override
    public void startup() {
        beanFinder.scan("/");
        beanFinder.getRootNodeSet().forEach(node -> {
            DependencyTreeNode next = node.getFirstParent();
            do {
                beanKeeper.register(next.getClassFullName());
                next = node.next();
            } while (!isNull(next));
        });
    }

    @Override
    public <T> Optional<T> getBeanInstance(String beanName, Class<T> cls) {
        return beanKeeper.get(beanName, cls).map(Bean::getInstance);
    }
}
