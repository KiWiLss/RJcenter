package com.angcyo.sample.rx;

import rx.Observable;
import rx.functions.Func1;

/**
 * Rx 错误操作符
 * <p>
 * Created by robi on 2016-07-14 15:11.
 */
@SuppressWarnings("unchecked")
public class RxErrorOperator {
    public static void catchDemo() {
        //从onError通知中恢复发射数据

//        RxJava将Catch实现为三个不同的操作符：
//
//        onErrorReturn
//
//        让Observable遇到错误时发射一个特殊的项并且正常终止。
//
//        onErrorResumeNext
//
//        让Observable在遇到错误时开始发射第二个Observable的数据序列。
//
//        onExceptionResumeNext
//
//        让Observable在遇到错误时继续发射后面的数据项。


//        03:54:50 273 main:1->onNext 2
//        03:54:50 278 main:1->onNext 100
//        03:54:50 278 main:1->onCompleted
        Observable.range(1, 6)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return 2 / (2 - integer);
                    }
                })
                //当出现错误的时候, 调用.后面的都不执行
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        return 100;
                    }
                }).subscribe(new RxCreateOperator.Sub());

//        03:53:06 313 main:1->onNext 2
//        03:53:06 319 main:1->onNext 1000
//        03:53:06 319 main:1->onCompleted
//        Observable.range(1, 6)
//                .map(new Func1<Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer) {
//                        return 2 / (2 - integer);
//                    }
//                }
//                 //暂时没发现有何不同.
//                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
//                    @Override
//                    public Observable<? extends Integer> call(Throwable throwable) {
//                        return Observable.just(1000);
//                    }
//                }).subscribe(new RxCreateOperator.Sub());

//        03:57:36 804 main:1->onNext 2
//        03:57:36 809 main:1->onNext 100
//        03:57:36 809 main:1->onCompleted
//        03:57:36 811 main:1->call 1
//        03:57:36 812 main:1->onNext 2
//        03:57:36 812 main:1->call 2
//        03:57:36 812 main:1->onNext 100
//        03:57:36 812 main:1->onCompleted
        Observable.range(1, 6)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        RxDemo.log(RxDemo.getMethodName() + " " + integer);
                        return 2 / (2 - integer);
                    }
                })
                //暂时没发现不同
                .onExceptionResumeNext(Observable.just(100)
                ).subscribe(new RxCreateOperator.Sub());
    }
}
