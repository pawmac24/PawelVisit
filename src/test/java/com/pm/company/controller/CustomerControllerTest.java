package com.pm.company.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
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

import com.pm.company.dto.primarykey.CustomerPrimaryKeyDTO;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.model.Person;
import com.pm.company.validator.field.FieldValidator;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.dto.result.CustomerResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.service.CustomerService;

public class CustomerControllerTest {

	@InjectMocks
	private CustomerController sut;

	@Mock
	private CustomerService customerService;

	@Mock
	private FieldValidator<CustomerCreateDTO> customerCrtValidator;

	@Mock
	private FieldValidator<Person> customerUpdtValidator;

	private CustomerResultDTO customerResult1;
	private CustomerResultDTO customerResult2;
	private CustomerPrimaryKeyDTO customerPKDTO;
	private Long customerId;

	@Before
	public void before() {
		customerId = 1L;
		customerResult1 = new CustomerResultDTO(1L, "12345678901", "Firstname", "Lastname", new Date());
		customerResult2 = new CustomerResultDTO(2L, "12345678901", "Firstname", "Lastname", new Date());
		customerPKDTO = new CustomerPrimaryKeyDTO(customerId);
		sut = new CustomerController();
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.standaloneSetup(sut);
	}

	@Test
	public void getCustomerShouldSucceedAndReturnResultDTO() throws BusinessValidationException {
		when(customerService.find(customerResult1.getCustomerId())).thenReturn(customerResult1);

		given().
		when().
			get("/customers/get/{customerId}", customerResult1.getCustomerId()).
		then().
			statusCode(200).
			body("customerId", equalTo(customerResult1.getCustomerId().intValue())).
			body("pesel", equalTo(customerResult1.getPesel())).
			body("firstName", equalTo(customerResult1.getFirstName())).
			body("lastName", equalTo(customerResult1.getLastName()));
		
		verify(customerService, times(1)).find(customerResult1.getCustomerId());
	}

	@Test
	public void getCustomerListShouldSucceedAndReturnListOfResultDTO(){
		List<CustomerResultDTO> customerList = Arrays.asList(new CustomerResultDTO[]{customerResult1, customerResult2});
		when(customerService.findAll()).thenReturn(customerList);

		String jsonAsString =
			given().
			when().
				get("/customers/list").
			then().
				statusCode(200).
				body("$", hasSize(2)).
				extract().response().asString();

		// first we put our 'jsonAsString' into an ArrayList of Maps of type <String, ?>
		List<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

		// now we count the number of entries in the JSON file, each entry is 1 customer
		assertThat(jsonAsArrayList.size(), IsEqual.equalTo(2));
		assertThat(((Integer)jsonAsArrayList.get(0).get("customerId")).longValue(), equalTo(customerResult1.getCustomerId()));
		assertThat((String)jsonAsArrayList.get(0).get("firstName"), equalTo(customerResult1.getFirstName()));
		assertThat((String)jsonAsArrayList.get(0).get("lastName"), equalTo(customerResult1.getLastName()));
		assertThat((String)jsonAsArrayList.get(0).get("pesel"), equalTo(customerResult1.getPesel()));
		//
		assertThat(((Integer)jsonAsArrayList.get(1).get("customerId")).longValue(), equalTo(customerResult2.getCustomerId()));
		assertThat((String)jsonAsArrayList.get(1).get("firstName"), equalTo(customerResult2.getFirstName()));
		assertThat((String)jsonAsArrayList.get(1).get("lastName"), equalTo(customerResult2.getLastName()));
		assertThat((String)jsonAsArrayList.get(1).get("pesel"), equalTo(customerResult2.getPesel()));

		verify(customerService, times(1)).findAll();
	}
	
	@Test
	public void postShouldSucceedAndReturnIdOfCreated() throws BusinessValidationException, CustomValidationException {
		String addJson = "{  \"pesel\": \"19925678901\",\"firstName\": \"Mary\",\"lastName\": \"Lee\",  \"dateOfBirth\": \"1986-12-02\"}";
		when(customerService.create(any(CustomerCreateDTO.class))).thenReturn(customerPKDTO);

		given().
			header("Content-Type", "application/json").
			body(addJson).
		when().
			post("/customers/add").
		then().
			statusCode(200).
			body("id", equalTo(customerPKDTO.getId().intValue()));
		
		verify(customerCrtValidator, times(1)).validate(any(CustomerCreateDTO.class));
		verify(customerService, times(1)).create(any(CustomerCreateDTO.class));
	}
	
	@Test
	public void putShouldSucceed() throws CustomValidationException, BusinessValidationException{
		String updateJson = "{  \"firstName\": \"Mary\",\"lastName\": \"Lee\"}";
		given().
			header("Content-Type", "application/json").
			body(updateJson).
		when().
			put("/customers/update/{customerId}", customerId).
		then().
			statusCode(200);
		
		verify(customerUpdtValidator, times(1)).validate(any(CustomerUpdateDTO.class));
		verify(customerService, times(1)).update(eq(customerId), any(CustomerUpdateDTO.class));
	}
	
	@Test
	public void deleteShouldSucceed() throws BusinessValidationException{
		
		given().
		when().
		delete("/customers/delete/{customerId}", customerId).
		then().
			statusCode(200);
		
		verify(customerService,times(1)).delete(customerId);
	}

}
