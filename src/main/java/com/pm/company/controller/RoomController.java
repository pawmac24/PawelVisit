package com.pm.company.controller;

import com.pm.company.dto.RoomCreateDTO;
import com.pm.company.dto.RoomUpdateDTO;
import com.pm.company.dto.primarykey.RoomPrimaryKeyDTO;
import com.pm.company.dto.result.RoomResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.exception.CustomValidationException;
import com.pm.company.service.RoomService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@RestController
@RequestMapping(value = "rooms")
public class RoomController {

    public final static Logger logger = Logger.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<RoomResultDTO> findAllRooms() {
        List<RoomResultDTO> roomResultDTOList = roomService.findAll();
        logger.info("Find " + roomResultDTOList);
        return roomResultDTOList;
    }

    @RequestMapping(value = "/list/{departmentId}", method = RequestMethod.GET)
    public @ResponseBody
    List<RoomResultDTO> findRoomsByDepartmentId(@PathVariable Long departmentId) {
        List<RoomResultDTO> roomResultDTOList = roomService.findAllByDepartmentId(departmentId);
        logger.info("Find " + roomResultDTOList);
        return roomResultDTOList;
    }

    @RequestMapping(value = "/get/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<?> findRoomById(@PathVariable Long roomId) throws BusinessValidationException {
        RoomResultDTO roomResultDTO = roomService.find(roomId);
        logger.info("Find " + roomResultDTO);
        return new ResponseEntity<>(roomResultDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomCreateDTO roomDTO) throws CustomValidationException,
            BusinessValidationException {
        RoomPrimaryKeyDTO roomPKeyDTO = roomService.create(roomDTO);
        logger.info("Add room with " + roomPKeyDTO.getId());
        return new ResponseEntity<>(roomPKeyDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{roomId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRoom(@PathVariable Long roomId, @Valid @RequestBody RoomUpdateDTO roomDTO)
            throws CustomValidationException, BusinessValidationException {
        roomService.update(roomId, roomDTO);
        logger.info("Update room with id " + roomId + " was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{roomId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRoomById(@PathVariable Long roomId) throws BusinessValidationException {
        roomService.delete(roomId);
        logger.info("Delete room by id = " + roomId + "successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
