package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ResrCustExcelFailed信息
 */
@Entity
@Table(name = "tResrCustExcelFailed")
public class ResrCustExcelFailed extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "TaskPK")
	private Integer taskPK;
	@Column(name = "FileRows")
	private Integer fileRows;
	@Column(name = "FailedReason")
	private String failedReason;
	@Column(name = "ExistsCustName")
	private String existsCustName;
	@Column(name = "ExistsBusinessMan")
	private String existsBusinessMan;
	@Column(name = "FailedFieldContent")
	private String failedFieldContent;
	@Column(name = "FailedFieldName")
	private String failedFieldName;
	
	public ResrCustExcelFailed() {
		super();
	}

	public ResrCustExcelFailed(Integer taskPK) {
		super();
		this.taskPK = taskPK;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public Integer getTaskPK() {
		return taskPK;
	}

	public void setTaskPK(Integer taskPK) {
		this.taskPK = taskPK;
	}

	public Integer getFileRows() {
		return fileRows;
	}

	public void setFileRows(Integer fileRows) {
		this.fileRows = fileRows;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public String getExistsCustName() {
		return existsCustName;
	}

	public void setExistsCustName(String existsCustName) {
		this.existsCustName = existsCustName;
	}

	public String getExistsBusinessMan() {
		return existsBusinessMan;
	}

	public void setExistsBusinessMan(String existsBusinessMan) {
		this.existsBusinessMan = existsBusinessMan;
	}

	public String getFailedFieldContent() {
		return failedFieldContent;
	}

	public void setFailedFieldContent(String failedFieldContent) {
		this.failedFieldContent = failedFieldContent;
	}

	public String getFailedFieldName() {
		return failedFieldName;
	}

	public void setFailedFieldName(String failedFieldName) {
		this.failedFieldName = failedFieldName;
	}

}
