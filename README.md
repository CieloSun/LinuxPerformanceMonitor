# LinuxPerformanceMonitor

## 开发工具
- Develop by Spring boot + Echart

## 运行环境

- Ubuntu 16.04
- sysstat
- mysql

## 配置说明

请在apllication.yml中配置您的总带宽（默认20mb/s）和端口号（默认46000）。

## 开发日志

- 将Web前端在应用中部署。

- 修复动态数据显示加载的问题，目前所有的BUG均已修复

- 修复静态数据显示加载之数据问题，显示正常化，动态数据暂时没有解决

- Web开发完成，只需要运行Web下的index页面即可看到效果目前存在的问题是实施加载数据的时候不能清空上一次的数据导致显示有一定的问题，后期进行修复。

- 使用Spring定时任务定时记录历史。

- 使用jpa完成基于时间查询。

- 使用/proc/net/dev文件中内容完成网络使用率功能的开发。

- 使用iostat -dx 命令完成磁盘IO使用率功能开发。

- 使用/proc/meminfo文件中内容完成内存使用率功能的开发。

- 使用/proc/stat文件中内容完成cpu使用率功能的开发。


