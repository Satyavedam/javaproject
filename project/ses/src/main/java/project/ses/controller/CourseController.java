package project.ses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import project.ses.entities.Course;
import project.ses.entities.CourseRepo;
import project.ses.entities.Payment;
import project.ses.entities.PaymentRepo;
import project.ses.entities.Student;
import project.ses.entities.StudentRepo;

@RestController
public class CourseController {
	@Autowired
	CourseRepo courseRepo;
	

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/AddCourse")
	@Operation(summary = "To Add a new Course", description = "Adding a new Course details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New course is Added"),
			@ApiResponse(responseCode = "400", description = "Invalid user input "),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	public Course addNewCourse(@Valid @RequestBody Course course) {
		List<Course> existingCourses = courseRepo.findAll();

		// Check if a course with the same details already exists
		if (existingCourses.contains(course)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A course with the same details already exists.");
		}

		courseRepo.save(course);
		return course;
	}


	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/UpdateCourse/{code}")
	@Operation(summary = "Update a Course", description = "Update Course details by its code")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Course successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "404", description = "Course not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Course updateCourse(@PathVariable("code") String code, @Valid @RequestBody Course course) {
		var optCourse = courseRepo.findById(code);
		try {
			if (!optCourse.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Code Not Found..!");
			courseRepo.save(course);
			return course;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteCourse/{code}")
	@Operation(summary = "Delete a Course", description = "Delete a Course by its code")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Course successfully deleted"),
			@ApiResponse(responseCode = "400", description = "invalid data "),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void deleteCourse(@PathVariable("code") String code) {
		var optCourse = courseRepo.findById(code);
		if (optCourse.isPresent()) {
			courseRepo.deleteById(code);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given Course code doesn't exist");
		}
	}}
