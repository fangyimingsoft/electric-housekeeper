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

/*Table structure for table `t_data` */

DROP TABLE IF EXISTS `t_data`;

CREATE TABLE `t_data` (
  `id` int(11) NOT NULL,
  `start_char` varchar(1) DEFAULT NULL COMMENT '起始符号',
  `code` varchar(20) NOT NULL,
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
  `gpsEffective` tinyint(1) DEFAULT NULL COMMENT 'gps是否有效',
  `check_digit` varchar(4) DEFAULT NULL COMMENT '检验位',
  `time` datetime DEFAULT NULL COMMENT '数据时间',
  `topic_offset` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_data` */

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
  `protect` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_device` */

insert  into `t_device`(`code`,`dept_id`,`owner`,`type`,`name`,`capacity`,`dept`,`depSN`,`address`,`lng`,`lat`,`cameraSN`,`AccessToken`,`HDsrcP`,`SmoothsrcP`,`HDsrc`,`Smoothsrc`,`comcard`,`vediocard`,`lng1`,`lat1`,`cycle`,`CT`,`serverId`,`serveraddr`,`count`,`account1`,`name1`,`phone1`,`account2`,`name2`,`phone2`,`account3`,`name3`,`phone3`,`update_time`,`temper_ha`,`temper_hb`,`temper_hc`,`temper_la`,`temper_lb`,`temper_lc`,`temper_n`,`voltage_a`,`voltage_b`,`voltage_c`,`current_a`,`current_b`,`current_c`,`freq`,`active_power_a`,`active_power_b`,`active_power_c`,`active_power`,`reactive_power_a`,`reactive_power_b`,`reactive_power_c`,`reactive_power`,`apparent_power_a`,`apparent_power_b`,`apparent_power_c`,`apparent_power`,`power_factor_a`,`power_factor_b`,`power_factor_c`,`power_factor`,`voltage_harm_a`,`voltage_harm_b`,`voltage_harm_c`,`current_harm_a`,`current_harm_b`,`current_harm_c`,`kwh`,`status`,`protect`) values 
('#00000000001',NULL,'GWTL-BT','SBH15-200油变','龙南线七里分47#右2',200,'国网铁岭供电公司检修分公司','GWTL-BT','铁岭市银州区岭东路七里屯村S202省道右（南）侧20米','$***********','************','C36830879','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/0a18a2f573284bb9b40c486bcf2d742e.hd','rtmp://rtmp.open.ys7.com/openlive/0a18a2f573284bb9b40c486bcf2d742e','ezopen://open.ys7.com/C36830879/1.hd.live','ezopen://open.ys7.com/C36830879/1.hd.live	ezopen://open.ys7.com/C36830879/1.live',NULL,NULL,'123.897005','42.283079',NULL,80,NULL,NULL,NULL,'tlgd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:07:31',15.2,14.2,14.7,17.9,18.4,18.4,17.9,237.3,235.4,235.8,20.64,34.32,44.16,50.05,4.64,7.92,9.92,22.56,1.04,1.12,2.48,4.72,NULL,NULL,NULL,23.36,0.96,0.981,0.953,0.964,0,0,0,0,0,0,285369,0,1),
('#00000000002',NULL,'GWTL-BT','SBH15-315油变','龙南线七里分17#',315,'国网铁岭供电公司检修分公司','GWTL-BT','铁岭市银州区七兴新区(北区) 10KV龙南线七里分17#','$***********','************','C36830876','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/bf0fc0235dd24066b3727bae7fea9219.hd','rtmp://rtmp.open.ys7.com/openlive/bf0fc0235dd24066b3727bae7fea9219','\r\nezopen://open.ys7.com/C36830876/1.hd.live','ezopen://open.ys7.com/C36830876/1.live',NULL,NULL,'123.881714','42.287073',NULL,120,NULL,NULL,NULL,'tlgd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:30',15.7,16.2,16.2,44.4,0,30.7,23.2,236.1,236.8,236.1,47.16,141.48,126.36,50.07,10.56,33.24,29.52,73.32,3,2.52,3.72,3.24,NULL,NULL,NULL,74.52,0.952,0.991,0.99,0.985,0,0,0,0,0,0,365685,0,1),
('#00000000003',NULL,'GWTL-BT','S9-160油变','龙南线七里分47#左1',160,'国网铁岭供电公司检修分公司','GWTL-BT','铁岭市银州区岭东路七里屯村S202省道左（北）侧20米,10KV龙南线七里分47#左1','$4216.56246N','12353.07766E','C36830891','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/7fa04a8b47324568b90e4decd1f99684.hd','rtmp://rtmp.open.ys7.com/openlive/7fa04a8b47324568b90e4decd1f99684','ezopen://open.ys7.com/C36830891/1.hd.live','ezopen://open.ys7.com/C36830891/1.live',NULL,NULL,'123.896491','42.284123',NULL,80,NULL,NULL,NULL,'tlgd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:53',16.4,15.7,15.7,18.9,16.9,17.4,23.2,230.1,232,246.4,71.84,13.04,3.36,50.06,16.24,2.8,0.72,19.84,1.44,0.48,0.08,2.16,NULL,NULL,NULL,20.4,0.984,0.942,0.905,0.975,0,0,0,0,0,0,315750,0,1),
('#00000000004',NULL,'GWTL-BT','S11-100油变','龙南线七里分41#左1',100,'国网铁岭供电公司检修分公司','GWTL-BT','铁岭市银州区岭东路七里屯村S202省道左（北）侧20米院内10KV龙南线七里分41#左1','$***********','************','C36830884','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/b496556bafdb420a9de22a0fd2860ea7.hd','rtmp://rtmp.open.ys7.com/openlive/b496556bafdb420a9de22a0fd2860ea7','ezopen://open.ys7.com/c36830884/1.hd.live','ezopen://open.ys7.com/c36830884/1.live',NULL,NULL,'123.895664','42.283787',NULL,40,NULL,NULL,NULL,'tlgd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:28',0,16.4,15.7,19.4,39.7,18.4,17.9,245.8,231.4,267,24.76,80.36,26.12,50.07,5.56,17.76,6.52,29.88,1.64,3.56,1.92,7.16,NULL,NULL,NULL,31.68,0.915,0.955,0.936,0.943,0,0,0,0,0,0,625553,0,1),
('#00000000005',NULL,'GWTL-BT','S7-100油变','龙南线七里分57#',100,'国网铁岭供电公司检修分公司','GWTL-BT','铁岭市银州区龙山乡黄金屯路边3米北侧  10KV龙南线七里分57#','$***********','************','C70957282','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/962b523a92f644d1a42de7ed80b224e8.hd','rtmp://rtmp.open.ys7.com/openlive/962b523a92f644d1a42de7ed80b224e8','ezopen://open.ys7.com/C70957282/1.hd.live','ezopen://open.ys7.com/C70957282/1.live',NULL,NULL,'123.902689','42.28144',NULL,40,NULL,NULL,NULL,'tlgd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-12-21 11:04:10',5.9,5.9,5.9,5.9,5.9,5.9,5.4,234.6,234.6,234.6,0,0,0,50.03,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,1,1,1,1,0,0,0,0,0,0,49165.4,1,1),
('#00000000006',NULL,'szgxc','箱式变压器','玉深泵站',630,'铁岭市政管修处','szgxc','辽宁市铁岭银州区玉深路','$***********','************','166356417','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/766e003e65a14782a2aa46cb8cc843bb.hd','rtmp://rtmp.open.ys7.com/openlive/766e003e65a14782a2aa46cb8cc843bb','ezopen://open.ys7.com/166356417/1.hd.live','ezopen://open.ys7.com/166356417/1.live',NULL,NULL,'123.81083333333333','42.27',NULL,250,NULL,NULL,NULL,'zdt',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-28 13:08:29',18.9,18.9,18.9,19.9,19.9,20.7,19.9,240.2,240.8,240.6,0,0,0,50,0,0,0,0.25,0,0,0,0,NULL,NULL,NULL,0,1,1,1,1,0,0,0,0,0,0,35883.8,0,0),
('#00000000007',NULL,'zkhb','柱上变压器','紫科环保',250,'辽宁紫科环保科技有限公司','zkhb','特来兹环保设备制造公司（铁岭市铁岭县）','$***********','************','C15248668','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/2f0ac9aa8d004417923dac9b7001b93c.hd','rtmp://rtmp.open.ys7.com/openlive/2f0ac9aa8d004417923dac9b7001b93c','ezopen://open.ys7.com/C15248668/1.hd.live','ezopen://open.ys7.com/C15248668/1.live',NULL,NULL,'123.631421',' 42.134174',NULL,80,NULL,NULL,NULL,'lougang',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-24 10:34:19',20.7,19.9,20.7,20.7,19.9,22.7,22.2,221.5,222.7,220.7,10.48,10.16,20.32,50.01,1.68,1.6,4.16,7.52,1.2,1.28,1.2,3.84,NULL,NULL,NULL,9.04,0.754,0.718,0.938,0.836,0,0,0,0,0,0,49844.1,0,1),
('#00000000008',NULL,'GWLY-BT','柱上变压器','砂砣线44#变压器',100,'国网辽阳供电公司','GWLY-BT','辽宁市文圣区太子岛村 前沙砣子','$4114.23419N','12316.58021E','C15248686','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/b3eea3019ec949378f3de98041397693.hd','rtmp://rtmp.open.ys7.com/openlive/b3eea3019ec949378f3de98041397693','ezopen://open.ys7.com/C15248686/1.hd.live','ezopen://open.ys7.com/C15248686/1.live',NULL,NULL,'123.2761111111111','41.23722222222222',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:42',24.2,24.2,24.9,18.4,20.7,21.7,21.7,237.3,237.3,237.3,46.64,46.88,59.92,50.06,10.36,9.8,13.32,33.52,3.48,4.96,4.56,13.08,NULL,NULL,NULL,36.44,0.937,0.882,0.937,0.92,0,0,0,0,0,0,643034,0,1),
('#00000000009',NULL,'GWLY-BT','柱上变压器','砂砣线54右4右1#变压器',100,'国网辽阳供电公司','GWLY-BT','辽宁市文圣区太子岛村','$***********','************','C15421825','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/df41c44de1cc495f9b08327aaebaa9d2.hd','rtmp://rtmp.open.ys7.com/openlive/df41c44de1cc495f9b08327aaebaa9d2','ezopen://open.ys7.com/C15421825/1.hd.live','ezopen://open.ys7.com/C15421825/1.live',NULL,NULL,'123.28','41.24194444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:30',19.4,19.4,18.9,21.2,21.7,21.7,21.7,236.7,236.7,236.4,38.84,37.12,47.72,50.07,8.68,8.2,10.56,27.52,2.76,3,3.72,9.52,NULL,NULL,NULL,29.28,0.947,0.935,0.937,0.94,0,0,0,0,0,0,510473,0,1),
('#00000000010',NULL,'GWLY-BT','柱上变压器','砂砣线105#变压器',100,'国网辽阳供电公司','GWLY-BT','辽宁市文圣区太子岛村 西沙砣子','$***********','************','175634548','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/d9a0b769f8294d65bff090c9eb7d8f63.hd','rtmp://rtmp01open.ys7.com/openlive/d9a0b769f8294d65bff090c9eb7d8f63.hd','ezopen://open.ys7.com/175634548/1.hd.live','ezopen://open.ys7.com/175634548/1.live',NULL,NULL,'123.24583333333334','41.243611111111115',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:08:52',24.2,23.7,21.7,0,22.2,22.4,21.7,242.6,231.6,239.5,17.12,16.56,11.04,50.05,3.92,3.4,0.92,6.36,0.28,1.24,2.28,3.88,NULL,NULL,NULL,10.64,0.948,0.886,0.362,0.6,0,0,0,0,0,0,280510,0,1),
('#00000000011',NULL,'GWLY-BT','柱上变压器 全绝缘变台','南分线60#变压器',315,'国网辽阳供电公司','GWLY-BT','辽宁市白塔区八一街东段南郊街148-2号','$***********','************','C15248674','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/f613f5209e2546adb0bd793833292a3f.hd','rtmp://rtmp.open.ys7.com/openlive/f613f5209e2546adb0bd793833292a3f','ezopen://open.ys7.com/C15248674/1.hd.live','ezopen://open.ys7.com/C15248674/1.live',NULL,NULL,'123.18888888888888','41.24777777777778',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:48',19.9,19.4,18.9,17.4,17.4,16.9,17.4,240.3,239.8,239.9,4.12,1.56,3,50.06,0.96,0.36,0.64,2,0.08,0.04,0.12,0.12,NULL,NULL,NULL,2.08,0.984,0.962,0.925,0.96,0,0,0,0,0,0,38275.9,0,1),
('#00000000012',NULL,'GWLY-BT','柱上变压器 全绝缘变台','南外线11#变压器',315,'国网辽阳供电公司','GWLY-BT','辽宁市白塔区东文化小学门前 民主路100号','$4115.41752N','12310.00193E','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:10',16.4,16.9,16.4,17.4,17.9,17.9,17.4,234,226.7,225.7,36.96,35.08,46.8,50.05,8.56,7.84,10.44,26.92,0,0.32,0.24,0.08,NULL,NULL,NULL,27.16,0.992,0.989,0.99,0.991,0,0,0,0,0,0,308582,0,1),
('#00000000013',NULL,'nku','柱上变压器 全绝缘变台','测试13',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000014',NULL,'nku','柱上变压器 全绝缘变台','测试14',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000015',NULL,'nku','柱上变压器 全绝缘变台','测试15',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000016',NULL,'nku','柱上变压器 全绝缘变台','测试16',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000017',NULL,'nku','柱上变压器 全绝缘变台','测试17',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000018',NULL,'GWLY-GCL','S13-M.RL-100','10kv温乙线#2右41左67右14',100,'国网辽阳供电公司弓长岭分公司','GWLY-GCL','辽阳市弓长岭区汤河镇瓦子沟三六组','$4104.64247N','12317.45759E','C70685887','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/6e842344af6045eeb6fadb1acc0abff9.hd','rtmp://rtmp.open.ys7.com/openlive/6e842344af6045eeb6fadb1acc0abff9','ezopen://open.ys7.com/C70685887/1.hd.live','ezopen://open.ys7.com/C70685887/1.live',NULL,NULL,'123.29055555555556','41.07666666666667',NULL,40,NULL,NULL,NULL,'rx-lygd/',NULL,'13591925192 ',NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:13',16.2,15.2,0,16.4,16.9,16.4,16.4,224.2,224.7,224.6,3.6,5.16,3.36,50.05,0.72,1.08,0.68,2.56,0.2,0.12,0.2,0.6,NULL,NULL,NULL,2.72,0.92,0.949,0.934,0.936,0,0,0,0,0,0,0,0,1),
('#00000000019',NULL,'nku','柱上变压器 全绝缘变台','测试19',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-08-01 13:07:05',0,0,0,0,0,0,0,0,39.3,0,0,73.84,0,49.96,0,2.88,0,2.88,0,0,0,0,NULL,NULL,NULL,2.88,1,0.999,1,0.999,0,0,0,0,0,0,5266.4,0,1),
('#00000000020',NULL,'nku','柱上变压器 全绝缘变台','测试20',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000021',NULL,'GWLY-GCL','S13-M RL-200/10','10KV温乙线#2右41左111左8',200,'国网辽阳供电公司弓长岭分公司','GWLY-GCL','辽阳市弓长岭区汤河镇瓦子四组北沟台区','$4104.23719N','12318.91777E','C70685884','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/789029392fdb421bb8fae94bcab84b9b.hd','rtmp://rtmp.open.ys7.com/openlive/789029392fdb421bb8fae94bcab84b9b','ezopen://open.ys7.com/C70685884/1.hd.live','ezopen://open.ys7.com/C70685884/1.live',NULL,NULL,'123.315','41.070277777777775',NULL,80,NULL,NULL,NULL,'rx-lygd',NULL,'13591925192',NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:14',19.9,20.7,21.2,18.4,18.4,18.9,20.7,242.2,241.8,242.2,6.4,4.4,2.64,50.05,1.44,0.72,0.56,2.8,0.48,0.72,0.16,1.36,NULL,NULL,NULL,3.28,0.931,0.719,0.928,0.861,0,0,0,0,0,0,0,0,1),
('#00000000022',NULL,'nku','SCB10-10/0.4-1000','10kv石山线北一路支新三分线6#杆',1000,'上海汽车地毯材料(铁岭)有限公司','dtc','腰堡','$***********','************','C07685854','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/7830425e9d32422684402aabcfa57196.hd','rtmp://rtmp.open.ys7.com/openlive/7830425e9d32422684402aabcfa57196','ezopen://open.ys7.com/C70685854/1.hd.live','ezopen://open.ys7.com/C70685854/1.live',NULL,NULL,'123.710446','42.145929',NULL,40,NULL,NULL,NULL,'shdtc',NULL,'13764180358',NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:10:02',0,0,0,0,0,0,0,10120,10130,10120,315.6,303.6,290,50.07,2400,0,2560,5000,1960,0,1200,760,NULL,NULL,NULL,5280,0.756,1,0.88,0.941,0,0,0,0,0,0,11007,0,1),
('#00000000023',NULL,'nku','柱上变压器 全绝缘变台','测试23',315,'南开大学','nku','南开大学综合实验楼','$4212.48063N','12350.81133E','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-03-23 14:42:45',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,1,1),
('#00000000024',NULL,'nku','柱上变压器 全绝缘变台','测试24',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-19 17:50:17',6.4,6.9,6.9,11.9,11.9,12.4,10.2,227.5,226.5,221.9,34.32,27.16,36.2,49.96,7.64,5.96,7.88,21.52,0.32,0,0.12,0.48,NULL,NULL,NULL,22,0.981,0.971,0.979,0.978,0,0,0,0,0,0,52610.3,1,1),
('#00000000025',NULL,'nku','柱上变压器 全绝缘变台','测试25',315,'南开大学','dtc1','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-08-12 15:00:53',26.9,26.9,27.4,26.9,26.9,26.4,26.9,414.2,415.7,415,0,0.56,0.56,50.05,0.04,0,0.2,0.24,0,0,0.08,0.08,NULL,NULL,NULL,0.24,0.839,1,0.878,1,0,0,0,0,0,0,280.16,0,1),
('#00000000026',NULL,'nku','柱上变压器 全绝缘变台','测试26',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:09:57',15.2,0,0,0,16.4,16.2,15.2,240.5,0,240.5,0,0,0,50.07,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,1,1,1,1,0,0,0,0,0,0,0,0,1),
('#00000000027',NULL,'nku','柱上变压器 全绝缘变台','测试27',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-10-30 10:10:00',15.7,15.7,15.2,15.7,16.2,15.7,15.7,240.4,0,240.2,0,0,0,50.07,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,1,1,1,1,0,0,0,0,0,0,0,0,1),
('#00000000028',NULL,'nku','柱上变压器 全绝缘变台','测试28',315,'南开大学','nku','南开大学综合实验楼','$***********','************','C36830877','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp01open.ys7.com/openlive/68ee527f415f454da92fbdb9eb95c5dc.hd','rtmp://rtmp01open.ys7.com/openlive/68ee527f415f454da92fbdb9eb95c5dc','ezopen://open.ys7.com/C36830877/1.hd.live','ezopen://open.ys7.com/C36830877/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-12-19 09:00:42',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,1,1),
('000000000000001',NULL,'nku','柱上变压器 全绝缘变台','测试15',315,'南开大学','nku','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'lygd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-30 15:00:04',0,0,0,0,0,0,0,222.1,222.4,222.3,0.3,0,0.3,49.95,50,0,50,110,30,0,40,80,NULL,NULL,NULL,140,0.857,1,0.793,0.844,0,0,0,0,0,0,0.138,0,1),
('863920031465953',NULL,'nku','柱上变压器 全绝缘变台','终端6',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-06-21 08:31:26',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,1),
('863920031467884',NULL,'nku','柱上变压器 全绝缘变台','终端7',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-16 10:38:28',0,0,0,0,0,0,0,240.2,239.5,241.5,5.8,5.6,5.5,49.97,1400,1350,1320,4080,0,0,0,20,NULL,NULL,NULL,4080,1,1,1,0.999,0,0,0,0,0,0,0.816,0,1),
('863920031475440',NULL,'nku','柱上变压器 全绝缘变台','终端3',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-04 14:58:28',17.8,0,0,17.9,17.6,17.7,17.5,239.3,238.3,240.2,6.4,7,6.1,50.05,1540,1670,1470,4690,0,10,0,30,NULL,NULL,NULL,4690,1,1,1,1,0,0,0,0,0,0,0.234,0,1),
('863920031478980',NULL,'nku','柱上变压器 全绝缘变台','终端5',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-04 16:20:37',17.7,27,25,25.2,0,25.5,17.3,238.6,238.2,240.3,5.9,6,6,49.97,1400,1430,1430,4280,0,0,0,20,NULL,NULL,NULL,4280,1,1,1,1,0,0,0,0,0,0,4.136,0,1),
('863920031483600',NULL,'nku','柱上变压器 全绝缘变台','终端1',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-07-23 16:34:40',28.2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,1),
('863920031483659',NULL,'nku','柱上变压器 全绝缘变台','终端4',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-04 15:36:43',-80.1,25.1,27.8,19.8,27.1,20.5,0,238.3,238,239.6,6.9,6.6,7,50.05,1630,1570,1680,4900,10,0,10,30,NULL,NULL,NULL,4900,1,1,1,1,0,0,0,0,0,0,4.383,0,1),
('863920031484145',NULL,'nku','柱上变压器 全绝缘变台','终端2',315,'南开大学','cs','南开大学综合实验楼','************','*************','C15248680','at.6znq5d5jdv58rv4hb9m2yfsgbg71gam9-2dmp1tfjm0-144k88q-l2butajyv','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5.hd','rtmp://rtmp.open.ys7.com/openlive/a82576af098a4c6fbb5da7576bae02e5','ezopen://open.ys7.com/C15248680/1.hd.live','ezopen://open.ys7.com/C15248680/1.live',NULL,NULL,'123.1661111111111','41.25694444444444',NULL,40,NULL,NULL,NULL,'cs',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-04 14:32:30',0,0,0,18,17.6,17.6,17.8,237.2,236.8,239,0,0,0.3,50.03,0,0,70,70,0,0,0,0,NULL,NULL,NULL,70,1,1,1,1,0,0,0,0,0,0,0.143,0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;