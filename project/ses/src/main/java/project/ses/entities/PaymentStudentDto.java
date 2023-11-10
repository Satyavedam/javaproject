package project.ses.entities;

import java.time.LocalDate;

public class PaymentStudentDto {
	private String name;
	private String email;
	private String mobile;
	private Integer batchId;
	private LocalDate dateOfJoining;
	private LocalDate payDate;
	private Double amount;
	private String payMode;
	public PaymentStudentDto(String name, String email, String mobile, Integer batchId, LocalDate dateOfJoining,
			LocalDate payDate, Double amount, String payMode) {
		super();
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.batchId = batchId;
		this.dateOfJoining = dateOfJoining;
		this.payDate = payDate;
		this.amount = amount;
		this.payMode = payMode;
	}
	public PaymentStudentDto() {
		super();
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getMobile() {
		return mobile;
	}
	public Integer getBatchId() {
		return batchId;
	}
	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}
	public LocalDate getPayDate() {
		return payDate;
	}
	public Double getAmount() {
		return amount;
	}
	public String getPayMode() {
		return payMode;
	}
}
