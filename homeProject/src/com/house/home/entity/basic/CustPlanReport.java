package com.house.home.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tCustPlanReport")
public class CustPlanReport extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Desc1")
	private String desc1;
	
	@Column(name = "Desc2")
	private String desc2;
	
	@Column(name = "Type")
	private String type;

	@Column(name = "IsEnable")
	private String isEnable;

	@Column(name = "DispSeq")
	private Integer dispSeq;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }
	
}
