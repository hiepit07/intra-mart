/**
 * ファイル名:mst0104d01.js
 * 概要:コースマスタメンテナンス一覧画面
 * 
 * 作成者:ABV）NhungMa
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00                  ABV）NhungMa        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/
var DELIMITER = "##";
var isTableMenuShowing = false;
var currentShowingColId = "";
var strSelectedCourseCode = "";
var checkErrorFlag = false;
var SEARCH_MAX = "searchMax";
jQuery(document).ready(function($) {
	if ($("#txtMess").html().trim() == "") {
		searchData("getSearchResultInit");
		// 画面制御
		showControl();
		// フォーカスセット
		setFocus();
	}
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}
	
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});
	$(document).on("mouseover", ".contextMenu", function(){
		// change text to "▼" when user is hovering on a No column
		$(this).find("span").text("▼");
		// if menu is showing, hide menu when user is hovering on other
		// No columns
		if (isTableMenuShowing) {
			// only hide menu if user is hovering on other No columns,
			// not the column that is showing menu
			if ($(this).attr("id") != currentShowingColId) {
				// hide menu
				$("#tableMenu").fadeOut("fast");
				// switch flag value
				isTableMenuShowing = false;
				// change the text back from "▼" to number
				$("#" + currentShowingColId).find("span").text(
						currentShowingColId);
			}
		}
		// is menu is not showing, change the text back from "▼" to
		// number
		if ($(this).attr("id") != currentShowingColId) {
			$("#" + currentShowingColId).find("span").text(
					currentShowingColId);
			currentShowingColId = $(this).attr("id");
		}
	});
	
	$(document).on("click", ".contextMenu", function(e){
		// click event on No column
		// get current coordinates of mouse
		var x = e.pageX;
		var y = e.pageY;
		// show menu at current mouse's coordinates
		$("#tableMenu").css("position", "absolute").css("left",
				x + "px").css("top", y + "px").fadeIn("fast");
		// switch flag value
		isTableMenuShowing = true;
		strSelectedJigyoCode = $(this).attr("nameJigyoCode");
		strSelectedCourseCode = $(this).attr("nameCosCode");
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

	$(document).bind("click", function(e) {
		
		var container1 = $(".contextMenu");

		if (!container1.is(e.target) // if the target of the click
				// isn't the container
				&& container1.has(e.target).length === 0
				&& $("#tableMenu").is(":visible")) { // nor a
			// descendant of
			// the container
			$("#tableMenu").fadeOut("fast");
			$("#" + currentShowingColId).find("span").text(
					currentShowingColId);
			isTableMenuShowing = false;
		}
	});
	// ▼処理ＥＮＤ

	$("#viewSubMenu").bind("click", function() {
		submitForm(SHOUKAI_MODE, strSelectedJigyoCode, strSelectedCourseCode);
	});
	$("#modifySubMenu").bind("click", function() {
		submitForm(TEISEI_MODE, strSelectedJigyoCode, strSelectedCourseCode);
	});
	$("#deleteSubMenu").bind("click", function() {
		submitForm(TORIKESI_MODE, strSelectedJigyoCode, strSelectedCourseCode);
	});
	
	
	$("#btnNew").on("click", function(){
		//ボタンの2重押下防止
		disableButton("btnNew");
		submitForm(SHINKI_MODE,"");
		enableButton("btnNew");
	});
	// コースコードからフォーカスが外れた場合の処理。入力値の不足桁をゼロ編集する（５桁）
	$("#txtCourseCode").focusout(function() {
		var strBefore = $("#txtCourseCode").val();
		var strAfter = "";
		if (strBefore != "") {
			if (strBefore.length < LENGTH_COURSE_CODE) {
				strAfter = fillZero(strBefore);
				$("#txtCourseCode").val(strAfter);
			}
		}
	});
	
	/**
	 * クリアボタン(編集エリア)がクリックされた時の処理。入力エリアを初期化する
	 */
	$("#btnConditionClear").bind("click", function() {
		$("#txtMess").html("");
		$("input").removeClass("form-error-field");
		initSearchConditional();
		setFocus();
	});
	
	/**
	 * 検索ボタンが押下された場合の処理
	 */
	$("#btnSearch").bind("click", function() {
		//ボタンの2重押下防止
		disableButton("btnSearch");
		if(checkInput()) {
			$("#txtMess").html("");
			if(errorMessageCheckInput != "") {
				$("#txtMess").html(errorMessageCheckInput);
				var lstError = strIdCheckInput.split(DELIMITER);
				addClassErrorToControl(lstError);
				//ボタンの2重押下防止 可能
				enableButton("btnSearch");
			}
		} else {
			removeInfoClass();
			$("#txtCourseCode").removeClass("form-error-field");
			$("#txtCourseName").removeClass("form-error-field");
			$("#txtHaisoKb").removeClass("form-error-field");
			$("#tbl_tbody").empty();
			searchData("getSearchResult");
		}
		setFocus();
		errorMessageCheckInput = "";
		strIdCheckInput = "";
	});
	
	/**
	 * CSVボタンが押下された場合の処理
	 */
	$("#btnCsv").bind("click",function() {
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				disableButton("btnCsv");
				if(checkInput()) {
					$("#txtMess").html("");
					if(errorMessageCheckInput != "") {
						$("#txtMess").html(errorMessageCheckInput);
						var lstError = strIdCheckInput.split(DELIMITER);
						addClassErrorToControl(lstError);
						setFocus();
						enableButton("btnCsv");
					}
				} else {
					removeInfoClass();
					$("#txtCourseCode").removeClass("form-error-field");
					$("#txtCourseName").removeClass("form-error-field");
					$("#txtHaisoKb").removeClass("form-error-field");
					exportCSV();
				}
			}
			errorMessageCheckInput = "";
			strIdCheckInput = "";
		});
	});
	
	/**
	 * 入力チェック.
	 */
	var errorMessageCheckInput = "";
	var strIdCheckInput = "";
	function checkInput() {
		var inputCheckErrorFlag = false;
		var strErrorCheckInput = "";
		strErrorCheckInput = chkItem($("#txtCourseCode").val(), false, TYPE_NUM, false, null);
		if(strErrorCheckInput != null) {
			errorMessageCheckInput += $("#" + strErrorCheckInput).val().replace("{1}", "コースコード") + "<br>";
			strIdCheckInput += "txtCourseCode" + DELIMITER;
			inputCheckErrorFlag = true;
		} else {
			$("#txtCourseCode").removeClass("form-error-field");
		}
		strErrorCheckInput = chkItem($("#txtCourseName").val(), false, TYPE_EM, false, null);
		if(strErrorCheckInput != null) {
			errorMessageCheckInput += $("#" + strErrorCheckInput).val().replace("{1}", "コース名称") + "<br>";
			strIdCheckInput += "txtCourseName" + DELIMITER;
			inputCheckErrorFlag = true;
		} else {
			$("#txtCourseName").removeClass("form-error-field");
		}
		strErrorCheckInput = chkItem($("#txtHaisoKb").val(), false, TYPE_NUM, false, null);
		if(strErrorCheckInput != null) {
			errorMessageCheckInput += $("#" + strErrorCheckInput).val().replace("{1}", "配送区分") + "<br>";
			strIdCheckInput += "txtHaisoKb" + DELIMITER;
			inputCheckErrorFlag = true;
		} else {
			$("#txtHaisoKb").removeClass("form-error-field");
		}
		if($("#txtHaisoKb").val() != "" && $("#txtHaisoKb").val() != null
				&& ($("#txtHaisoKb").val() != JISHA_HAISO && $("#txtHaisoKb").val() != TAKUHAIBIN)) {
			errorMessageCheckInput += $("#MST013-E").val().replace("{1}", "配送区分は１または２で入力してください") + "<br>";
			strIdCheckInput += "txtHaisoKb" + DELIMITER;
			inputCheckErrorFlag = true;
		} else {
			$("#txtHaisoKb").removeClass("form-error-field");
		}
		return inputCheckErrorFlag;
	}
	
	/**
	 * サブメニューのSubmit処理
	 */
	function submitForm(strMode, strSelectedJigyoCode, strSelectCourseCode){	
		if (strMode == SHINKI_MODE) {
			$("#viewModeID").val(SHINKI_MODE_CHAR);;		
		} else if (strMode == SHOUKAI_MODE){
			$("#viewModeID").val(SHOUKAI_MODE_CHAR);
			$("#selectJigyoCodeID").val(strSelectedJigyoCode);
			$("#selectCourseCodeID").val(strSelectCourseCode);
		} else if (strMode == TEISEI_MODE) {
			$("#viewModeID").val(TEISEI_MODE_CHAR);
			$("#selectJigyoCodeID").val(strSelectedJigyoCode);
			$("#selectCourseCodeID").val(strSelectCourseCode);
		} else {
			$("#viewModeID").val(TORIKESI_MODE_CHAR);
			$("#selectJigyoCodeID").val(strSelectedJigyoCode);
			$("#selectCourseCodeID").val(strSelectCourseCode);
		}
		$("#formMst0104d01").submit();
	}
	
	/**
	 * 検索ボタンを押す処理
	 */
	function searchData(url) {
		$("#txtMess").html("");	
		$("#tbl_tbody").html("");
		$('input').removeClass('form-error-field');
		$.ajax({
			type : "POST",
			url : url,
			async : false,
			data : $("form").serialize(),
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(returnData) {
				var data = JSON.parse(returnData);
				if (data[0].message != null) {
					if (data[0].type == "body") {
						$("#txtMess").html("");
						$("#tbl_tbody").html(data[0].message);
					} else {
						$("#tbl_tbody").html("");
						$("#txtMess").html(data[0].message);
					}
					showButtonStatus(true);
				} else {
					$("#txtMess").html("");
					showtable(data);
					showButtonStatus(false);
				}
			},
			error : function(e) {
				$("#txtMess").val(EXCEPTION_ERROR);
			},
			complete : function(jqXHR, textStatus) {
				//ボタンの2重押下防止 可能
				enableButton("btnSearch");
			}
		});
	}
	
	/**
	 * テーブルの表示
	 */
	function showtable(data) {
		var htmlData = [];
		var searchMax = parseInt($("#searchMax").val()) + 1;
		// テーブルの表示
		for (var i = 0; i < data.length; i++) {
			if(data[i].jigyoCode != "" && data[i].jigyoCode != null) {
				if (data[i].stsCode == TORIKESHI) {
					htmlData.push("<tr class='tbl_del'>");
				} else {
					htmlData.push("<tr>");
				}
				if ((i + 1) < searchMax) {
					htmlData.push(drawRow(i + 1, data[i]));
				}
				htmlData.push("</tr>");
			} else {
				if(data[i].haitaDate != "" && data[i].haitaDate != null) {
					$("#haitaDate").val(data[i].haitaDate);
					$("#haitaTime").val(data[i].haitaTime);
				}
				if (data[i].type == SEARCH_MAX) {
					$("#txtMess").html(data[i].message);
				}
			}
		}
		
		document.getElementById('tbl_tbody').innerHTML = htmlData.join('');
		
		// table scroll
		var tblBodyHeight = $("#tblBody").height();
		var divBodyHeight = $("#divBody").height();
		if (tblBodyHeight > divBodyHeight) {
			$("#divBody").css("overflow-y", "scroll");
			$("#divHead").width($("#divBody").width() - 17);
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width() - 17);
			});
		} else {
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width());
			});
			$("#divHead").width($("#divBody").width());
			$("#divBody").css("overflow-y", "hidden");
		}
	}
	
	/**
	 * テーブルを描く
	 */
	function drawRow(renban, dataRow) {
		var htmlRow = [];
		htmlRow.push("<td class='contextMenu align_center' id='" + renban + "' nameJigyoCode='"
				+ ReplaceNullReturnBlank(dataRow.jigyoCode) + "' nameCosCode='" + ReplaceNullReturnBlank(dataRow.cosCode) +"'><span>" + renban
				+ "</span></td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.jgyMei) + "</td>");
		htmlRow.push("<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.jigyoCode) + "</td>");
		htmlRow.push("<td class='align_center'>"
					+ ReplaceNullReturnBlank(dataRow.cosCode) + "</td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.cosNm) + "</td>");
		htmlRow.push("<td class='align_center'>"
					+ ReplaceNullReturnBlank(dataRow.shippingTypeName) + "</td>");
		htmlRow.push("<td class='display_none'>" + ReplaceNullReturnBlank(dataRow.stsCode) + "</td>");
		return htmlRow.join('');
	}

	/**
	 * null値は""に変換する。
	 */
	function ReplaceNullReturnBlank(item) {
		return item = (item == null) ? '' : item;
	}
	
	/**
	 * ＣＳＶボタン状態の表示
	 */
	function showButtonStatus(bError) {
		if (bError) {
			// エラーがある場合
			disableButton("btnCsv");
		} else {
			enableButton("btnCsv");
		}
	}
	
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
	 * フォーカス処理
	 */
	function setFocus() {
		var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
		var sysAdminFlg = $("#sysAdminFlag").val();
		if (typeof idError === "undefined") {
			if (sysAdminFlg == SYS_ADMIN_FLAG_ADMIN) {
				$('#ddlJigyoCode').focus();
			} else if(sysAdminFlg == SYS_ADMIN_FLAG_USER){
				$('#txtCourseCode').focus();
			}
		} else {
			$("#"+idError).select();
			$("#"+idError).focus();
		}
	}

	/**
	 * 入力値を左ゼロ埋め５桁にする
	 */
	function fillZero(strInput) {
		var strResult = strInput;
		for (var i = strResult.length; i < LENGTH_COURSE_CODE; i++) {
			strResult = FILL_ZERO + strResult;
		}
		return strResult;
	}

	/**
	 * 条件項目をクリアする
	 */
	function initSearchConditional() {
		var sysAdminFlg = $("#sysAdminFlag").val();
		var loginJigyoshoCode = $("#loginJigyouShoCode").val();
		if(sysAdminFlg == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlJigyoCode").val(loginJigyoshoCode);
		}
		$("#txtCourseCode").val("");
		$("#txtCourseName").val("");
		$("#txtHaisoKb").val("");
		$("#chkCancellationData").prop('checked', false);
	}
	
	/**
	 * CSVボタンを押す処理
	 */
	function exportCSV() {
		$.ajax({
			type : "POST",
			url : "exportCsv",
			async : false,
			data : $("form").serialize(),
			success : function(returnData) {
				var data = JSON.parse(returnData);
				if (data.message != null) {
					$("#txtMess").html(data.message);
					showButtonStatus(true);
				} else {
					var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
					var data = JSON.parse(returnData);
					var path = rootPath + data.strPathFile;
					window.open(path);
					$("#txtMess").html("");
					showButtonStatus(false);
				}
			},
			error : function(e) {
				$("#txtMess").val(EXCEPTION_ERROR);
			},
			complete : function(jqXHR, textStatus) {
				//ボタンの2重押下防止 可能
				enableButton("btnCsv");
			}
		});
	}
});