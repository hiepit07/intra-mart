<!DOCTYPE html>
<%--
  ファイル名:uri0103d01.jsp
  画面名:再請求入金登録

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
<meta http-equiv="X-UA-Compatible"
	content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/uri/uri0103d01.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/inputChkCom.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/inputChkJucUri.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/uri/uri0103d01.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/uri/uri01func.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/juc/delDialogJucUri.js" />"
	type="text/javascript"></script>


<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input"
			modelAttribute="formUri0103d01" id="formUri0103d01">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError">
					<div>
						<span id="txtMess"> <c:forEach items="${errorMessages}"
								var="errorMessages" varStatus="status">
								${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont border_none">
							<div class="condition_cont">
								<div class="option_regis_area">
									<div class="hidden_area">
										<input type="hidden" id="hdnContextPath" name="hdnContextPath"
											value="${pageContext.request.contextPath}"> <input
											type="hidden" id="lstErrorID" value="${lstErrorID}" />
										<form:hidden id="hdnSysAdminFlg" path="hdnSysAdminFlg" />
										<form:hidden id="hdnLoginJigyoCode" path="hdnLoginJigyoCode" />
										<form:hidden id="hdnPriceMax" path="hdnPriceMax" />
										<form:hidden id="hdnOperateMode" path="hdnOperateMode" />
										<form:hidden id="hdnJsonBillSrc" path="hdnJsonBillSrc" />
										<form:hidden id="hdnSlipIdx" path="hdnSlipIdx" />
										<form:hidden id="hdnUpdateDateTime" path="hdnUpdateDateTime" />
									</div>
									<div class="option_regis">
										<input id="rad_New" type="radio" value="1" name="optionR"
											class="optionR"> <label for="rad_New" class="mr10">新規登録</label>
										<input id="rad_Correct" type="radio" value="2" name="optionR"
											class="optionR"> <label for="rad_Correct"
											class="mr10">訂正</label> <input id="rad_Cancel" type="radio"
											value="3" name="optionR" class="optionR"> <label
											for="rad_Cancel" class="mr10">取消</label> <input
											id="rad_Query" type="radio" value="4" name="optionR"
											class="optionR"> <label for="rad_Query">照会</label>
									</div>
									<div class="slip_no">
										<label for="txtSlipNo">自社伝票No</label>
										<form:input type="text" class="slipNo" path="txtSlipNo"
											id="txtSlipNo" value="" maxlength="9" />
										<form:hidden id="hdnSlipNo" path="hdnSlipNo" />
										<form:hidden id="hdnSlipIdx" path="hdnSlipIdx" />
									</div>
									<div class="tax_customer">
										<span class="txt_tax">内税顧客</span>
									</div>
								</div>
								<table class="tbl_data_condition1 mt15">
									<tr id="jigyo_area">
										<td class="align_right">事業所</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4"><form:input type="text"
												class="txt_width30" path="txtJigyoCode" id="txtJigyoCode"
												value="${JigyoCode}" maxlength="2" /> <form:hidden
												id="hdnJigyoCode" path="hdnJigyoCode" /> <span
											class="span_width25"></span> <span id="lblJigyoNm"
											class="color_blue">${JigyoNm}</span> <form:hidden
												id="hdnJigyoName" path="hdnJigyoName" /></td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
									</tr>
									<tr>
										<td class="align_right">入力担当者</td>
										<td class="td_width3"></td>
										<td><span id="lblRegistUserCode"
											class="span_width102 color_blue">${UserCode}</span> <form:hidden
												id="hdnRegistUserCode" path="hdnRegistUserCode" /> <span
											class="span_width25"></span><span id="lblRegistUserName"
											class="color_blue">${UserNm}</span></td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
										<td class="align_right">請求元伝票No</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input type="text" class="txt_width100"
												path="txtBillSrcNo" id="txtBillSrcNo" value="" maxlength="9" />
											<form:hidden id="hdnBillSrcNo" path="hdnBillSrcNo" /></td>
									</tr>
									<tr>
										<td class="align_right">得意先コード</td>
										<td class="td_width3"></td>
										<td colspan="4"><span id="lblCustomerCode"
											class="span_width102 color_blue"></span><form:hidden
												id="hdnCustomerCode" path="hdnCustomerCode" /><span
											class="span_width25"></span><span id="lblCustomerNameR"
											class="color_blue"></span></td>
										<td class="align_right">店舗コード</td>
										<td class="td_width3"></td>
										<td><span id="lblShopCode"
											class="span_width102 color_blue"></span><form:hidden
												id="hdnShopCode" path="hdnShopCode" /><span
											class="span_width25"></span><span id="lblShopNmR"
											class="color_blue"></span></td>
									</tr>
								</table>
								<table class="tbl_data_condition2 mt15">
									<tr>
										<td class="align_right">納品日</td>
										<td class="td_width3"></td>
										<td><span id="lblDeliDate" class="color_blue"></span><form:hidden
												id="hdnDeliDate" path="hdnDeliDate" /></td>
									</tr>
									<tr>
										<td class="align_right">便区分</td>
										<td class="td_width3"></td>
										<td><span id="lblBinKb" class="color_blue"></span><form:hidden
												id="hdnBinKb" path="hdnBinKb" /> <span
											class="normal">便</span></td>
										<td class="align_right">納品区分</td>
										<td class="td_width3"></td>
										<td><span id="lblDeliKb" class="color_blue"></span> <span
											class="normal">（1:店直 2:センター）</span></td>
										<td class="align_right">販売区分</td>
										<td class="td_width3"></td>
										<td><span id="lblSalesKb" class="color_blue"></span> <span>（1:定番
												2:特売 3:チラシ 4:サービス）</span></td>
									</tr>
									<tr>
										<td class="align_right">コースコード</td>
										<td class="td_width3"></td>
										<td colspan=4><span id="lblCourseCode"
											class="color_blue span_width50"></span><span
											class="span_width25"></span> <span id="lblCourseNameR"
											class="color_blue"></span></td>
									</tr>
									<tr>
										<td class="align_right">出荷伝票種別</td>
										<td class="td_width3"></td>
										<td colspan=4><span id="lblSlipType" class="color_blue"></span></td>
										<td class="align_right">伝票行数</td>
										<td class="td_width3"></td>
										<td><span id="lblSlipLine"
											class="span_width33 color_blue"></span></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="data_result_area clear">
						<div class="div_zentai">
							<div id="divHead" class="divHead"></div>
							<table id="tblHead" class="tbl_list">
								<col class="td_width4per" id="tdNumber">
								<col class="td_width10per" id="tdItemCode">
								<col class="td_width11per" id="tdCustomerItemCode">
								<col class="td_width15per" id="tdItemName">
								<col class="td_width7per" id="tdRyomoku">
								<col class="td_width7per" id="tdIrisu">
								<col class="td_width9per" id="tdJucCase">
								<col class="td_width9per" id="tdJucSeparate">
								<col class="td_width9per" id="tdDeliPrica">
								<col class="td_width9per" id="tdSalesPrice">
								<col class="td_width10per" id="tdDeliAmt">
								<tr>
									<th rowspan="2">No</th>
									<th colspan="2">品目コード</th>
									<th rowspan="2">品目名</th>
									<th rowspan="2">量目</th>
									<th rowspan="2">入数</th>
									<th colspan="2">受注数量</th>
									<th colspan="2">単価</th>
									<th rowspan="2">納品金額</th>
								</tr>
								<tr>
									<th>自社</th>
									<th>得意先</th>
									<th>ケース</th>
									<th>バラ</th>
									<th>納品単価</th>
									<th>販売単価</th>
								</tr>
							</table>
							<div id="divBody" class="divBody height270">
								<form:hidden id="hdnItemJson" path="hdnItemJson" />
								<table id="tblBody" class="tbl_list">
									<col class="td_width4per" id="tdNumber">
									<col class="td_width10per" id="tdItemCode">
									<col class="td_width11per" id="tdCustomerItemCode">
									<col class="td_width15per" id="tdItemName">
									<col class="td_width7per" id="tdRyomoku">
									<col class="td_width7per" id="tdIrisu">
									<col class="td_width9per" id="tdJucCase">
									<col class="td_width9per" id="tdJucSeparate">
									<col class="td_width9per" id="tdDeliPrica">
									<col class="td_width9per" id="tdSalesPrice">
									<col class="td_width10per" id="tdDeliAmt">
									<tbody id="tblBodyRow">
									</tbody>
								</table>
							</div>
							<div id="divFooter" class="divFooter">
								<table id="tblFooter" class="tbl_list">
									<col class="td_width75per">
									<col class="td_width16per">
									<col class="td_width9per">
									<tr>
										<td class="bg_white">&nbsp;</td>
										<td class="bg_aa5930 money_total align_center">納品金額合計</td>
										<td class="bg_ffffcc align_right" id="txtTotalDeliAmt"><form:hidden
												id="hdnTotalDeliAmt" path="hdnTotalDeliAmt" /></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!--/content-->
				<div class="footer_area bg_gray_c0c0c0">
					<div class="float_left">
						<button id="btnRegist" class="btn_button float_left" type="button">登録</button>
						<button id="hdnRegist" class="display_none" type="submit"
							name="Register"></button>
						<button id="btnInputReturn" class="btn_button float_left"
							type="button">入力戻り</button>
					</div>
					<div class="float_right">
						<button id="btnAmountError"
							class="btn_button float_left btn_width130 mr100" type="button">金額エラー解除</button>
						<button id="btnClear" class="btn_button float_right mr0"
							type="button">クリア</button>
					</div>
				</div>
			</div>
			<!-- default message start -->
			<c:forEach items="${defaultMessages}" var="defaultMessage"
				varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}"
					name="defaultMessages" value="${defaultMessage.messageContent}"
					title="${defaultMessage.messageTitle}" />
			</c:forEach>
			<!--menu-->
			<div id="tableMenu" class="sub-menu">
				<a href="#" id="removeSubMenu"> <img
					src="${pageContext.request.contextPath}/resources/img/tool_remove.png"
					height="12" /> 削除
				</a>
			</div>
			<!--DIALOG-->
			<div id="overlay" class="web_dialog_overlay"></div>
			<div id="dialog" class="web_dialog"></div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>