package com.utfpr.backenddepartamentofuncionario.service;

import com.utfpr.backenddepartamentofuncionario.entity.Department;
import com.utfpr.backenddepartamentofuncionario.entity.Employee;
import com.utfpr.backenddepartamentofuncionario.repository.DepartmentRepository;
import com.utfpr.backenddepartamentofuncionario.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired private EmployeeRepository emprepo;
    @Autowired private DepartmentRepository deprepo;
    @PersistenceContext private EntityManager em;

    public List<Employee> findAll() {
        return emprepo.findAll();
    }

    public List<Employee> findByDepartmentName(String name) {
        return emprepo.findByDepartmentName(name);
    }

    public Employee save(Employee e) {
        return emprepo.save(e);
    }

    public List<Employee> findByNameAndDependants(String name, Integer dependants) {
        return emprepo.findByNameAndDependants(name, dependants);
    }

    public List<Employee> findByDepartment(Department d) {
        return emprepo.findByDepartment(d);
    }

    public Optional<Employee> findByMaximumSalaryAndLowestId() {
        return emprepo.findByMaximumSalaryAndLowestId();
    }

    public List<Employee> findByHighestSalaryAndLowestIdLimitThree() {
        return emprepo.findByHighestSalaryAndLowestIdLimitThree();
    }

    public List<Employee> findByZeroDependantsOrderByName() {
        return emprepo.findByZeroDependantsOrderByName();
    }

    public List<Employee> findBySalaryGreaterThan(BigDecimal val) {
        return emprepo.findBySalaryGreaterThan(val);
    }

    public List<Employee> findBySalaryGreaterThanNative(BigDecimal val) {
        return emprepo.findBySalaryGreaterThanNative(val);
    }

    public List<Employee> findByDependants(int deps) {
        var q = em.createNamedQuery("findByDependants", Employee.class);
        q.setParameter("deps", deps);
        return q.getResultList();
    }

    public List<Employee> findByNameLike(String s) {
        var q = em.createNamedQuery("findByNameLike");
        q.setParameter("param", "%"+s+"%");
        return q.getResultList();
    }

    public void increaseAllSalariesByPercent(int percent) {
        emprepo.increaseAllSalariesByPercent(percent);
    }

    public List<Employee> findByZeroDependantsAndDepartmentName(String name) {
        return emprepo.findByZeroDependantsAndDepartmentName(name);
    }

    @Transactional
    public void swapAllDepartments(String srcName, String destName) {
        var src = deprepo.findByName(srcName);
        var dest = deprepo.findByName(destName);
        if (src.isEmpty() || dest.isEmpty())
            throw new IllegalArgumentException("Valid src and dest Departments are required");
        emprepo.swapAllDepartments(src.get(), dest.get());
    }

    @Transactional
    public void deleteAllByDepartment(String depName) {
        var dep = deprepo.findByName(depName);
        if (dep.isEmpty())
            throw new IllegalArgumentException("A valid department name is required");
        emprepo.deleteAllByDepartment(dep.get());
    }
}
