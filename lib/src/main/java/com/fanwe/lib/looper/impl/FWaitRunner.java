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
package com.fanwe.lib.looper.impl;

import android.os.Looper;

import com.fanwe.lib.looper.FLooper;
import com.fanwe.lib.looper.FTimeouter;

/**
 * 等待执行类，等待Condition对象返回true的时候，执行设置的Runnable对象。可以设置等待超时，默认等待10秒，超时后执行超时Runnable
 */
public class FWaitRunner implements FTimeouter
{
    private final FLooper mLooper;
    private final FTimeouter mTimeouter = new FSimpleTimeouter();

    private Condition mCondition;
    private Runnable mRunnable;

    public FWaitRunner()
    {
        this(Looper.getMainLooper());
    }

    public FWaitRunner(Looper looper)
    {
        mLooper = new FSimpleLooper(looper);
    }

    /**
     * 设置条件成立后要执行的Runnable对象
     *
     * @param runnable
     * @return
     */
    public FWaitRunner run(Runnable runnable)
    {
        mRunnable = runnable;
        return this;
    }

    /**
     * 设置执行条件
     *
     * @param condition
     * @return
     */
    public FWaitRunner condition(Condition condition)
    {
        mCondition = condition;
        return this;
    }

    /**
     * 开始等待执行<br>
     * 默认每300毫秒检测一次条件是否成立
     */
    public void startWait()
    {
        startWait(300);
    }

    /**
     * 开始等待执行
     *
     * @param period 检测条件是否成立的时间间隔(毫秒)
     */
    public void startWait(long period)
    {
        mLooper.start(period, mInternalRunnable);
    }

    private final Runnable mInternalRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mCondition != null)
            {
                if (mCondition.canRun())
                {
                    runRunnable();
                    stopWait();
                } else
                {
                    // wait...
                }
            } else
            {
                stopWait();
            }
        }
    };

    private void runRunnable()
    {
        if (mRunnable != null)
        {
            mRunnable.run();
        }
    }

    /**
     * 停止等待
     */
    public void stopWait()
    {
        mLooper.stop();
    }

    @Override
    public long getTimeout()
    {
        return mTimeouter.getTimeout();
    }

    @Override
    public boolean isTimeout()
    {
        return mTimeouter.isTimeout();
    }

    @Override
    public FWaitRunner setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
        return this;
    }

    @Override
    public FWaitRunner runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
        return this;
    }

    @Override
    public FWaitRunner setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
        return this;
    }

    @Override
    public FWaitRunner startTimeout()
    {
        mTimeouter.startTimeout();
        return this;
    }

    public interface Condition
    {
        /**
         * 是否可以执行Runnable
         *
         * @return true-可以执行
         */
        boolean canRun();
    }
}
