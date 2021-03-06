package com.cj.common.tuple;

import java.util.Optional;

/**
 * 功能: 元组(a, b)<br/>
 *
 * @author miracle
 */
public class Tuple2<A, B> extends Tuple {
    private A a;
    private B b;

    Tuple2(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Optional<A> _1() {
        return Optional.of(a);
    }

    @Override
    public Optional<B> _2() {
        return Optional.of(b);
    }

    @Override
    public <C> Optional<C> _3() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "Tuple2{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
