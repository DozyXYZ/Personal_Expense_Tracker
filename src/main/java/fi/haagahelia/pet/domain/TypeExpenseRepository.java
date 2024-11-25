package fi.haagahelia.pet.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * This file defines the TypeExpenseRepository interface, which extends the
 * CrudRepository interface provided by Spring Data JPA. It is used for
 * performing CRUD operations on TypeExpense entities.
 * The interface includes a method for finding a TypeExpense entity by type.
 */

public interface TypeExpenseRepository extends CrudRepository<TypeExpense, Long> {
    TypeExpense findByType(String type);

}
