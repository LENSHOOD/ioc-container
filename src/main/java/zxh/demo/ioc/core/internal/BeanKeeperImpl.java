package zxh.demo.ioc.core.internal;

import static java.util.Objects.*;

import com.google.common.base.CaseFormat;
import lombok.SneakyThrows;
import zxh.demo.ioc.core.Bean;
import zxh.demo.ioc.core.BeanKeeper;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Stream;

/**
 * BeanKeeperImpl:
 * @author zhangxuhai
 * @date 2020/6/12
*/
public class BeanKeeperImpl implements BeanKeeper {
    private Map<String, Object> beanInstanceMap = new HashMap<>();

    @SneakyThrows
    @Override
    public void register(String fullyQualifiedClassName) {
        Class<?> cls = Class.forName(fullyQualifiedClassName);
        if (get(getBeanName(cls), cls).isPresent()) {
            return;
        }

        Constructor<?>[] constructors = cls.getConstructors();
        if (constructors.length == 0) {
            throw new ClassNotFoundException();
        }

        Constructor<?> leastParamsConstructor = getLeastParameterConstructor(constructors);
        Object[] params = getConstructorParams(leastParamsConstructor);
        beanInstanceMap.put(getBeanName(cls), leastParamsConstructor.newInstance(params));
    }

    private Constructor<?> getLeastParameterConstructor(Constructor<?>[] constructors) {
        return Stream
                .of(constructors)
                .min(Comparator.comparing(c -> c.getParameterTypes().length))
                .orElseThrow(IllegalArgumentException::new);
    }

    private Object[] getConstructorParams(Constructor<?> leastParamsConstructor) {
        return Stream.of(leastParamsConstructor.getParameterTypes())
                .map(paramCls -> get(getBeanName(paramCls), paramCls)
                        .orElseThrow(() -> new DependencyBeanNotFoundException(paramCls.getName())))
                .map(Bean::getInstance)
                .toArray();
    }

    private String getBeanName(Class<?> cls) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, cls.getSimpleName());
    }

    @Override
    public <T> Optional<Bean<T>> get(String beanName, Class<T> cls) {
        Object instance = beanInstanceMap.get(beanName);
        if (isNull(instance)) {
            return Optional.empty();
        }

        if (!cls.isInstance(instance)) {
            return Optional.empty();
        }

        return Optional.of(new Bean<>(beanName, cls, cls.cast(instance)));
    }

    @Override
    public void unRegister(String beanName) {
        beanInstanceMap.remove(requireNonNull(beanName));
    }
}
