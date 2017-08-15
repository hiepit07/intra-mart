<!DOCTYPE html>
<%--
  ファイル名:mst0199d01.jsp
  画面名:汎用マスタ一覧画面

  作成者:ABV）QuanTran
  作成日:2015/9/23
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/9/23 1.00                  ABV）QuanTran        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0199d01.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0199d01.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
	<form:form id="formMst0199d01" action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0199d01">
	<div id="messError" class="messError_error">
		<div>
			<span id="txtMess"> 
				<c:forEach items="${errorMessages}"
					var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
				</c:forEach>
			</span>
		</div>
	</div>
	<div id="wrapper">
		<div class="content">
			<div class="data_conditions_area">
				<div class="data_conditions_area_cont">
					<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
					<div class="condition_cont">
						<table id="tblDataCondition" class="tbl_data_condition">
							<tr>
								<td class="align_right td_width50">分類</td>
								<td class="td_width3"></td>
								<td class="td_width100">
									<form:select id="category" path="category" class="cbx_width115">
										<form:options items="${categoryList}" itemLabel="name" itemValue="code" />
									</form:select>
								</td>
								<td class="align_right td_width50">区分名称</td>
								<td class="td_width3"></td>
								<td>
									<form:input id="kbNm" path="kbNm" class="txt_width400 imeActive" maxlength="100"/>
									<span>※あいまい検索</span>
								</td>
								<td class="td_width3"></td>
								<td class="td_width3"></td>
								<td class="td_width3"></td>
							</tr>								
						</table>
					</div>
					<div class="condition_button">
						<button id="btnSearch" class="btn_button float_left" type="button">検索</button>
						<button id="btnConditionClear" class="btn_button float_left" type="button">条件クリア</button>
						<button id="btnCsv" class="btn_button float_right mr0" type="button">CSV</button>
					</div>
				</div>
			</div>
			<div class="data_result_area clear">
				<div class="div_zentai">
					<div id="divHead" class="divHead">
						<table class="tbl_list">
							<col class="td_width4per">
							<col class="td_width14per">
							<col class="td_width45per">
							<col class="td_width12per">
							<col class="td_width12per">
							<col class="td_width13per">
							<tr>
								<th>No</th>
								<th>区分</th>
								<th>区分名称</th>
								<th>分類</th>
								<th>最終登録者</th>
								<th>更新日時</th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="divBody height470">
						<table id="tblBody" class="tbl_list">
							<col class="td_width4per">
							<col class="td_width14per">
							<col class="td_width45per">
							<col class="td_width12per">
							<col class="td_width6per">
							<col class="td_width6per">
							<col class="td_width13per">
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
		<c:forEach items="${defaultMessages}" var="defaultMessage"
			varStatus="status">
			<input type="hidden" id="${defaultMessage.messageCode}"
				name="defaultMessages" value="${defaultMessage.messageContent}"
				title="${defaultMessage.messageTitle}" />
		</c:forEach>
		<!--menu-->
		<div id="tableMenu" class="sub-menu">
			<a id="viewSubMenu" name="viewSubMenu" href="#"><img src="${pageContext.request.contextPath}/resources/img/tool_view.png" height="12"/>　照会</a>
			<a id="modifySubMenu" name="modifySubMenu" href="#"><img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12"/>　訂正</a>
		</div>
		<input id="view" type="submit" name="view" style="visibility: hidden;">
		<input id="modify" type="submit" name="modify" style="visibility: hidden;">
		<form:hidden id="viewModeID" path="viewMode"/>
		<form:hidden id="selectGlCodeID" path="selectGlCode"/>
		<form:hidden id="haitaDate" path="haitaDate" />
		<form:hidden id="haitaTime" path="haitaTime" />
		<form:hidden id="searchMax" path="searchMax"/>
	</div>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>