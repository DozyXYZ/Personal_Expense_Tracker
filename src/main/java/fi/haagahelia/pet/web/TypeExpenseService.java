package fi.haagahelia.pet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

/**
 * This file defines the TypeExpenseService class, which is a Spring service
 * class that interacts with the TypeExpenseRepository class. It includes
 * methods for retrieving all type expenses and saving a type expense.
 * 
 * The class uses the TypeExpenseRepository class.
 */
@Service
public class TypeExpenseService {

    @Autowired
    private TypeExpenseRepository typeRepository;

    // Retrieve all type expenses
    public List<TypeExpense> getAllTypeExpenses() {
        return (List<TypeExpense>) typeRepository.findAll();
    }

    // Save a type expense
    public void saveTypeExpense(TypeExpense typeExpense) {
        typeRepository.save(typeExpense);
    }
}
