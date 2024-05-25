package example;

import example.Customer;
import example.Movie;
import example.Rental;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

	@Test
	public void statementIncludesCustomerName() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("John Doe"));
	}

	@Test
	public void statementIncludesRentalDetailsForEachMovie() {
		List<Rental> rentals = List.of(
			new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1),
			new Rental(new Movie("Movie2", Movie.MovieType.NEW_RELEASE), 2),
			new Rental(new Movie("Movie3", Movie.MovieType.CHILDRENS), 3)
		);
		Customer customer = new Customer("Jane Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Movie1") && statement.contains("Movie2") && statement.contains("Movie3"));
	}

	@Test
	public void statementCalculatesTotalAmountCorrectly() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 3));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is 3.5"));
	}

	@Test
	public void statementAddsFrequentRenterPointsCorrectly() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("You earned 1 frequent renter points"));
	}

	@Test
	public void statementAddsBonusForTwoDayNewReleaseRental() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.NEW_RELEASE), 2));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("You earned 2 frequent renter points"));
	}

	@Test
	public void statementIncludesTotalAmountOwed() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is 2.0"));
	}

	@Test
	public void statementIncludesTotalFrequentRenterPointsEarned() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("You earned 1 frequent renter points"));
	}

	@Test
	public void statementHandlesEmptyRentalListGracefully() {
		List<Rental> rentals = List.of();
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is 0.0") && statement.contains("You earned 0 frequent renter points"));
	}

	@Test
	public void scenarioWithZeroDaysRentedForMovie() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 0));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertFalse(statement.contains("Amount owed is 0.0") && statement.contains("You earned 1 frequent renter points"));
	}

	@Test
	public void scenarioWithNegativeDaysRentedForMovie() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), -1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertFalse(statement.contains("Amount owed is 0.0") && statement.contains("You earned 1 frequent renter points"));
	}

	@Test
	public void statementIncludesMovieTitleForEachRental() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Movie1"));
	}

	@Test
	public void scenarioWithCustomerNameContainingSpecialCharacters() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John#Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("John#Doe"));
	}

	@Test
	public void scenarioWithMovieTitleContainingSpecialCharacters() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie#1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Movie#1"));
	}

	@Test
	public void scenarioWithLargeNumberOfRentals() {
		List<Rental> rentals = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			rentals.add(new Rental(new Movie("Movie" + i, Movie.MovieType.REGULAR), 1));
		}
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertFalse(statement.contains("Amount owed is 1000.0") && statement.contains("You earned 1000 frequent renter points"));
	}

	@Test
	public void scenarioWithCustomerHavingLongName() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe John Doe John Doe John Doe John Doe John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("John Doe John Doe John Doe John Doe John Doe John Doe"));
	}

	@Test
	public void scenarioWithMovieTitleExceedingTypicalLengths() {
		List<Rental> rentals = List.of(new Rental(new Movie("This is a very long movie title that exceeds typical lengths", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("This is a very long movie title that exceeds typical lengths"));
	}

	@Test
	public void statementFormattingIsCorrectWithDifferentCombinationsOfRentals() {
		List<Rental> rentals = List.of(
			new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1),
			new Rental(new Movie("Movie2", Movie.MovieType.NEW_RELEASE), 2),
			new Rental(new Movie("Movie3", Movie.MovieType.CHILDRENS), 3)
		);
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is") && statement.contains("You earned") && statement.contains("frequent renter points"));
	}

	@Test
	public void scenarioWithAllRentalsHavingSameDaysRented() {
		List<Rental> rentals = List.of(
			new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 2),
			new Rental(new Movie("Movie2", Movie.MovieType.NEW_RELEASE), 2),
			new Rental(new Movie("Movie3", Movie.MovieType.CHILDRENS), 2)
		);
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is") && statement.contains("You earned") && statement.contains("frequent renter points"));
	}

	@Test
	public void scenarioWithDifferentDaysRentedForRentals() {
		List<Rental> rentals = List.of(
			new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1),
			new Rental(new Movie("Movie2", Movie.MovieType.NEW_RELEASE), 3),
			new Rental(new Movie("Movie3", Movie.MovieType.CHILDRENS), 5)
		);
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is") && statement.contains("You earned") && statement.contains("frequent renter points"));
	}

	@Test
	public void statementHandlesRoundingOfAmountsCorrectly() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 3));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is 3.5"));
	}

	@Test
	public void scenarioWithNegativeRentalAmount() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), -1));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertFalse(statement.contains("Amount owed is 0.0") && statement.contains("You earned 0 frequent renter points"));
	}

	@Test
	public void scenarioWithCustomerNameConsistingOfSpaces() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("    ", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is") && statement.contains("You earned") && statement.contains("frequent renter points"));
	}

	@Test
	public void statementFormattingIsCorrectForEdgeCases() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("!@#$%^&*()_+<>?:{}|", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("!@#$%^&*()_+<>?:{}|"));
	}

	@Test
	public void scenarioWithLargeNumberOfMovies() {
		List<Rental> rentals = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			rentals.add(new Rental(new Movie("Movie" + i, Movie.MovieType.REGULAR), 1));
		}
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertFalse(statement.contains("Amount owed is 1000.0") && statement.contains("You earned 1000 frequent renter points"));
	}

	@Test
	public void scenarioWithLargeNumberOfCustomers() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), 1));
		List<Customer> customers = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			customers.add(new Customer("Customer" + i, rentals));
		}
		String statement = customers.get(500).statement();
		assertTrue(statement.contains("Amount owed is") && statement.contains("You earned") && statement.contains("frequent renter points"));
	}

	@Test
	public void statementFormattingIsCorrectForDifferentLocales() {
		List<Rental> rentals = List.of(new Rental(new Movie("Фільм1", Movie.MovieType.REGULAR), 1));
		Customer customer = new Customer("Іван Іванов", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Фільм1") && statement.contains("Іван Іванов"));
	}

	@Test
	public void scenarioWithExtremeValuesForRentalDaysOrAmounts() {
		List<Rental> rentals = List.of(new Rental(new Movie("Movie1", Movie.MovieType.REGULAR), Integer.MAX_VALUE));
		Customer customer = new Customer("John Doe", rentals);
		String statement = customer.statement();
		assertTrue(statement.contains("Amount owed is") && statement.contains("You earned") && statement.contains("frequent renter points"));
	}
}
