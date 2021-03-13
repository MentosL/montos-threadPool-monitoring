package xyz.lushun.threadPool.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Montos
 * @create 2021/3/13 4:31 下午
 *   自定义线程池工具类
 */
public class CustomThreadPool extends ThreadPoolExecutor {

    private final Logger logger = LoggerFactory.getLogger(getClass());



    /**
     * ActiveCount
     * */
    int activeCount = 0;

    /**
     * 当前所有线程消耗的时间
     * */
    private final AtomicLong totalCostTime = new AtomicLong();

    /**
     * 当前执行的线程总数
     * */
    private final AtomicLong totalTasks = new AtomicLong();

    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 最短 执行时间
     * */
    private long minCostTime;

    /**
     * 最长执行时间
     * */
    private long maxCostTime;


    /**
     * 保存任务开始执行的时间
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();


    public CustomThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                             TimeUnit unit, BlockingQueue<Runnable> workQueue, String poolName) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), poolName);
    }

    public CustomThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                             TimeUnit unit, BlockingQueue<Runnable> workQueue,
                             ThreadFactory threadFactory, String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.poolName = poolName;
    }

    public static ExecutorService newFixedThreadPool(int nThreads, String poolName) {
        return new CustomThreadPool(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), poolName);
    }

    public static ExecutorService newCachedThreadPool(String poolName) {
        return new CustomThreadPool(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), poolName);
    }

    public static ExecutorService newSingleThreadExecutor(String poolName) {
        return new CustomThreadPool(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), poolName);
    }

    /**
     * 线程池延迟关闭时（等待线程池里的任务都执行完毕），统计线程池情况
     */
    @Override
    public void shutdown() {
        // 统计已执行任务、正在执行任务、未执行任务数量
        if (logger.isDebugEnabled()){
            logger.debug("{} Going to shutdown. Executed tasks: {}, Running tasks: {}, Pending tasks: {}",this.poolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        }
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        // 统计已执行任务、正在执行任务、未执行任务数量
        if (logger.isDebugEnabled()){
            logger.debug("{} Going to immediately shutdown. Executed tasks: {}, Running tasks: {}, Pending tasks: {}",this.poolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        }
        return super.shutdownNow();
    }

    /**
     * 任务执行之前，记录任务开始时间
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 任务执行之后，计算任务结束时间
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long costTime = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        maxCostTime = Math.max(maxCostTime, costTime);
        if (totalTasks.get() == 0) {
            minCostTime = costTime;
        }
        minCostTime = Math.min(minCostTime, costTime);
        totalCostTime.addAndGet(costTime);
        totalTasks.incrementAndGet();
        activeCount = this.getActiveCount();

        if (logger.isDebugEnabled()){
            logger.debug("{}-pool-monitor: " +
                            "Duration: {} ms, PoolSize: {}, CorePoolSize: {}, ActiveCount: {}, " +
                            "Completed: {}, Task: {}, Queue: {}, LargestPoolSize: {}, " +
                            "MaximumPoolSize: {},  KeepAliveTime: {}, isShutdown: {}, isTerminated: {}",
                    this.poolName,
                    costTime, this.getPoolSize(), this.getCorePoolSize(), super.getActiveCount(),
                    this.getCompletedTaskCount(), this.getTaskCount(), this.getQueue().size(), this.getLargestPoolSize(),
                    this.getMaximumPoolSize(), this.getKeepAliveTime(TimeUnit.MILLISECONDS), this.isShutdown(), this.isTerminated());
        }
    }

    public int getActiveCount() {
        return activeCount;
    }

    /**
     * 线程平均耗时
     *
     * @return
     * */
    public float getAverageCostTime() {
        return totalCostTime.get() / totalTasks.get();
    }

    /**
     * 线程最大耗时
     * */
    public long getMaxCostTime() {
        return maxCostTime;
    }

    /**
     * 线程最小耗时
     * */
    public long getMinCostTime() {
        return minCostTime;
    }

}
