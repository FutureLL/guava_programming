package com.lilei.guava.functional;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

public class SupplierExample {

    public static void main(String[] args) throws InterruptedException {
        //每次get都会初始化
        Supplier<String> supplier = new Supplier<>() {
            @Override
            public String get() {
                System.out.println("init supplier wrapped object");
                return "Google Guava";
            }
        };
        System.out.println(supplier.get());
        System.out.println(supplier.get());

        System.out.println("------------------");

        //类似于Functions.compose()
        Supplier<Integer> supplierCompose = Suppliers.compose(new Function<String, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                //断言传进来的参数是不是null,如果是null会抛出异常NullPointerException
                Preconditions.checkNotNull(input);
                return input.length();
            }
        }, new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        });
        System.out.println(supplierCompose.get());

        System.out.println("------------------");

        //Lazy初始化,Supplier wrapped的对象只在第一次get时候会被初始化
        Supplier<String> memoize = Suppliers.memoize(new Supplier<>() {
            @Override
            public String get() {
                System.out.println("init supplier wrapped object");
                return  "Google Guava";
            }
        });
        System.out.println("main thread block");
        Thread.sleep(2000);
        System.out.println(memoize.get());
        System.out.println(memoize.get());

        System.out.println("------------------");

        //Supplier wrapped对象只初始化一次
        Supplier<String> memoizeOne = Suppliers.memoize(new Supplier<>() {
            @Override
            public String get() {
                System.out.println("init supplier wrapped object");
                return "Google Guava";
            }
        });
        System.out.println(memoizeOne.get());
        System.out.println(memoizeOne.get());

        System.out.println("------------------");

        //创建过期设置的Supplier对象，时间过期，get对象会重新初始化对象
        Supplier<String> memoizeTime = Suppliers.memoizeWithExpiration(new Supplier<>() {
            @Override
            public String get() {
                System.out.println("init supplier wrapped object");
                return "Google Guava";
            }
        }, 5, TimeUnit.SECONDS);
        System.out.println(memoizeTime.get());
        Thread.sleep(6000);
        System.out.println(memoizeTime.get());


    }
}
