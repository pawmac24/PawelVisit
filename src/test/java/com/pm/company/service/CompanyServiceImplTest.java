package com.pm.company.service;

import com.pm.company.dto.AddressDTO;
import com.pm.company.dto.CompanyCreateDTO;
import com.pm.company.dto.CompanyUpdateDTO;
import com.pm.company.dto.primarykey.CompanyPrimaryKeyDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Address;
import com.pm.company.model.Company;
import com.pm.company.repository.CompanyRepository;
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
public class CompanyServiceImplTest {

    @InjectMocks
    private CompanyService sut;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private BusinessValidator<Company, Long> companyValidator;

    @Mock
    private Mapper mapper;

    @Before
    public void setUp() {
        sut = new CompanyServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    private Object[] provideCreate_Data(){
        CompanyCreateDTO company1 = new CompanyCreateDTO("companyName1", new AddressDTO());
        Company companyExpected1 = new Company(1L, company1.getName(), new Address());
        CompanyCreateDTO company2 = new CompanyCreateDTO("companyName2", new AddressDTO());
        Company companyExpected2 = new Company(2L, company2.getName(), new Address());
        CompanyCreateDTO company3 = new CompanyCreateDTO("companyName3", new AddressDTO());
        Company companyExpected3 = new Company(3L, company3.getName(), new Address());

        return new Object[]{
                        new Object[]{ company1, companyExpected1 },
                        new Object[]{ company2, companyExpected2 },
                        new Object[]{ company3, companyExpected3 }
        };
    }

    @Test
    @Parameters(method = "provideCreate_Data")
    public void testCreateCompanies(CompanyCreateDTO companyInput, Company companyExpected) throws BusinessValidationException {
        Mockito.doNothing().when(companyValidator).validateCreate(any(Company.class));
        when(companyRepository.save(any(Company.class))).thenReturn(companyExpected);

        CompanyPrimaryKeyDTO customerPrimaryKeyDTO = sut.create(companyInput);

        verify(companyValidator, times(1)).validateCreate(any(Company.class));
        verify(companyRepository, times(1)).save(any(Company.class));
        assertNotNull(customerPrimaryKeyDTO.getId());
        assertEquals(companyExpected.getCompanyId().longValue(), customerPrimaryKeyDTO.getId().longValue());
    }
    
    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideCreate_Data")
    public void testCreateCompaniesShouldFailBecauseOfValidaton(CompanyCreateDTO companyInput, Company companyExpected) throws BusinessValidationException {
        Mockito.doThrow(new BusinessValidationException("")).when(companyValidator).validateCreate(any(Company.class));
        when(companyRepository.save(any(Company.class))).thenReturn(companyExpected);

        sut.create(companyInput);
    }

    private Object[] provideUpdate_Data(){
        CompanyUpdateDTO company1 = new CompanyUpdateDTO("companyName1", new AddressDTO());
        Company companyExpected1 = new Company(1L, company1.getName(), new Address());
        CompanyUpdateDTO company2 = new CompanyUpdateDTO("companyName2", new AddressDTO());
        Company companyExpected2 = new Company(2L, company2.getName(), new Address());
        CompanyUpdateDTO company3 = new CompanyUpdateDTO("companyName3", new AddressDTO());
        Company companyExpected3 = new Company(3L, company3.getName(), new Address());

        return new Object[]{
                new Object[]{ company1, companyExpected1 },
                new Object[]{ company2, companyExpected2 },
                new Object[]{ company3, companyExpected3 }
        };
    }

    private Object[] provideUpdateNotExistient_Data(){
        CompanyUpdateDTO company1 = new CompanyUpdateDTO("companyName1", new AddressDTO());
        Long companyIdNotExistient1 = 1L;
        CompanyUpdateDTO company2 = new CompanyUpdateDTO("companyName2", new AddressDTO());
        Long companyIdNotExistient2 = 2L;
        CompanyUpdateDTO company3 = new CompanyUpdateDTO("companyName3", new AddressDTO());
        Long companyIdNotExistient3 = 3L;

        return new Object[]{
                new Object[]{ company1, companyIdNotExistient1 },
                new Object[]{ company2, companyIdNotExistient2 },
                new Object[]{ company3, companyIdNotExistient3 }
        };
    }

    @Test
    @Parameters(method = "provideUpdate_Data")
    public void testUpdateCustomersWithCorrectPesel(CompanyUpdateDTO companyInput, Company companyExpected) throws BusinessValidationException {
    	when(companyRepository.findOne(companyExpected.getCompanyId())).thenReturn(companyExpected);
    	when(companyRepository.save(any(Company.class))).thenReturn(companyExpected);

        sut.update(companyExpected.getCompanyId(), companyInput);

        verify(companyRepository, times(1)).save(any(Company.class));
        verify(companyValidator, times(1)).validateUpdate(companyExpected);
    }

    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideUpdateNotExistient_Data")
    public void testUpdateCompaniesShouldFailBecauseOfNonexistientCompany(CompanyUpdateDTO customerInput, Long companyIdNotExistient) throws BusinessValidationException {
    	when(companyRepository.findOne(companyIdNotExistient)).thenReturn(null);

        sut.update(companyIdNotExistient, customerInput);
        fail();
    }

    @Test(expected = BusinessValidationException.class)
    @Parameters(method = "provideUpdate_Data")
    public void testUpdateCompaniesShouldFailBecauseOfValidation(CompanyUpdateDTO companyInput, Company companyExpected) throws BusinessValidationException {
    	when(companyRepository.findOne(companyExpected.getCompanyId())).thenReturn(companyExpected);
    	when(companyRepository.save(any(Company.class))).thenReturn(companyExpected);
        Mockito.doThrow(new BusinessValidationException("")).when(companyValidator).validateUpdate(companyExpected);

        sut.update(companyExpected.getCompanyId(), companyInput);
    }
}
