/* 员工请假表 */
CREATE TABLE t_leave_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    employee_id BIGINT NOT NULL COMMENT '员工编号（可关联员工表）',
    leave_type VARCHAR(50) NOT NULL COMMENT '请假类型（如：病假、事假、年假、婚假、产假等）',
    start_date DATE NOT NOT NULL COMMENT '请假开始日期',
    end_date DATE NOT NULL COMMENT '请假结束日期',
    reason VARCHAR(255) COMMENT '请假原因说明',
    status VARCHAR(20) NOT NULL DEFAULT '待审批' COMMENT '业务审批状态（待审批、已批准、已拒绝、已撤销）',
    process_definition_id VARCHAR(100) COMMENT '流程定义ID（BPMN流程定义标识，如 leave_process:1:xxx）',
    process_status VARCHAR(50) COMMENT '流程实例状态（如：submitted, running, completed, terminated）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by VARCHAR(50) COMMENT '创建人（员工工号或用户名）',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工请假表';


/* 员工报销表 */
CREATE TABLE t_reimbursement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    employee_id BIGINT NOT NULL COMMENT '员工ID',
    reimbursement_amount DECIMAL(12, 2) NOT NULL DEFAULT 0.00 COMMENT '报销金额',
    reimbursement_type VARCHAR(10) NOT NULL COMMENT '报销类型（如：差旅费、办公费、招待费等）',
    description VARCHAR(500) COMMENT '费用说明',
    invoice_count INT DEFAULT 0 COMMENT '发票张数',
    submission_date DATE COMMENT '提交日期',
    process_definition_id VARCHAR(100) COMMENT '流程实例ID',
    process_state VARCHAR(50) COMMENT '流程状态',
    status VARCHAR(2) NOT NULL DEFAULT '0' COMMENT '业务状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工报销表';


/** 提示词管理表 */
CREATE TABLE t_prompt_management (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID，自增',
    prompt_name VARCHAR(100) NOT NULL COMMENT '提示词名称（简短标识）',
    prompt_content TEXT NOT NULL COMMENT '提示词具体内容',
    prompt_category VARCHAR(50) NOT NULL COMMENT '提示词分类（如：coding, database, writing, analysis 等）',
    status VARCHAR(2) NOT NULL DEFAULT '1' COMMENT '状态：1（启用）、0（停用）、3（归档）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_by VARCHAR(50) COMMENT '最后修改人',
    remark VARCHAR(500) COMMENT '备注说明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提示词管理表';


INSERT INTO t_prompt_management (
    prompt_name,
    prompt_content,
    prompt_category,
    status,
    create_by,
    remark
) VALUES
      (
          '生成用户权限管理三张表',
          '你是一个资深后端工程师，请生成 MySQL 的用户权限管理表结构，要求如下：
          1. 用户表 t_user：主键id、用户名、密码、邮箱、手机号、状态（0-禁用,1-启用）
          2. 角色表 t_role：主键id、角色编码、角色名称、描述
          3. 用户角色关联表 t_user_role：主键id、user_id、role_id
          其他要求：
          - 所有表包含 create_time, update_time, create_by, update_by, remark 字段
          - 主键自增
          - 建立外键约束（user_id → t_user.id, role_id → t_role.id）
          - 为每个字段添加 COMMENT
          - 使用 InnoDB 引擎，utf8mb4 字符集
          - 插入 5 条测试数据（含 admin 用户）',
          'database',
          'active',
          'sys_admin',
          'RBAC 权限模型基础表'
      ),
      (
          '生成订单与订单项一对多表结构',
          '请生成电商订单系统的 MySQL 表结构：
          1. 订单主表 t_order：主键id、订单号（唯一）、用户id、总金额、支付状态（0-未支付,1-已支付）、收货地址
          2. 订单明细表 t_order_item：主键id、order_id、商品id、商品名称、单价、数量、小计金额
          其他要求：
          - 所有表包含标准审计字段（create_time 等）
          - 主键自增
          - order_id 外键关联 t_order.id，级联删除
          - 所有字段加 COMMENT
          - 插入 3 条订单及对应明细测试数据',
          'database',
          'active',
          'ecommerce_dev',
          '典型一对多业务模型'
      ),
      (
          '生成日志记录表（无外键）',
          '生成一张系统操作日志表 t_operation_log，字段包括：
          - 主键 id（自增）
          - 操作人 user_id（BIGINT）
          - 操作类型（如 LOGIN, CREATE, UPDATE, DELETE）
          - 操作模块（如 USER, ORDER）
          - 请求IP
          - User-Agent
          - 操作详情（JSON 格式存储请求参数）
          - 是否成功（TINYINT 1/0）
          其他要求：
          - 包含 create_time（默认当前时间），无需 update_time
          - 其他审计字段按规范添加
          - 所有字段加 COMMENT
          - 不需要外键，不插入测试数据',
          'database',
          'active',
          'security_team',
          '用于安全审计与追踪'
      ),
      (
          '生成多租户 SaaS 架构基础表',
          '设计支持多租户的 SaaS 系统基础表：
          1. 租户表 t_tenant：主键id、租户名称、域名、状态、过期时间
          2. 用户表 t_user：主键id、tenant_id、用户名、密码、姓名
          要求：
          - 所有表必须包含 tenant_id 字段（除 t_tenant 自身）
          - 所有表包含完整审计字段
          - 主键自增
          - 外键：t_user.tenant_id → t_tenant.id
          - 字段 COMMENT 完整
          - 插入 2 个租户及其用户测试数据',
          'database',
          'active',
          'architect',
          '支持数据隔离的 SaaS 模型'
      ),
      (
          '生成设备管理与分组表',
          '生成物联网设备管理系统表结构：
          1. 设备分组表 t_device_group：主键id、分组名称、父分组ID（支持树形结构）、排序
          2. 设备表 t_device：主键id、设备编码（唯一）、设备名称、group_id、在线状态（0-离线,1-在线）、最后心跳时间
          要求：
          - 所有表含标准审计字段
          - 主键自增
          - 外键：t_device.group_id → t_device_group.id
          - 所有字段加 COMMENT
          - 插入 3 个分组（含父子关系）和 5 台设备测试数据',
          'database',
          'active',
          'iot_dev',
          '支持树形分组的设备管理'
      ),
      (
          '生成文章与标签多对多关联表',
          '生成博客系统的文章与标签多对多模型：
          1. 文章表 t_article：主键id、标题、内容、作者id、发布状态（0-草稿,1-发布）
          2. 标签表 t_tag：主键id、标签名（唯一）、使用次数
          3. 关联表 t_article_tag：主键id、article_id、tag_id
          要求：
          - 所有表含 create_time, update_time, create_by, update_by, remark
          - 主键自增
          - 外键约束完整
          - 字段 COMMENT 清晰
          - 插入 2 篇文章、3 个标签及关联数据',
          'database',
          'active',
          'content_team',
          '典型的多对多关系建模'
      ),
      (
          '生成定时任务配置表',
          '生成分布式定时任务配置表 t_job_config：
          - 主键 id（自增）
          - 任务名称（唯一）
          - cron 表达式
          - 执行类全限定名
          - 任务参数（JSON）
          - 是否启用（TINYINT）
          - 最近执行时间
          - 下次执行时间
          要求：
          - 包含完整审计字段
          - 所有字段加 COMMENT
          - 不需要外键
          - 插入 3 条常见任务测试数据（如每日数据统计、每周清理等）',
          'database',
          'active',
          'scheduler_dev',
          '用于 Quartz 或 XXL-JOB 配置'
      ),
      (
          '生成文件上传记录表',
          '生成文件上传记录表 t_file_record：
          - 主键 id（自增）
          - 原始文件名
          - 存储路径（OSS 或本地）
          - 文件大小（字节）
          - 文件类型（如 image/png）
          - 上传人 user_id
          - 业务类型（如 AVATAR, ATTACHMENT）
          - 业务ID（关联其他表主键）
          要求：
          - 含标准审计字段
          - 所有字段加 COMMENT
          - 不建外键（业务ID为泛关联）
          - 插入 5 条模拟上传记录',
          'database',
          'active',
          'file_service',
          '通用文件存储元数据表'
      ),
      (
          '生成审批流程模板表',
          '生成 OA 审批流程模板相关表：
          1. 流程模板表 t_process_template：主键id、模板名称、适用类型（如请假、报销）、是否启用
          2. 审批节点表 t_process_node：主键id、template_id、节点顺序、审批角色（如 MANAGER, HR）、是否允许多人审批
          要求：
          - 所有表含审计字段
          - 主键自增
          - 外键：t_process_node.template_id → t_process_template.id
          - 字段 COMMENT 完整
          - 插入 2 个模板（请假、报销）及对应节点',
          'database',
          'active',
          'oa_dev',
          '支持动态审批流配置'
      ),
      (
          '生成国际化多语言键值表',
          '生成多语言资源表 t_i18n_resource：
          - 主键 id（自增）
          - 资源键（如 user.login.title，唯一）
          - 语言代码（如 zh-CN, en-US）
          - 资源值（如 "登录"）
          - 模块标识（如 auth, common）
          要求：
          - 含 create_time, create_by, remark（无需 update_time/update_by）
          - 所有字段加 COMMENT
          - 插入中英文对照的 10 条常用文案（登录、注册、保存、取消等）',
          'database',
          'active',
          'frontend_team',
          '支持前端 i18n 国际化'
      );
