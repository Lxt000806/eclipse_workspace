/**
 * 
 */
package edu.mju.carwork.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import edu.mju.carwork.domain.Car;
import edu.mju.carwork.service.CarQueryHelper;
import edu.mju.carwork.service.CarService;
import edu.mju.carwork.utils.Page;

/**
 * @author HY
 *
 */
@Controller
public class CarController {

	@Autowired
	private CarService carService = null;
	

	
	@PostMapping("/cars")
	public String createCar(Car car) throws Exception{
		
		carService.addCar(car);
		
		return "redirect:/cars";
	}
	
	@GetMapping("/cars")
    public String loadCar(Model model,CarQueryHelper helper,Page page) throws Exception{
		//List<Car> carList = carService.loadCarByCondition(helper);	
		//model.addAttribute("carList", carList);
		page = carService.loadPagedCar(helper, page);
		System.out.println(helper);
		model.addAttribute("page", page); //往model中存入数据，就是以key/value形式保存数据到请求范围
		model.addAttribute("helper", helper);
		return "list_car";
    	
    }

	@GetMapping("/cars/{carNo}")
	public String preUpdate(@PathVariable String carNo,Model model) throws Exception{
		
		Car car = carService.getCarByNo(carNo);
		
		model.addAttribute("car", car);
			
		return "update_car";
	}
	
	@PutMapping("/cars/{carNo}")
	public String updateCar(Car car,@PathVariable String carNo) throws Exception{
		
		car.setCarNo(carNo);
		carService.updateCar(car);
		
		return "redirect:/cars";
	}
	
	@DeleteMapping("/cars/{carNo}")
	public String removeCar(@PathVariable String carNo) throws Exception{
		
		carService.delCar(carNo);
		
		return "redirect:/cars";
		
	}
}
