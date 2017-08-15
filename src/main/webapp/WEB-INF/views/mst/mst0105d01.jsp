<%--
  ファイル名:mst0105d01.jsp
  画面名:オンライン得意先変換マスタ登録画面

  作成者:ABV）コイー
  作成日:2015/10/01
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
 2015/10/01 1.00                  ABV）コイー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0105d01.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0105d01.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
	<form:form id="formMst0105d01" action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0105d01">
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
			<!--condition area-->
			<div class="data_conditions_area">
				<div class="data_conditions_area_cont">
					<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
					<div class="condition_cont">
						<table id="tblDataCondition" class="tbl_data_condition">
							<tr>
								<td class="align_right td_width150 pr10">オンラインセンターコード</td>
								<td class="td_width3"></td>
								<td class="td_width380 pl3">
									<form:input id="txtOnlineCenterCode" path="txtOnlineCenterCode" class="txt_width120" maxlength="13"/>
									<span id="lblCustomerNameR01" class="color_blue">${lblCustomerNameR01}</span>
								</td>
								<td class="align_right pr10">オンライン取引先コード</td>
								<td class="td_width3"></td>
								<td class="pl3">
									<form:input id="txtOnlineTorihikiCode" path="txtOnlineTorihikiCode" class="txt_width120" maxlength="12"/>
								</td>
							</tr>
							<tr>
								<td class="align_right pr10">相手取引先コード</td>
								<td class="td_width3"></td>
								<td class="td_width380 pl3">
									<form:input id="txtAtTorihikiCode" path="txtAtTorihikiCode" class="txt_width120 align_right" maxlength="12"/>
									<span id="lblCustomerNameR02" class="color_blue">${lblCustomerNameR02}</span>
								</td>
								<td colspan="2"></td>
								<td>
									<form:checkbox id="chkCancellationData" path="chkCancellationData"/>
									<label class="color_black" for="chkCancellationData">取消データを対象とする</label>
								</td>
							</tr>
						</table>
					</div>
					<div class="condition_button">
						<button id="btnSearch" class="btn_button float_left" type="button">検索</button>
						<button id="btnClear" class="btn_button float_left" type="button">条件クリア</button>
						<button id="btnNew" class="btn_button float_left" type="button">新規</button>
						<input id="btnNewSubmit" type="submit" name="btnNew" class="visibility-hidden">
						<button id="btnCsv" class="btn_button float_right mr0" type="button">CSV</button>
					</div>
				</div>
			</div>
			<div class="data_result_area clear">
				<div class="div_zentai">
					<div id="divHead" class="divHead">
						<table class="tbl_list">
							<col class="td_width4per">
							<col class="td_width11per">
							<col class="td_width13per">
							<col class="td_width13per">
							<col class="td_width58per">
							<tr>
								<th>No</th>
								<th>O/Lセンターコード</th>
								<th>O/L取引先コード</th>
								<th>相手取引先コード</th>
								<th>自社得意先</th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="divBody height500">
						<table id="tblBody" class="tbl_list">
							<col class="td_width4per">
							<col class="td_width11per">
							<col class="td_width13per">
							<col class="td_width13per">
							<col class="td_width7per">
							<col class="td_width51per">
							<tbody id="tbl_tbody">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div> <!--/content-->
		<div class="footer_area">
			<div></div>
		</div>
		<div id="tableMenu" class="sub-menu">
			<a href="#" id="viewSubMenu">
				<img src="${pageContext.request.contextPath}/resources/img/tool_view.png" height="12" /> 照会</a>
			<a href="#" id="modifySubMenu">
				<img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12" /> 訂正</a>
			<a href="#" id="deleteSubMenu">
				<img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12" /> 取消</a>
		</div>
	</div>
		<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}"
					name="defaultMessages" value="${defaultMessage.messageContent}"
					title="${defaultMessage.messageTitle}" />
		</c:forEach>
		<form:hidden id="businessDate" path="businessDate" />
		<form:hidden id="screenMode" path="screenMode"/>
		<form:hidden id="haitaDate" path="haitaDate" />
		<form:hidden id="haitaTime" path="haitaTime" />
		<form:hidden id="searchMax" path="searchMax"/>
		
		<form:hidden id="olCenterCode" path="olCenterCode"/>
		<form:hidden id="olTorihikiCode" path="olTorihikiCode"/>
		<form:hidden id="atTorihikiCode" path="atTorihikiCode"/>
		
		<form:hidden id="olTorihikiCodeNone" path="olTorihikiCodeNone"/>
		<form:hidden id="isErrorControl" path="errorControl"/>
		
		<input id="view" type="submit" name="view" style="visibility: hidden;">
		<input id="modify" type="submit" name="modify" style="visibility: hidden;">
		<input id="delete" type="submit" name="delete" style="visibility: hidden;">
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>