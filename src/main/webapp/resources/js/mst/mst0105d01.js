/**
 * パッケージ名: ファイル名: mst0105d01.js
 * 
 * 作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
var DELIMITER = "##";
var currentShowingColId = "";
var isTableMenuShowing = false;
var olCenterCode = "";
var atTorihikiCode = "";
var olTorihikiCode = "";
var rowSelected = null;
jQuery(document).ready(function($) {
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".condition_cont");
		}
	});
	
	$("input[type='text']" ).change(function() {
		$(this).removeClass("form-error-field");
	});
	
	var isClickButton = false;
	var initialOnlineCenterCode = "";
	var initialAttorihikiCode = "";
	if($("#isErrorControl").val() == "true") {
		errorControl();
	} else {
		getOlCustConvMasterData(false);
	}
	
	/**
	 * オンラインセンターコードがフォーカスを得た際の処理。入力値を保存する。
	 */
	$("#txtOnlineCenterCode").bind("focus", function() {
		// チェーンコード ≠ NULL の場合、チェーンコードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			initialOnlineCenterCode = $(this).val().trim();
		}
	});
	
	/**
	 * オンラインセンターコードからフォーカスが外れた際の処理。関連項目の初期化を行う。
	 */
	$("#txtOnlineCenterCode").bind("focusout", function() {
		$(this).removeClass('form-error-field');
		if($(this).val() != initialOnlineCenterCode) {
			$("#lblCustomerNameR01").text("");
		}
	});
	
	/**
	 * 相手取引先コードがフォーカスを得た際の処理。入力値を保存する。
	 */
	$("#txtAtTorihikiCode").bind("focus", function() {
		// チェーンコード ≠ NULL の場合、チェーンコードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			initialAttorihikiCode = $(this).val().trim();
		}
	});
	
	/**
	 * 相手取引先コードからフォーカスが外れた際の処理。関連項目の初期化を行う。
	 */
	$("#txtAtTorihikiCode").bind("focusout", function() {
		$(this).removeClass('form-error-field');
		if($(this).val() != initialAttorihikiCode) {
			$("#lblCustomerNameR02").text("");
		}
	});
	/**
	 * （一覧画面）検索ボタン
	 */
	$("#btnSearch").bind("click", function() {
		disableButton("btnSearch");
		search();
	});
	
	/**
	 * （一覧画面）新規ボタン
	 */
	$("#btnNew").bind("click", function() {
		disableButton("btnNew");
		$("#btnNewSubmit").trigger( "click" );
	});
	
	/**
	 * （一覧画面）条件クリアボタン
	 */
	$("#btnClear").bind("click", function() {
		$("#txtOnlineCenterCode").val("");
		$("#lblCustomerNameR01").text("");
		$("#txtOnlineTorihikiCode").val("");
		$("#txtAtTorihikiCode").val("");
		
		$("#lblCustomerNameR02").text("");
		$('#chkCancellationData').prop('checked', false);
		// フォーカスセット
		$("#txtOnlineCenterCode").focus().select();
		resetItemColor();
	});
	
	/**
	 * （一覧画面）条件クリアボタン
	 */
	$("#btnCsv").bind("click", function() {
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
		rowSelected =  $(this).parent();
		olCenterCode = rowSelected.find("td").eq(1).text();
		olTorihikiCode = rowSelected.find("td").eq(2).text();
		atTorihikiCode = rowSelected.find("td").eq(3).text();
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
	
	// （一覧画面）登録画面遷移メニュー_照会ボタン
	$("#viewSubMenu").bind("click", function() {
		$("#olCenterCode").val(olCenterCode);
		$("#olTorihikiCode").val(olTorihikiCode);
		$("#atTorihikiCode").val(atTorihikiCode);
		$("#view").trigger( "click" );
	});
	
	$("#modifySubMenu").bind("click", function() {
		$("#olCenterCode").val(olCenterCode);
		$("#olTorihikiCode").val(olTorihikiCode);
		$("#atTorihikiCode").val(atTorihikiCode);
		$( "#modify").trigger( "click" );
	});
	
	$("#deleteSubMenu").bind("click", function() {
		$("#olCenterCode").val(olCenterCode);
		$("#olTorihikiCode").val(olTorihikiCode);
		$("#atTorihikiCode").val(atTorihikiCode);
		$("#delete").trigger( "click" );
	});
});

/**
 * （一覧画面）検索ボタン
 */
function search() {
	$.ajax({
		type: "POST",
		url: "search",
		async : false,
		data:$("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(returnJsonData) {
			$("#lblCustomerNameR01").text(ReplaceNullReturnBlank(returnJsonData.lblCustomerNameR01));
			$("#lblCustomerNameR02").text(ReplaceNullReturnBlank(returnJsonData.lblCustomerNameR02));
			if(returnJsonData.convMasterInfos != null) {
				enableButton("btnCsv");
				// (2)取得したオンライン得意先変換マスタデータを一覧に表示する。
				drawTable(returnJsonData.convMasterInfos,returnJsonData.errMessage);
				setHaitaDate(returnJsonData);
				$("#txtMess").html(returnJsonData.errMessage);
				// 3. フォーカスセット
				$("#txtOnlineCenterCode").focus().select();
			} else {
				disableButton("btnCsv");
				resetItemColor();
				var errorItemId = returnJsonData.errorItemId;
				var lstError = errorItemId.split(DELIMITER);
				$("#" + lstError[0]).focus().select();
				addClassErrorToControl(lstError);
				if(returnJsonData.messageType == 'body') {
					$("#txtMess").html("");
					$("#tbl_tbody").html(returnJsonData.errMessage);
				} else {
					$("#tbl_tbody").html("");
					$("#txtMess").html(returnJsonData.errMessage);
				}
				resizeTable(0, "");
			}
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
			enableButton("btnSearch");
		}
	});
}
/**
 * オンライン得意先変換マスタ一覧表示
 * 
 * @param isSearchEvent
 */
function getOlCustConvMasterData(isSearchEvent) {
	$.ajax({
		type: "POST",
		url: "getOlCustConvMasterData",
		async : false,
		data:$("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(returnJsonData) {
			$("#lblCustomerNameR01").text(ReplaceNullReturnBlank(returnJsonData.lblCustomerNameR01));
			$("#lblCustomerNameR02").text(ReplaceNullReturnBlank(returnJsonData.lblCustomerNameR02));
			if(returnJsonData.convMasterInfos != null) {
				// (2)取得したオンライン得意先変換マスタデータを一覧に表示する。
				drawTable(returnJsonData.convMasterInfos, returnJsonData.errMessage);
				setHaitaDate(returnJsonData);
				$("#txtMess").html(returnJsonData.errMessage);
			} else {
				if(returnJsonData.messageType == 'body') {
					$("#txtMess").html("");
				} else {
					$("#tbl_tbody").html("");
					$("#txtMess").html(returnJsonData.errMessage);
				}
			}
			// 3. フォーカスセット
			$("#txtOnlineCenterCode").focus().select();
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
		}
	});
}

/**
 * テーブルの表示
 * @param data　テーブルのデータ
 * @return 
 * @exception
 */
function drawTable(data, message) {
	$("#tbl_tbody").html("");
	var htmlData = "";
	//テーブルの表示	
	var searchMax = $("#searchMax").val();
	var len = data.length;
	
	if (searchMax < len) {
		len = searchMax;
	}
	
	
	for (var i = 0 ; i < len; i++){
		htmlData += "<tr>";
		htmlData += drawRow(i+1, data[i]);
		htmlData += "</tr>";
	}
	$("#tbl_tbody").html(htmlData);
	resizeTable(len, message);
}

/**
 * Draw a row.
 * @param renban 
 * @param dataRow
 * @returns {htmlRow}
 */
function drawRow(renban, dataRow) {
	var htmlRow = "";
	htmlRow += "<td id='" + renban + "' name='"+ ReplaceNullReturnBlank(dataRow.olCenterCode) +"' class='contextMenu align_center'><span>" + renban + "</span></td>";
	htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.olCenterCode) + "</td>";
	if(dataRow.olTorihikiCode == $("#olTorihikiCodeNone").val()) {
		htmlRow += "<td></td>";
	} else {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.olTorihikiCode) + "</td>";
	}
	htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.atTorihikiCode) + "</td>";
	htmlRow += "<td class='align_center'>" + ReplaceNullReturnBlank(dataRow.custCode) + "</td>";
	htmlRow += "<td>" + ReplaceNullReturnBlank(htmlEntities(dataRow.custNmR)) + "</td>";
	return htmlRow;
}

function resetItemColor() {
	$("#txtOnlineCenterCode").removeClass("form-error-field");
	$("#txtOnlineTorihikiCode").removeClass("form-error-field");
	$("#txtAtTorihikiCode").removeClass("form-error-field");
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
			$("#lblCustomerNameR01").text(ReplaceNullReturnBlank(data.lblCustomerNameR01));
			$("#lblCustomerNameR02").text(ReplaceNullReturnBlank(data.lblCustomerNameR02));
			if (data.filePath == null) {
				$("#txtMess").html(data.errMessage);
				resetItemColor();
				var errorItemId = data.errorItemId;
				var lstError = errorItemId.split(DELIMITER);
				$("#" + lstError[0]).focus().select();
				addClassErrorToControl(lstError);
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

function setHaitaDate(data) {
	$("#haitaDate").val(data.haitaDate);
	$("#haitaTime").val(data.haitaTime);
}

/**
 * Resize the table.
 */
function resizeTable (len , message) {
	var tblBodyHeight = 27 * len;
	var divBodyHeight = $("#divBody").height();
	if (tblBodyHeight > divBodyHeight) {
		$("#divBody").css("overflow-y", "scroll");
		$("#divHead").width($("#divBody").width() - 17);
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width() - 17);
		});
	} else {
		$("#divBody").css("overflow-y", "hidden");
		$("#divHead").width($("#divBody").width());
		
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width());
		});
	}
}