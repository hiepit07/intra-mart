<!DOCTYPE html>
<%--
  ファイル名:uri0101d01.jsp
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
<meta http-equiv="X-UA-Compatible"
	content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/uri/uri0101d01.css" />
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
<script src="<c:url value="/resources/js/uri/uri0101d01.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/uri/uri01func.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/juc/delDialogJucUri.js" />"
	type="text/javascript"></script>


<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input"
			modelAttribute="formUri0101d01" id="formUri0101d01">
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
										<!-- 

									<input type="hidden" id="sysAdminFlg" name="sysAdminFlg" value="${SysAdminFlg}">
									<input type="hidden" id="priceMax" name="priceMax" value="${PriceMax}">
									<input type="hidden" id="deliDate" name="deliDate" value="${DeliDate}">
									<input type="hidden" id="aplDate" name="aplDate" value="${AplDate}">
									<input type="hidden" id="determMonth" name="determMonth" value="${DetermMonth}">
								 -->
										<form:hidden id="hdnSysAdminFlg" path="hdnSysAdminFlg" />
										<form:hidden id="hdnLoginJigyoCode" path="hdnLoginJigyoCode" />
										<form:hidden id="hdnPriceMax" path="hdnPriceMax" />
										<form:hidden id="hdnAddDay" path="hdnAddDay" />
										<form:hidden id="hdnAplDate" path="hdnAplDate" />
										<form:hidden id="hdnDetermMonth" path="hdnDetermMonth" />
										<form:hidden id="hdnShopKb" path="hdnShopKb" />
										<form:hidden id="hdnChainCode" path="hdnChainCode" />
										<form:hidden id="hdnChainIdx" path="hdnChainIdx" />
										<form:hidden id="hdnChainName" path="hdnChainName" />
										<form:hidden id="hdnDeliCode" path="hdnDeliCode" />
										<form:hidden id="hdnDeliName" path="hdnDeliName" />
										<form:hidden id="hdnDeliNameKana" path="hdnDeliNameKana" />
										<form:hidden id="hdnChainSalesKb" path="hdnChainSalesKb" />
										<form:hidden id="hdnBillRudKb" path="hdnBillRudKb" />
										<form:hidden id="hdnBillRudPoint" path="hdnBillRudPoint" />
										<form:hidden id="hdnBillAmtRudKb" path="hdnBillAmtRudKb" />
										<form:hidden id="hdnClassCodeHead" path="hdnClassCodeHead" />
										<form:hidden id="hdnClassCodeItem" path="hdnClassCodeItem" />
										<form:hidden id="hdnOperateMode" path="hdnOperateMode" />
										<form:hidden id="hdnUpdateDateTime" path="hdnUpdateDateTime" />
										<form:hidden id="hdnCancelMode" path="hdnCancelMode" />
										<form:hidden id="hdnPrmDenNo" path="hdnPrmDenNo" />
										<form:hidden id="hdnPrmShoriKb" path="hdnPrmShoriKb" />
										<form:hidden id="errorControl" path="errorControl" />
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
									<div class="cor_class">
										<label for="corClass">修正区分</label>
										<form:select id="ddlModifyKb" path="ddlModifyKb"
											class="corClass">
											<form:options items="${ModifyKbList}" itemLabel="name"
												itemValue="code" />
										</form:select>
										<!-- select id="corClass" class="corClass">
										<option>破損返品</option>
										<option></option>
										<option></option>
									</select-->
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
										<td class="align_right">伝票区分</td>
										<td class="td_width3 orange_line"></td>
										<td><form:select id="ddlSlipKb" path="ddlSlipKb"
												class="cbx_width70"
												cssErrorClass="form-error-field cbx_width70">
												<form:options items="${SlipKbList}" itemLabel="name"
													itemValue="code" />
											</form:select> <form:hidden id="hdnSlipKb" path="hdnSlipKb" /></td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
									</tr>
									<tr>
										<td class="align_right">入力担当者</td>
										<td class="td_width3"></td>
										<td colspan="4"><span id="lblRegistUserCode"
											class="span_width102">${UserCode}</span><span
											class="span_width25"></span> <span id="lblRegistUserName"
											class="color_blue">${UserNm}</span> <form:hidden
												id="hdnRegistUserCode" path="hdnRegistUserCode"
												value="${UserCode}" /> <form:hidden id="hdnRegistUserName"
												path="hdnRegistUserName" value="${UserNm}" /> <form:hidden
												id="hdnRegistUserNameKana" path="hdnRegistUserNameKana"
												value="${UserNmKana}" /></td>
										<td class="align_right">伝票No</td>
										<td class="td_width3"></td>
										<td><span id="lblSlipNo" class="span_width102 color_blue"></span>
											<form:hidden id="hdnCustomerSlipNo" path="hdnCustomerSlipNo" />
										</td>
									</tr>
									<tr>
										<td class="align_right">得意先コード</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4"><form:input type="text"
												class="txt_width100" path="txtCustomerCode"
												id="txtCustomerCode" value="" maxlength="7" /> <!-- input class="txt_width100 align_right" type="text" maxlength="7"-->
											<img
											src="${pageContext.request.contextPath}/resources/css/images/search.png"
											id="btnSearchCustomer" class="searchIcon" alt="検索"> <span
											id="lblCustomerNameR" class="color_blue"></span> <form:hidden
												id="hdnCustomerCode" path="hdnCustomerCode" /> <form:hidden
												id="hdnCustomerName" path="hdnCustomerName" /> <form:hidden
												id="hdnCustomerNameR" path="hdnCustomerNameR" /> <form:hidden
												id="hdnCustomerNameK" path="hdnCustomerNameK" /></td>
										<td class="align_right">店舗コード</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input type="text" class="txt_width100"
												path="txtShopCode" id="txtShopCode" value="" maxlength="8" />
											<!-- input class="txt_width100 align_right" type="text" maxlength="8" -->
											<img
											src="${pageContext.request.contextPath}/resources/css/images/search.png"
											id="btnSearchShop" class="searchIcon" alt="検索"> <span
											id="lblShopNm" class="color_blue"></span> <form:hidden
												id="hdnShopCode" path="hdnShopCode" /> <form:hidden
												id="hdnShopName" path="hdnShopName" /> <form:hidden
												id="hdnShopNameR" path="hdnShopNameR" /> <form:hidden
												id="hdnShopNameK" path="hdnShopNameK" /></td>
									</tr>
								</table>
								<table class="tbl_data_condition2 mt15">
									<tr>
										<td class="align_right">伝票発行</td>
										<td class="td_width3 orange_line"></td>
										<td colspan=7><form:input type="text"
												class="txt_width30 align_center" path="txtSlipOut"
												id="txtSlipOut" value="" maxlength="1" /> <span>（1:即発行
												2:指定後発行 3:発行しない）</span> <form:hidden id="hdnSlipOut"
												path="hdnSlipOut" /></td>
									</tr>
									<tr>
										<td class="align_right">納品日</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input type="text" class="year txt_width50"
												path="txtDeliDateYear" id="txtDeliDateYear" value=""
												maxlength="4" /> <span class="normal">/</span> <form:input
												type="text" class="month txt_width30"
												path="txtDeliDateMonth" id="txtDeliDateMonth" value=""
												maxlength="2" /> <span class="normal">/</span> <form:input
												type="text" class="day txt_width30" path="txtDeliDateDay"
												id="txtDeliDateDay" value="" maxlength="2" /> <form:hidden
												id="hdnDeliYmd" path="hdnDeliYmd" /> <form:hidden
												id="hdnDeliYear" path="hdnDeliYear" /> <form:hidden
												id="hdnDeliMonth" path="hdnDeliMonth" /> <form:hidden
												id="hdnDeliDay" path="hdnDeliDay" /></td>
										<td class="align_right">計上日付</td>
										<td id="orangeLineAccount" class="td_width3"></td>
										<td><span id="lblAccountDate" class="color_blue"></span>
											<form:input type="text" class="accDay txt_width30"
												path="txtAccountDay" id="txtAccountDay" value=""
												maxlength="2" /> <form:hidden id="hdnAccountDay"
												path="hdnAccountDay" /> <form:hidden id="hdnAccountYmd"
												path="hdnAccountYmd" /></td>
										<td class="align_right"><span id="ttlOriginSlipNo">返品／欠品元伝票No</span></td>
										<td class="td_width3"></td>
										<td><form:input type="text" class="txt_width100"
												path="txtOriginSlipNo" id="txtOriginSlipNo" value=""
												maxlength="9" /> <form:hidden id="hdnOriginSlipNo"
												path="hdnOriginSlipNo" /></td>
									</tr>
									<tr>
										<td class="align_right">便区分</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input type="text"
												class="txt_width30 align_center" path="txtBinKb"
												id="txtBinKb" value="" maxlength="1" /> <span
											class="normal">便</span> <form:hidden id="hdnBinKb"
												path="hdnBinKb" /></td>
										<td class="align_right">納品区分</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input type="text"
												class="txt_width30 align_center" path="txtDeliKb"
												id="txtDeliKb" value="" maxlength="1" /> <span
											class="normal">（1:店直 2:センター）</span> <form:hidden
												id="hdnDeliKb" path="hdnDeliKb" /></td>
										<td class="align_right">販売区分</td>
										<td class="td_width3 orange_line"></td>
										<td><form:input type="text"
												class="txt_width30 align_center" path="txtSalesKb"
												id="txtSalesKb" value="" maxlength="1" /> <span>（1:定番
												2:特売 3:チラシ 4:サービス）</span> <form:hidden id="hdnSalesKb"
												path="hdnSalesKb" /></td>
									</tr>
									<tr>
										<td class="align_right">コースコード</td>
										<td class="td_width3"></td>
										<td colspan=4><span id="lblCourseCode"
											class="color_blue span_width50"></span><span
											class="span_width25"></span> <span id="lblCourseNameR"
											class="color_blue"></span> <form:hidden id="hdnCourseCode"
												path="hdnCourseCode" /> <form:hidden id="hdnCourseName"
												path="hdnCourseName" /> <form:hidden id="hdnCourseNameR"
												path="hdnCourseNameR" /></td>
										<td class="align_right">先方伝票No</td>
										<td class="td_width3"></td>
										<td><form:input type="text" class="txt_width100"
												path="txtSnpSlipNo" id="txtSnpSlipNo" value=""
												maxlength="9" /> <form:hidden id="hdnSnpSlipNo"
												path="hdnSnpSlipNo" /></td>
									</tr>
									<tr>
										<td class="align_right">出荷伝票種別</td>
										<td class="td_width3"></td>
										<td colspan=4><span id="lblSlipType" class="color_blue"></span>
											<form:hidden id="hdnSlipId" path="hdnSlipId" /> <form:hidden
												id="hdnSlipType" path="hdnSlipType" /></td>
										<td class="align_right">伝票行数</td>
										<td class="td_width3"></td>
										<td><span id="lblSlipLine"
											class="span_width33 color_blue"></span> <form:hidden
												id="hdnSlipLine" path="hdnSlipLine" /></td>
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
								<col class="td_width9per" id="tdItemCode">
								<col class="td_width10per" id="tdCustomerItemCode">
								<col class="td_width14per" id="tdItemName">
								<col class="td_width6per" id="tdRyomoku">
								<col class="td_width6per" id="tdIrisu">
								<col class="td_width10per bodyModifyKbList" id="tdModifyKbList">
								<!-- 修正区分 -->
								<col class="td_width8per" id="tdJucCase">
								<col class="td_width8per" id="tdJucSeparate">
								<col class="td_width8per" id="tdDeliPrica">
								<col class="td_width8per" id="tdSalesPrice">
								<col class="td_width9per" id="tdDeliAmt">
								<tr>
									<th rowspan="2">No</th>
									<th colspan="2">品目コード</th>
									<th rowspan="2">品目名</th>
									<th rowspan="2">量目</th>
									<th rowspan="2">入数</th>
									<th rowspan="2" id="headMofifyKbList">修正区分</th>
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
									<col class="td_width9per" id="tdItemCode">
									<col class="td_width10per" id="tdCustomerItemCode">
									<col class="td_width14per" id="tdItemName">
									<col class="td_width6per" id="tdRyomoku">
									<col class="td_width6per" id="tdIrisu">
									<col class="td_width10per bodyModifyKbList" id="tdModifyKbList">
									<!-- 修正区分 -->
									<col class="td_width8per" id="tdJucCase">
									<col class="td_width8per" id="tdJucSeparate">
									<col class="td_width8per" id="tdDeliPrica">
									<col class="td_width8per" id="tdSalesPrice">
									<col class="td_width9per" id="tdDeliAmt">
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