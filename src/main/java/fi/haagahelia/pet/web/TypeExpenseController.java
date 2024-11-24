package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.pet.domain.TypeExpense;

@Controller
public class TypeExpenseController {

    @Autowired
    private TypeExpenseService typeExpenseService;

    @GetMapping("/typeExpenses")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getTypeExpenses(Model model) {
        model.addAttribute("typeExpenses", typeExpenseService.getAllTypeExpenses());
        return "typeexpenses";
    }

    @GetMapping("/typeExpenses/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showTypeExpenseForm(Model model) {
        model.addAttribute("typeExpense", new TypeExpense());
        return "typeexpensesform";
    }

    @GetMapping("/typeExpenses/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showTypeExpenseEditForm(@PathVariable("id") Long typeExpenseId, Model model) {
        TypeExpense type = typeExpenseService.getAllTypeExpenses().stream().filter(e -> e.getId().equals(typeExpenseId))
                .findFirst().orElse(null);
        model.addAttribute("typeExpense", type);
        return "typeexpensesform";
    }

    @PostMapping("/typeExpenses/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveTypeExpense(TypeExpense typeExpense) {
        typeExpenseService.saveTypeExpense(typeExpense);
        return "redirect:/typeExpenses";
    }
}
