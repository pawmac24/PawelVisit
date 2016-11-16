package com.pm.company.controller;

import com.pm.company.dto.result.ExampleTestDTO;
import com.pm.company.service.ExampleTestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pmackiewicz on 2016-02-03.
 */
@RestController
@RequestMapping(value = "xxx")
public class ExampleTestController {

    public final static Logger logger = Logger.getLogger(ExampleTestController.class);

    @Autowired
    private ExampleTestService exampleTestService;

    @RequestMapping(value = "/prepare", method = RequestMethod.GET)
    public ResponseEntity<?> prepare()  {
        ExampleTestDTO exampleTestDTO = exampleTestService.get();
        logger.info("Find " + exampleTestDTO);
        return new ResponseEntity<>(exampleTestDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<?> find()  {
        ExampleTestDTO exampleTestDTO = exampleTestService.find();
        logger.info("Find " + exampleTestDTO);
        return new ResponseEntity<>(exampleTestDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> delete()  {
        ExampleTestDTO exampleTestDTO = exampleTestService.delete();
        logger.info("Find " + exampleTestDTO);
        return new ResponseEntity<>(exampleTestDTO, HttpStatus.OK);
    }

}
