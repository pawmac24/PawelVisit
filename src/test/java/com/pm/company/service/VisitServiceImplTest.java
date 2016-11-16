package com.pm.company.service;

import com.pm.company.dto.VisitCreateDTO;
import com.pm.company.dto.primarykey.VisitPrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Customer;
import com.pm.company.model.Employee;
import com.pm.company.model.Status;
import com.pm.company.model.Visit;
import com.pm.company.repository.CustomerRepository;
import com.pm.company.repository.EmployeeRepository;
import com.pm.company.repository.VisitRepository;
import com.pm.company.utils.DateUtils;
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

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by pmackiewicz on 2015-10-26.
 */
@RunWith(JUnitParamsRunner.class)
public class VisitServiceImplTest {

    @InjectMocks
    private VisitService sut;

    @Mock
    private VisitRepository visitRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
	private BusinessValidator<Visit, Long> visitValidator;
	
    @Mock
    private Mapper mapper;

    @Before
    public void setUp() {
        sut = new VisitServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    private Object[] provideCreateDTO_VisitPairs(){
        VisitCreateDTO visit1 = new VisitCreateDTO(
                DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 00)),
                DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 30)),
                true, 1L, 1L, Status.NEW);
        Visit visitExpected1 = new Visit(1L, visit1.getStart(), visit1.getEnd(), visit1.isActive(), new Customer(), new Employee(), 1);
        VisitCreateDTO visit2 = new VisitCreateDTO(
                DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 00)),
                DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 30)),
                true, 1L, 1L, Status.NEW);
        Visit visitExpected2 = new Visit(2L, visit2.getStart(), visit2.getEnd(), visit2.isActive(), new Customer(), new Employee(), 1);
        VisitCreateDTO visit3 = new VisitCreateDTO(
                DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 00)),
                DateUtils.asDate(LocalDateTime.of(2005, Month.JANUARY, 2, 10, 30)),
                true, 1L, 1L, Status.NEW);
        Visit visitExpected3 = new Visit(3L, visit3.getStart(), visit3.getEnd(), visit3.isActive(), new Customer(), new Employee(), 1);

        return new Object[]{
                        new Object[]{ visit1, visitExpected1 },
                        new Object[]{ visit2, visitExpected2 },
                        new Object[]{ visit3, visitExpected3 }
        };
    }


    @Test
    @Parameters(method = "provideCreateDTO_VisitPairs")
    public void testCreateVisits(VisitCreateDTO visitInput, Visit visitExpected) throws BusinessValidationException {
        when(employeeRepo.findOne(visitInput.getEmployeeId())).thenReturn(new Employee());
        when(customerRepo.findOne(visitInput.getCustomerId())).thenReturn(new Customer());
        when(mapper.map(visitInput, Visit.class)).thenReturn(new Visit());
        when(visitRepo.save(any(Visit.class))).thenReturn(visitExpected);

        VisitPrimaryKeyDTO visitPrimaryKeyDTO = sut.create(visitInput);

        verify(employeeRepo, times(1)).findOne(visitInput.getEmployeeId());
        verify(customerRepo, times(1)).findOne(visitInput.getCustomerId());
        verify(visitRepo, times(1)).save(any(Visit.class));
        assertNotNull(visitPrimaryKeyDTO.getId());
        assertEquals(visitExpected.getVisitId().longValue(), visitPrimaryKeyDTO.getId().longValue());
    }
    
    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideCreateDTO_VisitPairs")
    public void testCreateVisitsShouldFailBecauseOfValidaton(VisitCreateDTO visitInput, Visit visitExpected) throws BusinessValidationException {
        when(employeeRepo.findOne(visitInput.getEmployeeId())).thenReturn(new Employee());
        when(customerRepo.findOne(visitInput.getCustomerId())).thenReturn(new Customer());
        when(mapper.map(visitInput, Visit.class)).thenReturn(new Visit());
        Mockito.doThrow(new BusinessValidationException("")).when(visitValidator).validateCreate(any(Visit.class));
        when(visitRepo.save(any(Visit.class))).thenReturn(visitExpected);

        sut.create(visitInput);
    }
}
