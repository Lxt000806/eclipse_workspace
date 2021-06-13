package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * WorkType12信息
 */
@Entity
@Table(name = "tWorkType12")
public class WorkType12 extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 工种分类12编码 */
    @Id
	@Column(name = "Code")
	private String code;
    
    /** 工种分类12名称 */
	@Column(name = "Descr")
	private String descr;
	
	/** 工种分类1 */
	@Column(name = "WorkType1")
	private String workType1;
	
	/** 工种分类1名称 */
    @Transient
    private String workType1Name;
    
    /** 施工项目 */
    @Column(name = "PrjItem")
    private String prjItem;
    
    /** 施工项目名称 */
    @Transient
    private String prjItemName;
	
    /** 修改人 */
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	
	/** 过期标志 */
	@Column(name = "Expired")
	private String expired;
	
	/** 最后更新操作 */
	@Column(name = "ActionLog")
	private String actionLog;
	
	/** 最后更新时间 */
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	
	/** 付款期数 */
	@Column(name = "PayNum")
	private String payNum; 
	
	/** 申请最小延后天数 */
	@Column(name = "AppMinDay")
	private Integer appMinDay;
	
	/** 申请最大延后天数 */
	@Column(name = "AppMaxDay")
	private Integer appMaxDay;
	
	/** 付款才允许申请 */
	@Column(name = "MustPay")
	private String mustPay;
	
	/** 显示顺序 */
	@Column(name = "DispSeq")
	private Integer dispSeq;
	
	/** 验收施工项目 */
	@Column(name = "confPrjItem")
	private String confPrjItem;
	
	/** 人工工种分类2名称 */
	@Column(name = "OfferWorkType2")
	private String offerWorkType2;
	
	/** 人工工种分类2名称 */
    @Transient
    private String offerWorkType2Name;
    
    /** 签约每工地扣质保金 */
    @Column(name = "PerQualityFee")
    private Double perQualityFee;
    
    /** 签约质保金总额 */
    @Column(name = "SignQualityFee")
    private Double signQualityFee;
    
    /** 最少施工天数 */
    @Column(name = "MinDay")
    private Integer minDay;
    
    /** 最大施工天数 */
    @Column(name = "MaxDay")
    private Integer maxDay;
    
    /** 质保金起扣点 */
    @Column(name = "QualityFeeBegin")
    private Double qualityFeeBegin;
	
    /** 需要工种分类2人工需求 */
    @Column(name = "NeedWorktype2Req")
    private String needWorktype2Req;
    
    /** 上一同类工种 */
    @Column(name = "BefSameWorkType12")
    private String befSameWorkType12;
    @Column(name = "MinPhotoNum")
    private Integer minPhotoNum;
    @Column(name= "MaxPhotoNum")
    private Integer maxPhotoNum;
    @Column(name= "SalaryCtrl")
    private double salaryCtrl;
    @Column(name="IsTechnology")
    private String isTechnology; //是否上传图片模板
    @Column(name="BeginPrjItem")
    private String beginPrjItem; //开始施工节点
    @Column(name = "BeginCheck")
    private String beginCheck;
    @Column(name = "MinSalaryProvideAmount")
    private Double minSalaryProvideAmount;
    @Column(name = "ConfType")
    private String confType;
    @Column(name = "IsRegisterMall")
    private String isRegisterMall;
    @Column(name = "IsPrjApp")
    private String isPrjApp;
    
    /** 验收施工项目名称 */
    @Transient
    private String confPrjItemName;
    
    
	@Transient
	private String isSign;
	@Transient
	private String constructType;
	@Transient
	private String techCode; //工艺编号
	@Transient
	private String sourceType; //来源类型
	@Transient
	private String custCode; //客户编号
	@Transient
	private String workerClassify;//工人分类
	@Transient
	private String oldWorkerClassify;//原工人分类
	//equals
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj.getClass() != getClass()){
            return false;
        }
        WorkType12 workType12 = (WorkType12)obj;
        return workType12.getCode().trim().equals(this.code.trim());
    }

	public double getSalaryCtrl() {
		return salaryCtrl;
	}

	public void setSalaryCtrl(double salaryCtrl) {
		this.salaryCtrl = salaryCtrl;
	}

	//-- Getter、Setter--//
	public String getConstructType() {
		return constructType;
	}

	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}
	
	public String getWorkType1() {
		return this.workType1;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public Integer getAppMinDay() {
		return appMinDay;
	}

	public void setAppMinDay(Integer appMinDay) {
		this.appMinDay = appMinDay;
	}

	public Integer getAppMaxDay() {
		return appMaxDay;
	}

	public void setAppMaxDay(Integer appMaxDay) {
		this.appMaxDay = appMaxDay;
	}

	public String getMustPay() {
		return mustPay;
	}

	public void setMustPay(String mustPay) {
		this.mustPay = mustPay;
	}

	public Integer getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getConfPrjItem() {
		return confPrjItem;
	}

	public void setConfPrjItem(String confPrjItem) {
		this.confPrjItem = confPrjItem;
	}

	public String getOfferWorkType2() {
		return offerWorkType2;
	}

	public void setOfferWorkType2(String offerWorkType2) {
		this.offerWorkType2 = offerWorkType2;
	}


    public String getWorkType1Name() {
        return workType1Name;
    }


    public void setWorkType1Name(String workType1Name) {
        this.workType1Name = workType1Name;
    }


    public String getPrjItem() {
        return prjItem;
    }


    public void setPrjItem(String prjItem) {
        this.prjItem = prjItem;
    }


    public String getPrjItemName() {
        return prjItemName;
    }


    public void setPrjItemName(String prjItemName) {
        this.prjItemName = prjItemName;
    }


    public String getOfferWorkType2Name() {
        return offerWorkType2Name;
    }


    public void setOfferWorkType2Name(String offerWorkType2Name) {
        this.offerWorkType2Name = offerWorkType2Name;
    }


    public Double getPerQualityFee() {
        return perQualityFee;
    }


    public void setPerQualityFee(Double perQualityFee) {
        this.perQualityFee = perQualityFee;
    }


    public Double getSignQualityFee() {
        return signQualityFee;
    }


    public void setSignQualityFee(Double signQualityFee) {
        this.signQualityFee = signQualityFee;
    }


    public Integer getMinDay() {
        return minDay;
    }


    public void setMinDay(Integer minDay) {
        this.minDay = minDay;
    }


    public Integer getMaxDay() {
        return maxDay;
    }


    public void setMaxDay(Integer maxDay) {
        this.maxDay = maxDay;
    }


    public Double getQualityFeeBegin() {
        return qualityFeeBegin;
    }


    public void setQualityFeeBegin(Double qualityFeeBegin) {
        this.qualityFeeBegin = qualityFeeBegin;
    }


    public String getNeedWorktype2Req() {
        return needWorktype2Req;
    }


    public void setNeedWorktype2Req(String needWorktype2Req) {
        this.needWorktype2Req = needWorktype2Req;
    }


    public String getBefSameWorkType12() {
        return befSameWorkType12;
    }


    public void setBefSameWorkType12(String befSameWorkType12) {
        this.befSameWorkType12 = befSameWorkType12;
    }


    public String getConfPrjItemName() {
        return confPrjItemName;
    }


    public void setConfPrjItemName(String confPrjItemName) {
        this.confPrjItemName = confPrjItemName;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }
	public Integer getMinPhotoNum() {
		return minPhotoNum;
	}
	public void setMinPhotoNum(Integer minPhotoNum) {
		this.minPhotoNum = minPhotoNum;
	}
	public Integer getMaxPhotoNum() {
		return maxPhotoNum;
	}
	public void setMaxPhotoNum(Integer maxPhotoNum) {
		this.maxPhotoNum = maxPhotoNum;
	}

	public String getIsTechnology() {
		return isTechnology;
	}

	public void setIsTechnology(String isTechnology) {
		this.isTechnology = isTechnology;
	}

	public String getTechCode() {
		return techCode;
	}

	public void setTechCode(String techCode) {
		this.techCode = techCode;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getWorkerClassify() {
		return workerClassify;
	}

	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}

	public String getOldWorkerClassify() {
		return oldWorkerClassify;
	}

	public void setOldWorkerClassify(String oldWorkerClassify) {
		this.oldWorkerClassify = oldWorkerClassify;
	}

	public String getBeginPrjItem() {
		return beginPrjItem;
	}

	public void setBeginPrjItem(String beginPrjItem) {
		this.beginPrjItem = beginPrjItem;
	}

    public String getBeginCheck() {
        return beginCheck;
    }

    public void setBeginCheck(String beginCheck) {
        this.beginCheck = beginCheck;
    }

    public Double getMinSalaryProvideAmount() {
        return minSalaryProvideAmount;
    }

    public void setMinSalaryProvideAmount(Double minSalaryProvideAmount) {
        this.minSalaryProvideAmount = minSalaryProvideAmount;
    }

	public String getIsRegisterMall() {
		return isRegisterMall;
	}
	
	public void setIsRegisterMall(String isRegisterMall) {
		this.isRegisterMall = isRegisterMall;
	}
	
	public String getConfType() {
		return confType;
	}

	public void setConfType(String confType) {
		this.confType = confType;
	}

	public String getIsPrjApp() {
		return isPrjApp;
	}

	public void setIsPrjApp(String isPrjApp) {
		this.isPrjApp = isPrjApp;
	}

}
