package fi.haagahelia.pet;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;
import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.ExpenseRepository;
import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private TypeExpenseRepository typeRepository;

    @Test
    public void findByDateShouldReturnExpenses() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        List<Expense> expenses = repository.findByDate(date);
        assertThat(expenses).isNotEmpty();
    }

    @Test
    public void findByDescriptionShouldReturnExpenses() {
        String description = "Grocery Store";
        List<Expense> expenses = repository.findByDescription(description);
        assertThat(expenses).isNotEmpty();
    }

    @Test
    public void findByAmountShouldReturnExpenses() {
        double amount = 100.0;
        List<Expense> expenses = repository.findByAmount(amount);
        assertThat(expenses).isNotEmpty();
    }

    @Test
    public void testCreateExpense() {
        TypeExpense housing = typeRepository.findByType("Housing");
        AppUser user1 = userRepository.findByUsername("user1");
        Expense expense = new Expense(LocalDate.of(2023, 11, 24), housing, "xyz", 1000, user1);
        repository.save(expense);
        assertThat(expense.getId()).isNotNull();
    }

    @Test
    public void testDeleteSpecificExpense() {
        List<Expense> expenses = repository.findByDescription("Grocery Store");
        Expense expenseToDelete = expenses.stream()
                .filter(expense -> expense.getAmount() == 200.0)
                .findFirst()
                .orElse(null);
        assertThat(expenseToDelete).isNotNull();
        repository.delete(expenseToDelete);
        List<Expense> deletedExpenses = repository.findByDescription("Grocery Store");
        assertThat(deletedExpenses).doesNotContain(expenseToDelete);
    }

    @Test
    public void testUpdateSpecificExpense() {
        Expense expense = repository.findByDescription("Rent").get(0);
        expense.setAmount(1500.0);
        repository.save(expense);

        Expense updatedExpense = repository.findByDescription("Rent").get(0);
        assertThat(updatedExpense.getAmount()).isEqualTo(1500.0);
        assertThat(updatedExpense.getId()).isEqualTo(expense.getId());
    }
}
