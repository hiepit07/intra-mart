<!DOCTYPE html>
<%--
  ファイル名:mst0101d02.jsp
  画面名:担当者マスタ登録画面

  作成者:ABV）コイー
  作成日:2015/08/24
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/08/24 1.00                  ABV）コイー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0101d02.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0101d02.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<form:form method="POST" action="proc" modelAttribute="formMst0101d02" id="formMst0101d02">
		<div id="wrapper">
			<!--page note output -->
			<div id="messError" class="messError_error">
				<div>
					<span id="txtMess">
						<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">
							${errorMessages.messageContent}<br />
						</c:forEach>
					</span>
				</div>
			</div>
	
			<div class="content">
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
							<table id="tblNewRegistration" class="tbl_new_registration mb30">
								<tr>
									<td class="align_right"><span>担当者コード</span></td>
									<td class="td_width3 orange_line"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1'}">
												<form:input id="txtUserCode" path="txtUserCode" class="txt_width80" type="text" maxlength="8" />
											</c:when>
											<c:otherwise>
												<form:input id="txtUserCode" path="txtUserCode" class="txt_width80 txt_disable" type="text" maxlength="8"
													readonly="true" tabindex="-1" />
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="6"></td>
								</tr>
	
								<tr>
									<td colspan="9" class="height10 border_dot"></td>
								</tr>
								<tr>
									<td colspan="9" class="height10"></td>
								</tr>
	
								<tr>
									<td class="align_right"><span class="normal">担当者氏名</span></td>
									<td class="td_width3 orange_line"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtUserName" path="txtUserName"
													class="txt_width160 imeActive" type="text" maxlength="20" />
											</c:when>
											<c:otherwise>
												<form:input id="txtUserName" path="txtUserName"
													class="txt_width160 imeActive txt_disable" type="text" maxlength="20"
													readonly="true" tabindex="-1" />
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="6"></td>
								</tr>
								<tr>
									<td class="align_right"><span>担当者氏名カナ</span></td>
									<td></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtUserNameKata" path="txtUserNameKata"
													class="txt_width160 imeActive" type="text" maxlength="30" />
											</c:when>
											<c:otherwise>
												<form:input id="txtUserNameKata" path="txtUserNameKata"
													class="txt_width160 imeActive txt_disable" type="text" maxlength="30"
													readonly="true" tabindex="-1" />
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="6"></td>
								</tr>
	
								<tr>
									<td colspan="9" class="height10"></td>
								</tr>
	
								<tr>
									<td class="align_right"><span>所属事業所</span></td>
									<td class="td_width3 orange_line"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:select id="ddlShozoku" path="ddlShozoku" class="cbx_width185">
													<form:options items="${ShozokuClassList}" itemLabel="name" itemValue="code" />
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select id="ddlShozoku" path="ddlShozoku" class="cbx_width185" disabled="true">
													<form:options items="${ShozokuClassList}" itemLabel="name" itemValue="code" />
												</form:select>
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="6"></td>
								</tr>
	
								<tr>
									<td colspan="9" class="height10 border_dot"></td>
								</tr>
								<tr>
									<td colspan="9" class="height10"></td>
								</tr>
	
								<tr>
									<td class="align_right"><span>利用権限</span></td>
									<td class="td_width3 orange_line"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:select id="ddlRiyoKengen" path="ddlRiyoKengen" class="cbx_width185" name="loadDataForRiyoKengen">
													<form:options items="${RiyoKengenClassList}" itemLabel="name" itemValue="code" />
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select id="ddlRiyoKengen" path="ddlRiyoKengen" class="cbx_width185" name="loadDataForRiyoKengen" disabled="true">
													<form:options items="${RiyoKengenClassList}" itemLabel="name" itemValue="code" />
												</form:select>
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="6"></td>
								</tr>
	
								<tr>
									<td colspan="9" class="height10"></td>
								</tr>
	
								<tr>
									<td class="align_right"><span>部署名</span></td>
									<td></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtDivisionName" path="txtDivisionName"
													class="txt_width200 imeActive" type="text" maxlength="25" />
											</c:when>
											<c:otherwise>
												<form:input id="txtDivisionName" path="txtDivisionName"
													class="txt_width200 imeActive txt_disable" type="text" readonly="true" tabindex="-1" maxlength="25" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="align_right">メールアドレス</td>
									<td class="w3"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtAddress" path="txtAddress"
													class="txt_width200" type="text" maxlength="25" />
											</c:when>
											<c:otherwise>
												<form:input id="txtAddress" path="txtAddress"
													class="txt_width200 txt_disable" type="text" readonly="true" tabindex="-1" maxlength="25" />
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="3"></td>
								</tr>
	
								<tr>
									<td class="align_right"><span>電話番号</span></td>
									<td></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtTel1" path="txtTel1"
													class="txt_width45" type="text" maxlength="18" /> -
													<form:input id="txtTel2" path="txtTel2"
													class="txt_width45" type="text" maxlength="18" /> -
													<form:input id="txtTel3" path="txtTel3"
													class="txt_width45" type="text" maxlength="18" />
											</c:when>
											<c:otherwise>
												<form:input id="txtTel1" path="txtTel1"
													class="txt_width45 txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="18" /> -
													<form:input id="txtTel2" path="txtTel2"
													class="txt_width45 txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="18" /> -
													<form:input id="txtTel3" path="txtTel3"
													class="txt_width45 txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="18" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="align_right"><span>FAX番号</span></td>
									<td class="w3"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtFax1" path="txtFax1"
													class="txt_width45" type="text" maxlength="18" /> -
													<form:input id="txtFax2" path="txtFax2"
													class="txt_width45" type="text" maxlength="18" /> -
													<form:input id="txtFax3" path="txtFax3"
													class="txt_width45" type="text" maxlength="18" />
											</c:when>
											<c:otherwise>
												<form:input id="txtFax1" path="txtFax1"
													class="txt_width45 txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="18" /> -
													<form:input id="txtFax2" path="txtFax2"
													class="txt_width45 txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="18" /> -
													<form:input id="txtFax3" path="txtFax3"
													class="txt_width45 txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="18" />
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="3"></td>
								</tr>
	
								<tr>
									<td colspan="9" class="height10"></td>
								</tr>
	
								<tr>
									<td class="align_right"><span>状況コード</span></td>
									<td class="td_width3 orange_line"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:input id="txtStatusCode" path="txtStatusCode"
													class="txt_width20 align_center" type="text" maxlength="1" />
											</c:when>
											<c:otherwise>
												<form:input id="txtStatusCode" path="txtStatusCode"
													class="txt_width20 align_center txt_disable" type="text" readonly="true" tabindex="-1"
													maxlength="1" />
											</c:otherwise>
										</c:choose> （1:登録 9:取消）
									</td>
									<td colspan="6"></td>
								</tr>
	
								<tr>
									<td colspan="9" class="height10 border_dot"></td>
								</tr>
								<tr>
									<td colspan="9" class="height10"></td>
								</tr>
	
								<tr>
									<c:choose>
										<c:when test="${modeView != '1'}">
											<td class="align_right"><span>システム利用</span></td>
										</c:when>
										<c:otherwise>
											<td class="align_right"></td>
										</c:otherwise>
									</c:choose>
									<td class="td_width3"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '3'}">
												<form:checkbox id="chkSystemUse" path="chkSystemUse" />
												<label for="chkSystemUse">停止</label>
											</c:when>
											<c:when test="${modeView == '1'}">
												<form:checkbox id="chkSystemUse" path="chkSystemUse" class="display_none"/>
												<label for="chkSystemUse" class="display_none">停止</label>
											</c:when>
											<c:otherwise>
												<form:checkbox id="chkSystemUse" path="chkSystemUse" disabled="true" />
												<label for="chkSystemUse">停止</label>
											</c:otherwise>
										</c:choose>
									</td>
									<c:choose>
										<c:when test="${modeView != '1'}">
											<td class="align_right">パスワード</td>
										</c:when>
										<c:otherwise>
											<td class="align_right"></td>
										</c:otherwise>
									</c:choose>
									<td class="td_width3"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '3'}">
												<form:checkbox id="chkPassword" path="chkPassword" />
												<label for="chkPassword">初期化</label>
											</c:when>
											<c:when test="${modeView == '1'}">
												<form:checkbox id="chkPassword" path="chkPassword"
													class="display_none" />
												<label for="chkPassword" class="display_none">初期化</label>
											</c:when>
											<c:otherwise>
												<form:checkbox id="chkPassword" path="chkPassword"
													disabled="true" />
												<label for="chkPassword">初期化</label>
											</c:otherwise>
										</c:choose>
									</td>
									<c:choose>
										<c:when test="${modeView != '1'}">
											<td class="align_right pr10">ロックアウト</td>
										</c:when>
										<c:otherwise>
											<td class="align_right"></td>
										</c:otherwise>
									</c:choose>								
									<td class="td_width3"></td>
									<td>
										<c:choose>
											<c:when test="${modeView == '3'}">
												<form:checkbox id="chkLogout" path="chkLogout" />
												<label for="chkLogout">ロック中</label>
											</c:when>
											<c:when test="${modeView == '1'}">
												<form:checkbox id="chkLogout" class="display_none" path="chkLogout" />
												<label for="chkLogout" class="display_none">ロック中</label>
											</c:when>
											<c:otherwise>
												<form:checkbox id="chkLogout" path="chkLogout"
													disabled="true" />
												 <label for="chkLogout">ロック中</label>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="footer_area bg_gray_c0c0c0">
				<div>
					<c:choose>
						<c:when test="${modeView != '2'}">
							<button id="btnRegister" class="btn_button float_left" type="button">登 録</button>
							<button id="btnRegisterHidden" class="display_none" type="submit" name="Register"></button>
						</c:when>
					</c:choose>
					<button id="btnClear" class="btn_button float_right mr0" type="button" name="Clear">クリア</button>
					<button id="btnClearHidden" class="display_none" type="submit" name="Clear"></button>
				</div>
			</div>
	
			<form:input id="businessDateID" type="hidden" path="businessDate" />
			<form:input id="form1JigyoshoCodeId" type="hidden" path="form1JigyoshoCode" />
			<form:input id="form1UserCodeId" type="hidden" path="form1UserCode" />
			<form:input id="form1UserNmId" type="hidden" path="form1UserNm" />
			<form:input id="form1AuthCodeId" type="hidden" path="form1AuthCode" />
			<form:input id="form1CancelDataId" type="hidden" path="form1CancelData" />
			<form:input id="form1UserStatusId" type="hidden" path="form1UserStatus" />
			<form:input id="shozokuSelect" type="hidden" path="shozokuSelect" />
			<form:input id="riyoKengenSelect" type="hidden" path="riyoKengenSelect" />
			<form:input id="txtTelResult" type="hidden" path="txtTelResult" />
			<form:input id="txtFaxResult" type="hidden" path="txtFaxResult" />
			<form:input id="mode" type="hidden" path="mode" />
			<form:input id="haitaDate" type="hidden" path="haitaDate" />
			<form:input id="haitaTime" type="hidden" path="haitaTime" />
			<form:hidden id ="errorControl" path="errorControl"/>
			<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
			<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
			<button id="btnBackHidden" type="submit" name="Return" class="display_none"></button>
			
			<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
			<input type="hidden" id="infoMessFlag" value="${infoMessFlag}" />
			
			<!-- default message -->
			<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
				<input type="hidden" id="${defaultMessage.messageCode}"
					name="defaultMessages" value="${defaultMessage.messageContent}"
					title="${defaultMessage.messageTitle}" />
			</c:forEach>
	
			<!--DIALOG-->
			<div id="overlay" class="web_dialog_overlay"></div>
			<div id="dialog" class="web_dialog"></div>
			<!--end dialog-->
		</div>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>