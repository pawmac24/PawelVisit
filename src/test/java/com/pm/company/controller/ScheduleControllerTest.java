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

import com.pm.company.dto.ScheduleCreateDTO;
import com.pm.company.dto.ScheduleUpdateDTO;
import com.pm.company.dto.primarykey.SchedulePrimaryKeyDTO;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.ScheduleService;
import com.pm.company.validator.field.FieldValidator;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.pm.company.dto.result.ScheduleResultDTO;
import com.pm.company.exception.BusinessValidationException;

public class ScheduleControllerTest {

	@InjectMocks
	private ScheduleController sut;

	@Mock
	private ScheduleService scheduleService;

	@Mock
	private FieldValidator<ScheduleCreateDTO> scheduleCrtValidator;

	@Mock
	private FieldValidator<ScheduleUpdateDTO> scheduleUpdtValidator;

	private ScheduleResultDTO scheduleResult1;
	private ScheduleResultDTO scheduleResult2;
	private SchedulePrimaryKeyDTO schedulePKDTO;
	private Long scheduleId;

	@Before
	public void before() {
		scheduleId = 1L;
		scheduleResult1 = new ScheduleResultDTO(1L, new Date(), new Date(), "Place 1");
		scheduleResult2 = new ScheduleResultDTO(2L, new Date(), new Date(), "Place 2");
		schedulePKDTO = new SchedulePrimaryKeyDTO(scheduleId);
		sut = new ScheduleController();
		MockitoAnnotations.initMocks(this);
		RestAssuredMockMvc.standaloneSetup(sut);
	}

	@Test
	public void getScheduleShouldSucceedAndReturnResultDTO() throws BusinessValidationException {
		when(scheduleService.find(scheduleResult1.getScheduleId())).thenReturn(scheduleResult1);

		given().
		when().
			get("/schedules/get/{scheduleId}", scheduleResult1.getScheduleId()).
		then().
			statusCode(200).
			body("scheduleId", equalTo(scheduleResult1.getScheduleId().intValue())).
			body("start", equalTo(scheduleResult1.getStart().getTime())).
			body("stop", equalTo(scheduleResult1.getStop().getTime())).
			body("place", equalTo(scheduleResult1.getPlace()));
		
		verify(scheduleService, times(1)).find(scheduleResult1.getScheduleId());
	}

	@Test
	public void getScheduleListShouldSucceedAndReturnListOfResultDTO(){
		List<ScheduleResultDTO> scheduleList = Arrays.asList(new ScheduleResultDTO[]{scheduleResult1, scheduleResult2});
		when(scheduleService.findAll()).thenReturn(scheduleList);

		String jsonAsString =
			given().
			when().
				get("/schedules/list").
			then().
				statusCode(200).
				body("$", hasSize(2)).
				extract().response().asString();

		// first we put our 'jsonAsString' into an ArrayList of Maps of type <String, ?>
		List<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

		// now we count the number of entries in the JSON file, each entry is 1 customer
		assertThat(jsonAsArrayList.size(), IsEqual.equalTo(2));
		assertThat(((Integer)jsonAsArrayList.get(0).get("scheduleId")).longValue(), equalTo(scheduleResult1.getScheduleId()));
		//
		assertThat(((Integer)jsonAsArrayList.get(1).get("scheduleId")).longValue(), equalTo(scheduleResult2.getScheduleId()));

		verify(scheduleService, times(1)).findAll();
	}
	
	@Test
	public void postShouldSucceedAndReturnIdOfCreated() throws BusinessValidationException, CustomValidationException {
		String addJson = "{ \"start\": 1420110000000, " +
				"\"stop\": 1420196400000, " +
				"\"place\": \"The place\", " +
				"\"employeeId\": 1, " +
				"\"roomId\": 1 }";
		when(scheduleService.create(any(ScheduleCreateDTO.class))).thenReturn(schedulePKDTO);

		given().
			header("Content-Type", "application/json").
			body(addJson).
		when().
			post("/schedules/add").
		then().
			statusCode(200).
			body("id", equalTo(schedulePKDTO.getId().intValue()));
		
		verify(scheduleCrtValidator, times(1)).validate(any(ScheduleCreateDTO.class));
		verify(scheduleService, times(1)).create(any(ScheduleCreateDTO.class));
	}
	
	@Test
	public void putShouldSucceed() throws CustomValidationException, BusinessValidationException{
		String updateJson = "{ \"start\": 1420110000000, \"stop\": 1420196400000, \"place\": \"The place\"}";
		given().
			header("Content-Type", "application/json").
			body(updateJson).
		when().
			put("/schedules/update/{scheduleId}", scheduleId).
		then().
			statusCode(200);
		
		verify(scheduleUpdtValidator, times(1)).validate(any(ScheduleUpdateDTO.class));
		verify(scheduleService, times(1)).update(any(ScheduleUpdateDTO.class), eq(scheduleId));
	}
	
	@Test
	public void deleteShouldSucceed() throws BusinessValidationException{
		
		given().
		when().
		delete("/schedules/delete/{scheduleId}", scheduleId).
		then().
			statusCode(200);
		
		verify(scheduleService,times(1)).delete(scheduleId);
	}

}
