package xyz.lushun.test;

import xyz.lushun.threadPool.adaptor.HtmlMBeanAdaptor;
import xyz.lushun.threadPool.custom.CustomThreadPool;
import xyz.lushun.threadPool.mbean.ThreadPoolDesc;
import xyz.lushun.threadPool.util.MBeanServerUtils;

import javax.management.ObjectName;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Montos
 * @create 2021/3/13 6:12 下午
 */
public class TestThreadPool {

    private static Random random = new Random();


    public static void main(String[] args) throws Exception {
        int count = Runtime.getRuntime().availableProcessors();

        CustomThreadPool customThreadPool1 = new CustomThreadPool(count,count*2,5_000, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),"test1");
        CustomThreadPool customThreadPool2 = new CustomThreadPool(count,count*2,5_000, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),"test2");


        ThreadPoolDesc threadPoolMBean1 = new ThreadPoolDesc(customThreadPool1);
        ThreadPoolDesc threadPoolMBean2 = new ThreadPoolDesc(customThreadPool2);


        MBeanServerUtils.registerMBean(threadPoolMBean1, new ObjectName("test-pool-1:type=threadPoolMBean"));
        MBeanServerUtils.registerMBean(threadPoolMBean2, new ObjectName("test-pool-2:type=threadPoolMBean"));

        HtmlMBeanAdaptor.start();

        executeTask(customThreadPool1);
        executeTask(customThreadPool2);
        Thread.sleep(1000 * 60 * 60);

    }


    private static void executeTask(ExecutorService es) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int temp = i;
                es.submit(() -> {
                    //随机睡眠时间
                    try {
                        Thread.sleep(random.nextInt(60) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(temp);
                });
            }
        }).start();
    }
}
