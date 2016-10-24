package care.page.desktop;

import care.page.BasePage;
import care.util.Driver;
import care.variables.Variables;

public class EnrollmentPage extends BasePage {

	public EnrollmentPage(Driver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void completeRegistration(){
		
		// Fill required details
		super.type(Variables.enrollmentPage_tb_FirstName, randomStringOfLength(8));
		super.type(Variables.enrollmentPage_tb_LastName, randomStringOfLength(8));
		super.type(Variables.enrollmentPage_tb_Address, randomStringOfLength(8));
		super.type(Variables.enrollmentPage_tb_Email, generateEmail("seekerEnrollment"));
		super.type(Variables.enrollmentPage_tb_Password, Variables.gen_Password);
		
		// Click on Join button
		locateElement(Variables.enrollmentPage_btn_Join).click();
	}

}
