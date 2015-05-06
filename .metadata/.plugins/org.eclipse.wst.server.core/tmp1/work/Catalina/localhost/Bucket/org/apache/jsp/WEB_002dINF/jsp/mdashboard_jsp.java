package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class mdashboard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005faction;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005faction.release();
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
      out.write("\t<meta charset=\"utf-8\">\r\n");
      out.write("\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("\t<title>Little Sprouts Management Portal</title>\r\n");
      out.write("\t<link href=\"assets/css/littleSproutsStyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"//cdn.datatables.net/1.10.0/css/jquery.dataTables.css\">\r\n");
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
      out.write("\t<script type=\"text/javascript\" src=\"//cdn.datatables.net/1.10.0/js/jquery.dataTables.js\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- Latest compiled JavaScript -->\r\n");
      out.write("\t<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\tjQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {\r\n");
      out.write("\t\t\t\t\treturn {\r\n");
      out.write("\t\t\t\t\t\t\"iStart\" : oSettings._iDisplayStart,\r\n");
      out.write("\t\t\t\t\t\t\"iEnd\" : oSettings.fnDisplayEnd(),\r\n");
      out.write("\t\t\t\t\t\t\"iLength\" : oSettings._iDisplayLength,\r\n");
      out.write("\t\t\t\t\t\t\"iTotal\" : oSettings.fnRecordsTotal(),\r\n");
      out.write("\t\t\t\t\t\t\"iFilteredTotal\" : oSettings.fnRecordsDisplay(),\r\n");
      out.write("\t\t\t\t\t\t\"iPage\" : oSettings._iDisplayLength === -1 ? 0 : Math\r\n");
      out.write("\t\t\t\t\t\t\t\t.ceil(oSettings._iDisplayStart\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t/ oSettings._iDisplayLength),\r\n");
      out.write("\t\t\t\t\t\t\"iTotalPages\" : oSettings._iDisplayLength === -1 ? 0\r\n");
      out.write("\t\t\t\t\t\t\t\t: Math.ceil(oSettings.fnRecordsDisplay()\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t/ oSettings._iDisplayLength)\r\n");
      out.write("\t\t\t\t\t};\r\n");
      out.write("\t\t\t\t};\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t$(document).ready(function() {\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t$(\"#schedules\").dataTable({\r\n");
      out.write("\t\t\t\t\t\t\"bProcessing\" : true,\r\n");
      out.write("\t\t\t\t\t\t\"bServerSide\" : true,\r\n");
      out.write("\t\t\t\t\t\t\"sort\" : \"position\", \r\n");
      out.write("\t\t\t\t\t\t\"bStateSave\" : false,\r\n");
      out.write("\t\t\t\t\t\t\"iDisplayLength\" : 10,\r\n");
      out.write("\t\t\t\t\t\t\"iDisplayStart\" : 0,\r\n");
      out.write("\t\t\t\t\t\t\"sAjaxSource\" : \"mdashboard.html\",\r\n");
      out.write("\t\t\t\t\t\t\"aoColumns\" : [ {\r\n");
      out.write("\t\t\t\t\t\t\t\"mData\" : \"account_id\"\r\n");
      out.write("\t\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\t\"mData\" : \"date\"\r\n");
      out.write("\t\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\t\"mData\" : \"room_id\"\r\n");
      out.write("\t\t\t\t\t\t}, {\r\n");
      out.write("\t\t\t\t\t\t\t\"mData\" : \"time_start\"\r\n");
      out.write("\t\t\t\t\t\t},{\r\n");
      out.write("\t\t\t\t\t\t\t\"mData\" : \"time_end\"\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t]\r\n");
      out.write("\t\t\t\t\t  } );\r\n");
      out.write("\t\t\t\t} );\r\n");
      out.write("\t\t\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<nav class=\"navbar navbar-inverse\">\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"navbar-header\">\r\n");
      out.write("\t\t\t<a class=\"navbar-brand\" href=\"index.jsp\">Little Sprouts</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t<ul class=\"nav navbar-nav\">\r\n");
      out.write("\t\t\t\t<li class=\"active\"><a href=\"mdashboard.html\">Dashboard</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"mrequests.html\">Requests</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"maccounts.html\">Accounts</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</nav>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container-fluid\" align=\"center\">\r\n");
      out.write("\t\t<form action=\"login.html\">\r\n");
      out.write("\t\t\t<input type=\"image\" style=\"float: right\" src=\"assets/img/logout.jpg\"\r\n");
      out.write("\t\t\t\twidth=\"50\" height=\"50\" alt=\"logout\" />\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\r\n");
      out.write("\t\t");
      if (_jspx_meth_form_005fform_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
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

  private boolean _jspx_meth_form_005fform_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:form
    org.springframework.web.servlet.tags.form.FormTag _jspx_th_form_005fform_005f0 = (org.springframework.web.servlet.tags.form.FormTag) _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005faction.get(org.springframework.web.servlet.tags.form.FormTag.class);
    _jspx_th_form_005fform_005f0.setPageContext(_jspx_page_context);
    _jspx_th_form_005fform_005f0.setParent(null);
    // /WEB-INF/jsp/mdashboard.jsp(108,2) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_form_005fform_005f0.setAction("");
    // /WEB-INF/jsp/mdashboard.jsp(108,2) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_form_005fform_005f0.setMethod("GET");
    int[] _jspx_push_body_count_form_005fform_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_form_005fform_005f0 = _jspx_th_form_005fform_005f0.doStartTag();
      if (_jspx_eval_form_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t<legend>Little Sprouts Schedules<br><br></legend>\r\n");
          out.write("\t\t<table width=\"70%\" style=\"border: 3px;background: rgb(230, 244, 230);\"><tr><td>\r\n");
          out.write("\t\t\t<table id=\"schedules\" class=\"display\" cellspacing=\"0\" width=\"100%\">\r\n");
          out.write("\t\t        <thead>\r\n");
          out.write("\t\t            <tr>\r\n");
          out.write("\t\t                <th>Account Id</th>\r\n");
          out.write("\t\t     \t\t\t<th>Date</th>\r\n");
          out.write("\t\t     \t\t\t<th>Room</th>\r\n");
          out.write("\t\t     \t\t\t<th>Time Start</th>\r\n");
          out.write("\t\t     \t\t\t<th>Time End</th>\r\n");
          out.write("\t\t            </tr>\r\n");
          out.write("\t\t        </thead>       \r\n");
          out.write("\t\t    </table>\r\n");
          out.write("\t\t</td></tr></table>\r\n");
          out.write("\t\t");
          int evalDoAfterBody = _jspx_th_form_005fform_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_form_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_form_005fform_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_form_005fform_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_form_005fform_005f0.doFinally();
      _005fjspx_005ftagPool_005fform_005fform_0026_005fmethod_005faction.reuse(_jspx_th_form_005fform_005f0);
    }
    return false;
  }
}
