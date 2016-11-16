package com.pm.company.service;

import java.util.Date;
import java.util.List;

import com.pm.company.dto.ScheduleCreateUpdateDTO;
import com.pm.company.dto.primarykey.SchedulePrimaryKeyDTO;
import com.pm.company.dto.result.ScheduleResultDTO;
import com.pm.company.dto.ScheduleCreateDTO;
import com.pm.company.dto.ScheduleUpdateDTO;
import com.pm.company.exception.BusinessValidationException;

public interface ScheduleService {

	SchedulePrimaryKeyDTO create(ScheduleCreateDTO scheduleDTO) throws BusinessValidationException;

	void update(ScheduleUpdateDTO scheduleDTO, Long scheduleId) throws BusinessValidationException;
	
	ScheduleResultDTO find(Long scheduleId) throws BusinessValidationException;

	void delete(Long scheduleId) throws BusinessValidationException;

	List<ScheduleResultDTO> findAll();

	List<ScheduleResultDTO> findAllByEmployeeId(Long employeeId);

	List<ScheduleResultDTO> findAllByEmployeeIdAndDates(Long employeeId, long dateFromTimestamp, long dateToTimestamp);

	boolean exists(Long scheduleId);

	boolean periodOverlapsWithExistingSchedules(Date start, Date stop, Long employeeId);

	boolean periodOverlapsWithOtherExistingSchedules(Date start, Date stop,
			Long employeeId, Long scheduleId);

	void createOrUpdate(List<ScheduleCreateUpdateDTO> scheduleDTOList) throws BusinessValidationException;
}
