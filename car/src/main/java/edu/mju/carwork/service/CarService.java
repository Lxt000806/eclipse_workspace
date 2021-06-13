package edu.mju.carwork.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.mju.carwork.domain.Car;
import edu.mju.carwork.service.CarQueryHelper;
import edu.mju.carwork.utils.Page;

@Service
public interface CarService {
	void addCar(Car car);
	List<Car> loadCar();
	List<Car> loadCarByCondition(CarQueryHelper helper);
	void delCar(String carNo);
	Car getCarByNo(String carNo);
	void updateCar(Car car);
	Page loadPagedCar(CarQueryHelper helper,Page page);
}
