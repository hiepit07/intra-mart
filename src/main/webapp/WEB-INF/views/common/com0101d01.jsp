<%--
  ファイル名:com0101d01.jsp
  画面名:ログイン

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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
	<meta charset="utf-8"/>
	<title>ログイン</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/com/com0101d01.css" />
	<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/lib/jstorage.min.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/com/systemCommon.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/com/inputChkJucUri.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/com/inputChkCom.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/com/com0101d01.js" />" type="text/javascript"></script>
</head>
<body>
	<div id="index">
		<div class="login_content">
			<form:form action="proc" method="POST" modelAttribute="formCom0101d01">
				<input type="hidden" value="1"></input>
				<fieldset>
					<br /><br />
					<div align="center"> <img src="${pageContext.request.contextPath}/resources/img/img_login_logo.PNG" width="600" height="70" />
					</div>
					<div class="login_field_container">
						<table align="center">
							<tr>
								<td class="align_right">担当者コード</td>
								<td><form:input id="userid" path="userid" maxlength="8" tabindex="1"/></td>
							</tr>
							<tr>
								<td class="align_right">パスワード</td>
								<td>
									<form:password path="password" id="password" maxlength="20" tabindex="2"/>
									<form:hidden id="focusPass" path="focusPass"/>
								</td>
							</tr>
							<tr><td colspan="2" class="height3"></td></tr>
							<tr>
								<td colspan="2">
									<div class="checkbox-1a">
										<form:checkbox id="chkIdRemainder" path="chkIdRemainder"/>
										<label for="chkIdRemainder">担当者コードを保存する</label>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="btn_login_area">
						<button id="btnLogin" class="btn_login" type="button">ログイン</button>
						<input id="btnLoginSubmit" type="submit" name="login" class="visibility-hidden">
					</div>
				</fieldset>
				<div class="error_msg"><span id="errMsg">${errMsg}</span></div>
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
			</form:form>
		</div>
		<br />
		<div class="login_footer">Copyright&copy; DAITOKU CO.,LTD. All rights reserved.</div>
	</div>
</body>
</html>