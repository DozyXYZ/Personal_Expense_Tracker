package fi.haagahelia.pet.domain;

import org.springframework.data.repository.CrudRepository;

public interface TypeExpenseRepository extends CrudRepository<TypeExpense, Long> {
    TypeExpense findByType(String type);

}
