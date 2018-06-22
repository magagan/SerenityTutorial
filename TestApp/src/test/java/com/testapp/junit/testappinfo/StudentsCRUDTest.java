package com.testapp.junit.testappinfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.testapp.cucumber.serenity.StudentSerenitySteps;
import com.testapp.model.StudentClass;
import com.testapp.testbase.TestBase;
import com.testapp.utils.ReuseableSpecifications;
import com.testapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {

	static String firstName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String lastName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String programme = "ComputerScience";
	static String email = TestUtils.getRandomValue()+"xyz@gmail.com";
	static int studentId;
	
	@Steps
	StudentSerenitySteps steps;
	
	@Title("This test will create new student")
	@Test
	public void test001(){
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");		
		
		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201)
		.spec(ReuseableSpecifications.getGenericResponseSpec());
	}
	
	@Title("Verify if the student was added to the application")
	@Test
	public void test002(){
		
	HashMap<String,Object> value =	steps.getStudentInfoByFirstName(firstName);	
	assertThat(value,hasValue(firstName));
	
	studentId = (int) value.get("id");
		
	}
	
	@Title("Update the user information and verify the updated information!")
	@Test
	public void test003(){
		
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		firstName = firstName+"_Updated";
		steps.updateStudent(studentId, firstName, lastName,email, programme, courses);
		
		HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
		assertThat(value,hasValue(firstName));
	}
	
	@Title("Delete the student and verify if the student is deleted!")
	@Test
	public void test004(){
		steps.deleteStudent(studentId);
		steps.getStudentById(studentId).statusCode(404);
	}	

}
