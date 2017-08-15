package com.fanwe.library.looper;

import android.os.CountDownTimer;

/**
 * 倒计时类
 */
public class SDCountDownTimer
{

    private CountDownTimer mTimer;
    private Callback mCallback;

    /**
     * 设置回调对象
     *
     * @param callback
     * @return
     */
    public synchronized SDCountDownTimer setCallback(Callback callback)
    {
        mCallback = callback;
        return this;
    }

    /**
     * 开始倒计时
     *
     * @param millisInFuture    总时长（毫秒）
     * @param countDownInterval 隔多久触发一次（毫秒）
     */
    public synchronized void start(long millisInFuture, long countDownInterval)
    {
        stop();
        if (mTimer == null)
        {
            mTimer = new CountDownTimer(millisInFuture, countDownInterval)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    if (mCallback != null)
                    {
                        mCallback.onTick(millisUntilFinished);
                    }
                }

                @Override
                public void onFinish()
                {
                    if (mCallback != null)
                    {
                        mCallback.onFinish();
                    }
                }
            };
            mTimer.start();
        }
    }

    /**
     * 停止倒计时
     */
    public synchronized void stop()
    {
        if (mTimer != null)
        {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public interface Callback
    {
        void onTick(long leftTime);

        void onFinish();
    }
}
