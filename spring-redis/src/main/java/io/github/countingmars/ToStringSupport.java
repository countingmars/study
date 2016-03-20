package io.github.countingmars;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by Mars on 3/20/16.
 */
public class ToStringSupport {
    @Override public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
