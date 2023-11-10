package project.ses.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "courses")
public class Course {
	@NotBlank(message="Course Name is required")
	@Pattern(regexp = "^[a-zA-Z0-9 #]*$",message="Course code should only contain letters and numbers")
	@Id
	@Column(name = "Code")
	private String code;
	@NotBlank(message="Course Name is required")
	@Pattern(regexp="^[A-Za-z #]+$",message="Name must contain only letters and spaces")
	@Column(name = "Name")
	private String name;
	@Positive(message="Duration cannot be negative")
	@Column(name = "Durationindays")
	private Integer durationInDays;
	@PositiveOrZero(message = "Fee must be >= 0")
	@Column(name = "Fee")
	private Double fee;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(Integer durationInDays) {
		this.durationInDays = durationInDays;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	@JsonIgnore
	List<Batch> batches = new ArrayList<>();

	@Override
	public int hashCode() {
		return Objects.hash(code, durationInDays, fee, name);
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(code, other.code) && Objects.equals(durationInDays, other.durationInDays)
				&& Objects.equals(fee, other.fee) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", durationInDays=" + durationInDays + ", fee=" + fee + "]";
	}

}
