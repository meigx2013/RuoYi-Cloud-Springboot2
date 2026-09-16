 
# RuoYi-Cloud-Flowable : —若依微服务-Flowable工作流-生成代码版本
## 不带工作流中心
## 若依微服务的一个flowable扩展版本，在生成代码的时候，可以选择流程模式，然后生成带流程功能的模块。

## 指南：
https://gitee.com/bdn007/RuoYi-Cloud/tree/Gen_master

https://gitee.com/bdn007/RuoYi-Cloud/tree/master （带工作流中心的版本, 以后再部署试试）

## 介绍
若依微服务的一个flowable扩展版本，在生成代码的时候，可以选择流程模式，然后生成带流程功能的模块。

### 源码链接：
- Gitee：[https://gitee.com/bdn007/RuoYi-Cloud]

### 技术栈
本项目使用以下技术栈构建：
- **前端**: Vue 3, Element Plus, Vite , js
- **后端**: 若依微服务

## 系统模块

~~~
com.ruoyi     
├── ruoyi-ui              // 前端框架 [80]
├── ruoyi-gateway         // 网关模块 [8080]
├── ruoyi-auth            // 认证中心 [9200]
├── ruoyi-api             // 接口模块
│       └── ruoyi-api-system                          // 系统接口
├── ruoyi-common          // 通用模块
│       └── ruoyi-common-core                         // 核心模块
│       └── ruoyi-common-datascope                    // 权限范围
│       └── ruoyi-common-datasource                   // 多数据源
│       └── ruoyi-common-log                          // 日志记录
│       └── ruoyi-common-redis                        // 缓存服务
│       └── ruoyi-common-seata                        // 分布式事务
│       └── ruoyi-common-security                     // 安全模块
│       └── ruoyi-common-sensitive                    // 数据脱敏
│       └── ruoyi-common-swagger                      // 系统接口
├── ruoyi-modules         // 业务模块
│       └── ruoyi-system                              // 系统模块 [9201]
│       └── ruoyi-gen                                 // 代码生成 [9202]
│       └── ruoyi-job                                 // 定时任务 [9203]
│       └── ruoyi-file                                // 文件服务 [9300]
│       └── ruoyi-flowable                            // 流程服务 [9308]
│       └── ruoyi-oa                                  // 办公管理 [9601]
├── ruoyi-visual          // 图形化管理模块
│       └── ruoyi-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
~~~

### 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。


# Ruoyi-cloud-flowable 微服务版搭建
## 基础环境
1. JDK-8
2. MySQL
1) 平台数据库
   数据库:  basedb_flow
   用户名:  basedb_flow
   密 码:  basedb_flow123
   ry_20240629.sql（必选）
   quartz.sql（可选）
   flowable相关表.sql（必选）
   工作流中心菜单.sql（必选）

#字典类型
insert into sys_dict_type values(500, '流程状态', 'wf_process_status',   '0', 'admin', sysdate(), '', null, '工作流程状态');

#字典值
insert into sys_dict_data values(501, 1,  '进行中',   'running',    'wf_process_status',   '',   'primary',  'N', '0', 'admin', sysdate(), '', null, '进行中状态');
insert into sys_dict_data values(502, 2,  '已终止',   'terminated', 'wf_process_status',   '',   'danger',   'N', '0', 'admin', sysdate(), '', null, '已终止状态');
insert into sys_dict_data values(503, 3,  '已完成',   'completed',  'wf_process_status',   '',   'success',  'N', '0', 'admin', sysdate(), '', null, '已完成状态');
insert into sys_dict_data values(504, 4,  '已取消',   'canceled',   'wf_process_status',   '',   'warning',  'N', '0', 'admin', sysdate(), '', null, '已取消状态');


ALTER TABLE gen_table
ADD COLUMN process_status_field varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '流程状态字段' AFTER remark,
ADD COLUMN process_key varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '流程部署实例key' AFTER process_status_field;


2) nocas数据库
   数据库: basedb_flow_config
   用户名： basedb_flow_config
   秘  密： basedb_flow_config123
   ry_config_20250224.sql（必选）

3) 集成seata分布式事务（可选配置，默认不启用）
   ry_seata_20210128.sql（可选）

3. Redis
   端口： 6379
   密码： test2021

   
## Nacos单机版
版本：2.5.2
配置文件： application.properties


# 前端环境搭建
https://gitcode.com/qq_34355118/RuoYi-Cloud-Vue3/tree/Gen_master



# 系统账号
管理员    admin/admin123
员工      ry/admin123
业务主管   zhangliao/123456
人事经理   wangyun/123456
财务总监   zhuge/123456
总经理     caocao/123456



