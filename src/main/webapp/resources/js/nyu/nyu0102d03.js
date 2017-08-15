/**
 * ファイル名:nyu0102d03.js
 * 概要:都度請求未回収設定
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
	// 表示ボタン
	//-------------------------------------------------------------------------
	
	$("#btnSearch").bind("click", function() {
		$("#btnName").val("btnSearch");
		$("#FormNyu0102d03").submit();
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
		$("#btnName").val("btnProc");
		$("#FormNyu0102d03").submit();
	});
	
	//-------------------------------------------------------------------------
	// クリアボタン
	//-------------------------------------------------------------------------
	
	$("#btnClear").bind("click", function() {
		$("#btnName").val("btnClear");
		$("#FormNyu0102d03").submit();
	});
	
	//-------------------------------------------------------------------------
	// 戻るボタン
	//-------------------------------------------------------------------------
	
	$("#btnBack").bind("click", function() {
		var pathname = location.pathname;
		var dirs = pathname.split("/");
		location.href = "/" + dirs[1] + "/nyu/NYU01-02D00/";
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
