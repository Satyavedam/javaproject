package project.ses.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatchRepo extends JpaRepository<Batch, Integer> {
	@Query("From Batch where endDate>GetDate()")
	List<Batch> getAllBatchesCurrentlyRunning();

	@Query("From Batch where endDate<GetDate()")
	List<Batch> getAllBatchesCompleted();

	List<Batch> getAllBatchesByCourseCode(String Code);

	@Query("From Batch where startDate between :date1  and   :date2")
	List<Batch> getAllBatchesBy2GivenDates(LocalDate date1, LocalDate date2);
}
