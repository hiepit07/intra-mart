<%--
  ファイル名:com0101d03.jsp
  画面名:パスワード変更

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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/com/com0101d03.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/com0101d03.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
	<form:form id="formCom0101d03" action="proc" method="POST" modelAttribute="formCom0101d03">
	<c:choose>
		<c:when test="${not empty infoMessages}">
			<div id="messError" class="messError_info">
				<div>
					<span id="txtMess" class="info"> 
						<c:forEach items="${infoMessages}"
							var="infoMessages" varStatus="status">${infoMessages.messageContent}<br />
						</c:forEach>
					</span>
				</div>
		</div>
		</c:when>
		<c:otherwise>
			<div id="messError" class="messError_error">
				<div>
					<span id="txtMess" class="error"> 
						<c:forEach items="${errorMessages}"
							var="errorMessages" varStatus="status">${errorMessages.messageContent}<br />
						</c:forEach>
					</span>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
		
	<div id="wrapper">
		<div class="content">
			<div class="data_conditions_area">
				<div class="data_conditions_area_cont border_none">
					<div class="condition_cont">
						<table class="tbl_data_condition">
							<tr>
								<td class="align_right td_width150">現在のパスワード</td>
								<td class="td_width3 red_line"></td>
								<td colspan="4">
									<form:password id="password" path="password" cssClass="txt_width180" maxlength="20" tabindex="1" value ="${password}"/>
								</td>
							</tr>
							<tr><td class="height3 border_dot" colspan="6"></td></tr>
							<tr><td class="height3" colspan="6"></td></tr>
							<tr>
								<td class="align_right">新しいパスワード</td>
								<td class="td_width3 red_line"></td>
								<td>
									<form:password id ="newPass" path="newPass" cssClass="txt_width180" maxlength="20" tabindex="2" value ="${newPass}"/>
								</td>
								<td class="align_right td_width200">新しいパスワード（再入力）</td>
								<td class="td_width3 red_line"></td>
								<td><form:password id="reEnterNewPass" path="reEnterNewPass" cssClass="txt_width180" maxlength="20" tabindex="3" value ="${reEnterNewPass}"/></td>
							</tr>
						</table>
					</div>
					<div class="condition_button bg_body mt10">
						<button id="btnChange" class="btn_button float_left" name="changePassword" type="button">変更する</button>
						<input id="btnChangeSubmit" type="submit" name="changePassword" class="visibility-hidden">
					</div>
				</div>
			</div>
			<div class="footer_area">
				<div></div>
			</div>
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
			<form:hidden id="haitaDate" path="haitaDate" />
			<form:hidden id="haitaTime" path="haitaTime" />
			<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
		</div>
	</div>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>