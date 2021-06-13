package com.house.home.entity.salary;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tSalaryStatusCtrl")
public class SalaryStatusCtrl extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryMon")
	private Integer salaryMon;
	@Column(name = "SalaryScheme")
	private Integer salaryScheme;
	@Column(name = "Status")
	private String status;
	@Column(name = "ProcessTime")
	private Date processTime;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getSalaryMon() {
		return salaryMon;
	}
	public void setSalaryMon(Integer salaryMon) {
		this.salaryMon = salaryMon;
	}
	public Integer getSalaryScheme() {
		return salaryScheme;
	}
	public void setSalaryScheme(Integer salaryScheme) {
		this.salaryScheme = salaryScheme;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}


}
