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
import java.util.List;
import java.util.Map;

import com.pm.company.dto.EmployeeCreateDTO;
import com.pm.company.dto.EmployeeUpdateDTO;
import com.pm.company.dto.primarykey.EmployeePrimaryKeyDTO;
import com.pm.company.dto.result.EmployeeResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.EmployeeService;
import com.pm.company.validator.field.FieldValidator;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.pm.company.model.Person;

public class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController sut;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private FieldValidator<Person> employeeValidator;

	private EmployeeResultDTO employeeResult1;
	private EmployeeResultDTO employeeResult2;
	private EmployeePrimaryKeyDTO employeePKDTO;
	private Long employeeId;

	@Before
	public void before() {
		employeeId = 1L;
		employeeResult1 = new EmployeeResultDTO(1L, "John", "Doe", "Description 1");
		employeeResult2 = new EmployeeResultDTO(2L, "William", "Travolta", "Description 2");
		employeePKDTO = new EmployeePrimaryKeyDTO(employeeId);
		sut = new EmployeeController();
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.standaloneSetup(sut);
	}

	@Test
	public void getEmployeeShouldSucceedAndReturnResultDTO() throws BusinessValidationException {
		when(employeeService.find(employeeResult1.getEmployeeId())).thenReturn(employeeResult1);

		given().
		when().
			get("/employees/get/{employeeId}", employeeResult1.getEmployeeId()).
		then().
			statusCode(200).
			body("employeeId", equalTo(employeeResult1.getEmployeeId().intValue())).
			body("firstName", equalTo(employeeResult1.getFirstName())).
			body("lastName", equalTo(employeeResult1.getLastName())).
			body("description", equalTo(employeeResult1.getDescription()));
		
		verify(employeeService, times(1)).find(employeeResult1.getEmployeeId());
	}

	@Test
	public void getEmployeeListShouldSucceedAndReturnListOfResultDTO(){
		List<EmployeeResultDTO> employeeList = Arrays.asList(new EmployeeResultDTO[]{employeeResult1, employeeResult2});
		when(employeeService.findAll()).thenReturn(employeeList);

		String jsonAsString =
			given().
			when().
				get("/employees/list").
			then().
				statusCode(200).
				body("$", hasSize(2)).
				extract().response().asString();

		// first we put our 'jsonAsString' into an ArrayList of Maps of type <String, ?>
		List<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

		// now we count the number of entries in the JSON file, each entry is 1 customer
		assertThat(jsonAsArrayList.size(), IsEqual.equalTo(2));
		assertThat(((Integer)jsonAsArrayList.get(0).get("employeeId")).longValue(), equalTo(employeeResult1.getEmployeeId()));
		assertThat((String)jsonAsArrayList.get(0).get("firstName"), equalTo(employeeResult1.getFirstName()));
		assertThat((String)jsonAsArrayList.get(0).get("lastName"), equalTo(employeeResult1.getLastName()));
		assertThat((String)jsonAsArrayList.get(0).get("description"), equalTo(employeeResult1.getDescription()));
		//
		assertThat(((Integer)jsonAsArrayList.get(1).get("employeeId")).longValue(), equalTo(employeeResult2.getEmployeeId()));
		assertThat((String)jsonAsArrayList.get(1).get("firstName"), equalTo(employeeResult2.getFirstName()));
		assertThat((String)jsonAsArrayList.get(1).get("lastName"), equalTo(employeeResult2.getLastName()));
		assertThat((String)jsonAsArrayList.get(1).get("description"), equalTo(employeeResult2.getDescription()));

		verify(employeeService, times(1)).findAll();
	}
	
	@Test
	public void postShouldSucceedAndReturnIdOfCreated() throws BusinessValidationException, CustomValidationException {
		String addJson = "{ \"firstName\": \"Mary\",\"lastName\": \"Lee\",  \"description\": \"Description\",  \"departmentId\": 1}";
		when(employeeService.create(any(EmployeeCreateDTO.class))).thenReturn(employeePKDTO);

		given().
			header("Content-Type", "application/json").
			body(addJson).
		when().
			post("/employees/add").
		then().
			statusCode(200).
			body("id", equalTo(employeePKDTO.getId().intValue()));
		
		verify(employeeValidator, times(1)).validate(any(EmployeeCreateDTO.class));
		verify(employeeService, times(1)).create(any(EmployeeCreateDTO.class));
	}
	
	@Test
	public void putShouldSucceed() throws CustomValidationException, BusinessValidationException{
		String updateJson = "{  \"firstName\": \"Mary\",\"lastName\": \"Lee\",  \"description\": \"Description\"}";
		given().
			header("Content-Type", "application/json").
			body(updateJson).
		when().
			put("/employees/update/{employeeId}", employeeId).
		then().
			statusCode(200);
		
		verify(employeeValidator, times(1)).validate(any(EmployeeUpdateDTO.class));
		verify(employeeService, times(1)).update(eq(employeeId), any(EmployeeUpdateDTO.class));
	}
	
	@Test
	public void deleteShouldSucceed() throws BusinessValidationException{
		
		given().
		when().
		delete("/employees/delete/{employeeId}", employeeId).
		then().
			statusCode(200);
		
		verify(employeeService,times(1)).delete(employeeId);
	}

}
