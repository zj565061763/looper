# AndroidLooper
实现循环触发任务的库

## Gradle
`compile 'com.fanwe.android:looper:1.0.1'`

## SDSimpleLooper
应用场景：<br>
* 轮播图每隔一秒切换一页
* 定时间隔刷新，定时间隔请求接口
* 倒计时，可以实现倒计时每隔一秒更新一下剩余时长，但是会存在误差，如果需要准确的倒计时，建议用系统的android.os.CountDownTimer
* 直播间聊天列表有大量数据的时候，每隔几百毫秒取数据展示
* 直播间循环遍历礼物view的播放状态，从礼物队列中取数据展示
* ...

该类是库中已经实现ISDLooper接口的实现类，内部基于Handler实现，较Timer性能消耗更少，构造方法支持传入Looper对象来指定要循环的线程，默认在主线程循环<br>
```java
private void testSDSimpleLooper()
{
    //延迟500毫秒后，每秒触发一次设置的Runnable对象
    ISDLooper looper = new SDSimpleLooper();
    looper.start(500, 1000, new Runnable()
    {
        @Override
        public void run()
        {
            Toast.makeText(getApplication(), "toast", 0).show();
        }
    });
    // looper.stop(); //停止循环，在需要停止的地方停止，比如ui销毁
}
```

## SDWaitRunner
```java
/**
 * 等待某个条件成立后需要执行的Runnable
 */
private void testSDWaitRunner()
{
    SDWaitRunner waitRunner = new SDWaitRunner()
            .run(new Runnable() //设置需要等待执行的Runnable
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "run", 0).show();
                }
            })
            .condition(new SDWaitRunner.Condition() //设置Runnable执行条件
            {
                @Override
                public boolean canRun()
                {
                    // 返回true则Runnable立即执行，返回false则继续等待，如果超时会执行超时Runnable
                    return false;
                }
            })
            .setTimeout(5 * 1000)//设置等待超时时间
            .setTimeoutRunnable(new Runnable() //设置超时需要执行的Runnable
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "timeout", 0).show();
                }
            })
            .startWait(100); //开始等待，每100毫秒检测一次Runnable执行条件是否成立

    // waitRunner.stopWait(); //停止等待，在需要停止的地方停止，比如ui销毁
}
```


