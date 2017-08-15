/**
 * ファイル名:mst0101d02.js
 * 概要:担当者マスタ登録画面
 * 
 * 作成者:ABV）コイー 
 * 作成日:2015/08/24
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

//Controlエラーの受信デリミター
var DELIMITER = "##";

//変更前listの保存
var modValue = [];

jQuery(document).ready(function($) {
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0101D02";
	
	var isClickButton = false;
	// エラー表示	
	var errControl = $('#errorControl').val();
	if(errControl != "") {
		errorControl();
	}
	
	var strError = $("#lstErrorID").val();
	if (strError != null && strError != "") {
		var lstError = strError.split(DELIMITER);
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
	setInit();

	if ($("#txtTelResult").hasClass("form-error-field")) {
		$("#txtTel1").addClass("form-error-field");
		$("#txtTel2").addClass("form-error-field");
		$("#txtTel3").addClass("form-error-field");
	} else {
		$("#txtTel1").removeClass("form-error-field");
		$("#txtTel2").removeClass("form-error-field");
		$("#txtTel3").removeClass("form-error-field");
	}

	if ($("#txtFaxResult").hasClass("form-error-field")) {
		$("#txtFax1").addClass("form-error-field");
		$("#txtFax2").addClass("form-error-field");
		$("#txtFax3").addClass("form-error-field");
	} else {
		$("#txtFax1").removeClass("form-error-field");
		$("#txtFax2").removeClass("form-error-field");
		$("#txtFax3").removeClass("form-error-field");
	}

	if ($("#shozokuSelect").hasClass("form-error-field")) {
		$("#ddlShozoku").addClass("form-error-field");
	} else {
		$("#ddlShozoku").removeClass("form-error-field");
	}
	
	if ($("#riyoKengenSelect").hasClass("form-error-field")) {
		$("#ddlRiyoKengen").addClass("form-error-field");
	} else {
		$("#ddlRiyoKengen").removeClass("form-error-field");
	}
	
	// フォーカスセット
	setFocus();	
	
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
			e.preventDefault();
			EnterKey(e, this, ".data_conditions_area_cont");
		}

	});	
	
	$("#ddlShozoku").change(function() {
		var x = $(this).val();
		// and update the hidden input's value
		$("#shozokuSelect").val(x);
	});
	
	$(document).on('focusout', '#ddlRiyoKengen', function(){
		if($("#ddlRiyoKengen").val() != "") {
			$("#ddlRiyoKengen").removeClass("form-error-field");
		}
	});
	
	$("#ddlRiyoKengen").change(function() {
		var x = $(this).val();
		// and update the hidden input's value
		$("#riyoKengenSelect").val(x);
	});	

	
	
	//check focus out
	$(document).on('focusout', '#txtUserCode', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var strBefore = $("#txtUserCode").val();
				var strAfter = "";
				if (strBefore != "") {
					if (strBefore.length < 8) {
						strAfter = addLeadingChar(strBefore, 8);
						$("#txtUserCode").val(strAfter);
					}
				}
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtUserCode').val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "担当者コード"));
						$("#txtUserCode").addClass("form-error-field");
					}else {
						$("#txtUserCode").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}			
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtUserName', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtUserName').val(), true, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "担当者氏名"));
						$("#txtUserName").addClass("form-error-field");			
					}else {
						$("#txtUserName").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}				
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtUserNameKata', function(){		
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtUserNameKata').val(), false, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "担当者氏名カナ"));
						$("#txtUserNameKata").addClass("form-error-field");			
					}else {
						$("#txtUserNameKata").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}				
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	
	$(document).on('focusout', '#txtDivisionName', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtDivisionName').val(), false, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "部署名"));
						$("#txtDivisionName").addClass("form-error-field");			
					}else {
						$("#txtDivisionName").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtAddress', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtAddress').val(), false, TYPE_NUM_ALPHA_SIGN, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "メールアドレス"));
						$("#txtAddress").addClass("form-error-field");			
					}else {
						$("#txtAddress").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtTel1', function(){
		// 電話番号を入力時、本当の電話番号をtxtTelResultに保存する
		var tel1 = $("#txtTel1").val();
		var tel2 = $("#txtTel2").val();
		var tel3 = $("#txtTel3").val();
		$("#txtTelResult").val(tel1 + tel2 + tel3);
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtTel1').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtTel1").addClass("form-error-field");			
					}else {
						$("#txtTel1").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtTel2', function(){
		// 電話番号を入力時、本当の電話番号をtxtTelResultに保存する
		var tel1 = $("#txtTel1").val();
		var tel2 = $("#txtTel2").val();
		var tel3 = $("#txtTel3").val();
		$("#txtTelResult").val(tel1 + tel2 + tel3);
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtTel2').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtTel2").addClass("form-error-field");			
					}else {
						$("#txtTel2").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtTel3', function(){
		// 電話番号を入力時、本当の電話番号をtxtTelResultに保存する
		var tel1 = $("#txtTel1").val();
		var tel2 = $("#txtTel2").val();
		var tel3 = $("#txtTel3").val();
		$("#txtTelResult").val(tel1 + tel2 + tel3);
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtTel3').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtTel3").addClass("form-error-field");			
					}else {
						$("#txtTel3").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
		
	});
	
	$(document).on('focusout', '#txtFax1', function(){
		// FAX番号を入力時、本当のFAX番号をtxtFaxResultに保存する
		var fax1 = $("#txtFax1").val();
		var fax2 = $("#txtFax2").val();
		var fax3 = $("#txtFax3").val();
		$("#txtFaxResult").val(fax1 + fax2 + fax3);
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFax1').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFax1").addClass("form-error-field");			
					}else {
						$("#txtFax1").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtFax2', function(){
		// FAX番号を入力時、本当のFAX番号をtxtFaxResultに保存する
		var fax1 = $("#txtFax1").val();
		var fax2 = $("#txtFax2").val();
		var fax3 = $("#txtFax3").val();
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFax2').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFax2").addClass("form-error-field");			
					}else {
						$("#txtFax2").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtFax3', function(){
		// FAX番号を入力時、本当のFAX番号をtxtFaxResultに保存する
		var fax1 = $("#txtFax1").val();
		var fax2 = $("#txtFax2").val();
		var fax3 = $("#txtFax3").val();
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFax3').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFax3").addClass("form-error-field");			
					}else {
						$("#txtFax3").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtStatusCode', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtStatusCode').val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "状況コード"));
						$("#txtStatusCode").addClass("form-error-field");			
					}else {
						$("#txtStatusCode").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	/**
	 *　更新処理前/後に保存する
	 * 
	 * @param 
	 * @return array 
	 * @exception
	 */
	function saveData() {
		tempValue = [];
		tempValue.push($('#txtUserCode').val());
		tempValue.push($('#txtUserName').val());
		tempValue.push($('#txtUserNameKata').val());
		tempValue.push($('#ddlShozoku').val());
		tempValue.push($('#ddlRiyoKengen').val());
		tempValue.push($('#txtDivisionName').val());
		tempValue.push($('#txtAddress').val());
		tempValue.push($('#txtTel1').val());
		tempValue.push($('#txtTel2').val());
		tempValue.push($('#txtTel3').val());
		tempValue.push($('#txtFax1').val());
		tempValue.push($('#txtFax2').val());
		tempValue.push($('#txtFax3').val());
		tempValue.push($('#txtStatusCode').val());		
		tempValue.push($('#chkSystemUse').is(':checked'));
		tempValue.push($('#chkPassword').is(':checked'));
		tempValue.push($('#chkLogout').is(':checked'));
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
	
	/**
	 * フォーカス処理
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	function setFocus() {
		var idError =  $("input.form-error-field[type^=text]:enabled:not([readonly]):first").attr("id");
		if (typeof idError === "undefined") {
			var mode = $('#mode').val();
			if (mode == SHINKI_MODE_CHAR) {
				$('#txtUserCode').focus();
			} else if (mode == SHOUKAI_MODE_CHAR) {
				$('#btnBack').focus();
			} else if (mode == TEISEI_MODE_CHAR) {
				$('#txtUserName').focus();
			} else if (mode == TORIKESI_MODE_CHAR) {
				$('#btnRegister').focus();
			}
		}else {
			$("#"+idError).select();
			$("#"+idError).focus();
		}
	}
	
	/**
	 * 初期表示
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	function setInit() {
		// 本当の電話番号をtxtTelResultに保存する
		var tel1 = $("#txtTel1").val().trim();
		var tel2 = $("#txtTel2").val().trim();
		var tel3 = $("#txtTel3").val().trim();
		$("#txtTelResult").val(tel1 + tel2 + tel3);

		// 本当のFAX番号をtxtFaxResultに保存する
		var fax1 = $("#txtFax1").val().trim();
		var fax2 = $("#txtFax2").val().trim();
		var fax3 = $("#txtFax3").val().trim();
		$("#txtFaxResult").val(fax1 + fax2 + fax3);
	}
});