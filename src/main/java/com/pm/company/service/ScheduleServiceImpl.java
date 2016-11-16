package com.pm.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pm.company.dto.ScheduleCreateUpdateDTO;
import com.pm.company.dto.primarykey.SchedulePrimaryKeyDTO;
import com.pm.company.dto.result.ScheduleResultDTO;
import com.pm.company.model.Room;
import com.pm.company.repository.RoomRepository;
import com.pm.company.utils.DateUtils;
import com.pm.company.dto.ScheduleCreateDTO;
import com.pm.company.dto.ScheduleUpdateDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.repository.ScheduleRepository;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.company.model.Employee;
import com.pm.company.model.Schedule;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.validator.business.BusinessValidator;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	public final static Logger logger = Logger.getLogger(ScheduleServiceImpl.class);

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BusinessValidator<Schedule, Long> scheduleValidator;

	@Autowired
	private Mapper mapper;

	@Override
	public SchedulePrimaryKeyDTO create(ScheduleCreateDTO scheduleDTO) throws BusinessValidationException {
		Employee employee = employeeRepo.findOne(scheduleDTO.getEmployeeId());
		Room room = roomRepository.findOne(scheduleDTO.getRoomId());
		
		Schedule schedule = mapper.map(scheduleDTO, Schedule.class);
		schedule.setEmployee(employee);
		schedule.setRoom(room);
		scheduleValidator.validateCreate(schedule);

		schedule = scheduleRepository.save(schedule);
		SchedulePrimaryKeyDTO schedulePrimaryKeyDTO = new SchedulePrimaryKeyDTO(schedule.getScheduleId());

		return schedulePrimaryKeyDTO;
	}

	@Override
	public void update(ScheduleUpdateDTO scheduleDTO, Long scheduleId) throws BusinessValidationException {
		Schedule schedule = getScheduleById(scheduleId);
		mapper.map(scheduleDTO, schedule);
		
		scheduleValidator.validateUpdate(schedule);
		
		schedule = scheduleRepository.save(schedule);
		logger.info("update " + schedule);
	}


	@Override
	public ScheduleResultDTO find(Long scheduleId) throws BusinessValidationException {
		Schedule schedule = getScheduleById(scheduleId);
		ScheduleResultDTO scheduleResultDTO = mapper.map(schedule, ScheduleResultDTO.class);
		return scheduleResultDTO;
	}

	@Override
	public void delete(Long scheduleId) throws BusinessValidationException {
		scheduleValidator.ensureThatExists(scheduleId);
		scheduleRepository.delete(scheduleId);
	}

	@Override
	public List<ScheduleResultDTO> findAll() {
		List<Schedule> scheduleList = scheduleRepository.findAll();
		List<ScheduleResultDTO> scheduleResultDTOList = new ArrayList<>();

		for (Schedule schedule : scheduleList) {
			scheduleResultDTOList.add(mapper.map(schedule, ScheduleResultDTO.class));
		}
		return scheduleResultDTOList;
	}

	@Override
	public List<ScheduleResultDTO> findAllByEmployeeId(Long employeeId) {
		List<Schedule> scheduleList = scheduleRepository.findByEmployeeId(employeeId);
		List<ScheduleResultDTO> scheduleResultDTOList = new ArrayList<>();

		for (Schedule schedule : scheduleList) {
			scheduleResultDTOList.add(mapper.map(schedule, ScheduleResultDTO.class));
		}
		return scheduleResultDTOList;
	}

	@Override
	public List<ScheduleResultDTO> findAllByEmployeeIdAndDates(Long employeeId, long dateFromTimestamp, long dateToTimestamp) {

		Date dateFrom = new Date(dateFromTimestamp);
		Date dateTo = DateUtils.asDate(DateUtils.asLocalDate(new Date(dateToTimestamp)).plusDays(1));

		List<Schedule> scheduleList = scheduleRepository.findByEmployeeIdAndDates(employeeId, dateFrom, dateTo);
		List<ScheduleResultDTO> scheduleResultDTOList = new ArrayList<>();

		for (Schedule schedule : scheduleList) {
			scheduleResultDTOList.add(mapper.map(schedule, ScheduleResultDTO.class));
		}
		return scheduleResultDTOList;
	}

	@Override
	public boolean exists(Long scheduleId) {
		return scheduleRepository.exists(scheduleId);
	}

	@Override
	public boolean periodOverlapsWithExistingSchedules(Date start, Date stop, Long employeeId) {
		return scheduleRepository.countOverlappingSchedules(start,
				stop, employeeId) > 0L;
	}
	
	@Override
	public boolean periodOverlapsWithOtherExistingSchedules(Date start, Date stop,
			Long employeeId, Long scheduleId){
		return scheduleRepository.countOverlappingSchedulesOmittingSchedule(start,
				stop, employeeId, scheduleId) > 0L;
	}

	@Override
	public void createOrUpdate(List<ScheduleCreateUpdateDTO> scheduleDTOList) throws BusinessValidationException {
		logger.info("createOrUpdate = " + scheduleDTOList);
		List<Schedule> scheduleList = new ArrayList<>();

		for (ScheduleCreateUpdateDTO scheduleDTO : scheduleDTOList) {
			scheduleList.add(mapper.map(scheduleDTO, Schedule.class));
		}
		for(Schedule schedule: scheduleList){
			if(schedule.getScheduleId() != null){
				scheduleValidator.validateUpdate(schedule);
				schedule = scheduleRepository.save(schedule);
				logger.info("update " + schedule);
			}
			else {
				scheduleValidator.validateCreate(schedule);
				schedule = scheduleRepository.save(schedule);
				logger.info("create " + schedule);
			}
		}
	}

	private Schedule getScheduleById(Long scheduleId) throws BusinessValidationException {
		scheduleValidator.ensureThatExists(scheduleId);
		return scheduleRepository.findOne(scheduleId);
	}
}
