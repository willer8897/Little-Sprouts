package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class mrequests_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("\t<title>Little Sprouts Management Portal</title>\r\n");
      out.write("\t<link href=\"assets/css/littleSproutsStyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("\t<style>\r\n");
      out.write("\t.error {\r\n");
      out.write("\t\tcolor: #ff0000;\r\n");
      out.write("\t\tfont-size: 0.9em;\r\n");
      out.write("\t\tfont-weight: bold;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t.errorblock {\r\n");
      out.write("\t\tcolor: #000;\r\n");
      out.write("\t\tbackground-color: #ffEEEE;\r\n");
      out.write("\t\tborder: 3px solid #ff0000;\r\n");
      out.write("\t\tpadding: 8px;\r\n");
      out.write("\t\tmargin: 16px;\r\n");
      out.write("\t}\r\n");
      out.write("\t</style>\r\n");
      out.write("\t<!-- Latest compiled and minified CSS -->\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- jQuery library -->\r\n");
      out.write("\t<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- Latest compiled JavaScript -->\r\n");
      out.write("\t<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<nav class=\"navbar navbar-inverse\">\r\n");
      out.write("\t  <div class=\"container-fluid\">\r\n");
      out.write("\t    <div class=\"navbar-header\">\r\n");
      out.write("\t      <a class=\"navbar-brand\" href=\"index.jsp\">Little Sprouts</a>\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t    <div>\r\n");
      out.write("\t      <ul class=\"nav navbar-nav\">\r\n");
      out.write("\t        <li><a href=\"mdashboard.html\">Dashboard</a></li>\r\n");
      out.write("\t        <li class=\"active\"><a href=\"mrequests.html\">Requests</a></li>\r\n");
      out.write("\t        <li><a href=\"maccounts.html\">Accounts</a></li>\r\n");
      out.write("\t      </ul>\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t  </div>\r\n");
      out.write("\t</nav>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container-fluid\" align=\"center\">\r\n");
      out.write("\t\t<form action=\"login.html\">\r\n");
      out.write("\t\t<input type=\"image\" style=\"float:right\" src=\"assets/img/logout.jpg\" width=\"50\" height=\"50\" alt=\"logout\" />\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\r\n");
      out.write("\t\t<table border=\"1\" align=\"right\" style=\"width:25%\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t    \t<td>**/** - **/**</td>\r\n");
      out.write("\t\t        <td><input type=\"image\" src=\"assets/img/leftarrow.jpg\" name=\"leftarrow\" width=\"20px\" height=\"20px\"  /></td>\r\n");
      out.write("\t\t        <td><input type=\"image\" src=\"assets/img/rightarrow.png\" width=\"20\" height=\"20\" alt=\"rightarrow\" /></td>\r\n");
      out.write("\t\t    </tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t<br />\r\n");
      out.write("\t\t<table border=\"1\" align=\"center\" style=\"width:100%\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t        <td height=\"500\" align=\"center\" valign=\"middle\" style=\"wifth:75%\">Requests</td>\r\n");
      out.write("\t\t  </tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
