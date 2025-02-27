package com.HMS.Repository;

import com.HMS.Entity.Property;
import com.HMS.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
    @Query("select r from Rooms r where r.date between :startDate and :endDate and r.type=:type and r.property=:property")
    List<Rooms> findByTypeAndProperty(
                @Param("startDate") LocalDate fromDate,
                @Param("endDate") LocalDate toDate,
                @Param("type") String type,
                @Param("property") Property property);


}