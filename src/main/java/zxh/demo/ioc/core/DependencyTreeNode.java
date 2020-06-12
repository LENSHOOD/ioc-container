package zxh.demo.ioc.core;

/**
 * DependencyTreeNode:
 * A inversion tree that include a class and it's all dependencies
 * and the dependency's all dependencies.
 *
 * dep.1      dep.2        dep.3
 *     \     /             /
 *      dep.4             dep.5
 *           \           /
 *            target class
 *
 * Assume target class A have two field dep.4 and dep.5 that need inject
 * from constructor, and the dep.4 also have two fields dep.1 and dep.2,
 * dep.5 have one field dep.3, then the whole dependency relation can be
 * abstract to a inversion tree (see above).
 *
 * @author zhangxuhai
 * @date 2020/6/12
*/
public interface DependencyTreeNode {
    /**
     * Add one node as a parent
     *
     * @param node node
     */
    void addParent(DependencyTreeNode node);

    /**
     * Set child node
     *
     * @param node node
     */
    void setChild(DependencyTreeNode node);

    /**
     * Get next node traverse by LRN
     *
     * @return the next DependencyTreeNode
     */
    DependencyTreeNode next();

    /**
     * Reset to most left leaf node
     *
     * @return first node
     */
    DependencyTreeNode getFirstParent();

    /**
     * Return root child
     *
     * @return root child node
     */
    DependencyTreeNode getRootChild();

    /**
     * Return class full name in the node
     *
     * @return class full name
     */
    String getClassFullName();
}
