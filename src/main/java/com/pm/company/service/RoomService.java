package com.pm.company.service;

import com.pm.company.dto.RoomCreateDTO;
import com.pm.company.dto.RoomUpdateDTO;
import com.pm.company.dto.primarykey.RoomPrimaryKeyDTO;
import com.pm.company.dto.result.RoomResultDTO;
import com.pm.company.exception.BusinessValidationException;

import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-05.
 */
public interface RoomService {

    RoomPrimaryKeyDTO create(RoomCreateDTO roomDTO) throws BusinessValidationException;

    void update(Long roomId, RoomUpdateDTO companyDTO) throws BusinessValidationException;

    RoomResultDTO find(Long roomId) throws BusinessValidationException;

    void delete(Long roomId) throws BusinessValidationException;

    List<RoomResultDTO> findAll();

    List<RoomResultDTO> findAllByDepartmentId(Long departmentId);

    boolean exists(Long roomId);
}
