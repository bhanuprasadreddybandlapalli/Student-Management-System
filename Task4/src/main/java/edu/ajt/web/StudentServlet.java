package edu.ajt.web;
import java.io.IOException;
import javax.servlet.*;import javax.servlet.http.*;import javax.servlet.annotation.*;
import edu.ajt.dao.StudentDao;import edu.ajt.model.Student;
@WebServlet("/students")
public class StudentServlet extends HttpServlet {
 private final StudentDao dao=new StudentDao();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  String action=req.getParameter("action");
  try{
   if("edit".equals(action)||"new".equals(action)){req.setAttribute("courses",dao.courses());if("edit".equals(action))req.setAttribute("student",dao.get(Long.parseLong(req.getParameter("id"))));req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req,res);}
   else{req.setAttribute("students",dao.list(req.getParameter("q")));req.getRequestDispatcher("/WEB-INF/views/students.jsp").forward(req,res);}
  }catch(Exception e){getServletContext().log("Student query failed",e);res.sendError(500,"Database operation failed");}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  req.setCharacterEncoding("UTF-8");
  try{
   String action=req.getParameter("action");
   if("delete".equals(action)){dao.delete(Long.parseLong(req.getParameter("id")));}
   else if("save".equals(action)){
    String enrollment=trim(req.getParameter("enrollmentNo")),name=trim(req.getParameter("fullName")),email=trim(req.getParameter("email"));
    if(!enrollment.matches("[A-Za-z0-9-]{3,30}")||name.length()<2||name.length()>120||!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")){res.sendError(400,"Invalid student data");return;}
    String id=req.getParameter("id");Student st=id==null||id.isEmpty()?new Student():dao.get(Long.parseLong(id));
    if(st==null){res.sendError(404);return;}st.setEnrollmentNo(enrollment);st.setFullName(name);st.setEmail(email);dao.save(st,Long.parseLong(req.getParameter("courseId")));
   }else{res.sendError(400);return;}
   res.sendRedirect(req.getContextPath()+"/students");
  }catch(IllegalArgumentException e){res.sendError(400,"Invalid input");}catch(Exception e){getServletContext().log("Student update failed",e);res.sendError(500,"Could not save changes");}
 }
 private static String trim(String s){return s==null?"":s.trim();}
}