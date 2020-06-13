package zxh.demo.ioc.core.internal;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import zxh.demo.ioc.core.DependencyTreeNode;
import java.util.ArrayList;
import java.util.Comparator;
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

        DependencyTreeNodeImpl implNode = (DependencyTreeNodeImpl) node;
        parents.add(requireNonNull(implNode));
        implNode.setChild(this);
        ((DependencyTreeNodeImpl) getRootChild()).reArrange();
    }

    private void setChild(DependencyTreeNode node) {
        if (!(node instanceof DependencyTreeNodeImpl)) {
            throw new IllegalArgumentException("Only DependencyTreeNodeImpl node supported.");
        }

        child = (DependencyTreeNodeImpl) node;
    }

    @Override
    public DependencyTreeNode next() {
        if (isNull(child)) {
            return null;
        }

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

        return parents.stream()
                .max(Comparator.comparing(DependencyTreeNodeImpl::getMaximumDepth))
                .orElseThrow(IllegalArgumentException::new)
                .getFirstParent();
    }

    private int getMaximumDepth() {
        if (parents.isEmpty()) {
            return 0;
        }

        return parents.stream().mapToInt(DependencyTreeNodeImpl::getMaximumDepth).max().orElse(0) + 1;
    }

    private void reArrange() {
        if (parents.isEmpty()) {
            return;
        }

        parents.sort(Comparator.comparing(DependencyTreeNodeImpl::getMaximumDepth).reversed());
        parents.forEach(DependencyTreeNodeImpl::reArrange);
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
