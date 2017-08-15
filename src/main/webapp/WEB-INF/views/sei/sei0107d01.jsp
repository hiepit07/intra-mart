<%--
  ファイル名:sei0106d01.jsp
  画面名:請求一覧

  作成者:ABV）NghiaNguyen
  作成日:2015/12/15
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
 2015/12/08 1.00                  ABV）コイー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sei/sei0107d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/sei/sei0107d01.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form id="formSei0107d01" action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formSei0107d01">
	<div id="messError" class="messError_error">
		<div>
			<span id="txtMess"> 
				<c:forEach items="${errorMessages}"
					var="errorMessages" varStatus="status">${errorMessages.messageContent}<br />
				</c:forEach>
			</span>
		</div>
	</div>
	<div id="wrapper">
		<div class="content">
			<div class="data_conditions_area">
				<div class="data_conditions_area_cont">
					<div class="condition_title">
						<h3>
							<span >■</span>印刷条件
						</h3>
					</div>
					<div class="condition_cont">
						<table id="tblDataCondition" class="tbl_data_condition">
								<tr>
									<td class="align_right td_width100">請求集計表区分</td>
									<td class="td_width3 orange_line"></td>
									<td colspan="4">
										<form:select id="ddlSumBill" path="ddlSumBill" class="td_width185" tabindex="1">
											<form:options items="${ddlSumBillList}" itemLabel="name" itemValue="code" />
										</form:select>
									</td>
								</tr>
							<tr>
								<td class="align_right">請求月</td>
								<td class="td_width3 orange_line"></td>
								<td>
									<form:input id="txtBillMonth" path="txtBillMonth" class="txt_width100 mr5" maxlength="7" tabindex="2"/> <span>YYYYMM</span>
								</td>
								<td class="align_right">締め日</td>
								<td class="td_width3"></td>
								<td colspan="4">
									<form:select id="ddlClose" path="ddlClose" class="cbx_width80" tabindex="4">
										<option></option>
									</form:select>
								</td>
							</tr>
						</table>
					</div>
					<div class="condition_button">
						<button id="btnPrint" class="btn_button float_left ml0" type="button">印刷</button>
						<button id="btnCSV" class="btn_button float_left ml0" type="button">CSV出力</button>
					</div>
				</div>
			</div>
			
			
		</div> <!--/content-->
	</div>
	<form:hidden path="businessDate" id="businessDate"/>
	<!-- プロパティファイルから各事業所のコードを取得する -->
	<form:hidden path="naraJigyoCode" id="naraJigyoCode"/>
	<form:hidden path="chukyoJigyoCode" id="chukyoJigyoCode"/>
	<form:hidden path="fujinomiyaJigyoCode" id="fujinomiyaJigyoCode"/>
	<form:hidden path="utsunomiyaJigyoCode" id="utsunomiyaJigyoCode"/>
	<form:hidden path="sapporoJigyoCode" id="sapporoJigyoCode"/>
	<!-- errorControll -->
	<form:hidden path="isErrorControl" id="isErrorControl"/>
	
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>