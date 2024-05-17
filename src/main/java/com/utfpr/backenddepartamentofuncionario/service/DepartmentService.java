package com.utfpr.backenddepartamentofuncionario.service;

import com.utfpr.backenddepartamentofuncionario.entity.Department;
import com.utfpr.backenddepartamentofuncionario.entity.Employee;
import com.utfpr.backenddepartamentofuncionario.repository.DepartmentRepository;
import com.utfpr.backenddepartamentofuncionario.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired private DepartmentRepository deprepo;
    @Autowired private EmployeeRepository emprepo;

    public List<Department> findAll() {
        return deprepo.findAll();
    }

    public Department save(Department d) {
        return deprepo.save(d);
    }

    public Optional<Department> findByMinimumId() {
        return deprepo.findByMinimumId();
    }

    @Transactional
    public Employee saveDepartmentAndEmployee(Department d, Employee e) {
        var depsaved = deprepo.save(d);
        e.setDepartment(depsaved);
        return emprepo.save(e);
    }
}
