<!DOCTYPE html>
<%--
  ファイル名:get0101d00.jsp
  画面名:担当者マスタ一覧画面

  作成者:ABV）TuanNguyen
  作成日:2015/10/07
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
 2015/08/24 1.00 ABV)TuanNguyen 新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/get/get0101d00.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="Stylesheet" href="${pageContext.request.contextPath}/resources/css/get/get0101d00.css" />
<link rel="Stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
	<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formGet0101d00" id="formGet0101d00">	
		<div id="wrapper">
			<!-- page header -->
			<!-- page output msg error -->
			<div id="messError" class="messError_error">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
			<div class="content">
				<!--condition area-->
				<div class="data_conditions_area">
					<div class="data_conditions_area_cont">
						<div class="condition_title">&nbsp;</div>
						<div class="condition_cont">
							<table class="tbl_office_monthly_fixed ">
										<tr class="${displayCbOfficeInfo}">
									<td class="align_left">事業所</td>
									<td class="td_width3"></td>
										<td colspan="7">
											<form:select id="ddlJigyouSho" path="ddlJigyouSho" class="cbx_width185">
		      									<form:options items="${UI_officeInfo}" itemLabel="name" itemValue="code" />
		   									</form:select>
										</td>
								</tr>
								<tr>
									<td class="height20" colspan="9"></td>
								</tr>
								<tr>
									<td class="align_left">確定売掛月度</td>
									<td class="td_width3"></td>
									<td><span id="lblConfirmAccMonth"
										class="color_blue weight_bold resetafterchange">${lblConfirmAccMonth}</span></td>
									<td class="align_left">処理開始時間</td>
									<td class="td_width3"></td>
									<td><span id="lblProcStartTime"
										class="color_blue resetafterchange">${lblProcStartTime}</span></td>
									<td class="align_left">処理終了時間</td>
									<td class="td_width3"></td>
									<td><span id="lblProcEndTime"
										class="color_blue resetafterchange">${lblProcEndTime}</span></td>
								</tr>
								<tr>
									<td class="height20" colspan="9"></td>
								</tr>
								<tr>
									<td class="align_left">当月売掛月度</td>
									<td class="td_width3"></td>
									<td colspan="7"><span style="display: inline-block; min-width: 60px; min-height: 18px;"
										class="color_blue weight_bold bg_lightgreen_ccffcc pl5 pr20 resetafterchange"
										id="thisMonthFixWK">${lblCurrAccMonth}</span></td>
								</tr>
								<tr>
									<td class="height20" colspan="9"></td>
								</tr>
								<tr>
									<td colspan="9" class="align_left">確定ボタンを押すと当月売掛月度が確定されます。</td>
								</tr>
							</table>
						</div>
						<div class="condition_button">
							<button id="btnConfirm" class="btn_button float_left"
								type="button" >確定</button>
						</div>
					</div>
				</div>
			</div>
			<!--/content-->
			<div class="footer_area">
				<div></div>
			</div>
			<!-- hidden proccess-->
			<input id="initfocus" type="hidden" value="${initfocus}" /> <input
				id="businessDateID" type="hidden" value="${businessDate}" />
			<div id="haitaDate" class="display_none">${haitaDate}</div>
			<div id="haitaTime" class="display_none">${haitaTime}</div>
			<div id="isEnableBtnConfirm" class="display_none">${isEnableBtnConfirm}</div>
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
			<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
			<form:hidden id="lblConfirmAccMonth" path="lblConfirmAccMonth" />
				<form:hidden id="lblProcStartTime" path="lblProcStartTime" />
			<form:hidden id="lblProcEndTime" path="lblProcEndTime" />	
			<form:hidden id="lblCurrAccMonth" path="lblCurrAccMonth" />
			<!-- 最大表示件数 -->
			<!-- default message start -->
			<c:forEach items="${defaultMessages}" var="defaultMessage"
				varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}"
					name="defaultMessages" value="${defaultMessage.messageContent}"
					title="${defaultMessage.messageTitle}" />
			</c:forEach>

		</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>