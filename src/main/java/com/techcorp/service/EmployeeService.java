package com.techcorp.service;

import com.techcorp.model.Employee;
import com.techcorp.model.Position;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Serwis zarządzający pracownikami w organizacji.
public class EmployeeService {
    private final Map<String, Employee> employees = new LinkedHashMap<>();

    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee");
        String key = employee.getEmail().toLowerCase();
        if (employees.containsKey(key)) {
            throw new DuplicateEmailException(employee.getEmail());
        }
        employees.put(key, employee);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public List<Employee> findByCompany(String companyName) {
        Objects.requireNonNull(companyName, "companyName");
        return stream()
                .filter(e -> e.getCompanyName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesSortedByLastName() {
        Comparator<Employee> cmp = Comparator
                .comparing((Employee e) -> e.getLastName().toLowerCase())
                .thenComparing(e -> e.getFullName().toLowerCase());
        return stream().sorted(cmp).collect(Collectors.toList());
    }

    public Map<Position, List<Employee>> groupByPosition() {
        return stream().collect(Collectors.groupingBy(Employee::getPosition));
    }

    public Map<Position, Long> countByPosition() {
        return stream().collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
    }

    public OptionalDouble getAverageSalary() {
        return employees.values().stream().mapToDouble(Employee::getSalary).average();
    }

    public Optional<Employee> getTopEarner() {
        return stream().max(Comparator.comparingDouble(Employee::getSalary));
    }

    public int size() {
        return employees.size();
    }

    private Stream<Employee> stream() {
        return employees.values().stream();
    }
}
