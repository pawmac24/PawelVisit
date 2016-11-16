DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `employee_id` bigint(20),
  PRIMARY KEY (`user_id`),
  KEY `k_user_employee_id` (`employee_id`),
  CONSTRAINT `fk_user_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
);

