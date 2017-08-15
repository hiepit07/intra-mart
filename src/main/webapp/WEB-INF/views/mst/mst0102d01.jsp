<!DOCTYPE html>
<%--
  ファイル名:mst0102d01.jsp
  画面名:得意先マスタ登録画面

  作成者:ABV）コアー
  作成日:2015/09/21
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/21 1.00                  ABV）コアー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0102d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0102d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0102d01" id="formMst0102d01">	
			<div id="wrapper">
				<!--page note output -->
				<div id="messError" class="mst messError_error">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">
								${errorMessages.messageContent}<br/>
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
										<td class="align_right">事業所</td>
										<td class="td_width3"></td>
										<td colspan="7">
											<form:select id="ddlJigyouSho" path="ddlJigyouSho" class="cbx_width185">
		      									<form:options items="${arrListJigyouSho}" itemLabel="name" itemValue="code" />
		   									</form:select>
										</td>
									</tr>
									<tr>
										<td class="align_right">得意先コード</td>
										<td class="td_width3"></td>
										<td>
											<form:input id="txtCustomerCode" path="txtCustomerCode" class="txt_width135" type="text" maxlength="7" />
										</td>
										<td class="align_right">得意先名称</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<form:input id="txtCustomerName" path="txtCustomerName" class="txt_width240 imeActive" type="text" maxlength="20" />
											<span>※あいまい検索</span>
										</td>
									</tr>
									<tr>
										<td class="align_right">チェーンコード</td>
										<td class="td_width3"></td>
										<td colspan="7">
											<form:input id="txtChainCode" path="txtChainCode" class="txt_width65" type="text" maxlength="4" />
											<form:input id="txtChainEda" path="txtChainEda" class="txt_width45 ml17" type="text" maxlength="2" />
											<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btnSearchChainCode" alt="検索">
											<span id="txtChainName" class="color_blue"></span>
											<form:hidden path="txtChainName"/>
										</td>
									</tr>
									<tr>
										<td class="align_right">営業担当者コード</td>
										<td class="td_width3"></td>
										<td>
											<form:input id="txtEigyouTantoushaCode" path="txtEigyouTantoushaCode" class="txt_width135" type="text" maxlength="8" />
											<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btnSearchEigyouTantoushaCode" alt="検索">
											<span id="txtEigyouTantoushaName" class="color_blue"></span>
											<form:hidden path="txtEigyouTantoushaName"/>
										</td>
										<td class="align_right">事務担当者コード</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<form:input id="txtJimuTantoushaCode" path="txtJimuTantoushaCode" class="txt_width135" type="text" maxlength="8" />
											<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btnSearchJimuTantoushaCode" alt="検索">
											<span id="txtJimuTantoushaName" class="color_blue"></span>
											<form:hidden path="txtJimuTantoushaName"/>
										</td>
									</tr>
									<tr>
										<td class="align_right">得意先種別</td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlCustomerType" path="ddlCustomerType" class="cbx_width139">
		      									<form:options items="${arrListCustomerType}" itemLabel="name" itemValue="code" />
		   									</form:select>
										</td>
										<td class="align_right">内税顧客区分</td>
										<td class="td_width3"></td>
										<td>
											<form:select id="ddlUchiZeiKoKyakuKubun" path="ddlUchiZeiKoKyakuKubun" class="cbx_width139">
		      									<form:options items="${arrListUchiZeiKoKyakuKubun}" itemLabel="name" itemValue="code" />
		   									</form:select>
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td>
											<form:checkbox id="chkCancelData" path="chkCancelData" value="cancelData"/>
											<label for="chkCancelData" class="color_black">取消データを対象とする</label>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_cont mt5">
								<table class="tbl_data_condition_bot">
									<tr>
										<td class="td_width110">&nbsp;</td>
										<td class="align_left td_width330">
											<form:checkbox id="chkCustomer" path="chkCustomer" value="customer"/>
											<label for="chkCustomer" class="color_black">得意先を対象とする</label>
										</td>
										<td class="td_width400">
											<form:checkbox id="chkBilling" path="chkBilling" value="billing"/>
											<label for="chkBilling" class="color_black">請求先を対象とする</label>
										</td>
										<td>
											<form:checkbox id="chkCustomerBilling" path="chkCustomerBilling" value="customerBilling"/>
											<label for="chkCustomerBilling" class="color_black">得意先かつ請求先を対象とする</label>
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
									<col class="td_width17per">
									<col class="td_width20per">
									<col class="td_width14per">
									<col class="td_width15per">
									<col class="td_width15per">
									<col class="td_width7per">
									<col class="td_width7per">
									<tr>
										<th>No</th>
										<th>チェーン</th>
										<th>得意先</th>
										<th>事業所</th>
										<th>営業担当者</th>
										<th>事務担当者</th>
										<th>得意先種別</th>
										<th>内税顧客区分</th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height360">
								<table id="tblBody" class="tbl_list">
									<col class="td_width5per">
									<col class="td_width5per">
									<col class="td_width3per">
									<col class="td_width9per">
									<col class="td_width6per">
									<col class="td_width14per">
									<col class="td_width14per">
									<col class="td_width6per">
									<col class="td_width9per">
									<col class="td_width6per">
									<col class="td_width9per">
									<col class="td_width7per">
									<col class="td_width7per">
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
						
				<!-- hidden proccess-->
				<form:hidden id="businessDate" path="businessDate" />
				<form:hidden id="viewMode" path="viewMode" />
				<form:hidden id="selectUserCode" path="selectUserCode" />
				<form:hidden id="selectChainCode" path="selectChainCode" />
				<form:hidden id="selectChainEda" path="selectChainEda" />
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="haitaDate" path="haitaDate" />
				<form:hidden id="haitaTime" path="haitaTime" />
				<form:hidden id="searchMax" path="searchMax" />	
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
				<!--end dialog-->
				<div id="tableMenu" class="sub-menu">
					<a href="#" id="view"><img src="${pageContext.request.contextPath}/resources/img/tool_view.png" height="12"/>　照会</a>
					<a href="#" id="modify"><img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12"/>　訂正</a>
					<a href="#" id="cancel"><img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12"/>　取消</a>
				</div>
			</div>		
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>