package com.utfpr.backenddepartamentofuncionario;

import com.utfpr.backenddepartamentofuncionario.entity.Department;
import com.utfpr.backenddepartamentofuncionario.entity.Employee;
import com.utfpr.backenddepartamentofuncionario.service.DepartmentService;
import com.utfpr.backenddepartamentofuncionario.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BackendDepartamentoFuncionarioApplication {
    private static final Logger log = LoggerFactory.getLogger(BackendDepartamentoFuncionarioApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BackendDepartamentoFuncionarioApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(DepartmentService depserv, EmployeeService empserv) {
        return args -> {
            var deparr = new Department[4];
            for (int i = 0; i < deparr.length; i++)
                deparr[i] = new Department();
            deparr[0].setName("Portaria");
            deparr[1].setName("Diretoria Financeira");
            deparr[2].setName("Diretoria Geral");
            deparr[3].setName("Engenharia de Software");
            for (var d : deparr)
                depserv.save(d);
            var emparr = new Employee[14];
            emparr[0] = new Employee("Joao Jose da Silva", 2, BigDecimal.valueOf(2000), "Porteiro", deparr[0]);
            emparr[1] = new Employee("Pedro dos Santos", 0, BigDecimal.valueOf(2100), "Porteiro", deparr[0]);
            emparr[2] = new Employee("Jonas Mariano", 0, BigDecimal.valueOf(3000), "Analista Financeiro", deparr[1]);
            emparr[3] = new Employee("Maria da Silva", 0, BigDecimal.valueOf(3500), "Analista Financeiro", deparr[1]);
            emparr[4] = new Employee("Antonio Sales", 1, BigDecimal.valueOf(5000), "Gerente de Finanças", deparr[1]);
            emparr[5] = new Employee("Marcos de Paula", 3, BigDecimal.valueOf(6500), "Gerente de Tecnologia", deparr[2]);
            emparr[6] = new Employee("Lucas da Silva", 3, BigDecimal.valueOf(5500), "Gerente de Segurança", deparr[2]);
            emparr[7] = new Employee("Mariana Alves", 1, BigDecimal.valueOf(6500), "Secretária", deparr[2]);
            emparr[8] = new Employee("Josefina Peres", 2, BigDecimal.valueOf(4500), "Gerente de Zeladoria", deparr[2]);
            emparr[9] = new Employee("Dirceu Pereira", 0, BigDecimal.valueOf(4000), "Desenvolvedor Backend", deparr[3]);
            emparr[10] = new Employee("Aldir Lacerda", 2, BigDecimal.valueOf(4100), "Desenvolvedor Frontend", deparr[3]);
            emparr[11] = new Employee("Luciano Ferreira", 2, BigDecimal.valueOf(3950), "Desenvolvedor Backend", deparr[3]);
            emparr[12] = new Employee("Nilson Marostega", 2, BigDecimal.valueOf(6500), "Gerente de Projetos", deparr[3]);
            emparr[13] = new Employee("Joaozinho Teles", 1, BigDecimal.valueOf(6600), "Analista de Negócio", deparr[3]);
            for (var e : emparr)
                empserv.save(e);

            log.info("==================== Questão 02 ====================");
            for (var e: empserv.findByZeroDependantsAndDepartmentName("Diretoria Financeira"))
                log.info(e.toString());
            log.info("==================== Questão 03 ====================");
            empserv.swapAllDepartments("Diretoria Geral", "Portaria");
            for (var e: empserv.findByDepartment(deparr[0]))
                log.info(e.toString());
            log.info("==================== Questão 04 ====================");
            empserv.deleteAllByDepartment("Portaria");
            var l = empserv.findByDepartment(deparr[0]);
            if (l.isEmpty())
                log.info("[]");
            else {
                for (var e : l)
                    log.info(e.toString());
            }
            log.info("==================== Questão 05 ====================");
            var d = new Department();
            d.setName("DEPARTAMENTO NOVO");
            var e = new Employee();
            e.setName("JOAOZINHO NOVO");
            e.setSalary(BigDecimal.valueOf(10500));
            e.setRole("Analista de Negócio");
            depserv.saveDepartmentAndEmployee(d, e);
            for (var res: empserv.findByDepartmentName("DEPARTAMENTO NOVO"))
                log.info(res.toString());
            System.exit(0);
        };
    }
}
