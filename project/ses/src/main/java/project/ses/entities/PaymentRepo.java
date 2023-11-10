package project.ses.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
	//14
	@Query("From Payment as p inner join Student as s on p.studentId=s.studentId where batchId=:batchId")
	List<Payment>getAllPaymentsByBatch(@Param("batchId")int batchId);

	Payment findByStudentId(int studentId);
	
	// 16.List all payments of a particular payment mode
		@Query("select p from Payment as p where payMode=:payMode")
		List<Payment> getAllPaymentsByPayMode(@Param("payMode") String payMode);

	// 15.List all payments made between two dates
		@Query("From Payment where payDate between :date1 and :date2")
		List<Payment> getAllPaymentsBTDays(@Param("date1") LocalDate date1, @Param("date2") LocalDate date2);

		@Query(value = "select * from payments where paydate between :date1 and :date2", nativeQuery = true)
		List<Payment> getAllPaymentsBy2Dates(@Param("date1") Date date1, @Param("date2") Date date2);


	
}
