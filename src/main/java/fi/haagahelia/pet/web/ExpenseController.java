package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.ExpenseRepository;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository repository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({ "/", "/expenses" })
    public String expenseList(Model model) {
        model.addAttribute("expenses", repository.findAll());
        return "expenses";
    }
}
