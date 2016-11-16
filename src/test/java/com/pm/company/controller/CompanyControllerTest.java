package com.pm.company.controller;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.pm.company.dto.AddressDTO;
import com.pm.company.dto.CompanyCreateDTO;
import com.pm.company.dto.CompanyUpdateDTO;
import com.pm.company.dto.primarykey.CompanyPrimaryKeyDTO;
import com.pm.company.dto.result.CompanyResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.CompanyService;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CompanyControllerTest {

	@InjectMocks
	private CompanyController sut;

	@Mock
	private CompanyService companyService;

	private CompanyResultDTO companyResult1;
	private CompanyResultDTO companyResult2;
	private CompanyPrimaryKeyDTO companyPKDTO;
	private Long companyId;

	@Before
	public void before() {
		companyId = 1L;
		companyResult1 = new CompanyResultDTO(1L, "company1", new AddressDTO("city1", "postcode1", "street1", "number1"));
		companyResult2 = new CompanyResultDTO(2L, "company2", new AddressDTO("city2", "postcode2", "street2", "number2"));
		companyPKDTO = new CompanyPrimaryKeyDTO(companyId);
		sut = new CompanyController();
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.standaloneSetup(sut);
	}

	@Test
	public void getCompanyShouldSucceedAndReturnResultDTO() throws BusinessValidationException {
		when(companyService.find(companyResult1.getCompanyId())).thenReturn(companyResult1);

		given().
		when().
			get("/companies/get/{companyId}", companyResult1.getCompanyId()).
		then().
			statusCode(200).
			body("companyId", equalTo(companyResult1.getCompanyId().intValue())).
			body("name", equalTo(companyResult1.getName()))
		;
		
		verify(companyService, times(1)).find(companyResult1.getCompanyId());
	}

	@Test
	public void getCompanyListShouldSucceedAndReturnListOfResultDTO(){
		List<CompanyResultDTO> customerList = Arrays.asList(new CompanyResultDTO[]{companyResult1, companyResult2});
		when(companyService.findAll()).thenReturn(customerList);

		String jsonAsString =
			given().
			when().
				get("/companies/list").
			then().
				statusCode(200).
				body("$", hasSize(2)).
				extract().response().asString();

		// first we put our 'jsonAsString' into an ArrayList of Maps of type <String, ?>
		List<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

		// now we count the number of entries in the JSON file, each entry is 1 customer
		assertThat(jsonAsArrayList.size(), IsEqual.equalTo(2));
		assertThat(((Integer)jsonAsArrayList.get(0).get("companyId")).longValue(), equalTo(companyResult1.getCompanyId()));
		assertThat((String)jsonAsArrayList.get(0).get("name"), equalTo(companyResult1.getName()));
		//
		assertThat(((Integer)jsonAsArrayList.get(1).get("companyId")).longValue(), equalTo(companyResult2.getCompanyId()));
		assertThat((String)jsonAsArrayList.get(1).get("name"), equalTo(companyResult2.getName()));

		verify(companyService, times(1)).findAll();
	}
	
	@Test
	public void postShouldSucceed() throws BusinessValidationException, CustomValidationException {
		String addJson = "{" +
				"\"name\": \"companyName1\"," +
				"\"address\": " +
				"{" +
					"\"city\": \"city1\"," +
					"\"postcode\": \"postcode1\"," +
					"\"street\": \"street1\"," +
					"\"number\": \"123\"" +
				"}" +
				"}";
		when(companyService.create(any(CompanyCreateDTO.class))).thenReturn(companyPKDTO);

		given().
			header("Content-Type", "application/json").
			body(addJson).
		when().
			post("/companies/add").
		then().
			statusCode(200).
			body("id", equalTo(companyPKDTO.getId().intValue()));

		verify(companyService, times(1)).create(any(CompanyCreateDTO.class));
	}

	@Test
	public void putShouldSucceed() throws CustomValidationException, BusinessValidationException{
		String updateJson = "{" +
				"\"name\": \"companyName1\"," +
				"\"address\": " +
				"{" +
					"\"city\": \"city1\"," +
					"\"postcode\": \"postcode1\"," +
					"\"street\": \"street1\"," +
					"\"number\": \"123\"" +
				"}" +
				"}";
		given().
			header("Content-Type", "application/json").
			body(updateJson).
		when().
			put("/companies/update/{companyId}", companyId).
		then().
			statusCode(200);

		verify(companyService, times(1)).update(eq(companyId), any(CompanyUpdateDTO.class));
	}
	
	@Test
	public void deleteShouldSucceed() throws BusinessValidationException{
		
		given().
		when().
		delete("/companies/delete/{companyId}", companyId).
		then().
			statusCode(200);
		
		verify(companyService,times(1)).delete(companyId);
	}

}
