package run.nya.petbbs.config.security.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 有该注解的缓存方法会抛出异常
 *
 * 2021/02/18
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
