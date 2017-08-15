<!DOCTYPE html>
<%--
  ファイル名:mst0104d01.jsp
  画面名:コースマスタ一覧画面

  作成者:ABV）NhungMa
  作成日:2015/09/21
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/21 1.00                  ABV）NhungMa        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0104d01.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0104d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0104d01" id="formMst0104d01">	
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
							<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
							<div class="condition_cont">
								<table id="tblDataCondition" class="tbl_data_condition">
									<tr>
										<td class="align_right td_width90"><span id="lblJigyoCode">事業所</span></td>
										<td class="td_width3"></td>
										<td class="td_width150">
											<form:select id="ddlJigyoCode" path="ddlJigyoCode" class="cbx_width185">
			        							<form:options items="${JigyoCodeClassList}" itemLabel="name" itemValue="code" />
			     							</form:select>
										</td>
										<td colspan="7"></td>
									</tr>
									<tr>
										<td class="align_right td_width90">コースコード</td>
										<td class="td_width3"></td>
										<td class="td_width150">
											<form:input value="" maxlength="5" id="txtCourseCode" class="txt_width100" path="txtCourseCode" />	
										</td>
										<td class="align_right td_width80">コース名称</td>
										<td class="td_width3"></td>
										<td class="td_width360">
											<form:input id="txtCourseName" class="txt_width240 imeActive" path="txtCourseName" value="" maxlength="40" />
											<span>※あいまい検索</span>
										</td>
										<td class="align_right td_width50">配送区分</td>
										<td class="td_width3"></td>
										<td class="td_width230">
											<form:input id="txtHaisoKb" class="txt_width30" path="txtHaisoKb" value="" maxlength="1" />
											<span>（1:自社配送、2:宅配便）</span>
										</td>
										<td>
											<form:checkbox id="chkCancellationData" path="chkCancellationData" />
											<label for="chkCancellationData" class="color_black">取消データを対象とする</label>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnSearch" class="btn_button float_left" type="button">検索</button>
								<button id="btnConditionClear" class="btn_button float_left" type="button">条件クリア</button>
								<button id="btnNew" class="btn_button float_left" type="button">新規</button>
								<button id="btnCsv" class="btn_button float_right mr0" type="button">CSV</button>
							</div>
						</div>
					</div>
					<div class="data_result_area clear">
						<div class="div_zentai">
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width5per">
									<col class="td_width8per">
									<col class="display_none">
									<col class="td_width8per">
									<col class="td_width68per">
									<col class="td_width10per">
									<col class="display_none">
									<tr>
										<th>No</th>
										<th>事業所</th>
										<th class="display_none">事業所コード</th>
										<th>コースコード</th>
										<th>コース名称</th>
										<th>配送区分</th>
										<th class="display_none">状況コード</th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height530">
								<table id="tblBody" class="tbl_list">
									<col class="td_width5per">
									<col class="td_width8per">
									<col class="display_none">
									<col class="td_width8per">
									<col class="td_width68per">
									<col class="td_width10per">
									<col class="display_none">
									<tbody id="tbl_tbody">
									
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="footer_area">
					<div></div>
				</div>
				<!-- hidden proccess-->
				<form:input id="businessDateID" type="hidden" path="businessDate" />
				<form:input id="viewModeID" type="hidden" path="viewMode" />
				<form:hidden id="selectJigyoCodeID" path="selectJigyoCode" />
				<form:hidden id="selectCourseCodeID" path="selectCourseCode" />
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="searchMax" path="searchMax"/>
				<form:hidden id ="errorControl" path="errorControl"/>
				<form:hidden id="haitaDate" path="haitaDate" />
				<form:hidden id="haitaTime" path="haitaTime" />
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
				<div id="tableMenu" class="sub-menu">
					<a href="#" id="viewSubMenu" name="viewSubMenu"><img src="${pageContext.request.contextPath}/resources/img/tool_view.png" height="12" /> 照会</a>
					<a href="#" id="modifySubMenu" name="modifySubMenu"><img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12" /> 訂正</a>
					<a href="#" id="deleteSubMenu" name="deleteSubMenu"><img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12" /> 取消</a>
				</div>
			</div>		
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>