package fi.haagahelia.pet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;
import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.ExpenseRepository;
// import fi.haagahelia.pet.domain.TypeExpense;

// This class provides the business logic related to expenses
// Users can perform CRUD operations on their own expenses after login
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private AppUserRepository userRepository;

    public List<Expense> getExpensesForUser(String username) {
        return repository.findByUser(userRepository.findByUsername(username));
    }

    public void saveExpenseForUser(Expense expense, String username) {
        AppUser user = userRepository.findByUsername(username);
        expense.setUser(user);
        repository.save(expense);
    }

    public void deleteExpenseForUser(Long expenseId, String username) {
        AppUser user = userRepository.findByUsername(username);
        Expense expense = repository.findById(expenseId).orElse(null);
        if (expense != null && expense.getUser().equals(user)) {
            repository.delete(expense);
        }
    }

    public List<Expense> getExpensesForUserAndType(String username, String type) {
        AppUser user = userRepository.findByUsername(username);
        return repository.findByUserAndType(user, type);
    }

    public List<Expense> getExpensesForUserAndYear(String username, int year) {
        AppUser user = userRepository.findByUsername(username);
        return repository.findByUserAndYear(user, year);
    }

    public List<Expense> getExpensesForUserAndYearAndMonth(String username, int year, int month) {
        AppUser user = userRepository.findByUsername(username);
        return repository.findByUserAndYearAndMonth(user, year, month);
    }

    public List<Expense> getExpensesForUserAndTypeAndYearAndMonth(String username, String type, int year,
            int month) {
        AppUser user = userRepository.findByUsername(username);
        return repository.findByUserAndTypeAndYearAndMonth(user, type, year, month);
    }
}
