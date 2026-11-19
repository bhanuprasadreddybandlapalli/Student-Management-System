package edu.ajt.web;
import java.io.IOException;
import javax.servlet.*;import javax.servlet.http.*;import javax.servlet.annotation.*;
import org.hibernate.*;import org.mindrot.jbcrypt.BCrypt;
import edu.ajt.db.HibernateUtil;import edu.ajt.model.Admin;
@WebServlet("/setup")
public class SetupServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{req.getRequestDispatcher("/WEB-INF/views/setup.jsp").forward(req,res);}
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  String token=System.getenv("SMS_SETUP_TOKEN");
  if(token==null||token.isEmpty()||!token.equals(req.getParameter("token"))){res.sendError(403,"Setup token missing or incorrect");return;}
  String u=req.getParameter("username"),p=req.getParameter("password");
  if(u==null||!u.matches("[A-Za-z0-9_]{3,30}")||p==null||p.length()<10){req.setAttribute("error","Username 3-30 letters/digits; password at least 10 characters");doGet(req,res);return;}
  try(Session s=HibernateUtil.get().openSession()){Transaction tx=s.beginTransaction();try{
   Long count=s.createQuery("select count(a) from Admin a",Long.class).uniqueResult();
   if(count>0){tx.rollback();res.sendError(403,"Setup already completed");return;}
   Admin a=new Admin();a.setUsername(u);a.setPasswordHash(BCrypt.hashpw(p,BCrypt.gensalt()));s.save(a);tx.commit();res.sendRedirect(req.getContextPath()+"/login");
  }catch(Exception e){if(tx.isActive())tx.rollback();throw e;}}
  catch(Exception e){getServletContext().log("Setup failed",e);res.sendError(500,"Setup failed");}
 }
}