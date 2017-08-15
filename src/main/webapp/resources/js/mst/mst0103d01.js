/**
 * ファイル名:mst0103d01.js
 * 概要:店舗マスタメンテナンス画面
 * 
 * 作成者:ABV）TramChu
 * 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00                  ABV）TramChu        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

var isTableMenuShowing = false;
var currentShowingColId = "";
var strSelectedCustomerCode = "";
var strSelectedStoreCode = "";
var strSystemAdminFlg = "";
txtCustomerCode = "";
txtStoreCode = "";
jQuery(document).ready(function($) {
	loadDialogCom0102d02();
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0103D02";
	strSystemAdminFlg = $("#sysAdminFlag").val();
	if ($("#txtMess").html().trim() == "") {
		searchData("getSearchResultInit");
		// 初期処理
		// フォーカスセット
		setFocus(strSystemAdminFlg);
	}
	var errControl = $('#errControl').val();
	if (errControl == "1") {
		errorControl();
		$('#errControl').val('');
		return;
	}
	// jStorageのクリア
	clearStorage(SCREEN_NAME);
	// 得意先コードにフォーカスが当たった場合の処理
	$("#txtCustomerCode").focus(function() {
		txtCustomerCode = $("#txtCustomerCode").val();
	});
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtCustomerCode").focusout(function() {
		if(txtCustomerCode != $("#txtCustomerCode").val()) {
			$("#customerName").html("");
		}
	});
	// 店舗コードにフォーカスが当たった場合の処理
	$("#txtStoreCode").focus(function() {
		txtStoreCode = $("#txtStoreCode").val();
	});
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtStoreCode").focusout(function() {
		if(txtStoreCode != $("#txtStoreCode").val()) {
			$("#storeName").html("");
		}
	});
	// 条件クリアボタンが押下された場合の処理
	$("#btnConditionClear").bind("click", function() {
		$("#txtMess").html("");
		$('input').removeClass('form-error-field');
		$('select').removeClass('form-error-field');
		initSearchConditional();
		setFocus(strSystemAdminFlg)
	});
	
	$("#btnNew").bind("click", function() {
		submitForm(SHINKI_MODE,"","");
	});
	// CSVボタン
	$("#btnCsv").bind("click",function() {
		disableButton("btnCsv");
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {				
				exportCSV();
			} else {
				enableButton("btnCsv");
			}
		});
	});
	// 検索ボタンが押下された場合の処理
	$("#btnSearch").bind("click", function() {
		searchData("getSearchResult");
	});
	//Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this,".data_conditions_area_cont");
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
		strSelectedCustomerCode = $(this).parent().find("td").eq(1).text();
		strSelectedStoreCode = $(this).parent().find("td").eq(3).text();
		
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
		submitForm(SHOUKAI_MODE, strSelectedCustomerCode,strSelectedStoreCode);
	});
	$("#modifySubMenu").bind("click", function() {
		submitForm(TEISEI_MODE, strSelectedCustomerCode,strSelectedStoreCode);
	});
	$("#deleteSubMenu").bind("click", function() {
		submitForm(TORIKESI_MODE, strSelectedCustomerCode,strSelectedStoreCode);
	});

	/**
	 * CSVボタンが押下された場合の処理
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
				if (data != "") {
					if (data[0].message != null) {
						if (data[0].type == "body") {
							$("#txtMess").html(data[0].message);
							for (var i = 1; i < data.length; i++) {
								if (data[i].type == "customerInfo") {
									$("#txtCustomerCode").val(data[i].custNmR);
									$("#customerName").html(data[i].message);
								} else if (data[i].type == "shopInfo") {
									$("#txtStoreCode").val(data[i].shopCode);
									$("#storeName").html(data[i].shopNm);
								}
							}
						} else if (data[0].type == "msgEr") {
							var html = "";
							for (var j = 0; j < data.length; j++) {
								if (data[j].type == "msgEr") {
									html += data[j].message + "<br>";
									$("#" + data[j].idClear).html("");
									if (data[j].idClear == "customerName") {
										$("#txtCustomerCode").select();
										$("#txtCustomerCode").focus();
										$("#txtCustomerCode").addClass("form-error-field");
									} else {
										$("#txtStoreCode").select();
										$("#txtStoreCode").addClass("form-error-field");
										$("#txtStoreCode").focus();
									}
								}
							}
							if (data[0].idClear == "customerName") {
								$("#txtCustomerCode").select();
								$("#txtCustomerCode").addClass("form-error-field");
								$("#txtCustomerCode").focus();
							} else {
								$("#txtStoreCode").select();
								$("#txtStoreCode").addClass("form-error-field");
								$("#txtStoreCode").focus();
							}
							$("#txtMess").html(html);
							for (var i = 1; i < data.length; i++) {
								if (data[i].type == "customerInfo") {
									$("#txtCustomerCode").val(data[i].custNmR);
									$("#customerName").html(data[i].message);
								} else if (data[i].type == "shopInfo") {
									$("#txtStoreCode").val(data[i].shopCode);
									$("#storeName").html(data[i].shopNm);
								}
							}
						}
					} else {
						var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
						for (var i = 1; i < data.length; i++) {
							if (data[i].type == "customerInfo") {
								$("#txtCustomerCode").val(data[i].custNmR);
								$("#customerName").html(data[i].message);
							} else if (data[i].type == "shopInfo") {
								$("#txtStoreCode").val(data[i].shopCode);
								$("#storeName").html(data[i].shopNm);
							}
						}
						$("#txtMess").html("");
						var path = rootPath + data[0].adr1;
						window.open(path);
					}
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
	 * 条件項目をクリアする
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function initSearchConditional() {
		var loginJigyoshoCode = $("#loginJigyouShoCode").val();
		$("#ddlOffice").val(loginJigyoshoCode);
		$("#txtCustomerCode").val("");
		$("#txtCustomerName").val("");
		$("#txtStoreCode").val("");
		$("#txtStoreName").val("");
		$("#storeName").html("");
		$("#customerName").html("");
		$("#chkCancelData").prop("checked",false);
		enableImgButton("btn_search_customer_id");
	}


	/**
	 * 検索ボタンを押す処理
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function searchData(url) {
		$("#tbl_tbody").html("");
		$('input').removeClass('form-error-field');
		$('select').removeClass('form-error-field');
		$.ajax({
			type : "POST",
			url : url,
			async : false,
			data : $("form").serialize(),
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(returnData) {
				var data = JSON.parse(returnData);
				if (data != "") {
					if (data[0].message != null) {
						if (data[0].type == "body") {
							$("#txtMess").html("");
							$("#tbl_tbody").html(data[0].message);
						} else if (data[0].type == "msgEr") {
							var html = "";
							for (var j = 0; j < data.length; j++) {
								if (data[j].type == "msgEr") {
									html += data[j].message + "<br>";
									$("#" + data[j].idClear).html("");
									if (data[j].idClear == "customerName") {
										$("#txtCustomerCode").select();
										$("#txtCustomerCode").focus();
										$("#txtCustomerCode").addClass("form-error-field");
									} else {
										$("#txtStoreCode").select();
										$("#txtStoreCode").addClass("form-error-field");
										$("#txtStoreCode").focus();
									}
								}
							}
							if (data[0].idClear == "customerName") {
								$("#txtCustomerCode").select();
								$("#txtCustomerCode").focus();
								$("#txtCustomerCode").addClass("form-error-field");
							} else {
								$("#txtStoreCode").select();
								$("#txtStoreCode").addClass("form-error-field");
								$("#txtStoreCode").focus();
							}
							$("#tbl_tbody").html("");
							$("#txtMess").html(html);
							for (var i = 1; i < data.length; i++) {
								if (data[i].type == "customerInfo") {
									$("#txtCustomerCode").val(data[i].custNmR);
									$("#customerName").html(data[i].message);
								} else if (data[i].type == "shopInfo") {
									$("#txtStoreCode").val(data[i].shopCode);
									$("#storeName").html(data[i].shopNm);
								}
							}
						}
						else {
							$("#tbl_tbody").html("");
							$("#txtMess").html(data[0].message);
						}
						$("#btnCsv").attr('disabled', 'disabled');
						$("#btnCsv").removeClass("btn_button").addClass("btn_button_disabled");
					} else {
						$("#txtMess").html("");
						showtable(data);
						$("#btnCsv").removeAttr('disabled');
						$("#btnCsv").removeClass("btn_button_disabled").addClass("btn_button");
					} 
				}
			},
			error : function(e) {
				// TODO エラーメッセージ
				$("#txtMess").html("例外エラー");
			},
			complete : function(jqXHR, textStatus) {
			}
		});
	}



	/**
	 * テーブルの表示
	 * @param data　テーブルのデータ
	 * @return 
	 * @exception
	 */
	function showtable(data){
		// table scroll
		var htmlData = [];
		var businessDate = parseInt($("#businessDate").val());
		var searchMax = parseInt($("#searchMax").val()) + 1 ;
		// テーブルの表示
		for (var i = 0; i < data.length; i++) {
			if (data[i].custCode != "" && data[i].custCode != null) {
				if (data[i].stsCode == "9" || parseInt(data[i].closeDate) <= businessDate) {
					htmlData.push( "<tr class='bg_gray'>");
				}else {
					htmlData.push("<tr>");
				}
				if ((i + 1) < searchMax) {
					htmlData.push(drawRow(i + 1, data[i]));
				}
				htmlData.push("</tr>");
			} else if (data[i].type == "customerInfo") {
				$("#txtCustomerCode").val(data[i].custNmR);
				$("#customerName").html(data[i].message);
			} else if (data[i].type == "shopInfo") {
				$("#txtStoreCode").val(data[i].shopCode);
				$("#storeName").html(data[i].shopNm);
			} else if (data[i].type == "searchMax") {
				$("#txtMess").html(data[i].message);
			} else if (data[i].type == "haita") {
				$("#haitaDate").val(data[i].updymd);
				$("#haitaTime").val(data[i].updtime);
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
			$("#divHead").width($("#divBody").width());
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width());
			});
		}
	}

	/**
	 * フォーカス処理
	 * @param [セッション]システム管理者フラグ
	 * @return 
	 * @exception
	 */
	function setFocus(strSysAdminFlag){
		if (strSysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$('#ddlOffice').focus();
		}else if (strSysAdminFlag == SYS_ADMIN_FLAG_USER){
			$('#txtCustomerCode').focus();
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
		var htmlRow = [];
		htmlRow.push('<td class="align_center contextMenu" id="'+ renban + '"><span>' + renban + '</span></td>');
		htmlRow.push('<td class="align_center">' + ReplaceNullReturnBlank(dataRow.custCode) + '</td>');
		htmlRow.push('<td>' + ReplaceNullReturnBlank(dataRow.custNmR) + '</td>');
		htmlRow.push('<td>' + ReplaceNullReturnBlank(dataRow.shopCode) + '</td>');
		htmlRow.push('<td>' + ReplaceNullReturnBlank(dataRow.shopNmR) + '</td>');
		htmlRow.push('<td class="align_center">' + ReplaceNullReturnBlank(dataRow.jigyoCode) + '</td>');
		htmlRow.push('<td>' + ReplaceNullReturnBlank(dataRow.jgymei) + '</td>');
		htmlRow.push('<td class="align_center">' + ReplaceNullReturnBlank(dataRow.zipCode) + '</td>');
		htmlRow.push('<td>' + ReplaceNullReturnBlank(dataRow.adr1) + ReplaceNullReturnBlank(dataRow.adr2)+'</td>');
		if (dataRow.telNo == "--") {
			htmlRow.push('<td class="align_center"></td>');
		} else {
			htmlRow.push('<td class="align_center">' + ReplaceNullReturnBlank(dataRow.telNo) + '</td>');
		}
		return htmlRow.join('');
	}

	/**
	 * null値は""に変換する。
	 * 
	 * @param item
	 *            項目
	 * @return
	 * 
	 * @exception
	 */
	function ReplaceNullReturnBlank(item) {
		return item = (item == null) ? '' : item;
	}

	/**
	 * サブメニューのSubmit処理
	 * @param　strMode　モード値
	 * @param　strSelectUserCode　選択した担当者コード
	 * @return 
	 * @exception
	 */
	function submitForm(strMode, strSelectCustomerCode, strSelectStoreCode){	
		if (strMode == SHINKI_MODE) {
			$("#viewModeID").val(SHINKI_MODE_CHAR);
			$("#selectCustomerCode").val(strSelectCustomerCode);
			$("#selectStoreCode").val(strSelectStoreCode);
		}else if (strMode == SHOUKAI_MODE){
			$("#viewModeID").val(SHOUKAI_MODE_CHAR);
			$("#selectCustomerCode").val(strSelectCustomerCode);
			$("#selectStoreCode").val(strSelectStoreCode);
		}else if (strMode == TEISEI_MODE) {
			$("#viewModeID").val(TEISEI_MODE_CHAR);
			$("#selectCustomerCode").val(strSelectCustomerCode);
			$("#selectStoreCode").val(strSelectStoreCode);
		}else {
			$("#viewModeID").val(TORIKESI_MODE_CHAR);
			$("#selectCustomerCode").val(strSelectCustomerCode);
			$("#selectStoreCode").val(strSelectStoreCode);
		}
		$("#formMst0103d01").submit();
	}	

	// 得意先検索画面表示
	$(document).on("click", "#btn_search_customer_id", function(e) {
		if (isBtnImgEnabled("btn_search_customer_id")) {
			var strJigyo = "";
			var sysAdminFlg = $("#sysAdminFlag").val();
			if (sysAdminFlg == SYS_ADMIN_FLAG_USER) {			
				strJigyo = $("#loginJigyouShoCode").val();
			} else if(sysAdminFlg == SYS_ADMIN_FLAG_ADMIN) {
				strJigyo = $("#ddlOffice").find('option:selected').val();			
			}
			var custCode = $("#txtCustomerCode").val();
			var shopKb = THERE_STORE;
			var searchKb = CUSTOMERS;
			// 検索子画面呼び出し用関数を呼び出す
	        showCom0102d02(strJigyo, custCode, shopKb, searchKb);
		}
	});
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
		$("#txtCustomerCode").val(data[0].custCode);
		$("#customerName").text(data[0].custNmR);
		$("#txtStoreCode").focus().select();
	}
	reTurnCheckTab();
}

