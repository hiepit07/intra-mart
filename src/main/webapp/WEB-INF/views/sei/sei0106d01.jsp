<%--
  ファイル名:sei0106d01.jsp
  画面名:請求一覧

  作成者:ABV）QuanTran
  作成日:2015/12/08
  
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sei/sei0106d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/sei/sei0106d01.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
	<form:form id="formSei0106d01" action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formSei0106d01">
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
							<span >■</span>データ呼び出し条件
						</h3>
					</div>
					<div class="condition_cont">
						<table id="tblDataCondition" class="tbl_data_condition">
							<c:if test="${sysAdminFlag == '1'}">
								<tr>
									<td class="align_right td_width100">事業所</td>
									<td class="td_width3 orange_line"></td>
									<td colspan="4">
										<form:select id="ddlJigyoCode" path="ddlJigyoCode" class="cbx_width185 enterKey">
											<form:options items="${jigyoCodeList}" itemLabel="name" itemValue="code" />
										</form:select>
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="align_right">請求月</td>
								<td class="td_width3 orange_line"></td>
								<td>
									<form:input id="txtBildDate" path="txtBildDate" class="txt_width100 mr5 enterKey" maxlength="7"/> <span>YYYYMM</span>
								</td>
								<td class=" align_right">事務担当者</td>
								<td class="td_width3"></td>
								<td class="width550">
									<form:input id="txtJgycd" path="txtJgycd" class="txt_width100 enterKey" maxlength="8"/> 
									<img id ="btnJgycd" src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" alt="検索"> 
									<span id="lblJgycdNm" class="color_blue">${lblJgycdNm}</span>
								</td>
							</tr>
							<tr>
								<td class="align_right">締日</td>
								<td class="td_width3"></td>
								<td colspan="4">
									<form:select id="ddlJgymei" path="ddlJgymei" class="cbx_width80 enterKey">
										<form:options items="${jgymeiList}" itemLabel="name" itemValue="code" />
									</form:select>
								</td>
							</tr>
							<tr>
								<td class="align_right">請求先コード</td>
								<td class="td_width3"></td>
								<td colspan="4">
									<form:input id="txtUserCode" path="txtUserCode" class="txt_width100 enterKey"  maxlength="7"/> 
									<img id ="btnUserCode" src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" alt="検索">
									<span id="lblUserNm" class="color_blue">${lblUserNm}</span>
								</td>
							</tr>
						</table>
					</div>
					<div class="condition_button">
						<button id="btnSearch" class="btn_button float_left ml0" type="button">表示</button>
					</div>
				</div>
			</div>
			<div class="data_conditions_area">
				<div class="condition_cont">
					<table class="tbl_data_condition">
						<tr>
							<td class="align_right" colspan="3">請求一覧出力形式</td>
							<td colspan="6">
								<div class="div_option width550 align_center">
									<form:radiobutton id="rbCustCode" path="rdOutputFormat" value="0"/>
									<label for="rbCustCode" class="mr15">請求一覧表（請求先）</label> 
									<form:radiobutton id="rbCustNmR" path="rdOutputFormat" value="1"/>
									<label for="rbCustNmR">請求一覧表（得意先／店舗毎）</label>
									<form:radiobutton id="rbNo" path="rdOutputFormat" value="2"/>
									<label for="rbNo">請求一覧表（伝票毎）</label>
								</div>
							</td>
							
						</tr>
					</table>
				</div>
			</div>
			<div class="data_result_area clear mt5">
				<div class="div_zentai">
					<div id="divHead" class="divHead display_none">
						<table class="tbl_list">
							<col class="td_width5per">
							<col class="td_width5per">
							<col class="td_width5per">
							<col class="td_width27per">
							<col class="td_width9per">
							<col class="td_width9per">
							<col class="td_width9per">
							<col class="td_width9per">
							<col class="td_width11per">
							<col class="td_width11per">
							<tr>
								<th rowspan="2"><span>No</span></th>
								<th rowspan="2">選択<br /><input id="chkSelectAll" type="checkbox"></th>
								<th rowspan="2">締日</th>
								<th rowspan="2">請求先</th>
								<th colspan="3" >今回売上額</th>
								<th rowspan="2">今回請求額</th>
								<th rowspan="2">事業所</th>
								<th rowspan="2">事務担当者</th>	
							</tr>
							<tr>
								<th>税抜き金額</th>
								<th>消費税額</th>
								<th>税込み金額</th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="divBody">
						<table id="tblBody" class="tbl_list">
							<col class="td_width5per">
							<col class="td_width5per">
							<col class="td_width5per">
							<col class="td_width7per">
							<col class="td_width20per">
							<col class="td_width9per">
							<col class="td_width9per">
							<col class="td_width9per">
							<col class="td_width9per">
							<col class="td_width11per">
							<col class="td_width11per">
							<tbody id="tbl_tbody">
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div> <!--/content-->
		<div class="footer_area bg_gray_c0c0c0">
			<div>
				<button id="btnPrint" class="btn_button " type="button">印刷</button>
				<button id="btnCsv" class="btn_button  mr0" type="button">CSV出力</button>
				<button id="btnClear" class="btn_button float_right mr0 button_out" type="button">クリア</button>
			</div>
		</div>
		<c:forEach items="${defaultMessages}" var="defaultMessage"
			varStatus="status">
			<input type="hidden" id="${defaultMessage.messageCode}"
				name="defaultMessages" value="${defaultMessage.messageContent}"
				title="${defaultMessage.messageTitle}" />
		</c:forEach>
	</div>
	<div id="overlay" class="web_dialog_overlay"></div>
	<div id="dialog" class="web_dialog"></div>
	<!--  業務日付-->
	<form:hidden id="businessDate" path="businessDate"/>
	<form:hidden id="isErrorControl" path="errorControl"/>
	<form:hidden id="sysAdminFlag" path="sysAdminFlag"/>
	<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode"/>
	<form:hidden id="jigyoName" path="jigyoName"/>
	<form:hidden id="searchMax" path="searchMax"/>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>