package zxh.demo.ioc.core.internal;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import zxh.demo.ioc.core.DependencyTreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * DependencyTreeNode:
 * @author zhangxuhai
 * @date 2020/6/12
*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DependencyTreeNodeImpl implements DependencyTreeNode {
    @EqualsAndHashCode.Include
    private final String clsName;

    private final List<DependencyTreeNodeImpl> parents = new ArrayList<>();
    private DependencyTreeNodeImpl child = null;

    public DependencyTreeNodeImpl(String clsName) {
        this.clsName = clsName;
    }

    @Override
    public void addParent(DependencyTreeNode node) {
        if (!(node instanceof DependencyTreeNodeImpl)) {
            throw new IllegalArgumentException("Only DependencyTreeNodeImpl node supported.");
        }

        parents.add(requireNonNull((DependencyTreeNodeImpl) node));
        node.setChild(this);
    }

    @Override
    public void setChild(DependencyTreeNode node) {
        if (!(node instanceof DependencyTreeNodeImpl)) {
            throw new IllegalArgumentException("Only DependencyTreeNodeImpl node supported.");
        }

        child = (DependencyTreeNodeImpl) node;
    }

    @Override
    public DependencyTreeNode next() {
        int thisIndex = child.parents.indexOf(this);
        boolean isLast = thisIndex == child.parents.size() - 1;
        if (isLast) {
            return child;
        }

        return child.parents.get(thisIndex + 1);
    }

    @Override
    public DependencyTreeNode getFirstParent() {
        if (parents.isEmpty()) {
            return this;
        }

        return parents.get(0).getFirstParent();
    }

    @Override
    public DependencyTreeNode getRootChild() {
        if (isNull(child)) {
            return this;
        }

        return child.getRootChild();
    }

    @Override
    public String getClassFullName() {
        return clsName;
    }
}
