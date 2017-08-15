/**
 * ファイル名: get0105d00.js
 * 
 * <p>
 * 作成者:HiepTruong
 * 作成日:2015/10/21
 * 
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/21 1.00 ABV)HiepTruong
 * -------------------------------------------------------------------------
 * 
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
var DELIMITER = "##";
var seikyusakiCode = "";
var seikyusakiCodo = "";
var tantoshaCodo = "";
jQuery(document).ready(function($) {
	var isClickButton = false;
	// common use
	var checkError = function(returnData){
		var data = JSON.parse(returnData);
		if(data.hasOwnProperty('haitaDate')){
			$("#haitaDate").html(data['haitaDate']);
		}
		if(data.hasOwnProperty('haitaTime')){
			$("#haitaTime").html(data['haitaTime']);
		}
		if(data.hasOwnProperty('Error')){
			$("#txtMess").html(data['Error']);
			$("#txtMess").removeClass("display_none");
			removeInfoClass();
		} else if (data.hasOwnProperty('Info')) {
			$("#txtMess").html(data['Info']);
			$("#txtMess").removeClass("display_none");
			addInfoClass();
		} else{
			$("#txtMess").addClass("display_none");
		}
	}
	
	// Dialogのadd_clickイベントの作成
	loadDialogCom0102d02();	
	loadDialogCom0102d05();

	
	function disableAll() {
		$('#txtAccountMonth').prop("disabled",true);
		$('#txtSaleUserCd').prop("disabled",true);
		$('#txtCustCd').prop("disabled",true);
		$('#ddlJigyouSho').prop("disabled",true);
		$('#urikakeMonth').prop("disabled",true);
		disableImgButton("btn_search_customer");
		disableImgButton("btn_search_customer_id");
		
	}
	
	function enableAll() {
		$('#txtAccountMonth').prop("disabled",false);
		$('#txtSaleUserCd').prop("disabled",false);
		$('#txtCustCd').prop("disabled",false);
		$('#ddlJigyouSho').prop("disabled",false);
		$('#urikakeMonth').prop("disabled",false);
		enableImgButton("btn_search_customer");
		enableImgButton("btn_search_customer_id");
	}
	var init = function(){
		var isDisableAll = $("#isDisableAll").val().trim();
		if (isDisableAll == 'true') {
			disableAll();
		} 
		else {
			enableAll();
		}
	}
	init();
	var eventChange = function(){
		$("#ddlJigyouSho").change(function(){
			var officeCode = $(this).val();
			console.log($("#haitaDate").html());
			console.log($("#haitaTime").html());
			$.ajax({
				type : "POST",
				url : "get0105D00changeOfficeCode",
				async : false,
				data : {
					officeCode : officeCode,
					haitaDate: $("#haitaDate").html(),
					haitaTime: $("#haitaTime").html()
				},
				success : function(returnData) {
					var data = JSON.parse(returnData);
					if(data.hasOwnProperty('setFocus')){
						$(data['setFocus']).focus();
					}
				},
				error : function(e) {
				
				},
				complete : function(jqXHR, textStatus) {
				}
			});
		});
	}
	eventChange();
	$("#btn_search_customer").on("click", function() {
		if (isBtnImgEnabled("btn_search_customer")){
			isClickButton = true;
			var strJigyo = $("#ddlJigyouSho").val();
			var custCode = $("#txtSaleUserCd").val();
			showCom0102d05(custCode,"",strJigyo);
		}

		  
		 });
	/**
	 * エンターキー押下時の遷移
	 */
	$("input[type=text]").bind("keydown", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this, ".data_conditions_area");
		}
	});
	/**
	 * ＣＳＶボタン状態の表示
	 * 
	 * @param data テーブルのデータ
	 * @return
	 * @exception
	 */
	function showButtonStatus(bError) {
		if (bError) {
			// エラーがある場合
			disableButton("btnCSV");
		} else {
			enableButton("btnCSV");			
		}
	}
	//得意先ダイアログのイベント
	$(document).on("click", "#btn_search_customer_id", function(e) {
		if (isBtnImgEnabled("btn_search_customer_id")){
		     //[変数]事業所コード
			isClickButton = true;
		     var strJigyo = $("#ddlJigyouSho").val();
		     if(strJigyo != undefined){
		            if (strJigyo == "" || strJigyo == null) {
		                          strJigyo = "";
		             }
		       } else {
		       strJigyo = "";
		       }
		       var custCode = $("#txtCustCd").val();//[画面]得意先コード
		        var shopKb = ""; // Null
		         var searchKb = "2";// '1'（得意先）
		          // 検索子画面呼び出し用関数を呼び出す
		          showCom0102d02(strJigyo, custCode, shopKb, searchKb);
		}
	 
	});

	// CSVボタン
	
	$("#btnCSV").bind("click", function() {
		isClickButton = true;
		disableButton("btnCSV");
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				
				exportCSV();
			}
			else{
				enableButton("btnCSV");
			}
		});
	});
	/**
	 * ControlIDListにエラークラスを追加する。
	 * 
	 * @param idArray:ControlIDList
	 */
	function errorFocus(idArray) {
		for (var i = 0 ; i < idArray.length ; i++) {
			$("#" + idArray[i]).addClass("form-error-field");
			$("#" + idArray[i]).focus();
		}
	}
	/**
	 * エラー時項目はエラー背景を設定する。
	 *
	 * @exception
	 */	
	function setBGError(errorMess) {
		if (errorMess != null && errorMess != "") {
			var lstError = errorMess.split(DELIMITER);
			errorFocus(lstError);
		}
	}
	/**
	 * check focusout.
	 */
	$(document).on("focusout", "#urikakeMonth", function() {
		setTimeout(function(){
			if(!isClickButton) {
				removeInfoClass();
				var errorID = chkItem($('#urikakeMonth').val(), true, TYP_DATE_FORMAT, false, DATE_FORMAT_YM);
				var strBeforeCourseCode = $("#txtCourseCode").val();
				if (errorID != null){
					$("#txtMess").html($("#" + errorID).val().replace("{1}", "売掛月度"));
					$("#urikakeMonth").addClass("form-error-field");
				}else {
					$("#urikakeMonth").removeClass("form-error-field");
					$("#txtMess").html("");
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	/**
	 * check focusin.
	 */
	$(document).on("focusin", "#txtCustCd", function() {
		seikyusakiCodo = $("#txtCustCd").val();
		if (strBefore != seikyusakiCodo) {
			 $("#seikyusakiname").html("");
		}
	});
	/**
	 * check focusin.
	 */
	$(document).on("focusin", "#txtSaleUserCd", function() {
		tantoshaCodo = $("#txtSaleUserCd").val();
		
	
	});
	/**
	 * check focusout.
	 */
	$(document).on("focusout", "#txtCustCd", function() {
		var strBefore = $("#txtCustCd").val();
		if (strBefore != seikyusakiCodo) {
			 $("#seikyusakiname").html("");
		}
	});
	/**
	 * check focusout.
	 */
	$(document).on("focusout", "#txtSaleUserCd", function() {
		var strBefore = $("#txtSaleUserCd").val();
		if (strBefore != tantoshaCodo) {
			$("#txtSaleUserNm").html("");
		}
	
	});
	
	/**
	 * CSVボタンが押下された場合の処理
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	function exportCSV() {
		$('input').removeClass('form-error-field');
		$.ajax({
			type : "POST",
			url : "exportCsv",
			async : false,
			data : $("form").serialize(),
			success : function(returnData) {
				
				var data = JSON.parse(returnData);
				if(data.hasOwnProperty('setFocus')){
					$(data['setFocus']).focus();
				}
				if (data.message != null) {
					$("#txtMess").html(data.message);
					setBGError(data.type);						
					showButtonStatus(true);
				} else {
					var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
					var data = JSON.parse(returnData);
					var path = rootPath + data.strPathFile;
					window.open(path);
					$("#txtMess").html("");
					showButtonStatus(false);
					$("#seikyu").val(data.seikyuName);
					$("#seikyusakiname").html($("#seikyu").val().trim());
					$("#tantoshaName").val(data.tantoshaName);
					$("#txtSaleUserNm").html($("#tantoshaName").val().trim());
				}
			},
			error : function(e) {
				$("#txtMess").val(EXCEPTION_ERROR);
			},
			complete : function(jqXHR, textStatus) {
				//ボタンの2重押下防止 可能
				enableButton("btnCSV");
			}
		});
	}
	// 5 - 終了処理・例外処理
	var initFocus = function(){
		// システム管理者フラグを取得する
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
	
		// システム管理者フラグ ＝ '1'（システム管理者）の場合
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			$("#ddlJigyouSho").focus();
		}
		// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
		else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			$("#txtCustCd").focus();
		}
	}
	// initialize focus
	initFocus();
});
/**
 * 検索画面からデータを取得する。
 * 
 * @param
 * @return
 * @exception
 */
function getCom0102d05(data) {
	$("#txtSaleUserCd").val(data[0].userCode);	
	$("#tantoshaName").val(data[0].userNm);
	tantoshaCodo = data[0].userNm ;
	$("#txtSaleUserNm").html($("#tantoshaName").val().trim());
	$("#btnCSV").focus();
	reTurnCheckTab();
}
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
		if (data[0].custCode != seikyusakiCode){
			$("#txtCustCd").val(data[0].custCode);
			$("#seikyu").val(data[0].custNmR);
			seikyusakiCodo = data[0].custNmR ;
			 $("#seikyusakiname").html( $("#seikyu").val().trim());
			$("#txtSaleUserCd").focus();
		}
	}
	reTurnCheckTab();
}
