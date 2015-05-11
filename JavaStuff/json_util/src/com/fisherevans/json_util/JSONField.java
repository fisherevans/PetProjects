package com.fisherevans.webservutil.json;

import java.lang.annotation.*;

/**
 * Author: Fisher Evans
 * Date: 7/21/14
 */

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONField {
    public String name();
    public String desc();
}
