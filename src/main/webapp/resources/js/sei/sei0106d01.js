/**
 * パッケージ名: ファイル名: sei0106d01.js
 * 
 * 作成者:QuanTran 作成日:2015/12/08
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

COL_NO = 0;
COL_CHECKBOX = 1;
COL_BILD_DATE = 2; // 請求締日
COL_CUST_CODE = 3; // 請求先コード
COL_BILD_NM_R = 4; // 請求先略称
COL_TAX_AMOUNT = 5; // 税抜き金額
COL_SALE_TAX = 6; // 消費税
COL_INC_TAX_AMOUNT = 7; // 税込み金額
COL_BILL_AMOUNT = 8; // 請求額
COL_JGYMEI = 9; // 事業所名
COL_USER_NM = 10; // 担当者氏名
COL_JIGYO_CODE = 11; // 事業所コード
COL_USER_CODE = 12; // 担当者コード
COL_BILD_ID = 13; // 請求ID
COL_CHAIN_IDX = 14; // チェーン枝番
COL_CHAIN_CODE = 15; // チェーンコード
COL_BILD_DATE_FULL = 16; // 請求締日 full

var DELIMITER = "##";
var initialJgycd = "";
var initialUserCode = "";

var initialJogyCode = "";
var initialBildDate = "";
var initialJgycdNm = "";
jQuery(document).ready(function($) {
	resizeTable(0);
	// 使用中止日を有効にする
	$("#txtBildDate").datepicker({
		showOn : "button",
		buttonImage : rootPath + "/resources/css/images/calendar.gif",
		buttonImageOnly : true,
		dateFormat : 'yy/mm'
	});
	
	initialJogyCode = $("#ddlJigyoCode").val();
	initialBildDate = $("#txtBildDate").val();
	initialJgycdNm = $("#lblJgycdNm").text();
	initialJgycd = $("#txtJgycd").val();
	
	loadDialogCom0102d02();
	loadDialogCom0102d05();
	// エンターキー押下時の遷移
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKeyDown(e, this, ".data_conditions_area");
		}
	});

	if ($("#isErrorControl").val() == "true") {
		errorControl();
		$("#txtBildDate").datepicker("disable");
		disableImgButton("btnUserCode");
		disableImgButton("btnJgycd");
	} else {
		setJigyoName();
		enableImgButton("btnUserCode");
		enableImgButton("btnJgycd");
		disableButton("btnPrint");
		disableButton("btnCsv");
		enableButton("btnClear")

		var sysAdminFlag = $("#sysAdminFlag").val();
		// 終了処理・例外処理
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlJigyoCode").focus();
		} else {
			$("#txtBildDate").focus();
		}
	}

	$("#txtBildDate").bind("focusout", function() {
		var txtDate = $(this).val().replace(/\//g, '');
		if (txtDate != "") {
			var yearStart = txtDate.substring(0, 4);
			var monthStart = txtDate.substring(4, 6);
			var dayStart = txtDate.substring(6, 8);
			var formatDateStart = yearStart + "/" + monthStart;
			$(this).val(formatDateStart);
		}
	});
	$("#txtBildDate").bind("focus", function() {
		var txtDate = $(this).val().replace(/\//g, '');
		$(this).val(txtDate);
	});

	

	/**
	 * 事務担当者_GetFocus
	 */
	$("#txtJgycd").bind("focus", function() {
		initialJgycd = $(this).val().trim();
	});
	
	/**
	 * 請求先選択（見出し）
	 */
	$("#chkSelectAll").change(function () {
		$("input:checkbox").prop('checked', $(this).prop("checked"));
	});
	/**
	 * 事務担当者_LostFocus
	 */
	$("#txtJgycd").bind("focusout", function() {
		if ($(this).val() != initialJgycd) {
			$("#lblJgycdNm").text("");
		}
	});

	/**
	 * 請求先コード_GetFocus
	 */
	$("#txtUserCode").bind("focus", function() {
		initialUserCode = $(this).val().trim();
	});

	/**
	 * 請求先コード_LostFocus
	 */
	$("#txtUserCode").bind("focusout", function() {
		if ($(this).val() != initialUserCode) {
			$("#lblUserNm").text("");
		}
	});

	/**
	 * 担当者検索
	 */
	$("#btnJgycd").bind("click", function() {
		if (isBtnImgEnabled("btnJgycd")) {
			// システム管理者フラグを取得する
			var sysAdminFlag = $("#sysAdminFlag").val().trim();
			// 変数値の情報を取得する
			// 担当者コード
			var userCode = $("#txtJgycd").val().trim();
			userCode = replaceText(userCode);
			// 利用権限コード
			var userRole = "";
			// 所属事業所コード
			var jigyoushoCode = "";
			if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				jigyoushoCode = $("#ddlJigyoCode").val().trim();
			} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
				jigyoushoCode = loginJigyouShoCode;
			}
			jigyoushoCode = replaceText(jigyoushoCode);
			showCom0102d05(userCode, userRole, jigyoushoCode);
		}
	});

	/**
	 * 請求先検索
	 */
	$("#btnUserCode").bind("click", function(e) {
		if (isBtnImgEnabled("btnUserCode")) {
			var plantCodeWk = "";
			// 得意先検索画面を表示する
			var sysAdminFlag = $("#sysAdminFlag").val();
			// 事業所コード編集
			if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				plantCodeWk = $('#ddlJigyoCode').val();
			} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
				plantCodeWk = loginJigyouShoCode;
			}
			// [画面]請求先コード
			var custCode = $("#txtUserCode").val();
			var shopCode = "";
			var searchKb = "2";
			// 検索子画面呼び出し用関数を呼び出す
			showCom0102d02(plantCodeWk, custCode, shopCode, searchKb);
		}
	});
	
	$("input[type='text']" ).change(function() {
		$(this).removeClass("form-error-field");
	});
	
	$("#ddlJigyoCode").change(function() {
		$(this).removeClass("form-error-field");
	});
	
	$("#ddlJgymei").change(function() {
		$(this).removeClass("form-error-field");
		
	});
	
	/**
	 * 表示ボタン
	 */
	$("#btnSearch").bind("click", function() {
		disableButton("btnSearch");
		display();
	});
	
	/**
	 * 表示ボタン
	 */
	$("#btnPrint").bind("click", function() {
		disableButton("btnPrint");
		// 確認メッセージ
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "印刷"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				print();
			} else {
				enableButton("btnPrint");
			}
		});
	});
	
	/**
	 * 表示ボタン
	 */
	$("#btnCsv").bind("click", function() {
		disableButton("btnCsv");
		jQuestion_confirm($("#COM001-I").val().replace("{1}", "CSVファイルを出力"), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				exportCsv();
			} else {
				enableButton("btnCsv");
			}
		});
	});
	
	/**
	 * クリアボタン
	 */
	$("#btnClear").bind("click", function(){
		pressClearButton();
	});
});


/**
 * 担当者コード検索のCallback
 * 
 * @param data ：
 * 取得されたデータ
 */
function getCom0102d05(data) {
	var showDataCnt = data.length;
	// 担当者情報を画面にセット
	for (var i = 0; i < showDataCnt; i++) {
		$("#txtJgycd").val(data[i].userCode);
		$("#lblJgycdNm").text(data[i].userNm);
	}
	// [画面]締日へフォーカスをセットする
	$("#ddlJgymei").focus();
	$("#txtJgycd").removeClass("form-error-field");
	reTurnCheckTab();
}

/**
 * 得意先子画面データ受け取り用関数（仮）
 * 
 * @param 取得したデータ
 * @return
 * @exception
 */
function getCom0102d02(data) {
	// 親画面側処理（仮）
	if (data[0].custCode != null) {
		$("#txtUserCode").val(data[0].custCode);
		$("#lblUserNm").html(data[0].custNmR);
		$("#btnSearch").focus();
	}
	
	$("#txtUserCode").removeClass("form-error-field");
	reTurnCheckTab();
}

/**
 * 表示ボタンをクリックされた場合の処理。入力された条件により請求先情報を取得する
 */
function display() {
	$.ajax({
		type: "POST",
		url: "display",
		data:$("form").serialize(),
		async : false,
		success: function(returnJsonData) {
			if(returnJsonData.billInfoList != null) {
				showTblHead();
				// リスト部を表示する
				drawTable(returnJsonData.billInfoList);
				$("#txtMess").html(returnJsonData.errMessage);
				
				// フッタエリアのボタン制御
				enableButton("btnPrint");
				enableButton("btnCsv");
				enableButton("btnClear");
				
				// ヘッダ部（データ呼び出し条件）の非活性化
				disableHeaderArea();
			} else {
				displayMessages("", null);
				if(returnJsonData.messageType == 'body') {
					showTblHead();
					$("#txtMess").html("");
					$("#tbl_tbody").html(returnJsonData.errMessage);
				} else {
					var errMessage = returnJsonData.errMessage
					if(errMessage != null && errMessage != "") {
						$("#tbl_tbody").html("");
						displayMessages(errMessage, MSG_ERR);
						if(returnJsonData.errItemIds != null && returnJsonData.errItemIds != "") {
							var lstError = returnJsonData.errItemIds.split(DELIMITER);
							$("#" + lstError[0]).focus().select();
							addClassErrorToControl(lstError);
						}
					}
				}
				enableButton("btnSearch")
				resizeTable(0);
			}
		},error: function(e) {

		},complete: function(jqXHR,textStatus){
		}
	});
}

/**
 * 印刷ボタンをクリックされた場合の処理。請求一覧を印刷する
 */
function print() {
	var billInfoList = getDataList();
	var txtBildDate = $("#txtBildDate").val();
	var ddlJigyoCode = $("#ddlJigyoCode").val();
	var txtJgycd = $("#txtJgycd").val();
	var ddlJgymei = $("#ddlJgymei").val();
	var txtUserCode = $("#txtUserCode").val();
	var loginJigyouShoCode= $("#loginJigyouShoCode").val();
	var sysAdminFlag = $("#sysAdminFlag").val();
	var rdOutputFormat = $('input[name=rdOutputFormat]:checked', '#formSei0106d01').val()
	var tempObj = {
			billInfoList : billInfoList,
			txtBildDate : txtBildDate,
			ddlJigyoCode : ddlJigyoCode,
			txtJgycd : txtJgycd,
			ddlJgymei : ddlJgymei,
			txtUserCode : txtUserCode,
			loginJigyouShoCode: loginJigyouShoCode,
			sysAdminFlag : sysAdminFlag,
			rdOutputFormat: rdOutputFormat
		};
	$.ajax({
		type: "POST",
		url: "print",
		contentType: 'application/json; charset=utf-8',
		dataType: "json",
		data : JSON.stringify(tempObj),
		async: false,
		success: function(returnJsonData) {
			displayMessages("", null);
			var errMessage = returnJsonData.errMessage
			if(errMessage != null && errMessage != "") {
				displayMessages(errMessage, MSG_ERR);
				if(returnJsonData.errItemIds != null && returnJsonData.errItemIds != "") {
					var lstError = returnJsonData.errItemIds.split(DELIMITER);
					$("#" + lstError[0]).focus().select();
					addClassErrorToControl(lstError);
				}
			}
		},
		error: function(e) {
			
		},
		complete: function(jqXHR, textStatus) {
			enableButton("btnPrint");
		}
	});
}

/**
 * 印刷ボタンをクリックされた場合の処理。請求一覧を印刷する
 */
function exportCsv() {
	var billInfoList = getDataList();
	var txtBildDate = $("#txtBildDate").val();
	var ddlJigyoCode = $("#ddlJigyoCode").val();
	var txtJgycd = $("#txtJgycd").val();
	var ddlJgymei = $("#ddlJgymei").val();
	var txtUserCode = $("#txtUserCode").val();
	var loginJigyouShoCode= $("#loginJigyouShoCode").val();
	var sysAdminFlag = $("#sysAdminFlag").val();
	var rdOutputFormat = $('input[name=rdOutputFormat]:checked', '#formSei0106d01').val()
	var tempObj = {
			billInfoList : billInfoList,
			txtBildDate : txtBildDate,
			ddlJigyoCode : ddlJigyoCode,
			txtJgycd : txtJgycd,
			ddlJgymei : ddlJgymei,
			txtUserCode : txtUserCode,
			loginJigyouShoCode: loginJigyouShoCode,
			sysAdminFlag : sysAdminFlag,
			rdOutputFormat: rdOutputFormat
		};
	$.ajax({
		type: "POST",
		url: "exportCsv",
		contentType: 'application/json; charset=utf-8',
		dataType: "json",
		data : JSON.stringify(tempObj),
		async: false,
		success: function(returnJsonData) {
			displayMessages("", null);
			var errMessage = returnJsonData.errMessage
			if(errMessage != null && errMessage != "") {
				displayMessages(errMessage, MSG_ERR);
				if(returnJsonData.errItemIds != null && returnJsonData.errItemIds != "") {
					var lstError = returnJsonData.errItemIds.split(DELIMITER);
					$("#" + lstError[0]).focus().select();
					addClassErrorToControl(lstError);
				}
			} else {
				var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
				var path = rootPath + returnJsonData.filePath;
				window.open(path);
				$("#txtMess").html("");
			}
		},
		error: function(e) {
			
		},
		complete: function(jqXHR, textStatus) {
			enableButton("btnCsv");
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
	resizeTable(len);
}

/**
 * Draw a row.
 * @param renban 
 * @param dataRow
 * @returns {htmlRow}
 */
function drawRow(renban, dataRow) {
	var htmlRow = "";
	// No.
	htmlRow += "<td id='" + renban +"' class='align_center'><span>" + renban + "</span></td>";
	htmlRow += "<td class='align_center'><input type='checkbox'></td>";
	// 締日
	htmlRow += "<td class='align_center'>" + ReplaceNullReturnBlank(getDay(dataRow.bildDate)) + "</td>";
	
	// 請求先
	//請求先コード
	htmlRow += "<td class='align_left border_r_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.custCode)) + "</td>";
	//請求先略称
	htmlRow += "<td class='align_left border_l_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.bildNmR)) + "</td>";
	
	// 税抜き金額
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(addFigure(dataRow.taxAmount)) + "</td>";
	// 消費税
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(addFigure(dataRow.saleTax)) + "</td>";
	// 税込み金額
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(addFigure(dataRow.incTaxAmount)) + "</td>";
	
	// 今回請求額
	htmlRow += "<td class='align_right'>" + ReplaceNullReturnBlank(addFigure(dataRow.billAmount)) + "</td>";
	// 事業所
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(dataRow.jgymei)) + "</td>";
	//事務担当者
	htmlRow += "<td class=''>" + ReplaceNullReturnBlank(htmlEntities(dataRow.userNm)) + "</td>";
	
	// 事業所コード
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.jigyoCode)) + "</td>";
	// 担当者コード
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.userCode)) + "</td>";
	// 請求ID
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.bildId)) + "</td>";
	// チェーン枝番
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.chainIdx)) + "</td>";
	// チェーンコード
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.chainCode)) + "</td>";
	// 請求締日
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(htmlEntities(dataRow.bildDate)) + "</td>";
	return htmlRow;
}

/**
 * ヘッダ部（データ呼び出し条件）の非活性化
 */
function disableHeaderArea() {
	disableButton("btnSearch");
	$('#ddlJigyoCode').attr('disabled', true);
	$('#txtBildDate').attr('disabled', true);
	$('#txtJgycd').attr('disabled', true);
	$('#ddlJgymei').attr('disabled', true);
	$('#txtUserCode').attr('disabled', true);
	disableImgButton("btnUserCode");
	disableImgButton("btnJgycd");
	$("#txtBildDate").datepicker("disable");
}

/**
 * クリアボタンをクリックされた場合の処理。画面の初期化を行う
 */
function pressClearButton() {
	enableButton("btnSearch");
	$("#txtBildDate").datepicker("enable");
	$("#txtBildDate").attr('disabled', false).val(initialBildDate);
	$('#ddlJigyoCode').attr('disabled', false).val(initialJogyCode);
	$('#txtJgycd').attr('disabled', false).val(initialJgycd);
	$('#ddlJgymei').attr('disabled', false).val("");
	$('#txtUserCode').attr('disabled', false).val("");
	$("#lblJgycdNm").text(initialJgycdNm);
	$("#lblUserNm").text("");
	
	setJigyoName();
	enableImgButton("btnUserCode");
	enableImgButton("btnJgycd");
	disableButton("btnPrint");
	disableButton("btnCsv");
	enableButton("btnClear")

	var sysAdminFlag = $("#sysAdminFlag").val();
	// 終了処理・例外処理
	if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
		$("#ddlJigyoCode").focus();
	} else {
		$("#txtBildDate").focus();
	}
	hideTblHead();
	$("#tbl_tbody").html("");
	
}
/**
 * エンターキー押下時の遷移
 * 
 * @param event
 * @param element
 */
function EnterKeyDown(event, element, className) {
	var next_idx = $(className).find(".enterKey:enabled:not([readonly])").index(element) + 1;
	if (next_idx == 0) {
		return;
	}
	// get number of text input element in a html document	
	var tot_idx = $(className)
			.find(".enterKey:enabled:not([readonly])").length;
	if (tot_idx == next_idx) {
		// go to the first text element if focused in the last text input
		// element
		$(className).find(".enterKey:enabled:not([readonly]):eq(0)").focus().select();
	} else {
		// go to the next text input element
		$(className).find(".enterKey:enabled:not([readonly]):eq(" + next_idx + ")").focus().select();
	}

}

/**
 * Set jigyo name
 */
function setJigyoName(){
	var text = $("#ddlJigyoCode :selected").text();
	if (text != "") {
		var jigyoName = text.substring(text.indexOf(":") + 1).trim();
		$("#jigyoName").val(jigyoName);
	}
}

/**
 * Show the table header
 */
function showTblHead() {
	$("#divHead").removeClass("display_none");
}

/**
 * hide the table header
 */
function hideTblHead() {
	$("#divHead").addClass("display_none");
}

/**
 * Resize the table.
 */
function resizeTable (len) {
	var tblBodyHeight = 27 * len;
	var divBodyHeight = $("#divBody").height();
	if(checkScrollBar()) {
		var hContent = $("html").height(); // get the height of your content
		var hWindow = $(window).height();  // get the height of the visitor's browser window
		$("#divBody").height(divBodyHeight - (hContent- hWindow));
		divBodyHeight = $("#divBody").height();
	}
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

/**
 * Get the list of rows in the table.
 * 
 * @returns {Array} The list of rows in the table
 */
function getDataList () {
	var dataList = [];
	$('#tblBody tbody tr').each(function() {
		var row = $(this);
		if (row.find('input[type="checkbox"]').is(':checked')) {
			var renban = row.find("td").eq(COL_NO).text();
			var bildDate = row.find("td").eq(COL_BILD_DATE_FULL).text();
			var custCode = row.find("td").eq(COL_CUST_CODE).text();
			var bildNmR = row.find("td").eq(COL_BILD_NM_R).text();
			var taxAmount = delFigure(row.find("td").eq(COL_TAX_AMOUNT).text());
			var saleTax = delFigure(row.find("td").eq(COL_SALE_TAX).text());
			var incTaxAmount = delFigure(row.find("td").eq(COL_INC_TAX_AMOUNT).text());
			var billAmount = delFigure(row.find("td").eq(COL_BILL_AMOUNT).text());
			var jgymei = row.find("td").eq(COL_JGYMEI).text();
			var userNm = row.find("td").eq(COL_USER_NM).text();
			var jigyoCode = row.find("td").eq(COL_JIGYO_CODE).text();
			var userCode = row.find("td").eq(COL_USER_CODE).text();
			var bildId = row.find("td").eq(COL_BILD_ID).text();
			var chainIdx = row.find("td").eq(COL_CHAIN_IDX).text();
			var chainCode = row.find("td").eq(COL_CHAIN_CODE).text();
			var tempObj = {
					bildDate: bildDate,
					custCode : custCode,
					bildNmR : bildNmR,
					taxAmount : taxAmount,
					saleTax :saleTax,
					incTaxAmount :incTaxAmount,
					billAmount : billAmount,
					jgymei : jgymei,
					userNm : userNm,
					jigyoCode : jigyoCode,
					userCode : userCode,
					bildId : bildId,
					chainIdx : chainIdx,
					chainCode: chainCode
				};
			dataList.push(tempObj);
		}
	});
	return dataList;
}

/**
 * 「YYYYMMDD」形式を「DD」形式に変換 
 * @param yyyyMMdd date
 * @return 「DD」
 */
function getDay(yyyyMMdd) {
  return yyyyMMdd.substring(6, 8) + "日";
}
