package com.sd.lib.looper;

public interface FTimeouter
{
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
    void setTimeoutRunnable(Runnable timeoutRunnable);

    /**
     * 执行超时需要执行的Runnable
     */
    void runTimeoutRunnable();

    /**
     * 设置超时时间(毫秒)
     *
     * @param timeout
     */
    void setTimeout(long timeout);

    /**
     * 开始超时计时
     */
    void startTimeout();
}