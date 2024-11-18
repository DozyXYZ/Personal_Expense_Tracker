package fi.haagahelia.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;
import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.ExpenseRepository;
import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;
import fi.haagahelia.pet.web.FilesExporter;

@SpringBootApplication
public class PetApplication {

	private static final Logger log = LoggerFactory.getLogger(PetApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}

	@Bean
	public FilesExporter fileExport() {
		return new FilesExporter();
	}

	@Bean
	public CommandLineRunner expenseDemo(ExpenseRepository repository, TypeExpenseRepository typeRepository,
			AppUserRepository userRepository) {
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

			// Create users: admin/1234 user1/123 user2/111
			AppUser user1 = new AppUser("user1", "$2a$12$/RIRTZXpf7iOQQE9YQriuO3bqoQ6VNmetairJjw7lfo6EAVHvg0KS", "USER",
					"user1@email.fi", "A123456");
			AppUser user2 = new AppUser("user2", "$2a$12$KRZIYwVAlwj.WTvuH1j6JePubrwlWy8bnI7aVVPiWpVLz4Ayu8Sp6", "USER",
					"user2@email.fi", "B123456");
			AppUser user3 = new AppUser("admin", "$2a$12$B.4rPzaUrVPA2oYrIqzjPOnl0T5U2kvJK74wzj51Kd7zgzyMlPDp6",
					"ADMIN", "admin@email.fi", "C123456");

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);

			log.info("save a couple of expenses");
			// January expenses
			repository.save(new Expense(LocalDate.of(2023, 1, 1), housing, "Rent", 1000, user1));
			repository.save(new Expense(LocalDate.of(2023, 1, 5), utilities, "Electricity Bill", 150, user1));
			repository.save(new Expense(LocalDate.of(2024, 1, 10), groceries, "Grocery Store", 200, user1));
			repository.save(new Expense(LocalDate.of(2024, 1, 15), transportation, "Gas", 50, user1));
			repository.save(new Expense(LocalDate.of(2024, 1, 20), healthcare, "Prescription", 30, user1));
			repository.save(new Expense(LocalDate.of(2024, 1, 25), insurance, "Car Insurance", 100, user1));
			repository.save(new Expense(LocalDate.of(2024, 1, 28), debtPayments, "Credit Card Payment", 300, user1));
			repository.save(new Expense(LocalDate.of(2024, 1, 5), entertainment, "Movie Tickets", 25, user2));
			repository.save(new Expense(LocalDate.of(2024, 1, 12), education, "Online Course", 50, user2));
			repository.save(new Expense(LocalDate.of(2024, 1, 18), clothing, "New Shoes", 80, user2));
			repository.save(new Expense(LocalDate.of(2024, 1, 22), giftsAndDonations, "Charity Donation", 40, user2));
			repository.save(new Expense(LocalDate.of(2024, 1, 29), childcare, "Daycare", 300, user2));

			// February expenses
			repository.save(new Expense(LocalDate.of(2024, 2, 1), housing, "Rent", 1000, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 5), utilities, "Electricity Bill", 140, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 10), groceries, "Grocery Store", 210, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 15), transportation, "Gas", 55, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 18), healthcare, "Doctor Visit", 100, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 20), insurance, "Health Insurance", 120, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 25), debtPayments, "Student Loan Payment", 400, user1));
			repository.save(new Expense(LocalDate.of(2024, 2, 6), entertainment, "Concert Tickets", 60, user2));
			repository.save(new Expense(LocalDate.of(2024, 2, 10), education, "Workshop Fee", 75, user2));
			repository.save(new Expense(LocalDate.of(2024, 2, 15), clothing, "Winter Jacket", 90, user2));
			repository.save(new Expense(LocalDate.of(2024, 2, 20), giftsAndDonations, "Birthday Gift", 50, user2));

			log.info("fetch all expenses");
			for (Expense expense : repository.findAll()) {
				log.info(expense.toString());
			}

			log.info(user1.getRole());
			log.info(user2.getRole());
			log.info(user3.getRole());

		};
	}
}
