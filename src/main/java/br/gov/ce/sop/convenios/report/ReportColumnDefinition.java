package br.gov.ce.sop.convenios.report;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportColumnDefinition {

    String property();

    Class<?> type() default String.class;

    int width() default 80;

    String title();

    int order() default 20;

}
