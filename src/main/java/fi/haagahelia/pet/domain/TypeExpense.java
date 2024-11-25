package fi.haagahelia.pet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;

import java.util.List;

/**
 * This file defines the TypeExpense entity class, which represents a type of
 * expense in the application.
 * The TypeExpense class includes attributes: id and type.
 * It has a one-to-many relationship with the Expense entity.
 */

@Entity
public class TypeExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeExpense")
    private List<Expense> expenses;

    public TypeExpense() {
    }

    public TypeExpense(String type) {
        super();
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

}
