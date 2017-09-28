/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.looper;

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
