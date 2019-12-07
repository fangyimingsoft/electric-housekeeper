/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.3.8-MariaDB : Database - ele_housekeeper
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ele_housekeeper` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ele_housekeeper`;

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `parent_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='部门';

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `upt_time` datetime DEFAULT NULL,
  `status` int(10) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

/*Table structure for table `t_data` */

DROP TABLE IF EXISTS `t_data`;

CREATE TABLE `t_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_char` varchar(1) DEFAULT NULL COMMENT '起始符号',
  `code` varchar(20) NOT NULL,
  `device_name` varchar(255) DEFAULT NULL,
  `frame_type` varchar(2) DEFAULT NULL COMMENT '帧类型',
  `temper_ha` float DEFAULT NULL COMMENT '温度1',
  `temper_hb` float DEFAULT NULL,
  `temper_hc` float DEFAULT NULL,
  `temper_la` float DEFAULT NULL,
  `temper_lb` float DEFAULT NULL,
  `temper_lc` float DEFAULT NULL,
  `temper_n` float DEFAULT NULL,
  `voltage_a` float DEFAULT NULL COMMENT 'A相电压',
  `voltage_b` float DEFAULT NULL COMMENT 'B相',
  `voltage_c` float DEFAULT NULL COMMENT 'C相',
  `current_a` float DEFAULT NULL COMMENT 'A相电流',
  `current_b` float DEFAULT NULL COMMENT 'B相',
  `current_c` float DEFAULT NULL COMMENT 'C相',
  `power_factor_str` varchar(5) DEFAULT NULL COMMENT '电力系数',
  `active_power_a` float DEFAULT NULL COMMENT 'A相有功功率',
  `active_power_b` float DEFAULT NULL COMMENT 'B',
  `active_power_c` float DEFAULT NULL COMMENT 'C',
  `active_power` float DEFAULT NULL COMMENT '总有功功率',
  `reactive_power_a` float DEFAULT NULL COMMENT 'A相无功功率',
  `reactive_power_b` float DEFAULT NULL COMMENT 'B',
  `reactive_power_c` float DEFAULT NULL COMMENT 'C',
  `reactive_power` float DEFAULT NULL COMMENT '总无功功率',
  `power_factor_a` float DEFAULT NULL COMMENT 'a相功率因数',
  `power_factor_b` float DEFAULT NULL COMMENT 'b相功率因数',
  `power_factor_c` float DEFAULT NULL COMMENT 'c相功率因数',
  `power_factor` float DEFAULT NULL COMMENT '总功率因数',
  `apparent_power_a` float DEFAULT NULL,
  `apparent_power_b` float DEFAULT NULL,
  `apparent_power_c` float DEFAULT NULL,
  `apparent_power` float NOT NULL COMMENT '总视在功率',
  `freq` float DEFAULT NULL COMMENT '频率',
  `voltage_harm_a` float DEFAULT NULL COMMENT 'A相电压谐波',
  `voltage_harm_b` float DEFAULT NULL COMMENT 'B相电压谐波',
  `voltage_harm_c` float DEFAULT NULL COMMENT 'C相电压谐波',
  `current_harm_a` float DEFAULT NULL COMMENT 'A相电流谐波',
  `current_harm_b` float DEFAULT NULL COMMENT 'B相电流谐波',
  `current_harm_c` float DEFAULT NULL COMMENT 'C相电流谐波',
  `voltage_dist_a` float DEFAULT NULL COMMENT 'a相电压畸变率',
  `voltage_dist_b` float DEFAULT NULL,
  `voltage_dist_c` float DEFAULT NULL,
  `current_dist_a` float DEFAULT NULL COMMENT 'a相电流畸变率',
  `current_dist_b` float DEFAULT NULL,
  `current_dist_c` float DEFAULT NULL,
  `secondary_absorb_active` float DEFAULT NULL COMMENT '吸收有功二次侧',
  `secondary_release_active` float DEFAULT NULL COMMENT '释放有功二次侧',
  `secondary_inductive_reactive` float DEFAULT NULL COMMENT '感性无功二次侧',
  `secondary_capacitive_reactive` float DEFAULT NULL COMMENT '容性无功二次侧',
  `primary_absorb_active` float DEFAULT NULL COMMENT '吸收有功一次侧',
  `primary_release_active` float DEFAULT NULL COMMENT '释放有功一次侧',
  `primary_inductive_reactive` float DEFAULT NULL COMMENT '感性无功一次侧',
  `primary_capacitive_reactive` float DEFAULT NULL COMMENT '容性无功二次侧',
  `latitude` varchar(12) DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(12) DEFAULT NULL COMMENT '经度',
  `gps_effective` tinyint(1) DEFAULT NULL COMMENT 'gps是否有效',
  `check_digit` varchar(4) DEFAULT NULL COMMENT '检验位',
  `time` datetime DEFAULT NULL COMMENT '数据时间',
  `topic_offset` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38726 DEFAULT CHARSET=utf8;

/*Table structure for table `t_device` */

DROP TABLE IF EXISTS `t_device`;

CREATE TABLE `t_device` (
  `code` varchar(15) NOT NULL,
  `dept_id` int(128) DEFAULT NULL COMMENT '所属部门ID',
  `owner` varchar(20) DEFAULT NULL,
  `type` varchar(125) DEFAULT NULL COMMENT '变压器类型',
  `name` varchar(125) DEFAULT NULL COMMENT '变压器名称',
  `capacity` int(11) DEFAULT 0 COMMENT '容量',
  `dept` varchar(20) DEFAULT NULL,
  `depSN` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `lng` varchar(20) DEFAULT NULL COMMENT '经度',
  `lat` varchar(20) DEFAULT NULL COMMENT '纬度',
  `cameraSN` varchar(20) DEFAULT NULL COMMENT '相机SN',
  `AccessToken` varchar(200) DEFAULT NULL COMMENT 'token',
  `HDsrcP` varchar(200) DEFAULT NULL,
  `SmoothsrcP` varchar(200) DEFAULT NULL,
  `HDsrc` varchar(200) DEFAULT NULL,
  `Smoothsrc` varchar(200) DEFAULT NULL,
  `comcard` varchar(20) DEFAULT NULL,
  `vediocard` varchar(20) DEFAULT NULL,
  `lng1` varchar(20) DEFAULT NULL,
  `lat1` varchar(20) DEFAULT NULL,
  `cycle` varchar(5) DEFAULT NULL,
  `ct` smallint(6) DEFAULT NULL,
  `serverId` varchar(20) DEFAULT NULL,
  `serveraddr` varchar(20) DEFAULT NULL,
  `count` varchar(20) DEFAULT NULL,
  `account1` varchar(20) DEFAULT NULL,
  `name1` varchar(20) DEFAULT NULL,
  `phone1` varchar(20) DEFAULT NULL,
  `account2` varchar(20) DEFAULT NULL,
  `name2` varchar(20) DEFAULT NULL,
  `phone2` varchar(40) DEFAULT NULL,
  `account3` varchar(20) DEFAULT NULL,
  `name3` varchar(20) DEFAULT NULL,
  `phone3` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  `temper_ha` float DEFAULT 0 COMMENT '温度1',
  `temper_hb` float DEFAULT 0,
  `temper_hc` float DEFAULT 0,
  `temper_la` float DEFAULT 0,
  `temper_lb` float DEFAULT 0,
  `temper_lc` float DEFAULT 0,
  `temper_n` float DEFAULT 0,
  `voltage_a` float DEFAULT 0 COMMENT 'A相电压',
  `voltage_b` float DEFAULT 0 COMMENT 'B相',
  `voltage_c` float DEFAULT 0 COMMENT 'C相',
  `current_a` float DEFAULT 0 COMMENT 'A相电流',
  `current_b` float DEFAULT 0 COMMENT 'B相',
  `current_c` float DEFAULT 0 COMMENT 'C相',
  `freq` float DEFAULT 0 COMMENT '频率',
  `active_power_a` float DEFAULT 0 COMMENT 'A相有功功率',
  `active_power_b` float DEFAULT 0 COMMENT 'B',
  `active_power_c` float DEFAULT 0 COMMENT 'C',
  `active_power` float DEFAULT 0 COMMENT '总有功功率',
  `reactive_power_a` float DEFAULT 0 COMMENT 'A相无功功率',
  `reactive_power_b` float DEFAULT 0 COMMENT 'B',
  `reactive_power_c` float DEFAULT 0 COMMENT 'C',
  `reactive_power` float DEFAULT 0 COMMENT '总无功功率',
  `apparent_power_a` float DEFAULT 0,
  `apparent_power_b` float DEFAULT 0,
  `apparent_power_c` float DEFAULT 0,
  `apparent_power` float DEFAULT 0 COMMENT '总视在功率',
  `power_factor_a` float DEFAULT 0 COMMENT 'a功率因数',
  `power_factor_b` float DEFAULT 0 COMMENT 'b功率因数',
  `power_factor_c` float DEFAULT 0 COMMENT 'c功率因数',
  `power_factor` float DEFAULT 0 COMMENT '总功率因数',
  `voltage_harm_a` float DEFAULT 0 COMMENT 'A相电压谐波',
  `voltage_harm_b` float DEFAULT 0 COMMENT 'B相电压谐波',
  `voltage_harm_c` float DEFAULT 0 COMMENT 'C相电压谐波',
  `current_harm_a` float DEFAULT 0 COMMENT 'A相电流谐波',
  `current_harm_b` float DEFAULT 0 COMMENT 'B相电流谐波',
  `current_harm_c` float DEFAULT 0 COMMENT 'C相电流谐波',
  `kwh` float DEFAULT 0 COMMENT '累计电能',
  `status` tinyint(1) DEFAULT 0,
  `protect` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `t_warning` */

DROP TABLE IF EXISTS `t_warning`;

CREATE TABLE `t_warning` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `warning_type` varchar(255) NOT NULL COMMENT '告警类型,直接以中文存储',
  `device_code` varchar(100) NOT NULL COMMENT '设备编码',
  `device_name` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL COMMENT '报警时间,以topic的timestamp为准',
  `status` int(10) NOT NULL COMMENT '告警状态,0未处理，1已确认，2已发布工单，3已完成',
  `data_id` int(100) DEFAULT NULL COMMENT '数据ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1674 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
