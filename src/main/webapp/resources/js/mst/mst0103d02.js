/**
 * ファイル名:mst0103d02.js
 * 概要:店舗マスタメンテナンス画面
 * 
 * 作成者:ABV）TramChu
 * 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00                 ABV）TramChu    新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

var isClickRegister = false;
var isClickClear = false;
var txtCustomerCode= "";
var txtStoreCode= "";
var ddlDeliveryCenterDisable
var txtCollectionDestination = "";
var DELIMITER = "##";
//変更前listの保存
var modValue = [];
jQuery(document).ready(function($) {
	//Dialogのadd_clickイベントの作成
	loadDialogCom0102d02();
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0103D02";
	isClickButton = false;
	ddlDeliveryCenterDisable = $("#ddlDeliveryCenter").is(':disabled');
	// エラー表示
	var err = $(".checkInputError").html();
	if (err != null) {
		$("#txtMess").html(err);
		errorControl();
	}	
	var errControl = $('#errControl').val();
	if (errControl == "1") {
		errorControl();
		$('#errControl').val('');
		return;
	}
	var strError = $("#lstErrorID").val();
	if (strError != null && strError != "") {
		var lstError = strError.split(DELIMITER);
		addClassErrorToControl(lstError);
	}
	if ($("#infoMessFlag").val() == "true") {
		addInfoClass();
		// jStorageの更新
		modValue = saveData();
		saveToStorage(SCREEN_NAME, modValue)
	}
	//  カレンダーを表示する
	var mode = $('#mode').val();
	if (mode == TORIKESI_MODE) {
		$('#ddlOffice').val($('#officeSelect').val());
		$('#ddlDeliveryCenter').val($('#deliveryCenterSelect').val());
		$('#ddlCondition').val($('#conditionSelect').val());
		$('#ddlThanksClassification').val($('#thanksClassificationSelect').val());		
		if ($('#chkDestinationStoreDis').val() == "true") {
			$("#chkDestinationStore").prop("checked",true);
		} else {
			$("#chkDestinationStore").prop("checked",false);
		}
		$('#chkDestinationStore').val($('#thanksClassificationSelect').val());	
	}
	$('#ddlCondition').val($('#conditionSelect').val());
	if (mode == SHINKI_MODE || mode == TEISEI_MODE) {
		$("#txtDate").datepicker({
			showOn: "button",
		    buttonImage: rootPath + "/resources/css/images/calendar.gif",
		    buttonImageOnly: true
		});
	}
	
	//初期表示
	setFocus();
	// 初期処理
	modValue = [];
	// オリジナルのデータを作成する
	if (!checkStorageExist(SCREEN_NAME)) {
		modValue = saveData();
		saveToStorage(SCREEN_NAME, modValue);
	}
	// （3-1）画面項目編集可否制御
	var isChkDestinationStore = $("#chkDestinationStore").is(':checked');
	if (mode == "3") {
		if (isChkDestinationStore) {
			$("#ddlCondition").prop("disabled",false);
			$("#txtCollectionDestination").prop("readonly",true);
			$("#txtCollectionDestination").addClass("txt_disable");
		} else {
			$("#ddlCondition").prop("disabled",true);
			$("#txtCollectionDestination").prop("readonly",false);
			$("#txtCollectionDestination").removeClass("txt_disable");			
		}
	}
	if (mode == "1") {
		if ($("#txtMess").html().trim() != "") {
			if (isChkDestinationStore) {
				$("#ddlCondition").prop("disabled",false);
				$("#txtCollectionDestination").prop("readonly",true);
				$("#txtCollectionDestination").addClass("txt_disable");
			} else {
				$("#ddlCondition").prop("disabled",true);
				$("#txtCollectionDestination").prop("readonly",false);
				$("#txtCollectionDestination").removeClass("txt_disable");			
			}
		}		
	}
	
	$("#ddlDeliveryCenter").change(function() {
		var x = $(this).val();
		// and update the hidden input's value
		$("#deliveryCenterSelect").val(x);
	});
	$("#ddlCondition").change(function() {
		var x = $(this).val();
		// and update the hidden input's value
		$("#conditionSelect").val(x);
	});
	$("#ddlThanksClassification").change(function() {
		var x = $(this).val();
		// and update the hidden input's value
		$("#thanksClassificationSelect").val(x);
	});
	
	/*// エラー表示
	var err = $(".checkInputError").html();
	if (err != null) {
		$("#txtMess").html(err);
	}*/
	// 得意先コードにフォーカスが当たった場合の処理
	$("#txtCustomerCode").focus(function(){
		txtCustomerCode = $("#txtCustomerCode").val();
	});
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtCustomerCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE) {
					removeInfoClass();
					if ( txtCustomerCode != $("#txtCustomerCode").val()){
						$("#customerName").html("");
					}
					
					var errorID = chkItem($("#txtCustomerCode").val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先コード"));
						$("#txtCustomerCode").addClass("form-error-field");
					}else {
						$("#txtCustomerCode").removeClass('form-error-field');
						$("#txtMess").html("");
					}
					var officeCode = $("#ddlOffice").find('option:selected').val();
					var customerCode = $("#txtCustomerCode").val();
					if (officeCode != "" && officeCode != undefined && customerCode != "") {
						var businessDate = $("#businessDate").val();						
						$.ajax({
							type : "POST",
							url : "getDeliveryCenter",
							async : false,
							data: {"businessDate" : businessDate,"officeCode" : officeCode, "customerCode" : customerCode},
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",
							success : function(returnData) {
								$("#ddlDeliveryCenter option").remove();
								var data = JSON.parse(returnData);
								if(data != "") {
									if (data[0].addr1 != null) {
										if(data[0].addr1 == "null" || data[0].addr1 == "noData") {
											$("#ddlDeliveryCenter").attr("disabled",true);
										} else {
											$("#txtMess").html(data[0].addr1);
											$("#ddlDeliveryCenter").attr("disabled",true);
											errorControl();
											$('#errControl').val('');
										}
									} else {
										$("#ddlDeliveryCenter option").remove();
										$("#ddlDeliveryCenter").attr("disabled",false);
										$("#ddlDeliveryCenter").append(new Option("", ""));
										for (var i = 0 ; i < data.length; i++ ) {
											$("#ddlDeliveryCenter").append(new Option(data[i].nhscd + " : " + data[i].nhsmei,data[i].nhscd));
										}
									}
								}
							},
							error : function(e) {
								$("#txtMess").html(EXCEPTION_ERROR);
							},
							complete : function(jqXHR, textStatus) {
							}
						});
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	// 店舗コードにフォーカスが当たった場合の処理
	$("#txtStoreCode").focus(function(){
		txtStoreCode = $("#txtStoreCode").val();
	})
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtStoreCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE) {
					removeInfoClass();
					var errorID = chkItem($("#txtStoreCode").val(), true, TYPE_NUM_ALPHA, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "店舗コード"));
						$("#txtStoreCode").addClass("form-error-field");
					}else {
						$("#txtStoreCode").removeClass('form-error-field');
						$("#txtMess").html("");
					}
					if(txtStoreCode != $("#txtStoreCode").val()){
						$("#txtStoreName").val("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$("#txtStoreName").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($("#txtStoreName").val(), true, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "店舗名称"));
						$("#txtStoreName").addClass("form-error-field");
					}else {
						$("#txtStoreName").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtStoreNameKana', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtStoreNameKana').val(), false, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "店舗名称カナ"));
						$("#txtStoreNameKana").addClass("form-error-field");
					}else {
						$("#txtStoreNameKana").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$("#txtStoreAbbreviation").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($("#txtStoreAbbreviation").val(), true, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "店舗略称"));
						$("#txtStoreAbbreviation").addClass("form-error-field");
					}else {
						$("#txtStoreAbbreviation").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	/**
	 * 郵便番号からフォーカスが外れた場合の処理
	 */
	$("#txtPostalCode1, #txtPostalCode2").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) { // 新規／訂正モード
					removeInfoClass();
					var errorID = chkItem($("#txtPostalCodeResult").val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "郵便番号"));
						$("#txtPostalCode1").addClass("form-error-field");
						$("#txtPostalCode2").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtPostalCode1").removeClass("form-error-field");
						$("#txtPostalCode2").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 郵便番号を入力時、本当の郵便番号をtxtPostalCodeResultに保存する
	 */
	$("#txtPostalCode1, #txtPostalCode2").bind("keyup", function() {
		var postalCode1 = $("#txtPostalCode1").val().trim();
		var postalCode2 = $("#txtPostalCode2").val().trim();
		$("#txtPostalCodeResult").val(postalCode1 + postalCode2);
	});
	
	/**
	 * 請求先電話番号を入力時、本当の請求電話番号をtxtPhoneNumberResultに保存する
	 */
	$("#txtPhoneNumber1, #txtPhoneNumber2, #txtPhoneNumber3").bind("keyup", function() {
		var tel1 = $("#txtPhoneNumber1").val().trim();
		var tel2 = $("#txtPhoneNumber2").val().trim();
		var tel3 = $("#txtPhoneNumber3").val().trim();
		$("#txtPhoneNumberResult").val(tel1 + tel2 + tel3);
	});

	/**
	 * 請求先電話番号を入力時、本当の請求電話番号をtxtPhoneNumberResultに保存する
	 */
	$("#txtFaxNumber1, #txtFaxNumber2, #txtFaxNumber3").bind("keyup", function() {
		var tel1 = $("#txtFaxNumber1").val().trim();
		var tel2 = $("#txtFaxNumber2").val().trim();
		var tel3 = $("#txtFaxNumber3").val().trim();
		$("#txtFaxNumberResult").val(tel1 + tel2 + tel3);
	});
	$("#txtAddress1").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($("#txtAddress1").val(), true, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "住所１"));
						$("#txtAddress1").addClass("form-error-field");
					}else {
						$("#txtAddress1").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	$(document).on('focusout', '#txtAddress2', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtAddress2').val(), false, TYPE_EM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "住所2"));
						$("#txtAddress2").addClass("form-error-field");
					}else {
						$("#txtAddress2").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtPhoneNumber1', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtPhoneNumber1').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtPhoneNumber1").addClass("form-error-field");
					}else {
						$("#txtPhoneNumber1").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtPhoneNumber3', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtPhoneNumber3').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtPhoneNumber3").addClass("form-error-field");
					}else {
						$("#txtPhoneNumber3").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtPhoneNumber2', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtPhoneNumber2').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtPhoneNumber2").addClass("form-error-field");			
					}else {
						$("#txtPhoneNumber2").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	$(document).on('focusout', '#txtFaxNumber1', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFaxNumber1').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFaxNumber1").addClass("form-error-field");	
					}else {
						$("#txtFaxNumber1").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtFaxNumber2', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFaxNumber2').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFaxNumber2").addClass("form-error-field");
					}else {
						$("#txtFaxNumber2").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtFaxNumber3', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFaxNumber3').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFaxNumber3").addClass("form-error-field");
					}else {
						$("#txtFaxNumber3").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtFixMisejika', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFixMisejika').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "社店コード"));
						$("#txtFixMisejika").addClass("form-error-field");
					}else {
						$("#txtFixMisejika").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtFixCenter', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtFixCenter').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "社店コード"));
						$("#txtFixCenter").addClass("form-error-field");
					}else {
						$("#txtFixCenter").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtSaleMisejika', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtSaleMisejika').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "社店コード"));
						$("#txtSaleMisejika").addClass("form-error-field");
					}else {
						$("#txtSaleMisejika").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focusout', '#txtSaleCenter', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#txtSaleCenter').val(), false, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "社店コード"));
						$("#txtSaleCenter").addClass("form-error-field");
					}else {
						$("#txtSaleCenter").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	$("#txtStatusCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					removeInfoClass();
					var errorID = chkItem($("#txtStatusCode").val(), true, TYPE_NUM, false, null);
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
	// 集約先店舗コードにフォーカスが当たった場合の処理
	$("#txtCollectionDestination").focus(function(){
		txtCollectionDestination = $("#txtCollectionDestination").val();
	})
	// 得意先コードからフォーカスが外れた場合の処理。
	$("#txtCollectionDestination").focusout(function(){
		setTimeout(function(){
			if (!isClickButton) {
				var iMode = $("#mode").val();
				if (iMode == SHINKI_MODE || iMode == TEISEI_MODE) {
					if (txtCollectionDestination != $("#txtCollectionDestination").val()) {
						$("#collectionDestination").html("");
					}					
					removeInfoClass();
					var errorID = chkItem($('#txtCollectionDestination').val(), false, TYPE_NUM_ALPHA, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "集約先店舗コード"));
						$("#txtCollectionDestination").addClass("form-error-field");
					}else {
						$("#txtCollectionDestination").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focus', '#txtDate', function(){
		var mode = $('#mode').val();
		if (mode == SHINKI_MODE || mode == TEISEI_MODE) {
			var txtDate = $('#txtDate').val().replace(/\//g, '');
			$("#txtDate").val(txtDate);
		}
		
	});
	
	$(document).on('focusout', '#txtDate', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var mode = $('#mode').val();
				if (mode == SHINKI_MODE || mode == TEISEI_MODE) {
					removeInfoClass();
					var txtDate = $('#txtDate').val().replace(/\//g, '');
					var error = chkItem(txtDate, false, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
					if (error != null) {
						$("#txtMess").html($("#" + error).val().replace("{1}", "取引中止日"));
						$("#txtDate").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtDate").removeClass("form-error-field");
					}
					if (txtDate != "") {
						var yearStart = txtDate.substring(0,4);
						var monthStart = txtDate.substring(4,6);
						var dayStart = txtDate.substring(6,8);
						var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;
						$("#txtDate").val(formatDateStart);
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	// 得意先コードにフォーカスが当たった場合の処理
	$("#chkDestinationStore").click(function(){
		if ($("#chkDestinationStore").is(':checked')) {
			$('#ddlCondition').attr("disabled",false);
			$("#txtCollectionDestination").prop("readonly",true);
			$("#txtCollectionDestination").addClass("txt_disable");
		} else {
			$('#ddlCondition').attr("disabled",true);
			$("#txtCollectionDestination").prop("readonly",false);
			$("#txtCollectionDestination").removeClass("txt_disable");
		}
	});

	// 戻るボタンをクリックされた時の処理。得意先マスタ一覧画面に戻る
	$("#btnBack").bind("click", function() {
		
		disableReturnButton("btnBack");
		$("#btnBackHidden").trigger("click");
		enableReturnButton("btnBack");
	});
	// 事業所の選択肢が変更された際の処理。
	$("#ddlOffice").change(function(){
		var businessDate = $("#businessDate").val();
		var officeCode = $("#ddlOffice").find('option:selected').val();
		var customerCode = $("#txtCustomerCode").val();
		$("#officeSelect").val(officeCode);
		if (customerCode != "" && officeCode != "" && officeCode != undefined) {
			$.ajax({
				type : "POST",
				url : "getDeliveryCenter",
				async : false,
				data: {"businessDate" : businessDate,"officeCode" : officeCode, "customerCode" : customerCode},
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(returnData) {
					$("#ddlDeliveryCenter option").remove();
					var data = JSON.parse(returnData);
					if(data != "") {
						if (data[0].addr1 != null) {
							if(data[0].addr1 == "null" || data[0].addr1 == "noData") {
								$("#ddlDeliveryCenter").attr("disabled",true);
							} else {
								$("#txtMess").html(data[0].addr1);
								$("#ddlDeliveryCenter").attr("disabled",true);
								errorControl();
								$('#errControl').val('');
							}
						} else {
							$("#ddlDeliveryCenter").attr("disabled",false);
							$("#ddlDeliveryCenter").append(new Option("", ""));
							for (var i = 0 ; i < data.length; i++ ) {
								$("#ddlDeliveryCenter").append(new Option(data[i].nhscd + " : " + data[i].nhsmei,data[i].nhscd));
							}
						}
					}
				},
				error : function(e) {
					$("#txtMess").html(EXCEPTION_ERROR);
				},
				complete : function(jqXHR, textStatus) {
				}
			});
		}
	});

	// 登録ボタンをクリックされた時の処理。
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
						isClickButton = false;
						return;
					}
				}
				$("#btnRegisterHidden").trigger("click");
			} else {
				enableButton("btnRegister");
				isClickButton = false;
			}
		});
	});

	// クリアボタンが押下された場合の処理
	$("#btnClear").bind("click",function(e) {
		isClickButton = true;
		jQuestion_confirm($("#COM001-I02").val(), $("#COM001-I02").attr("title"), function(retVal) {
			if (retVal) {
				var mode = $('#mode').val();
				$('input').removeClass('form-error-field');
				$('select').removeClass('form-error-field');
				$("#txtMess").html("");
				removeInfoClass();
				if (mode == TEISEI_MODE) {
					var businessDate = $("#businessDate").val();
					var customerCode = $("#txtCustomerCode").val();
					var storeCode = $("#txtStoreCode").val();
					$.ajax({
						type : "POST",
						url : "getStoreData",
						async : false,
						data: {"businessDate" : businessDate,"customerCode" : customerCode, "storeCode" : storeCode},
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success : function(returnData) {
							var data = JSON.parse(returnData);
							if(data != "") {
								if (data.custCode != null) {
									clearModeTeisei(data);
								} else {
									$("#txtMess").html(data.adr1);
									errorControl();
									$('#errControl').val('');
								}
							}
						},
						error : function(e) {
							$("#txtMess").html(EXCEPTION_ERROR);
						},
						complete : function(jqXHR, textStatus) {
						}
					});
				} else if (mode == SHINKI_MODE) {
					clearModeShinki();
				}
			}
		});
	});

	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});

	/**
	 * 画面表示モード　＝　'3'
	 */
	function clearModeTeisei( data){
		$("#txtStoreName").val(data.shopNm);
		$("#txtStoreNameKana").val(data.shopNmKana);
		$("#txtStoreAbbreviation").val(data.shopNmR);
		var arrZipCode = data.zipCode.split("-");
		$("#txtPostalCode1").val(arrZipCode[0]);
		$("#txtPostalCode2").val(arrZipCode[1]);
		$("#txtPostalCodeResult").val(arrZipCode[0] + arrZipCode[1]);
		$("#txtAddress1").val(data.adr1);
		$("#txtAddress2").val(data.adr2);
		var arrPhone = data.telNo.split("-");
		$("#txtPhoneNumber1").val(arrPhone[0]);
		$("#txtPhoneNumber2").val(arrPhone[1]);
		$("#txtPhoneNumber3").val(arrPhone[2]);
		$("#txtPhoneNumberResult").val(arrPhone[0] + arrPhone[1] + arrPhone[2]);
		var arrFax = data.faxNo.split("-");
		$("#txtFaxNumber1").val(arrFax[0]);
		$("#txtFaxNumber2").val(arrFax[1]);
		$("#txtFaxNumber3").val(arrFax[2]);
		$("#txtFaxNumberResult").val(arrFax[0] + arrFax[1] + arrFax[2]);
		var sysAdminFlag = $("#sysAdminFlag").val();
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlOffice").val(data.jigyoCode);
			$("#officeSelect").val(data.jigyoCode);
		} else {
			$("#ddlOffice").val("");
			$("#officeSelect").val("");
		}
		$("#ddlDeliveryCenter").attr("disabled",ddlDeliveryCenterDisable);
		$("#ddlDeliveryCenter").val(data.deliCenterCode);
		$("#deliveryCenterSelect").val(data.deliCenterCode);
		$("#ddlThanksClassification").val(data.sunksKb);
		$("#thanksClassificationSelect").val(data.sunksKb);
		$("#txtFixMisejika").val(data.stCodeSts);
		$("#txtFixCenter").val(data.stCodeStc);
		$("#txtSaleMisejika").val(data.stCodeSps);
		$("#txtSaleCenter").val(data.stCodeSpc);
		if (data.sumShopKb == CHECK_OFF) {
			$("#chkDestinationStore").prop("checked",false);
		} else {
			$("#chkDestinationStore").prop("checked",true);
		}
		$("#ddlCondition").val(data.sumShopJkn);
		$("#conditionSelect").val(data.sumShopJkn);
		$("#ddlCondition").attr("disabled",false);
		$("#txtCollectionDestination").val(data.sumShopCode);
		$("#txtCollectionDestination").removeClass("txt_disable");
		$("#collectionDestination").html(data.shopNmSus);
		$("#txtStatusCode").val(data.stsCode);
		var formatDateEnd = "";
		if (data.closeDate.trim() != ""){
			var yearEnd = data.closeDate.substring(0,4);
			var monthEnd = data.closeDate.substring(4,6);
			var dayEnd = data.closeDate.substring(6,8);
			formatDateEnd = yearEnd + "/" + monthEnd + "/" + dayEnd;
		}
		
		$("#txtDate").val(formatDateEnd);
		
		$("#txtStoreName").focus();
		enableButton("btnRegister");
		enableButton("btnClear");
	}

	/**
	 * 画面表示モード ＝　'1'
	 */
	function clearModeShinki(){
		$("#txtCustomerCode").val("");
		enableImgButton("btn_search_customer_id");
		$("#customerName").html("");
		$("#txtStoreCode").val("");
		$("#txtStoreName").val("");
		$("#txtStoreNameKana").val("");
		$("#txtStoreAbbreviation").val("");
		$("#txtPostalCode1").val("");
		$("#txtPostalCode2").val("");
		$("#txtAddress1").val("");
		$("#txtAddress2").val("");
		$("#txtPhoneNumber1").val("");
		$("#txtPhoneNumber2").val("");
		$("#txtPhoneNumber3").val("");
		$("#txtFaxNumber1").val("");
		$("#txtFaxNumber2").val("");
		$("#txtFaxNumber3").val("");
		$("#ddlOffice").val("");
		$("#ddlDeliveryCenter option").remove();
		$("#ddlDeliveryCenter").attr("disabled",true);
		$("#ddlThanksClassification").val("");
		$("#txtFixMisejika").val("");
		$("#txtFixCenter").val("");
		$("#txtSaleMisejika").val("");
		$("#txtSaleCenter").val("");
		$("#chkDestinationStore").prop("checked",false);
		$("#ddlCondition").val("");
		$("#ddlCondition").attr("disabled",false);
		$("#txtCollectionDestination").val("");
		$("#txtCollectionDestination").attr("disabled",false);
		$("#collectionDestination").html("");
		$("#txtDate").val("");
		$("#txtStatusCode").val("1");
		$(".tbl_info_table td").parent().remove();
		$("#txtCustomerCode").focus();
		enableButton("btnRegister");
		enableReturnButton("btnBack");
		enableButton("btnClear");
	}
	/**
	 * フォーカス処理
	 */
	function setFocus(){
		var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
		var mode = $('#mode').val();
		if (typeof idError === "undefined") {
			if (mode == SHINKI_MODE) {
				$('#txtCustomerCode').focus();
			}else if (mode == SHOUKAI_MODE){
				$('#btnBack').focus();
			}else if (mode == TEISEI_MODE){
				$('#txtStoreName').focus();
			}else if (mode == TORIKESI_MODE){
				$('#btnRegister').focus();
			}
		}else {
			$("#"+idError).select();
			$("#"+idError).focus();
		}
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
	 *　更新処理前/後に保存する
	 * 
	 * @param 
	 * @return array 
	 * @exception
	 */
	function saveData() {
		tempValue = [];
		tempValue.push($('#txtCustomerCode').val());
		tempValue.push($('#txtStoreCode').val());
		tempValue.push($('#txtStoreName').val());
		tempValue.push($('#txtStoreNameKana').val());
		tempValue.push($('#txtStoreAbbreviation').val());
		tempValue.push($('#txtPostalCode1').val());
		tempValue.push($('#txtPostalCode2').val());
		tempValue.push($('#txtAddress1').val());
		tempValue.push($('#txtAddress2').val());
		tempValue.push($('#txtPhoneNumber1').val());
		tempValue.push($('#txtPhoneNumber2').val());
		tempValue.push($('#txtPhoneNumber3').val());
		tempValue.push($('#txtFaxNumber1').val());
		tempValue.push($('#txtFaxNumber2').val());
		tempValue.push($('#txtFaxNumber3').val());
		tempValue.push($('#officeSelect').val());
		tempValue.push($('#deliveryCenterSelect').val());
		//tempValue.push($('#ddlThanksClassification').find('option:selected').val());
		tempValue.push($('#thanksClassificationSelect').val());
		tempValue.push($('#txtFixMisejika').val());
		tempValue.push($('#txtSaleMisejika').val());
		tempValue.push($('#txtFixCenter').val());
		tempValue.push($('#txtSaleCenter').val());
		tempValue.push($('#chkDestinationStore').is(':checked'));
		//tempValue.push($('#ddlCondition').find('option:selected').val());
		tempValue.push($('#conditionSelect').val());
		tempValue.push($('#txtCollectionDestination').val());
		tempValue.push($('#txtDate').val());
		tempValue.push($('#txtStatusCode').val());
		return tempValue;
	}
	// 得意先検索画面表示
	$(document).on("click", "#btn_search_customer_id", function(e) {
		if (isBtnImgEnabled("btn_search_customer_id")) {
			var strJigyo = "";
			var sysAdminFlg = $("#sysAdminFlag").val();
			if (sysAdminFlg == SYS_ADMIN_FLAG_USER) {
				strJigyo = $("#loginJigyouShoCode").val();
			} else if(sysAdminFlg == SYS_ADMIN_FLAG_ADMIN) {
				strJigyo = "";
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
