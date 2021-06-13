/**
 * 
 */
package edu.mju.carwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.carwork.dao.CarDao;
import edu.mju.carwork.domain.Car;
import edu.mju.carwork.utils.Page;

/**
 * @author HY
 *
 */

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDao carDao = null;
	
	@Override
	public void addCar(Car car) {

		carDao.addCar(car);
	}

	
	@Override
	public List<Car> loadCar() {

		return carDao.loadCar();
	}

	@Override
	public void delCar(String carNo) {

		carDao.delCar(carNo);
	}


	@Override
	public Car getCarByNo(String carNo) {
		

		return carDao.getCarByNo(carNo);
	}

	@Override
	public void updateCar(Car car) {


		carDao.updateCar(car);
	}

	@Override
	public List<Car> loadCarByCondition(CarQueryHelper helper) {

		return carDao.loadCarByCondition(helper);
	}

	@Override
	public Page loadPagedCar(CarQueryHelper helper, Page page) {
		
		page.setTotalRecNum(carDao.cntCarByCondition(helper));
		page.setPageContent(carDao.loadScopedCarByCondition(helper, page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

}
