package com.techcorp.service;

import com.techcorp.model.Employee;
import com.techcorp.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;

    @BeforeEach
    void setUp() {
        service = new EmployeeService();
        service.addEmployee(new Employee("Jan Kowalski", "jan@corp.com", "TechCorp", Position.PROGRAMISTA, 8500));
        service.addEmployee(new Employee("Anna Nowak", "anna@corp.com", "TechCorp", Position.MANAGER, 12500));
        service.addEmployee(new Employee("Piotr Ziel", "piotr@other.com", "OtherCorp", Position.STAZYSTA, 3200));
        service.addEmployee(new Employee("Karol Prezes", "karol@corp.com", "TechCorp", Position.PREZES, 30000));
    }

    @Test
    @DisplayName("Dodanie duplikatu email powinno rzucić DuplicateEmailException")
    void duplicateEmail() {
        assertThrows(DuplicateEmailException.class, () ->
                service.addEmployee(new Employee("X", "JAN@corp.com", "TechCorp", Position.STAZYSTA, 1000))
        );
    }

    @Test
    @DisplayName("Dodanie null powinno rzucić NullPointerException")
    void addNullEmployee() {
        assertThrows(NullPointerException.class, () -> service.addEmployee(null));
    }

    @Test
    @DisplayName("findByCompany zwraca pracowników danej firmy (case-insensitive)")
    void findByCompany() {
        List<Employee> result = service.findByCompany("techcorp");
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(e -> e.getCompanyName().equals("TechCorp")));
    }

    @Test
    @DisplayName("findByCompany dla braku wyników zwraca pustą listę")
    void findByCompanyNoResults() {
        List<Employee> result = service.findByCompany("NieIstnieje");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Sortowanie po nazwisku i pełnym imieniu")
    void sorting() {
        List<Employee> sorted = service.getEmployeesSortedByLastName();
        // oczekujemy porządku: Jan Kowalski, Anna Nowak, Karol Prezes, Piotr Ziel (alfabetycznie po nazwisku)
        assertEquals("Jan Kowalski", sorted.get(0).getFullName());
        assertEquals("Anna Nowak", sorted.get(1).getFullName());
        assertEquals("Karol Prezes", sorted.get(2).getFullName());
        assertEquals("Piotr Ziel", sorted.get(3).getFullName());
    }

    @Test
    @DisplayName("Sortowanie przy takich samych nazwiskach używa pełnego imienia")
    void sortingSameLastName() {
        service.addEmployee(new Employee("Adam Nowak", "adam.nowak@corp.com", "TechCorp", Position.STAZYSTA, 4000));
        service.addEmployee(new Employee("Zuzanna Nowak", "z.nowak@corp.com", "TechCorp", Position.STAZYSTA, 4000));
        List<Employee> sorted = service.getEmployeesSortedByLastName();
        // Wyciągnij tylko Nowaków i sprawdź kolejność po pełnym imieniu (Adam, Anna, Zuzanna)
        List<String> nowaks = sorted.stream().filter(e -> e.getLastName().equals("Nowak")).map(Employee::getFullName).toList();
        assertEquals(List.of("Adam Nowak", "Anna Nowak", "Zuzanna Nowak"), nowaks);
    }

    @Test
    @DisplayName("Grupowanie po stanowisku")
    void groupByPosition() {
        Map<Position, List<Employee>> map = service.groupByPosition();
        assertEquals(4, map.values().stream().mapToInt(List::size).sum());
        assertEquals(1, map.get(Position.PREZES).size());
        assertEquals(1, map.get(Position.MANAGER).size());
        assertEquals(1, map.get(Position.PROGRAMISTA).size());
        assertEquals(1, map.get(Position.STAZYSTA).size());
    }

    @Test
    @DisplayName("Grupowanie po stanowisku z wieloma na tym samym stanowisku")
    void groupByPositionMultiple() {
        service.addEmployee(new Employee("Staz1", "s1@corp.com", "TechCorp", Position.STAZYSTA, 3100));
        service.addEmployee(new Employee("Staz2", "s2@corp.com", "TechCorp", Position.STAZYSTA, 3150));
        Map<Position, List<Employee>> map = service.groupByPosition();
        assertEquals(6, map.values().stream().mapToInt(List::size).sum());
        assertEquals(3, map.get(Position.STAZYSTA).size());
    }

    @Test
    @DisplayName("Zliczanie po stanowisku")
    void countByPosition() {
        Map<Position, Long> counts = service.countByPosition();
        assertEquals(1L, counts.get(Position.PREZES));
        assertEquals(1L, counts.get(Position.MANAGER));
        assertEquals(1L, counts.get(Position.PROGRAMISTA));
        assertEquals(1L, counts.get(Position.STAZYSTA));
    }

    @Test
    @DisplayName("Zliczanie po stanowisku po dodaniu nowych")
    void countByPositionAfterAdding() {
        service.addEmployee(new Employee("Extra Prog", "prog2@corp.com", "TechCorp", Position.PROGRAMISTA, 9000));
        Map<Position, Long> counts = service.countByPosition();
        assertEquals(2L, counts.get(Position.PROGRAMISTA));
    }

    @Test
    @DisplayName("Średnia pensja")
    void averageSalary() {
        double avg = service.getAverageSalary().orElseThrow();
        assertTrue(avg > 0);
    }

    @Test
    @DisplayName("Średnia pensja dla pustego serwisu jest pusta")
    void averageSalaryEmpty() {
        EmployeeService empty = new EmployeeService();
        OptionalDouble avg = empty.getAverageSalary();
        assertTrue(avg.isEmpty());
    }

    @Test
    @DisplayName("Najwyżej zarabiający")
    void topEarner() {
        Employee top = service.getTopEarner().orElseThrow();
        assertEquals("karol@corp.com", top.getEmail());
    }

    @Test
    @DisplayName("Najwyżej zarabiający dla pustego serwisu jest pusty")
    void topEarnerEmpty() {
        EmployeeService empty = new EmployeeService();
        assertTrue(empty.getTopEarner().isEmpty());
    }

    @Nested
    class DefensiveCopies {
        @Test
        @DisplayName("Lista z getAllEmployees nie powinna być modyfikowalna na serwisie")
        void returnedListIsCopy() {
            int size = service.size();
            List<Employee> list = service.getAllEmployees();
            list.clear();
            assertEquals(size, service.size());
        }
    }
}
