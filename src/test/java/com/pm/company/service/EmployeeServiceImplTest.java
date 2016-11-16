package com.pm.company.service;

import com.pm.company.dto.EmployeeCreateDTO;
import com.pm.company.dto.EmployeeUpdateDTO;
import com.pm.company.dto.primarykey.EmployeePrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Department;
import com.pm.company.model.Employee;
import com.pm.company.repository.DepartmentRepository;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.validator.business.BusinessValidator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by pmackiewicz on 2015-10-26.
 */
@RunWith(JUnitParamsRunner.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl sut;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
	private BusinessValidator<Employee, Long> employeeValidator;

    @Mock
    private Mapper mapper;

    @Before
    public void setUp() {
        sut = new EmployeeServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    private Object[] provideCreateDTO_EmployeePairs(){
        EmployeeCreateDTO employee1 = new EmployeeCreateDTO("Paul", "Mc Cook", "description 1", 1L);
        Employee employeeExpected1 = new Employee(1L, employee1.getFirstName(), employee1.getLastName(), employee1.getDescription(), new Department());
        EmployeeCreateDTO employee2 = new EmployeeCreateDTO("Andrew", "Coger", "description 2", 1L);
        Employee employeeExpected2 = new Employee(2L, employee2.getFirstName(), employee2.getLastName(), employee2.getDescription(), new Department());
        EmployeeCreateDTO employee3 = new EmployeeCreateDTO("Mathew", "Duck", "description 3", 1L);
        Employee employeeExpected3 = new Employee(3L, employee3.getFirstName(), employee3.getLastName(), employee3.getDescription(), new Department());

        return new Object[]{
                        new Object[]{ employee1, employeeExpected1 },
                        new Object[]{ employee2, employeeExpected2 },
                        new Object[]{ employee3, employeeExpected3 }
        };
    }

    private Object[] provideUpdateDTO_EmployeePairs(){
        EmployeeUpdateDTO employee1 = new EmployeeUpdateDTO("Paul", "Mc Cook", "description 1");
        Employee employeeExpected1 = new Employee(1L, employee1.getFirstName(), employee1.getLastName(), employee1.getDescription(), new Department());
        EmployeeUpdateDTO employee2 = new EmployeeUpdateDTO("Andrew", "Coger", "description 2");
        Employee employeeExpected2 = new Employee(2L, employee2.getFirstName(), employee2.getLastName(), employee2.getDescription(), new Department());
        EmployeeUpdateDTO employee3 = new EmployeeUpdateDTO("Mathew", "Duck", "description 3");
        Employee employeeExpected3 = new Employee(3L, employee3.getFirstName(), employee3.getLastName(), employee3.getDescription(), new Department());

        return new Object[]{
                        new Object[]{ employee1, employeeExpected1 },
                        new Object[]{ employee2, employeeExpected2 },
                        new Object[]{ employee3, employeeExpected3 }
        };
    }

    private Object[] provideUpdateNotExisting__Data(){
        EmployeeUpdateDTO employee1 = new EmployeeUpdateDTO("Paul", "Mc Cook", "description 1");
        Long employeeNotExising1 = 1L;
        EmployeeUpdateDTO employee2 = new EmployeeUpdateDTO("Andrew", "Coger", "description 2");
        Long employeeNotExising2 = 2L;
        EmployeeUpdateDTO employee3 = new EmployeeUpdateDTO("Mathew", "Duck", "description 3");
        Long employeeNotExising3 = 3L;

        return new Object[]{
                new Object[]{ employee1, employeeNotExising1 },
                new Object[]{ employee2, employeeNotExising2 },
                new Object[]{ employee3, employeeNotExising3 }
        };
    }


    @Test
    @Parameters(method = "provideCreateDTO_EmployeePairs")
    public void testCreateEmployees(EmployeeCreateDTO employeeInput, Employee employeeExpected) throws BusinessValidationException {
        when(departmentRepository.findOne(employeeInput.getDepartmentId())).thenReturn(new Department());
        when(mapper.map(employeeInput, Employee.class)).thenReturn(new Employee());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeExpected);

        EmployeePrimaryKeyDTO employeePrimaryKeyDTO = sut.create(employeeInput);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        assertNotNull(employeePrimaryKeyDTO.getId());
        assertEquals(employeeExpected.getEmployeeId().longValue(), employeePrimaryKeyDTO.getId().longValue());
    }
    
    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideCreateDTO_EmployeePairs")
    public void testCreateEmployeesShouldFailBecauseOfValidation(EmployeeCreateDTO employeeInput, Employee employeeExpected) throws BusinessValidationException {
        when(departmentRepository.findOne(employeeInput.getDepartmentId())).thenReturn(new Department());
        when(mapper.map(employeeInput, Employee.class)).thenReturn(new Employee());
        Mockito.doThrow(new BusinessValidationException("")).when(employeeValidator).validateCreate(any(Employee.class));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeExpected);

        sut.create(employeeInput);
    }

    @Test
    @Parameters(method = "provideUpdateDTO_EmployeePairs")
    public void testUpdateCustomers(EmployeeUpdateDTO employeeInput, Employee employeeExpected) throws BusinessValidationException {
    	when(employeeRepository.findOne(employeeExpected.getEmployeeId())).thenReturn(employeeExpected);
    	when(employeeRepository.save(any(Employee.class))).thenReturn(employeeExpected);

        sut.update(employeeExpected.getEmployeeId(), employeeInput);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeValidator, times(1)).validateUpdate(employeeExpected);
    }

    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideUpdateNotExisting__Data")
    public void testUpdateEmployeesShouldFailBecauseOfVNotExistingEmployee(EmployeeUpdateDTO employeeInput, Long employeeIdNotexisting) throws BusinessValidationException {
    	when(employeeRepository.findOne(employeeIdNotexisting)).thenReturn(null);
        sut.update(employeeIdNotexisting, employeeInput);
        fail();
    }

    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideUpdateDTO_EmployeePairs")
    public void testUpdateCustomersShouldFailBecauseOfValidation(EmployeeUpdateDTO employeeInput, Employee employeeExpected) throws BusinessValidationException {
    	when(employeeRepository.findOne(employeeExpected.getEmployeeId())).thenReturn(employeeExpected);
    	when(employeeRepository.save(any(Employee.class))).thenReturn(employeeExpected);
        Mockito.doThrow(new BusinessValidationException("")).when(employeeValidator).validateUpdate(employeeExpected);
        sut.update(employeeExpected.getEmployeeId(), employeeInput);
    }
}
