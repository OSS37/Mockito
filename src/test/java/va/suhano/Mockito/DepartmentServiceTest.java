package va.suhano.Mockito;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import va.suhano.Mockito.Employee.DepartmentAndSalary.DepartmentAndSalaryService;
import va.suhano.Mockito.Employee.Employee.Employee;
import va.suhano.Mockito.Employee.Employee.EmployeeService;
import va.suhano.Mockito.exception.EmployeeNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;

    // аннотация @InjectMocks говорит о том, чтобы мокито создал реальный объект DepartmentAndSalaryService
    // и внутрь него закинул моки, которые определены выше
    @InjectMocks
    private DepartmentAndSalaryService departmentAndSalaryService;

    private List<Employee> employees;

    public static Stream<Arguments> MaxSalaryInDepartmentTestParams() {
        return Stream.of(
                of(1, new Employee("Yulia", "Sorokina", 1, 15000)),
                of(2, new Employee("Svetlana", "Petrova", 2, 17000)),
                of(3, new Employee("Valery", "Golov", 3, 20000))
        );
    }

    public static Stream<Arguments> MinSalaryInDepartmentTestParams() {
        return Stream.of(
                of(1, new Employee("Galina", "Ivanova", 1, 10000)),
                of(2, new Employee("Ivan", "Ivanov", 2, 15000)),
                of(3, new Employee("Valery", "Golov", 3, 20000))
        );
    }

    public static Stream<Arguments> EmployeesInDepartmentParams() {
        return Stream.of(
                Arguments.of(
                        1,
                        List.of(
                                new Employee("Galina", "Ivanova", 1, 10000),
                                new Employee("Yulia", "Sorokina", 1, 15000)
                        )
                ),
                Arguments.of(
                        2,
                        List.of(
                                new Employee("Ivan", "Ivanov", 2, 15000),
                                new Employee("Svetlana", "Petrova", 2, 17000)
                        )
                ),
                Arguments.of(
                        3,
                        Collections.singletonList(new Employee("Valery", "Golov", 3, 20000))
                ),
                Arguments.of(
                        4,
                        Collections.emptyList()
                )
        );

    }

    @BeforeEach
    public void beforeEach() {
        employees = List.of(
                new Employee("Galina", "Ivanova", 1, 10000),
                new Employee("Ivan", "Ivanov", 2, 15000),
                new Employee("Yulia", "Sorokina", 1, 15000),
                new Employee("Svetlana", "Petrova", 2, 17000),
                new Employee("Valery", "Golov", 3, 20000)
        );
        Mockito.when(employeeService.showAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("MaxSalaryInDepartmentTestParams")
    public void MaxSalaryInDepartmentTest(int department, Employee expected) {
        Assertions.assertThat(departmentAndSalaryService.MaxSalaryInDepartment(department))
                .isEqualTo(expected);
    }

    @Test
    public void MaxSalaryInDepartmentWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> departmentAndSalaryService.MaxSalaryInDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("MinSalaryInDepartmentTestParams")
    public void MinSalaryInDepartmentTest(int department, Employee expected) {
        Assertions.assertThat(departmentAndSalaryService.MinSalaryInDepartment(department))
                .isEqualTo(expected);
    }

    @Test
    public void MinSalaryInDepartmentWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> departmentAndSalaryService.MinSalaryInDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("EmployeesInDepartmentParams")
    public void EmployeesInDepartmentTest(int department, List<Employee> expected) {
        Assertions.assertThat(departmentAndSalaryService.EmployeesInDepartment(department))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void AllEmployeesTest() {
        Map<Integer, List<Employee>> expected = Map.of(
                1,
                List.of(
                        new Employee("Galina", "Ivanova", 1, 10000),
                        new Employee("Yulia", "Sorokina", 1, 15000)
                ),
                2,
                List.of(
                        new Employee("Ivan", "Ivanov", 2, 15000),
                        new Employee("Svetlana", "Petrova", 2, 17000)
                ),
                3,
                Collections.singletonList(new Employee("Valery", "Golov", 3, 20000))
        );
        Assertions.assertThat(departmentAndSalaryService.AllEmployees())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }
}

