package com.pm.company.validator.helper;

import java.util.Date;

import com.pm.company.exception.BusinessValidationException;
import com.pm.company.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisitValidationHelper {

	@Autowired
	private ScheduleRepository scheduleRepo;
	
	public void validateAgainstSchedule(Date start, Date end, Long employeeId) throws BusinessValidationException {
		Long matchingSchedulesCnt = scheduleRepo.countContainingPeriod(start, end,
				employeeId);
		if (matchingSchedulesCnt < 1L) {
			throw new BusinessValidationException("There is no matching schedule for given employee");
		}
		if (matchingSchedulesCnt > 1L) {
			throw new BusinessValidationException(
					"Visit should be contained within precisely one schedule of given employee");
		}
	}
	
}
