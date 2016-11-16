package com.pm.company.repository;

import com.pm.company.model.XXXEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pmackiewicz on 2016-02-03.
 */
@Repository
public interface XXXRepository extends JpaRepository<XXXEmployee, Long> {
}
