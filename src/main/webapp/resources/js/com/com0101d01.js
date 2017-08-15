/**
 * パッケージ名: ファイル名: com0101d01.js
 * 
 * 作成者:アクトブレーンベトナム 作成日:2015/09/15
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/15 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

jQuery(document).ready(function($) {
	var isClickButton = false;
	// エンターキー押下時の遷移
	$(document).on("keydown", "input[type=text],input[type=password]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKeyDown(e, $(this));
		}
	});
	
	//check focus out
	$(document).on('focusout', '#userid', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#errMsg").html('');
				$('#userid').removeClass('form-error-field');
				var errorID = chkItem($('#userid').val(), false, TYPE_NUM_ALPHA, null, null);
				if (errorID != null){
					$("#errMsg").html($("#" + errorID).val().replace("{1}", "担当者コード"));
					$("#userid").addClass("form-error-field");
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#password', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#errMsg").html('');
				$('#password').removeClass('form-error-field');
				var errorID = chkItem($('#password').val(), false, TYPE_NUM_ALPHA, null, null);
				if (errorID != null){
					$("#errMsg").html($("#" + errorID).val().replace("{1}", "パスワード"));
					$("#password").addClass("form-error-field");
					return;
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
		
	});
	
	$("#btnLogin").bind("click",function(e) {
		isClickButton = true;
		// エラーメッセージが表示されていた場合、非表示にする
		$("#errMsg").html('');
		disableButton("btnLogin")
		$("#btnLoginSubmit").trigger("click");
	});
	// [画面]パスワードへフォーカスをセットする
	if ($("#focusPass").val() == "true") {
		$("#password").select();
		$("#password").focus();
		$("#focusPass").val("false");
		if($("#errMsg").html() != "") {
			$("#password").addClass("form-error-field");
		}
	} else {
		// [画面]担当者コードへフォーカスをセットする
		$("#userid").select();
		$("#userid").focus();
		
		if($("#errMsg").html() != "") {
			$("#userid").addClass("form-error-field");
		}
	}
	$.jStorage.deleteKey('menu');
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
	if(tabindex == 3) {
		tabindex = 1;
	}
	$('[tabindex=' + tabindex + ']').focus();
	$('[tabindex=' + tabindex + ']').select();
}