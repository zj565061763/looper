
## Gradle
[![](https://jitpack.io/v/zj565061763/looper.svg)](https://jitpack.io/#zj565061763/looper)

## FSimpleLooper
应用场景：<br>
* 轮播图每隔一秒切换一页
* 定时间隔刷新，定时间隔请求接口
* 倒计时，可以实现倒计时每隔一秒更新一下剩余时长，但是会存在误差，如果需要准确的倒计时，建议用系统的android.os.CountDownTimer
* 直播间聊天列表有大量数据的时候，每隔几百毫秒取数据展示
* 直播间循环遍历礼物view的播放状态，从礼物队列中取数据展示
* ...

该类是库中已经实现FLooper接口的实现类，内部基于Handler实现，较Timer性能消耗更少，构造方法支持传入Looper对象来指定要循环的线程，默认在主线程循环<br>
```java
private void testSimpleLooper()
{
    //延迟500毫秒后，每隔1000毫秒触发一次设置的Runnable对象
    FLooper looper = new FSimpleLooper();
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

## FConditionRunner
```java
private void testConditionRunner()
{
    mConditionRunner.run(new Runnable() //设置条件成立后要执行的Runnable对象
    {
        @Override
        public void run()
        {
            Toast.makeText(getApplication(), "run", Toast.LENGTH_SHORT).show();
        }
    }).condition(new FConditionRunner.Condition() //设置Runnable的执行条件
    {
        @Override
        public boolean canRun()
        {
            //返回true则Runnable立即执行，返回false则继续检测，如果超时会执行超时Runnable
            return false;
        }
    }).setTimeout(5 * 1000)//设置检测超时时间
            .setTimeoutRunnable(new Runnable() //设置超时需要执行的Runnable
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "timeout", Toast.LENGTH_SHORT).show();
                }
            })
            .startCheck(); //开始检测条件是否成立，默认每300毫秒检测一次
}
```

