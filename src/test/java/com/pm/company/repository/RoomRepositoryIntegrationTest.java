package com.pm.company.repository;

import com.pm.company.PersistenceContextTest;
import com.pm.company.model.Address;
import com.pm.company.model.Company;
import com.pm.company.model.Department;
import com.pm.company.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceContextTest.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class RoomRepositoryIntegrationTest {

	@Autowired
	private RoomRepository sut;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private Company company;
	private Department department;
	private Room room;

	@Before
	public void before(){
		Address addressCompany = new Address("city", "postcode", "street", "123");
		Address addressDepartment = new Address("citydep", "postcode", "streetdep", "123");

		company = new Company("companymane", addressCompany);
		company = companyRepository.save(company);

		department = new Department("depname", addressDepartment, company);
		department = departmentRepository.save(department);

		room = new Room("111", department);
	}
	
	@Test
	public void testCreate(){
		room = sut.save(room);
		assertNotNull(room);
		assertNotNull(room.getRoomId());
		Room retrievedRoom = sut.findOne(room.getRoomId());
		assertNotNull(retrievedRoom);
		assertEquals(room, retrievedRoom);
	}
	
	@Test
	public void testUpdate(){
		String updatedNumber = "111P";
		room = sut.save(room);
		assertNotNull(room);
		assertNotNull(room.getRoomId());
		room.setNumber(updatedNumber);
		Room updatedRoom = sut.save(room);
		assertNotNull(updatedRoom);
		assertEquals(updatedNumber, room.getNumber());
		
	}
	
	@Test
	public void testDelete(){
		room = sut.save(room);
		assertNotNull(room);
		assertNotNull(room.getRoomId());
		Long deletedRoomId = room.getRoomId();
		sut.delete(deletedRoomId);
		room = sut.findOne(deletedRoomId);
		assertNull(room);
	}
}
