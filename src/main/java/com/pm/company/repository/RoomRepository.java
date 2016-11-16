package com.pm.company.repository;

import com.pm.company.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pmackiewicz on 2015-11-05.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT COUNT(r) FROM Room r WHERE r.department.departmentId = :departmentId")
    Long countRooms(@Param("departmentId") Long departmentId);

    @Query("SELECT r FROM Room r WHERE r.department.departmentId = :departmentId")
    List<Room> findByDepartmentId(@Param("departmentId") Long departmentId);
}
