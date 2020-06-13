package zxh.demo.ioc.core.internal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import zxh.demo.ioc.core.DependencyTreeNode;
import zxh.demo.ioc.core.IoCBean;

class BeanFinderImplTest {
    @IoCBean
    public static class ParentBeanA {

    }

    @IoCBean
    public static class ParentBeanB {
        private final ParentBeanA a;

        public ParentBeanB(ParentBeanA a) {
            this.a = a;
        }
    }

    @IoCBean
    public static class ChildBean {
        private final ParentBeanA a;
        private final ParentBeanB b;

        public ChildBean(ParentBeanA a, ParentBeanB b) {
            this.a = a;
            this.b = b;
        }
    }

    @Test
    void should_convert_class_to_dependency_tree_node() {
        // given
        Class<ChildBean> cls = ChildBean.class;
        BeanFinderImpl beanFinder = new BeanFinderImpl();

        // when
        DependencyTreeNode node = beanFinder.convertToDependencyTreeNode(cls);

        // then
        DependencyTreeNode first = node.getFirstParent();
        assertThat(first.getClassFullName(), is(ParentBeanA.class.getName()));
        DependencyTreeNode second = first.next();
        assertThat(second.getClassFullName(), is(ParentBeanB.class.getName()));
        DependencyTreeNode third = second.next();
        assertThat(third.getClassFullName(), is(ParentBeanA.class.getName()));
        DependencyTreeNode last = third.next();
        assertThat(last.getClassFullName(), is(ChildBean.class.getName()));
    }
}