package org.kohsuke;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Indicates that the class javadoc should be captured.
 *
 * @author Kohsuke Kawaguchi
 */
@Target(TYPE)
@Inherited
@Retention(RUNTIME)
public @interface CaptureJavadoc {
}
