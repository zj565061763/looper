package com.fanwe.lib.looper.impl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Looper;

/**
 * Activity销毁的时候自动停止的Looper
 */
public class FActivityLooper extends FSimpleLooper implements Application.ActivityLifecycleCallbacks
{
    private final Activity mActivity;

    public FActivityLooper(Activity activity)
    {
        this(null, activity);
    }

    public FActivityLooper(Looper looper, Activity activity)
    {
        super(looper);
        if (activity == null)
            throw new NullPointerException("activity is null");
        mActivity = activity;
        activity.getApplication().registerActivityLifecycleCallbacks(this);
    }

    public final Activity getActivity()
    {
        return mActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {

    }

    @Override
    public void onActivityPaused(Activity activity)
    {

    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        if (activity == mActivity)
        {
            stop();
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }
}
