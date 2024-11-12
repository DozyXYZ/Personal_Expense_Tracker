package fi.haagahelia.pet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
