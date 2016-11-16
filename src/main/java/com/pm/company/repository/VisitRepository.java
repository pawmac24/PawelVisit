package com.pm.company.repository;

import com.pm.company.model.Visit;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

	@Query("SELECT COUNT(v) FROM Visit v WHERE v.start <= :end AND :start <= v.end AND v.employee.employeeId = :employeeId and v.active = true")
	Long countEmployeesOverlappingVisits(@Param("start") Date start, @Param("end") Date end,
			@Param("employeeId") Long employeeId);

	@Query("SELECT COUNT(v) FROM Visit v WHERE v.start <= :end AND :start <= v.end AND v.employee.employeeId = :employeeId AND v.visitId <> :visitId and v.active = true")
	Long countEmployeesOverlappingVisitsOmitting(@Param("start") Date start, @Param("end") Date end,
			@Param("employeeId") Long employeeId, @Param("visitId") Long visitId);
	
	@Query("SELECT COUNT(v) FROM Visit v WHERE v.start <= :end AND :start <= v.end AND v.customer.customerId = :customerId and v.active = true")
	Long countCustomerOverlappingVisits(@Param("start") Date start, @Param("end") Date end,
			@Param("customerId") Long customerId);
	
	@Query("SELECT COUNT(v) FROM Visit v WHERE v.start <= :end AND :start <= v.end AND v.customer.customerId = :customerId AND v.visitId <> :visitId and v.active = true")
	Long countCustomerOverlappingVisitsOmitting(@Param("start") Date start, @Param("end") Date end,
			@Param("customerId") Long customerId, @Param("visitId") Long visitId);

	@Query("SELECT COUNT(v) FROM Visit v WHERE v.employee.employeeId = :employeeId")
	Long countVisitsByEmployeeId(@Param("employeeId") Long employeeId);
}
