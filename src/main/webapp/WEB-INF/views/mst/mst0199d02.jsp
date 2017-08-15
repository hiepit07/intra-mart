<!DOCTYPE html>
<%--
  ファイル名:mst0199d02.jsp
  画面名:汎用マスタ登録画面

  作成者:ABV）QuanTran
  作成日:2015/9/23
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/9/23 1.00                  ABV）QuanTran        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0199d02.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0199d02.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<form:form id="formMst0199d02" action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0199d02">
		<div id="messError" class="messError_error">
			<div>
				<span id="txtMess"> 
					<c:forEach items="${errorMessages}"
						var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
					</c:forEach>
				</span>
			</div>
		</div>
		<div id="wrapper">
			<div class="content">
				<!--condition area-->
				<div class="data_conditions_area">
					<div class="data_conditions_area_cont border_none">
						<div class="condition_title">
							<h3>
								<span>■</span>
								<c:choose>
									<c:when test="${viewMode == 2}">
										照会
									</c:when>
									<c:when test="${viewMode == 3}">
										訂正
									</c:when>
								</c:choose>
							</h3>
						</div>
						<div class="condition_cont">
							<table class="tbl_data_condition_cont">
								<tr>
									<td class="td_width25">区分</td>
									<td class="td_width3"></td>
									<td id="glKb" class="td_width250">${glKb}</td>
									<td class="td_width80 align_right">区分名称</td>
									<td class="td_width35"></td>
									<td id="glKbNm" class="td_width110">${glKbNm}</td>
									<td></td>
									<td class="td_width3"></td>
									<td></td>
								</tr>
								<tr>
									<td class="td_width25"></td>
									<td class="td_width3"></td>
									<td class="td_width250"></td>
									<td class="td_width80 align_right">コード名称</td>
									<td class="td_width35"></td>
									<td class="td_width110">${codeNm}</td>
									<td></td>
									<td class="td_width3"></td>
									<td></td>
								</tr>
								<tr>
									<td class="td_width25"></td>
									<td class="td_width3"></td>
									<td class="td_width250"></td>
									<td class="td_width80 align_right">コード属性</td>
									<td class="td_width35"></td>
									<td class="td_width110">${codeAttr}</td>
									<td class="td_width80 align_right">コード桁数</td>
									<td class="td_width3"></td>
									<td>${codeDigit}</td>
								</tr>
								<tr>
									<td class="td_width25"></td>
									<td class="td_width3"></td>
									<td class="td_width250"></td>
									<td class="td_width80 align_right">分類</td>
									<td class="td_width35"></td>
									<td class="td_width110">${category}</td>
									<td class="td_width80 align_right">表示順</td>
									<td class="td_width3"></td>
									<td>${dispNumeric}</td>
								</tr>
								<tr>
									<td class="height20" colspan="9"></td>
								</tr>
								<tr>
									<td class="td_width25"></td>
									<td class="td_width3"></td>
									<td class="td_width250"></td>
									<td class="td_width80 align_right">権限</td>
									<td class="td_width35"></td>
									<td colspan="4">
										<input id="updPermitFlg" type="checkbox" disabled="disabled">
										<label for="updPermitFlg" class="color_black pr20">更新可能</label>
											<form:hidden id="updFlg"  path="updPermitFlg"/>
											
										<input id="insPermitFlg" type="checkbox" disabled="disabled">
										<label for="insPermitFlg" class="color_black pr20">追加可能</label>
											<form:hidden id="insFlg"  path="insPermitFlg"/>
											
										<input id="delPermitFlg" type="checkbox" disabled="disabled">
										<label for="delPermitFlg" class="color_black">削除可能</label>
											<form:hidden id="delFlg"  path="delPermitFlg"/>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="data_conditions_area">
				<c:if test="${viewMode == 3}">
					<div class="data_conditions_input_cont">
						<div class="input_area">
						
							<table class="tbl_data_condition_search">
								<tr>
									<td class="align_right td_width120 pr10">コード</td>
									<td class="td_width3 orange_line"></td>
									<td class="pl3">
										<form:input id="gpCode" path="gpCode" class ="txt_width100" maxlength="20"/>
									</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<c:if test="${title1 != '' && not empty title1}">
										<td id="title1" class="align_right td_width120 pr10" title="${targetExp1}">${title1}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue1" path="itemValue1" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
									<c:if test="${title6 != '' && not empty title6}">
										<td id="title6"class="align_right td_width120 pr10" title="${targetExp6}">${title6}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue6" path="itemValue6" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${title2 != '' && not empty title2}">
									<td id="title2" class="align_right td_width120 pr10" title="${targetExp2}">${title2}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue2" path="itemValue2" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
									<c:if test="${title7 != '' && not empty title7}">
										<td id="title7" class="align_right td_width120 pr10" title="${targetExp7}">${title7}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue7" path="itemValue7" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${title3 != '' && not empty title3}">
									<td id="title3" class="align_right td_width120 pr10" title="${targetExp3}">${title3}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue3" path="itemValue3" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
									<c:if test="${title8 != '' && not empty title8}">
										<td id="title8" class="align_right td_width120 pr10" title="${targetExp8}">${title8}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
												<form:input id="itemValue8" path="itemValue8" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${title4 != '' && not empty title4}">
										<td id="title4" class="align_right td_width120 pr10" title="${targetExp4}">${title4}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue4" path="itemValue4" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
									<c:if test="${title9 != '' && not empty title9}">
										<td id="title9" class="align_right td_width120 pr10" title="${targetExp9}">${title9}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
												<form:input id="itemValue9" path="itemValue9" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${title5 != '' && not empty title5}">
										<td id="title5" class="align_right td_width120 pr10" title="${targetExp5}">${title5}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
												<form:input id="itemValue5" path="itemValue5" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
									<c:if test="${title10 != '' && not empty title10}">
									<td id="title10" class="align_right td_width120 pr10" title="${targetExp10}">${title10}</td>
										<td class="td_width3 orange_line"></td>
										<td class="pl3">
											<form:input id="itemValue10" path="itemValue10" class="txt_width420" maxlength="100"/>
										</td>
									</c:if>
								</tr>
							</table>
							<div class="button_area">
								<button id="btnAddUpdate" class="btn_button float_left ml50" type="button">更新</button>
								<button id="btnClear" class="btn_button float_right mr50" type="button">クリア</button>
							</div>
						</div>
					</div>
				</c:if>
					<form:hidden id="targetAttr1" path="targetAttr1"/>
					<form:hidden id="targetAttr2" path="targetAttr2"/>
					<form:hidden id="targetAttr3" path="targetAttr3"/>
					<form:hidden id="targetAttr4" path="targetAttr4"/>
					<form:hidden id="targetAttr5" path="targetAttr5"/>
					<form:hidden id="targetAttr6" path="targetAttr6"/>
					<form:hidden id="targetAttr7" path="targetAttr7"/>
					<form:hidden id="targetAttr8" path="targetAttr8"/>
					<form:hidden id="targetAttr9" path="targetAttr9"/>
					<form:hidden id="targetAttr10" path="targetAttr10"/>
				
					<form:hidden id="targetDigit1" path="targetDigit1"/>
					<form:hidden id="targetDigit2" path="targetDigit2"/>
					<form:hidden id="targetDigit3" path="targetDigit3"/>
					<form:hidden id="targetDigit4" path="targetDigit4"/>
					<form:hidden id="targetDigit5" path="targetDigit5"/>
					<form:hidden id="targetDigit6" path="targetDigit6"/>
					<form:hidden id="targetDigit7" path="targetDigit7"/>
					<form:hidden id="targetDigit8" path="targetDigit8"/>
					<form:hidden id="targetDigit9" path="targetDigit9"/>
					<form:hidden id="targetDigit10" path="targetDigit10"/>
					
					<form:hidden id="targetExp1" path="targetExp1"/>
					<form:hidden id="targetExp2" path="targetExp2"/>
					<form:hidden id="targetExp3" path="targetExp3"/>
					<form:hidden id="targetExp4" path="targetExp4"/>
					<form:hidden id="targetExp5" path="targetExp5"/>
					<form:hidden id="targetExp6" path="targetExp6"/>
					<form:hidden id="targetExp7" path="targetExp7"/>
					<form:hidden id="targetExp8" path="targetExp8"/>
					<form:hidden id="targetExp9" path="targetExp9"/>
					<form:hidden id="targetExp10" path="targetExp10"/>
				</div>
				<div class="data_result_area clear">
					<div class="div_zentai">
						<div id="divHead" class="divHead">
							<table class="tbl_list">
								<col class="td_width3per">
								<col class="td_width7per">
								<c:forEach var="i" begin="1" end="${numColumn}">
									<col width="${colWidth}">
								</c:forEach>
								<thead id="tbl_thead">
								</thead>
							</table>
						</div>
						<div id="divBody" class="divBody height280">
							<table id="tblBody" class="tbl_list">
								<col class="td_width3per">
								<col class="td_width7per">
								<c:forEach var="i" begin="1" end="${numColumn}">
									<col width="${colWidth}">
								</c:forEach>
								<tbody id="tbl_tbody">
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!--menu-->
				<div id="tableMenu" class="sub-menu">
					<a id="modifySubMenu" name="modifySubMenu" href="#"><img
					src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12" /> 訂正</a>
					<a id="deleteSubMenu" name="deleteSubMenu" href="#"><img
					src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12" /> 行削除</a>
				</div>
			<div class="footer_area bg_gray_c0c0c0">
				<div>
					<c:if test="${viewMode == 3}">
						<button class="btn_button float_left" id="btnRegistration" type="button">登録</button>
						<button id="btnFooterClear" class="btn_button float_right mr0" type="button" name="Clear">クリア</button>
					</c:if>
				</div>
			</div>
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
			</c:forEach>
		</div>
		<!--/content-->
			<form:hidden path="category"/>
			<form:hidden path="kbNm"/>
			<form:hidden id ="glCode" path="glCode"/>
			<form:hidden id="viewMode" path="viewMode"/>
			<form:hidden id ="errorControl" path="errorControl"/>
			<form:hidden id="codeDigit"  path="codeDigit"/>
			<form:hidden id="editMode" path="editMode"/>
			<form:hidden id="errorItem" path="errorItem"/>
			<form:hidden id="no" path="no"/>
			<form:hidden path="title1" />
			<form:hidden path="title2" />
			<form:hidden path="title3" />
			<form:hidden path="title4" />
			<form:hidden path="title5" />
			<form:hidden path="title6" />
			<form:hidden path="title7" />
			<form:hidden path="title8" />
			<form:hidden path="title9" />
			<form:hidden path="title10" />
			<form:hidden id="haitaDate" path="haitaDate" />
			<form:hidden id="haitaTime" path="haitaTime" />
			<input id="eventBack" type="submit" name="eventBack" class="visibility-hidden">
			<input id="clearSubmit" type="submit" name="clear" class="visibility-hidden">
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>