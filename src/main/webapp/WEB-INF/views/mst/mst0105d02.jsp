<%--
  ファイル名:mst0105d02.jsp
  画面名:___

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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0105d02.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0105d02.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/com0102d02.js" />"type="text/javascript"></script>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<form:form id="formMst0105d02" action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0105d02">
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
					<div class="condition_title">
						<h3>
							<span>■</span>
								<c:choose>
									<c:when test="${screenMode == 1}">新規</c:when>
									<c:when test="${screenMode == 2}">照会</c:when>
									<c:when test="${screenMode == 3}">訂正</c:when>
									<c:when test="${screenMode == 4}">取消</c:when>
								</c:choose>
							<span>
							</span>
						</h3>
					</div>
					<div class="condition_cont">
						<table class="tbl_data_condition_cont">
							<tr>
								<td class="align_right td_width150 pr10">オンラインセンターコード</td>
								<td class="td_width3 orange_line"></td>
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
								<td class="align_right td_width150 pr10">相手取引先コード</td>
								<td class="td_width3"></td>
								<td class="td_width380 pl3">
									<form:input id="txtAtTorihikiCode" path="txtAtTorihikiCode" class="txt_width120 align_right" maxlength="12"/>
									<span id="lblCustomerNameR02" class="color_blue">${lblCustomerNameR02}</span>
								</td>
								<td colspan="3"></td>
							</tr>
						</table>
					</div>
					<div class="condition_button">
						<button id="btnCreate" class="btn_button float_left" type="button">設定</button>
					</div>
				</div>
			</div>
			<c:if test="${screenMode != 4}">
			<div class="data_conditions_area mr_ml20">
				<div class="data_conditions_input_cont">
					<div class="input_area height150">
						<table class="tbl_data_condition_search">
							<tr>
								<td class="align_right td_width170 pr10">相手取引先コード</td>
								<td class="td_width3 orange_line"></td>
								<td class="td_width300 pl3">
									<form:input id="txtAtTorihikiCode02" path="txtAtTorihikiCode02" class="txt_width120 align_right" maxlength="12"/>
								</td>
								<td colspan="6"></td>
							</tr>
							<tr>
								<td class="align_right td_width170 pr10">相手店コード</td>
								<td class="td_width3 orange_line"></td>
								<td class="td_width300 pl3">
									<form:input id="txtAtTenCode"  path="txtAtTenCode" class="txt_width120" maxlength="12"/>
								</td>
								<td colspan="6"></td>
							</tr>
							<tr>
								<td colspan="12" class="height10 border_a6a6a6"></td>
							</tr>
							<tr>
								<td colspan="12" class="height10"></td>
							</tr>
							<tr>
								<td class="align_right td_width170 pr10">自社得意先コード</td>
								<td class="td_width3 orange_line"></td>
								<td class="td_width300 pl3">
									<form:input id="txtCustomerCode" path="txtCustomerCode" class="txt_width100 align_right" maxlength="7"/>
									<img class="searchIcon" id="btnSearchCusts" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
									<span id="lblCustomerNameR" class="color_blue">${lblCustomerNameR}</span>
								</td>
								<td colspan="6"></td>
							</tr>
							<tr>
								<td class="align_right td_width170 pr10">自社店舗コード</td>
								<td class="td_width3 orange_line"></td>
								<td class="td_width300 pl3">
									<form:input id="txtShopCode" class="txt_width100" path="txtShopCode" maxlength="8" />
									<img class="searchIcon" id="btnSearchStore" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
									<span id="lblShopNameR" class="color_blue">${lblShopNameR}</span>
								</td>
								<td class="align_right td_width90 pr10">納品区分</td>
								<td class="td_width3 orange_line"></td>
								<td class="td_width130 pl3">
									<form:select id="ddlDeliKb" path="ddlDeliKb" class="cbx_width90">
										<form:options items="${deliKbList}" itemLabel="name" itemValue="code" />
									</form:select>
								</td>
								<td class="align_right td_width90 pr10">状況コード</td>
								<td class="td_width3 orange_line"></td>
								<td class="pl3">
									<form:input id="txtStsCode" class="txt_width30 align_right" path="txtStsCode" maxlength="1" />
									<span id="lblStsCodeNm">（1:登録、9:取消）</span>
								</td>
							</tr>
						</table>
						<div class="button_area">
							<button id="btnAddUpdate" class="btn_button float_left ml50" type="button">
								<c:choose>
									<c:when test="${editMode == 1}">
										追加
									</c:when>
									<c:when test="${editMode == 2}">
										更新
									</c:when>
									<c:otherwise>
										追加／更新
									</c:otherwise>
								</c:choose>
							</button>
							<button id="btnClear" class="btn_button float_right mr50" type="button">クリア</button>
						</div>
					</div>
				</div>
				
			</div>
			</c:if>
			<div class="data_result_area clear">
				<div class="div_zentai">
					<div id="divHead" class="divHead">
						<table class="tbl_list">
							<col class="td_width3per">
							<col class="td_width9per">
							<col class="td_width8per">
							<col class="td_width8per">
							<col class="td_width9per">
							<col class="td_width10per">
							<col class="td_width9per">
							<col class="td_width6per">
							<col class="td_width18per">
							<col class="td_width18per">
							<tr>
								<th>No</th>
								<th>O/Lセンター</th>
								<th>O/L取引先</th>
								<th>相手取引先</th>
								<th>相手店コード</th>
								<th>自社得意先コード</th>
								<th>自社店舗コード</th>
								<th>納品区分</th>
								<th>得意先名称</th>
								<th>店舗名称</th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="divBody height340">
						<table id="tblBody" class="tbl_list">
							<col class="td_width3per">
							<col class="td_width9per">
							<col class="td_width8per">
							<col class="td_width8per">
							<col class="td_width9per">
							<col class="td_width10per">
							<col class="td_width9per">
							<col class="td_width6per">
							<col class="td_width18per">
							<col class="td_width18per">
							<tbody id="tbl_tbody">
							</tbody>
							
						</table>
					</div>
				</div>
			</div>
		</div> <!--/content-->
		<div class="footer_area bg_gray_c0c0c0">
			<div>
				<button class="btn_button float_left" id="btnRegistration" type="button">登録</button>
				<button class="btn_button float_right mr0" id="btnFooterClear" type="button">クリア</button>
			</div>
		</div>
		<div id="dialog" class="web_dialog"></div>
		<div id="overlay" class="web_dialog_overlay"></div>
		<div id="tableMenu" class="sub-menu">
			<a href="#" id="modifySubMenu">
				<img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12" /> 訂正</a>
			<a href="#" id="deleteSubMenu">
				<img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12" /> 取消</a>
		</div>
	</div>
		<form:hidden id="sysAdminFlag" path="sysAdminFlag"/>
		<form:hidden id="plantCode" path="plantCode" />
		<form:hidden id="screenMode" path="screenMode"/>
		<form:hidden id="haitaDate" path="haitaDate" />
		<form:hidden id="haitaTime" path="haitaTime" />
		<form:hidden id="isErrorControl" path="errorControl"/>
		<form:hidden id="tenCodeNone" path="tenCodeNone"/>
		<form:hidden id="olTorihikiCodeNone" path="olTorihikiCodeNone"/>
		<!-- 業務日付 -->
		<form:hidden path="businessDate" id="businessDate" />
		<form:hidden path="preOnlineCenterCode"/>
		<form:hidden path="preOnlineTorihikiCode"/>
		<form:hidden path="preAtTorihikiCode"/>
		<form:hidden path="chkCancellationData"/>
		<form:hidden id= "hCustomerNameR01" path="lblCustomerNameR01"/>
		<form:hidden id= "hCustomerNameR02" path="lblCustomerNameR02"/>
		<form:hidden id="editMode" path="editMode"/>
		<form:hidden id="errMessage" path="errMessage"/>
		<input id="eventBack" type="submit" name="eventBack" class="visibility-hidden">
		<input id="clearSubmit" type="submit" name="clear" class="visibility-hidden">
		<!-- (編集エリア) 対象行No -->
		<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}"
					name="defaultMessages" value="${defaultMessage.messageContent}"
					title="${defaultMessage.messageTitle}" />
		</c:forEach>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>