TODO
* 底部切换按钮
* 计划，我的 Fragment
* 配置greendao，RecycleView，CardView
* 更换铃声播放方式，并进行预加载
* 在合适的时候创建所有未过时的闹钟
* 单例模式封装AudioPlayer
* AudioPlayer的使用
* 重构闹钟系统
* 完善唤起界面

* 铃声列表，铃声存储位置
* 文件和文件夹的复制
* Fragment切换或者关闭时停止铃声



FIX
* java.lang.RuntimeException: Unable to instantiate service com.kingja.trainingday.service.AlarmService: java.lang.InstantiationException: can't instantiate class com.kingja.trainingday.service.AlarmService; no empty constructor


GET
* Android中assets目录和raw目录的区别和使用情况
因为assets和res目录下的文件是可读不可写得，所有要想修改文件就要复制到手机存储里
assets不能获取绝对地址，只能通过URI返回，复制到内存或者SD卡可以获取绝对地址，方便管理

* assets的存取
* uri的使用