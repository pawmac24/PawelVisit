package com.pm.company.service;

import com.pm.company.dto.CompanyCreateDTO;
import com.pm.company.dto.CompanyUpdateDTO;
import com.pm.company.dto.primarykey.CompanyPrimaryKeyDTO;
import com.pm.company.dto.result.CompanyResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Company;
import com.pm.company.repository.CompanyRepository;
import com.pm.company.validator.business.BusinessValidator;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService{

    public final static Logger logger = Logger.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BusinessValidator<Company, Long> companyValidator;

    @Autowired
    private Mapper mapper;

    @Override
    public CompanyPrimaryKeyDTO create(CompanyCreateDTO companyDTO) throws BusinessValidationException {
        Company company = mapper.map(companyDTO, Company.class);
        companyValidator.validateCreate(company);

        company = companyRepository.save(company);
        logger.info("create " + company);
        CompanyPrimaryKeyDTO companyPKeyDTO = new CompanyPrimaryKeyDTO(company.getCompanyId());
        return companyPKeyDTO;
    }

    @Override
    public void update(Long companyId, CompanyUpdateDTO companyDTO) throws BusinessValidationException {
        Company company = getCompanyById(companyId);
        mapper.map(companyDTO, company);
        companyValidator.validateUpdate(company);

        company = companyRepository.save(company);
        logger.info("update " + company);
    }

    @Override
    public CompanyResultDTO find(Long companyId) throws BusinessValidationException {
        Company company = getCompanyById(companyId);
        CompanyResultDTO companyResultDTO = mapper.map(company, CompanyResultDTO.class);
        return companyResultDTO;
    }

    private Company getCompanyById(Long companyId) throws BusinessValidationException {
        Company company = companyRepository.findOne(companyId);
        if(company == null) {
            throw new BusinessValidationException("Company with id: " + companyId + " does not exist");
        }
        return company;
    }

    @Override
    public void delete(Long companyId) throws BusinessValidationException {
        companyValidator.validateDelete(companyId);
        companyRepository.delete(companyId);
    }

    @Override
    public List<CompanyResultDTO> findAll() {
        List<Company> customerList = companyRepository.findAll();
        List<CompanyResultDTO> customerResultDTOList = new ArrayList<>();

        for (Company customer : customerList) {
            customerResultDTOList.add(mapper.map(customer, CompanyResultDTO.class));
        }
        return customerResultDTOList;
    }

    @Override
    public boolean exists(Long companyId) {
        return companyRepository.exists(companyId);
    }
}
