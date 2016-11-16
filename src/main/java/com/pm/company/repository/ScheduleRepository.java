package com.pm.company.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pm.company.model.Schedule;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("SELECT COUNT(s) FROM Schedule s WHERE s.start < :stop AND :start < s.stop AND s.employee.employeeId = :employeeId")
	Long countOverlappingSchedules(@Param("start") Date start,
			@Param("stop") Date stop, @Param("employeeId") Long employeeId);
	
	@Query("SELECT COUNT(s) FROM Schedule s WHERE s.start < :stop AND :start < s.stop AND s.employee.employeeId = :employeeId AND s.scheduleId <> :scheduleId")
	Long countOverlappingSchedulesOmittingSchedule(@Param("start") Date start,
			@Param("stop") Date stop, @Param("employeeId") Long employeeId, @Param("scheduleId") Long scheduleId);
	
	@Query("SELECT COUNT(s) FROM Schedule s WHERE s.start <= :start AND s.stop >= :stop AND s.employee.employeeId = :employeeId")
	Long countContainingPeriod(@Param("start") Date start, @Param("stop") Date stop,
			@Param("employeeId") Long employeeId);

	@Query("SELECT COUNT(s) FROM Schedule s WHERE s.employee.employeeId = :employeeId")
	Long countSchedulesByEmployeeId(@Param("employeeId") Long employeeId);

	@Query("SELECT s FROM Schedule s WHERE s.employee.employeeId = :employeeId order by s.start asc, s.stop asc")
	List<Schedule> findByEmployeeId(@Param("employeeId") Long employeeId);

	@Query("SELECT s FROM Schedule s WHERE s.employee.employeeId = :employeeId AND s.start >= :dateFrom AND s.stop < :dateTo order by s.start asc, s.stop asc")
	List<Schedule> findByEmployeeIdAndDates(@Param("employeeId")Long employeeId, @Param("dateFrom")Date dateFrom, @Param("dateTo")Date dateTo);
}
