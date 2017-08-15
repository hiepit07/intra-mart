/**
 * ファイル名: mst0199d01.js
 * 
 * 作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran　新規作成
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
/**
 * Declare global var
 */
var currentShowingColId = "";
var isTableMenuShowing = false;
var strSelectedGlCode="";
jQuery(document).ready(function($) {
	var isClickButton = false;
	var length = $('#category').children('option').length;
	// カテゴリ情報取得エラー処理
	if(length <= 0) {
		errorControl();
	} else {
		// 画面表示
		search(false);
	}
	// Evt2: 検索ボタン
	$("#btnSearch").bind("click", function() {
		isClickButton = true;
		disableButton("btnSearch");
		search(true);
	});
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});
	// Evt3: 条件クリアボタン
	$("#btnConditionClear").bind("click", function() {
		$("#txtMess").text('');
		$('#kbNm').removeClass('form-error-field');
		// [関数]検索条件入力エリア初期化
		$('#kbNm').val('');
		$('#category').prop('selectedIndex',0);
		// [画面]分類にフォーカスをセットする
		$('#category').focus().select();
	});
	
	// CSVボタン
	$("#btnCsv").bind("click",function() {
		isClickButton = true;
		disableButton("btnCsv");
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				exportCSV();
			} else {
				enableButton("btnCsv");
				isClickButton = true;
			}
		});
	});
	
	//▼処理	
	$(document).on("mouseover", ".contextMenu", function(){
		// change text to "▼" when user is hovering on a No column
		$(this).find("span").text("▼");
		// if menu is showing, hide menu when user is hovering on other No columns
		if (isTableMenuShowing) {
			// only hide menu if user is hovering on other No columns, not the column that is showing menu
			if ($(this).attr("id") != currentShowingColId) {
				// hide menu
				$("#tableMenu").fadeOut("fast");
				// switch flag value
				isTableMenuShowing = false;
				// change the text back from "▼" to number
				$("#" + currentShowingColId).find("span").text(currentShowingColId);
			}
		}
		// is menu is not showing, change the text back from "▼" to number
		if ($(this).attr("id") != currentShowingColId) {
			$("#" + currentShowingColId).find("span").text(currentShowingColId);
			currentShowingColId = $(this).attr("id");
		}
	});
	$(document).on("click", ".contextMenu", function(e){
		// get current coordinates of mouse
		var x = e.pageX;
		var y = e.pageY;
		// show menu at current mouse's coordinates
		$("#tableMenu").css("position", "absolute").css("left", x + "px").css("top", y + "px").fadeIn("fast");
		// switch flag value
		isTableMenuShowing = true;
		strSelectedGlCode = $(this).attr("name");
	});
	
	$(document).on("mouseover", "td", function(){
		if (!$(this).hasClass("contextMenu")) {
			// if menu is not showing, change the text back from "▼" to
			// number
			if (!isTableMenuShowing) {
				$("#" + currentShowingColId).find("span").text(currentShowingColId);
			}
		}
		
	});
	// hover event on sub menu items
	$(".sub-menu a").bind("mouseover", function() {
		$(this).css("background-color", "#003366").css("color", "#FFFFFF");
	}).bind("mouseout", function() {
		$(this).css("background-color", "#FFFFFF").css("color", "#003366");
	});
	
	$("#tbl_tbody").bind("click",function(e) {
		var container1 = $(".contextMenu");
		
		if (!container1.is(e.target) // if the target of the click isn't the container
			&& container1.has(e.target).length === 0 && $("#tableMenu").is(":visible")) { // nor a descendant of the container
			$("#tableMenu").fadeOut("fast");
			$("#" + currentShowingColId).find("span").text(currentShowingColId);
			isTableMenuShowing = false;
		}
	});
	//▼処理ＥＮＤ
	
	$("#viewSubMenu").bind("click", function() {
		$("#viewModeID").val(SHOUKAI_MODE);
		$("#selectGlCodeID").val(strSelectedGlCode);
		$("#view").trigger( "click" );
	});
	$("#modifySubMenu").bind("click", function() {
		$("#viewModeID").val(TEISEI_MODE);
		$("#selectGlCodeID").val(strSelectedGlCode);
		$( "#modify").trigger( "click" );
	});
	
	//check focus out
	$(document).on('focusout', '#kbNm', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#txtMess").text('');
				$('#kbNm').removeClass('form-error-field');
				var errorID = chkItem($('#kbNm').val(), false, TYPE_EM, null, null);
				if (errorID != null){
					$("#txtMess").text($("#" + errorID).val().replace("{1}", "区分名称"));
					removeInfoClass();
					$("#kbNm").addClass("form-error-field");
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
});

/**
 * 検索ボタンをクリックされた時の処理。検索条件に該当するマスタデータを一覧に表示する
 */
function search(isSearchEvent) {
	$.ajax({
		type: "POST",
		url: "search",
		async : false,
		data:$("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(returnJsonData) {
			var haveData = false;
			// （2）[変数]管理区分一覧情報格納クラス ＝ NULL の場合、警告メッセージを表示する
			if(returnJsonData.rstMst0199d01List == null) {
				if(returnJsonData.messageType == 'body') {
					$("#txtMess").html("");
					$("#tbl_tbody").html(returnJsonData.errMessage);
				} else {
					$("#tbl_tbody").html("");
					$("#txtMess").html(returnJsonData.errMessage);
				}
			// （1）[変数]管理区分一覧情報格納クラス ≠ NULL の場合、正常とする
			} else {
				$("#tbl_tbody").html("");
				$("#txtMess").html(returnJsonData.errMessage);
				$("#haitaDate").val(returnJsonData.haitaDate);
				$("#haitaTime").val(returnJsonData.haitaTime);
				drawTable(returnJsonData.rstMst0199d01List);
				haveData = true;
			}
			removeInfoClass();
			if(isSearchEvent) {
				buttonControl(haveData)
			} else {
				$('#category').focus().select();
			}
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
			enableButton("btnSearch");
			isClickButton = false;
		}
	});
}

/**
 * テーブルの表示
 * @param data　テーブルのデータ
 * @return 
 * @exception
 */
function drawTable(data) {
	var searchMax = $("#searchMax").val();
	var htmlData = "";
	//テーブルの表示	
	for (var i = 0 ; i < data.length ; i++){
		if(i < searchMax) {
			htmlData += "<tr>";
			htmlData += drawRow(i+1, data[i]);
			htmlData += "</tr>";
		}
	}
	
	$("#tbl_tbody").html(htmlData);
	var tblBodyHeight = $("#tblBody").height();
	var divBodyHeight = $("#divBody").height();
	if (tblBodyHeight > divBodyHeight) {
		$("#divBody").css("overflow-y", "scroll");
		$("#divHead").width($("#divBody").width() - 17);
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width() - 17);
		});
	} else {
		$("#divHead").width($("#divBody").width());
		$("#divBody").css("overflow-y", "hidden");
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width());
		});
	}
}

/**
 * テーブルを描く
 * @param　renban 連番
 * @param　行のデータ
 * @return 
 * @exception
 */
function drawRow(renban, dataRow) {
	var htmlRow = "";
	htmlRow += "<td id='" + renban + "' name='"+ ReplaceNullReturnBlank(dataRow.glCode) +"' class='contextMenu align_center'><span>" + renban + "</span></td>";
	htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.glCode) + "</td>";
	htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.kbNm) + "</td>";
	htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.category) +" "+ ReplaceNullReturnBlank(dataRow.target1)  + "</td>";
	htmlRow += "<td class='border_r_none'>" + ReplaceNullReturnBlank(dataRow.updUserid) + "</td>";
	htmlRow += "<td class='border_l_none'>" + ReplaceNullReturnBlank(dataRow.userNm) + "</td>";
	htmlRow += "<td class='align_center'>" + formatYmd (dataRow.updYmd) +" "+ formatTime(dataRow.updTime) + "</td>";
	return htmlRow;
}

/**
 * ボタン制御
 * 
 * @param haveData
 */
function buttonControl(haveData) {
	// [変数]管理区分一覧情報格納クラス ≠ NULL の場合、
	if(haveData) {
		enableButton("btnCsv");
	// [変数]管理区分一覧情報格納クラス ＝ NULL の場合、
	} else {
		// [画面]CSVボタン = 非活性化
		disableButton("btnCsv");
	}
}

/**
 * CSVボタンを押す処理
 * 
 * @param
 * @return
 * @exception
 */
function exportCSV() {
	$.ajax({
		type : "POST",
		url : "exportCsv",
		async : false,
		data : $("form").serialize(),
		success : function(data) {
			if (data.errMessage != null) {
				$("#txtMess").html(data.errMessage);
				removeInfoClass();
			} else {
				var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
				var path = rootPath + data.filePath;
				window.open(path);
				$("#txtMess").html("");
			}
		},
		error : function(e) {
			
		},
		complete : function(jqXHR, textStatus) {
			enableButton("btnCsv");
			isClickButton = true;
		}
	});
}

function formatYmd(updYmd) {
	var result = "";
	if(updYmd != null && updYmd != "") {
		var yearStart = updYmd.substring(0,4);
		var monthStart = updYmd.substring(4,6);
		var dayStart = updYmd.substring(6,8);
		result = yearStart + "/" + monthStart + "/" + dayStart;
	}
	return result;
}

function formatTime(updTime) {
	var result = "";
	if(updTime != null && updTime != "") {
		var hStart = updTime.substring(0,2);
		var mStart = updTime.substring(2,4);
		var sStart = updTime.substring(4,6);
		result = hStart + ":" + mStart + ":" + sStart;
	}
	return result;
}