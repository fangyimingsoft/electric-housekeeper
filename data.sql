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

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`id`,`name`,`code`,`parent_id`) values 
(1,'南开大学',NULL,NULL),
(2,'上海汽车地毯(铁岭)汽车材料有限公司',NULL,NULL),
(3,'国网辽阳供电公司',NULL,NULL),
(4,'国网辽阳供电公司弓长岭分公司',NULL,NULL),
(5,'国网铁岭供电公司',NULL,NULL),
(6,'铁岭市政管修处',NULL,NULL),
(7,'辽宁紫科环保科技有限公司',NULL,NULL);

/*Data for the table `sys_log` */

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`name`,`tel`,`role`,`crt_time`,`upt_time`,`status`) values 
(1,'admin','21232f297a57a5a743894a0e4a801fc3',NULL,NULL,NULL,NULL,NULL,1);

/*Data for the table `t_data` */

/*Data for the table `t_device` */

/*Data for the table `t_threshold` */

insert  into `t_threshold`(`id`,`type_code`,`value`,`is_global`,`device_code`,`update_time`) values 
(1,'current_over_load',10,1,NULL,'2019-12-08 14:50:35'),
(2,'over_temperature',80,1,NULL,'2019-12-08 14:51:43'),
(3,'low_voltage',200,1,NULL,'2019-12-08 15:45:17'),
(4,'phase_unbalance',20,1,NULL,'2019-12-08 15:45:20'),
(5,'electric_increase',10,1,NULL,'2019-12-08 14:53:48'),
(6,'harmonic_over_limit',10,1,NULL,'2019-12-08 15:45:23');

/*Data for the table `t_warning` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
