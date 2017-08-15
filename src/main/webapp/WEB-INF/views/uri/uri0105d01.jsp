<!DOCTYPE html>
<%--
  ファイル名:uri0105d01.jsp
  画面名:修正データ照合画面

  作成者:ABV）コイー
  作成日:2015/12/08
  
  履歴
-------------------------------------------------------------------------
  日付                 指示者      担当            内容
-------------------------------------------------------------------------
  2015/12/08 1.00                  ABV）コイー        新規開発
-------------------------------------------------------------------------
  
ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
--%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/uri/uri0105d01.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css" />

<script src="<c:url value="/resources/js/lib/jquery1.11.1.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui-1.11.2.custom.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery.ui.datepicker-jp.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/lib/jquery-ui.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/load_dialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/uri/uri0105d01.js" />" type="text/javascript"></script>

<tiles:insertDefinition name="menuTemplate">
	<tiles:putAttribute name="body">
		<form:form action="proc" method="POST" modelAttribute="formUri0105d01" id="formUri0105d01">
			<div id="wrapper">
				<!--page note output -->
				<div id="messError">
					<div>
						<span id="txtMess">
							<c:forEach items="${errorMessages}" var="errorMessages" varStatus="status">	${errorMessages.messageContent}<br />
							</c:forEach>
						</span>
					</div>
				</div>
				<div class="content">
					<div class="data_conditions_area">
						<div class="data_conditions_area_cont">
							<div class="condition_title">
								<h3>
									<span>■</span>データ呼び出し条件
								</h3>
							</div>
							<div class="condition_cont">
								<table id="tblDataCondition" class="tbl_data_condition">
									<tr>
										<td class="align_right td_width90">事業所</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width350">
											<form:select id="ddlOyaShozoku" path="ddlOyaShozoku" class="cbx_width185">
												<form:options items="${OyaShozokuClassList}" itemLabel="name" itemValue="code" />
											</form:select>
										</td>
										<td colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right td_width90">データ受信日</td>
										<td class="td_width3 orange_line"></td>
										<td class="td_width365">
											<form:input id="txtJuDateFrom" path="txtJuDateFrom" class="txt_width90 align_center mr5" type="text" value="" maxlength="10"/>
											<span class="pl10 pr10">～</span>
											<form:input id="txtJuDateTo" path="txtJuDateTo" class="txt_width90 align_center mr5" type="text" value="" maxlength="10"/>
											<span class="pl10">YYYYMMDD</span>
										</td>
										<td colspan="6"></td>
									</tr>
									<tr>
										<td class="align_right td_width90">得意先コード</td>
										<td class="td_width3"></td>
										<td class="td_width350">
											<form:input type="text" class="txtCustCode" path="txtCustCode" id="txtCustCode" value="" maxlength="7" />
											<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btn_search_customer_id" alt="検索">
											<span class="color_blue" id="lblCustNmR"></span>
										</td>
										<td class="align_right td_width60">店舗コード</td>
										<td class="td_width3"></td>
										<td class="td_width250">
											<c:choose>
												<c:when test="${shopDisable == '1'}">
													<form:input type="text" class="txtShopCode txt_disable" path="txtShopCode" id="txtShopCode" value="" maxlength="8" tabindex="-1" readonly="true"/>
													<img src="${pageContext.request.contextPath}/resources/css/images/search_dis.png" class="searchIcon" id="btn_search_shop_id" alt="検索">
												</c:when>
												<c:otherwise>
													<form:input type="text" class="txtShopCode" path="txtShopCode" id="txtShopCode" value="" maxlength="8" />
													<img src="${pageContext.request.contextPath}/resources/css/images/search.png" class="searchIcon" id="btn_search_shop_id" alt="検索">													
												</c:otherwise>
											</c:choose>
											<span class="color_blue" id="lblShopNmR"></span>		
										</td>
										<td class="align_right td_width50">伝票No</td>
										<td class="td_width3"></td>
										<td>
											<form:input type="text" class="txtDenCode" path="txtDenCode" id="txtDenCode" value="" maxlength="9" />											
										</td>
										<td>											
											<form:checkbox id="chkSyori" path="chkSyori" value="chkSyori" />
											<label for="chkSyori" class="color_black">処理済みデータを対象とする</label>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_cont mt5">
								<table class="tbl_data_condition_bot">
									<tr>
										<td>
											<form:checkbox id="chkReceiptData" path="chkReceiptData" value="chkReceiptData" />
											<label for="chkReceiptData" class="color_black">受領データ</label>
										</td>
										<td>
											<form:checkbox id="chkReturnedData" path="chkReturnedData" value="chkReturnedData" />
											<label for="chkReturnedData">返品データ</label>
										</td>
										<td>
											<form:checkbox id="chkLackOfData" path="chkLackOfData" value="chkLackOfData" />
											<label for="chkLackOfData">欠品データ</label>
										</td>
										<td>
											<form:checkbox id="chkModifyData" path="chkModifyData" value="chkModifyData" />
											<label for="chkModifyData">修正データ</label>
										</td>
									</tr>
								</table>
							</div>
							<div class="condition_button">
								<button id="btnViewing" class="btn_button float_left mr5" type="button">表　示</button>
								<button id="btnConditionClear" class="btn_button float_left" type="button">条件クリア</button>
							</div>
						</div>
					</div>
					<div class="data_result_area clear">
						<div class="div_zentai">
							<div id="divHead" class="divHead">
								<table class="tbl_list">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width7per">
									<col class="td_width16per">
									<col class="td_width16per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width10per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width9per">
									<tr>
										<th>No</th>
										<th>選択<br><input type="checkbox"></th>
										<th>種別</th>
										<th>納品日</th>
										<th>得意先</th>
										<th>店舗</th>
										<th>伝票No</th>
										<th>品目コード</th>
										<th>品名</th>
										<th>受注数</th>
										<th>受信数量</th>
										<th>修正区分</th>
									</tr>
								</table>
							</div>
							<div id="divBody" class="divBody height315">
								<table id="tblBody" class="tbl_list">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width4per">
									<col class="td_width7per">
									<col class="td_width5per">
									<col class="td_width11per">
									<col class="td_width6per">
									<col class="td_width10per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width10per">
									<col class="td_width7per">
									<col class="td_width7per">
									<col class="td_width9per">
									<tr>
										<td class="align_right">1</td>
										<td class="align_center"><input type="checkbox"></td>
										<td class="align_center">受領</td>
										<td>2015/02/27</td>
										<td class="align_right">0231000</td>
										<td>㈱ＡＢＣ○○○○○</td>
										<td class="align_right">000000001</td>
										<td>あいうえ１２３４５</td>
										<td class="align_right">123456789</td>
										<td class="align_right">123456</td>
										<td>冠山うどん</td>
										<td class="align_right">26</td>
										<td class="align_right">23</td>
										<td>
											<select class="cbx_w100per bg_faf9e5 border_none">
												<option>受領欠品</option>
												<option></option>
												<option></option>
												<option></option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="align_right">2</td>
										<td class="align_center"><input type="checkbox"></td>
										<td class="align_center">受領</td>
										<td>2015/02/27</td>
										<td class="align_right">0231000</td>
										<td>㈱ＡＢＣ○○○○○</td>
										<td class="align_right">000000001</td>
										<td>あいうえ１２３４５</td>
										<td class="align_right">123456789</td>
										<td class="align_right">123456</td>
										<td>冠山うどん</td>
										<td class="align_right">26</td>
										<td class="align_right">23</td>
										<td>
											<select class="cbx_w100per border_none">
												<option>受領欠品</option>
												<option></option>
												<option></option>
												<option></option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="align_right"></td>
										<td class="align_center"></td>
										<td class="align_center"></td>
										<td></td>
										<td class="align_right"></td>
										<td></td>
										<td class="align_right"></td>
										<td></td>
										<td class="align_right"></td>
										<td class="align_right"></td>
										<td></td>
										<td class="align_right"></td>
										<td class="align_right"></td>
										<td></td>
									</tr>									
								</table>
							</div>
							<div class="recorded_date width230 mt10 pl30">
								<span class="margin10">計上日付</span>
								<span class="color_blue">2014/03/03</span>
							</div>
						</div>
					</div>
				</div> <!--/content-->
				<div class="footer_area bg_gray_c0c0c0">
					<div class="float_left">
						<button id="btnSale" class="btn_button float_left width105" type="button">売上訂正処理</button>
					</div>
					<div class="float_right">
						<button id="btnPrint" class="btn_button float_left mr5" type="button">印刷</button>
						<button id="btnClear" class="btn_button float_left mr0" type="button">クリア</button>
					</div>
				</div>
				<!--DIALOG-->
				<div id="overlay" class="web_dialog_overlay"></div>
				<div id="dialog" class="web_dialog"></div>
				<!--end dialog-->
				
				<!-- hidden proccess-->
				<form:hidden id="businessDate" path="businessDate" />
				<form:hidden id="sysAdminFlag" path="sysAdminFlag" />
				<form:hidden id="loginJigyouShoCode" path="loginJigyouShoCode" />
				<form:hidden id="loginBussinessDate" path="loginBussinessDate" />
				<form:hidden id ="errorControl" path="errorControl"/>
				<form:hidden id="lblCustNmRHidden" path="lblCustNmRHidden" />
				<form:hidden id="lblShopNmRHidden" path="lblShopNmRHidden" />
		</div>	
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>