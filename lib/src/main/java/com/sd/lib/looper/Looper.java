package com.sd.lib.looper;

/**
 * 循环触发接口
 */
public interface Looper
{
    /**
     * 设置状态变化回调
     *
     * @param callback
     */
    void setOnStateChangeCallback(OnStateChangeCallback callback);

    /**
     * 是否已经开始循环
     *
     * @return
     */
    boolean isStarted();

    /**
     * 返回循环间隔
     *
     * @return
     */
    long getInterval();

    /**
     * 设置循环触发间隔，大于0才有效
     *
     * @param interval 循环触发间隔(毫秒)
     */
    void setInterval(long interval);

    /**
     * 设置要循环触发的runnable
     *
     * @param runnable
     */
    void setLoopRunnable(Runnable runnable);

    /**
     * 开始循环
     *
     * @return
     */
    boolean start();

    /**
     * 延迟多少毫秒后开始循环
     *
     * @param delayMillis
     * @return
     */
    boolean startDelayed(long delayMillis);

    /**
     * 停止循环
     */
    void stop();

    interface OnStateChangeCallback
    {
        /**
         * 循环是否开始状态变化回调
         *
         * @param started
         */
        void onStateChanged(boolean started);
    }
}