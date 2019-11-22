/*
SQLyog  v12.2.6 (64 bit)
MySQL - 10.4.6-MariaDB : Database - ele_housekeeper
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

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`id`,`name`,`code`,`parent_id`) values 
(1,'南开大学',NULL,NULL),
(2,'上海汽车地毯(铁岭)汽车材料有限公司',NULL,NULL),
(3,'国网辽阳供电公司',NULL,NULL),
(4,'国网辽阳供电公司弓长岭分公司',NULL,NULL),
(5,'国网铁岭供电公司',NULL,NULL),
(6,'铁岭市政管修处',NULL,NULL),
(7,'辽宁紫科环保科技有限公司',NULL,NULL);

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Data for the table `sys_log` */

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

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`name`,`tel`,`role`,`crt_time`,`upt_time`,`status`) values 
(1,'admin','21232f297a57a5a743894a0e4a801fc3',NULL,NULL,NULL,NULL,NULL,1);

/*Table structure for table `t_device` */

DROP TABLE IF EXISTS `t_device`;

CREATE TABLE `t_device` (
  `code` varchar(15) NOT NULL,
  `dept_id` int(128) DEFAULT NULL COMMENT '所属部门ID',
  `owner` varchar(20) DEFAULT NULL,
  `type` varchar(125) NOT NULL COMMENT '变压器类型',
  `name` varchar(125) DEFAULT NULL COMMENT '变压器名称',
  `capacity` int(11) DEFAULT 0 COMMENT '容量',
  `dept` varchar(20) DEFAULT NULL,
  `depSN` varchar(20) DEFAULT NULL,
  `address` varchar(50) NOT NULL,
  `lng` varchar(20) DEFAULT NULL COMMENT '经度',
  `lat` varchar(20) DEFAULT NULL COMMENT '纬度',
  `cameraSN` varchar(20) NOT NULL COMMENT '相机SN',
  `AccessToken` varchar(200) DEFAULT NULL COMMENT 'token',
  `HDsrcP` varchar(200) NOT NULL,
  `SmoothsrcP` varchar(200) NOT NULL,
  `HDsrc` varchar(200) DEFAULT NULL,
  `Smoothsrc` varchar(200) DEFAULT NULL,
  `comcard` varchar(20) DEFAULT NULL,
  `vediocard` varchar(20) DEFAULT NULL,
  `lng1` varchar(20) DEFAULT NULL,
  `lat1` varchar(20) DEFAULT NULL,
  `cycle` varchar(5) DEFAULT NULL,
  `CT` smallint(6) NOT NULL,
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
  `apparent_power` float NOT NULL DEFAULT 0 COMMENT '总视在功率',
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
  `protect` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_device` */

insert  into `t_device`(`code`,`dept_id`,`owner`,`type`,`name`,`capacity`,`dept`,`depSN`,`address`,`lng`,`lat`,`cameraSN`,`AccessToken`,`HDsrcP`,`SmoothsrcP`,`HDsrc`,`Smoothsrc`,`comcard`,`vediocard`,`lng1`,`lat1`,`cycle`,`CT`,`serverId`,`serveraddr`,`count`,`account1`,`name1`,`phone1`,`account2`,`name2`,`phone2`,`account3`,`name3`,`phone3`,`update_time`,`temper_ha`,`temper_hb`,`temper_hc`,`temper_la`,`temper_lb`,`temper_lc`,`temper_n`,`voltage_a`,`voltage_b`,`voltage_c`,`current_a`,`current_b`,`current_c`,`freq`,`active_power_a`,`active_power_b`,`active_power_c`,`active_power`,`reactive_power_a`,`reactive_power_b`,`reactive_power_c`,`reactive_power`,`apparent_power_a`,`apparent_power_b`,`apparent_power_c`,`apparent_power`,`power_factor_a`,`power_factor_b`,`power_factor_c`,`power_factor`,`voltage_harm_a`,`voltage_harm_b`,`voltage_harm_c`,`current_harm_a`,`current_harm_b`,`current_harm_c`,`kwh`,`status`,`protect`) values 
('#34235325',1,NULL,'类型1','设备1',290,NULL,NULL,'e',NULL,NULL,'?',NULL,'?','?','?','?','?','?',NULL,NULL,NULL,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-11-13 19:23:49',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,78,70,33,3,1,12,132,132,132,321,12,0,1,0),
('#234235255',2,NULL,'类型3','设备2',0,NULL,NULL,'l',NULL,NULL,'?',NULL,'?','?','?','?','?','?',NULL,NULL,NULL,89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-11-21 19:23:54',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
