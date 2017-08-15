<!DOCTYPE html>
<%--
  ファイル名:mst0104d02.jsp
  画面名:コースマスタ登録画面

  作成者:ABV）Nhungma
  作成日:2015/09/21
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/21 1.00                  ABV）Nhungma        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
	href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonSub.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layout.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/com/com0102d05.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/messageHandler.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery_load_message.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/utils.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/const.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/com0102d05.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/common.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/inputChkCom.js" />"
	type="text/javascript"></script>

<script src="<c:url value="/resources/js/testcom0102d05.js" />"
type="text/javascript"></script>

<div id="wrapper">
	<!-- page header -->
	<div id="header">
		<div class="header_cont">
			<img class="logo" src="img/img_logo.png" width="205" height="30"
				alt="債権管理システム">
			<div class="drop-menu float_right">
				<ul>
					<li><a href="#">
							<div id="person_name">大徳 花子 ▼</div>
					</a>
						<ul>
							<li><a href="./changePassword.html">パスワード変更</a></li>
							<li><a href="./login.html"
								onclick="javascript:return confirm('ログアウトします。よろしいですか？');">ログアウト</a></li>
						</ul></li>
				</ul>
			</div>
			<div class="rightHeader">
				<span id="company_name">奈良営業所</span>
			</div>
		</div>
	</div>

	<!-- page title -->
	<div class="title">
		<div class="title_cont">
			<h2>コースマスタメンテナンス</h2>
		</div>
		<button id="btnBack" class="btn_back cursor_hover" type="submit"
			name="Return">
			<span>戻る</span>
		</button>
	</div>

	<!--page note output -->
	<div id="messError">
		<div>
			<span id="txtMess"> <c:forEach items="${errorMessages}"
					var="errorMessages" varStatus="status"> ${errorMessages.messageContent}<br />
				</c:forEach>
			</span>
		</div>
	</div>
	<div class="content">
		<!--condition area-->

		<div class="data_conditions_area mr_ml20">
			<div class="data_conditions_input_cont">
				<div class="input_area height70">
					<table class="tbl_data_condition_search">
						<tr>
							<td class="align_right td_width90 pr10">担当者コード</td>
							<td class="td_width3 orange_line"></td>
							<td class="td_width270 pl3"><input
								class="txt_width100 float_left" type="text" value="00000001"
								id="userCodeWk" maxlength="8"> <img
								class="searchIcon float_left ml_mr4" id="btn_search_customer_id05"
								alt="検索"
								src="${pageContext.request.contextPath}/resources/css/images/search.png">
								<div class="width120 float_left mt3">
									<span id="userNm" class="color_blue"></span>
								</div></td>
						</tr>
						<tr>
							<td class="align_right td_width90 pr10">利用権限</td>
							<td class="td_width3 orange_line"></td>
							<td class="td_width270 pl3"><input
								class="txt_width100 float_left" type="text" value="01AAA"
								id="userRodeWk" maxlength="8"> </td>
						</tr>
						<tr>
							<td class="align_right td_width90 pr10">所属事業所</td>
							<td class="td_width3 orange_line"></td>
							<td class="td_width270 pl3"><input
								class="txt_width100 float_left" type="text" value="4"
								id="myOfficeWk" maxlength="8"> </td>
							
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
						<col class="td_width8per">
						<col class="td_width32per">
						<col class="td_width8per">
						<col class="td_width30per">
						<col class="td_width8per">
						<col class="td_width11per">
						<tr>
							<th>No</th>
							<th>得意先コード</th>
							<th>得意先名称</th>
							<th>店舗コード</th>
							<th>店舗名称</th>
							<th>便区分</th>
							<th>納品区分</th>
						</tr>
					</table>
				</div>
				<div id="divBody" class="divBody height395">
					<table id="tblBody" class="tbl_list">
						<col class="td_width3per">
						<col class="td_width8per">
						<col class="td_width32per">
						<col class="td_width8per">
						<col class="td_width30per">
						<col class="td_width8per">
						<col class="td_width11per">
						<tr>
							<td class="align_center">1</td>
							<td>0120502</td>
							<td>中ノ島産業（株）</td>
							<td>00007</td>
							<td>中ノ島小食堂</td>
							<td class="align_center">１便</td>
							<td class="align_center">センター</td>
						</tr>
						
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="footer_area bg_gray_c0c0c0">
		<div>
			<button class="btn_button float_left" id="btnRegistration"
				type="button">登録</button>
			<button class="btn_button float_right mr0" id="btnClear"
				type="button">クリア</button>
		</div>
	</div>


	
</div>
<div id="overlay" class="web_dialog_overlay"></div>
	<div id="dialogStoreFinder" class="web_dialog"></div>
