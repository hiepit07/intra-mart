<!DOCTYPE html>
<%--
  ファイル名:nyu0106d01.jsp
  画面名:会計入金実績作成

  作成者:都築電気
  作成日:2015/12/07
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/12/07 1.00                  ACT)ANZAI       新規作成
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/nyu/nyu0106d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/nyu/nyu0106d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="FormNyu0106d01" id="FormNyu0106d01">
			
			<!-- START WRAPPER -->
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
				
				<!-- START CONTENT -->
				<div class="content">
					
					<!-- ヘッダ部 -->
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title">
								<h3>
									<span >■</span>入金実績作成
								</h3>
							</div>
							<div class="condition_cont">
								<table class="tbl_data_condition clear mt10">
									<tr>
										<td class="align_right">前回作成日付</td>
										<td>
											<span class="span_width10 color_blue mr15 ">
												<c:if test="${!empty FormNyu0106d01.prevCreateDate }">
													<fmt:formatDate value="${ DateUtil.toDate(FormNyu0106d01.prevCreateDate, 'yyyyMMdd')}" pattern="yyyy/MM/dd"/>
												</c:if>
											</span>
										</td>
										<td class="align_right">前回作成時刻</td>
										<td>
											<span class="span_width10 color_blue mr15 ">
												<c:if test="${!empty FormNyu0106d01.prevCreateTime }">
													<fmt:formatDate value="${ DateUtil.toDate(FormNyu0106d01.prevCreateTime, 'HHmmss')}" pattern="HH:mm:ss"/>
												</c:if>
											</span>
										</td>
									</tr>
									<tr>
										<td class="align_right">今回入金件数</td>
										<td>
											<span class="span_width10 color_blue mr15 ">
												${FormNyu0106d01.curNyukinCount}
											</span>
										</td>
										<td class="align_right">今回仕訳件数</td>
										<td>
											<span class="span_width10 color_blue mr15 ">
												${FormNyu0106d01.curShiwakeCount}
											</span>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<c:choose>
									<c:when test="${FormNyu0106d01.curShiwakeCount > 0}">
										<button id="btnProc" class="btn_button float_left ml0" type="button">実行</button>
									</c:when>
									<c:otherwise>
										<button id="btnProc" disabled="true" class="btn_button_disabled float_left ml0" type="button">実行</button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					
					<div class="data_result_area clear">
						
						<!-- 入金実績仕分CSVダウンロード一覧 -->
						<div class="div_zentai border_bottom mb5">
							<div class="ml30">
								
								<c:if test="${FormNyu0106d01.showCsvLink}">
									<p class="pb5">入金実績の仕訳作成が完了しました。</p>
									<p class="pb5">以下をクリックして、今回作成分の仕訳ファイルを取得してください。</p>
									<div id="divHead1" class="divHead1">
										<table class="tbl_list">
											<col class="td_width100per">
										</table>
									</div>
									<div id="divBody1" class="divBody1 height105 mb10">
										<table id="tblBody1" class="tbl_list">
											<col class="td_width100per">
											
											<%-- START ファイルリンク --%>
											<c:forEach items="${FormNyu0106d01.csvLinkList}" var="linkInfo" varStatus="status">
												<tr class="bg_white">
													<td class="align_center">
														<a href="${pageContext.request.contextPath}/${FormNyu0106d01.downloadUrl}/${linkInfo}">
															${linkInfo}
														</a>
													</td>
												</tr>
											</c:forEach>
											<%-- END ファイルリンク --%>
											
										</table>
									</div>
								</c:if>
								
							</div>
						</div>
						
						<!-- 明細部 -->
						<div class="div_zentai">
							<p class="text_request">■ 作成履歴</p>
							
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width3per">
									<col class="td_width16per">
									<col class="td_width16per">
									<col class="td_width7per">
									<col class="td_width16per">
									<col class="td_width14per">
									<col class="td_width14per">
									<col class="td_width14per">
									<tr>
										<th><span>No</span></th>
										<th>作成日付</th>
										<th>作成日時</th>
										<th>種別</th>
										<th>入金／売上件数</th>
										<th>仕訳件数</th>
										<th>出力回数</th>
										<th>CSV出力</th>
									</tr>
								</table>
							</div>
							
							<div id="divBody" class="divBody height290">
								<table id="tblBody" class="tbl_list">
									<col class="td_width3per">
									<col class="td_width16per">
									<col class="td_width16per">
									<col class="td_width7per">
									<col class="td_width16per">
									<col class="td_width14per">
									<col class="td_width14per">
									<col class="td_width14per">
									
									<%-- START 履歴リスト --%>
									<c:forEach items="${FormNyu0106d01.rirekiList}" var="rirekiInfo" varStatus="status">
										<tr>
											<%-- No --%>
											<td class="align_center">
												${status.count}
											</td>
											<%-- 作成日付 --%>
											<td>
												<fmt:formatDate value="${ DateUtil.toDate(rirekiInfo.createDate, 'yyyyMMdd')}" pattern="yyyy/MM/dd"/>
											</td>
											<%-- 作成日時 --%>
											<td class="align_center">
												<fmt:formatDate value="${ DateUtil.toDate(rirekiInfo.createTime, 'HHmmss')}" pattern="HH:mm:ss"/>
											</td>
											<%-- 種別 --%>
											<td class="align_center">
												<c:if test="${rirekiInfo.shubetsu == '1'}">
													入金
												</c:if>
												<c:if test="${rirekiInfo.shubetsu != '1'}">
													売掛
												</c:if>
											</td>
											<%-- 入金／売上件数 --%>
											<td class="align_center">
												${rirekiInfo.dempyoCount}
											</td>
											<%-- 仕訳件数 --%>
											<td class="align_center">
												${rirekiInfo.shiwakeCount}
											</td>
											<%-- 出力回数 --%>
											<td class="align_center">
												${rirekiInfo.outputCount}
											</td>
											<%-- CSV出力 --%>
											<td class="align_center">
												<button name="btnCsv" class="btn_billing_submit" type="button" value="${rirekiInfo.renkeiId}" data-shubetsu="${rirekiInfo.shubetsu}" data-create-date="${rirekiInfo.createDate}" data-create-time="${rirekiInfo.createTime}">CSV出力</button>
											</td>
										</tr>
									</c:forEach>
									<%-- END 履歴リスト --%>
									
								</table>
							</div>
							
						</div>
						
					</div>
					
				</div>
				<!-- END CONTENT-->
				
				<!-- フッタ部 -->
				<div class="footer_area">
					<div></div>
				</div>
				
			</div>
			<!-- END WRAPPER -->
			
			<!-- HIDDDEN部  -->
			<form:hidden id="btnName"         path="btnName" />
			<form:hidden id="sysAdminFlag"    path="sysAdminFlag" />
			<form:hidden id="businessDate"    path="businessDate" />
			<form:hidden id="loginJigyoshoCd" path="loginJigyoshoCd" />
			<form:hidden id="errorControl"    path="errorControl" />
			<form:hidden id="showCsvLink"     path="showCsvLink" />
			<form:hidden id="downloadUrl"     path="downloadUrl" />
			
			<!-- CSV出力 -->
			<form:hidden id="csvRenkeiId"   path="csvRenkeiId" />
			<form:hidden id="csvShubetsu"   path="csvShubetsu" />
			<form:hidden id="csvCreateDate" path="csvCreateDate" />
			<form:hidden id="csvCreateTime" path="csvCreateTime" />
			
			<!-- サーバチェックエラー項目ID  -->
			<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
			
			<!-- default message 部 -->
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
