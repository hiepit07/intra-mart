/**
 * パッケージ名: ファイル名: uriBillSource.js
 *
 * 作成者:アクトブレーン
 * 作成日:2015/12/10
 *
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

// ---------------------------------------------------------------------
// 定数
// ---------------------------------------------------------------------
var urlPathUriBillSource = "/uri/uriBillSource/";

// ---------------------------------------------------------------------
// 共通変数
// ---------------------------------------------------------------------
// テーブルに縦スクロールが表示される行数
var tableRowCntUriBillSrc = 0; // showControlにて値セット
var lstBillSrcInfo = null

jQuery(document).ready(function($) {
	// ---------------------------------------------------------------------
	// 初期処理
	// ---------------------------------------------------------------------
	if ($("#dlgTxtMessage").val() == "") {
	// 正常処理
		$("#noteOutput").hide(); // エラーメッセージエリア非表示
		showControlUriBillSrc(); // 画面制御
		var count = searchDataUriBillSrc(); // 一覧検索
//		alert("count = " + count);
		if(count == 0){
			// 取得件数 = 0件の場合、
			$("#dialog").empty();
			HideDialog();
			reTurnCheckTab();
			callbackUriBillSource(null);
		} else if(count == 1) {
			// 取得件数 = 1件の場合、請求元売上情報取得
			$("#dialog").empty();
			billSrcInfo = lstBillSrcInfo[0];
			getBillSource(billSrcInfo.jigyoCode, billSrcInfo.denNo);
		} else {
			// 取得件数 > 1件の場合、ダイアログ表示
			showTableUriBillSrc(lstBillSrcInfo); // 一覧表示処理
			ShowDialog(700, 500); // 検索子画面表示
			$(".d_title").css('background-color', '#c4d79b');
			$(".tblSearchResult_head th").css('background-color', '#76933c');
			$(".d_body").css('background-color', '#ff0000');
		}
	} else {
		// 初期表示エラー処理
		$("#noteOutput").show(); // エラーメッセージエリア表示
		$("#noteOutput").text($("#dlgTxtMessage").val()); // エラーメッセージ表示
		errControl(); // エラー処理
	}

	// ---------------------------------------------------------------------
	//閉じるボタン押下
	// ---------------------------------------------------------------------
	$(document).on("click", "#cancelUriBillSrc", function() {
		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
		reTurnCheckTab();
	});
});

//----------------------------------------------------------------------------------------

/**
 * 一覧検索処理
 */
function searchDataUriBillSrc() {
	$("#noteOutput").text(""); // エラーメッセージ表示エリアを初期化
	$("#noteOutput").hide(); // エラーメッセージ表示エリアを非表示
	$("#tblSearchResult_body tbody").html(""); // 一覧を初期化
	// 一覧テーブルの縦横をデフォルト値に初期化
	$("#tblSearchResult_body").css({'cssText' : 'width: 660px !important;'});
	var rtnVal = 0;
	$.ajax({
		type : "POST",
		url : rootPath + urlPathUriBillSource + "getBillSourceList",
		async : false,
		data : $("form").serialize(),
		success : function(returnData) {
			var data = JSON.parse(returnData);
			var errMsg = ""; // エラーメッセージ格納用変数
			if (data[0].strJsonUriHead != null) {
				lstBillSrcInfo = JSON.parse(data[0].strJsonUriHead);
				rtnVal = lstBillSrcInfo.length;
			}
		},
		error : function(e) {
			alert(e.message);

		},
		complete : function() {
		}
	});
	return rtnVal;
}

/**
 * 項目活性・非活性
 */
function showControlUriBillSrc() {
	// 親画面引数の事業所コードがNULLの場合のみ事業所コンボ表示
	if ($("#diaJigyoshoCode").val() == "") {
		$(".showJigyosho").show(); // 事業所を表示
		$("#ddlDiaShozoku").prepend($("<option>").val("").text("")); // 事業所を表示する場合、先頭に空白行を追加する
		$("#ddlDiaShozoku").val(""); // 空白（先頭）を選択状態にする
		// テーブル縦スクロール表示件数をセット
		tableRowCntUriBillSrc = 10;
	} else {
		$(".showJigyosho").hide(); // 事業所を非表示
		// テーブル縦スクロール表示件数をセット
		tableRowCntUriBillSrc = 11;
	}
}

/**
 * データ表示
 *
 * @param data 表示データ
 * @param showDataCnt 表示データ件数
 */
function showTableUriBillSrc(lstBillSrcInfo) {
	var jigyoCode = ""; // 事業所コード
	var deliDate = ""; // 納品日
	var binKb = ""; // 便区分
	var custCode = ""; // 得意先コード
	var custName = ""; // 得意先名
	var shopCode = ""; // 店舗コード
	var shopName = ""; // 店舗名
	var denNo = ""; // 自社伝票No
	var billFlg = ""; // 締め処理フラグ
	var count = 1; // 重複件数
	
	var bodyHtml = "";
	for (var idx = 0; idx < lstBillSrcInfo.length; idx++) {
		var billSrcInfo = lstBillSrcInfo[idx];

		var year = billSrcInfo.deliDate.substr(0, 4);
		var month = billSrcInfo.deliDate.substr(4, 2);
		var day = billSrcInfo.deliDate.substr(6, 2);
		var date = year + "/" + month + "/" + day;
		bodyHtml = bodyHtml
				 + "<tr>"
				 + "<td id='idBillSrcDate" + idx + "' class='cursor_hover add_clickUriBillSrc'>" + date + "</td>"
				 + "<td id='idBillSrcBin" + idx + "' class='cursor_hover add_clickUriBillSrc'>" + billSrcInfo.binKb + "</td>"
				 + "<td id='idBillSrcCust" + idx + "' class='cursor_hover add_clickUriBillSrc'>" + billSrcInfo.custCode + "</td>"
				 + "<td class='cursor_hover add_clickUriBillSrc'>" + billSrcInfo.custName + "</td>"
				 + "<td id='idBillSrcShop" + idx + "' class='cursor_hover add_clickUriBillSrc'>" + billSrcInfo.shopCode + "</td>"
				 + "<td class='cursor_hover add_clickUriBillSrc'>" + billSrcInfo.shopName + "</td>"
				 + "<td id='idBillSrcJigyoCode" + idx + "' class='cursor_hover add_clickUriBillSrc'>"
				 + billSrcInfo.jigyoCode + "</td>"
				 + "<td id='idBillSrcDenNo" + idx + "' class='cursor_hover add_clickUriBillSrc'>"
				 + billSrcInfo.denNo + "</td>"
				 + "<td id='idBillSrcCount" + idx + "' class='cursor_hover add_clickUriBillSrc'>"
				 + billSrcInfo.count + "</td>"
				 + "<td id='idBillSrcBillFlg" + idx + "' class='cursor_hover add_clickUriBillSrc'>"
				 + billSrcInfo.billFlg + "</td>"
				 + "</tr>"
	}
	$("#tblSearchResult_body tbody").html(bodyHtml);
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
