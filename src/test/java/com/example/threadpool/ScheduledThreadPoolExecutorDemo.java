package com.example.threadpool;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WJ
 * @date 2018/8/16
 */
public class ScheduledThreadPoolExecutorDemo {

    private static SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

    /**
     * 每隔一段时间打印系统时间，互不影响的
     * 第一次05执行，第二次10执行，第三次15执行
     * scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
     * command:执行线程
     * initialDelay：初始化延时,就是什么时候开始执行这个循环的操作
     * period：两次开始执行的最小间隔时间
     * unit:计时单位
     */
    @Test
    public void testScheduleFixedRate() throws InterruptedException {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(formate.format(new Date()));
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        //main方法执行完成之后，由于@Test里面，所以其他线程都会结束，为了测试结果，让主线程睡一会
        //如果在main函数里面，则主线程退出之后，其他线程不会退出
        Thread.sleep(100000);
    }


    /**
     * 开始执行后就触发异常,next周期将不会运行
     *
     * @throws InterruptedException
     */
    @Test
    public void testScheduleFixRateExcetion() throws InterruptedException {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("RuntimeException no catch,next time can't run");
                throw new RuntimeException();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);
    }


    /**
     * 创建并执行在给定延迟后启用的一次性操作
     */
    @Test
    public void testSchedule() throws InterruptedException {
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("The thread can only run once!");
            }
        }, 5000, TimeUnit.MILLISECONDS);
        Thread.sleep(10000);
    }


    /**
     * 创建并执行一个在给定初始延迟后首次启用的定期操作
     * 随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
     * @throws InterruptedException
     */
    @Test
    public void testScheduleWithFixedDelay() throws InterruptedException {
        exec.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("scheduleWithFixedDelay:begin," + formate.format(new Date()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("scheduleWithFixedDelay:end," + formate.format(new Date()));
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);
        Thread.sleep(10000);
    }


}
