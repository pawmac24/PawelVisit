ALTER TABLE employee ADD COLUMN department_id BIGINT NOT NULL;
ALTER TABLE employee ADD INDEX `k_employee_department_id` (department_id);
ALTER TABLE employee ADD CONSTRAINT `fk_employee_department_id` FOREIGN KEY  (`department_id`) REFERENCES `department` (`department_id`);
