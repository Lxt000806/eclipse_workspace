package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemSetDetail信息
 */
@Entity
@Table(name = "tItemSetDetail")
public class ItemSetDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ItemCode")
	private String itemcode;
	@Column(name = "UnitPrice")
	private Double unitprice;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Column(name = "CustTypeItemPk")
	private Integer custTypeItemPk;
	
	@Column(name = "Algorithm")
	private String algorithm;
	
	@Column(name = "AlgorithmPer")
	private Double algorithmPer;
	
	@Column(name = "AlgorithmDeduct")
	private Double algorithmDeduct;
	
	@Column(name = "ItemTypeCode")
	private String itemTypeCode;
	
	@Column(name = "Remarks")
	private String remarks;
	
	@Column(name = "Qty")
	private Double qty;
	
	@Column(name = "CutType")
	private String cutType;

    @Transient
	private String itemDescr;	
	@Transient
	private String itemtype1;
	@Transient
	private String unSelected;
	@Transient
	private String custCode;
	@Transient
	private String custType;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}


	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
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

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getItemtype1() {
		return itemtype1;
	}

	public void setItemtype1(String itemtype1) {
		this.itemtype1 = itemtype1;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

    public Integer getCustTypeItemPk() {
        return custTypeItemPk;
    }

    public void setCustTypeItemPk(Integer custTypeItemPk) {
        this.custTypeItemPk = custTypeItemPk;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Double getAlgorithmPer() {
        return algorithmPer;
    }

    public void setAlgorithmPer(Double algorithmPer) {
        this.algorithmPer = algorithmPer;
    }

    public Double getAlgorithmDeduct() {
        return algorithmDeduct;
    }

    public void setAlgorithmDeduct(Double algorithmDeduct) {
        this.algorithmDeduct = algorithmDeduct;
    }

    public String getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(String itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getCutType() {
        return cutType;
    }

    public void setCutType(String cutType) {
        this.cutType = cutType;
    }

}
