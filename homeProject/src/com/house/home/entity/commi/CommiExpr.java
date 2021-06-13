package com.house.home.entity.commi;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tCommiExpr")
public class CommiExpr extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Expr")
	private String expr;
	@Column(name = "ExprRemarks")
	private String exprRemarks;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "RightCardinalExpr")
	private String rightCardinalExpr;
	@Column(name = "RightCardinalExprRemarks")
	private String rightCardinalExprRemarks;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getExpr() {
		return expr;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getExprRemarks() {
		return exprRemarks;
	}
	public void setExprRemarks(String exprRemarks) {
		this.exprRemarks = exprRemarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getRightCardinalExpr() {
		return rightCardinalExpr;
	}
	public void setRightCardinalExpr(String rightCardinalExpr) {
		this.rightCardinalExpr = rightCardinalExpr;
	}
	public String getRightCardinalExprRemarks() {
		return rightCardinalExprRemarks;
	}
	public void setRightCardinalExprRemarks(String rightCardinalExprRemarks) {
		this.rightCardinalExprRemarks = rightCardinalExprRemarks;
	}
	
}
