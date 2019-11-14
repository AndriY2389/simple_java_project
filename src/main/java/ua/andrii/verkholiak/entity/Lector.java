package ua.andrii.verkholiak.entity;

import java.util.Objects;

public class Lector {

    private Integer id;
    private String firstName;
    private String lastName;
    private Degree degree;
    private Integer salary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lector lector = (Lector) o;
        return id.equals(lector.id) &&
                firstName.equals(lector.firstName) &&
                lastName.equals(lector.lastName) &&
                degree == lector.degree &&
                salary.equals(lector.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, degree, salary);
    }

    @Override
    public String toString() {
        return "Lector{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", degree=" + degree +
                ", salary=" + salary +
                '}';
    }
}
