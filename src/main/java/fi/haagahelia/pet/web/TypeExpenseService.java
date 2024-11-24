package fi.haagahelia.pet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

@Service
public class TypeExpenseService {

    @Autowired
    private TypeExpenseRepository typeRepository;

    public List<TypeExpense> getAllTypeExpenses() {
        return (List<TypeExpense>) typeRepository.findAll();
    }

    public void saveTypeExpense(TypeExpense typeExpense) {
        typeRepository.save(typeExpense);
    }
}
