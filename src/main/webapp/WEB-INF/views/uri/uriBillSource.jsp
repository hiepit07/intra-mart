
<%--
  ファイル名:uriBillSource.jsp
  画面名:請求元売上情報選択ダイアログ

  作成者:アクト）前田
  作成日:2015/12/10

  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/12/10 1.00                  アクト)前田       新規開発
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
	href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonSub.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/uri/uriBillSource.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/uri/uriBillSource.js" />"
	type="text/javascript"></script>

</head>
<body class="d_body">
	<form:form action="proc" method="POST" cssErrorClass="error-input"
		modelAttribute="formUri0103d01" id="formUri0103d01">
		<div id="searchSubArea" class="searchSubArea">
			<!-- btn close-->
			<span id="cancelUriBillSrc" class="close">［閉じる］</span>

			<!-- page title -->
			<div class="d_title">
				<p id="txt_d_title">請求元選択</p>
			</div>

			<!--page note output -->
			<div id="noteOutput"></div>

			<!-- tbl result-->
			<div id="divSearchResult" class="divSearchResult">
				<div id="divSearchResult_head" class="divSearchResult_head">
					<table id="tblSearchResult_head" class="tblSearchResult_head">
						<thead>
							<tr>
								<th>納品日</th>
								<th>便</th>
								<th colspan="2">得意先</th>
								<th colspan="2">店舗</th>
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
			<form:input type="hidden" path="dlgTxtMessage" id="dlgTxtMessage" />

			<!-- default message start -->
			<c:forEach items="${defaultMessages}" var="defaultMessages"
				varStatus="status">
				<input type="hidden" id="${defaultMessages.messageCode}"
					class="defaultMessages" name="defaultMessages"
					value="${defaultMessages.messageContent}" title="${defaultMessages.messageTitle}" />
			</c:forEach>
		</div>
	</form:form>
</body>
