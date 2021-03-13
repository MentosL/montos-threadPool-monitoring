package xyz.lushun.threadPool.mbean;

/**
 * @author Montos
 * @create 2021/3/13 5:28 下午
 *  统一接口
 */
public interface ThreadPoolDescMBean {


    /**
     *
     *
     * @return 线程池中正在执行任务的线程数量
     */
    int getActiveCount();

    /**
     *
     *
     * @return 线程池已完成的任务数量
     */
    long getCompletedTaskCount();

    /**
     *
     *
     * @return 线程池的核心线程数量
     */
    int getCorePoolSize();

    /**
     *
     *
     * @return 线程池曾经创建过的最大线程数量
     */
    int getLargestPoolSize();

    /**
     *
     *
     * @return  线程池的最大线程数量
     */
    int getMaximumPoolSize();

    /**
     *
     *
     * @return  线程池当前的线程数量
     */
    int getPoolSize();

    /**
     *
     *
     * @return 线程池需要执行的任务数量
     */
    long getTaskCount();

    /**
     *
     *
     * @return 线程最大耗时
     * */
    long getMaxCostTime();

    /**
     *
     *
     * @return 线程最小耗时
     * */
    long getMinCostTime();

    /**
     *
     *
     * @return 线程平均耗时
     * */
    float getAverageCostTime();
}
