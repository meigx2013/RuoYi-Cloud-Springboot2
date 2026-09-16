-- 一级菜单
insert into sys_menu values('2350', '流程管理', '0', '4', 'process',  null, '','', 1, 0, 'M', '0', '0', '', 'skill',    'admin', sysdate(), '', null, '流程管理目录');




-- 二级菜单
-- 流程管理
insert into sys_menu values('2351',  '流程分类', '2350',   '1', 'category',   'workflow/category/index',      '','', 1, 0, 'C', '0', '0', 'workflow:category:list',   'nested',    'admin', sysdate(), '', null, '流程分类菜单');
insert into sys_menu values('2353',  '流程模型', '2350',   '3', 'model',      'workflow/model/index',         '','', 1, 0, 'C', '0', '0', 'workflow:model:list',      'component', 'admin', sysdate(), '', null, '流程模型菜单');
insert into sys_menu values('2354',  '部署管理', '2350',   '4', 'deploy',     'workflow/deploy/index',        '','', 1, 0, 'C', '0', '0', 'workflow:deploy:list',     'example',   'admin', sysdate(), '', null, '部署管理菜单');



-- 三级菜单
-- 流程分类管理
insert into sys_menu values('2363', '分类查询', '2351', '1', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:category:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2364', '分类新增', '2351', '2', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:category:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2365', '分类编辑', '2351', '3', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:category:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2366', '分类删除', '2351', '4', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:category:remove', '#', 'admin', sysdate(), '', null, '');

-- 流程模型
insert into sys_menu values('2372', '模型查询', '2353', '1', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:query',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2373', '模型新增', '2353', '2', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:add',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2374', '模型修改', '2353', '3', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:edit',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2375', '模型删除', '2353', '4', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:remove',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2376', '模型导出', '2353', '5', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:export',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2377', '模型导入', '2353', '6', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:import',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2378', '模型设计', '2353', '7', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:designer', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2379', '模型保存', '2353', '8', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:save',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2380', '流程部署', '2353', '9', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:model:deploy',   '#', 'admin', sysdate(), '', null, '');
-- 部署管理
insert into sys_menu values('2381', '部署查询', '2354', '1', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:deploy:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2382', '部署删除', '2354', '2', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:deploy:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2383', '更新状态', '2354', '3', '#', '', '','', 1, 0, 'F', '0', '0', 'workflow:deploy:status',  '#', 'admin', sysdate(), '', null, '');