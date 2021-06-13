package com.house.home.entity.project;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 上一工种复合主键
 * @author lenovo-l729
 *
 */
@Embeddable
public class BefWorkType12PK implements Serializable{
    
    /** 工种分类12 */
    private String workType12;
    
    /** 评价上一工种 */
    private String befWorktype12;

    //-- Getter、Setter --//
    public String getWorkType12() {
        return workType12;
    }

    public void setWorkType12(String workType12) {
        this.workType12 = workType12;
    }

    public String getBefWorktype12() {
        return befWorktype12;
    }

    public void setBefWorktype12(String befWorktype12) {
        this.befWorktype12 = befWorktype12;
    }
    
    
}
