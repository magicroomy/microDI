package de.microdi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Component
{
   public enum TYPE { Singleton, New } ;
   public abstract String value() default "";
   TYPE type() default TYPE.Singleton ;
   
}
