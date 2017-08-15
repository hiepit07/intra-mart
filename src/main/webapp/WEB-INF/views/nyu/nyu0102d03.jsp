<!DOCTYPE html>
<%--
  ファイル名:nyu0102d03.jsp
  画面名:都度請求未回収設定

  作成者:都築電気
  作成日:2015/12/02
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/12/02 1.00                  ACT)ANZAI       新規作成
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ page import="jp.co.daitoku_sh.dfcm.common.util.DateUtil" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/nyu/nyu0102d03.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/nyu/nyu0102d03.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="FormNyu0102d03" id="FormNyu0102d03">
			
			<div id="wrapper">
				
				<!-- エラー表示部 -->
				<c:choose>
					<c:when test="${FormNyu0102d03.msgErrorLevel}">
						<%-- エラー --%>
						<div id="messError" class="messError_error">
					</c:when>
					<c:when test="${FormNyu0102d03.msgWarningLevel}">
						<%-- 警告 --%>
						<div id="messError" class="messError_warning">
					</c:when>
					<c:when test="${FormNyu0102d03.msgInfoLevel}">
						<%-- 情報 --%>
						<div id="messError" class="messError_info">
					</c:when>
					<c:otherwise>
						<%-- 何もなし --%>
						<div id="messError">
					</c:otherwise>
				</c:choose>
					<div>
						<span id="txtMess"> 
							<c:forEach items="${errorMessages}"
								var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				
				<%-- content start --%>
				<div class="content">
					
					<!-- ヘッダ部 -->
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
							<div class="condition_cont">
								<table class="tbl_data_condition">
									
									<c:if test="${FormNyu0102d03.sysAdminFlag}">
										<tr>
											<td class="align_right"><span id="ddlShozokuLabel">事業所</span></td>
											<td class="td_width3 orange_line"></td>
											<td>
												<c:choose>
													<c:when test="${FormNyu0102d03.notFoundFlag}">
														<form:select id="selectedJigyoshoCd" path="selectedJigyoshoCd" class="cbx_width150">
															<form:options items="${JigyoshoList}" itemLabel="name" itemValue="code" />
														</form:select>
													</c:when>
													<c:otherwise>
														<form:select id="selectedJigyoshoCd" disabled="true" path="selectedJigyoshoCd" class="cbx_width150">
															<form:options items="${JigyoshoList}" itemLabel="name" itemValue="code" />
														</form:select>
													</c:otherwise>
												</c:choose>
											</td>
											<td colspan="6"></td>
										</tr>
									</c:if>
									
									<tr>
										<td class="td_width100 align_right">請求先コード</td>
										<td class="td_width3"></td>
										<td class="td_width635">
										
											<c:choose>
												<c:when test="${FormNyu0102d03.notFoundFlag}">
													<form:input type="text" class="txt_width100 align_right" path="seikyusakiCd" id="seikyusakiCd" maxlength="7" />
												</c:when>
												<c:otherwise>
													<form:input type="text" disabled="true" class="txt_width100 align_right" path="seikyusakiCd" id="seikyusakiCd" maxlength="7" />
												</c:otherwise>
											</c:choose>
											<img class="searchIcon" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
											<span id="dispSeikyusakiName" class="color_blue">${FormNyu0102d03.seikyusakiName}</span>
											<%-- HIDDEN --%>
											<form:hidden id="seikyusakiName" path="seikyusakiName" />
										</td>
										<td colspan="6"></td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<c:choose>
									<c:when test="${FormNyu0102d03.notFoundFlag}">
										<button id="btnSearch" name = "btnSearch" class="btn_button float_left" type="button">表示</button>
									</c:when>
									<c:otherwise>
										<button id="btnSearch" disabled="true" name = "btnSearch" class="btn_button_disabled float_left" type="button">表示</button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					
					<!-- 明細部 -->
					<c:if test="${FormNyu0102d03.showListFlag}">
						<div class="data_result_area clear">
							<div class="div_zentai">
								
								<div id="divHead" class="divHead">
									<table class="tbl_list">
										<col class="td_width3per">
										<col class="td_width4per">
										<col class="td_width33per">
										<col class="td_width10per">
										<col class="td_width10per">
										<col class="td_width10per">
										<col class="td_width10per">
										<col class="td_width10per">
										<col class="td_width10per">
										<tr>
											<th>No</th>
											<th>選択<br><input id="selectCheckBox" type="checkbox"></th>
											<th>請求先</th>
											<th>事業所</th>
											<th>伝票No</th>
											<th>納品日</th>
											<th>売上計上日</th>
											<th>請求金額</th>
											<th>未回収残</th>
										</tr>
									</table>
								</div>
								
								<c:choose>
									<c:when test="${FormNyu0102d03.notFoundFlag}">
										<%-- 検索結果0件 --%>
										<div id="divBody" class="divBody height475 align_center tbl_list">
											${notFoundMessage}
										</div>
									</c:when>
									<c:otherwise>
										<div id="divBody" class="divBody height475">
											<table id="tblBody" class="tbl_list">
												<col class="td_width3per">
												<col class="td_width4per">
												<col class="td_width6per">
												<col class="td_width27per">
												<col class="td_width10per">
												<col class="td_width10per">
												<col class="td_width10per">
												<col class="td_width10per">
												<col class="td_width10per">
												<col class="td_width10per">
												
												<%-- START 請求先情報ループ --%>
												<c:forEach items="${FormNyu0102d03.nyuSeikyusakiList}" var="seikyusakiInfo" varStatus="status">
												
													<tr>
														<%-- No --%>
														<td class="align_right">
															${status.count}
															
															<%-- ▽▽▽ HIDDEN ▽▽▽ --%>
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].chainCd"                value="${seikyusakiInfo.chainCd}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].chainIdx"               value="${seikyusakiInfo.chainIdx}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].seikyusakiCd"           value="${seikyusakiInfo.seikyusakiCd}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].seikyusakiName"         value="${seikyusakiInfo.seikyusakiName}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].seikyuTani"             value="${seikyusakiInfo.seikyuTani}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].kankeiKaishaCls"        value="${seikyusakiInfo.kankeiKaishaCls}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].kankeiKaishaHojoKamoku" value="${seikyusakiInfo.kankeiKaishaHojoKamoku}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].jigyoshoCd"             value="${seikyusakiInfo.jigyoshoCd}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].jigyoshoName"           value="${seikyusakiInfo.jigyoshoName}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].seikyuId"               value="${seikyusakiInfo.seikyuId}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].seikyuShimebi"          value="${seikyusakiInfo.seikyuShimebi}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].nyukinYoteibi"          value="${seikyusakiInfo.nyukinYoteibi}" />
															
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].dempyoNo"               value="${seikyusakiInfo.dempyoNo}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].nohinbi"                value="${seikyusakiInfo.nohinbi}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].uriageKejobi"           value="${seikyusakiInfo.uriageKejobi}" />
															
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].seikyuGaku"             value="${seikyusakiInfo.seikyuGaku}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].uriageGaku"             value="${seikyusakiInfo.uriageGaku}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].mikaishuGaku"           value="${seikyusakiInfo.mikaishuGaku}" />
															
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].uriageGakuUrikake"      value="${seikyusakiInfo.uriageGakuUrikake}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].uriageGakuMishu"        value="${seikyusakiInfo.uriageGakuMishu}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].taxUrikake"             value="${seikyusakiInfo.taxUrikake}" />
															<input type="hidden" name="nyuSeikyusakiList[${status.index}].taxMishu"               value="${seikyusakiInfo.taxMishu}" />
															<%-- △△△ HIDDEN △△△ --%>
															
														</td>
														<%-- Check Box --%>
														<td class="align_center">
															<input type="checkbox" name="nyuSeikyusakiList[${status.index}].checkBoxFlag"
																				<c:if test="${seikyusakiInfo.checkBoxFlag}">checked</c:if> />
														</td>
														<%-- 請求先コード --%>
														<td class="align_right border_r_none">
															${seikyusakiInfo.seikyusakiCd}
														</td>
														<%-- 請求先名 --%>
														<td class="border_l_none">
															${seikyusakiInfo.seikyusakiName}
														</td>
														<%-- 事業所名 --%>
														<td>
															${seikyusakiInfo.jigyoshoName}
														</td>
														<%-- 伝票No --%>
														<td class="align_right">
															${seikyusakiInfo.dempyoNo}
														</td>
														<%-- 納品日 --%>
														<td>
															<fmt:formatDate value="${ DateUtil.toDate(seikyusakiInfo.nohinbi, 'yyyyMMdd')}" pattern="yyyy/MM/dd"/>
														</td>
														<%-- 売上計上日 --%>
														<td>
															<fmt:formatDate value="${ DateUtil.toDate(seikyusakiInfo.uriageKejobi, 'yyyyMMdd')}" pattern="yyyy/MM/dd"/>
														</td>
														<%-- 請求金額 --%>
														<td class="align_right">
															<fmt:formatNumber value="${seikyusakiInfo.seikyuGaku}" pattern="###,###" />
														</td>
														<%-- 未回収残 --%>
														<td class="align_right">
															<fmt:formatNumber value="${seikyusakiInfo.mikaishuGaku}" pattern="###,###" />
														</td>
													</tr>
												
												</c:forEach>
												<%-- END 請求先情報ループ --%>
												
											</table>
										</div>
									</c:otherwise>
								</c:choose>
								
							</div>
						</div>
					</c:if>
					
				</div>
				<%-- content end --%>
				
				<!-- フッタ部 -->
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						
						<c:choose>
							<c:when test="${FormNyu0102d03.notFoundFlag}">
								<button id="btnProc" disabled="true" class="btn_button_disabled float_left ml0" type="button">実行</button>
							</c:when>
							<c:otherwise>
								<button id="btnProc" class="btn_button float_left ml0" type="button">実行</button>
							</c:otherwise>
						</c:choose>
						<button id="btnClear" class="btn_button float_right mr0" type="button">クリア</button>
					</div>
				</div>
			
			</div>
			
			<!-- HIDDDEN部  -->
			<form:hidden id="btnName"         path="btnName" />
			<form:hidden id="sysAdminFlag"    path="sysAdminFlag" />
			<form:hidden id="authKbn"         path="authKbn" />
			<form:hidden id="businessDate"    path="businessDate" />
			<form:hidden id="loginJigyoshoCd" path="loginJigyoshoCd" />
			<form:hidden id="searchMax"       path="searchMax" />
			<form:hidden id="haitaDate"       path="haitaDate" />
			<form:hidden id="haitaTime"       path="haitaTime" />
			<form:hidden id="errorControl"    path="errorControl" />
			<form:hidden id="showListFlag"    path="showListFlag" />
			<form:hidden id="notFoundFlag"    path="notFoundFlag" />
			
			<!-- 検索条件 -->
			<form:hidden id="condSelectedJigyoshoCd" path="condSelectedJigyoshoCd" />
			<form:hidden id="condSeikyusakiCd"       path="condSeikyusakiCd" />
			
			<!-- サーバチェックエラー項目ID  -->
			<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
			
			<!-- default message 部 -->
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
