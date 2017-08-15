/**
 * ファイル名: com0102d03.js
 * 
 * 作成者:グエン　リユオン　ギア 
 * 作成日:2015/10/07
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)グエン　リユオン　ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

var urlPathCom0102d03 = "/com/COM01-02D03/";
//テーブルに縦スクロールが表示される行数
var tableRowCntCom0102d03 = 10;

jQuery(document).ready(function($) {
	// 初期処理
	if ($("#txtDiaMess").val() !== undefined) {
		searchDataCom0102d03(); // 一覧検索
		setFocusCom0102d03(); // フォーカスセット
	}
	
	//エンターキー押下（タブ移動処理）
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this,".divSubCondition");
		}
	});
	
	
	// 検索ボタン押下
	$("#searchButtonCom010203").bind("click", function() {
		$(".errorMessages").hide();
		searchDataCom0102d03(); // 一覧検索
	});
	
	//閉じるボタン押下
	$("#cancelCom0102d03").bind("click", function() {
		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
		reTurnCheckTab();	
	});
	
	// function SearchData
	function searchDataCom0102d03() {
		$("#txtDiaMess").html("");
		$("#noteOutput").hide(); // エラーメッセージ表示エリアを非表示
		$("#tblSearchResult_body tbody").html(""); // 一覧を初期化
		$("#tblSearchResult_body").css({'cssText' : 'width: 660px !important;'});
		$("#divSearchResult_body").css({'cssText' : 'height: 250px !important;'});
		
		
		//関数は、現在の日付を取ります		
		var custCode = $("#txtDiaCustCode").text();
		custCode = replaceNum(custCode);
		var shopCode = $("#txtDiaShopCode").val().trim();
		shopCode = replaceNum(shopCode);
		var shopName = $("#txtDiaShopNm").val().trim();
		shopName = replaceNum(shopName);
		var searchMax =$("#diaSearchMax").val();
		
		
		$.ajax({
			type : "POST",
			url : rootPath + urlPathCom0102d03 + "com0102d03getSearchResult",
			async : false,
			data : "custCode=" + custCode + "&shopCode=" + shopCode + "&shopName=" + shopName,
			success : function(returnData) {
				var data = JSON.parse(returnData);
				var errMsg = ""; // エラーメッセージ格納用変数
				if (data.length == 0) {
					errMsg = $("#COM025-EDia").val(); // エラーメッセージ取得処理（ID:COM025-E）
					$("#tblSearchResult_body tbody").html("<div class='text_size_js' >"+ errMsg+ "</div>");
				} else {
					var showDataCnt = 0; // 一覧表示件数
					if (data.length > searchMax) {
						showDataCnt = $("#diaSearchMax").val().trim(); // 最大表示件数を超えた場合は、最大表示件数まで表示
						errMsg = $("#COM005-WDia").val().replace("{0}", showDataCnt); // エラーメッセージ取得処理（ID:COM005-W）
						$("#txtDiaMess").html(errMsg);
						$("#noteOutput").show();
						$("#divSearchResult_body").css({'cssText' : 'height: 225px !important;'});
					} else {
						$("#txtDiaMess").html("");						
						showDataCnt = data.length; // 最大表示件数以内の場合は、データ数分表示する
					}
					// 縦スクロール表示行数を超えている場合
					if(showDataCnt > tableRowCntCom0102d03) {					
						// 一覧が子画面領域からはみ出るため、横幅を狭める
						$("#tblSearchResult_body").css({'cssText' : 'width: 640px !important;'});
					}
					showtableCom0102d03(data, showDataCnt); // 一覧表示処理
					setFocusCom0102d03(); // フォーカスセット
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
	function setFocusCom0102d03() {
			$("#txtDiaShopCode").focus();
			$("#txtDiaShopCode").select(); // 得意先コードを全選択状態	
	}
	
	// テーブル表示
	function showtableCom0102d03(data, showDataCnt) {
		var htmlRow = "";
		var idshopCode = ""; // 得意先コードID
		var idshopName = ""; // 得意先名称ID
		for (var i = 0; i < showDataCnt; i++) {
				idshopCode = "idshopCode" + i; // 得意先コードID格納用変数
				idshopName = "idshopName" + i; // 得意先名称ID格納用変数
				htmlRow += "<tr>";
				htmlRow += "<td id=\""
						+ idshopCode
						+ "\" class=\"w20  cursor_hover add_clickCom0102d03 align_center\">"
						+ data[i].shopCode + "</td>";
				htmlRow += "<td id=\"" + idshopName
						+ "\" class=\" cursor_hover add_clickCom0102d03\">"
						+ data[i].shopNmR + "</td>";
				htmlRow += "</tr>";
		}
		$("#tblSearchResult_body tbody").html(htmlRow);
	}
});

