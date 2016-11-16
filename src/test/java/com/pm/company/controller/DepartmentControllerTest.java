package com.pm.company.controller;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.pm.company.dto.AddressDTO;
import com.pm.company.dto.DepartmentCreateDTO;
import com.pm.company.dto.DepartmentUpdateDTO;
import com.pm.company.dto.primarykey.DepartmentPrimaryKeyDTO;
import com.pm.company.dto.result.DepartmentResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.DepartmentService;
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

public class DepartmentControllerTest {

	@InjectMocks
	private DepartmentController sut;

	@Mock
	private DepartmentService departmentService;

	private DepartmentResultDTO departmentResult1;
	private DepartmentResultDTO departmentResult2;
	private DepartmentPrimaryKeyDTO departmentPKDTO;
	private Long departmentId;

	@Before
	public void before() {
		departmentId = 1L;
		departmentResult1 = new DepartmentResultDTO(1L, "department1", new AddressDTO("city1", "postcode1", "street1", "number1"), 1L);
		departmentResult2 = new DepartmentResultDTO(2L, "department2", new AddressDTO("city2", "postcode2", "street2", "number2"), 1L);
		departmentPKDTO = new DepartmentPrimaryKeyDTO(departmentId);
		sut = new DepartmentController();
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.standaloneSetup(sut);
	}

	@Test
	public void getDepartmentShouldSucceedAndReturnResultDTO() throws BusinessValidationException {
		when(departmentService.find(departmentResult1.getCompanyId())).thenReturn(departmentResult1);

		given().
		when().
			get("/departments/get/{departmentId}", departmentResult1.getCompanyId()).
		then().
			statusCode(200).
			body("departmentId", equalTo(departmentResult1.getCompanyId().intValue())).
			body("name", equalTo(departmentResult1.getName()))
		;
		
		verify(departmentService, times(1)).find(departmentResult1.getCompanyId());
	}

	@Test
	public void getCompanyListShouldSucceedAndReturnListOfResultDTO(){
		List<DepartmentResultDTO> departmentList = Arrays.asList(new DepartmentResultDTO[]{departmentResult1, departmentResult2});
		when(departmentService.findAll()).thenReturn(departmentList);

		String jsonAsString =
			given().
			when().
				get("/departments/list").
			then().
				statusCode(200).
				body("$", hasSize(2)).
				extract().response().asString();

		// first we put our 'jsonAsString' into an ArrayList of Maps of type <String, ?>
		List<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

		// now we count the number of entries in the JSON file, each entry is 1 customer
		assertThat(jsonAsArrayList.size(), IsEqual.equalTo(2));
		assertThat(((Integer)jsonAsArrayList.get(0).get("departmentId")).longValue(), equalTo(departmentResult1.getDepartmentId()));
		assertThat((String)jsonAsArrayList.get(0).get("name"), equalTo(departmentResult1.getName()));
		//
		assertThat(((Integer)jsonAsArrayList.get(1).get("departmentId")).longValue(), equalTo(departmentResult2.getDepartmentId()));
		assertThat((String)jsonAsArrayList.get(1).get("name"), equalTo(departmentResult2.getName()));

		verify(departmentService, times(1)).findAll();
	}
	
	@Test
	public void postShouldSucceed() throws BusinessValidationException, CustomValidationException {
		String addJson = "{" +
				"\"name\": \"department1Name1\"," +
				"\"address\": " +
				"{" +
					"\"city\": \"city1\"," +
					"\"postcode\": \"postcode1\"," +
					"\"street\": \"street1\"," +
					"\"number\": \"123\"" +
				"}," +
				"\"companyId\": 1" +
				"}";
		when(departmentService.create(any(DepartmentCreateDTO.class))).thenReturn(departmentPKDTO);

		given().
			header("Content-Type", "application/json").
			body(addJson).
		when().
			post("/departments/add").
		then().
			statusCode(200).
			body("id", equalTo(departmentPKDTO.getId().intValue()));

		verify(departmentService, times(1)).create(any(DepartmentCreateDTO.class));
	}

	@Test
	public void putShouldSucceed() throws CustomValidationException, BusinessValidationException{
		String updateJson = "{" +
				"\"name\": \"department1Name1\"," +
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
			put("/departments/update/{departmentId}", departmentId).
		then().
			statusCode(200);

		verify(departmentService, times(1)).update(eq(departmentId), any(DepartmentUpdateDTO.class));
	}
	
	@Test
	public void deleteShouldSucceed() throws BusinessValidationException{
		
		given().
		when().
		delete("/departments/delete/{departmentId}", departmentId).
		then().
			statusCode(200);
		
		verify(departmentService,times(1)).delete(departmentId);
	}

}
