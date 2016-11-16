package com.pm.company.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.pm.company.dto.VisitCreateDTO;
import com.pm.company.dto.VisitUpdateDTO;
import com.pm.company.dto.primarykey.VisitPrimaryKeyDTO;
import com.pm.company.dto.result.VisitResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.VisitService;
import com.pm.company.validator.field.FieldValidator;

public class VisitControllerTest {

	@InjectMocks
	private VisitController sut;

	@Mock
	private VisitService visitService;

	@Mock
	private FieldValidator<VisitCreateDTO> visitCrtValidator;

	@Mock
	private FieldValidator<VisitUpdateDTO> visitUpdtValidator;

	private VisitResultDTO visitResult1;
	private VisitResultDTO visitResult2;
	private VisitPrimaryKeyDTO visitPKDTO;
	private Long visitId;

	@Before
	public void before() {
		visitId = 1L;
		visitResult1 = new VisitResultDTO(1L, new Date(), new Date(), true, 1);
		visitResult2 = new VisitResultDTO(2L, new Date(), new Date(), true, 1);
		visitPKDTO = new VisitPrimaryKeyDTO(visitId);
		sut = new VisitController();
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.standaloneSetup(sut);
	}

	@Test
	public void getVisitShouldSucceedAndReturnResultDTO() throws BusinessValidationException {
		when(visitService.find(visitResult1.getVisitId())).thenReturn(visitResult1);

		given().
		when().
			get("/visits/get/{visitId}", visitResult1.getVisitId()).
		then().
			statusCode(200).
			body("visitId", equalTo(visitResult1.getVisitId().intValue())).
			body("start", equalTo(visitResult1.getStart().getTime())).
			body("end", equalTo(visitResult1.getEnd().getTime())).
			body("status", equalTo(visitResult1.getStatus())).
			body("active", equalTo(visitResult1.isActive()));
		
		verify(visitService, times(1)).find(visitResult1.getVisitId());
	}

	@Test
	public void getVisitListShouldSucceedAndReturnListOfResultDTO(){
		List<VisitResultDTO> visitList = Arrays.asList(new VisitResultDTO[]{visitResult1, visitResult2});
		when(visitService.findAll()).thenReturn(visitList);

		String jsonAsString =
			given().
			when().
				get("/visits/list").
			then().
				statusCode(200).
				body("$", hasSize(2)).extract().response().asString();

		// first we put our 'jsonAsString' into an ArrayList of Maps of type <String, ?>
		List<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

		// now we count the number of entries in the JSON file, each entry is 1 customer
		assertThat(jsonAsArrayList.size(), IsEqual.equalTo(2));
		assertThat(((Integer)jsonAsArrayList.get(0).get("visitId")).longValue(), equalTo(visitResult1.getVisitId()));
		//
		assertThat(((Integer)jsonAsArrayList.get(1).get("visitId")).longValue(), equalTo(visitResult2.getVisitId()));

		verify(visitService, times(1)).findAll();
	}
	
	@Test
	public void postShouldSucceedAndReturnIdOfCreated() throws BusinessValidationException, CustomValidationException {
		String addJson = "{ \"start\": 1420110000000, \"end\": 1420196400000, \"active\": true, \"status\": 1, \"employeeId\": 1, \"customerId\": 1 }";
		when(visitService.create(any(VisitCreateDTO.class))).thenReturn(visitPKDTO);

		given().
			header("Content-Type", "application/json").
			body(addJson).
		when().
			post("/visits/add").
		then().
			statusCode(200).
			body("id", equalTo(visitPKDTO.getId().intValue()));
		
		verify(visitCrtValidator, times(1)).validate(any(VisitCreateDTO.class));
		verify(visitService, times(1)).create(any(VisitCreateDTO.class));
	}
	
	@Test
	public void putShouldSucceed() throws CustomValidationException, BusinessValidationException{
		String updateJson = "{ \"start\": 1420110000000, \"end\": 1420196400000, \"active\": true, \"status\": 1}";
		given().
			header("Content-Type", "application/json").
			body(updateJson).
		when().
			put("/visits/update/{visitId}", visitId).
		then().
			statusCode(200);
		
		verify(visitUpdtValidator, times(1)).validate(any(VisitUpdateDTO.class));
		verify(visitService, times(1)).update(any(VisitUpdateDTO.class), eq(visitId));
	}
	
	@Test
	public void deleteShouldSucceed() throws BusinessValidationException{
		
		given().
		when().
		delete("/visits/delete/{visitId}", visitId).
		then().
			statusCode(200);
		
		verify(visitService,times(1)).delete(visitId);
	}

}
