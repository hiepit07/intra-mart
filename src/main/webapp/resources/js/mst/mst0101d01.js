/**
 * ファイル名:mst0101d01.js 概要:担当者マスタ一覧画面
 * 
 * 作成者:ABV）コイー 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV）コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

var isTableMenuShowing = false;
var currentShowingColId = "";
var strSelectedUserCode = "";
jQuery(document).ready(function($) {
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0101D02";
	
	if ($("#txtMess").html().trim() == "") {
		searchData("getSearchResultInit");
		// 初期処理
		// ボタン制御
		showButtonStatus(false);
		// 画面制御
		showControl($('#sysAdminFlag').val());
		// フォーカスセット
		setFocus($('#sysAdminFlag').val());
	}
	
	// jStorageのクリア
	clearStorage(SCREEN_NAME);
	
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}

	// イベント作成
	$("#btnSearch").bind("click", function() {
		//ボタンの2重押下防止
		disableButton("btnSearch");
		searchData("getSearchResult");
	});
	$("#txtUserCode").focusout(function() {
		var strBefore = $("#txtUserCode").val();
		var strAfter = "";
		if (strBefore != "") {
			if (strBefore.length < 8) {
				strAfter = addLeadingChar(strBefore, 8);
				$("#txtUserCode").val(strAfter);
			}
		}
	});
	$("#btnConditionClear").bind("click", function() {		
		$("#txtMess").html("");
		initSearchConditional();
		setFocus($('#sysAdminFlag').val())
	});

	$("#btnNew").bind("click", function() {
		//ボタンの2重押下防止
		disableButton("btnNew");
		submitForm(SHINKI_MODE, "");
		enableButton("btnNew");
	});

	$("#btnCsv").bind("click",function() {
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				//ボタンの2重押下防止
				disableButton("btnCsv");
				exportCSV();
			}
		});
	});

	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
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
		strSelectedUserCode = $(this).attr("name");
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
		submitForm(SHOUKAI_MODE, strSelectedUserCode);
	});
	$("#modifySubMenu").bind("click", function() {
		submitForm(TEISEI_MODE, strSelectedUserCode);
	});
	$("#deleteSubMenu").bind("click", function() {
		submitForm(TORIKESI_MODE, strSelectedUserCode);
	});
	
	/**
	 * 条件項目をクリアする
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function initSearchConditional() {
		$("#ddlShozoku").val($("#loginJigyouShoCode").val());
		$("#txtUserCode").val("");
		$("#txtUserName").val("");
		$("#ddlRiyoKengen").val($("#ddlRiyoKengen option:first").val());
		$('#chkTorikeshiData').prop('checked', false);
		$('#chkLock').prop('checked', false);
	}

	/**
	 * 検索ボタンを押す処理
	 * 
	 * @param
	 * @return
	 * @exception
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
						//担当者名称をクリアする。
						$("#txtUserName").val("");
						$("#txtUserCode").addClass("form-error-field");
						$("#txtUserCode").select();
						$("#txtUserCode").focus();
					}
					showButtonStatus(true);
				} else {
					$("#txtMess").html("");
					showtable(data);
					showButtonStatus(false);						
				}
			},
			error : function(e) {
				$("#txtMess").html(EXCEPTION_ERROR);
			},
			complete : function(jqXHR, textStatus) {
				//ボタンの2重押下防止 可能
				enableButton("btnSearch");
			}
		});
	}
	
	/**
	 * CSVボタンを押す処理
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function exportCSV() {
		$('input').removeClass('form-error-field');
		$.ajax({
			type : "POST",
			url : "exportCsv",
			async : false,
			data : $("form").serialize(),
			success : function(returnData) {
				var data = JSON.parse(returnData);
				if (data.message != null) {
					$("#txtMess").html(data.message);					
					if (data.type == "COM009-E") {
						$("#txtUserCode").addClass("form-error-field");
						$("#txtUserCode").select().focus();
					}					
					showButtonStatus(true);
				} else {
					var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
					var data = JSON.parse(returnData);
					$("#txtUserName").html(data.strName);
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

	/**
	 * テーブルの表示
	 * 
	 * @param data テーブルのデータ
	 * @return
	 * @exception
	 */
	function showtable(data) {	
		var htmlData = [];
		var searchMax = parseInt($("#searchMax").val()) + 1;
		// テーブルの表示
		
		for (var i = 0; i < data.length; i++) {
			if (data[i].userCode != "" && data[i].userCode != null) {
				if (data[i].stsCode == "9") {
					htmlData.push("<tr class='tbl_del'>");
				}else {
					htmlData.push("<tr>");
				}
				if ((i + 1) < searchMax) {
					htmlData.push(drawRow(i + 1, data[i]));
				}
				htmlData.push("</tr>");
			} else {
				//担当者名を表示する
				if (data[i].userNm != "" && data[i].userNm != null) {
					if ($("#txtUserName").val() == "") {
						$("#txtUserName").val(data[i].userNm);
					}
				}					
				if (data[i].haitaDate != "" && data[i].haitaDate != null) {
					$("#haitaDate").val(data[i].haitaDate);
					$("#haitaTime").val(data[i].haitaTime);
				}
				if (data[i].type == "searchMax") {
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
			$("#divBody").css("overflow-y", "hidden");
		}
	}

	/**
	 * ＣＳＶボタン状態の表示
	 * 
	 * @param data テーブルのデータ
	 * @return
	 * @exception
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
	 * 
	 * @param [セッション]システム管理者フラグ
	 * @return
	 * @exception
	 */
	function showControl(strSysAdminFlag) {
		if (strSysAdminFlag == "1") {
			$('#ddlShozoku').show();
			$('#ddlShozokuLabel').show();
		} else {
			$('#ddlShozoku').hide()
			$('#ddlShozokuLabel').hide();
		}
	}

	/**
	 * フォーカス処理
	 * 
	 * @param [セッション]システム管理者フラグ
	 * @return
	 * @exception
	 */
	function setFocus(strSysAdminFlag) {
		if (strSysAdminFlag == "1") {
			$('#ddlShozoku').focus();
		} else {
			$('#txtUserCode').focus();
		}
	}

	/**
	 * テーブルを描く
	 * 
	 * @param renban 連番
	 * @param 行のデータ
	 * @return
	 * @exception
	 */
	function drawRow(renban, dataRow) {
		var htmlRow = [];

		htmlRow.push("<td class='contextMenu align_right' id='" + renban + "' name='"
				+ ReplaceNullReturnBlank(dataRow.userCode) + "'><span>" + renban
				+ "</span></td>");
		htmlRow.push("<td class='align_right'>"
				+ ReplaceNullReturnBlank(dataRow.userCode) + "</td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.userNm) + "</td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.targetAuth) + "</td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.jgymei) + "</td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.userPost) + "</td>");
		htmlRow.push("<td class='align_right'>"
				+ ReplaceNullReturnBlank(dataRow.telNo) + "</td>");
		htmlRow.push("<td class='align_right'>"
				+ ReplaceNullReturnBlank(dataRow.faxNo) + "</td>");
		htmlRow.push("<td class='align_right'>"
				+ ReplaceNullReturnBlank(dataRow.targetUserStatus) + "</td>");
		return htmlRow.join('');
	}

	/**
	 * null値は""に変換する。
	 * 
	 * @param item 項目
	 * @return
	 * @exception
	 */
	function ReplaceNullReturnBlank(item) {
		return item = (item == null) ? '' : item;
	}

	/**
	 * サブメニューのSubmit処理
	 * 
	 * @param strMode モード値
	 * @param strSelectUserCode 選択した担当者コード
	 * @return
	 * @exception
	 */
	function submitForm(iMode, strSelectUserCode) {
		if (iMode == SHINKI_MODE) {
			$("#viewModeID").val("1");
		} else if (iMode == SHOUKAI_MODE) {
			$("#viewModeID").val("2");
			$("#selectUserCodeID").val(strSelectUserCode);
		} else if (iMode == TEISEI_MODE) {
			$("#viewModeID").val("3");
			$("#selectUserCodeID").val(strSelectUserCode);
		} else {
			$("#viewModeID").val("4");
			$("#selectUserCodeID").val(strSelectUserCode);
		}
		$("#formMst0101d01").submit();
	}
});
