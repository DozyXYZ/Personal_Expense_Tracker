package fi.haagahelia.pet.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;
import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.ExpenseRepository;

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

    public List<Expense> getExpensesByUserAndFilters(String username, String type, Integer year, Integer month) {
        AppUser user = userRepository.findByUsername(username);
        return repository.findByUserAndFilters(user, type, year, month);
    }

    public Map<Integer, Double> getMonthlyExpensesForYear(String username, Integer year) {
        AppUser user = userRepository.findByUsername(username);
        List<Expense> expenses = repository.findByUserAndFilters(user, null, year, null);
        return expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getDate().getMonthValue(),
                        Collectors.summingDouble(Expense::getAmount)));
    }

    public Map<String, Double> getMonthlyExpenseDetails(String username, Integer year, Integer month) {
        AppUser user = userRepository.findByUsername(username);
        List<Expense> expenses = repository.findByUserAndFilters(user, null, year, month);
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getTypeExpenseType,
                        Collectors.summingDouble(Expense::getAmount)));
    }
}
