package com.pm.company.controller;

import java.util.List;

import javax.validation.Valid;

import com.pm.company.dto.ScheduleCreateUpdateDTO;
import com.pm.company.dto.ScheduleCreateDTO;
import com.pm.company.dto.ScheduleUpdateDTO;
import com.pm.company.dto.primarykey.SchedulePrimaryKeyDTO;
import com.pm.company.dto.result.ScheduleResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.ScheduleService;
import com.pm.company.validator.field.FieldValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "schedules")
public class ScheduleController {

	public static final Logger logger = Logger.getLogger(ScheduleController.class);

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private FieldValidator<ScheduleCreateDTO> scheduleCrtValidator;
	
	@Autowired
	private FieldValidator<ScheduleUpdateDTO> scheduleUpdtValidator;

	@Autowired
	private FieldValidator<ScheduleCreateUpdateDTO> scheduleCrtUpdtValidator;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<ScheduleResultDTO> findAllSchedules() {
		logger.info("Find all schedules");
		List<ScheduleResultDTO> scheduleResultDTOList = scheduleService.findAll();
		logger.info("Find " + scheduleResultDTOList);
		return scheduleResultDTOList;
	}

	@RequestMapping(value = "/list/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody List<ScheduleResultDTO> findAllSchedulesByEmployeeId(@PathVariable Long employeeId) {
		logger.info("Find all schedules by employeeId");
		List<ScheduleResultDTO> scheduleResultDTOList = scheduleService.findAllByEmployeeId(employeeId);
		logger.info("Find " + scheduleResultDTOList);
		return scheduleResultDTOList;
	}


	@RequestMapping(value = "/list/{employeeId}/{dateFrom}/{dateTo}", method = RequestMethod.GET)
	public @ResponseBody List<ScheduleResultDTO> findAllSchedulesByEmployeeIdAndDates(@PathVariable Long employeeId,
																					  @PathVariable("dateFrom") long dateFromTimestamp,
																					  @PathVariable("dateTo") long dateToTimestamp) {
		logger.info("Find all schedules by employeeId and dates");
		List<ScheduleResultDTO> scheduleResultDTOList = scheduleService.findAllByEmployeeIdAndDates(employeeId, dateFromTimestamp, dateToTimestamp);
		logger.info("Find " + scheduleResultDTOList);
		return scheduleResultDTOList;
	}


	@RequestMapping(value = "/get/{scheduleId}", method = RequestMethod.GET)
	public ResponseEntity<?> findScheduleById(@PathVariable Long scheduleId) throws BusinessValidationException {
		ScheduleResultDTO scheduleResultDTO = scheduleService.find(scheduleId);
		logger.info("Find schedule: " + scheduleResultDTO);
		return new ResponseEntity<>(scheduleResultDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> createSchedule(@Valid @RequestBody ScheduleCreateDTO scheduleDTO)
			throws CustomValidationException, BusinessValidationException {
		scheduleCrtValidator.validate(scheduleDTO);
		SchedulePrimaryKeyDTO schedulePKeyDTO = scheduleService.create(scheduleDTO);
		logger.info("Add schedule with " + schedulePKeyDTO.getId());
		return new ResponseEntity<>(schedulePKeyDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{scheduleId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId,
			@Valid @RequestBody ScheduleUpdateDTO scheduleDTO)
					throws CustomValidationException, BusinessValidationException {
		scheduleUpdtValidator.validate(scheduleDTO);
		scheduleService.update(scheduleDTO, scheduleId);
		logger.info("Updated schedule with id : " + scheduleId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/createUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> createSchedule(@Valid @RequestBody List<ScheduleCreateUpdateDTO> scheduleDTOList)
			throws CustomValidationException, BusinessValidationException {
		for (ScheduleCreateUpdateDTO scheduleDTO: scheduleDTOList) {
			scheduleCrtUpdtValidator.validate(scheduleDTO);
			logger.info("Add schedules " + scheduleDTO);
		}
		scheduleService.createOrUpdate(scheduleDTOList);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{scheduleId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) throws BusinessValidationException {
		scheduleService.delete(scheduleId);
		logger.info("Deleted schedule (ID: " + scheduleId + ")");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
