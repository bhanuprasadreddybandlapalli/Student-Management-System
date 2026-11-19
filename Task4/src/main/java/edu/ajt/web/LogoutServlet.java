package edu.ajt.web;
import java.io.IOException;import javax.servlet.http.*;import javax.servlet.annotation.*;
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{HttpSession s=req.getSession(false);if(s!=null)s.invalidate();res.sendRedirect(req.getContextPath()+"/login");}
}