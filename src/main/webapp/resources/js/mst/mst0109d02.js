/**
 * ファイル名:mst0109d02.js
 * 概要:訂正区分登録画面
 * 
 * 作成者:ABV）コイー
 * 作成日:2015/10/28
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/28 1.00 ABV）コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

//Controlエラーの受信デリミター
var txtCustCode0109d02="";

//変更前listの保存
var modValue = [];
jQuery(document).ready(function($) {
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0109D02";
	
	var isClickButton = false;
	// エラー表示	
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}
	
	var strError = $("#lstErrorID").val();
	if (strError != null && strError != "") {
		var lstError = strError.split(COMMON_DELIMITER);
		addClassErrorToControl(lstError);
	}
	
	if ($("#infoMessFlag").val() == "true") {
		addInfoClass();
		//jstorage更新
		modValue = saveData();
		saveToStorage(SCREEN_NAME, modValue);
	}
	
	// 初期処理
	modValue = [];	
	// オリジナルのデータを作成する
	if (!checkStorageExist(SCREEN_NAME)) {
		modValue = saveData();
		saveToStorage(SCREEN_NAME, modValue);
	}
	
	// 初期表示
	setStKb();
	setFocus();
	
	//得意先ダイアログのイベントロード
	loadDialogCom0102d02(); 
	
	//登録ボタンのイベント
	$("#btnRegister").bind("click",function(e) {
		isClickButton = true;
		disableButton("btnRegister");
		jQuestion_confirm($("#COM001-I01").val(), $("#COM001-I01").attr("title"), function(retVal) {
			if (retVal) {
				//変更有無の判断
				if ($('#mode').val() == TEISEI_MODE ){
					if (!compare()) {
						//変更無
						enableButton("btnRegister");
						removeInfoClass();
						$("#txtMess").html($("#COM034-E").val());
						return;
					}
				}
				$("#btnRegisterHidden").trigger("click");
				enableButton("btnRegister");
			} else {
				enableButton("btnRegister");
			}
		});
	});
	
	//クリアボタンのイベント
	$("#btnClear").bind("click",function(e) {
		isClickButton = true;
		disableButton("btnClear");
		jQuestion_confirm($("#COM001-I02").val(), $("#COM001-I02").attr("title"), function(retVal) {
			if (retVal) {
				var mode = $('#mode').val();
				if (mode != SHOUKAI_MODE && mode != TORIKESI_MODE) {
					$("#btnClearHidden").trigger("click");
				} else {
					setFocus();
					enableButton("btnClear");
				}
			} else {
				// ボタンを有効にする
				enableButton("btnClear");
			}
		});
	});
	
	//メニュークリックの処理
	$("#btn_show").bind("click",function(e) {
		isClickButton = true;		
	});
	
	/**
	 * 戻るボタンをクリックされた時の処理。得意先マスタ一覧画面に戻る
	 */
	$("#btnBack").bind("click", function() {
		isClickButton = true;
		//ボタンの2重押下防止
		disableReturnButton("btnBack");
		$("#btnBackHidden").trigger("click");
		enableReturnButton("btnBack");
	});
	
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});
		
	//以下の通りにフォカス処理
	// 得意先コードにフォーカスが当たった場合の処理
	$("#txtCustCode").focus(function() {
		txtCustCode0109d02 = $("#txtCustCode").val();
	});
	
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtCustCode").focusout(function() {
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				if(txtCustCode0109d02 != $("#txtCustCode").val()) {
					$("#lblCustNmR").html("");
				}
				var errorID = chkItem($('#txtCustCode').val(), true, TYPE_NUM, false, null);
				
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先コード"));
					$("#txtCustCode").addClass("form-error-field");
				}else {
					$("#txtCustCode").removeClass('form-error-field');
					$("#txtMess").html("");
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	// 訂正区分からフォーカスが外れた場合の処理。
	$("#txtCorrectKb").focusout(function() {
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var errorID = chkItem($('#txtCorrectKb').val(), true, TYPE_NUM, false, null);
				
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "訂正区分"));
					$("#txtCorrectKb").addClass("form-error-field");
				}else {
					$("#txtCorrectKb").removeClass('form-error-field');
					$("#txtMess").html("");
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	// 訂正理由からフォーカスが外れた場合の処理。
	$("#txtCorrectCause").focusout(function() {
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var errorID = chkItem($('#txtCorrectCause').val(), false, TYPE_EM, false, null);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "訂正理由"));
					$("#txtCorrectCause").addClass("form-error-field");
				}else {
					$("#txtCorrectCause").removeClass('form-error-field');
					$("#txtMess").html("");
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	// 数量ゼロ納品データ連携からフォーカスが外れた場合の処理。
	$("#txtZeroDlvdatFlg").focusout(function() {
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var errorID = chkItem($('#txtZeroDlvdatFlg').val(), false, TYPE_NUM, false, null);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "数量ゼロ納品データ連携"));
					$("#txtZeroDlvdatFlg").addClass("form-error-field");
				}else {
					$("#txtZeroDlvdatFlg").removeClass('form-error-field');
					$("#txtMess").html("");
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	// 備考からフォーカスが外れた場合の処理。
	$("#txtBikou").focusout(function() {
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var errorID = chkItem($('#txtBikou').val(), false, TYPE_EM, false, null);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "備考"));
					$("#txtBikou").addClass("form-error-field");
				}else {
					$("#txtBikou").removeClass('form-error-field');
					$("#txtMess").html("");
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	// 状況コードからフォーカスが外れた場合の処理。
	$("#txtStsCode").focusout(function() {
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var errorID = chkItem($('#txtStsCode').val(), true, TYPE_NUM, false, null);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "状況コード"));
					$("#txtStsCode").addClass("form-error-field");
				}else {
					$("#txtStsCode").removeClass('form-error-field');
					$("#txtMess").html("");
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	//(登録画面)設定区分_Change処理
	$("#chkStKbNotCommon").bind("click", function() {
		if ($('#mode').val() == SHINKI_MODE_CHAR) {			
			//[画面]得意先コード	＝NULL		活性化
			$("#txtCustCode").val("");
			$("#lblCustNmR").html("");
			$("#txtCustCode").removeClass("txt_disable");
			$('#txtCustCode').attr('readonly', false);
			enableImgButton("btn_search_customer_id");
		}
	});
	
	//(登録画面)設定区分_Change処理
	$("#chkStKbCommon").bind("click", function() {
		if ($('#mode').val() == SHINKI_MODE_CHAR) {
			if(!$('#txtCustCode').hasClass('txt_disable')) {
				//[画面]得意先コード	＝	NULL			非活性化
				$("#txtCustCode").val("");
				$("#lblCustNmR").html("");
				$("#txtCustCode").addClass("txt_disable");
				$('#txtCustCode').attr('readonly', true);
				disableImgButton("btn_search_customer_id");
			}
		}
	});
	
	//得意先ダイアログのイベント
	$("#btn_search_customer_id").bind("click", function() {
		if (isBtnImgEnabled("btn_search_customer_id")) {
			var strJigyo = "";
			//[セッション]システム管理者フラグ ＝ '0'（一般ユーザ） の場合																					
			if ($("#sysAdminFlag").val() == SYS_ADMIN_FLAG_USER) {
				strJigyo = loginJigyouShoCode
			}
			
			var custCode = $("#txtCustCode").val();//[画面]得意先コード
			var shopKb = ""; // Null
			var searchKb = "1";// '1'（得意先）
			// 検索子画面呼び出し用関数を呼び出す
			showCom0102d02(strJigyo, custCode, shopKb, searchKb);
		}		
	});
	
	/**
	 * フォーカス処理
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	function setFocus() {
		var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
		if (typeof idError === "undefined") {
			var mode = $('#mode').val();
			if (mode == SHINKI_MODE_CHAR) {
				$('#chkStKbNotCommon').focus();
			} else if (mode == SHOUKAI_MODE_CHAR) {
				$('#btnBack').focus();
			} else if (mode == TEISEI_MODE_CHAR) {
				$('#txtCorrectCause').focus().select();
			} else if (mode == TORIKESI_MODE_CHAR) {
				$('#btnRegister').focus();
			}
		}else {
			$("#"+idError).select();
			$("#"+idError).focus();
		}
	}
	
	/**
	 * 設定区分の通りに得意先コード状況を設定する。 
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	function setStKb() {
		if ($('#mode').val() == SHINKI_MODE_CHAR) {
			if($('#chkStKbCommon').is(':checked')) {
				$("#txtCustCode").addClass("txt_disable");
				 $('#txtCustCode').attr('readonly', true);
				 disableImgButton("btn_search_customer_id");
			} else {
				$("#txtCustCode").removeClass("txt_disable");
				$('#txtCustCode').attr('readonly', false);
				enableImgButton("btn_search_customer_id");
			}
		}
	}
	
	/**
	 *　更新処理前/後に保存する
	 * 
	 * @param 
	 * @return array 
	 * @exception
	 */
	function saveData() {
		tempValue = [];
		if($('#chkStKbCommon').is(':checked')) {
			tempValue.push($('#chkStKbCommon').val());
		} else {
			tempValue.push($('#chkStKbNotCommon').val());
		}
		tempValue.push($('#txtUserCode').val());
		tempValue.push($('#txtCustCode').val());
		tempValue.push($('#txtCorrectKb').val());
		tempValue.push($('#txtCorrectCause').val());
		tempValue.push($('#txtZeroDlvdatFlg').val());
		tempValue.push($('#txtBikou').val());
		tempValue.push($('#txtStsCode').val());
		return tempValue;
	}
	
	/**
	 *　変更有無の判断
	 * 
	 * @param 
	 * @return boolean true:　違う点がある false:違う点がなし
	 * @exception
	 */
	function compare() {
		var newValue = saveData();
		//compare oldValue and newValue
		return !compareWithStorage(SCREEN_NAME, newValue);
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
		$("#txtCorrectKb").focus().select();
	}
	reTurnCheckTab();
}
