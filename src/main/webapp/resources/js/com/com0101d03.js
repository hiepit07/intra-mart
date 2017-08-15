/**
 * ファイル名: com0101d03.js
 * 
 * 作成者:アクトブレーンベトナム 
 * 作成日:2015/09/21
 * 
 * 履歴
 *  -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

var DELIMITER = "##";
jQuery(document).ready(function($) {
	var isClickButton = false;
	var strError = $("#lstErrorID").val();
	if (strError != null && strError != "") {
		var lstError = strError.split(DELIMITER);
		$("#" + lstError[0]).focus().select();
		addClassErrorToControl(lstError);
	} else {
		$("#password").focus();
	}
	// 変更する
	$("#btnChange").bind("click",function(e) {
		isClickButton = true;
		// エラーメッセージが表示されていた場合、非表示にする
		$("#txtMess").text('');
		$("#btnChangeSubmit").trigger("click");
		disableButton("btnChange");
	});
	// エンターキー押下時の遷移
	$(document).on("keydown", "input[type=text],input[type=password]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKeyDown(e, $(this));
		}
	});
	
	//check focus out
	$(document).on('focusout', '#password', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#txtMess").text('');
				$('#password').removeClass('form-error-field');
				var errorID = chkItem($('#password').val(), true, TYPE_NUM_ALPHA, null, null);
				if (errorID != null){
					$("#txtMess").text($("#" + errorID).val().replace("{1}", "現在のパスワード"));
					$("#password").addClass("form-error-field");
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#newPass', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#txtMess").text('');
				$('#newPass').removeClass('form-error-field');
				var errorID = chkItem($('#newPass').val(), true, TYPE_NUM_ALPHA, null, null);
				if (errorID != null){
					$("#txtMess").text($("#" + errorID).val().replace("{1}", "新しいパスワード"));
					$("#newPass").addClass("form-error-field");
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#reEnterNewPass', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#txtMess").html('');
				$('#reEnterNewPass').removeClass('form-error-field');
				var errorID = chkItem($('#reEnterNewPass').val(), true, TYPE_NUM_ALPHA, null, null);
				if (errorID != null){
					$("#txtMess").text($("#" + errorID).val().replace("{1}", "新しいパスワード（再入力）"));
					$("#reEnterNewPass").addClass("form-error-field");
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
});

/**
 * エンターキー押下時の遷移
 * 
 * @param event
 * @param element
 */
function EnterKeyDown(event, element) {
	var tabindex = element.attr('tabindex');
	tabindex++;
	if(tabindex == 4) {
		tabindex = 1;
	}
	$('[tabindex=' + tabindex + ']').focus();
	$('[tabindex=' + tabindex + ']').select();
}