package fi.haagahelia.pet.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TypeExpenseRepository extends CrudRepository<TypeExpense, Long> {
    List<TypeExpense> findByType(String type);

}
