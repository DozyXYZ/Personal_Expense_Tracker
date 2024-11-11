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
import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

@SpringBootApplication
public class PetApplication {

	private static final Logger log = LoggerFactory.getLogger(PetApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}

	@Bean
	public CommandLineRunner expenseDemo(ExpenseRepository repository, TypeExpenseRepository typeRepository) {
		return (args) -> {
			log.info("save a couple of expenses types");
			TypeExpense housing = new TypeExpense("Housing");
			TypeExpense utilities = new TypeExpense("Utilities");
			TypeExpense groceries = new TypeExpense("Groceries");
			TypeExpense transportation = new TypeExpense("Transportation");
			TypeExpense healthcare = new TypeExpense("Healthcare");
			TypeExpense insurance = new TypeExpense("Insurance");
			TypeExpense debtPayments = new TypeExpense("Debt Payments");
			TypeExpense entertainment = new TypeExpense("Entertainment");
			TypeExpense education = new TypeExpense("Education");
			TypeExpense clothing = new TypeExpense("Clothing");
			TypeExpense giftsAndDonations = new TypeExpense("Gifts & Donations");
			TypeExpense childcare = new TypeExpense("Childcare");

			typeRepository.save(housing);
			typeRepository.save(utilities);
			typeRepository.save(groceries);
			typeRepository.save(transportation);
			typeRepository.save(healthcare);
			typeRepository.save(insurance);
			typeRepository.save(debtPayments);
			typeRepository.save(entertainment);
			typeRepository.save(education);
			typeRepository.save(clothing);
			typeRepository.save(giftsAndDonations);
			typeRepository.save(childcare);

			log.info("save a couple of expenses");
			// January expenses
			repository.save(new Expense(LocalDate.of(2024, 1, 1), housing, "Rent", 1000));
			repository.save(new Expense(LocalDate.of(2024, 1, 5), utilities, "Electricity Bill", 150));
			repository.save(new Expense(LocalDate.of(2024, 1, 10), groceries, "Grocery Store", 200));
			repository.save(new Expense(LocalDate.of(2024, 1, 15), transportation, "Gas", 50));
			repository.save(new Expense(LocalDate.of(2024, 1, 20), healthcare, "Prescription", 30));
			repository.save(new Expense(LocalDate.of(2024, 1, 25), insurance, "Car Insurance", 100));
			repository.save(new Expense(LocalDate.of(2024, 1, 28), debtPayments, "Credit Card Payment", 300));
			repository.save(new Expense(LocalDate.of(2024, 1, 5), entertainment, "Movie Tickets", 25));
			repository.save(new Expense(LocalDate.of(2024, 1, 12), education, "Online Course", 50));
			repository.save(new Expense(LocalDate.of(2024, 1, 18), clothing, "New Shoes", 80));
			repository.save(new Expense(LocalDate.of(2024, 1, 22), giftsAndDonations, "Charity Donation", 40));
			repository.save(new Expense(LocalDate.of(2024, 1, 29), childcare, "Daycare", 300));

			// February expenses
			repository.save(new Expense(LocalDate.of(2024, 2, 1), housing, "Rent", 1000));
			repository.save(new Expense(LocalDate.of(2024, 2, 5), utilities, "Electricity Bill", 140));
			repository.save(new Expense(LocalDate.of(2024, 2, 10), groceries, "Grocery Store", 210));
			repository.save(new Expense(LocalDate.of(2024, 2, 15), transportation, "Gas", 55));
			repository.save(new Expense(LocalDate.of(2024, 2, 18), healthcare, "Doctor Visit", 100));
			repository.save(new Expense(LocalDate.of(2024, 2, 20), insurance, "Health Insurance", 120));
			repository.save(new Expense(LocalDate.of(2024, 2, 25), debtPayments, "Student Loan Payment", 400));
			repository.save(new Expense(LocalDate.of(2024, 2, 6), entertainment, "Concert Tickets", 60));
			repository.save(new Expense(LocalDate.of(2024, 2, 10), education, "Workshop Fee", 75));
			repository.save(new Expense(LocalDate.of(2024, 2, 15), clothing, "Winter Jacket", 90));
			repository.save(new Expense(LocalDate.of(2024, 2, 20), giftsAndDonations, "Birthday Gift", 50));
			repository.save(new Expense(LocalDate.of(2024, 2, 28), childcare, "Daycare", 300));

			log.info("fetch all expenses");
			for (Expense expense : repository.findAll()) {
				log.info(expense.toString());
			}

		};
	}
}
