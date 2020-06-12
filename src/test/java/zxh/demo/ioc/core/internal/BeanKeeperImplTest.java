package zxh.demo.ioc.core.internal;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import zxh.demo.ioc.core.Bean;
import zxh.demo.ioc.core.BeanKeeper;
import java.util.Optional;

class BeanKeeperImplTest {
    public static class ParentBeanA {

    }

    public static class ParentBeanB {
        private final ParentBeanA a;

        public ParentBeanB(ParentBeanA a) {
            this.a = a;
        }
    }

    public static class ChildBean {
        private final ParentBeanA a;
        private final ParentBeanB b;

        public ChildBean(ParentBeanA a, ParentBeanB b) {
            this.a = a;
            this.b = b;
        }
    }

    @Test
    void should_register_class_and_inject_dependency() {
        // given
        BeanKeeper beanKeeper = new BeanKeeperImpl();

        // when
        beanKeeper.register(ParentBeanA.class.getName());
        beanKeeper.register(ParentBeanB.class.getName());
        beanKeeper.register(ChildBean.class.getName());

        // then
        Optional<Bean<ChildBean>> childBean = beanKeeper.get("childBean", ChildBean.class);
        assertTrue(childBean.isPresent());
        assertNotNull(childBean.get().getInstance());
    }

    @Test
    void should_fail_when_dependency_bean_not_found() {
        // given
        BeanKeeper beanKeeper = new BeanKeeperImpl();

        // when
        DependencyBeanNotFoundException dependencyBeanNotFoundException =
                assertThrows(DependencyBeanNotFoundException.class, () -> beanKeeper.register(ChildBean.class.getName()));

        // then
        MatcherAssert.assertThat(dependencyBeanNotFoundException.getMessage(), Matchers.is(ParentBeanA.class.getName()));
    }

}