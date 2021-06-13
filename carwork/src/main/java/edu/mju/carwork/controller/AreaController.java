package edu.mju.carwork.controller;

import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import edu.mju.carwork.domain.Area;
import edu.mju.carwork.service.AreaQueryHelper;
import edu.mju.carwork.service.AreaService;
import edu.mju.carwork.utils.Page;

@Controller
public class AreaController {
	@Autowired
	private AreaService areaService = null;
	

	
	@PostMapping("/areas")
	public String createArea(Area area,MultipartFile areaPhoto) throws Exception{
		area.setAreaPic(areaPhoto.getBytes());
		areaService.addArea(area);
		
		return "redirect:/areas";
	}
	
	@GetMapping("/areas")
    public String loadArea(Model model,AreaQueryHelper helper,Page page) throws Exception{
		//List<Area> areaList = areaService.loadAreaByCondition(helper);	
		//model.addAttribute("areaList", areaList);
		page = areaService.loadPagedArea(helper, page);
		model.addAttribute("page", page); //往model中存入数据，就是以key/value形式保存数据到请求范围
		model.addAttribute("helper", helper);
		return "list_area";
    	
    }

	@GetMapping("/areas/{areaNo}")
	public String preUpdate(@PathVariable int areaNo,Model model) throws Exception{
		
		Area area = areaService.getAreaByNo(areaNo);
		
		model.addAttribute("area", area);
			
		return "update_area";
	}
	
	@PostMapping("/areas/{areaNo}")
	public String updateArea(Area area,@PathVariable int areaNo,MultipartFile areaPhoto) throws Exception{
		area.setAreaNo(areaNo);
		if(areaPhoto.getBytes()!=null && areaPhoto.getBytes().length>0)
		    area.setAreaPic(areaPhoto.getBytes());
		else
			area.setAreaPic(areaService.getAreaPicByNo(areaNo));
		areaService.updateArea(area);
		
		return "redirect:/areas";
	}
	
	@DeleteMapping("/areas/{areaNo}")
	public String removeArea(@PathVariable int areaNo) throws Exception{
		
		areaService.delArea(areaNo);
		
		return "redirect:/areas";
		
	}
	
	@GetMapping("/areas/{areaNo}/photo")
	public String getAreaPicByNo(@PathVariable int areaNo, 
			                    HttpServletRequest request,
			                    HttpServletResponse response) throws Exception{
		
		byte[] data = areaService.getAreaPicByNo(areaNo);
//		System.out.println(data.length);
		
		//无法从服务器获得数据
		if(data==null || data.length==0){
			//网址路径转成物理路径
			String imgFilePath = request.getRealPath("/")+"/resources/pics/default.png";
			//System.out.println(imgFilePath);
			FileInputStream fis = new FileInputStream(imgFilePath);
			data = new byte[fis.available()];
			fis.read(data);
			fis.close();
		}
		
		response.setContentType("image/jpeg"); //MIME
		ServletOutputStream oss = response.getOutputStream();
		oss.write(data);
		oss.flush();
		oss.close();
		
		
		return null;
		
	}

}
