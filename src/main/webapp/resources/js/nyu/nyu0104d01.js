/**
 * ファイル名:nyu0104d01.js
 * 概要:入金予定一覧
 * 
 * 作成者:ABV）NhungMa
 * 作成日:2015/12/16
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/12/16 1.00                  ABV)NhungMa       新規開発
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
	$(".txtDate").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true
	});
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}
	// 画面表示
	showControl();
	
	// 終了処理・例外処理
	setFocus();
	
	// リスト部、フッタ部は非表示とする
	$(".div_zentai").addClass("display_none");
	$(".footer_area").addClass("display_none");
	
	// 入金予定日 Focus IN
	$("#txtPaymentDateFrom").bind("focus", function(){
		var txtDateFrom = $(this).val().replace(/\//g, '');
		$(this).val(txtDateFrom);
	});
	$("#txtPaymentDateTo").bind("focus", function(){
		var txtDateTo = $(this).val().replace(/\//g, '');
		$(this).val(txtDateTo);
	});
	
	// 入金予定日 Focus OUT
	$("#txtPaymentDateFrom").bind("focusout", function() {
		var txtDateFrom = $(this).val().replace(/\//g, '');
		if (txtDateFrom != "") {
			var yearStart = txtDateFrom.substring(0, 4);
			var monthStart = txtDateFrom.substring(4, 6);
			var dayStart = txtDateFrom.substring(6);
			var formatDateFrom = yearStart + "/" + monthStart + "/" + dayStart;
			$(this).val(formatDateFrom);
		}
	});
	
	$("#txtPaymentDateTo").bind("focusout", function() {
		var txtDateTo = $(this).val().replace(/\//g, '');
		if (txtDateTo != "") {
			var yearStart = txtDateTo.substring(0, 4);
			var monthStart = txtDateTo.substring(4, 6);
			var dayStart = txtDateTo.substring(6);
			var formatDateTo = yearStart + "/" + monthStart + "/" + dayStart;
			$(this).val(formatDateTo);
		}
	});
	
	$("#btnShow").bind("click", function(){
		var sysAdminFlg = $("#sysAdminFlag").val();
		if(sysAdminFlg == SYS_ADMIN_FLAG_ADMIN) {
			console.log($("#ddlJigyoCode").val());
			$("#jigyoshoCodeWk").val($("#ddlJigyoCode").val());
		} else if (sysAdminFlg == SYS_ADMIN_FLAG_USER) {
			$("#jigyoshoCodeWk").val($("#loginJigyoshoCode").val());
		}
		if($("#txtPaymentDateFrom").val() == "" && $("#txtPaymentDateTo").val() == "") {
			$("#txtMess").html($("#COM006-E").val());
		}
		$.ajax({
			type: "POST",
			url: "display",
			data:$("form").serialize(),
			async : false,
			success: function(returnJsonData) {
				
			}
		});
	});
	
	/**
	 * 所属事業所の状態の表示
	 */
	function showControl() {
		var sysAdminFlg = $("#sysAdminFlag").val();
		if (sysAdminFlg == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlJigyoCode").show();
			$("#lblJigyoCode").show();
		} else if(sysAdminFlg == SYS_ADMIN_FLAG_USER) {
			$("#ddlJigyoCode").hide()
			$("#lblJigyoCode").hide();
		}
	}
	
	/**
	 * set focus for end processing
	 */
	function setFocus() {
		var sysAdminFlgFocus = $("#sysAdminFlag").val();
		if(sysAdminFlgFocus == SYS_ADMIN_FLAG_USER) {
			$("#txtPaymentDateFrom").focus();
		} else if(sysAdminFlgFocus == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlJigyoCode").focus();
		}
	}
});
