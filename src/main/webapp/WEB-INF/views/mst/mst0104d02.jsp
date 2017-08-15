<!DOCTYPE html>
<%--
  ファイル名:mst0104d02.jsp
  画面名:コースマスタ登録画面

  作成者:ABV）NhungMa
  作成日:2015/09/21
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/09/21 1.00                  ABV）NhungMa        新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0104d02.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0104d02.js" />" type="text/javascript"></script>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0104d02" id="formMst0104d02">	
			<div id="wrapper">
				<!--page note output -->
				<div id="messError" class="messError_error">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status"> ${errorMessages.messageContent}<br /></c:forEach>
						</span>
					</div>
			    </div>
				<div class="content">
					<!--condition area-->
					<div class="data_area_check">
						<div class="data_conditions_area">
							<div class="data_conditions_area_cont">
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
									<table class="tbl_data_condition_cont">
										<tr>
											<td class="align_right td_width100">事業所</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width380">
												<c:choose>
													<c:when test="${modeView == '1'}">
														<form:select id="ddlJigyoCode" path="ddlJigyoCode" class="cbx_width185">
						        							<form:options items="${JigyoCodeClassList}" itemLabel="name" itemValue="code" />
						     							</form:select>
													</c:when>
													<c:otherwise>
														<form:select id="ddlJigyoCode" path="ddlJigyoCode" class="cbx_width185" disabled="true">
						        							<form:options items="${JigyoCodeClassList}" itemLabel="name" itemValue="code" />
						     							</form:select>
													</c:otherwise>
												</c:choose>
											</td>
											<td colspan="6"></td>
										</tr>
										<tr>
											<td class="align_right td_width100">コースコード</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width380">
												<c:choose>
													<c:when test="${modeView == '1'}">
														<form:input id="txtCourseCode" class="txt_width100" maxlength="5" path="txtCourseCode" />	
													</c:when>
													<c:otherwise>
														<form:input id="txtCourseCode" class="txt_width100 txt_disable" maxlength="5" path="txtCourseCode" readonly="true" />
													</c:otherwise>
												</c:choose>
											</td>
											<td colspan="6"></td>
										</tr>
										<tr>
											<td class="align_right td_width100">コース名称</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width380">
												<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtCourseName" path="txtCourseName" class="txt_width340 imeActive" maxlength="20" />
													</c:when>
													<c:otherwise>
														<form:input id="txtCourseName" path="txtCourseName" class="txt_width340 imeActive txt_disable" maxlength="20" readonly="true" />
													</c:otherwise>
												</c:choose>
											</td>
											<td class="align_right td_width90">配送時間</td>
											<td class="td_width3"></td>
											<td class="td_width270">
												<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtHaisoTime01" path="txtHaisoTime01" class="txt_width40 align_center" maxlength="2" />
														:
														<form:input id="txtHaisoTime02" path="txtHaisoTime02" class="txt_width40 align_center" maxlength="2" />
													</c:when>
													<c:otherwise>
														<form:input id="txtHaisoTime01" path="txtHaisoTime01" class="txt_width40 txt_disable align_center" maxlength="2" readonly="true" />
														:
														<form:input id="txtHaisoTime02" path="txtHaisoTime02" class="txt_width40 txt_disable align_center" maxlength="2" readonly="true" />
													</c:otherwise>
												</c:choose>
											</td>
											<td class="align_right td_width90">出荷更新時間</td>
											<td class="td_width3"></td>
											<td>
												<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtShipUpdateTime01" path="txtShipUpdateTime01" class="txt_width40 align_center" maxlength="2" />
														:
														<form:input id="txtShipUpdateTime02" path="txtShipUpdateTime02" class="txt_width40 align_center" maxlength="2" />		
													</c:when>
													<c:otherwise>
														<form:input id="txtShipUpdateTime01" path="txtShipUpdateTime01" class="txt_width40 txt_disable align_center" maxlength="2" readonly="true" />
														:
														<form:input id="txtShipUpdateTime02" path="txtShipUpdateTime02" class="txt_width40 txt_disable align_center" maxlength="2" readonly="true" />
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td class="align_right td_width100">コース略称</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width380">
												<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtCourseNameR" path="txtCourseNameR" class="txt_width340 imeActive" maxlength="10" />		
													</c:when>
													<c:otherwise>
														<form:input id="txtCourseNameR" path="txtCourseNameR" class="txt_width340 imeActive txt_disable" maxlength="10" readonly="true" />
													</c:otherwise>
												</c:choose>									
											</td>
											<td class="align_right td_width90">配送区分</td>
											<td class="td_width3"></td>
											<td class="td_width270">
												<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtHaisoKb" path="txtHaisoKb" class="txt_width40 align_center" type="text" maxlength="1" />	
													</c:when>
													<c:otherwise>
														<form:input id="txtHaisoKb" path="txtHaisoKb" class="txt_width40 align_center txt_disable" type="text" maxlength="1" readonly="true" />
													</c:otherwise>
												</c:choose>
												<span>（1:自社配送、2:宅配便）</span>
											</td>
											<td class="align_right td_width90">状況コード</td>
											<td class="td_width3 orange_line"></td>
											<td>
												<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtStsCode01" path="txtStsCode01" class="txt_width40 align_center" type="text" maxlength="1" />
													</c:when>
													<c:otherwise>
														<form:input id="txtStsCode01" path="txtStsCode01" class="txt_width40 align_center txt_disable" type="text" maxlength="1" readonly="true" />
													</c:otherwise>
												</c:choose>
												<span>（1:登録 9:取消）</span>
											</td>
										</tr>
									</table>
								</div>
								<div class="condition_button">
									<button id="btnConfig" class="btn_button float_left" type="button">設定</button>
								</div>
							</div>
						</div>
						<div class="data_conditions_area mr_ml20">
							<div class="data_conditions_input_cont">					
								<div class="input_area height70">
									<table class="tbl_data_condition_search">
										<tr>
											<td class="align_right td_width90 pr10">得意先コード</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width270 pl3">
												<c:choose>
													<c:when test="${modeView == '3'}">
														<form:input id="txtCustomerCode" class="txt_width100 float_left" path="txtCustomerCode" maxlength="7" />
														<img class="searchIcon float_left ml_mr4 cursor_pointer" id="btn_search_customer_id" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search.png">
														<div class="width120 float_left mt3">
															<span id="lblCustomerNameR" class="color_blue"></span>
														</div>
													</c:when>
													<c:otherwise>
														<form:input id="txtCustomerCode" class="txt_width100 float_left txt_disable" path="txtCustomerCode"  maxlength="7" readonly="true" />	
														<img class="searchIcon float_left ml_mr4" id="btn_search_customer_id" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search_dis.png">
														<div class="width120 float_left mt3">
															<span id="lblCustomerNameR" class="color_blue"></span>
														</div>
													</c:otherwise>
												</c:choose>
											</td>
											<td class="align_right td_width70 pr10">店舗コード</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width230 pl3">
												<c:choose>
													<c:when test="${modeView == '3'}">
														<form:input id="txtShopCode" class="txt_width100 float_left" path="txtShopCode" maxlength="8" />
														<img class="searchIcon float_left ml_mr4" id="btn_search_shop_id" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search_dis.png">
														<div class="width90 float_left mt3">
															<span id="lblShopNameR" class="color_blue">${lblShopNameR}</span>
														</div>
													</c:when>
													<c:otherwise>
														<form:input id="txtShopCode" class="txt_width100 float_left txt_disable" path="txtShopCode" maxlength="8" readonly="true" />
														<img class="searchIcon float_left ml_mr4" id="btn_search_shop_id" alt="検索" src="${pageContext.request.contextPath}/resources/css/images/search_dis.png">
														<div class="width90 float_left mt3">
															<span id="lblShopNameR" class="color_blue">${lblShopNameR}</span>
														</div>
													</c:otherwise>
												</c:choose>
											</td>
											<td class="align_right td_width70 pr10">便区分</td>
											<td class="td_width3 orange_line"></td>
											<td class="td_width170 pl3">
												<c:choose>
													<c:when test="${modeView == '3'}">
														<form:input id="txtBinKb" class="txt_width40 align_center" path="txtBinKb" maxlength="1" />
													</c:when>
													<c:otherwise>
														<form:input id="txtBinKb" class="txt_width40 align_center txt_disable" path="txtBinKb" maxlength="1" readonly="true" />
													</c:otherwise>
												</c:choose>
											</td>
											<td class="align_right td_width70 pr10">納品区分</td>
											<td class="td_width3 orange_line"></td>
											<td class="pl3">
												<c:choose>
													<c:when test="${modeView == '3'}">
														<form:select id="ddlDeliKb" path="ddlDeliKb" class="cbx_width100">
															<form:options items="${GenClassList}" itemLabel="name" itemValue="code" />
						     							</form:select>
													</c:when>
													<c:otherwise>
														<form:select id="ddlDeliKb" path="ddlDeliKb" class="cbx_width100" disabled="true">
						        							<form:options items="${GenClassList}" itemLabel="name" itemValue="code" />
						     							</form:select>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td colspan="6"></td>
											<td class="align_right td_width70 pr10">状況コード</td>
											<td class="td_width3 orange_line"></td>
											<td class="pl3">
												<c:choose>
													<c:when test="${modeView == '3'}">
														<form:input id="txtStsCode02" class="txt_width40 align_center" path="txtStsCode02" maxlength="1" />
													</c:when>
													<c:otherwise>
														<form:input id="txtStsCode02" class="txt_width40 align_center txt_disable" path="txtStsCode02" maxlength="1" readonly="true" />
													</c:otherwise>
												</c:choose>
												<span>（1:登録 9:取消）</span>
											</td>
											<td colspan="3"></td>
										</tr>
									</table>
									<div class="button_area">
										<button id="btnAddUpdate" class="btn_button float_left ml50" type="button">追加／更新</button>
										<button id="btnClearCustomer" type="button" class="btn_button float_right mr50">クリア</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="data_result_area clear">
						<div class="div_zentai">
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width3per">
									<col class="td_width8per">
									<col class="td_width32per">
									<col class="td_width8per">
									<col class="td_width30per">
									<col class="td_width8per">
									<col class="td_width11per">
									<col class="display_none">
									<col class="display_none">
									<col class="display_none">
									<tr>
										<th>No</th>
										<th>得意先コード</th>
										<th>得意先名称</th>
										<th>店舗コード</th>
										<th>店舗名称</th>
										<th>便区分</th>
										<th>納品区分</th>
										<th class="display_none"></th>
										<th class="display_none"></th>
										<th class="display_none"></th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height395">
								<table id="tblBody" class="tbl_list">
									<col class="td_width3per">
									<col class="td_width8per">
									<col class="td_width32per">
									<col class="td_width8per">
									<col class="td_width30per">
									<col class="td_width8per">
									<col class="td_width11per">
									<col class="display_none">
									<col class="display_none">
									<col class="display_none">
									<tbody id="tbl_tbody">
										<c:forEach var="item" items="${lstDelivery}" varStatus="status">
											<c:choose>
												<c:when test="${item.stsCode == 9}">
													<tr class ="tbl_del">
														<td id="${status.index + 1}" class="contextMenu align_center">
															<span>${status.index + 1}</span>
															<input type="hidden" class="no" value="${status.index + 1}" >
														</td>
														<td class="align_center">${item.custCode}</td>
														<td>${item.custNmR}</td>
														<c:choose>
															<c:when test="${item.shopCode == hiddenConfig}">
																<td>---</td>
																<td>---</td>
															</c:when>
															<c:otherwise>
																<td>${item.shopCode}</td>
																<td>${item.shopNmR}</td>
															</c:otherwise>
														</c:choose>
														<td class="align_center">${item.binKb}便</td>
														<td class="align_center">
															<span>${item.deliveryDivisionName}</span>
															<input type="hidden" class="deliKbCode" value="${item.deliKb}">
														</td>
														<td class="display_none">${item.stsCode}</td>
														<td class="display_none"><input type="hidden" class="addFlg" id="addFlg${status.index + 1}" value="0"></td>
														<td class="display_none"><input type="hidden" class="updateFlg" id="updateFlg${status.index + 1}" value="0"></td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td id="${status.index + 1}" class="contextMenu align_center">
															<span>${status.index + 1}</span>
															<input type="hidden" class="no" value="${status.index + 1}" >
														</td>
														<td class="align_center">${item.custCode}</td>
														<td>${item.custNmR}</td>
														<c:choose>
															<c:when test="${item.shopCode == hiddenConfig}">
																<td>---</td>
																<td>---</td>
															</c:when>
															<c:otherwise>
																<td>${item.shopCode}</td>
																<td>${item.shopNmR}</td>
															</c:otherwise>
														</c:choose>
														<td class="align_center">${item.binKb}便</td>
														<td class="align_center">
															<span>${item.deliveryDivisionName}</span>
															<input type="hidden" class="deliKbCode" value="${item.deliKb}">
														</td>
														<td class="display_none">${item.stsCode}</td>
														<td class="display_none"><input type="hidden" class="addFlg" id="addFlg${status.index + 1}" value="0"></td>
														<td class="display_none"><input type="hidden" class="updateFlg" id="updateFlg${status.index + 1}" value="0"></td>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						<button class="btn_button float_left" id="btnRegister" type="button">登録</button>
						<button class="btn_button float_right mr0" id="btnClearFooter" type="button">クリア</button>
					</div>
				</div>
				<input id="targetRowNo" type="hidden">
				<input id="editMode" type="hidden" value="1">
				<form:hidden id="businessDateID" path="businessDate" />
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="jigyoCode" path="form1JigyoCode"/>
				<form:hidden id="courseCode" path="form1CourseCode"/>
				<form:hidden id="courseName" path="form1CourseName"/>
				<form:hidden id="haisoKb" path="form1HaisoKb"/>
				<form:hidden id="cancelData" path="form1CancelData" />
				<form:hidden id="mode" path="mode" />
				<form:hidden id="selectjigyoCodeHidden" path="selectJigyoCodeHidden" />
				<form:hidden id="haitaDate" path="haitaDate" />
				<form:hidden id="haitaTime" path="haitaTime" />
				<form:hidden id ="errorControl" path="errorControl"/>
				<input type="hidden" id="hiddenConfigShop" value="${hiddenConfig}" >
				<form:input id="txtHaisoTime" type="hidden" path="txtHaisoTime" />
				<form:input id="txtShipUpdateTime" type="hidden" path="txtShipUpdateTime" />
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
				<!--end dialog-->
				<div id="tableMenu" class="sub-menu">
					<a href="#" id="modifySubMenu" name="modifySubMenu"><img src="${pageContext.request.contextPath}/resources/img/tool_modify.png" height="12" /> 訂正</a>
					<a href="#" id="deleteSubMenu" name="deleteSubMenu"><img src="${pageContext.request.contextPath}/resources/img/tool_remove.png" height="12" /> 行削除</a>
				</div>
				<button id="btnBackHidden" type="submit" name="Return" class="display_none"></button>
				
				<!-- default message start -->
				<c:forEach items="${defaultMessages}" var="defaultMessage" varStatus="status">
					<input type="hidden" id="${defaultMessage.messageCode}" name="defaultMessages" value="${defaultMessage.messageContent}" title="${defaultMessage.messageTitle}" />
				</c:forEach>
			</div>		
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>