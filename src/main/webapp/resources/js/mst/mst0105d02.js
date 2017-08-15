/**
 * パッケージ名: ファイル名: mst0105d02.js
 * 
 * 作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

var DELIMITER = "##";
var preRow = null;
var rowSelected = null;
var currentShowingColId = "";
var isTableMenuShowing = false;

var COL_NO = 0;
/** O/Lセンター*/
var COL_OL_CENTER_CODE = 1;
/** O/L取引先*/
var COL_OL_TORIHIKI_CODE = 2;
/** 相手取引先*/
var COL_AT_TORIHIKI_CODE = 3;
/** 相手店コード*/
var COL_AT_TEN_CODE = 4;
/** 自社得意先コード*/
var COL_CUST_CODE = 5;
/** 自社店舗コード*/
var COL_SHOP_CODE = 6;
/** 納品区分*/
var COL_TARGET1 = 7;
/** 得意先名称*/
var COL_CUST_NM_R = 8;
/** 自社店舗名称*/
var COL_SHOP_NM_R = 9;
/** 納品区分*/
var COL_DELI_KB = 10;
/** 状況コード*/
var COL_STS_CODE = 11;
var COL_UPDATE_FLG = 12;
var COL_ADD_FLG = 13;
var COL_DELETEF_LG = 14;

var srcRowList = [];

jQuery(document).ready(function($) {
	//Dialogのadd_clickイベントの作成
	loadDialogCom0102d03();
	
	loadDialogCom0102d02(); 
	
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".content");
		}
	});
	
	var initialOnlineCenterCode = "";
	var initialAttorihikiCode = "";
	var initialAttorihikiCode02 = "";
	var initialAtTenCode = "";
	var initialCustomerCode = "";
	var initialShopCode = "";
	if($("#isErrorControl").val() == "true") {
		if($("#errMessage").val() != "") {
			displayMessages("", null);
			// 共通仕様 3-6適用
			$("#tbl_tbody").html($("#errMessage").val());
			
		}
		// // 共通仕様 4-4適用
		errorControl();
		disableImgButton("btnSearchCusts");
		disableImgButton("btnSearchStore");
	} else {
		configureScreen();
	}
	
	/**
	 * （登録画面）OLセンターコードコード_GetFocus
	 */
	$("#txtOnlineCenterCode").bind("focus", function() {
		initialOnlineCenterCode = $(this).val().trim();
	});
	
	/**
	 * （登録画面）OLセンターコード_LostFocus
	 */
	$("#txtOnlineCenterCode").bind("focusout", function() {
		if($(this).val() != initialOnlineCenterCode) {
			$("#lblCustomerNameR01").text("");
		}
	});
	
	/**
	 * （登録画面）相手取引先コード_GetFocus
	 */
	$("#txtAtTorihikiCode").bind("focus", function() {
		initialAttorihikiCode = $(this).val().trim();
	});
	
	/**
	 * （登録画面）相手取引先コード_LostFocus
	 */
	$("#txtAtTorihikiCode").bind("focusout", function() {
		if($(this).val() != initialAttorihikiCode) {
			$("#lblCustomerNameR02").text("");
		}
	});
	
	/**
	 * （登録画面）相手取引先コード_GetFocus
	 */
	$("#txtAtTorihikiCode02").bind("focus", function() {
		initialAttorihikiCode02 = $(this).val().trim();
	});
	
	/**
	 * （登録画面）相手取引先コード_LostFocus
	 */
	$("#txtAtTorihikiCode02").bind("focusout", function() {
		if($(this).val() == initialAttorihikiCode02) {
			return;
		}
		
		if($(this).val() == "" || $(this).val() != initialAttorihikiCode02) {
			$('#txtAtTenCode').val('');
			$('#txtCustomerCode').val('');
			$("#lblCustomerNameR").text('');
			$('#txtShopCode').val('');
			$("#lblShopNameR").text('');
			$('#ddlDeliKb').val(2);
			disableImgButton("btnSearchStore")
			$('#txtStsCode').val(TOROKU);
		}
	});
	
	/**
	 * （登録画面_編集）相手店コード_GetFocus
	 */
	$("#txtAtTenCode").bind("focus", function() {
		initialAtTenCode = $(this).val().trim();
	});
	
	/**
	 * （登録画面）相手店コード_LostFocus
	 */
	$("#txtAtTenCode").bind("focusout", function() {
		if($(this).val() == initialAtTenCode) {
			return;
		}
		
		if($(this).val() == "") {
			return;
		}
		
		$("#txtShopCode").val($(this).val().trim());
		$("#lblShopNameR").text('');
	});
	
	/**
	 * （登録画面_編集）相手店コード_GetFocus
	 */
	$("#txtCustomerCode").bind("focus", function() {
		initialCustomerCode = $(this).val().trim();
	});
	
	/**
	 * （登録画面）相手店コード_LostFocus
	 */
	$("#txtCustomerCode").bind("focusout", function() {
		// 自項目初期化
		if($(this).val() != initialCustomerCode) {
			$('#lblCustomerNameR').text("");
		}
		
		// 関連項目初期化
		if($(this).val() == "") {
			disableImgButton("btnSearchStore");
		} else {
			enableImgButton("btnSearchStore");
		};
	});
	
	
	/**
	 * （登録画面_編集）相手店コード_GetFocus
	 */
	$("#txtCustomerCode").bind("focus", function() {
		initialCustomerCode = $(this).val().trim();
	});
	
	/**
	 * （登録画面）相手店コード_LostFocus
	 */
	$("#txtCustomerCode").bind("focusout", function() {
		// 自項目初期化
		if($(this).val() != initialCustomerCode) {
			$('#lblCustomerNameR').text("");
		}
		
		// 関連項目初期化
		if($(this).val() == "") {
			disableImgButton("btnSearchStore");
		} else {
			enableImgButton("btnSearchStore");
		};
	});
	
	/**
	 * （登録画面_編集）相手店コード_GetFocus
	 */
	$("#txtShopCode").bind("focus", function() {
		initialShopCode = $(this).val().trim();
	});
	
	/**
	 * （登録画面）相手店コード_LostFocus
	 */
	$("#txtShopCode").bind("focusout", function() {
		// 自項目初期化
		if($(this).val() != initialShopCode) {
			$('#lblShopNameR').text("");
		}
	});
	
	$("#ddlDeliKb" ).change(function() {
		$(this).removeClass("form-error-field");
	});
	
	/**
	 * （登録画面）設定_Click
	 */
	$("#btnCreate").bind("click",function(){
		create();
	});
	
	/**
	 * （（登録画面）追加・更新_Click
	 */
	$("#btnAddUpdate").bind("click",function(){
		addUpdate();
	});
	
	/**
	 * （登録画面）戻るボタン_Click
	 */
	$("#btnBack").bind("click", function() {
		$("#eventBack").trigger( "click" );
	});
	
	$("input[type='text']" ).change(function() {
		$(this).removeClass("form-error-field");
	});
	
	/**
	 * （登録画面）登録ボタン_Click
	 */
	$("#btnRegistration").bind("click", function() {
		disableButton("btnRegistration")
		jQuestion_confirm($("#COM001-I2").val(), $("#COM001-I2").attr("title"), function(retVal) {
			if (retVal) {
				register();
			} else {
				enableButton("btnRegistration");
			}
		});
	});
	
	/**
	 * （登録画面_編集）クリア_Click
	 */
	$("#btnClear").bind("click", function(){
		resetEditArea();
	});
	/**
	 * （登録画面）得意先検索_Click
	 */
	$("#btnSearchCusts").bind("click",function(e){
		if (isBtnImgEnabled("btnSearchCusts")) {
			var plantCodeWk =  "";
			// 得意先検索画面を表示する
			var sysAdminFlag = $("#sysAdminFlag").val();
			// 事業所コード編集
			if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				plantCodeWk = $('#plantCode').val();
			} else if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				plantCodeWk = "";
			}
			var custCode = $("#txtCustomerCode").val();
			var shopCode = "";
			var searchKb = "1";
			// 検索子画面呼び出し用関数を呼び出す
			showCom0102d02(plantCodeWk, custCode, shopCode, searchKb);
		}
	});
	
	/**
	 * （登録画面）店舗検索_Click
	 */
	$("#btnSearchStore").bind("click",function(){
		if (isBtnImgEnabled("btnSearchStore")) {
			// 変数値の情報を得ます
			var custCode = $("#txtCustomerCode").val();
			custCode = replaceText(custCode);
			var shopCode = $("#txtShopCode").val();
			shopCode = replaceText(shopCode);
			
			// 店舗検索画面を表示する。
			showCom0102d03(custCode,shopCode);
		}
	});
	
	//▼処理	
	$(document).on("mouseover", ".contextMenu", function(){
		var screenMode =$("#screenMode").val();
		if (screenMode == SHINKI_MODE_CHAR || screenMode == TEISEI_MODE_CHAR) {
			// change text to "▼" when user is hovering on a No column
			$(this).find("span").text("▼");
			// if menu is showing, hide menu when user is hovering on other No columns
			if (isTableMenuShowing) {
				// only hide menu if user is hovering on other No columns, not the column that is showing menu
				if ($(this).attr("id") != currentShowingColId) {
					// hide menu
					$("#tableMenu").fadeOut("fast");
					// switch flag value
					isTableMenuShowing = false;
					// change the text back from "▼" to number
					$("#" + currentShowingColId).find("span").text(currentShowingColId);
				}
			}
			// is menu is not showing, change the text back from "▼" to number
			if ($(this).attr("id") != currentShowingColId) {
				$("#" + currentShowingColId).find("span").text(currentShowingColId);
				currentShowingColId = $(this).attr("id");
			}
		}
	});
	
	// (登録画面)クリアボタン_フッダー
	$("#btnFooterClear").bind("click", function() {
		disableButton("btnFooterClear");
		jQuestion_confirm($("#COM001-I1").val(), $("#COM001-I1").attr("title"), function(retVal) {
			if (retVal) {
				// 画面表示モードにより画面初期化内容を制御する。
				footerClear();
			} else {
				enableButton("btnFooterClear");
			}
		});
	});
	
	$(document).on("click", ".contextMenu", function(e){
		var screenMode =$("#screenMode").val();
		if (screenMode == SHINKI_MODE_CHAR || screenMode == TEISEI_MODE_CHAR) {
			// get current coordinates of mouse
			var x = e.pageX;
			var y = e.pageY;
			// show menu at current mouse's coordinates
			$("#tableMenu").css("position", "absolute").css("left", x + "px").css("top", y + "px").fadeIn("fast");
			rowSelected =  $(this).parent();
			var addFlg = rowSelected.find("td").eq(COL_ADD_FLG).text();
			var updateFlg = rowSelected.find("td").eq(COL_UPDATE_FLG).text();
			if(screenMode == TEISEI_MODE_CHAR &&  addFlg == FLAG_DISABLE_CHAR) {
				$('#deleteSubMenu').hide();
			} else if (screenMode == TEISEI_MODE_CHAR && updateFlg == FLAG_ENABLE_CHAR) {
				$('#deleteSubMenu').show();
			}
			// switch flag value
			isTableMenuShowing = true;
		}
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
	
	$("#tbl_tbody").bind("click",function(e) {
		var container1 = $(".contextMenu");
		if (!container1.is(e.target) // if the target of the click isn't the container
			&& container1.has(e.target).length === 0 && $("#tableMenu").is(":visible")) { // nor a descendant of the container
			$("#tableMenu").fadeOut("fast");
			$("#" + currentShowingColId).find("span").text(currentShowingColId);
			isTableMenuShowing = false;
		}
	});
	
	/**
	 * （登録画面）変換定義一覧編集メニュー_訂正_Click
	 */
	$("#modifySubMenu").bind("click", function(e) {
		isTableMenuShowing = false;
		$("#tableMenu").fadeOut("fast");
		// 変数、画面項目初期化
		$("#editMode").val(UPDATE_MODE);
		if (preRow != null) {
			preRow.removeClass("bg_00B0F0");
			
		}
		rowSelected.addClass("bg_00B0F0");
		preRow = rowSelected;
		var txtAtTorihikiCode02 = rowSelected.find("td").eq(COL_AT_TORIHIKI_CODE).text();
		var txtAtTenCode = rowSelected.find("td").eq(COL_AT_TEN_CODE).text();
		var txtCustomerCode = rowSelected.find("td").eq(COL_CUST_CODE).text();
		var txtShopCode = rowSelected.find("td").eq(COL_SHOP_CODE).text();
		var ddlDeliKb = rowSelected.find("td").eq(COL_DELI_KB).text();
		var lblCustomerNameR = rowSelected.find("td").eq(COL_CUST_NM_R).text();
		var lblShopNameR = rowSelected.find("td").eq(COL_SHOP_NM_R).text();
		var txtStsCode = rowSelected.find("td").eq(COL_STS_CODE).text();
		// 変換定義一覧の選択行を編集エリアに表示する。
		$("#txtAtTorihikiCode02").val(txtAtTorihikiCode02);
		$("#txtAtTenCode").val(txtAtTenCode);
		$("#txtCustomerCode").val(txtCustomerCode);
		$("#txtShopCode").val(txtShopCode);
		$("#ddlDeliKb").val(ddlDeliKb);
		$("#lblCustomerNameR").text(lblCustomerNameR);
		$("#lblShopNameR").text(lblShopNameR);
		$("#txtStsCode").val(txtStsCode);
		setStsCodeLbl();
		$("#btnAddUpdate").text("更新");
		
		// 編集エリアの入力項目
		$("#txtAtTorihikiCode02").attr('disabled', true);
		$("#txtAtTenCode").attr('disabled', true);
		//フォーカスセット
		$("#txtCustomerCode").focus().select();
	});
	
	/**
	 * （登録画面）変換定義一覧編集メニュー_削除_Click
	 */
	$("#deleteSubMenu").bind("click", function(e) {
		$("#tableMenu").fadeOut("fast");
		isTableMenuShowing = false;
		if (preRow != null && rowSelected != null) {
			var preObject = convertRowToObject(preRow);
			var curObject = convertRowToObject(rowSelected);
			if (areEquivalent(convertRowToObject(preRow),curObject)) {
				resetEditArea();
			}
		}
		
		deleteRow();
		resizeTable();
		var count = $('#tbl_tbody').children('tr').length;
		if (count == 0) {
			disableButton("btnRegistration");
		}
	});
});

/**
 * 得意先子画面データ受け取り用関数（仮）
 * 
 * @param 取得したデータ
 * @return
 * @exception
 */
function getCom0102d02(data) {
	// 親画面側処理（仮）
	if (data[0].custCode != null) {
		$("#txtCustomerCode").val(data[0].custCode);
		$("#lblCustomerNameR").html(data[0].custNmR);
		enableImgButton("btnSearchStore");
		$("#txtShopCode").focus();
		$("#txtCustomerCode").removeClass("form-error-field");
	}
	reTurnCheckTab();
}

/**
 * （登録画面）店舗選択_Close
 * 
 * @param data
 */
function getCom0102d03(data) {
	var showDataCnt = data.length;
	for (var i = 0; i < showDataCnt; i++) {
		$("#txtShopCode").val(data[i].shopCode);
		$("#lblShopNameR").text(data[i].shopNmR);
	}
	// フォーカスセット
	$("#ddlDeliKb").focus();
	reTurnCheckTab();
	$("#txtShopCode").removeClass("form-error-field");
}
/**
 * （登録画面）追加・更新_Click
 */
function addUpdate() {
	var errItemIds = "";
	displayMessages("",null);
	var errorMsg = "";
	// 項目編集
	var custCodeWk = "";
	var custCode = $("#txtCustomerCode").val();
	if (custCode != "") {
		if (custCode.length < 7) {
			custCodeWk = addLeadingChar(custCode, 7);
			$("#txtCustomerCode").val(custCodeWk);
		}
	}
	
	var id = "txtAtTorihikiCode02";
	// 共通チェック
	if(isEnabled(id)) {
		$("#" + id).removeClass("form-error-field");
		var errorID = chkItem($("#" + id).val(), true, TYPE_NUM, null, null);
		if (errorID != null){
			errItemIds += id + DELIMITER;
			errorMsg += $("#" + errorID).val().replace("{1}", "相手取引先コード") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
	}
	
	id = "txtAtTenCode";
	if(isEnabled(id)) {
		$("#" + id).removeClass("form-error-field");
		var errorID = chkItem($("#" + id).val(), true, TYPE_NUM_ALPHA, null, null);
		if (errorID != null){
			errItemIds += id + DELIMITER;
			errorMsg += $("#" + errorID).val().replace("{1}", "相手店コード") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
	}
	
	id = "txtCustomerCode";
	if(isEnabled(id)) {
		$("#" + id).removeClass("form-error-field");
		var errorID = chkItem($("#" + id).val(), true, TYPE_NUM, null, null);
		if (errorID != null){
			errItemIds += id + DELIMITER;
			errorMsg += $("#" + errorID).val().replace("{1}", "自社得意先コード") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
	}
	
	id = "txtShopCode";
	if(isEnabled(id)) {
		$("#" + id).removeClass("form-error-field");
		var errorID = chkItem($("#" + id).val(), true, TYPE_NUM_ALPHA, null, null);
		if (errorID != null){
			errItemIds += id + DELIMITER;
			errorMsg += $("#" + errorID).val().replace("{1}", "自社店舗コード") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
		// 店舗コード例外チェック
		if ($("#tenCodeNone").val() == $("#txtShopCode").val()) {
			errItemIds += id + DELIMITER;
			errorMsg += $("#COM030-E").val().replace("{1}", "自社店舗コード") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
	}
	
	id = "ddlDeliKb";
	if(isEnabled(id)) {
		$("#" + id).removeClass("form-error-field");
		var ddlDeliKb = $('select[name=ddlDeliKb]').val();
		var errorID = chkEmpty(ddlDeliKb);
		if (errorID != null){
			errItemIds += id + DELIMITER;
			errorMsg += $("#" + errorID).val().replace("{1}", "納品区分") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
	}
	
	id = "txtStsCode";
	if(isEnabled(id)) {
		$("#" + id).removeClass("form-error-field");
		var errorID = chkItem($("#" + id).val(), true, TYPE_NUM, null, null);
		if (errorID != null){
			errItemIds += id + DELIMITER;
			errorMsg += $("#" + errorID).val().replace("{1}", "状況コード") + "<br>";
			$("#" + id).addClass("form-error-field");
		}
		
		
	}
	id = "txtStsCode";
	var stsCode = $("#txtStsCode").val();
	if(stsCode != "" && (stsCode != TOROKU && stsCode != TORIKESHI)) {
		errItemIds += id + DELIMITER;
		errorMsg += $("#MST013-E").val() + "<br>";
		$("#" + id).addClass("form-error-field");
	}
	
	displayMessages(errorMsg,MSG_ERR);
	var lstError = errItemIds.split(DELIMITER);
	$("#" + lstError[0]).focus().select();
	// フォーカスセット
	addClassErrorToControl(lstError);
	if(errorMsg != "") {
		resizeTable();
		return;
	}
	
	// 事業所コード編集
	var sysAdminFlag = $("#sysAdminFlag").val();
	var plantCodeWk = null;
	// 事業所コード編集
	if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
		plantCodeWk = $('#plantCode').val();
	} else if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
		plantCodeWk = null;
	}
	
	var custCode = $("#txtCustomerCode").val();
	var shopCode = $("#txtShopCode").val();
	$.ajax({
		type : "POST",
		url : "getCustomerData",
		async : false,
		data : {"custCode" : custCode,
				"jigyoCode" : plantCodeWk,
				"shopCode" : shopCode
				},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			$("#lblCustomerNameR").text(returnData.lblCustomerNameR);
			$("#lblShopNameR").text(returnData.lblShopNameR);
			if(returnData.errMessage != null && returnData.errMessage != "") {
				displayMessages(returnData.errMessage, MSG_ERR)
				var lstError = returnData.errItemIds.split(DELIMITER);
				$("#" + lstError[0]).focus().select();
				// フォーカスセット
				addClassErrorToControl(lstError);
			} else {
				// 編集エリアの内容で変換定義一覧を更新する。
				var editMode = $("#editMode").val();
				if (editMode == ADD_MODE) {
					addData();
				} else if (editMode == UPDATE_MODE) {
					updateData()
				}
			}
		},
		error: function(e) {
			
		},
		complete: function(jqXHR, textStatus) {
		}
	});
	resizeTable();
}


/**
 * 編集エリアの内容を[画面]変換定義一覧の最後尾へ追加する。
 */
function addData() {
	// 変換定義一覧重複チェック
	var txtOnlineCenterCode = $("#txtOnlineCenterCode").val();
	var txtOnlineTorihikiCode = $("#txtOnlineTorihikiCode").val();
	var txtAtTorihikiCode02 = $("#txtAtTorihikiCode02").val();
	var txtAtTenCode = $("#txtAtTenCode").val();
	// 次の条件全てに合致するデータが[画面]変換定義一覧に存在する場合
	if (duplicateCheck(txtOnlineCenterCode, txtOnlineTorihikiCode, txtAtTorihikiCode02, txtAtTenCode)) {
		// 画面にエラーメッセージを表示する。
		displayMessages($("#COM027-E").val(), MSG_ERR);
		// フォーカスをセットする。
		if (isEnabled("txtAtTorihikiCode02")) {
			$("#txtAtTorihikiCode02").focus().select();
		} else {
			$("#txtAtTenCode").focus().select();
		}
	// 編集エリアの内容を[画面]変換定義一覧の最後尾へ追加する。
	} else {
		var count = $('#tbl_tbody').children('tr').length;
		var count_display= $('#tbl_tbody').children('tr.display_none').length;
		var no = (count - count_display) + 1;
		var row = newRow((count - count_display) + 1);
		$('#tblBody > tbody:last-child').append(row);
		resizeTable();
		
		// 画面に正常終了メッセージを表示する。（情報）
		displayMessages($("#MST001-I").val().replace("{2}", no), MSG_INFO);
		enableButton("btnRegistration");
		
		// フォーカスをセットする。
		if ( isEnabled("txtAtTorihikiCode02")) {
			$("#txtAtTorihikiCode02").focus().select();
		} else {
			$("#txtAtTenCode").focus().select();
		}
	}
}

/**
 * [変数](編集エリア) 対象行Noのレコードを対象に、編集エリアの内容で更新する。　（変換定義一覧にて選択された行を編集エリアの内容で更新する。）
 */
function updateData() {
	// [変数](編集エリア) 対象行Noのレコードを対象に、編集エリアの内容で更新する。　（変換定義一覧にて選択された行を編集エリアの内容で更新する。）
	var olCenterCode = $("#txtOnlineCenterCode").val();
	var olTorihikiCode = $("#txtOnlineTorihikiCode").val();
	var atTorihikiCode = $("#txtAtTorihikiCode02").val();
	var atTenCode = $("#txtAtTenCode").val();
	var custCode = $("#txtCustomerCode").val();
	var txtTarget = $('#ddlDeliKb :selected').text();
	var target1 = txtTarget.substring(txtTarget.indexOf(":") + 1).trim();
	var custNmR = $("#lblCustomerNameR").text();
	var shopCode = $("#txtShopCode").val();
	var shopNmR = $("#lblShopNameR").text();
	var deliKb = $("#ddlDeliKb").val();
	var stsCode = $("#txtStsCode").val();
	if (stsCode == TOROKU) {
		$("#lblStsCodeNm").text("登録");
	} else if (stsCode == TORIKESHI) {
		$("#lblStsCodeNm").text("取消");
	}
	var tempObj = {
			olCenterCode: olCenterCode,
			olTorihikiCode : olTorihikiCode,
			atTorihikiCode : atTorihikiCode,
			atTenCode : atTenCode,
			custCode :custCode,
			target1 :target1,
			custNmR : custNmR,
			shopCode : shopCode,
			shopNmR : shopNmR,
			deliKb : deliKb,
			stsCode : stsCode
		};
	var no = rowSelected.find("td").eq(COL_NO).attr("id");
	rowSelected.find("td").eq(COL_OL_CENTER_CODE).text(olCenterCode);
	rowSelected.find("td").eq(COL_OL_TORIHIKI_CODE).text(olTorihikiCode);
	rowSelected.find("td").eq(COL_AT_TORIHIKI_CODE).text(atTorihikiCode);
	rowSelected.find("td").eq(COL_AT_TEN_CODE).text(atTenCode);
	rowSelected.find("td").eq(COL_CUST_CODE).text(custCode);
	rowSelected.find("td").eq(COL_TARGET1).text(target1);
	rowSelected.find("td").eq(COL_CUST_NM_R).text(custNmR);
	rowSelected.find("td").eq(COL_SHOP_CODE).text(shopCode)
	rowSelected.find("td").eq(COL_SHOP_NM_R).text(shopNmR);
	rowSelected.find("td").eq(COL_DELI_KB).text(deliKb);
	rowSelected.find("td").eq(COL_STS_CODE).text(stsCode);
	if(isRowChange(tempObj)) {
		displayMessages($("#MST002-I").val().replace("{2}", no), MSG_INFO);
		rowSelected.find("td").eq(COL_UPDATE_FLG).text(FLAG_ENABLE_CHAR);
		enableButton("btnRegistration");
	} else {
		rowSelected.find("td").eq(COL_UPDATE_FLG).text(FLAG_DISABLE_CHAR);
	}
	
	$("#btnClear").focus();
}

/**
 * Check at least one column is changed
 * 
 * @param row
 * @returns {Boolean}
 */
function isRowChange (row) {
	// Fix bug : update value for the new row.
	var addFlg = rowSelected.find("td").eq(COL_ADD_FLG).text();;
	if (addFlg == FLAG_ENABLE_CHAR) {
		return true;
	}
	if (srcRowList.length > 0) {
		for(var i = 0; i < srcRowList.length; i++) {
			if((row.olCenterCode == srcRowList[i].olCenterCode
			&& row.olTorihikiCode == srcRowList[i].olTorihikiCode
			&&  row.atTorihikiCode == srcRowList[i].atTorihikiCode
			&&  row.atTenCode == srcRowList[i].atTenCode)
			&& (row.custCode != srcRowList[i].custCode
			|| row.shopCode != srcRowList[i].shopCode
			|| row.deliKb != srcRowList[i].deliKb
			|| row.stsCode != srcRowList[i].stsCode)
			) {
				return true;
			}
		}
	} else {
		return true;
	}
	
	return false;
}

/**
 * Check rows are considered equivalent.
 * 
 * @param preRow
 * @param curRow
 * @returns {Boolean} true if the the rows are equivalent; false otherwise
 */
function areEquivalent(preRow, curRow) {
	if((preRow.olCenterCode == curRow.olCenterCode
			&& preRow.olTorihikiCode == curRow.olTorihikiCode
			&& preRow.atTorihikiCode == curRow.atTorihikiCode
			&& preRow.atTenCode == curRow.atTenCode)) {
		return true;
	}
	return false;
}
/**
 * Delete the selected row on the table.
 */
function deleteRow() {
	var dataList = getDataList();
	var htmlRow = "";
	var no = rowSelected.find("td").eq(COL_NO).attr("id");
	if (dataList.length > 0) {
		var renban = 0;
		for(var i = 0; i < dataList.length; i ++) {
			var dataRow = dataList[i];
			if (no != (i+1)) {
				renban ++;
				// ［引数］オンライン得意先変換マスタ情報格納クラス.状況コード　=　9　（取消）　の場合、背景色を取消色とする。
				if (dataRow.stsCode == TORIKESHI) {
					htmlRow += "<tr class ='tbl_del'>";
				} else {
					htmlRow += "<tr>";
				}
				htmlRow += "<td id='" + renban + "' name='"+ ReplaceNullReturnBlank(dataRow.olCenterCode) +"' class='contextMenu align_center'><span>" + renban + "</span></td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(dataRow.olCenterCode) + "</td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(dataRow.olTorihikiCode) + "</td>";
				htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(dataRow.atTorihikiCode) + "</td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(dataRow.atTenCode) + "</td>";
				htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(dataRow.custCode) + "</td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(dataRow.shopCode) + "</td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(dataRow.target1) + "</td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(dataRow.custNmR)) + "</td>";
				htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(dataRow.shopNmR)) + "</td>";
				htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.deliKb) + "</td>";
				htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.stsCode) + "</td>";
				htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.updateFlg) + "</td>";
				htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.addFlg) + "</td>";
				htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.deleteFlg) + "</td>";
				htmlRow += "</tr>";
			}
		}
	}
	$("#tbl_tbody").html('');
	$("#tbl_tbody").html(htmlRow);
}

function replaceText(text) {
	text = text.replace(/ /g,"");
	return text;
}
/**
 * 画面構成.
 */
function configureScreen () {
	var screenMode = $('#screenMode').val();
	if(screenMode == SHINKI_MODE_CHAR) {
		// ヘッダ部
		$('#txtOnlineCenterCode').val('');
		$('#txtOnlineTorihikiCode').val('');
		$('#txtAtTorihikiCode').val('');
		
		// 編集エリア
		$('#txtAtTorihikiCode02').val('').attr('disabled', true);
		$('#txtAtTenCode').val('').attr('disabled', true);
		$('#txtCustomerCode').val('').attr('disabled', true);
		$('#txtShopCode').val('').attr('disabled', true);
		$('#ddlDeliKb').attr('disabled', true);
		$('#txtStsCode').val('').attr('disabled', true);
		
		disableImgButton("btnSearchCusts");
		disableImgButton("btnSearchStore");
		
		disableButton("btnAddUpdate");
		disableButton("btnClear");
		
		// Footer area
		disableButton("btnRegistration");
		// フォーカス
		$("#txtOnlineCenterCode").focus();
	} else if (screenMode == SHOUKAI_MODE_CHAR) {
		search();
		// ヘッダ部
		$('#txtOnlineCenterCode').attr('disabled', true);
		$('#txtOnlineTorihikiCode').attr('disabled', true);
		$('#txtAtTorihikiCode').attr('disabled', true);
		$("#lblCustomerNameR01").text($("#hCustomerNameR01").val());
		$("#lblCustomerNameR02").text($("#hCustomerNameR02").val());
		disableButton("btnCreate");
		// 編集エリア
		$('#txtAtTorihikiCode02').val('').attr('disabled', true);
		$('#txtAtTenCode').val('').attr('disabled', true);
		$('#txtCustomerCode').val('').attr('disabled', true);
		$('#txtShopCode').val('').attr('disabled', true);
		$('#ddlDeliKb').attr('disabled', true);
		$('#txtStsCode').val('').attr('disabled', true);
		$("#lblCustomerNameR").text("");
		$("#lblShopNameR").text("");
		disableImgButton("btnSearchCusts");
		disableImgButton("btnSearchStore");
		
		disableButton("btnClear");
		disableButton("btnAddUpdate");
		
		// Footer area
		disableButton("btnRegistration");
		disableButton("btnFooterClear");
		// フォーカス
		$("#btnBack").focus();
	} else if (screenMode == TEISEI_MODE_CHAR) {
		// リスト部
		search();
		// ヘッダ部
		$('#txtOnlineCenterCode').attr('disabled', true);
		$('#txtOnlineTorihikiCode').attr('disabled', true);
		$('#txtAtTorihikiCode').attr('disabled', true);
		$("#lblCustomerNameR01").text($("#hCustomerNameR01").val());
		$("#lblCustomerNameR02").text($("#hCustomerNameR02").val());
		disableButton("btnCreate");
		// 編集エリア
		$('#txtAtTorihikiCode02').attr('disabled', true);
		enableImgButton("btnSearchCusts");
		disableImgButton("btnSearchStore");
		// Footer area
		enableReturnButton("btnBack");
		enableButton("btnFooterClear");
		disableButton("btnRegistration");
		// フォーカス
		$("#txtAtTenCode").focus();
	} else if (screenMode == TORIKESI_MODE_CHAR) {
		// リスト部
		search();
		// ヘッダ部
		$('#txtOnlineCenterCode').attr('disabled', true);
		$('#txtOnlineTorihikiCode').attr('disabled', true);
		$('#txtAtTorihikiCode').attr('disabled', true);
		disableButton("btnCreate");
		// 編集エリア
		$('#txtAtTorihikiCode02').val('').attr('disabled', true);
		$('#txtAtTenCode').val('').attr('disabled', true);
		$('#txtCustomerCode').val('').attr('disabled', true);
		$('#txtShopCode').val('').attr('disabled', true);
		$('#ddlDeliKb').attr('disabled', true);
		$('#txtStsCode').val('').attr('disabled', true);
		disableButton("btnClear");
		
		// Footer area
		disableButton("btnFooterClear");
		// フォーカス
		$("#btnBack").focus();
	}
	
	setStsCodeLbl();
}

/**
 * 検索ボタン.
 */
function search() {
	$.ajax({
		type: "POST",
		url: "search",
		data:$("form").serialize(),
		async : false,
		success: function(returnJsonData) {
			displayMessages("", null);
			var haveData = false;
			var dataList = returnJsonData.olCustConvList;
			if(dataList == null) {
				// 共通仕様 4-4適用
				$("#tbl_tbody").html(returnJsonData.errMessage);
				// 共通仕様 3-6適用
				errorControl();
				
				disableImgButton("btnSearchCusts");
				disableImgButton("btnSearchStore");
			} else {
				drawTable(dataList);
				srcRowList = getDataList();
			}
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
		}
	});
}

/**
 * オンライン得意先変換マスタデータ取得.
 */
function getOlCustConvData() {
	var screenMode = $("#screenMode").val();
	var atTorihikiCode = $("#txtAtTorihikiCode").val();
	var olCenterCode = $("#txtOnlineCenterCode").val();
	var olTorihikiCode = $("#txtOnlineTorihikiCode").val();
	var businessDate = $("#businessDate").val();
	var olTorihikiCodeNone = $("#olTorihikiCodeNone").val();
	$.ajax({
		type: "POST",
		url: "getOlCustConvData",
		data : {"screenMode" : screenMode,
			"atTorihikiCode" : atTorihikiCode,
			"olCenterCode" : olCenterCode,
			"olTorihikiCode" : olTorihikiCode,
			"businessDate" : businessDate,
			"olTorihikiCodeNone" : olTorihikiCodeNone
			},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		async : false,
		success: function(returnJsonData) {
			displayMessages("", null);
			var haveData = false;
			var dataList = returnJsonData.olCustConvList;
			if(dataList == null) {
				// 共通仕様 4-4適用
				$("#tbl_tbody").html(returnJsonData.errMessage);
				// 共通仕様 3-6適用
				errorControl();
				
				disableImgButton("btnSearchCusts");
				disableImgButton("btnSearchStore");
			} else {
				drawTable(dataList);
				srcRowList = getDataList();
			}
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
		}
	});
}
/**
 * 変換定義一覧画面表示
 * 
 * @param dataList オンライン得意先変換マスタ情報格納クラス
 */
function drawTable(dataList) {
	// 変換定義一覧が既に作成されている場合は、変換定義一覧を全件削除する
	$("#tbl_tbody").html("");
	if(dataList != null && dataList.length > 0) {
		var rowList = "";
		for (var i = 0 ; i < dataList.length ; i++){
			if($('#screenMode').val() == SHOUKAI_MODE_CHAR) {
				rowList += "<tr class ='display_none'>";
			} else {
				// ［引数］オンライン得意先変換マスタ情報格納クラス.状況コード　=　9　（取消）　の場合、背景色を取消色とする。
				if (dataList[i].stsCode == TORIKESHI) {
					rowList += "<tr class ='tbl_del'>";
				} else {
					rowList += "<tr>";
				}
			}
			// ［引数］オンライン得意先変換マスタ情報格納クラス全件を［画面］オンライン得意先変換マスタ一覧にセットする。
			rowList += displayRow(i+1, dataList[i]);
			rowList += "</tr>";
		}
		
		$("#tbl_tbody").html(rowList);
		resizeTable();
	}
}


/**
 * Display a row data to the table data
 * 
 * @param no The ordinal number (from 1)
 * @param rowData
 * @returns {String} The HTML element table row
 */
function displayRow(no, rowData) {
	var htmlRow = "";
	// 1. No
	htmlRow += "<td id='" + no + "' name='"+ ReplaceNullReturnBlank(rowData.olCenterCode) +"' class='contextMenu align_center'><span>" + no + "</span></td>";
	// 2. OLセンター
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(rowData.olCenterCode) + "</td>";
	// 3. OL取引先
	if($("#olTorihikiCodeNone").val() == rowData.olTorihikiCode) {
		htmlRow += "<td class=''></td>";
	} else {
		htmlRow += "<td class=''>" + ReplaceNullReturnBlank(rowData.olTorihikiCode) + "</td>";
	}
	// 4.相手取引先
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(rowData.atTorihikiCode) + "</td>";
	// 5.相手店コード
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(rowData.atTenCode) + "</td>";
	// 6.自社得意先コード
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(rowData.custCode) + "</td>";
	if ($("#tenCodeNone").val() == rowData.shopCode) {
		// 7. 自社店舗コード
		htmlRow += "<td class=''></td>";
	} else {
		// 7. 自社店舗コード
		htmlRow += "<td class=''>" + ReplaceNullReturnBlank(rowData.shopCode) + "</td>";
	}
	
	// 8.納品区分名
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(rowData.target1) + "</td>";
	// 9.得意先名称
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(rowData.custNmR)) + "</td>";
	if ($("#tenCodeNone").val() == rowData.shopCode) {
		// 10. 店舗名称
		htmlRow += "<td class=''></td>";
	} else {
		// 10. 店舗名称
		htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(rowData.shopNmR)) + "</td>";
	}
	
	// 11. 納品区分
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(rowData.deliKb) + "</td>";
	if ($('#screenMode').val() == TORIKESI_MODE_CHAR) {
		// ※変換マスタ格納クラス.状況コード≠9　を対象に、全行9：取消に置き換える。
		// 12. 状況コード
		htmlRow += "<td class='display_none'>9</td>";
	} else {
		// 12. 状況コード
		htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(rowData.stsCode) + "</td>";
	}
	
	// 13. 更新フラグ
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(rowData.updateFlg) + "</td>";
	// 14. 追加フラグ
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(rowData.addFlg) + "</td>";
	
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(rowData.deleteFlg) + "</td>";
	return htmlRow;
}

/**
 * （登録画面）設定_Click
 */
function create() {
	var id = "txtOnlineCenterCode";
	var errItemIds = "";
	var errorMsg = "";
	// オンラインセンターコード
	$("#" + id).removeClass("form-error-field");
	var errorID = chkItem($("#" + id).val(), true, TYPE_NUM_ALPHA, null, null);
	if (errorID != null){
		errItemIds += id + DELIMITER;
		errorMsg += $("#" + errorID).val().replace("{1}", "オンラインセンターコード") + "<br>";
		$("#" + id).addClass("form-error-field");
	}
	
	// 相手取引先コード
	id = "txtAtTorihikiCode";
	$("#" + id).removeClass("form-error-field");
	var errorID = chkItem($("#" + id).val(), false, TYPE_NUM, null, null);
	if (errorID != null){
		errItemIds += id + DELIMITER;
		errorMsg += $("#" + errorID).val().replace("{1}", "相手取引先コード") + "<br>";
		$("#" + id).addClass("form-error-field");
	}
	
	// オンライン取引先コード
	id = "txtOnlineTorihikiCode";
	$("#" + id).removeClass("form-error-field");
	var errorID = chkItem($("#" + id).val(), false, TYPE_NUM_ALPHA, null, null);
	if (errorID != null){
		errItemIds += id + DELIMITER;
		errorMsg += $("#" + errorID).val().replace("{1}", "オンライン取引先コード") + "<br>";
		$("#" + id).addClass("form-error-field");
	}
	
	// オンライン取引先コード
	id = "txtOnlineTorihikiCode";
	$("#" + id).removeClass("form-error-field");
	if ($("#olTorihikiCodeNone").val() == $("#txtOnlineTorihikiCode").val()) {
		errItemIds += id + DELIMITER;
		errorMsg += $("#COM030-E").val().replace("{1}", "オンライン取引先コード") + "<br>";
		$("#" + id).addClass("form-error-field");
	}
	
	if (errItemIds != "" || errorMsg != "" ) {
		displayMessages(errorMsg, MSG_ERR);
		var lstError = errItemIds.split(DELIMITER);
		addClassErrorToControl(lstError);
		// フォーカスセット
		$("#" + lstError[0]).focus().select();
		return;
	}
	
	$.ajax({
		type: "POST",
		url: "create",
		data:$("form").serialize(),
		async : false,
		success: function(returnJsonData) {
			displayMessages("", null);
			$("#lblCustomerNameR01").text(returnJsonData.lblCustomerNameR01);
			$("#lblCustomerNameR02").text(returnJsonData.lblCustomerNameR02);
			var errMessage = returnJsonData.errMessage
			if(errMessage != null && errMessage != "") {
				displayMessages(errMessage, MSG_ERR);
				if(returnJsonData.errItemIds != null && returnJsonData.errItemIds != "") {
					var lstError = returnJsonData.errItemIds.split(DELIMITER);
					$("#" + lstError[0]).focus().select();
					addClassErrorToControl(lstError);
				}
			} else {
				// ヘッダーエリアを入力確定とし、非活性化する。
				$('#txtOnlineCenterCode').attr('disabled', true);
				$('#txtOnlineTorihikiCode').attr('disabled', true);
				$('#txtAtTorihikiCode').attr('disabled', true);
				disableButton("btnCreate");
				
				// 編集エリアを初期化する。
				
				// 相手取引先コード
				var txtAtTorihikiCode =  $("#txtAtTorihikiCode").val();
				if(txtAtTorihikiCode == "") {
					$("#txtAtTorihikiCode02").val("");
					$('#txtAtTorihikiCode02').attr('disabled', false);
					
				} else {
					$("#txtAtTorihikiCode02").val(txtAtTorihikiCode);
					$('#txtAtTorihikiCode02').attr('disabled', true);
				}
				enableImgButton("btnSearchCusts");
				disableImgButton("btnSearchStore");
				
				$('#txtAtTenCode').val('').attr('disabled', false);
				$('#txtCustomerCode').val('').attr('disabled', false);
				$('#txtShopCode').val('').attr('disabled', false);
				$('#ddlDeliKb').attr('disabled', false);
				$('#txtStsCode').val('').attr('disabled', false);
				enableButton("btnAddUpdate");
				enableButton("btnClear");
				$("#editMode").val(ADD_MODE);
				$("#btnAddUpdate").text("追加");
				if(txtAtTorihikiCode == "") {
					$("#txtAtTorihikiCode02").focus();
				} else {
					$("#txtAtTenCode").focus();
				}
			}
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
			
		}
	});
}

/**
 * Create a new row in the table.
 * 
 * @param no The ordinal numbers
 * @returns {String} A row of cells in a table
 */
function newRow(no) {
	var olCenterCode = $("#txtOnlineCenterCode").val();
	var olTorihikiCode = $("#txtOnlineTorihikiCode").val();
	var atTorihikiCode = $("#txtAtTorihikiCode02").val();
	var atTenCode = $("#txtAtTenCode").val();
	var custCode = $("#txtCustomerCode").val();
	var txtTarget = $('#ddlDeliKb :selected').text();
	var target1 = txtTarget.substring(txtTarget.indexOf(":") + 1).trim();
	var custNmR = $("#lblCustomerNameR").text();
	var shopCode = $("#txtShopCode").val();
	var shopNmR = $("#lblShopNameR").text();
	var deliKb = $("#ddlDeliKb").val();
	var stsCode = $("#txtStsCode").val();
	if (stsCode == TOROKU) {
		$("#lblStsCodeNm").text("登録");
	} else if (stsCode == TORIKESHI) {
		$("#lblStsCodeNm").text("取消");
	}
	var addFlg = FLAG_ENABLE_CHAR;
	var updateFlg = FLAG_ENABLE_CHAR;
	var deleteFlg = FLAG_DISABLE_CHAR;
	
	var htmlRow = "";
	htmlRow += "<tr>";
	htmlRow += "<td id='" + no + "' name='"+ ReplaceNullReturnBlank(olCenterCode) +"' class='contextMenu align_center'><span>" + no + "</span></td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(olCenterCode) + "</td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(olTorihikiCode) + "</td>";
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(atTorihikiCode) + "</td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(atTenCode) + "</td>";
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(custCode) + "</td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(shopCode) + "</td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(target1) + "</td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(custNmR)) + "</td>";
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(shopNmR)) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(deliKb) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(stsCode) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(addFlg) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(updateFlg) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(deleteFlg) + "</td>";
	htmlRow += "</tr>";
	return htmlRow;
}

/**
 * Get the list of rows in the table.
 * 
 * @returns {Array} The list of rows in the table
 */
function getDataList () {
	var dataList = [];
	$('#tblBody tbody tr').each(function() {
		var row = $(this);
		var renban = row.find("td").eq(COL_NO).text();
		var olCenterCode = row.find("td").eq(COL_OL_CENTER_CODE).text();
		var olTorihikiCode = row.find("td").eq(COL_OL_TORIHIKI_CODE).text();
		var atTorihikiCode = row.find("td").eq(COL_AT_TORIHIKI_CODE).text();
		var atTenCode = row.find("td").eq(COL_AT_TEN_CODE).text();
		var custCode = row.find("td").eq(COL_CUST_CODE).text();
		var target1 = row.find("td").eq(COL_TARGET1).text();
		var shopCode = row.find("td").eq(COL_SHOP_CODE).text()
		var custNmR = row.find("td").eq(COL_CUST_NM_R).text();
		var shopNmR = row.find("td").eq(COL_SHOP_NM_R).text();
		var deliKb = row.find("td").eq(COL_DELI_KB).text();
		var stsCode = row.find("td").eq(COL_STS_CODE).text();
		var addFlg = row.find("td").eq(COL_ADD_FLG).text();
		var updateFlg = row.find("td").eq(COL_UPDATE_FLG).text();
		var deleteFlg = row.find("td").eq(COL_DELETEF_LG).text();
		var tempObj = {
			olCenterCode: olCenterCode,
			olTorihikiCode : olTorihikiCode,
			atTorihikiCode : atTorihikiCode,
			atTenCode : atTenCode,
			custCode :custCode,
			target1 :target1,
			custNmR : custNmR,
			shopCode : shopCode,
			shopNmR : shopNmR,
			deliKb : deliKb,
			stsCode : stsCode,
			addFlg : addFlg,
			updateFlg : updateFlg,
			deleteFlg: deleteFlg
		};
		dataList.push(tempObj);
	});
	return dataList;
}

/**
 * 次の条件全てに合致するデータが[画面]変換定義一覧に存在する場合
 * 
 * @param txtOnlineCenterCode [画面](ヘッダ部)オンラインセンターコード
 * @param txtOnlineTorihikiCode [画面](ヘッダ部)オンライン取引先コード
 * @param txtAtTorihikiCode02 [画面](編集エリア)相手取引先コード
 * @param txtAtTenCode [画面](編集エリア)相手店コード
 * @returns {Boolean} 
 */
function duplicateCheck(txtOnlineCenterCode, txtOnlineTorihikiCode, txtAtTorihikiCode02, txtAtTenCode) {
	var dataList = getDataList();
	for(var i = 0; i < dataList.length;  i++) {
		if (txtOnlineCenterCode == dataList[i].olCenterCode
			&& txtOnlineTorihikiCode == dataList[i].olTorihikiCode
			&& txtAtTorihikiCode02 == dataList[i].atTorihikiCode
			&& txtAtTenCode == dataList[i].atTenCode) {
			return true;
		}
	}
	return false;
}

/**
 * （登録画面）クリアボタン_Click
 */
function footerClear() {
	$('input').removeClass('form-error-field');
	$('select').removeClass('form-error-field');
	displayMessages("", null);
	var screenMode = $("#screenMode").val();
	if (screenMode == SHINKI_MODE_CHAR) {
		// ヘッダ部
		$('#txtOnlineCenterCode').attr('disabled', false).removeClass("form-error-field").val('');
		$('#txtOnlineTorihikiCode').attr('disabled', false).removeClass("form-error-field").val('');
		$('#txtAtTorihikiCode').attr('disabled', false).removeClass("form-error-field").val('');
		enableButton("btnCreate");
		// 編集エリア
		$('#txtAtTorihikiCode02').val('').attr('disabled', true);
		$('#txtAtTenCode').val('').attr('disabled', true);
		$('#txtCustomerCode').val('').attr('disabled', true);
		$('#txtShopCode').val('').attr('disabled', true);
		var optFirst = $("#ddlDeliKb option:first").val();
		$('#ddlDeliKb').val(optFirst).attr('disabled', true);
		$('#txtStsCode').val('').attr('disabled', true);
		setStsCodeLbl();		
		$("#lblCustomerNameR").text("");
		$("#lblShopNameR").text("");
		disableImgButton("btnSearchCusts");
		disableImgButton("btnSearchStore");
		
		disableButton("btnAddUpdate");
		disableButton("btnClear");
		
		// リスト部
		$("#tbl_tbody").html('');
		// Footer area
		enableReturnButton("btnBack");
		enableButton("btnRegistration");
		enableButton("btnFooterClear");
		// フォーカス
		$("#txtOnlineCenterCode").focus();
	} else if(screenMode == TEISEI_MODE_CHAR) {
		// ヘッダ部の値を条件にオンライン得意先変換マスタデータの再取得を行う。
		getOlCustConvData();
		// ヘッダ部
		$('#txtOnlineCenterCode').attr('disabled', true).removeClass("form-error-field");
		$('#txtOnlineTorihikiCode').attr('disabled', true).removeClass("form-error-field");
		$('#txtAtTorihikiCode').attr('disabled', true).removeClass("form-error-field");
		disableButton("btnCreate");
		// 編集エリア
		$('#txtAtTorihikiCode02').removeClass("form-error-field").attr('disabled', true).val($('#txtAtTorihikiCode').val());
		$('#txtAtTenCode').val('').attr('disabled', false);
		$('#txtCustomerCode').val('').attr('disabled', false);
		$('#txtShopCode').val('').attr('disabled', false);
		var optFirst = $("#ddlDeliKb option:first").val();
		$('#ddlDeliKb').val(optFirst).attr('disabled', false);
		$('#txtStsCode').val(1).attr('disabled', false);
		setStsCodeLbl();
		$("#lblCustomerNameR").text("");
		$("#lblShopNameR").text("");
		enableImgButton("btnSearchCusts");
		disableImgButton("btnSearchStore");
		enableButton("btnAddUpdate");
		
		// Footer area
		enableReturnButton("btnBack");
		enableButton("btnFooterClear");
		disableButton("btnRegistration");
		// フォーカス
		$("#txtAtTenCode").focus();
	}
	resizeTable();
}

/**
 * 登録ボタン
 */
function register() {
	var olCustConvList = getDataList();
	var haitaDate = $("#haitaDate").val();
	var haitaTime = $("#haitaTime").val();
	var olTorihikiCodeNone = $("#olTorihikiCodeNone").val();
	var tenCodeNone = $("#tenCodeNone").val();
	var screenMode = $("#screenMode").val();
	var tempObj = {
			olCustConvList : olCustConvList,
			haitaDate: haitaDate,
			haitaTime: haitaTime,
			olTorihikiCodeNone: olTorihikiCodeNone,
			tenCodeNone: tenCodeNone,
			screenMode: screenMode
		};
	$.ajax({
		type: "POST",
		url: "register",
		contentType: 'application/json; charset=utf-8',
		dataType: "json",
		data : JSON.stringify(tempObj),
		async: false,
		success: function(data) {
			var list = data.olCustConvList;
			if(list != null && list.length > 0) {
				resetEditArea();
				$("#haitaDate").val(data.haitaDate);
				$("#haitaTime").val(data.haitaTime);
				getOlCustConvData();
				displayMessages(data.message, MSG_INFO);
			} else {
				displayMessages(data.message, MSG_ERR);
			}
		},
		error: function(e) {
			
		},
		complete: function(jqXHR, textStatus) {
			enableButton("btnRegistration");
		}
	});
}

/**
 * Resize the table.
 */
function resizeTable () {
	var tblBodyHeight = $("#tblBody").height();
	var divBodyHeight = $("#divBody").height();
	if (tblBodyHeight > divBodyHeight) {
		$("#divBody").css("overflow-y", "scroll");
		$("#divHead").width($("#divBody").width() - 17);
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width() - 17);
		});
	} else {
		$("#divBody").css("overflow-y", "hidden");
		$("#divHead").width($("#divBody").width());
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width());
		});
	}
}

/**
 * Reset the edit area.
 */
function resetEditArea() {
	displayMessages("",null);
	$('.data_conditions_input_cont input').removeClass('form-error-field');
	$('.data_conditions_input_cont select').removeClass('form-error-field');
	// 編集エリアの入力項目を初期化する。
	if (isEnabled("txtAtTorihikiCode02")) {
		$("#txtAtTorihikiCode02").val("");
	}
	$("#txtAtTenCode").attr('disabled', false).val("");
	$("#txtCustomerCode").val("");
	$("#lblCustomerNameR").text("");
	enableImgButton("btnSearchCusts");
	$("#txtShopCode").val("");
	$('#ddlDeliKb').val(2);
	$("#lblShopNameR").text("");
	disableImgButton("btnSearchStore");
	$("#btnAddUpdate").text("追加");
	$("#editMode").val(ADD_MODE);
	// フォーカスセット
	if (isEnabled("txtAtTorihikiCode02")) {
		$("#txtAtTorihikiCode02").focus().select();
	} else {
		$("#txtAtTenCode").val("").focus().select();
	}
	resizeTable();
}

/**
 * Conver the row to the object.
 * @param row
 * @returns {Object}
 */
function convertRowToObject(row) {
	var olCenterCode = row.find("td").eq(COL_OL_CENTER_CODE).text();
	var olTorihikiCode = row.find("td").eq(COL_OL_TORIHIKI_CODE).text();
	var atTorihikiCode = row.find("td").eq(COL_AT_TORIHIKI_CODE).text();
	var atTenCode = row.find("td").eq(COL_AT_TEN_CODE).text();
	var custCode = row.find("td").eq(COL_CUST_CODE).text();
	var target1 = row.find("td").eq(COL_TARGET1).text();
	var shopCode = row.find("td").eq(COL_SHOP_CODE).text()
	var custNmR = row.find("td").eq(COL_CUST_NM_R).text();
	var shopNmR = row.find("td").eq(COL_SHOP_NM_R).text();
	var deliKb = row.find("td").eq(COL_DELI_KB).text();
	var stsCode = row.find("td").eq(COL_STS_CODE).text();
	var addFlg = row.find("td").eq(COL_ADD_FLG).text();
	var updateFlg = row.find("td").eq(COL_UPDATE_FLG).text();
	var deleteFlg = row.find("td").eq(COL_DELETEF_LG).text();
	var tempObj = {
		olCenterCode: olCenterCode,
		olTorihikiCode : olTorihikiCode,
		atTorihikiCode : atTorihikiCode,
		atTenCode : atTenCode,
		custCode :custCode,
		target1 :target1,
		custNmR : custNmR,
		shopCode : shopCode,
		shopNmR : shopNmR,
		deliKb : deliKb,
		stsCode : stsCode,
		addFlg : addFlg,
		updateFlg : updateFlg,
		deleteFlg: deleteFlg
	};
	
	return tempObj;
}

function setStsCodeLbl() {
	var stsCode = $("#txtStsCode").val();
	if (stsCode == TOROKU) {
		$("#lblStsCodeNm").text("登録");
	} else if (stsCode == TORIKESHI) {
		$("#lblStsCodeNm").text("取消");
	} else {
		$("#lblStsCodeNm").text("（1:登録、9:取消）");
	}
}