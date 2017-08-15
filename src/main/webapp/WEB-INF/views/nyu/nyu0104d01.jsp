<!DOCTYPE html>
<%--
  ファイル名:nyu0104d01.jsp
  画面名:入金予定一覧

  作成者:ABV）NhungMa
  作成日:2015/12/16
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/12/16 1.00                  ABV）NhungMa       新規作成
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ page import="jp.co.daitoku_sh.dfcm.common.util.DateUtil" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/nyu/nyu0104d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/nyu/nyu0104d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formNyu0104d01" id="FormNyu0104d01">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError" class="messError_error">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status"> ${errorMessages.messageContent}<br /></c:forEach>
						</span>
					</div>
			    </div>
				<div class="content">
					<!--condition area-->
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title"><h3><span>■</span>検索条件</h3></div>
							<div class="condition_cont">
								<table class="tbl_data_condition">
									<tr>
										<td class="td_width100 align_right"><span id="lblJigyoCode">事業所</span></td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlJigyoCode" path="ddlJigyoCode" class="cbx_width185">
			        							<form:options items="${lstJigyoInfo}" itemLabel="name" itemValue="code" />
			     							</form:select>
										</td>
									</tr>
									<tr>
										<td class="td_width100 align_right">入金予定日</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<form:input id="txtPaymentDateFrom" class="txt_width85 txtDate mr5" path="txtPaymentDateFrom" type="text" maxlength="10" />
											<span class="pl10 pr10">～</span>
											<form:input id="txtPaymentDateTo" class="txt_width85 txtDate mr5" path="txtPaymentDateTo" type="text" maxlength="10" />
											<span>YYYY/MM/DD</span>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnShow" class="btn_button float_left mr30 ml0" type="button">表示</button>
							</div>
						</div>
					</div>
					<div class="data_result_area clear">
						<div class="div_zentai">
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width3per">
									<col class="td_width8per">
									<col class="td_width44per">
									<col class="td_width8per">
									<col class="td_width8per">
									<col class="td_width8per">
									<col class="td_width11per">
									<col class="td_width10per">
									<tr>
										<th>No</th>
										<th>入金予定日</th>
										<th>請求先</th>
										<th>請求額</th>
										<th>請求締日</th>
										<th>事業所</th>
										<th>営業担当</th>
										<th>入金口座</th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height465">
								<table id="tblBody" class="tbl_list">
									<col class="td_width3per">
									<col class="td_width8per">
									<col class="td_width6per">
									<col class="td_width38per">
									<col class="td_width8per">
									<col class="td_width8per">
									<col class="td_width8per">
									<col class="td_width11per">
									<col class="td_width10per">
									<tr>
										<td class="align_center">1</td>
										<td class="align_center">2014/10/10</td>
										<td class="border_r_none">0231000</td>
										<td class="border_l_none">合同会社 ○○</td>
										<td class="align_right">34,567,890</td>
										<td class="align_center">2014/10/10</td>
										<td class="align_center">奈良事業所</td>
										<td>佐藤　太郎</td>
										<td>みずほ</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						<button class="btn_button float_left mr30" type="button">印刷</button>
						<button class="btn_button float_left" type="button">CSV出力</button>
						<button class="btn_button float_right mr0" type="button">クリア</button>
					</div>
				</div>
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyoshoCode" path="loginJigyoshoCode" />
				<form:hidden id="jigyoshoCodeWk" path="jigyoshoCodeWk" />
				<form:hidden id ="errorControl" path="errorControl"/>
			</div>
			<!-- default message start -->
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>