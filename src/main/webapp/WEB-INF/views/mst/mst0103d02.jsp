<!DOCTYPE html>
<%--
  ファイル名:mst0103d02.jsp
  画面名:店舗マスタ管理画面

  作成者:ABV）TramChu
  作成日:2015/08/24
  
  履歴
  -------------------------------------------------------------------------
  日付                 指示者      担当            内容
  -------------------------------------------------------------------------
  2015/08/24 1.00                  ABV）TramChu       新規開発
  -------------------------------------------------------------------------
  
  ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mst/mst0103d02.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />
<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/mst/mst0103d02.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />"type="text/javascript"></script>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" cssErrorClass="error-input" modelAttribute="formMst0103d02" id="formMst0103d02">	
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
					<!--page note output -->
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
									<c:otherwise>
										取消
									</c:otherwise>
								</c:choose>
							</h3>
							</div>
							<div class="condition_cont">
								<table id="tblNewRegistration" class="tbl_new_registration mb30">
									<!--block1-->
									<tr>
										<td class="td_width120 align_right">得意先コード</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
													<c:when test="${modeView == '1'}">
														<form:input id="txtCustomerCode" path="txtCustomerCode" class="txt_width96" type="text" value="" maxlength="7" />
														<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon cursor_pointer" id="btn_search_customer_id" alt="検索">
													</c:when>
													<c:otherwise>
														<form:input id="txtCustomerCode" path="txtCustomerCode" class="txt_width96 txt_disable" type="text" value="" maxlength="7" readonly="true"/>
														<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="searchIcon" id="btn_search_customer_id" alt="検索" >
													</c:otherwise>
											</c:choose>											
											<span id="customerName" class="color_blue">${customerName}</span>
										</td>
										<td class="align_right"></td>
										<td class="td_width3"></td>
										<td></td>
									</tr>
									<tr>
										<td class="td_width120 align_right">店舗コード</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1'}">
														<form:input id="txtStoreCode" path="txtStoreCode" class="txt_width96" type="text" value="" maxlength="8"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtStoreCode" path="txtStoreCode" class="txt_width96 txt_disable" type="text" value="" maxlength="8" readonly="true"/>
													</c:otherwise>
											</c:choose>
											<span id="storeName" class="color_blue">${storeName}</span>
										</td>
									</tr>
									<tr><td class="height20 border_dot" colspan="6"></td></tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<!--block2 -->
									<tr>
										<td class="align_right">店舗名称</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtStoreName" path="txtStoreName" class="txt_width370 imeActive" type="text" value="" maxlength="20"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtStoreName" path="txtStoreName" class="txt_width370 imeActive txt_disable" type="text" value="" maxlength="20" readonly="true"/>
													</c:otherwise>
											</c:choose>
											
										</td>
									</tr>	
									<tr>
										<td class="align_right">店舗名称カナ</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtStoreNameKana" path="txtStoreNameKana" class="txt_width370 imeActive" type="text" value="" maxlength="20"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtStoreNameKana" path="txtStoreNameKana" class="txt_width370 imeActive txt_disable" type="text" value="" maxlength="20" readonly="true"/>
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">店舗略称</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtStoreAbbreviation" path="txtStoreAbbreviation" class="txt_width370 imeActive" type="text" value="" maxlength="10"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtStoreAbbreviation" path="txtStoreAbbreviation" class="txt_width370 imeActive txt_disable" type="text" value="" maxlength="10" readonly="true"/>
													</c:otherwise>
											</c:choose>									
										</td>
									</tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<tr>
										<td class="align_right">郵便番号</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtPostalCode1" path="txtPostalCode1" class="txt_width35" type="text" maxlength="9" value=""/>
														-
														<form:input id="txtPostalCode2" path="txtPostalCode2" class="txt_width44" type="text" maxlength="9" value=""/>
											（例：639-1038） 
													</c:when>
													<c:otherwise>
														<form:input id="txtPostalCode1" path="txtPostalCode1" class="txt_width35 txt_disable" type="text" maxlength="9" value="" readonly="true"/>
														-
														<form:input id="txtPostalCode2" path="txtPostalCode2" class="txt_width44 txt_disable" type="text" maxlength="9" value="" readonly="true"/>
											（例：639-1038） 
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">住所１</td>
										<td class="td_width3 orange_line"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtAddress1" path="txtAddress1" class="txt_width370 imeActive" type="text" value="" maxlength="20"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtAddress1" path="txtAddress1" class="txt_width370 imeActive txt_disable" type="text" value="" maxlength="20" readonly="true"/>
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">住所２</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtAddress2" path="txtAddress2" class="txt_width370 imeActive" type="text" value="" maxlength="20"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtAddress2" path="txtAddress2" class="txt_width370 imeActive txt_disable" type="text" value="" maxlength="20" readonly="true"/>
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">電話番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtPhoneNumber1" path="txtPhoneNumber1" class="txt_width45" type="text" maxlength="8" value=""/>
														-									
														<form:input id="txtPhoneNumber2" path="txtPhoneNumber2" class="txt_width45" type="text" maxlength="8" value=""/>
														-
														<form:input id="txtPhoneNumber3" path="txtPhoneNumber3" class="txt_width45" type="text" maxlength="8" value=""/>
														（例：0743-56-2911）
													</c:when>
													<c:otherwise>
														<form:input id="txtPhoneNumber1" path="txtPhoneNumber1" class="txt_width45 txt_disable" type="text" maxlength="8" value="" readonly="true"/>
														-									
														<form:input id="txtPhoneNumber2" path="txtPhoneNumber2" class="txt_width45 txt_disable" type="text" maxlength="8" value="" readonly="true"/>
														-
														<form:input id="txtPhoneNumber3" path="txtPhoneNumber3" class="txt_width45 txt_disable" type="text" maxlength="8" value="" readonly="true"/>
														（例：0743-56-2911）
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td class="align_right">FAX番号</td>
										<td class="td_width3"></td>
										<td colspan="4">
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtFaxNumber1" path="txtFaxNumber1" class="txt_width45" type="text" maxlength="8" value=""/>
														-
														<form:input id="txtFaxNumber2" path="txtFaxNumber2" class="txt_width45" type="text" maxlength="8" value=""/>
														-
														<form:input id="txtFaxNumber3" path="txtFaxNumber3" class="txt_width45" type="text" maxlength="8" value=""/>
													</c:when>
													<c:otherwise>
														<form:input id="txtFaxNumber1" path="txtFaxNumber1" class="txt_width45 txt_disable" type="text" maxlength="8" value="" readonly="true"/>
														-
														<form:input id="txtFaxNumber2" path="txtFaxNumber2" class="txt_width45 txt_disable" type="text" maxlength="8" value="" readonly="true"/>
														-
														<form:input id="txtFaxNumber3" path="txtFaxNumber3" class="txt_width45 txt_disable" type="text" maxlength="8" value="" readonly="true"/>
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<!--block3-->
									<tr><td class="height20 border_dot" colspan="6"></td></tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<tr>
										<td class="td_width120 align_right">事業所</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width550" colspan="4">
										<c:choose>
											<c:when test="${modeView == '1' || modeView == '3'}">
												<form:select id="ddlOffice" path="ddlOffice"  class="cbx_width185 float_left ${displayDdlOffice}">
													<form:options items="${lstMstSJigyo}" itemLabel="name" itemValue="code" />
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select id="ddlOffice" path="ddlOffice"  class="cbx_width185 float_left ${displayDdlOffice}" disabled="true">
													<form:options items="${lstMstSJigyo}" itemLabel="name" itemValue="code" />
												</form:select>
											</c:otherwise>													
										</c:choose>
											<span class="span_width27 float_left ${displayDdlOffice}">&nbsp;</span>
											<span class="float_left color_blue">${ddlOfficeName}</span>
										</td>
									</tr>
									<tr>
										<td class="td_width120 align_right">納品センター</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width550">
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">									
													<form:select id="ddlDeliveryCenter" path="ddlDeliveryCenter"  class="cbx_width374" disabled="${ddlDeliveryCenterDisable}">
														<form:options items="${lstDeliveryCenter}" itemLabel="name" itemValue="code" />
													</form:select>
												</c:when>
												<c:otherwise>
													<form:select id="ddlDeliveryCenter" path="ddlDeliveryCenter"  class="cbx_width374" disabled="${ddlDeliveryCenterDisable}">
														<form:options items="${lstDeliveryCenter}" itemLabel="name" itemValue="code" />
													</form:select>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="td_width120 align_right">サンクス区分</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">												
														<form:select id="ddlThanksClassification" path="ddlThanksClassification"  class="cbx_width374">
															<form:options items="${lstClassification}" itemLabel="name" itemValue="code" />
														</form:select>
													</c:when>
													<c:otherwise>
														<form:select id="ddlThanksClassification" path="ddlThanksClassification"  class="cbx_width374" disabled="true">
															<form:options items="${lstClassification}" itemLabel="name" itemValue="code" />
														</form:select>
													</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<!--block4-->
									<tr><td class="height20 border_dot" colspan="6"></td></tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<tr>
										<td class="align_right align_top">社店コード</td>
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
															<c:when test="${modeView == '1' || modeView == '3'}">
																<form:input id="txtFixMisejika" path="txtFixMisejika" class="txt_width60 border_none" type="text" value="" maxlength="6"/>
															</c:when>
															<c:otherwise>
																<form:input id="txtFixMisejika" path="txtFixMisejika" class="txt_width60 border_none txt_disable" type="text" value="" maxlength="6" readonly="true"/>
															</c:otherwise>
													</c:choose>												
													</td>
												</tr>
												<tr>
													<td class="color_darkred">センター</td>
													<td class="align_right">
														<c:choose>
															<c:when test="${modeView == '1' || modeView == '3'}">
																<form:input id="txtFixCenter" path="txtFixCenter" class="txt_width60 border_none" type="text" value="" maxlength="6"/>
															</c:when>
															<c:otherwise>
																<form:input id="txtFixCenter" path="txtFixCenter" class="txt_width60 border_none txt_disable" type="text" value="" maxlength="6" readonly="true"/>
															</c:otherwise>
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
															<c:when test="${modeView == '1' || modeView == '3'}">
																<form:input id="txtSaleMisejika" path="txtSaleMisejika" class="txt_width60 border_none" type="text" value="" maxlength="6"/>
															</c:when>
															<c:otherwise>
																<form:input id="txtSaleMisejika" path="txtSaleMisejika" class="txt_width60 border_none txt_disable" type="text" value="" maxlength="6" readonly="true"/>
															</c:otherwise>
														</c:choose>												
													</td>
												</tr>
												<tr>
													<td class="color_darkred">センター</td>
													<td class="align_right">
														<c:choose>
															<c:when test="${modeView == '1' || modeView == '3'}">
																<form:input id="txtSaleCenter" path="txtSaleCenter" class="txt_width60 border_none" type="text" value="" maxlength="6"/>
															</c:when>
															<c:otherwise>
																<form:input id="txtSaleCenter" path="txtSaleCenter" class="txt_width60 border_none txt_disable" type="text" value="" maxlength="6" readonly="true"/>
															</c:otherwise>
														</c:choose>
														
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!--block5-->
									<tr><td class="height20 border_dot" colspan="6"></td></tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<tr>
										<td class="td_width120 align_right"></td>
										<td class="td_width3"></td>
										<td class="td_width550">
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:checkbox id="chkDestinationStore" path="chkDestinationStore" />
												</c:when>
												<c:otherwise>
													<form:checkbox id="chkDestinationStore" path="chkDestinationStore" disabled="true"/>
												</c:otherwise>
											</c:choose>
											<label class="mr30" for="chkDestinationStore">集約先店舗として登録する</label>
											<span class="ml50 mr10">店舗集約条件</span>
											<img class="orange_line_shape align_middle" src="${pageContext.request.contextPath}/resources/img/orange_line.jpg" width="3" height="23" alt="">
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:select id="ddlCondition" path="ddlCondition" class="cbx_width100">
														<form:options items="${lstCondition}" itemLabel="name" itemValue="code" />
													</form:select>
												</c:when>
												<c:otherwise>
													<form:select id="ddlCondition" path="ddlCondition" class="cbx_width100" disabled="true">
														<form:options items="${lstCondition}" itemLabel="name" itemValue="code" />
													</form:select>
												</c:otherwise>
											</c:choose>									
										</td>
										<td class="td_width120 align_right">集約先店舗コード</td>
										<td class="td_width3"></td>
										<td>
											<c:choose>
													<c:when test="${modeView == '1' || modeView == '3'}">
														<form:input id="txtCollectionDestination" path="txtCollectionDestination" class="txt_width130 float_left" type="text" value="" maxlength="8"/>
													</c:when>
													<c:otherwise>
														<form:input id="txtCollectionDestination" path="txtCollectionDestination" class="txt_width130 float_left txt_disable" type="text" value="" maxlength="8" readonly="true"/>
													</c:otherwise>
											</c:choose>									
											<span class="span_width27 float_left">&nbsp;</span>
											<span id="collectionDestination" class="float_left color_blue">${collectionDestination}</span>
										</td>
									</tr>
									<!--block6-->
									<tr><td class="height20 border_dot" colspan="6"></td></tr>
									<tr><td class="height20" colspan="6"></td></tr>
									<tr>
										<td class="td_width120 align_right">取引中止日</td>
										<td class="td_width3"></td>
										<td class="td_width550">
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:input id="txtDate" path="txtDate" class="txt_width130 align_center mr5" type="text" value="" maxlength="10"/>
												</c:when>
												<c:otherwise>
													<form:input id="txtDate" path="txtDate" class="txt_width130 mr5 align_center txt_disable" type="text" value="" maxlength="10" readonly="true"/>
												</c:otherwise>
											</c:choose>											
										</td>
										<td class="td_width120 align_right">状況コード</td>
										<td class="td_width3 orange_line"></td>
										<td>
											<c:choose>
												<c:when test="${modeView == '1' || modeView == '3'}">
													<form:input id="txtStatusCode" path="txtStatusCode" class="txt_width35 align_center" type="text" value="" maxlength="1"/>
										（1:登録 9:取消）
												</c:when>
												<c:otherwise>
													<form:input id="txtStatusCode" path="txtStatusCode" class="txt_width35 align_center txt_disable" type="text" value="" maxlength="1" readonly="true"/>
										（1:登録 9:取消）
												</c:otherwise>
											</c:choose>
											
										</td>
									</tr>
									<!--block7-->
									<tr><td class="height20 border_dot" colspan="6"></td></tr>
									<tr><td class="height20" colspan="6"></td></tr>	
									<tr>
										<td colspan="6">
											<div class="float_left">
												【コース情報】
												<table class="tbl_info_table tbl_width550">
													<col class="td_width40">
													<col class="td_width110">
													<col class="td_width250">
													<col class="td_width40">
													<col class="td_width110">
													<tr>
														<th>No</th>
														<th>コースコード</th>
														<th>コース名称</th>
														<th>便</th>
														<th>納品区分</th>
													</tr>
													<c:forEach var="item" items="${lstCourseInformation}" varStatus="status">
														<tr>
															<td class="align_center">${status.index +1}</td>
															<td class="align_center">${item.cosCode}</td>
															<td>${item.cosNmR}</td>
															<td class="align_center">${item.binKb}</td>
															<td>${item.deliKb}&nbsp;${item.target1}</td>
														</tr>
													</c:forEach>
												</table>
											</div>
											<div class="float_right">
												【オンライン得意先変換マスタ情報】
												<table class="tbl_info_table tbl_width460">
													<col class="td_width40">
													<col class="td_width110">
													<col class="td_width100">
													<col class="td_width100">
													<col class="td_width110">
													<tr>
														<th>No</th>
														<th>EOSセンター</th>
														<th>相手取引先コード</th>
														<th>相手店コード</th>
														<th>納品区分</th>
													</tr>													
													<c:forEach var="item" items="${lstCommonSpecificationsConfirmation}" varStatus="status">
														<tr>
															<td class="align_center">${status.index +1}</td>
															<td>${item.olCenterCode}</td>
															<td>${item.atTorihikiCode}</td>
															<td>${item.atTenCode}</td>												
															<td>${item.deliKb}&nbsp;${item.target1}</td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div> <!--/content-->
				<div class="footer_area bg_gray_c0c0c0">
					<div>
						<c:choose>
							<c:when test="${modeView != '2'}">
							<button id="btnRegister" class="btn_button float_left" type="button" name="Register">登録</button>
							<button id="btnRegisterHidden" class="display_none" type="submit" name="Register"></button>
							</c:when>
						</c:choose>
						<button id="btnClear" class="btn_button float_right mr0" type="button" name="Clear">クリア</button>
					</div>
				</div>
				<!-- hidden proccess-->
				<form:input id="businessDate" type="hidden" path="businessDate"/>
				<form:input id="frm1Office" type="hidden" path="frm1Office"/>
				<form:input id="frm1CustomerCode" type="hidden" path="frm1CustomerCode"/>
				<form:input id="frm1CustomerName" type="hidden" path="frm1CustomerName"/>
				<form:input id="frm1StoreCode" type="hidden" path="frm1StoreCode"/>
				<form:input id="frm1StoreName" type="hidden" path="frm1StoreName"/>
				<form:input id="frm1chkCancelData" type="hidden" path="frm1chkCancelData"/>
				<form:input id="sysAdminFlag" type="hidden" path="sysAdminFlag"/>
				<form:input id="loginJigyouShoCode" type="hidden" path="loginJigyouShoCode"/>				
				<form:input id="loginJigyouShoName" type="hidden" path="loginJigyouShoName"/>				
				<form:input id="haitaDate" type="hidden" path="haitaDate" />
				<form:input id="haitaTime" type="hidden" path="haitaTime" />
				<form:input id="deliveryCenterSelect" type="hidden" path="deliveryCenterSelect" />
				<form:input id="officeSelect" type="hidden" path="officeSelect" />
				<form:input id="chkDestinationStoreDis" type="hidden" path="chkDestinationStoreDis" />
				<form:input id="thanksClassificationSelect" type="hidden" path="thanksClassificationSelect" />
				<form:input id="conditionSelect" type="hidden" path="conditionSelect" />
				<form:input id="txtPostalCodeResult" type="hidden" path="txtPostalCodeResult" />
				<form:input id="txtPhoneNumberResult" type="hidden" path="txtPhoneNumberResult" />
				<form:input id="txtFaxNumberResult" type="hidden" path="txtFaxNumberResult" />
				<form:input id="tenCodeNone" type="hidden" path="tenCodeNone" />
				<input id="errControl" type="hidden" value="${errControl}"/>
				<form:input id="mode" type="hidden" path="mode"/>
				<input type="hidden" id="infoMessFlag" value="${infoMessFlag}" />
				<button id="btnBackHidden" type="submit" name="Return" class="display_none"></button>
				<input type="hidden" id="lstErrorID" value="${lstErrorID}" />
				<input type="hidden" id="infoMessFlag" value="${infoMessFlag}" />
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