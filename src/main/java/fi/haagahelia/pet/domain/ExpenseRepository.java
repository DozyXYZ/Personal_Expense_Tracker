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

    @Query("SELECT e FROM Expense e JOIN e.typeExpense te WHERE e.user = :user AND te.type = :type")
    List<Expense> findByUserAndType(@Param("user") AppUser user, @Param("type") String type);

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year")
    List<Expense> findByUserAndYear(@Param("user") AppUser user, @Param("year") int year);

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findByUserAndYearAndMonth(@Param("user") AppUser user, @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT e FROM Expense e JOIN e.typeExpense te WHERE e.user = :user AND te.type = :type AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findByUserAndTypeAndYearAndMonth(@Param("user") AppUser user, @Param("type") String type,
            @Param("year") int year, @Param("month") int month);
}
