package com.example.future;

import lombok.val;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture基本操作
 *
 * @author WJ
 * @date 2018/8/16
 * <p>
 * 参考url:
 * http://kriszhang.com/CompletableFuture/
 *
 * 记忆规范：
 *      1.以Async后缀结尾的方法，均是异步方法，对应无Async则是同步方法。
 *      2.以Async后缀结尾的方法，一定有两个重载方法。其一是采用内部forkjoin线程池执行异步，其二是指定一个Executor去运行。
 *      3.以run开头的方法，其方法入参的lambda表达式一定是无参数，并且无返回值的，其实就是指定Runnable
 *      4.以supply开头的方法，其方法入参的lambda表达式一定是无参数，并且有返回值，其实就是指Supplier
 *      5.以Accept为开头或结尾的方法，其方法入参的lambda表达式一定是有参数，但是无返回值，其实就是指Consumer
 *      6.以Apply为开头或者结尾的方法，其方法入参的lambda表达式一定是有参数，但是有返回值，其实就是指Function
 *      7.带有either后缀的表示谁先完成则消费谁
 *
 *
 *
 */
public class CompletableFutureDemo {

    @Test
    public void testCompletableFuture() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        System.out.println("师傅准备做蛋糕");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("师傅做蛋糕做好了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //这个的返回结果不固定
                    return "cake";
                })
                //做好了告诉我一声(即future拥有回调功能，让师傅主动告诉我蛋糕做好了)
                .thenAccept(cake -> System.out.println("我吃蛋糕:" + cake));
        System.out.println("我先去喝杯牛奶");
        //如果没有这句话，主线线程结束了之后，就结束，我们需要主线程结束之前，voidCompletableFuture线程先结束
        voidCompletableFuture.join();
    }

    /**
     * CompletableFuture执行完成之后(done)再执行我指定所的方法
     */
    @Test
    public void testContinueWay1() {
        val makeCake = CompletableFuture.supplyAsync(() -> {
            System.out.println("糕点师做糕点");
            return "cake";
        });
        makeCake.thenApplyAsync(cake -> {
            System.out.println("蛋糕做好了，我来做牛奶吧");
            return "milk";
        })
        .thenAcceptAsync(milk ->{
            System.out.println("牛奶做好了");
            System.out.println("我开始吃早饭了");
        })
        .thenRun(()->{
            System.out.println("吃完早饭去上班");
        });
    }

    /**
     * 如果我们只想做CompletableFuture结果处理，而不接续其他动作，
     * whenCompleteAsync: 处理完成或异常，无返回值
     * handleAsync：处理完成或异常，有返回值
     *
     */
    @Test
    public void testHandlerResultWay2(){
        val makeCake = CompletableFuture.supplyAsync(() -> {
            System.out.println("糕点师做蛋糕");
            val i = 1/ 0;
            return "cake";
        });

        //无返回值处理
        makeCake.whenCompleteAsync((cake, exception) ->{
           if(exception != null){
               System.out.println("蛋糕做糊了。。。。。");
           } else {
               System.out.println("蛋糕做好了");
           }
        });

        //有返回值处理
        makeCake.handleAsync((cake, exception) ->{
            if(exception != null){
                System.out.println("蛋糕做糊了。。。，出去买个现成的蛋糕.");
                return "现成的蛋糕";
            }else {
                System.out.println("蛋糕做好了");
            }
            return cake;
        });

        //只处理异常，只有阻塞调用，产生异常则会返回“出去买一个现成的蛋糕吧”
        makeCake.exceptionally(exception ->{
            System.out.println("蛋糕做糊了。。。，出去买个蛋糕吧");
            return "现成的蛋糕";
        });
    }
}
