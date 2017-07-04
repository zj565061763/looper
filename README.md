# AndroidLooper
实现循环触发任务的库

应用场景：<br>
* 轮播图每隔一秒切换一页
* 定时间隔刷新，定时间隔请求接口
* 倒计时，可以实现倒计时每隔一秒更新一下剩余时长，但是会存在误差，如果需要准确的倒计时，建议用系统的android.os.CountDownTimer
* 直播间聊天列表有大量数据的时候，每隔几百毫秒取数据展示
* 直播间循环遍历礼物view的播放状态，从礼物队列中取数据展示
* ...

# SDSimpleLooper

该Looper是库中已经实现ISDLooper的实现类基于Handler实现，较Timer性能消耗更少
![](http://thumbsnap.com/i/ZgUfB62K.png?0703)

