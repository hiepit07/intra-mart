/**
 * ファイル名:nyu0106d01.js
 * 概要:会計入金実績作成
 * 
 * 作成者:都築電気
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00                  ACT)ANZAI       新規開発
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
	// 実行ボタン
	//-------------------------------------------------------------------------
	
	$("#btnProc").bind("click", function() {
		$("#btnName").val("btnProc");
		$("#FormNyu0106d01").submit();
	});
	
	//-------------------------------------------------------------------------
	// CSV出力ボタン
	//-------------------------------------------------------------------------
	
	$("button[name='btnCsv']").bind("click", function() {
		$("#btnName").val("btnCsv");
		$("#csvRenkeiId").val(this.value);
		$("#csvShubetsu").val($(this).attr("data-shubetsu"));
		$("#csvCreateDate").val($(this).attr("data-create-date"));
		$("#csvCreateTime").val($(this).attr("data-create-time"));
		// CSV出力
		exportCSV();
	});
	
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
	
	/**
	 * CSV出力
	 */
	function exportCSV() {
		$.ajax({
			type : "POST",
			url : "exportCsv",
			async : false,
			data : $("form").serialize(),
			// 成功
			success : function(retVal) {
				console.log("success");
				
				var data = JSON.parse(retVal);
				console.log(retVal);
				console.log(data);
				
				if (data.message != null) {
					$("#txtMess").html(data.message);
					
				} else {
					var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
					var path = rootPath + data.filePath;
					window.open(path);
				}
			},
			// エラー
			error : function(e) {
				console.log("error");
			},
			// 完了
			complete : function(jqXHR, textStatus) {
				console.log("complete");
			}
		});
	}
	
});
