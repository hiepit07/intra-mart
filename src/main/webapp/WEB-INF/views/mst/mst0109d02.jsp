<!DOCTYPE html>
<%--
  ファイル名:mst0109d02.jsp
  画面名:訂正区分マスタ登録画面

  作成者:ABV）コイー
  作成日:2015/10/28
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/10/28 1.00                  ABV）コイー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0109d02.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0109d02.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<form:form method="POST" action="proc" modelAttribute="formMst0109d02" id="formMst0109d02">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError" class="mst">
					<div>
						<span id="txtMess" class="error">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">						
					<!--condition area-->
				<div class="data_conditions_area">
					<div class="data_conditions_area_cont border_none">
						<div class="condition_title">
							<h3>
								<span>■</span>
								<c:choose>
									<c:when test="${modeView == '1'}">
										新規登録
									</c:when>
									<c:when test="${modeView == '2'}">
										照会
									</c:when>
									<c:when test="${modeView == '3'}">
										訂正
									</c:when>
									<c:when test="${modeView == '4'}">
										取消
									</c:when>
								</c:choose>
							</h3>
						</div>
						<div class="condition_cont">
							<div class="option_condition_area">
								<div class="divOption">
									<div class="divOption_cont">
										<c:choose>
												<c:when test="${modeView == '1'}">
													<span class="mr20 float_left">設定区分</span>
													<form:radiobutton id="chkStKbNotCommon" path="chkStKb" value="notCommon" />
													<label class="mr20" for="chkStKbNotCommon">得意先個別設定</label>
													<form:radiobutton id="chkStKbCommon" path="chkStKb" value="isCommon" />
													<label class="mr20" for="chkStKbCommon">共通設定</label>
												</c:when>
												<c:otherwise>
													<span class="mr20 float_left">設定区分</span>
													<form:radiobutton id="chkStKbNotCommon" path="chkStKb" disabled="true" value="notCommon" />
													<label class="mr20" for="chkStKbNotCommon">得意先個別設定</label>
													<form:radiobutton id="chkStKbCommon" path="chkStKb" disabled="true" value="isCommon" />
													<label class="mr20" for="chkStKbCommon">共通設定</label>
												</c:otherwise>
											</c:choose>
										
									</div>
								</div>
								<table class="tbl_new_registration mb30 tbl_width750">
									<tr>
										<td class="align_right">得意先コード</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1'}">
													<form:input id="txtCustCode" path="txtCustCode" class="txtCustCode" type="text" maxlength="7" />
													<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btn_search_customer_id" alt="検索">
													<span class="color_blue" id="lblCustNmR"></span>
												</c:when>
												<c:otherwise>
													<form:input id="txtCustCode" path="txtCustCode" class="txtCustCode txt_disable" type="text" maxlength="7" readonly="true" tabindex="-1" />
													<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="searchIcon" id="btn_search_customer_id" alt="検索">
													<span class="color_blue" id="lblCustNmR"></span>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td class="height20" colspan="3"></td></tr>
									<tr>
										<td class="align_right">訂正区分</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1'}">
													<form:input id="txtCorrectKb" path="txtCorrectKb" class="txtCorrectKb" type="text" maxlength="4" />
												</c:when>
												<c:otherwise>
													<form:input id="txtCorrectKb" path="txtCorrectKb" class="txtCorrectKb txt_disable" type="text" maxlength="4" readonly="true" tabindex="-1"  />
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td class="height20 border_dot" colspan="3"></td></tr>								
									<tr><td class="height20" colspan="3"></td></tr>
									<tr>
										<td class="align_right">訂正理由</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:input id="txtCorrectCause" path="txtCorrectCause" class="txtCorrectCause" type="text" maxlength="20" />
												</c:when>
												<c:otherwise>
													<form:input id="txtCorrectCause" path="txtCorrectCause" class="txtCorrectCause txt_disable" type="text" maxlength="20" readonly="true" tabindex="-1" />
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td class="height20" colspan="3"></td></tr>
									<tr>
										<td class="align_right">数量ゼロ納品データ連携</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:input id="txtZeroDlvdatFlg" path="txtZeroDlvdatFlg" class="txtZeroDlvdatFlg" type="text" maxlength="1" />
												</c:when>
												<c:otherwise>
													<form:input id="txtZeroDlvdatFlg" path="txtZeroDlvdatFlg" class="txtZeroDlvdatFlg txt_disable" type="text" maxlength="1" readonly="true" tabindex="-1" />
												</c:otherwise>
											</c:choose>
											<span>（1:連携対象 2:連携対象外）</span>
										</td>
									</tr>
									<tr><td class="height20" colspan="3"></td></tr>
									<tr>
										<td class="align_right">備考</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:input id="txtBikou" path="txtBikou" class="txtBikou" type="text" maxlength="25" />
												</c:when>
												<c:otherwise>
													<form:input id="txtBikou" path="txtBikou" class="txtBikou txt_disable" type="text" maxlength="25" readonly="true" tabindex="-1"  />
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td class="height20" colspan="3"></td></tr>
									<tr>
										<td class="align_right">状況コード</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:input id="txtStsCode" path="txtStsCode" class="txtStsCode" type="text" maxlength="1" />
												</c:when>
												<c:otherwise>
													<form:input id="txtStsCode" path="txtStsCode" class="txtStsCode txt_disable" type="text" maxlength="1" readonly="true" tabindex="-1" />
												</c:otherwise>
											</c:choose>
											<span>（１:登録 9:取消）</span>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="footer_area bg_gray_c0c0c0">
				<div>
					<c:choose>
						<c:when test="${modeView == '1' || modeView == '3'}">
							<button id="btnRegister" class="btn_button float_left" type="button">登 録</button>
						</c:when>
						<c:when test="${modeView == '4'}">
							<button id="btnRegister" class="btn_button float_left" type="button">取 消</button>
						</c:when>
						<c:otherwise>
							<button id="btnRegister" class="float_left btn_button_disabled" type="button" disabled="disabled">登 録</button>							
						</c:otherwise>
					</c:choose>
					<button id="btnRegisterHidden" class="display_none" type="submit" name="Register"></button>
					
					<c:choose>
						<c:when test="${modeView == '1' || modeView == '3'}">
							<button id="btnClear" class="btn_button float_right mr0" type="button" name="Clear">クリア</button>							
						</c:when>
						<c:otherwise>
							<button id="btnClear" class="float_right mr0 btn_button_disabled" type="button" name="Clear" disabled="disabled">クリア</button>
						</c:otherwise>
					</c:choose>								
					<button id="btnClearHidden" class="display_none" type="submit" name="Clear"></button>
					<button id="btnBackHidden" type="submit" name="Return" class="display_none"></button>
				</div>
			</div>				
				<!-- hidden proccess-->
				<form:hidden id="form1JigyoshoCode" path="form1JigyoshoCode" />
				<form:hidden id="form1CustCode" path="form1CustCode" />
				<form:hidden id="form1CustName" path="form1CustName" />
				<form:hidden id="form1CorrectKb" path="form1CorrectKb" />
				<form:hidden id="form1ZeroDlvdatFlg" path="form1ZeroDlvdatFlg" />
				<form:hidden id="form1StsCode" path="form1StsCode" />
				<form:hidden id="form1StKb" path="form1StKb" />
				<form:hidden id="custCodeNone" path="custCodeNone" />
				<form:hidden id="mode" path="mode" />
				<form:hidden id="lblCustNmRHidden" path="lblCustNmRHidden" />
				<form:hidden id ="errorControl" path="errorControl"/>
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="haitaDate" path="haitaDate" />
				<form:hidden id="haitaTime" path="haitaTime" />
				<form:hidden id="stKbSelect" path="stKbSelect" />
				
				<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
				<input type="hidden" id="infoMessFlag" value="${infoMessFlag}" />
				
				<!-- 最大表示件数 -->
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
								
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>