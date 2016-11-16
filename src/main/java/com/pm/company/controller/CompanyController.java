package com.pm.company.controller;

import com.pm.company.dto.CompanyCreateDTO;
import com.pm.company.dto.CompanyUpdateDTO;
import com.pm.company.dto.primarykey.CompanyPrimaryKeyDTO;
import com.pm.company.dto.result.CompanyResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.CompanyService;
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
@RequestMapping(value = "companies")
public class CompanyController {

    public final static Logger logger = Logger.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<CompanyResultDTO> findAllCompanies() {
        List<CompanyResultDTO> customerResultDTOList = companyService.findAll();
        logger.info("Find " + customerResultDTOList);
        return customerResultDTOList;
    }

    @RequestMapping(value = "/get/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<?> findCompanyById(@PathVariable Long companyId) throws BusinessValidationException {
        CompanyResultDTO companyResultDTO = companyService.find(companyId);
        logger.info("Find " + companyResultDTO);
        return new ResponseEntity<>(companyResultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyCreateDTO companyDTO) throws CustomValidationException,
            BusinessValidationException {
        CompanyPrimaryKeyDTO companyPKeyDTO = companyService.create(companyDTO);
        logger.info("Add company with " + companyPKeyDTO.getId());
        return new ResponseEntity<>(companyPKeyDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{companyId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@PathVariable Long companyId, @Valid @RequestBody CompanyUpdateDTO companyDTO)
            throws CustomValidationException, BusinessValidationException {
        companyService.update(companyId, companyDTO);
        logger.info("Update company with id " + companyId + " was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{companyId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyById(@PathVariable Long companyId) throws BusinessValidationException {
        companyService.delete(companyId);
        logger.info("Delete by id = " + companyId + "successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
