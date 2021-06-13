package edu.mju.carwork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.mju.carwork.domain.Car;
import edu.mju.carwork.service.CarQueryHelper;


public interface CarDao {

	void addCar(Car car);
	List<Car> loadCar();
	List<Car> loadCarByCondition(CarQueryHelper helper);
	void delCar(String carNo);
	Car getCarByNo(String carNo);
	void updateCar(Car car);
	
	/**
    * ��ѯ��ĳ��ѯ��������£��ܹ��ļ�¼����
    * @param helper
    * @return
    */
	long cntCarByCondition(CarQueryHelper helper);
	List<Car> loadScopedCarByCondition(@Param("helper") CarQueryHelper helper, 
									   @Param("begin") int beginIdx, 
									   @Param("size") int pageSize);
}
