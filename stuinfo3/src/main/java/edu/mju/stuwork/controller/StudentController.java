/**
 * 
 */
package edu.mju.stuwork.controller;

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

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.service.StudentQueryHelper;
import edu.mju.stuwork.service.StudentService;
import edu.mju.stuwork.utils.Page;

/**
 * @author joeyang ong
 *
 */
@Controller
public class StudentController {
	
	@Autowired
	private StudentService stuService = null;
	
	//RESTFUL API规范
	
	@GetMapping("/students/input")
	public String toInput() throws Exception{
		return "input_student";
	}
	
	@PostMapping("/students")
	public String createStudent(Student stu,MultipartFile stuPhoto) throws Exception{
		
		stu.setStuPic(stuPhoto.getBytes());
		stuService.regStu(stu);
		
		return "redirect:/students";
	}
	
	@GetMapping("/students")
    public String loadStus(Model model, StudentQueryHelper helper,Page page) throws Exception{

		page = stuService.loadPagedStus(helper, page);
		
		System.out.println(helper);
		
		model.addAttribute("page", page); //往model中存入数据，就是以key/value形式保存数据到请求范围
		model.addAttribute("helper", helper);
		
		return "list_student";
    	
    }
	
	@GetMapping("/students/{stuNo}")
	public String preUpdate(@PathVariable int stuNo,Model model) throws Exception{
		
		Student stu = stuService.getStuByNo(stuNo);
		
		model.addAttribute("stu", stu);
		
		
		return "update_student";
	}
	
	@PostMapping("/students/{stuNo}")
	public String updateStudent(Student stu,@PathVariable int stuNo,MultipartFile stuPhoto) throws Exception{
		
		stu.setStuNo(stuNo);
		if(stuPhoto.getBytes()!=null && stuPhoto.getBytes().length>0)
		    stu.setStuPic(stuPhoto.getBytes());
		else
			stu.setStuPic(stuService.getStuPicByNo(stuNo));

		stuService.updateStu(stu);
		
		return "redirect:/students";
	}
	
	@DeleteMapping("/students/{stuNo}")
	public String removeStudent(@PathVariable int stuNo) throws Exception{
		
		stuService.removeStu(stuNo);
		
		return "redirect:/students";
		
	}

	
	@GetMapping("/students/{stuNo}/photo")
	public String getStuPicByNo(@PathVariable int stuNo, 
			                    HttpServletRequest request,
			                    HttpServletResponse response) throws Exception{
		
		byte[] data = stuService.getStuPicByNo(stuNo);
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
