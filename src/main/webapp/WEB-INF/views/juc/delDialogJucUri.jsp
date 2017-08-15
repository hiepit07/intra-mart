<%--
  ファイル名:delDialogJucUri.jsp
  画面名:取消処理モード選択

  作成者:都築電気)関口
  作成日:2015/11/20

  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/11/20 1.00                  都築電気)関口       新規開発
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
	href="${pageContext.request.contextPath}/resources/css/juc/delDialogJucUri.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/juc/delDialogJucUri.js" />"
	type="text/javascript"></script>

</head>
<body>
	<form>
		<div id="delModeArea" class="delModeArea">

			<!-- タイトル -->
			<div class="d_title">
				<p id="txt_d_title">取消処理モード選択</p>
			</div>

			<!-- 処理選択 -->
			<div id="divSubCondition" class="divSubCondition">
				<input type="radio" name="delMode_DelDialogJucUri" value="0" checked="checked">${dspModeTitle}取消
				<br><br>
				<input type="radio" name="delMode_DelDialogJucUri" value="1">${dspModeTitle}データ無効化
				<br>
			</div>

			<!-- ボタン -->
			<div id="d_footer" class="d_footer">
				<button id="okButton_DelDialogJucUri" class="okButton" type="button">Ｏ　Ｋ</button>
				<button id="cancelButton_DelDialogJucUri" class="cancelButton" type="button">キャンセル</button>
			</div>
		</div>
	</form>

</body>
