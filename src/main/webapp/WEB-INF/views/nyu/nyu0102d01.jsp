<!DOCTYPE html>
<%--
  ファイル名:nyu0102d01.jsp
  画面名:入金登録

  作成者:都築電気
  作成日:2015/12/01
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/12/01 1.00                  ACT)ANZAI       新規作成
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/nyu/nyu0102d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/nyu/nyu0102d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="FormNyu0102d01" id="FormNyu0102d01">
			
			<div id="wrapper">
				
				<!-- エラー表示部 -->
				<c:choose>
					<c:when test="${FormNyu0102d01.msgErrorLevel}">
						<%-- エラー --%>
						<div id="messError" class="messError_error">
					</c:when>
					<c:when test="${FormNyu0102d01.msgWarningLevel}">
						<%-- 警告 --%>
						<div id="messError" class="messError_warning">
					</c:when>
					<c:when test="${FormNyu0102d01.msgInfoLevel}">
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
						<div class="data_conditions_area_cont border_none">
							<div class="condition_cont">
								
								<div class="option_regis_area">
								
									<div class="option_regis">
										<form:radiobuttons path="shoriKbn" items="${shoriKbns}" itemLabel="title" itemValue="value" delimiter="&nbsp;&nbsp;&nbsp;" />
									</div>
									
									<div id="divNyukinDempyoNo" class="slip_no">
										<label for="slipNo">入金伝票No</label>
										<form:input type="text" class="slipNo align_right" path="nyukinDempyoNo" id="nyukinDempyoNo" maxlength="9" />
									</div>
									
									<div id="divHeaderButtons" class="float_left mr30">
										<c:if test="${!FormNyu0102d01.showFlag}">
											<button id="btnShimeSeikyu" class="btn_button_op bg_b3a2c7 mr0" type="button">締め請求</button>
											<button id="btnTsudoSeikyu" class="btn_button_op bg_b3a2c7 mr0" type="button">都度請求</button>
										</c:if>
										<c:if test="${FormNyu0102d01.showFlag && FormNyu0102d01.shoriKbn == '1'}">
											<button id="btnShonin" class="btn_button_op bg_33cc33 mr0" type="button">承認</button>
											<button id="btnHinin"  class="btn_button_op bg_ff3399 mr0" type="button">否 認</button>
										</c:if>
									</div>
									
									<div class="slip_no mr0">
										<c:if test="${!empty FormNyu0102d01.shoninNichiji }">
											<label>承認日時</label>
											<span class="color_blue">
												<fmt:formatDate value="${ DateUtil.toDate(FormNyu0102d01.shoninNichiji, 'yyyyMMddHHmmss')}" pattern="yyyy/MM/dd HH:mm"/>
											</span>
										</c:if>
									</div>
									
								</div>
								
								<c:if test="${FormNyu0102d01.showFlag}">
									<table class="tbl_data_condition">
										<tr>
											<td class="td_width100 align_right">請求先コード</td>
											<td class="td_width3"></td>
											<td class="td_width400">
												<div class="span_width70 float_left align_right mr15 mt3">
													<span class="color_blue">${FormNyu0102d01.seikyusakiCd}</span>
												</div>
												<div class="width300 float_left mt3">
													<span class="color_blue">${FormNyu0102d01.seikyusakiName}</span>
												</div>
											</td>
											<td class="td_width100 align_right">
												<c:if test="${FormNyu0102d01.authKbn != '0'}">
													事業所
												</c:if>
											</td>
											<td class="td_width3"></td>
											<td class="td_width250">
												<div class="span_width40 float_left align_right mr15 mt3">
													<span class="color_blue">
														<c:if test="${FormNyu0102d01.authKbn != '0'}">
															${FormNyu0102d01.jigyoshoCd}
														</c:if>
													</span>
												</div>
												<div class="width185 float_left mt3">
													<span class="color_blue">
														<c:if test="${FormNyu0102d01.authKbn != '0'}">
															${FormNyu0102d01.jigyoshoName}
														</c:if>
													</span>
												</div>
											</td>
											<td class="td_width100 align_right">請求締日</td>
											<td class="td_width3"></td>
											<td>
												<span class="color_blue">
													<fmt:formatDate value="${ DateUtil.toDate(FormNyu0102d01.seikyuShimebi, 'yyyyMMdd')}" pattern="yyyy/MM/dd"/>
												</span>
											</td>
										</tr>
										<tr>
											<td class="td_width100 align_right">入金日</td>
											<td class="td_width3 orange_line"></td>
											<td colspan="4" class="td_width400">
												<form:input type="text" class="year txt_width50"  path="nyukinbiYY" id="nyukinbiYY" maxlength="4" />
												<span class="normal">/</span>
												<form:input type="text" class="month txt_width30" path="nyukinbiMM" id="nyukinbiMM" maxlength="2" />
												<span class="normal">/</span>
												<form:input type="text" class="day txt_width30"   path="nyukinbiDD" id="nyukinbiDD" maxlength="2" />
											</td>
											<td class="td_width100 align_right">入金予定日</td>
											<td class="td_width3"></td>
											<td>
												<span class="color_blue">
													<fmt:formatDate value="${ DateUtil.toDate(FormNyu0102d01.nyukinYoteibi, 'yyyyMMdd')}" pattern="yyyy/MM/dd"/>
												</span>
											</td>
										</tr>
										<tr>
											<td colspan="7" class="td_width100 align_right">遅れ理由</td>
											<td class="td_width3"></td>
											<td>
												<select class="cbx_width100">
													<option>休日の為</option>
												</select>
											</td>
										</tr>
									</table>
								</c:if>
								
							</div>
						</div>
					</div>
					
					<!-- 明細部 START -->
					<c:if test="${FormNyu0102d01.showFlag}">
						<div class="data_result_area clear">
							<div class="div_zentai">
								<!-- 明細.明細部 -->
								<div id="divHead" class="divHead">
									<table class="tbl_list">
										<col class="td_width2per">
										<col class="td_width10per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width8per">
										<col class="td_width9per">
										<col class="td_width20per">
										<col class="td_width11per">
										<col class="td_width11per">
										<col class="td_width7per">
										<col class="td_width8per">
										<tr>
											<th>No</th>
											<th>貸借区分</th>
											<th>事業所</th>
											<th>発生場所</th>
											<th>科目</th>
											<th>補助コード</th>
											<th>科目名</th>
											<th>売掛金消込金額</th>
											<th>未収金消込金額</th>
											<th>手形No</th>
											<th>手形期日</th>
										</tr>
									</table>
								</div>
								
								<div id="divBody" class="divBody height270">
									<table id="tblBody" class="tbl_list">
										<col class="td_width2per">
										<col class="td_width2per">
										<col class="td_width4per">
										<col class="td_width4per">
										<col class="td_width7per">
										<col class="td_width7per">
										<col class="td_width8per">
										<col class="td_width9per">
										<col class="td_width20per">
										<col class="td_width11per">
										<col class="td_width11per">
										<col class="td_width7per">
										<col class="td_width8per">
										
										<%-- START 明細ループ --%>
										<c:forEach items="${FormNyu0102d01.meisaiList}" var="meisaiInfo" varStatus="status">
											<tr>
												<%-- No --%>
												<td class="align_right">
													${meisaiInfo.no}
												</td>
												
												<%-- 貸借区分 --%>
												<td class="align_right">
													${meisaiInfo.taishakuCd}
												</td>
												<td>
													借方
												</td>
												<td>&nbsp;</td>
												
												<%-- 事業所 --%>
												<td>
													奈良
												</td>
												
												<%-- 発生場所 --%>
												<td>
													<select class="cbx_width100per bg_faf9e5 border_none">
														<option>0790</option>
													</select>
												</td>
												
												<%-- 科目 --%>
												<td class="align_right">
													1010101
												</td>
												
												<%-- 補助コード --%>
												<td>
													<select class="cbx_width100per bg_faf9e5 border_none">
														<option>0790</option>
													</select>
												</td>
												
												<%-- 科目名 --%>
												<td>
													現金
													
													<%-- 科目選択 --%>
													<select class="cbx_width100 border_none float_right">
														<option>一般</option>
														<option>G会社</option>
													</select>
												</td>
												
												<%-- 売掛金消込金額 --%>
												<td>
													<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="10000" maxlength="10">
												</td>
												
												<%-- 未収金消込金額 --%>
												<td>
													<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="10000" maxlength="10">
												</td>
												
												<%-- 手形No --%>
												<td>
													<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="10000" maxlength="10">
												</td>
												
												<%-- 手形期日 --%>
												<td>
													<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="10000" maxlength="10">
												</td>
												
											</tr>
										</c:forEach>
										<%-- END 明細ループ --%>
										
									</table>
								</div>
								
								<!-- 明細.行追加部 -->
								<table class="tbl_list mt10">
									<col class="td_width2per">
									<col class="td_width2per">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width8per">
									<col class="td_width9per">
									<col class="td_width20per">
									<col class="td_width11per">
									<col class="td_width11per">
									<col class="td_width7per">
									<col class="td_width8per">
									<tr>
										<th>No</th>
										<th colspan="3">貸借区分</th>
										<th>事業所</th>
										<th>発生場所</th>
										<th>科目</th>
										<th>補助コード</th>
										<th>科目名</th>
										<th>売掛金消込金額</th>
										<th>未収金消込金額</th>
										<th>手形No</th>
										<th>手形期日</th>
									</tr>
									<tr>
										<td class="align_right"></td>
										<td class="align_right">1</td>
										<td></td>
										<td>&nbsp;</td>
										<td>
											<select class="cbx_width100per border_none">
												<option></option>
											</select>
										</td>
										<td>
											<select class="cbx_width100per border_none">
												<option></option>
											</select>
										</td>
										<td><select class="cbx_width100per border_none">
												<option></option>
											</select></td>
										<td>
											<select class="cbx_width100per border_none">
												<option></option>
											</select>
										</td>
										<td></td>
										<td><input class="txt_width100per border_none align_right" type="text" value="" maxlength="7"></td>
										<td><input class="txt_width100per border_none align_right" type="text" value="" maxlength="7"></td>
										<td><input class="txt_width100per border_none align_right" type="text" value="" maxlength="7"></td>
										<td><input class="txt_width100per border_none align_right" type="text" value="" maxlength="7"></td>
									</tr>
								</table>
								<button id="addRow" class="btn_add mt5" type="button">追加</button>
								
								<!-- 明細.フッタ部 -->
								<div class="mt10">
									<table class="tbl_width1200">
										<tr>
											<td class="td_width90 align_top">未回収残高</td>
											<td class="align_top">
												<input class="txt_width100 align_right" type="text" value="21,000" maxlength="10">
											</td>
											
											<td class="td_width30 align_center align_top">=</td>
											
											<td class="td_width75 align_top">入金金額</td>
											<td class="align_top">
												<input class="txt_width100 align_right" type="text" value="13,000" maxlength="10">
											</td>
											
											<td class="td_width30 align_center align_top">+</td>
											
											<td class="td_width90 align_top">経費相殺額</td>
											<td class="align_top">
												<input class="txt_width100 align_right" type="text" value="7,000" maxlength="10">
											</td>
											
											<td class="td_width30 align_center align_top">+</td>
											
											<td class="td_width100 align_top">今回未回収残</td>
											<td class="align_top">
												<input class="txt_width100 align_right" type="text" value="1,000" maxlength="10">
											</td>
											
											<td class="td_width100 align_center align_top">残高理由</td>
											<td>
												<div class="divBalance">
													<table class="tbl_list">
														<tr>
															<td class="td_width55per">
																<select class="cbx_width100per bg_faf9e5 border_none">
																	<option>請求・入金差異</option>
																</select>
															</td>
															<td>
																<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="" maxlength="10">
															</td>
														</tr>
														<tr>
															<td>
																<select class="cbx_width100per border_none">
																	<option>請求・入金差異</option>
																</select>
															</td>
															<td>
																<input class="txt_width100per border_none align_right" type="text" value="" maxlength="10">
															</td>
														</tr>
														<tr>
															<td>
																<select class="cbx_width100per bg_faf9e5 border_none">
																	<option>請求・入金差異</option>
																</select>
															</td>
															<td>
																<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="" maxlength="10">
															</td>
														</tr>
														<tr>
															<td>
																<select class="cbx_width100per bg_faf9e5 border_none">
																	<option>請求・入金差異</option>
																</select>
															</td>
															<td>
																<input class="txt_width100per bg_faf9e5 border_none align_right" type="text" value="" maxlength="10">
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</div>
								
							</div>
						</div>
					</c:if>
					<!-- 明細部 END -->
					
				</div>
				<%-- content end --%>
				
				<!--  フッター部 -->
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						<c:choose>
							<c:when test="${!FormNyu0102d01.showFlag}">
								<button id="btnProc"  disabled="true" class="btn_button_disabled float_left"              type="button">登録</button>
								<button id="btnPrint" disabled="true" class="btn_button_disabled float_left btn_width120" type="button">入金内訳書</button>
							</c:when>
							<c:otherwise>
								<button id="btnProc"  class="btn_button float_left"              type="button">登録</button>
								<button id="btnPrint" class="btn_button float_left btn_width120" type="button">入金内訳書</button>
							</c:otherwise>
						</c:choose>
						<button id="btnClear" class="btn_button float_right" type="button">クリア</button>
					</div>
				</div>
				
			</div>
			
			<!-- HIDDDEN部  -->
			<form:hidden id="btnName"         path="btnName" />
			<form:hidden id="sysAdminFlag"    path="sysAdminFlag" />
			<form:hidden id="authKbn"         path="authKbn" />
			<form:hidden id="businessDate"    path="businessDate" />
			<form:hidden id="loginJigyoshoCd" path="loginJigyoshoCd" />
			<form:hidden id="haitaDate"       path="haitaDate" />
			<form:hidden id="haitaTime"       path="haitaTime" />
			<form:hidden id="errorControl"    path="errorControl" />
			<form:hidden id="showFlag"        path="showFlag" />
			<form:hidden id="shoriKbn"        path="shoriKbn" />
			
			<!-- サーバチェックエラー項目ID  -->
			<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
			
			<!-- default message 部 -->
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
