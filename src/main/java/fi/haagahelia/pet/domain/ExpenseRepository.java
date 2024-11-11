package fi.haagahelia.pet.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.time.LocalDate;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findByDate(LocalDate date);

    List<Expense> findByType(String type);

    List<Expense> findByAmount(double amount);

    List<Expense> findByDescription(String description);
}
