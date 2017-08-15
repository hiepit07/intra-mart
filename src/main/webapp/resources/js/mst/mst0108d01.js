/**
 * ファイル名:mst0108d01.js
 * 概要:得意先品目価格マスタ管理
 * 
 * 作成者:ABV）ギア
 * 作成日:2015/11/01
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/20 1.00 ABV）ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
$(document).ready(function(){
	
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0108D02";
	
	// jStorageのクリア
	clearStorage(SCREEN_NAME);	
	//Controlエラーの受信デリミター
	var DELIMITER = "##"
	var TYPE_UNDEFINED = "undefined";
	//Dialogのadd_clickイベントの作成
	loadDialogCom0102d02(); 
	loadDialogCom0102d03();	
	loadDialogCom0102d04();
	var ShozokuClassList = "";
	MasutaKubunClassList = $("#salesKb").val() 
	if (MasutaKubunClassList != "") {
		$("#MasutaKubunClassList").val(MasutaKubunClassList);
	}
	var ddlShozoku = $("#searchShozokuClassList").val();
	if (ddlShozoku != "") {
		$("#ddlShozoku").val(ddlShozoku);
	}
	var Error = $("#txtMess").val();
	// ボタンを無効にする
	disableImgButton("btn_search_customer_id04");
	disableImgButton("btn_search_customer_id03");
	// reset table
	resetTableSize();
	// フォーカスセット
	var flag = $("#flag").val();
	var bussinessDate = $("#bussinessDate").val();
	if (bussinessDate == "") {
		$("#txtMess").html("<span>"+$("#COM015-E").val()+"</span>")
	}
	var custCode = "";
	var custNm = "";
	var shopCode = "";
	var itemCode  = "";
	var isClickButton = false;
	var isTableMenuShowing = false;
	var currentShowingColId = "";
	//得意先コード
	var strSelectedCustCode ="";
	//品目コード
	var strSelectedItemCode = "";
	//店舗コード
	var strSelectedShopCode = "";
	//販売区分
	var strSelectedSaleType = "";
	//適用期間（終了）
	var strSelectedEndTime = "";
	// error COM016_E
	var COM016E = $(".COM016E").val();
	
	if (flag == 0) {
		$("#first").hide();
		$("#cstCode").focus().select();
	} else if (flag == 1 ) {
		$("#first").show();
		$("#ddlShozoku").focus().select();
		//search Data for EVT init
		if ($("#txtMess").html().trim() == "") {
			searchData();
		}
	}
	
	
	// set for CSV button
	var itemPriceInfo = $("#itemPriceInfo").val();
	if ( itemPriceInfo == '0') {
		disableButton("btnCSV");
	} else {
		enableButton("btnCSV");
	}
	//（共通仕様 3-6適用）
	var errorControll = $("#errorControll").val();
	if (errorControll != "") {
		errorControl();
	} 
	
	// getfocus Combobox
	$("#cstCode").bind("focus",function() {
		if ($(this).val() != null && $(this).val() != "") {
			custCode = $(this).val();
		}
	});
	//lostFocus Combobox
	$("#cstCode").bind("focusout",function() {
		if (custCode != $(this).val().trim()) {
			$("#custNmR").html("");
		}
		if ($(this).val().trim() == "") {
			disableImgButton("btn_search_customer_id04");
			disableImgButton("btn_search_customer_id03");
		} else {
			enableImgButton("btn_search_customer_id04");
			enableImgButton("btn_search_customer_id03");
		}
	});
	
	//エンターキー押下（タブ移動処理）
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this,".data_conditions_area");
		}
	});
	
	$(document).on('focus', '#validFrom', function(){
		var txtDate = $('#validFrom').val().replace(/\//g, '');
		$("#validFrom").val(txtDate);
	});

	$(document).on('focusout', '#validFrom', function(){
		setTimeout(function(){
			if (!isClickButton) {
					removeInfoClass();
					var txtDate = $('#validFrom').val().replace(/\//g, '');
					var error = chkItem(txtDate, false, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
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
					var error = chkItem(txtDate, false, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
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
			var txtDate = $('#validTo').val().replace(/\//g, '');
			$("#validTo").val(txtDate);
	});
	
	// 使用中止日を有効にする
	$("#validTo").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true,
	});
	$("#validFrom").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true,
	});
	//（一覧画面）品目コード_GetFocus_VN
	$("#itemCode").bind("focus",function(){
		itemCode = $(this).val();
	});
	//（一覧画面）品目コード_LostFocus_VN
	$("#itemCode").bind("focusout",function(){
		if (itemCode != $(this).val()) {
			$("#itemName").text("");
		}
	});
	//（一覧画面）店舗コード_GetFocus _VN
	$("#shopCode").bind("focus",function(){
		shopcode = $(this).val();
	});
	//（一覧画面）店舗コード_LostFocus_VN
	$("#shopCode").bind("focusout",function(){
		if (shopcode != $(this).val()) {
			$("#shopNm").text("");
		}
	});
	// call Popup DFCM-COM01-02D02
	$("#btn_search_customer_id02").on("click", function(e){
		// get loginJigyouShoCode
		var JigyouShoCode = $("#JigyouShoCode").val();
		var officeCode = "";
		var ShozokuClassList = $("#ddlShozoku").val();
		//(1) 事業所コード編集
		if (flag == 0 ) {
			//[変数]セッション.システム管理者フラグ = 0（無効）の場合
			officeCode = JigyouShoCode;
		} else {
			//[変数]セッション.システム管理者フラグ = 1（有効）の場合
			if (ShozokuClassList == "") {
				officeCode = "";
			} else {
				ShozokuClassList = ShozokuClassList.substring(0,2);
				officeCode = ShozokuClassList;
			}
		}
		var custCode = $("#cstCode").val();	
		var shopCode = "";
		var searchKb = "1";
		// 検索子画面呼び出し用関数を呼び出す
		showCom0102d02(officeCode, custCode, shopCode, searchKb);
	});
	
	$("#btn_search_customer_id04").on("click", function(){
		//getStoreFinder(customerCode, storeCode);
		if($(this).attr("src").indexOf("dis") < LENGTH_ZERO) {
			var changeCodeWk = "";
			var changeBranchWk = "";
			var ourCompanyItemCodeWk = $("#itemCode").val();
			var customerCodeWk = $("#cstCode").val();
			var saleTypeWk = "";
			var deadlineWk = $("#bussinessDate").val();
			var flightWk = "";
			showCom0102d04(changeCodeWk,changeBranchWk,ourCompanyItemCodeWk,
					customerCodeWk,saleTypeWk,deadlineWk,flightWk);
		}
	});
	//Com0102d03
	$("#btn_search_customer_id03").on("click", function() {
		// getStoreFinder(customerCode, storeCode);
		if($(this).attr("src").indexOf("dis") < LENGTH_ZERO) {
			// 変数値の情報を得ます
			var custCode = $("#cstCode").val();
			custCode = replaceText(custCode);
			var shopCode = $("#shopCode").val();
			shopCode = replaceText(shopCode);
			showCom0102d03(custCode,shopCode);
		}
	});
	// 検索ボタン押下
	$("#searchButtonMst0108d01").click(function(){
		$("#txtMess").html("");
		custCode = $("#cstCode").val();
		if (custCode != "") {
			$("#custNmR").text(custNm);
		}
		//入力文字数が７文字に満たない場合、0編集を行う。
		if (custCode.length <= 7) {
			if (custCode != "") {
				custCode = addLeadingChar(custCode,7);
			}
			$("#cstCode").val(custCode);
		}
		itemCode = $("#itemCode").val();
		// 入力文字数が６文字に満たない場合、0編集を行う。
		if (itemCode.length <= 6) {
			if (itemCode != "") {
				itemCode = addLeadingChar(itemCode,6);
				$("#itemCode").val(itemCode);
			}
		}
		shopCode = $("#shopCode").val();
		var ShozokuClassList = $("#ddlShozoku").val();
		ShozokuClassList = ShozokuClassList.substring(0,2);
		var bunruiCode = $("#bunruiCode").val();
		var salesKb = $("#MasutaKubunClassList").val();;
		if (salesKb != null) {
			salesKb = salesKb.substring(0,1);
		} else {
			salesKb = "";
		}
		
		//Date form to
		var To = $('#validTo').val().replace(/\//g, '');
		var from = $('#validFrom').val().replace(/\//g, '');
		var officeCode ="";
		var checkType = $("#chk1").is(":checked") ? true : false;
		var checkedType =0;
		if (checkType == true) {
			checkedType = 1;
		} else {
			checkedType = 0 ;
		}
		var checkCancelData = $("#chk2").is(":checked") ? true : false;
		
		var defaultMess = $("#COM025-E").val();
		var searchMax = $("#searchMax").val();
		var COM005W = $("#COM005-W").val();
		var searchMax = $("#searchMax").val();
		COM005W = COM005W.replace("、汎","、"+searchMax+"汎");
		$.ajax({
			type : "POST",
			url : rootPath+"/mst/MST01-08D00/getSearchResult",
			data: {
				custCode : custCode,
				officeCode : officeCode,
				checkType : checkedType,
				checkCancelData : checkCancelData,
				itemCode : itemCode,
				shopCode : shopCode,
				ShozokuClassList : ShozokuClassList,
				from : from,
				To : To,
				bunruiCode : bunruiCode ,
				salesKb : salesKb,
			},
			async : false,
			success:function(returnData){
				var Data = returnData.mst0108d01Models;
				var error = returnData.errMessage;
				var custNmR = returnData.custNmR;
				var relationMasterErrorFlag = returnData.relationMasterErrorFlag;
				if (custNmR != null) {
					$("#custNmR").text(custNmR);
				}
				var hinRyaKu = returnData.hinRyaKu;
				var shopNmR = returnData.shopNmR;
				if (hinRyaKu != null) {
					$("#itemName").text(hinRyaKu);
				}
				if (shopNmR != null) {
					$("#shopNm").text(shopNmR);
				}
				
				if (Data == null || Data.length <= 0) {
					$("#tblBody tbody ").html("");
					disableButton("btnCSV");
					errMsg = defaultMess;// エラーメッセージ取得処理（ID:COM025-E）（仮）
					
					if (error != null) {
						$("#txtMess").html("<span>"+error+"</span>");
					} else {
						$("#tblBody tbody").html("<div class='align_center'>"+errMsg+"</div>");
					}
					if (relationMasterErrorFlag == 0) {
						if (flag == 0) {
							//[変数]セッション.システム管理者フラグ = 0（無効）の場合
							//[画面]得意先コードにフォーカスをセットする。
							$("#cstCode").focus().select();
						}else if (flag == 1) {
							$("#ddlShozoku").focus().select();
						}
					} else {
						 if (relationMasterErrorFlag == 1) {
							 //[変数]関連マスタエラー発生フラグ　＝　1　　：　[画面]得意先コードにフォーカスをセットする。
							 $("#cstCode").focus().select();
						 } else if (relationMasterErrorFlag == 2) {
							 //[変数]関連マスタエラー発生フラグ　＝　2　　：　[画面]品目コードにフォーカスをセットする。
							 $("#itemCode").focus().select();
						 } else if (relationMasterErrorFlag == 3) {
							 // [変数]関連マスタエラー発生フラグ　＝　3　　：　[画面]店舗コードにフォーカスをセットする。
							 $("#shopCode").focus().select();
						 }
					}
				} else {
					if (Data.length > searchMax) {
						$("#tblBody tbody ").html("");
						enableButton("btnCSV");
						var countData = searchMax;
						showDataTable(Data,countData);
						
						$("#txtMess").html("<span>"+ COM005W+ "</span>");
						
					} else {
						$("#tblBody tbody ").html("");
						
						var err = $("#errMessage").val();
						enableButton("btnCSV");
						var countData = Data.length;
						showDataTable(Data,countData);
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
	});
	// searchData for initaction
	function searchData() {
		custCode = $("#cstCode").val();
		var officeCode ="";
		var checkType = $("#chk1").is(":checked") ? true : false;
		if (checkType == true) {
			checkType = 1;
		} else {
			checkType = 0;
		}
		itemCode = $("#itemCode").val();
		shopCode = $("#shopCode").val();
		var checkCancelData = $("#chk2").is(":checked") ? true : false;
		var ShozokuClassList = $("#ddlShozoku").val();
		if (ShozokuClassList != TYPE_UNDEFINED){
			ShozokuClassList = ShozokuClassList.substring(0,2);
		} else {
			ShozokuClassList = $("#JigyouShoCode").val();
		}
		var from = $("#validFrom").val();
		var To = $("#validTo").val();;
		var bunruiCode = $("#bunruiCode").val();;
		var salesKb = $("#MasutaKubunClassList").val();
		var searchMax = $("#searchMax").val();
		var defaultMess = $("#COM025-E").val();
		var COM005W = $("#COM005-W").val();
		COM005W = COM005W.replace("、汎","、"+searchMax+"汎");
		$.ajax({
			type: "POST",
			url : rootPath+"/mst/MST01-08D00/getSearchResultInit",
			data: {
				custCode : custCode,
				officeCode : officeCode,
				checkType : checkType,
				checkCancelData : checkCancelData,
				itemCode : itemCode,
				shopCode : shopCode,
				ShozokuClassList : ShozokuClassList,
				from : from,
				To : To,
				bunruiCode : bunruiCode ,
				salesKb : salesKb,
			},
			async: false,
			success:function(returnData) {
				var Data = returnData;
				if (Data == null || Data.length <= 0) {
					itemPriceInfo = 0 ;
					$("#itemPriceInfo").val(itemPriceInfo);
					disableButton("btnCSV");
					errMsg = defaultMess;// エラーメッセージ取得処理（ID:COM025-E）（仮）
					$("#tblBody tbody").html("<div class='align_center'>"+errMsg+"</div>");
				} else {
					if (Data.length > searchMax) {
						var countData = searchMax;
						$("#txtMess").html("<span>"+COM005W+"</span>")
						itemPriceInfo = 1;
						$("#itemPriceInfo").val(itemPriceInfo);
						$("#tblBody tbody ").html("");
						enableButton("btnCSV");
						showDataTable(Data,countData);
					} else {
						countData = Data.length;
						itemPriceInfo = 1;
						$("#itemPriceInfo").val(itemPriceInfo);
						$("#tblBody tbody ").html("");
						enableButton("btnCSV");
						showDataTable(Data,countData);
					}
				}
			}
		});
	}
	$("#btnClear").click(function(){
		var JigyouShoCode = $("#JigyouShoCode").val();
		var ShozokuClassList = $("#ddlShozoku").val();
		ShozokuClassList = ShozokuClassList.substring(0,2);
		$("#ddlShozoku").val(JigyouShoCode);
		if (ShozokuClassList = JigyouShoCode) {
			$("#ddlShozoku").focus().select();
		}
		$("#MasutaKubunClassList").val("");	
		disableImgButton("btn_search_customer_id04");
		disableImgButton("btn_search_customer_id03");
		$("#cstCode").val("");
		$("#custNmR").text("");
		$("#itemCode").val("");
		$("#itemName").text("");
		$("#shopCode").val("");
		$("#shopNm").text("");
		$("#validFrom").val("");
		$("#validTo").val("");
		 var MasutaKubunClassList = $("#MasutaKubunClassList").val();
		/*if (MasutaKubunClassList == "") {
			$("#MasutaKubunClassList").val(MasutaKubunClassList);
		}*/
		$("#bunruiCode").val("");
		$("#chk1").attr("checked",false);
		$("#chk2").attr("checked",false);
		$("#txtMess").html("");
		if (flag == 0) {
			//[変数]セッション.システム管理者フラグ = 0（無効）の場合
			//[画面]得意先コードにフォーカスをセットする。
			$("#cstCode").focus().select();
		}else if (flag == 1) {
			$("#ddlShozoku").focus().select();
		}
		$("input").removeClass("form-error-field");
	});
	
	$("#btnNew").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnNew");
		submitForm(SHINKI_MODE, "");
	});
	
	/**
	 * CSVボタンが押下された場合の処理
	 */
	$("#btnCSV").bind("click",function() {
		
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				$("#txtMess").html("");
				enableButton("btnCSV");
				exportCSV();
			}
		});
	});
	
	function checkExist() {
		/**
		 * [画面]得意先コード　≠　""　の場合、得意先マスタより得意先名称を表示する。
		 */
		custCode = $("#cstCode").val();
		if (custCode.length <= 7) {
			if (custCode != "") {
				custCode = addLeadingChar(custCode,7);
			}
		}
		var ShozokuClassList = $("#ddlShozoku").val();
		if (ShozokuClassList != TYPE_UNDEFINED){
			ShozokuClassList = ShozokuClassList.substring(0,2);
		} else {
			ShozokuClassList = $("#JigyouShoCode").val();
		}
		//Date form to
		var To = $('#validTo').val().replace(/\//g, '');
		var from = $('#validFrom').val().replace(/\//g, '');
		var bunruiCode = $("#bunruiCode").val();
		var officeCode = "";
		
		shopCode = $("#shopCode").val();
		var checkType = 1 ;
		var checkCancelData = $("#chk2").is(":checked") ? true : false;
		itemCode = $("#itemCode").val();
		// 入力文字数が６文字に満たない場合、0編集を行う。
		if (itemCode.length <= 6) {
			if (itemCode != "") {
				itemCode = addLeadingChar(itemCode,6);
				$("#itemCode").val(itemCode);
			}
		}
		var salesKb = $("#MasutaKubunClassList").val();;
		if (salesKb != null) {
			salesKb = salesKb.substring(0,1);
		} else {
			salesKb = "";
		}
		$("#cstCode").val(custCode);
		$.ajax({
			type : "POST",
			url : rootPath+"/mst/MST01-08D00/checkExist",
			data: {
				custCode : custCode,
				officeCode : officeCode,
				checkType : checkType,
				checkCancelData : checkCancelData,
				itemCode : itemCode,
				shopCode : shopCode,
				ShozokuClassList : ShozokuClassList,
				from : from,
				To : To,
				bunruiCode : bunruiCode ,
				salesKb : salesKb,
			},
			success:function(returnData){
				var data =  returnData;
				if (data.errMessage != null ) {
					$("#txtMess").html("<span>"+data.errMessage+"</span>");
					enableButton("btnCSV");
					exportCSV();
					return true;
				}  else {
					$("#txtMess").html("");
					enableButton("btnCSV");
					exportCSV();
					return true;
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
	/**
	 * サブメニューのSubmit処理
	 * 
	 * @param　strMode　モード値
	 */
	function submitForm(strMode) {	
		if (strMode == SHINKI_MODE) { // 新規モード
			$("#viewMode").val(SHINKI_MODE);
		} else if (strMode == SHOUKAI_MODE) { // 照会モード
			$("#viewMode").val(SHOUKAI_MODE);
			$("#strSelectedCustCode").val(strSelectedCustCode);
			$("#strSelectedItemCode").val(strSelectedItemCode);
			$("#strSelectedShopCode").val(strSelectedShopCode);
			$("#strSelectedSaleType").val(strSelectedSaleType);
			$("#strSelectedEndTime").val(strSelectedEndTime);
		} else if (strMode == TEISEI_MODE) { // 訂正モード
			$("#viewMode").val(TEISEI_MODE);
			$("#strSelectedCustCode").val(strSelectedCustCode);
			$("#strSelectedItemCode").val(strSelectedItemCode);
			$("#strSelectedShopCode").val(strSelectedShopCode);
			$("#strSelectedSaleType").val(strSelectedSaleType);
			$("#strSelectedEndTime").val(strSelectedEndTime);
		} else if (strMode == TORIKESI_MODE) { // 取消モード
			$("#viewMode").val(TORIKESI_MODE);
			$("#strSelectedCustCode").val(strSelectedCustCode);
			$("#strSelectedItemCode").val(strSelectedItemCode);
			$("#strSelectedShopCode").val(strSelectedShopCode);
			$("#strSelectedSaleType").val(strSelectedSaleType);
			$("#strSelectedEndTime").val(strSelectedEndTime);
		} else if (strMode == COPY_MODE) {
			$("#viewMode").val(COPY_MODE);
			$("#strSelectedCustCode").val(strSelectedCustCode);
			$("#strSelectedItemCode").val(strSelectedItemCode);
			$("#strSelectedShopCode").val(strSelectedShopCode);
			$("#strSelectedSaleType").val(strSelectedSaleType);
			$("#strSelectedEndTime").val(strSelectedEndTime);
		}
		$("#formMst0108d01").submit();
	}
	
	/**
	 * 一覧のNo列をマウスオーバーされた時の処理。対象行のNoを「▼」表示に変更する
	 */
	$(document).on("mouseover", ".contextMenu", function() {
		// change text to "▼" when user is hovering on a No column
		$(this).text("▼");
		// if menu is showing, hide menu when user is hovering on other No columns
		if (isTableMenuShowing) {
			// only hide menu if user is hovering on other No columns, not the column that is showing menu
			if ($(this).attr("id") != currentShowingColId) {
				// hide menu
				$("#tableMenu").css("display", "none");
				// switch flag value
				isTableMenuShowing = false;
				// change the text back from "▼" to number
				$("#" + currentShowingColId).text(currentShowingColId);
			}
		}
		// is menu is not showing, change the text back from "▼" to number
		if ($(this).attr("id") != currentShowingColId) {
			$("#" + currentShowingColId).text(currentShowingColId);
			currentShowingColId = $(this).attr("id");
		}
	});
	$(document).on("click", ".contextMenu", function(e) { // click event on No column
		// get current coordinates of mouse
		var x = e.pageX;
		var y = e.pageY;
		// show menu at current mouse's coordinates
		$("#tableMenu").css("position", "absolute").css("left", x + "px").css("top", y + "px").css("display", "block");
		// switch flag value
		isTableMenuShowing = true;
		// 編集メニューの項目の表示をチェックする
		var sysAdminFlag = $("#systemAdministratorFlag").val();
		var loginJigyouShoCode = $("#JigyouShoCode").val();
		var currentJigyouShoCode = $(this).parent().find("td").eq(6).text();
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN || currentJigyouShoCode == loginJigyouShoCode) {
			// システム管理者フラグ ＝ '1'（システム管理者） or 得意先一覧[i].事業所 ＝ ログイン事業所コード　の場合
			$("#tableMenu").find("a").css("display", "block");
		} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER && currentJigyouShoCode != loginJigyouShoCode) {
			// システム管理者フラグ ＝ '0'（一般ユーザ） and 得意先一覧[i].事業所 ≠ ログイン事業所コード　の場合
			$("#tableMenu").find("a").eq(0).css("display", "block");
			$("#tableMenu").find("a").eq(1).css("display", "none");
			$("#tableMenu").find("a").eq(2).css("display", "none");
		} else {
			$("#tableMenu").find("a").css("display", "block");
		}
		var parentRow = $(this).parent();
		strSelectedCustCode = $(parentRow).find("td").eq(1).text();
		strSelectedItemCode = $(parentRow).find("td").eq(3).text();
		strSelectedShopCode = $(parentRow).find("td").eq(5).text();
		strSelectedSaleType = $(parentRow).find("td").eq(7).text();
		strSelectedSaleType = strSelectedSaleType.substring(0,1);
		strSelectedEndTime = $(parentRow).find("td").eq(8).text();
		strSelectedEndTime = strSelectedEndTime.substring(13,23);
		strSelectedEndTime = strSelectedEndTime.replace(/\//g, '');
	});
	/**
	 * 一覧の他の列をマウスオーバーされた時の処理
	 */
	$(document).on("mouseover", "td", function() {
		if (!$(this).hasClass("contextMenu")) {
			// if menu is not showing, change the text back from "▼" to number
			if (!isTableMenuShowing) {
				$("#" + currentShowingColId).text(currentShowingColId);
			}
		}
	});
	/**
	 * 編集メニューの項目をマウスオーバーされた時の処理
	 */
	$(".sub-menu a").bind("mouseover", function() {
		$(this).css("background-color", "#003366").css("color", "#FFFFFF");
	}).bind("mouseout", function() {
		$(this).css("background-color", "#FFFFFF").css("color", "#000000");
	});
	/**
	 * 編集メニューの項目をクリックされた時の処理
	 */
	$(".sub-menu a").bind("click", function() {
		if ($(this).attr("id") == "copy") {
			//コピー
			submitForm(COPY_MODE);
		} else if ($(this).attr("id") == "view") {
			// 照会
			submitForm(SHOUKAI_MODE);
		} else if ($(this).attr("id") == "modify") {
			// 訂正
			submitForm(TEISEI_MODE);
		} else if ($(this).attr("id") == "cancel") {
			// 取消
			submitForm(TORIKESI_MODE);
		}
	});
	/**
	 * 編集メニュー以外をクリックされた時の処理
	 */
	$("body").bind("click",function(e) {
		var container = $(".contextMenu");
		if (!container.is(e.target) && container.has(e.target).length === 0
				&& $("#tableMenu").is(":visible")) {
			$("#tableMenu").css("display", "none");
			$("#" + currentShowingColId).find("span").text(currentShowingColId);
			isTableMenuShowing = false;
		}
	});
	/**
	 * CSVボタンを押す処理
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function exportCSV() {
		$.ajax({
			type : "POST",
			url : "exportCsv",
			async : false,
			data : $("form").serialize(),
			success : function(returnData) {
				var data = JSON.parse(returnData.jisonData);
				var custNmR = returnData.custNmR;
				if (custNmR != null) {
					$("#custNmR").text(custNmR);
				} else {
					$("#cstCode").focus().select();
				}
				
				var hinRyaKu = returnData.hinRyaKu;
				var shopNmR = returnData.shopNmR;
				if (hinRyaKu != null) {
					$("#itemName").text(hinRyaKu);
				} else {
					$("#itemCode").focus().select();
				}
				if (shopNmR != null) {
					$("#shopNm").text(shopNmR);
				} else {
					$("#shopCode").focus().select();
				}
				var errMess = $("#errorMessages").val();
				if (returnData.errorMessages != "") {
					$("#txtMess").html("<span>"+returnData.errorMessages+"</span>");
				} else if (data.message != null) {
					$("#txtMess").html("<span>"+data.message+"</span>");
				} else {
					var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
					var path = rootPath + data.strPathFile;
					window.open(path);
					$("#txtMess").html("");
				}
			},
			error : function(e) {
				
			},
			complete : function(jqXHR, textStatus) {
				enableButton("btnCSV");
				isClickButton = true;
			}
		});
	}
});
// showDataTable
function showDataTable(Data,countData) {
	var htmlRow = "";
	var ShowDataCnt = Data.length;
	if (ShowDataCnt <= 0) {
		return false;
	}
	for (var i=0; i < countData; i++) {
		if (Data[i].stsCode == 9) {
			htmlRow +="<tr class='tbl_del'>";
		} else {
			htmlRow +="<tr>";
		}
		
		htmlRow +="<td id='" + (i + 1) + "' class='align_center contextMenu'>"+Data[i].number+"</td>";
		if (Data[i].cstCode == null  ) {
			Data[i].cstCode ="";
		}
		htmlRow	+="<td>"+Data[i].cstCode+"</td>";
		if (Data[i].custNmR == null  ) {
			Data[i].custNmR ="";
		}
		htmlRow +="<td>"+Data[i].custNmR+"</td>";
		if (Data[i].itemCode == null  ) {
			Data[i].itemCode ="";
		}
		htmlRow +="<td class='align_right'>"+Data[i].itemCode+"</td>";
		if (Data[i].hinRyaKu == null  ) {
			Data[i].hinRyaKu ="";
		}
		htmlRow +="<td>"+Data[i].hinRyaKu+"</td>";
		
		
		if (Data[i].shopNmR == null  ) {
			Data[i].shopNmR ="";
		}
		if (Data[i].shopCode == null  ) {
			Data[i].shopCode ="";
			Data[i].shopNmR ="";
		}
		htmlRow +="<td class='align_right'>"+Data[i].shopCode+"</td>";
		htmlRow +="<td>"+Data[i].shopNmR+"</td>";
		if (Data[i].salesKb == null  ) {
			Data[i].salesKb ="";
		}
		if (Data[i].salesNm == null  ) {
			Data[i].salesNm ="";
		}
		htmlRow +="<td>"+Data[i].salesKb+":"+Data[i].salesNm+"</td>";
		if (Data[i].validFrom == null  ) {
			Data[i].validFrom ="";
		}
		if (Data[i].validTo == null  ) {
			Data[i].validTo ="";
		}
		var validFrom = Data[i].validFrom;
		var validTo = Data[i].validTo;
		if (validFrom != "") {
			var yearStart = validFrom.substring(0,4);
			var monthStart = validFrom.substring(4,6);
			var dayStart = validFrom.substring(6,8);
			var validFrom = yearStart + "/" + monthStart + "/" + dayStart;
		}
		if (validTo != "") {
			var yearStart = validTo.substring(0,4);
			var monthStart = validTo.substring(4,6);
			var dayStart = validTo.substring(6,8);
			var validTo = yearStart + "/" + monthStart + "/" + dayStart;
		}
		htmlRow +="<td>"+validFrom+" ～ "+ validTo+"</td>";
		if (Data[i].custDeliTanka == null  ) {
			Data[i].custDeliTanka ="";
		}
		
		htmlRow +="<td class='align_right'>"+Data[i].custDeliTanka+"</td>";
		if (Data[i].custSellTanka == null  ) {
			Data[i].custSellTanka ="";
		}
		if (Data[i].custSellTanka == null  ) {
			Data[i].custSellTanka ="";
		}
		htmlRow +="<td class='align_right'>"+Data[i].custSellTanka+"</td>";
		if (Data[i].custBildTanka == null  ) {
			Data[i].custBildTanka ="";
		}
		
		htmlRow +="<td class='align_right'>"+Data[i].custBildTanka+"</td>";
		if (Data[i].bunruiCode == null  ) {
			Data[i].bunruiCode ="";
		}
		htmlRow +="<td class='align_right'>"+Data[i].bunruiCode+"</td>";
		htmlRow +="</tr>";
	}
	$("#tblBody tbody").html(htmlRow);
	// table scroll
	var tblBodyHeight = $("#tblBody").height();
	var divBodyHeight = $("#divBody").height();
	if (tblBodyHeight > divBodyHeight) {
		$("#divBody").css("overflow-y", "scroll");
		$("#divHead").width($("#divBody").width() - 17);
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width() - 17);
		});
	} else {
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width());
		});
		$("#divHead").width($("#divBody").width());
		$("#divBody").css("overflow-y", "hidden");
	}
}
//getCom0102d02
function getCom0102d02(data) {
	var custNm = "";
	for (var i = 0; i < data.length; i++) {
	$("#cstCode").val(data[i].custCode);
	$("#custNmR").text(data[i].custNmR);
	custNm = data[i].custNmR;
	}
	enableImgButton("btn_search_customer_id04");
	enableImgButton("btn_search_customer_id03");
	$("#itemCode").focus().select();
	// 検索画面ダイアログを閉じる
	$("#dialog").empty();
	HideDialog();
}
/**
 * getCom0102d04.
 * @param ItemCodeWk
 * @param ItemNameWk
 */
function getCom0102d04(ItemCodeWk,ItemNameWk) {
	$("#itemCode").val(ItemCodeWk);
	$("#itemName").text(ItemNameWk);
	$("#shopCode").focus().select();
	reTurnCheckTab();
}
/**
 * getCom0102d03.
 * @param data
 */
function getCom0102d03(data) {
	 var showDataCnt = data.length;
	 for (var i = 0; i < showDataCnt; i++) {
	  $("#shopCode").val(data[i].shopCode);
	  $("#shopNm").text(data[i].shopNm);
	 }
	 $("#MasutaKubunClassList").focus().select();
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

