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

/**
 * This file defines the ExpenseService class, which is used to interact with
 * the ExpenseRepository and AppUserRepository classes. It includes methods for
 * retrieving, saving, and deleting expenses for a specific user, as well as
 * retrieving expenses by user and additional filters (type, year, month).
 * 
 * The class uses the ExpenseRepository and AppUserRepository classes.
 */

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private AppUserRepository userRepository;

    // Retrieve all expenses for a specific user
    public List<Expense> getExpensesForUser(String username) {
        return repository.findByUser(userRepository.findByUsername(username));
    }

    // Save an expense for a specific user
    public void saveExpenseForUser(Expense expense, String username) {
        AppUser user = userRepository.findByUsername(username);
        expense.setUser(user);
        repository.save(expense);
    }

    // Delete an expense for a specific user
    public void deleteExpenseForUser(Long expenseId, String username) {
        AppUser user = userRepository.findByUsername(username);
        Expense expense = repository.findById(expenseId).orElse(null);
        if (expense != null && expense.getUser().equals(user)) {
            repository.delete(expense);
        }
    }

    // Retrieve expenses by user and additional filters (type, year, month)
    public List<Expense> getExpensesByUserAndFilters(String username, String type, Integer year, Integer month) {
        AppUser user = userRepository.findByUsername(username);
        return repository.findByUserAndFilters(user, type, year, month);
    }

    // Retrieve and Map monthly expenses for a specific user to draw a chart
    public Map<Integer, Double> getMonthlyExpensesForYear(String username, Integer year) {
        AppUser user = userRepository.findByUsername(username);
        List<Expense> expenses = repository.findByUserAndFilters(user, null, year, null);
        return expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getDate().getMonthValue(),
                        Collectors.summingDouble(Expense::getAmount)));
    }

    // Retrieve and Map monthly expense details for a specific user to draw a chart
    public Map<String, Double> getMonthlyExpenseDetails(String username, Integer year, Integer month) {
        AppUser user = userRepository.findByUsername(username);
        List<Expense> expenses = repository.findByUserAndFilters(user, null, year, month);
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getTypeExpenseType,
                        Collectors.summingDouble(Expense::getAmount)));
    }
}
