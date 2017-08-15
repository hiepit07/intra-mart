<%--
  ファイル名:com0102d04.jsp
  画面名:品番マスタ登録画面

  作成者:ABV）グエン　リユオン　ギア
  作成日:2015/10/07
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/21 1.00                  ABV）グエン　リユオン　ギア        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible"
	content="IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/com/com0102d04.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonSub.css" />

<script src="<c:url value="/resources/js/com/com0102d04.js" />"
	type="text/javascript"></script>
</head>

<body>
<form:form action="proc" method="POST" cssErrorClass="error-input"
	modelAttribute="formCom0102d04" id="formCom0102d04">
	<div id="searchSubArea" class="searchSubArea">
		<!-- btn close-->
		<span id="cancelCom0102d04" class="close">［閉じる］</span>
		
		<!-- page title -->
		<div class="d_title">
			<p id="txt_d_title">品番検索</p>
		</div>
		
		<!--page note output -->
		<div id="noteOutput">
			<span id="txtDiaMess" class="error">
				<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
				</c:forEach>
			</span>
		</div>
		
		<!-- tbl チェーン検索 -->
		<div id="divSubCondition" class="divSubCondition">
			<table id="tblSubSearch" class="tblSubSearch">
				<tr>
					<td class="w20">品目コード</td>
					<td>
						<form:input class="txtDiaOurCompanyItemCodeWk" type="text" maxlength="8"
							path="ourCompanyItemCodeWk"	oncopy="return false"
							onpaste="return false" id="txtDiaOurCompanyItemCodeWk" oncontextmenu="return false" />
						
					</td>
				</tr>
				<tr>
					<td class="w20">品目名称</td>
					<td>
						<form:input class="txtDiaOurCompanyItemNameWk" type="text" maxlength="20"
						path="ourCompanyItemNameWk" oncopy="return false" onpaste="return false"
						id="txtDiaOurCompanyItemNameWk" oncontextmenu="return false" />
						<span>※あいまい検索</span>
					</td>
				</tr>
			</table>
		</div>		
		
		<!-- btn search -->
		<div>
			<button class="searchButton" id="searchButtonCom010204" type="button">検索</button>
		</div>
		
		<!-- tbl result-->
		<div id="divSearchResult" class="divSearchResult">
			<div id="divSearchResult_head" class="divSearchResult_head">
				<table id="tblSearchResult_head" class="tblSearchResult_head">
					<thead>
						<tr>
							<th class="w20">品目コード</th>
							<th>品目名称</th>
						</tr>
					</thead>
				</table>
			</div>
			<div id="divSearchResult_body" class="divSearchResult_body">
				<table id="tblSearchResult_body" class="tblSearchResult_body">
					<tbody>
					</tbody>
				</table>					
			</div>
		</div>
		<!-- hidden proccess-->
		<form:input type="hidden" path="changeCodeWk" id="diaChangeCodeWk" />
		<!-- チェーンコード（呼出元） -->
		<form:input type="hidden" path="changeBranchWk" id="diaChangeBranchWk" />
		<!-- チェーン枝番（呼出元） -->
		<form:input type="hidden" path="customerCodeWk" id="diaCustomerCodeWk" />
		<!-- 得意先コード（呼出元） -->
		<form:input type="hidden" path="saleTypeWk" id="diaSaleTypeWk" />
		<!-- 販売区分（呼出元）） -->
		<form:input type="hidden" path="deadlineWk" id="diaDeadlineWk" />
		<!-- 納品日（呼出元） -->
		<form:input type="hidden" path="flightWk" id="diaFlightWk" />
		<!-- 便（呼出元）） -->
		<form:input type="hidden" path="searchMax" id="diaSearchMax" />
		<!-- 便（呼出元）） -->
		<!-- hidden proccess-->
		<form:input type="hidden" path="txtDiaMessage" id="txtDiaMessage" />
		
		<!-- default message start -->
		<c:forEach items="${defaultMessages}" var="defaultMessage"
			varStatus="status">
			<input type="hidden" id="${defaultMessage.messageCode}"
				class="defaultMessage" name="defaultMessages"
				value="${defaultMessage.messageContent}"
				title="${defaultMessage.messageTitle}" />
		</c:forEach>
	</div>	
</form:form>
</body>
