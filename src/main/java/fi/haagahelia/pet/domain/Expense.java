package fi.haagahelia.pet.domain;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * This file defines the Expense entity class, which represents an expense
 * record in the application.
 * The Expense class includes attributes such as date, description, amount, type
 * of expense, and the user who made the expense.
 * It has a many-to-one relationship with the TypeExpense and AppUser entities.
 */

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private String description;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "type")
    private TypeExpense typeExpense;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Expense() {
    }

    public Expense(LocalDate date, TypeExpense typeExpense, String description, double amount, AppUser user) {
        this.date = date;
        this.typeExpense = typeExpense;
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TypeExpense getTypeExpense() {
        return typeExpense;
    }

    public void setTypeExpense(TypeExpense typeExpense) {
        this.typeExpense = typeExpense;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    // Get the type for the CSV export of Expense table
    public String getTypeExpenseType() {
        return typeExpense != null ? typeExpense.getType() : null;
    }

}
