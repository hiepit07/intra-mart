<!DOCTYPE html>
<%--
  ファイル名:mst0108d01.jsp
  画面名:得意先品目価格マスタ

  作成者:ABV）ギア
  作成日:2015/１１/01
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/１１/01 1.00                  ABV）ギア       新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


		<link href="${pageContext.request.contextPath}/resources/css/mst/mst0108d01.css" rel="Stylesheet" type="text/css">		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
		
		<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
		<script src="<c:url value="/resources/js/mst/mst0108d01.js" />" type="text/javascript"></script>		
		
<tiles:insertDefinition name="menuTemplate">
<tiles:putAttribute name="body">
	<form:form action="proc" method="POST" cssErrorClass="error-input"
	modelAttribute="formMst0108d01" id="formMst0108d01">
	
	<div id="wrapper">
		
		<!-- page output msg error -->
		<div id="messError">
			<div>
				<span id="txtMess">
				<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">
							${errorMessages.messageContent}<br />
						</c:forEach>
				</span>
			</div>
		</div>
		<div class="content">
			<div class="data_conditions_area">
				<div class="data_conditions_area_cont">
					<div class="condition_title"><p><span>■</span>データ呼び出し条件</p></div>
						<div class="condition_cont">
							<table class="tbl_data_condition">
								<tr id="first">
									<td class="align_right" id = "shozokuTitle">事業所</td>
									<td id= "shozokuline"></td>
									<td colspan="7">
										<form:select class="td_width185" path="shozokuClassList" id="ddlShozoku">
											<option></option>
											<c:forEach items="${ShozokuClassList}" var="item"
											varStatus="list">
											${item.name}
											</c:forEach>
										</form:select>
									</td>
								</tr>
								<tr>
									<td class="align_right">得意先コード</td>
									<td></td>
									<td class="td_width360">
										<form:input class="txt_width90 align_right" type="text" maxlength="7" path="cstCode" id="cstCode" />
										<img class="searchIcon" id="btn_search_customer_id02" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue" id="custNmR"></span>
									</td>
									<td class="align_right">品目コード</td>
									<td class="td_width3"></td>
									<td class="td_width265">
										<form:input class="txt_width90 align_right" type="text" maxlength="6"  path="itemCode" id = "itemCode" />
										<img class="searchIcon" id = "btn_search_customer_id04" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue" id="itemName"></span>
									</td>
									<td class="align_right">店舗コード</td>
									<td class="td_width3"></td>
									<td class="td_width265">
										<form:input class="txt_width90 align_right" type="text" maxlength="8"  path="shopCode" id="shopCode" />
										<img class="searchIcon" id= "btn_search_customer_id03" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue" id="shopNm" style="text-algin: right;"></span>
									</td>
								</tr>
								<tr>
									<td class="align_right">販売区分</td>
									<td class="td_width3"></td>
									<td>
										<form:select class="cbx_width94" path="masutaKubunClassList" id="MasutaKubunClassList">
											<option selected=selected></option>
											<c:forEach items="${MasutaKubunClassList}" var="item"
											varStatus="list">
											${item.name}
											</c:forEach>
										</form:select>
									</td>
									<td class="align_right">分類コード</td>
									<td class="td_width3"></td>
								   <td colspan="4">
										<form:input class="txt_width90 align_right" type="text" maxlength="6"  path="bunruiCode"  id="bunruiCode"/>
									</td>
								</tr>
								<tr>
									<td class="align_right">適用期間</td>
									<td class="td_width3"></td>
									<td>
										<form:input class="txt_width90 mr5  align_left" type="text" maxlength="10"  path="validFrom" id="validFrom" />
										<span class="pl10 pr10">～</span>
										<form:input class="txt_width90 mr5  align_left" type="text" maxlength="10"  path="validTo" id= "validTo" />
										<span>(YYYYMMDD)</span>
									</td>
									<td class="align_right"></td>
									<td class="td_width3"></td>
									<td>
										<form:checkbox  id="chk1"  path="checkUpdYmd" value=""/>
										<label class="color_black" for="chk1">当日訂正分のみを対象とする。</label>
									</td>
									<td colspan="3">
										<form:checkbox  id="chk2"  path="checkCancleData" value="" />
										<label class="color_black" for="chk2">取消データを対象とする</label>
									</td>
								</tr>  
							</table>
						</div>
						<div class="condition_button">
							<button class="btn_button float_left" type="button" id="searchButtonMst0108d01">検索</button>
							<button class="btn_button float_left" type="button" id="btnClear">条件クリア</button>
							<button class="btn_button float_left" type="button" id="btnNew">新規</button>
							<button class="btn_button float_right mr0" type="button" id="btnCSV">CSV</button>
						</div>
					</div>
				</div>
				<div class="data_result_area clear">
				<div class="div_zentai">
					<div class="divHead" id="divHead">
						<table class="tbl_list">
							<col class="td_width3per">	<col class="td_width16per">	<col class="td_width16per">	<col class="td_width17per">
							<col class="td_width6per">	<col class="td_width14per">	<col class="td_width7per">	<col class="td_width7per">
							<col class="td_width7per">	<col class="td_width7per">
							<tbody>
								<tr>
									<th>No</th>
									<th>得意先</th>
									<th>品目</th>
									<th>店舗</th>
									<th>販売区分</th>
									<th>適用期間</th>
									<th>納品単価</th>
									<th>販売単価</th>
									<th>先方仕切価</th>
									<th>分類コード</th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="divBody height500" id="divBody">
						<table class="tbl_list" id="tblBody">
							<col class="td_width3per">	<col class="td_width5per">	<col class="td_width11per">	<col class="td_width5per">
							<col class="td_width11per">	<col class="td_width6pertd_width6per">	<col class="td_width11per">	<col class="td_width6per">
							<col class="td_width14per">	<col class="td_width7per">	<col class="td_width7per">	<col class="td_width7per">
							<col class="td_width7per">
							<tbody>
								
							</tbody>
						</table>
						
						<form:input path="errorControll" type="hidden" id = "errorControll" />
						<form:input path="flag" type="hidden" id = "flag" />
						<form:input path="itemPriceInfo" type="hidden" id = "itemPriceInfo" />
						<form:input path="bussinessDate" type="hidden" id = "bussinessDate" />
						<form:input path="jigyouShoCode" type="hidden" id = "JigyouShoCode" />
						<form:hidden id="strSelectedCustCode" path="strSelectedCustCode" />
						<form:hidden id="strSelectedItemCode" path="strSelectedItemCode" />
						<form:hidden id="strSelectedShopCode" path="strSelectedShopCode" />
						<form:hidden id="strSelectedSaleType" path="strSelectedSaleType" />
						<form:hidden id="strSelectedEndTime" path="strSelectedEndTime" />
						<form:input path="systemAdministratorFlag" type="hidden" id = "systemAdministratorFlag" />
						<form:hidden id="viewMode" path="viewMode" />
						<form:hidden id="searchMax" path="searchMax" />
						<form:hidden id="haitaDate" path="haitaDate" />
						<form:hidden id="haitaTime" path="haitaTime" />
						<form:hidden id="salesKb" path="salesKb" />
						<form:hidden id="errorMessages" path="errorMessages" />
						<form:hidden id="tenCodeNone" path="tenCodeNone" />
						<form:hidden id="errMessage" path="errMessage" />
						<form:input path="relationMasterErrorFlag" type="hidden" id = "relationMasterErrorFlag" />
						<form:hidden id="searchShozokuClassList" path="searchShozokuClassList" />
						
						<!-- default message start -->
						
						<c:forEach items="${defaultMessages}" var="defaultMessages"
							varStatus="status">
							<input type="hidden" id="${defaultMessages.messageCode}"
								class="defaultMessages" name="defaultMessages"
								value="${defaultMessages.messageContent}"
								title="${defaultMessages.messageTitle}" />
						</c:forEach>
					</div>
					<div id="tableMenu" class="sub-menu">
						<a href="#" id="copy"><img src="${pageContext.request.contextPath}/resources/img/tool_choice.png" height="12"/>　コピー</a>
						<a href="#" id="view"><img src="${pageContext.request.contextPath}/resources/img/tool_view.png" height="12"/>　照会</a>
						<a href="#" id="modify"><img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12"/>　訂正</a>
						<a href="#" id="cancel"><img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12"/>　取消</a>
					</div>
				</div>
			</div>
		</div> <!--/content-->
		<div class="footer_area">
		</div><!--footer_area bg_gray_c0c0c0-->	  
	</div><!---wrapper-->

</form:form>

		<div id="overlay" class="web_dialog_overlay"></div>
		<div id="dialog" class="web_dialog"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>