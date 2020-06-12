package zxh.demo.ioc.core;

import lombok.*;

/**
 * Bean:
 * @author zhangxuhai
 * @date 2020/6/12
*/
@AllArgsConstructor()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Bean<T> {
    @EqualsAndHashCode.Include
    private final String name;
    private final Class<T> clazz;
    private final T instance;
}
