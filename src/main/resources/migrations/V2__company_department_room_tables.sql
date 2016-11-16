DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `company_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `city` varchar(40) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `street` varchar(55) NOT NULL,
  `number` varchar(10) NOT NULL,
  PRIMARY KEY (`company_id`)
);

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `department_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `city` varchar(40) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `street` varchar(55) NOT NULL,
  `number` varchar(10) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  PRIMARY KEY (`department_id`),
  KEY `k_department_company_id` (`company_id`),
  CONSTRAINT `fk_department_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
);

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` varchar(5) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  PRIMARY KEY (`room_id`),
  KEY `k_room_department_id` (`department_id`),
  CONSTRAINT `fk_room_department_id` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
);
