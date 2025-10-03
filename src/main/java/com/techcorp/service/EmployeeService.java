package com.techcorp.service;

import com.techcorp.model.Employee;
import com.techcorp.model.Position;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Serwis zarządzający kolekcją pracowników oraz operacjami analitycznymi.
 */
public class EmployeeService {
    // Przechowujemy po znormalizowanym (lowercase) emailu dla wymuszenia unikalności niezależnie od wielkości liter.
    private final Map<String, Employee> employees = new LinkedHashMap<>();

    /**
     * Dodaje nowego pracownika do systemu.
     * @param employee pracownik do dodania
     * @throws DuplicateEmailException jeśli pracownik z tym adresem email już istnieje
     * @throws NullPointerException jeśli employee jest null
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee");
        String key = employee.getEmail().toLowerCase();
        if (employees.containsKey(key)) {
            throw new DuplicateEmailException(employee.getEmail());
        }
        employees.put(key, employee);
    }

    /**
     * Zwraca wszystkich pracowników (nowa lista, aby nie umożliwić modyfikacji wewnętrznej struktury).
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    /**
     * Wyszukuje pracowników zatrudnionych w konkretnej firmie.
     * @param companyName nazwa firmy (case-insensitive)
     * @return lista dopasowanych pracowników
     */
    public List<Employee> findByCompany(String companyName) {
        Objects.requireNonNull(companyName, "companyName");
        return stream()
                .filter(e -> e.getCompanyName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę pracowników posortowaną alfabetycznie po nazwisku, a następnie po pełnym imieniu i nazwisku.
     */
    public List<Employee> getEmployeesSortedByLastName() {
        Comparator<Employee> cmp = Comparator
                .comparing((Employee e) -> e.getLastName().toLowerCase())
                .thenComparing(e -> e.getFullName().toLowerCase());
        return stream().sorted(cmp).collect(Collectors.toList());
    }

    /**
     * Grupuje pracowników według stanowiska.
     * @return mapa: stanowisko -> lista pracowników
     */
    public Map<Position, List<Employee>> groupByPosition() {
        return stream().collect(Collectors.groupingBy(Employee::getPosition));
    }

    /**
     * Zlicza liczbę pracowników na każdym stanowisku.
     * @return mapa: stanowisko -> liczba pracowników
     */
    public Map<Position, Long> countByPosition() {
        return stream().collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
    }

    /**
     * Oblicza średnie wynagrodzenie w organizacji.
     * @return OptionalDouble z wartością średniej lub pusty jeśli brak pracowników
     */
    public OptionalDouble getAverageSalary() {
        return employees.values().stream().mapToDouble(Employee::getSalary).average();
    }

    /**
     * Zwraca pracownika z najwyższym wynagrodzeniem.
     * @return Optional z pracownikiem o najwyższym wynagrodzeniu lub pusty jeśli brak pracowników
     */
    public Optional<Employee> getTopEarner() {
        return stream().max(Comparator.comparingDouble(Employee::getSalary));
    }

    /**
     * Liczba wszystkich pracowników.
     */
    public int size() {
        return employees.size();
    }

    private Stream<Employee> stream() {
        return employees.values().stream();
    }
}
