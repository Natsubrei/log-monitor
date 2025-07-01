# 实时日志可视化监控系统
### 项目功能

本项目实现了一个实时日志可视化监控微服务：

* 针对不同设备，实时显示其WARN/ERROR占比、最近一次 ERROR 事件、严重告警状态/次数等信息。

* 针对不同设备，绘制过去一段时间内WARN/ERROR 数量变化趋势的折线图。

### 项目结构

**ActiveMQConsumer**程序接收到ActiveMQ中的分析结果消息和严重警告消息后，使用**LogWebSocketHandler**中的方法将这个些消息广播给所有WebSocket客户端。**WebSocketConfig**则是配置了WebSocket服务。

**index.html**使用WebSocket实时接收分析数据，并使用原生JS和Chart.js实现了前端展示。

### 数据来源

[log-collection: 日志采集分析系统](https://github.com/Natsubrei/log-collection) 
