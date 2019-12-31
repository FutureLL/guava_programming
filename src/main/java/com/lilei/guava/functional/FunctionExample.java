package com.lilei.guava.functional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Handler;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/10 12:16
 * @version: 1.0
 * @modified By:
 */
public class FunctionExample {

    interface Handler<IN, OUT> {
        OUT Handle(IN input);

        class LengthDoubleHandler implements Handler<String, Integer> {

            @Override
            public Integer Handle(String input) {
                return input.length() * 2;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Function<String, Integer> function = new Function<String, Integer>() {

            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                //断言传进来的参数是不是null,如果是null会抛出异常NullPointerException
                Preconditions.checkNotNull(input, "The input Stream shoule not be null.");
                return input.length();
            }
        };
        //Exception in thread "main" java.lang.NullPointerException: The input Stream shoule not be null.
        //System.out.println(function.apply(null));
        System.out.println(function.apply("Hello"));

        //接口实现
        System.out.println(new Handler.LengthDoubleHandler().Handle("Hello"));

        /**
         * Functions: 函数集的工厂方法
         *
         * ToStringFunction 等同于Java中的 Object::toString
         *      里边重写了两个方法: apply() 和 toString()
         *
         *  源码实现:
         *  private enum ToStringFunction implements Function<Object, String> {
         *     INSTANCE;
         *
         *     @Override
         *     public String apply(Object o) {
         *       checkNotNull(o); // eager for GWT.
         *       return o.toString();
         *     }
         *
         *     @Override
         *     public String toString() {
         *       return "Functions.toStringFunction()";
         *     }
         *  }
         */
        System.out.println(Functions.toStringFunction().apply(new ServerSocket(8888)));
        System.out.println(Functions.toStringFunction().toString());

        /**
         *   混合器: apply将a转为b,再将b转为c
         *   代码实现:
         *   public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) {
         *     return new FunctionComposition<>(g, f);
         *   }
         *
         *   private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
         *     private final Function<B, C> g;
         *     private final Function<A, ? extends B> f;
         *
         *     public FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
         *       this.g = checkNotNull(g);
         *       this.f = checkNotNull(f);
         *     }
         *
         *     @Override
         *     public C apply(@Nullable A a) {
         *       return g.apply(f.apply(a));
         *     }
         *
         *     @Override
         *     public boolean equals(@Nullable Object obj) {
         *       if (obj instanceof FunctionComposition) {
         *         FunctionComposition<?, ?, ?> that = (FunctionComposition<?, ?, ?>) obj;
         *         return f.equals(that.f) && g.equals(that.g);
         *       }
         *       return false;
         *     }
         *
         *     @Override
         *     public int hashCode() {
         *       return f.hashCode() ^ g.hashCode();
         *     }
         *
         *     @Override
         *     public String toString() {
         *       // TODO(cpovirk): maybe make this look like the method call does ("Functions.compose(...)")
         *       return g + "(" + f + ")";
         *     }
         *
         *     private static final long serialVersionUID = 0;
         *   }
         */
        Function<String, Double> compose = Functions.compose(new Function<Integer, Double>() {
            @Nullable
            @Override
            public Double apply(@Nullable Integer input) {
                return input * 1.0D;
            }
        }, new Function<String, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                return input.length();
            }
        });
        System.out.println(compose.apply("Hello"));

        /**
         * 断言,判断变量是否满足条件,返回一个Boolean变量
         * 代码实现:
         * public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
         *     //传入一个Guava的Predicate进行断言
         * 	   return new PredicateFunction<T>(predicate);
         * }
         *
         * private static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
         *     private final Predicate<T> predicate;
         *
         *     private PredicateFunction(Predicate<T> predicate) {
         *     	this.predicate = checkNotNull(predicate);
         *     }
         *
         *     @Override
         *     public Boolean apply(@Nullable T t) {
         *     	return predicate.apply(t);
         *     }
         *
         *     @Override
         *     public boolean equals(@Nullable Object obj) {
         *         if (obj instanceof PredicateFunction) {
         *             PredicateFunction<?> that = (PredicateFunction<?>) obj;
         *             return predicate.equals(that.predicate);
         *         }
         *         return false;
         *     }
         *
         *     @Override
         *     public int hashCode() {
         *     	return predicate.hashCode();
         *     }
         *
         *     @Override
         *     public String toString() {
         *     	return "Functions.forPredicate(" + predicate + ")";
         *     }
         *
         *     private static final long serialVersionUID = 0;
         * }
         */
        Function<String, Boolean> forPredicate = Functions.forPredicate(new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.length() == 1;
            }
        });
        System.out.println(forPredicate.apply("AA"));  //false
        System.out.println(forPredicate.apply("A"));   //true


    }
}
