<%--
  ファイル名:com0101d02.jsp
  画面名:トップページ

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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/com/com0101d02.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/com0101d02.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
	<div id="wrapper">
		<div class="content">
			<!-- トップページ画像 -->
			<div class="banner">
				<img src="${pageContext.request.contextPath}/resources/img/img_menu.png" width="1240" height="402" />
			</div>
			<div class="contact_matter_area">
				<h2>&nbsp;&nbsp;&nbsp;連絡事項</h2>
			</div>
			<div class="info_area">
				<table id="tbl_infomation" >
					<c:if test="${not empty matterContactList}">
						<c:forEach items="${matterContactList}" var="matterContact" varStatus="i">
							<tr>
								<td nowrap>${matterContact}</td>
							</tr>
							<tr><td class="height3 border_dot"></td></tr>
							<tr><td class="height3"></td></tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
		</div>
		<div class="footer_area">
			<div></div>
		</div>
		<!--DIALOG-->
		<div id="overlay" class="web_dialog_overlay"></div>
		<div id="dialog" class="web_dialog"></div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>