package com.pm.company.validator.business;

import com.pm.company.repository.EmployeeRepository;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Schedule;
import com.pm.company.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleBusinessValidator implements BusinessValidator<Schedule, Long> {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public void validateCreate(Schedule validatedObject) throws BusinessValidationException {
		if (!employeeRepo.exists(validatedObject.getEmployee().getEmployeeId())) {
			throw new BusinessValidationException(
					"Employee with id: " + validatedObject.getEmployee().getEmployeeId() + " does not exist");
		}
		if (scheduleService.periodOverlapsWithExistingSchedules(validatedObject.getStart(), validatedObject.getStop(),
				validatedObject.getEmployee().getEmployeeId())) {
			throw new BusinessValidationException("Schedule overlaps with other existing schedules of this employee");
		}
	}

	@Override
	public void validateUpdate(Schedule schedule) throws BusinessValidationException {
		if (scheduleService.periodOverlapsWithOtherExistingSchedules(schedule.getStart(), schedule.getStop(),
				schedule.getEmployee().getEmployeeId(), schedule.getScheduleId())) {
			throw new BusinessValidationException("Schedule overlaps with other existing schedules of this employee");
		}
	}

	@Override
	public void validateDelete(Long scheduleId) throws BusinessValidationException {
	}

	@Override
	public void ensureThatExists(Long scheduleId) throws BusinessValidationException {
		if (!scheduleService.exists(scheduleId)) {
			throw new BusinessValidationException("Schedule with id: " + scheduleId + " does not exist");
		}
	}

}
