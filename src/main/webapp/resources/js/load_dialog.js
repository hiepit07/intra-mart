/**
 * パッケージ名: ファイル名: load_dialog.js
 *
 * 作成者:アクトブレーンベトナム 作成日:2015/09/15
 *
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/15 1.00 ABV)Nghianguyen 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

/**
 * ダイアログの表示
 *
 * @param box_width 幅
 * @param box_height　縦
 * @return
 * @exception
 */
function ShowDialog(box_width, box_height)
{
	$("#overlay").show();
	$("#dialog").fadeIn(300);

	//Set width, height for dialog
	document.getElementById('dialog').style.width = box_width+"px";
	document.getElementById('dialog').style.height= box_height+"px";

	//Get the window height and width
	var winH = $(window).height();
	var winW = $(window).width();

	//Set position center for dialog
	var h = (winH-$("#dialog").height())/2;
	var w = (winW-$("#dialog").width())/2;
	$("#dialog").css('top', h);
	$("#dialog").css('left', w);

	$("#overlay").unbind("click");
}

/**
 * ダイアログの非表示
 *
 * @param
 * @return
 * @exception
 */
function HideDialog()
{
	$("#overlay").hide();
	$("#dialog").fadeOut(300);
}

/**
 * チェーン検索Dialogのイベントのロード
 *
 * @param
 * @return
 * @exception
 */
function loadDialogCom0102d01() {
	//一覧選択
	$(document).on("click", ".add_clickCom0102d01", function(e) {
		var MstSChain = $("#idChainCodeCom0102d01" + $(this).closest('tr').index()).text(); // 選択行の得意先コード取得
		if (MstSChain != "") {
			var index = MstSChain.indexOf("-");
			var lastString = MstSChain.lastIndexOf("");
			var chainCd = MstSChain.substr(0,index);
			var chainBranch = MstSChain.substr(index+1,lastString);
			var chainMei = $("#idChainNameCom0102d01" + $(this).closest('tr').index()).text();
			getCom0102d01(chainCd,chainBranch,chainMei);
		}

		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
	});
}

/**
 * 得意先検索Dialogのイベントのロード
 *
 * @param
 * @return
 * @exception
 */
function loadDialogCom0102d02() {
	//一覧選択
	$(document).on("click", ".add_clickCom0102d02", function(e) {
		var custCode = $("#idCustCode" + $(this).closest('tr').index()).text(); // 選択行の得意先コード取得

		$.ajax({
			type : "POST",
			url : rootPath + "/com/COM01-02D02/com0102d02closeSupplierSearch",
			async : false,
			data : "custCode=" + custCode,
			success : function(returnData) {
				var data = JSON.parse(returnData);

				// テキストボックスのデータで
				getCom0102d02(data);
			},
			error : function(e) {
				alert(e.message);
			},
			complete : function(jqXHR,
					textStatus) {
			}
		});

		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
	});
}

/**
 * 店舗検索Dialogのイベントのロード
 *
 * @param
 * @return
 * @exception
 */
function loadDialogCom0102d03() {
	//一覧選択
	$(document).on("click", ".add_clickCom0102d03", function(e) {
		var custCode = $("#txtDiaCustCode").text();
		var shopCode = $("#idshopCode"+ $(this).closest('tr').index()).text(); // 選択行の得意先コード取得

		$.ajax({
			type : "POST",
			url : rootPath + "/com/COM01-02D03/com0102d03closeSupplierSearch",
			async : false,
			data : "custCode=" + custCode + "&shopCode=" + shopCode,
			success : function(returnData) {
				var data = JSON.parse(returnData);

				// テキストボックスのデータで
				getCom0102d03(data);
			},
			error : function(e) {
				alert(e.message);
			},
			complete : function(jqXHR,
					textStatus) {
			}
		});

		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
	});
}

/**
 * 品番検索Dialogのイベントのロード
 *
 * @param
 * @return
 * @exception
 */
function loadDialogCom0102d04() {
	//一覧選択
	$(document).on("click", ".add_clickCom0102d04", function(e) {
		//画面からデータを取得します。
		var ItemNameWk = $("#ItemNameWkCom0102d04" + $(this).closest('tr').index()).text(); // 選択行の得意先コード取得
		var ItemCodeWk = $("#ItemCodeWkCom0102d04" + $(this).closest('tr').index()).text(); // 選択行の得意先コード取得

		getCom0102d04(ItemCodeWk,ItemNameWk);

		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();

	});
}

/**
 * 担当者検索Dialogのイベントのロード
 *
 * @param
 * @return
 * @exception
 */
function loadDialogCom0102d05() {
	//一覧選択
	$(document).on("click", ".add_clickCom0102d05", function(e) {
		var MstUser = $("#idUserCodeCom0102d05" + $(this).closest('tr').index()).text(); // 選択行の得意先コード取得

		$.ajax({
			type : "POST",
			url : rootPath + "/com/COM01-02D05/com0102d05closeSupplierSearch",
			async : false,
			data : {
				userCodeWk : MstUser,
			},
			success : function(returnData) {
				var data = JSON.parse(returnData);
				getCom0102d05(data);
			},
			error : function(e) {
				alert(e.message);
			},
			complete : function(jqXHR, textStatus) {
			}
		});

		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
	});
}

/**
 * 取消処理モードDialogのイベントのロード（受注・売上共通）
 *
 * @param
 * @return
 * @exception
 */
function loadDialogDelDialogJucUri() {
	// ＯＫボタン押下
	$(document).on("click", "#okButton_DelDialogJucUri", function() {
		// ラジオボタンの値を取得
		var result = $("input:radio[name='delMode_DelDialogJucUri']:checked").val();

		// 親画面に選択結果を返却
		getDelDialogJucUri(result);

		// 子画面を閉じる
		$("#dialog").empty();
		HideDialog();
	});
}

/**
 * 請求元伝票No Dialogのイベントのロード
 */
function loadDialogUriBillSource() {
	//一覧選択
	$(document).on("click", ".add_clickUriBillSrc", function(e) {
		var jigyoCode = $("#idBillSrcJigyoCode" + $(this).closest('tr').index()).text(); // 選択行の事業所コード
		var denNo = $("#idBillSrcDenNo" + $(this).closest('tr').index()).text(); // 選択行の自社伝票No取得
		var count = $("#idBillSrcCount" + $(this).closest('tr').index()).text(); // 選択行の重複件数
		var billFlg = $("#idBillSrcBillFlg" + $(this).closest('tr').index()).text(); // 選択行の最新締め処理フラグ

//		var date = $("#idBillSrcDate" + $(this).closest('tr').index()).text(); // 選択行の納品日取得
//		var deliDate = removeDateSeparate(date);
//		var binKb = $("#idBillSrcBin" + $(this).closest('tr').index()).text(); // 選択行の便区分取得
//		var custCode = $("#idBillSrcCust" + $(this).closest('tr').index()).text(); // 選択行の得意先コード取得
//		var shopCode = $("#idBillSrcShop" + $(this).closest('tr').index()).text(); // 選択行の店舗コード取得
//
//		// 以下、ダイアログ非表示列
//		var billSrcNo = $("#txtBillSrcNo").val();
//		alert("deliDate=" + deliDate + ", binKb=" + binKb + ", custCode=" + custCode + ", shopCode=" + shopCode + ", jigyoCode=" + jigyoCode + ", denNo = " + denNo + ", count = " + count + ", billSrcNo=" + billSrcNo);

//		alert("count = " + count + ", billFlg = " + billFlg);
		if (Number(count) > 1 && billFlg == "0") {
			callbackUriBillSource("#URI018-E");
		} else {
			getBillSource(jigyoCode, denNo);
		}

//		getBillSource(jigyoCode, custCode, shopCode, deliDate, binKb, billSrcNo);
		// 検索子画面を閉じる
		$("#dialog").empty();
		HideDialog();
	});
}

/**
 * 請求元伝票情報を取得
 * ※１件のみ該当の場合、ダイアログを表示せずに取得するため外出しにしています
 * 
 * @param denNo 自社伝票No
 */
function getBillSource(jigyoCode, denNo){
//	alert("jigyoCode=" + jigyoCode + ", deliDate=" + deliDate + ", binKb=" + binKb + ", custCode=" + custCode + ", shopCode=" + shopCode + ", customerDenNo=" + customerDenNo);
	$.ajax({
		type : "POST",
		url : rootPath + "/uri/uriBillSource/getBillSourceData",
		async : false,
		data : {
			prmJigyoCode : jigyoCode, // 事業所コード
			prmDenNo : denNo // 自社伝票No
		},
		success : function(returnData) {
//			var data = JSON.parse();
			callbackUriBillSource(returnData);
		},
		error : function(e) {
			alert(e.message);
		},
		complete : function(jqXHR, textStatus) {
		}
	});
}

/**
 * チェーンダイアログのコール
 *
 * @param formId フォームID
 * @param txtChainCode チェーンコード
 * @param txtChainBranch　チェーン枝コード
 * @return
 * @exception
 */
function showCom0102d01(txtChainCode,txtChainBranch) {
	//コールダイアログを作ります
	$("#dialog").load(rootPath + "/com/COM01-02D01/?txtChainCode="+txtChainCode+"&txtChainBranch="+txtChainBranch,function() {
		ShowDialog(700, 500); // 検索子画面表示
		checkTabIndex();
	});
}

/**
 * 得意先ダイアログのコール
 *
 * @param jigyoshoCode 事業所コード
 * @param custCode 得意先コード
 * @param shopKb 店舗区分
 * @param searchKb 検索対象区分
 * @return
 * @exception
 */
function showCom0102d02(jigyoshoCode, custCode, shopKb, searchKb) {
	// コールダイアログを作ります
	$("#dialog").load(rootPath + "/com/COM01-02D02/" +
		"?jigyoshoCode=" + jigyoshoCode +
		"&custCode=" + custCode +
		"&shopKb=" + shopKb +
		"&searchKb=" + searchKb,
	function() {
		ShowDialog(700, 500);
		checkTabIndex();
	});
}

/**
 * 店舗ダイアログのコール
 *
 * @param formId フォームID
 * @param custCode 得意先コード
 * @param shopCode　店舗コード
 * @return
 * @exception
 */
function showCom0102d03(custCode,shopCode) {
	// コールダイアログを作ります
	$("#dialog").load(rootPath + "/com/COM01-02D03/?custCode=" + custCode+ "&shopCode=" + shopCode, function() {
		ShowDialog(700, 500);
		checkTabIndex();
	});
}

/**
 * 品番ダイアログのコール
 *
 * @param formId フォームID
 * @param changeCodeWk チェーンコード
 * @param changeBranchWk　チェーン枝番
 * @param ourCompanyItemCodeWk　自社品目コード
 * @param customerCodeWk　得意先コード
 * @param saleTypeWk　販売区分
 * @param deadlineWk　納品日
 * @param flightWk　便
 * @return
 * @exception
 */
function showCom0102d04(changeCodeWk,changeBranchWk,ourCompanyItemCodeWk,
		customerCodeWk,saleTypeWk,deadlineWk,flightWk) {
	//コールダイアログを作ります
	$("#dialog").load(rootPath+"/com/COM01-02D04/?changeCodeWk="+changeCodeWk+
			"&changeBranchWk="+changeBranchWk+"&ourCompanyItemCodeWk="+ourCompanyItemCodeWk+"" +
			"&customerCodeWk="+customerCodeWk+"&saleTypeWk="+saleTypeWk+"" +
			"&deadlineWk="+deadlineWk+"&flightWk="+flightWk,function() {
		ShowDialog(700, 500); // 検索子画面表示
		checkTabIndex();
	});
}

/**
 * 担当者ダイアログのコール
 *
 * @param formId フォームID
 * @param userCodeWk 担当者コード
 * @param userRodeWk　権限コード
 * @param myOfficeWk　事業所コード
 * @return
 * @exception
 */
function showCom0102d05(userCodeWk,userRodeWk,myOfficeWk) {
	$("#dialog").load(rootPath + "/com/COM01-02D05/?userCodeWk="+userCodeWk+"&userRodeWk="+userRodeWk+"&myOfficeWk="+myOfficeWk,function() {
		ShowDialog(700, 500); // 検索子画面表示
		checkTabIndex();
	});
}

/**
 * 取消処理モードダイアログのコール（受注・売上共通）
 *
 * @param dspMode 表示モード（1：受注、2：売上）
 */
function showDelDialogJucUri(dspMode) {
	$("#dialog").load(rootPath + "/juc/DelDialogJucUri/?dspMode="+dspMode,
		function() {
		ShowDialog(400, 180); // 画面表示
		checkTabIndex();
	});
}

/**
 * 請求元伝票ダイアログのコール
 *
 * @param billSrcNo 請求元伝票No
 */
function showUriBillSource(billSrcNo) {
	$("#dialog").load(rootPath + "/uri/uriBillSource/?billSrcNo="+billSrcNo,function() {
		// ダイアログの表示はリスト取得結果（> 1）によって実施
//		ShowDialog(700, 500); // 検索子画面表示
		checkTabIndex();
	});
}

/**
 * 親画面でTabIndexを無効にする
 */
function checkTabIndex() {
	$("form").find("input").attr("tabindex", -1);
	$("form").find("button").attr("tabindex", -1);
	$("form").find("select").attr("tabindex", -1);

	$("#formCom0102d01").find("input").attr("tabindex", 0);
	$("#formCom0102d01").find("button").attr("tabindex", 0);
	$("#formCom0102d01").find("select").attr("tabindex", 0);

	$("#formCom0102d02").find("input").attr("tabindex", 0);
	$("#formCom0102d02").find("button").attr("tabindex", 0);
	$("#formCom0102d02").find("select").attr("tabindex", 0);

	$("#formCom0102d03").find("input").attr("tabindex", 0);
	$("#formCom0102d03").find("button").attr("tabindex", 0);
	$("#formCom0102d03").find("select").attr("tabindex", 0);

	$("#formCom0102d04").find("input").attr("tabindex", 0);
	$("#formCom0102d04").find("button").attr("tabindex", 0);
	$("#formCom0102d04").find("select").attr("tabindex", 0);

	$("#formCom0102d05").find("input").attr("tabindex", 0);
	$("#formCom0102d05").find("button").attr("tabindex", 0);
	$("#formCom0102d05").find("select").attr("tabindex", 0);
}

/**
 * 親画面でTabIndexを有効にする
 */
function reTurnCheckTab() {
	$("form").find("input").attr("tabindex", 0);
	$("form").find("button").attr("tabindex", 0);
	$("form").find("select").attr("tabindex", 0);
}