<!DOCTYPE html>
<%--
  ファイル名:mst0101d01.jsp
  画面名:担当者マスタ一覧画面

  作成者:ABV）コイー
  作成日:2015/08/24
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/08/24 1.00                  ABV）コイー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0101d01.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0101d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" modelAttribute="formMst0101d01" id="formMst0101d01">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError">
					<div>
						<span id="txtMess" class="error">
							<c:forEach items="${errorMessages}"
								var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title">
								<h3>
									<span>■</span>データ呼び出し条件
								</h3>
							</div>
							<div class="condition_cont">
								<table class="tbl_data_condition">
									<tr>
										<td class="align_right">
											<span id="ddlShozokuLabel">所属事業所</span>
										</td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlShozoku" path="ddlShozoku" class="cbx_width185">
												<form:options items="${ShozokuClassList}" itemLabel="name" itemValue="code" />
											</form:select>
										</td>
										<td colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right">担当者コード</td>
										<td class="td_width3"></td>
										<td>
											<form:input type="text" class="txt_width80" path="txtUserCode" id="txtUserCode" value="" maxlength="8" />
										</td>
										<td class="align_right">担当者氏名</td>
										<td></td>
										<td>
											<form:input type="text" class="txt_width160 imeActive" path="txtUserName" id="txtUserName" maxlength="20" /><span>※あいまい検索</span>
										</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td class="align_right">利用権限</td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlRiyoKengen" path="ddlRiyoKengen" class="cbx_width185" name="loadDataForRiyoKengen">
												<form:options items="${RiyoKengenClassList}" itemLabel="name" itemValue="code" />
											</form:select>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td class="align_left" colspan=3>
											<form:checkbox id="chkTorikeshiData" path="chkTorikeshiData" value="torikeshiData" />
											<label for="chkTorikeshiData">取消データを対象とする</label>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_cont mt5">
								<table class="tbl_data_condition">
									<tr>
										<td class="align_right">状態</td>
										<td class="w3"></td>
										<td>
											<form:checkbox id="chkLock" path="chkLock" value="lock" />
											<label for="chkLock">ロック中</label>
										</td>
										<td class="align_right"></td>
										<td class="w3"></td>
										<td></td>
										<td></td>
										<td class="w3"></td>
										<td></td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnSearch" class="btn_button float_left" type="button" ondblclick="return false">検索</button>
								<button id="btnConditionClear" class="btn_button float_left" type="button">条件クリア</button>
								<button id="btnNew" class="btn_button float_left" type="button">新規</button>
								<button id="btnCsv" class="btn_button float_right mr0" type="button">CSV</button>
							</div>
						</div>
					</div>
				</div>
		
				<!-- registration data result area -->
				<div class="data_result_area clear">
					<div class="div_zentai">
						<div id="divHead" class="divHead">
							<table class="tbl_list">
								<col class="td_width3per align_right">
								<col class="td_width10per">
								<col class="td_width16per">
								<col class="td_width16per">
								<col class="td_width10per">
								<col class="td_width16per">
								<col class="td_width10per">
								<col class="td_width10per">
								<col class="td_width9per">
								<tr>
									<th>No</th>
									<th>担当者コード</th>
									<th>担当者氏名</th>
									<th>利用権限</th>
									<th>所属事業所</th>
									<th>部署名</th>
									<th>電話番号</th>
									<th>FAX番号</th>
									<th>状態</th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="divBody height470">
							<table id="tblBody" class="tbl_list">
								<col class="td_width3per align_right">
								<col class="td_width10per">
								<col class="td_width16per">
								<col class="td_width16per">
								<col class="td_width10per">
								<col class="td_width16per">
								<col class="td_width10per">
								<col class="td_width10per">
								<col class="td_width9per">
								<tbody id="tbl_tbody">
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="footer_area">
					<div></div>
				</div>
		
				<!-- hidden proccess-->
				<form:input id="businessDateID" type="hidden" path="businessDate" />
				<form:input id="viewModeID" type="hidden" path="viewMode" />
				<form:input id="selectUserCodeID" type="hidden" path="selectUserCode" />
				<form:hidden id ="errorControl" path="errorControl"/>
				<form:input id="searchMax" type="hidden" path="searchMax" />
				<form:input id="haitaDate" type="hidden" path="haitaDate" />
				<form:input id="haitaTime" type="hidden" path="haitaTime" />				
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				
				<!-- 最大表示件数 -->
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
		
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
				<!--end dialog-->
				<!--menu-->
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