package com.pm.company.repository;

import com.pm.company.model.ExampleTestEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pmackiewicz on 2016-02-03.
 */
@Repository
public interface ExampleTestRepository extends JpaRepository<ExampleTestEmployee, Long> {
}
