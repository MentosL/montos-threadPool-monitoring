package xyz.lushun.threadPool.mbean;

import xyz.lushun.threadPool.custom.CustomThreadPool;

/**
 * @author Montos
 * @create 2021/3/13 5:30 下午
 * 默认Mbean
 */
public class ThreadPoolDesc implements ThreadPoolDescMBean {

    private  CustomThreadPool customThreadPool;

    public ThreadPoolDesc(CustomThreadPool customThreadPool) {
        this.customThreadPool =  customThreadPool;
    }


    @Override
    public int getActiveCount() {
        return customThreadPool.getActiveCount();
    }


    @Override
    public long getCompletedTaskCount() {
        return customThreadPool.getCompletedTaskCount();
    }


    @Override
    public int getCorePoolSize() {
        return customThreadPool.getCorePoolSize();
    }

    @Override
    public int getLargestPoolSize() {
        return customThreadPool.getLargestPoolSize();
    }

    @Override
    public int getMaximumPoolSize() {
        return customThreadPool.getMaximumPoolSize();
    }

    @Override
    public int getPoolSize() {
        return customThreadPool.getPoolSize();
    }

    @Override
    public long getTaskCount() {
        return customThreadPool.getTaskCount();
    }


    @Override
    public long getMaxCostTime() {
        return customThreadPool.getMaxCostTime();
    }


    @Override
    public long getMinCostTime() {
        return customThreadPool.getMinCostTime();
    }


    @Override
    public float getAverageCostTime() {
        return customThreadPool.getAverageCostTime();
    }
}
