<!DOCTYPE html>
<%--
  ファイル名:sei0101d01.jsp
  画面名:

  作成者:
  作成日:
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sei/sei0101d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/sei/sei0101d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0102d01" id="formMst0102d01">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError">
					<div>
						<span id="txtMess"> <c:forEach items="${errorMessages}"
								var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">
				<!--condition area-->
				<div class="data_conditions_area">
					<div class="data_conditions_area_cont">
						<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
						<div class="condition_cont">
							<table class="tbl_data_condition" id="tblDataCondition">
								<tr>
									<td class="align_right">事業所</td>
										<td class="td_width3"></td>
										<td colspan="7">
											<form:select id="ddlJigyouSho" path="ddlJigyouSho" class="cbx_width170">
		      									<form:options items="${arrListJigyouSho}" itemLabel="name" itemValue="code" />
		   									</form:select>
										</td>
									</tr>
								<tr>
									<td class="td_width100 align_right">請求締日</td>
									<td class="td_width3 orange_line"></td>
									<td class="td_width200">
										<input class="txt_width100" type="text" value="20141130" maxlength="8">
										<img class="align_middle" src="${pageContext.request.contextPath}/resources/css/images/calendar.gif">
										<span class="align_middle">YYYYMMDD</span>
									</td>
									<td class="align_right">事務担当者</td>
									<td class="td_width3"></td>
									<td class="td_width250">
										<input class="txt_width100 align_right" type="text" maxlength="8" value="11000008">
										<img class="searchIcon" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue">大徳　花子</span>
									</td>
									<td>
										<input id="chkDisplayBillable" type="checkbox" name="chkDisplayBillable">
										<label for="chkDisplayBillable">請求対象のみ表示</label>
									</td>
									<td>
										<input id="chkOtherCustomer" type="checkbox" name="chkOtherCustomer">
										<label for="chkOtherCustomer">他社顧客</label>
									</td>
								</tr>
								<tr>
									<td class="td_width100 align_right">請求先コード</td>
									<td class="td_width3"></td>
									<td colspan="7">
										<input class="txt_width100 align_right" type="text" maxlength="8" value="11000008">
										<img class="searchIcon" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
										<span class="color_blue">㈱ＡＢＣ○○○○○○</span>
									</td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
						<div class="condition_button">
							<button id="btnSearch" class="btn_button float_left" type="button">表示</button>
						</div>
					</div>
					<div class="data_conditions_area_cont border_none mt10">
						<div class="condition_cont">
							<table class="tbl_data_condition_option">
								<tr>
									<td class="td_width70 align_right">出力最終日</td>
									<td class="td_width3"></td>
									<td colspan="7">
										<input class="txt_width100" type="text" value="20141130" maxlength="8">
										<img class="align_middle" src="${pageContext.request.contextPath}/resources/css/images/calendar.gif">
										<span class="align_middle">YYYYMMDD</span>
									</td>
								</tr>
								<tr>
									<td class="align_right">並び順</td>
									<td class="td_width3"></td>
									<td>
										<div class="div_option">
											<input id="rdoOfficePersonnel" type="radio" value="0" name="optionAlign" checked>
											<label for="rdoOfficePersonnel" class="mr15">事務担当者</label>
											<input id="rdoBillingCode" type="radio" value="1" name="optionAlign">
											<label for="rdoBillingCode">請求先コード</label>
										</div>
									</td>
									<td class="td_width150 align_right">チェックリスト出力形式</td>
									<td class="td_width3"></td>
									<td>
										<div class="div_option">
											<input id="rdoCusStore" type="radio" value="0" name="optionChecklist">
											<label for="rdoCusStore" class="mr15">得意先／店舗毎</label>
											<input id="rdoEachslip" type="radio" value="1" name="optionChecklist" checked>
											<label for="rdoEachslip">伝票毎</label>
										</div>
									</td>
									<td class="td_width70 align_right">出力金額</td>
									<td class="td_width3"></td>
									<td>
										<div class="div_option">
											<input id="rdoDelivery" type="radio" value="0" name="optionOutput">
											<label for="rdoDelivery" class="mr15">納品単価計上</label>
											<input id="rdoPartition" type="radio" value="1" name="optionOutput" checked>
											<label for="rdoPartition">先方仕切価計上</label>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="data_result_area clear">
					<div class="div_zentai">
						<div id="divHead" class="divHead">
							<table class="tbl_list">
								<col class="td_width3per">
								<col class="td_width4per">
								<col class="td_width68per">
								<col class="td_width13per">
								<col class="td_width12per">
								<tr>
									<th>No</th>
									<th>選択<br><input type="checkbox"></th>
									<th>請求先</th>
									<th>事業所</th>
									<th>事務担当者</th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="divBody height345">
							<table id="tblBody" class="tbl_list">
								<col class="td_width3per">
								<col class="td_width4per">
								<col class="td_width5per">
								<col class="td_width63per">
								<col class="td_width13per">
								<col class="td_width12per">
								<tr>
									<td class="align_right">1</td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right">0231000</td>
									<td class="border_l_none">㈱ＡＢＣ○○○○○○</td>
									<td class="align_left">奈良事業所</td>
									<td>大徳　花子</td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
								<tr>
									<td class="align_right"></td>
									<td class="align_center"><input type="checkbox"></td>
									<td class="border_r_none align_right"></td>
									<td class="border_l_none"></td>
									<td class="align_left"></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div> <!--/content-->
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
			<div class="footer_area bg_gray_c0c0c0">
				<div>
					<button class="btn_button float_left mr100" type="button">実行</button>
					<button class="btn_button btn_width120 float_left" type="button">納品明細書印刷</button>
					<button class="btn_button float_right mr0" type="button">クリア</button>
				</div>
			</div>				
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>