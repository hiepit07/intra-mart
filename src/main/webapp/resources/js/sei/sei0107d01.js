/**
 * パッケージ名: ファイル名: sei0107d01.js
 * 
 * 作成者:NghiaNguyen 
 * 作成日:2015/12/15
 * 
 * 履歴 
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/15 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */


jQuery(document).ready(function($) {
	var isErrorControl = ($("#isErrorControl").val());
	if (isErrorControl == "") {
		$("#ddlClose").focus().select();
	}
	$("#ddlSumBill").bind("focusout", function() {
		var ddlSumBill = $("#ddlSumBill").val();
		if (ddlSumBill != "") {
			changeDivision();
		}
	});
	
	// 使用中止日を有効にする
	$("#txtBillMonth").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true,
	    dateFormat : 'yy/mm'
	});
});

/**
 * changeDivision.
 */
function changeDivision() {
	$.ajax({
		type: "POST",
		url: rootPath+"/sei/SEI01-07D00/changeDivision",
		data: $("form").serialize(),
		async : false,
		success: function(returnData) {
			var Data = returnData;
			var htmlRow = "<option></option>";
			for (var i =0; i< Data.length;i++) {
				htmlRow +="<option value='"+Data[i].shiMeBi+"'>"+Data[i].shiMeBi+"</option>";
			}
			$("#ddlClose").html(htmlRow);
		},
		error : function(e) {
			
		},
		complete : function(jqXHR, textStatus) {
			
		}
	});
}



