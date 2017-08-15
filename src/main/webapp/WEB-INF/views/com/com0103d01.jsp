<!DOCTYPE html>
<%--
  ファイル名:com0103d01.jsp
  画面名:担当者マスタ一覧画面

  作成者:ABV）TramChu
  作成日:2015/08/24
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/08/24 1.00                  ABV）TramChu        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/com/com0103d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/com/com0103d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formCom0103d01" id="formCom0103d01">	
			<div id="wrapper">		
				<!--page note output -->
				<div id="messError" class="messError_error">
				  <div>
				    <span id="txtMess">
					    <c:forEach items="${errorMessages}"
					      var="errorMessages" varStatus="status">
					      ${errorMessages.messageContent}<br />
					     </c:forEach>
				    </span>
				  </div>
				 </div>
				<div class="content">
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title"><h3><span>■</span>データ呼び出し条件</h3></div>
							<div class="condition_cont">
								<table id="tblDataCondition" class="tbl_data_condition">
									<tr class="${displayDdlDiaJigyo}">
											<td class="align_right td_width175 ">事業所</td>
											<td class="td_width3 orange_line"></td>
											<td  class="td_width1400">
												<form:select id="ddlDiaJigyo" path="ddlDiaJigyo" class="cbx_width185 ${displayDdlDiaJigyo}">
			        								<form:options items="${ddlDiaJigyoLst}" itemLabel="name" itemValue="code" />
			     								</form:select>
											</td>
										</tr>
										<tr>
											<td class="align_right td_width80">処理日付</td>
											<td class="td_width3 orange_line"></td>
											<td>
												<form:input path="txtDiaDateFromExec" id="txtDiaDateFromExec" class="txt_width106 mr5 align_center" type="text" maxlength="10" value=""/>										
												<form:input path="txtDiaTimeFromExec" id="txtDiaTimeFromExec"  class="txt_width46 align_center" type="text" maxlength="4" value=""/>
												<span class="pl10 pr10">～</span>
												<form:input path="txtDiaDateToExec" id="txtDiaDateToExec" class="txt_width106 mr5 align_center" type="text" maxlength="10" value=""/>
												<form:input path="txtDiaTimeToExec" id="txtDiaTimeToExec" class="txt_width46 align_center" type="text" maxlength="4" value=""/>
												<span class="pl10"></span>
											</td>
										</tr>
										<tr>
											<td class="align_right">処理種別</td>
											<td class="td_width3"></td>
											<td>
												<form:select id="ddlDiaTypExec" path="ddlDiaTypExec" class="cbx_width132">
			        								<form:options items="${ddlDiaTypExecLst}" itemLabel="name" itemValue="code" />
			     								</form:select>
											</td>
										</tr>
										<tr>
											<td class="align_right td_width175">処理ジョブ</td>
											<td class="td_width3"></td>
											<td>
												<form:select id="ddlDiaJobExec" path="ddlDiaJobExec" class="cbx_width312">
			        								<form:options items="${ddlDiaJobExecLst}" itemLabel="name" itemValue="code" />
			     								</form:select>
											</td>
										</tr>
										<tr>
											<td class="align_right td_width175">処理担当者</td>
											<td class="td_width3"></td>
											<td>
												<form:input path="txtDiaUserCodeExec" id="txtDiaUserCodeExec" class="txt_width106 float_left" type="text" maxlength="8" value=""/>
												<img id="btn_search_customer_id05" class="searchIcon cursor_pointer" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
												<span class="color_blue" id="lblDiaUserNmExec">${DiaUserNmExec}</span>
											</td>
										</tr>
								</table>
							</div>
							<div class="condition_cont mt5">
								<table class="tbl_data_condition_bot">
									<tr>
										<td class="td_width164">&nbsp;</td>
										<td class="align_left td_width330"><form:checkbox id="chkDiaRsJob" path="chkDiaRsJob"/>
											<label for="chkDiaRsJob" class="color_black"> 配信ジョブを対象とする</label></td>
										<td class="td_width400"><form:checkbox id="chkDiaSsJob" path="chkDiaSsJob"/>
											<label for="chkDiaSsJob" class="color_black"> 集信ジョブを対象とする</label></td>
										<td><form:checkbox id="chkDiaIjJob" path="chkDiaIjJob"/>
											<label for="chkDiaIjJob" class="color_black">異常ジョブのみを対象とする</label></td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnSearch" class="btn_button float_left" type="button">表示</button>
								<button id="btnConditionClear" class="btn_button float_left" type="button">条件クリア</button>
							</div>
						</div>
					</div>
					<!-- registration data result area -->
					<div class="data_result_area clear  pt20">
						<div class="div_zentai">
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width10per">
									<col class="td_width18per">
									<col class="td_width12per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width13per">
									<tr>
										<th></th>
										<th>No</th>
										<th>種別</th>
										<th>ジョブID</th>
										<th>ジョブ名</th>
										<th>処理担当者</th>
										<th>開始日</th>
										<th>開始時刻</th>
										<th>終了日</th>
										<th>終了時刻</th>
										<th>処理結果</th>
										<th>メッセージ</th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height350">						
								<table id="tblBody" class="tbl_list">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width10per">
									<col class="td_width18per">
									<col class="td_width6per">
									<col class="td_width6per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width13per">
									<tbody id="tbl_tbody">										
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div> <!--/content-->
				<div class="footer_area bg_gray_c0c0c0">
					<div class="float_left">
						<button id="btnReproces"class="btn_button_disabled float_left width105" type="button" disabled>再処理</button>
					</div>
				</div>		
				<form:input id="sysAdminFlag" type="hidden" path="sysAdminFlag"/>
				<form:input id="loginJigyouShoCode" type="hidden" path="loginJigyouShoCode"/>
				<form:input id="officeCodeWk" type="hidden" path="officeCodeWk"/>
				<form:input id="loginOfficeName" type="hidden" path="loginOfficeName"/>
				<form:input id="systemDate" type="hidden" path="systemDate"/>
				<form:input id="loginUserID" type="hidden" path="loginUserId"/>
				<form:input id="searchMax" type="hidden" path="searchMax"/>
				<form:input id="haitaDate" type="hidden" path="haitaDate" />
				<form:input id="haitaTime" type="hidden" path="haitaTime" />
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
		
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>							
				<!--end dialog-->
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>