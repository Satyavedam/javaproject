package project.ses.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import project.ses.entities.Payment;
import project.ses.entities.PaymentRepo;
import project.ses.entities.PaymentStudentDto;
import project.ses.entities.Student;
import project.ses.entities.StudentRepo;
@RestController
public class StudentPaymentController {
@Autowired
private	StudentRepo studentRepo;
@Autowired
private	PaymentRepo paymentRepo;
	
//	@PostMapping("/addstudentwithpayment")
//	public PaymentStudentDto addStudentPayment(@Valid @RequestBody PaymentStudentDto paymentstudent) {
//		try {
//			Student s = new Student();
//			s.setName(paymentstudent.getName());
//			s.setEmail(paymentstudent.getEmail());
//			s.setMobile(paymentstudent.getMobile());
//			s.setBatchId(paymentstudent.getBatchId());
//			s.setDateOfJoining(paymentstudent.getDateOfJoining());
//
//			List<Student> listStudent = studentRepo.findAll();
//			boolean present = listStudent.contains(s);
//			if (present)
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already present");
//			studentRepo.save(s);
//			Payment p = new Payment();
//			p.setStudentId(s.getStudentId());
//			p.setAmount(paymentstudent.getAmount());
//			p.setPayDate(paymentstudent.getPayDate());
//			p.setPayMode(paymentstudent.getPayMode());
//
//			paymentRepo.save(p);
//
//			return paymentstudent;
//		} catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//		}
//
//	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/AddStudentWithPayment")
	@Operation(summary = "Add a Student with Payment", description = "add a new student along with payment to the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student and Payment created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@Transactional
	public PaymentStudentDto addStudentPayment(@Valid @RequestBody PaymentStudentDto paymentstudent) {
		try {
			Student s = new Student();
			s.setName(paymentstudent.getName());
			s.setEmail(paymentstudent.getEmail());
			s.setMobile(paymentstudent.getMobile());
			s.setBatchId(paymentstudent.getBatchId());
			s.setDateOfJoining(paymentstudent.getDateOfJoining());

			List<Student> listStudent = studentRepo.findAll();
			boolean present = listStudent.contains(s);
			if (present)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already present");
			studentRepo.save(s);
			Payment p = new Payment();
			p.setStudentId(s.getStudentId());
			p.setAmount(paymentstudent.getAmount());
			p.setPayDate(paymentstudent.getPayDate());
			p.setPayMode(paymentstudent.getPayMode());

			paymentRepo.save(p);

			return paymentstudent;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}
	
//	@DeleteMapping("/deletestudentpayment/{studentId}")
//	public String deleteStudentWithPayment(@PathVariable("studentId") int studentId) {
//		Optional<Student> s = studentRepo.findById(studentId);
//		if (!s.isPresent()) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Id Not Exist...!");
//		} else {
//			Student student = s.get();
//			Payment payment = paymentRepo.findByStudentId(studentId);
//			paymentRepo.delete(payment);
//			studentRepo.delete(student);
//			return "Both Student ,StudentPayment are Deleted";
//		}
//
//	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteStudentPayment/{studentId}")
	@Operation(summary = "Delete Student with Payment", description = "Delete a student along with their payment from the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student and Payment deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Student not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@Transactional
	public String deleteStudentWithPayment(@PathVariable("studentId") int studentId) {
		Optional<Student> s = studentRepo.findById(studentId);
		if (!s.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Id Not Exist...!");
		} else {
			Student student = s.get();
			Payment payment = paymentRepo.findByStudentId(studentId);
			paymentRepo.delete(payment);
			studentRepo.delete(student);
			return "Both Student ,StudentPayment are Deleted";
		}

	}
	
	
//3	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/UpdateStudent/{StudentId}")
	@Operation(summary = "UpdateStudent by StudentId", description = "Updating a Student By Studentid.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updated student by StudentId"),
			@ApiResponse(responseCode = "404", description = "StudentId not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public String updateStudent(@Valid @PathVariable("studentId") Integer studentId, @RequestParam("email") String email) {
		var optStudent = studentRepo.findById(studentId);
		if (!optStudent.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "StudentId Not Found...!");
		var student = optStudent.get();
		student.setEmail(email);
		studentRepo.save(student);
		return "Updated";
	}
//4
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/UpdatePayments/{PaymentId}")
	@Operation(summary = "Update Payment By PaymentId", description = "Updating a Payment By PaymentId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updated Payment by PaymentId"),
			@ApiResponse(responseCode = "404", description = "PaymentId not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Payment updatePayment(@Valid @PathVariable("paymentId") Integer paymentId, @RequestBody Payment payment) {
		var optPayment = paymentRepo.findById(paymentId);
		if (!optPayment.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Id Not Found...!");
		paymentRepo.save(payment);
		return payment;
	}


}
