package com.erasmuarrem.ErasMove.repositories;

import com.erasmuarrem.ErasMove.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByOutgoingStudentID(Long id);
    List<Application> findByOutgoingStudent_DepartmentID(Long departmentID);
}
