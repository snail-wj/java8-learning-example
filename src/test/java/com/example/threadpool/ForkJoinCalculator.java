package com.example.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * ForkJoinCalculator实现
 *
 * @author WJ
 * @date 2018/8/24
 *
 * 参考url:
 * http://blog.dyngr.com/blog/2016/09/15/java-forkjoinpool-internals/
 */
public class ForkJoinCalculator {
    private  ForkJoinPool pool;

    private static class SumTask extends RecursiveTask<Long> {

        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            //当需要计算的数字小于6 时，直接计算结果
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
            } else {
                int middle = (from + to) / 2;
                SumTask taskLeft = new SumTask(numbers, from, middle);
                SumTask taskRight = new SumTask(numbers, middle + 1, to);
                // fork()：开启一个新线程（或是重用线程池内的空闲线程），将任务交给该线程处理。把任务推入当前工作线程的工作队列
                taskLeft.fork();
                taskRight.fork();
                // join()：等待该任务的处理线程处理完毕，获得返回值。
                return taskLeft.join() + taskRight.join();
            }
        }
    }

    public ForkJoinCalculator() {
        pool = new ForkJoinPool();
    }

    private  long sumUp(long[] numbers){
        return pool.invoke(new SumTask(numbers, 0, numbers.length - 1));
    }

    public static void main(String[] args) {
        ForkJoinCalculator calculator = new ForkJoinCalculator();
        long[] numbers = LongStream.rangeClosed(1, 1000).toArray();
        System.out.println(calculator.sumUp(numbers));
        System.out.println(calculator.pool.getPoolSize());
    }
}
