package com.erasmuarrem.ErasMove.repositories;

import com.erasmuarrem.ErasMove.models.ErasmusUniversityDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ErasmusUniversityDepartmentRepository extends JpaRepository<ErasmusUniversityDepartment, Long> {
    Optional<ErasmusUniversityDepartment> findByErasmusUniversityIDAndDepartmentName(Long erasmusUniversityID, String departmentName);
}
