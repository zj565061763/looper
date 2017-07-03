package com.fanwe.library.looper;

public interface ISDTimeouter
{
    /**
     * 默认超时
     */
    long DEFAULT_TIMEOUT = 10 * 1000;

    /**
     * 获得设置的超时时间
     */
    long getTimeout();

    /**
     * 是否超时
     */
    boolean isTimeout();

    /**
     * 设置超时需要执行的Runnable
     */
    ISDTimeouter setTimeoutRunnable(Runnable timeoutRunnable);

    /**
     * 执行超时需要执行的Runnable
     */
    ISDTimeouter runTimeoutRunnable();

    /**
     * 设置超时时间
     *
     * @param timeout 大于0超时才有效
     * @return
     */
    ISDTimeouter setTimeout(long timeout);

    /**
     * 开始超时计时
     */
    ISDTimeouter startTimeout();

    /**
     * 停止超时计时
     */
    ISDTimeouter stopTimeout();
}