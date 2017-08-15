/**
 * ファイル名:mst0104d01.js
 * 概要:コースマスタメンテナンス登録画面
 * 
 * 作成者:ABV）NhungMa
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00                  ABV）NhungMa        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/
//Controlエラーの受信デリミター
var DELIMITER = "##";
var isTableMenuShowing = false;
var currentShowingColId = "";
var rowSelected;
var NOT_EXIST_DATA = "NOT_EXIST";
var HYPHEN_MARK_TEXT = "---";
var KOSHIN_TEXT = "更新";
var TOROKU_TEXT = "登録";
var TSUIKA_TEXT = "追加";
var TORIKESHI_TEXT = "取消";
var TYPE_UNDEFINED = "undefined";
var TYPE_SUCESS = "sucess";
var originalDataCourse = [];
var originalDataCourseList = [];
jQuery(document).ready(function($) {
	
	//Dialogのadd_clickイベントの作成
	loadDialogCom0102d03();
	
	loadDialogCom0102d02();
	
	/** 変数定義 */
	var isClickButton = false;
	var txtCustomerCodeValue = "";
	var txtShpCodeValue = "";
	var modeVal = $("#mode").val();
	var strError = "";
	originalDataCourse = saveDataCourse();
	originalDataCourseList = saveDataCourseList();
	showButtonStatus();
	setFocus();
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".data_area_check");
		}
	});
	resizeTbl();
	/**
	 * resize table when change screen dimension
	 */
	function resizeTbl() {
		// table scroll
		var tblBodyHeight = $("#tblBody").height();
		var divBodyHeight = $("#divBody").height();
		if (tblBodyHeight > divBodyHeight) {
			$("#divBody").css("overflow-y", "scroll");
			$("#divHead").width($("#divBody").width() - 17);
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width() - 17);
			});
		} else {
			$("#divHead").width($("#divBody").width());
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width());
			});
			$("#divBody").css("overflow-y", "hidden");
		}
	}
	if(modeVal == SHINKI_MODE_CHAR || modeVal == TEISEI_MODE_CHAR) {
		$(document).on("mouseover", ".contextMenu", function(){
			// change text to "▼" when user is hovering on a No column
			$(this).find("span").text("▼");
			$(this).find("span").css("cursor","pointer");
			// if menu is showing, hide menu when user is hovering on other
			// No columns
			if (isTableMenuShowing) {
				// only hide menu if user is hovering on other No columns,
				// not the column that is showing menu
				if ($(this).attr("id") != currentShowingColId) {
					// hide menu
					$("#tableMenu").fadeOut("fast");
					// switch flag value
					isTableMenuShowing = false;
					// change the text back from "▼" to number
					$("#" + currentShowingColId).find("span").text(
							currentShowingColId);
				}
			}
			// is menu is not showing, change the text back from "▼" to
			// number
			if ($(this).attr("id") != currentShowingColId) {
				$("#" + currentShowingColId).find("span").text(
						currentShowingColId);
				currentShowingColId = $(this).attr("id");
			}
		});
		
		$(document).on("click", ".contextMenu", function(e){
			// click event on No column
			// get current coordinates of mouse
			var x = e.pageX;
			var y = e.pageY;
			// show menu at current mouse's coordinates
			$("#tableMenu").css("position", "absolute").css("left",
					x + "px").css("top", y + "px").fadeIn("fast");
			if(modeVal == TEISEI_MODE_CHAR) {
				if($(this).closest("tr").find("td").find(".addFlg").val() == "0") {
					$("#deleteSubMenu").css("display","none");
				} else {
					$("#deleteSubMenu").css("display","block");
				}
			}
			// switch flag value
			isTableMenuShowing = true;
			strSelectedCourseCode = $(this).attr("name");
			rowSelected = $(this).parent();
		});
		$(document).on("mouseover", "td", function(){
			if (!$(this).hasClass("contextMenu")) {
				// if menu is not showing, change the text back from "▼" to
				// number
				if (!isTableMenuShowing) {
					$("#" + currentShowingColId).find("span").text(currentShowingColId);
				}
			}
			
		});
		
		// hover event on sub menu items
		$(".sub-menu a").bind("mouseover", function() {
			$(this).css("background-color", "#003366").css("color", "#FFFFFF");
		}).bind("mouseout", function() {
			$(this).css("background-color", "#FFFFFF").css("color", "#003366");
		});

		$(document).bind("click", function(e) {
			
			var container1 = $(".contextMenu");

			if (!container1.is(e.target) // if the target of the click
					// isn't the container
					&& container1.has(e.target).length === 0
					&& $("#tableMenu").is(":visible")) { // nor a
				// descendant of
				// the container
				$("#tableMenu").fadeOut("fast");
				$("#" + currentShowingColId).find("span").text(
						currentShowingColId);
				isTableMenuShowing = false;
			}
		});
		// ▼処理ＥＮＤ
	}
	
	/**
	 * 表示ボタンがクリックされた時の処理。入力エリアと納入先一覧を表示する
	 */
	$("#btnConfig").bind("click", function(e){
		isClickButton = true;
		var errorMessage = "";
		$(".tbl_data_condition_cont input").removeClass("form-error-field");
		setTimeout(function(){
			if(!checkInputConfig()) {
				$.ajax({
					type: "POST",
					url: "config",
					async : false,
					data:$("form").serialize(),
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(returnJsonData) {
						if(returnJsonData.errorMessage != "" && returnJsonData.errorMessage != null) {
							for(var i = 0; i < returnJsonData.errorMessage.length; i++) {
								errorMessage += returnJsonData.errorMessage[i].messageContent  + "<br>";
							}
							$("#txtMess").html(errorMessage);
							strError = returnJsonData.strId;
							// add background for input textbox when error
							if (strError != null && strError != "" && strError != NOT_EXIST_DATA) {
								var lstError = strError.split(DELIMITER);
								addClassErrorToControl(lstError);
								setFocus();
							}
						} else {
							strError = returnJsonData.strId;
							checkExist();
							$("#txtMess").html("");
						}
					}
				});
				if($("#ddlJigyoCode").val() != "" && strError == NOT_EXIST_DATA) {
					$("#ddlJigyoCode").removeClass("form-error-field");
				}
			}
			isClickButton = false;
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * 入力チェック
	 */
	function checkInputAddUpdate() {
		var errIdAddUpdate = "";
		var strErrorAddUpdate = "";
		var strIdAddUpdate = "";
		errIdAddUpdate = chkItem($("#txtCustomerCode").val(), true, TYPE_NUM, false, null);
		if(errIdAddUpdate != null && errIdAddUpdate != "") {
			strIdAddUpdate += "txtCustomerCode" + DELIMITER;
			strErrorAddUpdate += $("#" + errIdAddUpdate).val().replace("{1}", "得意先コード") + "<br>";
		}
		errIdAddUpdate = chkItem($("#txtShopCode").val(), true, TYPE_NUM, false, null);
		if(errIdAddUpdate != null && errIdAddUpdate != "") {
			strIdAddUpdate += "txtShopCode" + DELIMITER;
			strErrorAddUpdate += $("#" + errIdAddUpdate).val().replace("{1}", "店舗コード") + "<br>";
		}
		errIdAddUpdate = chkItem($("#txtBinKb").val(), true, TYPE_NUM, false, null);
		if(errIdAddUpdate != null && errIdAddUpdate != "") {
			strIdAddUpdate += "txtBinKb" + DELIMITER;
			strErrorAddUpdate += $("#" + errIdAddUpdate).val().replace("{1}", "便区分") + "<br>";
		}
		if($("#ddlDeliKb").val() == "") {
			strIdAddUpdate += "ddlDeliKb" + DELIMITER;
			strErrorAddUpdate += $("#COM006-E").val().replace("{1}", "納品区分") + "<br>";
		}
		errIdAddUpdate = chkItem($('#txtStsCode02').val(), true, TYPE_NUM, false, null);
		if(errIdAddUpdate != null && errIdAddUpdate != "") {
			strIdAddUpdate += "txtStsCode02" + DELIMITER;
			strErrorAddUpdate += $("#" + errIdAddUpdate).val().replace("{1}", "状況コード") + "<br>";
		}
		var shopCodeVal = $("#txtShopCode").val();
		var hiddenConfig = $("#hiddenConfigShop").val();
		if(shopCodeVal == hiddenConfig) {
			strIdAddUpdate += "txtShopCode" + DELIMITER;
			strErrorAddUpdate += $("#COM030-E").val() + "<br>";
		}
		var binKb = $("#txtBinKb").val();
		if(binKb != "" && (binKb < CHECK_BINKB_VALUE_1 || binKb > CHECK_BINKB_VALUE_9)) {
			strIdAddUpdate += "txtBinKb" + DELIMITER;
			strErrorAddUpdate += $("#MST013-E03").val() + "<br>";
		}
		
		var stsCode = $("#txtStsCode02").val();
		if(stsCode != "" && (stsCode != TOROKU && stsCode != TORIKESHI)) {
			strIdAddUpdate += "txtStsCode02" + DELIMITER;
			strErrorAddUpdate += $("#MST013-E04").val() + "<br>";
		}
		if(strErrorAddUpdate != "") {
			$("#txtMess").html(strErrorAddUpdate);
			removeInfoClass();
			var lstErrorAddUpdate = strIdAddUpdate.split(DELIMITER);
			addClassErrorToControl(lstErrorAddUpdate);
			setFocus();
			resizeTbl();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 入力チェックを行う
	 */
	function checkInputConfig() {
		var errIdConfig = "";
		var strErrorConfig = "";
		var strIdConfig = "";
		// 事業所
		if($("#ddlJigyoCode").val() == "") {
			strIdConfig += "ddlJigyoCode" + DELIMITER;
			strErrorConfig += $("#COM006-E").val().replace("{1}", "事業所") + "<br>";
		}
		// コースコード
		errIdConfig = chkItem($("#txtCourseCode").val(), true, TYPE_NUM, false, null);
		if(errIdConfig != null && errIdConfig != "") {
			strIdConfig += "txtCourseCode" + DELIMITER;
			strErrorConfig += $("#" + errIdConfig).val().replace("{1}", "コースコード") + "<br>";
		}
		// コース名称
		errIdConfig = chkItem($("#txtCourseName").val(), true, TYPE_EM, false, null);
		if(errIdConfig != null && errIdConfig != "") {
			strIdConfig += "txtCourseName" + DELIMITER;
			strErrorConfig += $("#" + errIdConfig).val().replace("{1}", "コース名称") + "<br>";
		}
		// コース略称
		errIdConfig = chkItem($("#txtCourseNameR").val(), true, TYPE_EM, false, null);
		if(errIdConfig != null && errIdConfig != "") {
			strIdConfig += "txtCourseNameR" + DELIMITER;
			strErrorConfig += $("#" + errIdConfig).val().replace("{1}", "コース略称") + "<br>";
		}
		// 状況コード
		errIdConfig = chkItem($("#txtStsCode01").val(), true, TYPE_NUM, false, null);
		if(errIdConfig != null && errIdConfig != "") {
			strIdConfig += "txtStsCode01" + DELIMITER;
			strErrorConfig += $("#" + errIdConfig).val().replace("{1}", "状況コード") + "<br>";
		}
		// 配送時間
		var haisoTimeInput = $("#txtHaisoTime01").val() + $("#txtHaisoTime02").val()
		errIdConfig = chkItem(haisoTimeInput, false, TYPE_NUM, false, null);
		if(errIdConfig != null && errIdConfig != "") {
			strIdConfig += "txtHaisoTime01" + DELIMITER + "txtHaisoTime02" + DELIMITER;
			strErrorConfig += $("#" + errIdConfig).val().replace("{1}", "配送時間") + "<br>";
		}
		// 出荷更新時間
		var shipUpdateTimeInput = $("#txtShipUpdateTime01").val() + $("#txtShipUpdateTime02").val()
		errIdConfig = chkItem(shipUpdateTimeInput, false, TYPE_NUM, false, null);
		if(errIdConfig != null && errIdConfig != "") {
			strIdConfig += "txtShipUpdateTime01" + DELIMITER + "txtShipUpdateTime01" + DELIMITER;;
			strErrorConfig += $("#" + errIdConfig).val().replace("{1}", "出荷更新時間") + "<br>";
		}
		// [画面]状況コード ≠ NULL and ([画面]状況コード ≠ '1' or [画面]状況コード ≠ '9')
		if($("#txtStsCode01").val() != "" && ($("#txtStsCode01").val() != TOROKU && $("#txtStsCode01").val() != TORIKESHI)) {
			strIdConfig += "txtStsCode01" + DELIMITER;
			strErrorConfig += $("#MST013-E02").val() + "<br>";
		}
		// 配送区分
		if($("#txtHaisoKb").val() != "" && ($("#txtHaisoKb").val() != JISHA_HAISO && $("#txtHaisoKb").val() != TAKUHAIBIN)) {
			strIdConfig += "txtHaisoKb" + DELIMITER;
			strErrorConfig += $("#MST013-E01").val() + "<br>";
		}
		if(strErrorConfig != "") {
			$("#txtMess").html(strErrorConfig);
			removeInfoClass();
			var lstErrorConfig = strIdConfig.split(DELIMITER);
			addClassErrorToControl(lstErrorConfig);
			setFocus();
			resizeTbl();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 追加／更新ボタンがクリックされた時の処理。入力エリアの情報を納入先一覧に追加及び更新する
	 */
	$("#btnAddUpdate").bind("click", function(e){
		var strError = "";
		isClickButton = true;
		disableButton("btnAddUpdate");
		setTimeout(function(){
			var errorMessage = "";
			var checkValTbl = false;
			var checkMode = $("#editMode").val();
			var ddlJigyoCode = $("#ddlJigyoCode").val();
			var txtCustomerCode = $("#txtCustomerCode").val();
			var lblCustomerNameR = $("#lblCustomerNameR").text();
			var txtShopCode = $("#txtShopCode").val();
			var lblShopNameR = $("#lblShopNameR").text();
			var tempObj = {
				ddlJigyoCode: ddlJigyoCode,
				txtCustomerCode: txtCustomerCode,
				lblCustomerNameR: lblCustomerNameR,
				txtShopCode: txtShopCode,
				lblShopNameR: lblShopNameR,
			};
			if(!checkInputAddUpdate()) {
				$.ajax({
					type: "POST",
					url: "addUpdate",
					async : false,
					contentType: 'application/json; charset=utf-8',
					dataType: "json",
					data : JSON.stringify(tempObj),
					success: function(returnJsonData) {
						if(returnJsonData.msgExist != "" && returnJsonData.msgExist != null) {
							var strSetData = returnJsonData.msgExist;
							var lstSetData = strSetData.split(DELIMITER);
							for (var i = 0 ; i < lstSetData.length ; i++) {
								if(lstSetData[i] == "lblCustomerNameR") {
									$("#" + lstSetData[i]).text(returnJsonData.lblCustomerNameR);
								} else if(lstSetData[i] == "lblShopNameR") {
									$("#" + lstSetData[i]).text(returnJsonData.lblShopNameR);
								}
							}
						}
						if(returnJsonData.errorMessage != "" && returnJsonData.errorMessage != null) {
							for(var i = 0; i < returnJsonData.errorMessage.length; i++) {
								errorMessage += returnJsonData.errorMessage[i].messageContent  + "<br>";
							}
						} 
						if($("#txtCustomerCode").val() != "" && $("#txtShopCode").val() != "") {
							if(checkMode != UPDATE_MODE) {
								$("#tblBody tr").each(function() {
									var checkCusCode = $(this).find("td").eq(1).text();
									var checkShpCode = $(this).find("td").eq(3).text() ;
									var checkBinKb = $(this).find("td").eq(5).text().replace("便","");
									if(checkCusCode == $("#txtCustomerCode").val() && checkShpCode == $("#txtShopCode").val()
											&& checkBinKb == $("#txtBinKb").val()) {
										checkValTbl = true;
									}
								});
							}
							if(checkValTbl) {
								errorMessage += $("#COM027-E").val();
								removeInfoClass();
								setFocus();
							}
						}
						if(errorMessage != "") {
							$("#txtMess").html(errorMessage);
							removeInfoClass();
							strError = returnJsonData.strId;
							if (strError != null && strError != "") {
								var lstError = strError.split(DELIMITER);
								addClassErrorToControl(lstError);
								setFocus();
							}
						} else {
							if(checkMode == ADD_MODE) {
								var count = $("#tbl_tbody").children("tr").length;
								var count_display= $("#tbl_tbody").children("tr.display_none").length;
								var row = newRow((count - count_display) + 1);
								$("#tblBody > tbody:last-child").append(row);
								$("#txtMess").html($("#MST001-I").val().replace("{2}", (count - count_display)+ 1));
								addInfoClass();
								resizeTbl();
							} else if(checkMode == UPDATE_MODE) {
								var updDeliKb = $("#ddlDeliKb :selected").text();
								var deliTextUpd = updDeliKb.substring(updDeliKb.indexOf(":") + 1).trim();
								var updStsCode = $("#txtStsCode02").val();
								var targetRowNo = $("#targetRowNo").val();
								rowSelected.find("td").eq(6).find("span").text(deliTextUpd);
								rowSelected.find("td").eq(6).find(".deliKbCode").val($("#ddlDeliKb").val());
								rowSelected.find("td").eq(7).text(updStsCode);
								rowSelected.find("td").eq(9).find(".updateFlg").val(ADD_MODE);
								$("#txtMess").html($("#MST002-I").val().replace("{2}", targetRowNo));
								addInfoClass();
							}
							enableEditArea();
							$("#btnRegister").removeAttr('disabled');
							$("#btnRegister").removeClass("btn_button_disabled").addClass("btn_button");
							$("#editMode").val(ADD_MODE);
							setFocusAddUpdate(checkMode);
							$(".tbl_data_condition_search input").removeClass("form-error-field");
							$(".tbl_data_condition_search select").removeClass("form-error-field");
						}
					}
				});
			}
			isClickButton = false;
			enableButton("btnAddUpdate");
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * フォーカスセット
	 */
	function setFocusAddUpdate(editMode) {
		if(editMode == ADD_MODE) {
			$("#txtCustomerCode").focus();
		} else if(editMode == UPDATE_MODE) {
			$("#btnClearCustomer").focus();
		}
	}
	
	/**
	 * draw new row
	 */
	function newRow(renban) {
		var htmlRow = "";
		
		htmlRow +="<tr>";
		var cusCodeVal = $("#txtCustomerCode").val();
		var cusNameVal = $("#lblCustomerNameR").html();
		var shpCodeVal = $("#txtShopCode").val();
		var shpNameVal = $("#lblShopNameR").html();
		var binKbVal = $("#txtBinKb").val();
		var deliKb = $("#ddlDeliKb :selected").text();
		var deliText = deliKb.substring(deliKb.indexOf(":") + 1).trim();
		var deliKbVal = $("#ddlDeliKb").val();
		var stsCodeVal = $("#txtStsCode02").val();
		
		htmlRow += "<td id='" + renban + "' class='contextMenu align_center'><span>" + renban + "</span>" +
				"<input type='hidden' class='no' value='" + renban + "'></td>";
		htmlRow += "<td class='align_center'>" + ReplaceNullReturnBlank(cusCodeVal) + "</td>";
		htmlRow += "<td>" + ReplaceNullReturnBlank(cusNameVal) + "</td>";
		if(shpCodeVal == "") {
			htmlRow += "<td>---</td>";
			htmlRow += "<td>---</td>";
		} else {
			htmlRow += "<td>" + ReplaceNullReturnBlank(shpCodeVal) + "</td>";
			htmlRow += "<td>" + ReplaceNullReturnBlank(shpNameVal) + "</td>";
		}
		htmlRow += "<td class='align_center'>" + ReplaceNullReturnBlank(binKbVal) + "便" + "</td>";
		htmlRow += "<td class='align_center'><span>" + ReplaceNullReturnBlank(deliText) + "</span>" +
		 "<input type='hidden' class='deliKbCode' value='" + ReplaceNullReturnBlank(deliKbVal) + "'></td>";
		htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(stsCodeVal) + "</td>";
		htmlRow += "<td class='display_none'><input type='hidden' class='addFlg' value='1'></td>";
		htmlRow += "<td class='display_none'><input type='hidden' class='updateFlg' value='1'></td>";
		htmlRow += "</tr>";
		return htmlRow;
	}
	
	/**
	 * 納入先一覧の編集メニューにて「訂正」をクリックされた時の処理。選択行の情報を入力エリアに表示する
	 */
	$("#modifySubMenu").bind("click", function(e){
		// [画面_hidden]編集モード
		$("#editMode").val(UPDATE_MODE);
		$(".tbl_list tr").each(function() {
			$(this).removeClass("bg_00B0F0");
		});
		isTableMenuShowing = false;
		rowSelected.addClass("bg_00B0F0");
		
		var cusCodeRow = rowSelected.find("td").eq(1).text();
		var cusNameRow = rowSelected.find("td").eq(2).text();
		var shopCodeRow = rowSelected.find("td").eq(3).text();
		var shopNameR = rowSelected.find("td").eq(4).text();
		var binKbRow = rowSelected.find("td").eq(5).text().replace("便","");
		var ddlDeliKbRow = rowSelected.find("td").eq(6).find(".deliKbCode").val();
		var stsCodeRow = rowSelected.find("td").eq(7).text();
		// [画面_hidden]対象行No = [画面]納入先一覧[i].No
		$("#targetRowNo").val(rowSelected.find("td").find(".no").val());
		$("#txtCustomerCode").val(cusCodeRow);
		$("#lblCustomerNameR").val(cusNameRow);
		if(shopCodeRow == HYPHEN_MARK_TEXT) {
			$("#txtShopCode").val("");
			$("#lblShopNameR").val("");
		} else if(shopCodeRow != HYPHEN_MARK_TEXT) {
			$("#txtShopCode").val(shopCodeRow);
			$("#lblShopNameR").val(shopNameR);
		}
		$("#txtBinKb").val(binKbRow);
		$("#ddlDeliKb").val(ddlDeliKbRow);
		$("#txtStsCode02").val(stsCodeRow);
		$("#btnAddUpdate").text(KOSHIN_TEXT);
		// 編集エリアの入力項目制御
		controlEditArea();
		// フォーカスセット
		$("#ddlDeliKb").focus();
		$("#tableMenu").fadeOut("fast");
	});
	
	/**
	 * 納入先一覧削除処理
	 */
	$("#deleteSubMenu").bind("click", function() {
		$("#tableMenu").fadeOut("fast");
		var mode = $("#mode").val();
		rowSelected.remove();
		isTableMenuShowing = false;
		$("#txtMess").html("");
		var count_display= $('#tbl_tbody').children('tr.display_none').length;
		var index = 0;
		$("#tblBody tr").each(function() {
			var target = $(this);
			index++;
			target.find("td").eq(0).find("span").text(index);
			target.find("td").eq(0).find(".no").val(index);
			target.find("td").eq(0).attr("id", index);
		});
		if($("#tbl_tbody").children("tr").length == 0) {
			// [画面]登録ボタンを非活性化する。
			$("#btnRegister").attr('disabled', true);
			$("#btnRegister").removeClass("btn_button").addClass("btn_button_disabled");
		}
		if(rowSelected.find(".addFlg").val() == ADD_FLAG) {
			var checkCusCode = rowSelected.find("td").eq(1).text();
			var checkShpCode = rowSelected.find("td").eq(3).text() ;
			var checkBinKb = rowSelected.find("td").eq(5).text().replace("便","");
			if(checkCusCode == $("#txtCustomerCode").val() && checkShpCode == $("#txtShopCode").val()
					&& checkBinKb == $("#txtBinKb").val()) {
				$("#txtMess").html("");
				$(".tbl_data_condition_search input").removeClass("form-error-field");
				$(".tbl_data_condition_search select").removeClass("form-error-field");
				initSearchConditional();
			}
		}
	});
	
	/**
	 * check lost focus of コースコード.
	 */
	$(document).on("focusout", "#txtCourseCode", function() {
		setTimeout(function(){
			if(!isClickButton) {
				var strBeforeCourseCode = $("#txtCourseCode").val();
				var strAfterCourseCode = "";
				if (strBeforeCourseCode != "") {
					if (strBeforeCourseCode.length < LENGTH_COURSE_CODE) {
						strAfterCourseCode = fillZero(strBeforeCourseCode);
						$("#txtCourseCode").val(strAfterCourseCode);
					}
				}
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtCourseCode').val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "コースコード"));
						$("#txtCourseCode").addClass("form-error-field");
					} else {
						$("#txtCourseCode").removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * 得意先コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$(document).on("focus", "#txtCustomerCode", function(){
		if($(this).val() != '' && $(this).val() != null) {
			txtCustomerCodeValue = $(this).val();
		}
	});
	
	/**
	 * 得意先コードからフォーカスが外れた場合の処理。自項目と関連項目の初期化を行う
	 */
	$(document).on("focusout", "#txtCustomerCode", function(){
		var target = $("#txtCustomerCode");
		setTimeout(function(){
			if(!isClickButton) {
				if(target.val() != txtCustomerCodeValue) {
					$("#lblCustomerNameR").html("");
				}
				if(target.val() == "") {
					disableImgButton("btn_search_shop_id");
				} else {
					enableImgButton("btn_search_shop_id");
				}
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先コード"));
						target.addClass("form-error-field");
					}else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * 店舗コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$(document).on("focus", "#txtShopCode", function(){
		if($(this).val() != '' && $(this).val() != null) {
			txtShpCodeValue = $(this).val();
		}
	});
	
	/**
	 * 店舗コードからフォーカスが外れた場合の処理。自項目を初期化する
	 */
	$(document).on("focusout", "#txtShopCode", function() {
		var target = $("#txtShopCode");
		setTimeout(function(){
			if(!isClickButton) {
				if(target.val() != txtShpCodeValue) {
					$("#lblShopNameR").html("");
				}
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "店舗コード"));
						target.addClass("form-error-field");
					}else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of コース名称
	 */
	$(document).on("focusout", "#txtCourseName", function() {
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtCourseName').val(), true, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "コース名称"));
						$("#txtCourseName").addClass("form-error-field");
					}else {
						$("#txtCourseName").removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 配送時間 01
	 */
	$(document).on("focusout", "#txtHaisoTime01", function() {
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtHaisoTime01').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "配送時間"));
						$("#txtHaisoTime01").addClass("form-error-field");
					}else {
						$("#txtHaisoTime01").removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 配送時間 02
	 */
	$(document).on("focusout", "#txtHaisoTime02", function() {
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtHaisoTime02').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "配送時間"));
						$("#txtHaisoTime02").addClass("form-error-field");
					}else {
						$("#txtHaisoTime02").removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 出荷更新時間 01
	 */
	$(document).on("focusout", "#txtShipUpdateTime01", function() {
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtShipUpdateTime01').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "出荷更新時間"));
						$("#txtShipUpdateTime01").addClass("form-error-field");
					}else {
						$("#txtShipUpdateTime01").removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 出荷更新時間 02
	 */
	$(document).on("focusout", "#txtShipUpdateTime02", function() {
		var target = $(this);
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "出荷更新時間"));
						target.addClass("form-error-field");
					}else {
						$("#txtShipUpdateTime02").removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of コース略称
	 */
	$(document).on("focusout", "#txtCourseNameR", function() {
		var target = $(this);
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), true, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "コース略称"));
						target.addClass("form-error-field");
					}else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 配送区分
	 */
	$(document).on("focusout", "#txtHaisoKb", function() {
		var target = $(this);
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					if (target.val() != "" && (target.val() != JISHA_HAISO && target.val() != TAKUHAIBIN)){
						$("#txtMess").html($('#MST013-E01').val());
						target.addClass("form-error-field");
					} else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 状況コード 01
	 */
	$(document).on("focusout", "#txtStsCode01", function() {
		var target = $(this);
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "状況コード"));
						target.addClass("form-error-field");
					} else if(target.val() != "" && (target.val() != "1" && target.val() != "9")) {
						$("#txtMess").html($("#MST013-E02").val());
						target.addClass("form-error-field");
					} else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * check lost focus of 状況コード 02
	 */
	$(document).on("focusout", "#txtStsCode02", function() {
		var target = $(this);
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "状況コード"));
						target.addClass("form-error-field");
					} else if(target.val() != "" && (target.val() != TOROKU && target.val() != TORIKESHI)) {
						$("#txtMess").html($('#MST013-E02').val());
						target.addClass("form-error-field");
					} else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * 得意先ダイアログのイベント
	 */
	
	$("#btn_search_customer_id").bind("click", function() {
		if (isBtnImgEnabled("btn_search_customer_id")) {
			var strJigyo = $("#ddlJigyoCode").val();
			if(strJigyo != undefined){
				if (strJigyo == "" || strJigyo == null) {
					strJigyo = "";
				}
			} else {
				strJigyo = "";
			}
			var custCode = $("#txtCustomerCode").val();
			var shopKb = "";
			var searchKb = SEARCH_KB;
			// 検索子画面呼び出し用関数を呼び出す
			showCom0102d02(strJigyo, custCode, shopKb, searchKb);
		}
	});
	
	/**
	 * check lost focus of 便区分
	 */
	$(document).on("focusout", "#txtBinKb", function() {
		var target = $(this);
		setTimeout(function(){
			if(!isClickButton) {
				var iMode = $("#mode").val();
				if(iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem(target.val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "便区分"));
						target.addClass("form-error-field");
					} else {
						target.removeClass("form-error-field");
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * set value of ddlJigyoCode for input hidden
	 */
	$("#selectjigyoCodeHidden").val($("#ddlJigyoCode").val());
	
	/**
	 * クリアボタン(編集エリア)がクリックされた時の処理。入力エリアを初期化する
	 */
	$("#btnClearCustomer").bind("click", function(){
		$("#txtMess").html("");
		$(".tbl_data_condition_search input").removeClass("form-error-field");
		$(".tbl_data_condition_search select").removeClass("form-error-field");
		isClickButton = true;
		initSearchConditional();
		resizeTbl();
	});
	var valDdlJgyoCode = $("#ddlJigyoCode").val();
	/**
	 * クリアボタン(フッダー)がクリックされた時の処理。新規登録モードへ移行する
	 */
	$("#btnClearFooter").bind("click", function(){
		var jigyoCode = $("#ddlJigyoCode").val();
		var cosCode = $("#txtCourseCode").val();
		isClickButton = true;
		setTimeout(function(){
			disableButton("btnClearFooter");
			jQuestion_confirm($("#COM001-I01").val(), $("#COM001-I01").attr("title"), function(retVal){
				if(retVal) {
					enableButton("btnClearFooter");
					if(modeVal == SHINKI_MODE_CHAR) {
						initHeaderEnable(true);
						// 事業所
						$("#ddlJigyoCode").removeAttr("disabled");
						$("#ddlJigyoCode").val(valDdlJgyoCode);
						// コースコード
						$("#txtCourseCode").removeClass("txt_disable");
						$("#txtCourseCode").removeAttr("readonly");
						controlEditAreaFooter();
						// [画面]事業所へフォーカスをセットする
						$("#ddlJigyoCode").focus();
						$("#txtMess").html("");
						resizeTbl();
					} else if(modeVal == TEISEI_MODE_CHAR) {
						$.ajax({
							type : "POST",
							url : "getDataCourse",
							async : false,
							data : { "jigyoCode" : jigyoCode,
				        			 "cosCode" : cosCode
				        	  	   },
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",
							success : function(returnData) {
								if (returnData.txtMessage != "" && returnData.txtMessage != null) {
									$("#txtMess").html(returnData.txtMessage);
								} else {
									$("#ddlJigyoCode").val(returnData.ddlJigyoCode);
									$("#txtCourseCode").val(returnData.txtCourseCode);
									$("#txtCourseName").val(returnData.txtCourseName);
									$("#txtCourseNameR").val(returnData.txtCourseNameR);
									$("#txtHaisoTime01").val(returnData.txtHaisoTime01);
									$("#txtHaisoTime02").val(returnData.txtHaisoTime02);
									$("#txtShipUpdateTime01").val(returnData.txtShipUpdateTime01);
									$("#txtShipUpdateTime02").val(returnData.txtShipUpdateTime02);
									$("#txtHaisoKb").val(returnData.txtHaisoKb);
									$("#txtStsCode01").val(returnData.txtStsCode01);
									initHeaderEnable(false);
									clearInputEditArea(false);
									// 事業所
									$("#ddlJigyoCode").attr("disabled", true);
									// コースコード
									$("#txtCourseCode").addClass("txt_disable");
									$("#txtCourseCode").attr("readonly", true);
									$("#txtMess").html("");
									resizeTbl();
								}
							},
							complete : function(jqXHR, textStatus) {
								// [画面]コース名称へフォーカスをセットする
								$("#txtCourseName").focus();
							}
						});
					}
					showButtonStatus();
					$('input').removeClass('form-error-field');
				    $('select').removeClass('form-error-field');
				} else {
					enableButton("btnClearFooter");
				}
			});
			isClickButton = false;
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * 登録ボタンをクリックされた時の処理。入力されたコース情報をDBへ登録及び更新を行う
	 */
	$("#btnRegister").bind("click", function() {
		isClickButton = true;
		var mode = $("#mode").val();
		setTimeout(function(){
			disableButton("btnRegister");
			var jigyoCode = $("#ddlJigyoCode").val();
			var courseCode = $("#txtCourseCode").val();
			var courseName = $("#txtCourseName").val();
			var courseNameR = $("#txtCourseNameR").val();
			var haisoTime01 = $("#txtHaisoTime01").val();
			var haisoTime02 = $("#txtHaisoTime02").val();
			var haisoTime = haisoTime01 + haisoTime02;
			var shipUpdTime01 = $("#txtShipUpdateTime01").val();
			var shipUpdTime02 = $("#txtShipUpdateTime02").val();
			var shipUpdTime = shipUpdTime01 + shipUpdTime02;
			var haisoKb = $("#txtHaisoKb").val();
			var stsCode01 = $("#txtStsCode01").val();
			var haitaDate = $("#haitaDate").val();
			var haitaTime = $("#haitaTime").val();
			var dataListCourse = getDataList();
			var mode = $("#mode").val();
			var strMessError = "";
			var tempObj = {
					jigyoCode: jigyoCode,
					courseCode: courseCode,
					courseName: courseName,
					courseNameR: courseNameR,
					haisoTime01: haisoTime01,
					haisoTime02: haisoTime02,
					haisoTime: haisoTime,
					shipUpdTime01: shipUpdTime01,
					shipUpdTime02: shipUpdTime02,
					shipUpdTime: shipUpdTime,
					haisoKb: haisoKb,
					stsCode01: stsCode01,
					dataList : dataListCourse,
					haitaDate: haitaDate,
					haitaTime: haitaTime,
					mode: mode
				};
			jQuestion_confirm($("#COM001-I02").val(), $("#COM001-I02").attr("title"), function(retVal){
				if(retVal) {
					enableButton("btnRegister");
					if(mode != TORIKESI_MODE_CHAR) {
						if(isDataChangedCourse()) {
							$.ajax({
								type: "POST",
								url: "register",
								contentType: 'application/json; charset=utf-8',
								dataType: "json",
								data : JSON.stringify(tempObj),
								async: false,
								success: function(data) {
									if(data.errorMess != "" && data.errorMess != null) {
										for(var i = 0; i < data.errorMess.length; i++) {
											strMessError += data.errorMess[i].messageContent + "<br>";
										}
										$("#txtMess").html(strMessError);
										resizeTbl();
										removeInfoClass();
										var strIdErrorRegister = data.strId;
										if (strIdErrorRegister != null && strIdErrorRegister != "") {
											var lstError = strIdErrorRegister.split(DELIMITER);
											addClassErrorToControl(lstError);
											setFocus();
										}
									}
									if(data.errorMessage != "" && data.errorMessage != null) {
										if(data.type == TYPE_SUCESS) {
											$("#txtMess").html(data.errorMessage.messageContent);
											$("#haitaDate").val(data.haitaDate);
											$("#haitaTime").val(data.haitaTime);
											addInfoClass();
											$("input").removeClass("form-error-field");
											$("select").removeClass("form-error-field");
											originalDataCourse = saveDataCourse();
											originalDataCourseList = saveDataCourseList();
										} else {
											$("#txtMess").html(data.errorMessage.messageContent);
											removeInfoClass();
											resizeTbl();
											if(data.errorControl != null) {
												errorControl();
											}
										}
									}
								},
							});
						} else {
							if(isDataChangedCourseList()) {
								$.ajax({
									type: "POST",
									url: "register",
									contentType: 'application/json; charset=utf-8',
									dataType: "json",
									data : JSON.stringify(tempObj),
									async: false,
									success: function(data) {
										if(data.errorMess != "" && data.errorMess != null) {
											for(var i = 0; i < data.errorMess.length; i++) {
												strMessError += data.errorMess[i].messageContent + "<br>";
											}
											$("#txtMess").html(strMessError);
											removeInfoClass();
											resizeTbl();
											var strIdErrorRegister = data.strId;
											if (strIdErrorRegister != null && strIdErrorRegister != "") {
												var lstError = strIdErrorRegister.split(DELIMITER);
												addClassErrorToControl(lstError);
												setFocus();
											}
										}
										if(data.errorMessage != "" && data.errorMessage != null) {
											if(data.type == TYPE_SUCESS) {
												$("#txtMess").html(data.errorMessage.messageContent);
												$("#haitaDate").val(data.haitaDate);
												$("#haitaTime").val(data.haitaTime);
												addInfoClass();
												$("input").removeClass("form-error-field");
												$("select").removeClass("form-error-field");
												originalDataCourse = saveDataCourse();
												originalDataCourseList = saveDataCourseList();
											} else {
												$("#txtMess").html(data.errorMessage.messageContent);
												removeInfoClass();
												resizeTbl();
												if(data.errorControl != null) {
													errorControl();
												}
											}
										}
									},
								});
							} else {
								$("#txtMess").html($("#COM034-E").val());
								removeInfoClass();
							}
						}
					} else {
						$.ajax({
							type: "POST",
							url: "register",
							contentType: 'application/json; charset=utf-8',
							dataType: "json",
							data : JSON.stringify(tempObj),
							async: false,
							success: function(data) {
								if(data.errorMess != "" && data.errorMess != null) {
									for(var i = 0; i < data.errorMess.length; i++) {
										strMessError += data.errorMess[i].messageContent + "<br>";
									}
									$("#txtMess").html(strMessError);
									removeInfoClass();
									resizeTbl();
									var strIdErrorRegister = data.strId;
									if (strIdErrorRegister != null && strIdErrorRegister != "") {
										var lstError = strIdErrorRegister.split(DELIMITER);
										addClassErrorToControl(lstError);
										setFocus();
									}
								}
								if(data.errorMessage != "" && data.errorMessage != null) {
									if(data.type == TYPE_SUCESS) {
										$("#txtMess").html(data.errorMessage.messageContent);
										$("#haitaDate").val(data.haitaDate);
										$("#haitaTime").val(data.haitaTime);
										addInfoClass();
										$("input").removeClass("form-error-field");
										$("select").removeClass("form-error-field");
										originalDataCourse = saveDataCourse();
										originalDataCourseList = saveDataCourseList();
									} else {
										$("#txtMess").html(data.errorMessage.messageContent);
										removeInfoClass();
										resizeTbl();
									}
								}
							},
						});
					}
				} else {
					enableButton("btnRegister");
				}	
			});
			isClickButton = false;
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 * set data into array
	 */
	function getDataList() {
		var dataList = [];
		$('#tblBody tbody tr').each(function() {
			var row = $(this);
			var renBan = row.find("td").eq(0).text();
			var custCode = row.find("td").eq(1).text();
			var custNmR = row.find("td").eq(2).text();
			var shopCode = row.find("td").eq(3).text();
			var shopNmR = row.find("td").eq(4).text();
			var binKb = row.find("td").eq(5).text();
			var deliKb = row.find("td").eq(6).find(".deliKbCode").val();
			var stsCode = row.find("td").eq(7).text()
			var addFlg = row.find("td").eq(8).find("input").val();
			var updateFlg = row.find("td").eq(9).find("input").val();
			var tempObjData = {
				renBan: renBan,
				custCode: custCode,
				custNmR : custNmR,
				shopCode : shopCode,
				shopNmR : shopNmR,
				binKb : binKb,
				deliKb : deliKb,
				stsCode : stsCode,
				addFlg : addFlg,
				updateFlg : updateFlg,
			};
			dataList.push(tempObjData);
		});
		return dataList;
	}
	
	/**
	 * 戻るボタンをクリックされた時の処理。コースマスタ一覧画面に戻る
	 */
	$("#btnBack").bind("click", function() {
		isClickButton = true;
		$("#btnBackHidden").trigger("click");
	});
	
	/**
	 * control textBox, comboBox, button when end processing 
	 */
	function checkExist() {
		// 事業所
		$("#ddlJigyoCode").attr("disabled", true);
		// コースコード
		$("#txtCourseCode").attr("readonly", true)
		$("#txtCourseCode").addClass("txt_disable");
		// コース名称
		$("#txtCourseName").attr("readonly", true);
		$("#txtCourseName").addClass("txt_disable");
		// コース略称
		$("#txtCourseNameR").attr("readonly", true);
		$("#txtCourseNameR").addClass("txt_disable");
		// 配送時間
		$("#txtHaisoTime01").attr("readonly", true);
		$("#txtHaisoTime01").addClass("txt_disable");
		$("#txtHaisoTime02").attr("readonly", true);
		$("#txtHaisoTime02").addClass("txt_disable");
		// 出荷更新時間
		$("#txtShipUpdateTime01").attr("readonly", true);
		$("#txtShipUpdateTime01").addClass("txt_disable");
		$("#txtShipUpdateTime02").attr("readonly", true);
		$("#txtShipUpdateTime02").addClass("txt_disable");
		// 配送区分
		$("#txtHaisoKb").attr("readonly", true);
		$("#txtHaisoKb").addClass("txt_disable");
		// 状況コード
		$("#txtStsCode01").attr("readonly", true);
		$("#txtStsCode01").addClass("txt_disable");
		// 設定ボタン
		$("#btnConfig").attr("disabled", true);
		$("#btnConfig").removeClass("btn_button").addClass("btn_button_disabled");
		// 編集エリアとリスト部を活性化する
		clearInputEditArea(false);
		// ボタン制御を行う
		controlButtonEditArea();
		
		// 納入先一覧
		$("#tbl_tbody").empty();
		// 登録ボタン
		$("#btnRegister").text(TOROKU_TEXT);
		$("#btnRegister").attr("disabled", true);
		$("#btnRegister").removeClass("btn_button").addClass("btn_button_disabled");
		// クリアボタン（フッダー）
		$("#btnClearFooter").removeAttr("disabled");
		$("#btnClearFooter").removeClass("btn_button_disabled").addClass("btn_button");
		// [画面]得意先コードにフォーカスをセットする
		$("#txtCustomerCode").focus();
	}
	
	/**
	 * ボタン制御
	 */
	function showButtonStatus() {
		var mode = $("#mode").val();
		if(mode == SHINKI_MODE_CHAR) {
			// 設定
			$("#btnConfig").removeAttr("disabled");
			$("#btnConfig").removeClass("btn_button_disabled").addClass("btn_button");
			disableImgButton("btn_search_shop_id");
			disableImgButton("btn_search_customer_id");
			// 追加／更新ボタン
			$("#btnAddUpdate").attr("disabled", true);
			$("#btnAddUpdate").removeClass("btn_button").addClass("btn_button_disabled");
			// クリアボタン（編集エリア）
			$("#btnClearCustomer").attr("disabled", true);
			$("#btnClearCustomer").removeClass("btn_button").addClass("btn_button_disabled");
			// 登録ボタン
			$("#btnRegister").attr("disabled", true);
			$("#btnRegister").removeClass("btn_button").addClass("btn_button_disabled");
			// クリア
			$("#btnClearFooter").removeAttr("disabled");
			$("#btnClearFooter").removeClass("btn_button_disabled").addClass("btn_button");
		} else if(mode == SHOUKAI_MODE_CHAR) {
			disableImgButton("btn_search_shop_id");
			disableImgButton("btn_search_customer_id");
			// 設定ボタン
			$("#btnConfig").attr("disabled", true);
			$("#btnConfig").removeClass("btn_button").addClass("btn_button_disabled");
			// 追加／更新ボタン
			$("#btnAddUpdate").attr("disabled", true);
			$("#btnAddUpdate").removeClass("btn_button").addClass("btn_button_disabled");
			// クリアボタン（編集エリア）
			$("#btnClearCustomer").attr("disabled", true);
			$("#btnClearCustomer").removeClass("btn_button").addClass("btn_button_disabled");
			// 登録ボタン
			$("#btnRegister").attr("disabled", true);
			$("#btnRegister").removeClass("btn_button").addClass("btn_button_disabled");
			// クリアボタン（フッダー）
			$("#btnClearFooter").attr("disabled", true);
			$("#btnClearFooter").removeClass("btn_button").addClass("btn_button_disabled");
		} else if(mode == TEISEI_MODE_CHAR) {
			disableImgButton("btn_search_shop_id");
			// 設定ボタン
			$("#btnConfig").attr("disabled", true);
			$("#btnConfig").removeClass("btn_button").addClass("btn_button_disabled");
			// 得意先検索ボタン
			enableImgButton("btn_search_customer_id");
			// 追加／更新
			$("#btnAddUpdate").removeAttr("disabled");
			$("#btnAddUpdate").removeClass("btn_button_disabled").addClass("btn_button");
			// クリアボタン（編集エリア）
			$("#btnClearCustomer").removeAttr("disabled");
			$("#btnClearCustomer").removeClass("btn_button_disabled").addClass("btn_button");
			// 登録ボタン
			$("#btnRegister").removeAttr("disabled");
			$("#btnRegister").removeClass("btn_button_disabled").addClass("btn_button");
			$("#btnRegister").text(TOROKU_TEXT);
			// クリア
			$("#btnClearFooter").removeAttr("disabled");
			$("#btnClearFooter").removeClass("btn_button_disabled").addClass("btn_button");
			// 追加／更新ボタン
			$("#btnAddUpdate").text(TSUIKA_TEXT);
		} else if(mode == TORIKESI_MODE_CHAR) {
			disableImgButton("btn_search_shop_id");
			disableImgButton("btn_search_customer_id");
			// 設定ボタン
			$("#btnConfig").attr("disabled", true);
			$("#btnConfig").removeClass("btn_button").addClass("btn_button_disabled");
			// 追加／更新ボタン
			$("#btnAddUpdate").attr("disabled", true);
			$("#btnAddUpdate").removeClass("btn_button").addClass("btn_button_disabled");
			// クリアボタン（編集エリア）
			$("#btnClearCustomer").attr("disabled", true);
			$("#btnClearCustomer").removeClass("btn_button").addClass("btn_button_disabled");
			// 登録ボタン
			$("#btnRegister").removeAttr("disabled");
			$("#btnRegister").removeClass("btn_button_disabled").addClass("btn_button");
			$("#btnRegister").text(TORIKESHI_TEXT);
			// クリアボタン（フッダー）
			$("#btnClearFooter").attr("disabled", true);
			$("#btnClearFooter").removeClass("btn_button").addClass("btn_button_disabled");
		}
	}
	
	/**
	 * フォーカスセット
	 */
	function setFocus() {
		var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
		if (typeof idError === TYPE_UNDEFINED) {
			var mode = $("#mode").val();
			if (mode == SHINKI_MODE_CHAR) {
				$("#ddlJigyoCode").focus();
			} else if(mode == SHOUKAI_MODE_CHAR){
				$("#btnBack").focus();
			} else if(mode == TEISEI_MODE_CHAR) {
				$("#txtCourseName").focus();
			} else if(mode == TORIKESI_MODE_CHAR) {
				$("#btnRegister").focus();
			}
		} else {
			$("#"+idError).select();
			$("#"+idError).focus();
		}
	}
	
	/**
	 * 入力値を左ゼロ埋め５桁にする
	 */
	function fillZero(strInput) {
		var strResult = strInput;
		for (var i = strResult.length; i < LENGTH_COURSE_CODE; i++) {
			strResult = FILL_ZERO + strResult;
		}
		return strResult;
	}
	
	/**
	 * 編集エリア初期化
	 */
	function initSearchConditional() {
		clearInputEditArea(true);
		controlButtonEditArea();
		
		// フォーカスセット
		$("#txtCustomerCode").focus();
		$("#editMode").val(ADD_MODE);
	}
	
	/**
	 * 編集エリアの入力項目制御
	 */
	function controlEditArea() {
		// 得意先コード
		$("#txtCustomerCode").attr("readonly", true);
		$("#txtCustomerCode").addClass("txt_disable");
		
		disableImgButton("btn_search_customer_id");
		disableImgButton("btn_search_shop_id");
		
		// 店舗コード
		$("#txtShopCode").attr("readonly", true);
		$("#txtShopCode").addClass("txt_disable");
		
		// 便区分
		$("#txtBinKb").attr("readonly", true);
		$("#txtBinKb").addClass("txt_disable");
		
		// 納品区分
		$("#ddlDeliKb").removeAttr("disabled");
		
		// 状況コード
		$("#txtStsCode02").removeAttr("readonly");
		$("#txtStsCode02").removeClass("txt_disable");
	}
	
	/**
	 * 画面表示モードにより画面項目を制御する
	 */
	function controlEditAreaFooter() {
		$(".tbl_data_condition_search input").val("");
		$(".tbl_data_condition_search select").val("");
		$(".tbl_data_condition_search div span").html("");
		// 得意先コード
		$("#txtCustomerCode").attr("readonly", true);
		$("#txtCustomerCode").addClass("txt_disable");
		
		// 店舗コード
		$("#txtShopCode").attr("readonly", true);
		$("#txtShopCode").addClass("txt_disable");
		
		// 便区分
		$("#txtBinKb").attr("readonly", true);
		$("#txtBinKb").addClass("txt_disable");
		
		// 納品区分
		$("#ddlDeliKb").attr("disabled", true);
		
		// 状況コード
		$("#txtStsCode02").attr("readonly", true);
		$("#txtStsCode02").addClass("txt_disable");
		// 納入先一覧
		$("#tbl_tbody").empty();
	}
	
	/**
	 * 編集エリア初期化
	 */
	function clearInputEditArea(flg) {
		// 得意先コード
		$("#txtCustomerCode").val("");
		$("#txtCustomerCode").removeAttr("readonly");
		$("#txtCustomerCode").removeClass("txt_disable");
		
		// 得意先名称
		$("#lblCustomerNameR").html("");
		
		// 店舗コード
		$("#txtShopCode").removeAttr("readonly");
		$("#txtShopCode").removeClass("txt_disable");
		$("#txtShopCode").val("");
		
		// 店舗名称
		$("#lblShopNameR").html("");
		
		// 便区分
		$("#txtBinKb").removeAttr("readonly");
		$("#txtBinKb").removeClass("txt_disable");
		$("#txtBinKb").val("");
		
		// 納品区分
		controlDeliKb(flg);
		
		// 状況コード
		$("#txtStsCode02").removeAttr("readonly");
		$("#txtStsCode02").removeClass("txt_disable");
		$("#txtStsCode02").val("");
	}
	
	/**
	 * control button in edit area
	 */
	function controlButtonEditArea() {
		// 得意先検索ボタン
		enableImgButton("btn_search_customer_id");
		// 店舗検索ボタン
		disableImgButton("btn_search_shop_id");
		// 追加／更新ボタン
		$("#btnAddUpdate").text("追加");
		$("#btnAddUpdate").removeAttr("disabled");
		$("#btnAddUpdate").removeClass("btn_button_disabled").addClass("btn_button");
		
		// クリアボタン（編集エリア）
		$("#btnClearCustomer").removeAttr("disabled");
		$("#btnClearCustomer").removeClass("btn_button_disabled").addClass("btn_button");
	}
	
	/**
	 * control select box 納品区分
	 */
	function controlDeliKb(flg) {
		// 納品区分
		if(flg) {
			$("#ddlDeliKb").removeAttr("disabled");
			$("#ddlDeliKb").val("");
		} else {
			$("#ddlDeliKb").removeAttr("disabled");
		}
	}
	
	/**
	 * enable textBox, comboBox, button in edit area
	 */
	function enableEditArea() {
		// 得意先コード
		$("#txtCustomerCode").removeAttr("readonly");
		$("#txtCustomerCode").removeClass("txt_disable");
		
		enableImgButton("btn_search_customer_id");
		enableImgButton("btn_search_shop_id");
		
		// 店舗コード
		$("#txtShopCode").removeAttr("readonly");
		$("#txtShopCode").removeClass("txt_disable");
		
		// 便区分
		$("#txtBinKb").removeAttr("readonly");
		$("#txtBinKb").removeClass("txt_disable");
		
		// 納品区分
		$("#ddlDeliKb").removeAttr("disabled");
		
		// 状況コード
		$("#txtStsCode02").removeAttr("readonly");
		$("#txtStsCode02").removeClass("txt_disable");
		
		// 追加／更新ボタン
		$("#btnAddUpdate").removeAttr('disabled');
		$("#btnAddUpdate").removeClass("btn_button_disabled").addClass("btn_button");
	}
	
	/**
	 * enable textBox in header area
	 */
	function initHeaderEnable(flg) {
		if(flg) {
			// コースコード
			$("#txtCourseCode").val("");
			// コース名称
			$("#txtCourseName").val("");
			// コース略称
			$("#txtCourseNameR").val("");
			// 配送時間
			$("#txtHaisoTime01").val("");
			$("#txtHaisoTime02").val("");
			// 出荷更新時間
			$("#txtShipUpdateTime01").val("");
			$("#txtShipUpdateTime02").val("");
			// 配送区分
			$("#txtHaisoKb").val("");
			// 状況コード
			$("#txtStsCode01").val("");
		}
		// コース名称
		$("#txtCourseName").removeClass("txt_disable");
		$("#txtCourseName").removeAttr("readonly");
		// コース略称
		$("#txtCourseNameR").removeClass("txt_disable");
		$("#txtCourseNameR").removeAttr("readonly");
		// 配送時間
		$("#txtHaisoTime01").removeClass("txt_disable");
		$("#txtHaisoTime01").removeAttr("readonly");
		$("#txtHaisoTime02").removeClass("txt_disable");
		$("#txtHaisoTime02").removeAttr("readonly");
		// 出荷更新時間
		$("#txtShipUpdateTime01").removeClass("txt_disable");
		$("#txtShipUpdateTime01").removeAttr("readonly");
		$("#txtShipUpdateTime02").removeClass("txt_disable");
		$("#txtShipUpdateTime02").removeAttr("readonly");
		// 配送区分
		$("#txtHaisoKb").removeClass("txt_disable");
		$("#txtHaisoKb").removeAttr("readonly");
		// 状況コード
		$("#txtStsCode01").removeClass("txt_disable");
		$("#txtStsCode01").removeAttr("readonly");
	}
	
	/**
	 * 店舗コード検索画面表示
	 */
	$("#btn_search_shop_id").on("click", function() {
		if(isBtnImgEnabled("btn_search_shop_id")) {
			// 変数値の情報を得ます
			var custCode = $("#txtCustomerCode").val();
			custCode = replaceText(custCode);
			var shopCode = $("#txtShopCode").val();
			shopCode = replaceText(shopCode);
			showCom0102d03(custCode,shopCode);
		}
	});
	
	/**
	 *　check data コースマスタ is change
	 *
	 * @return boolean true :　違う点がある false:違う点がなし
	 */
	function isDataChangedCourse() {
		var currentData = saveDataCourse();
		// オリジナルデータと現在のデータを比べる
		for (var i = 0; i < currentData.length; i++) {
			if (originalDataCourse[i] != currentData[i]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *　check data コース納入先明細マスタ is change
	 *
	 * @return boolean true :　違う点がある false:違う点がなし
	 */
	function isDataChangedCourseList() {
		var currentData = saveDataCourseList();
		// オリジナルデータと現在のデータを比べる
		for (var i = 0; i < currentData.length; i++) {
			if (originalDataCourseList[i] != currentData[i]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 更新処理前/後に保存する
	 */
	function saveDataCourse() {
		var resultArray = [];
		if($("#ddlJigyoCode").val() != null) {
			resultArray.push($("#ddlJigyoCode").val());
		}
		resultArray.push($("#txtCourseCode").val());
		resultArray.push($("#txtCourseName").val());
		resultArray.push($("#txtHaisoTime01").val() + $("#txtHaisoTime02").val());
		resultArray.push($("#txtShipUpdateTime01").val() + $("#txtShipUpdateTime02").val());
		resultArray.push($("#txtCourseNameR").val());
		resultArray.push($("#txtHaisoKb").val());
		resultArray.push($("#txtStsCode01").val());
		
		return resultArray;
	}
	
	/**
	 * 更新処理前/後に保存する
	 */
	function saveDataCourseList() {
		var resultArray = [];
		var tableRowString = "";
		var tableRowArray = $("#tblBody").find("tr");
		for(var i = 0; i < tableRowArray.length; i++) {
			tableRowString += $(tableRowArray[i]).find("td").eq(1).text();
			tableRowString += $(tableRowArray[i]).find("td").eq(2).text();
			tableRowString += $(tableRowArray[i]).find("td").eq(3).text();
			tableRowString += $(tableRowArray[i]).find("td").eq(4).text();
			tableRowString += $(tableRowArray[i]).find("td").eq(5).text();
			tableRowString += $(tableRowArray[i]).find("td").eq(6).find("span").text();
			tableRowString += $(tableRowArray[i]).find("td").eq(7).text();
		}
		resultArray.push(tableRowString.trim());
		return resultArray;
	}
});

/**
 * 以下のdialogの処理
 */
/**
 * 戻り値チェック
 * @param data: [変数]店舗情報格納クラス
 */
function getCom0102d03(data) {
	var showDataCnt = data.length;
	for (var i = 0; i < showDataCnt; i++) {
		$("#txtShopCode").val(data[i].shopCode);
		$("#lblShopNameR").text(data[i].shopNmR);
	}
	$("#txtBinKb").focus().select();
	$("#txtMess").html("");
	$("#txtShopCode").removeClass("form-error-field");
	reTurnCheckTab();
}

/**
 * 得意先子画面データ受け取り用関数（仮）
 */
function getCom0102d02(data) {
	if (data[0].custCode != null) {
		$("#txtCustomerCode").val(data[0].custCode);
		$("#lblCustomerNameR").text(data[0].custNmR);
		$("#txtShopCode").focus().select();
	}
	if($("#txtCustomerCode").val() != "") {
		// 得意先コード
		$("#txtCustomerCode").attr("readonly", true);
		$("#txtCustomerCode").addClass("txt_disable");
		disableImgButton("btn_search_customer_id");
		enableImgButton("btn_search_shop_id");
		$("#txtMess").html("");
	}
	reTurnCheckTab();
}
