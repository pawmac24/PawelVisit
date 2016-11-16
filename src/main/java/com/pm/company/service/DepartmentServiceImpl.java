package com.pm.company.service;

import com.pm.company.dto.DepartmentCreateDTO;
import com.pm.company.dto.result.DepartmentResultDTO;
import com.pm.company.dto.DepartmentUpdateDTO;
import com.pm.company.dto.primarykey.DepartmentPrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Company;
import com.pm.company.model.Department;
import com.pm.company.repository.CompanyRepository;
import com.pm.company.repository.DepartmentRepository;
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
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{

    public final static Logger logger = Logger.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private BusinessValidator<Department, Long> departmentValidator;

    @Autowired
    private Mapper mapper;


    @Override
    public DepartmentPrimaryKeyDTO create(DepartmentCreateDTO departmentDTO) throws BusinessValidationException {
        Company company = companyRepo.findOne(departmentDTO.getCompanyId());
        Department department = mapper.map(departmentDTO, Department.class);
        department.setCompany(company);

        department = departmentRepository.save(department);
        logger.info("create " + department);
        DepartmentPrimaryKeyDTO departmentPKeyDTO = new DepartmentPrimaryKeyDTO(department.getDepartmentId());
        return departmentPKeyDTO;
    }

    @Override
    public void update(Long departmentId, DepartmentUpdateDTO departmentDTO) throws BusinessValidationException {
        Department department = getDepartmentById(departmentId);
        mapper.map(departmentDTO, department);
        departmentValidator.validateUpdate(department);

        department = departmentRepository.save(department);
        logger.info("update " + department);
    }

    @Override
    public DepartmentResultDTO find(Long departmentId) throws BusinessValidationException {
        Department department = getDepartmentById(departmentId);
        String companyName = department.getCompany().getName();
        DepartmentResultDTO departmentResultDTO = mapper.map(department, DepartmentResultDTO.class);
        departmentResultDTO.setCompanyName(companyName);
        return departmentResultDTO;
    }

    @Override
    public void delete(Long departmentId) throws BusinessValidationException {
        departmentValidator.validateDelete(departmentId);
        departmentRepository.delete(departmentId);
    }

    @Override
    public List<DepartmentResultDTO> findAll() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentResultDTO> departmentResultDTOList = new ArrayList<>();

        for (Department department : departmentList) {
            departmentResultDTOList.add(mapper.map(department, DepartmentResultDTO.class));
        }
        return departmentResultDTOList;
    }

    @Override
    public List<DepartmentResultDTO> findAllByCompanyId(Long companyId) {
        List<Department> departmentList = departmentRepository.findByCompanyId(companyId);
        List<DepartmentResultDTO> departmentResultDTOList = new ArrayList<>();

        for (Department department : departmentList) {
            departmentResultDTOList.add(mapper.map(department, DepartmentResultDTO.class));
        }
        return departmentResultDTOList;
    }

    @Override
    public boolean exists(Long companyId) {
        return departmentRepository.exists(companyId);
    }

    private Department getDepartmentById(Long departmentId) throws BusinessValidationException {
        Department department = departmentRepository.findOne(departmentId);
        if(department == null){
            throw new BusinessValidationException("Department with id: " + departmentId + " does not exist");
        }
        return department;
    }
}
