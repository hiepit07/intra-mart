<!DOCTYPE html>
<%--
  ファイル名:mst0103d01.jsp
  画面名:店舗マスタ管理画面

  作成者:ABV）TramChu
  作成日:2015/08/24
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/08/24 1.00                  ABV）TramChu        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0103d01.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0103d01.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0103d01" id="formMst0103d01">	
			<div id="wrapper">
				<!--page note output -->
				<div id="messError" class="messError_error">
				  <div>
				    <span id="txtMess">
					    <c:forEach items="${errorMessages}"
					      var="errorMessages" varStatus="status">
					      ${errorMessages.messageContent}<br />
					     </c:forEach>
				    </span>
				  </div>
				 </div>
				<div class="content">
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
							<div class="condition_cont">
								<table id="tblDataCondition" class="tbl_data_condition">
									<tr>
										<td class="align_right "><span class="${displayDdlOffice} color_black">事業所</span></td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlOffice" path="ddlOffice" class="cbx_width185 ${displayDdlOffice}">
			        							<form:options items="${lstMstSJigyo}" itemLabel="name" itemValue="code" />
			     							</form:select>	
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
									</tr>
									<tr>
										<td class="align_right">得意先コード</td>
										<td class="td_width3"></td>
										<td>
											<form:input path="txtCustomerCode" id="txtCustomerCode" class="txt_width135" type="text" value="" maxlength="7"/>
											<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon cursor_pointer" id="btn_search_customer_id" alt="検索">
											<span id="customerName"></span>
										</td>
										<td class="align_right">得意先名称</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<form:input path="txtCustomerName" id="txtCustomerName" class="txt_width240 imeActive" type="text" value="" maxlength="10"/>
											<span class="color_black">※あいまい検索</span>
										</td>
									</tr>
									<tr>
										<td class="align_right">店舗コード</td>
										<td class="td_width3"></td>
										<td>
											<form:input path="txtStoreCode" id="txtStoreCode" class="txt_width135 float_left" type="text" value="" maxlength="8"/>
											<span class="span_width27 float_left">&nbsp;</span>
											<span class="float_left" id="storeName"></span>
										</td>
										<td class="align_right">店舗名称</td>
										<td class="td_width3"></td>
										<td>
											<form:input path="txtStoreName" id="txtStoreName" class="txt_width240 imeActive" type="text" value="" maxlength="10"/>
											<span class="color_black">※あいまい検索</span>
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td>
											<form:checkbox id="chkCancelData" path="chkCancelData"/>
											<label for="chkCancelData" class="color_black">取消データを対象とする</label>
										</td>
									</tr>								
								</table>
							</div>
							<div class="condition_button">
								<button id="btnSearch" class="btn_button float_left" type="button">検索</button>
								<button id="btnConditionClear" class="btn_button float_left" type="button">条件クリア</button>
								<button id="btnNew" class="btn_button float_left" type="button">新規</button>
								<button id="btnCsv" class="btn_button_disabled float_right mr0" type="button" disabled>CSV</button>
							</div>
						</div>
					</div>
					<!-- registration data result area -->
					<div class="data_result_area clear">
						<div class="div_zentai">
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width5per">	<col class="td_width21per">	<col class="td_width21per">	<col class="td_width14per">
									<col class="td_width10per">	<col class="td_width20per">	<col class="td_width9per">
									<tr>
										<th>No</th>
										<th>得意先</th>
										<th>店舗</th>
										<th>事業所</th>
										<th>郵便番号</th>
										<th>住所</th>
										<th>電話番号</th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height430">						
								<table id="tblBody" class="tbl_list">
									<col class="td_width5per">	<col class="td_width7per">	<col class="td_width14per">	<col class="td_width7per">
									<col class="td_width14per">	<col class="td_width5per">	<col class="td_width9per">	<col class="td_width10per">
									<col class="td_width20per">	<col class="td_width9per">
									<tbody id="tbl_tbody"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div> <!--/content-->
				<div class="footer_area">
					<div></div>
				</div>
				<!-- hidden proccess-->
				<form:input id="businessDate" type="hidden" path="businessDate"/>
				<form:input id="viewModeID" type="hidden" path="viewMode"/>
				<form:input id="selectCustomerCode" type="hidden" path="selectCustomerCode"/>
				<form:input id="selectStoreCode" type="hidden" path="selectStoreCode"/>
				<form:input id="sysAdminFlag" type="hidden" path="sysAdminFlag"/>
				<form:input id="loginJigyouShoCode" type="hidden" path="loginJigyouShoCode"/>
				<form:input id="searchMax" type="hidden" path="searchMax"/>				
				<form:input id="haitaDate" type="hidden" path="haitaDate" />
				<form:input id="haitaTime" type="hidden" path="haitaTime" />
				<input id="errControl" type="hidden" value="${errControl}"/>
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
		
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
				<!--end dialog-->
				<div id="tableMenu" class="sub-menu">
					<a href="#" id="viewSubMenu">
						<img src="${pageContext.request.contextPath}/resources/img/tool_view.png" height="12" /> 照会</a>
					<a href="#" id="modifySubMenu">
						<img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12" /> 訂正</a>
					<a href="#" id="deleteSubMenu">
						<img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12" /> 取消</a>
				</div>
			</div>		
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>