/**
 * ファイル名:nyu0102d01.js
 * 概要:締め請求未回収設定
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
// 明細.No メニュー制御フラグ
var isSubMenuShow = false;
// 明細.No メニュー表示位置
var currentRowIdx = "";

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
		$("#FormNyu0102d02").submit();
	});
	
	//-------------------------------------------------------------------------
	// 選択ボタン
	//-------------------------------------------------------------------------
	
	$("#selectSubMenu").bind("click", function() {
		$("#btnName").val("btnSelect");
		$("#selectIdx").val(currentRowIdx);
		$("#FormNyu0102d02").submit();
	});
	
	//-------------------------------------------------------------------------
	// クリアボタン
	//-------------------------------------------------------------------------
	
	$("#btnClear").bind("click", function() {
		$("#btnName").val("btnClear");
		$("#FormNyu0102d02").submit();
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
		$("[id^='nyukinYoteibi']").datepicker({
		      showOn         : "button",
		      buttonImage    : "/" + dirs[1] + "/resources/css/images/calendar.gif",
		      buttonImageOnly: true,
		      buttonText     : ''
		});
	}
	
	//-------------------------------------------------------------------------
	// 入金予定日 初期表示設定
	//-------------------------------------------------------------------------
	
	var nyukinYoteibiFrom = $("#nyukinYoteibiFrom").val();
	if (nyukinYoteibiFrom.length == 8) {
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibiFrom, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			$("#nyukinYoteibiFrom").val( nyukinYoteibiFrom.substring(0, 4) + "/" +
										 nyukinYoteibiFrom.substring(4, 6) + "/" +
										 nyukinYoteibiFrom.substring(6) );
		}
	}
	
	var nyukinYoteibiTo = $("#nyukinYoteibiTo").val();
	if (nyukinYoteibiTo.length == 8) {
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibiTo, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			$("#nyukinYoteibiTo").val( nyukinYoteibiTo.substring(0, 4) + "/" +
										nyukinYoteibiTo.substring(4, 6) + "/" +
										nyukinYoteibiTo.substring(6) );
		}
	}
	
	//-------------------------------------------------------------------------
	// focus イベント
	//-------------------------------------------------------------------------
	
	
	$("[id^='nyukinYoteibi']").focus(function() {
		var nyukinYoteibi = $(this).val().replace(/\//g, "");
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			// エラーなし
			$(this).val(nyukinYoteibi);
		}
		// 入金予定日.選択
		$(this).select();
	});
	
	
	//-------------------------------------------------------------------------
	// blur イベント
	//-------------------------------------------------------------------------
	
	
	$("[id^='nyukinYoteibi']").blur(function() {
		var nyukinYoteibi = $(this).val().replace(/\//g, "");
		// js.com.inputChkCom.js
		var rtnCode = chkItem(nyukinYoteibi, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
		if (rtnCode == null) {
			// エラーなし
			$(this).val( nyukinYoteibi.substring(0, 4) + "/" +
					 	 nyukinYoteibi.substring(4, 6) + "/" +
					 	 nyukinYoteibi.substring(6) );
			$(this).removeClass('form-error-field');
			$("#txtMess").html("");
		}
	});
	
	/**************************************************************************
	 * 明細.No イベント
	 *************************************************************************/
	
	//-------------------------------------------------------------------------
	// マウスオーバー イベント
	//-------------------------------------------------------------------------
	
	$(document).on("mouseover", ".contextMenu", function(){
		
		// 表示を変更
		$(this).find("span").text("▼");
		
		if ($(this).attr("id") != currentRowIdx) {
			
			// メニューを表示している行以外でのイベント発生
			
			if (isSubMenuShow) {
				
				// 現在、メニューを表示している
				
				// メニューを非表示
				$("#tableMenu").fadeOut("fast");
				isSubMenuShow = false;
			}
			// 表示を番号に戻す
			$("#" + currentRowIdx).find("span").text(currentRowIdx);
			currentRowIdx = $(this).attr("id");
		}
	});
	
	//-------------------------------------------------------------------------
	// クリックイベント
	//-------------------------------------------------------------------------
	
	$(document).on("click", ".contextMenu", function(e){
		
		var x = e.pageX;
		var y = e.pageY;
		
		// メニューを表示
		$("#tableMenu").css("position", "absolute").css("left", x + "px").css("top", y + "px").fadeIn("fast");
		isSubMenuShow = true;
	});
	
	
	//-------------------------------------------------------------------------
	// マウスオーバー
	//-------------------------------------------------------------------------
	
	$(document).on("mouseover", "td", function(){
		
		if (!$(this).hasClass("contextMenu")) {
			// No列以外でイベント発生
			if (!isSubMenuShow) {
				// 表示を番号に戻す
				$("#" + currentRowIdx).find("span").text(currentRowIdx);
			}
		}
		
	});
	
	//-------------------------------------------------------------------------
	// マウスオーバー／マウスアウト
	//-------------------------------------------------------------------------
	
	$(".sub-menu a").bind("mouseover", function() {
		// サブメニューでのマウスオーバー
		$(this).css("background-color", "#003366").css("color", "#FFFFFF");
		
	}).bind("mouseout", function() {
		// サブメニューでのマウスアウト
		$(this).css("background-color", "#FFFFFF").css("color", "#003366");
	});
	
	//-------------------------------------------------------------------------
	// クリックイベント
	//-------------------------------------------------------------------------
	
	$("#tbl_tbody").bind("click",function(e) {
		
		// テーブル内でのクリックイベント
		
		var container1 = $(".contextMenu");

		if (    !container1.is(e.target)
			 && container1.has(e.target).length === 0
			 && $("#tableMenu").is(":visible")) {
			
			// メニューを非表示
			$("#tableMenu").fadeOut("fast");
			// 表示を番号に戻す
			$("#" + currentRowIdx).find("span").text(currentRowIdx);
			isSubMenuShow = false;
		}
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
	
});
