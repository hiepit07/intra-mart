/**
 * ファイル名:sei0103d01.js
 * 概要:臨時請求締め処理
 * 
 * 作成者:都築電気
 * 作成日:2015/11/27
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/11/27 1.00                  ACT)ANZAI       新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

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
		$("#seikyusakiCd").focus();
	}
	
	/**************************************************************************
	 * 請求締日（臨時締日）
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
	 * 入金予定日
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// 入金予定日 DatePicker
	//-------------------------------------------------------------------------
	
	var notFoundFlag = $("#notFoundFlag").val();
	if (notFoundFlag == "true") {
		var pathname = location.pathname;
		var dirs = pathname.split("/");
		// datepicker 設定
		$("#nyukinYoteibi").datepicker({
		      showOn         : "button",
		      buttonImage    : "/" + dirs[1] + "/resources/css/images/calendar.gif",
		      buttonImageOnly: true,
		      buttonText     : ''
		});
	}
	
	//-------------------------------------------------------------------------
	// 入金予定日 初期表示設定
	//-------------------------------------------------------------------------
	
	var nyukinYoteibi = $("#nyukinYoteibi").val();
	if (nyukinYoteibi.length == 8) {
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			$("#nyukinYoteibi").val( nyukinYoteibi.substring(0, 4) + "/" +
									 nyukinYoteibi.substring(4, 6) + "/" +
									 nyukinYoteibi.substring(6) );
		}
	}
	
	//-------------------------------------------------------------------------
	// focus イベント
	//-------------------------------------------------------------------------
	
	
	$("#nyukinYoteibi").focus(function() {
		var nyukinYoteibi = $("#nyukinYoteibi").val().replace(/\//g, "");
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			// エラーなし
			$("#nyukinYoteibi").val(nyukinYoteibi);
		}
		// 入金予定日.選択
		$("#nyukinYoteibi").select();
	});
	
	
	//-------------------------------------------------------------------------
	// blur イベント
	//-------------------------------------------------------------------------
	
	
	$("#nyukinYoteibi").blur(function() {
		var nyukinYoteibi = $("#nyukinYoteibi").val().replace(/\//g, "");
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			// エラーなし
			$("#nyukinYoteibi").val( nyukinYoteibi.substring(0, 4) + "/" +
					 				 nyukinYoteibi.substring(4, 6) + "/" +
					 				 nyukinYoteibi.substring(6) );
			$("#nyukinYoteibi").removeClass('form-error-field');
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
		$("#FormSei0103d01").submit();
	});
	
	//-------------------------------------------------------------------------
	// 実行ボタン
	//-------------------------------------------------------------------------
	
	$("#btnProc").bind("click", function() {
		
		// ボタン無効 js.common.js
		disableButton("btnProc");
		disableButton("btnClear");
		
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "実行"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnProc");
				$("#FormSei0103d01").submit();
			} else {
				
				// ボタン有効 js.common.js
				enableButton("btnProc");
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
		disableButton("btnClear");
		
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "クリア"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// はい
				$("#btnName").val("btnClear");
				$("#FormSei0103d01").submit();
			} else {
				
				var notFoundFlag = $("#notFoundFlag").val();
				if (notFoundFlag != "true") {
					// ボタン有効 js.common.js
					enableButton("btnProc");
				}
				// ボタン有効 js.common.js
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
				
				$("#FormSei0103d01").submit();
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
	
});

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
