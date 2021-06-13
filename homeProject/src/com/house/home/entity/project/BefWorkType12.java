package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 上一工种
 * @author lenovo-l729
 *
 */
@Entity(name = "befWorkType12")
@Table(name = "tBefWorkType12")
public class BefWorkType12 {

    /** 主键：WorkType12、BefWorktype12 */
    @EmbeddedId
    private BefWorkType12PK id;
    
    /** 是否评价 */
    @Column(name = "IsEval")
    private String isEval;
    
    /** 最后更新时间 */
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    /** 最后更新人 */
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    /** 过期标志 */
    @Column(name = "Expired")
    private String expired;
    
    /** 最后更新操作 */
    @Column(name = "ActionLog")
    private String actionLog;

    @Column(name = "IsNext")//是否相邻工种
    private String isNext;
    
    @Column(name = "WorkerArrCtrl")//工人安排控制
    private String workerArrCtrl;
    //-- Getter、Setter --//
    public BefWorkType12PK getId() {
        return id;
    }

    public void setId(BefWorkType12PK id) {
        this.id = id;
    }

    public String getIsEval() {
        return isEval;
    }

    public void setIsEval(String isEval) {
        this.isEval = isEval;
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

	public String getIsNext() {
		return isNext;
	}

	public void setIsNext(String isNext) {
		this.isNext = isNext;
	}

	public String getWorkerArrCtrl() {
		return workerArrCtrl;
	}

	public void setWorkerArrCtrl(String workerArrCtrl) {
		this.workerArrCtrl = workerArrCtrl;
	}
    
    
}
