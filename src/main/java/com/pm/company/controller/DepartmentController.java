package com.pm.company.controller;

import com.pm.company.dto.DepartmentCreateDTO;
import com.pm.company.dto.result.DepartmentResultDTO;
import com.pm.company.dto.DepartmentUpdateDTO;
import com.pm.company.dto.primarykey.DepartmentPrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.DepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@RestController
@RequestMapping(value = "departments")
public class DepartmentController {

    public final static Logger logger = Logger.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<DepartmentResultDTO> findAllDepartments() {
        List<DepartmentResultDTO> departmentResultDTOList = departmentService.findAll();
        logger.info("Find " + departmentResultDTOList);
        return departmentResultDTOList;
    }

    @RequestMapping(value = "/list/{companyId}", method = RequestMethod.GET)
    public @ResponseBody
    List<DepartmentResultDTO> findAllDepartmentsByCompanyId(@PathVariable Long companyId) {
        List<DepartmentResultDTO> departmentResultDTOList = departmentService.findAllByCompanyId(companyId);
        logger.info("Find " + departmentResultDTOList);
        return departmentResultDTOList;
    }

    @RequestMapping(value = "/get/{departmentId}", method = RequestMethod.GET)
    public ResponseEntity<?> findDepartmentById(@PathVariable Long departmentId) throws BusinessValidationException {
        DepartmentResultDTO departmentResultDTO = departmentService.find(departmentId);
        logger.info("Find " + departmentResultDTO);
        return new ResponseEntity<>(departmentResultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentCreateDTO departmentDTO) throws CustomValidationException,
            BusinessValidationException {
        DepartmentPrimaryKeyDTO departmentPKeyDTO = departmentService.create(departmentDTO);
        logger.info("Add department with " + departmentPKeyDTO.getId());
        return new ResponseEntity<>(departmentPKeyDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{departmentId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody DepartmentUpdateDTO departmentDTO)
            throws CustomValidationException, BusinessValidationException {
        departmentService.update(departmentId, departmentDTO);
        logger.info("Update department with id " + departmentId + " was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{departmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Long departmentId) throws BusinessValidationException {
        departmentService.delete(departmentId);
        logger.info("Delete by id = " + departmentId + "successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
