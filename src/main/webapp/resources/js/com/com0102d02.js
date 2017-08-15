/*
 * パッケージ名: ファイル名: com0102d02.js
 *
 * 作成者:都築電気)関口
 * 作成日:2015/09/10
 *
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/10 1.00 都築電気)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

// ---------------------------------------------------------------------
// 定数
// ---------------------------------------------------------------------
var urlPathCom0102d02 = "/com/COM01-02D02/";

// ---------------------------------------------------------------------
// 共通変数
// ---------------------------------------------------------------------
// テーブルに縦スクロールが表示される行数
var tableRowCntCom0102d02 = 0; // showControlにて値セット

jQuery(document).ready(function($) {

	// ---------------------------------------------------------------------
	// 初期処理
	// ---------------------------------------------------------------------
	if ($("#diaTxtMessage").val() == "") {
		// 正常処理
		$("#noteOutput").hide(); // エラーメッセージエリア非表示
		showControlCom0102d02(); // 画面制御
		searchDataCom0102d02(); // 一覧検索
		setFocusCom0102d02(); // フォーカスセット
	} else {
		// 初期表示エラー処理
		$("#noteOutput").show(); // エラーメッセージエリア表示
		$("#noteOutput").text($("#diaTxtMessage").val()); // エラーメッセージ表示
		errControl(); // エラー処理
	}

	// ---------------------------------------------------------------------
	// 検索ボタン押下
	// ---------------------------------------------------------------------
	$("#searchButtonCom010202").bind("click", function() {
		searchDataCom0102d02(); // 一覧検索
	});

	// ---------------------------------------------------------------------
	//閉じるボタン押下
	// ---------------------------------------------------------------------
	$(document).on("click", "#cancelCom0102d02", function() {
		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
		reTurnCheckTab();
	});

	// ---------------------------------------------------------------------
	//エンターキー押下（タブ移動処理）
	// ---------------------------------------------------------------------
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this,".divSubCondition");
		}
	});

});

//----------------------------------------------------------------------------------------

/**
 * 一覧検索処理
 */
function searchDataCom0102d02() {
	$("#noteOutput").text(""); // エラーメッセージ表示エリアを初期化
	$("#noteOutput").hide(); // エラーメッセージ表示エリアを非表示
	$("#tblSearchResult_body tbody").html(""); // 一覧を初期化
	// 一覧テーブルの縦横をデフォルト値に初期化
	$("#tblSearchResult_body").css({'cssText' : 'width: 660px !important;'});
	if($("#ddlDiaShozoku").is(":visible")) {
		// 事業所表示の場合
		$("#divSearchResult_body").css({'cssText' : 'height: 250px !important;'});
	} else {
		// 事業所非表示の場合
		$("#divSearchResult_body").css({'cssText' : 'height: 275px !important;'});
	}

	$.ajax({
		type : "POST",
		url : rootPath + urlPathCom0102d02 + "com0102d02getSearchResult",
		async : false,
		data : $("form").serialize(),
		success : function(returnData) {
			var data = JSON.parse(returnData);
			var errMsg = ""; // エラーメッセージ格納用変数
			if (data.length == 0) {
				errMsg = $("#COM025-EDia").val(); // エラーメッセージ取得処理（ID:COM025-E）
				$("#tblSearchResult_body tbody").html("<div class='text_size_js' >"+ errMsg+ "</div>");
			} else {
				var showDataCnt = 0; // 一覧表示件数
				if (data.length > $("#diaSearchMax").val()) {
					showDataCnt = $("#diaSearchMax").val(); // 最大表示件数を超えた場合は、最大表示件数まで表示
					errMsg = $("#COM005-WDia").val().replace("{0}", showDataCnt); // エラーメッセージ取得処理（ID:COM005-W）
					$("#noteOutput").show();
					$("#noteOutput").text(errMsg); // エラーメッセージ表示
					// 最大表示件数を超えた場合、テーブル一覧の縦幅を狭める（一覧が子画面領域からはみ出るため）
					if($("#ddlDiaShozoku").is(":visible")) {
						// 事業所表示の場合
						$("#divSearchResult_body").css({'cssText' : 'height: 225px !important;'});
					} else {
						// 事業所非表示の場合
						$("#divSearchResult_body").css({'cssText' : 'height: 250px !important;'});
					}
				} else {
					showDataCnt = data.length; // 最大表示件数以内の場合は、データ数分表示する
				}
				// 縦スクロール表示行数を超えている場合
				if(showDataCnt > tableRowCntCom0102d02) {
					// 一覧が子画面領域からはみ出るため、横幅を狭める
					$("#tblSearchResult_body").css({'cssText' : 'width: 640px !important;'});
				}
				showtableCom0102d02(data, showDataCnt); // 一覧表示処理
				setFocusCom0102d02(); // フォーカスセット
			}
		},
		error : function(e) {
			alert(e.message);

		},
		complete : function() {
		}
	});
}

/**
 * 項目活性・非活性
 */
function showControlCom0102d02() {
	// 親画面引数の事業所コードがNULLの場合のみ事業所コンボ表示
	if ($("#diaJigyoshoCode").val() == "") {
		$(".showJigyosho").show(); // 事業所を表示
		$("#ddlDiaShozoku").prepend($("<option>").val("").text("")); // 事業所を表示する場合、先頭に空白行を追加する
		$("#ddlDiaShozoku").val(""); // 空白（先頭）を選択状態にする
		// テーブル縦スクロール表示件数をセット
		tableRowCntCom0102d02 = 10;
	} else {
		$(".showJigyosho").hide(); // 事業所を非表示
		// テーブル縦スクロール表示件数をセット
		tableRowCntCom0102d02 = 11;
	}
}

// フォーカスセット
function setFocusCom0102d02() {
	if ($("#diaJigyoshoCode").val() == "") {
		$("#ddlDiaShozoku").focus();
	} else {
		$("#txtDiaCustCode").focus().select(); // 得意先コードを全選択状態
	}
}

/**
 * 項目活性・非活性
 *
 * @param data 表示データ
 * @param showDataCnt 表示データ件数
 */
function showtableCom0102d02(data, showDataCnt) {
	var htmlRow = "";
	var idCustId = ""; // 得意先コードID
	var idCustNm = ""; // 得意先名称ID
	for (var i = 0; i < showDataCnt; i++) {
		idCustId = "idCustCode" + i; // 得意先コードID格納用変数
		idCustNm = "idCustNmR" + i; // 得意先名称ID格納用変数
		htmlRow += "<tr>";
		htmlRow += "<td id=\"" + idCustId
				+ "\" class=\"w20 cursor_hover add_clickCom0102d02\">" + data[i].custCode
				+ "</td>";
		htmlRow += "<td id=\"" + idCustNm
				+ "\" class=\"cursor_hover add_clickCom0102d02\">" + data[i].custNmR
				+ "</td>";
		htmlRow += "</tr>";
	}
	$("#tblSearchResult_body tbody").html(htmlRow);
}

/**
 * エラー時の項目制御
 */
function errControl() {
	// 閉じるボタン以外無効化
	$("#ddlDiaShozoku").attr("disabled", ""); // 事業所を無効化
	$("#txtDiaCustCode").attr("disabled", ""); // 得意先コードを無効化
	$("#txtDiaCustNm").attr("disabled", ""); // 得意先名称を無効化
	$("#searchButtonCom010202").attr("disabled", ""); // 検索ボタンを無効化
	// 閉じるボタンへフォーカスセット
	$("#cancelCom0102d02").focus();

}

