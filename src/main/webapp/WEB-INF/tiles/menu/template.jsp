<%--
  ファイル名:teamplate.jsp
  画面名: None

  作成者:ABV）QuanTran
  作成日:2015/11/09
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/11/09 1.00 ABV)QuanTran 新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<title>${screenName}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<script src="<c:url value="/resources/js/lib/jquery.cookie.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jstorage.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" /> "type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/systemCommon.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/inputChkJucUri.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/inputChkCom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/menu.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<div id="wrapper">
		<tiles:insertAttribute name="body" />
	</div>
</body>
</html>