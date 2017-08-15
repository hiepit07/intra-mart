/**
 * パッケージ名: ファイル名: com0102d04.js
 * 
 * 作成者:グエン　リユオン　ギア 
 * 作成日:2015/10/12
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/12 1.00 ABV)グエン　リユオン　ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

//---------------------------------------------------------------------
//定数
//---------------------------------------------------------------------
var urlPathCom0102d04 = "/com/COM01-02D04/";
//テーブルに縦スクロールが表示される行数
var tableRowCntCom0102d04 = 10;

jQuery(document).ready(function($) {	
	// 初期処理
	if ($("#txtDiaMess").val() !== undefined) {		
		if ($("#txtDiaMessage").val() == "") {		
			searchDataInitCom0102d04(); // 一覧検索
			setFocusCom0102d04(); // フォーカスセット
		} else {
			$("#tblSearchResult_body tbody").html("<div class='text_size_js' >"+ $("#COM025-EDia").val()+ "</div>");
		}
	}
	
	
	//エンターキー押下（タブ移動処理）
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this,"#divSubCondition");
		}
	});
	
	// 検索ボタン押下
	$("#searchButtonCom010204").bind("click", function() {
		$(".errorMessages").hide();
		searchDataCom0102d04(); // 一覧検索
	});
	
	//close dialog
	$("#cancelCom0102d04").bind("click", function() {
		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
		reTurnCheckTab();
	});	

	// 検索ボタンを押す
	function searchDataCom0102d04() {
		$("#txtDiaMess").html("");
		$("#noteOutput").hide(); // エラーメッセージ表示エリアを非表示
		$("#tblSearchResult_body tbody").html(""); // 一覧を初期化
		$("#tblSearchResult_body").css({'cssText' : 'width: 660px !important;'});
		$("#divSearchResult_body").css({'cssText' : 'height: 275px !important;'});
		
		var changeCodeWk = $("#diaChangeCodeWk").val();
		var changeBranchWk = $("#diaChangeBranchWk").val();		
		var ourCompanyItemCodeWk = $("#txtDiaOurCompanyItemCodeWk").val();
		var ourCompanyItemNameWk = $("#txtDiaOurCompanyItemNameWk").val();
		var customerCodeWk = $("#diaCustomerCodeWk").val();
		var saleTypeWk = $("#diaSaleTypeWk").val();
		var deadlineWk = $("#diaDeadlineWk").val();
		var flightWk = $("#diaFlightWk").val();
		var searchMax =$("#diaSearchMax").val();
		
		$.ajax({
			type : "POST",
			url : rootPath + urlPathCom0102d04 + "com0102d04getSearchResult",
			async : false,
			data : {
				changeCodeWk : changeCodeWk,
				changeBranchWk : changeBranchWk,
				ourCompanyItemCodeWk: ourCompanyItemCodeWk,
				ourCompanyItemNameWk : ourCompanyItemNameWk,
				customerCodeWk : customerCodeWk,
				saleTypeWk : saleTypeWk,
				deadlineWk : deadlineWk,
				flightWk : flightWk
			},
			success : function(returnData) {
				var data = JSON.parse(returnData);
				var errMsg = ""; // エラーメッセージ格納用変数
				if (data == null ||data.length == 0) {
					errMsg = $("#COM025-EDia").val(); // エラーメッセージ取得処理（ID:COM025-E）
					$("#tblSearchResult_body tbody").html("<div class='text_size_js' >"+ errMsg+ "</div>");
				} else {
					var showDataCnt = 0; // 一覧表示件数
					if (data.length > searchMax) {
						showDataCnt = $("#diaSearchMax").val().trim(); // 最大表示件数を超えた場合は、最大表示件数まで表示
						errMsg = $("#COM005-WDia").val().replace("{0}", showDataCnt); // エラーメッセージ取得処理（ID:COM005-W）
						$("#txtDiaMess").html(errMsg);
						$("#noteOutput").show();
						$("#divSearchResult_body").css({'cssText' : 'height: 250px !important;'});
					} else {
						$("#txtDiaMess").html("");						
						showDataCnt = data.length; // 最大表示件数以内の場合は、データ数分表示する
					}
					// 縦スクロール表示行数を超えている場合
					if(showDataCnt > tableRowCntCom0102d04) {
						// 一覧が子画面領域からはみ出るため、横幅を狭める
						$("#tblSearchResult_body").css({'cssText' : 'width: 640px !important;'});
					}
					showtableCom0102d04(data, showDataCnt); // 一覧表示処理
					setFocusCom0102d04(); // フォーカスセット
				}
			},
			error : function(e) {
				alert(e.message);
			},
			complete : function(jqXHR, textStatus) {

			}
		});
	}
	
	// 初期検索
	function searchDataInitCom0102d04() {
		$("#txtDiaMess").html("");
		$("#noteOutput").hide(); // エラーメッセージ表示エリアを非表示
		$("#tblSearchResult_body tbody").html(""); // 一覧を初期化
		$("#tblSearchResult_body").css({'cssText' : 'width: 660px !important;'});
		$("#divSearchResult_body").css({'cssText' : 'height: 275px !important;'});
		
		var changeCodeWk = $("#diaChangeCodeWk").val();
		var changeBranchWk = $("#diaChangeBranchWk").val();		
		var ourCompanyItemCodeWk = $("#txtDiaOurCompanyItemCodeWk").val();
		var customerCodeWk = $("#diaCustomerCodeWk").val();
		var saleTypeWk = $("#diaSaleTypeWk").val();
		var deadlineWk = $("#diaDeadlineWk").val();
		var flightWk = $("#diaFlightWk").val();
		var searchMax =$("#diaSearchMax").val();
		
		$.ajax({
			type : "POST",
			url : rootPath + urlPathCom0102d04 + "com0102d04getSearchInitResult",
			async : false,
			data : {
				changeCodeWk : changeCodeWk,
				changeBranchWk : changeBranchWk,
				ourCompanyItemCodeWk: ourCompanyItemCodeWk,
				customerCodeWk : customerCodeWk,
				saleTypeWk : saleTypeWk,
				deadlineWk : deadlineWk,
				flightWk : flightWk,
				searchMax : searchMax
			},
			success : function(returnData) {
				var data = JSON.parse(returnData);
				var errMsg = ""; // エラーメッセージ格納用変数
				if (data == null ||data.length == 0) {
					errMsg = $("#COM025-EDia").val(); // エラーメッセージ取得処理（ID:COM025-E）
					$("#tblSearchResult_body tbody").html("<div class='text_size_js' >"+ errMsg+ "</div>");
				} else {
					var showDataCnt = 0; // 一覧表示件数
					if (data.length > searchMax) {
						showDataCnt = $("#diaSearchMax").val().trim(); // 最大表示件数を超えた場合は、最大表示件数まで表示
						errMsg = $("#COM005-WDia").val().replace("{0}", showDataCnt); // エラーメッセージ取得処理（ID:COM005-W）
						$("#txtDiaMess").html(errMsg);
						$("#noteOutput").show();
						$("#divSearchResult_body").css({'cssText' : 'height: 250px !important;'});
					} else {
						$("#txtDiaMess").html("");						
						showDataCnt = data.length; // 最大表示件数以内の場合は、データ数分表示する
					}
					// 縦スクロール表示行数を超えている場合
					if(showDataCnt > tableRowCntCom0102d04) {
						// 一覧が子画面領域からはみ出るため、横幅を狭める
						$("#tblSearchResult_body").css({'cssText' : 'width: 640px !important;'});
					}
					showtableCom0102d04(data, showDataCnt); // 一覧表示処理
					setFocusCom0102d04(); // フォーカスセット
				}
			},
			error : function(e) {
				alert(e.message);
			},
			complete : function(jqXHR, textStatus) {

			}
		});
	}
	
	// フォーカスセット
	function setFocusCom0102d04() {
			$("#txtDiaOurCompanyItemCodeWk").focus();
			$("#txtDiaOurCompanyItemCodeWk").select(); // 得意先コードを全選択状態	
	}
	
	// テーブル表示
	function showtableCom0102d04(data, showDataCnt) {
		var htmlRow = "";
		var ItemCodeWk = ""; // 得意先コードID
		var ItemNameWk = ""; // 得意先名称ID
		for (var i = 0; i < showDataCnt; i++) {
			ItemCodeWk = "ItemCodeWkCom0102d04" + i; // 得意先コードID格納用変数
			ItemNameWk = "ItemNameWkCom0102d04" + i; // 得意先名称ID格納用変数
			htmlRow += "<tr>";
			htmlRow += "<td id=\"" + ItemCodeWk
					+ "\" class=\"w20 cursor_hover align_center add_clickCom0102d04\">" + data[i].cainCode
					+ "</td>";
			htmlRow += "<td id=\"" + ItemNameWk
					+ "\" class=\"cursor_hover add_clickCom0102d04\">" + data[i].cainIdx
					+ "</td>";
			htmlRow += "</tr>";
		}
		$("#tblSearchResult_body tbody").html(htmlRow);
	}	
});

