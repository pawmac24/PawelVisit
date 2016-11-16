package com.pm.company.repository;

import com.pm.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by pmackiewicz on 2015-12-02.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.employee IS NOT NULL AND u.employee.employeeId = :employeeId")
    Long countUsers(@Param("employeeId") Long employeeId);
}
