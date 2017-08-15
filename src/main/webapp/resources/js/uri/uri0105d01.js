/**
 * ファイル名:uri0105d01.js
 * 概要:売上登録画面
 * 
 * 作成者:ABV）コイー 
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ABV）コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
jQuery(document).ready(function($) {
	$("#txtJuDateFrom").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true
	});
	$("#txtJuDateTo").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true
	});
	
	//得意先ダイアログのイベントロード
	loadDialogCom0102d02();
	loadDialogCom0102d03();
	
	setInit();
	setDdlOyaShozoku();
	
	if ($("#txtMess").html().trim() == "") {	
		// フォーカスセット
		setFocus($('#sysAdminFlag').val());
	}
	
	// イベント作成
	//得意先ダイアログのイベント
	$("#btn_search_customer_id").bind("click", function() {
		var strJigyo = "";
		//[変数]セッション.システム管理者フラグ = '1'（有効）の場合、[画面]事業所コンボボックス.選択値
		if ($("#sysAdminFlag").val() == SYS_ADMIN_FLAG_ADMIN) {
			strJigyo = $("#ddlOyaShozoku").val();
		} else {
			//[変数]セッション.システム管理者フラグ <> '1'（有効）の場合、[変数]セッション.ログイン事業所コード
			strJigyo = $("#loginJigyouShoCode").val();		
		}
		
		var custCode = $("#txtCustCode").val();//[画面]得意先コード
		var shopKb = ""; // Null
		var searchKb = "1";// '1'（得意先）
		// 検索子画面呼び出し用関数を呼び出す
		showCom0102d02(strJigyo, custCode, shopKb, searchKb);
	});
	
	$("#btn_search_shop_id").on("click", function() {
		if(isBtnImgEnabled("btn_search_shop_id")) {
			// 変数値の情報を得ます
			var custCode = $("#txtCustCode").val();
			custCode = replaceText(custCode);
			var shopCode = $("#txtShopCode").val();
			shopCode = replaceText(shopCode);
			showCom0102d03(custCode,shopCode);
		}
	});
	
	/**
	 * 事業所が変更された場合の処理。[変数]セッション.システム管理者フラグ = '1'（有効）の場合のみ発生
	 */
	$("#ddlOyaShozoku").bind("change", function() {
		setDdlOyaShozoku();
	});
	
	
	//表示ボタンクリック
	$("#btnViewing").bind("click", function() {
		//ボタンの2重押下防止
		disableButton("btnViewing");
		showData();
	});
	
	
	function setDdlOyaShozoku() {
		//(1) [画面]事業所でブランクが選択された場合、
		if ($("#ddlOyaShozoku").val() == "") {
			//[変数]事業所コードWK=Null TODO
			//[画面]得意先コード =''（非活性化）
			$("#txtCustCode").val("")
			$("#txtCustCode").prop("readonly", true).prop("tabindex", -1).addClass("txt_disable");		
			//[画面]得意先検索ボタン=非活性化
			disableImgButton("btn_search_customer_id");
			//[画面]得意先名称=''（非活性化）
			$("#lblCustNmR").val("")
			//[画面]店舗コード =''（非活性化）
			$("#txtShopCode").val("")
			$("#txtShopCode").prop("readonly", true).prop("tabindex", -1).addClass("txt_disable");
			//[画面]店舗検索ボタン=非活性化
			disableImgButton("btn_search_shop_id");		
			//[画面]店舗名称=''（非活性化）
			$("#lblShopNmR").val("");
		} else {
			//(2) [画面]事業所でブランク以外が選択された場合、
			//[変数]事業所コードWK=[変数]事業所情報[i].事業所コード（[画面]事業所 = [変数]事業所情報[i].事業所名となるデータ） TODO			
			//[画面]得意先コード=''（活性化）
			$("#txtCustCode").prop("readonly", false).prop("tabindex", 0).removeClass("txt_disable");			
			//[画面]得意先検索ボタン=活性化											
			enableImgButton("btn_search_customer_id");
			//[画面]得意先名称=''（活性化）					
			$("#lblCustNmR").val("")
			//[画面]店舗コード=''（非活性化）
			$("#txtShopCode").val("")
			$("#txtShopCode").prop("readonly", true).prop("tabindex", -1).addClass("txt_disable");
			//[画面]店舗検索ボタン=非活性化				
			disableImgButton("btn_search_shop_id");
			//[画面]店舗名称=''（非活性化）
			$("#lblShopNmR").val("");
		}
	}
	
	//初期値セット
	function setInit() {
		//[画面]得意先名称 =''
		$("#lblCustNmR").html("");
	    //[画面]店舗名称=''（非活性化）
		$("#lblShopNmR").html("");
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
			$('#txtJuDateFrom').focus().select();;
		}
	}
	
	function showData() {
		$.ajax({
			type : "POST",
			url : "showData",
			async : false,
			data : $("form").serialize(),
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(returnData) {
				/*var data = JSON.parse(returnData);
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
				}*/
			},
			error : function(e) {
				$("#txtMess").html(EXCEPTION_ERROR);
			},
			complete : function(jqXHR, textStatus) {
				//ボタンの2重押下防止 可能
				enableButton("btnViewing");
			}
		});
	}
});
/**
 * 以下のdialogの処理
 */

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
		//[画面]得意先コード =[変数]得意先情報.得意先コード
		$("#txtCustCode").val(data[0].custCode);
		//[画面]得意先名称  =  [変数]得意先情報.得意先略称
		$("#lblCustNmR").html(data[0].custNm);
		//[画面]店舗コード   =  ''（活性化）				
		//[画面]店舗検索ボタン  =  活性化				
		//[画面]店舗名称  =  ''（活性化）
		
		//[画面]店舗コードへフォーカスをセットする
		$("#txtShopCode").focus().select();		
	}
	reTurnCheckTab();
}

/**
 * 店舗子画面データ受け取り用関数（仮）
 * 
 * @param 取得したデータ
 * @return
 * @exception
 */
function getCom0102d03(data) {
	if (data[0].shopCode != null) {
		$("#txtShopCode").val(data[0].shopCode);
		//	[画面]店舗名称=[変数]店舗情報.店舗略称
		$("#lblShopNmR").text(data[0].shopNmR);
		//[画面]伝票Noへフォーカスをセットする
		$("#txtDenCode").focus().select();
	}

	reTurnCheckTab();
}