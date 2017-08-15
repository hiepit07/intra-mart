<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/TestParent.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/TestParent.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/systemCommon.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/com0102d02.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />"type="text/javascript"></script>

<tiles:putAttribute name="body">
	<form:form action="proc" method="POST" cssErrorClass="error-input"
		modelAttribute="formTest" id="formTest">

		<div id="header">
			<div class="header_bg">
				<div class="header_cont">
					<img class="logo"
						src="${pageContext.request.contextPath}/resources/img/img_logo.png"
						width="205" height="30" alt="債権管理システム">
					<div class="rightHeader">
						<span id="company_name">奈良営業所</span> <span id="person_name">大徳
							花子 ▼</span>
					</div>
				</div>
			</div>
		</div>

		<!-- page title -->
		<div id="title">
			<div class="title_cont">
				<h2>店舗マスタ</h2>
				<h2 id="operateType">新規作成</h2>
				<div class="btn_back cursor_hover" id="btn_back">
					<p class="text_btn_back">戻る</p>
				</div>
			</div>
		</div>

		<!-- search data entry area -->
		<div id="registrationArea2">
			<!-- table including inputs,... registration data entry area -->
			<div class="registrationAreaInput">
				<table id="registrationAreaTable" style="line-height: 250%;">
					<tr>
						<td class="align_right"><span id="txt_lb_customer_id"
							class="normal">得意先コード</span></td>
						<td class="w3 red_line"></td>
						<td><input id="customer_id" class="customer_id" type="text"
							value="" maxlength="7"> <img
							src="${pageContext.request.contextPath}/resources/img/search.png"
							class="searchIcon" id="btn_search_customer_id" alt="検索"> <span
							id="txt_customer_id"></span></td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_store_id"
							class="normal">店舗コード</span></td>
						<td class="w3 red_line"></td>
						<td><input id="store_code" class="store_code" type="text"
							value="" maxlength="9"> <img
							src="${pageContext.request.contextPath}/resources/img/search.png"
							class="searchIcon" id="btn_search_store_code" alt="検索"> <span
							id="txt_store_code"></span></td>
					</tr>

					<tr>
						<td colspan="9" class="height3 border_dot"></td>
					</tr>
					<tr>
						<td colspan="9" class="height3"></td>
					</tr>

					<tr>
						<td class="align_right"><span id="txt_lb_store_name"
							class="normal">店舗名称</span></td>
						<td class="w3 red_line"></td>
						<td><input id="txt_store_name" class="txt_store_name"
							type="text" value="" maxlength="20"></td>
						<td class="align_right"></td>
						<td class="w3"></td>
						<td colspan=3></td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_store_name_kana"
							class="normal">店舗名称カナ</span></td>
						<td class="w3"></td>
						<td><input id="txt_store_name_kana"
							class="txt_store_name_kana" type="text" value="" maxlength="20"></td>

					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_store_name_ryaku"
							class="normal">店舗略称</span></td>
						<td class="w3 red_line"></td>
						<td><input id="txt_store_name_ryaku"
							class="txt_store_name_ryaku" type="text" value="" maxlength="10"></td>
						<td class="align_right"></td>
						<td class="w3"></td>
						<td colspan=3></td>
					</tr>

					<tr>
						<td colspan="9" class="height3"></td>
					</tr>

					<tr>
						<td colspan="8" class="height3"></td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_postal_code"
							class="normal">郵便番号</span></td>
						<td class="w3 red_line"></td>
						<td><input id="txt_postal_code1" class="txt_postal_code1"
							type="text" value="" maxlength="3">- <input
							id="txt_postal_code2" class="txt_postal_code2" type="text"
							value="" maxlength="4">（例：639-1038）</td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_address1"
							class="normal">住所１</span></td>
						<td class="w3 red_line"></td>
						<td><input id="txt_address1" class="txt_address1" type="text"
							value="" maxlength="20"></td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_address2"
							class="normal">住所２</span></td>
						<td class="w3"></td>
						<td><input id="txt_address2" class="txt_address2" type="text"
							value="" maxlength="20"></td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_tel" class="normal">電話番号</span></td>
						<td class="w3 red_line"></td>
						<td><input id="txt_tel1" class="txt_tel1" type="text"
							value="" maxlength="4"> - <input id="txt_tel2"
							class="txt_tel2" type="text" value="" maxlength="4"> - <input
							id="txt_tel3" class="txt_tel3" type="text" value="" maxlength="4">（例：0743-56-2911）
						</td>
					</tr>
					<tr>
						<td class="align_right"><span id="txt_lb_fax" class="normal">FAX番号</span></td>
						<td class="w3"></td>
						<td><input id="txt_fax1" class="txt_fax1" type="text"
							value="" maxlength="4"> - <input id="txt_fax2"
							class="txt_fax2" type="text" value="" maxlength="4"> - <input
							id="txt_fax3" class="txt_fax3" type="text" value="" maxlength="4">
						</td>
					</tr>
					<tr>
						<td colspan="9" class="height3 border_dot"></td>
					</tr>
					<tr>
						<td colspan="9" class="height3"></td>
					</tr>

					<tr>
						<td class="align_right"><span id="cmb_lb_office"
							class="normal">事業所</span></td>
						<td class="w3 red_line"></td>
						<td><select id="cmb_office">
								<option></option>
								<option>奈良事業所</option>
								<option>宇都宮事業所</option>
								<option>札幌事業所</option>
								<option>富士宮事業所</option>
								<option>中京事業所</option>
						</select></td>
						<td class="align_right"></td>
						<td class="w3"></td>
						<td colspan=3></td>
					</tr>
					<td class="align_right"><span id="cmb_lb_delivery_center"
						class="normal">納品センター</span></td>
					<td class="w3 red_line"></td>
					<td colspan=><select id="cmb_delivery_center"
						class="cmb_delivery_center">
							<option></option>
							<option>0711 近畿Ｊ大阪ＸＤ</option>
					</select></td>
					<td class="align_right"><span id="cmb_lb_thanks_cls"
						class="normal">サンクス区分</span></td>
					<td colspan=3><select id="cmb_thanks_cls"
						class="cmb_thanks_cls">
							<option></option>
							<option>1：サンクス東海</option>
							<option>2：サンクス京阪奈</option>
							<option>3：サンクス①（奈良）</option>
							<option>4：サンクス⑤（中京）</option>
							<option>5：サンクス（札幌）</option>
							<option>6：サークルＫ（札幌）</option>
							<option>7：サークルＫ（奈良）</option>
							<option>8：サークルＫ（中京）</option>
							<option>9：その他</option>
					</select></td>

					<tr>
						<td colspan="9" class="height3 border_dot"></td>
					</tr>
					<tr>
						<td colspan="9" class="height3"></td>
					</tr>

					<tr>
						<td class="align_right">社店コード</td>
						<td class="w3"></td>
						<td>
							<div id="shaten">
								<table>
									<tr>
										<td rowspan="2" align="center">定番</td>
										<td>店直</td>
										<td></td>
									</tr>
									<tr>
										<td>センター</td>
										<td></td>
									</tr>
								</table>

							</div>
						</td>
						<td class="align_right"></td>
						<td class="w3"></td>
						<td>
							<div id="shaten">
								<table>
									<tr>
										<td rowspan="2" align="center">定番</td>
										<td>店直</td>
										<td></td>
									</tr>
									<tr>
										<td>センター</td>
										<td></td>
									</tr>
								</table>

							</div>
						</td>
					</tr>

					<tr>
						<td colspan="9" class="height3 border_dot"></td>
					</tr>
					<tr>
						<td colspan="9" class="height3"></td>
					</tr>

					<tr>
						<td colspan="8" class="height3"></td>
					</tr>
					<tr>
						<td class="align_right"></td>
						<td class="w3"></td>
						<td><input type="checkbox" id="chk_aggre_store"
							name="block1_check" checked><span id="chk_lb_aggre_store"
							class="normal">集約店舗として登録する</span></td>
						<td class="align_right"><span id="txt_lb_aggre_store_id"
							class="normal">集約店舗コード</span></td>
						<td class="w3"></td>
						<td colspan=3><input id="txt_aggre_store_id"
							class="txt_aggre_store_id" type="text" value="" maxlength="9">
							<span id="txt_store_code"></span></td>
					</tr>
					<tr>
						<td class="align_right"><span id="cmb_lb_aggre_criteria"
							class="normal">店舗集約条件</span></td>
						<td class="w3"></td>
						<td><select id="cmb_aggre_criteria">
								<option></option>
								<option>店舗</option>
								<option>伝票No</option>
						</select></td>
						<td class="align_right"></td>
						<td class="w3"></td>
						<td colspan=3></td>
					</tr>
					<tr>
						<td colspan="9" class="height3 border_dot"></td>
					</tr>
					<tr>
						<td colspan="9" class="height3"></td>
					</tr>
					<td class="align_right"><span id="txt_lb_trade_abort"
						class="normal">取引中止日</span></td>
					<td class="w3"></td>
					<td><input id="txt_trade_abort_y" class="txt_trade_abort_y"
						type="text" value="" maxlength="4"> / <input
						id="txt_trade_abort_m" class="txt_trade_abort_m" type="text"
						value="" maxlength="2"> / <input id="txt_trade_abort_d"
						class="txt_trade_abort_d" type="text" value="" maxlength="2">
						/ <input id="txt_trade_abort" class="txt_trade_abort" type="text"
						value="" maxlength="8"></td>
					<td class="align_right"><span id="txt_lb_status_code"
						class="normal">状況コード</span></td>
					<td class="w3 red_line"></td>
					<td colspan=3><input id="txt_status_code"
						class="txt_status_code" type="text" value="" maxlength="1">（1：登録
						9：取消）</td>
					<tr>
						<td colspan="9" class="height3 border_dot"></td>
					</tr>
					<tr>
						<td colspan="9" class="height3"></td>
					</tr>
					<tr>
						<td colspan="8" class="height10"></td>
					</tr>
				</table>
			</div>
		</div>

		<!--DIALOG-->
		<div id="overlay" class="web_dialog_overlay"></div>
		<div id="dialog" class="web_dialog">

			<!--取引先検索-->

		</div>
		<!--end dialog-->

	</form:form>
</tiles:putAttribute>
