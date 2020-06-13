package zxh.demo.ioc.core.internal;

import zxh.demo.ioc.core.BeanFinder;
import zxh.demo.ioc.core.DependencyTreeNode;
import zxh.demo.ioc.core.IoCBean;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Stream;

/**
 * BeanFinderImpl:
 * @author zhangxuhai
 * @date 2020/6/13
*/
public class BeanFinderImpl implements BeanFinder {
    private Set<DependencyTreeNode> treeNodeSet = new HashSet<>();

    @Override
    public void scan(String basePackage) {
        List<Class<?>> classes = findAllClassesInBasePackage();
        classes.forEach(cls -> treeNodeSet.add(convertToDependencyTreeNode(cls)));
    }

    private List<Class<?>> findAllClassesInBasePackage() {
        return new ArrayList<>();
    }

    DependencyTreeNode convertToDependencyTreeNode(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(IoCBean.class)) {
            throw new IllegalArgumentException(
                    String.format("Illegal bean: No IoCBean Annotation found on the class %s.", clazz.getName()));
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        DependencyTreeNodeImpl thisNode = new DependencyTreeNodeImpl(clazz.getName());
        if (constructors.length == 0) {
            throw new IllegalClassForBean(
                    String.format("The class %s have no public constructor, which not supported by IoC.", clazz.getName()));
        }

        Constructor<?> constructor = Stream.of(clazz.getConstructors())
                .min(Comparator.comparing(c -> c.getParameterTypes().length))
                .orElseThrow(IllegalArgumentException::new);

        for (Class<?> cls : constructor.getParameterTypes()) {
            thisNode.addParent(convertToDependencyTreeNode(cls));
        }

        return thisNode;
    }

    @Override
    public Set<DependencyTreeNode> getRootNodeSet() {
        return treeNodeSet;
    }
}
