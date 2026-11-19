package edu.ajt.web;
import java.io.IOException;
import javax.servlet.*;import javax.servlet.http.*;import javax.servlet.annotation.*;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import edu.ajt.db.HibernateUtil;import edu.ajt.model.Admin;
@WebServlet("/login")
public class AuthServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,res);}
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  String username=req.getParameter("username"),password=req.getParameter("password");
  if(username!=null&&password!=null){try(Session s=HibernateUtil.get().openSession()){
   Admin a=s.createQuery("from Admin where username=:u",Admin.class).setParameter("u",username.trim()).uniqueResult();
   if(a!=null&&BCrypt.checkpw(password,a.getPasswordHash())){req.changeSessionId();req.getSession().setAttribute("admin",a.getUsername());res.sendRedirect(req.getContextPath()+"/students");return;}
  }catch(Exception e){getServletContext().log("Login error",e);}}
  req.setAttribute("error","Invalid credentials or database unavailable");doGet(req,res);
 }
}