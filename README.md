# Whoever

##Introduction：
* 一款以兴趣为驱动的陌生人社交app
* 后台服务器使用 leancloud，通过 rxJava 对其 sdk 进行封装
* 单独抽取一层 controller 层，避免 activity 过于臃肿对聊天模块以及相册模块单独封包，便于以后提取复用
* dagger2 依赖注入EventBus
* 即时通讯模块使用开放平台leancloud，如果想更换，需要去 https://leancloud.cn 注册一个自己的帐号 然后创建应用，并把`PlayTogether-master\app\src\main\java\com\chenantao\playtogether\MyApplication.java`中`initLeanCloud()`方法中的KEY和密码换成在leancloud平台注册的key和密码就可以了

##TODO：
* 窗口左滑功能
* 问问模块存在bug
* 刷新过程存在bug

##Test:
* 自己注册账号测试即可，账号信息存储于第三方服务器
