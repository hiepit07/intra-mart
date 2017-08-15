/**
 * ファイル名:sei0102d01.js
 * 概要:請求締め処理
 * 
 * 作成者:都築電気
 * 作成日:2015/10/27
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/10/27 1.00                  ACT)ANZAI       新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

// 事務担当者コード（Focus In）
var prevJimuTantoshaCd = "";
// 請求先コード（Focus In）
var prevSeikyusakiCd = "";
// Controlエラーの受信デリミター
var DELIMITER = "##";

/**
 * DOCUMENT READY イベント
 * 
 */
jQuery(document).ready(function($) {
	
	//-------------------------------------------------------------------------
	// サーバーチェックエラー項目設定
	//-------------------------------------------------------------------------
	
	var lstErrorIDVal = $("#lstErrorID").val();
	var errorItem = "";
	if (lstErrorIDVal != null && lstErrorIDVal != "") {
		var errorIdList = lstErrorIDVal.split(DELIMITER);
		// js.common.js
		addClassErrorToControl(errorIdList);
		errorItem = errorIdList[0];
	}
	
	//-------------------------------------------------------------------------
	// エラー時コントロール制御
	//-------------------------------------------------------------------------
	
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		// js.com.systemCommon.js
		errorControl();
	}
	
	//-------------------------------------------------------------------------
	// フォーカスセット
	//-------------------------------------------------------------------------
	
	if (errorItem != null && errorItem != "") {
		$("#" + errorItem).focus();
		$("#" + errorItem).select();
	} else if ($("#sysAdminFlag").val() == "true") {
		$("#selectedJigyoshoCd").focus();
	} else {
		$("#seikyuShimebi").focus();	
	}
	
	/**************************************************************************
	 * 請求締日
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// 請求締日 DatePicker
	//-------------------------------------------------------------------------
	
	var notFoundFlag = $("#notFoundFlag").val();
	if (notFoundFlag == "true") {
		var pathname = location.pathname;
		var dirs = pathname.split("/");
		// datepicker 設定
		$("#seikyuShimebi").datepicker({
		      showOn         : "button",
		      buttonImage    : "/" + dirs[1] + "/resources/css/images/calendar.gif",
		      buttonImageOnly: true,
		      buttonText     : ''
		});
	}
	
	//-------------------------------------------------------------------------
	// 請求締日 初期表示設定
	//-------------------------------------------------------------------------
	
	var seikyuShimebi = $("#seikyuShimebi").val();
	if (seikyuShimebi.length == 8) {
		// js.com.inputChkCom.js
		var rtnCode = chkItem(seikyuShimebi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			$("#seikyuShimebi").val( seikyuShimebi.substring(0, 4) + "/" +
									 seikyuShimebi.substring(4, 6) + "/" +
									 seikyuShimebi.substring(6) );
		}
	}
	
	//-------------------------------------------------------------------------
	// focus イベント
	//-------------------------------------------------------------------------
	
	
	$("#seikyuShimebi").focus(function() {
		var seikyuShimebi = $("#seikyuShimebi").val().replace(/\//g, "");
		// js.com.inputChkCom.js
		var rtnCode = chkItem(seikyuShimebi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			// エラーなし
			$("#seikyuShimebi").val(seikyuShimebi);
		}
		// 請求締日.選択
		$("#seikyuShimebi").select();
	});
	
	
	//-------------------------------------------------------------------------
	// blur イベント
	//-------------------------------------------------------------------------
	
	
	$("#seikyuShimebi").blur(function() {
		var seikyuShimebi = $("#seikyuShimebi").val().replace(/\//g, "");
		// js.com.inputChkCom.js
		var rtnCode = chkItem(seikyuShimebi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			// エラーなし
			$("#seikyuShimebi").val( seikyuShimebi.substring(0, 4) + "/" +
					 				 seikyuShimebi.substring(4, 6) + "/" +
					 				 seikyuShimebi.substring(6) );
			$("#seikyuShimebi").removeClass('form-error-field');
			$("#txtMess").html("");
		}
	});
	
	/**************************************************************************
	 * POST ボタン
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// 表示ボタン
	//-------------------------------------------------------------------------
	
	$("#btnSearch").bind("click", function() {
		$("#btnName").val("btnSearch");
		$("#FormSei0102d01").submit();
	});
	
	//-------------------------------------------------------------------------
	// 実行ボタン
	//-------------------------------------------------------------------------
	
	$("#btnProc").bind("click", function() {
		// 選択チェックボックス確認
		if (!isSeikyusakiSelected()) {
			$("#txtMess").html($("#COM008-E").val().replace("{1}", "請求先"));
			$("#messError").removeClass("messError_info").removeClass("messError_warning").addClass("messError_error");
			return;
		}
		
		// ボタン無効 js.common.js
		disableButton("btnProc");
		disableButton("btnPrintNohinMeisai");
		disableButton("btnRePrintSeikyusho");
		disableButton("btnClear");
		
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "実行"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnProc");
				$("#FormSei0102d01").submit();
			} else {
				
				// ボタン有効 js.common.js
				enableButton("btnProc");
				enableButton("btnPrintNohinMeisai");
				enableButton("btnRePrintSeikyusho");
				enableButton("btnClear");
				
			}
		});
	});
	
	//-------------------------------------------------------------------------
	// 納品明細書印刷ボタン
	//-------------------------------------------------------------------------
	
	$("#btnPrintNohinMeisai").bind("click", function() {
		// 選択チェックボックス確認
		if (!isSeikyusakiSelected()) {
			$("#txtMess").html($("#COM008-E").val().replace("{1}", "請求先"));
			$("#messError").removeClass("messError_info").removeClass("messError_warning").addClass("messError_error");
			return;
		}
		
		// ボタン無効 js.common.js
		disableButton("btnProc");
		disableButton("btnPrintNohinMeisai");
		disableButton("btnRePrintSeikyusho");
		disableButton("btnClear");
		
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "印刷"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnPrintNohinMeisai");
				$("#FormSei0102d01").submit();
			} else {
				
				// ボタン有効 js.common.js
				enableButton("btnProc");
				enableButton("btnPrintNohinMeisai");
				enableButton("btnRePrintSeikyusho");
				enableButton("btnClear");
				
			}
		});
	});
	
	//-------------------------------------------------------------------------
	// 再印刷ボタン
	//-------------------------------------------------------------------------
	
	$("#btnRePrintSeikyusho").bind("click", function() {
		// 選択チェックボックス確認
		if (!isSeikyusakiSelected()) {
			$("#txtMess").html($("#COM008-E").val().replace("{1}", "請求先"));
			$("#messError").removeClass("messError_info").removeClass("messError_warning").addClass("messError_error");
			return;
		}
		
		// ボタン無効 js.common.js
		disableButton("btnProc");
		disableButton("btnPrintNohinMeisai");
		disableButton("btnRePrintSeikyusho");
		disableButton("btnClear");
		
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "再印刷"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnRePrintSeikyusho");
				$("#FormSei0102d01").submit();
			} else {
				
				// ボタン有効 js.common.js
				enableButton("btnProc");
				enableButton("btnPrintNohinMeisai");
				enableButton("btnRePrintSeikyusho");
				enableButton("btnClear");
				
			}
		});
	});
	
	//-------------------------------------------------------------------------
	// クリアボタン
	//-------------------------------------------------------------------------
	
	$("#btnClear").bind("click", function() {
		
		// ボタン無効 js.common.js
		disableButton("btnProc");
		disableButton("btnPrintNohinMeisai");
		disableButton("btnRePrintSeikyusho");
		disableButton("btnClear");
		
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "クリア"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnClear");
				$("#FormSei0102d01").submit();
			} else {
				
				var notFoundFlag = $("#notFoundFlag").val();
				if (notFoundFlag != "true") {
					// ボタン有効 js.common.js
					enableButton("btnProc");
					enableButton("btnPrintNohinMeisai");
					enableButton("btnRePrintSeikyusho");
				}
				enableButton("btnClear");
				
			}
		});
	});
	
	//-------------------------------------------------------------------------
	// 請求送信ボタン
	//-------------------------------------------------------------------------
	
	$("button[name='seikyu_send_button']").bind("click", function() {
		var obj = this;
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "請求データ送信"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnSeikyuSend");
				$("#dataSendSeikyuId").val(obj.value);
				$("#dataSendSoshinId").val($(obj).attr("data-soshin-id"));
				
				$("#FormSei0102d01").submit();
			}
		});
	});
	
	/**************************************************************************
	 * 得意先検索ダイアログ
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// 検索ボタン
	//-------------------------------------------------------------------------
	
	$("#btnSearchCustomer").on("click", function() {
		
		var jigyoCode = $("#loginJigyoshoCd").val(); // 事業所コード
		var custCode = $("#seikyusakiCd").val(); // 得意先コード
		
		if ($("#sysAdminFlag").val() == "true") {
			if ($("#selectedJigyoshoCd").val()) {
				jigyoCode = $("#selectedJigyoshoCd").val();
			}
		}

		// ダイアログの表示
		showCom0102d02(jigyoCode, custCode, "", 2);
	});
	
	//-------------------------------------------------------------------------
	// 得意先検索
	//-------------------------------------------------------------------------
	
	loadDialogCom0102d02();
	
	/**************************************************************************
	 * 担当者検索ダイアログ 
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// 検索ボタン
	//-------------------------------------------------------------------------
	
	$("#btnSearchUser").on("click", function() {
		
		var jigyoCode = $("#loginJigyoshoCd").val(); // 事業所コード
		var userCode = $("#jimuTantoshaCd").val(); // 得意先コード
		
		if ($("#sysAdminFlag").val() == "true") {
			if ($("#selectedJigyoshoCd").val()) {
				jigyoCode = $("#selectedJigyoshoCd").val();
			}
		}

		// ダイアログの表示
		showCom0102d05(userCode, "", jigyoCode);
	});
	
	//-------------------------------------------------------------------------
	// 担当者検索
	//-------------------------------------------------------------------------
	
	loadDialogCom0102d05();
	
	/**************************************************************************
	 * その他
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// Enter key
	//-------------------------------------------------------------------------
	
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});	
	
	//-------------------------------------------------------------------------
	// 選択チェックボックス
	//-------------------------------------------------------------------------
	
	$("#selectCheckBox").bind("change", function() {
		if ($(this).is(':checked')) {
			$("input[type='checkbox'][name$='.checkBoxFlag']").prop("checked",true);
		} else {
			$("input[type='checkbox'][name$='.checkBoxFlag']").prop("checked",false);
		}
	});
	
});

/**
 * 選択チェックボックスのチェック判定
 *
 * @param
 * @return ひとつでもチェックされていれば true
 */
function isSeikyusakiSelected() {
	var isChecked = $("input[type='checkbox'][name$='.checkBoxFlag']").is(':checked');
	return isChecked;
}

/**
 * 得意先コード検索画面callback関数
 * 
 * @param data 得意先マスタ情報
 */
function getCom0102d02(data) {
	var mstCustomer = data[0];
	$("#seikyusakiCd").val(mstCustomer.custCode);
	$("#dispSeikyusakiName").text(mstCustomer.custNmR);
	$("#seikyusakiName").val(mstCustomer.custNmR);
}

/**
 * 担当者コード検索画面callback関数
 * 
 * @param data 担当者マスタ情報
 */
function getCom0102d05(data) {
	var mstUser = data[0];
	$("#jimuTantoshaCd").val(mstUser.userCode);
	$("#dispJimuTantoshaName").text(mstUser.userNm);
	$("#jimuTantoshaName").val(mstUser.userNm);
}
