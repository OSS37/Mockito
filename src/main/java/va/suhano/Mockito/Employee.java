package va.suhano.Mockito;

import java.util.Objects;

public class Employee {
    private String firstName;
    private String lastName;



    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName (){
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Имя: " + firstName +
                "\nФамилия" + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstName.equals(employee.firstName) && lastName.equals(employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}


