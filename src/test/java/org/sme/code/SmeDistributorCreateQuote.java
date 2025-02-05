package org.sme.code;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.JavascriptExecutor;
import org.sme.pojo.CreateQuotePojo;
import org.sme.utilities.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SmeDistributorCreateQuote extends BaseClass {

	CreateQuotePojo createQuoteElements;

	@Given("User must be in the create qoute page")
	public void user_must_be_in_the_create_qoute_page() {
		createQuoteElements = new CreateQuotePojo();
		clickButton(createQuoteElements.getCreateQuoteButton());
		assertTrue(createQuoteElements.getCreateNewQuotePage().getText().contains("Create New Quote"));
	}

	@When("User enter company name")
	public void user_enter_company_name() throws IOException {
		fillTextBox(createQuoteElements.getCompanyNameTextBox(), createQuoteData().getProperty("companyName"));
	}

	@When("User enter trade licence number")
	public void user_enter_trade_licence_number() throws IOException {
		fillTextBox(createQuoteElements.getLicenceNumberTextBox(), createQuoteData().getProperty("tradeLicenceNumber"));
	}

	@When("User enter email id")
	public void user_enter_email_id() throws IOException {

		fillTextBox(createQuoteElements.getEmailTextBox(), createQuoteData().getProperty("email"));
	}

	@When("User enter mobile number")
	public void user_enter_mobile_number() throws IOException {

		selectDropDownData(createQuoteElements.getMobileNumberDropDown(), "50");
		fillTextBox(createQuoteElements.getMobileNumberTextBox(), createQuoteData().getProperty("mobileNumber"));
	}

	@When("User select industry categories")
	public void user_select_industry_categories() {
		selectDropDownData(createQuoteElements.getCategoriesDropDown(), "0");
	}

	@When("User click company details next button")
	public void user_click_company_details_next_button() throws InterruptedException {
		Thread.sleep(2000);
		clickButton(createQuoteElements.getCompanyDetailsNextButton());
	}

	@When("User select start date and end date in census")
	public void user_select_start_date_and_end_date_in_census() {
		clickButton(createQuoteElements.getCensusPolicyStartDate());
		createQuoteElements.getCensusPolicyStartDate().clear();
		String currentDate = currentDate();
		System.out.println(currentDate);
		createQuoteElements.getCensusPolicyStartDate().sendKeys(currentDate);
	}

	@When("User select number of categories")
	public void user_select_number_of_categories() {
		selectDropDownData(createQuoteElements.getNumCategoriesDropDown(), "2");
	}

	@When("User enter distributor commission")
	public void user_enter_distributor_commission() {
		fillTextBox(createQuoteElements.getDistributorCommisionTextBox(), "6");
	}

	@When("User enter sales agent")
	public void user_enter_sales_agent() {
		fillTextBox(createQuoteElements.getSalesAgentTextBox(), "aura");
	}

	@When("User click census next button")
	public void user_click_census_next_button() throws InterruptedException {
		Thread.sleep(2000);
		clickButton(createQuoteElements.getCensusNextButton());

	}

	@When("User should choose group")
	public void user_should_choose_group() throws InterruptedException {
		Thread.sleep(2000);
		selectDropDownData(createQuoteElements.getSelectGroupnameDropDown(), "0");
	}

	@When("User should select emirates category")
	public void user_should_select_emirates_category() {
		selectDropDownData(createQuoteElements.getSelectEmiratesDropDownCatA(), "1");
		selectDropDownData(createQuoteElements.getSelectEmiratesDropDownCatB(), "1");
	}

	@When("User should select TPA category")
	public void user_should_select_TPA_category() {

		selectDropDownData(createQuoteElements.getSelectTpaCatA(), "0");
		selectDropDownData(createQuoteElements.getSelectTpaCatB(), "0");
	}

	@When("User should select plan category")
	public void user_should_select_plan_category() {

		selectDropDownData(createQuoteElements.getSelectPlanCatA(), "0");
		selectDropDownData(createQuoteElements.getSelectPlanCatB(), "0");
	}

	@When("User should click and upload template")
	public void user_should_click_and_upload_template() {
		fillTextBox(createQuoteElements.getUploadTemplateId(),
				"C:\\Users\\impelox-pc-048\\Desktop\\censuses sheet\\census_ab_automation.xlsx");
		clickButton(createQuoteElements.getWarningPopupCancelButton());
	}

	@When("User click proceed")
	public void user_click_proceed() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", createQuoteElements.getProceedBtn());

		Thread.sleep(2000);
		jsClick(createQuoteElements.getProceedBtn());
	}

	@When("User click next button")
	public void user_click_next_button() throws InterruptedException {
		Thread.sleep(3000);
		jsClick(createQuoteElements.getNextButtonChoosePlan());
	}

	@Then("User must displayed with quote created popup message")
	public void user_must_displayed_with_quote_created_popup_message() {

		System.out.println(createQuoteElements.getQouteCreatedPopUpMessage().getText());

	}

	@Then("User should validate the total premium for the created qoute")
	public void user_should_validate_the_total_premium_for_the_created_qoute() throws SQLException, IOException {

		System.out.println("queryAIAWBasePremium");
		// base premium
		fetchDataFromDatabase(calculatorData().getProperty("dbUrlUAT"), calculatorData().getProperty("dbUsernameUAT"),
				calculatorData().getProperty("dbPasswordUAT"), calculatorData().getProperty("queryAIAWBasePremium"),
				calculatorData().getProperty("excelCalculatorFilePath"), 0);

		// benefits
		fetchDataFromDatabase(calculatorData().getProperty("dbUrlUAT"), calculatorData().getProperty("dbUsernameUAT"),
				calculatorData().getProperty("dbPasswordUAT"), "SELECT * FROM 7003_group_medical_aiaw_transactions.benefits_table where client_reference_number like 'TEST-ABC-0125-1-00020'",
				calculatorData().getProperty("excelCalculatorFilePath"), 1);

		// nationality loadings
		fetchDataFromDatabase(calculatorData().getProperty("dbUrlUAT"), calculatorData().getProperty("dbUsernameUAT"),
				calculatorData().getProperty("dbPasswordUAT"),
				calculatorData().getProperty("queryAIAWNationalityLoadings"),
				calculatorData().getProperty("excelCalculatorFilePath"), 4);

		// industry loading
		fetchDataFromDatabase(calculatorData().getProperty("dbUrlUAT"), calculatorData().getProperty("dbUsernameUAT"),
				calculatorData().getProperty("dbPasswordUAT"),
				calculatorData().getProperty("queryAIAWIndustryLoadings"),
				calculatorData().getProperty("excelCalculatorFilePath"), 5);

		// previous insurer loading
		fetchDataFromDatabase(calculatorData().getProperty("dbUrlUAT"), calculatorData().getProperty("dbUsernameUAT"),
				calculatorData().getProperty("dbPasswordUAT"),
				calculatorData().getProperty("queryAIAWPreviousInsurerLoadings"),
				calculatorData().getProperty("excelCalculatorFilePath"), 6);

		// Commission
		fetchDataFromDatabase(calculatorData().getProperty("dbUrlUAT"), calculatorData().getProperty("dbUsernameUAT"),
				calculatorData().getProperty("dbPasswordUAT"), calculatorData().getProperty("queryAIAWCommission"),
				calculatorData().getProperty("excelCalculatorFilePath"), 9);

		// Census
		toFetchCensusSheet("C:\\Users\\impelox-pc-048\\Desktop\\censuses sheet\\census_ab_automation.xlsx",
				"C:\\Users\\impelox-pc-048\\Downloads\\Sme\\Sme\\target\\ExcelCalculatorForDistributor\\Arshad New Calculator.xlsx", 0,
				10);
	}

}

;