package va.suhano.Mockito.Employee.DepartmentAndSalary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import va.suhano.Mockito.Employee.Employee.Employee;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")

public class DepartmentAndSalaryController {
    public final DepartmentAndSalaryService service;

    public DepartmentAndSalaryController(DepartmentAndSalaryService service) {
        this.service = service;
    }

    @GetMapping("/max-salary")
    public Employee MaxSalaryInDepartment(@RequestParam("departmentId") int department) {
        return service.MaxSalaryInDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee MinSalaryInDepartment(@RequestParam("departmentId") int department) {
        return service.MinSalaryInDepartment(department);
    }

    @GetMapping("/all")
    public List<Employee> EmployeesInDepartment(@RequestParam("departmentId") int department) {
        return service.EmployeesInDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> AllEmployees() {
        return service.AllEmployees();
    }
}
