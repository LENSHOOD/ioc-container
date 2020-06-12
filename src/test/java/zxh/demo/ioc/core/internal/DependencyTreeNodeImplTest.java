package zxh.demo.ioc.core.internal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import zxh.demo.ioc.core.DependencyTreeNode;

class DependencyTreeNodeImplTest {
    @Test
    void should_return_to_first_parent() {
        // given
        DependencyTreeNode node1 = new DependencyTreeNodeImpl("cls1");
        DependencyTreeNode node2 = new DependencyTreeNodeImpl("cls2");
        DependencyTreeNode node3 = new DependencyTreeNodeImpl("cls3");
        DependencyTreeNode node4 = new DependencyTreeNodeImpl("cls4");
        DependencyTreeNode node5 = new DependencyTreeNodeImpl("cls5");
        node1.addParent(node2);
        node1.addParent(node3);
        node2.addParent(node4);
        node2.addParent(node5);

        // when
        DependencyTreeNode firstParent = node1.getFirstParent();

        // then
        assertThat(firstParent.getClassFullName(), is("cls4"));
    }

    @Test
    void should_return_next_node() {
        // given
        DependencyTreeNode node1 = new DependencyTreeNodeImpl("cls1");
        DependencyTreeNode node2 = new DependencyTreeNodeImpl("cls2");
        DependencyTreeNode node3 = new DependencyTreeNodeImpl("cls3");
        DependencyTreeNode node4 = new DependencyTreeNodeImpl("cls4");
        DependencyTreeNode node5 = new DependencyTreeNodeImpl("cls5");
        node1.addParent(node2);
        node1.addParent(node3);
        node2.addParent(node4);
        node2.addParent(node5);

        // when
        DependencyTreeNode expectNode5 = node4.next();
        DependencyTreeNode expectNode2 = node5.next();
        DependencyTreeNode expectNode3 = node2.next();

        // then
        assertThat(expectNode5.getClassFullName(), is("cls5"));
        assertThat(expectNode2.getClassFullName(), is("cls2"));
        assertThat(expectNode3.getClassFullName(), is("cls3"));
    }

    @Test
    void should_return_root_node() {
        // given
        DependencyTreeNode node1 = new DependencyTreeNodeImpl("cls1");
        DependencyTreeNode node2 = new DependencyTreeNodeImpl("cls2");
        DependencyTreeNode node3 = new DependencyTreeNodeImpl("cls3");
        DependencyTreeNode node4 = new DependencyTreeNodeImpl("cls4");
        DependencyTreeNode node5 = new DependencyTreeNodeImpl("cls5");
        node1.addParent(node2);
        node1.addParent(node3);
        node2.addParent(node4);
        node2.addParent(node5);

        // when
        DependencyTreeNode root = node4.getRootChild();

        // then
        assertThat(root.getClassFullName(), is("cls1"));
    }
}