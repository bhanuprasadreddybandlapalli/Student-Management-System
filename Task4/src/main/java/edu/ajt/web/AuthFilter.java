package edu.ajt.web;
import java.io.IOException;
import javax.servlet.*;import javax.servlet.http.*;import javax.servlet.annotation.*;
@WebFilter("/students")
public class AuthFilter implements Filter {
 public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{
  HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse res=(HttpServletResponse)response;
  res.setHeader("Cache-Control","no-store");
  HttpSession session=req.getSession(false);
  if(session==null||session.getAttribute("admin")==null){res.sendRedirect(req.getContextPath()+"/login");return;}
  chain.doFilter(request,response);
 }
}