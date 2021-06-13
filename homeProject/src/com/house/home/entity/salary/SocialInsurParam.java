package com.house.home.entity.salary;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * 社保公积金参数
 */
@Entity
@Table(name = "tSocialInsurParam")
public class SocialInsurParam extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;

    @Column(name = "Descr")
    private String descr;
    
    // 养老保险最低基数
    @Column(name = "EdmInsurBaseMin")
    private Double edmInsurBaseMin;
    
    // 养老保险最高基数
    @Column(name = "EdmInsurBaseMax")
    private Double edmInsurBaseMax;
    
    // 养老保险个人费率
    @Column(name = "EdmInsurIndRate")
    private Double edmInsurIndRate;
    
    // 养老保险单位费率
    @Column(name = "EdmInsurCmpRate")
    private Double edmInsurCmpRate;
    
    // 失业保险个人费率
    @Column(name = "UneInsurIndRate")
    private Double uneInsurIndRate;
    
    // 失业保险单位费率
    @Column(name = "UneInsurCmpRate")
    private Double uneInsurCmpRate;
    
    // 医保最低基数
    @Column(name = "MedInsurBaseMin")
    private Double medInsurBaseMin;
    
    // 医保最高基数
    @Column(name = "MedInsurBaseMax")
    private Double medInsurBaseMax;
    
    // 医保个人费率
    @Column(name = "MedInsurIndRate")
    private Double medInsurIndRate;
    
    // 医保单位费率
    @Column(name = "MedInsurCmpRate")
    private Double medInsurCmpRate;
    
    // 生育保险单位费率
    @Column(name = "BirthInsurCmpRate")
    private Double birthInsurCmpRate;
    
    // 公积金最低基数
    @Column(name = "HouFundBaseMin")
    private Double houFundBaseMin;
    
    // 公积金最高基数
    @Column(name = "HouFundBaseMax")
    private Double houFundBaseMax;
    
    // 公积金个人费率
    @Column(name = "HouFundIndRate")
    private Double houFundIndRate;
    
    // 公积金单位费率
    @Column(name = "HouFundCmpRate")
    private Double houFundCmpRate;
    
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

	@Column(name="BirthInsurBaseMin")
	private Double BirthInsurBaseMin;
	
	@Column(name="InjuryInsurBaseMin")
	private Double injuryInsurBaseMin;
	
	@Column(name="injuryInsurRate")
	private Double InjuryInsurRate;
	
    public Double getInjuryInsurBaseMin() {
		return injuryInsurBaseMin;
	}

	public void setInjuryInsurBaseMin(Double injuryInsurBaseMin) {
		this.injuryInsurBaseMin = injuryInsurBaseMin;
	}

	public Double getInjuryInsurRate() {
		return InjuryInsurRate;
	}

	public void setInjuryInsurRate(Double injuryInsurRate) {
		InjuryInsurRate = injuryInsurRate;
	}

	public Double getBirthInsurBaseMin() {
		return BirthInsurBaseMin;
	}

	public void setBirthInsurBaseMin(Double birthInsurBaseMin) {
		BirthInsurBaseMin = birthInsurBaseMin;
	}

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

    public Double getEdmInsurBaseMin() {
        return edmInsurBaseMin;
    }

    public void setEdmInsurBaseMin(Double edmInsurBaseMin) {
        this.edmInsurBaseMin = edmInsurBaseMin;
    }

    public Double getEdmInsurBaseMax() {
        return edmInsurBaseMax;
    }

    public void setEdmInsurBaseMax(Double edmInsurBaseMax) {
        this.edmInsurBaseMax = edmInsurBaseMax;
    }

    public Double getEdmInsurCmpRate() {
        return edmInsurCmpRate;
    }

    public void setEdmInsurCmpRate(Double edmInsurCmpRate) {
        this.edmInsurCmpRate = edmInsurCmpRate;
    }

    public Double getEdmInsurIndRate() {
        return edmInsurIndRate;
    }

    public void setEdmInsurIndRate(Double edmInsurIndRate) {
        this.edmInsurIndRate = edmInsurIndRate;
    }

    public Double getUneInsurIndRate() {
        return uneInsurIndRate;
    }

    public void setUneInsurIndRate(Double uneInsurIndRate) {
        this.uneInsurIndRate = uneInsurIndRate;
    }

    public Double getUneInsurCmpRate() {
        return uneInsurCmpRate;
    }

    public void setUneInsurCmpRate(Double uneInsurCmpRate) {
        this.uneInsurCmpRate = uneInsurCmpRate;
    }

    public Double getMedInsurBaseMin() {
        return medInsurBaseMin;
    }

    public void setMedInsurBaseMin(Double medInsurBaseMin) {
        this.medInsurBaseMin = medInsurBaseMin;
    }

    public Double getMedInsurBaseMax() {
        return medInsurBaseMax;
    }

    public void setMedInsurBaseMax(Double medInsurBaseMax) {
        this.medInsurBaseMax = medInsurBaseMax;
    }

    public Double getMedInsurIndRate() {
        return medInsurIndRate;
    }

    public void setMedInsurIndRate(Double medInsurIndRate) {
        this.medInsurIndRate = medInsurIndRate;
    }

    public Double getMedInsurCmpRate() {
        return medInsurCmpRate;
    }

    public void setMedInsurCmpRate(Double medInsurCmpRate) {
        this.medInsurCmpRate = medInsurCmpRate;
    }

    public Double getBirthInsurCmpRate() {
        return birthInsurCmpRate;
    }

    public void setBirthInsurCmpRate(Double birthInsurCmpRate) {
        this.birthInsurCmpRate = birthInsurCmpRate;
    }

    public Double getHouFundBaseMin() {
        return houFundBaseMin;
    }

    public void setHouFundBaseMin(Double houFundBaseMin) {
        this.houFundBaseMin = houFundBaseMin;
    }

    public Double getHouFundBaseMax() {
        return houFundBaseMax;
    }

    public void setHouFundBaseMax(Double houFundBaseMax) {
        this.houFundBaseMax = houFundBaseMax;
    }

    public Double getHouFundIndRate() {
        return houFundIndRate;
    }

    public void setHouFundIndRate(Double houFundIndRate) {
        this.houFundIndRate = houFundIndRate;
    }

    public Double getHouFundCmpRate() {
        return houFundCmpRate;
    }

    public void setHouFundCmpRate(Double houFundCmpRate) {
        this.houFundCmpRate = houFundCmpRate;
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

}
