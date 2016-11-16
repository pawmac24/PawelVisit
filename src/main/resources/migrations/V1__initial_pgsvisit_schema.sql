CREATE DATABASE  IF NOT EXISTS `pgsvisit` ;
USE `pgsvisit`;


DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_of_birth` date NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `pesel` varchar(11) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `uk_customer_pesel` (`pesel`)
);

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(80) DEFAULT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  PRIMARY KEY (`employee_id`)
);

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `schedule_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `place` varchar(100) NOT NULL,
  `start` datetime NOT NULL,
  `stop` datetime NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `k_schedule_employee_id` (`employee_id`),
  CONSTRAINT `fk_schedule_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
);

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
CREATE TABLE `visit` (
  `visit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `end` datetime NOT NULL,
  `start` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`visit_id`),
  KEY `k_visit_customer_id` (`customer_id`),
  KEY `k_visit_employee_id` (`employee_id`),
  CONSTRAINT `fk_visit_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_visit_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
);