package com.pm.company.controller;

import com.pm.company.dto.result.XXXDTO;
import com.pm.company.service.XXXService;
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
public class XXXController {

    public final static Logger logger = Logger.getLogger(XXXController.class);

    @Autowired
    private XXXService xxxService;

    @RequestMapping(value = "/prepare", method = RequestMethod.GET)
    public ResponseEntity<?> prepare()  {
        XXXDTO xxxDTO = xxxService.get();
        logger.info("Find " + xxxDTO);
        return new ResponseEntity<>(xxxDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<?> find()  {
        XXXDTO xxxDTO = xxxService.find();
        logger.info("Find " + xxxDTO);
        return new ResponseEntity<>(xxxDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> delete()  {
        XXXDTO xxxDTO = xxxService.delete();
        logger.info("Find " + xxxDTO);
        return new ResponseEntity<>(xxxDTO, HttpStatus.OK);
    }

}
