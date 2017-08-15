<%--
  ファイル名:com0102d02.jsp
  画面名:得意先検索子画面

  作成者:都築電気)関口
  作成日:2015/09/10

  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/10 1.00                  都築電気)関口       新規開発
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
	href="${pageContext.request.contextPath}/resources/css/com/com0102d02.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonSub.css" />

<script src="<c:url value="/resources/js/com/com0102d02.js" />"
	type="text/javascript"></script>

</head>
<body>
	<form:form action="proc" method="POST" cssErrorClass="error-input"
		modelAttribute="formCom0102d02" id="formCom0102d02">
		<div id="searchSubArea" class="searchSubArea">
			<!-- btn close-->
			<span id="cancelCom0102d02" class="close">［閉じる］</span>

			<!-- page title -->
			<div class="d_title">
				<p id="txt_d_title">得意先検索</p>
			</div>

			<!--page note output -->
			<div id="noteOutput"></div>

			<!-- tbl 得意先検索 -->
			<div id="divSubCondition" class="divSubCondition">
				<table id="tblSubSearch" class="tblSubSearch">
					<tr class="showJigyosho">
						<td class="w20">事業所</td>
						<td><form:select id="ddlDiaShozoku" path="ddlShozoku">
								<form:options items="${ShozokuClassList}" itemLabel="jgymei"
									itemValue="jgycd" />
							</form:select></td>
					</tr>
					<tr>
						<td class="w20">得意先コード</td>
						<td><form:input id="txtDiaCustCode" class="txtDiaCustCode"
								type="text" maxlength="7" path="custCode" oncopy="return false"
								onpaste="return false" oncontextmenu="return false" /></td>
					</tr>
					<tr>
						<td>得意先名称</td>
						<td><form:input id="txtDiaCustNm" class="txtDiaCustNm" type="text"
								maxlength="20" path="custNm" oncopy="return false"
								onpaste="return false" oncontextmenu="return false" /><span>※あいまい検索</span></td>
					</tr>
				</table>
			</div>
			<!-- btn search -->
			<div>
				<button id="searchButtonCom010202" class="searchButton" type="button">検 索</button>
			</div>

			<!-- tbl result-->
			<div id="divSearchResult" class="divSearchResult">
				<div id="divSearchResult_head" class="divSearchResult_head">
					<table id="tblSearchResult_head" class="tblSearchResult_head">
						<thead>
							<tr>
								<th class="w20">得意先コード</th>
								<th>得意先名称</th>
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
			<form:input type="hidden" path="txtMessage" id="diaTxtMessage" />
			<!-- エラーメッセージ -->
			<form:input type="hidden" path="searchMax" id="diaSearchMax" />
			<!-- 最大表示件数 -->
			<form:input type="hidden" path="businessDate" id="diaBusinessDate" />
			<!-- 業務日付 -->
			<form:input type="hidden" path="jigyoshoCode" id="diaJigyoshoCode" />
			<!-- 事業所コード（親画面引数） -->
			<form:input type="hidden" path="shopKb" id="diaShopKb" />
			<!-- 店舗区分（親画面引数） -->
			<form:input type="hidden" path="searchKb" id="diaSearchKb" />
			<!-- 検索対象区分（親画面引数） -->

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
