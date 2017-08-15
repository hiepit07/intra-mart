<!DOCTYPE html>
<%--
  ファイル名:error.jsp
  画面名:共通エラー画面

  作成者:都築)安延
  作成日:2015/12/07

  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/12/07 1.00                  都築）安延        新規開発
  -------------------------------------------------------------------------

  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>error</title>
</head>
<body>
<h1>
    共通エラー画面（仮）
</h1>
<P>  ${message} </P>
<P>  ${sysDateTime}</P>
</body>
</html>
