<!DOCTYPE html>
<%--
  ファイル名: get0105d00.jsp
  画面名:担当者マスタ一覧画面

  作成者:ABV）HiepTruong
  作成日:2015/10/21
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/10/21 1.00                  ABV）HiepTruong
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<link rel="Stylesheet" href="${pageContext.request.contextPath}/resources/css/get/get0105d00.css" />
<link rel="Stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/get/get0105d00.js" />"type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input"
			modelAttribute="formGet0105d00" id="formGet0105d00">
			<div id="wrapper">
				<!-- page header -->
				<!-- page title -->
				<!-- page output msg error -->
				<div id="messError" class="messError_error">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}"
								var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont ${uiComboBox_DisplayClass}">
							<div class="condition_title">
								<p>
									<span>■</span>実行条件
								</p>
							</div>
							<div class="condition_cont">
								<table class="tbl_data_condition">
									<tr class="${displayCbOfficeInfo}">
										<td class="align_right">事業所</td>
										<td class="txt_width3"></td>
										<td colspan="7"><form:select id="ddlJigyouSho"
												path="ddlJigyouSho" class="cbx_width185">
												<form:options items="${uiOfficeinfo}" itemLabel="name"
													itemValue="code" />
											</form:select></td>
									</tr>
									<tr>
										<td class="align_right td_width100">売掛月度</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input id="urikakeMonth"
												class="txt_width60  align_left" type="text"
												path="urikakeMonth" maxlength="6"
												value="${uiAccountMonthly}" /> <span class="pl20">YYYYMM</span>
										</td>
									</tr>
									<tr>
										<td class="align_right td_width100">請求先コード</td>
										<td class="td_width3"></td>
										<td class="td_width550"><form:input id="txtCustCd"
												path="txtCustCd"
												class="td_width100 billingCodeInput" type="text"
												maxlength="7" value="${uiBillingCode}" /> <img
											class="searchIcon cursor_pointer" id="btn_search_customer_id"
											alt="検索"
											src="${pageContext.request.contextPath}/resources/css/images/search.png">
											<span id="seikyusakiname" class="color_blue pl20"></span></td>
										<td class="align_right td_width100">営業担当者</td>
										<td class="td_width3"></td>
										<td><form:input class="td_width100"
												type="text" id="txtSaleUserCd" path="txtSaleUserCd"
												maxlength="8" value="${uiUserId}" /> <img
											class="searchIcon cursor_pointer ${uiUserIdSearchBtn_DisplayClass}"
											id="btn_search_customer" alt="検索"
											src="${pageContext.request.contextPath}/resources/css/images/search.png"><span
											id="txtSaleUserNm" class="color_blue pl20">${uiloginUserName}</span></td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnCSV" class="btn_button float_left mr5 "
									type="button">CSV出力</button>
							</div>
						</div>
					</div>
				</div>
				<!--/content-->
				<div class="footer_area"></div>					
			</div>
			<form:input id="seikyu" type="hidden" path="seikyu" />
			<form:input id="tantoshaName" type="hidden" path="tantoshaName" />
			<!-- hidden proccess-->
			<div class="display_none">
				<input id="uiInitFocus" type="hidden" value="${uiInitFocus}" /> 
				<input id="businessDateID" type="hidden" value="${businessDate}" />
				<div id="haitaDate" class="display_none">${haitaDate}</div>
				<div id="haitaTime" class="display_none">${haitaTime}</div>
				<div id="isEnableBtnConfirm" class="display_none">${isEnableBtnConfirm}</div>
				<form:input id="businessDate" type="hidden" path="businessDate" />
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="isDisableAll" path="isDisableAll" />
				<form:hidden id="ddlJigyouShoName" path="ddlJigyouShoName" />
				<!-- 最大表示件数 -->
				<!-- default message start -->
			</div>
			<c:forEach items="${defaultMessages}" var="defaultMessage"
				varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}"
					name="defaultMessages" value="${defaultMessage.messageContent}"
					title="${defaultMessage.messageTitle}" />
			</c:forEach>
			<div id="overlay" class="web_dialog_overlay"></div>
    	<div id="dialog" class="web_dialog"></div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>