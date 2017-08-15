<%--
  ファイル名:header.jsp
  画面名: None

  作成者:ABV）QuanTran
  作成日:2015/11/09
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/11/09 1.00 ABV)QuanTran 新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!-- page header -->
	<div id="header">
		<div class="header_bg">
			<div class="header_cont">
				<img class="logo" src="${pageContext.request.contextPath}/resources/img/img_logo.png" width="205" height="30" alt="債権管理システム">
				<div class="drop-menu float_right">
					<ul>
						<li>
							<a href="#">
								<span id="person_name">${persName} ▼</span>
							</a>
							<ul>
								<li><a href="${pageContext.request.contextPath}/com/COM01-01D03/">パスワード変更</a></li>
								<li><a href="${pageContext.request.contextPath}/logout" onclick="javascript:return confirm('ログアウトします。よろしいですか？');">ログアウト</a></li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="rightHeader">
					<span id="company_name">${compName}</span>
				</div>
			</div>
		</div>
	</div>
	<!-- page title -->
	<div class="title">
		<div class="title_cont">
			<h2>${screenName}</h2>
		</div>
		<div class="show_hide_area">
		<!-- show hide button -->
			<div class="show_hide">
				<div id="btn_show"><img src="${pageContext.request.contextPath}/resources/img/icon_menu.png" alt="メニュー"></div>
			</div>
		</div>
	</div>
	<!-- dialog onMenu -->
	<div id="dialogonmenu" class="web_dialog_onmenu">
		<div id="btn_close_menu" class="cursor_hover">
			<img src="${pageContext.request.contextPath}/resources/img/btn_close.png" alt="閉じる">
		</div>
		<div id="onMenu">
			<div class="menu_header">
				<h2 id="goMenuButton">
					<a href="${pageContext.request.contextPath}/com/COM01-01D02/">
						<span>メインメニュー</span>
					</a>
				</h2>
			</div>
			<div id="menu">
				<c:if test="${not empty menuList}">
					<ul class="par_menu">
					<c:forEach items="${menuList}" var="menu" varStatus="i">
						<li>
							<p><span>${menu.businessFuncName}</span></p>
							<ul class="sub_menu">
							<c:forEach items="${menu.urlList}" var="url" varStatus="j">
								<li><a href="${url.url}">${url.funcName}</a></li>
							</c:forEach>
							</ul>
						</li>
					</c:forEach>
					</ul>
				</c:if>
			</div>
		</div>
	</div>