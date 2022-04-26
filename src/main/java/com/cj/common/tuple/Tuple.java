package com.cj.common.tuple;

import java.util.Optional;

/**
 * 功能: 元组抽象类<br/>
 *
 * @author miracle
 */
public abstract class Tuple {
    public abstract <A> Optional<A> _1();

    public abstract <B> Optional<B> _2();

    public abstract <C> Optional<C> _3();

    public static <A, B> Tuple2 of(A a, B b) {
        return new Tuple2(a, b);
    }

    public static <A, B, C> Tuple3 of(A a, B b, C c) {
        return new Tuple3(a, b, c);
    }
}
