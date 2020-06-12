package zxh.demo.ioc.core;

import java.util.Set;

/**
 * BeanFinder:
 * @author zhangxuhai
 * @date 2020/6/12
*/
public interface BeanFinder {
    /**
     * Scan and find all class defined as a bean to store to dependency tree
     */
    void scan();

    /**
     * Get set of root node of dependency tree
     *
     * @return set of dependency tree node
     */
    Set<DependencyTreeNode> getRootNodeSet();
}
