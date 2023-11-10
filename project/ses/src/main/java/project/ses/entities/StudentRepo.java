package project.ses.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepo extends JpaRepository<Student, Integer> {

	// List<Student> getAllStudentByBatchId(int batchId);

	@Query("From Student  where batchId in :batchId")
	List<Student> getAllStudentByBatchId(@Param("batchId") Integer batchId);

	@Query("From Student as s inner join Batch as b on s.batchId=b.batchId where endDate>GETDATE()")
	List<Student> getAllStudentsCurrentlyRunning();

	@Query("From Student as s inner join Batch as b on  s.batchId=b.batchId join Course c on c.code=b.courseCode where code=:code")
	List<Student> getAllStudentsByCourse(@Param("code") String code);

	List<Student> findStudentByNameContaining(@Param("batchId") String Name);

}
