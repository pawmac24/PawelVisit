package com.pm.company.repository;

import com.pm.company.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{

    @Query("SELECT COUNT(d) FROM Department d WHERE d.company.companyId = :companyId")
    Long countDepartments(@Param("companyId") Long companyId);

    @Query("SELECT d FROM Department d WHERE d.company.companyId = :companyId")
    List<Department> findByCompanyId(@Param("companyId") Long companyId);
}
