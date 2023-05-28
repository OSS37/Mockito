package va.suhano.Mockito;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import va.suhano.Mockito.Employee.Employee.Employee;
import va.suhano.Mockito.Employee.Employee.EmployeeService;
import va.suhano.Mockito.exception.EmployeeAlreadyAddedException;
import va.suhano.Mockito.exception.EmployeeNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    private EmployeeService employeeService = new EmployeeService();


    @BeforeEach
    public void beforeEach() {
        employeeService.add("Olya", "Sukhanova", 3, 100000);
        employeeService.add("Yulia", "Sorokina", 4, 90000);
        employeeService.add("Maks", "Fadeev", 1, 150000);
    }

    @AfterEach
    public void afterEach() {
        employeeService.showAll()
                .forEach(employee -> employeeService.remove(employee.getFirstName(), employee.getLastName()));
    }

    @Test
    public void addTest() {
        Employee expected = new Employee("Marina", "Ivanova", 1, 80000);
        Assertions.assertThat(employeeService.add("Marina", "Ivanova", 1, 80000))
                .isEqualTo(expected)
                .isIn(employeeService.showAll());
        Assertions.assertThat(employeeService.find("Marina", "Ivanova")).isEqualTo(expected);
    }

    @Test
    public void addWhenAlreadyExistsTest() {
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.add("Olya", "Sukhanova", 3, 100000));
    }

    @Test
    public void removeTest() {
        Employee expected = new Employee("Olya", "Sukhanova", 3, 100000);
        Assertions.assertThat(employeeService.remove("Olya", "Sukhanova"))
                .isEqualTo(expected)
                .isNotIn(employeeService.showAll());
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Olya", "Sukhanova"));
    }

    @Test
    public void removeWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Galina", "Sukhanova"));
    }

    @Test
    public void findTest() {
        Employee expected = new Employee("Olya", "Sukhanova", 3, 100000);
        Assertions.assertThat(employeeService.find("Olya", "Sukhanova"))
                .isEqualTo(expected)
                .isIn(employeeService.showAll());
    }

    @Test
    public void findWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Galina", "Sukhanova"));
    }

    @Test
    public void showAllTest() {
        Assertions.assertThat(employeeService.showAll())
                .containsExactly(
                        new Employee("Olya", "Sukhanova", 3, 100000),
                        new Employee("Yulia", "Sorokina", 4, 90000),
                        new Employee("Maks", "Fadeev", 1, 150000)
                );
    }
}
