<%--
  ファイル名:mst0108d02.jsp
  画面名:得意先品目価格マスタ

  作成者:ABV）ギア
  作成日:2015/１１/16
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/１１/16 1.00                  ABV）ギア       新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<link href="${pageContext.request.contextPath}/resources/css/mst/mst0108d02.css" rel="Stylesheet" type="text/css">		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
		
		<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
		<script src="<c:url value="/resources/js/mst/mst0108d02.js" />" type="text/javascript"></script>		
		

<tiles:insertDefinition name="defaultTemplate">
<tiles:putAttribute name="body">
	<form:form action="" method="POST" cssErrorClass="error-input"
	modelAttribute="formMst0108d02" id="formMst0108d02">
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
				<!--condition area-->
				<div class="data_conditions_area">
					<div class="data_conditions_area_cont border_none">
						<div class="condition_title"><h3>
							<span>■</span>
								<c:choose>
									<c:when test="${modeView == '1'}">
										新規登録
									</c:when>
									<c:when test="${modeView == '2'}">
										照会
									</c:when>
									<c:when test="${modeView == '3'}">
										訂正
									</c:when>
									<c:when test="${modeView == '4'}">
										取消
									</c:when>
									<c:when test="${modeView == '5'}">
										コピー
									</c:when>
								</c:choose></h3></div>
						<div class="condition_cont">
							<table id="tblNewRegistration" class="tbl_new_registration mb30 tbl_width800">
								<tr>
									<td class="align_right">得意先コード</td>
									<td class="td_width3 orange_line"></td>
									<td class="td_width540">
										<form:input class="txt_width110 align_right" type="text" path="cstCode" id="cstCode" maxlength="7" />
										<img class="searchIcon" id="btn_search_customer_id" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue" id="custNmR">${custNmR}</span>
									</td>
								</tr>
								<tr>
									<td class="align_right">品目コード</td>
									<td class="td_width3 orange_line" ></td>
									<td>
										<form:input class="txt_width110 align_right" type="text" path="itemCode" id="itemCode"  maxlength="6" />
										<img class="searchIcon" id="btn_search_item_code" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue" id="itemNmR">${itemNmR}</span>
									</td>
								</tr>
								<tr>
									<td class="align_right">販売区分</td>
									<td class="td_width3 orange_line"></td>
									<td>
										<form:select class="cbx_width94" path="masutaKubunClassList" id="MasutaKubunClassList">
											<option></option>
											<c:forEach items="${MasutaKubunClassList}" var="item"
											varStatus="list">
											${item.name}
											</c:forEach>
										</form:select>
									</td>
								</tr>
								<tr>
									<td class="align_right">適用期間</td>
									<td class="td_width3 orange_line"></td>
									<td>
										<form:input class="txt_width110 mr5 pt5 align_left" type="text" path="validFrom" id="validFrom"  maxlength="10"  />
										<img src="${pageContext.request.contextPath}/resources/css/images/calendar.gif" class="align_middle img_displaynone calendar" >
										<span class="pl10 pr10">～</span>
										<form:input class="txt_width110 mr5 pt5 align_left" type="text" path="validTo" id= "validTo" maxlength="10" />
										<img src="${pageContext.request.contextPath}/resources/css/images/calendar.gif" class="align_middle img_displaynone calendar" >
										<span >(YYYYMMDD)</span>
								  </td>
								</tr>
								<tr>
									<td class="align_right">店舗コード</td>
									<td class="td_width3"></td>
									<td>
										<form:input class="txt_width110 align_right" type="text" path="shopCode" id="shopCode" maxlength="8" />
										<img class="searchIcon" id="btn_search_shop_code" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue" id="shopNmR">${shopNmR}</span>
									</td>
								</tr>
								<tr>
									<td class="height20 border_dot" colspan="3"></td>
								</tr>
								<tr>
									<td class="height20" colspan="3"> </td>
								</tr>
								<tr>
									<td class="align_right">納品単価</td>
									<td class="td_width3 orange_line"></td>
									<td>
										<form:input class="txt_width110 align_right" type="text" path="custDeliTanka" id="custDeliTanka" maxlength="9" />
									</td>
								</tr>
								<tr>
									<td class="align_right">販売単価</td>
									<td class="td_width3 orange_line"></td>
									<td>
										<form:input class="txt_width110 align_right" type="text" path="custSellTanka"  id="custSellTanka" maxlength="6" />
									</td>
								</tr>
								<tr>
									<td class="align_right">先方仕切単価</td>
									<td class="td_width3"></td>
									<td>
										<form:input class="txt_width110 align_right" type="text" path="custBildTanka" id="custBildTanka"  maxlength="9" />
										
									</td>
								</tr>
								<tr>
									<td class="align_right">分類コード</td>
									<td class="td_width3"></td>
									<td>
										<form:input class="txt_width110 align_right" type="text" path="bunruiCode" id="bunruiCode"  maxlength="6" />
									</td>
								</tr>
								<tr>
									<td class="height20" colspan="3"></td>
								</tr>
								<tr>
									<td class = "align_right">状況コード</td>
									<td class="td_width3 orange_line"></td>
									<td>
										<form:input class="txt_width30 align_right" type="text" path="stsCode" id="stsCode" maxlength="1" />
										<span>（1:登録 9:取消）</span>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div> <!--/content-->
			<div class="footer_area bg_gray_c0c0c0">
				<div>
					<button id="btnRegister1" class="btn_button float_left" type="button">登録</button>
					<button id="btnRegisterHidden1" class="display_none" type="submit" name="Register"></button>
					<button id="btnRegisterHidden2" class="display_none" type="submit" name="Register1"></button>
					<button id="btnClear" class="btn_button float_right mr0" type="button">クリア</button>
				</div>
			</div>
			<!--DIALOG-->
			<!-- <div id="overlay" class="web_dialog_overlay"></div> 
			<div id="dialog" class="web_dialog">
			
			</div> --> <!--/dialog-->
		</div>
		<form:hidden path="viewMode" id="viewMode"/>
		<form:hidden path="sysAdminFlag" id="sysAdminFlag"/>
		<form:hidden path="loginJigyouShoCode" id="loginJigyouShoCode"/>
		<form:hidden path="bussinessDate" id="bussinessDate"/>
		<form:hidden path="errMessage" id="errMessage"/>
		<form:hidden path="masterCheckErrorFlag" id="masterCheckErrorFlag"/>
		<form:hidden id="strSelectedCustCode" path="strSelectedCustCode" />
		<form:hidden id="strSelectedItemCode" path="strSelectedItemCode" />
		<form:hidden id="strSelectedShopCode" path="strSelectedShopCode" />
		<form:hidden id="strSelectedSaleType" path="strSelectedSaleType" />
		<form:hidden id="strSelectedEndTime" path="strSelectedEndTime" />
		<form:hidden id="salesKb" path="salesKb" />
		<form:hidden id="haitaDate" path="haitaDate" />
		<form:hidden id="haitaTime" path="haitaTime" />
		<form:hidden id="flag" path="flag" />
		<form:hidden id="success" path="success" />
		<form:hidden id="pass" path="pass" />
		<form:hidden id="masterExistRecordEditFlag" path="masterExistRecordEditFlag" />
		<form:hidden id="searchCstCode" path="searchCstCode" />
		<form:hidden id="searchItemCode" path="searchItemCode" />
		<form:hidden id="searchShopCode" path="searchShopCode" />
		<form:hidden id="searchValidFrom" path="searchValidFrom" />
		<form:hidden id="searchValidTo" path="searchValidTo" />
		<form:hidden id="searchBunruiCode" path="searchBunruiCode" />
		<form:hidden id="searchcheckDay" path="searchcheckDay" />
		<form:hidden id="searchcheckCancleData" path="searchcheckCancleData" />
		<form:hidden id="searchMasutaKubunClassList" path="searchMasutaKubunClassList" />
		<form:hidden id="searchShozokuClassList" path="searchShozokuClassList" />
		<form:hidden id="errorControll" path="errorControll" />
		<form:hidden id="tenCodeNone" path="tenCodeNone" />
		<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
		<input type="hidden" id="infoMessFlag" value="${infoMessFlag}" />
		
		<c:forEach items="${defaultMessages}" var="defaultMessages"
							varStatus="status">
			<input type="hidden" id="${defaultMessages.messageCode}" 
			class="defaultMessages" name="defaultMessages"
			value="${defaultMessages.messageContent}"
			title="${defaultMessages.messageTitle}" />
		</c:forEach>
		
		<button id="btnBackHidden" type="submit" name="Return" class="display_none"></button>
</form:form>
		<div id="overlay" class="web_dialog_overlay"></div>
		<div id="dialog" class="web_dialog"></div>
</tiles:putAttribute>
</tiles:insertDefinition>