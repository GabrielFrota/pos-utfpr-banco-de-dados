package com.utfpr.backenddepartamentofuncionario.repository;

import com.utfpr.backenddepartamentofuncionario.entity.Department;
import com.utfpr.backenddepartamentofuncionario.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameAndDependants(String name, Integer dependants);

    @Query("SELECT e FROM Employee e WHERE e.department = :d")
    List<Employee> findByDepartment(Department d);

    @Query("SELECT e FROM Employee e WHERE e.department.name = :name")
    List<Employee> findByDepartmentName(String name);

    @Query("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)" +
            "ORDER BY e.id LIMIT 1")
    Optional<Employee> findByMaximumSalaryAndLowestId();

    @Query("SELECT e FROM Employee e ORDER BY e.salary DESC, e.id ASC LIMIT 3")
    List<Employee> findByHighestSalaryAndLowestIdLimitThree();

    @Query("SELECT e FROM Employee e WHERE e.dependants = 0 ORDER BY e.name")
    List<Employee> findByZeroDependantsOrderByName();

    @Query("SELECT e FROM Employee e WHERE e.salary > :val")
    List<Employee> findBySalaryGreaterThan(BigDecimal val);

    @Query (value = "SELECT * FROM employee WHERE salary > :val", nativeQuery=true)
    List<Employee> findBySalaryGreaterThanNative(BigDecimal val);

    @Procedure(name = "Employee.increaseAllSalariesByPercent")
    void increaseAllSalariesByPercent(@Param("percent") int percent);

    @Query("SELECT e FROM Employee e WHERE e.dependants = 0 AND e.department.name = :name")
    List<Employee> findByZeroDependantsAndDepartmentName(String name);

    @Modifying
    @Query("UPDATE Employee e SET e.department = :dest WHERE e.department = :src")
    void swapAllDepartments(Department src, Department dest);

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.department = :dep")
    void deleteAllByDepartment(Department dep);
}
