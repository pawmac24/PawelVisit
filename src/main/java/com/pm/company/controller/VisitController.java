package com.pm.company.controller;

import java.util.List;

import javax.validation.Valid;

import com.pm.company.dto.primarykey.VisitPrimaryKeyDTO;
import com.pm.company.dto.result.VisitResultDTO;
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

import com.pm.company.dto.VisitCreateDTO;
import com.pm.company.dto.VisitUpdateDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.VisitService;
import com.pm.company.validator.field.FieldValidator;

@RestController
@RequestMapping("visits")
public class VisitController {

	public final static Logger logger = Logger.getLogger(VisitController.class);

	@Autowired
	private VisitService visitService;

	@Autowired
	private FieldValidator<VisitCreateDTO> visitCrtValidator;
	
	@Autowired
	private FieldValidator<VisitUpdateDTO> visitUpdtValidator;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<VisitResultDTO> findAllVisits() {
		logger.info("Find all visits");
		return visitService.findAll();
	}

	@RequestMapping(value = "/get/{visitId}", method = RequestMethod.GET)
	public ResponseEntity<?> findVisitById(@PathVariable Long visitId) throws BusinessValidationException {
		VisitResultDTO visitResultDTO = visitService.find(visitId);
		logger.info("Visit found: " + visitResultDTO);
		return new ResponseEntity<>(visitResultDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> createVisit(@Valid @RequestBody VisitCreateDTO visitDTO)
			throws BusinessValidationException, CustomValidationException {
		visitCrtValidator.validate(visitDTO);
		VisitPrimaryKeyDTO visitPKeyDTO = visitService.create(visitDTO);
		logger.info("Add visit with id: " + visitPKeyDTO.getId());
		return new ResponseEntity<>(visitPKeyDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{visitId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateVisit(@PathVariable Long visitId, @Valid @RequestBody VisitUpdateDTO visitDTO)
			throws BusinessValidationException, CustomValidationException {
		visitUpdtValidator.validate(visitDTO);
		visitService.update(visitDTO, visitId);
		logger.info("Update Visit with id : " + visitId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{visitId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteVisit(@PathVariable Long visitId) throws BusinessValidationException {
		visitService.delete(visitId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
