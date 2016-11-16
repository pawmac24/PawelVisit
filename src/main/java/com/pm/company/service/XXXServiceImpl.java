package com.pm.company.service;

import com.pm.company.dto.result.XXXDTO;
import com.pm.company.model.XXXEmployee;
import com.pm.company.model.XXXPhone;
import com.pm.company.repository.XXXRepository;
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
public class XXXServiceImpl implements XXXService{

    public final static Logger logger = Logger.getLogger(XXXServiceImpl.class);

    @Autowired
    private XXXRepository xxxRepository;

    @Override
    public XXXDTO get() {
        logger.info("===get===");
        XXXEmployee xxxEmployee = new XXXEmployee("Bob", "Way", new BigDecimal(100.50).setScale(2));
        xxxEmployee.addPhone( new XXXPhone("home", "613", "792-0001"));
        xxxEmployee.addPhone( new XXXPhone("work", "613", "494-1234"));
        XXXEmployee xxxEmployeeRyzy = xxxRepository.save(xxxEmployee);
        logger.info("(zobaczymy)" + xxxEmployeeRyzy);
//        logger.info("(1)" + xxxEmployeeRyzy);
        xxxEmployeeRyzy.setFirstName("Bobik");
        xxxEmployeeRyzy.setLastName("Wayek");
//        logger.info("(2)" + xxxEmployeeRyzy);

        XXXEmployee xxxEmployee2 = new XXXEmployee("Joe", "Smith", new BigDecimal(300.00).setScale(2));
        xxxEmployee2.addPhone( new XXXPhone("work", "416", "892-0005"));
        XXXEmployee xxxEmployeeRyzy2 = xxxRepository.save(xxxEmployee2);
//        logger.info("(3)" + xxxEmployeeRyzy2);

        XXXEmployee xxxEmployeeFind = xxxRepository.findOne(xxxEmployeeRyzy.getId());
//        logger.info("(4)" + xxxEmployeeFind);
        XXXEmployee xxxEmployeeFind2 = xxxRepository.findOne(xxxEmployeeRyzy2.getId());
//        logger.info("(5)" + xxxEmployeeFind2);

        xxxEmployeeFind.setFirstName("Bobas");
        xxxEmployeeFind.setLastName("Wayas");
//        logger.info("(6)" + xxxEmployeeFind);
//        xxxRepository.delete(xxxEmployee);
//        xxxRepository.delete(xxxEmployee2);

        return new XXXDTO();
    }

    @Override
    public XXXDTO find() {
        logger.info("===find===");
        xxxRepository.findAll();
        return new XXXDTO();
    }

    @Override
    public XXXDTO delete() {
        logger.info("===delete===");
        xxxRepository.deleteAll();
        return new XXXDTO();
    }
}
