-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `account_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属帐户标识',
  `parent_id` varchar(36)  NULL DEFAULT NULL COMMENT '父级对象标识',
  `code` varchar(30)  NULL DEFAULT NULL COMMENT '代码',
  `application_name` varchar(50)  NULL DEFAULT NULL COMMENT '应用名称',
  `application_display_name` varchar(100)  NULL DEFAULT NULL COMMENT '应用显示名称',
  `application_key` varchar(36)  NULL DEFAULT NULL COMMENT '应用key',
  `application_secret` varchar(50)  NULL DEFAULT NULL COMMENT '应用秘钥',
  `pinyin` varchar(100)  NULL DEFAULT NULL COMMENT '拼音',
  `description` varchar(800)  NULL DEFAULT NULL COMMENT '描述',
  `has_children` bit(1) NULL DEFAULT NULL COMMENT '是否有子应用',
  `administrator_email` varchar(100)  NULL DEFAULT NULL COMMENT '管理员邮箱',
  `authority` int(11) NULL DEFAULT NULL COMMENT '权限等级',
  `is_sync` bit(1) NULL DEFAULT NULL COMMENT '是否同步',
  `recive_url` varchar(800)  NULL DEFAULT NULL COMMENT '接收地址',
  `recive_filter` varchar(400)  NULL DEFAULT NULL COMMENT '接收过滤器',
  `icon_path` varchar(400)  NULL DEFAULT NULL COMMENT '图标路径',
  `big_icon_path` varchar(400)  NULL DEFAULT NULL COMMENT '大图标路径',
  `help_url` varchar(800)  NULL DEFAULT NULL COMMENT '帮助地址',
  `license_status` varchar(20)  NULL DEFAULT NULL COMMENT '授权状态',
  `hidden` bit(1) NULL DEFAULT NULL COMMENT '隐藏',
  `locking` int(11) NULL DEFAULT NULL COMMENT '锁定',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(200)  NULL DEFAULT NULL COMMENT '备注',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '应用管理-应用信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '0001', 'ApplicationManagement', '应用管理', '00000000-0000-0000-0000-000000000001', 'hello', NULL, '应用管理', b'1', '', 1, b'0', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '06-01', 'Knowledge', '知识管理', '00000000-0000-0000-0000-000000000002', 'hello', '', '知识中心', b'0', '', 1, b'1', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 19:38:24', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-12', 'WorkflowPlus', '工作流管理', '00000000-0000-0000-0000-00000000', 'hello', '', '工作流管理中心', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '01-12', 1, '', '2015-06-02 10:50:36', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '05-03', 'Meeting', '会议管理', '00000000-0000-0000-0000-000000000004', 'hello', '', '会议管理', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 19:46:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '05-02', 'WorkRelation', '工作联系', '00000000-0000-0000-0000-000000000005', 'hello', '', '工作联系', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 20:41:26', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000001', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '04-03', 'Bugzilla', '问题跟踪', '00000000-0000-0000-0000-000000000006', 'hello', '', '问题跟踪', b'0', '', 1, b'0', '', '', '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 19:37:33', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '0007', 'Wiki', '维基百科', '00000000-0000-0000-0000-000000000007', 'hello', '', '维基百科', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:28:25', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000001', '4727c972-7e6c-4f54-9356-06e66535b66a', '02-01', 'Navigation', '导航管理', '00000000-0000-0000-0000-000000000008', 'hello', '', '导航管理', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:24:37', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000001', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '0009', 'Projects', '项目管理', '00000000-0000-0000-0000-000000000009', 'hello', '', 'Projects', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:27:28', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '1000', 'CollaborationPlatform', '协同平台', '00000000-0000-0000-0000-000000000010', 'hello', '', '协同平台', b'1', '', 1, b'0', '', '', '', '', '', '', b'0', 0, '10001', 1, '', '2015-05-30 00:32:07', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-05', 'Membership', '人员及权限管理', '00000000-0000-0000-0000-00000000', 'hello', '', '用户管理中心', b'1', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '01-05', 1, '', '2015-06-02 10:49:11', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('00000000-0000-0000-0000-000000000900', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '01', 'SupportPlatform', '基础支撑平台', '00000000-0000-0000-0000-000000000900', 'hello', '', '支撑平台', b'1', '', 1, b'0', '', '', '', '', '', '', b'0', 0, '10002', 1, '', '2015-05-30 00:31:58', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('0b4091b2-4316-430f-94cd-e080f9ce10cf', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '1009', 'WenDa', '问答管理', '0b4091b2-4316-430f-94cd-e080f9ce10cf', 'hello', '', '问答管理', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:27:01', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('0d4aa915-e075-4e22-abc5-e3dfc35b3471', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-05', 'DocumentManagement', '公文管理', '0d4aa915-e075-4e22-abc5-e3dfc35b3471', 'hello', NULL, '公文管理', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('1632a994-d54b-42ce-8d82-713ec61828e7', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-07', 'AttachmentStorage', '附件存储管理', '1632a994-d54b-42ce-8d82-713ec618', 'hello', '', '附件存储管理', b'0', '', 1, NULL, '', '', '', '', '', '', b'0', 1, '01-07', 1, '', '2015-06-02 10:57:43', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000001', 'ea559efc-22b4-441c-b8ea-9d0e9e50868f', '1007', 'PersonalSettings', '我的控制台', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', 'hello', '', '我的控制台', b'0', '', 0, NULL, '', '', '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:30:03', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('1d5143f9-c36c-4b7a-a83b-a181f8ff59fd', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '08', 'FinancialPlatform', '资金管理平台', '1d5143f9-c36c-4b7a-a83b-a181f8ff', 'hello', '', '自定义门户平台', b'0', '', 1, b'0', '', '', '', '', '', '', b'0', 1, '10008', 1, '', '2015-05-30 00:54:58', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-09', 'Tasks', '任务管理', '1f3e1dec-c411-44e0-82cf-ae4447a3', 'hello', '', '任务管理', b'0', '', 1, NULL, '', '', '', '', '', '', b'0', 0, '01-09', 1, '', '2015-06-02 11:09:20', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000001', 'ea559efc-22b4-441c-b8ea-9d0e9e50868f', '03-03', 'Schedule', '日程安排', '2128b7cf-a2e8-4e64-a09d-19d755860df3', 'hello', '', '日程安排', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:26:09', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('400ad6db-088c-4d02-8721-e4ab119216bc', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-08', 'WorkAttendance', '请假出差', '400ad6db-088c-4d02-8721-e4ab119216bc', 'hello', '', '请假出差', b'0', '', 1, NULL, '', '', '', '', '', '0', NULL, 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('43f8d051-79f4-4d7f-9007-c8d83236cdcd', '00000000-0000-0000-0000-000000000001', '4727c972-7e6c-4f54-9356-06e66535b66a', '02-02', 'Customizes', '自定义页面管理', 'a2d7472d213d4b3ba9295689279699c2', 'hello', '', '导航管理', b'0', '', 0, b'0', '', '', '', '', '', '0', b'1', 0, '02-02', 1, '2012-08-23 14:19:46.187', '2015-06-02 13:19:16', NULL);
INSERT INTO `application` VALUES ('472768be-db8f-46b9-89ce-1d3c2a1bc452', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '10', 'OpenPlatform', '应用开放平台', '472768be-db8f-46b9-89ce-1d3c2a1bc452', '123456', '', '个人事务平台', b'1', '', 0, b'0', '', '', '', '', '', '', b'0', 0, '10010', 1, '', '2015-05-30 00:31:33', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('4727c972-7e6c-4f54-9356-06e66535b66a', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '02', 'PortalPlatform', '自定义门户平台', '4727c972-7e6c-4f54-9356-06e66535b66a', 'hello', '', '自定义门户平台', b'0', '', 1, b'0', '', '', '', '', '', '', b'0', 1, '10001', 1, '', '2015-05-30 00:29:50', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000001', '4727c972-7e6c-4f54-9356-06e66535b66a', '02-05', 'Forum', '论坛管理', '4c65fa99-82e3-4c49-a97a-07a6344c', 'hello', '', '论坛管理', b'0', '', 1, NULL, '', '', '', '', '', '', b'0', 0, '02-05', 1, '', '2015-06-02 13:19:42', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000001', '4727c972-7e6c-4f54-9356-06e66535b66a', '02-04', 'News', '新闻管理', '4d946db8-2be7-40f3-9cdf-8e8bb30a', 'hello', '', '新闻', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 0, '02-04', 1, '', '2015-06-02 13:19:51', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('4dbdff30-42e1-484c-a27a-bee1c2eba073', '00000000-0000-0000-0000-000000000001', '472768be-db8f-46b9-89ce-1d3c2a1bc452', '1013', 'Test1', '测试系统123', 'a4ac7590-8700-419d-aae7-6a0f6468', 'abcdefgee', 'testfffff', '测试系统1', b'0', 'e', 1, b'0', '', '', 'ef', 'f', 'd', 'ab', b'1', 0, '00031', 1, '', '2018-08-22 16:53:56', '1970-01-01 00:00:00');
INSERT INTO `application` VALUES ('5034d638-18e9-4586-aa8d-a27d96e07ad7', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '1003', 'KnowledgeManagementPlatform', '知识管理平台', '5034d638-18e9-4586-aa8d-a27d96e07ad7', 'hello', NULL, '知识管理平台', b'1', '', 0, NULL, '', '', NULL, NULL, NULL, NULL, NULL, NULL, '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-03', 'Entities', '实体数据管理', '5079360f-ceed-496b-8a5e-a121c5ad', 'hello', '', '实体元数据管理', b'0', '', 0, NULL, '', '', '1', '', '2010-08-06 13:31:34', '', b'0', 0, '01-03', 1, '', '2015-06-02 10:47:07', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('50bec887-c422-4694-8de0-6ae1a46f8e84', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1004', 'Assets', '资产管理', '50bec887-c422-4694-8de0-6ae1a46f8e84', 'hello', '', '资产管理', b'0', '', 0, NULL, '', '', NULL, NULL, NULL, '0', NULL, 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '9999', 'test', '测试系统', '52cf89ba-7db5-4e64-9c64-3c868b6e', 'hello', '', '测试系统', b'0', '', 0, b'1', 'http://oa.longhu.net:81/API/ThirdPart/PackageReceiver.asmx', NULL, '', '', '', '', b'0', 0, '9999', 1, '', '2015-06-02 11:10:27', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('5a5e3601-fc48-4363-9793-73437db5114e', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '1003-04', 'Training', '培训管理', '5a5e3601-fc48-4363-9793-73437db5114e', 'hello', NULL, '培训管理', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('64c6a20a-7516-4b41-bc87-53fd5da85b12', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '1003-05', 'Exam', '考试管理', '64c6a20a-7516-4b41-bc87-53fd5da85b12', 'hello', NULL, '考试管理', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '10005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('6c2a314d-0c2d-455f-b64a-d098b9d49c42', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000100', '1012', 'AccumulatePoint', '累计积分管理', '6c2a314d-0c2d-455f-b64a-d098b9d49c42', 'hello', NULL, '累计积分管理', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('6cc8d049-4a46-4a9a-bc81-348c86e7b8ff', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-20', 'Optimizer', '网页优化计时器', '6cc8d049-4a46-4a9a-bc81-348c86e7', 'hello', '', '网页优化计时器', b'0', '', 0, NULL, '', '', '', '', '', '', b'0', 1, '01-20', 1, '', '2015-06-02 11:15:25', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-06', 'Contract', '合同管理', '73eb767e-340a-453e-a42d-68600e885fa9', 'hello', 'HT', '合同管理', b'0', '', 1, NULL, '', '', '', '', '', '0', NULL, 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000001', '1d5143f9-c36c-4b7a-a83b-a181f8ff59fd', '1001-07', 'Cost', '费用报销管理', '8711f459-972f-4eee-9348-1f28f4da', 'hello', 'COST', '费用报销管理', b'0', '', 1, NULL, '', '', '', '', '', '0', b'0', 0, '', 1, '', '2015-05-30 00:55:26', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('88888888-8888-8888-8888-000000000001', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '01', 'demo-app', '演示应用', '00000000-0000-0000-0000-000000000900', '123456', '', '支撑平台', b'1', '', 1, b'0', '', '', '', '', '', '', b'0', 0, '10002', 1, '', '2015-05-30 00:31:58', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('88bbad70-2e09-45b1-856b-1d16128d122d', '00000000-0000-0000-0000-000000000001', 'ea559efc-22b4-441c-b8ea-9d0e9e50868f', '1012', 'Favorite', '我的收藏夹', '88bbad70-2e09-45b1-856b-1d16128d122d', 'hello', '', '我的收藏夹', b'0', '', 1, NULL, '', '', NULL, NULL, NULL, '0', NULL, 0, '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-15', 'HumanResources', '人力资源管理', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', 'hello', '', '人力资源管理', b'0', '', 0, NULL, '', '', '', '', '', '', NULL, NULL, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('94f30a73-244f-46f8-a476-1570a563480a', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-11', 'Itil', 'IT运维', '94f30a73-244f-46f8-a476-1570a563480a', 'hello', NULL, 'IT运维', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '10011', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '1003-06', 'BuildStandard', '建造标准管理', '9df3ee14-3718-42f4-9da1-226c55e8cc53', 'hello', '', '建造标准管理', b'0', '', 0, NULL, '', '', '', '', '', '0', NULL, 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('a4077a0d-6126-46a8-89bf-f4e5f2c0623a', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-19', 'Cms', '内容管理', 'a4077a0d-6126-46a8-89bf-f4e5f2c0', 'hello', '', '基础配置管理', b'0', '', 0, b'0', '', '', '', '', '', '', b'0', 0, '01-19', 1, '', '2015-06-02 10:53:36', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000001', '472768be-db8f-46b9-89ce-1d3c2a1bc452', '1012', 'DengBao', '登报管理', 'a4ac7590-8700-419d-aae7-6a0f6468', 'hello', '', '登报管理', b'0', '', 1, b'0', '', '', '', '', '', '', b'1', 1, '0003', 1, '', '2015-06-02 10:51:14', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('a70633f6-b37a-4e91-97a0-597d708fdcef', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '04', 'ProjectManagementPlatform', '项目管理平台', 'a70633f6-b37a-4e91-97a0-597d708fdcef', 'hello', '', '项目管理平台', b'1', '', 0, NULL, '', '', '', '', '', '', b'0', 0, '10003', 1, '', '2015-05-30 00:30:43', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('a9205590-6a7e-424d-a784-5b3aaf60a429', '00000000-0000-0000-0000-000000000001', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '01-15', 'Plugins', '插件管理', 'a9205590-6a7e-424d-a784-5b3aaf60a429', 'hello', '', '插件管理', b'0', '', 1, b'0', '', '', '', '', '', '', b'0', 1, '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('af426531-ecf5-46fb-828c-27624dad8d23', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-12', 'CheckI', '考勤打卡', 'af426531-ecf5-46fb-828c-27624dad8d23', 'hello', '', '考勤打卡', b'0', '', 0, NULL, '', '', '', '', '', '0', NULL, 1, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('b6a2d8e0-d92b-4948-8cc7-868352e8bc9b', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '09', 'LearningPlatform', '在线学习平台', 'b6a2d8e0-d92b-4948-8cc7-868352e8bc9b', 'hello', '', '项目管理平台', b'1', '', 0, NULL, '', '', '', '', '', '', b'0', 0, '10009', 1, '', '2015-05-30 00:25:37', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-08', 'Wizards', '向导组件管理', 'bbdc4b78-50d1-465e-b930-888e6b00', 'hello', '', '', b'0', '向导组件管理', NULL, NULL, 'False', '', '', '', '', '', b'0', 0, '01-08', 1, '10009', '2015-06-02 11:07:47', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('bc9568ea-39ce-4bea-ba09-7f3055ff83c2', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-06', 'Storages', '存储管理', 'd2d7db4b722240b3a7095c0ede0843b9', 'hello', '', '存储管理', b'0', '', NULL, b'0', '', '', '', '', '', '', b'1', 10004, '01-06', 1, '2000-01-01 00:00:00.000', '2015-06-02 11:15:53', NULL);
INSERT INTO `application` VALUES ('c786db9a-b8f8-4162-8be7-57d828c004ff', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-13', 'Connect', '应用连接器管理', 'c786db9a-b8f8-4162-8be7-57d828c0', 'hello', '', '应用连接器管理', b'0', '', 1, b'0', '', NULL, '', '', '', '', b'0', 1, '01-13', 1, '', '2015-06-02 10:55:16', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('ccfab2f9-4fad-400a-98ce-1f20f719d329', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '05', 'PublicServicesPlatform', '公共事务平台', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', 'hello', '', '公共事务平台', b'1', '', 0, NULL, '', '', '', '', '', '', b'0', 0, '10004', 1, '', '2015-05-30 00:33:17', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('d3398a7d-c369-4988-a953-09a88cd73d4d', '00000000-0000-0000-0000-000000000001', '5034d638-18e9-4586-aa8d-a27d96e07ad7', '1006', 'WenKu', '文库管理', 'd3398a7d-c369-4988-a953-09a88cd73d4d', 'hello', '', '文库管理', b'0', '', 0, b'0', '', '', '', '', '', '', b'0', 0, '', 1, '', '2015-05-27 21:26:39', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('d6e3f4ba-44c8-45d4-8ca1-12de005bd470', '00000000-0000-0000-0000-000000000001', 'ea559efc-22b4-441c-b8ea-9d0e9e50868f', '03-04', 'Timeline', '时间轴管理', 'd6e3f4ba-44c8-45d4-8ca1-12de005bd470', 'hello', NULL, '文档管理', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('de646d64-14e4-43d3-bb3c-54dc5b1035ff', '00000000-0000-0000-0000-000000000001', 'ea559efc-22b4-441c-b8ea-9d0e9e50868f', '1002-03', 'TimeTracking', '时间跟踪', 'de646d64-14e4-43d3-bb3c-54dc5b1035ff', 'hello', NULL, '时间跟踪', b'0', '', 0, NULL, '', '', '', '', '', NULL, NULL, 1, '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-11', 'CustomForms', '表单管理', 'df45ddaa-b9f4-49ef-bb32-07c8f0a8', '222', '', 'CustomForms', b'0', '22', 2222, b'0', '发生的', NULL, '', '', '', '', b'0', 0, '01-11', 1, '', '2015-06-02 10:50:59', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-10', 'Survey', '问卷调查', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', 'hello', NULL, '问卷调查', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '10010', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('ea559efc-22b4-441c-b8ea-9d0e9e50868f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000010', '03', 'PersonalServicesPlatform', '个人事务平台', 'ea559efc-22b4-441c-b8ea-9d0e9e50868f', 'hello', '', '个人事务平台', b'1', '', 0, NULL, '', '', '', '', '', '', b'0', 0, '10002', 1, '', '2015-05-30 00:30:14', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('ecc45e71-d758-4a53-9dc5-ade0eeb36c62', '00000000-0000-0000-0000-000000000001', 'ccfab2f9-4fad-400a-98ce-1f20f719d329', '1001-09', 'Docs', '文档管理', 'ecc45e71-d758-4a53-9dc5-ade0eeb36c62', 'hello', NULL, '文档管理', b'0', '', 1, NULL, '', '', '', '', '', NULL, NULL, NULL, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application` VALUES ('ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000900', '01-01', 'ConfigurationManagement', '基础配置管理', 'ff2bff6b-922c-4eda-b5ec-f2553577', 'hello', '', '基础配置管理', b'0', '', 0, NULL, '', '', '', '', '', '', b'0', 0, '01-01', 1, '', '2015-06-02 10:52:29', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for application_error
-- ----------------------------
DROP TABLE IF EXISTS `application_error`;
CREATE TABLE `application_error`  (
  `id` varchar(36)  NOT NULL,
  `application_id` varchar(36)  NULL DEFAULT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `title` varchar(50)  NULL DEFAULT NULL,
  `description` text  NULL,
  `status_code` int(11) NULL DEFAULT NULL,
  `locking` int(11) NULL DEFAULT NULL,
  `tags` varchar(50)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_error
-- ----------------------------
INSERT INTO `application_error` VALUES ('401', '00000000-0000-0000-0000-000000000000', '401', '噢，系统找到相关数据内容，但是未授权给你查阅权限，请联系数据拥有者。', '<ul><li>直接复制粘贴的地址;</li><li>没有相关内容的查看权限;</li><li>请求的内容正在审核中或者被禁止查阅;</li></ul>', 401, 1, '常见错误', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_error` VALUES ('404', '00000000-0000-0000-0000-000000000000', '404', '哎呀，系统经过努力的查找，居然没找到相关的内容。', '<ul><li>请求的内容不存在;</li><li>请求的内容正在审核中或者被禁止查阅;</li><li>请求的内容已被删除;</li></ul>', 404, 1, '常见错误', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_error` VALUES ('500', '00000000-0000-0000-0000-000000000000', '500', '哟，系统正在思考人生。。。', '<ul><li>系统发生内部错误;</li><li>发送的请求过多，系统忙不过来了，请稍候再试;</li></ul>', 500, 1, '常见错误', '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for application_event
-- ----------------------------
DROP TABLE IF EXISTS `application_event`;
CREATE TABLE `application_event`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `application_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属应用标识',
  `level` varchar(100)  NULL DEFAULT NULL COMMENT '事件级别',
  `description` varchar(800)  NULL DEFAULT NULL COMMENT '描述',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `timespan` decimal(10, 0) NULL DEFAULT NULL COMMENT '时间总计',
  `ip` varchar(20)  NULL DEFAULT NULL COMMENT 'IP',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for application_feature
-- ----------------------------
DROP TABLE IF EXISTS `application_feature`;
CREATE TABLE `application_feature`  (
  `id` varchar(36)  NOT NULL,
  `application_id` varchar(36)  NULL DEFAULT NULL,
  `parent_id` varchar(36)  NULL DEFAULT NULL,
  `code` varchar(10)  NULL DEFAULT NULL,
  `name` varchar(50)  NULL DEFAULT NULL,
  `display_name` varchar(50)  NULL DEFAULT NULL,
  `type` varchar(20)  NULL DEFAULT NULL,
  `url` varchar(800)  NULL DEFAULT NULL,
  `target` varchar(20)  NULL DEFAULT NULL,
  `icon_path` varchar(400)  NULL DEFAULT NULL,
  `big_icon_path` varchar(400)  NULL DEFAULT NULL,
  `help_url` varchar(800)  NULL DEFAULT NULL,
  `hidden` bit(1) NULL DEFAULT NULL,
  `effect_scope` int(2) NULL DEFAULT NULL,
  `tree_view_scope` int(2) NULL DEFAULT NULL,
  `locking` int(2) NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '应用管理-应用功能信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_feature
-- ----------------------------
INSERT INTO `application_feature` VALUES ('1878f980-ff95-41bf-ab7d-7d09ca8bc0b5', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100049', 'i', 'i', 'i', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, 'i', 1, 'i', '2015-05-18 15:35:48', '2015-05-18 15:35:48');
INSERT INTO `application_feature` VALUES ('230ca77f-e6a1-44a4-a966-a4551a28a869', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100054', '测试功能2', '测试功能2', 'function', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, '', 1, '', '2015-05-21 21:35:51', '2015-05-21 21:35:51');
INSERT INTO `application_feature` VALUES ('264bc5c4-8393-42ce-9faa-b3fc6cc94c77', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '7c3e2e3f-d6ff-4bf4-a19f-28230f02b52a', '100052', '9999', '999', 'action', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, '', 1, '', '2015-05-21 15:42:14', '2015-05-21 15:42:14');
INSERT INTO `application_feature` VALUES ('7c3e2e3f-d6ff-4bf4-a19f-28230f02b52a', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100053', '测试功能1', '测试功能1', 'function', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, '', 1, '', '2015-05-21 21:36:14', '2015-05-21 20:57:35');
INSERT INTO `application_feature` VALUES ('928d28d9-7046-4c50-8822-bcf81fc00e92', '00000000-0000-0000-0000-000000000001', 'd5e022c3-364b-4ef4-b1e0-10b69c263415', '100048', '9i', '9', 'function', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, '', 0, '', '2015-05-21 22:53:03', '2015-05-18 15:35:33');
INSERT INTO `application_feature` VALUES ('d5e022c3-364b-4ef4-b1e0-10b69c263415', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100051', 'ii', '999', 'function', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, '99', 1, '999', '2015-05-18 15:54:26', '2015-05-18 15:54:26');
INSERT INTO `application_feature` VALUES ('ef951386-174c-4a3c-8b94-055b316e9b1c', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100050', '99', '99', '999', NULL, NULL, NULL, NULL, NULL, b'0', NULL, NULL, 0, '', 0, '', '2015-05-18 15:40:42', '2015-05-18 15:40:42');

-- ----------------------------
-- Table structure for application_feature_scope
-- ----------------------------
DROP TABLE IF EXISTS `application_feature_scope`;
CREATE TABLE `application_feature_scope`  (
  `entity_id` varchar(36)  NOT NULL,
  `entity_class_name` varchar(400)  NULL DEFAULT NULL,
  `authority_id` varchar(36)  NOT NULL,
  `authorization_object_type` varchar(20)  NOT NULL,
  `authorization_object_id` varchar(36)  NOT NULL,
  PRIMARY KEY (`entity_id`, `authority_id`, `authorization_object_type`, `authorization_object_id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for application_menu
-- ----------------------------
DROP TABLE IF EXISTS `application_menu`;
CREATE TABLE `application_menu`  (
  `id` varchar(36)  NOT NULL,
  `application_id` varchar(36)  NULL DEFAULT NULL,
  `parent_id` varchar(36)  NULL DEFAULT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `name` varchar(100)  NULL DEFAULT NULL,
  `description` varchar(200)  NULL DEFAULT NULL,
  `url` varchar(800)  NULL DEFAULT NULL,
  `target` varchar(20)  NULL DEFAULT NULL,
  `menu_type` varchar(20)  NULL DEFAULT NULL,
  `icon_path` varchar(400)  NULL DEFAULT NULL,
  `big_icon_path` varchar(400)  NULL DEFAULT NULL,
  `display_type` varchar(20)  NULL DEFAULT NULL,
  `has_child` int(11) NULL DEFAULT NULL,
  `context_object` varchar(200)  NULL DEFAULT NULL,
  `order_id` varchar(50)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `full_path` varchar(800)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_menu
-- ----------------------------
INSERT INTO `application_menu` VALUES ('03a5a3d6-5d1d-4bbc-857e-474fdf601775', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100025', '流程设置', '流程设置', '/apps/pages/survey/survey-workflow-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 1, '', '问卷调查流程设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('03e23eaa-7c13-416a-8505-0ff2a7b6f3a0', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100037', '提交表单', '提交表单', '/apps/pages/custom-forms/custom-form-template-launcher.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '表单管理提交表单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('047cb199-c281-42ca-9264-45a69b507e73', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100018', '发布新的帖子', '发布新的帖子', '/forum/form', '_blank', 'ApplicationMenu', 'fa fa-file', '', 'MenuItem', 0, '', '10002', 1, '', '论坛管理\\发布新的帖子', '2015-07-14 23:14:16', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('08880493-b81b-482c-8e5c-4386851cf8f6', '2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000000', '120006', '类别设置', '类别设置', '/apps/pages/schedule/schedule-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '日程安排类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('09177a12-87cc-44ee-b792-001fb62f5c79', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100034', '待审批表单', '待审批表单', '/apps/pages/custom-forms/custom-form-instance-workflow-request.aspx?type=pending', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '表单管理待审批表单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('09dafaf4-16a7-4fdd-b01b-3e875be73e5b', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100026', '应用方法管理', '应用方法管理', '/applications/application-method/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0008', 1, '', '应用管理\\应用方法管理', '2013-05-27 10:09:36', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('0a5eadf0-13f9-46b6-b6a4-c6d0cfda9b39', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104016', '会议室类别设置', '会议室类别设置', '/apps/pages/meeting/meeting-room-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10016', 1, '', '会议管理会议室类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('0ac88168-f676-4a67-a00a-b5206c517380', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100019', '最新帖子', '最新帖子', '/forum/', '_self', 'ApplicationMenu', 'fa fa-newspaper-o', '', 'MenuItem', 0, '', '10001', 1, '', '论坛管理\\最新帖子', '2015-07-14 23:16:31', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('0ad98816-47ee-4ef1-a94d-96ea4bbacd52', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100020', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10005', 1, '', '问卷调查审批管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('0c81ddea-be77-4193-ba8c-a0995773560d', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104003', '安排新的会议', '安排新的会议', '/apps/pages/meeting/meeting-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '会议管理安排新的会议', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('0ddf1edb-7033-4215-af5e-aa285b6cec4b', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122005', '待审联系单', '待审联系单', '/apps/pages/workrelation/workrelationworking-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '工作联系待审联系单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('0e640785-1a59-48ff-8e3e-983231c5f5e5', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000000', '117003', '开发帮助手册', '开发帮助手册', '/wiki/book/system_sdk_help.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '维基百科系统开发手册', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('10fe08b3-5ef0-4c3c-8b23-7b310502cf12', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100039', '新闻查询', '新闻查询', '/news/', '_self', 'ApplicationMenu', 'fa fa-search', '', 'MenuItem', 0, '', '10001', 1, '', '新闻管理\\新闻查询', '2015-05-27 17:26:57', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('11624408-2110-45b5-bfdd-331e663d84b2', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100029', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '建造标准管理审批管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('11c5f03b-fec9-4595-98f7-531cb7b1abc9', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100048', '合同查询', '合同查询', '/apps/pages/contract/contract-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('12ddac90-83ee-4252-b04e-e016d0a24137', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100054', '已审合同', '已审合同', '/apps/pages/contract/contract-workflow-request.aspx?type=approved', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('1324e35d-e86a-41bb-b7e2-ee5899c46eec', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100054', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10007', 1, '', '物业报销管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('147a7aea-3832-45c0-aff8-6dcd8259b5a5', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100021', '菜单分割线1', '菜单分割线1', '#', '_self', 'StartMenu', '', '', 'MenuSplitLine', 0, '', '0003', 1, '', '开始菜单菜单分割线1', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('147deb29-2895-4695-ad8c-5cdfb35d4892', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100005', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '1008', 1, '', '人力资源管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('17303594-faf2-4c19-abed-aeebef4af33a', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104014', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10014', 1, '', '会议管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('17c0b98a-aa91-41f4-b5db-a915400f1c89', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100020', '项目管理', '项目管理', '/projects/', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0004', 1, '', '开始菜单\\项目管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('18417b12-bf41-43ae-bb44-6818887cea25', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000000', '108005', '侧栏菜单项分组', '侧栏菜单项分组', '/apps/pages/navigation/navigation-portal-siderbaritemgroup-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '导航管理侧栏菜单项分组', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('1a915c79-65bc-4840-9c9c-89106891bb63', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100046', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10008', 1, '', '新闻管理\\基础设置', '2015-05-29 23:32:42', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('1b7ad104-d0f0-45bc-9671-09fa38ecb9e0', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104008', '会议室状态查询', '会议室状态查询', '/apps/pages/meeting/meeting-room-report.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '会议管理会议室状态查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('1dfe14b6-6d71-4e77-85f9-ab6486aaeab0', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100052', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10005', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('1e013112-b3a4-424b-ab22-b4a1a6ea90a3', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100032', '基本设置', '基本设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10008', 1, '', '建造标准管理基本设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('206f2cc8-d46e-4c21-888b-f36a71cde71c', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '123009', '参数设置', '参数设置', '/apps/pages/contract/contract-application-setting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '合同管理参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('21303ccd-6371-40ab-be0e-4ef1db1abcc7', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000000', '108006', '侧栏菜单项管理', '侧栏菜单项管理', '/apps/pages/navigation/navigation-portal-siderbaritem-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '导航管理侧栏菜单项管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('21cbea24-8aff-4f21-9753-2315bc7ab131', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100014', '人员及权限管理', '人员及权限管理', '/membership/', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '开始菜单\\人员及权限管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2518d83f-0eb2-473e-bd9c-9be9df2de911', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100033', '新员工入职', '新员工入职', '/apps/pages/hr/member-register.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1004', 1, '', '人力资源管理新员工入职', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('25a83847-4231-4013-aa9d-8d12b85cfbfd', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100030', '标准通用角色设置', '标准通用角色设置', '/membership/standard-general-role/list', '_self', 'ApplicationMenu', 'fa fa-users', '', 'MenuItem', 0, '', '10015', 1, '', '人员及权限管理\\标准通用角色设置', '2015-06-01 21:57:36', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('25af37e1-a386-45f8-90dc-fff039a3de90', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100051', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '物业报销管理审批管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('25ea3299-6a38-47a3-95ef-dfed123cb22f', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100055', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10008', 1, '', '物业合同管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('26629b07-d131-4bbc-af79-032764a9f3cc', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100017', '帖子查询', '帖子查询', '/forum/forum-thread/list', '_self', 'ApplicationMenu', 'fa fa-archive', '', 'MenuItem', 0, '', '10004', 1, '', '论坛管理\\帖子查询', '2015-07-14 23:12:20', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2664b12a-d081-49de-8587-ddf33b99a4b4', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100027', '应用配置管理', '应用配置管理', '/applications/', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '应用管理\\应用配置管理', '2015-05-30 11:57:02', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2955877e-21ab-44fd-a050-0e2b5d72bbbf', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100028', '应用选项设置', '应用选项设置', '/applications/application-option/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '应用管理\\应用选项设置', '2013-04-19 09:45:19', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('29bf4f44-48e5-4ba1-a78e-9ef0c226f90b', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104001', '我要参加的会议 ', '我要参加的会议', '/apps/pages/meeting/', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '会议管理我要参加的会议', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2b0cd4b1-4550-4485-a7fc-e4d2cf3773d4', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100051', '邮件设置', '邮件设置', '/apps/pages/platform/email-client-setting.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '基础配置管理邮件设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2bf6b5c5-76b0-441a-820f-2f0534159667', '00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000000', '100003', '参数设置', '参数设置', '/apps/pages/projects/project-setting-list.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-gear.gif', '', 'MenuItem', 0, '', '0003', 1, '', '项目管理\\参数设置', '2013-04-16 22:49:32', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2da83694-4d8a-499e-833c-2244e3032de4', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100011', '用户信息查询', '用户信息查询', '/membership/contacts/terminal', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10011', 0, '', '人员及权限管理\\用户信息查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2f01a991-7369-4ec7-9c3d-2f71ce3a984c', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100003', '组织管理', '组织管理', '/membership/organization/list', '_self', 'ApplicationMenu', 'fa fa-building', '', 'MenuItem', 0, '', '10004', 1, '', '人员及权限管理\\组织管理', '2015-06-01 21:26:53', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('2feced95-48de-4f01-adb5-15ed92678bc9', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100032', '应用管理', '应用管理', '/apps/pages/applications/default.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '开始菜单\\应用管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('30411afd-ed32-4856-b349-37629d81c73d', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '106002', '发送任务信息', '发送任务信息', '/tasks/task/form', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '任务管理\\发送任务信息', '2015-07-23 14:28:56', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('304a8838-7301-4e72-aa12-e5c5908a64c8', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '110009', '参数设置', '参数设置', '/forum/forum-setting/list', '_self', 'ApplicationMenu', 'fa fa-cog', '', 'MenuItem', 0, '', '10009', 1, '', '论坛管理\\参数设置', '2015-07-14 23:06:55', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('304de3ea-59b5-42d8-ab11-6c9fa4ee7ada', '00000000-0000-0000-0000-000000000001', '2feced95-48de-4f01-adb5-15ed92678bc9', '100005', '开始菜单1.1', '开始菜单1.1', '#', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3078905e-c34b-4473-adcd-a4b5cdfc4f69', '00000000-0000-0000-0000-000000000001', '2feced95-48de-4f01-adb5-15ed92678bc9', '100008', '应用菜单管理', '应用菜单管理', '/apps/pages/applications/application-menu-list.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('32bd4dcb-abfe-48af-a7d4-e3cd4a470f31', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104018', '会议类别设置', '会议类别设置', '/apps/pages/meeting/meeting-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10018', 1, '', '会议管理会议类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('32c5edf2-2eff-4bac-bbec-102e86f27bce', '6cc8d049-4a46-4a9a-bc81-348c86e7b8ff', '00000000-0000-0000-0000-000000000000', '100019', '详细信息页面', '详细信息页面', '/apps/pages/optimizer/blank-view.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '网页优化计时器详细信息页面', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('33661a96-b344-4dbf-8502-d1d341e05e70', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100058', '相关链接设置向导', '相关链接设置向导', 'shared?path=wizards/related-link-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '向导组件管理\\相关链接设置向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('33d385a2-bfdc-4237-89c8-881989a642ba', '400ad6db-088c-4d02-8721-e4ab119216bc', '00000000-0000-0000-0000-000000000000', '100070', '类别设置', '类别设置', '/apps/pages/workattendance/workattendance-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 1, '', '请假出差类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('34026a84-9c86-4987-81ad-df2396cfb47c', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100007', '职级管理', '职级管理', '/apps/pages/hr/job-grade-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1010', 1, '', '人力资源管理职级管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3427acc4-9b1d-4984-8de5-3b78461581af', '6cc8d049-4a46-4a9a-bc81-348c86e7b8ff', '00000000-0000-0000-0000-000000000000', '100018', '列表页面', '列表页面', '/apps/pages/optimizer/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '网页优化计时器列表页面', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('362a2f66-9995-4ce0-ab71-aa6806070771', '00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000000', '100002', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '0002', 1, '', '项目管理\\基础设置', '2013-04-16 21:33:57', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('36491ad2-1252-4514-a81a-4fc132d31c51', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100005', '通用角色管理', '通用角色管理', '/membership/general-role/list', '_self', 'ApplicationMenu', 'fa fa-users', '', 'MenuItem', 0, '', '10006', 1, '', '人员及权限管理\\通用角色管理', '2015-06-01 21:32:35', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3808f574-f089-4f59-8ea3-8b2d42868ac8', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100008', '标准组织管理', '标准组织管理', '/membership/standard-organization/list', '_self', 'ApplicationMenu', 'fa fa-building', '', 'MenuItem', 0, '', '10007', 1, '', '人员及权限管理\\标准组织管理', '2015-06-01 21:26:43', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('397704cf-01c4-473e-81b8-3d0aacb002fe', '00000000-0000-0000-0000-000000000001', 'e9018fab-d7e8-418f-b524-6e1b08b5e31b', '100022', '应用参数设置', '应用参数设置', '/apps/pages/applications/application-setting-list.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '开始菜单\\应用管理\\应用参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3b1599c3-20c9-4ce0-a7c2-4562fdda4d87', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '111004', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '问题跟踪\\基础设置', '2013-04-16 22:00:20', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3e4b0a4b-4f3d-4e5a-9ac3-785615d2a1f0', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000000', '100020', '新建登报信息', '新建登报信息', '/apps/pages/dengbao/dengbao-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '登报管理新建登报信息', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3e7382c8-e266-442c-b107-8da1c0b18701', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104012', '待审会议纪要', '待审会议纪要', '/apps/pages/meeting/meeting-memo-workflow-request.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10012', 1, '', '会议管理待审会议纪要', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3ea9a389-0b58-4887-88fa-7dc2bca9b7fc', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100056', '项目选择向导', '项目选择向导', 'shared?path=wizards/project-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '向导组件管理\\项目选择向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3ecc898a-a870-418f-b5d1-1412d30e037b', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100028', '应用功能设置', '应用功能设置', '/applications/application-feature/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0003', 1, '', '应用管理\\应用功能设置', '2013-05-27 10:09:28', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('3f944366-6f6b-43e8-ad4a-4f7cd406729c', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104007', '会议室借用', '会议室借用', '/apps/pages/meeting/meeting-room-lock-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '会议管理会议室借用', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4141f7ae-bb0d-4f7c-875f-230b777af780', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100050', '我的费用报销', '我的费用报销', '/cost/my-cost/list', '_self', 'ApplicationMenu', 'fa fa-folder', '', 'MenuItem', 0, '', '10003', 1, '', '费用报销管理\\我的费用报销', '2015-05-30 01:01:11', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4176b203-ca6b-4698-9240-9e4bd0ae19e4', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '111001', '问题列表', '问题列表', '/apps/pages/bugzilla/', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-bug.gif', '', 'MenuItem', 0, '', '10001', 1, '', '问题跟踪\\问题列表', '2013-04-16 22:48:44', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('44014075-ea64-4824-861b-b38edd6cef18', '00000000-0000-0000-0000-000000000001', '2feced95-48de-4f01-adb5-15ed92678bc9', '100010', '应用参数设置', '应用参数设置', '/applications/application-setting/list', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('448f8f46-747d-4864-b795-531d76164436', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104006', '我添加的纪要', '我添加的纪要', '/apps/pages/meeting/my-meeting-memo.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '会议管理我添加的纪要', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('45300dac-4803-43e7-9987-9de110926c28', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100032', '员工通讯录', '员工通讯录', '/apps/pages/hr/contacts.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1003', 1, '', '人力资源管理员工通讯录', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('45538e3d-bd06-47b9-abfc-d676feefa4a6', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100012', '登报管理', '登报管理', '/apps/pages/dengbao/default.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0004', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('456b63f7-f036-48cb-b419-01bd3ce26e89', 'c786db9a-b8f8-4162-8be7-57d828c004ff', '6d182f8b-0f86-47ae-900c-55360b316bd7', '100007', '华丽的分割线', '华丽的分割线', '#', '_self', 'ApplicationMenu', '', '', 'MenuSplitLine', 0, '', '10002', 1, '', '应用连接器管理\\我的应用连接器\\华丽的分割线', '2013-05-27 10:11:22', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('476aab11-62c6-45f9-b04c-1e80eafb522b', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100060', '落实情况向导', '落实情况向导', 'shared?path=wizards/implementation-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '向导组件管理\\落实情况向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('47ce9004-380d-40cc-866a-1c8438bc35c0', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122004', '审批管理', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '工作联系审批管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('484b2793-8648-4f52-a57e-b7fbbeb8393f', '00000000-0000-0000-0000-000000000001', 'e9018fab-d7e8-418f-b524-6e1b08b5e31b', '100018', '应用菜单管理', '应用菜单管理', '/applications/application-menu-list.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0003', 1, '', '开始菜单应用管理应用菜单管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('48599e4d-73e3-48e4-af64-56ea2f40ee9e', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122006', '已审联系单', '已审联系单', '/apps/pages/workrelation/workrelationapproved-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '工作联系已审联系单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('48f601f2-0087-4f3d-a140-b5985b6eca5e', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000000', '100023', '类别设置16', '类别设置123', '/apps/pages/dengbao/dengbao-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0006', 0, 'fffty', '登报管理\\类别设置16', '2018-08-22 23:53:21', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('491d3f00-e255-40d9-86cb-cd8184cf8029', '2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000000', '120002', '新建日程安排', '新建日程安排', '/apps/pages/schedule/schedule-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '日程安排新建日程安排', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4d8e44da-d5da-40a9-8071-18c8ebb1e310', '6c2a314d-0c2d-455f-b64a-d098b9d49c42', '00000000-0000-0000-0000-000000000000', '100028', '历史记录', '历史记录', '/membership/accumulatepoint-history/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '累计积分管理历史记录', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4da29ddc-5d55-a1b1-4b8e-69c11db2c59c', '4dbdff30-42e1-484c-a27a-bee1c2eba073', '00000000-0000-0000-0000-000000000000', '', 'sssss', 'sssss', 'ssssss', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', 'eee', 1, '', '测试系统123\\sssss', '2018-08-23 23:33:16', '2018-08-23 10:14:00');
INSERT INTO `application_menu` VALUES ('4dd7567a-c04d-405c-8e42-e3097ba4f995', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104017', '会议室设置', '会议室设置', '/apps/pages/meeting/meeting-room-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10017', 1, '', '会议管理会议室设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4e259325-4395-4531-9382-7ded0a980daf', 'd3398a7d-c369-4988-a953-09a88cd73d4d', '00000000-0000-0000-0000-000000000000', '117001', '最新书籍', '最新书籍', '/apps/pages/wenku/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '论坛管理最新书籍', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4e3549e5-5c71-4a85-a5d9-c0768d6c5a79', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100026', '应用数据同步', '应用数据同步', '/applications/application-package/sync', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0010', 0, '', '应用管理\\应用数据同步', '2015-05-18 00:26:36', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('4f977519-a109-4946-bb7d-947a3266af17', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122008', '数据字典', '数据字典', '/apps/pages/workrelation/companynavigation-list.aspx?type=d', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '工作联系数据字典', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('51f0f993-158f-422a-8bfb-1a36651b2741', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100016', '参与调查', '参与调查', '/apps/pages/survey/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '问卷调查参与调查', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('54223abd-0313-4152-a85b-5d639ecb0687', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100003', '缓存设置', '缓存设置', '/apps/pages/platform/cache-setting.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '基础配置管理权限参数', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('54ce21b1-55c3-414a-b283-4447b47187b8', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100044', '待审新闻', '待审新闻', '/news/news-workflow-request/pending', '_self', 'ApplicationMenu', 'fa fa-minus-square-o', '', 'MenuItem', 0, '', '10006', 1, '', '新闻管理\\待审新闻', '2015-05-27 20:20:52', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('557ec604-8924-4ffb-bc72-9717091e49c9', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100021', '待审调查', '待审调查', '/apps/pages/survey/survey-workflow-request.aspx?type=pending', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '问卷调查待审调查', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('58d286c2-ff72-4156-8c1a-3ea09ad67916', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100035', '流程管理', '流程管理', '/apps/pages/buildstandard/buildstandard-workflow-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10011', 1, '', '建造标准管理流程管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('596d28b7-d80b-4d66-b75d-0b6fcf1333e7', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100035', '我的收藏夹', '我的收藏夹', '/tasks/my-favorite-list', '_self', 'ApplicationMenu', 'fa fa-star', '', 'MenuItem', 0, '', '10002', 1, '', '任务管理\\我的收藏夹', '2015-05-27 17:17:17', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('597880d2-da33-4d57-8066-1ea535cf240a', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100050', '已归档合同', '已归档合同', '/apps/pages/contract/contract-archived-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '合同管理已归档合同', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('5a95888f-042b-4c40-b479-d0b954a9bd33', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100047', '类别设置', '类别设置', '/news/news-category/list', '_self', 'ApplicationMenu', 'fa fa-list', '', 'MenuItem', 0, '', '10010', 1, '', '新闻管理\\类别设置', '2015-05-29 23:31:11', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('5b71f3da-38f4-4403-a386-81ee6b449408', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000000', '108001', '门户管理', '门户管理', '/apps/pages/navigation/', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '导航管理门户管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('5f4740af-dc69-44a8-9cbe-a8687d683117', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100056', '类别设置', '类别设置', '/apps/pages/contract/contract-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '物业合同管理类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('5ff56bce-b872-47af-8855-c39a6d14176a', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100006', '职位序列管理', '职位序列管理', '/apps/pages/hr/job-family-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1009', 1, '', '人力资源管理职位序列管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('60c05454-c7b1-4dc4-9a12-5fba572de40b', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100053', '已审费用报销', '已审费用报销', '/cost/cost-workflow-request/approved', '_self', 'ApplicationMenu', 'fa fa-check-square-o', '', 'MenuItem', 0, '', '10006', 1, '', '费用报销管理\\已审费用报销', '2015-05-30 01:03:18', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('61f70d0b-03ff-4a11-a5a8-161e6b4a87b1', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100024', '新闻管理', '新闻管理', '/news/', '_self', 'TopMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '顶部菜单新闻管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('62e80c52-eeb1-4b7c-b034-eb1c15390d18', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100059', '应用选择向导', '应用选择向导', 'shared?path=wizards/application-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '向导组件管理\\应用选择向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('643d3aa1-8da8-4c06-94b0-16b435b1d7e6', '400ad6db-088c-4d02-8721-e4ab119216bc', '00000000-0000-0000-0000-000000000000', '129010', '参数设置', '参数设置', '/apps/pages/workattendance/workattendance-application-setting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 1, '', '请假出差参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('647d48b0-28ed-45d7-bfc4-3dae5511240c', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100033', '分割线1', '分割线1', '11111', '_self', 'StartMenu', '', '', 'MenuSplitLine', 0, '', '0003', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('647ff95d-ca4c-4170-8b1c-646e187fb0c5', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100023', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10008', 1, '', '问卷调查基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('65038e0d-7e6d-4ea5-bfe0-edca1d2fef47', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100025', '流水号设置', '流水号设置', '/apps/pages/platform/digital-number-setting.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '基础配置管理流水号设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6608e914-0bfa-4529-bf7b-e82ccbbc9d31', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100024', '应用功能授权', '应用功能授权', '/applications/application-feature/setting', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0004', 1, '', '应用管理\\应用功能授权', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('66413cc0-5d3d-4fdb-9ed8-64000fb959ac', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100031', '参数设置', '参数设置', '/tasks/task-setting/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '任务管理\\参数设置', '2015-07-23 14:28:24', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('668f0c28-56e8-4053-82e8-1a72dac83dbb', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100002', '标准通用角色管理', '标准通用角色管理', '/membership/standard-general-role/mapping', '_self', 'ApplicationMenu', 'fa fa-cube', '', 'MenuItem', 0, '', '10009', 1, '', '人员及权限管理\\标准通用角色管理', '2015-06-01 21:36:56', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6793ac2f-0a3f-43ab-95e2-db7da38943ad', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100001', '问题跟踪', '问题跟踪', '/bugzilla/default.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0006', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('698d5409-4416-4751-a43e-2f5ef68b3b42', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100028', '我的建造标准', '我的建造标准', '/apps/pages/buildstandard/my-buildstandard-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '建造标准管理我的建造标准', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('69af5198-7daa-4c53-8e66-07ccaab6e813', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100016', '环境参数', '环境参数', '/apps/pages/platform/system-config-editor.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '基础配置管理环境参数', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6a9db232-3463-4665-9150-794b770eba7a', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100031', '已审建造标准', '已审建造标准', '/apps/pages/buildstandard/buildstandard-workflow-request.aspx?type=approved', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '建造标准管理已审建造标准', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6b3d99b8-2dc4-4bf0-87b0-0ec616af4aad', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104005', '会议纪要查询', '会议纪要查询', '/apps/pages/meeting/meeting-memo-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '会议管理会议纪要查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6cc68e97-37fb-427b-9775-6db4fe294337', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100038', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10007', 1, '', '表单管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6d182f8b-0f86-47ae-900c-55360b316bd7', 'c786db9a-b8f8-4162-8be7-57d828c004ff', '00000000-0000-0000-0000-000000000000', '113001', '我的应用连接器', '我的应用连接器', '/apps/pages/connect/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '应用连接器管理\\我的应用连接器', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6e49cf3b-09f9-4247-949f-d54efbdce42a', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100048', '我的帖子', '我的帖子', '/forum/my-forum-thread/list', '_self', 'ApplicationMenu', 'fa fa-folder', '', 'MenuItem', 0, '', '10005', 1, '', '论坛管理\\我的帖子', '2015-07-14 23:13:50', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6ef3aff9-4140-41ab-a245-ae9f16398292', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122002', '提交新的联系单', '提交新的联系单', '/apps/pages/workrelation/workrelationdetail-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '工作联系提交新的联系单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('6fb6fd6d-1bbd-4234-8ded-11ece9ac27e3', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '129008', '参数设置', '参数设置', '/cost/cost-setting/list', '_self', 'ApplicationMenu', 'fa fa-cog', '', 'MenuItem', 0, '', '10008', 1, '', '费用报销管理\\参数设置', '2015-05-30 01:00:36', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('70396b95-ccc8-4fad-b996-c13b4c17cfd7', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100050', '定时动作管理', '定时动作管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10007', 1, '', '基础配置管理定时动作管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7078785e-f88f-4ed3-8cce-9d3f3fb23def', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000000', '117002', '用户帮助手册', '用户帮助手册', '/wiki/book/system_user_help.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '维基百科系统帮助手册', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('708d442d-5dd7-47b4-b2b3-6e9c6af583b9', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100018', '调查查询', '调查查询', '/apps/pages/survey/survey-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '问卷调查调查查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('708f732a-f0ea-4595-a709-dc36a0ce4ed8', '400ad6db-088c-4d02-8721-e4ab119216bc', '00000000-0000-0000-0000-000000000000', '100063', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10009', 1, '', '请假出差基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('70e100ac-0ee7-4856-bb8e-e5152ca8c978', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100061', '流程审批向导', '流程审批向导', 'shared?path=wizards/workflow-transact-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '向导组件管理\\流程审批向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('716b42aa-193a-4eb0-88e4-5a8be132b01f', 'd3398a7d-c369-4988-a953-09a88cd73d4d', '00000000-0000-0000-0000-000000000000', '100020', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10003', 1, '', '文库管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('718bf041-2ac7-472c-832a-0480eaad556b', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000000', '100025', '登报费用查询', '登报费用查询', '/apps/pages/dengbao/dengbao-report.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0003', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('729d6266-13d9-464d-bdf9-66217aadfd93', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100031', '应用参数分组', '应用参数分组', '/applications/application-setting-group/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0004', 1, '', '应用管理\\应用参数分组', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7358c3d9-443d-4524-938f-1d885d689927', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100031', '论坛管理', '论坛管理', '/forum/', '_self', 'TopMenu', '', '', 'MenuItem', 0, '', '0003', 1, '', '顶部菜单\\论坛管理', '2015-06-01 22:01:13', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('759486de-b7dd-4645-9526-65ba1ae56f4f', 'c786db9a-b8f8-4162-8be7-57d828c004ff', '6d182f8b-0f86-47ae-900c-55360b316bd7', '100005', '应用概述', '应用概述', '/apps/pages/connect/connect-overview.aspx?id=${id}', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '应用连接器管理\\我的应用连接器\\应用概述', '2013-05-27 10:11:13', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7610f126-4531-4387-a9dc-031ac45f071e', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100059', '应用参数分组向导', '应用参数分组向导', 'shared?path=wizards/application-setting-group-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '向导组件管理\\应用参数分组向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('76a426bb-70bc-4a77-9336-be4969c4d587', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100002', '帐号委托', '帐号委托', '/membership/account-grant/list', '_self', 'ApplicationMenu', 'fa fa-share-alt', '', 'MenuItem', 0, '', '10002', 1, '', '人员及权限管理\\帐号委托', '2015-06-01 21:34:17', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('780bdfe2-78ff-48d1-9722-7742640542be', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100024', '类别设置', '类别设置', '/apps/pages/survey/survey-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '问卷调查类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7958f45a-86e3-1951-838e-52afa52332c5', '4dbdff30-42e1-484c-a27a-bee1c2eba073', '00000000-0000-0000-0000-000000000000', '', 'ddddddddd', 'ddddddddd123fff', 'ddddddddddddd', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '', 0, '', '测试系统123\\ddddddddd', '2018-08-23 23:33:26', '2018-08-23 10:38:50');
INSERT INTO `application_menu` VALUES ('7971003d-71e7-4392-80db-8b9526e4508a', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100015', '费用报销', '费用报销', '/cost/', '_self', 'TopMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '顶部菜单\\费用报销', '2015-03-20 09:39:59', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7971e5d3-77e2-48d7-bf5d-98d554dd4239', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100010', '授权信息查询', '授权信息查询', '/membership/authorization-object-view/terminal', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 0, '', '人员及权限管理\\授权信息查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('799cdfad-37ed-46d0-aa06-64dae4cc5418', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100026', '最新建造标准', '建造标准管理', '/apps/pages/buildstandard/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '建造标准管理最新建造标准', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7a2081ed-2f0f-4763-85ee-e895ae3aa83a', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100035', '已审批表单', '已审批表单', '/apps/pages/custom-forms/custom-form-instance-workflow-request.aspx?type=approved', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '表单管理已审批表单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7ba041bc-180d-4f65-89ef-59833da719f0', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000000', '100031', '我的积分管理', '我的积分管理', '/apps/pages/account/accumulatepoint.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1005', 1, '', '我的控制台我的积分管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7bc792e9-8849-4f10-a94c-ea90721e1265', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100027', '流程模板管理', '流程模板管理', '/apps/pages/workflowplus/workflow-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '工作流管理流程模板管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7bca3d0f-3636-41da-94a0-b8037fc47965', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '106002', '定时任务查询', '定时任务查询', '/tasks/task-waiting/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '任务管理定时任务查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7e68b9b2-3fa1-4ebd-83b2-4c6dad9ac716', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '111005', '参数设置', '参数设置', '/apps/pages/bugzilla/bugzilla-setting-list.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-gear.gif', '', 'MenuItem', 0, '', '10005', 1, '', '问题跟踪\\参数设置', '2013-04-17 09:53:06', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('7f9750e6-d4ee-4465-b2e5-49a6aa6d1fcb', 'c786db9a-b8f8-4162-8be7-57d828c004ff', '6d182f8b-0f86-47ae-900c-55360b316bd7', '100006', '应用属性', '应用属性', '/apps/pages/connect/connect-form.aspx?id=${id}', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-default.gif', '', 'MenuItem', 0, '', '10003', 1, '', '应用连接器管理\\我的应用连接器\\应用属性', '2013-05-27 10:11:28', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('80bf0e34-4136-465d-91aa-a99d4b9b5377', '400ad6db-088c-4d02-8721-e4ab119216bc', '00000000-0000-0000-0000-000000000000', '100047', '流程设置', '流程设置', '/apps/pages/workattendance/workattendance-workflow-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10011', 1, '', '请假出差流程设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('80c487a0-062f-4541-92ee-9aff27965f88', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100019', '调查结果', '调查结果', '/apps/pages/survey/survey-result.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '问卷调查调查结果', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('81ec8da5-44f3-438a-9934-b390a1eeaa29', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000000', '100029', '我的联系方式', '我的联系方式', '/apps/pages/account/contact.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-contacts.gif', '', 'MenuItem', 0, '', '1004', 1, '', '我的控制台\\我的联系方式', '2013-04-17 10:57:21', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('823879bc-9f25-4987-8be9-917281c9fe00', 'af426531-ecf5-46fb-828c-27624dad8d23', '00000000-0000-0000-0000-000000000000', '100074', '假日设置', '假日设置', '/apps/pages/checkin/checkin-holiday.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10011', 1, '', '假日设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('832ca9c3-8ead-4feb-a963-807c4fd2bf5a', '00000000-0000-0000-0000-000000000001', 'c48cb0ed-7b2c-489e-bc30-ef516516d883', '100007', '开始菜单1.1.1.1', '开始菜单1.1.1.1', '#', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('84135cd5-bdce-4b8c-ab61-f80129dffc1b', '6c2a314d-0c2d-455f-b64a-d098b9d49c42', '00000000-0000-0000-0000-000000000000', '100026', '在线充值', '在线充值', '/apps/pages/membership/accumulatepoint-payment.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '累计积分管理在线充值', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('854c21be-7f92-4557-a568-6fe504e8c8e4', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100020', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10008', 1, '', '论坛管理\\基础设置', '2013-01-14 00:15:22', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('86b8eb05-0974-4fde-8c63-518951d75013', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100036', '我的表单', '我的表单', '/apps/pages/custom-forms/custom-form-instance-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '表单管理我的表单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('88831251-9a3b-476b-9b3e-ffdf506d5187', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100036', '历史记录查询', '历史记录查询', '/tasks/task-history/list', '_self', 'ApplicationMenu', 'fa fa-history', '', 'MenuItem', 0, '', '10003', 1, '', '任务管理\\历史记录查询', '2015-05-27 17:17:56', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('88ba266d-e395-40fb-a7c6-8d4693fa0600', '2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000000', '120001', '日程安排查询', '日程安排查询', '/apps/pages/schedule/', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '日程安排日程安排查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('8ac822de-5f35-49ef-8cb5-7b1f116fd0b1', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '106003', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '任务管理\\基础设置', '2015-07-23 14:28:04', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('8b29437c-23f0-4e09-836f-6b2a69aff859', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100008', '职位管理', '职位管理', '/apps/pages/hr/job-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1011', 1, '', '人力资源管理职位管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('8bee7c42-f035-42f7-9710-79b23347ca2f', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100009', '岗位管理', '岗位管理', '/apps/pages/hr/assigned-job-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1006', 1, '', '人力资源管理岗位管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('8e4f30b0-6b2e-4df4-9d58-e4759d2bc51c', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100049', '新建费用报销', '新建费用报销', '/cost/form', '_blank', 'ApplicationMenu', 'fa fa-pencil-square-o', '', 'MenuItem', 0, '', '10002', 1, '', '费用报销管理\\新建费用报销', '2015-05-30 01:02:25', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('8f07314b-701c-4517-b68a-d63362f1db70', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122009', '审批类别设置', '审批类别设置', '/apps/pages/workrelation/companynavigation-list.aspx?type=t', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '工作联系审批类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('8fb44c39-57b0-4750-9561-1061d84789f0', 'd3398a7d-c369-4988-a953-09a88cd73d4d', '00000000-0000-0000-0000-000000000000', '100017', '质量红宝书', '质量红宝书', '/wenku/book/longfor_quality_manual.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '文库管理质量红宝书', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('94386ed1-3428-4e17-b98b-755cf9bb6cac', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122003', '我的联系单', '我的联系单', '/apps/pages/workrelation/myworkrelation-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '工作联系我的联系单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('94c483bf-672f-4e8b-9465-a7c2663e4bfd', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100024', '流程实例查询', '流程实例查询', '/apps/pages/workflowplus/workflow-instance-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '工作流管理流程实例查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('9614127c-2fc5-4af7-a288-114feeb7697d', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100045', '已审新闻', '已审新闻', '/news/news-workflow-request/approved', '_self', 'ApplicationMenu', 'fa fa-check-square-o', '', 'MenuItem', 0, '', '10007', 1, '', '新闻管理\\已审新闻', '2015-05-27 20:19:56', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('96860216-e090-4021-8d4a-6ea7701351f5', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100028', '流程超时节点查询', '流程超时节点查询', '/apps/pages/workflowplus/workflow-timelimt-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '工作流管理流程超时节点查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('97218a99-6465-499c-906b-c6ef9ff9a7ab', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100022', '环境变量', '环境变量', '/apps/pages/platform/system-environment.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '基础配置管理环境变量', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('9876b6dd-1c30-44a6-9d56-9ed3a7c98ac2', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100052', '待审费用报销', '待审费用报销', '/cost/cost-workflow-request/pending', '_self', 'ApplicationMenu', 'fa fa-minus-square-o', '', 'MenuItem', 0, '', '10005', 1, '', '费用报销管理\\待审费用报销', '2015-05-30 01:02:59', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('989a371d-e822-4105-83d5-2a54166d25cc', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100040', '提交新闻', '提交新闻', '/news/form', '_blank', 'ApplicationMenu', 'fa fa-pencil-square-o', '', 'MenuItem', 0, '', '10002', 1, '', '新闻管理\\提交新闻', '2015-05-27 17:34:19', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('9a585983-72f2-4c82-8e1d-bb274a826382', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100001', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10012', 1, '', '人员及权限管理\\基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('9bef1391-5f40-4510-9e83-207d59f94e86', '00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000000', '100001', '项目查询', '项目查询', '/apps/pages/projects/default.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-triangle.gif', '', 'MenuItem', 0, '', '0001', 1, '', '项目管理\\项目查询', '2013-04-16 22:48:35', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('9bf15f95-2dab-4e9c-b9bf-aac7e4e4e394', 'af426531-ecf5-46fb-828c-27624dad8d23', '00000000-0000-0000-0000-000000000000', '100073', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10007', 1, '', '基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('9d1cb823-d63a-4fd3-aae3-45aecb28c18d', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100003', '人员及权限管理', '人员及权限管理', '/membership/', '_self', 'TopMenu', '', '', 'MenuItem', 0, '', '0002', 0, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a027e3f9-c21f-4008-bd83-e4ce5b424415', '400ad6db-088c-4d02-8721-e4ab119216bc', '00000000-0000-0000-0000-000000000000', '129005', '年假查询', '年假查询', '/apps/pages/workattendance/workattendance-annualreport.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '请假出差年假查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a2ad37d8-13a6-4e3d-a61c-b0c4e8c238b2', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122001', '联系单查询', '联系单查询', '/apps/pages/workrelation/workrelation-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '工作联系联系单查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a2b88441-9989-4c79-bc35-b18e6cff202a', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100025', '应用参数设置', '应用参数设置', '/applications/application-setting/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0005', 1, '', '应用管理\\应用参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a2e5b549-27ea-4a48-b767-bd7660633e4a', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100021', '版块设置', '版块设置', '/forum/forum-category/list', '_self', 'ApplicationMenu', 'fa fa-th-large', '', 'MenuItem', 0, '', '10010', 1, '', '论坛管理\\版块设置', '2015-07-14 23:03:40', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a53070b3-cf7c-4daa-914d-d379fdce69b6', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000000', '117006', '历史记录', '历史记录', '/apps/pages/wiki/wiki-history.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '维基百科历史记录', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a636aa8b-0bfb-49eb-a563-64f2df322e3f', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100006', '分组类别设置', '分组类别设置', '/membership/group-tree/list', '_self', 'ApplicationMenu', 'fa fa-slack', '', 'MenuItem', 0, '', '10014', 1, '', '人员及权限管理\\分组类别设置', '2015-06-01 21:35:49', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('a9ad8486-5463-4571-ab68-3f71229f1619', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000000', '108003', '快捷方式分组管理', '快捷方式分组管理', '/apps/pages/navigation/navigation-portal-shortcutgroup-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '导航管理快捷方式分组管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('aa8c5791-a157-4b5c-81e3-13a4f43a85f4', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000000', '100029', '修改头像信息', '修改头像信息', '/apps/pages/account/avatar.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-user.gif', '', 'MenuItem', 0, '', '1003', 1, '', '我的控制台\\修改头像信息', '2013-04-17 11:07:06', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ab4043d4-8833-4436-ba59-88edcb3244a3', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100034', '审批类别', '审批类别', '/apps/pages/buildstandard/buildstandard-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '建造标准管理审批类别', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ab5259fe-db96-43cb-9844-824531e645ed', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000000', '108004', '快捷方式管理', '快捷方式管理', '/apps/pages/navigation/navigation-portal-shortcut-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '导航管理快捷方式管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('abb39418-770a-4f74-8154-1f024a303016', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104013', '待审会议室借用', '待审会议室借用', '/apps/pages/meeting/meeting-room-lock-request.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10013', 1, '', '会议管理待审会议室借用', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('abc4c75b-3de7-4041-9638-aa4eb0dd18ab', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100055', '公司选择向导', '公司选择向导', 'shared?path=wizards/corporation-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '向导组件管理\\公司选择向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ac1cf11d-dc7d-4bef-9b35-0183996a4d95', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '109009', '参数设置', '参数设置', '/news/news-setting/list', '_self', 'ApplicationMenu', 'fa fa-cog', '', 'MenuItem', 0, '', '10009', 1, '', '新闻管理\\参数设置', '2015-07-14 23:04:50', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ae410c64-3f6c-47f2-a920-4dd8f1ee20ae', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100055', '类别设置', '类别设置', '/cost/cost-category/list', '_self', 'ApplicationMenu', 'fa fa-list', '', 'MenuItem', 0, '', '10008', 1, '', '费用报销管理\\类别设置', '2015-05-30 01:04:30', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('aec1a811-5fcd-4692-9db6-d248e13ee893', '5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000000', '100027', '实体元数据管理', '实体元数据管理', '/apps/pages/entities/entity-metadata-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '实体数据管理实体元数据管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('afd18acb-39ad-44af-97ae-ebf367b8a11a', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100020', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10002', 1, '', '数据安全报表基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b07277c1-bc9e-4563-8638-65a86e5ff9b4', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100030', '用户信息模拟器', '用户信息模拟器', '/apps/pages/workflowplus/workflow-actor-view.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '工作流管理用户信息模拟器', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b0b5ab86-2368-412b-add0-e0a9774a2960', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100034', '我的申请单', '我的申请单', '/apps/pages/hr/my-custom-form-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1002', 1, '', '人力资源管理我的申请单', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b200c790-44c4-4d4a-bb0f-7cbf4a127943', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100011', '人员管理', '人员管理', '/apps/pages/hr/member-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1007', 1, '', '人力资源管理人员管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b3fe7fc4-f043-4386-8bcf-39c2f782debd', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104011', '待审会议安排', '待审会议安排', '/apps/pages/meeting/meeting-workflow-request.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10011', 1, '', '会议管理待审会议安排', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b40bbc68-240f-406c-a302-475a0b76e719', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100047', '流程设置', '流程设置', '/news/news-workflow-template/list', '_self', 'ApplicationMenu', 'fa fa-gears', '', 'MenuItem', 0, '', '10011', 1, '', '新闻管理\\流程设置', '2015-05-29 23:30:35', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b4b12566-542a-4c53-a035-2e1d93784544', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100009', '标准角色管理', '标准角色管理', '/membership/standard-role/list', '_self', 'ApplicationMenu', 'fa fa-users', '', 'MenuItem', 0, '', '10008', 1, '', '人员及权限管理\\标准角色管理', '2015-06-01 21:27:16', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b4c25a88-1f36-4bbf-9a57-c5b63ebf8026', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122007', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10007', 1, '', '工作联系基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b5cd9637-0dcf-4f52-a5f0-8d96ba41d8e6', '5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000000', '100029', '参数设置', '参数设置', '/apps/pages/entities/entity-setting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '实体数据管理参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b6c84d7b-d60f-45ce-ae0c-39e2ba85ae95', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000000', '100030', '帐户基本信息', '查看当前用户帐号的基本信息', '/apps/pages/account/default.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-identity.gif', '', 'MenuItem', 0, '', '1001', 1, '', '我的控制台\\帐户基本信息', '2013-04-17 10:33:52', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b6f2ab48-7f7c-4eeb-8c78-6960d3b2ed83', '43f8d051-79f4-4d7f-9007-c8d83236cdcd', '00000000-0000-0000-0000-000000000000', '103001', '内容管理', '内容管理', '/customizes/customize-content/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '自定义页面管理\\内容管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b7e9ccf1-7273-4e79-a498-a1e9a257f7e4', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104009', '统计查询', '统计查询', '/apps/pages/meeting/meeting-query.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '会议管理统计查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b80eeb23-8eaa-48ce-ad96-4698720b8393', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100017', '精华区', '精华区', '/forum/forum-essential-thread/list', '_self', 'ApplicationMenu', 'fa fa-heartbeat', '', 'MenuItem', 0, '', '10003', 1, '', '论坛管理\\精华区', '2015-07-14 23:09:16', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('b82df2c5-c412-4687-b9ff-1598569fef21', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000000', '108002', '顶部菜单管理', '顶部菜单管理', '/apps/pages/navigation/navigation-portal-topmenu-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '导航管理顶部菜单管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('baa6f81e-fd05-4d73-9f56-ad36482bda00', '5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000000', '100028', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10003', 1, '', '实体数据管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('bb259313-8578-48fc-b3de-9b0fb8b10344', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '111003', '我的问题列表', '我的问题列表', '/apps/pages/bugzilla/my-bugzilla-list.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-marked.gif', '', 'MenuItem', 0, '', '10003', 1, '', '问题跟踪\\我的问题列表', '2013-04-16 22:35:38', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('bc3ffd8a-fd58-4f7e-b236-4643eb4ba59a', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100001', '人事自助', '人事自助', '/apps/pages/hr/', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1001', 1, '', '人力资源管理员工通讯录', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('bc76d4bd-ce7f-4266-8af1-884ffa3db308', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000000', '100032', '修改帐号密码', '修改帐号密码', '/apps/pages/account/change-password.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-password.gif', '', 'MenuItem', 0, '', '1002', 1, '', '我的控制台\\修改帐号密码', '2013-04-17 11:03:58', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c01ecd76-b8c5-4b35-9854-7756c73a99af', 'c786db9a-b8f8-4162-8be7-57d828c004ff', '00000000-0000-0000-0000-000000000000', '113002', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '应用连接器管理\\基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c043e9fd-8553-4d39-977d-041f56e8dc40', 'd3398a7d-c369-4988-a953-09a88cd73d4d', '00000000-0000-0000-0000-000000000000', '100021', '书籍设置', '书籍设置', '/apps/pages/wenku/wenku-book-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '文库管理书籍设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c07ffc66-9db8-4e19-8c60-30e220fa5cb2', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100051', '我的合同', '我的合同', '/apps/pages/contract/my-contract-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c0ded12e-0875-47a7-9fa8-2fc070a000c3', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '106001', '我的待办事宜', '我的待办事宜', '/tasks/', '_self', 'ApplicationMenu', 'fa fa-tasks', '', 'MenuItem', 0, '', '10001', 1, '', '任务管理\\我的待办事宜', '2015-05-27 17:16:24', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c14ec544-9fd1-4a48-81e2-0a54f1c532fb', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100042', '我的收藏夹', '我的收藏夹', '/news/my-favorite/list', '_self', 'ApplicationMenu', 'fa fa-star', '', 'MenuItem', 0, '', '10004', 1, '', '新闻管理\\我的收藏夹', '2015-05-27 17:26:16', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c1674a15-6ec0-4295-ae9a-504461f5d869', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100029', '应用菜单管理', '应用菜单管理', '/applications/application-menu/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0006', 1, '', '应用管理\\应用菜单管理', '2013-05-27 10:09:16', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c25befd6-307a-420b-a9c8-3ddbaf4f4e28', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100001', '帐号管理', '帐号管理', '/membership/account/list', '_self', 'ApplicationMenu', 'fa fa-user', '', 'MenuItem', 0, '', '10001', 1, '', '人员及权限管理\\帐号管理', '2015-06-01 21:25:05', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c265fcb7-c229-4780-942c-61fb5f0d4666', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100007', '群组管理', '群组管理', '/membership/group/list', '_self', 'ApplicationMenu', 'fa fa-users', '', 'MenuItem', 0, '', '10003', 1, '', '人员及权限管理\\群组管理', '2015-06-01 21:25:21', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c2fd7fc5-b1ea-4b67-9a76-3475067ee2f6', '43f8d051-79f4-4d7f-9007-c8d83236cdcd', '00000000-0000-0000-0000-000000000000', '103001', '页面管理', '页面管理', '/customizes/customize-page/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '自定义页面管理\\页面管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c404b674-23ea-4f36-90f1-5f890da2f32e', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104010', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10010', 1, '', '会议管理审批管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c48cb0ed-7b2c-489e-bc30-ef516516d883', '00000000-0000-0000-0000-000000000001', '304de3ea-59b5-42d8-ab11-6c9fa4ee7ada', '100006', '开始菜单1.1.1', '开始菜单1.1.1', '#', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c4ca9e9d-46f8-4927-b83c-0a89572b8e37', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '106004', '类别设置', '类别设置', '/tasks/task-category/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '任务管理\\类别设置', '2015-07-23 14:28:37', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c5463c17-0cf5-4b3e-bc70-1d2cb1afa5b2', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100029', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10006', 1, '', '工作流管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c6bc5396-c8f7-4264-8d3f-e5b57210b5e7', '43f8d051-79f4-4d7f-9007-c8d83236cdcd', '00000000-0000-0000-0000-000000000000', '103001', '布局管理', '布局管理', '/customizes/customize-layout/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '自定义页面管理\\布局管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c6f42b69-79d2-44a1-a53e-b8c509ad522e', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '111002', '提交新的问题', '提交新的问题', '/apps/pages/bugzilla/bugzilla-form.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-new.gif', '', 'MenuItem', 0, '', '10002', 1, '', '问题跟踪\\提交新的问题', '2013-04-16 22:29:48', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c73e9ae2-1552-41a8-a52c-545798ac12db', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000000', '100021', '我的登报信息', '我的登报信息', '/apps/pages/dengbao/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c788ef16-affc-44dd-aef9-116b25b13458', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100054', '人员选择向导', '人员选择向导', 'shared?path=wizards/contacts-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '向导组件管理\\人员选择向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c7b5f8c1-b0ea-40c5-ba06-627f8e5548d3', '9288a727-9aed-4340-a6f4-cf1bcc6954d9', '00000000-0000-0000-0000-000000000000', '100010', '组织管理', '组织管理', '/apps/pages/hr/organization-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1005', 1, '', '人力资源管理组织管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c897aeb3-4025-4f3b-98d4-b6e729cafdd5', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100017', '新建调查', '新建调查', '/apps/pages/survey/survey-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '问卷调查新建调查', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('c924c32d-6e6d-4f6c-b113-4efbc2056ce6', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100027', '新建建造标准', '新建建造标准', '/apps/pages/buildstandard/buildstandard-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '建造标准管理新建建造标准', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('cb18b48b-812e-4da7-8ae2-fe6a4609b3c2', '5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000000', '100026', '实体架构管理', '实体架构管理', '/apps/pages/entities/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '实体数据管理实体架构管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('cbb2bebc-42b1-4333-b8c5-5b35cb86b77e', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104015', '参数设置', '参数设置', '/apps/pages/meeting/meeting-application-setting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10015', 1, '', '会议管理参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('cc544156-98a4-4415-a819-3c94a8cb7450', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100031', '表单查询', '表单查询', '/apps/pages/custom-forms/custom-form-instance-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '表单管理表单查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ce45c92d-a5ec-4619-ab90-3e90d27e8a2e', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104004', '我安排的会议', '我安排的会议', '/apps/pages/meeting/my-meeting.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10004', 1, '', '会议管理我安排的会议', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('cf1a5c9b-f904-4de7-b526-6e4c2f0a5540', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100049', '我关注的用户', '我关注的用户', '/forum/my-follow/list', '_self', 'ApplicationMenu', 'fa fa-users', '', 'MenuItem', 0, '', '10006', 1, '', '论坛管理\\我关注的用户', '2015-07-14 23:02:22', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('cf6ea4a6-e5e7-4712-9844-64f98650faae', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100055', '流程设置', '流程设置', '/apps/pages/contract/contract-workflow-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 1, '', '物业合同管理流程设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d07f0192-e793-4f40-8514-c57931d20a2a', '2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000000', '120004', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '日程安排基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d1975e95-ee86-4385-ae07-180509715d7e', '43f8d051-79f4-4d7f-9007-c8d83236cdcd', '00000000-0000-0000-0000-000000000000', '103001', '部件管理', '部件管理', '/customizes/customize-widget/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '自定义页面管理\\部件管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d1a80f10-7c22-4784-971c-80bfe5286545', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100015', '定时动作模板管理', '定时动作模板管理', '/apps/pages/platform/timing-action-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '基础配置管理定时动作模板管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d439d21e-6923-4d8f-a09a-21b7daf92e84', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100056', '流程设置', '流程设置', '/cost/cost-workflow-template/list', '_self', 'ApplicationMenu', 'fa fa-gears', '', 'MenuItem', 0, '', '10009', 1, '', '费用报销管理\\流程设置', '2015-05-30 01:03:54', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d5712cb5-fcb0-4c54-b4fc-4170968c268f', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100037', '任务查询', '任务查询', '/tasks/task/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '任务管理任务查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d671c620-8b97-4868-a5a9-ec2de77cf386', 'c786db9a-b8f8-4162-8be7-57d828c004ff', '00000000-0000-0000-0000-000000000000', '113003', '参数设置', '参数设置', '/apps/pages/connect/connect-setting-list.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-gear.gif', '', 'MenuItem', 0, '', '10005', 1, '', '应用连接器管理\\参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('d821aad0-bb5e-42d6-bb0b-1493309b3fb1', 'e3bf3960-101e-4377-bf1f-c2a6979a8fbc', '00000000-0000-0000-0000-000000000000', '100022', '已审调查', '已审调查', '/apps/pages/survey/survey-workflow-request.aspx?type=approved', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '问卷调查已审调查', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('dad458ab-6c76-4140-9643-45da556a60a5', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100049', '提交新的合同', '提交新的合同', '/apps/pages/contract/contract-form.aspx', '_blank', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '物业合同管理提交新的合同', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('daf8baf5-abaf-4ade-8267-895edc257fc3', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '111006', '类别设置', '类别设置', '/apps/pages/bugzilla/bugzilla-category-list.aspx', '_self', 'ApplicationMenu', '/resources/images/icon/icon-menu-category.gif', '', 'MenuItem', 0, '', '10006', 1, '', '问题跟踪\\类别设置', '2013-04-17 10:19:31', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('df65fd00-303a-4363-9a7d-1401e8051c56', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100062', '流程模板选择向导', '流程模板选择向导', 'shared?path=wizards/workflow-template-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '向导组件管理\\流程模板选择向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e1431f08-ae90-42f3-8fd7-874ec3374b51', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '104007', '参数设置', '参数设置', '/apps/pages/workflowplus/workflow-application-setting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10007', 1, '', '工作流管理参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e1923997-acf9-4737-bbf0-7755028278bd', '6c2a314d-0c2d-455f-b64a-d098b9d49c42', '00000000-0000-0000-0000-000000000000', '100013', '历史记录查询', '历史记录查询', '/apps/pages/membership/accumulatepoint-history-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '', 1, '', '累计积分管理历史记录查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e1f1c99c-5e24-4f12-95f5-a0aba0f18dd6', '6c2a314d-0c2d-455f-b64a-d098b9d49c42', '00000000-0000-0000-0000-000000000000', '100012', '货币符号管理', '货币符号管理', '/apps/pages/membership/accumulatepoint-currency-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '', 1, '', '累计积分管理货币符号管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e1f2c38c-1a4e-4bcb-9273-8c1da5667649', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100032', '表单模板管理', '表单模板管理', '/apps/pages/custom-forms/custom-form-template-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '表单管理表单模板管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e28aad9c-5abc-41d3-9d73-b1a1eeca2dc8', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100053', '待审合同', '待审合同', '/apps/pages/contract/contract-workflow-request.aspx?type=pending', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e363fb32-1c10-4fbf-a5ed-8507277a6e2d', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100057', '收藏设置向导', '收藏设置向导', 'shared?path=wizards/favorite-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '向导组件管理\\收藏设置向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e3e0bf3b-7444-4030-ba8c-ad7ec76ed781', 'df45ddaa-b9f4-49ef-bb32-07c8f0a83b90', '00000000-0000-0000-0000-000000000000', '100033', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '表单管理审批管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e490455c-698e-4adb-9dae-e23daba262f9', 'af426531-ecf5-46fb-828c-27624dad8d23', '00000000-0000-0000-0000-000000000000', '100093', '例外设置', '例外设置', '/apps/pages/checkin/checkin-uncheck.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 1, '', '例外设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e6a76fc0-25e8-4de0-b5c3-d641cac3a10a', '6c2a314d-0c2d-455f-b64a-d098b9d49c42', '00000000-0000-0000-0000-000000000000', '100027', '货币符号设置', '货币符号设置', '/apps/pages/membership/accumulatepoint-currency-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0003', 1, '', '累计积分管理货币符号设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e6c99aeb-a8b7-41af-83b7-1924af2b7d6b', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100004', '人员及权限管理', '人员及权限管理', '/membership/', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0002', 1, '', '开始菜单\\人员及权限管理', '2013-04-14 22:53:33', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e858064e-fad5-4f2b-b349-13f38824d55f', '9df3ee14-3718-42f4-9da1-226c55e8cc53', '00000000-0000-0000-0000-000000000000', '100030', '待审建造标准', '待审建造标准', '/apps/pages/buildstandard/buildstandard-workflow-request.aspx?type=pending', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '建造标准管理待审建造标准', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e9018fab-d7e8-418f-b524-6e1b08b5e31b', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100016', '应用管理', '应用管理', '/apps/pages/applications/default.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '开始菜单应用管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e92886ac-0cc9-44ae-84d4-c68d180e29a8', '00000000-0000-0000-0000-000000000001', 'e9018fab-d7e8-418f-b524-6e1b08b5e31b', '100019', '应用配置管理', '应用配置管理', '/apps/pages/applications/default.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0001', 1, '', '开始菜单应用管理应用配置管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('e9f1a4c4-9046-4e09-9f70-d290e895e4de', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100023', '权限参数设置', '权限参数设置', '/apps/pages/platform/authority-setting.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '基础配置管理权限参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ebc37c4f-b3b3-4ce5-8fb1-7135f4b49801', 'af426531-ecf5-46fb-828c-27624dad8d23', '00000000-0000-0000-0000-000000000000', '100089', '班次设置', '班次设置', '/apps/pages/checkin/checkin-work-schedule.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '班次设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('eef1a4d6-8e05-4e37-ac14-c2e4f4c0b7e5', '2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000000', '120003', '我的日程安排', '我的日程安排', '/apps/pages/schedule/my-schedule-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '日程安排我的日程安排', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ef80f31e-c7a0-4e9f-b5de-2d43d0dcd94e', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100043', '审批管理', '审批管理', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10005', 1, '', '新闻管理\\审批管理', '2015-05-29 23:43:43', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f049c235-dd78-4d9c-a5d1-b822eee4121d', '2128b7cf-a2e8-4e64-a09d-19d755860df3', '00000000-0000-0000-0000-000000000000', '120005', '参数设置', '参数设置', '/apps/pages/schedule/schedule-application-setting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '日程安排参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f05687e4-2d22-4196-9547-d8c93ed34335', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000000', '122010', '联系单监控', '联系单监控', '/apps/pages/workrelation/workrelationtrace.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10010', 1, '', '工作联系联系单监控', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f0b5c06b-4e6c-4682-9a53-0683d183891e', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000000', '117001', '最近更新', '最近更新', '/apps/pages/wiki/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '维基百科最近更新', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f15cffe7-fe9d-45d1-9f7a-a1c299c877bc', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100026', '应用事件管理', '应用事件管理', '/applications/application-event/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0009', 1, '', '应用管理\\应用事件管理', '2015-05-30 11:56:43', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f18e46e6-b69c-4eab-b077-357ed672c857', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100017', '参数设置', '参数设置', '/apps/pages/symantec/reports/dlp-report-setting.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10003', 1, '', '数据安全报表参数设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f196b9fb-b6ec-456a-985b-f33583a98d3e', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100004', '角色管理', '角色管理', '/membership/role/list', '_self', 'ApplicationMenu', 'fa fa-users', '', 'MenuItem', 0, '', '10005', 1, '', '人员及权限管理\\角色管理', '2015-06-01 21:25:13', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f271b73d-9759-4cfe-90cd-013fd3f8f0b5', '184e5c3a-6147-4e18-b3ed-83b87e3f0084', '00000000-0000-0000-0000-000000000000', '100033', '我的个性设置', '我的个性设置', '/apps/pages/account/settings.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '1006', 1, '', '我的控制台我的个性设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f313d641-35ed-43d3-bbe9-ac944859967e', 'ff2bff6b-922c-4eda-b5ec-f2553577666f', '00000000-0000-0000-0000-000000000000', '100014', '定时动作实例管理', '定时动作实例管理', '/apps/pages/platform/timing-action-instance-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '基础配置管理定时动作实例管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f34ee60e-4129-4471-84e8-79e5303793e6', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000000', '104002', '会议安排查询', '会议安排查询', '/apps/pages/meeting/meeting-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10002', 1, '', '会议管理会议安排查询', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f44b4c2b-3a6b-46f3-9b7e-5d57f3a4a288', 'af426531-ecf5-46fb-828c-27624dad8d23', '00000000-0000-0000-0000-000000000000', '100090', '考勤设置', '考勤设置', '/apps/pages/checkin/checkin-basic.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10009', 1, '', '考勤设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f45d572b-cd6d-49c9-891e-21ba210e60f3', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100048', '费用报销查询', '费用报销查询', '/cost/', '_self', 'ApplicationMenu', 'fa fa-search', '', 'MenuItem', 0, '', '10001', 1, '', '费用报销管理\\费用报销查询', '2015-05-30 01:01:45', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f49132c6-072d-4342-bdd6-9c1b31480c99', 'bbdc4b78-50d1-465e-b930-888e6b00e745', '00000000-0000-0000-0000-000000000000', '100059', '推荐向导', '推荐向导', 'shared?path=wizards/recommend-wizard', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10006', 1, '', '向导组件管理\\推荐向导', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f68179f2-fef2-4dd9-b4a6-28b996c8db17', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100013', '充值管理', '充值管理', '/apps/pages/membership/accumulatepoint-payment.aspx', '_self', 'StartMenu', '', '', 'MenuItem', 0, '', '0005', 1, '', '开始菜单充值管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f7855362-89c4-4a3f-aba7-6c9a3d18e87b', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100013', '参数设置', '参数设置', '/membership/setting/list', '_self', 'ApplicationMenu', 'fa fa-cog', '', 'MenuItem', 0, '', '10013', 1, '', '人员及权限管理\\参数设置', '2015-06-01 21:29:34', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f7f37b06-829c-4cdb-9aef-c51c343f6344', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100060', '我的论坛信息', '我的论坛信息', '/forum/identity', '_self', 'ApplicationMenu', 'fa fa-user', '', 'MenuItem', 0, '', '10007', 1, '', '论坛管理\\我的论坛信息', '2015-07-14 23:03:06', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f89a2e75-8baa-40e7-bf0a-3df9b4acd0de', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100026', '应用数据管理', '应用数据管理', '/applications/application-package/list', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '0011', 0, '', '应用管理\\应用数据管理', '2013-05-27 10:09:47', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('f9a00749-d8af-487a-9395-d6ae13310943', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100041', '我的文档', '我的文档', '/news/my-news/list', '_self', 'ApplicationMenu', 'fa fa-folder', '', 'MenuItem', 0, '', '10003', 1, '', '新闻管理\\我的文档', '2015-05-27 17:28:07', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('fabd014a-d578-4cad-9400-f1c1ae6a15ed', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000000', '117004', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '10004', 1, '', '维基百科基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('fb4c1c96-76be-4f92-ba9c-ae1393b1e7a4', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000000', '117005', '书籍设置', '书籍设置', '/apps/pages/wiki/wiki-book-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10005', 1, '', '维基百科书籍设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('fda541c9-4687-47e1-ae79-2bac4d2bcec2', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000000', '100022', '基础设置', '基础设置', '#', '_self', 'ApplicationMenu', '', '', 'MenuGroup', 0, '', '0004', 1, '', '登报管理基础设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('fe95706d-b866-4946-9982-fc5509b8e18a', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100026', '流程类别设置', '流程类别设置', '/apps/pages/workflowplus/workflow-category-list.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10008', 1, '', '工作流管理流程类别设置', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_menu` VALUES ('ff2c5ced-4cdf-4f5a-bcbf-618b3aa92723', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '103001', '流程统计', '流程统计', '/apps/pages/workflowplus/default.aspx', '_self', 'ApplicationMenu', '', '', 'MenuItem', 0, '', '10001', 1, '', '工作流管理流程模板管理', '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for application_menu_scope
-- ----------------------------
DROP TABLE IF EXISTS `application_menu_scope`;
CREATE TABLE `application_menu_scope`  (
  `entity_id` varchar(36)  NOT NULL,
  `entity_class_name` varchar(400)  NULL DEFAULT NULL,
  `authority_id` varchar(36)  NOT NULL,
  `authorization_object_type` varchar(20)  NOT NULL,
  `authorization_object_id` varchar(36)  NOT NULL,
  PRIMARY KEY (`entity_id`, `authority_id`, `authorization_object_type`, `authorization_object_id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for application_option
-- ----------------------------
DROP TABLE IF EXISTS `application_option`;
CREATE TABLE `application_option`  (
  `application_id` varchar(36)  NULL DEFAULT NULL,
  `name` varchar(50)  NOT NULL,
  `label` varchar(50)  NULL DEFAULT NULL,
  `description` text  NULL,
  `value` text  NULL,
  `is_internal` bit(1) NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_option
-- ----------------------------
INSERT INTO `application_option` VALUES ('', 'ApplicationClientId', '', '内置客户端的标识', '8ffdec2a3a2c406482800aebd86de17c', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'ApplicationClientSecret', '', '内置客户端的 Secret', '19af94', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'ApplicationHomePage', '', '应用默认主页', '/', b'1', '1001', 0, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000001', 'Apps.Administrators', '', '内置超级管理员帐号,多个人以逗号隔开', 'admin,root', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000001', 'Apps.HiddenTopMenu', NULL, '', 'On', b'0', NULL, 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('1632a994-d54b-42ce-8d82-713ec61828e7', 'AttachmentStorage.DistributedFileStorageMode', '', '分布式文件模式 : 可选的值 1.On(默认) 2.Off ', 'Off', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000006', 'Bugzilla.SendMailAlert', '', '发送邮件提醒', 'On', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'DatabaseSettings.Database', '', '数据库的名称', 'test', b'1', '', 0, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'DatabaseSettings.DataSource', '', '数据库的服务器地址', 'localhost', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'DatabaseSettings.IBatisSqlMapFilePathRoot', '', '数据库的SQL配置文件地址', '${ApplicationPathRoot}config\\', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'DatabaseSettings.LoginName', '', '数据库的默认登录名', 'root', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'DatabaseSettings.Password', '', '数据库的默认密码', '12345@abc', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'Domain', '', '应用所属的域信息', 'x3platform.com', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'ExchangeClient.Domain', '', 'Exchange Server 域', 'x3platform.com', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'ExchangeClient.EWSUrl', '', 'Exchange Server OWA 地址', 'https://mail.x3platform.com/ews/exchange.asmx', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'ExchangeClient.OWAUrl', '', 'Exchange Server OWA 地址', 'https://mail.x3platform.com/owa/', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'HostName', '', '应用服务器名称', 'http://x10.x3platform.com', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('a4ac7590-8700-419d-aae7-6a0f646838d3', 'j', NULL, NULL, 'jijiijij', b'0', 'ij', 1, 'jjji', '2015-03-14 23:35:46', '2015-03-14 23:22:09');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000100', 'Membership.AutoBindingJobByAssignedJob', '', '根据岗位数据自动绑定职位信息 : 可选的值 1.On 2.Off(默认)', 'On', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000100', 'Membership.AutoBindingJobGradeByAssignedJob', '', '根据岗位数据自动绑定职级信息 : 可选的值 1.On 2.Off(默认)', 'On', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000100', 'Membership.AutoBindingOrganizationByPrimaryKey', '', '根据字段数据自动绑定组织信息 : 可选的值 1.None(默认) 2.RoleId 3.AssignedJobId', 'AssignedJobId', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000100', 'Membership.DefaultPassword', '', '系统登录的默认初始密码', 'x3platform$2010$p@ssw0rd', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000100', 'Membership.MockAuthenticationPassword', '', '系统登录的模拟验证密码', '1', b'0', '1001', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'NetworkCredentialDomain', '', '网络认证所属的域信息(NetworkCredential)', 'x3platform', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000009', 'Projects.SendMailAlert', '', '发送邮件提醒', 'On', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'SystemName', '', '系统名称', '3号站台(开发环境)9999', b'1', '', 1, '', '2014-05-08 17:06:16', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('1f3e1dec-c411-44e0-82cf-ae4447a36769', 'Tasks.PrefixTargetUrl', '', '任务的默认前缀地址', '${HostName}', b'0', '1001', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('', 'WebmasterEmail', '', '系统管理员邮箱', 'admin@x3platform.com', b'1', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000003', 'WorkflowPlus.AssemblyNames', '', '工作流程序集列表', 'X3Platform.Abstractions.dll,X3Platform.Support.dll,X3Platform.Membership.dll,X3Platform.WorkflowPlus.dll', b'0', '123', 0, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_option` VALUES ('00000000-0000-0000-0000-000000000003', 'WorkflowPlus.WorkflowIdentifyType', '', '工作流标识类型', 'Guid', b'0', '', 1, NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for application_scope
-- ----------------------------
DROP TABLE IF EXISTS `application_scope`;
CREATE TABLE `application_scope`  (
  `entity_id` varchar(36)  NOT NULL,
  `entity_class_name` varchar(400)  NULL DEFAULT NULL,
  `authority_id` varchar(36)  NOT NULL,
  `authorization_object_type` varchar(20)  NOT NULL,
  `authorization_object_id` varchar(36)  NOT NULL,
  PRIMARY KEY (`entity_id`, `authority_id`, `authorization_object_type`, `authorization_object_id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for application_setting
-- ----------------------------
DROP TABLE IF EXISTS `application_setting`;
CREATE TABLE `application_setting`  (
  `id` varchar(36)  NOT NULL,
  `application_id` varchar(36)  NULL DEFAULT NULL,
  `application_setting_group_id` varchar(36)  NULL DEFAULT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `text` varchar(200)  NULL DEFAULT NULL,
  `value` varchar(100)  NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_setting
-- ----------------------------
INSERT INTO `application_setting` VALUES ('00f9bd39-549c-4ecb-8b3a-5e77624e7367', '00000000-0000-0000-0000-000000000001', '1ed555f3-824c-465d-82a9-c3457295103e', '100020', '顶部菜单', 'TopMenu', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('044a5d98-0dd6-4ee8-ae93-128aa882825e', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100042', '小于等于', '=<', '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('047f8feb-31b4-47d2-93e7-c71b9d6ac24a', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100050', '集成测试', '3', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('059e65d1-2925-48c6-8c62-ac18bb6f74be', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '17bc9996-bfe7-4526-9e22-deb04102a0a0', '100060', '一般', '★★★', '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('07606a0c-dcf3-45c7-b58d-ca52b76b8cf8', '00000000-0000-0000-0000-000000000100', '3fdff176-308c-4e95-a4fd-86a2a41288d6', '100010', 'CRM帐号', '2', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('07effeab-f7d8-40d6-92df-be59e9e39279', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '地区模块负责人', '71', '10006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('087bec6a-1d0b-4ac4-9d1f-7b632e3e740e', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100047', '项目立项', '0', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('09fbbf57-d88b-4915-bbb0-07431cb7f73b', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '17bc9996-bfe7-4526-9e22-deb04102a0a0', '100059', '说的过去', '★★', '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('0a402464-c200-405b-b131-29409700c4e5', '00000000-0000-0000-0000-000000000006', 'a4c35065-0eff-4bb1-b222-d7d697c05760', '100042', '处理中', '1', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('0b0c8169-4300-4274-a528-82ea7d1b2d1a', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '集团中心负责人', '80', '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('10d123b1-61a0-4ab3-8684-89d42f7f5257', '00000000-0000-0000-0000-000000000006', 'aaae4e87-d84f-4116-9816-6cc0d01ed0e3', '100040', '紧急', '2', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('12d2a33c-263e-455f-98e7-e31f50de9075', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '9ce6bdcf-66e3-4d80-abbd-678181de1563', '100013', '签到管理', '签到管理', '100003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('150046bb-af78-425e-8478-6e2131a16356', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', 'd4e9b338-0b20-4136-b7d4-d86d456f9f06', '100054', '非常重要', '4', '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('158fcbef-334c-4d28-af05-1ec0b2e36e02', '00000000-0000-0000-0000-000000000003', '264155d4-7508-42a9-9bd3-14013ed7a592', '100046', '或者', 'OR', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('1791faeb-2e93-45dd-9e92-8e1e38e701e5', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100007', '地区商业公司', '11', '1004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('18a626a4-f923-4c16-a948-56eb8c3c990b', '00000000-0000-0000-0000-000000000003', 'cf693a04-81af-4b1e-9a3b-32a2226a97ed', '100056', '((((', '((((', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('19f11f46-3bcd-4285-8080-029d0019a05a', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100048', '分析设计', '1', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('1c258c18-c900-4d25-a689-28a3fa861d33', '00000000-0000-0000-0000-000000000001', '8d8572dd-2cf5-4670-bae2-f566836a3859', '100037', '菜单分割线', 'MenuSplitLine', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('1c29617b-5eac-4cd0-bc6e-df99edde4b41', '00000000-0000-0000-0000-000000000001', '8d8572dd-2cf5-4670-bae2-f566836a3859', '100035', '菜单项', 'MenuItem', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('1e5ddc3b-fa2f-4c30-853b-5dc34699d9f4', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '1043554a-c6bf-4cf9-870e-1e0019809589', '100056', '已发布', 'Published', '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('20320c90-3aef-4ea6-8b9e-673fab7360c2', '00000000-0000-0000-0000-000000000100', '2c0c0e12-cc86-47b8-959e-22368bd6a053', '100003', '标准角色', '4', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('2081bb31-1f97-4e0f-918a-bfa104e38a15', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100083', '12', '123', '', 1, '', '2015-05-17 10:46:19', '2015-04-11 17:04:18');
INSERT INTO `application_setting` VALUES ('20fc0681-9537-4955-a9ec-080205cc0865', '00000000-0000-0000-0000-000000000001', '1ed555f3-824c-465d-82a9-c3457295103e', '100019', '开始菜单', 'StartMenu', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('2285a0c8-725e-4d68-8e9b-7c905533606e', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '17bc9996-bfe7-4526-9e22-deb04102a0a0', '100058', '比较差', '★', '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('2368f5b5-ffd9-4810-94d9-f9bf9a1311d5', '00000000-0000-0000-0000-000000000100', 'acf612c6-bfb7-4527-ba48-4e574fd5321a', '100011', '角色群组', '2', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('25067f28-68aa-4236-afc9-601faa4c468d', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100012', '地区物业项目团队', '22', '1007', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('2a04bc23-d7bf-4521-b391-08600ad6c624', '00000000-0000-0000-0000-000000000100', '3fdff176-308c-4e95-a4fd-86a2a41288d6', '100009', '邮箱帐号', '1', '1002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('2d0de233-2f2f-491f-a4f7-e014a982ab86', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', 'd4e9b338-0b20-4136-b7d4-d86d456f9f06', '100056', '普通', '2', '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('2f6dc032-a361-4abd-bd15-1eaf4ddf759f', '00000000-0000-0000-0000-000000000001', '1ed555f3-824c-465d-82a9-c3457295103e', '100021', '应用菜单', 'ApplicationMenu', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('30b92880-0e34-4b8a-850a-b6c432aef665', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100038', '大于', '>', '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('323084af-08b3-40a9-8f91-b35d36017ef9', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '1043554a-c6bf-4cf9-870e-1e0019809589', '100055', '被驳回', 'Rejected', '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('38d5eb1b-9d46-4dcd-a11d-f2a9fd52371b', '00000000-0000-0000-0000-000000000001', '8d8572dd-2cf5-4670-bae2-f566836a3859', '100036', '菜单分组', 'MenuGroup', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('3a87c4fd-e5ed-4d50-a432-93d272423b5e', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '1043554a-c6bf-4cf9-870e-1e0019809589', '100057', '审批中', 'Approving', '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4221f231-b143-4dd5-a4ea-6049af0e84ed', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100004', '集团总部', '0', '1001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('42221065-051d-49c9-b209-c15a898af78d', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '普通员工', '0', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4409806f-777c-4c3f-b81f-2397c179663a', '00000000-0000-0000-0000-000000000006', 'a4c35065-0eff-4bb1-b222-d7d697c05760', '100043', '已解决', '2', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('44d67c41-3688-4fa5-84b5-c28a872aa10f', '00000000-0000-0000-0000-000000000006', 'aaae4e87-d84f-4116-9816-6cc0d01ed0e3', '100039', '重要', '1', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4524a770-4a7b-400b-b619-1e54dda61b6a', '00000000-0000-0000-0000-000000000100', 'bfff78de-0c0a-4b3c-96e0-396516d901d9', '100008', '公司', '0', '1001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('47ba2347-b0ca-41f0-bb46-b65bfc11c9de', '00000000-0000-0000-0000-000000000100', 'bfff78de-0c0a-4b3c-96e0-396516d901d9', '100009', '部门', '1', '1002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('47bef00a-4d5b-4a48-812d-9328302afc4d', '00000000-0000-0000-0000-000000000100', '2c0c0e12-cc86-47b8-959e-22368bd6a053', '100005', '组织', '1', '1002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('482cfe30-5db1-42e8-bb16-b1f77d649ce8', '00000000-0000-0000-0000-000000000003', 'cf693a04-81af-4b1e-9a3b-32a2226a97ed', '100055', '(((((', '(((((', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4a0fa9e8-47b1-4f2c-9bd2-c2dc0ce91cf8', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '9ce6bdcf-66e3-4d80-abbd-678181de1563', '100011', '工作流管理', '工作流管理', '100003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4bcde716-14ab-42b2-81a5-6388be8b489a', '00000000-0000-0000-0000-000000000100', '32050159-680d-45de-a52c-94d0766e4d0c', '100011', '剪切待办', '2', '1007', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4cbe1a4f-d8db-4082-8afb-24540ef3f40b', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'a539ee35-66ce-478c-b624-8b1bd874e093', '100005', '消息', '2', '100002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4cbe96ef-ad39-4d0b-8bbd-a4f163b48158', '00000000-0000-0000-0000-000000000100', '2c0c0e12-cc86-47b8-959e-22368bd6a053', '100003', '群组', '3', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('4dbc5227-10b9-41be-a931-7bfa265a2936', '00000000-0000-0000-0000-000000000003', '961f697e-6f2a-47a1-9a3c-76fca606aafa', '100048', ');)', ');)', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('532c5365-5412-4be5-a788-9a035e57a1a1', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '9ce6bdcf-66e3-4d80-abbd-678181de1563', '100012', '会议管理', '会议管理', '100003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('55649db7-ba97-4c58-836c-c3ed339b2f9f', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100043', '结束', 'EndWith', '10007', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('57085b8d-89c8-40f4-8653-dbd5b1c79fed', '00000000-0000-0000-0000-000000000003', 'cf693a04-81af-4b1e-9a3b-32a2226a97ed', '100052', '(', '(', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('57e659b5-36ba-4ad7-b08d-1d8bf4ce0e37', '5079360f-ceed-496b-8a5e-a121c5ad7461', '2de08811-1638-41b1-bba4-4a90d21db43d', '100061', '管理员可见', '8', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5a17f7d4-7cb6-42d0-af1f-01a87d35fac3', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100017', '事件同步时间间隔', '6', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5a94453b-629d-4089-ac92-4d07d69fd06a', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100044', '大于等于', '>=', '10005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5da7e653-ce36-4641-af6a-d8ffcfcbe56d', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '17bc9996-bfe7-4526-9e22-deb04102a0a0', '100061', '很好', '★★★★', '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5e4e71a0-74cc-474b-b9e8-5c105150e3c0', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'fb4d8848-75ae-4370-92e3-f1a60b00a721', '100007', '未完成', '0', '100001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5ed0e16b-0721-4c19-9ea3-1e23b0b93e50', '00000000-0000-0000-0000-000000000006', 'aaae4e87-d84f-4116-9816-6cc0d01ed0e3', '100038', '一般', '0', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5f492ddf-ed3b-4501-b30a-e4ddf6470590', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '1043554a-c6bf-4cf9-870e-1e0019809589', '100054', '全部', 'All', '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('5f5539df-3a79-4b0b-89fd-bd047e57f6df', '5079360f-ceed-496b-8a5e-a121c5ad7461', '2de08811-1638-41b1-bba4-4a90d21db43d', '100060', '普通用户可见', '1', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('620479c7-b1ad-4588-9465-df9557dc8aaa', '00000000-0000-0000-0000-000000000100', 'bfff78de-0c0a-4b3c-96e0-396516d901d9', '100011', '项目团队', '3', '1004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('62c4d76b-7a03-48e5-a1f0-5976ea141a67', '00000000-0000-0000-0000-000000000100', '32050159-680d-45de-a52c-94d0766e4d0c', '100011', '复制待办', '4', '1008', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('6492c4a1-1161-4f19-a13d-df337901d90e', '00000000-0000-0000-0000-000000000100', 'bfff78de-0c0a-4b3c-96e0-396516d901d9', '100020', '门店', '4', '1004', 1, 'ijij<div>', '2015-03-15 13:36:27', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('64f29b09-1856-469f-99c3-a4b129685ef5', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100003', '地区地产项目团队', '2', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('658fd921-6048-4b81-9e83-5c334b27b05d', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a13d73b8-d879-4e35-b61d-498236b14914', '100014', '60%', '中', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('6638fff7-7967-433c-926c-1151af78b8b4', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '17bc9996-bfe7-4526-9e22-deb04102a0a0', '100062', '非常好', '★★★★★', '10005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('664dae7c-07a8-4e10-8aad-b83b4d0549e9', '00000000-0000-0000-0000-000000000001', '3eabe2b3-4500-408a-9947-bae22b21678d', '100029', '应用窗口(自定义)', '_app', '0005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('67aa8475-135f-471e-9673-5f99824e9fd3', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'a539ee35-66ce-478c-b624-8b1bd874e093', '100006', '通知', '4', '100003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('6a5c8459-ced9-4f1f-8c43-d396a5c3d356', '00000000-0000-0000-0000-000000000001', '248df0c8-911a-467e-902c-7fbcd0543dd2', '100065', '应用管理员', '16', '10005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('6d05637e-d471-4e97-806e-ad8d1b7b8765', '00000000-0000-0000-0000-000000000100', '185604f1-0500-4582-85b3-d3b22478d0ca', '100013', '自身及子对象', '1', '1005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('7133dfce-ed80-4fde-8f7a-e89339b9cee4', '845bd93f-817d-4115-b40d-0b94a8686e53', '84bb9ab7-23ef-4162-9298-a8f44a56ec6b', '100011', '秘密', '3', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('713eb05f-bda6-4909-a8f1-3c3b47813e6d', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100006', '地区物业公司', '21', '1006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('76ea5f29-197b-4b0e-9ed0-e20ac57ad5bb', '00000000-0000-0000-0000-000000000100', 'acf612c6-bfb7-4527-ba48-4e574fd5321a', '100009', '普通群组', '0', '1001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('7936f186-0463-474f-949d-26cfea0c6871', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100013', '地区商业项目团队', '12', '1005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('7a0288db-6a19-4ed0-8f1d-36691b55551e', '00000000-0000-0000-0000-000000000006', 'a4c35065-0eff-4bb1-b222-d7d697c05760', '100046', '已关闭', '5', '0006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('7aa0ab32-f98b-4426-b242-31a2580ff5bd', '00000000-0000-0000-0000-000000000001', 'f0bc993e-28d7-4f87-9196-10f6f9c407cf', '100033', '列表', '0', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('7d6bfdff-bf2f-4bd8-8141-2a655d7f86db', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100039', '小于', '<', '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('7eeae85e-e92f-420a-8589-c7dbf2851d93', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100084', '1234', '2233', '', 1, '', '2015-04-11 17:04:46', '2015-04-11 17:04:46');
INSERT INTO `application_setting` VALUES ('7fba498f-1fbd-499f-bff4-6a733c198685', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'fb4d8848-75ae-4370-92e3-f1a60b00a721', '100008', '已完成', '1', '100002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8057ec1a-24c1-491e-b646-55fc9a8dc398', '00000000-0000-0000-0000-000000000100', '3fdff176-308c-4e95-a4fd-86a2a41288d6', '100011', 'RTX帐号', '3', '1004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('82dcb29d-e771-49a2-ad09-dfbf161c7abc', '00000000-0000-0000-0000-000000000003', '264155d4-7508-42a9-9bd3-14013ed7a592', '100045', '并且', 'AND', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('851685a7-b5bc-475d-9107-83d5ca1459e2', '00000000-0000-0000-0000-000000000006', 'a4c35065-0eff-4bb1-b222-d7d697c05760', '100044', '延后处理', '3', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8762a287-09e2-48b5-9f70-b9c03778c298', '00000000-0000-0000-0000-000000000100', '2c0c0e12-cc86-47b8-959e-22368bd6a053', '100003', '角色', '2', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8a806f6d-1db2-4317-b0d9-0952ad7a7c77', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100063', '查看页面禁止拷贝', '1', '', 1, '0 表示允许拷贝 1 表示禁止拷贝', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8ae1c603-6423-4bb3-986b-2bc32b362e76', '00000000-0000-0000-0000-000000000003', 'cf693a04-81af-4b1e-9a3b-32a2226a97ed', '100053', '((', '((', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8afa032e-78e8-4577-bc38-726cbae0e45d', '00000000-0000-0000-0000-000000000003', '961f697e-6f2a-47a1-9a3c-76fca606aafa', '100047', ');', ');', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8c7a476d-83fc-4a91-af09-e122e8911c6e', '4c65fa99-82e3-4c49-a97a-07a6344c13f3', '00000000-0000-0000-0000-000000000000', '100062', '记录匿名标识', '1', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8d4e736b-4b61-4c4d-9a77-fbcb4ada4e64', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100052', '项目中止', '5', '0006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8d7a04d7-d172-4550-a1b5-227f7e49c497', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a8831199-1720-49c4-84cb-cc81783e00c0', '100006', '网络', 'Network', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8ee70700-f701-4478-9136-18fae2aef369', '00000000-0000-0000-0000-000000000100', 'c187f46b-0369-4c7b-9b19-6cfb3b43865c', '100011', '不授权', '0', '1001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('8f7ae7bd-9738-47b3-99c4-6296e38cda1a', '845bd93f-817d-4115-b40d-0b94a8686e53', '84bb9ab7-23ef-4162-9298-a8f44a56ec6b', '100010', '机密', '6', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('904b25cf-7c93-433c-b76b-4a95fe8c5fce', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100051', '后期维护', '4', '0005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('917d57c3-9741-4e6b-959a-f1d3d3004a7f', '00000000-0000-0000-0000-000000000001', '1ed555f3-824c-465d-82a9-c3457295103e', '100022', '快捷菜单', 'ShortcutMenu', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('972b261f-798e-4c0d-906a-21be58730118', '00000000-0000-0000-0000-000000000001', '3eabe2b3-4500-408a-9947-bae22b21678d', '100030', '父级窗口', '_parent', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('98b0a3a4-ffa0-4103-bb7e-2327a1a5987e', '00000000-0000-0000-0000-000000000100', '94081a9e-f8ab-490a-a1f0-a6e247864936', '100005', '地区地产公司', '1', '1002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('9aecc90e-d3a3-49de-b07e-5beb006a42e8', '00000000-0000-0000-0000-000000000001', '3eabe2b3-4500-408a-9947-bae22b21678d', '100031', '顶层窗口', '_top', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('9b72d7a2-0320-4476-bf88-fbb4e9041e0a', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'fb4d8848-75ae-4370-92e3-f1a60b00a721', '100009', '待处理', '2', '100003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('9de92f8b-9b3a-4c7f-9298-892ebe2825ef', '00000000-0000-0000-0000-000000000001', '248df0c8-911a-467e-902c-7fbcd0543dd2', '100067', '应用审查员', '8', '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('9eb9070d-d683-4251-9bf1-1f9b11acf542', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100053', '项目结束', '6', '0007', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('9f3ed888-450a-4a3a-9747-78af1d10a666', '00000000-0000-0000-0000-000000000003', '961f697e-6f2a-47a1-9a3c-76fca606aafa', '100049', ');))', ');))', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('a21baa24-b58b-407c-a40b-573f7d50e5ee', '5079360f-ceed-496b-8a5e-a121c5ad7461', 'a70b4f9e-5096-43e2-b5bc-ef3caee748f8', '100058', '字符串', '字符串', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('a95b0d51-0f52-4309-afed-279aec3032a9', '00000000-0000-0000-0000-000000000003', 'cf693a04-81af-4b1e-9a3b-32a2226a97ed', '100054', '(((', '(((', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('aa416783-8040-4f25-acdd-c6ddb8fbfa00', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'fb4d8848-75ae-4370-92e3-f1a60b00a721', '100010', '待会签', '3', '100003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b0542b34-e8cf-416b-bcfa-fd5a7a3681e4', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a8831199-1720-49c4-84cb-cc81783e00c0', '100008', '全部', 'All', '0099', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b0ab6eef-59c1-4775-b6b8-7fc01c95aa3e', '00000000-0000-0000-0000-000000000006', 'a4c35065-0eff-4bb1-b222-d7d697c05760', '100045', '无法修复', '4', '0005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b11a4953-e05b-4894-84aa-f96ea860e5bf', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a8831199-1720-49c4-84cb-cc81783e00c0', '100007', '端点', 'Endpoint', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b2d80555-429d-45a8-8403-b0b0d6a7a35d', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a13d73b8-d879-4e35-b61d-498236b14914', '100016', '2%', '信息', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b31c1652-3674-423f-acd3-dc05dda79747', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '集团职能负责人', '90', '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b6b50923-5487-4774-84c0-57e84093e16a', '845bd93f-817d-4115-b40d-0b94a8686e53', '84bb9ab7-23ef-4162-9298-a8f44a56ec6b', '100012', '普通', '0', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('b94dddef-7c7d-47f6-8617-aff9f044e9f5', '00000000-0000-0000-0000-000000000001', '3eabe2b3-4500-408a-9947-bae22b21678d', '100028', '新窗口', '_blank', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('bc7784dc-61b5-4e66-8ced-390fff8e5e91', '00000000-0000-0000-0000-000000000100', 'bfff78de-0c0a-4b3c-96e0-396516d901d9', '100010', '团队', '2', '1003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('bcf2f9c8-24d5-449b-b92c-e297fa3f0c51', '00000000-0000-0000-0000-000000000100', 'c187f46b-0369-4c7b-9b19-6cfb3b43865c', '100011', '授权', '1', '1002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('bde169bc-5ab0-4e41-98c8-02fcc840bd93', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '8a7ba734-c25f-4ed5-809d-315393a4f310', '100019', '截止时间的值', '18', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('c1a45310-e008-4d07-ab7b-16f43fa435cc', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '地区职能负责人', '81', '10005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('c42b64ad-2066-4923-a351-463e14b5cafd', '00000000-0000-0000-0000-000000000001', 'f3d46dc4-d66d-4df8-8615-70b0b4c6973f', '100025', '读取和写入权限', '2', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('c69af5e8-87d0-4593-95a1-3befce2220fd', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100041', '开始', 'StartWith', '10006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('c7523cb4-bada-41c1-b398-a5c309b6049d', '00000000-0000-0000-0000-000000000001', 'f3d46dc4-d66d-4df8-8615-70b0b4c6973f', '100026', '完全控制权限', '3', '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('c7e496b5-1d4d-4176-b02d-ed4cba25ac2e', '00000000-0000-0000-0000-000000000001', '3eabe2b3-4500-408a-9947-bae22b21678d', '100032', '主窗口(自定义)', '_mai', '0006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('ca9049ee-de08-45fb-a3d8-b9eed69effae', '00000000-0000-0000-0000-000000000003', '961f697e-6f2a-47a1-9a3c-76fca606aafa', '100051', ');)))', ');)))', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('cae53e51-e27e-46fc-b95c-2f3de05786c8', '00000000-0000-0000-0000-000000000100', '3fdff176-308c-4e95-a4fd-86a2a41288d6', '100008', '普通帐号', '0', '1001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('cbb00056-e2ef-4cb9-81d7-b1ac57e6a784', '00000000-0000-0000-0000-000000000001', 'f0bc993e-28d7-4f87-9196-10f6f9c407cf', '100034', '树型', '1', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('cbf5a5cb-c5fc-4948-a856-c3167cddb2e8', '00000000-0000-0000-0000-000000000100', '2c0c0e12-cc86-47b8-959e-22368bd6a053', '100004', '人员', '0', '1001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('cc0b449e-30c8-4160-b274-c8df3d0badc4', '00000000-0000-0000-0000-000000000100', 'acf612c6-bfb7-4527-ba48-4e574fd5321a', '100010', '组织单位', '1', '1002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('d43c7643-6f8b-4cc0-9059-dc6a994b5f96', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '地区公司负责人', '91', '10004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('d4a3343c-987a-4204-998f-a7b2d60076e0', '00000000-0000-0000-0000-000000000003', 'ec4f9c29-3119-46b7-ba2b-55d09f96b001', '100040', '等于', '=', '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('d4b01b1a-f016-499e-8744-e016f9a00828', '00000000-0000-0000-0000-000000000003', '961f697e-6f2a-47a1-9a3c-76fca606aafa', '100050', ');))))', ');))))', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('d7f5b91f-65ba-4399-b5eb-74f0ad002085', '00000000-0000-0000-0000-000000000001', 'f3d46dc4-d66d-4df8-8615-70b0b4c6973f', '100023', '无权限', '0', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('d95cf6f7-befa-43dc-b09f-0984cc7a04fd', '00000000-0000-0000-0000-000000000001', '248df0c8-911a-467e-902c-7fbcd0543dd2', '100068', '应用可访问成员', '4', '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('daa323ed-74e0-4660-a887-1917dba138bf', '00000000-0000-0000-0000-000000000001', '3eabe2b3-4500-408a-9947-bae22b21678d', '100027', '本身窗口', '_self', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('dcacc469-8cd2-4580-8fad-0827de3cb201', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', 'd4e9b338-0b20-4136-b7d4-d86d456f9f06', '100057', '图片新闻', '1', '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e100332b-9acf-4fae-a17e-db03b56da807', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a13d73b8-d879-4e35-b61d-498236b14914', '100015', '40%', '低', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e1dea696-8530-4fa5-8a02-cd294a16fabc', '00000000-0000-0000-0000-000000000100', '90b2b411-263a-4000-bda8-adc5eab6a39a', '100011', '集团董事长', '100', '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e395c757-5574-4eab-a3a2-90439dec35d9', '00000000-0000-0000-0000-000000000001', '1ed555f3-824c-465d-82a9-c3457295103e', '100022', '底部菜单', 'BottomMenu', '0005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e3f6a4c0-8eda-4712-886c-5183ebc2d02e', '00000000-0000-0000-0000-000000000001', '248df0c8-911a-467e-902c-7fbcd0543dd2', '100063', '匿名用户', '1', '10001', 1, '', '2015-03-13 15:30:13', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e8370cc4-96e4-4e2c-875c-c1c41db918bb', '845bd93f-817d-4115-b40d-0b94a8686e53', '84bb9ab7-23ef-4162-9298-a8f44a56ec6b', '100009', '绝密', '9', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e992c1cb-5e3f-44a3-9329-295723632cf3', '00000000-0000-0000-0000-000000000100', '185604f1-0500-4582-85b3-d3b22478d0ca', '100007', '自身对象', '0', '1004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('e9bc21f3-f37b-445b-9ba2-01a99359cc94', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100018', '待办提醒的文件相似度条件', '高', '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('eb43a946-64ad-4434-b54b-0dd388ffca17', '5079360f-ceed-496b-8a5e-a121c5ad7461', 'a70b4f9e-5096-43e2-b5bc-ef3caee748f8', '100059', '整数', '整数', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('eb9a4def-b400-49c2-ad69-31f3a19fc319', '845bd93f-817d-4115-b40d-0b94a8686e53', 'a13d73b8-d879-4e35-b61d-498236b14914', '100013', '80%', '高', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('ed5c6d26-8795-4e93-a821-86619e6d0ee6', '1f3e1dec-c411-44e0-82cf-ae4447a36769', 'a539ee35-66ce-478c-b624-8b1bd874e093', '100004', '审批', '1', '100001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('ede89692-f3dc-4326-b8bc-fe3734d9b4bb', '00000000-0000-0000-0000-000000000001', '248df0c8-911a-467e-902c-7fbcd0543dd2', '100064', '登录用户', '2', '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('eed1cceb-73c5-4bcb-b14a-69a090f69756', '00000000-0000-0000-0000-000000000006', 'a4c35065-0eff-4bb1-b222-d7d697c05760', '100041', '新问题', '0', '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('f031b850-f0d9-4739-bd22-6555ab9237bb', '00000000-0000-0000-0000-000000000009', '12a0f106-c192-4401-becd-0e157ebc27cc', '100049', '系统开发', '2', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('f2e07b0d-0be6-43df-af7b-4d3dc7d583c2', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', 'd4e9b338-0b20-4136-b7d4-d86d456f9f06', '100055', '重要', '3', '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('f48d9bbe-edf2-4d25-9f8c-f1271304875c', '00000000-0000-0000-0000-000000000100', '32050159-680d-45de-a52c-94d0766e4d0c', '100011', '不授权', '1', '1006', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('f5c2cd02-1d1c-4824-a9d0-6ed0f349cfa7', '5079360f-ceed-496b-8a5e-a121c5ad7461', 'a70b4f9e-5096-43e2-b5bc-ef3caee748f8', '100057', '时间', '时间', '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting` VALUES ('fd1a3fb8-5718-4ef0-ab5f-74de14f4a3a7', '00000000-0000-0000-0000-000000000001', 'f3d46dc4-d66d-4df8-8615-70b0b4c6973f', '100024', '读取权限', '1', '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for application_setting_group
-- ----------------------------
DROP TABLE IF EXISTS `application_setting_group`;
CREATE TABLE `application_setting_group`  (
  `id` varchar(36)  NOT NULL,
  `application_id` varchar(36)  NULL DEFAULT NULL,
  `parent_id` varchar(36)  NULL DEFAULT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `name` varchar(100)  NULL DEFAULT NULL,
  `display_name` varchar(50)  NULL DEFAULT NULL,
  `content_type` int(11) NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_setting_group
-- ----------------------------
INSERT INTO `application_setting_group` VALUES ('1043554a-c6bf-4cf9-870e-1e0019809589', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100001', '应用管理_协同平台_新闻管理_文档状态管理', '文档状态管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('106a2a41-94f7-4f23-a310-b8a7db9691b5', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100036', '应用管理_协同平台_工作流管理_业务对象管理', '业务对象管理', 0, '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('12a0f106-c192-4401-becd-0e157ebc27cc', '00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000000', '100035', '应用管理_协同平台_项目管理_项目状态', '项目状态', NULL, '1', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('17bc9996-bfe7-4526-9e22-deb04102a0a0', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100038', '应用管理_协同平台_新闻管理_评分级别', '评分级别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('185604f1-0500-4582-85b3-d3b22478d0ca', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100012', '应用管理_协同平台_人员及权限管理_权限作用范围', '权限作用范围', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('1d328615-e04b-4e7d-ac8d-2ed23cf414ff', '73eb767e-340a-453e-a42d-68600e885fa9', '00000000-0000-0000-0000-000000000000', '100001', '应用管理_协同平台_合同管理_文档状态管理', '文档状态管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('1ed555f3-824c-465d-82a9-c3457295103e', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100029', '应用管理_应用菜单类别', '应用菜单类别', NULL, '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('22a412c4-33ab-41d7-ba07-a2388360fd37', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100033', '应用管理_协同平台_工作流管理_节点管理', '节点管理', 0, '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('248df0c8-911a-467e-902c-7fbcd0543dd2', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100043', '应用管理_应用方法作用范围', '应用方法作用范围', 0, '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('264155d4-7508-42a9-9bd3-14013ed7a592', '00000000-0000-0000-0000-000000000003', 'b3b012bc-eb7e-4362-8ba3-86039eca8c30', '100038', '应用管理_协同平台_工作流管理_分支出口条件管理_链接方式', '链接方式', 0, '10001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('28d27880-b62c-4a22-9981-e6c7a2af875b', '00000000-0000-0000-0000-000000000003', '22a412c4-33ab-41d7-ba07-a2388360fd37', '100035', '应用管理_协同平台_工作流管理_节点管理_执行方式', '执行方式', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('2c0c0e12-cc86-47b8-959e-22368bd6a053', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100011', '应用管理_协同平台_人员及权限管理_权限对象类别', '权限对象类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('2de08811-1638-41b1-bba4-4a90d21db43d', '5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000000', '100042', '应用管理_协同平台_实体数据管理_字段作用范围', '字段作用范围', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('32050159-680d-45de-a52c-94d0766e4d0c', '00000000-0000-0000-0000-000000000100', 'c1f302c0-9100-486f-be6e-696fd50086b6', '100012', '应用管理_协同平台_人员及权限管理_帐号委托_流程审批委托类别', '流程审批委托类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('3eabe2b3-4500-408a-9947-bae22b21678d', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100031', '应用管理_应用链接打开方式', '应用链接打开方式', NULL, '0005', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('3fdff176-308c-4e95-a4fd-86a2a41288d6', '00000000-0000-0000-0000-000000000100', 'a38372c3-ed4b-4d7e-b31b-4dfa8353e568', '100012', '应用管理_协同平台_人员及权限管理_帐号管理_帐号类别', '帐号类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('533e10b5-da45-4951-91d2-b5f92a93422b', '00000000-0000-0000-0000-000000000100', 'ffa6c0b5-e096-433e-87cf-f796605e4405', '100012', '应用管理_协同平台_人员及权限管理_角色管理_角色类别', '角色类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('5be95e2a-323b-48de-95a1-e3a8a700c6bf', '8711f459-972f-4eee-9348-1f28f4daee8c', '00000000-0000-0000-0000-000000000000', '100001', '应用管理_协同平台_费用报销管理_文档状态管理', '文档状态管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('66d84884-7ae2-48a2-9d71-7fc735fe16ba', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100011', '应用管理_协同平台_人员及权限管理_群组管理', '群组管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('84bb9ab7-23ef-4162-9298-a8f44a56ec6b', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100027', 'DLP报表客户端_文件密级', '文件密级', NULL, '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('8a7ba734-c25f-4ed5-809d-315393a4f310', 'a4ac7590-8700-419d-aae7-6a0f646838d3', '00000000-0000-0000-0000-000000000000', '100029', '登报管理_登报截止时间', '登报截止时间', NULL, '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('8d8572dd-2cf5-4670-bae2-f566836a3859', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100033', '应用管理_应用菜单展现方式', '应用菜单展现方式', NULL, '0004', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('90b2b411-263a-4000-bda8-adc5eab6a39a', '00000000-0000-0000-0000-000000000100', 'bf337dd0-7675-4e4c-9158-3749893673c6', '100009', '应用管理_协同平台_人员及权限管理_标准角色管理_标准角色权重', '标准角色权重', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('94081a9e-f8ab-490a-a1f0-a6e247864936', '00000000-0000-0000-0000-000000000100', 'bf337dd0-7675-4e4c-9158-3749893673c6', '100009', '应用管理_协同平台_人员及权限管理_标准角色管理_标准角色类别', '标准角色类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('961f697e-6f2a-47a1-9a3c-76fca606aafa', '00000000-0000-0000-0000-000000000003', 'b3b012bc-eb7e-4362-8ba3-86039eca8c30', '100040', '应用管理_协同平台_工作流管理_分支出口条件管理_右侧括号', '右侧括号', 0, '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('9ce6bdcf-66e3-4d80-abbd-678181de1563', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100007', '应用管理_协同平台_任务管理_标签管理', '标签管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('a13d73b8-d879-4e35-b61d-498236b14914', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100028', 'DLP报表客户端_文件相似度', '文件相似度', NULL, '0003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('a38372c3-ed4b-4d7e-b31b-4dfa8353e568', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100011', '应用管理_协同平台_人员及权限管理_帐号管理', '帐号管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('a4c35065-0eff-4bb1-b222-d7d697c05760', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '100033', '应用管理_协同平台_问题跟踪_问题状态', '问题状态', NULL, '1', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('a539ee35-66ce-478c-b624-8b1bd874e093', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100005', '应用管理_协同平台_任务管理_任务类别', '任务类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('a70b4f9e-5096-43e2-b5bc-ef3caee748f8', '5079360f-ceed-496b-8a5e-a121c5ad7461', '00000000-0000-0000-0000-000000000000', '100041', '应用管理_协同平台_实体数据管理_字段类型', '字段类型', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('a8831199-1720-49c4-84cb-cc81783e00c0', '845bd93f-817d-4115-b40d-0b94a8686e53', '00000000-0000-0000-0000-000000000000', '100026', 'DLP报表客户端_事件类型', '事件类型', NULL, '0001', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('aaae4e87-d84f-4116-9816-6cc0d01ed0e3', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000000', '100034', '应用管理_协同平台_问题跟踪_问题优先级', '问题优先级', NULL, '2', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('acf612c6-bfb7-4527-ba48-4e574fd5321a', '00000000-0000-0000-0000-000000000100', '66d84884-7ae2-48a2-9d71-7fc735fe16ba', '100012', '应用管理_协同平台_人员及权限管理_群组管理_群组类别', '群组类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('b3b012bc-eb7e-4362-8ba3-86039eca8c30', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', '100034', '应用管理_协同平台_工作流管理_分支出口条件管理', '分支出口条件管理', 0, '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('be85b960-7670-472c-abc1-d8f0a605dff6', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100010', '应用管理_协同平台_人员及权限管理_组织管理', '组织管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('bf337dd0-7675-4e4c-9158-3749893673c6', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100010', '应用管理_协同平台_人员及权限管理_标准角色管理', '标准角色管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('bfff78de-0c0a-4b3c-96e0-396516d901d9', '00000000-0000-0000-0000-000000000100', 'be85b960-7670-472c-abc1-d8f0a605dff6', '100009', '应用管理_协同平台_人员及权限管理_组织管理_组织类别', '组织类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('c187f46b-0369-4c7b-9b19-6cfb3b43865c', '00000000-0000-0000-0000-000000000100', 'c1f302c0-9100-486f-be6e-696fd50086b6', '100012', '应用管理_协同平台_人员及权限管理_帐号委托_数据授权委托类别', '数据授权委托类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('c1f302c0-9100-486f-be6e-696fd50086b6', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100011', '应用管理_协同平台_人员及权限管理_帐号委托', '帐号委托', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('c89dc465-3d23-429d-82c0-31bf27139176', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', '00000000-0000-0000-0000-000000000000', '100036', '应用管理_协同平台_新闻管理_新闻类别', '新闻类别', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('cf693a04-81af-4b1e-9a3b-32a2226a97ed', '00000000-0000-0000-0000-000000000003', 'b3b012bc-eb7e-4362-8ba3-86039eca8c30', '100039', '应用管理_协同平台_工作流管理_分支出口条件管理_左侧括号', '左侧括号', 0, '10002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('d4e9b338-0b20-4136-b7d4-d86d456f9f06', '4d946db8-2be7-40f3-9cdf-8e8bb30a09c5', 'c89dc465-3d23-429d-82c0-31bf27139176', '100037', '应用管理_协同平台_新闻管理_新闻类别_重要程度', '重要程度', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('ec4f9c29-3119-46b7-ba2b-55d09f96b001', '00000000-0000-0000-0000-000000000003', 'b3b012bc-eb7e-4362-8ba3-86039eca8c30', '100037', '应用管理_协同平台_工作流管理_分支出口条件管理_判断方式', '判断方式', 0, '10003', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('f0bc993e-28d7-4f87-9196-10f6f9c407cf', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100032', '应用管理_应用参数类别', '应用参数类别', NULL, '0002', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('f3d46dc4-d66d-4df8-8615-70b0b4c6973f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '100030', '应用管理_应用访问权限', '应用访问权限', 0, '0001', 1, '', '2015-03-13 14:49:33', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('fb4d8848-75ae-4370-92e3-f1a60b00a721', '1f3e1dec-c411-44e0-82cf-ae4447a36769', '00000000-0000-0000-0000-000000000000', '100006', '应用管理_协同平台_任务管理_状态管理', '状态管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `application_setting_group` VALUES ('ffa6c0b5-e096-433e-87cf-f796605e4405', '00000000-0000-0000-0000-000000000100', '00000000-0000-0000-0000-000000000000', '100011', '应用管理_协同平台_人员及权限管理_角色管理', '角色管理', 0, '', 1, '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL,
  `name` varchar(255)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`  (
  `Id` varchar(36)  NOT NULL,
  `AccountId` varchar(36)  NULL DEFAULT NULL,
  `AccountName` varchar(50)  NULL DEFAULT NULL,
  `CategoryIndex` varchar(200)  NULL DEFAULT NULL,
  `PrefixCode` varchar(10)  NULL DEFAULT NULL,
  `Description` text  NULL,
  `Priority` int(11) NULL DEFAULT NULL,
  `AllowEditPriority` int(11) NULL DEFAULT NULL,
  `ExpireDays` int(11) NULL DEFAULT NULL,
  `AllowEditExpireDays` int(11) NULL DEFAULT NULL,
  `AllowComment` int(11) NULL DEFAULT NULL,
  `AuthorizedRoleInstantiated` int(11) NULL DEFAULT NULL,
  `WorkflowTemplateId` varchar(36)  NULL DEFAULT NULL,
  `AllowEditWorkflowTemplate` int(11) NULL DEFAULT NULL,
  `OrderId` varchar(20)  NULL DEFAULT NULL,
  `Status` int(11) NULL DEFAULT NULL,
  `UpdateDate` datetime(0) NULL DEFAULT NULL,
  `CreateDate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_category
-- ----------------------------
INSERT INTO `article_category` VALUES ('047ee15d-2372-4932-8093-71d72d03d36b', '00000000-0000-0000-0000-000000000001', '超级管理员', '北京物业公司新闻', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-25 23:33:22', '2015-05-25 21:55:39');
INSERT INTO `article_category` VALUES ('24f3ae7f-8d1b-470f-ba4a-b2ea30f25de6', '00000000-0000-0000-0000-000000000001', '超级管理员', '排行榜', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-27 19:05:41', '2015-05-27 19:05:41');
INSERT INTO `article_category` VALUES ('6e9b00ff-2a32-4832-b27f-0fb43af08f36', '00000000-0000-0000-0000-000000000001', '超级管理员', '上海物业公司新闻', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-25 23:33:22', '2015-05-25 21:55:39');
INSERT INTO `article_category` VALUES ('86658cd7-7e2e-4282-9791-8a144dc842f5', '00000000-0000-0000-0000-000000000001', '超级管理员', '重庆商业公司新闻', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-25 23:33:28', '2015-05-25 22:05:27');
INSERT INTO `article_category` VALUES ('964f6ec0-b7d5-4224-8c03-78ded9519d0e', '00000000-0000-0000-0000-000000000001', '超级管理员', '北京物业公司新闻', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-25 23:33:22', '2015-05-25 21:55:39');
INSERT INTO `article_category` VALUES ('9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', '00000000-0000-0000-0000-000000000001', '超级管理员', '测试类别', '', '999', 3, 1, 7, 1, 1, 0, '083c0214-0c12-4acc-9336-7d90645b8099', 0, '100-998', 1, '2015-05-26 13:25:26', '2015-05-25 22:19:05');
INSERT INTO `article_category` VALUES ('9d4157a7-4956-4155-8a4a-f3edb7670ccc', '00000000-0000-0000-0000-000000000001', '超级管理员', '厦门地产新闻', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-25 23:33:22', '2015-05-25 21:55:39');
INSERT INTO `article_category` VALUES ('c157e59d-a451-4bb2-984d-0485b26313ac', '00000000-0000-0000-0000-000000000001', '超级管理员', '南京物业公司新闻', '', '', 3, 1, 7, 1, 1, 0, '', 0, '0', 1, '2015-05-25 23:33:22', '2015-05-25 21:55:39');

-- ----------------------------
-- Table structure for article_category_scope
-- ----------------------------
DROP TABLE IF EXISTS `article_category_scope`;
CREATE TABLE `article_category_scope`  (
  `EntityId` varchar(36)  NOT NULL,
  `EntityClassName` varchar(400)  NOT NULL,
  `AuthorityId` varchar(36)  NOT NULL,
  `AuthorizationObjectType` varchar(20)  NOT NULL,
  `AuthorizationObjectId` varchar(36)  NOT NULL,
  PRIMARY KEY (`EntityId`, `AuthorityId`, `AuthorizationObjectType`, `AuthorizationObjectId`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_category_scope
-- ----------------------------
INSERT INTO `article_category_scope` VALUES ('24f3ae7f-8d1b-470f-ba4a-b2ea30f25de6', 'X3Platform.Plugins.News.Model.NewsCategoryInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_category_scope` VALUES ('24f3ae7f-8d1b-470f-ba4a-b2ea30f25de6', 'X3Platform.Plugins.News.Model.NewsCategoryInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000002', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_category_scope` VALUES ('9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 'X3Platform.Plugins.News.Model.NewsCategoryInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_category_scope` VALUES ('9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 'X3Platform.Plugins.News.Model.NewsCategoryInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000002', 'Role', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for article_click
-- ----------------------------
DROP TABLE IF EXISTS `article_click`;
CREATE TABLE `article_click`  (
  `Id` varchar(36)  NOT NULL,
  `EntityId` varchar(36)  NULL DEFAULT NULL,
  `EntityClassName` varchar(400)  NULL DEFAULT NULL,
  `AccountId` varchar(36)  NULL DEFAULT NULL,
  `AccountName` varchar(50)  NULL DEFAULT NULL,
  `Click` int(11) NULL DEFAULT NULL,
  `UpdateDate` datetime(0) NULL DEFAULT NULL,
  `CreateDate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment`  (
  `Id` varchar(36)  NOT NULL,
  `NewsId` varchar(36)  NULL DEFAULT NULL,
  `AccountId` varchar(36)  NULL DEFAULT NULL,
  `AccountName` varchar(50)  NULL DEFAULT NULL,
  `Title` varchar(100)  NULL DEFAULT NULL,
  `Content` text  NULL,
  `Grade` varchar(10)  NULL DEFAULT NULL,
  `IsAnonymous` int(11) NULL DEFAULT NULL,
  `SendAlertTask` int(11) NULL DEFAULT NULL,
  `IP` varchar(20)  NULL DEFAULT NULL,
  `UpdateDate` datetime(0) NULL DEFAULT NULL,
  `CreateDate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_frontpage
-- ----------------------------
DROP TABLE IF EXISTS `article_frontpage`;
CREATE TABLE `article_frontpage`  (
  `Id` varchar(36)  NOT NULL,
  `AccountId` varchar(36)  NULL DEFAULT NULL,
  `AccountName` varchar(50)  NULL DEFAULT NULL,
  `CategoryIndex` varchar(20)  NOT NULL,
  `Title` varchar(100)  NULL DEFAULT NULL,
  `PublishDate` datetime(0) NULL DEFAULT NULL,
  `IsTop` int(11) NULL DEFAULT NULL,
  `ExpireDate` datetime(0) NULL DEFAULT NULL,
  `Status` int(11) NULL DEFAULT NULL,
  `CreateDate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_frontpage
-- ----------------------------
INSERT INTO `article_frontpage` VALUES ('56faeaba-b3c0-494e-af96-f3fffd4dea3c', '', '超级管理员', '测试类别', '99898', '2015-05-27 14:51:08', 0, '2015-06-03 14:51:08', 1, '2015-05-27 09:22:25');
INSERT INTO `article_frontpage` VALUES ('d42a3f3c-5e7d-422e-a961-70c536e83ecb', '', '阮郁', '测试类别', '9988', '2015-05-26 14:16:46', 0, '2015-06-02 14:35:46', 0, '2015-05-26 14:35:51');
INSERT INTO `article_frontpage` VALUES ('dea20b67-90bd-4a4f-b9c4-9b7c50256371', '', '超级管理员', '测试类别', '6677', '2015-05-27 20:40:14', 0, '2015-06-03 20:40:15', 0, '2015-05-27 20:40:21');
INSERT INTO `article_frontpage` VALUES ('fe66df30-a7e2-4065-8ad3-63e5530376f9', '', '阮郁', '测试类别', '67y67uu8u7', '2015-05-26 21:10:43', 0, '2015-06-02 21:10:44', 0, '2015-05-26 21:10:47');

-- ----------------------------
-- Table structure for article_mapping
-- ----------------------------
DROP TABLE IF EXISTS `article_mapping`;
CREATE TABLE `article_mapping`  (
  `NewsId` varchar(36)  NOT NULL,
  `CategoryId` varchar(36)  NOT NULL,
  `IsDefault` int(11) NOT NULL,
  PRIMARY KEY (`NewsId`, `CategoryId`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_mapping
-- ----------------------------
INSERT INTO `article_mapping` VALUES ('078e21b0-66c8-4937-83c0-a25a39200d2d', '9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 1);
INSERT INTO `article_mapping` VALUES ('56faeaba-b3c0-494e-af96-f3fffd4dea3c', '9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 1);
INSERT INTO `article_mapping` VALUES ('d42a3f3c-5e7d-422e-a961-70c536e83ecb', '9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 1);
INSERT INTO `article_mapping` VALUES ('dea20b67-90bd-4a4f-b9c4-9b7c50256371', '9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 1);
INSERT INTO `article_mapping` VALUES ('fe66df30-a7e2-4065-8ad3-63e5530376f9', '9b861f80-f6dd-4079-b4dc-7d6b45bd9b66', 1);

-- ----------------------------
-- Table structure for article_operationlog
-- ----------------------------
DROP TABLE IF EXISTS `article_operationlog`;
CREATE TABLE `article_operationlog`  (
  `Id` varchar(36)  NOT NULL,
  `EntityId` varchar(36)  NULL DEFAULT NULL,
  `EntityClassName` varchar(400)  NULL DEFAULT NULL,
  `AccountId` varchar(36)  NULL DEFAULT NULL,
  `OperationType` int(11) NULL DEFAULT NULL,
  `ToAuthorizationObjectType` varchar(36)  NULL DEFAULT NULL,
  `ToAuthorizationObjectId` varchar(100)  NULL DEFAULT NULL,
  `Reason` text  NULL,
  `CreateDate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_scope
-- ----------------------------
DROP TABLE IF EXISTS `article_scope`;
CREATE TABLE `article_scope`  (
  `EntityId` varchar(36)  NOT NULL,
  `EntityClassName` varchar(400)  NOT NULL,
  `AuthorityId` varchar(36)  NOT NULL,
  `AuthorizationObjectType` varchar(20)  NOT NULL,
  `AuthorizationObjectId` varchar(36)  NOT NULL,
  PRIMARY KEY (`EntityId`, `AuthorityId`, `AuthorizationObjectType`, `AuthorizationObjectId`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_scope
-- ----------------------------
INSERT INTO `article_scope` VALUES ('078e21b0-66c8-4937-83c0-a25a39200d2d', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_scope` VALUES ('081af18c-3360-4ff3-b0ca-98a6ea4b758e', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000040', 'Account', '00000000-0000-0000-0000-000000100000');
INSERT INTO `article_scope` VALUES ('56faeaba-b3c0-494e-af96-f3fffd4dea3c', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_scope` VALUES ('56faeaba-b3c0-494e-af96-f3fffd4dea3c', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000041', 'Account', '00000000-0000-0000-0000-000000000001');
INSERT INTO `article_scope` VALUES ('d42a3f3c-5e7d-422e-a961-70c536e83ecb', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_scope` VALUES ('d42a3f3c-5e7d-422e-a961-70c536e83ecb', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000041', 'Account', '00000000-0000-0000-0000-000000100000');
INSERT INTO `article_scope` VALUES ('dea20b67-90bd-4a4f-b9c4-9b7c50256371', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_scope` VALUES ('fe66df30-a7e2-4065-8ad3-63e5530376f9', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000001', 'Role', '00000000-0000-0000-0000-000000000000');
INSERT INTO `article_scope` VALUES ('fe66df30-a7e2-4065-8ad3-63e5530376f9', 'X3Platform.Plugins.News.Model.NewsInfo, X3Platform.Plugins.News', '00000000-0000-0000-0000-000000000041', 'Account', '00000000-0000-0000-0000-000000100000');

-- ----------------------------
-- Table structure for attachment_distributedfile
-- ----------------------------
DROP TABLE IF EXISTS `attachment_distributedfile`;
CREATE TABLE `attachment_distributedfile`  (
  `id` varchar(36)  NOT NULL,
  `virtual_path` varchar(1000)  NULL DEFAULT NULL,
  `file_data` blob NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for attachment_file
-- ----------------------------
DROP TABLE IF EXISTS `attachment_file`;
CREATE TABLE `attachment_file`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `storage_node_index` int(11) NULL DEFAULT NULL COMMENT '存储节点索引',
  `entity_id` varchar(50)  NULL DEFAULT NULL COMMENT '所属实体标识',
  `entity_class_name` varchar(400)  NULL DEFAULT NULL COMMENT '所属实体类名称',
  `attachment_name` varchar(100)  NULL DEFAULT NULL COMMENT '附件名称',
  `virtual_path` varchar(1000)  NULL DEFAULT NULL COMMENT '虚拟路径',
  `folder_rule` varchar(100)  NULL DEFAULT NULL COMMENT '目录规则',
  `file_type` varchar(20)  NULL DEFAULT NULL COMMENT '文件类型',
  `file_size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `file_status` int(11) NULL DEFAULT NULL COMMENT '文件状态',
  `order_id` varchar(40)  NULL DEFAULT NULL COMMENT '排序',
  `created_by` varchar(36)  NULL DEFAULT NULL COMMENT '创建人',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IX_tb_Attachment_File_EntityId_EntityClassName`(`entity_id`, `entity_class_name`(255)) USING BTREE
) ENGINE = InnoDB  COMMENT = '附件存储管理-附件信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attachment_file
-- ----------------------------
INSERT INTO `attachment_file` VALUES ('02ef34fdda9b484ea3733299436cd143', NULL, '2daa92fcacca42e8a2a3f0214cfc525c', 'TestObject', 'test_1565054868', 'test/2015/2Q/4/20150415154516159470246.txt', '', '.txt', 0, 0, NULL, NULL, '2019-08-06 01:27:48');
INSERT INTO `attachment_file` VALUES ('031a0c301e4045bf829fb4a8ff980052', NULL, NULL, NULL, '9.jpg', '{uploads}test/2018/3Q/9/031a0c301e4045bf829fb4a8ff980052.jpg', 'folder/year/quarter/month/', '.jpg', 29595, 0, NULL, NULL, '2018-09-28 15:44:06');
INSERT INTO `attachment_file` VALUES ('04d51121c067463dad3d16a1cfca01ba', NULL, '28cdaba916da4d93b20a728b51712877', 'TestObject', 'test_1565054789', 'test/2015/2Q/4/20150415154516159470246.txt', '', '.txt', 0, 0, NULL, NULL, '2019-08-06 01:26:29');
INSERT INTO `attachment_file` VALUES ('13a176ba801f45ba94539b660b053e05', NULL, NULL, NULL, '9.jpg', '{uploads}test/2018/3Q/9/13a176ba801f45ba94539b660b053e05jpg', 'folder/year/quarter/month/', 'jpg', 29595, 0, NULL, NULL, '2018-09-28 13:13:11');
INSERT INTO `attachment_file` VALUES ('20150415152823240893301', NULL, '79398250-ca7a-4983-9d9b-f36c90bbcf05', 'X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost', '测试文件2.txt', '{uploads}trainings/2015/2Q/4/20150415152823240893301.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 1, NULL, NULL, '2015-04-15 15:28:23');
INSERT INTO `attachment_file` VALUES ('20150415153159069885337', NULL, '79398250-ca7a-4983-9d9b-f36c90bbcf05', 'X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost', '测试文件2.txt', '{uploads}trainings/2015/2Q/4/20150415153159069885337.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-04-15 15:31:59');
INSERT INTO `attachment_file` VALUES ('20150415153355065241769', NULL, '79398250-ca7a-4983-9d9b-f36c90bbcf05', 'X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost', 'test.txt', '{uploads}trainings/2015/2Q/4/20150415153355065241769.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-04-15 15:33:55');
INSERT INTO `attachment_file` VALUES ('20150415154516159470246', NULL, '6b01a790-ade0-41e2-ad0b-51fd25612ed8', 'X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost', '测试文件2.txt', '{uploads}trainings/2015/2Q/4/20150415154516159470246.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-04-15 15:45:16');
INSERT INTO `attachment_file` VALUES ('20150415154717579325870', NULL, 'a583e2e5-ecf4-47c9-ac0f-fe1a0c419673', 'X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost', '测试文件2.txt', '{uploads}cost/2015/2Q/4/20150415154717579325870.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-04-15 15:47:17');
INSERT INTO `attachment_file` VALUES ('20150715000339462556673', NULL, '5c660547-b60c-4cc6-b565-3c8f5ce996aa', NULL, '测试文件2.txt', '{uploads}forum/2015/3Q/7/20150715000339462556673.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-07-15 00:03:39');
INSERT INTO `attachment_file` VALUES ('20150715000741620968179', NULL, '1debddbb-8a7c-42d1-8c79-3db4b302aa80', '', '测试文件2.txt', '{uploads}forum/2015/3Q/7/20150715000741620968179.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-07-15 00:07:41');
INSERT INTO `attachment_file` VALUES ('20150715000854631864796', NULL, 'b267902b-cc02-4088-86e2-0d1017f31302', 'X3Platform.Plugins.Forum.Model.ForumThreadInfo, X3Platform.Plugins.Forum', '测试文件2.txt', '{uploads}forum/2015/3Q/7/20150715000854631864796.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-07-15 00:08:54');
INSERT INTO `attachment_file` VALUES ('20150715000859101581560', NULL, 'b267902b-cc02-4088-86e2-0d1017f31302', 'X3Platform.Plugins.Forum.Model.ForumThreadInfo, X3Platform.Plugins.Forum', 'test.txt', '{uploads}forum/2015/3Q/7/20150715000859101581560.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-07-15 00:08:59');
INSERT INTO `attachment_file` VALUES ('20150715000903553947015', NULL, 'b267902b-cc02-4088-86e2-0d1017f31302', 'X3Platform.Plugins.Forum.Model.ForumThreadInfo, X3Platform.Plugins.Forum', 'test.txt', '{uploads}forum/2015/3Q/7/20150715000903553947015.txt', 'folder\\year\\quarter\\month\\', '.txt', 8, 0, NULL, NULL, '2015-07-15 00:09:03');

-- ----------------------------
-- Table structure for attachment_warn
-- ----------------------------
DROP TABLE IF EXISTS `attachment_warn`;
CREATE TABLE `attachment_warn`  (
  `id` varchar(36)  NOT NULL,
  `warn_type` varchar(20)  NULL DEFAULT NULL,
  `message` varchar(200)  NULL DEFAULT NULL,
  `attachment_storage_id` varchar(36)  NULL DEFAULT NULL,
  `virtual_path` varchar(800)  NULL DEFAULT NULL,
  `attachment_name` varchar(100)  NULL DEFAULT NULL,
  `file_type` varchar(20)  NULL DEFAULT NULL,
  `file_size` int(11) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for connect
-- ----------------------------
DROP TABLE IF EXISTS `connect`;
CREATE TABLE `connect`  (
  `id` varchar(36)  NOT NULL DEFAULT '',
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `account_name` varchar(50)  NULL DEFAULT NULL,
  `app_key` varchar(36)  NULL DEFAULT NULL,
  `app_secret` varchar(50)  NULL DEFAULT NULL,
  `app_type` varchar(20)  NULL DEFAULT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `name` varchar(50)  NULL DEFAULT NULL,
  `description` varchar(800)  NULL DEFAULT NULL,
  `domain` varchar(200)  NULL DEFAULT NULL,
  `redirect_uri` varchar(800)  NULL DEFAULT NULL,
  `options` text  NULL,
  `is_internal` bit(1) NULL DEFAULT NULL,
  `authorization_scope` text  NULL,
  `certified_code` varchar(36)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of connect
-- ----------------------------
INSERT INTO `connect` VALUES ('00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000001', '超级管理员', '00000000-0000-0000-0000-000000000003', 'dab4dc97', 'PC', '0003', '工作流管理', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `connect` VALUES ('00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000001', '超级管理员', '00000000-0000-0000-0000-000000000006', 'hello', 'PC', '0005', '问题跟踪', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `connect` VALUES ('00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000001', '超级管理员', '00000000-0000-0000-0000-000000000009', 'hello', 'PC', '0005', '任务管理', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `connect` VALUES ('52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '00000000-0000-0000-0000-000000000001', '超级管理员', '52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '00000000', 'PC', '0001', '测试应用', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `connect` VALUES ('7418ab17-45a8-4a31-bb63-fe4ab322420e', '00000000-0000-0000-0000-000000000001', '超级管理员', '7418ab17-45a8-4a31-bb63-fe4ab322420e', 'dab4dc97', 'PC', '0006', '附件管理', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `connect` VALUES ('88888888-8888-8888-8888-000000000001', '00000000-0000-0000-0000-000000000001', '超级管理员', '88888888-8888-8888-8888-000000000001', 'dab4dc97', 'PC', '0003', '工作流管理', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `connect` VALUES ('a70633f6-b37a-4e91-97a0-597d708fdcef', '00000000-0000-0000-0000-000000000001', '超级管理员', 'a70633f6-b37a-4e91-97a0-597d708fdcef', 'dab4dc97', 'PC', '0005', '任务管理', NULL, 'x3platform.com', NULL, '[]', b'1', NULL, NULL, 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for connect_access_token
-- ----------------------------
DROP TABLE IF EXISTS `connect_access_token`;
CREATE TABLE `connect_access_token`  (
  `id` varchar(36)  NOT NULL DEFAULT '',
  `app_key` varchar(36)  NULL DEFAULT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `expire_date` datetime(0) NULL DEFAULT NULL,
  `refresh_token` varchar(36)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of connect_access_token
-- ----------------------------
INSERT INTO `connect_access_token` VALUES ('1275e455758a419683d8d322cd5ebd5f', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-07-11 16:55:01', 'a5b10308735b4b979c79fdb6a7c1bde1', '2015-07-10 16:55:01', '2015-07-09 21:54:50');
INSERT INTO `connect_access_token` VALUES ('2461804a647f4698b2df6d717f0d2369', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '00000000-0000-0000-0000-000000000001', '2013-05-28 21:31:57', '5917555c89dc442daf7d1dd0f2f05ccf', '2013-05-28 21:31:56', '2013-05-28 17:00:33');
INSERT INTO `connect_access_token` VALUES ('2a45098aeb7243629926fcb50132ab87', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-07-02 23:32:14', '01e75d6f797f4b34897961477d4f7079', '2015-07-01 23:32:14', '2015-07-01 23:30:01');
INSERT INTO `connect_access_token` VALUES ('36ea920e6a3a45a9aabafae4e68fdc70', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-07-24 00:33:20', '267c2e6cdbd748f0bb250916db3708f0', '2015-07-23 00:33:20', '2015-07-23 00:33:14');
INSERT INTO `connect_access_token` VALUES ('475d91b31ba342cfb40b427b8c162c38', '52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '2f353db6-7f62-4a70-95bf-b73697dbdd43', '2014-11-10 00:57:26', 'a0f072939c77451d933d007078a9caf7', '2014-11-09 00:57:27', '2014-11-09 00:55:46');
INSERT INTO `connect_access_token` VALUES ('5e568b89cd3d47aebecfe8e0c2828178', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '00000000-0000-0000-0000-000000100000', '0001-01-01 00:00:00', '5dd5cf81fd0e42a29d07bd023d6396a9', '2013-05-28 22:49:23', '2013-05-28 22:49:23');
INSERT INTO `connect_access_token` VALUES ('617e799a53fa49f3b1f722f3cedccd69', NULL, '00000000-0000-0000-0000-000000100000', '2018-11-07 23:49:24', '55e8866be866459fb9a36185923f28ae', '2018-11-06 23:49:25', '2018-11-06 23:49:25');
INSERT INTO `connect_access_token` VALUES ('8040811eb4c6450fbaaba1c33041e59a', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-07-05 23:08:47', '8268086936b1477dbf75081ab424822c', '2015-07-04 23:08:47', '2015-07-04 23:02:44');
INSERT INTO `connect_access_token` VALUES ('8c2feaae0ba74bd59fc05b6b6945f842', '52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '00000000-0000-0000-0000-000000100000', '2015-05-14 09:52:01', '401d287f2de846f58d1eb21c8b912ae3', '2015-05-13 09:52:01', '2013-12-24 15:22:38');
INSERT INTO `connect_access_token` VALUES ('8fe917484be14b739787cb1fcbde8a1d', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-06-13 15:44:57', 'a3889828e52b453f84b8606443fa9873', '2015-06-12 15:44:57', '2015-06-12 15:15:05');
INSERT INTO `connect_access_token` VALUES ('98a7ddf35c6e4eeba25b0735df994244', NULL, '00000000-0000-0000-0000-000000100000', '2018-11-07 23:51:01', '0abcb0e28d1b491e8308af6b4a027270', '2018-11-06 23:51:02', '2018-11-06 23:51:02');
INSERT INTO `connect_access_token` VALUES ('9fdbe2bbe2024595842227e5433b6f79', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-06-28 13:39:56', '56db5d0ad7ed4a4d8911b7ec93acfca0', '2015-06-27 13:39:56', '2015-06-26 23:22:15');
INSERT INTO `connect_access_token` VALUES ('a57d42367282478a85831b49c7e720ca', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-06-26 11:19:10', '42327c284ea04da9b1178e5c57ae8d02', '2015-06-25 11:19:10', '2015-06-25 11:18:33');
INSERT INTO `connect_access_token` VALUES ('a6711eb02b0c46cb99b4f1e9d7f25576', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-07-01 21:42:38', '2207a60cc58a4fbeba5ee5f3a9ab028a', '2015-06-30 21:42:38', '2015-06-29 23:00:24');
INSERT INTO `connect_access_token` VALUES ('b739845deb8b4e57937b74d7e3aa2def', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-07-14 22:04:47', '4358796be8704ac69cf3eff788e53932', '2015-07-13 22:04:47', '2015-07-13 22:02:32');
INSERT INTO `connect_access_token` VALUES ('cc797e4a55894adcae4452e8d25a77f8', '', '00000000-0000-0000-0000-000000100000', '2018-11-21 14:25:37', 'a38ab918a53c4713a3849485bae5c500', '2018-11-20 14:25:38', '2018-11-06 23:57:03');
INSERT INTO `connect_access_token` VALUES ('d1985c6ba9c5403bb59116e8af9bc24c', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-06-18 16:43:21', 'b2aa67794d784f3f9c492c56cde1754b', '2015-06-17 16:43:21', '2015-06-17 16:43:10');
INSERT INTO `connect_access_token` VALUES ('d1bbbc8f1e154ea0ada7381fe7ae02a8', NULL, '00000000-0000-0000-0000-000000100000', '2018-11-07 23:50:14', 'f54a23c9182c47d28df676022bcf6b45', '2018-11-06 23:50:15', '2018-11-06 23:50:15');
INSERT INTO `connect_access_token` VALUES ('fb2d17df1d2d47b1987e3d444b82b5c7', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', '2015-06-12 09:00:12', '434b713689e344b291c62b1ef5021b1e', '2015-06-11 09:00:12', '2015-06-11 00:14:10');

-- ----------------------------
-- Table structure for connect_authorization_code
-- ----------------------------
DROP TABLE IF EXISTS `connect_authorization_code`;
CREATE TABLE `connect_authorization_code`  (
  `id` varchar(36)  NOT NULL DEFAULT '',
  `app_key` varchar(36)  NULL DEFAULT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `authorization_scope` text  NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of connect_authorization_code
-- ----------------------------
INSERT INTO `connect_authorization_code` VALUES ('07e0c53d0aa64626b661985e51b12d92', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:42:19', '2018-11-06 23:42:19');
INSERT INTO `connect_authorization_code` VALUES ('33314cc70a8a4d8db59b53e7ee4256bf', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:41:53', '2018-11-06 23:41:53');
INSERT INTO `connect_authorization_code` VALUES ('5b1263da7a2e48d19bd4b35daf761b5e', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 13:57:25', '2018-11-06 13:57:25');
INSERT INTO `connect_authorization_code` VALUES ('5cb528335279470fb71c66f9aa27a144', '', '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:57:00', '2018-11-06 23:57:00');
INSERT INTO `connect_authorization_code` VALUES ('5dac21e3371f472f9120fbeffd7e9b7e', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '00000000-0000-0000-0000-000000100000', 'public', '2013-05-28 22:49:22', '2013-05-28 22:49:22');
INSERT INTO `connect_authorization_code` VALUES ('60aed48ca7df4ec9a3a436b9c80a9a30', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:32:29', '2018-11-06 23:32:29');
INSERT INTO `connect_authorization_code` VALUES ('6f3c021840ea48af9023bb118e9cf5f5', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:50:12', '2018-11-06 23:50:12');
INSERT INTO `connect_authorization_code` VALUES ('75266c29f9e3497480e5ddc6cfa38b8c', 'a70633f6-b37a-4e91-97a0-597d708fdcef', '00000000-0000-0000-0000-000000000001', 'public', '2013-05-28 09:18:21', '2013-05-28 09:18:21');
INSERT INTO `connect_authorization_code` VALUES ('7776cb3b8cd646759db42c4cf8df64fd', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:32:50', '2018-11-06 23:32:50');
INSERT INTO `connect_authorization_code` VALUES ('94aaec63b9a549f99f02f31f7f3a7566', '52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '2f353db6-7f62-4a70-95bf-b73697dbdd43', 'public', '2014-11-09 00:55:45', '2014-11-09 00:55:45');
INSERT INTO `connect_authorization_code` VALUES ('9c2b74b0aa984b62b75655152832f12d', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:49:23', '2018-11-06 23:49:23');
INSERT INTO `connect_authorization_code` VALUES ('9cf1cada8b354e0d9b5c0e01290d790c', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:37:35', '2018-11-06 23:37:35');
INSERT INTO `connect_authorization_code` VALUES ('b6483f8b9a454a0c9c324e1e389b477c', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 12:01:11', '2018-11-06 12:01:11');
INSERT INTO `connect_authorization_code` VALUES ('cd0a7c902e9c48d1ad8f7968747da430', '8ffdec2a3a2c406482800aebd86de17c', '00000000-0000-0000-0000-000000000001', 'public', '2015-06-11 00:14:10', '2015-06-11 00:14:10');
INSERT INTO `connect_authorization_code` VALUES ('cdd6f7ae823a4ce99ba30426ddc15632', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:47:22', '2018-11-06 23:47:22');
INSERT INTO `connect_authorization_code` VALUES ('cff901d2ec1c409287719583cabd6fb3', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:43:25', '2018-11-06 23:43:25');
INSERT INTO `connect_authorization_code` VALUES ('dc81ca7e06884ff191cb61fdb0728040', '52cf89ba-7db5-4e64-9c64-3c868b6e7a99', '00000000-0000-0000-0000-000000100000', 'public', '2013-12-24 15:22:38', '2013-12-24 15:22:38');
INSERT INTO `connect_authorization_code` VALUES ('ee126018d974474d838bf5435bfa70db', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:36:29', '2018-11-06 23:36:29');
INSERT INTO `connect_authorization_code` VALUES ('f70854e65d934da1b91bc3a8c0126c00', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:47:44', '2018-11-06 23:47:44');
INSERT INTO `connect_authorization_code` VALUES ('f7df951c650f4392b80a1b9cfa5fb7c7', NULL, '00000000-0000-0000-0000-000000100000', 'public', '2018-11-06 23:50:59', '2018-11-06 23:50:59');

-- ----------------------------
-- Table structure for connect_call
-- ----------------------------
DROP TABLE IF EXISTS `connect_call`;
CREATE TABLE `connect_call`  (
  `id` varchar(36)  NOT NULL DEFAULT '',
  `app_key` varchar(36)  NULL DEFAULT NULL,
  `request_uri` varchar(800)  NULL DEFAULT NULL,
  `request_data` text  NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `finish_time` datetime(0) NULL DEFAULT NULL,
  `timespan` float NULL DEFAULT NULL,
  `return_code` int(11) NULL DEFAULT NULL,
  `ip` varchar(20)  NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_catalog
-- ----------------------------
DROP TABLE IF EXISTS `data_catalog`;
CREATE TABLE `data_catalog`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `account_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属帐号标识',
  `account_name` varchar(36)  NULL DEFAULT NULL COMMENT '所属帐号名称',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(200)  NULL DEFAULT NULL COMMENT '描述信息',
  `display_type` varchar(50)  NULL DEFAULT NULL COMMENT '显示的类别',
  `root_catalog_item_id` varchar(36)  NULL DEFAULT NULL COMMENT '目录项根节点',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序标识',
  `status` int(4) NULL DEFAULT NULL COMMENT '状态',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据目录信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_catalog_item
-- ----------------------------
DROP TABLE IF EXISTS `data_catalog_item`;
CREATE TABLE `data_catalog_item`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `parent_id` varchar(36)  NULL DEFAULT NULL COMMENT '父级对象标识',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '名称',
  `discrption` varchar(200)  NULL DEFAULT NULL COMMENT '描述',
  `is_key` int(1) NULL DEFAULT NULL COMMENT '关键节点',
  `locking` int(1) NULL DEFAULT NULL COMMENT '锁定',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `full_path` varchar(200)  NULL DEFAULT NULL COMMENT '完整路径',
  `distinguished_name` varchar(800)  NULL DEFAULT NULL COMMENT '唯一名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据目录项信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_catalog_item
-- ----------------------------
INSERT INTO `data_catalog_item` VALUES ('2576ccc7-bd49-46a2-a93e-6e291307fe02', '40000000-0000-0000-0000-000000000000', '系统管理组', '', 0, NULL, '1000', 1, NULL, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `data_catalog_item` VALUES ('4904d97b-8542-44dd-8e5d-a18e683861d0', '00000000-0000-0000-0000-000000000000', 'jijiji', NULL, 1, NULL, NULL, 1, NULL, NULL, '2016-09-15 23:06:33', '2016-09-15 23:06:28');
INSERT INTO `data_catalog_item` VALUES ('8dedd245-1547-4a0e-bf7b-dc0a54330100', '00000000-0000-0000-0000-000000000000', 'ji', NULL, 1, NULL, NULL, 1, NULL, NULL, '2016-09-15 23:06:05', '2016-09-15 22:10:36');
INSERT INTO `data_catalog_item` VALUES ('dd3002c7-b308-42c8-81f1-daf854c982f2', '50000000-0000-0000-0000-000000000000', '其他', '', 0, NULL, '', 1, NULL, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for data_dict
-- ----------------------------
DROP TABLE IF EXISTS `data_dict`;
CREATE TABLE `data_dict`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `name` varchar(20)  NULL DEFAULT NULL COMMENT '名称',
  `data_storage_schema_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属数据存储架构标识',
  `data_table_name` varchar(50)  NULL DEFAULT NULL COMMENT '所属数据表名',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据架构_数据字典信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_label
-- ----------------------------
DROP TABLE IF EXISTS `data_label`;
CREATE TABLE `data_label`  (
  `id` varchar(36)  NOT NULL,
  `name` varchar(100)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_metadata
-- ----------------------------
DROP TABLE IF EXISTS `data_metadata`;
CREATE TABLE `data_metadata`  (
  `id` varchar(36)  NOT NULL COMMENT '主键',
  `model_id` varchar(36)  NULL DEFAULT NULL COMMENT '模型id',
  `field_name` varchar(50)  NULL DEFAULT NULL COMMENT '字段名称',
  `field_display_name` varchar(50)  NULL DEFAULT NULL COMMENT '字段显示名称',
  `field_type` varchar(20)  NULL DEFAULT NULL COMMENT '字段类型',
  `field_length` int(11) NULL DEFAULT NULL COMMENT '字段长度',
  `description` varchar(200)  NULL DEFAULT NULL COMMENT '描述',
  `is_primary_key` int(2) NULL DEFAULT NULL COMMENT '主键',
  `hidden` int(2) NULL DEFAULT NULL COMMENT '隐藏',
  `locking` int(2) NULL DEFAULT NULL COMMENT '锁定',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_data_metadata__model_id`(`model_id`) USING BTREE,
  CONSTRAINT `data_metadata_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `data_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB  COMMENT = '元数据管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_metadata_rule
-- ----------------------------
DROP TABLE IF EXISTS `data_metadata_rule`;
CREATE TABLE `data_metadata_rule`  (
  `metadata_id` varchar(36)  NOT NULL,
  `rule_id` varchar(36)  NOT NULL,
  PRIMARY KEY (`metadata_id`, `rule_id`) USING BTREE,
  INDEX `FK_data_metadata_rule__rule_id`(`rule_id`) USING BTREE,
  CONSTRAINT `data_metadata_rule_ibfk_1` FOREIGN KEY (`metadata_id`) REFERENCES `data_metadata` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_model
-- ----------------------------
DROP TABLE IF EXISTS `data_model`;
CREATE TABLE `data_model`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `name` varchar(128)  NULL DEFAULT NULL COMMENT '名称',
  `display_name` varchar(128)  NULL DEFAULT NULL COMMENT '显示名称',
  `description` varchar(255)  NULL DEFAULT NULL COMMENT '描述',
  `status` int(8) NULL DEFAULT NULL COMMENT '状态',
  `modified_by` varchar(255)  NULL DEFAULT NULL COMMENT '修改者',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_by` varchar(255)  NULL DEFAULT NULL COMMENT '创建者',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据模型管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_rule
-- ----------------------------
DROP TABLE IF EXISTS `data_rule`;
CREATE TABLE `data_rule`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `name` varchar(100)  NULL DEFAULT NULL COMMENT '规则名称',
  `type` varchar(30)  NULL DEFAULT NULL COMMENT '规则类型 - 1 必填, 2 内置数据类型 3 正则表达式, 4 自定义接口',
  `expression` varchar(255)  NULL DEFAULT NULL COMMENT '规则表达式',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态(1启用，0停用)',
  `modified_by` varchar(255)  NULL DEFAULT NULL COMMENT '修改人',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_by` varchar(255)  NULL DEFAULT NULL COMMENT '创建人',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据架构_数据规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_rule_validator
-- ----------------------------
DROP TABLE IF EXISTS `data_rule_validator`;
CREATE TABLE `data_rule_validator`  (
  `id` int(11) NOT NULL COMMENT '唯一标识',
  `name` varchar(255)  NULL DEFAULT NULL COMMENT '名称',
  `target_class_name` varchar(200)  NULL DEFAULT NULL COMMENT '目标对象的类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据规则验证器配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_standard
-- ----------------------------
DROP TABLE IF EXISTS `data_standard`;
CREATE TABLE `data_standard`  (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_standard_dict
-- ----------------------------
DROP TABLE IF EXISTS `data_standard_dict`;
CREATE TABLE `data_standard_dict`  (
  `id` varchar(36)  NOT NULL,
  `name` varchar(30)  NULL DEFAULT NULL,
  `standard_id` varchar(36)  NULL DEFAULT NULL,
  `dict_id` varchar(36)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_storage_adapter
-- ----------------------------
DROP TABLE IF EXISTS `data_storage_adapter`;
CREATE TABLE `data_storage_adapter`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '名称',
  `target_class_name` varchar(400)  NULL DEFAULT NULL COMMENT '目标对象的类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据存储管理-数据适配器配置[字典表]' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_storage_node
-- ----------------------------
DROP TABLE IF EXISTS `data_storage_node`;
CREATE TABLE `data_storage_node`  (
  `id` varchar(11)  NOT NULL COMMENT '唯一标识',
  `storage_schema_id` varchar(36)  NULL DEFAULT NULL COMMENT '存储架构唯一标识',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '名称',
  `type` varchar(36)  NULL DEFAULT NULL COMMENT '类型',
  `provider_name` varchar(100)  NULL DEFAULT NULL COMMENT '提供器名称',
  `host` varchar(50)  NULL DEFAULT NULL COMMENT '服务器',
  `port` int(11) NULL DEFAULT NULL COMMENT '端口',
  `database` varchar(30)  NULL DEFAULT NULL COMMENT '数据库',
  `username` varchar(50)  NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50)  NULL DEFAULT NULL COMMENT '密码',
  `connection_string_format` varchar(400)  NULL DEFAULT NULL COMMENT '连接字符串格式',
  `connection_string` varchar(800)  NULL DEFAULT NULL COMMENT '连接字符串',
  `connection_state` int(11) NULL DEFAULT NULL COMMENT '连接状态',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(200)  NULL DEFAULT NULL COMMENT '备注',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_data_storage_node__storage_schema_id`(`storage_schema_id`) USING BTREE,
  CONSTRAINT `data_storage_node_ibfk_1` FOREIGN KEY (`storage_schema_id`) REFERENCES `data_storage_schema` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB  COMMENT = '数据存储管理-数据存储节点信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_storage_node
-- ----------------------------
INSERT INTO `data_storage_node` VALUES ('default-0', 'default', 'f', 'f', 'f', 'localhost', 3306, NULL, NULL, NULL, NULL, 'jdbc:mysql://mysql.amiintellect.com:3306/ami_base_dev?user=test&password=ami#42aa3B11&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for data_storage_schema
-- ----------------------------
DROP TABLE IF EXISTS `data_storage_schema`;
CREATE TABLE `data_storage_schema`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `name` varchar(200)  NULL DEFAULT NULL COMMENT '名称',
  `options` text  NULL COMMENT '选项',
  `storage_adapter_class_name` varchar(400)  NULL DEFAULT NULL COMMENT '存储适配器类名',
  `storage_strategy_class_name` varchar(400)  NULL DEFAULT NULL COMMENT '存储策略类名',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_data_storage_schema__storage_adapter_id`(`storage_adapter_class_name`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据存储管理-数据存储架构信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_storage_schema
-- ----------------------------
INSERT INTO `data_storage_schema` VALUES ('default', '默认存储', '{}', NULL, NULL, '0001', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `data_storage_schema` VALUES ('tasks', '任务信息', '{}', NULL, NULL, '0001', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `data_storage_schema` VALUES ('test', '测试', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for data_storage_schema_application
-- ----------------------------
DROP TABLE IF EXISTS `data_storage_schema_application`;
CREATE TABLE `data_storage_schema_application`  (
  `storage_schema_id` varchar(36)  NOT NULL,
  `application_id` varchar(36)  NOT NULL,
  PRIMARY KEY (`storage_schema_id`, `application_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '数据存储管理-数据存储架构与应用的关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_storage_schema_application
-- ----------------------------
INSERT INTO `data_storage_schema_application` VALUES ('default', '52cf89ba-7db5-4e64-9c64-3c868b6e7a99');

-- ----------------------------
-- Table structure for data_view
-- ----------------------------
DROP TABLE IF EXISTS `data_view`;
CREATE TABLE `data_view`  (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` varchar(36)  NOT NULL,
  `organization_unit_id` varchar(36)  NULL DEFAULT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `account_name` varchar(50)  NULL DEFAULT NULL,
  `code` varchar(100)  NULL DEFAULT NULL,
  `name` varchar(100)  NULL DEFAULT NULL,
  `display_name` varchar(100)  NULL DEFAULT NULL,
  `description` varchar(800)  NULL DEFAULT NULL,
  `icon_path` varchar(400)  NULL DEFAULT NULL,
  `big_icon_path` varchar(400)  NULL DEFAULT NULL,
  `options` text  NULL,
  `is_private` bit(1) NULL DEFAULT NULL,
  `assign_to_account_id` varchar(36)  NULL DEFAULT NULL,
  `assign_to_account_name` varchar(50)  NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_changelog
-- ----------------------------
DROP TABLE IF EXISTS `project_changelog`;
CREATE TABLE `project_changelog`  (
  `id` varchar(36)  NOT NULL,
  `project_id` varchar(36)  NULL DEFAULT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `account_name` varchar(50)  NULL DEFAULT NULL,
  `release_version` varchar(20)  NULL DEFAULT NULL,
  `publish_date` datetime(0) NULL DEFAULT NULL,
  `content` text  NULL,
  `operation_log` text  NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_scope
-- ----------------------------
DROP TABLE IF EXISTS `project_scope`;
CREATE TABLE `project_scope`  (
  `entity_id` varchar(36)  NOT NULL,
  `entity_class_name` varchar(400)  NOT NULL,
  `authority_id` varchar(36)  NOT NULL,
  `authorization_object_type` varchar(20)  NOT NULL,
  `authorization_object_id` varchar(36)  NOT NULL,
  PRIMARY KEY (`entity_id`, `authority_id`, `authorization_object_type`, `authorization_object_id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_work_item
-- ----------------------------
DROP TABLE IF EXISTS `project_work_item`;
CREATE TABLE `project_work_item`  (
  `id` varchar(36)  NOT NULL,
  `project_id` varchar(36)  NULL DEFAULT NULL,
  `project_plan_id` varchar(36)  NULL DEFAULT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `account_name` varchar(50)  NULL DEFAULT NULL,
  `code` varchar(20)  NULL DEFAULT NULL,
  `name` varchar(50)  NULL DEFAULT NULL,
  `description` varchar(50)  NULL DEFAULT NULL,
  `duration` varchar(50)  NULL DEFAULT NULL,
  `assign_to_account_id` varchar(36)  NULL DEFAULT NULL,
  `assign_to_account_name` varchar(50)  NULL DEFAULT NULL,
  `begin_date` datetime(0) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  `progress` int(255) NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `code` varchar(30)  NULL DEFAULT NULL COMMENT '编号',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '名称',
  `global_name` varchar(100)  NULL DEFAULT NULL COMMENT '全局名称',
  `display_name` varchar(50)  NULL DEFAULT NULL COMMENT '显示名称',
  `pinyin` varchar(100)  NULL DEFAULT NULL COMMENT '拼音',
  `login_name` varchar(50)  NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(50)  NULL DEFAULT NULL COMMENT '密码',
  `password_salt` varchar(8)  NULL DEFAULT NULL COMMENT '密码盐值',
  `password_changed_date` datetime(0) NULL DEFAULT NULL COMMENT '密码修改时间',
  `identity_card` varchar(30)  NULL DEFAULT NULL COMMENT '身份证',
  `type` int(4) NULL DEFAULT NULL COMMENT '类型',
  `certified_telephone` varchar(50)  NULL DEFAULT NULL COMMENT '已验证的电话',
  `certified_email` varchar(50)  NULL DEFAULT NULL COMMENT '已验证的邮箱',
  `certified_avatar` varchar(100)  NULL DEFAULT NULL COMMENT '已验证的头像',
  `enable_email` int(11) NULL DEFAULT NULL COMMENT '启用邮箱',
  `is_draft` bit(1) NULL DEFAULT NULL COMMENT '草稿对象',
  `locking` int(11) NULL DEFAULT NULL COMMENT '锁定',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(200)  NULL DEFAULT NULL COMMENT '备注',
  `ip` varchar(20)  NULL DEFAULT NULL COMMENT '最近一次登录地址',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `distinguished_name` varchar(800)  NULL DEFAULT NULL COMMENT '唯一名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000000000', '0000', '匿名用户', '匿名用户', '匿名用户', '', 'anonymous', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL, NULL, '', 0, '', '', NULL, 0, NULL, 1, '', 1, '', '127.0.0.1', '2000-01-01 00:00:00', 'CN=guest,OU=组织用户,DC=sharepoint,DC=net', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000000001', '0001', '超级管理员', '超级管理员', '超级管理员', '', 'root', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 65536, '', '', '{uploads}avatar/050/00000000-0000-0000-0000-000000000001_120x120.png', 1, NULL, 1, '', 1, '', '127.0.0.1', '2015-08-04 21:01:17', 'CN=admin,OU=组织用户,DC=lhwork,DC=net', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000000002', '0002', '服务帐号', '服务帐号', '搜索服务帐号', '', 'service', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL, NULL, '', 0, '', '', NULL, 1, NULL, 1, '', 1, '', '127.0.0.1', '2000-01-01 00:00:00', 'CN=service,OU=组织用户,DC=lhwork,DC=net', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000000003', '0003', '管理员帐号', '管理员帐号', '管理员帐号', 'admin', 'admin', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL, NULL, '', 0, '', '', '', 1, b'0', 1, '', 1, 'jjj', '127.0.0.1', '2014-09-18 11:14:08', 'CN=管理员帐号,OU=组织用户,DC=x3platform,DC=com', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000000008', '0008', '演示用户', '演示用户', '演示用户', 'demo', 'demo', '7c4a8d09ca3762af61e59520943dc26494f8941b', NULL, NULL, '', 0, '', '', '', 1, b'0', 1, '', 1, 'jjj', '127.0.0.1', '2014-09-18 11:14:08', 'CN=管理员帐号,OU=组织用户,DC=x3platform,DC=com', '2015-03-11 11:22:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100000', '00000018', '阮郁', '阮郁', '阮郁', 'ruanyu', 'ruanyu', '356a192b7913b04c54574d18c28d46e6395428ab', NULL, NULL, '', 0, NULL, NULL, '{uploads}avatar/049/00000000-0000-0000-0000-000000100000_120x120.png', 0, NULL, 1, '', 1, '测试帐号', '0:0:0:0:0:0:0:1', '2018-11-20 14:25:34', 'CN=阮郁,OU=组织用户,DC=x3platform,DC=com', '2015-07-22 22:58:54', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100001', '00000019', '吉其亮', '吉其亮', '吉其亮', 'jiqiliang', 'jiqiliang@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '123.145.39.24', '2013-01-12 07:08:32', 'CN=吉其亮,OU=组织用户,DC=lhwork,DC=net', '2013-04-16 13:42:34', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100002', '00000020', '孙啸', '孙啸', '孙啸', 'sunxiao', 'sunxiao@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '192.168.4.1', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100003', '00000021', '刘亚江', '刘亚江', '刘亚江', 'liuyajiang', 'liuyajiang@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '127.0.0.1', '2000-01-01 00:00:00', 'CN=刘亚江,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100005', '00000023', '王锐', '王锐', '王锐', 'wangrui', 'wangrui@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '192.168.4.54', '2000-01-01 00:00:00', 'CN=王锐,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100007', '00000025', '游彪', '游彪', '游彪', 'youbiao', 'youbiao@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', NULL, '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100008', '00000026', '张春梅', '张春梅', '张春梅', 'zhangchunmei', 'zhangchunmei@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '192.168.4.174', '2000-01-01 00:00:00', 'CN=张春梅,OU=组织用户,DC=lhwork,DC=net', '2013-04-16 14:40:02', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100009', '00000027', '陈国强', '陈国强', '陈国强', 'chenguoqiang', 'chenguoqiang@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '127.0.0.1', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100010', '00000028', '陈国太', '陈国太', '陈国太', 'chenguotai', 'chenguotai@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '192.168.4.76', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100012', '00000030', '郭晓莉', '郭晓莉', '郭晓莉', 'guoxiaoli', 'guoxiaoli@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '127.0.0.1', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100013', '00000031', '李谨', '李谨', '李谨', 'liji', 'liji@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, NULL, '192.168.4.43', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100015', '00000033', '熊竹', '熊竹', '熊竹', 'xiongzhu', 'xiongzhu@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, NULL, '192.168.4.96', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100018', '00000036', '程颜', '程颜', '程颜', 'chengya', 'chengya@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '127.0.0.1', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100019', '00000037', '杜维', '杜维', '杜维', 'duwei', 'duwei@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 1, '', '192.168.4.76', '2000-01-01 00:00:00', NULL, '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100023', '00000041', '谭红梅', '谭红梅', '谭红梅', 'tanhongmei', 'tanhongmei@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '127.0.0.1', '2013-04-18 10:52:26', 'CN=谭红梅,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('00000000-0000-0000-0000-000000100025', '00000043', '李聪', '李聪', '李聪', 'licong', 'licong@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '192.168.4.24', '2000-01-01 00:00:00', 'CN=李聪,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('0057c651-e5d4-4111-bf57-fa1ec28d90f5', '10000007', '杨开建', '杨开建', '杨开建', '', 'yangkaijia@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '', '2000-01-01 00:00:00', 'CN=杨开建,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('3785e8c3-2b79-457d-92d2-63ff1c8a6b52', '10000005', '吴承松', '吴承松', '吴承松', 'wuchengsong', 'wuchengsong@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '192.168.4.30', '2000-01-01 00:00:00', 'CN=吴承松,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('7438aebd-a650-4192-8764-2cbc4a9a0219', '10000004', '谭军', '谭军', '谭军', 'tanju', 'tanju@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '192.168.4.99', '2000-01-01 00:00:00', 'CN=谭军,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('78430e43-cba1-4f5a-97c7-772d937eab58', '10000006', '戴刚', '戴刚', '戴刚', 'daigang', 'daigang@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '192.168.4.170', '2000-01-01 00:00:00', 'CN=戴刚,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_account` VALUES ('b667dace-df73-4f75-b7d5-2883009841a8', '10000003', '沈立', '沈立', '沈立', 'shenli', 'shenli@x3platform.com', '2ca20143dc515cc2bb6711cea21f7a5e4e8326ff', NULL, NULL, '', 0, NULL, NULL, NULL, 0, NULL, 1, '', 1, '', '127.0.0.1', '2013-04-16 00:15:16', 'CN=沈立,OU=组织用户,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for sys_account_assigned_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_assigned_job`;
CREATE TABLE `sys_account_assigned_job`  (
  `account_id` varchar(36)  NOT NULL,
  `assigned_job_id` varchar(36)  NOT NULL,
  `is_default` bit(1) NULL DEFAULT NULL,
  `begin_date` datetime(0) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`, `assigned_job_id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_account_grant
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_grant`;
CREATE TABLE `sys_account_grant`  (
  `id` varchar(36)  NOT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `grantor_id` varchar(36)  NULL DEFAULT NULL,
  `grantee_id` varchar(36)  NULL DEFAULT NULL,
  `granted_time_from` datetime(0) NULL DEFAULT NULL,
  `granted_time_to` datetime(0) NULL DEFAULT NULL,
  `workflow_grant_mode` int(11) NULL DEFAULT NULL,
  `data_query_grant_mode` int(11) NULL DEFAULT NULL,
  `is_aborted` bit(1) NULL DEFAULT NULL,
  `approved_url` varchar(800)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号委托信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_account_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_group`;
CREATE TABLE `sys_account_group`  (
  `account_id` varchar(36)  NOT NULL,
  `group_id` varchar(36)  NOT NULL,
  `is_default` bit(1) NULL DEFAULT NULL,
  `begin_date` datetime(0) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`, `group_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号群组关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_account_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_log`;
CREATE TABLE `sys_account_log`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `account_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属帐号标识',
  `operation_by` varchar(36)  NULL DEFAULT NULL COMMENT '操作人标识',
  `operation_name` varchar(50)  NULL DEFAULT NULL COMMENT '操作名称',
  `description` varchar(800)  NULL DEFAULT NULL COMMENT '描述',
  `snapshot` int(11) NULL DEFAULT NULL COMMENT '快照编号',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_account_organization_unit
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_organization_unit`;
CREATE TABLE `sys_account_organization_unit`  (
  `account_id` varchar(36)  NOT NULL,
  `organization_unit_id` varchar(36)  NOT NULL,
  `is_default` bit(1) NULL DEFAULT NULL,
  `begin_date` datetime(0) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`, `organization_unit_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号组织单元关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_account_organization_unit
-- ----------------------------
INSERT INTO `sys_account_organization_unit` VALUES ('00000000-0000-0000-0000-000000100000', '', b'0', '2015-07-22 22:58:54', '9999-12-31 23:59:59');

-- ----------------------------
-- Table structure for sys_account_profile
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_profile`;
CREATE TABLE `sys_account_profile`  (
  `id` varchar(36)  NOT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `options` text  NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号偏好配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_account_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_role`;
CREATE TABLE `sys_account_role`  (
  `account_id` varchar(36)  NOT NULL,
  `role_id` varchar(36)  NOT NULL,
  `is_default` bit(1) NULL DEFAULT NULL,
  `begin_date` datetime(0) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`, `role_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-帐号角色关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_account_role
-- ----------------------------
INSERT INTO `sys_account_role` VALUES ('00000000-0000-0000-0000-000000100000', '00000000-0000-0000-0000-000000000007', b'1', '2015-07-22 22:58:54', '9999-12-31 23:59:59');

-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority`  (
  `id` varchar(36)  NOT NULL,
  `name` varchar(30)  NULL DEFAULT NULL,
  `description` varchar(200)  NULL DEFAULT NULL,
  `locking` int(11) NULL DEFAULT NULL,
  `tags` varchar(50)  NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-权限配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000001', '应用_通用_查看权限', '应用_通用_查看权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000002', '应用_通用_添加权限', '应用_通用_添加权限www', 0, '通用', '10000', '2014-05-08 21:43:10', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000003', '应用_通用_修改权限', '应用_通用_修改权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000004', '应用_通用_删除权限', '应用_通用_删除权限11', 0, '通用', '10000', '2014-05-08 21:41:42', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000005', '应用_通用_复制权限', '应用_通用_复制权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000006', '应用_通用_打印权限', '应用_通用_打印权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000007', '应用_通用_下载权限', '应用_通用_下载权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000008', '应用_通用_预留权限08', '应用_项目管理中心_查看权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000009', '应用_通用_通知查看权限', '用户接收到通知类待办需要查看某文档，但是如果此时用户没有此文档权限，将赋予【应用_通用_通知查看权限】使用户可以查阅此文档信息。', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000010', '应用_通用_代理查看权限', '应用_通用_代理查看权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000011', '应用_会议管理_会议室可创建者', '应用_会议管理_会议室可创建者', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000012', '应用_会议管理_会议室默认可使用者', '应用_会议管理_会议室默认可使用者', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000013', '应用_会议管理_会议室可使用者', '应用_会议管理_会议室可使用者', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000014', '应用_会议管理_模板可使用者', '应用_会议管理_模板可使用者', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000015', '应用_会议管理_主持人', '应用_会议管理_主持人', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000016', '应用_会议管理_纪要录入人', '应用_会议管理_纪要录入人', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000017', '应用_会议管理_与会人员', '应用_会议管理_与会人员', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000018', '应用_会议管理_抄送人员', '应用_会议管理_抄送人员', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000019', '应用_会议管理_其他阅读人员', '应用_会议管理_其他阅读人员', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000020', '应用_会议管理_协调员', '应用_会议管理_协调员', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000021', '应用_会议管理_可使用人', '应用_会议管理_可使用人', 1, '会议管理', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000040', '应用_通用_推荐权限', '应用_通用_推荐权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000041', '应用_通用_工作流权限', '应用_通用_工作流权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000000042', '应用_通用_审批完成权限', '应用_通用_审批完成权限', 1, '通用', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000001001', '应用_默认_管理员', '应用_默认_管理员', 1, '默认', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000001002', '应用_默认_审查员', '应用_默认_审查员', 1, '默认', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_authority` VALUES ('00000000-0000-0000-0000-000000001003', '应用_默认_可访问成员', '应用_默认_可访问成员', 1, '默认', '10000', '2000-01-01 00:00:00', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for sys_digital_number
-- ----------------------------
DROP TABLE IF EXISTS `sys_digital_number`;
CREATE TABLE `sys_digital_number`  (
  `name` varchar(100)  NOT NULL,
  `expression` varchar(200)  NULL DEFAULT NULL,
  `seed` int(11) NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB  COMMENT = '流水号配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_digital_number
-- ----------------------------
INSERT INTO `sys_digital_number` VALUES ('Key_32DigitGuid', '{guid:N}', 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Key_Guid', '{guid}', 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Key_Nonce', '{random:1234567890:6}', 19, '2014-11-02 21:09:15', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Key_Random_10', '{random:abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ:10}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Key_RunningNumber', '{date:yyyyMMdd}{dailyIncrement:seed:10}', 13, '2019-08-06 01:29:11', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Key_Session', '{guid:N}', 5, '2013-07-08 08:59:26', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Key_Timestamp', '{timestamp}', 82, '2019-08-06 01:28:09', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Account_Grant_Key_Code', '{int:seed:8}', 10000002, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Account_Key_Code', '{int:seed:8}', 10000066, '2015-07-22 09:43:53', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Feature_Key_Code', '{int:seed:6}', 100054, '2015-05-21 21:35:51', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Key_Code', '{int:seed:4}', 1004, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Menu_Key_Code', '{int:seed:6}', 100037, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Package_Key_Code', '{int:seed:10}', 127, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Package_Key_Id', '{dailyIncrement:seed:8}', 3, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_RequestRoute_Key_Code', '{int:seed:6}', 100000, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Setting_Group_Key_Code', '{int:seed:6}', 100000, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Application_Setting_Key_Code', '{int:seed:6}', 100084, '2015-04-11 17:04:46', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Audit_File_Key_Id', '{guid:N}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Bugzilla_Key_Id', '{guid}', 258, '2013-06-25 16:54:45', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_CustomForm_Instance_Key_Code', '{date:yyyyMMdd}{dailyIncrement:seed:8}', 92, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_CustomForm_Instance_Key_Id', '{guid}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_CustomForm_Template_Key_Code', '{int:seed:8}', 3, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_CustomForm_Template_Key_Id', '{guid}', 3, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Event_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:8}', 2, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_GeneralRole_Key_Code', '{int:seed:8}', 10000002, '2015-03-13 09:00:23', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Group_Key_Code', '{int:seed:8}', 10000008, '2015-06-02 15:49:23', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Meeting_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:8}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Meeting_Room_Lock_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:8}', 6, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_News_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:8}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_NonstandardRole_Key_Code', '{int:seed:8}', 10000000, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Organization_Key_Code', '{int:seed:8}', 10000001, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Organization_Key_Id', '{int:seed:6}', 100001, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Organization_Key_OrderId', '{int:seed:8}', 10000000, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Role_Key_Code', '{int:seed:8}', 10000016, '2015-03-12 10:54:38', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_StandardGeneralRole_Key_Code', '{int:seed:8}', 10000012, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_StandardOrganization_Key_Code', '{int:seed:8}', 10000004, '2015-03-10 15:54:03', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_StandardRole_Key_Code', '{int:seed:8}', 10000001, '2015-03-12 14:10:49', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_TimingAction_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:8}', 7, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Wiki_Book_Chapter_Key_Id', '{tag:__ISBN_Placeholder__}{int:seed:8}', 18, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_Wiki_Book_Key_Id', '{tag:X_ISBN_}{int:seed}', 19, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_WorkflowInstance_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:10}', 361, '2015-05-27 20:40:15', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('Table_WorkflowTemplate_Key_Id', '{date:yyyyMMdd}{dailyIncrement:seed:10}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('test1', '{int:seed:8}', 9, '2014-10-24 14:26:02', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('test2', '{date:yyyyMMddHHmmss}', 20, '2019-08-06 01:28:09', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('test3', '{date:yyyyMMddHH}{dailyIncrement:seed:8}', 16, '2019-08-06 01:28:09', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('test4', '{date:yyyyMMdd}{tag:-}{int:seed}', 9, '2014-10-24 14:26:05', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('test5', '{guid}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `sys_digital_number` VALUES ('test6', '{random:abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ:10}', 1, '1970-01-01 00:00:00', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
  `id` varchar(36)  NOT NULL,
  `parent_id` varchar(36)  NULL DEFAULT NULL,
  `code` varchar(30)  NULL DEFAULT NULL,
  `name` varchar(50)  NULL DEFAULT NULL,
  `global_name` varchar(100)  NULL DEFAULT NULL,
  `pinyin` varchar(50)  NULL DEFAULT NULL,
  `type` varchar(50)  NULL DEFAULT NULL,
  `catalog_item_id` varchar(36)  NULL DEFAULT NULL,
  `enable_email` int(11) NULL DEFAULT NULL,
  `effect_scope` int(11) NULL DEFAULT NULL,
  `locking` int(11) NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `remark` varchar(200)  NULL DEFAULT NULL,
  `full_path` varchar(400)  NULL DEFAULT NULL,
  `distinguished_name` varchar(800)  NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-群组信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('b88dcc26-2db1-49f0-b400-9219825dd2de', NULL, '10000007', '系统管理员组', '系统管理员组', '', NULL, '2576ccc7-bd49-46a2-a93e-6e291307fe02', 0, NULL, 1, '', 1, '', '系统管理组\\系统管理员组', 'CN=系统管理员组,OU=系统管理组,DC=x3platform,DC=com', '2015-03-12 11:03:48', '2015-03-11 21:03:31');

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `account_id` varchar(36)  NULL DEFAULT NULL COMMENT '帐号标识',
  `code` varchar(8)  NULL DEFAULT NULL COMMENT '编号, 编号规则为取唯一标识前八位字符',
  `name` varchar(200)  NULL DEFAULT NULL COMMENT '名称',
  `domain` varchar(100)  NULL DEFAULT NULL COMMENT '域',
  `pattern` varchar(100)  NULL DEFAULT NULL COMMENT '模式',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-组织信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_organization_unit
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_unit`;
CREATE TABLE `sys_organization_unit`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `parent_id` varchar(36)  NULL DEFAULT NULL COMMENT '父级对象标识',
  `code` varchar(30)  NULL DEFAULT NULL COMMENT '编号',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '名称',
  `global_name` varchar(100)  NULL DEFAULT NULL COMMENT '全局名称',
  `full_name` varchar(50)  NULL DEFAULT NULL COMMENT '全称',
  `pinyin` varchar(100)  NULL DEFAULT NULL COMMENT '拼音',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `level` int(11) NULL DEFAULT NULL COMMENT '层级',
  `standard_organization_id` varchar(36)  NULL DEFAULT NULL COMMENT '标准组织标识',
  `enable_email` int(11) NULL DEFAULT NULL COMMENT '启用邮箱',
  `effect_scope` int(2) NULL DEFAULT NULL COMMENT '权限作用范围',
  `tree_view_scope` int(2) NULL DEFAULT NULL COMMENT '树视图范围',
  `locking` int(11) NULL DEFAULT NULL COMMENT '锁定',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(200)  NULL DEFAULT NULL COMMENT '备注',
  `full_path` varchar(400)  NULL DEFAULT NULL COMMENT '完整路径',
  `distinguished_name` varchar(800)  NULL DEFAULT NULL COMMENT '唯一名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-组织单元信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_organization_unit
-- ----------------------------
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000000', '0000', '组织结构', '组织结构', '组织架构', 'root', 1, 0, '', 0, 0, 0, 1, '0001', 1, '', '', 'CN=组织结构,OU=组织结构,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000001', '9000', '其他', '其他', '其他', 'uncategorized', 0, 1, '', 1, 0, 0, 1, '9999', 1, '', '组织结构\\其他', 'CN=其他,OU=其他,OU=组织结构,DC=x3platform,DC=com', '2015-03-07 23:42:26', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000002', '9001', '系统内置用户组', '系统内置用户组', '系统内置用户组', 'built-in-users', 1, 1, '', 1, 0, 0, 1, '0001', 1, '', '组织结构\\其他\\系统内置用户组', 'CN=系统内置用户组,OU=内置用户组,OU=其他,OU=组织结构,DC=x3platform,DC=com', '2015-03-12 10:55:07', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000001', '0001', '集团总部', '集团总部', '集团总部', 'jtzb', 0, 1, '', 1, 0, 0, 1, '0001', 1, '', '组织结构\\集团总部', 'CN=集团总部,OU=集团总部,OU=组织结构,DC=x3platform,DC=com', '2015-03-07 23:42:31', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000004', '0002', '人力资源部', '集团人力资源部', '人力资源部', 'jtrlzyb', 1, 2, '', 1, 0, 0, 1, '0002', 1, '', '组织结构集团总部人力资源部', 'CN=人力资源部,OU=集团总部,OU=组织结构,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000004', '0003', '财务部', '集团财务部', '', 'jtcwb', 1, 2, '', 1, 0, 0, 1, '0003', 1, '', '', 'CN=集团财务部,OU=财务部,OU=集团总部,OU=组织结构,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000004', '0004', '公共事务及行政部', '集团公共事务及行政部', '', 'jtxzb', 1, 2, '', 1, 0, 0, 1, '0004', 1, '', '', 'CN=集团财务部,OU=财务部,OU=集团总部,OU=组织结构,DC=lhwork,DC=net', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_organization_unit` VALUES ('00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000004', '0005', '流程与信息管理部', '集团流程与信息管理部', '流程与信息管理部', 'jtxxb', 1, 3, '', 1, 0, 0, 1, '0001', 1, '', '组织结构\\集团总部\\流程与信息管理部', 'CN=集团流程与信息管理部,OU=信息中心,OU=公共事务及行政部,OU=集团总部,OU=组织结构,DC=x3platform,DC=com', '2015-07-22 23:18:13', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `code` varchar(30)  NULL DEFAULT NULL COMMENT '编号',
  `name` varchar(100)  NULL DEFAULT NULL COMMENT '名称',
  `global_name` varchar(100)  NULL DEFAULT NULL COMMENT '全局名称',
  `pinyin` varchar(100)  NULL DEFAULT NULL COMMENT '拼音',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `parent_id` varchar(36)  NULL DEFAULT NULL COMMENT '父级对象标识',
  `standard_role_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属标准角色标识',
  `organization_unit_id` varchar(36)  NULL DEFAULT NULL COMMENT '组织单元标识',
  `general_role_id` varchar(36)  NULL DEFAULT NULL COMMENT '[废弃]通用角色标识',
  `enable_email` int(11) NULL DEFAULT NULL COMMENT '启用邮箱',
  `effect_scope` int(11) NULL DEFAULT NULL COMMENT '权限作用范围',
  `locking` int(11) NULL DEFAULT NULL COMMENT '锁定',
  `order_id` varchar(20)  NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(200)  NULL DEFAULT NULL COMMENT '备注',
  `full_path` varchar(400)  NULL DEFAULT NULL COMMENT '完整路径',
  `distinguished_name` varchar(800)  NULL DEFAULT NULL COMMENT '唯一名称',
  `modified_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '人员及权限管理-角色信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('00000000-0000-0000-0000-000000000000', '0000', '所有人', '所有人', '', NULL, '', '', '', '', NULL, NULL, NULL, '', 1, '', '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00');
INSERT INTO `sys_role` VALUES ('00000000-0000-0000-0000-000000000001', '0001', '默认角色', '默认角色', '', 0, '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', 0, 0, 1, '', 0, '', '组织结构\\其他\\系统内置用户组\\默认角色', 'CN=默认角色,OU=系统内置用户组,OU=其他,OU=组织结构,DC=x3platform,DC=com', '2015-06-02 14:25:57', '2000-01-01 00:00:00');
INSERT INTO `sys_role` VALUES ('00000000-0000-0000-0000-000000000002', '0002', '系统配置管理员', '系统配置管理员', '', 0, '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000000', 0, 0, 1, '', 1, '', '组织结构\\其他\\系统内置用户组\\系统配置管理员', 'CN=系统配置管理员,OU=系统内置用户组,OU=其他,OU=组织结构,DC=x3platform,DC=com', '2015-06-02 14:36:24', '2000-01-01 00:00:00');

-- ----------------------------
-- Table structure for sys_session_ticket
-- ----------------------------
DROP TABLE IF EXISTS `sys_session_ticket`;
CREATE TABLE `sys_session_ticket`  (
  `ticket_id` varchar(100)  NOT NULL,
  `app_key` varchar(36)  NULL DEFAULT NULL COMMENT 'App Key',
  `ticket_value` varchar(50)  NOT NULL,
  `account_object` text  NULL,
  `account_object_type` varchar(400)  NULL DEFAULT NULL,
  `ip` varchar(30)  NULL DEFAULT NULL COMMENT 'IP',
  `location` varchar(200)  NULL DEFAULT NULL,
  `http_user_agent` varchar(400)  NULL DEFAULT NULL,
  `valid_from` datetime(0) NULL DEFAULT NULL,
  `valid_to` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ticket_id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_session_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for task_category
-- ----------------------------
DROP TABLE IF EXISTS `task_category`;
CREATE TABLE `task_category`  (
  `id` varchar(36)  NOT NULL,
  `account_id` varchar(36)  NULL DEFAULT NULL,
  `account_name` varchar(50)  NULL DEFAULT NULL,
  `category_index` varchar(200)  NULL DEFAULT NULL,
  `description` text  NULL,
  `tags` varchar(800)  NULL DEFAULT NULL,
  `order_id` varchar(20)  NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `modified_date` datetime(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '任务管理-类别信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task_category
-- ----------------------------

-- ----------------------------
-- Table structure for task_history_item
-- ----------------------------
DROP TABLE IF EXISTS `task_history_item`;
CREATE TABLE `task_history_item`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `application_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属应用标识',
  `task_code` varchar(120)  NULL DEFAULT NULL COMMENT '任务编号',
  `type` varchar(20)  NULL DEFAULT NULL COMMENT '任务类型',
  `title` varchar(400)  NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(800)  NULL DEFAULT NULL COMMENT '内容',
  `tags` varchar(50)  NULL DEFAULT NULL COMMENT '标签',
  `sender_id` varchar(36)  NULL DEFAULT NULL COMMENT '发送者标识',
  `receiver_id` varchar(36)  NOT NULL COMMENT '接收者标识',
  `is_read` bit(1) NULL DEFAULT NULL COMMENT '是否已读',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`, `receiver_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '任务管理-历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_waiting_item
-- ----------------------------
DROP TABLE IF EXISTS `task_waiting_item`;
CREATE TABLE `task_waiting_item`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `application_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属应用标识',
  `task_code` varchar(120)  NULL DEFAULT NULL COMMENT '任务编号',
  `type` varchar(20)  NULL DEFAULT NULL COMMENT '任务类型',
  `title` varchar(400)  NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(800)  NULL DEFAULT NULL COMMENT '内容',
  `tags` varchar(50)  NULL DEFAULT NULL COMMENT '标签',
  `sender_id` varchar(36)  NULL DEFAULT NULL COMMENT '接收者标识',
  `receiver_id` varchar(36)  NOT NULL COMMENT '接收者标识',
  `trigger_time` datetime(0) NULL DEFAULT NULL COMMENT '触发时间',
  `is_send` bit(1) NULL DEFAULT NULL COMMENT '是否已发送',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`, `receiver_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '任务管理-待发送项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_work_item
-- ----------------------------
DROP TABLE IF EXISTS `task_work_item`;
CREATE TABLE `task_work_item`  (
  `id` varchar(36)  NOT NULL COMMENT '唯一标识',
  `application_id` varchar(36)  NULL DEFAULT NULL COMMENT '所属应用标识',
  `task_code` varchar(120)  NULL DEFAULT NULL COMMENT '任务编号',
  `type` varchar(20)  NULL DEFAULT NULL COMMENT '任务类型',
  `title` varchar(400)  NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(800)  NULL DEFAULT NULL COMMENT '内容',
  `tags` varchar(50)  NULL DEFAULT NULL COMMENT '标签',
  `sender_id` varchar(36)  NOT NULL COMMENT '发送者标识',
  `receiver_id` varchar(36)  NOT NULL COMMENT '接收者标识',
  `is_read` bit(1) NULL DEFAULT NULL COMMENT '是否已读',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`, `receiver_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '任务管理-当前工作项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task_work_item
-- ----------------------------

-- ----------------------------
-- Table structure for ui_page
-- ----------------------------
DROP TABLE IF EXISTS `ui_page`;
CREATE TABLE `ui_page`  (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for view_authobject
-- ----------------------------
DROP VIEW IF EXISTS `view_authobject`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_authobject` AS select `sys_account`.`id` AS `authorization_object_id`,`sys_account`.`name` AS `authorization_object_name`,_utf8'Account' AS `authorization_object_type` from `sys_account` union select `sys_organization_unit`.`id` AS `authorization_object_id`,`sys_organization_unit`.`name` AS `authorization_object_name`,_utf8'OrganizationUnit' AS `authorization_object_type` from (`sys_account_organization_unit` join `sys_organization_unit` on((`sys_account_organization_unit`.`organization_unit_id` = `sys_organization_unit`.`id`))) where ((`sys_account_organization_unit`.`begin_date` < now()) and (`sys_account_organization_unit`.`end_date` > now())) union select `sys_account_role`.`role_id` AS `authorization_object_id`,`sys_role`.`name` AS `authorization_object_name`,_utf8'Role' AS `authorization_object_type` from (`sys_account_role` join `sys_role` on((`sys_account_role`.`role_id` = `sys_role`.`id`))) where ((`sys_account_role`.`begin_date` < now()) and (`sys_account_role`.`end_date` > now())) union select `sys_group`.`id` AS `authorization_object_id`,`sys_group`.`name` AS `authorization_object_name`,_utf8'Group' AS `authorization_object_type` from (`sys_account_group` join `sys_group` on((`sys_account_group`.`group_id` = `sys_group`.`id`))) where ((`sys_account_group`.`begin_date` < now()) and (`sys_account_group`.`end_date` > now())) union select _utf8'00000000-0000-0000-0000-000000000000' AS `authorization_object_id`,_utf8'所有人' AS `authorization_object_name`,_utf8'Role' AS `authorization_object_type`;

-- ----------------------------
-- View structure for view_authobject_account
-- ----------------------------
DROP VIEW IF EXISTS `view_authobject_account`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_authobject_account` AS select `t`.`authorization_object_id` AS `authorization_object_id`,`t`.`authorization_object_type` AS `authorization_object_type`,`t`.`authorization_object_name` AS `authorization_object_name`,`account`.`id` AS `account_id`,`account`.`global_name` AS `account_global_name`,`account`.`login_name` AS `account_login_name`,(case ifnull(`grant`.`grantee_id`,_utf8'') when _utf8'' then `account`.`id` else `grant`.`grantee_id` end) AS `grantee_id`,(case ifnull(`grant`.`grantee_id`,_utf8'') when _utf8'' then `account`.`global_name` else (select `sys_account`.`global_name` AS `global_name` from `sys_account` where (`sys_account`.`id` = `grant`.`grantee_id`)) end) AS `grantee_global_name` from ((`view_authobject_account_temp` `t` join `sys_account` `account` on((`account`.`id` = convert(`t`.`account_id` using utf8)))) left join `sys_account_grant` `grant` on(((`grant`.`grantor_id` = `account`.`id`) and (`grant`.`data_query_grant_mode` = 1) and (`grant`.`granted_time_from` < now()) and (`grant`.`granted_time_to` > now()))));

-- ----------------------------
-- View structure for view_authobject_account_temp
-- ----------------------------
DROP VIEW IF EXISTS `view_authobject_account_temp`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_authobject_account_temp` AS select `sys_account`.`id` AS `authorization_object_id`,`sys_account`.`name` AS `authorization_object_name`,`sys_account`.`id` AS `account_id`,_utf8'Account' AS `authorization_object_type` from `sys_account` union select `sys_organization_unit`.`id` AS `authorization_object_id`,`sys_organization_unit`.`name` AS `authorization_object_name`,`sys_account_organization_unit`.`account_id` AS `account_id`,_utf8'OrganizationUnit' AS `authorization_object_type` from (`sys_account_organization_unit` join `sys_organization_unit` on((`sys_account_organization_unit`.`organization_unit_id` = `sys_organization_unit`.`id`))) where ((`sys_account_organization_unit`.`begin_date` < now()) and (`sys_account_organization_unit`.`end_date` > now())) union select `sys_role`.`id` AS `authorization_object_id`,`sys_role`.`name` AS `authorization_object_name`,`sys_account_role`.`account_id` AS `account_id`,_utf8'Role' AS `authorization_object_type` from (`sys_account_role` join `sys_role` on((`sys_account_role`.`role_id` = `sys_role`.`id`))) where ((`sys_account_role`.`begin_date` < now()) and (`sys_account_role`.`end_date` > now())) union select `sys_group`.`id` AS `authorization_object_id`,`sys_group`.`name` AS `authorization_object_name`,`sys_account_group`.`account_id` AS `account_id`,_utf8'Group' AS `authorization_object_type` from (`sys_account_group` join `sys_group` on((`sys_group`.`id` = `sys_account_group`.`group_id`))) where ((`sys_account_group`.`begin_date` < now()) and (`sys_account_group`.`end_date` > now())) union select _utf8'00000000-0000-0000-0000-000000000000' AS `authorization_object_id`,_utf8'所有人' AS `authorization_object_name`,`sys_everyone`.`id` AS `account_id`,_utf8'Role' AS `authorization_object_type` from `sys_account` `sys_everyone`;

-- ----------------------------
-- Function structure for func_GetCategoryIndexTreeNodeName
-- ----------------------------
DROP FUNCTION IF EXISTS `func_GetCategoryIndexTreeNodeName`;
delimiter ;;
CREATE FUNCTION `func_GetCategoryIndexTreeNodeName`(CategoryIndex VARCHAR(200), SearchPath VARCHAR(200))
  RETURNS varchar(100) CHARSET utf8
  BEGIN

    DECLARE ResultText VARCHAR(200);

    DECLARE RemainText VARCHAR(200);

    -- padding \--
    IF ((SearchPath != '') && (SUBSTRING(SearchPath,CHAR_LENGTH(CategoryIndex),1) != '\\')) THEN
      SET SearchPath = CONCAT(SearchPath,'\\');
    END IF;

    SET RemainText = SUBSTRING(CategoryIndex, CHAR_LENGTH(SearchPath)+1, 200);

    IF (LOCATE('\\',RemainText)=0) THEN
      SET ResultText = CONCAT(RemainText,'$');
    ELSE
      SET ResultText = SUBSTRING(RemainText,1,LOCATE('\\',RemainText)-1);
    END IF;

    RETURN ResultText;
  END
;;
delimiter ;

-- ----------------------------
-- Function structure for func_GetCorporationIdByOrganizationUnitId
-- ----------------------------
DROP FUNCTION IF EXISTS `func_GetCorporationIdByOrganizationUnitId`;
delimiter ;;
CREATE FUNCTION `func_GetCorporationIdByOrganizationUnitId`(OrganizationId  VARCHAR(50))
  RETURNS varchar(50) CHARSET utf8
  BEGIN

    DECLARE OrganizationType VARCHAR(10);

    DECLARE BackwardCount INT;

    SET BackwardCount = 1;

    SET BackwardCount = 1;

    SELECT Id, Type INTO OrganizationId, OrganizationType FROM sys_organization_unit WHERE Id = OrganizationId;

    -- 组织类型等于零, 则为公司类型, 循环结束.
    WHILE (OrganizationType != '0') DO
      SET BackwardCount  = BackwardCount + 1;

      SELECT Id, Type INTO OrganizationId, OrganizationType
      FROM sys_organization_unit
      WHERE Id = ( SELECT parent_id FROM sys_organization_unit WHERE Id = OrganizationId );

      -- 如果父级组织标识为零, 则退出循环.
      -- 如果递归循环次数大于十次, 则退出循环(可能陷入死循环).
      IF ( OrganizationId = NULL || BackwardCount = 10) THEN
        -- BREAK;
        SET OrganizationType = 0;
      END IF;

    END WHILE;

    RETURN OrganizationId;
  End
;;
delimiter ;

-- ----------------------------
-- Function structure for func_GetDepartmentIdByOrganizationUnitId
-- ----------------------------
DROP FUNCTION IF EXISTS `func_GetDepartmentIdByOrganizationUnitId`;
delimiter ;;
CREATE FUNCTION `func_GetDepartmentIdByOrganizationUnitId`(OrganizationId  VARCHAR(50),`Level` INT)
  RETURNS varchar(50) CHARSET utf8
  BEGIN

    DECLARE CorporationId varchar(36);

    DECLARE DepartmentLevel INT;

    DECLARE OrganizationType varchar(20);

    DECLARE BackwardCount INT;

    SET CorporationId = func_GetCorporationIdByOrganizationUnitId(OrganizationId);

    SET DepartmentLevel = func_GetDepartmentLevelByOrganizationUnitId(OrganizationId);
    -- print ('@OrganizationId:' + @DepartmentLevel)

    SET BackwardCount = 1;

    SET OrganizationType = '1';

    -- SELECT @OrganizationId=Id, @OrganizationType=[Type] FROM tb_Organization WHERE Id = @OrganizationId

    -- print '@OrganizationId:' + @OrganizationId

    -- 如果要寻找的级次 @Level 大于部门最大级次 @DepartmentLevel, 则返回NULL.
    IF ( DepartmentLevel = (`Level` + 1)) THEN
      SET DepartmentLevel = BackwardCount;
      SELECT Id, Type INTO OrganizationId, OrganizationType FROM sys_organization_unit WHERE Id = OrganizationId;
    ELSEIF( DepartmentLevel < (`Level` + 1)) THEN
      SET DepartmentLevel = BackwardCount;
      SET OrganizationId = NULL;
    END IF;

    -- 组织类型等于零, 则为公司类型, 循环结束.
    WHILE (OrganizationType != '0' && DepartmentLevel > (BackwardCount + `Level`)) DO
      SET BackwardCount = BackwardCount + 1;

      SELECT Id, Type INTO OrganizationId, OrganizationType
      FROM sys_organization_unit
      WHERE Id = ( SELECT ParentId FROM sys_organization_unit WHERE Id = OrganizationId );

      -- 如果部门深度 = 反向计数器 + 层级, 则退出循环.
      IF ( DepartmentLevel = ( BackwardCount + `Level`) ) THEN
        -- BREAK;
        SET OrganizationType = 0;
      END IF;

      -- 如果父级组织标识等于公司标识, 则退出循环.
      -- 如果父级组织标识为零, 则退出循环.
      -- 如果递归循环次数大于十次, 则退出循环(可能陷入死循环).
      IF ( CorporationId = OrganizationId || OrganizationId = NULL || BackwardCount = 10 ) THEN
        SET OrganizationId = NULL;
      END IF;
    END WHILE;

    RETURN OrganizationId;
  END
;;
delimiter ;

-- ----------------------------
-- Function structure for func_GetDepartmentLevelByOrganizationUnitId
-- ----------------------------
DROP FUNCTION IF EXISTS `func_GetDepartmentLevelByOrganizationUnitId`;
delimiter ;;
CREATE FUNCTION `func_GetDepartmentLevelByOrganizationUnitId`(OrganizationId VARCHAR(50))
  RETURNS varchar(50) CHARSET utf8
  BEGIN
    DECLARE OrganizationType VARCHAR(10);

    DECLARE BackwardCount INT;

    SET BackwardCount = 1;

    SELECT Id, Type INTO OrganizationId, OrganizationType FROM tb_OrganizationUnit WHERE Id = OrganizationId;

    -- 组织类型等于零, 则为公司类型, 循环结束.
    WHILE (OrganizationType != '0') DO
      SET BackwardCount  = BackwardCount + 1;

      SELECT Id, Type INTO OrganizationId, OrganizationType
      FROM tb_Organization
      WHERE Id = ( SELECT ParentId FROM tb_OrganizationUnit WHERE Id = OrganizationId );

      -- 如果父级组织标识为零, 则退出循环.
      -- 如果递归循环次数大于十次, 则退出循环(可能陷入死循环).
      IF (OrganizationId = NULL || BackwardCount = 10) THEN
        SET OrganizationType = 0;
      END IF;
    END WHILE;

    RETURN BackwardCount;
  End
;;
delimiter ;

-- ----------------------------
-- Function structure for func_PaddingLeft
-- ----------------------------
DROP FUNCTION IF EXISTS `func_PaddingLeft`;
delimiter ;;
CREATE FUNCTION `func_PaddingLeft`(Text VARCHAR(100),

                                   PaddingChar CHAR(1),

                                   PaddingToLen INT)
  RETURNS varchar(1000) CHARSET utf8
  BEGIN

    DECLARE PaddingText VARCHAR(1000);

    DECLARE OriginalLen INT;

    DECLARE PaddingCount INT;

    SET OriginalLen = LENGTH(Text);

    IF (OriginalLen >= PaddingToLen) THEN
      SET PaddingText = Text;
    ELSE
      SET PaddingText = '';

      SET PaddingCount = PaddingToLen - OriginalLen;

      WHILE (PaddingCount > 0) DO
        SET PaddingText = CONCAT(PaddingText,PaddingChar);
        SET PaddingCount = PaddingCount - 1;
      END WHILE;

      SET PaddingText = CONCAT(PaddingText, Text);
    END IF;

    RETURN PaddingText;
  END
;;
delimiter ;

-- ----------------------------
-- Function structure for func_PaddingRight
-- ----------------------------
DROP FUNCTION IF EXISTS `func_PaddingRight`;
delimiter ;;
CREATE FUNCTION `func_PaddingRight`(Text VARCHAR(100),
                                    PaddingChar CHAR(1),
                                    PaddingToLen INT)
  RETURNS varchar(1000) CHARSET utf8
  BEGIN

    DECLARE PaddingText VARCHAR(1000);

    DECLARE OriginalLen INT;

    DECLARE PaddingCount INT;

    SET OriginalLen = LENGTH(Text);

    IF (OriginalLen >= PaddingToLen) THEN
      SET PaddingText = Text;
    ELSE
      SET PaddingText = '';

      SET PaddingCount = PaddingToLen - OriginalLen;

      WHILE (PaddingCount > 0) DO
        SET PaddingText = CONCAT(PaddingText,PaddingChar);
        SET PaddingCount = PaddingCount - 1;
      END WHILE;

      SET PaddingText = CONCAT(Text,PaddingText);
    END IF;

    RETURN PaddingText;
  END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
