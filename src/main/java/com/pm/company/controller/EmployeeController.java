package com.pm.company.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.pm.company.dto.EmployeeCreateDTO;
import com.pm.company.dto.EmployeeUpdateDTO;
import com.pm.company.dto.primarykey.EmployeePrimaryKeyDTO;
import com.pm.company.dto.result.EmployeeResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.model.Person;
import com.pm.company.service.EmployeeService;
import com.pm.company.validator.field.FieldValidator;

/**
 * Created by pmackiewicz on 2015-10-20.
 */
@RequestMapping("employees")
@RestController
public class EmployeeController {

    public final static Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
	private FieldValidator<Person> employeeValidator;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeResultDTO> findAllEmployees() {
        List<EmployeeResultDTO> employeeResultDTOList = employeeService.findAll();
        logger.info("Find " + employeeResultDTOList);
        return employeeResultDTOList;
    }

    @RequestMapping(value = "/list/{departmentId}", method = RequestMethod.GET)
    public @ResponseBody
    List<EmployeeResultDTO> findAllEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<EmployeeResultDTO> employeeResultDTOList = employeeService.findAllByDepartmentId(departmentId);
        logger.info("Find " + employeeResultDTOList);
        return employeeResultDTOList;
    }

    @RequestMapping(value = "/get/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<?> findEmployeeById(@PathVariable Long employeeId) throws BusinessValidationException {
        EmployeeResultDTO employeeResultDTO = employeeService.find(employeeId);
        logger.info("Find " + employeeResultDTO);
        return new ResponseEntity<>(employeeResultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/add",  method = RequestMethod.POST)
    public ResponseEntity<EmployeePrimaryKeyDTO> addEmployee(@Valid @RequestBody EmployeeCreateDTO employeeDTO)
            throws CustomValidationException, BusinessValidationException {
    	employeeValidator.validate(employeeDTO);
    	EmployeePrimaryKeyDTO employeePKeyDTO = employeeService.create(employeeDTO);
        logger.info("Add " + employeePKeyDTO.getId());
        return new ResponseEntity<>(employeePKeyDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{employeeId}",  method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId,
                                            @Valid @RequestBody EmployeeUpdateDTO employeeDTO)
            throws BusinessValidationException, CustomValidationException {
    	employeeValidator.validate(employeeDTO);
        employeeService.update(employeeId, employeeDTO);
        logger.info("Update employee " + employeeDTO + " with id = " + employeeId + " was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long employeeId) throws BusinessValidationException {
        employeeService.delete(employeeId);
        logger.info("Delete employee with id = " + employeeId + " was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
