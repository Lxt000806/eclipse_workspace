package edu.mju.stuwork.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.service.StudentService;
import edu.mju.stuwork.service.StudentServiceImpl;

/**
 * Servlet implementation class StuMgrServlet
 */
@WebServlet("/StuMgr")
public class StuMgrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StuMgrServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String task = request.getParameter("task");
		
		if(task.equals("createStu")) {
			//收集3个数据
			int stuNo = Integer.parseInt(request.getParameter("stuno"));
			String stuName = request.getParameter("stuname");
			int stuAge = Integer.parseInt(request.getParameter("stuage"));
			double stuMark = Double.parseDouble(request.getParameter("stumark"));
			
			//封装到学生当中
			Student stu = new Student();
			stu.setStuNo(stuNo);
			stu.setStuName(stuName);
			stu.setStuAge(stuAge);
			stu.setStuMark(stuMark);
			
			//创建一个service，把对象交给service，service会去访问Dao
			StudentService stuService = new StudentServiceImpl();
			stuService.regStudent(stu);
			
			response.sendRedirect("StuMgr?task=loadStus"); //重定向，发起一个新的请求
		}
		else if(task.equals("toInput")) {
			request.getRequestDispatcher("./WEB-INF/views/input_student.jsp").forward(request, response);
		}
		else if(task.equals("loadStus")){
			
			StudentService stuService = new StudentServiceImpl();
			List<Student> stuList = stuService.loadStus();
			
			//保存学生信息到请求范围，以便让其他的页面能够分享这些数据
			request.setAttribute("stuList", stuList);
			request.getRequestDispatcher("./WEB-INF/views/list_student.jsp").forward(request, response);			
		}
		else if(task.equals("removeStu")){
			int stuNo = Integer.parseInt(request.getParameter("stuno"));
			
			StudentService stuService = new StudentServiceImpl();
			stuService.removeStu(stuNo);
			
			response.sendRedirect("StuMgr?task=loadStus");
		}
		else if(task.equals("preUpdate")) {
			int stuNo = Integer.parseInt(request.getParameter("stuno"));
			
			StudentService stuService = new StudentServiceImpl();
			Student stu = stuService.getStuByNo(stuNo);
			
			request.setAttribute("stu",stu);
			request.getRequestDispatcher("./WEB-INF/views/update_student.jsp").forward(request, response);
			
		}
        else if(task.equals("updateStu")){
			int stuNo = Integer.parseInt(request.getParameter("stuno"));
			String stuName = request.getParameter("stuname");
			int stuAge = Integer.parseInt(request.getParameter("stuage"));
			double stuMark = Double.parseDouble(request.getParameter("stumark"));
			
			Student stu = new Student();
			stu.setStuNo(stuNo);
			stu.setStuName(stuName);
			stu.setStuAge(stuAge);
			stu.setStuMark(stuMark);
			
			StudentService stuService = new StudentServiceImpl();
			stuService.updateStu(stu);

			response.sendRedirect("StuMgr?task=loadStus");	
			
		}
	}

}


