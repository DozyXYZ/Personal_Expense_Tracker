package fi.haagahelia.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.ExpenseRepository;

@SpringBootApplication
public class PetApplication {

	private static final Logger log = LoggerFactory.getLogger(PetApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}

	@Bean
	public CommandLineRunner expenseDemo(ExpenseRepository repository) {
		return (args) -> {
			log.info("save a couple of expenses");
			repository.save(new Expense(LocalDate.parse("2021-01-01"), "Food", "Dog food", 20.0));
			repository.save(new Expense(LocalDate.parse("2021-01-02"), "Toy", "Dog toy", 10.0));
			repository.save(new Expense(LocalDate.parse("2021-01-03"), "Food", "Cat food", 15.0));
			repository.save(new Expense(LocalDate.parse("2021-01-04"), "Toy", "Cat toy", 5.0));

			log.info("fetch all expenses");
			for (Expense expense : repository.findAll()) {
				log.info(expense.toString());
			}

			log.info("fetch an expense by type");
			for (Expense expense : repository.findByType("Food")) {
				log.info(expense.toString());
			}

		};
	}

}
