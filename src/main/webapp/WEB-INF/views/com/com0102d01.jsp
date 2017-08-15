<%--
  ファイル名:com0102d01.jsp
  画面名:チェーン検索画面

  作成者:ABV）グエン　リユオン　ギア
  作成日:2015/09/25
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/25 1.00                  ABV）グエン　リユオン　ギア       新規開発
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
	href="${pageContext.request.contextPath}/resources/css/com/com0102d01.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonSub.css" />

<script src="<c:url value="/resources/js/com/com0102d01.js" />"
	type="text/javascript"></script>
</head>
<body>
	<form:form action="proc" method="POST" cssErrorClass="error-input"
		modelAttribute="formCom0102d01" id="formCom0102d01">
		<div id="searchSubArea" class="searchSubArea">
			<!-- btn close-->
			<span id="cancelCom0102d01" class="close">［閉じる］</span>

			<!-- page title -->
			<div class="d_title">
				<p id="txt_d_title">チェーン検索</p>
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
						<td class="w20">チェーンコード</td>
						<td>
							<form:input id="txtDiaChainCode" class="txtDiaChainCode"
								type="text" maxlength="4" path="txtChainCode" oncopy="return false"
								onpaste="return false" oncontextmenu="return false" />&nbsp;- 
							<form:input id="txtDiaChainBranch" class="txtDiaChainBranch"
								type="text" maxlength="2" path="txtChainBranch" oncopy="return false"
								onpaste="return false" oncontextmenu="return false" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="w20">チェーン名称</td>
						<td>
							<form:input id="txtDiaChainName" class="txtDiaChainName" type="text"
								maxlength="20" path="txtChainName" oncopy="return false"
								onpaste="return false" oncontextmenu="return false" /><span>※あいまい検索</span></td>
					</tr>
				</table>
			</div>
			<!-- btn search -->
			<div>
				<button class="searchButton" id="searchButtonCom010201" type="button">検索</button>
			</div>

			<!-- tbl result-->
			<div id="divSearchResult" class="divSearchResult">
				<div id="divSearchResult_head" class="divSearchResult_head">
					<table id="tblSearchResult_head" class="tblSearchResult_head">
						<thead>
							<tr>
								<th class="w20">チェーンコード</th>
								<th>チェーン名称</th>
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
			<form:input type="hidden" path="searchMax" id="diaSearchMax" />
			<!-- 最大表示件数 -->


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