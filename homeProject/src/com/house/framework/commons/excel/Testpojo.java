package com.house.framework.commons.excel;

import java.util.Date;

/**
 * excel元注释导入, 测试excel
 *
 */
public class Testpojo {                        
    @ExcelAnnotation(exportName = "用户名", order=4)        
    String username;                               
    @ExcelAnnotation(exportName = "登录名", order=3)        
    String loginname;                              
    @ExcelAnnotation(exportName = "年龄", order=2)          
    Integer age;                                   
    @ExcelAnnotation(exportName = "收入", order=1)          
    Long   money;                                  
    @ExcelAnnotation(exportName = "时间", pattern="yyyy-MM-dd HH:mm:ss", order=0)          
    Date createtime;     
    
    public String getUsername() {                  
        return username;                           
    }                                              
    public void setUsername(String username) {     
        this.username = username;                  
    }                                              
    public String getLoginname() {                 
        return loginname;                          
    }                                              
    public void setLoginname(String loginname) {   
        this.loginname = loginname;                
    }                                              
    public Integer getAge() {                      
        return age;                                
    }                                              
    public void setAge(Integer age) {              
        this.age = age;                            
    }                                              
    public Long getMoney() {                       
        return money;                              
    }                                              
    public void setMoney(Long money) {             
        this.money = money;                        
    }                                              
    public Date getCreatetime() {                  
        return createtime;                         
    }                                              
    public void setCreatetime(Date createtime) {   
        this.createtime = createtime;              
    }                                              
}  
