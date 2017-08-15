<!DOCTYPE html>
<%--
  ファイル名:sei0105d01.jsp
  画面名:取り纏め請求

  作成者:都築電気
  作成日:2015/11/27
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/11/27 1.00                  ACT)ANZAI       新規作成
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sei/sei0105d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/sei/sei0105d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="FormSei0105d01" id="FormSei0105d01">
		
			<div id="wrapper">
				
				<!-- エラー表示部 -->
				<c:choose>
					<c:when test="${FormSei0105d01.msgErrorLevel}">
						<%-- エラー --%>
						<div id="messError" class="messError_error">
					</c:when>
					<c:when test="${FormSei0105d01.msgWarningLevel}">
						<%-- 警告 --%>
						<div id="messError" class="messError_warning">
					</c:when>
					<c:when test="${FormSei0105d01.msgInfoLevel}">
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
				
				<!-- content start -->
				<div class="content">
				
					<!-- ヘッダ部 -->
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title condition_bg"><h3><span>■</span>実行条件</h3></div>
							<div class="condition_cont">
								<table id="tblDataCondition" class="tbl_data_condition">
									
									<c:if test="${FormSei0105d01.sysAdminFlag}">
										<tr>
											<td class="align_right"><span id="ddlShozokuLabel">事業所</span></td>
											<td class="td_width3 orange_line"></td>
											<td>
												<c:choose>
													<c:when test="${FormSei0105d01.notFoundFlag}">
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
										<td class="align_right" >請求締日</td>
										<td class="td_width3 orange_line"></td>
										<td>
											
											<c:choose>
												<c:when test="${FormSei0105d01.notFoundFlag}">
													<form:input type="text" class="txt_width100" path="seikyuShimebi" id="seikyuShimebi" maxlength="8" />
												</c:when>
												<c:otherwise>
													<form:input type="text" disabled="true" class="txt_width100" path="seikyuShimebi" id="seikyuShimebi" maxlength="8" />
												</c:otherwise>
											</c:choose>
											<!-- <img class="align_middle" src="css/images/calendar.gif">  -->
											<span>YYYYMMDD</span>
										</td>
										<td colspan="6"></td>
									</tr>
									
									<tr>
										<td class="align_right">請求先コード</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<c:when test="${FormSei0105d01.notFoundFlag}">
													<form:input type="text" class="txt_width100 align_left" path="seikyusakiCd" id="seikyusakiCd" maxlength="7" />
													<img id="btnSearchCustomer" class="searchIcon" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
												</c:when>
												<c:otherwise>
													<form:input type="text" disabled="true" class="txt_width100 align_left" path="seikyusakiCd" id="seikyusakiCd" maxlength="7" />
													<img id="btnSearchCustomer" disabled="true" class="searchIcon" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search_dis.png">
												</c:otherwise>
											</c:choose>
											<span id="dispSeikyusakiName" class="color_blue">${FormSei0105d01.seikyusakiName}</span>
											<%-- HIDDEN --%>
											<form:hidden id="seikyusakiName" path="seikyusakiName" />
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<c:choose>
									<c:when test="${FormSei0105d01.notFoundFlag}">
										<button id="btnSearch" name = "btnSearch" class="btn_button float_left ml0" type="button">表示</button>
									</c:when>
									<c:otherwise>
										<button id="btnSearch" disabled="true" name = "btnSearch" class="btn_button_disabled float_left ml0" type="button">表示</button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					
					<!-- 明細部 -->
					<c:if test="${FormSei0105d01.showListFlag}">
						<div class="data_result_area clear">
							<div class="div_zentai">
								
								<!-- 取り纏め請求先 -->
								<p class="text_request">■ 取り纏め請求先</p>
								
								<div id="divHead" class="divHead">
									<table class="tbl_list">
										<col class="td_width33per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width10per">
										<col class="td_width9per">
										<col class="td_width10per">
										
										<tr>
											<th>請求先</th>
											<th>今回売上</th>
											<th>今回入金</th>
											<th>請求残高</th>
											<th>処理状態</th>
											<th>締め処理日時</th>
											<th>事業所</th>
											<th>事務担当者</th>
											
										</tr>
									</table>
								</div>
								
								<c:choose>
									<c:when test="${FormSei0105d01.notFoundFlag}">
										<%-- 検索結果0件 --%>
										<div id="divBody" class="divBody height100 align_center tbl_list">
											${notFoundMessage}
										</div>
									</c:when>
									<c:otherwise>
										
										<div id="divBody" class="divBody height100">
											<table id="tblBody" class="tbl_list">
												<col class="td_width6per">
												<col class="td_width27per">
												<col class="td_width7per">
												<col class="td_width7per">
												<col class="td_width7per">
												<col class="td_width7per">
												<col class="td_width10per">
												<col class="td_width9per">
												<col class="td_width10per">
												<tr>
													<%-- 請求先コード --%>
													<td class="align_right border_r_none">
														${FormSei0105d01.torimatomeInfo.seikyusakiCd}
														
														<%-- ▽▽▽ HIDDEN ▽▽▽ --%>
														<form:hidden id="torimatomeInfo.seikyuId"           path="torimatomeInfo.seikyuId" />
														<form:hidden id="torimatomeInfo.chainCode"          path="torimatomeInfo.chainCode" />
														<form:hidden id="torimatomeInfo.chainIdx"           path="torimatomeInfo.chainIdx" />
														<form:hidden id="torimatomeInfo.seikyusakiCd"       path="torimatomeInfo.seikyusakiCd" />
														<form:hidden id="torimatomeInfo.seikyusakiNameR"    path="torimatomeInfo.seikyusakiNameR" />
														<form:hidden id="torimatomeInfo.seikyuShimebi"      path="torimatomeInfo.seikyuShimebi" />
														<form:hidden id="torimatomeInfo.seikyuShimeNichiji" path="torimatomeInfo.seikyuShimeNichiji" />
														<form:hidden id="torimatomeInfo.jigyoshoCd"         path="torimatomeInfo.jigyoshoCd" />
														<form:hidden id="torimatomeInfo.jigyoshoName"       path="torimatomeInfo.jigyoshoName" />
														<form:hidden id="torimatomeInfo.tantoshaCd"         path="torimatomeInfo.tantoshaCd" />
														<form:hidden id="torimatomeInfo.tantoshaName"       path="torimatomeInfo.tantoshaName" />
														
														<form:hidden id="torimatomeInfo.taxKeisanTani"      path="torimatomeInfo.taxKeisanTani" />
														<form:hidden id="torimatomeInfo.taxHasuShori"       path="torimatomeInfo.taxHasuShori" />
														<form:hidden id="torimatomeInfo.seikyushoCls"       path="torimatomeInfo.seikyushoCls" />
														<form:hidden id="torimatomeInfo.seikyuDataKbn"      path="torimatomeInfo.seikyuDataKbn" />
														
														<form:hidden id="torimatomeInfo.konakiUriageFlag"   path="torimatomeInfo.konakiUriageFlag" />
														<form:hidden id="torimatomeInfo.konkaiNyukinFlag"   path="torimatomeInfo.konkaiNyukinFlag" />
														<form:hidden id="torimatomeInfo.seikyuZanFlag"      path="torimatomeInfo.seikyuZanFlag" />
														<form:hidden id="torimatomeInfo.shimeSumiFlag"      path="torimatomeInfo.shimeSumiFlag" />
														<form:hidden id="torimatomeInfo.nyukinSumiFlag"     path="torimatomeInfo.nyukinSumiFlag" />
														<%-- △△△ HIDDEN △△△ --%>
														
													</td>
													<%-- 請求先名 --%>
													<td class="border_l_none">
														${FormSei0105d01.torimatomeInfo.seikyusakiNameR}
													</td>
													<%-- 今回売上 --%>
													<td class="align_left">
														<c:choose>
															<c:when test="${FormSei0105d01.torimatomeInfo.konakiUriageFlag}">
																有
															</c:when>
															<c:otherwise>
																&nbsp;
															</c:otherwise>
														</c:choose>
													</td>
													<%-- 今回入金 --%>
													<td class="align_left">
														<c:choose>
															<c:when test="${FormSei0105d01.torimatomeInfo.konkaiNyukinFlag}">
																有
															</c:when>
															<c:otherwise>
																&nbsp;
															</c:otherwise>
														</c:choose>
													</td>
													<%-- 請求残高 --%>
													<td class="align_left">
														<c:choose>
															<c:when test="${FormSei0105d01.torimatomeInfo.seikyuZanFlag}">
																有
															</c:when>
															<c:otherwise>
																&nbsp;
															</c:otherwise>
														</c:choose>
													</td>
													<%-- 処理状態 --%>
													<td>
														<c:choose>
															<c:when test="${!(empty FormSei0105d01.torimatomeInfo.seikyuId)}">
																済
															</c:when>
															<c:otherwise>
																&nbsp;
															</c:otherwise>
														</c:choose>
													</td>
													<%-- 締め処理日時 --%>
													<td>
														<fmt:formatDate value="${ DateUtil.toDate(FormSei0105d01.torimatomeInfo.seikyuShimeNichiji, 'yyyyMMddHHmmss')}" pattern="yyyy/MM/dd HH:mm"/>
													</td>
													<%-- 事業所 --%>
													<td>
														${FormSei0105d01.torimatomeInfo.jigyoshoName}
													</td>
													<%-- 事務担当者 --%>
													<td>
														${FormSei0105d01.torimatomeInfo.tantoshaName}
													</td>
												</tr>
											</table>
										</div>
										
									</c:otherwise>
								</c:choose>
								
								<!-- <div class="row_height"></div> -->
								
								<!-- 事業所締状況 -->
								<p class="text_request">■ 事業所締状況</p>
								
								<div id="divHead1" class="divHead">
									<table class="tbl_list">
										<col class="td_width6per">
										<col class="td_width27per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width10per">
										<col class="td_width9per">
										<col class="td_width10per">
										<tr>
											<th>NO</th>
											<th>請求先</th>
											<th>今回売上</th>
											<th>今回入金</th>
											<th>請求残高</th>
											<th>処理状態</th>
											<th>締め処理日時</th>
											<th>事業所</th>
											<th>事務担当者</th>
										</tr>
									</table>
								</div>
								
								<c:choose>
									<c:when test="${FormSei0105d01.notFoundFlag}">
										<%-- 検索結果0件 --%>
										<div id="divBody" class="divBody height250 align_center tbl_list">
										</div>
									</c:when>
									<c:otherwise>
									
										<div id="divBody1" class="divBody height250">
											<table id="tblBody1" class="tbl_list">
												<col class="td_width6per">
												<col class="td_width6per">
												<col class="td_width21per">
												<col class="td_width7per">
												<col class="td_width7per">
												<col class="td_width7per">
												<col class="td_width7per">
												<col class="td_width10per">
												<col class="td_width9per">
												<col class="td_width10per">
												
												<%-- START 請求先情報ループ --%>
												<c:forEach items="${FormSei0105d01.seikyusakiInfoList}" var="seikyusakiInfo" varStatus="status">
												
													<tr>
														<%-- No --%>
														<td class="align_right">
															${status.count}
															
															<%-- ▽▽▽ HIDDEN ▽▽▽ --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].chainCode"                  value="${seikyusakiInfo.chainCode}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].chainIdx"                   value="${seikyusakiInfo.chainIdx}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].seikyusakiCd"               value="${seikyusakiInfo.seikyusakiCd}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].seikyusakiNameR"            value="${seikyusakiInfo.seikyusakiNameR}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].jigyoshoCd"                 value="${seikyusakiInfo.jigyoshoCd}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].jigyoshoName"               value="${seikyusakiInfo.jigyoshoName}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].tantoshaCd"                 value="${seikyusakiInfo.tantoshaCd}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].tantoshaName"               value="${seikyusakiInfo.tantoshaName}" />
															<%-- 得意先詳細情報 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].seikyuDataKbn"              value="${seikyusakiInfo.seikyuDataKbn}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].taxKeisanTani"              value="${seikyusakiInfo.taxKeisanTani}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].taxHasuShori"               value="${seikyusakiInfo.taxHasuShori}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].seikyushoTanka"             value="${seikyusakiInfo.seikyushoTanka}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].tokuisakiCls"               value="${seikyusakiInfo.tokuisakiCls}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].seikyushoCls"               value="${seikyusakiInfo.seikyushoCls}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].kaishuTsukiKbn"             value="${seikyusakiInfo.kaishuTsukiKbn}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].kaishubi"                   value="${seikyusakiInfo.kaishubi}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].torimatomeJigyoshoCd"       value="${seikyusakiInfo.torimatomeJigyoshoCd}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].seikyuSoshinId"             value="${seikyusakiInfo.seikyuSoshinId}" />
															<%-- 前回請求額 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].zenkaiSeikyuGakuUrikake"    value="${seikyusakiInfo.zenkaiSeikyuGakuUrikake}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].zenkaiSeikyuGakuMishu"      value="${seikyusakiInfo.zenkaiSeikyuGakuMishu}" />
															<%-- 今回売上 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiUriageUrikake"        value="${seikyusakiInfo.konkaiUriageUrikake}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiUriageMishu"          value="${seikyusakiInfo.konkaiUriageMishu}" />
															<%-- 今回入金 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiNyukinUrikake"        value="${seikyusakiInfo.konkaiNyukinUrikake}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiNyukinMishu"          value="${seikyusakiInfo.konkaiNyukinMishu}" />
															<%-- 今回相殺 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiSosaiUrikake"         value="${seikyusakiInfo.konkaiSosaiUrikake}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiSosaiMishu"           value="${seikyusakiInfo.konkaiSosaiMishu}" />
															<%-- 今回調整 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiChoseiUrikake"        value="${seikyusakiInfo.konkaiChoseiUrikake}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiChoseiMishu"          value="${seikyusakiInfo.konkaiChoseiMishu}" />
															<%-- 繰越額 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].kurikoshiGakuUrikake"       value="${seikyusakiInfo.kurikoshiGakuUrikake}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].kurikoshiGakuMishu"         value="${seikyusakiInfo.kurikoshiGakuMishu}" />
															<%-- レコード数 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].urikakeMSu"                 value="${seikyusakiInfo.urikakeMSu}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].mishuMSu"                   value="${seikyusakiInfo.mishuMSu}" />
															<%-- 今回請求 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiSeikyuId"             value="${seikyusakiInfo.konkaiSeikyuId}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiSeikyuShimeNichiji"   value="${seikyusakiInfo.konkaiSeikyuShimeNichiji}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiSeikyuDataRenkeiFlag" value="${seikyusakiInfo.konkaiSeikyuDataRenkeiFlag}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].konkaiSeikyuShimeKbn"       value="${seikyusakiInfo.konkaiSeikyuShimeKbn}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].nyukinKakuninFlag"          value="${seikyusakiInfo.nyukinKakuninFlag}" />
															<%-- 前回請求 --%>
															<input type="hidden" name="seikyusakiInfoList[${status.index}].zenkaiSeikyuId"             value="${seikyusakiInfo.zenkaiSeikyuId}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].zenkaiSeikyuShimebi"        value="${seikyusakiInfo.zenkaiSeikyuShimebi}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].zenkaiSeikyuDataRenkeiFlag" value="${seikyusakiInfo.zenkaiSeikyuDataRenkeiFlag}" />
															<input type="hidden" name="seikyusakiInfoList[${status.index}].zenkaiSeikyuShimeKbn"       value="${seikyusakiInfo.zenkaiSeikyuShimeKbn}" />
															<%-- △△△ HIDDEN △△△ --%>
															
														</td>
														<%-- 請求先コード --%>
														<td class="align_right border_r_none">
															${seikyusakiInfo.seikyusakiCd}
														</td>
														<%-- 請求先名 --%>
														<td class="border_l_none">
															${seikyusakiInfo.seikyusakiNameR}
														</td>
														<%-- 今回売上 --%>
														<td class="align_left">
															<c:choose>
																<c:when test="${seikyusakiInfo.konkaiUriageUrikake > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.konkaiUriageMishu > 0}">
																	有
																</c:when>
																<c:otherwise>
																	&nbsp;
																</c:otherwise>
															</c:choose>
														</td>
														<%-- 今回入金 --%>
														<td class="align_left">
															<c:choose>
																<c:when test="${seikyusakiInfo.konkaiNyukinUrikake > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.konkaiNyukinMishu > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.konkaiSosaiUrikake > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.konkaiSosaiMishu > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.konkaiChoseiUrikake > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.konkaiChoseiMishu > 0}">
																	有
																</c:when>
																<c:otherwise>
																	&nbsp;
																</c:otherwise>
															</c:choose>
														</td>
														<%-- 請求残高 --%>
														<td class="align_left">
															<c:choose>
																<c:when test="${seikyusakiInfo.kurikoshiGakuUrikake > 0}">
																	有
																</c:when>
																<c:when test="${seikyusakiInfo.kurikoshiGakuMishu > 0}">
																	有
																</c:when>
																<c:otherwise>
																	&nbsp;
																</c:otherwise>
															</c:choose>
														</td>
														<%-- 処理状態 --%>
														<td class="align_left">
															<c:choose>
																<c:when test="${!(empty seikyusakiInfo.konkaiSeikyuId)}">
																	済
																</c:when>
																<c:otherwise>
																	&nbsp;
																</c:otherwise>
															</c:choose>
														</td>
														<%-- 締め処理日時 --%>
														<td class="align_left">
															<fmt:formatDate value="${ DateUtil.toDate(seikyusakiInfo.konkaiSeikyuShimeNichiji, 'yyyyMMddHHmmss')}" pattern="yyyy/MM/dd HH:mm"/>
														</td>
														<%-- 事業所 --%>
														<td>
															${seikyusakiInfo.jigyoshoName}
														</td>
														<%-- 事務担当者 --%>
														<td>
															${seikyusakiInfo.tantoshaName}
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
				<!-- content end -->
				
				<!-- フッタ部 -->
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						<c:choose>
							<c:when test="${FormSei0105d01.notFoundFlag}">
								<button id="btnProc"             disabled="true" class="btn_button_disabled float_left mr100"              type="button">実行</button>
								<button id="btnTorikeshi"        disabled="true" class="btn_button_disabled btn_width120 float_left mr100" type="button">取消</button>
								<button id="btnRePrintSeikyusho" disabled="true" class="btn_button_disabled btn_width120 float_left"       type="button">再印刷</button>
							</c:when>
							<c:otherwise>
								<button id="btnProc"             class="btn_button float_left mr100"              type="button">実行</button>
								<button id="btnTorikeshi"        class="btn_button btn_width120 float_left mr100" type="button">取消</button>
								<button id="btnRePrintSeikyusho" class="btn_button btn_width120 float_left"       type="button">再印刷</button>
							</c:otherwise>
						</c:choose>
						<button id="btnClear" class="btn_button float_right mr0" type="button">クリア</button>
					</div>
				</div>
				
			</div>
			
			<!-- HIDDDEN部  -->
			<form:hidden id="btnName"         path="btnName" />
			<form:hidden id="sysAdminFlag"    path="sysAdminFlag" />
			<form:hidden id="businessDate"    path="businessDate" />
			<form:hidden id="accountMonth"    path="accountMonth" />
			<form:hidden id="loginJigyoshoCd" path="loginJigyoshoCd" />
			<form:hidden id="haitaDate"       path="haitaDate" />
			<form:hidden id="haitaTime"       path="haitaTime" />
			<form:hidden id="haitaCount"      path="haitaCount" />
			<form:hidden id="errorControl"    path="errorControl" />
			<form:hidden id="showListFlag"    path="showListFlag" />
			<form:hidden id="notFoundFlag"    path="notFoundFlag" />
			
			<!-- サーバチェックエラー項目ID  -->
			<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
			
			<!-- 検索条件 -->
			<form:hidden id="condSelectedJigyoshoCd" path="condSelectedJigyoshoCd" />
			<form:hidden id="condSeikyuShimebi"      path="condSeikyuShimebi" />
			<form:hidden id="condSeikyusakiCd"       path="condSeikyusakiCd" />
			
			<!-- default message 部 -->
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
			
			<!-- DIALOG -->
			<div id="overlay" class="web_dialog_overlay"></div>
			<div id="dialog" class="web_dialog"></div>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
