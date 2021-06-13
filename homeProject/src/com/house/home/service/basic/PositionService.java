package com.house.home.service.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Position;

public interface PositionService extends BaseService {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Position position);
    
    public Position getByDesc2(String desc2);

    public List<Position> findByNoExpired();
    
    public boolean codeIsRepeative(String code);
    
    public String getDepLeader(String code);
    
    public Integer getMaxCode() throws SQLException;
    
    public void doSave(Position position);
}
