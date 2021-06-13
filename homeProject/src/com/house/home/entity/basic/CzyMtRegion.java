package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CzyMtRegion信息
 */
@Entity
@Table(name = "tCZYMtRegion")
public class CzyMtRegion extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CZYBH")
	private String czybh;
	@Column(name = "MtRegionCode")
	private String mtRegionCode;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
	}
	public void setMtRegionCode(String mtRegionCode) {
		this.mtRegionCode = mtRegionCode;
	}
	
	public String getMtRegionCode() {
		return this.mtRegionCode;
	}

}
