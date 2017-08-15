/**
 * ファイル名: mst0199d02.js
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

var currentShowingColId = "";
var isTableMenuShowing = false;
var strSelectedGlCode="";
var rowSelected;
var srcRowList = [];
jQuery(document).ready(function($) {
	var isClickButton = false;
	var errControl = $('#errorControl').val();
	if(errControl == "") {
		search();
	} else {
		errorControl();
	}
	
	if($("#updFlg").val() == "true") {
		$("#updPermitFlg").prop('checked', true);
	}
	
	if($("#insFlg").val() == "true") {
		$("#insPermitFlg").prop('checked', true);
	}
	
	if($("#delFlg").val() == "true") {
		$("#delPermitFlg").prop('checked', true);
	}
	buttonNameControl();
	var viewMode = $("#viewMode").val();
	// フォーカスセット
	if(viewMode == SHOUKAI_MODE_CHAR ) {
		$("#btnBack").focus();
	} else if(viewMode == TEISEI_MODE_CHAR) {
		$("#gpCode").focus();
	}
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".data_conditions_input_cont");
		}
	});
	// ＜編集エリア＞
	$("#btnBack").bind("click", function() {
		isClickButton = true;
		$("#eventBack").trigger( "click" );
	});
	
	// 追加・更新ボタン
	$("#btnAddUpdate").bind("click", function() {
		isClickButton = true;
		addUpdate();
	});
	
	// (登録画面)クリアボタン_フッダー
	$("#btnFooterClear").bind("click", function() {
		isClickButton = true;
		disableButton("btnFooterClear");
		jQuestion_confirm($("#COM001-I1").val(), $("#COM001-I1").attr("title"), function(retVal) {
			if (retVal) {
				resetEditArea();
				$("#clearSubmit").trigger( "click" );
				isClickButton = false;
			} else {
				enableButton("btnFooterClear");
				isClickButton = false;
			}
		});
	});
	
	$("#btnRegistration").bind("click", function() {
		isClickButton = true;
		disableButton("btnRegistration")
		jQuestion_confirm($("#COM001-I2").val(), $("#COM001-I2").attr("title"), function(retVal) {
			if (retVal) {
				register();
			} else {
				enableButton("btnRegistration");
				isClickButton = false;
			}
		});
	});
	
	// クリアボタン（編集エリア）
	$("#btnClear").bind("click", function() {
		isClickButton = true;
		resetEditArea();
	});
	
	//▼処理	
	$(document).on("mouseover", ".contextMenu", function(){
		var updPermitFlg = $("#updPermitFlg").is( ":checked" );
		var insPermitFlg = $("#insPermitFlg").is( ":checked" );
		var delPermitFlg = $("#delPermitFlg").is( ":checked" );
		// [画面]画面表示モード ＝ '3'（訂正）の場合
		if($("#viewMode").val() == TEISEI_MODE_CHAR && (updPermitFlg || insPermitFlg || delPermitFlg)) {
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
					isClickButton = true;
					// change the text back from "▼" to number
					$("#" + currentShowingColId).find("span").text(currentShowingColId);
				}
			}
			// is menu is not showing, change the text back from "▼" to number
			if ($(this).attr("id") != currentShowingColId) {
				$("#" + currentShowingColId).find("span").text(currentShowingColId);
				currentShowingColId = $(this).attr("id");
			}
		}
	});
	
	$(document).on("click", ".contextMenu", function(e){
		isClickButton = true;
		var updPermitFlg = $("#updPermitFlg").is( ":checked" );
		var insPermitFlg = $("#insPermitFlg").is( ":checked" );
		var delPermitFlg = $("#delPermitFlg").is( ":checked" );
		if($("#viewMode").val() == '3' && (updPermitFlg || insPermitFlg || delPermitFlg)) {
			// get current coordinates of mouse
			var x = e.pageX;
			var y = e.pageY;
			// show menu at current mouse's coordinates
			$("#tableMenu").css("position", "absolute").css("left", x + "px").css("top", y + "px").fadeIn("fast");
			// switch flag value
			isTableMenuShowing = true;
			strSelectedGlCode = $(this).attr("name");
			var updPermitFlg = $("#updPermitFlg").is( ":checked" );
			rowSelected =  $(this).parent();
		}
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
			isClickButton = true;
		}
	});
	//▼処理ＥＮＤ
	
	$("#modifySubMenu").bind("click", function(e) {
		$("#selectGlCodeID").val(strSelectedGlCode);
		// メニューを閉じる
		$("#tableMenu").fadeOut("fast");
		isClickButton = true;
		isTableMenuShowing = false;
		var updPermitFlg = $("#updPermitFlg").is( ":checked" );
		// [画面]権限（更新） ＝ チェックあり の場合
		if(updPermitFlg) {
			resetEditArea();
			var renban = rowSelected.find("td").eq(0).text();
			var glCode = rowSelected.find("td").eq(1).text();
			var target1 = rowSelected.find("td").eq(2).text();
			var target2 = rowSelected.find("td").eq(3).text();
			var target3 = rowSelected.find("td").eq(4).text();
			var target4 = rowSelected.find("td").eq(5).text();
			var target5 = rowSelected.find("td").eq(6).text();
			var target6 = rowSelected.find("td").eq(7).text()
			var target7 = rowSelected.find("td").eq(8).text();
			var target8 = rowSelected.find("td").eq(9).text();
			var target9 = rowSelected.find("td").eq(10).text();
			var target10 = rowSelected.find("td").eq(11).text();
			var addFlg = rowSelected.find("td").eq(12).text();
			var updateFlg = rowSelected.find("td").eq(13).text();
			var deleteFlg = rowSelected.find("td").eq(14).text();
			$("#no").val(renban);
			$("#editMode").val(UPDATE_MODE);
			buttonNameControl();
			$("#gpCode").val(glCode);
			$("#itemValue1").val(target1);
			$("#itemValue2").val(target2);
			$("#itemValue3").val(target3);
			$("#itemValue4").val(target4);
			$("#itemValue5").val(target5);
			$("#itemValue6").val(target6);
			$("#itemValue7").val(target7);
			$("#itemValue8").val(target8);
			$("#itemValue9").val(target9);
			$("#itemValue10").val(target10);
			
			$('#gpCode').prop('disabled', true);
			$("#itemValue1").focus().select();
		}
	});
	
	$("#deleteSubMenu").bind("click", function(e) {
		$("#viewModeID").val(TEISEI_MODE_CHAR);
		$("#selectGlCodeID").val(strSelectedGlCode);
		
		// メニューを閉じる
		$("#tableMenu").fadeOut("fast");
		var delPermitFlg = $("#delPermitFlg").is( ":checked" );
		isTableMenuShowing = false;
		isClickButton = true;
		// [画面]権限（削除） ＝ チェックなし の場合
		if(delPermitFlg) {
			deleteRow();
			if(isTableEmpty()) {
				disableButton("btnRegistration");
			}
		}
	});
	
	//check focus out
	$(document).on('focusout', '#gpCode', function(){
		setTimeout(function(){
			if (!isClickButton) {
				$("#txtMess").text('');
				$('#gpCode').removeClass('form-error-field');
				var errorID = chkItem($('#gpCode').val(), true, null, null, null);
				if (errorID != null){
					$("#txtMess").text($("#" + errorID).val().replace("{1}", "コード"));
					$("#gpCode").addClass("form-error-field");
					removeInfoClass();
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue1', function(){
		var colIndex = 1;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue2', function(){
		var colIndex = 2;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue3', function(){
		var colIndex = 3;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue4', function(){
		var colIndex = 4;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue5', function(){
		var colIndex = 5;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue6', function(){
		var colIndex = 6;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue7', function(){
		var colIndex = 7;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue8', function(){
		var colIndex = 8;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue9', function(){
		var colIndex = 9;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//check focus out
	$(document).on('focusout', '#itemValue10', function(){
		var colIndex = 10;
		setTimeout(function(){
			if (!isClickButton) {
				isItemError(colIndex,false);
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
});

/**
 * Reset the edit area.
 */
function resetEditArea () {
	// 編集エリアを初期化する
	$('#gpCode').prop('disabled', false);
	$("#gpCode").val('').removeClass("form-error-field");
	$("#itemValue1").val('').removeClass("form-error-field");
	$("#itemValue2").val('').removeClass("form-error-field");
	$("#itemValue3").val('').removeClass("form-error-field");
	$("#itemValue4").val('').removeClass("form-error-field");
	$("#itemValue5").val('').removeClass("form-error-field");
	$("#itemValue6").val('').removeClass("form-error-field");
	$("#itemValue7").val('').removeClass("form-error-field");
	$("#itemValue8").val('').removeClass("form-error-field");
	$("#itemValue9").val('').removeClass("form-error-field");
	$("#itemValue10").val('').removeClass("form-error-field");
	// フォーカスセット
	$("#gpCode").focus().select();
	$("#editMode").val(ADD_MODE)
	buttonNameControl();
	$('#txtMess').text('');
	isClickButton = false;
}

/**
 * (登録画面)追加・更新ボタン
 */
function addUpdate() {
	var dataList = getDataList();
	if(!checkInput()) {
		var success = false;
		if($("#editMode").val() == ADD_MODE) { // （追加）
			if(!checkPesence()) {
				var count = $('#tbl_tbody').children('tr').length;
				var count_display= $('#tbl_tbody').children('tr.display_none').length;
				var row = newRow((count - count_display) + 1);
				$('#tblBody > tbody:last-child').append(row);
				resetTableSize();
				success = true;
				$('#txtMess').html($("#MST001-I").val().replace("{2}",(count - count_display) + 1));
				addInfoClass();
				if(!isTableEmpty()) {
					enableButton("btnRegistration");
				}
			}
		}
		
		if($("#editMode").val() == UPDATE_MODE) { //（更新）
			var no = rowSelected.find("td").eq(0).text();
			var glCode = $("#gpCode").val();
			var target1 = $("#itemValue1").val();
			if(typeof target1 === 'undefined'){
				target1 = "";
			};
			var target2 = $("#itemValue2").val();
			if(typeof target2 === 'undefined'){
				target2 = "";
			};
			var target3 = $("#itemValue3").val();
			if(typeof target3 === 'undefined'){
				target3 = "";
			};
			var target4 = $("#itemValue4").val();
			if(typeof target4 === 'undefined'){
				target4 = "";
			};
			var target5 = $("#itemValue5").val();
			if(typeof target5 === 'undefined'){
				target5 = "";
			};
			var target6 = $("#itemValue6").val();
			if(typeof target6 === 'undefined'){
				target6 = "";
			};
			var target7 = $("#itemValue7").val();
			if(typeof target7 === 'undefined'){
				target7 = "";
			};
			var target8 = $("#itemValue8").val();
			if(typeof target8 === 'undefined'){
				target8 = "";
			};
			var target9 = $("#itemValue9").val();
			if(typeof target9 === 'undefined'){
				target9 = "";
			};
			var target10 = $("#itemValue10").val();
			if(typeof target10 === 'undefined'){
				target10 = "";
			};
			
			var tempObj = {
					glCode: glCode,
					target1 : target1,
					target2 : target2,
					target3 : target3,
					target4 : target4,
					target5 : target5,
					target6 : target6,
					target7 : target7,
					target8 : target8,
					target9 : target9,
					target10 : target10
				};
			rowSelected.find("td").eq(2).html(target1);
			rowSelected.find("td").eq(3).html(target2);
			rowSelected.find("td").eq(4).html(target3);
			rowSelected.find("td").eq(5).html(target4);
			rowSelected.find("td").eq(6).html(target5);
			rowSelected.find("td").eq(7).html(target6);
			rowSelected.find("td").eq(8).html(target7);
			rowSelected.find("td").eq(9).html(target8);
			rowSelected.find("td").eq(10).html(target9);
			rowSelected.find("td").eq(11).html(target10);
			if(isRowChange(tempObj)) {
				rowSelected.find("td").eq(13).html(FLAG_ENABLE_CHAR);
			} else {
				rowSelected.find("td").eq(13).html(FLAG_DISABLE_CHAR);
			}
			success = true;
			$('#txtMess').html($("#MST002-I").val().replace("{2}",no));
			addInfoClass();
		}
		// フォーカスセット & 画面制御
		if(success) {
			if($("#gpCode").val() == '---') {
				$('#gpCode').prop("disabled", true)
				$('#itemValue1').focus().select();
			} else {
				$('#gpCode').prop('disabled', false);
				$('#gpCode').focus().select();
			}
		}
		
		// 汎用区分設定一覧の編集モード設定
		var insPermitFlg = $("#insPermitFlg").is(":checked");
		if(insPermitFlg) {
			$("#editMode").val(ADD_MODE);
		} else {
			$("#editMode").val(UPDATE_MODE);
		}
		buttonNameControl();
		isClickButton = false;
	} else {
		isClickButton = false;
	}
}

/**
 * Delete the selected row on UI.
 */
function deleteRow() {
	var glCode = rowSelected.find("td").eq(1).text();	
	var oldList = getDataList();
	var newList = [];
	for(var i = 0; i < oldList.length; i ++) {
		var data = oldList[i];
		if(glCode != data.glCode){
			var tempObj = {
					glCode: data.glCode,
					target1 : data.target1,
					target2 : data.target2,
					target3 : data.target3,
					target4 : data.target4,
					target5 : data.target5,
					target6 : data.target6,
					target7 : data.target7,
					target8 : data.target8,
					target9 : data.target9,
					target10 : data.target10,
					addFlg: data.addFlg,
					updateFlg: data.updateFlg,
					deleteFlg: data.deleteFlg
				};
			newList.push(tempObj);
		} else {
			var tempObj = {
					glCode: data.glCode,
					target1 : data.target1,
					target2 : data.target2,
					target3 : data.target3,
					target4 : data.target4,
					target5 : data.target5,
					target6 : data.target6,
					target7 : data.target7,
					target8 : data.target8,
					target9 : data.target9,
					target10 : data.target10,
					addFlg: data.addFlg,
					updateFlg: data.updateFlg,
					deleteFlg: FLAG_ENABLE
				};
			newList.push(tempObj);
		}
	}
	redrawTable(newList);
}

/**
 * Redraw the table.
 * @param newList
 */
function redrawTable(newList) {
	var htmlRow = "";
	if(newList.length > 0) {
		var renban = 0;
		for(var i = 0; i < newList.length; i ++) {
			var data = newList[i];
			if(data.deleteFlg == FLAG_ENABLE_CHAR && data.addFlg == FLAG_DISABLE_CHAR) {
				htmlRow += "<tr class='display_none'>";
			} else {
				htmlRow += "<tr>";
				renban ++;
			}
			var glCode = data.glCode;
			var target1 = data.target1;
			var target2 = data.target2;
			var target3 = data.target3;
			var target4 = data.target4;
			var target5 = data.target5;
			var target6 = data.target6;
			var target7 = data.target7;
			var target8 = data.target8;
			var target9 = data.target9;
			var target10 =data.target10;
			var addFlg = data.addFlg;
			var updateFlg= data.updateFlg;
			var deleteFlg = data.deleteFlg;
			htmlRow += "<td id='" + renban + "' name='"+ ReplaceNullReturnBlank(glCode) +"' class='contextMenu align_center'><span>" + renban + "</span></td>";
			htmlRow += "<td>" + ReplaceNullReturnBlank(glCode) + "</td>";
			
			if ($("#title1").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target1) + "</td>";
			} else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title2").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target2) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title3").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target3) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title4").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target4) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title5").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target5) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title6").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target6) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title7").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target7) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title8").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target8) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			if ($("#title9").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target9) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			if ($("#title10").text() != "") {
				htmlRow += "<td>" + ReplaceNullReturnBlank(target10) + "</td>";
			}else {
				htmlRow += "<td class='display_none'></td>";
			}
			
			htmlRow += "<td class='display_none'>"+addFlg+"</td>"; // 新規追加行
			htmlRow += "<td class='display_none'>"+updateFlg +"</td>"; // 更新あり
			htmlRow += "<td class='display_none'>"+deleteFlg +"</td>";
			htmlRow += "</tr>";
		}
	}
	$("#tbl_tbody").html('');
	$("#tbl_tbody").html(htmlRow);
	resizeTable();
}

/**
 * Create a new row.
 * @param renban
 * @returns {String}
 */
function newRow(renban) {
	var htmlRow = "";
	htmlRow += "<tr>";
	var glCode = $("#gpCode").val();
	var target1 = $("#itemValue1").val();
	var target2 = $("#itemValue2").val();
	var target3 = $("#itemValue3").val();
	var target4 = $("#itemValue4").val();
	var target5 = $("#itemValue5").val();
	var target6 = $("#itemValue6").val();
	var target7 = $("#itemValue7").val();
	var target8 = $("#itemValue8").val();
	var target9 = $("#itemValue9").val();
	var target10 = $("#itemValue10").val();
	htmlRow += "<td id='" + renban + "' name='"+ ReplaceNullReturnBlank(glCode) +"' class='contextMenu align_center'><span>" + renban + "</span></td>";
	htmlRow += "<td>" + ReplaceNullReturnBlank(glCode) + "</td>";
	
	if ($("#title1").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target1) + "</td>";
	} else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title2").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target2) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title3").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target3) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title4").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target4) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title5").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target5) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title6").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target6) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title7").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target7) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title8").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target8) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	if ($("#title9").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target9) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if ($("#title10").text() != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(target10) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	htmlRow += "<td class='display_none'>1</td>"; // 新規追加行
	htmlRow += "<td class='display_none'>0</td>"; // 更新あり
	htmlRow += "<td class='display_none'>0</td>"; // 更新あり
	htmlRow += "</tr>";
	return htmlRow;
}

/**
 * 登録ボタン
 */
function register() {
	var dataList = getDataList();
	var glKb = $("#glKb").text();
	var glKbNm = $("#glKbNm").text();
	var haitaDate = $("#haitaDate").val();
	var haitaTime = $("#haitaTime").val();
	var title1 = $("#title1").text();
	var title2 = $("#title2").text();
	var title3 = $("#title3").text();
	var title4 = $("#title4").text();
	var title5 = $("#title5").text();
	var title6 = $("#title6").text();
	var title6 = $("#title6").text();
	var title8 = $("#title8").text();
	var title7 = $("#title7").text();
	var title9 = $("#title9").text();
	var title10 = $("#title10").text();
	
	var targetAttr1 = $("#targetAttr1").val();
	var targetAttr2 = $("#targetAttr2").val();
	var targetAttr3 = $("#targetAttr3").val();
	var targetAttr4 = $("#targetAttr4").val();
	var targetAttr5 = $("#targetAttr5").val();
	var targetAttr6 = $("#targetAttr6").val();
	var targetAttr7 = $("#targetAttr7").val();
	var targetAttr8 = $("#targetAttr8").val();
	var targetAttr9 = $("#targetAttr9").val();
	var targetAttr10 = $("#targetAttr10").val();
	
	var targetDigit1 = $("#targetDigit1").val();
	var targetDigit2 = $("#targetDigit2").val();
	var targetDigit3 = $("#targetDigit3").val();
	var targetDigit4 = $("#targetDigit4").val();
	var targetDigit5 = $("#targetDigit5").val();
	var targetDigit6 = $("#targetDigit6").val();
	var targetDigit7 = $("#targetDigit7").val();
	var targetDigit8 = $("#targetDigit8").val();
	var targetDigit9 = $("#targetDigit9").val();
	var targetDigit10 = $("#targetDigit10").val();
	
	var tempObj = {
			glKb: glKb,
			glKbNm: glKbNm,
			dataList : dataList,
			haitaDate: haitaDate,
			haitaTime: haitaTime,
			title1: title1,
			title2: title2,
			title3: title3,
			title4: title4,
			title5: title5,
			title6: title6,
			title7: title7,
			title8: title8,
			title9: title9,
			title10: title10,
			targetAttr1: targetAttr1,
			targetAttr2: targetAttr2,
			targetAttr3: targetAttr3,
			targetAttr4: targetAttr4,
			targetAttr5: targetAttr5,
			targetAttr6: targetAttr6,
			targetAttr7: targetAttr7,
			targetAttr8: targetAttr8,
			targetAttr9: targetAttr9,
			targetAttr10: targetAttr10,
			targetDigit1: targetDigit1,
			targetDigit2: targetDigit2,
			targetDigit3: targetDigit3,
			targetDigit4: targetDigit4,
			targetDigit5: targetDigit5,
			targetDigit6: targetDigit6,
			targetDigit7: targetDigit7,
			targetDigit8: targetDigit8,
			targetDigit9: targetDigit9,
			targetDigit10: targetDigit10
		};
	$.ajax({
		type: "POST",
		url: "register",
		contentType: 'application/json; charset=utf-8',
		dataType: "json",
		data : JSON.stringify(tempObj),
		async: false,
		success: function(data) {
			var list = data.dataList;
			if(list != null && list.length > 0) {
				search();
				$("#haitaDate").val(data.haitaDate);
				$("#haitaTime").val(data.haitaTime);
				addInfoClass();
			} else {
				removeInfoClass();
			}
			$('#txtMess').html(data.message);
		},
		error: function(e) {
			
		},
		complete: function(jqXHR, textStatus) {
			enableButton("btnRegistration");
			isClickButton = false;
		}
	});
}
/**
 * 【入力チェック】
 */
function checkInput() {
	var errorID = chkItem($('#gpCode').val(), true, null, null, null);
	if (errorID != null){
		$("#txtMess").text($("#" + errorID).val().replace("{1}", "コード"));
		$("#gpCode").addClass("form-error-field");
		removeInfoClass();
		return true;
	}
	
	for(var i = 1; i <= 10; i++) {
		if($("#title" + i).text() != "") {
			if(isItemError(i,true)) {
				return true;
			}
			
		}
	}
	return false;
}
/**
 * 【存在チェック】
 */
function checkPesence() {
	var dataList = getDataList();
	var gpCode = $("#gpCode").val();
	for(var i = 0; i < dataList.length;  i++) {
		if(gpCode == dataList[i].glCode) {
			$('#txtMess').html($("#COM027-E").val());
			$("#gpCode").addClass("form-error-field").focus().select();
			removeInfoClass();
			return true;
		}
	}
	return false;
}

/**
 * ボタン名制御
 */
function buttonNameControl() {
	var editMode = $("#editMode").val();
	// [画面]権限追加＝チェックあり の場合
	if(editMode == ADD_MODE) {
		$("#btnAddUpdate").text("追加");
	} 
	
	// [画面]権限追加＝チェックなし の場合
	if(editMode == UPDATE_MODE) {
		$("#btnAddUpdate").text("更新");
	}
}

/**
 * Search process
 */
function search() {
	$.ajax({
		type: "POST",
		url: "search",
		data:$("form").serialize(),
		async : false,
		success: function(returnJsonData) {
			$('#txtMess').html('');
			var haveData = false;
			if(returnJsonData.dataList == null) {
				if(returnJsonData.messageType == 'body') {
					var htmlData = drawTblHead(returnJsonData);
					$("#tbl_thead").html(htmlData);	
					$("#txtMess").html("");
					$("#tbl_tbody").html(returnJsonData.errMessage);
				} else {
					$("#tbl_tbody").html("");
					$("#txtMess").html(returnJsonData.errMessage);
				}
				removeInfoClass();
			} else {
				drawTable(returnJsonData);
				srcRowList = getDataList();
				haveData = true;
			}
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
function drawTable(returnJsonData) {
	var htmlData = drawTblHead(returnJsonData);
	$("#tbl_thead").html(htmlData);	
	var data = returnJsonData.dataList;
	if(data != null && data.length > 0) {
		htmlData = "";
		//テーブルの表示	
		for (var i = 0 ; i < data.length ; i++){
			htmlData += "<tr>";
			htmlData += drawRow(i+1, data[i], returnJsonData);
			htmlData += "</tr>";
		}
		$("#tbl_tbody").html(htmlData);
		resizeTable();
		
	}
}

/**
 * Resize the table.
 */
function resizeTable () {
	var tblBodyHeight = $("#tblBody").height();
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
/**
 * テーブルを描く
 * @param　renban 連番
 * @param　行のデータ
 * @return 
 * @exception
 */
function drawRow(renban, dataRow,returnJsonData) {
	var htmlRow = "";
	htmlRow += "<td id='" + renban + "' name='"+ ReplaceNullReturnBlank(dataRow.glCode) +"' class='contextMenu align_center'><span>" + renban + "</span></td>";
	var codeDigit = $("#codeDigit").val();
	if(codeDigit == "0" || codeDigit == "") {
		htmlRow += "<td>---</td>";
	} else {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.glCode) + "</td>";
	}
	
	if (returnJsonData.title1 != null && returnJsonData.title1 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target1) + "</td>";
	} else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title2 != null && returnJsonData.title2 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target2) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title3 != null && returnJsonData.title3 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target3) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title4 != null && returnJsonData.title4 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target4) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title5 != null && returnJsonData.title5 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target5) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title6 != null && returnJsonData.title6 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target6) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title7 != null && returnJsonData.title7 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target7) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title8 != null && returnJsonData.title8 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target8) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	if (returnJsonData.title9 != null && returnJsonData.title9 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target9) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	if (returnJsonData.title10 != null && returnJsonData.title10 != "") {
		htmlRow += "<td>" + ReplaceNullReturnBlank(dataRow.target10) + "</td>";
	}else {
		htmlRow += "<td class='display_none'></td>";
	}
	
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.addFlg) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.updateFlg) + "</td>";
	htmlRow += "<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.deleteFlg) + "</td>";
	return htmlRow;
}

/**
 * Draw the table header.
 * 
 * @param data
 * @returns {String}
 */
function drawTblHead(data) {
	var htmlData = "";
	htmlData += "<tr>";
	htmlData += "<th>No</th>";
	htmlData += "<th>コード</th>";

	if (data.title1 != null && data.title1 != "") {
		htmlData += "<th>" + data.title1 + "</th>";
		$("#title1").html(data.title1);
	}
	if (data.title2 != null && data.title2 != "") {
		htmlData += "<th>" + data.title2 + "</th>";
		$("#title2").html(data.title2);
	}
	if (data.title3 != null && data.title3 != "") {
		htmlData += "<th>" + data.title3 + "</th>";
		$("#title3").html(data.title3);
	}
	if (data.title4 != null && data.title4 != "") {
		htmlData += "<th>" + data.title4 + "</th>";
		$("#title4").html(data.title4);
	}
	if (data.title5 != null && data.title5 != "") {
		htmlData += "<th>" + data.title5 + "</th>";
		$("#title5").html(data.title5);
	}
	if (data.title6 != null && data.title6 != "") {
		htmlData += "<th>" + data.title6 + "</th>";
		$("#title6").html(data.title6);
	}
	if (data.title7 != null && data.title7 != "") {
		htmlData += "<th>" + data.title7 + "</th>";
		$("#title7").html(data.title7);
	}
	if (data.title8 != null && data.title7 != "") {
		htmlData += "<th>" + data.title8 + "</th>";
		$("#title8").html(data.title8);
	}
	if (data.title9 != null && data.title9 != "") {
		htmlData += "<th>" + data.title9 + "</th>";
		$("#title9").html(data.title9);
	}
	if (data.title10 != null && data.title10 != "") {
		htmlData += "<th>" + data.title10 + "</th>";
		$("#title10").html(data.title10);
	}
	htmlData += "</tr>";
	return htmlData;
}

/**
 *  Get the row list on the table.
 * @returns {Array}
 */
function getDataList () {
	var dataList = [];
	$('#tblBody tbody tr').each(function() {
		var row = $(this);
		var renban = row.find("td").eq(0).text();
		var glCode = row.find("td").eq(1).text();
		var target1 = row.find("td").eq(2).text();
		var target2 = row.find("td").eq(3).text();
		var target3 = row.find("td").eq(4).text();
		var target4 = row.find("td").eq(5).text();
		var target5 = row.find("td").eq(6).text();
		var target6 = row.find("td").eq(7).text()
		var target7 = row.find("td").eq(8).text();
		var target8 = row.find("td").eq(9).text();
		var target9 = row.find("td").eq(10).text();
		var target10 = row.find("td").eq(11).text();
		var addFlg = row.find("td").eq(12).text();
		var updateFlg = row.find("td").eq(13).text();
		var deleteFlg = row.find("td").eq(14).text();
		var tempObj = {
			glCode: glCode,
			target1 : target1,
			target2 : target2,
			target3 : target3,
			target4 : target4,
			target5 : target5,
			target6 : target6,
			target7 : target7,
			target8 : target8,
			target9 : target9,
			target10 : target10,
			addFlg: addFlg,
			updateFlg: updateFlg,
			deleteFlg:deleteFlg
		};
		dataList.push(tempObj);
	});
	
	return dataList;
}

/**
 * Check at least one column is changed
 * 
 * @param row
 * @returns {Boolean}
 */
function isRowChange (row) {
	for(var i = 0; i < srcRowList.length; i++) {
		if(row.glCode == srcRowList[i].glCode
		&& (row.target1 != srcRowList[i].target1
		|| row.target2 != srcRowList[i].target2
		|| row.target3 != srcRowList[i].target3
		|| row.target4 != srcRowList[i].target4
		|| row.target5 != srcRowList[i].target5
		|| row.target6 != srcRowList[i].target6
		|| row.target7 != srcRowList[i].target7
		|| row.target8 != srcRowList[i].target8
		|| row.target9 != srcRowList[i].target9
		|| row.target10 != srcRowList[i].target10)
		) {
			return true;
		}
	}
	return false;
}

/**
 * Check that the table is empty
 * 
 * @returns {Boolean}
 */
function isTableEmpty () {
	var count = $('#tbl_tbody').children('tr').length;
	var count_display= $('#tbl_tbody').children('tr.display_none').length;
	if(count == count_display) {
		return true;
	} else {
		return false;
	}
}

/**
 * Check input for UI item.
 * 
 * @param colIndex
 * @returns {Boolean}
 */
function isItemError(colIndex, isAddUpdate) {
	$("#txtMess").text('');
	$('#itemValue' + colIndex).removeClass('form-error-field');
	
	var itemValue = $('#itemValue' + colIndex).val();
	var checkEmptyItem = chkEmpty(itemValue);
	if (checkEmptyItem != null){
		$("#txtMess").text($("#"+checkEmptyItem).val().replace("{1}", $("#title" + colIndex).text()));
		$("#itemValue" + colIndex).addClass("form-error-field");
		removeInfoClass();
		return true;
	}
	
	if($("#targetAttr" + colIndex).val() == "2" && chkNum($("#itemValue" + colIndex).val(), false) != null) {
		$('#txtMess').html($("#MST013-E1").val().replace("{1}","[画面]" + $("#title" + colIndex).text() + "数値で入力してください"));
		$("#itemValue" + colIndex).addClass("form-error-field");
		if(isAddUpdate) {
			$("#itemValue" + colIndex).focus().select();
		}
		removeInfoClass();
		return true;
	}

	var length = $("#itemValue" + colIndex).val().length;
	var targetDigit = parseInt($("#targetDigit" + colIndex).val(),10);
	if(targetDigit < length ) {
		$('#txtMess').html($("#MST013-E2").val().replace("{1}"," [画面]"+  $("#title" + colIndex).text()  +"（"+$("#targetDigit" + colIndex).val()+"）以内で入力してください"));
		$("#itemValue" + colIndex).addClass("form-error-field");
		if(isAddUpdate) {
			$("#itemValue" + colIndex).focus().select();
		}
		removeInfoClass();
		return true;
	}
	return false;
}