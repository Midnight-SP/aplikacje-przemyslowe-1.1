package com.techcorp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    @DisplayName("Bazowe pensje są zgodne ze specyfikacją")
    void baseSalaries() {
        assertEquals(25_000, Position.PREZES.getBaseSalary());
        assertEquals(18_000, Position.WICEPREZES.getBaseSalary());
        assertEquals(12_000, Position.MANAGER.getBaseSalary());
        assertEquals(8_000, Position.PROGRAMISTA.getBaseSalary());
        assertEquals(3_000, Position.STAZYSTA.getBaseSalary());
    }

    @Test
    @DisplayName("Poziomy hierarchii rosną w dół struktury")
    void levelsIncreaseDownwards() {
        assertTrue(Position.PREZES.getLevel() < Position.WICEPREZES.getLevel());
        assertTrue(Position.WICEPREZES.getLevel() < Position.MANAGER.getLevel());
        assertTrue(Position.MANAGER.getLevel() < Position.PROGRAMISTA.getLevel());
        assertTrue(Position.PROGRAMISTA.getLevel() < Position.STAZYSTA.getLevel());
    }

    @Test
    @DisplayName("Enum zachowuje kolejność deklaracji")
    void declarationOrder() {
        Position[] values = Position.values();
        assertArrayEquals(new Position[]{
                Position.PREZES,
                Position.WICEPREZES,
                Position.MANAGER,
                Position.PROGRAMISTA,
                Position.STAZYSTA
        }, values);
    }
}
