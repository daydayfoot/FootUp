<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%
String path = request.getContextPath();
request.setAttribute("fts",path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="icon" href="${ctx}/resources/image_new/favicon.png"  />
<%-- <link type="text/css" rel="stylesheet" href="${ctx}/resources/css_new/main.css" /> --%>
<%-- <script type="text/javascript" src="${ctx }/resources/js_new/jquery.js"></script> --%>
<%-- <script	src="${ctx}/resources/js_new/tool/validate/jquery.validate.js"></script> --%>
<%-- <script src="${ctx }/resources/js_new/main.js"></script> --%>
<%-- <script src="${ctx }/resources/js_new/IdcardValidator.js"></script> --%>
<script type="text/javascript">
var path = '${fts}';
</script>
