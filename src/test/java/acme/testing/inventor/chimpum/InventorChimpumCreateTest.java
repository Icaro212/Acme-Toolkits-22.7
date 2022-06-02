package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final String title, final String description, final String startDate, final String endDate,
			final String budget, final String info) {

		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List my items");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);

		super.clickOnButton("Create chimpum");

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Inventor", "List the chimpums of my items");
		super.checkListingExists();
		super.sortListing(1, "desc");

		super.checkColumnHasValue(0, 2, title);
		super.checkColumnHasValue(0, 3, startDate);

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("info", info);

		super.clickOnSubmit("Delete");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest(final String title, final String description, final String startDate, final String endDate,
			final String budget, final String info) {

		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List my items");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);

		super.clickOnButton("Create chimpum");

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void hackingTest() {
		// As the framework doesn´t support this hacking feature we will have to perform
		// this manually
		// Create a chimpum for an item as a principal without the "Inventor" role;
		// Check that a panic happens
	}

}
