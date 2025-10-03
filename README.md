# TechCorp - System Zarządzania Pracownikami

Firma TechCorp potrzebuje prostego systemu do zarządzania informacjami o swoich pracownikach. System ma umożliwić podstawowe operacje analityczne na danych pracowników, takie jak wyszukiwanie, sortowanie i generowanie statystyk.

## Wymagania

### Model danych

Stwórz odpowiedni model obiektowy reprezentujący strukturę firmy. Każdy pracownik posiada następujące informacje:

- **Imię i nazwisko**
- **Adres email** (unikalny identyfikator)
- **Nazwa firmy**, w której pracuje
- **Stanowisko służbowe**
- **Wysokość wynagrodzenia**

W firmie występują następujące stanowiska w hierarchii od najwyższego do najniższego:

1. **Prezes** - bazowa stawka wynagrodzenia: 25,000
2. **Wiceprezes** - bazowa stawka wynagrodzenia: 18,000
3. **Manager** - bazowa stawka wynagrodzenia: 12,000
4. **Programista** - bazowa stawka wynagrodzenia: 8,000
5. **Stażysta** - bazowa stawka wynagrodzenia: 3,000

Stanowiska służbowe należy zaimplementować jako `enum` z polami przechowującymi bazową pensję i poziom w hierarchii. Klasy modelu muszą nadpisywać metody `equals()` i `hashCode()` (oparte na adresie email) oraz `toString()`. Stosuj enkapsulację z prywatnymi polami i publicznymi metodami dostępowymi.

### Funkcjonalność systemu

System musi umożliwiać wykonanie następujących operacji:

#### Zarządzanie pracownikami

- **Dodawanie nowego pracownika** do systemu z walidacją unikalności adresu email przed dodaniem.
- **Wyświetlanie listy wszystkich pracowników** w systemie.

#### Operacje analityczne

- **Wyszukiwanie pracowników zatrudnionych w konkretnej firmie** - zaimplementuj jako operację filtrowania kolekcji z wykorzystaniem `Stream API`.
- **Prezentacja pracowników w kolejności alfabetycznej według nazwiska** - użyj `Comparator` do zdefiniowania porządku sortowania.
- **Grupowanie pracowników według zajmowanego stanowiska** - operacja powinna zwrócić strukturę `Map`, gdzie kluczem jest stanowisko, a wartością lista pracowników na tym stanowisku.
- **Zliczanie liczby pracowników na każdym stanowisku** - wynik w formie `Map` mapującej stanowisko na liczbę pracowników.

#### Statystyki finansowe

- **Obliczanie średniego wynagrodzenia** w całej organizacji - operacja agregująca dane finansowe wszystkich pracowników.
- **Identyfikacja pracownika z najwyższym wynagrodzeniem** - operacja znajdowania maksimum z wykorzystaniem `Optional` do obsługi potencjalnie pustej kolekcji.

---

### Kryteria recenzji

1. **Model danych i podstawy programowania obiektowego** (20%)
2. **Zarządzanie danymi i walidacja** (15%)
3. **Operacje analityczne z wykorzystaniem Stream API** (25%)
4. **Operacje statystyczne i obsługa Optional** (15%)
5. **Jakość techniczna i czytelność kodu** (25%)

---

Jako odpowiedź prześlij link do repozytorium.