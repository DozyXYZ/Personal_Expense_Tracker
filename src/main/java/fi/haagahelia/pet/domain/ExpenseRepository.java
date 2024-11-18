package fi.haagahelia.pet.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDate;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
        List<Expense> findByDate(LocalDate date);

        List<Expense> findByAmount(double amount);

        List<Expense> findByDescription(String description);

        List<Expense> findByUser(AppUser user);

        @Query("SELECT e FROM Expense e JOIN e.typeExpense te WHERE e.user = :user AND (:type IS NULL OR :type = '' OR te.type = :type) AND (:year IS NULL OR FUNCTION('YEAR', e.date) = :year) AND (:month IS NULL OR FUNCTION('MONTH', e.date) = :month)")
        List<Expense> findByUserAndFilters(@Param("user") AppUser user, @Param("type") String type,
                        @Param("year") Integer year, @Param("month") Integer month);
}
