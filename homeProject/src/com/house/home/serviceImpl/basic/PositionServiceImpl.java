package com.house.home.serviceImpl.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PositionDao;
import com.house.home.entity.basic.Position;
import com.house.home.service.basic.PositionService;

@SuppressWarnings("serial")
@Service
public class PositionServiceImpl extends BaseServiceImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;
    
    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Position position){
        return positionDao.findPageBySql(page, position);
    }
    
    public Position getByDesc2(String desc2){
        return positionDao.getByDesc2(desc2);
    }

    public List<Position> findByNoExpired() {
        return positionDao.findByNoExpired();
    }
    
    @Override
    public boolean codeIsRepeative(String code){
        return positionDao.getByCode(code) != null;
    }
    
    @Override
    public String getDepLeader(String code) {
        return positionDao.getDepLeader(code);
    }

    @Override
    public Integer getMaxCode() throws SQLException {
        return positionDao.getMaxCode();
    }
    
    @Override
    public void doSave(Position position){
        position.setCode(getSeqNo("tPosition"));
        save(position);
    }

}
