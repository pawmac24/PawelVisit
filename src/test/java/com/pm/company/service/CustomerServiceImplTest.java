package com.pm.company.service;

import static org.junit.Assert.assertEquals;
import com.pm.company.dto.primarykey.CustomerPrimaryKeyDTO;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;

import com.pm.company.exception.BusinessValidationException;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import com.pm.company.dto.CustomerCreateDTO;
import com.pm.company.dto.CustomerUpdateDTO;
import com.pm.company.model.Customer;
import com.pm.company.repository.CustomerRepository;
import com.pm.company.utils.DateUtils;
import com.pm.company.validator.business.BusinessValidator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Created by pmackiewicz on 2015-10-26.
 */
@RunWith(JUnitParamsRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl sut;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
	private BusinessValidator<Customer, Long> customerValidator;

    @Mock
    private Mapper mapper;

    @Before
    public void setUp() {
        sut = new CustomerServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    private Object[] provideCreate_Data(){
        CustomerCreateDTO customer1 = new CustomerCreateDTO("12345678901", "Paul", "Mc Cook", DateUtils.asDate(LocalDate.of(2005, Month.SEPTEMBER, 5)));
        Customer customerExpected1 = new Customer(1L, customer1.getPesel(), customer1.getFirstName(), customer1.getLastName(), customer1.getDateOfBirth());
        CustomerCreateDTO customer2 = new CustomerCreateDTO("12345677777", "Andrew", "Coger", DateUtils.asDate(LocalDate.of(2003, Month.DECEMBER, 21)));
        Customer customerExpected2 = new Customer(2L, customer2.getPesel(), customer2.getFirstName(), customer2.getLastName(), customer2.getDateOfBirth());
        CustomerCreateDTO customer3 = new CustomerCreateDTO("55555577777", "Mathew", "Duck", DateUtils.asDate(LocalDate.of(1986, Month.JUNE, 17)));
        Customer customerExpected3 = new Customer(3L, customer3.getPesel(), customer3.getFirstName(), customer3.getLastName(), customer3.getDateOfBirth());

        return new Object[]{
                        new Object[]{ customer1, customerExpected1 },
                        new Object[]{ customer2, customerExpected2 },
                        new Object[]{ customer3, customerExpected3 }
        };
    }

    private Object[] provideUpdate_Data(){
        CustomerUpdateDTO customer1 = new CustomerUpdateDTO("Paul", "Mc Cook");
        Customer customerExpected1 = new Customer(1L, "12345678901", customer1.getFirstName(), customer1.getLastName(), DateUtils.asDate(LocalDate.of(2005, Month.SEPTEMBER, 5)));
        CustomerUpdateDTO customer2 = new CustomerUpdateDTO("Andrew", "Coger");
        Customer customerExpected2 = new Customer(2L, "12345677777", customer2.getFirstName(), customer2.getLastName(), DateUtils.asDate(LocalDate.of(2003, Month.DECEMBER, 21)));
        CustomerUpdateDTO customer3 = new CustomerUpdateDTO("Mathew", "Duck");
        Customer customerExpected3 = new Customer(3L, "55555577777", customer3.getFirstName(), customer3.getLastName(), DateUtils.asDate(LocalDate.of(1986, Month.JUNE, 17)));

        return new Object[]{
                        new Object[]{ customer1, customerExpected1 },
                        new Object[]{ customer2, customerExpected2 },
                        new Object[]{ customer3, customerExpected3 }
        };
    }

    private Object[] provideUpdateNotexisting_Data(){
        CustomerUpdateDTO customer1 = new CustomerUpdateDTO("Paul", "Mc Cook");
        Long customerIdNotExisting1 = 1L;
        CustomerUpdateDTO customer2 = new CustomerUpdateDTO("Andrew", "Coger");
        Long customerIdNotExisting2 = 2L;
        CustomerUpdateDTO customer3 = new CustomerUpdateDTO("Mathew", "Duck");
        Long customerIdNotExisting3 = 3L;

        return new Object[]{
                new Object[]{ customer1, customerIdNotExisting1 },
                new Object[]{ customer2, customerIdNotExisting2 },
                new Object[]{ customer3, customerIdNotExisting3 }
        };
    }

    private Object[] provideUsersWithTheSamePesel(){
        Customer customer1 = new Customer(1L, "12345678901", "Paul", "Mc Cook", DateUtils.asDate(LocalDate.of(2005, Month.SEPTEMBER, 5)));
        Customer customer2 = new Customer(2L, "12345678901", "Andrew", "Coger", DateUtils.asDate(LocalDate.of(2003, Month.DECEMBER, 21)));
        Customer customer3 = new Customer(3L, "12345678901", "Mathew", "Duck", DateUtils.asDate(LocalDate.of(1986, Month.JUNE, 17)));
        return new Object[]{
                new Object[]{ customer1, 0L, false },
                new Object[]{ customer2, 1L, true},
                new Object[]{ customer3, 2L, true}
        };
    }

    @Test
    @Parameters(method = "provideCreate_Data")
    public void testCreateCustomersWithCorrectPesel(CustomerCreateDTO customerInput, Customer customerExpected) throws BusinessValidationException {
        Mockito.doNothing().when(customerValidator).validateCreate(any(Customer.class));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerExpected);

        CustomerPrimaryKeyDTO customerPrimaryKeyDTO = sut.create(customerInput);

        verify(customerValidator, times(1)).validateCreate(any(Customer.class));
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertNotNull(customerPrimaryKeyDTO.getId());
        assertEquals(customerExpected.getCustomerId().longValue(), customerPrimaryKeyDTO.getId().longValue());
    }
    
    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideCreate_Data")
    public void testCreateCustomersShouldFailBecauseOfValidaton(CustomerCreateDTO customerInput, Customer customerExpected) throws BusinessValidationException {
        Mockito.doThrow(new BusinessValidationException("")).when(customerValidator).validateCreate(any(Customer.class));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerExpected);

        sut.create(customerInput);
    }
    
    @Test
    @Parameters(method = "provideUpdate_Data")
    public void testUpdateCustomersWithCorrectPesel(CustomerUpdateDTO customerInput, Customer customerExpected) throws BusinessValidationException {
    	when(customerRepository.findOne(customerExpected.getCustomerId())).thenReturn(customerExpected);
    	when(customerRepository.save(any(Customer.class))).thenReturn(customerExpected);
        
        sut.update(customerExpected.getCustomerId(), customerInput);

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(customerValidator, times(1)).validateUpdate(customerExpected);
    }
    
    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideUpdateNotexisting_Data")
    public void testUpdateCustomersShouldFailBecauseOfNonexistiendCustomer(CustomerUpdateDTO customerInput, Long customerIdNotexistient) throws BusinessValidationException {
    	when(customerRepository.findOne(customerIdNotexistient)).thenReturn(null);

        sut.update(customerIdNotexistient, customerInput);
        fail();
    }
    
    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideUpdate_Data")
    public void testUpdateCustomersShouldFailBecauseOfValidation(CustomerUpdateDTO customerInput, Customer customerExpected) throws BusinessValidationException {
    	when(customerRepository.findOne(customerExpected.getCustomerId())).thenReturn(customerExpected);
    	when(customerRepository.save(any(Customer.class))).thenReturn(customerExpected);
        Mockito.doThrow(new BusinessValidationException("")).when(customerValidator).validateUpdate(customerExpected);

        sut.update(customerExpected.getCustomerId(), customerInput);
    }
    
    @Test
    @Parameters(method = "provideUsersWithTheSamePesel")
    public void testPeselWhichExists(Customer customer, Long givenPeselCount, boolean existsExpected) {
        when(customerRepository.countByPesel(customer.getPesel())).thenReturn(givenPeselCount);

        assertEquals(existsExpected, sut.peselAlreadyInUse(customer.getPesel()));
    }
}
