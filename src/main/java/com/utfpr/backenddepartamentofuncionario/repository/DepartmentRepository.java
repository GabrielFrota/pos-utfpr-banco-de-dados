package com.utfpr.backenddepartamentofuncionario.repository;

import com.utfpr.backenddepartamentofuncionario.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    @Query("SELECT d FROM Department d WHERE d.id = (SELECT MIN(d.id) FROM Department d)")
    Optional<Department> findByMinimumId();
}
