/**
 * ファイル名:mst0108d02.js 
 * 概要:得意先品目価格マスタ管理
 * 
 * 作成者:ABV）ギア 作成日:2015/11/16
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/16 1.00 ABV）ギク 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
//変更前listの保存
	var modValue = [];
$(document).ready(function(){
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0108D02";
	//Dialogのadd_clickイベントの作成
	loadDialogCom0102d02(); 
	loadDialogCom0102d03();	
	loadDialogCom0102d04();
	var DELIMITER = "##";
	var TYPE_UNDEFINED = "undefined";
	//（新規）　の場合
	var viewMode = $("#viewMode").val();
	checkViewMode();
	//[変数]得意先コード_初期値
	var custCode = "";
	// [変数]品目コード_初期値
	var itemCode = '';
	//[変数]店舗コード_初期値
	var shopCode = "";
	var validTo = "";
	var salesKb = "";
	//sysAdminFlag
	var sysAdminFlag = $("#sysAdminFlag").val();
	// [変数]入力チェックエラーフラグ　
	var inputCheckErrorFlag = false;
	var flag = $("#flag").val();
	// （登録画面）得意先コード_GetFocus_VN
	var errControl = $('#errorControll').val();
	if (viewMode != SHINKI_MODE) {
		if(errControl != "") {
			errorControl();
		}
	}
	
	var isClickButton = false;
	
	$("#cstCode").bind("focus",function() {
		if ($(this).val() != null && $(this).val().trim() != "") {
			custCode = $(this).val().trim();
		}
	});
	
	
	var infoMessFlag = $("#infoMessFlag").val()
	if (infoMessFlag == "true") {
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
	
	/**
	 *　更新処理前/後に保存する
	 * 
	 * @param 
	 * @return array 
	 * @exception
	 */
	function saveData() {
		tempValue = [];
		tempValue.push($('#cstCode').val());
		tempValue.push($('#custNmR').val());
		tempValue.push($('#itemCode').val());
		tempValue.push($('#itemNmR').val());
		tempValue.push($('#validFrom').val());
		tempValue.push($('#validTo').val());
		tempValue.push($('#shopCode').val());
		tempValue.push($('#shopNmR').val());
		tempValue.push($('#custDeliTanka').val());
		tempValue.push($('#custSellTanka').val());
		tempValue.push($('#custBildTanka').val());
		tempValue.push($('#bunruiCode').val());
		tempValue.push($('#stsCode').val());
		return tempValue;
	}
	
	function compare() {
		var newValue = saveData();
		//compare oldValue and newValue
		for (var i = 0 ; i < newValue.length ; i++) {
			if (modValue[i] != newValue[i]) {
				return true;
			} 
		}
		return false;
	}
	
	var strError = $("#lstErrorID").val();
	if (strError != null && strError != "") {
		var lstError = strError.split(DELIMITER);
		addClassErrorToControl(lstError);
		
	}
	
	// 使用中止日を有効にする
	$("#validTo").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true
	});
	$("#validFrom").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true
	});
	//（登録画面）得意先コード_LostFocus _VN
	$("#cstCode").bind("focusout",function() {
				if (viewMode == SHINKI_MODE || viewMode == COPY_MODE) {
					removeInfoClass();
					var errorID = chkItem($('#cstCode').val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先コード"));
						$("#cstCode").addClass("form-error-field");
					} else {
						$("#cstCode").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}	
				if (custCode != $(this).val().trim()) {
					$("#custNmR").html("");
				}
				if ($(this).val().trim() == "") {
					disableImgButton("btn_search_item_code");
					disableImgButton("btn_search_shop_code");
				} else {
					enableImgButton("btn_search_item_code");
					enableImgButton("btn_search_shop_code");
				}
				
			
	});
	//（一覧画面）品目コード_GetFocus_VN
	$("#itemCode").bind("focus",function(){
		itemCode = $(this).val().trim();
	});
	//（一覧画面）品目コード_LostFocus_VN
	$("#itemCode").bind("focusout",function(){
			if (viewMode == SHINKI_MODE || viewMode == COPY_MODE ) {
				removeInfoClass();
				var errorID = chkItem($('#itemCode').val(), true, TYPE_NUM, false, null);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "品目コード"));
					$("#itemCode").addClass("form-error-field");
				}else {
					$("#itemCode").removeClass('form-error-field');
					$("#txtMess").html("");
				}
			}	
			if (itemCode != $(this).val().trim()) {
				$("#itemNmR").html("");
			}
	});
	//（一覧画面）店舗コード_GetFocus _VN
	$("#shopCode").bind("focus",function(){
		shopcode = $(this).val().trim();
	});
	//（一覧画面）店舗コード_LostFocus_VN
	$("#shopCode").bind("focusout",function() {
			if (viewMode == SHINKI_MODE || viewMode == COPY_MODE ) {
				removeInfoClass();
				var errorID = chkItem($('#shopCode').val(), false, TYPE_NUM, false, null);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "店舗コード"));
					$("#shopCode").addClass("form-error-field");
				}else {
					$("#shopCode").removeClass('form-error-field');
					$("#txtMess").html("");
				}
			}	
			if (shopcode != $(this).val().trim()) {
				$("#shopNmR").html("");
			}
	});
	$("#custDeliTanka").bind("focusout",function(){
				removeInfoClass();
				var errorID = chkNum($('#custDeliTanka').val(), false);
					errorID = chkEmpty($('#custDeliTanka').val());
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "納品単価"));
					$("#custDeliTanka").addClass("form-error-field");
				}else {
					$("#custDeliTanka").removeClass('form-error-field');
					$("#txtMess").html("");
				}
	});
	$("#custSellTanka").bind("focusout",function(){
				removeInfoClass();
				var errorID = chkNum($('#custSellTanka').val(), false);
					errorID = chkEmpty($('#custSellTanka').val());
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "販売単価"));
					$("#custSellTanka").addClass("form-error-field");
				}else {
					$("#custSellTanka").removeClass('form-error-field');
					$("#txtMess").html("");
				}
	});
	$("#custBildTanka").bind("focusout",function(){
				removeInfoClass();
				var errorID = chkNum($('#custBildTanka').val(), false);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "先方仕切単価"));
					$("#custBildTanka").addClass("form-error-field");
				}else {
					$("#custBildTanka").removeClass('form-error-field');
					$("#txtMess").html("");
				}
	});
	$("#bunruiCode").bind("focusout",function(){
				removeInfoClass();
				var errorID = chkNum($('#bunruiCode').val(), false);
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "先方仕切単価"));
					$("#bunruiCode").addClass("form-error-field");
				}else {
					$("#bunruiCode").removeClass('form-error-field');
					$("#txtMess").html("");
				}
	});
	$("#stsCode").bind("focusout",function(){
				removeInfoClass();
				var errorID = chkNum($('#stsCode').val(), false);
					errorID = chkEmpty($('#stsCode').val());
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "先方仕切単価"));
					$("#stsCode").addClass("form-error-field");
				}else {
					$("#stsCode").removeClass('form-error-field');
					$("#txtMess").html("");
				}
	});
	$("#MasutaKubunClassList").bind("focusout",function(){
				if (viewMode == SHINKI_MODE || viewMode == COPY_MODE ) {
					removeInfoClass();
					var errorID = chkItem($('#MasutaKubunClassList').val(), true, TYPE_NUM, false, null);
					if (errorID != null){
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "先方仕切単価"));
						$("#MasutaKubunClassList").addClass("form-error-field");
					}else {
						$("#MasutaKubunClassList").removeClass('form-error-field');
						$("#txtMess").html("");
					}
				}
	});
	$(document).on('focus', '#validFrom', function(){
		var mode = $('#viewMode').val();
		if (mode == SHINKI_MODE || mode == COPY_MODE) {
			var txtDate = $('#validFrom').val().replace(/\//g, '');
			$("#validFrom").val(txtDate);
		}
		
	});

	$(document).on('focusout', '#validFrom', function(){

		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var txtDate = $('#validFrom').val().replace(/\//g, '');
				var error = chkItem(txtDate, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
				if (error != null) {
					$("#txtMess").html($("#" + error).val().replace("{1}", "適用期間（開始）"));
					$("#validFrom").addClass("form-error-field");
				} else {
					$("#validFrom").removeClass("form-error-field");
				}
				if (txtDate != "") {
					var yearStart = txtDate.substring(0,4);
					var monthStart = txtDate.substring(4,6);
					var dayStart = txtDate.substring(6,8);
					var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;
					$("#validFrom").val(formatDateStart);
					if (error == null) {
						if (!$("#validTo").hasClass("form-error-field")) {
							var To = $("#validTo").val();
							var from = $("#validFrom").val();
							if (To == "" | from == "") {
								return false;
							} else {
								// replace date YYYYMMDD
								from = checkDateTime(from);
								To = checkDateTime(To);
								if (from > To) {
									$("#txtMess").html("<span>"+$("#COM016-E").val()+"</span>");
									$("#validFrom").addClass("form-error-field");
									$("#validFrom").focus().select();
								} else {
									$("#validFrom").removeClass("form-error-field");
									$("#txtMess").html("");
								}
							}
						}							
					}
				} 
			} else {
				isClickButton = false;
			}
			
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#validTo', function(){
		
		setTimeout(function(){
			if (!isClickButton) {
				removeInfoClass();
				var txtDate = $('#validTo').val().replace(/\//g, '');
				
				var error = chkItem(txtDate, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
				if (error != null) {
					$("#txtMess").html($("#" + error).val().replace("{1}", "適用期間（終了）"));
					$("#validTo").addClass("form-error-field");
				} else {
					$("#validTo").removeClass("form-error-field");
				}
				
				if (txtDate != "") {
					var yearStart = txtDate.substring(0,4);
					var monthStart = txtDate.substring(4,6);
					var dayStart = txtDate.substring(6,8);
					var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;
					$("#validTo").val(formatDateStart);
					if (error == null) {
						if (!$("#validFrom").hasClass("form-error-field")) {
							var To = $("#validTo").val();
							var from = $("#validFrom").val();
							if (To == "" | from == "") {
								return false;
							} else {
								// replace date YYYYMMDD
								from = checkDateTime(from);
								To = checkDateTime(To);
								if (from > To) {
									$("#validFrom").addClass("form-error-field");
									$("#txtMess").html("<span>"+$("#COM016-E").val()+"</span>");
									$("#validFrom").focus().select();
								} else {
									$("#txtMess").html("");
									$("#validTo").removeClass("form-error-field");
								}
							}
						}
					}
				} 		
			
		} else {
			isClickButton = false;
		}
		
	}, FOCUSOUT_TIMEOUT);
	});
	$(document).on('focus', '#validTo', function(){
		var mode = $('#viewMode').val();
		validTo = $("#validTo").val().trim();
		validTo = checkDateTime(validTo);
		if (mode == SHINKI_MODE || mode == COPY_MODE) {
			var txtDate = $('#validTo').val().replace(/\//g, '');
			$("#validTo").val(txtDate);
		}
		
	});
	
	$(document).on('focus', '#MasutaKubunClassList', function(){
		if (viewMode == SHINKI_MODE || viewMode == COPY_MODE) {
			salesKb = $("#MasutaKubunClassList").val();
		}
		
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
	
	// call Popup DFCM-COM01-02D02
	$("#btn_search_customer_id").on("click", function(e){
		// get loginJigyouShoCode
		
		var JigyouShoCode = $("#loginJigyouShoCode").val().trim();
		var officeCode = "";
		//(1) 事業所コード編集
		if (sysAdminFlag == LENGTH_ZERO ) {
			//[変数]セッション.システム管理者フラグ = 0（無効）の場合
			officeCode = JigyouShoCode;
		} else {
			//[変数]セッション.システム管理者フラグ = 1（有効）の場合
			officeCode = "";
		}
		var custCode = $("#cstCode").val().trim();	
		var shopCode = "";
		var searchKb = "1";
		// 検索子画面呼び出し用関数を呼び出す
		showCom0102d02(officeCode, custCode, shopCode, searchKb);
	});
	
	$("#btn_search_item_code").on("click", function(){
		//getStoreFinder(customerCode, storeCode);
		if($(this).attr("src").indexOf("dis") < LENGTH_ZERO) {
			var changeCodeWk = "";
			var changeBranchWk = "";
			var ourCompanyItemCodeWk = $("#itemCode").val().trim();
			var customerCodeWk = $("#cstCode").val().trim();
			var saleTypeWk = "";
			var deadlineWk = $("#bussinessDate").val().trim();
			var flightWk = "";
			showCom0102d04(changeCodeWk,changeBranchWk,ourCompanyItemCodeWk,
					customerCodeWk,saleTypeWk,deadlineWk,flightWk);
		}
	});
	//Com0102d03
	$("#btn_search_shop_code").on("click", function() {
		// getStoreFinder(customerCode, storeCode);
		if($(this).attr("src").indexOf("dis") < LENGTH_ZERO) {
			// 変数値の情報を得ます
			var custCode = $("#cstCode").val().trim();
			custCode = replaceText(custCode);
			var shopCode = $("#shopCode").val().trim();
			shopCode = replaceText(shopCode);
			showCom0102d03(custCode,shopCode);
		}
	});
	
	
	$("#btnClear").bind("click",function(){
		var viewMode = $("#viewMode").val();
		jQuestion_confirm($("#COM001-I1").val(), $("#COM001-I1").attr("title"), function(retVal) {
			if (retVal) {
				$('input').removeClass('form-error-field');
				$('select').removeClass('form-error-field');
				$("#txtMess").html("");
				removeInfoClass();
				if (viewMode == 1) {					
					$("#cstCode").val("").removeClass("form-error-field");
					$("#itemCode").val("").removeClass("form-error-field");
					$("#shopCode").val("").removeClass("form-error-field");
					$("#MasutaKubunClassList").val("").removeClass("form-error-field");
					$("#validFrom").val("").removeClass("form-error-field");
					$("#validTo").val("").removeClass("form-error-field");
					$("#shopCode").val("").removeClass("form-error-field");
					$("#custDeliTanka").val(0).removeClass("form-error-field");
					$("#custSellTanka").val(0).removeClass("form-error-field");
					$("#custBildTanka").val(0).removeClass("form-error-field");
					$("#stsCode").val("").removeClass("form-error-field");
					$("#bunruiCode").val("").removeClass("form-error-field");
					$("#custNmR").text("");
					$("#itemNmR").text("");
					$("#shopNmR").text("");
					$("#cstCode").focus().select();
				} else if(viewMode == TEISEI_MODE || viewMode == COPY_MODE) {
					getDataBtnClear();
					
				}
			}
		})
	});
	//function getDataforBtnClear
	function getDataBtnClear(){
		var cstCode = "";
		var itemCodeClear = "";
		var shopCodeClear= "";
		var salesKbClear = "";
		var validToClear = "";
		if (viewMode == COPY_MODE || viewMode == TEISEI_MODE) {
			cstCode = $("#cstCode").val().trim();
			itemCodeClear = $("#itemCode").val().trim();
			salesKbClear = $("#MasutaKubunClassList").val().trim();
			shopCodeClear = $("#shopCode").val();
			var validToClear = $("#validTo").val().trim();
			validToClear = checkDateTime(validToClear);
			if (custCode != cstCode && custCode != "") {
				cstCode = custCode
			} else {
				cstCode = $("#cstCode").val().trim();
			}
			if (itemCode != itemCodeClear && itemCode != "") {
				itemCodeClear = itemCode
			} else {
				itemCodeClear = $("#itemCode").val().trim();
			}
			if (shopCode != shopCodeClear && shopCode != "") {
				shopCodeClear = shopCode
			} else {
				shopCodeClear = $("#shopCode").val();
			}
			if (salesKb != salesKbClear && salesKb != "") {
				salesKbClear = salesKb
			} else {
				salesKbClear = $("#MasutaKubunClassList").val().trim();
			}
			if (validTo != validToClear && validTo != "") {
				validToClear = validTo
			} else {
				var validToClear = $("#validTo").val().trim();
				validToClear = checkDateTime(validToClear);
			}
		} else {
			
		}
		var custNmR = $("#custNmR").text();
		var itemNmR = $("#itemNmR").text();
		var shopNmR = $("#shopNmR").text();
		var validFrom = $("#validFrom").val().trim();
		validFrom = checkDateTime(validFrom);
		
		$.ajax({
			type : "POST",
			url : rootPath+"/mst/MST01-08D02/clear",
			async : true,
			data: {
				custCode : cstCode,
				itemCode : itemCodeClear,
				shopCode : shopCodeClear,
				salesKb : salesKbClear,
				validTo : validToClear,
				validFrom : validFrom
			},
			success:function(returnData) {
				var Data = returnData;
				if (Data == null) {
					var COM009E1 = $("#COM009-E1").val().trim();
					$("#txtMess").html("<span>"+COM009E1+"</span>");
					return false;
				} else {
					if (viewMode == TEISEI_MODE) {
						var from = setDateTime(Data[0].validFrom);
						$("#validFrom").val(from);
						$("#custNmR").val()
						$("#validFrom").attr("disabled",true);
						$("#stsCode").val(Data[0].stsCode);
						$("#custSellTanka").val(Data[0].custSellTanka);
						var custDeliTanka = Data[0].custDeliTanka;
						index = custDeliTanka.toString().indexOf('.');
						if (index != -1) {
							deliTanka = custDeliTanka;
						} else {
							var deliTanka = "";
							var deliTanka = custDeliTanka.toString() + ".00";
						}
						$("#custDeliTanka").val(deliTanka);
						
						$("#custBildTanka").val(Data[0].custBildTanka);
						var custBildTanka = Data[0].custBildTanka;
						index = custBildTanka.toString().indexOf('.');
						if (index != -1) {
							bildTanka = custBildTanka;
						} else {
							var bildTanka = "";
							var bildTanka = custBildTanka.toString() + ".00";
						}
						$("#custBildTanka").val(bildTanka);
						$("#bunruiCode").val(Data[0].bunruiCode);
						$("#custDeliTanka").focus().select();
						$("#custNmR").text(custNmR);
						$("#itemNmR").text(itemNmR);
						$("#shopNmR").text(shopNmR);
					} else if (viewMode == COPY_MODE) {
						$("#cstCode").val(Data[0].custCode);
						$("#itemCode").val(Data[0].itemCode);
						$("#MasutaKubunClassList").val(Data[0].salesKb);
						if (Data[0].shopCode == "NONE") {
							Data[0].shopCode = "";
						}
						$("#shopCode").val(Data[0].shopCode);
						$("#validTo").val(setDateTime(Data[0].validTo));
						var from = setDateTime(Data[0].validFrom);
						$("#validFrom").val(from);
						$("#custNmR").val()
						$("#stsCode").val(Data[0].stsCode);
						$("#custSellTanka").val(Data[0].custSellTanka);
						var custDeliTanka = Data[0].custDeliTanka;
						index = custDeliTanka.toString().indexOf('.');
						if (index != -1) {
							deliTanka = custDeliTanka;
						} else {
							var deliTanka = "";
							var deliTanka = custDeliTanka.toString() + ".00";
						}
						$("#custDeliTanka").val(deliTanka);
						
						$("#custBildTanka").val(Data[0].custBildTanka);
						var custBildTanka = Data[0].custBildTanka;
						index = custBildTanka.toString().indexOf('.');
						if (index != -1) {
							bildTanka = custBildTanka;
						} else {
							var bildTanka = "";
							var bildTanka = custBildTanka.toString() + ".00";
						}
						$("#custBildTanka").val(bildTanka);
						$("#bunruiCode").val(Data[0].bunruiCode);
						$("#cstCode").focus().select();
						$("#custNmR").text(custNmR);
						$("#itemNmR").text(itemNmR);
						$("#shopNmR").text(shopNmR);
					}
					
				}
			},
			error : function(e) {
				errMsg = "";
				alert(e.errMsg);
			},
			complete : function(jqXHR, textStatus) {

			}
		});
	}
	//function checlViewMode
	function checkViewMode() {
		//'-1. [入力]画面表示モード　＝　'1'　（新規）　の場合
		if (viewMode == SHINKI_MODE ) {
			// ボタンを無効にする
			//[画面]店舗検索ボタン && [画面]品目検索ボタン
			disableImgButton("btn_search_item_code");
			disableImgButton("btn_search_shop_code");
			
		}  else if (viewMode == SHOUKAI_MODE) {
			//無効に完全な形式
			setReadOnly("cstCode");
			setReadOnly("itemCode");
			setReadOnly("shopCode");
			setReadOnly("validFrom")
			setReadOnly("validTo");
			$(".calendar").removeClass("img_displaynone");
			$("#validFrom").datepicker({
				showOn: "hidden",
			});
			$("#validTo").datepicker({
				showOn: "hidden",
			});
			setReadOnly("custDeliTanka");
			setReadOnly("custSellTanka");
			setReadOnly("custBildTanka");
			setReadOnly("bunruiCode");
			setReadOnly("stsCode");
			setReadOnly("MasutaKubunClassList");
			$("#MasutaKubunClassList").attr("disabled","disabled");
			$("#btnBack").attr("disabled",false);
			disableButton("btnClear");
			disableButton("btnRegister1");
			//[画面]店舗検索ボタン && [画面]品目検索ボタン && [画面]得意先検索ボタン
			disableImgButton("btn_search_customer_id");
			$("#btn_search_customer_id").attr("disabled","disabled");
			disableImgButton("btn_search_item_code");
			disableImgButton("btn_search_shop_code");
		} else if (viewMode == TEISEI_MODE) {
			//条件ビューモードを無効にする==3
			setReadOnly("cstCode");
			setReadOnly("itemCode");
			setReadOnly("shopCode");
			setReadOnly("validFrom")
			setReadOnly("validTo");
			
			$(".calendar").removeClass("img_displaynone");
			$("#validFrom").datepicker({
				showOn: "hidden",
			});
			$("#validTo").datepicker({
				showOn: "hidden",
			});
			setReadOnly("MasutaKubunClassList");
			$("#MasutaKubunClassList").attr("disabled","disabled");
			//[画面]店舗検索ボタン && [画面]品目検索ボタン && [画面]得意先検索ボタン
			disableImgButton("btn_search_customer_id");
			$("#btn_search_customer_id").attr("disabled","disabled");
			disableImgButton("btn_search_item_code");
			disableImgButton("btn_search_shop_code");
		} else if (viewMode == TORIKESI_MODE) {
			//無効に完全な形式
			setReadOnly("cstCode");
			setReadOnly("itemCode");
			setReadOnly("shopCode");
			setReadOnly("validFrom")
			setReadOnly("validTo");
			setReadOnly("custDeliTanka");
			setReadOnly("custSellTanka");
			setReadOnly("custBildTanka");
			setReadOnly("bunruiCode");
			setReadOnly("stsCode");
			
			$(".calendar").removeClass("img_displaynone");
			$("#validFrom").datepicker({
				showOn: "hidden",
			});
			$("#validTo").datepicker({
				showOn: "hidden",
			});
			$("#MasutaKubunClassList").attr("disabled","disabled");
			disableButton("btnClear");/*
			$("#btnSearch").attr("disabled",false);*/
			//[画面]店舗検索ボタン && [画面]品目検索ボタン && [画面]得意先検索ボタン
			disableImgButton("btn_search_customer_id");
			disableImgButton("btn_search_item_code");
			disableImgButton("btn_search_shop_code");
			$("#btn_search_customer_id").attr("disabled","disabled");
		} 
	}
	
	
	if (flag == 1) {
		var masterCheckErrorFlag = $("#masterCheckErrorFlag").val();
		if (masterCheckErrorFlag == true) {
			return false;
		}
		
		var errMess = $("#errMessage").val();
		var COM034E = $("#COM034-E").val();
		isClickButton = true;
		enableButton("btnRegister1");
		var MST001W = $("#MST001-W").val();
		if (MST001W.length < 0 ) {
			enableButton("btnRegister1");
			return false;
		} 
		$("#txtMess").html("");
		if (viewMode == SHINKI_MODE || viewMode == COPY_MODE) {
			jQuestion_confirm($("#MST001-W").val(), $("#MST001-W").attr("title"), function(retVal) {
				if (retVal) {
					if (viewMode == TEISEI_MODE) {
						if (!compare()) {
								enableButton("btnRegister1");
								$("#txtMess").html("<span class='text_color_black'>"+errMess+COM034E + "</span>");
								addInfoClass();
								return false;
						}	
					}
					$("#btnRegisterHidden2").trigger("click");
				} else {
					enableButton("btnRegister1");
				}
			});
		} else {
			$("#btnRegisterHidden2").trigger("click");
			isClickButton = true;
			errMess = $("#errMessage").val();
			$("#txtMess").html("<span class='text_color_black'>"+errMess+"</span>");
			addInfoClass();
			return false;
		}
	}

	if (flag == 3) {
		errMess = $("#errMessage").val();
		$("#txtMess").html("<span id = 'messError'>"+errMess+ "</span>");
		removeInfoClass();
		$("#flag").val(0);
	}
	
	var success = $("#success").val();
	var COM002I = $("#errMessage").val();
	if (success == 1) {
		$("#txtMess").html("<span>"+COM002I+"</span>");
		addInfoClass();
		//jstorage更新
		modValue = saveData();
		saveToStorage(SCREEN_NAME, modValue);
		$("#success").val(0);
		$("#flag").val(0);
	}
	function setReadOnly(id) {
		$("#"+id).attr("readonly",true);
		$("#"+id).addClass("txt_disable");
		$("#"+id).attr("tabIndex",-1);
	}
	$("#btnRegister1").click(function() {
		var strSelectedSaleType = $("#strSelectedSaleType").val();
		$("#salesKb").val(strSelectedSaleType);
		isClickButton = true;
		disableButton("btnRegister1");
		var viewMode = $("#viewMode").val();
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				if (viewMode == TEISEI_MODE) {
					if (!compare()) {
						//変更無
						enableButton("btnRegister1");
						$("#txtMess").html($("#COM034-E").val());
						removeInfoClass();
						return;
					}
				}
				$("#btnRegisterHidden1").trigger("click");
				//変更無
				enableButton("btnRegister1");
			} else {
				//変更無
				enableButton("btnRegister1");
			}
		});
		
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
			var mode = $('#viewMode').val();
			if (mode == SHINKI_MODE_CHAR || mode == COPY_MODE_CHAR) {
				$('#cstCode').focus();
			} else if (mode == SHOUKAI_MODE_CHAR) {
				$('#btnBack').focus();
			} else if (mode == TEISEI_MODE_CHAR) {
				$('#custDeliTanka').focus().select();
			} else if (mode == TORIKESI_MODE_CHAR) {
				$('#btnRegister1').focus();
			}
		}else {
			$("#"+idError).select();
			$("#"+idError).focus();
		}
	}
	
	
	setFocus();
});

//getCom0102d02
function getCom0102d02(data) {
	var custNm = "";
	for (var i = 0; i < data.length; i++) {
	$("#cstCode").val(data[i].custCode);
	$("#custNmR").text(data[i].custNmR);
	custNm = data[i].custNmR;
	}
	enableImgButton("btn_search_item_code");
	enableImgButton("btn_search_shop_code");
	$("#itemCode").focus().select();
	$("#cstCode").removeClass("form-error-field");
	$("#txtMess").html("");
	// 検索画面ダイアログを閉じる
	$("#dialog").empty();
	HideDialog();
}
// function getDataForTextbox
function getCom0102d04(ItemCodeWk,ItemNameWk) {
	$("#itemCode").val(ItemCodeWk);
	$("#itemNmR").text(ItemNameWk);
	$("#MasutaKubunClassList").focus().select();
	$("#itemCode").removeClass("form-error-field");
	$("#txtMess").html("");
	reTurnCheckTab();
}
function getCom0102d03(data) {
	 var showDataCnt = data.length;
	 for (var i = 0; i < showDataCnt; i++) {
	  $("#shopCode").val(data[i].shopCode);
	  $("#shopNmR").text(data[i].shopNmR);
	 }
	 $("#custDeliTanka").focus().select();
	 $("#shopCode").removeClass("form-error-field");
		$("#txtMess").html("");
	 reTurnCheckTab();
	}

/**
 * checkDateTime.
 * @param date
 * @returns
 */
function checkDateTime(date) {
	if (date.length < 10) {
		date = date.replace(/\//g,"0");
		return date;
	} else {
		date = date.replace(/\//g,"");
		return date;
	}
}
function setDateTime(date) {
	if (date != "") {
		var yearStart = date.substring(0,4);
		var monthStart = date.substring(4,6);
		var dayStart = date.substring(6,8);
		var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;
		return formatDateStart;
	}
	return date;
}