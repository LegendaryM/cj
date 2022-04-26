package com.cj.common.tuple;

import java.util.Optional;

/**
 * 功能: 元组(a, b, c)<br/>
 *
 * @author miracle
 */
public class Tuple3<A, B, C> extends Tuple {
    private A a;
    private B b;
    private C c;

    Tuple3(A e, B t, C k) {
        this.a = e;
        this.b = t;
        this.c = k;
    }

    public Optional<A> _1() {
        return Optional.of(a);
    }

    public Optional<B> _2() {
        return Optional.of(b);
    }

    public Optional<C> _3() {
        return Optional.of(c);
    }

    @Override
    public String toString() {
        return "Tuple3{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}