package com.lilei.guava.functional;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PredicateExample {
    public static void main(String[] args) {
        Predicate<Integer> predicate = new Predicate<Integer>() {
            //返回对input调用这个断言之后的结果。
            @Override
            public boolean apply(@Nullable Integer input) {
                return input > 10;
            }

            //标示另一个对象是否等于这个断言
            @Override
            public boolean equals(@Nullable Object object) {
                return object == null;
            }
        };
        Predicate<Integer> predicateCopy = new Predicate<Integer>() {
            //返回对input调用这个断言之后的结果。
            @Override
            public boolean apply(@Nullable Integer input) {
                return input > 10;
            }

            //标示另一个对象是否等于这个断言
            @Override
            public boolean equals(@Nullable Object object) {
                return object == null;
            }
        };

        System.out.println(predicate.apply(12));        //true
        System.out.println(predicate.equals(null));           //true

        System.out.println("------------------");

        //不管input什么都返回true
        System.out.println(Predicates.alwaysTrue().apply(false));   //true
        System.out.println(Predicates.alwaysTrue().apply(""));      //true
        System.out.println(Predicates.alwaysTrue().apply(null));    //true
        //不管input什么都返回false
        System.out.println(Predicates.alwaysFalse().apply(true));   //false
        //返回判断对象是否为null的Predicate
        System.out.println(Predicates.isNull().apply(null));        //true
        System.out.println(Predicates.isNull().apply(""));          //false
        //返回判断对象是否不为null的Predicate
        System.out.println(Predicates.notNull().apply("Hello"));    //true
        System.out.println(Predicates.notNull().apply(null));       //false
        //返回与传入predicate结果相反的predicate
        System.out.println(Predicates.not(predicate).apply(9));     //true
        System.out.println(Predicates.not(predicate).apply(15));    //false
        //将两个predicate合并为一个
        System.out.println(Predicates.and(predicate,predicateCopy));
        //多个predicate结果并集
        System.out.println(Predicates.or(predicate,predicateCopy));
        //返回一个判断是否相等的Predicate
        System.out.println(Predicates.equalTo(null));
        System.out.println(Predicates.equalTo(predicate));
        //Object.isInstance方法,以Predicate封装返回
        System.out.println(Predicates.instanceOf(predicate.getClass()));
    }
}
