package com.pm.company.service;

import com.pm.company.dto.RoomCreateDTO;
import com.pm.company.dto.RoomUpdateDTO;
import com.pm.company.dto.primarykey.RoomPrimaryKeyDTO;
import com.pm.company.dto.result.RoomResultDTO;
import com.pm.company.exception.BusinessValidationException;
import com.pm.company.model.Department;
import com.pm.company.model.Room;
import com.pm.company.repository.DepartmentRepository;
import com.pm.company.repository.RoomRepository;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-10-20.
 */
@Service
public class RoomServiceImpl implements RoomService {

	public final static Logger logger = Logger.getLogger(RoomServiceImpl.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private Mapper mapper;

	@Override
	public RoomPrimaryKeyDTO create(RoomCreateDTO roomDTO) throws BusinessValidationException {
		Department department = departmentRepository.findOne(roomDTO.getDepartmentId());

		Room room = mapper.map(roomDTO, Room.class);
		room.setDepartment(department);
		room = roomRepository.save(room);
		logger.info("create " + room);

		RoomPrimaryKeyDTO roomPKeyDTO = new RoomPrimaryKeyDTO(room.getRoomId());
		return roomPKeyDTO;
	}

	@Override
	public void update(Long roomId, RoomUpdateDTO roomDTO) throws BusinessValidationException {
		Room room = roomRepository.findOne(roomId);
		mapper.map(roomDTO, room);

		room = roomRepository.save(room);
		logger.info("update " + room);
	}

	@Override
	public RoomResultDTO find(Long roomId) throws BusinessValidationException {
		Room room = roomRepository.findOne(roomId);
		RoomResultDTO roomResultDTO = mapper.map(room, RoomResultDTO.class);
		return roomResultDTO;
	}

	@Override
	public void delete(Long roomId) throws BusinessValidationException {
		roomRepository.delete(roomId);
	}

	@Override
	public List<RoomResultDTO> findAll() {
		List<Room> roomList = roomRepository.findAll();
		List<RoomResultDTO> roomResultDTOList = new ArrayList<>();

		for (Room room : roomList) {
			roomResultDTOList.add(mapper.map(room, RoomResultDTO.class));
		}
		return roomResultDTOList;
	}

	@Override
	public List<RoomResultDTO> findAllByDepartmentId(Long departmentId) {
		List<Room> roomList = roomRepository.findByDepartmentId(departmentId);
		List<RoomResultDTO> roomResultDTOList = new ArrayList<>();

		for (Room room : roomList) {
			roomResultDTOList.add(mapper.map(room, RoomResultDTO.class));
		}
		return roomResultDTOList;
	}

	@Override
	public boolean exists(Long roomId) {
		return roomRepository.exists(roomId);
	}
}
