package com.pm.company.repository;

import com.pm.company.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.departmentId = :departmentId")
    Long countEmployees(@Param("departmentId") Long departmentId);

    @Query("SELECT e FROM Employee e WHERE e.department.departmentId = :departmentId")
    List<Employee> findByDepartmentId(@Param("departmentId") Long departmentId);
}
