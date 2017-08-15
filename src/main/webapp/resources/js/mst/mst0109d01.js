/**
 * ファイル名:mst0109d01.js
 * 概要:訂正区分一覧画面
 * 
 * 作成者:ABV）コイー
 * 作成日:2015/10/20
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/20 1.00 ABV）コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

//値の定義
var CUST_CODE_EMPTY="-------";
var CUST_NAME_EMPTY="共通設定";
//Controlエラーの受信デリミター
var txtCustCode0109="";
//▼処理
var isTableMenuShowing = false;
var currentShowingColId = "";
var strSelectedCustCode = "";
var strSelectedCorrectKb = "";

jQuery(document).ready(function($) {
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0109D02";
	
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}
	
	if ($("#txtMess").html().trim() == "") {
		searchData("getSearchResultInit");
		showControl($('#sysAdminFlag').val());
		// フォーカスセット
		setFocus($('#sysAdminFlag').val());
	}
	
	// jStorageのクリア
	clearStorage(SCREEN_NAME);
	
	//得意先ダイアログのイベントロード
	loadDialogCom0102d02(); 
	
	//得意先ダイアログのイベント
	$("#btn_search_customer_id").bind("click", function() {
		//[変数]事業所コード
		var strJigyo = $("#ddlOyaShozoku").val();
		if(strJigyo != undefined){
			if (strJigyo == "" || strJigyo == null) {
				strJigyo = "";
			}
		} else {
			strJigyo = "";
		}
		var custCode = $("#txtCustCode").val();//[画面]得意先コード
		var shopKb = ""; // Null
		var searchKb = "1";// '1'（得意先）
		// 検索子画面呼び出し用関数を呼び出す
		showCom0102d02(strJigyo, custCode, shopKb, searchKb);
	});
	
	// イベント作成
	//検索ボタンクリック
	$("#btnSearch").bind("click", function() {
		//ボタンの2重押下防止
		disableButton("btnSearch");
		searchData("getSearchResult");
	});
	
	//クリアボタンクリック
	$("#btnClear").bind("click", function() {
		$("#txtMess").html("");
		//ボタンの2重押下防止
		disableButton("btnClear");
		initSearchConditional();
		enableButton("btnClear");
		setFocus($('#sysAdminFlag').val())
	});
	
	//CSVボタンクリック
	$("#btnCsv").bind("click",function() {
		//ボタンの2重押下防止
		disableButton("btnCsv");
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {				
				exportCSV();
			} else {
				enableButton("btnCsv");
			}			
		});
	});
	
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});	
	
	//新規ボタンクリック
	$("#btnNew").bind("click", function() {
		//ボタンの2重押下防止
		disableButton("btnNew");
		submitForm(SHINKI_MODE);
		enableButton("btnNew");
	});
	
	
	
	//以下の通りにフォカス処理
	// 得意先コードにフォーカスが当たった場合の処理
	$("#txtCustCode").focus(function() {
		txtCustCode0109 = $("#txtCustCode").val();
	});
	
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtCustCode").focusout(function() {
		if(txtCustCode0109 != $("#txtCustCode").val()) {
			$("#lblCustNmR").html("");
		}
	});
	
	//以下の通りにメニュー処理
	//▼処理START
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
		strSelectedCustCode = $(this).parent().find("td").eq(1).text();
		strSelectedCorrectKb = $(this).parent().find("td").eq(3).text();		
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
	
	//照会メニュークリック
	$("#viewSubMenu").bind("click", function() {
		submitForm(SHOUKAI_MODE);
	});
	
	//訂正メニュークリック
	$("#modifySubMenu").bind("click", function() {
		submitForm(TEISEI_MODE);
	});
	
	//取消メニュークリック
	$("#deleteSubMenu").bind("click", function() {
		submitForm(TORIKESI_MODE);
	});
	
	
	/**
	 * 検索ボタンを押す処理
	 * 
	 * @param　url:サーバでの巻数コール
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
						$("#txtMess").html(data[0].message);
						setBGError(data[0].lstId);						
						setErrorFocus();
					}
					showButtonStatus(true);
				} else {
					$("#txtMess").html("");
					showtable(data);
					if (data.length <= 1) {
						showButtonStatus(true);
					} else {
						showButtonStatus(false);
					}
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
			if (data[i].custCode != "" && data[i].custCode != null) {
				htmlData.push("<tr>");
				if (data[i].stsCode == TORIKESHI) {
					htmlData.push("<tr class='tbl_del'>");
				}else {
					htmlData.push("<tr>");
				}
				if ((i + 1) < searchMax) {
					htmlData.push(drawRow(i + 1, data[i]));
				}
				htmlData.push("</tr>");
			} else {
				//[画面]得意先名称（表示用） ＝ [変数]得意先情報格納クラス.得意先略称
				if (data[i].custNmR != "" && data[i].custNmR != null) {					
					$("#lblCustNmR").html(data[i].custNmR);					
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
	 * テーブルを描く
	 * 
	 * @param renban 連番
	 * @param 行のデータ
	 * @return
	 * @exception
	 */
	function drawRow(renban, dataRow) {
		var htmlRow = [];
		htmlRow.push("<td class='contextMenu align_right' id='" + renban + "'><span>" + renban + "</span></td>");
		//[入力]訂正区分マスタ一覧情報格納クラス[i].得意先コード ＝  [画面_hidden]設定情報）得意先コード_未指定 の場合、
		if ($("#custCodeNone").val() == dataRow.custCode.trim()) {
			htmlRow.push("<td>"
					+ CUST_CODE_EMPTY + "</td>");
			htmlRow.push("<td>" + CUST_NAME_EMPTY + "</td>");
		} else {
			//[入力]訂正区分マスタ一覧情報格納クラス[i].得意先コード ≠  [画面_hidden]設定情報）得意先コード_未指定 の場合、
			htmlRow.push("<td>"
					+ ReplaceNullReturnBlank(dataRow.custCode) + "</td>");
			htmlRow.push("<td>"
					+ ReplaceNullReturnBlank(dataRow.custNmR) + "</td>");
		}	
		
		htmlRow.push("<td class='align_right'>" + ReplaceNullReturnBlank(dataRow.correctKb) + "</td>");
		htmlRow.push("<td>" + ReplaceNullReturnBlank(dataRow.correctCause) + "</td>");
		htmlRow.push("<td class='align_right'>" + ReplaceNullReturnBlank(dataRow.zeroDlvdatFlg) + "</td>");
		htmlRow.push("<td>"
				+ ReplaceNullReturnBlank(dataRow.biKou) + "</td>");
		htmlRow.push("<td class='align_right'>"
				+ ReplaceNullReturnBlank(dataRow.stsCode) + "</td>");
		return htmlRow.join('');
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
					setBGError(data.type);						
					setErrorFocus();
					showButtonStatus(true);
				} else {
					var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
					var data = JSON.parse(returnData);
					$("#lblCustNmR").html(data.strName);
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
		if (strSysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$('#ddlOyaShozoku').show();
			$('#ddlShozokuLabel').show();
		} else {
			$('#ddlOyaShozoku').hide()
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
		if (strSysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$('#ddlOyaShozoku').focus();
		} else {
			$('#txtCustCode').focus().select();;
		}
	}
	
	/**
	 * エラー時項目はエラー背景を設定する。
	 * @param サーバから、メッセージ内容の処理
	 * @exception
	 */	
	function setBGError(errorMess) {
		if (errorMess != null && errorMess != "") {
			var lstError = errorMess.split(COMMON_DELIMITER);
			addClassErrorToControl(lstError);
		}
	}
	
	/**
	 * フォーカス処理
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	function setErrorFocus() {
		var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
		$("#"+idError).focus().select();
	}
	
	/**
	 * 条件項目をクリアする
	 * 初期表示
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function initSearchConditional() {
		$("#ddlOyaShozoku").val($("#loginJigyouShoCode").val());
		$("#txtCustCode").val("");
		$("#lblCustNmR").html("");
		$("#txtCustNmR").val("");
		$("#txtCorrectKb").val("");
		$("#txtZeroDlvdatFlg").val("");
		$('#chkStsCode').prop('checked', false);
		$('#chkStKb').prop('checked', true);
	}
	
	/**
	 * サブメニューのSubmit処理
	 * 
	 * @param strMode モード値
	 * @return
	 * @exception
	 */
	function submitForm(iMode) {
		if (iMode == SHINKI_MODE) {
			//選択行の訂正区分マスタ情報を取得する
			$("#viewModeID").val(SHINKI_MODE_CHAR);
			$("#selectCorrectKb").val("");
			$("#selectCustCode").val("");
		}
		else if (iMode == SHOUKAI_MODE) {
		 	//選択行の訂正区分マスタ情報を取得する
			$("#viewModeID").val(SHOUKAI_MODE_CHAR);
			$("#selectCorrectKb").val(strSelectedCorrectKb);
			$("#selectCustCode").val(strSelectedCustCode);
		} else if (iMode == TEISEI_MODE) {
			//選択行の訂正区分マスタ情報を取得する
			$("#viewModeID").val(TEISEI_MODE_CHAR);
			$("#selectCorrectKb").val(strSelectedCorrectKb);
			$("#selectCustCode").val(strSelectedCustCode);
		} else {
			//選択行の訂正区分マスタ情報を取得する
			$("#viewModeID").val(TORIKESI_MODE_CHAR);
			$("#selectCorrectKb").val(strSelectedCorrectKb);
			$("#selectCustCode").val(strSelectedCustCode);
		}
		$("#formMst0109d01").submit();
	}
});

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
		$("#txtCustCode").val(data[0].custCode);
		$("#lblCustNmR").html(data[0].custNm);		
		$("#txtCustNmR").focus().select();
	}
	reTurnCheckTab();
}
