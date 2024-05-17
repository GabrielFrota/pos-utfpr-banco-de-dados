package com.utfpr.backenddepartamentofuncionario.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@NamedQuery(
        name = "findByDependants",
        query = "SELECT e FROM Employee e WHERE e.dependants = :deps"
)
@NamedNativeQuery(
        name = "findByNameLike",
        query = "SELECT * FROM employee WHERE name LIKE :param",
        resultClass = Employee.class
)
@NamedStoredProcedureQuery(
        name = "Employee.increaseAllSalariesByPercent",
        procedureName = "INCREASE_ALL_SALARIES_BY_PERCENT", parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "percent", type = int.class)
        })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer dependants;
    private BigDecimal salary;
    private String role;
    @ManyToOne
    @JoinColumn
    private Department department;

    public Employee() {
    }

    public Employee(String name, Integer dependants, BigDecimal salary, String role, Department department) {
        this.name = name;
        this.dependants = dependants;
        this.salary = salary;
        this.role = role;
        this.department = department;
    }
}
