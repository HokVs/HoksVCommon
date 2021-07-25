package dev.hoksv.common.annotations;


/**
 * This annotation is for when
 * you want to make a singleton but want to make sure
 * it gets executed before every method call
 */
public @interface Singleton {

    String value();
}
