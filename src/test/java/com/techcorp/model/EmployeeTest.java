package com.techcorp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    @DisplayName("Powinien utworzyć pracownika z poprawnymi danymi")
    void shouldCreateEmployee() {
        Employee e = new Employee("Jan Kowalski", "jan.kowalski@corp.com", "TechCorp", Position.PROGRAMISTA, 9000);
        assertEquals("Jan Kowalski", e.getFullName());
        assertEquals("jan.kowalski@corp.com", e.getEmail());
        assertEquals("TechCorp", e.getCompanyName());
        assertEquals(Position.PROGRAMISTA, e.getPosition());
        assertEquals(9000, e.getSalary());
    }

    @Test
    @DisplayName("Powinien rzucić IllegalArgumentException przy ujemnym wynagrodzeniu")
    void shouldRejectNegativeSalary() {
        assertThrows(IllegalArgumentException.class, () -> new Employee("Jan", "a@a", "TechCorp", Position.STAZYSTA, -1));
    }

    @Test
    @DisplayName("Setter wynagrodzenia waliduje wartość")
    void salarySetterValidation() {
        Employee e = new Employee("Jan", "b@b", "TechCorp", Position.STAZYSTA, 1000);
        assertThrows(IllegalArgumentException.class, () -> e.setSalary(-5));
    }

    @Test
    @DisplayName("Setter wynagrodzenia aktualizuje wartość")
    void salarySetterUpdates() {
        Employee e = new Employee("Jan", "set@corp.com", "TechCorp", Position.STAZYSTA, 1000);
        e.setSalary(1500);
        assertEquals(1500, e.getSalary());
    }

    @Test
    @DisplayName("setFullName odrzuca null")
    void setFullNameRejectsNull() {
        Employee e = new Employee("Jan", "fn@corp.com", "TechCorp", Position.STAZYSTA, 1000);
        assertThrows(NullPointerException.class, () -> e.setFullName(null));
    }

    @Test
    @DisplayName("setCompanyName odrzuca null")
    void setCompanyNameRejectsNull() {
        Employee e = new Employee("Jan", "cn@corp.com", "TechCorp", Position.STAZYSTA, 1000);
        assertThrows(NullPointerException.class, () -> e.setCompanyName(null));
    }

    @Test
    @DisplayName("setPosition odrzuca null")
    void setPositionRejectsNull() {
        Employee e = new Employee("Jan", "pos@corp.com", "TechCorp", Position.STAZYSTA, 1000);
        assertThrows(NullPointerException.class, () -> e.setPosition(null));
    }

    @Test
    @DisplayName("Metoda getLastName zwraca ostatni człon nazwiska")
    void lastNameExtraction() {
        Employee e = new Employee("Anna Maria Nowak", "c@c", "TechCorp", Position.MANAGER, 12000);
        assertEquals("Nowak", e.getLastName());
    }

    @Test
    @DisplayName("getLastName dla jednego członu zwraca ten sam tekst")
    void lastNameSingleWord() {
        Employee e = new Employee("Madonna", "m@c", "TechCorp", Position.MANAGER, 12000);
        assertEquals("Madonna", e.getLastName());
    }

    @Test
    @DisplayName("getLastName ignoruje wiele spacji")
    void lastNameIgnoresMultipleSpaces() {
        Employee e = new Employee("Jan   Adam    Nowak", "spaces@c", "TechCorp", Position.MANAGER, 12000);
        assertEquals("Nowak", e.getLastName());
    }

    @Test
    @DisplayName("Równość po emailu ignorując wielkość liter")
    void equalityByEmailCaseInsensitive() {
        Employee e1 = new Employee("Jan", "X@EX.com", "TechCorp", Position.STAZYSTA, 1000);
        Employee e2 = new Employee("Jan", "x@ex.com", "TechCorp", Position.STAZYSTA, 2000);
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    @DisplayName("Pracownicy z różnymi emailami nie są sobie równi")
    void notEqualDifferentEmail() {
        Employee e1 = new Employee("Jan", "a@ex.com", "TechCorp", Position.STAZYSTA, 1000);
        Employee e2 = new Employee("Jan", "b@ex.com", "TechCorp", Position.STAZYSTA, 1000);
        assertNotEquals(e1, e2);
    }

    @Nested
    class ToStringContract {
        @Test
        void toStringContainsKeyFields() {
            Employee e = new Employee("Jan Kowalski", "jan@corp.com", "TechCorp", Position.PROGRAMISTA, 8000);
            String s = e.toString();
            assertTrue(s.contains("Jan Kowalski"));
            assertTrue(s.contains("jan@corp.com"));
            assertTrue(s.contains("PROGRAMISTA"));
            assertTrue(s.contains("salary=8000"));
        }
    }
}
