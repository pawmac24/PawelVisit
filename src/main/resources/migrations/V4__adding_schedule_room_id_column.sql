ALTER TABLE schedule ADD COLUMN room_id BIGINT NOT NULL;
ALTER TABLE schedule ADD INDEX `k_schedule_room_id` (room_id);
ALTER TABLE schedule ADD CONSTRAINT `fk_schedule_room_id` FOREIGN KEY  (`room_id`) REFERENCES `room` (`room_id`);
