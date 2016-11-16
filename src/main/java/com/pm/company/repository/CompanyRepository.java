package com.pm.company.repository;

import com.pm.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
}
