package com.fanwe.library.looper;

/**
 * 循环触发接口
 */
public interface ISDLooper
{

    /**
     * 默认循环触发间隔
     */
    public static final long DEFAULT_PERIOD = 300;

    /**
     * 是否正在循环中
     *
     * @return
     */
    boolean isRunning();

    /**
     * 设置循环触发间隔
     *
     * @param period
     */
    void setPeriod(long period);

    /**
     * 开始循环
     *
     * @param runnable 循环触发对象
     * @return
     */
    ISDLooper start(Runnable runnable);

    /**
     * 开始循环
     *
     * @param period   循环触发间隔
     * @param runnable 循环触发对象
     * @return
     */
    ISDLooper start(long period, Runnable runnable);

    /**
     * 开始循环
     *
     * @param delay    延迟多久开始循环
     * @param period   循环触发间隔
     * @param runnable 循环触发对象
     * @return
     */
    ISDLooper start(long delay, long period, Runnable runnable);

    /**
     * 停止循环
     *
     * @return
     */
    ISDLooper stop();

}