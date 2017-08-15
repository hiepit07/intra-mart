/**
 * ファイル名:nyu0102d01.js
 * 概要:入金登録
 * 
 * 作成者:都築電気
 * 作成日:2015/12/01
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/12/01 1.00                  ACT)ANZAI       新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

/*
 * 処理区分
 */
var SHORI_KBN_SHONIN    = "1";  // 承認
var SHORI_KBN_SHINKI    = "2";  // 新規登録
var SHORI_KBN_TEISEI    = "3";  // 訂正
var SHORI_KBN_TORIKESHI = "4";  // 取消
var SHORI_KBN_SHOKAI    = "5";  // 照会

// Controlエラーの受信デリミター
var DELIMITER = "##";

/**
 * DOCUMENT READY イベント
 * 
 */
jQuery(document).ready(function($) {
	
	//-------------------------------------------------------------------------
	// 初期表示
	//-------------------------------------------------------------------------
	
	var showFlag = $("#showFlag").val();
	if (showFlag == "false") {
		var shoriKbn = $("#shoriKbn").val();
		if (shoriKbn == SHORI_KBN_SHINKI) {
			// 新規登録
			$("#divNyukinDempyoNo").val();
			$("#divNyukinDempyoNo").hide();
			$("#divHeaderButtons").show();
		} else {
			// 新規登録以外
			$("#divNyukinDempyoNo").show();
			$("#divHeaderButtons").hide();
		}
	}
	
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
	// 処理区分
	//-------------------------------------------------------------------------
	
	$("input[name='shoriKbn']").change(function() {
		var radioVal = $(this).val();
		if (radioVal == SHORI_KBN_SHINKI) {
			// 新規登録
			$("#divNyukinDempyoNo").hide();
			$("#divHeaderButtons").show();
		} else {
			// 新規登録以外
			$("#divNyukinDempyoNo").show();
			$("#divHeaderButtons").hide();
		}
	});
	
	//-------------------------------------------------------------------------
	// 締め請求ボタン
	//-------------------------------------------------------------------------
	
	$("#btnShimeSeikyu").bind("click", function() {
		var pathname = location.pathname;
		var dirs = pathname.split("/");
		location.href = "/" + dirs[1] + "/nyu/NYU01-02D02/";
		
	});
	
	//-------------------------------------------------------------------------
	// 都度請求ボタン
	//-------------------------------------------------------------------------
	
	$("#btnTsudoSeikyu").bind("click", function() {
		var pathname = location.pathname;
		var dirs = pathname.split("/");
		location.href = "/" + dirs[1] + "/nyu/NYU01-02D03/";
	});
	
	//-------------------------------------------------------------------------
	// 入金伝票No
	//-------------------------------------------------------------------------
	
	// Enter Key
	$("#nyukinDempyoNo").on("keydown", function(e) {
		var nyukinDempyoNo = $(this).val();
		var shoriKbn = $("input[name='shoriKbn']:checked").val();
		if (e.keyCode == 13) {
			var pathname = location.pathname;
			var dirs = pathname.split("/");
			location.href = "/" + dirs[1] + "/nyu/NYU01-02D00/show?shoriKbn=" + shoriKbn + "&nyukinDempyoNo=" + nyukinDempyoNo;
			return false;
		}
	});
	
	// Focus Out
	$("#nyukinDempyoNo").blur(function() {
		var nyukinDempyoNo = $(this).val();
		var shoriKbn = $("input[name='shoriKbn']:checked").val();
		var pathname = location.pathname;
		var dirs = pathname.split("/");
		location.href = "/" + dirs[1] + "/nyu/NYU01-02D00/show?shoriKbn=" + shoriKbn + "&nyukinDempyoNo=" + nyukinDempyoNo;
	});
	
	/**************************************************************************
	 * その他
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// エラー時コントロール制御
	//-------------------------------------------------------------------------
	
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		// js.com.systemCommon.js
		errorControl();
	}
	
	//-------------------------------------------------------------------------
	// Enter key
	//-------------------------------------------------------------------------
	
	$(document).on("keydown", "input[type=text][id!=nyukinDempyoNo]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});
	
});
