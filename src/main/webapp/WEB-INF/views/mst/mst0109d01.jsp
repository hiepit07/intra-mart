<!DOCTYPE html>
<%--
  ファイル名:mst0109d01.jsp
  画面名:訂正区分マスタ一覧画面

  作成者:ABV）コイー
  作成日:2015/10/20
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/10/20 1.00                  ABV）コイー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0109d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0109d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" modelAttribute="formMst0109d01" id="formMst0109d01">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
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
										<td class="align_right">
											<span id="ddlShozokuLabel">事業所</span>
										</td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlOyaShozoku" path="ddlOyaShozoku" class="cbx_width185">
												<form:options items="${OyaShozokuClassList}" itemLabel="name" itemValue="code" />
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
											<form:input type="text" class="txtCustCode" path="txtCustCode" id="txtCustCode" value="" maxlength="7" />
											<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btn_search_customer_id" alt="検索">
											<span class="color_blue" id="lblCustNmR"></span>
										</td>
										<td class="align_right">得意先名称</td>
										<td class="td_width3"></td>
										<td>
											<form:input type="text" class="txtCustNmR" path="txtCustNmR" id="txtCustNmR" maxlength="20" />
											<span>※あいまい検索</span>
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
									</tr>
									<tr>
										<td class="align_right">訂正区分</td>
										<td class="td_width3"></td>
										<td>
											<form:input type="text" class="txtCorrectKb" path="txtCorrectKb" id="txtCorrectKb" maxlength="4" />
										</td>
										<td class="align_right">数量ゼロ納品データ連携</td>
										<td class="td_width3"></td>
										<td>
											<form:input type="text" class="txtZeroDlvdatFlg" path="txtZeroDlvdatFlg" id="txtZeroDlvdatFlg" maxlength="1" />
											<span>（1:連携対象 2:連携対象外）</span>
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td>
											<form:checkbox id="chkStsCode" path="chkStsCode" value="STSCode" />
											<label for="chkStsCode" class="color_black">取消データを対象とする</label>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_cont mt5">
								<table class="tbl_data_condition_bot">
									<tr>
										<td class="td_width110">&nbsp;</td>
										<td class="align_left td_width330">
											<form:checkbox id="chkStKb" path="chkStKb" value="StKb" />
											<label for="chkStKb" class="color_black">共通設定を検索対象とする</label>
										</td>
										<td class="td_width400"></td>
										<td></td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnSearch" class="btn_button float_left" type="button">検索</button>
								<button id="btnClear" class="btn_button float_left" type="button">条件クリア</button>
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
								<col class="td_width3per">
								<col class="td_width8per">
								<col class="td_width14per">
								<col class="td_width8per">
								<col class="td_width24per">
								<col class="td_width12per">
								<col class="td_width24per">
								<col class="td_width7per">
								<tr>
									<th>No</th>
									<th>得意先コード</th>
									<th>得意先名称</th>
									<th>訂正区分</th>
									<th>訂正理由</th>
									<th>数量ゼロ納品データ連携</th>
									<th>備考</th>
									<th>状況コード</th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="divBody height470">
							<table id="tblBody" class="tbl_list">
								<col class="td_width3per">
								<col class="td_width8per">
								<col class="td_width14per">
								<col class="td_width8per">
								<col class="td_width24per">
								<col class="td_width12per">
								<col class="td_width24per">
								<col class="td_width7per">
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
				<form:hidden id="businessDate" path="businessDate" />
				<form:hidden id="custCodeNone" path="custCodeNone" />
				<form:hidden id ="errorControl" path="errorControl"/>
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="lblCustNmRHidden" path="lblCustNmRHidden" />
				<form:hidden id="viewModeID" path="viewMode" />
				<form:hidden id="searchMax" path="searchMax" />
				<form:hidden id="haitaDate" path="haitaDate" />
				<form:hidden id="haitaTime" path="haitaTime" />
				<form:hidden id="selectCorrectKb" path="selectCorrectKb" />
				<form:hidden id="selectCustCode" path="selectCustCode" />
				
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