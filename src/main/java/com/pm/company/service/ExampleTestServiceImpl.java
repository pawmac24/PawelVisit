package com.pm.company.service;

import com.pm.company.dto.result.ExampleTestDTO;
import com.pm.company.model.ExampleTestEmployee;
import com.pm.company.model.ExampleTestPhone;
import com.pm.company.repository.ExampleTestRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by pmackiewicz on 2016-02-03.
 */
@Service
@Transactional
public class ExampleTestServiceImpl implements ExampleTestService {

    public final static Logger logger = Logger.getLogger(ExampleTestService.class);

    @Autowired
    private ExampleTestRepository exampleTestRepository;

    @Override
    public ExampleTestDTO get() {
        logger.info("===get===");
        ExampleTestEmployee exampleTestEmployee = new ExampleTestEmployee("Bob", "Way", new BigDecimal(100.50).setScale(2));
        exampleTestEmployee.addPhone( new ExampleTestPhone("home", "613", "792-0001"));
        exampleTestEmployee.addPhone( new ExampleTestPhone("work", "613", "494-1234"));
        ExampleTestEmployee xxxEmployeeRyzy = exampleTestRepository.save(exampleTestEmployee);
        logger.info("(see)" + xxxEmployeeRyzy);
//        logger.info("(1)" + xxxEmployeeRyzy);
        xxxEmployeeRyzy.setFirstName("Bobik");
        xxxEmployeeRyzy.setLastName("Wayek");
//        logger.info("(2)" + xxxEmployeeRyzy);

        ExampleTestEmployee xxxEmployee2 = new ExampleTestEmployee("Joe", "Smith", new BigDecimal(300.00).setScale(2));
        xxxEmployee2.addPhone( new ExampleTestPhone("work", "416", "892-0005"));
        ExampleTestEmployee xxxEmployeeRyzy2 = exampleTestRepository.save(xxxEmployee2);
//        logger.info("(3)" + xxxEmployeeRyzy2);

        ExampleTestEmployee exampleTestEmployeeFind = exampleTestRepository.findOne(xxxEmployeeRyzy.getId());
//        logger.info("(4)" + exampleTestEmployeeFind);
        ExampleTestEmployee xxxEmployeeFind2 = exampleTestRepository.findOne(xxxEmployeeRyzy2.getId());
//        logger.info("(5)" + xxxEmployeeFind2);

        exampleTestEmployeeFind.setFirstName("Bobas");
        exampleTestEmployeeFind.setLastName("Wayas");
//        logger.info("(6)" + exampleTestEmployeeFind);
//        exampleTestRepository.delete(exampleTestEmployee);
//        exampleTestRepository.delete(xxxEmployee2);

        return new ExampleTestDTO();
    }

    @Override
    public ExampleTestDTO find() {
        logger.info("===find===");
        exampleTestRepository.findAll();
        return new ExampleTestDTO();
    }

    @Override
    public ExampleTestDTO delete() {
        logger.info("===delete===");
        exampleTestRepository.deleteAll();
        return new ExampleTestDTO();
    }
}
