
package com.empsys.dao;

import com.empsys.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByEmail(String email);
    AttendanceEntity findByEmployeeIdAndDate(String employeeId, LocalDate date);
    Optional<AttendanceEntity> findByEmailAndDate(String email, LocalDate date);
    List<AttendanceEntity> findAllByDate(LocalDate date); // ✅ New method
	boolean existsByEmailAndDate(String email, LocalDate now);
	List<AttendanceEntity> findAllByOrderByDateDesc();
}

