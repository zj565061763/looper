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
 * 间隔的检测Condition对象的返回值<br>
 * 返回true的时候，执行设置的Runnable对象，结束检测<br>
 * 返回false的时候，检查是否超时(如果超时，执行超时Runnable后结束检测；如果未超时，等待下一次检测)
 */
public class FConditionRunner implements FTimeouter
{
    private final FLooper mLooper;
    private final FTimeouter mTimeouter = new FSimpleTimeouter();

    private Condition mCondition;
    private Runnable mRunnable;

    public FConditionRunner()
    {
        this(Looper.getMainLooper());
    }

    public FConditionRunner(Looper looper)
    {
        mLooper = new FSimpleLooper(looper);
        setInterval(300); //默认检测间隔，300毫秒
        setTimeout(10 * 1000); //默认超时时间，10秒
    }

    /**
     * 设置检测条件是否成立的时间间隔(毫秒)
     *
     * @param interval
     * @return
     */
    public FConditionRunner setInterval(long interval)
    {
        mLooper.setInterval(interval);
        return this;
    }

    /**
     * 设置条件成立后要执行的Runnable对象
     *
     * @param runnable
     * @return
     */
    public FConditionRunner run(Runnable runnable)
    {
        mRunnable = runnable;
        return this;
    }

    /**
     * 开始检测条件是否成立，默认每300毫秒检测一次
     *
     * @param condition
     */
    public void startCheck(Condition condition)
    {
        mCondition = condition;
        mLooper.start(mCheckRunnable);
        mTimeouter.startTimeout();
    }

    private final Runnable mCheckRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mCondition == null)
            {
                stopCheck();
                return;
            }
            if (isTimeout())
            {
                runTimeoutRunnable();
                stopCheck();
                return;
            }

            if (mCondition.run())
            {
                if (mRunnable != null)
                {
                    mRunnable.run();
                }
                stopCheck();
            } else
            {
                // wait...
            }
        }
    };

    /**
     * 停止检测
     */
    public void stopCheck()
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
    public FConditionRunner setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
        return this;
    }

    @Override
    public FConditionRunner runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
        return this;
    }

    @Override
    public FConditionRunner setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
        return this;
    }

    @Override
    public FConditionRunner startTimeout()
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
        boolean run();
    }
}
