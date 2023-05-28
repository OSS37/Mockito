package va.suhano.Mockito.Employee.Employee;


import org.springframework.stereotype.Service;
import va.suhano.Mockito.exception.EmployeeAlreadyAddedException;
import va.suhano.Mockito.exception.EmployeeNotFoundException;

import java.util.*;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();



    public Employee add(String firstName, String lastName, int department, double salary) {
        Employee person = new Employee(firstName, lastName, department, salary);
        if (employees.contains(person)) {
            throw new EmployeeAlreadyAddedException("Добавляемый сотрудник уже есть в списке");
        }
        employees.add(person);
        return person;
    }

    public Employee remove (String firstName, String lastName) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getFirstName().equals(firstName) && emp.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        employees.remove(employee);
        return employee;
    }


    public Employee find (String firstName, String lastName) {
        return employees.stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);

    }
    public List <Employee> showAll() {
        return new ArrayList<>(employees);
    }
}
