CREATE TABLE `dglue_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `app_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '应用code',
  `app_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '应用名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_code` (`app_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='应用表';

CREATE TABLE `dglue_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `app_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '应用编号',
  `rule_code` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规则编号',
  `rule_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `version` int(11) NOT NULL COMMENT '版本',
  `source` text COLLATE utf8_bin NOT NULL COMMENT '源码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_code_rule_code` (`app_code`,`rule_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='逻辑规则表';

CREATE TABLE `dglue_rule_his` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_id` bigint(20) NOT NULL COMMENT '规则Id',
  `app_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '应用编号',
  `code` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `version` int(11) NOT NULL COMMENT '版本',
  `source` text COLLATE utf8_bin NOT NULL COMMENT '源码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rule_id_version` (`rule_id`,`version`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='逻辑规则历史表';