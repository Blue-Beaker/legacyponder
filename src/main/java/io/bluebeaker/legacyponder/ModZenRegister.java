package io.bluebeaker.legacyponder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to replace CraftTweaker's @ZenRegister.
 * Classes annotated with this will be manually registered with CraftTweaker
 * during client-side initialization, avoiding classloading issues on dedicated servers.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModZenRegister {
}
