<!DOCTYPE html>
<%--
  ファイル名:mst0102d02.jsp
  画面名:得意先マスタ登録画面

  作成者:ABV）コアー
  作成日:2015/09/21
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/21 1.00                  ABV）コアー        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0102d02.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0102d02.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" modelAttribute="formMst0102d02" id="formMst0102d02">	
			<div id="wrapper">
				<!--page note output -->
				<div id="messError" class="mst messError_error">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">
								${errorMessages.messageContent}<br/>
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">
					<!--condition area-->
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont border_none">
							<div class="condition_title"><h3><span>■</span>${viewModeText}<span class="ml0"></span></h3></div>
							<div class="condition_cont">
								<table id="tblNewRegistration" class="tbl_new_registration mb25">
									<!--block1-->
									<tr>
										<td class="td_width120 align_right">得意先コード</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードの場合、得意先コードを活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:input path="txtCustomerCode" class="txt_width96" type="text" maxlength="7" />
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、得意先コードを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:input path="txtCustomerCode" class="txt_width96 txt_disable" type="text" maxlength="7" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr><td class="height3" colspan="6"></td></tr>
									<tr>
										<td class="td_width120 align_right align_top">利用情報</td>
										<td class="td_width3 align_top"><img class="orange_line_shape" src="${pageContext.request.contextPath}/resources/img/orange_line.jpg" width="3" height="23" alt=""></td>
										<td colspan="4">
											<table class="tbl_info_table tbl_width595">
												<col class="td_width50">
												<col class="td_width50">
												<col class="td_width110">
												<col>
												<tr>
													<th>得意先</th>
													<th>請求先</th>
													<th colspan="2">請求先指定</th>
												</tr>
												<tr>
													<c:choose>
														<%-- 新規モードと訂正モードの場合、得意先、請求先、検索ボタンを活性化する --%>
														<c:when test="${viewMode == '1' || viewMode == '3'}">
															<td class="align_center">
																<form:checkbox id="chkCustomer" path="chkCustomer" />
															</td>
															<td class="align_center">
																<form:checkbox id="chkBilling" path="chkBilling" />
															</td>
															<td>
																<form:input path="txtBillingCode" class="txt_width75 border_none" type="text" maxlength="7" />
																<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btnSearchBillingCode" alt="検索">
															</td>
															<td>
																<span id="txtBillingSearchName" class="color_blue"></span>
																<form:hidden path="txtBillingSearchNameHidden" />
															</td>
														</c:when>
														<%-- 照会モードと取消モードの場合、得意先、請求先、検索ボタンを非活性化する --%>
														<c:when test="${viewMode == '2' || viewMode == '4'}">
															<td class="align_center">
																<form:checkbox path="chkCustomer" onclick="return false" />
															</td>
															<td class="align_center">
																<form:checkbox path="chkBilling" onclick="return false" />
															</td>
															<td>
																<form:input path="txtBillingCode" class="txt_width75 border_none align_right txt_disable" type="text" maxlength="7" readonly="true" />
																<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="searchIcon" id="btnSearchBillingCode" alt="検索">
															</td>
															<td>
																<span id="txtBillingSearchName" class="color_blue"></span>
																<form:hidden path="txtBillingSearchNameHidden" />
															</td>
														</c:when>
													</c:choose>
												</tr>
											</table>
										</td>
									</tr>
									<tr><td class="height3 border_dot" colspan="6"></td></tr>
									<tr><td class="height3" colspan="6"></td></tr>
									<!--block2 -->
									<tr>
										<td class="align_right">チェーンコード</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードの場合、チェーンコード、検索ボタンを活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:input path="txtChainCode" class="txt_width45" type="text" maxlength="4" />&nbsp;&nbsp;
													<form:input path="txtChainEda" class="txt_width35" type="text" maxlength="2" />
													<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btnSearchChainCode" alt="検索">
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、チェーンコード、検索ボタンを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:input path="txtChainCode" class="txt_width45 txt_disable" type="text" maxlength="4" readonly="true" />&nbsp;&nbsp;
													<form:input path="txtChainEda" class="txt_width35 txt_disable" type="text" maxlength="2" readonly="true" />
													<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="searchIcon" id="btnSearchChainCode" alt="検索">
												</c:when>
											</c:choose>
											<span id="txtChainName" class="color_blue"></span>
											<form:hidden path="txtChainNameHidden" />
										</td>
									</tr>
									<tr>
										<td class="height3 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height3" colspan="6"></td>
									</tr>
									<!-- block3-->
									<tr>
										<td class="align_right">得意先名称</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、得意先名称を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtCustomerName" class="txt_width530 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、得意先名称を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtCustomerName" class="txt_width530 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">得意先名称カナ</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、得意先名称カナを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtCustomerNameKana" class="txt_width370 imeActive" type="text" maxlength="30" />
												</c:when>
												<%-- 照会モードと取消モードの場合、得意先名称カナを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtCustomerNameKana" class="txt_width370 imeActive txt_disable" type="text" maxlength="30" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">得意先略称</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、得意先略称を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtCustomerNameR" class="txt_width370 imeActive" type="text" maxlength="10" />
												</c:when>
												<%-- 照会モードと取消モードの場合、得意先略称を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtCustomerNameR" class="txt_width370 imeActive txt_disable" type="text" maxlength="10" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right">郵便番号</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、郵便番号を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtPostalCode1" class="txt_width35" type="text" maxlength="9" />
													-
													<form:input path="txtPostalCode2" class="txt_width44" type="text" maxlength="9" />
													（例：639-1038） 
												</c:when>
												<%-- 照会モードと取消モードの場合、郵便番号を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtPostalCode1" class="txt_width35 txt_disable" type="text" maxlength="9" readonly="true" />
													-
													<form:input path="txtPostalCode2" class="txt_width44 txt_disable" type="text" maxlength="9" readonly="true" />
													（例：639-1038） 
												</c:when>
											</c:choose>
											<form:hidden path="txtPostalCodeResult" />
										</td>
									</tr>
									<tr>
										<td class="align_right">住所１</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、住所１を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtAddress1" class="txt_width530 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、住所１を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtAddress1" class="txt_width530 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">住所２</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、住所２を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtAddress2" class="txt_width530 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、住所１を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtAddress2" class="txt_width530 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">電話番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、電話番号を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtTel1" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtTel2" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtTel3" class="txt_width45" type="text" maxlength="18" />
													（例：0743-56-2911）
												</c:when>
												<%-- 照会モードと取消モードの場合、電話番号を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtTel1" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													-
													<form:input path="txtTel2" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													-
													<form:input path="txtTel3" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													（例：0743-56-2911）
												</c:when>
											</c:choose>
											<form:hidden path="txtTelResult" />
										</td>
									</tr>
									<tr>
										<td class="align_right">FAX番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、FAX番号を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtFax1" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtFax2" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtFax3" class="txt_width45" type="text" maxlength="18" />
												</c:when>
												<%-- 照会モードと取消モードの場合、FAX番号を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtFax1" class="txt_width45 txt_disable" type="text" maxlength="8" readonly="true" />
													-
													<form:input path="txtFax2" class="txt_width45 txt_disable" type="text" maxlength="8" readonly="true" />
													-
													<form:input path="txtFax3" class="txt_width45 txt_disable" type="text" maxlength="8" readonly="true" />
												</c:when>
											</c:choose>
											<form:hidden path="txtFaxResult" />
										</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="td_width120 align_right">得意先担当者</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、得意先担当者を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtCustomerTantousha" class="txt_width96 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、得意先担当者を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtCustomerTantousha" class="txt_width96 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">メールアドレス</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、メールアドレスを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtMailAddress" class="txt_width530" type="text" maxlength="50" />
												</c:when>
												<%-- 照会モードと取消モードの場合、メールアドレスを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtMailAddress" class="txt_width530 txt_disable" type="text" maxlength="50" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block4-->
									<tr>
										<td class="td_width120 align_right align_top">取扱事業所</td>
										<td class="td_width3 align_top"><img class="orange_line_shape" src="${pageContext.request.contextPath}/resources/img/orange_line.jpg" width="3" height="23" alt=""></td>
										<td colspan="4">
											<table id="tbl_toriatsukai" class="tbl_info_table tbl_width670 float_left">
												<col class="td_width40">
												<col class="td_width185">
												<col class="td_width110">
												<col class="td_width110">
												<col class="td_width110">
												<col>
												<tr>
													<th>No</th>
													<th>取扱事業所</th>
													<th colspan="2">営業担当者</th>
													<th colspan="2">事務担当者</th>
												</tr>
												<c:choose>
													<%-- 新規モードと訂正モードの場合、取扱事業所を活性化する --%>
													<c:when test="${viewMode == '1' || viewMode == '3'}">
														<c:forEach items="${arrListToriatsukaiJigyouSho}" var="toriatsukaiJigyouSho" varStatus="status">
															<tr>
																<td id="${status.index + 1}" class="align_center contextMenu">${status.index + 1}</td>
																<td class="align_right pr0">
																	<form:select path="arrListToriatsukaiJigyouSho[${status.index}].ddlToriatsukaiJigyouSho" class="ddlToriatsukaiJigyouSho cbx_w100per border_none">
								      									<form:options items="${arrListSubToriatsukaiJigyouSho}" itemLabel="name" itemValue="code" />
								   									</form:select>
								   									<form:hidden path="arrListToriatsukaiJigyouSho[${status.index}].txtToriatsukaiJigyouShoHidden" />
																</td>
																<td>
																	<form:input path="arrListToriatsukaiJigyouSho[${status.index}].txtEigyouTantoushaCode" class="txtEigyouTantoushaCode txt_width75 border_none" type="text" maxlength="8" />
																	<form:hidden path="arrListToriatsukaiJigyouSho[${status.index}].txtEigyouTantoushaName" />
																	<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="btnSearchEigyouTantoushaCode searchIcon" alt="検索">
																</td>
																<td>
																	<span class="color_blue"></span>
																</td>
																<td>
																	<form:input path="arrListToriatsukaiJigyouSho[${status.index}].txtJimuTantoushaCode" class="txtJimuTantoushaCode txt_width75 border_none" type="text" maxlength="8" />
																	<form:hidden path="arrListToriatsukaiJigyouSho[${status.index}].txtJimuTantoushaName" />
																	<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="btnSearchJimuTantoushaCode searchIcon" alt="検索">
																</td>
																<td>
																	<span class="color_blue"></span>
																</td>
															</tr>
														</c:forEach>
													</c:when>
													<%-- 照会モードと取消モードの場合、取扱事業所を非活性化する --%>
													<c:when test="${viewMode == '2' || viewMode == '4'}">
														<c:forEach items="${arrListToriatsukaiJigyouSho}" var="toriatsukaiJigyouSho" varStatus="status">
															<tr>
																<td id="${status.index + 1}" class="align_center contextMenu">${status.index + 1}</td>
																<td class="align_right pr0">
																	<form:select path="arrListToriatsukaiJigyouSho[${status.index}].ddlToriatsukaiJigyouSho" class="cbx_w100per border_none" disabled="true">
								      									<form:options items="${arrListSubToriatsukaiJigyouSho}" itemLabel="name" itemValue="code" />
								   									</form:select>
								   									<form:hidden path="arrListToriatsukaiJigyouSho[${status.index}].txtToriatsukaiJigyouShoHidden" />
																</td>
																<td>
																	<form:input path="arrListToriatsukaiJigyouSho[${status.index}].txtEigyouTantoushaCode" class="txtEigyouTantoushaCode txt_width75 border_none txt_disable" type="text" maxlength="8" readonly="true" />
																	<form:hidden path="arrListToriatsukaiJigyouSho[${status.index}].txtEigyouTantoushaName" />
																	<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="btnSearchEigyouTantoushaCode searchIcon" alt="検索">
																</td>
																<td>
																	<span class="color_blue"></span>
																</td>
																<td>
																	<form:input path="arrListToriatsukaiJigyouSho[${status.index}].txtJimuTantoushaCode" class="txtJimuTantoushaCode txt_width75 border_none txt_disable" type="text" maxlength="8" readonly="true" />
																	<form:hidden path="arrListToriatsukaiJigyouSho[${status.index}].txtJimuTantoushaName" />
																	<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="btnSearchJimuTantoushaCode searchIcon" alt="検索">
																</td>
																<td>
																	<span class="color_blue"></span>
																</td>
															</tr>
														</c:forEach>
													</c:when>
												</c:choose>
											</table>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、行追加ボタンを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<button class="btn_add_row ml30" type="button" id="btnAddRow">行追加</button>
												</c:when>
												<%-- 照会モードと取消モードの場合、行追加ボタンを活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<button class="btn_add_row ml30" type="button" id="btnAddRow" disabled="disabled">行追加</button>
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block5-->
									<tr>
										<td class="td_width120 align_right">幹事事業所</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、幹事事業所を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlKanjiJigyouSho" class="cbx_width185">
				      									<form:options items="${arrListKanjiJigyouSho}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、幹事事業所を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlKanjiJigyouSho" class="cbx_width185" disabled="true">
				      									<form:options items="${arrListKanjiJigyouSho}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtKanjiJigyouShoHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">得意先種別</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、得意先種別を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlCustomerType" class="cbx_width140">
				      									<form:options items="${arrListCustomerType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、得意先種別を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlCustomerType" class="cbx_width140" disabled="true">
				      									<form:options items="${arrListCustomerType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtCustomerTypeHidden" />
										</td>
										<td class="td_width120 align_right">店舗区分</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、店舗区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlTenpoKubun" class="cbx_width120">
				      									<form:options items="${arrListTenpoKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、店舗区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlTenpoKubun" class="cbx_width120" disabled="true">
				      									<form:options items="${arrListTenpoKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtTenpoKubunHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">業態区分</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、業態区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlGyoutaiKubun" class="cbx_width140">
				      									<form:options items="${arrListGyoutaiKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、業態区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlGyoutaiKubun" class="cbx_width140" disabled="true">
				      									<form:options items="${arrListGyoutaiKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtGyoutaiKubunHidden" />
										</td>
										<td class="td_width120 align_right">納品先センター</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードの場合、納品先センターを活性化／非活性化チェックする --%>
												<c:when test="${viewMode == '1'}">
													<c:choose>
														<c:when test="${enableDeliveryCenter == '1'}">
															<form:select path="ddlDeliveryCenter" class="cbx_width250">
						      									<form:options items="${arrListDeliveryCenter}" itemLabel="name" itemValue="code" />
						   									</form:select>
														</c:when>
														<c:otherwise>
															<form:select path="ddlDeliveryCenter" class="cbx_width250" disabled="true">
						      									<form:options items="${arrListDeliveryCenter}" itemLabel="name" itemValue="code" />
						   									</form:select>
														</c:otherwise>
													</c:choose>
												</c:when>
												<%-- 照会モードと取消モードの場合、納品先センターを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlDeliveryCenter" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListDeliveryCenter}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 訂正モードの場合、納品先センターを活性化する --%>
												<c:when test="${viewMode == '3'}">
													<form:select path="ddlDeliveryCenter" class="cbx_width250">
				      									<form:options items="${arrListDeliveryCenter}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtDeliveryCenterHidden" />
											<form:hidden path="txtDeliveryCenterDataChanged" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">会社関係種別</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、会社関係種別を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlKankeiKaishaType" class="cbx_width140">
				      									<form:options items="${arrListKankeiKaishaType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、会社関係種別を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlKankeiKaishaType" class="cbx_width140" disabled="true">
				      									<form:options items="${arrListKankeiKaishaType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<c:choose>
												<%-- 新規モードの場合、補助科目を活性化する --%>
												<c:when test="${viewMode == '1'}">
													<span class="ml30 mr30">補助科目</span>
													<form:select path="ddlHojoKamoku" class="cbx_width250">
				      									<form:options items="${arrListHojoKamoku}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、補助科目を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<span class="ml30 mr30">補助科目</span>
													<form:select path="ddlHojoKamoku" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListHojoKamoku}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtKankeiKaishaTypeHidden" />
											<form:hidden path="txtHojoKamokuHidden" />
										</td>
										<td class="td_width120 align_right">採番区分</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、採番区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlSaibanKubun" class="cbx_width250">
				      									<form:options items="${arrListSaibanKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、採番区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlSaibanKubun" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListSaibanKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtSaibanKubunHidden" />
										</td>
									</tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<tr>
										<td class="td_width120 align_right">YG取引区分</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、YG取引区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlYgTorihikiKubun" class="cbx_width100">
				      									<form:options items="${arrListYGTorihikiKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、YG取引区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlYgTorihikiKubun" class="cbx_width100" disabled="true">
				      									<form:options items="${arrListYGTorihikiKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtYgTorihikiKubunHidden" />
										</td>
										<td class="td_width120 align_right">内税顧客区分</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、内税顧客区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlUchiZeiKoKyakuKubun" class="cbx_width250">
				      									<form:options items="${arrListUchiZeiKoKyakuKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、内税顧客区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlUchiZeiKoKyakuKubun" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListUchiZeiKoKyakuKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtUchiZeiKoKyakuKubunHidden" />
										</td>
									</tr>
									<tr>
										<td colspan="4" class="align_right">内税消費税端数処理</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードの場合、内税消費税端数処理を活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:select path="ddlUchiZeiShori" class="cbx_width250">
				      									<form:options items="${arrListUchiZeiShori}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、内税消費税端数処理を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:select path="ddlUchiZeiShori" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListUchiZeiShori}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtUchiZeiShoriHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">集金有無</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、集金有無を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlShuukinUmu" class="cbx_width250">
				      									<form:options items="${arrListShuukinUmu}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、集金有無を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlShuukinUmu" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListShuukinUmu}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtShuukinUmuHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">現金集金マーク印字</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、現金集金マーク印字を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlGenkinShuukinMark" class="cbx_width250">
				      									<form:options items="${arrListGenkinShuukinMark}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、現金集金マーク印字を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlGenkinShuukinMark" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListGenkinShuukinMark}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtGenkinShuukinMarkHidden" />
										</td>
										<td class="td_width120 align_right">集計出力FLG</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、集計出力FLGを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlShuukinOutputFlag" class="cbx_width250">
				      									<form:options items="${arrListShuukinOutputFlag}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、集計出力FLGを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlShuukinOutputFlag" class="cbx_width250" disabled="true">
				      									<form:options items="${arrListShuukinOutputFlag}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtShuukinOutputFlagHidden" />
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block6-->
									<tr>
										<td class="td_width120 align_right">手入力伝票発行</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、手入力伝票発行を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlManualInputBilling" class="cbx_width70">
				      									<form:options items="${arrListManualInputBilling}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、手入力伝票発行を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlManualInputBilling" class="cbx_width70" disabled="true">
				      									<form:options items="${arrListManualInputBilling}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtManualInputBillingHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">手入力出荷伝票</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、手入力出荷伝票を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == 3}">
													<form:select path="ddlManualInputDelivery" class="cbx_width450">
				      									<form:options items="${arrListManualInputDelivery}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、手入力出荷伝票を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlManualInputDelivery" class="cbx_width450" disabled="true">
				      									<form:options items="${arrListManualInputDelivery}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtManualInputDeliveryHidden" />
										</td>
										<td class="td_width120 align_right">伝票行計算金額まるめ</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、伝票行計算金額まるめを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlSlipLineCalculationAmountRounding" class="cbx_width200">
				      									<form:options items="${arrListSlipLineCalculationAmountRounding}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、伝票行計算金額まるめを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlSlipLineCalculationAmountRounding" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListSlipLineCalculationAmountRounding}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtSlipLineCalculationAmountRoundingHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">出荷伝票出力品コード</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、出荷伝票出力品コードを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlDeliveryOutputProductCode" class="cbx_width450">
				      									<form:options items="${arrListDeliveryOutputProductCode}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、出荷伝票出力品コードを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlDeliveryOutputProductCode" class="cbx_width450" disabled="true">
				      									<form:options items="${arrListDeliveryOutputProductCode}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtDeliveryOutputProductCodeHidden" />
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block7-->
									<tr>
										<td class="align_right">請求先名称</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求先名称を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingName" class="txt_width530 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、請求先名称を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingName" class="txt_width530 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">請求先名称カナ</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求先名称カナを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingNameKana" class="txt_width370 imeActive" type="text" maxlength="30" />
												</c:when>
												<%-- 照会モードと取消モードの場合、請求先名称カナを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingNameKana" class="txt_width370 imeActive txt_disable" type="text" maxlength="30" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">請求先略称</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求先略称を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingNameR" class="txt_width370 imeActive" type="text" maxlength="10" />
												</c:when>
												<%-- 照会モードと取消モードの場合、請求先略称を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingNameR" class="txt_width370 imeActive txt_disable" type="text" maxlength="10" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right">郵便番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、郵便番号を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingZipCode1" class="txt_width35" type="text" maxlength="9" />
													-
													<form:input path="txtBillingZipCode2" class="txt_width44" type="text" maxlength="9" />
													（例：639-1038） 
												</c:when>
												<%-- 照会モードと取消モードの場合、郵便番号を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingZipCode1" class="txt_width35 txt_disable" type="text" maxlength="9" readonly="true" />
													-
													<form:input path="txtBillingZipCode2" class="txt_width44 txt_disable" type="text" maxlength="9" readonly="true" />
													（例：639-1038） 
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingZipCodeResult" />
										</td>
									</tr>
									<tr>
										<td class="align_right">住所１</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、住所１を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingAddress1" class="txt_width530 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、住所１を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingAddress1" class="txt_width530 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">住所２</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、住所２を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingAddress2" class="txt_width530 imeActive" type="text" maxlength="20" />
												</c:when>
												<%-- 照会モードと取消モードの場合、住所２を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingAddress2" class="txt_width530 imeActive txt_disable" type="text" maxlength="20" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">電話番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、電話番号を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingTel1" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtBillingTel2" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtBillingTel3" class="txt_width45" type="text" maxlength="18" />
													（例：0743-56-2911）
												</c:when>
												<%-- 照会モードと取消モードの場合、電話番号を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingTel1" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													-
													<form:input path="txtBillingTel2" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													-
													<form:input path="txtBillingTel3" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													（例：0743-56-2911）
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingTelResult" />
										</td>
									</tr>
									<tr>
										<td class="align_right">FAX番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、FAX番号を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtBillingFax1" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtBillingFax2" class="txt_width45" type="text" maxlength="18" />
													-
													<form:input path="txtBillingFax3" class="txt_width45" type="text" maxlength="18" />
												</c:when>
												<%-- 照会モードと取消モードの場合、FAX番号を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtBillingFax1" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													-
													<form:input path="txtBillingFax2" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
													-
													<form:input path="txtBillingFax3" class="txt_width45 txt_disable" type="text" maxlength="18" readonly="true" />
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingFaxResult" />
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block8-->
									<tr>
										<td class="td_width120 align_right">請求単位</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求単位を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingUnit" class="cbx_width100">
				      									<form:options items="${arrListBillingUnit}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求単位を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingUnit" class="cbx_width100" disabled="true">
				      									<form:options items="${arrListBillingUnit}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingUnitHidden" />
										</td>
										<td class="td_width120 align_right">請求書単価</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求書単価を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingUnitPrice" class="cbx_width200">
				      									<form:options items="${arrListBillingUnitPrice}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求書単価を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingUnitPrice" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListBillingUnitPrice}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingUnitPriceHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">請求書種類</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求書種類を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingType" class="cbx_width380">
				      									<form:options items="${arrListBillingType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求書種類を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingType" class="cbx_width380" disabled="true">
				      									<form:options items="${arrListBillingType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingTypeHidden" />
										</td>
										<td class="td_width120 align_right">取纏め請求有無</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、取纏め請求有無を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingToriMatomeUMu" class="cbx_width90">
				      									<form:options items="${arrListBillingToriMatomeUMu}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、取纏め請求有無を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingToriMatomeUMu" class="cbx_width90" disabled="true">
				      									<form:options items="${arrListBillingToriMatomeUMu}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<c:choose>
												<%-- 新規モードの場合、取纏め請求事業所を活性化する --%>
												<c:when test="${viewMode == '1'}">
													<span class="ml20 mr20">取纏め請求事業所</span>
													<form:select path="ddlBillingToriMatomeJigyouSho" class="cbx_width90">
				      									<form:options items="${arrListBillingToriMatomeJigyouSho}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、取纏め請求事業所を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<span class="ml20 mr20">取纏め請求事業所</span>
													<form:select path="ddlBillingToriMatomeJigyouSho" class="cbx_width90" disabled="true">
				      									<form:options items="${arrListBillingToriMatomeJigyouSho}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingToriMatomeUMuHidden" />
											<form:hidden path="txtBillingToriMatomeJigyouShoHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">請求書パターン</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードの場合、請求書パターンを活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:select path="ddlBillingPattern" class="cbx_width380">
				      									<form:options items="${arrListBillingPattern}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、請求書パターンを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:select path="ddlBillingPattern" class="cbx_width380" disabled="true">
				      									<form:options items="${arrListBillingPattern}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingPatternHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">請求書住所印字</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求書住所印字を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingAddressPrint" class="cbx_width380">
				      									<form:options items="${arrListBillingAddressPrint}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求書住所印字を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingAddressPrint" class="cbx_width380" disabled="true">
				      									<form:options items="${arrListBillingAddressPrint}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingAddressPrintHidden" />
										</td>
										<td class="td_width120 align_right">請求集計表区分</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求集計表区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingShuukeiHyouKubun" class="cbx_width150">
				      									<form:options items="${arrListBillingShuukeiHyouKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求集計表区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingShuukeiHyouKubun" class="cbx_width150" disabled="true">
				      									<form:options items="${arrListBillingShuukeiHyouKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingShuukeiHyouKubunHidden" />
										</td>
									</tr>
									<tr>
										<td colspan="4" class="align_right">【請求チェックリスト】</td>
										<td class="td_width3"></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">消費税計算単位</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、消費税計算単位を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingShouhizeiCalculationUnit" class="cbx_width380">
				      									<form:options items="${arrListBillingShouhizeiCalculationUnit}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、消費税計算単位を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingShouhizeiCalculationUnit" class="cbx_width380" disabled="true">
				      									<form:options items="${arrListBillingShouhizeiCalculationUnit}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingShouhizeiCalculationUnitHidden" />
										</td>
										<td class="td_width120 align_right">出力対象</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、出力対象を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingCheckListOutputTarget" class="cbx_width200">
				      									<form:options items="${arrListBillingCheckListOutputTarget}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、出力対象を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingCheckListOutputTarget" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListBillingCheckListOutputTarget}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingCheckListOutputTargetHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">消費税端数処理</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、消費税端数処理を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingShouhizeiShori" class="cbx_width380">
				      									<form:options items="${arrListBillingShouhizeiShori}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、消費税端数処理を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingShouhizeiShori" class="cbx_width380" disabled="true">
				      									<form:options items="${arrListBillingShouhizeiShori}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingShouhizeiShoriHidden" />
										</td>
										<td class="td_width120 align_right">出力順</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードの場合、出力順を活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:select path="ddlBillingCheckListOutputOrder" class="cbx_width200">
				      									<form:options items="${arrListBillingCheckListOutputOrder}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、出力順を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:select path="ddlBillingCheckListOutputOrder" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListBillingCheckListOutputOrder}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingCheckListOutputOrderHidden" />
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block9-->
									<tr>
										<td class="td_width120 align_right">締日１</td>
										<td class="td_width3"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、締日１、回収月区分１、回収日１を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtCloseDay1" class="txt_width50 align_right" type="text" maxlength="2" />
													<span class="ml50 mr10">回収月区分１</span>
													<form:select path="ddlRecoveryMonthKubun1" class="cbx_width100">
				      									<form:options items="${arrListRecoveryMonthKubun1}" itemLabel="name" itemValue="code" />
				   									</form:select>
													<span class="ml30 mr10">回収日１</span>
													<form:input path="txtRecoveryDay1" class="txt_width50 align_right" type="text" maxlength="2" />
												</c:when>
												<%-- 照会モードと取消モードの場合、締日１、回収月区分１、回収日１を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtCloseDay1" class="txt_width50 align_right txt_disable" type="text" maxlength="2" readonly="true" />
													<span class="ml50 mr10">回収月区分１</span>
													<form:select path="ddlRecoveryMonthKubun1" class="cbx_width100" disabled="true">
				      									<form:options items="${arrListRecoveryMonthKubun1}" itemLabel="name" itemValue="code" />
				   									</form:select>
													<span class="ml30 mr10">回収日１</span>
													<form:input path="txtRecoveryDay1" class="txt_width50 align_right txt_disable" type="text" maxlength="2" readonly="true" />
												</c:when>
											</c:choose>
											<form:hidden path="txtRecoveryMonthKubun1Hidden" />
										</td>
										<td class="td_width120 align_right">入金種別</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、入金種別を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlPaymentType" class="cbx_width200">
				      									<form:options items="${arrListPaymentType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、入金種別を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlPaymentType" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListPaymentType}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtPaymentTypeHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">&nbsp;</td>
										<td class="td_width3"></td>
										<td class="td_width600">
											<span class="color_orange">※請求単位が締単位の場合入力必須</span>
										</td>
										<td class="td_width120 align_right">入金口座</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードの場合、入金口座を活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:select path="ddlPaymentAccount" class="cbx_width200">
				      									<form:options items="${arrListPaymentAccount}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、入金口座を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:select path="ddlPaymentAccount" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListPaymentAccount}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtPaymentAccountHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">締日２</td>
										<td class="td_width3"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、締日２、回収月区分２、回収日２を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtCloseDay2" class="txt_width50 align_right" type="text" maxlength="2" />
													<span class="ml50 mr10">回収月区分２</span>
													<form:select path="ddlRecoveryMonthKubun2" class="cbx_width100">
				      									<form:options items="${arrListRecoveryMonthKubun2}" itemLabel="name" itemValue="code" />
				   									</form:select>
													<span class="ml30 mr10">回収日２</span>
													<form:input path="txtRecoveryDay2" class="txt_width50 align_right" type="text" maxlength="2" />
												</c:when>
												<%-- 照会モードと取消モードの場合、締日２、回収月区分２、回収日２を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtCloseDay2" class="txt_width50 align_right txt_disable" type="text" maxlength="2" readonly="true" />
													<span class="ml50 mr10">回収月区分２</span>
													<form:select path="ddlRecoveryMonthKubun2" class="cbx_width100" disabled="true">
				      									<form:options items="${arrListRecoveryMonthKubun2}" itemLabel="name" itemValue="code" />
				   									</form:select>
													<span class="ml30 mr10">回収日２</span>
													<form:input path="txtRecoveryDay2" class="txt_width50 align_right txt_disable" type="text" maxlength="2" readonly="true" />
												</c:when>
											</c:choose>
											<form:hidden path="txtRecoveryMonthKubun2Hidden" />
										</td>
										<td class="td_width120 align_right">手形サイト</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、手形サイトを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtNoteSite" class="txt_width196" type="text" maxlength="3" />
												</c:when>
												<%-- 照会モードと取消モードの場合、手形サイトを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtNoteSite" class="txt_width196 txt_disable" type="text" maxlength="3" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block10-->
									<tr>
										<td class="align_right">【相手先コード】</td>
										<td class="td_width3"></td>
										<td colspan="4">&nbsp;</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right">取引コード</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、取引コードを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtTorihikiCode" class="txt_width50" type="text" maxlength="6" />
												</c:when>
												<%-- 照会モードと取消モードの場合、取引コードを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtTorihikiCode" class="txt_width50 txt_disable" type="text" maxlength="6" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right align_top">分類コード</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<table class="tbl_classification float_left mr30 tbl_width240">
												<col class="td_width65">
												<col class="td_width110">
												<col class="td_width65">
												<tr>
													<th rowspan="2">定番</th>
													<td class="color_darkred">店直</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、分類コードを活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtBunruiCodeTeibanShop" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtBunruiCodeTeibanShop" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td class="color_darkred">センター</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、分類コードを活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtBunruiCodeTeibanCenter" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtBunruiCodeTeibanCenter" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
											</table>
											<table class="tbl_classification tbl_width240">
												<col class="td_width65">
												<col class="td_width110">
												<col class="td_width65">
												<tr>
													<th rowspan="2">特売</th>
													<td class="color_darkred">店直</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、分類コードを活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtBunruiCodeTokubaiShop" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtBunruiCodeTokubaiShop" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td class="color_darkred">センター</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、分類コードを活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtBunruiCodeTokubaiCenter" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtBunruiCodeTokubaiCenter" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right align_top">伝票区分</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<table class="tbl_classification float_left mr30 tbl_width240">
												<col class="td_width65">
												<col class="td_width110">
												<col class="td_width65">
												<tr>
													<th rowspan="2">定番</th>
													<td class="color_darkred">店直</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、伝票区分を活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtDenpyouKubunTeibanShop" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtDenpyouKubunTeibanShop" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td class="color_darkred">センター</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、伝票区分を活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtDenpyouKubunTeibanCenter" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtDenpyouKubunTeibanCenter" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
											</table>
											<table class="tbl_classification tbl_width240">
												<col class="td_width65">
												<col class="td_width110">
												<col class="td_width65">
												<tr>
													<th rowspan="2">特売</th>
													<td class="color_darkred">店直</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、伝票区分を活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtDenpyouKubunTokubaiShop" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtDenpyouKubunTokubaiShop" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td class="color_darkred">センター</td>
													<td class="align_right">
														<c:choose>
															<%-- 新規モードと訂正モードの場合、伝票区分を活性化する --%>
															<c:when test="${viewMode == '1' || viewMode == '3'}">
																<form:input path="txtDenpyouKubunTokubaiCenter" class="txt_width60 border_none" type="text" maxlength="6" />
															</c:when>
															<%-- 照会モードと取消モードの場合、分類コードを非活性化する --%>
															<c:when test="${viewMode == '2' || viewMode == '4'}">
																<form:input path="txtDenpyouKubunTokubaiCenter" class="txt_width60 border_none txt_disable" type="text" maxlength="6" readonly="true" />
															</c:when>
														</c:choose>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block10-->
									<tr>
										<td class="td_width120 align_right">受領データ突合区分</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、受領データ突合区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlJuryouDataKubun" class="cbx_width200">
				      									<form:options items="${arrListJuryouDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、受領データ突合区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlJuryouDataKubun" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListJuryouDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtJuryouDataKubunHidden" />
										</td>
										<td class="td_width120 align_right">請求データ区分</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求データ区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingDataKubun" class="cbx_width200">
				      									<form:options items="${arrListBillingDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求データ区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingDataKubun" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListBillingDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingDataKubunHidden" />
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">修正データ突号区分</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、修正データ突号区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlModifyDataKubun" class="cbx_width200">
				      									<form:options items="${arrListModifyDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、修正データ突号区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlModifyDataKubun" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListModifyDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtModifyDataKubunHidden" />
										</td>
										<td class="td_width120 align_right">請求先支払データ区分</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求先支払データ区分を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:select path="ddlBillingPaymentDataKubun" class="cbx_width200">
				      									<form:options items="${arrListBillingPaymentDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
												<%-- 照会モードと取消モードの場合、請求先支払データ区分を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:select path="ddlBillingPaymentDataKubun" class="cbx_width200" disabled="true">
				      									<form:options items="${arrListBillingPaymentDataKubun}" itemLabel="name" itemValue="code" />
				   									</form:select>
												</c:when>
											</c:choose>
											<form:hidden path="txtBillingPaymentDataKubunHidden" />
										</td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<tr>
										<td class="td_width120 align_right"></td>
										<td class="td_width3"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、チェックボックスを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:checkbox id="chkModifyTypeHenpinData" path="chkModifyTypeHenpinData" />
													<label class="mr30" for="chkModifyTypeHenpinData">返品データ</label>
													<form:checkbox id="chkModifyTypeKetsuhinData" path="chkModifyTypeKetsuhinData" />
													<label class="mr30" for="chkModifyTypeKetsuhinData">欠品データ</label>
													<form:checkbox id="chkModifyTypeShuuseiData" path="chkModifyTypeShuuseiData" />
													<label for="chkModifyTypeShuuseiData">修正データ</label>
												</c:when>
												<%-- 照会モードと取消モードの場合、チェックボックスを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:checkbox id="chkModifyTypeHenpinData" path="chkModifyTypeHenpinData" onclick="return false" />
													<label class="mr30" for="chkModifyTypeHenpinData">返品データ</label>
													<form:checkbox id="chkModifyTypeKetsuhinData" path="chkModifyTypeKetsuhinData" onclick="return false" />
													<label class="mr30" for="chkModifyTypeKetsuhinData">欠品データ</label>
													<form:checkbox id="chkModifyTypeShuuseiData" path="chkModifyTypeShuuseiData" onclick="return false" />
													<label for="chkModifyTypeShuuseiData">修正データ</label>
												</c:when>
											</c:choose>
										</td>
										<td class="td_width120 align_right">請求データ配信ID</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
												<%-- 新規モードの場合、請求データ配信IDを活性化する --%>
												<c:when test="${viewMode == '1'}">
													<form:input path="txtBillingDataDeliveryId" class="txt_width196" type="text" maxlength="8" />
													<span>(HULFT-ID)</span>
												</c:when>
												<%-- 照会モードと訂正モードと取消モードの場合、請求データ配信IDを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '3' || viewMode == '4'}">
													<form:input path="txtBillingDataDeliveryId" class="txt_width196 txt_disable" type="text" maxlength="8" readonly="true" />
													<span>(HULFT-ID)</span>
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block10-->
									<tr>
										<td class="align_right">集計コード1</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、集計コード1、集計コード2を活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtShuukeiCode1" class="txt_width130" type="text" maxlength="6" />
													<span class="ml100 mr20">集計コード2</span>
													<form:input path="txtShuukeiCode2" class="txt_width130" type="text" maxlength="6" />
												</c:when>
												<%-- 照会モードと取消モードの場合、集計コード1、集計コード2を非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtShuukeiCode1" class="txt_width130 txt_disable" type="text" maxlength="6" readonly="true" />
													<span class="ml100 mr20">集計コード2</span>
													<form:input path="txtShuukeiCode2" class="txt_width130 txt_disable" type="text" maxlength="6" readonly="true" />
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block10-->
									<tr>
										<td class="td_width120 align_right">使用中止日</td>
										<td class="td_width3"></td>
										<td class="td_width600">
											<c:choose>
												<%-- 新規モードと訂正モードの場合、請求データ配信IDを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtShiyouChuushiDay" class="txt_width130 mr5" type="text" maxlength="10" />
												</c:when>
												<%-- 照会モードと取消モードの場合、請求データ配信IDを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtShiyouChuushiDay" class="txt_width130 mr5 txt_disable" type="text" maxlength="10" readonly="true" />
												</c:when>
											</c:choose>
										</td>
										<td class="td_width120 align_right">状況コード</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<%-- 新規モードと訂正モードの場合、状況コードを活性化する --%>
												<c:when test="${viewMode == '1' || viewMode == '3'}">
													<form:input path="txtStatusCode" class="txt_width35 align_center" type="text" maxlength="1" />
													（1:登録 9:取消）
												</c:when>
												<%-- 照会モードと取消モードの場合、状況コードを非活性化する --%>
												<c:when test="${viewMode == '2' || viewMode == '4'}">
													<form:input path="txtStatusCode" class="txt_width35 align_center txt_disable" type="text" maxlength="1" readonly="true" />
													（1:登録 9:取消）
												</c:when>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="height20 border_dot" colspan="6"></td>
									</tr>
									<tr>
										<td class="height20" colspan="6"></td>
									</tr>
									<!--block10-->
									<c:choose>
										<%-- 新規モードではない場合、コース情報を表示する --%>
										<c:when test="${viewMode != '1'}">
											<tr>
												<td class="td_width120 align_right">&nbsp;</td>
												<td colspan="5">
													<table class="tbl_info_table tbl_width705">
														<col class="td_width40">
														<col class="td_width110">
														<col class="td_width150">
														<col class="td_width150">
														<col class="td_width110">
														<col>
														<tr>
															<th>No</th>
															<th>事業所</th>
															<th>コースコード</th>
															<th>コース</th>
															<th>便区分</th>
															<th>納品区分</th>
														</tr>
														<c:forEach items="${arrListCourseInformation}" var="courseInformation" varStatus="status">
															<tr>
																<td class="align_center">${status.index + 1}</td>
																<td>${courseInformation.txtJigyouShoName}</td>
																<td class="align_center">${courseInformation.txtCourseCode}</td>
																<td>${courseInformation.txtCourseName}</td>
																<td class="align_center">${courseInformation.txtBinKubun}</td>
																<td>${courseInformation.txtDeliveryKubunName}</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
											<tr>
												<td class="height20 border_dot" colspan="6"></td>
											</tr>
											<tr>
												<td class="height20" colspan="6"></td>
											</tr>
										</c:when>
									</c:choose>
									<!--block10-->
								</table>
							</div>
						</div>
					</div>
				</div> <!--/content-->
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						<c:choose>
							<%-- 新規モードと訂正モードの場合、登録ボタンを活性化する --%>
							<c:when test="${viewMode == '1' || viewMode == '3'}">
								<button id="btnRegister" class="btn_button float_left" type="button" name="Register">登録</button>
							</c:when>
							<%-- 照会モードの場合、登録ボタンを非活性化する --%>
							<c:when test="${viewMode == '2'}">
								<button id="btnRegister" class="btn_button_disabled float_left" type="button" name="Register" disabled="disabled">登録</button>
							</c:when>
							<%-- 取消モードの場合、登録ボタンを活性化する --%>
							<c:when test="${viewMode == '4'}">
								<button id="btnRegister" class="btn_button float_left" type="button" name="Register">取消</button>
							</c:when>
						</c:choose>
						<c:choose>
							<%-- 新規モードと訂正モードの場合、クリアボタンを活性化する --%>
							<c:when test="${viewMode == '1' || viewMode == '3'}">
								<button id="btnClear" class="btn_button float_right mr0" type="button" name="Clear">クリア</button>
							</c:when>
							<%-- 照会モードと取消モードの場合、クリアボタンを非活性化する --%>
							<c:when test="${viewMode == '2' || viewMode == '4'}">
								<button id="btnClear" class="btn_button_disabled float_right mr0" type="button" name="Clear" disabled="disabled">クリア</button>
							</c:when>
						</c:choose>
					</div>
				</div>

				<!-- hidden proccess-->
				<form:hidden id="form1DdlJigyouSho" path="form1DdlJigyouSho" />
				<form:hidden id="form1TxtCustomerCode" path="form1TxtCustomerCode" />
				<form:hidden id="form1TxtCustomerName" path="form1TxtCustomerName" />
				<form:hidden id="form1TxtChainCode" path="form1TxtChainCode" />
				<form:hidden id="form1TxtChainEda" path="form1TxtChainEda" />
				<form:hidden id="form1TxtEigyouTantoushaCode" path="form1TxtEigyouTantoushaCode" />
				<form:hidden id="form1TxtJimuTantoushaCode" path="form1TxtJimuTantoushaCode" />
				<form:hidden id="form1DdlCustomerType" path="form1DdlCustomerType" />
				<form:hidden id="form1DdlUchiZeiKoKyakuKubun" path="form1DdlUchiZeiKoKyakuKubun" />
				<form:hidden id="form1ChkCancelData" path="form1ChkCancelData" />
				<form:hidden id="form1ChkCustomer" path="form1ChkCustomer" />
				<form:hidden id="form1ChkBilling" path="form1ChkBilling" />
				<form:hidden id="form1ChkCustomerBilling" path="form1ChkCustomerBilling" />
				<form:hidden id="businessDate" path="businessDate" />
				<form:hidden id="viewMode" path="viewMode" />
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="toriatsukaiJigyouShoId" path="toriatsukaiJigyouShoId" />
				<form:hidden id="haitaDate" path="haitaDate" />
				<form:hidden id="haitaTime" path="haitaTime" />	
				<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
				<input type="hidden" id="isDisableCombobox" value="${isDisableCombobox}" />
				<input type="hidden" id="isDisableControl" value="${isDisableControl}" />
				<input type="hidden" id="isDisableDeliveryCenter" value="${isDisableDeliveryCenter}" />
				<input type="hidden" id="infoMessFlag" value="${infoMessFlag}" />
				<button id="btnBackHidden" type="submit" name="Return" class="display_none"></button>
				<button id="btnRegisterHidden" type="submit" name="Register" class="display_none"></button>
				<button id="btnClearHidden" type="submit" name="Clear" class="display_none"></button>
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
				<!--end dialog-->
				<div id="tableMenu" class="sub-menu">
					<a href="#" id="cancel"><img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12"/>　行削除</a>
				</div>
			</div>		
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>