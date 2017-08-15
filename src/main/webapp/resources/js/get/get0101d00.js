/**
 * ファイル名:get0101d00.js
 * 概要:担当者マスタ一覧画面
 * 
 * 作成者:ABV）HiepTruong
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00     ABV)TuanNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/
jQuery(document).ready(function($) {
	condition_buttonVal="false";
	
	// common use
	var checkError = function(returnData){
		var data = JSON.parse(returnData);
		if(data.hasOwnProperty('haitaDate')){
			$("#haitaDate").html(data['haitaDate']);
		}
		if(data.hasOwnProperty('haitaTime')){
			$("#haitaTime").html(data['haitaTime']);
		}
		if(data.hasOwnProperty('Error')){
			$("#txtMess").html(data['Error']);
			$("#txtMess").removeClass("display_none");
			removeInfoClass();
		} else if (data.hasOwnProperty('Info')) {
			$("#txtMess").html(data['Info']);
			$("#txtMess").removeClass("display_none");
			addInfoClass();
		} else{
			$("#txtMess").addClass("display_none");
		}
	}
	var eventClick = function(){
		$("#btnConfirm").on("click", function(){ 
			disableButton("btnConfirm");
			jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
				// ダイアログを閉じ、処理続行
				if (retVal) {
					var officeCodeWK = $("#ddlJigyouSho").val();
					$.ajax({
						type : "POST",
						url : "get0101D00confirm",
						async : false,
						data : {
							officeCode : officeCodeWK,
							businessDate : $("#businessDateID").val(),
							thisMonthFixWK: $("#thisMonthFixWK").html(),
							haitaDate: $("#haitaDate").html(),
							haitaTime:$("#haitaTime").html()
						},
						success : function(returnData) {
							checkError(returnData);
							var data = JSON.parse(returnData);
							//$(".resetafterchange").html("");
							if(data.hasOwnProperty('isEnableBtnConfirm')  && data['isEnableBtnConfirm'] == 'true'){
								enableButton("btnConfirm");
							}else{
								disableButton("btnConfirm");
							}
							if(data.hasOwnProperty('isSuccess') && data['isSuccess'] == 'true'){
								if(data.hasOwnProperty('lblCurrAccMonth')){
									$("#thisMonthFixWK").html(data['lblCurrAccMonth']);
								}
								if(data.hasOwnProperty('lblProcEndTime')){
									$("#lblProcEndTime").html(data['lblProcEndTime']);
								}
								if(data.hasOwnProperty('lblConfirmAccMonth')){
									$("#lblConfirmAccMonth").html(data['lblConfirmAccMonth']);
								}
								if(data.hasOwnProperty('lblProcStartTime')){
									$("#lblProcStartTime").html(data['lblProcStartTime']);
								}
							}
							if(data.hasOwnProperty('setFocus')){
								$(data['setFocus']).focus();
							}
						},
						error : function(e) {
							alert(e.message);
						},
						complete : function(jqXHR, textStatus) {
						}
					});
				}else{
					// ダイアログを閉じ、処理終了
					enableButton("btnConfirm");
				}
			});
		});
	}
	
	var eventChange = function(){
		$("#ddlJigyouSho").change(function(){
			var officeCode = $(this).val();

			
			$.ajax({
				type : "POST",
				url : "get0101D00changeOfficeCode",
				async : false,
				data : {
					officeCode : officeCode,
					haitaDate: $("#haitaDate").html(),
					haitaTime: $("#haitaTime").html()
				},
				success : function(returnData) {
					checkError(returnData);
					var data = JSON.parse(returnData);
					$(".resetafterchange").html("");
						if(data.hasOwnProperty('lblCurrAccMonth')){
							$("#thisMonthFixWK").html(data['lblCurrAccMonth']);
						}
						if(data.hasOwnProperty('lblProcEndTime') ){
							$("#lblProcEndTime").html(data['lblProcEndTime']);
						}
						if(data.hasOwnProperty('lblConfirmAccMonth')){
							$("#lblConfirmAccMonth").html(data['lblConfirmAccMonth']);
						}
						if(data.hasOwnProperty('lblProcStartTime')){
							$("#lblProcStartTime").html(data['lblProcStartTime']);
						}
					if(data.hasOwnProperty('isEnableBtnConfirm')  && data['isEnableBtnConfirm'] == 'true'){
						enableButton("btnConfirm");
					}else{
						disableButton("btnConfirm");
					}
					// [画面]確定ボタンへフォーカスをセットする
					if(data.hasOwnProperty('setFocus')){
						$(data['setFocus']).focus();
					}
				},
				error : function(e) {
					alert(e.message);
				},
				complete : function(jqXHR, textStatus) {
				}
			});
		});
	}
	
	
	// 5 - 終了処理・例外処理
	var initFocus = function(){
		// システム管理者フラグを取得する
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
	
		// システム管理者フラグ ＝ '1'（システム管理者）の場合
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlJigyouSho").focus();
		}
		// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
		else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			$("#btnConfirm").focus();
		}
	}
	var init = function(){
 		if($('#isEnableBtnConfirm').html() == 'true'){
			enableButton("btnConfirm");
 		}else{
			disableButton("btnConfirm");
 		}
	}
	// initialize focus
	initFocus();
	init();
	eventClick();
	eventChange();
});


