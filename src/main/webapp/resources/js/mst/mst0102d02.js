/**
 * ファイル名:mst0102d02.js
 * 概要:得意先マスタ登録画面
 * 
 * 作成者:ABV）コアー
 * 作成日:2015/09/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/24 1.00                  ABV）コアー        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

// Controlエラーの受信デリミター
var DELIMITER = "##";
// 営業担当者コード／事務担当者コードのダイアログ表示フラグ
var userCodeDialogDisplayFlg = ""; // 1: 営業担当者コード, 2: 事務担当者コード
// 取扱事業所テーブルの検索ダイアログ処理の選択された行インデックス
var userCodeDialogSelectedIndex = 0;
// 請求先コードの初期化値の変数
var txtBillingCodeValue = "";

jQuery(document).ready(function($) {
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0102D02";
	/** 変数定義 */
	var originalData = [];
	var currentShowingColId = "";
	var isTableMenuShowing = false;
	var txtChainCodeValue = "";
	var txtChainEdaValue = "";
	// 一度に複数のボタンクリックを防止する
	var isClickButton = false;

	// Dialogのadd_clickイベントの作成
	loadDialogCom0102d01();
	loadDialogCom0102d02();
	loadDialogCom0102d05();

	// コントロールの値を初期化する
	initControlDataBasedOnMode();
	// オリジナルのデータを作成する
	if (!checkStorageExist(SCREEN_NAME)) {
		originalData = saveFormData();
		saveToStorage(SCREEN_NAME, originalData);
	}

	// エンターキー押下時の遷移処理を作成する
	bindEnterEvent();

	/**
	 * 得意先コードからフォーカスが外れた場合の処理。入力値の不足桁をゼロ編集する（７桁）
	 */
	$("#txtCustomerCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 得意先コード.桁数 ≠ NULL の場合、得意先コードの不足桁をゼロ編集する
				if ($("#txtCustomerCode").val() != null && $("#txtCustomerCode").val().trim() != "") {
					var currentCustomerCode = $("#txtCustomerCode").val().trim();
					$("#txtCustomerCode").val(addLeadingChar(currentCustomerCode, 7));
				}
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE) { // 新規モード
					var errorID = chkItem($("#txtCustomerCode").val(), true, TYPE_NUM_ALPHA, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先コード"));
						$("#txtCustomerCode").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCustomerCode").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 得意先フラグのチェックに変更があった場合の処理。請求先情報入力エリアの入力を制御する
	 */
	$("#chkCustomer").bind("change", function() {
		checkCustomerBillingChangedProcess();
	});

	/**
	 * 請求先フラグのチェックに変更があった場合の処理。請求先情報入力エリアの入力を制御する
	 */
	$("#chkBilling").bind("change", function() {
		checkCustomerBillingChangedProcess();
	});

	/**
	 * 請求先コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$("#txtBillingCode").bind("focus", function() {
		// 請求書コード ≠ NULL の場合、請求書コードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			txtBillingCodeValue = $(this).val().trim();
		}
	});

	/**
	 * 請求先コードからフォーカスが外れた場合の処理。入力値の不足桁をゼロ編集し（7桁）、請求先情報を取得する
	 */
	$("#txtBillingCode").bind("focusout", function() {
		setTimeout(function() {
			if (!isClickButton) {
				// 請求先コード ≠ [変数]請求先コード(初期値) の場合
				if ($("#txtBillingCode").val().trim() != txtBillingCodeValue) {
					// 請求先名称をクリア
					$("#txtBillingSearchName").text("");
					$("#txtBillingSearchNameHidden").val("");
				}
				// 請求先情報を取得する
				billingCodeChangedProcess();
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先の検索ボタンをクリックされた際の処理。請求先検索画面を呼び出す
	 */
	$("#btnSearchBillingCode").bind("click", function() {
		var currentImgPath = $(this).attr("src");
		if (currentImgPath.indexOf("search_dis") < 0) {
			// システム管理者フラグを取得する
			var sysAdminFlag = $("#sysAdminFlag").val().trim();
			// 事業所コード
			var jigyoushoCode = "";
			// システム管理者フラグ ＝ '1'（システム管理者）の場合
			if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				jigyoushoCode = "";
			}
			// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
			else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
				jigyoushoCode = loginJigyouShoCode;
			}
			// 得意先コード
			var custCode = $("#txtBillingCode").val().trim();
			// 店舗区分
			var shopCode = "";
			// 検索対象
			var searchKb = BILL_SEARCH_TARGET_BILLING; // 請求先
			// 検索子画面呼び出し用関数を呼び出す
	        showCom0102d02(jigyoushoCode, custCode, shopCode, searchKb);
		}
	});

	/**
	 * チェーンコードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$("#txtChainCode").bind("focus", function() {
		// チェーンコード ≠ NULL の場合、チェーンコードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			txtChainCodeValue = $(this).val().trim();
		}
	});

	/**
	 * チェーンコードからフォーカスが外れた場合の処理。自項目の初期化を行う
	 */
	$("#txtChainCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// チェーンコード ≠ [変数]チェーンコード(初期値) or チェーン枝番 ≠ [変数]チェーン枝番(初期値) の場合
				if ($("#txtChainCode").val().trim() != txtChainCodeValue || $("#txtChainEda").val().trim() != txtChainEdaValue) {
					// チェーン名称 = null
					$("#txtChainName").html("");
				}

				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtChainCode").val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "チェーンコード"));
						$("#txtChainCode").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtChainCode").removeClass("form-error-field");
					}
				}

				// 納品センター選択肢再設定
				// チェーンコード ≠ [変数]チェーンコード(初期値) の場合
				if ($("#txtChainCode").val() != txtChainCodeValue) {
					chainCodeManagerJigyoCodeChangedProcess();
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * チェーン枝番にフォーカスが当たった場合の処理。入力値を退避する
	 */
	$("#txtChainEda").bind("focus", function() {
		// チェーン枝番 ≠ NULL の場合、チェーン枝番を退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			txtChainEdaValue = $(this).val().trim();
		}
	});

	/**
	 * チェーン枝番からフォーカスが外れた場合の処理。自項目の初期化を行う
	 */
	$("#txtChainEda").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// チェーンコード ≠ [変数]チェーンコード(初期値) or チェーン枝番 ≠ [変数]チェーン枝番(初期値) の場合
				if ($("#txtChainCode").val().trim() != txtChainCodeValue || $("#txtChainEda").val().trim() != txtChainEdaValue) {
					// チェーン名称 = null
					$("#txtChainName").html("");
				}

				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtChainEda").val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "チェーンコード"));
						$("#txtChainEda").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtChainEda").removeClass("form-error-field");
					}
				}

				// 納品センター選択肢再設定
				// チェーン枝番 ≠ [変数]チェーン枝番(初期値) の場合
				if ($("#txtChainEda").val() != txtChainEdaValue) {
					chainCodeManagerJigyoCodeChangedProcess();
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * チェーンコードの検索ボタンをクリックされた際の処理。チェーンコード検索画面を呼び出す
	 */
	$("#btnSearchChainCode").bind("click", function() {
		var currentImgPath = $(this).attr("src");
		if (currentImgPath.indexOf("search_dis") < 0) {
			// 変数値の情報を取得する
			// チェーンコード
			var chainCode = $("#txtChainCode").val().trim();
			chainCode = replaceText(chainCode);
			// チェーン枝番
			var chainBranch = $("#txtChainEda").val().trim();
			chainBranch = replaceText(chainBranch);
			// ダイアログの表示
			showCom0102d01(chainCode, chainBranch);
		}
	});

	/**
	 * 得意先名称からフォーカスが外れた場合の処理
	 */
	$("#txtCustomerName").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtCustomerName").val(), true, TYPE_EM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先名称"));
						$("#txtCustomerName").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCustomerName").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 得意先名称カナからフォーカスが外れた場合の処理
	 */
	$("#txtCustomerNameKana").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtCustomerNameKana").val(), false, TYPE_EM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先名称カナ"));
						$("#txtCustomerNameKana").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCustomerNameKana").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 得意先略称からフォーカスが外れた場合の処理
	 */
	$("#txtCustomerNameR").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtCustomerNameR").val(), true, TYPE_EM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先略称"));
						$("#txtCustomerNameR").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCustomerNameR").removeClass("form-error-field");
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
		// 郵便番号を入力時、本当の郵便番号をtxtPostalCodeResultに保存する
		var postalCode1 = $("#txtPostalCode1").val().trim();
		var postalCode2 = $("#txtPostalCode2").val().trim();
		$("#txtPostalCodeResult").val(postalCode1 + postalCode2);

		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
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
	 * 住所１からフォーカスが外れた場合の処理
	 */
	$("#txtAddress1").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtAddress1").val(), true, TYPE_EM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "住所１"));
						$("#txtAddress1").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtAddress1").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 住所２からフォーカスが外れた場合の処理
	 */
	$("#txtAddress2").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtAddress2").val(), false, TYPE_EM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "住所２"));
						$("#txtAddress2").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtAddress2").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 電話番号からフォーカスが外れた場合の処理
	 */
	$("#txtTel1, #txtTel2, #txtTel3").bind("focusout", function() {
		// 電話番号を入力時、本当の電話番号をtxtTelResultに保存する
		var tel1 = $("#txtTel1").val().trim();
		var tel2 = $("#txtTel2").val().trim();
		var tel3 = $("#txtTel3").val().trim();
		$("#txtTelResult").val(tel1 + tel2 + tel3);

		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtTelResult").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "電話番号"));
						$("#txtTel1").addClass("form-error-field");
						$("#txtTel2").addClass("form-error-field");
						$("#txtTel3").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtTel1").removeClass("form-error-field");
						$("#txtTel2").removeClass("form-error-field");
						$("#txtTel3").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * FAX番号からフォーカスが外れた場合の処理
	 */
	$("#txtFax1, #txtFax2, #txtFax3").bind("focusout", function() {
		// FAX番号を入力時、本当のFAX番号をtxtFaxResultに保存する
		var fax1 = $("#txtFax1").val().trim();
		var fax2 = $("#txtFax2").val().trim();
		var fax3 = $("#txtFax3").val().trim();
		$("#txtFaxResult").val(fax1 + fax2 + fax3);

		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtFaxResult").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "FAX番号"));
						$("#txtFax1").addClass("form-error-field");
						$("#txtFax2").addClass("form-error-field");
						$("#txtFax3").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtFax1").removeClass("form-error-field");
						$("#txtFax2").removeClass("form-error-field");
						$("#txtFax3").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 得意先担当者からフォーカスが外れた場合の処理
	 */
	$("#txtCustomerTantousha").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtCustomerTantousha").val(), false, TYPE_EM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "得意先担当者"));
						$("#txtCustomerTantousha").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCustomerTantousha").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * メールアドレスからフォーカスが外れた場合の処理
	 */
	$("#txtMailAddress").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtMailAddress").val(), false, TYPE_NUM_ALPHA_SIGN, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "メールアドレス"));
						$("#txtMailAddress").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtMailAddress").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 行追加ボタンがクリックされた時の処理。取扱事業所一覧に新規行を追加する
	 */
	$("#btnAddRow").bind("click", function() {
		// 最後の行のインデックスを取得する
		var lastRowIndex = parseInt($("#tbl_toriatsukai tr:last").find("td").eq(0).text().trim()) - 1;
		// 最後の行のＨＴＭＬ文字を取得する
		var lastRowString = "<tr>" + $("#tbl_toriatsukai tr:last").html() + "</tr>";
		// 新しい行のＨＴＭＬ文字を作成する
		var replacedString1 = "\\[" + lastRowIndex + "\\]";
		var replacedString2 = "arrListToriatsukaiJigyouSho" + lastRowIndex;
		var regex1 = new RegExp(replacedString1, "g");
		var regex2 = new RegExp(replacedString2, "g");
		lastRowString = lastRowString.replace(regex1, "[" + (lastRowIndex + 1) + "]")
							.replace(regex2, "arrListToriatsukaiJigyouSho" + (lastRowIndex + 1))
							.replace(/form-error-field/g, "");
		// テーブルの最後尾に追加する
		$("#tbl_toriatsukai").append(lastRowString);
		// 必要な情報を編集する
		$("#tbl_toriatsukai tr:last").find("td").eq(0).text(lastRowIndex + 2);
		$("#tbl_toriatsukai tr:last").find("td").eq(0).attr("id", lastRowIndex + 2);
		$("#tbl_toriatsukai tr:last").find("input").val("");
		$("#tbl_toriatsukai tr:last").find("span").text("");
		// 取扱事業所コードへフォーカスをセット
		$("#tbl_toriatsukai tr:last").find("td").eq(1).find("select").focus();
		// エンターキー押下時の遷移処理を作成する
		bindEnterEvent();
	});

	/**
	 * 一覧のNo列をマウスオーバーされた時の処理。対象行のNoを「▼」表示に変更する
	 */
	$(document).on("mouseover", ".contextMenu", function() {
		// header行を含むので、１行から２行になる
		if ($("#tbl_toriatsukai tbody").find("tr").length > 2) {
			// change text to "▼" when user is hovering on a No column
			var currentTd = $(this);
			setTimeout(function() {
				currentTd.text("▼");
			}, 1);
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
		}
	});
	$(document).on("click", ".contextMenu", function(e) { // click event on No column
		// header行を含むので、１行から２行になる
		if ($("#tbl_toriatsukai tbody").find("tr").length > 2) {
			// get current coordinates of mouse
			var x = e.pageX;
			var y = e.pageY;
			// show menu at current mouse's coordinates
			$("#tableMenu").css("position", "absolute").css("left", x + "px").css("top", y + "px").css("display", "block");
			// switch flag value
			isTableMenuShowing = true;
			$("#tableMenu").find("a").css("display", "block");
		}
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
		$(this).css("background-color", "#000000").css("color", "#FFFFFF");
	}).bind("mouseout", function() {
		$(this).css("background-color", "#FFFFFF").css("color", "#000000");
	});
	/**
	 * 編集メニューの項目をクリックされた時の処理
	 */
	$(".sub-menu a").bind("click", function() {
		// 編集メニューを非表示する
		$("#tableMenu").css("display", "none");
		// 編集メニューの表示フラグを更新する
		isTableMenuShowing = false;
		// テーブルの行を削除する
		$("#" + currentShowingColId).parent().remove();
		// テーブルの順番を更新する
		var tableRowArray = $("#tbl_toriatsukai tbody").find("tr");
		for (var i = 1; i < tableRowArray.length; i++) {
			$(tableRowArray[i]).find("td").eq(0).text(i);
			$(tableRowArray[i]).find("td").eq(0).attr("id", i);
			$(tableRowArray[i]).find("td").eq(1).find("select").attr("id", "arrListToriatsukaiJigyouSho" + (i - 1) + ".ddlToriatsukaiJigyouSho");
			$(tableRowArray[i]).find("td").eq(1).find("select").attr("name", "arrListToriatsukaiJigyouSho[" + (i - 1) + "].ddlToriatsukaiJigyouSho");
			$(tableRowArray[i]).find("td").eq(1).find("input").attr("id", "arrListToriatsukaiJigyouSho" + (i - 1) + ".txtToriatsukaiJigyouShoHidden");
			$(tableRowArray[i]).find("td").eq(1).find("input").attr("name", "arrListToriatsukaiJigyouSho[" + (i - 1) + "].txtToriatsukaiJigyouShoHidden");
			$(tableRowArray[i]).find("td").eq(2).find("input").eq(0).attr("id", "arrListToriatsukaiJigyouSho" + (i - 1) + ".txtEigyouTantoushaCode");
			$(tableRowArray[i]).find("td").eq(2).find("input").eq(0).attr("name", "arrListToriatsukaiJigyouSho[" + (i - 1) + "].txtEigyouTantoushaCode");
			$(tableRowArray[i]).find("td").eq(2).find("input").eq(1).attr("id", "arrListToriatsukaiJigyouSho" + (i - 1) + ".txtEigyouTantoushaName");
			$(tableRowArray[i]).find("td").eq(2).find("input").eq(1).attr("name", "arrListToriatsukaiJigyouSho[" + (i - 1) + "].txtEigyouTantoushaName");
			$(tableRowArray[i]).find("td").eq(4).find("input").eq(0).attr("id", "arrListToriatsukaiJigyouSho" + (i - 1) + ".txtJimuTantoushaCode");
			$(tableRowArray[i]).find("td").eq(4).find("input").eq(0).attr("name", "arrListToriatsukaiJigyouSho[" + (i - 1) + "].txtJimuTantoushaCode");
			$(tableRowArray[i]).find("td").eq(4).find("input").eq(1).attr("id", "arrListToriatsukaiJigyouSho" + (i - 1) + ".txtEigyouTantoushaName");
			$(tableRowArray[i]).find("td").eq(4).find("input").eq(1).attr("name", "arrListToriatsukaiJigyouSho[" + (i - 1) + "].txtEigyouTantoushaName");
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
	 * 営業担当者コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$(document).on("focus", ".txtEigyouTantoushaCode", function() {
		// 営業担当者コード ≠ NULL の場合、営業担当者コードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			$(this).next("input").val($(this).val().trim());
		}
	});

	/**
	 * 営業担当者からフォーカスが外れた場合の処理。自項目の初期化を行う
	 */
	$(document).on("focusout", ".txtEigyouTantoushaCode", function() {
		var currentTextBox = $(this);
		setTimeout(function(){
			if (!isClickButton) {
				// 営業担当者コード ≠ [変数]営業担当者コード(初期値) の場合
				var txtEigyouTantoushaCode = currentTextBox.next("input").val().trim();
				currentTextBox.next("input").val("");
				if (currentTextBox.val().trim() != txtEigyouTantoushaCode) {
					// 営業担当者氏名 = null
					currentTextBox.parent().next().find("span").html("");
				}

				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem(currentTextBox.val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "営業担当者"));
						currentTextBox.addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						currentTextBox.removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 営業担当者コードの検索ボタンをクリックされた際の処理。営業担当者コード検索画面を呼び出す
	 */
	$(document).on("click", ".btnSearchEigyouTantoushaCode", function() {
		var currentImgPath = $(this).attr("src");
		if (currentImgPath.indexOf("search_dis") < 0) {
			// システム管理者フラグを取得する
			var sysAdminFlag = $("#sysAdminFlag").val().trim();
			// ダイアログ表示フラグを設定する
			userCodeDialogDisplayFlg = "1"; // 営業担当者コード
			// 選択された行インデックスを取得する
			userCodeDialogSelectedIndex = parseInt($(this).parent().parent().find("td").eq(0).attr("id"));		
			// 変数値の情報を取得する
			// 担当者コード
			var userCode = $(this).prev().prev().val().trim();
			userCode = replaceText(userCode);
			// 利用権限コード
			var userRole = "";
			// 所属事業所コード
			var jigyoushoCode = "";
			// システム管理者フラグ ＝ '1'（システム管理者）の場合
			if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				jigyoushoCode = "";
			}
			// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
			else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
				jigyoushoCode = loginJigyouShoCode;
			}
			jigyoushoCode = replaceText(jigyoushoCode);
			showCom0102d05(userCode, userRole, jigyoushoCode);
		}
	});

	/**
	 * 事務担当者コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$(document).on("focus", ".txtJimuTantoushaCode", function() {
		// 事務担当者コード ≠ NULL の場合、事務担当者コードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			$(this).next("input").val($(this).val().trim());
		}
	});

	/**
	 * 事務担当者からフォーカスが外れた場合の処理。自項目の初期化を行う
	 */
	$(document).on("focusout", ".txtJimuTantoushaCode", function() {
		var currentTextBox = $(this);
		setTimeout(function(){
			if (!isClickButton) {
				// 事務担当者コード ≠ [変数]事務担当者コード(初期値) の場合
				var txtJimuTantoushaCode = currentTextBox.next("input").val().trim();
				currentTextBox.next("input").val("");
				if (currentTextBox.val().trim() != txtJimuTantoushaCode) {
					// 事務担当者氏名 = null
					currentTextBox.parent().next().find("span").html("");
				}

				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem(currentTextBox.val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "事務担当者"));
						currentTextBox.addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						currentTextBox.removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 事務担当者コードの検索ボタンをクリックされた際の処理。事務担当者コード検索画面を呼び出す
	 */
	$(document).on("click", ".btnSearchJimuTantoushaCode", function() {
		var currentImgPath = $(this).attr("src");
		if (currentImgPath.indexOf("search_dis") < 0) {
			// システム管理者フラグを取得する
			var sysAdminFlag = $("#sysAdminFlag").val().trim();
			// ダイアログ表示フラグを設定する
			userCodeDialogDisplayFlg = "2"; // 営業担当者コード
			// 選択された行インデックスを取得する
			userCodeDialogSelectedIndex = parseInt($(this).parent().parent().find("td").eq(0).attr("id"));		
			// 変数値の情報を取得する
			// 担当者コード
			var userCode = $(this).prev().prev().val().trim();
			userCode = replaceText(userCode);
			// 利用権限コード
			var userRole = "";
			// 所属事業所コード
			var jigyoushoCode = "";
			// システム管理者フラグ ＝ '1'（システム管理者）の場合
			if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				jigyoushoCode = "";
			}
			// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
			else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
				jigyoushoCode = loginJigyouShoCode;
			}
			jigyoushoCode = replaceText(jigyoushoCode);
			showCom0102d05(userCode, userRole, jigyoushoCode);
		}
	});

	/**
	 * 幹事事業所からフォーカスが外れた場合の処理。納品センターリストの制御を行う
	 */
	$("#ddlKanjiJigyouSho").bind("change", function() {
		// 納品センター選択肢再設定
		// 幹事事業所.事業所コード ≠ NULL の場合
		if ($(this).val() != "") {
			chainCodeManagerJigyoCodeChangedProcess();
		}
	});

	/**
	 * 会社関係種別からフォーカスが外れた場合の処理。補助科目の入力制御を行う
	 */
	$("#ddlKankeiKaishaType").bind("change", function() {
		var kankeiKaishaType = $("#ddlKankeiKaishaType option:selected").text().trim();
		kankeiKaishaType = kankeiKaishaType.substr(kankeiKaishaType.indexOf("：") + 1);
		// 会社関係種別の選択に応じて、補助科目の入力を制限する
		if (kankeiKaishaType == "関係会社" || kankeiKaishaType == "") {
			// 活性化
			$("#ddlHojoKamoku").prop("disabled", false);
		} else {
			// 非活性化
			$("#ddlHojoKamoku").prop("disabled", true);
		}
	});

	/**
	 * 内税顧客区分からフォーカスが外れた場合の処理。内税消費税端数処理の入力制御を行う
	 */
	$("#ddlUchiZeiKoKyakuKubun").bind("change", function() {
		var uchiZeiKoKyakuKubun = $("#ddlUchiZeiKoKyakuKubun option:selected").text().trim();
		uchiZeiKoKyakuKubun = uchiZeiKoKyakuKubun.substr(uchiZeiKoKyakuKubun.indexOf("：") + 1);
		// 内税顧客区分の選択に応じて、内税消費税端数処理の入力を制限する
		if (uchiZeiKoKyakuKubun == "内税" || uchiZeiKoKyakuKubun == "") {
			// 活性化
			$("#ddlUchiZeiShori").prop("disabled", false);
		} else if (uchiZeiKoKyakuKubun == "外税") {
			// 非活性化
			$("#ddlUchiZeiShori").prop("disabled", true);
		}
	});

	/**
	 * 請求書種類からフォーカスが外れた場合の処理。請求書パターンの入力制御を行う
	 */
	$("#ddlBillingType").bind("change", function() {
		var billingType = $("#ddlBillingType option:selected").text().trim();
		billingType = billingType.substr(billingType.indexOf("：") + 1);
		// 請求書種類の選択に応じて、請求書パターンの入力を制限する
		if (billingType.indexOf("自社請求書") >= 0 || billingType == "") {
			// 活性化
			$("#ddlBillingPattern").prop("disabled", false);
		} else {
			// 非活性化
			$("#ddlBillingPattern").prop("disabled", true);
		}
	});

	/**
	 * 取纏め請求有無からフォーカスが外れた場合の処理。取纏め請求事業所の入力制御を行う
	 */
	$("#ddlBillingToriMatomeUMu").bind("change", function() {
		var billingToriMatomeUMu = $("#ddlBillingToriMatomeUMu option:selected").text().trim();
		billingToriMatomeUMu = billingToriMatomeUMu.substr(billingToriMatomeUMu.indexOf("：") + 1);
		// 取纏め請求有無の選択に応じて、取纏め請求事業所の入力を制限する
		if (billingToriMatomeUMu == "する" || billingToriMatomeUMu == "") {
			// 活性化
			$("#ddlBillingToriMatomeJigyouSho").prop("disabled", false);
		} else if (billingToriMatomeUMu == "しない") {
			// 非活性化
			$("#ddlBillingToriMatomeJigyouSho").prop("disabled", true);
		}
	});

	/**
	 * 請求チェックリスト_出力対象からフォーカスが外れた場合の処理。請求チェックリスト_出力順の入力制御を行う
	 */
	$("#ddlBillingCheckListOutputTarget").bind("change", function() {
		var billingCheckListOutputTarget = $("#ddlBillingCheckListOutputTarget option:selected").text().trim();
		billingCheckListOutputTarget = billingCheckListOutputTarget.substr(billingCheckListOutputTarget.indexOf("：") + 1);
		// 請求チェックリスト_出力対象の選択に応じて、請求チェックリスト_出力順の入力を制限する
		if (billingCheckListOutputTarget == "対象" || billingCheckListOutputTarget == "") {
			// 活性化
			$("#ddlBillingCheckListOutputOrder").prop("disabled", false);
		} else if (billingCheckListOutputTarget == "対象外") {
			// 非活性化
			$("#ddlBillingCheckListOutputOrder").prop("disabled", true);
		}
	});

	/**
	 * 入金種別からフォーカスが外れた場合の処理。入金口座の入力制御を行う
	 */
	$("#ddlPaymentType").bind("change", function() {
		var paymentType = $("#ddlPaymentType option:selected").text().trim();
		paymentType = paymentType.substr(paymentType.indexOf("：") + 1);
		// 入金種別の選択に応じて、入金口座の入力を制限する
		if (paymentType == "口座振替" || paymentType == "普通振込" || paymentType == "") {
			// 活性化
			$("#ddlPaymentAccount").prop("disabled", false);
		} else if (paymentType == "現金" || paymentType == "小切手") {
			// 非活性化
			$("#ddlPaymentAccount").prop("disabled", true);
		}
	});

	/**
	 * 請求データ区分からフォーカスが外れた場合の処理。請求データ配信ＩＤの入力制御を行う
	 */
	$("#ddlBillingDataKubun").bind("change", function() {
		var billingDataKubun = $("#ddlBillingDataKubun option:selected").text().trim();
		billingDataKubun = billingDataKubun.substr(billingDataKubun.indexOf("：") + 1);
		// 請求データ区分の選択に応じて、請求データ配信ＩＤの入力を制限する
		if (billingDataKubun == "オンライン" || billingDataKubun == "") {
			// 活性化
			$("#txtBillingDataDeliveryId").prop("readonly", false).removeClass("txt_disable");
		} else if (billingDataKubun == "請求書") {
			// 非活性化
			$("#txtBillingDataDeliveryId").prop("readonly", true).addClass("txt_disable");
		}
	});

	/**
	 * 請求先名称からフォーカスが外れた場合の処理
	 */
	$("#txtBillingName").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingName").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingName").val(), true, TYPE_EM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先名称"));
							$("#txtBillingName").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingName").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先名称カナからフォーカスが外れた場合の処理
	 */
	$("#txtBillingNameKana").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingNameKana").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingNameKana").val(), false, TYPE_EM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先名称カナ"));
							$("#txtBillingNameKana").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingNameKana").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先略称からフォーカスが外れた場合の処理
	 */
	$("#txtBillingNameR").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingNameR").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingNameR").val(), true, TYPE_EM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先略称"));
							$("#txtBillingNameR").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingNameR").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先郵便番号からフォーカスが外れた場合の処理
	 */
	$("#txtBillingZipCode1, #txtBillingZipCode2").bind("focusout", function() {
		// 請求先郵便番号を入力時、本当の請求郵便番号をtxtPostalCodeResultに保存する
		var postalCode1 = $("#txtBillingZipCode1").val().trim();
		var postalCode2 = $("#txtBillingZipCode2").val().trim();
		$("#txtBillingZipCodeResult").val(postalCode1 + postalCode2);

		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingZipCode1").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingZipCodeResult").val(), true, TYPE_NUM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先郵便番号"));
							$("#txtBillingZipCode1").addClass("form-error-field");
							$("#txtBillingZipCode2").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingZipCode1").removeClass("form-error-field");
							$("#txtBillingZipCode2").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先住所１からフォーカスが外れた場合の処理
	 */
	$("#txtBillingAddress1").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingAddress1").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingAddress1").val(), true, TYPE_EM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先住所１"));
							$("#txtBillingAddress1").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingAddress1").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先住所２からフォーカスが外れた場合の処理
	 */
	$("#txtBillingAddress2").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingAddress2").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingAddress2").val(), false, TYPE_EM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先住所２"));
							$("#txtBillingAddress2").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingAddress2").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先電話番号からフォーカスが外れた場合の処理
	 */
	$("#txtBillingTel1, #txtBillingTel2, #txtBillingTel3").bind("focusout", function() {
		// 請求先電話番号を入力時、本当の請求電話番号をtxtTelResultに保存する
		var tel1 = $("#txtBillingTel1").val().trim();
		var tel2 = $("#txtBillingTel2").val().trim();
		var tel3 = $("#txtBillingTel3").val().trim();
		$("#txtBillingTelResult").val(tel1 + tel2 + tel3);

		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingTel1").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingTelResult").val(), false, TYPE_NUM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先電話番号"));
							$("#txtBillingTel1").addClass("form-error-field");
							$("#txtBillingTel2").addClass("form-error-field");
							$("#txtBillingTel3").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingTel1").removeClass("form-error-field");
							$("#txtBillingTel2").removeClass("form-error-field");
							$("#txtBillingTel3").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求先FAX番号からフォーカスが外れた場合の処理
	 */
	$("#txtBillingFax1, #txtBillingFax2, #txtBillingFax3").bind("focusout", function() {
		// 請求先FAX番号を入力時、本当の請求FAX番号をtxtFaxResultに保存する
		var fax1 = $("#txtBillingFax1").val().trim();
		var fax2 = $("#txtBillingFax2").val().trim();
		var fax3 = $("#txtBillingFax3").val().trim();
		$("#txtBillingFaxResult").val(fax1 + fax2 + fax3);

		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingFax1").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingFaxResult").val(), false, TYPE_NUM, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先FAX番号"));
							$("#txtBillingFax1").addClass("form-error-field");
							$("#txtBillingFax2").addClass("form-error-field");
							$("#txtBillingFax3").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingFax1").removeClass("form-error-field");
							$("#txtBillingFax2").removeClass("form-error-field");
							$("#txtBillingFax3").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 締日１からフォーカスが外れた場合の処理
	 */
	$("#txtCloseDay1").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtCloseDay1").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "締日１"));
						$("#txtCloseDay1").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCloseDay1").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 回収日１からフォーカスが外れた場合の処理
	 */
	$("#txtRecoveryDay1").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtRecoveryDay1").val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "回収日１"));
						$("#txtRecoveryDay1").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtRecoveryDay1").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 締日２からフォーカスが外れた場合の処理
	 */
	$("#txtCloseDay2").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtCloseDay2").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "締日２"));
						$("#txtCloseDay2").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtCloseDay2").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 回収日２からフォーカスが外れた場合の処理
	 */
	$("#txtRecoveryDay2").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtRecoveryDay2").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "回収日２"));
						$("#txtRecoveryDay2").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtRecoveryDay2").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 手形サイトからフォーカスが外れた場合の処理
	 */
	$("#txtNoteSite").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtNoteSite").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "手形サイト"));
						$("#txtNoteSite").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtNoteSite").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 取引コードからフォーカスが外れた場合の処理
	 */
	$("#txtTorihikiCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtTorihikiCode").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "取引コード"));
						$("#txtTorihikiCode").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtTorihikiCode").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 分類コード（定番、店直）からフォーカスが外れた場合の処理
	 */
	$("#txtBunruiCodeTeibanShop").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtBunruiCodeTeibanShop").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "分類コード（定番、店直）"));
						$("#txtBunruiCodeTeibanShop").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtBunruiCodeTeibanShop").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 伝票区分（定番、店直）からフォーカスが外れた場合の処理
	 */
	$("#txtDenpyouKubunTeibanShop").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtDenpyouKubunTeibanShop").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "伝票区分（定番、店直）"));
						$("#txtDenpyouKubunTeibanShop").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtDenpyouKubunTeibanShop").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 分類コード（定番、センター）からフォーカスが外れた場合の処理
	 */
	$("#txtBunruiCodeTeibanCenter").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtBunruiCodeTeibanCenter").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "分類コード（定番、センター）"));
						$("#txtBunruiCodeTeibanCenter").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtBunruiCodeTeibanCenter").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 伝票区分（定番、センター）からフォーカスが外れた場合の処理
	 */
	$("#txtDenpyouKubunTeibanCenter").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtDenpyouKubunTeibanCenter").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "伝票区分（定番、センター）"));
						$("#txtDenpyouKubunTeibanCenter").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtDenpyouKubunTeibanCenter").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 分類コード（特売、店直）からフォーカスが外れた場合の処理
	 */
	$("#txtBunruiCodeTokubaiShop").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtBunruiCodeTokubaiShop").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "分類コード（特売、店直）"));
						$("#txtBunruiCodeTokubaiShop").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtBunruiCodeTokubaiShop").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 伝票区分（特売、店直）からフォーカスが外れた場合の処理
	 */
	$("#txtDenpyouKubunTokubaiShop").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtDenpyouKubunTokubaiShop").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "伝票区分（特売、店直）"));
						$("#txtDenpyouKubunTokubaiShop").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtDenpyouKubunTokubaiShop").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 分類コード（特売、センター）からフォーカスが外れた場合の処理
	 */
	$("#txtBunruiCodeTokubaiCenter").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtBunruiCodeTokubaiCenter").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "分類コード（特売、センター）"));
						$("#txtBunruiCodeTokubaiCenter").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtBunruiCodeTokubaiCenter").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 伝票区分（特売、センター）からフォーカスが外れた場合の処理
	 */
	$("#txtDenpyouKubunTokubaiCenter").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtDenpyouKubunTokubaiCenter").val(), false, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "伝票区分（特売、センター）"));
						$("#txtDenpyouKubunTokubaiCenter").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtDenpyouKubunTokubaiCenter").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 請求データ配信ＩＤからフォーカスが外れた場合の処理
	 */
	$("#txtBillingDataDeliveryId").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				if (!$("#txtBillingDataDeliveryId").hasClass("txt_disable")) {
					// 入力チェック
					var viewMode = $("#viewMode").val().trim();
					if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
						var errorID = chkItem($("#txtBillingDataDeliveryId").val(), false, TYPE_NUM_ALPHA, false, null);
						if (errorID != null) {
							$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求データ配信ＩＤ"));
							$("#txtBillingDataDeliveryId").addClass("form-error-field");
						} else {
							$("#txtMess").html("");
							$("#txtBillingDataDeliveryId").removeClass("form-error-field");
						}
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 集計コード１からフォーカスが外れた場合の処理
	 */
	$("#txtShuukeiCode1").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtShuukeiCode1").val(), false, TYPE_NUM_ALPHA, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "集計コード１"));
						$("#txtShuukeiCode1").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtShuukeiCode1").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 集計コード２からフォーカスが外れた場合の処理
	 */
	$("#txtShuukeiCode2").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtShuukeiCode2").val(), false, TYPE_NUM_ALPHA, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "集計コード２"));
						$("#txtShuukeiCode2").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtShuukeiCode2").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 使用中止日からフォーカスが当たった場合の処理
	 */
	$("#txtShiyouChuushiDay").bind("focus", function() {
		$(this).val(removeSlashChar($(this).val().trim())).select();
	});

	/**
	 * 使用中止日からフォーカスが外れた場合の処理
	 */
	$("#txtShiyouChuushiDay").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = checkDate($("#txtShiyouChuushiDay").val(), DATE_FORMAT_YMD);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "使用中止日"));
						$("#txtShiyouChuushiDay").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtShiyouChuushiDay").removeClass("form-error-field");
					}
					$("#txtShiyouChuushiDay").val(addSlashChar($("#txtShiyouChuushiDay").val().trim()));
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 状況コードからフォーカスが外れた場合の処理
	 */
	$("#txtStatusCode").bind("focusout", function() {
		setTimeout(function(){
			if (!isClickButton) {
				// 入力チェック
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
					var errorID = chkItem($("#txtStatusCode").val(), true, TYPE_NUM, false, null);
					if (errorID != null) {
						$("#txtMess").html($("#" + errorID).val().replace("{1}", "状況コード"));
						$("#txtStatusCode").addClass("form-error-field");
					} else {
						$("#txtMess").html("");
						$("#txtStatusCode").removeClass("form-error-field");
					}
				}
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 登録ボタンをクリックされた時の処理。入力された得意先情報をDBへ登録及び更新を行う
	 */
	$("#btnRegister").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnRegister");
		jQuestion_confirm($("#COM001-I_register").val(), $("#COM001-I_register").attr("title"), function(retVal) {
			if (retVal) {
				// ボタンを有効にする
				enableButton("btnRegister");
				var viewMode = $("#viewMode").val().trim();
				if (viewMode == SHINKI_MODE) { // 新規モード
					$("#btnRegisterHidden").trigger("click");
				} else if (viewMode == TEISEI_MODE) { // 訂正モード
					// フォームが変更されたかどうかチェックする
					if (isDataChanged()) {
						$("#btnRegisterHidden").trigger("click");
					} else {
						$("#txtMess").html($("#COM034-E").val());
					}
				} else if (viewMode == TORIKESI_MODE) { // 取消モード
					$("#btnRegisterHidden").trigger("click");
				}
			} else {
				// ボタンを有効にする
				enableButton("btnRegister");
			}
		});
	});

	/**
	 * 戻るボタンをクリックされた時の処理。得意先マスタ一覧画面に戻る
	 */
	$("#btnBack").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableReturnButton("btnBack");
		$("#btnBackHidden").trigger("click");
	});

	/**
	 * クリアボタンをクリックされた時の処理。登録画面を初期化する
	 */
	$("#btnClear").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnClear");
		jQuestion_confirm($("#COM001-I_clear").val(), $("#COM001-I_clear").attr("title"), function(retVal) {
			if (retVal) {
				// ボタンを有効にする
				enableButton("btnClear");
				$("#btnClearHidden").trigger("click");
			} else {
				// ボタンを有効にする
				enableButton("btnClear");
			}
		});
	});

	/**
	 * エンターキー押下時の遷移
	 */
	function bindEnterEvent() {
		$("input[type=text]").unbind("keydown").bind("keydown", function(e) {
			if (e.keyCode == 13) {
				EnterKey(e, this, ".data_conditions_area");
			}
		});
	}

	/**
	 * 得意先フラグ／請求先フラグのチェックに変更があった場合の処理。請求先情報入力エリアの入力を制御する
	 */
	function checkCustomerBillingChangedProcess() {
		// 得意先フラグ ＝ チェックあり and 請求先フラグ ＝ チェックあり　の場合
		if ($("#chkCustomer").is(":checked") && $("#chkBilling").is(":checked")) {
			$("#txtBillingName").val($("#txtCustomerName").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingNameKana").val($("#txtCustomerNameKana").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingNameR").val($("#txtCustomerNameR").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingZipCode1").val($("#txtPostalCode1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingZipCode2").val($("#txtPostalCode2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingZipCodeResult").val($("#txtPostalCodeResult").val().trim());
			$("#txtBillingAddress1").val($("#txtAddress1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingAddress2").val($("#txtAddress2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTel1").val($("#txtTel1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTel2").val($("#txtTel2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTel3").val($("#txtTel3").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTelResult").val($("#txtTelResult").val().trim());
			$("#txtBillingFax1").val($("#txtFax1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingFax2").val($("#txtFax2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingFax3").val($("#txtFax3").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingFaxResult").val($("#txtFaxResult").val().trim());
		}

		// 得意先フラグ ＝ チェックあり and 請求先フラグ ＝ チェックなし　の場合
		if ($("#chkCustomer").is(":checked") && !$("#chkBilling").is(":checked")) {
			$("#txtBillingName").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingNameKana").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingNameR").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingZipCode1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingZipCode2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingAddress1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingAddress2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingTel1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingTel2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingTel3").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingFax1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingFax2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingFax3").val("").prop("readonly", true).addClass("txt_disable");
		}

		// 得意先フラグ ＝ チェックなし and 請求先フラグ ＝ チェックあり　の場合
		if (!$("#chkCustomer").is(":checked") && $("#chkBilling").is(":checked")) {
			$("#txtBillingName").val($("#txtCustomerName").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingNameKana").val($("#txtCustomerNameKana").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingNameR").val($("#txtCustomerNameR").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingZipCode1").val($("#txtPostalCode1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingZipCode2").val($("#txtPostalCode2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingZipCodeResult").val($("#txtPostalCodeResult").val().trim());
			$("#txtBillingAddress1").val($("#txtAddress1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingAddress2").val($("#txtAddress2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTel1").val($("#txtTel1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTel2").val($("#txtTel2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTel3").val($("#txtTel3").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingTelResult").val($("#txtTelResult").val().trim());
			$("#txtBillingFax1").val($("#txtFax1").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingFax2").val($("#txtFax2").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingFax3").val($("#txtFax3").val().trim()).prop("readonly", false).removeClass("txt_disable");
			$("#txtBillingFaxResult").val($("#txtFaxResult").val().trim());
		}
	}

	/**
	 * 表示モードによって、コントロールの値をセットする
	 */
	function initControlDataBasedOnMode() {
		var viewMode = $("#viewMode").val().trim();

		// 請求先名称Hiddenの値をセットする
		$("#txtBillingSearchName").html($("#txtBillingSearchNameHidden").val().trim());
		// チェーン名称Hiddenの値をセットする
		$("#txtChainName").html($("#txtChainNameHidden").val().trim());
		// 取扱事業所テーブルの営業担当者名と事務担当者名の値をセットする
		$(".txtEigyouTantoushaCode").each(function() {
			var eigyouTantoushaName = $(this).next().val().trim();
			$(this).parent().parent().find("td").eq(3).find("span").html(eigyouTantoushaName);
		});
		$(".txtJimuTantoushaCode").each(function() {
			var jimuTantoushaName = $(this).next().val().trim();
			$(this).parent().parent().find("td").eq(5).find("span").html(jimuTantoushaName);
		});

		if (viewMode == SHINKI_MODE) { // 新規モード
			var kankeiKaishaType = $("#ddlKankeiKaishaType option:selected").text().trim();
			kankeiKaishaType = kankeiKaishaType.substr(kankeiKaishaType.indexOf("：") + 1);
			// 会社関係種別の選択に応じて、補助科目の入力を制限する
			if (kankeiKaishaType != "関係会社" && kankeiKaishaType != "") {
				// 非活性化
				$("#ddlHojoKamoku").prop("disabled", true);
			}

			var uchiZeiKoKyakuKubun = $("#ddlUchiZeiKoKyakuKubun option:selected").text().trim();
			uchiZeiKoKyakuKubun = uchiZeiKoKyakuKubun.substr(uchiZeiKoKyakuKubun.indexOf("：") + 1);
			// 内税顧客区分の選択に応じて、内税消費税端数処理の入力を制限する
			if (uchiZeiKoKyakuKubun != "内税" && uchiZeiKoKyakuKubun != "") {
				// 非活性化
				$("#ddlUchiZeiShori").prop("disabled", true);
			}

			var billingType = $("#ddlBillingType option:selected").text().trim();
			billingType = billingType.substr(billingType.indexOf("：") + 1);
			// 請求書種類の選択に応じて、請求書パターンの入力を制限する
			if (billingType.indexOf("自社請求書") < 0 && billingType != "") {
				// 非活性化
				$("#ddlBillingPattern").prop("disabled", true);
			}

			var billingToriMatomeUMu = $("#ddlBillingToriMatomeUMu option:selected").text().trim();
			billingToriMatomeUMu = billingToriMatomeUMu.substr(billingToriMatomeUMu.indexOf("：") + 1);
			// 取纏め請求有無の選択に応じて、取纏め請求事業所の入力を制限する
			if (billingToriMatomeUMu != "する" && billingToriMatomeUMu != "") {
				// 非活性化
				$("#ddlBillingToriMatomeJigyouSho").prop("disabled", true);
			}

			var billingCheckListOutputTarget = $("#ddlBillingCheckListOutputTarget option:selected").text().trim();
			billingCheckListOutputTarget = billingCheckListOutputTarget.substr(billingCheckListOutputTarget.indexOf("：") + 1);
			// 請求チェックリスト_出力対象の選択に応じて、請求チェックリスト_出力順の入力を制限する
			if (billingCheckListOutputTarget != "対象" && billingCheckListOutputTarget != "") {
				// 非活性化
				$("#ddlBillingCheckListOutputOrder").prop("disabled", true);
			}

			var paymentType = $("#ddlPaymentType option:selected").text().trim();
			paymentType = paymentType.substr(paymentType.indexOf("：") + 1);
			// 入金種別の選択に応じて、入金口座の入力を制限する
			if (paymentType != "口座振替" && paymentType != "普通振込" && paymentType != "") {
				// 非活性化
				$("#ddlPaymentAccount").prop("disabled", true);
			}

			var billingDataKubun = $("#ddlBillingDataKubun option:selected").text().trim();
			billingDataKubun = billingDataKubun.substr(billingDataKubun.indexOf("：") + 1);
			// 請求データ区分の選択に応じて、請求データ配信ＩＤの入力を制限する
			if (billingDataKubun != "オンライン" && billingDataKubun != "") {
				// 非活性化
				$("#txtBillingDataDeliveryId").prop("readonly", true).addClass("txt_disable");
			}

			// 使用中止日を有効にする
			$("#txtShiyouChuushiDay").datepicker({
				showOn: "button",
			    buttonImage: rootPath + "/resources/css/images/calendar.gif",
			    buttonImageOnly: true
			});
			// チェーンコードへフォーカスをセットする
			$("#txtChainCode").focus();
		} else if (viewMode == SHOUKAI_MODE || viewMode == TORIKESI_MODE) { // 照会モード／取消モード
			// 使用中止日を無効にする
			$("#txtShiyouChuushiDay").datepicker("disable");
			// 表示モードによって、コントロールへフォーカスをセットする
			if (viewMode == SHOUKAI_MODE) {
				// 戻るボタンへフォーカスをセットする
				$("#btnBack").focus();
			} else if (viewMode == TORIKESI_MODE) {
				// Hidden項目の値を設定する
				// 取扱事業所
				var toriatsukaiTableRowArray = $("#tbl_toriatsukai tbody").find("tr");
				for (var i = 1; i < toriatsukaiTableRowArray.length; i++) {
					var ddlToriatsukaiJigyousho = $(toriatsukaiTableRowArray[i]).find("td").eq(1).find("select").val().trim();
					$(toriatsukaiTableRowArray[i]).find("td").eq(1).find("input").val(ddlToriatsukaiJigyousho);
				}
				// 幹事事業所
				if ($("#ddlKanjiJigyouSho").val() != null) {
					$("#txtKanjiJigyouShoHidden").val($("#ddlKanjiJigyouSho").val().trim());
				}
				// 得意先種別
				if ($("#ddlCustomerType").val() != null) {
					$("#txtCustomerTypeHidden").val($("#ddlCustomerType").val().trim());
				}
				// 店舗区分
				if ($("#ddlTenpoKubun").val() != null) {
					$("#txtTenpoKubunHidden").val($("#ddlTenpoKubun").val().trim());
				}
				// 業態区分
				if ($("#ddlGyoutaiKubun").val() != null) {
					$("#txtGyoutaiKubunHidden").val($("#ddlGyoutaiKubun").val().trim());
				}
				// 納品センター
				if ($("#ddlDeliveryCenter").val() != null) {
					$("#txtDeliveryCenterHidden").val($("#ddlDeliveryCenter").val().trim());
				}
				// 関係会社種別
				if ($("#ddlKankeiKaishaType").val() != null) {
					$("#txtKankeiKaishaTypeHidden").val($("#ddlKankeiKaishaType").val().trim());
				}
				// 補助科目
				if ($("#ddlHojoKamoku").val() != null) {
					$("#txtHojoKamokuHidden").val($("#ddlHojoKamoku").val().trim());
				}
				// 採番区分
				if ($("#ddlSaibanKubun").val() != null) {
					$("#txtSaibanKubunHidden").val($("#ddlSaibanKubun").val().trim());
				}
				// YG取引区分
				if ($("#ddlYgTorihikiKubun").val() != null) {
					$("#txtYgTorihikiKubunHidden").val($("#ddlYgTorihikiKubun").val().trim());
				}
				// 内税顧客区分
				if ($("#ddlUchiZeiKoKyakuKubun").val() != null) {
					$("#txtUchiZeiKoKyakuKubunHidden").val($("#ddlUchiZeiKoKyakuKubun").val().trim());
				}
				// 内税消費税端数処理
				if ($("#ddlUchiZeiShori").val() != null) {
					$("#txtUchiZeiShoriHidden").val($("#ddlUchiZeiShori").val().trim());
				}
				// 集金有無
				if ($("#ddlShuukinUmu").val() != null) {
					$("#txtShuukinUmuHidden").val($("#ddlShuukinUmu").val().trim());
				}
				// 現金集金マーク印字
				if ($("#ddlGenkinShuukinMark").val() != null) {
					$("#txtGenkinShuukinMarkHidden").val($("#ddlGenkinShuukinMark").val().trim());
				}
				// 集計出力FLG
				if ($("#ddlShuukinOutputFlag").val() != null) {
					$("#txtShuukinOutputFlagHidden").val($("#ddlShuukinOutputFlag").val().trim());
				}
				// 手入力伝票発行
				if ($("#ddlManualInputBilling").val() != null) {
					$("#txtManualInputBillingHidden").val($("#ddlManualInputBilling").val().trim());
				}
				// 手入力出荷伝票
				if ($("#ddlManualInputDelivery").val() != null) {
					$("#txtManualInputDeliveryHidden").val($("#ddlManualInputDelivery").val().trim());
				}
				// 伝票行計算金額まるめ
				if ($("#ddlSlipLineCalculationAmountRounding").val() != null) {
					$("#txtSlipLineCalculationAmountRoundingHidden").val($("#ddlSlipLineCalculationAmountRounding").val().trim());
				}
				// 出荷伝票出力品コード
				if ($("#ddlDeliveryOutputProductCode").val() != null) {
					$("#txtDeliveryOutputProductCodeHidden").val($("#ddlDeliveryOutputProductCode").val().trim());
				}
				// 請求単位
				if ($("#ddlBillingUnit").val() != null) {
					$("#txtBillingUnitHidden").val($("#ddlBillingUnit").val().trim());
				}
				// 請求単価
				if ($("#ddlBillingUnitPrice").val() != null) {
					$("#txtBillingUnitPriceHidden").val($("#ddlBillingUnitPrice").val().trim());
				}
				// 請求書種類
				if ($("#ddlBillingType").val() != null) {
					$("#txtBillingTypeHidden").val($("#ddlBillingType").val().trim());
				}
				// 取纏め請求
				if ($("#ddlBillingToriMatomeUMu").val() != null) {
					$("#txtBillingToriMatomeUMuHidden").val($("#ddlBillingToriMatomeUMu").val().trim());
				}
				// 取纏め請求事業所
				if ($("#ddlBillingToriMatomeJigyouSho").val() != null) {
					$("#txtBillingToriMatomeJigyouShoHidden").val($("#ddlBillingToriMatomeJigyouSho").val().trim());
				}
				// 請求書パターン
				if ($("#ddlBillingPattern").val() != null) {
					$("#txtBillingPatternHidden").val($("#ddlBillingPattern").val().trim());
				}
				// 請求書住所印字
				if ($("#ddlBillingAddressPrint").val() != null) {
					$("#txtBillingAddressPrintHidden").val($("#ddlBillingAddressPrint").val().trim());
				}
				// 請求集計表区分
				if ($("#ddlBillingShuukeiHyouKubun").val() != null) {
					$("#txtBillingShuukeiHyouKubunHidden").val($("#ddlBillingShuukeiHyouKubun").val().trim());
				}
				// 消費税計算単位
				if ($("#ddlBillingShouhizeiCalculationUnit").val() != null) {
					$("#txtBillingShouhizeiCalculationUnitHidden").val($("#ddlBillingShouhizeiCalculationUnit").val().trim());
				}
				// 請求チェックリスト 出力対象
				if ($("#ddlBillingCheckListOutputTarget").val() != null) {
					$("#txtBillingCheckListOutputTargetHidden").val($("#ddlBillingCheckListOutputTarget").val().trim());
				}
				// 消費税端数処理
				if ($("#ddlBillingShouhizeiShori").val() != null) {
					$("#txtBillingShouhizeiShoriHidden").val($("#ddlBillingShouhizeiShori").val().trim());
				}
				// 請求チェックリスト 出力順
				if ($("#ddlBillingCheckListOutputOrder").val() != null) {
					$("#txtBillingCheckListOutputOrderHidden").val($("#ddlBillingCheckListOutputOrder").val().trim());
				}
				// 回収月区分１
				if ($("#ddlRecoveryMonthKubun1").val() != null) {
					$("#txtRecoveryMonthKubun1Hidden").val($("#ddlRecoveryMonthKubun1").val().trim());
				}
				// 回収月区分２
				if ($("#ddlRecoveryMonthKubun2").val() != null) {
					$("#txtRecoveryMonthKubun2Hidden").val($("#ddlRecoveryMonthKubun2").val().trim());
				}
				// 入金種別
				if ($("#ddlPaymentType").val() != null) {
					$("#txtPaymentTypeHidden").val($("#ddlPaymentType").val().trim());
				}
				// 入金口座
				if ($("#ddlPaymentAccount").val() != null) {
					$("#txtPaymentAccountHidden").val($("#ddlPaymentAccount").val().trim());
				}
				// 受領データ突合区分
				if ($("#ddlJuryouDataKubun").val() != null) {
					$("#txtJuryouDataKubunHidden").val($("#ddlJuryouDataKubun").val().trim());
				}
				// 請求データ区分
				if ($("#ddlBillingDataKubun").val() != null) {
					$("#txtBillingDataKubunHidden").val($("#ddlBillingDataKubun").val().trim());
				}
				// 修正データ突合区分
				if ($("#ddlModifyDataKubun").val() != null) {
					$("#txtModifyDataKubunHidden").val($("#ddlModifyDataKubun").val().trim());
				}
				// 請求先支払い案内データ区分
				if ($("#ddlBillingPaymentDataKubun").val() != null) {
					$("#txtBillingPaymentDataKubunHidden").val($("#ddlBillingPaymentDataKubun").val());
				}
				// 登録ボタンへフォーカスをセットする
				$("#btnRegister").focus();
			}
		} else if (viewMode == TEISEI_MODE) { // 訂正モード
			// 本当の郵便番号をtxtPostalCodeResultに保存する
			var postalCode1 = $("#txtPostalCode1").val().trim();
			var postalCode2 = $("#txtPostalCode2").val().trim();
			$("#txtPostalCodeResult").val(postalCode1 + postalCode2);

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

			// 本当の請求郵便番号をtxtPostalCodeResultに保存する
			var postalCode1 = $("#txtBillingZipCode1").val().trim();
			var postalCode2 = $("#txtBillingZipCode2").val().trim();
			$("#txtBillingZipCodeResult").val(postalCode1 + postalCode2);

			// 本当の請求電話番号をtxtTelResultに保存する
			var tel1 = $("#txtBillingTel1").val().trim();
			var tel2 = $("#txtBillingTel2").val().trim();
			var tel3 = $("#txtBillingTel3").val().trim();
			$("#txtBillingTelResult").val(tel1 + tel2 + tel3);

			// 本当の請求FAX番号をtxtFaxResultに保存する
			var fax1 = $("#txtBillingFax1").val().trim();
			var fax2 = $("#txtBillingFax2").val().trim();
			var fax3 = $("#txtBillingFax3").val().trim();
			$("#txtBillingFaxResult").val(fax1 + fax2 + fax3);

			// 納品先センターコンボボックスの活性化／非活性化をチェックする
			if ($("#isDisableDeliveryCenter").val() == "true") {
				// 画面制御
				$("#ddlDeliveryCenter").prop("disabled", true);
			}

			var kankeiKaishaType = $("#ddlKankeiKaishaType option:selected").text().trim();
			kankeiKaishaType = kankeiKaishaType.substr(kankeiKaishaType.indexOf("：") + 1);
			// 会社関係種別の選択に応じて、補助科目の入力を制限する
			if (kankeiKaishaType == "関係会社" || kankeiKaishaType == "") {
				// 活性化
				$("#ddlHojoKamoku").prop("disabled", false);
			} else {
				// 非活性化
				$("#ddlHojoKamoku").prop("disabled", true);
			}

			var uchiZeiKoKyakuKubun = $("#ddlUchiZeiKoKyakuKubun option:selected").text().trim();
			uchiZeiKoKyakuKubun = uchiZeiKoKyakuKubun.substr(uchiZeiKoKyakuKubun.indexOf("：") + 1);
			// 内税顧客区分 ＝ '内税' の場合、内税消費税端数処理を活性化する
			if (uchiZeiKoKyakuKubun == "内税" || uchiZeiKoKyakuKubun == "") {
				// 活性化
				$("#ddlUchiZeiShori").prop("disabled", false);
			} else {
				// 非活性化
				$("#ddlUchiZeiShori").prop("disabled", true);
			}

			var billingType = $("#ddlBillingType option:selected").text().trim();
			billingType = billingType.substr(billingType.indexOf("：") + 1);
			// 請求書種類 ＝ '自社請求書' の場合、請求書パターンを活性化する
			if (billingType.indexOf("自社請求書") >= 0 || billingType == "") {
				// 活性化
				$("#ddlBillingPattern").prop("disabled", false);
			} else {
				// 非活性化
				$("#ddlBillingPattern").prop("disabled", true);
			}

			var billingToriMatomeUMu = $("#ddlBillingToriMatomeUMu option:selected").text().trim();
			billingToriMatomeUMu = billingToriMatomeUMu.substr(billingToriMatomeUMu.indexOf("：") + 1);
			// 取纏め請求 ＝ 'する' の場合、取纏め請求事業所を活性化する
			if (billingToriMatomeUMu == "する" || billingToriMatomeUMu == "") {
				// 活性化
				$("#ddlBillingToriMatomeJigyouSho").prop("disabled", false);
			} else {
				// 非活性化
				$("#ddlBillingToriMatomeJigyouSho").prop("disabled", true);
			}

			var billingCheckListOutputTarget = $("#ddlBillingCheckListOutputTarget option:selected").text().trim();
			billingCheckListOutputTarget = billingCheckListOutputTarget.substr(billingCheckListOutputTarget.indexOf("：") + 1);
			// 請求チェックリスト 出力順 ＝ '対象' の場合、請求チェックリスト　出力順を活性化する
			if (billingCheckListOutputTarget == "対象" || billingCheckListOutputTarget == "") {
				// 活性化
				$("#ddlBillingCheckListOutputOrder").prop("disabled", false);
			} else {
				// 非活性化
				$("#ddlBillingCheckListOutputOrder").prop("disabled", true);
			}

			var paymentType = $("#ddlPaymentType option:selected").text().trim();
			paymentType = paymentType.substr(paymentType.indexOf("：") + 1);
			// 入金種別 ＝ '口座振替' or '普通振込' の場合、入金口座を活性化する
			if (paymentType == "口座振替" || paymentType == "普通振込" || paymentType == "") {
				// 活性化
				$("#ddlPaymentAccount").prop("disabled", false);
			} else {
				// 非活性化
				$("#ddlPaymentAccount").prop("disabled", true);
			}

			var billingDataKubun = $("#ddlBillingDataKubun option:selected").text().trim();
			billingDataKubun = billingDataKubun.substr(billingDataKubun.indexOf("：") + 1);
			// 請求データ区分 ＝ 'オンライン' の場合、請求データ配信ＩＤを活性化する
			if (billingDataKubun == "オンライン" || billingDataKubun == "") {
				// 活性化
				$("#txtBillingDataDeliveryId").prop("readonly", false).removeClass("txt_disable");
			}

			// 使用中止日を有効にする
			$("#txtShiyouChuushiDay").datepicker({
				showOn: "button",
			    buttonImage: rootPath + "/resources/css/images/calendar.gif",
			    buttonImageOnly: true
			});
			// 得意先名称へフォーカスをセットする
			$("#txtCustomerName").focus();
		}
		// エラークラスを追加する
		var strError = $("#lstErrorID").val();
		if (strError != null && strError != "") {
			var lstError = strError.split(DELIMITER);
			var focusControl = $("#" + lstError[0]);
			addClassErrorToControl(lstError);
			// 取扱事業所のエラーをチェックする
			var toriatsukaiTableRowArray = $("#tbl_toriatsukai tbody").find("tr");
			for (var i = toriatsukaiTableRowArray.length - 1; i >= 1; i--) {
				var rowIndex = i;
				var focusIndex = -1;
				for (var j = 0; j < lstError.length; j++) {
					if (lstError[j].indexOf("arrListToriatsukaiJigyouSho") >= 0) focusIndex = 0;
					if (lstError[j].indexOf("arrListToriatsukaiEigyou") >= 0) focusIndex = 1;
					if (lstError[j].indexOf("arrListToriatsukaiJimu") >= 0) focusIndex = 2;
					if (focusIndex == 0 || focusIndex == 1 || focusIndex == 2) {
						var errorIndex = lstError[j].replace("arrListToriatsukaiJigyouSho", "")
											.replace("arrListToriatsukaiEigyou", "")
											.replace("arrListToriatsukaiJimu", "");
						if (parseInt(rowIndex) == parseInt(errorIndex)) {
							$(toriatsukaiTableRowArray[i]).addClass("form-error-field");
							$(toriatsukaiTableRowArray[i]).find("select").addClass("form-error-field");
							$(toriatsukaiTableRowArray[i]).find("input").addClass("form-error-field");
							// フォーカスの項目を判断する
							if (j == 0) {
								// 事務担当者コード
								if (focusIndex == 2) focusControl = $(toriatsukaiTableRowArray[i]).find("td").eq(4).find("input").eq(0);
								// 営業担当者コード
								if (focusIndex == 1) focusControl = $(toriatsukaiTableRowArray[i]).find("td").eq(2).find("input").eq(0);
								// 事業所コード
								if (focusIndex == 0) focusControl = $(toriatsukaiTableRowArray[i]).find("select");
							}
						}
					}
				}
			}
			// 最上位の項目（タブ順が最も若い項目）にフォーカスをセットする
			focusControl.focus().select();
		}
		// 登録が完了の場合、メッセージの表示場所の背景を更新する
		if ($("#infoMessFlag").val() == "true") {
			addInfoClass();
			// jStorageを更新する
			originalData = saveFormData();
			saveToStorage(SCREEN_NAME, originalData);
		}
		// 請求情報の活性化／非活性化をチェックする
		// 得意先フラグ ＝ チェックあり and 請求先フラグ ＝ チェックなし　の場合
		if ($("#chkCustomer").is(":checked") && !$("#chkBilling").is(":checked")) {
			$("#txtBillingName").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingNameKana").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingNameR").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingZipCode1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingZipCode2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingAddress1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingAddress2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingTel1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingTel2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingTel3").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingFax1").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingFax2").val("").prop("readonly", true).addClass("txt_disable");
			$("#txtBillingFax3").val("").prop("readonly", true).addClass("txt_disable");
		}
		// コンボボックスの活性化／非活性化をチェックする
		if ($("#isDisableCombobox").val() == "true") {
			// 画面制御
			$("#ddlKanjiJigyouSho").prop("disabled", true);
			$("#ddlCustomerType").prop("disabled", true);
			$("#ddlTenpoKubun").prop("disabled", true);
			$("#ddlGyoutaiKubun").prop("disabled", true);
			$("#ddlDeliveryCenter").prop("disabled", true);
			$("#ddlKankeiKaishaType").prop("disabled", true);
			$("#ddlHojoKamoku").prop("disabled", true);
			$("#ddlSaibanKubun").prop("disabled", true);
			$("#ddlYgTorihikiKubun").prop("disabled", true);
			$("#ddlUchiZeiKoKyakuKubun").prop("disabled", true);
			$("#ddlUchiZeiShori").prop("disabled", true);
			$("#ddlShuukinUmu").prop("disabled", true);
			$("#ddlGenkinShuukinMark").prop("disabled", true);
			$("#ddlShuukinOutputFlag").prop("disabled", true);
			$("#ddlManualInputBilling").prop("disabled", true);
			$("#ddlManualInputDelivery").prop("disabled", true);
			$("#ddlSlipLineCalculationAmountRounding").prop("disabled", true);
			$("#ddlDeliveryOutputProductCode").prop("disabled", true);
		}
		// エラー時のコントロール制御
		if ($("#isDisableControl").val() == "true") {
			errorControl();
			// 検索イメージを変更する
			var currentImgPath = $(".searchIcon").eq(0).attr("src");
			var newImgPath = currentImgPath.replace("search.png", "search_dis.png");
			$(".searchIcon").attr("src", newImgPath);
		}
	}

	/**
	 *　変更有無の判断
	 *
	 * @return boolean true :　違う点がある false:違う点がなし
	 */
	function isDataChanged() {
		var currentData = saveFormData();
		// オリジナルデータと現在のデータを比べる
		return !compareWithStorage(SCREEN_NAME, currentData);
	}

	/**
	 * 更新処理前/後に保存する
	 */
	function saveFormData() {
		var resultArray = [];
		// 得意先コード
		resultArray.push($("#txtCustomerCode").val().trim());
		// 得意先フラグ
		resultArray.push($("#chkCustomer").is(":checked") ? true : false);
		// 請求先フラグ
		resultArray.push($("#chkBilling").is(":checked") ? true : false);
		// 請求先コード
		resultArray.push($("#txtBillingCode").val().trim());
		// チェーンコード
		resultArray.push($("#txtChainCode").val().trim());
	    // チェーン枝番
		resultArray.push($("#txtChainEda").val().trim());
		// 得意先名称
		resultArray.push($("#txtCustomerName").val().trim());
		// 得意先名称カナ
		resultArray.push($("#txtCustomerNameKana").val().trim());
		// 得意先略称
		resultArray.push($("#txtCustomerNameR").val().trim());
		// 郵便番号
		resultArray.push($("#txtPostalCode1").val().trim());
		resultArray.push($("#txtPostalCode2").val().trim());
		// 住所１
		resultArray.push($("#txtAddress1").val().trim());
		// 住所２
		resultArray.push($("#txtAddress2").val().trim());
		// 電話番号
		resultArray.push($("#txtTel1").val().trim());
		resultArray.push($("#txtTel2").val().trim());
		resultArray.push($("#txtTel3").val().trim());
		// FAX番号
		resultArray.push($("#txtFax1").val().trim());
		resultArray.push($("#txtFax2").val().trim());
		resultArray.push($("#txtFax3").val().trim());
		// 得意先担当者
		resultArray.push($("#txtCustomerTantousha").val().trim());
		// メールアドレス
		resultArray.push($("#txtMailAddress").val().trim());
		// 取扱事業所テーブルの値を作成する
		var toriatsukaiTableString = "";
		var toriatsukaiTableRowArray = $("#tbl_toriatsukai").find("tr");
		for (var i = 1; i < toriatsukaiTableRowArray.length; i++) {
			toriatsukaiTableString += $(toriatsukaiTableRowArray[i]).find("td").eq(1).find("select").val();
			toriatsukaiTableString += $(toriatsukaiTableRowArray[i]).find("td").eq(2).find("input").eq(0).val();
			toriatsukaiTableString += $(toriatsukaiTableRowArray[i]).find("td").eq(4).find("input").eq(0).val();
		}
		resultArray.push(toriatsukaiTableString.trim());
		// 幹事事業所
		if ($("#ddlKanjiJigyouSho").val() != null) resultArray.push($("#ddlKanjiJigyouSho").val().trim());
		// 得意先種別
		if ($("#ddlCustomerType").val() != null) resultArray.push($("#ddlCustomerType").val().trim());
		// 業態区分
		if ($("#ddlGyoutaiKubun").val() != null) resultArray.push($("#ddlGyoutaiKubun").val().trim());
		// 納品センター
		if ($("#ddlDeliveryCenter").val() != null) resultArray.push($("#ddlDeliveryCenter").val().trim());
		// 関係会社種別
		if ($("#ddlKankeiKaishaType").val() != null) resultArray.push($("#ddlKankeiKaishaType").val().trim());
		// 補助科目
		if ($("#ddlHojoKamoku").val() != null) resultArray.push($("#ddlHojoKamoku").val().trim());
		// 採番区分
		if ($("#ddlSaibanKubun").val() != null) resultArray.push($("#ddlSaibanKubun").val().trim());
		// 店舗区分
		if ($("#ddlTenpoKubun").val() != null) resultArray.push($("#ddlTenpoKubun").val().trim());
		// YG取引区分
		if ($("#ddlYgTorihikiKubun").val() != null) resultArray.push($("#ddlYgTorihikiKubun").val().trim());
		// 内税顧客区分
		if ($("#ddlUchiZeiKoKyakuKubun").val() != null) resultArray.push($("#ddlUchiZeiKoKyakuKubun").val().trim());
		// 内税消費税端数処理
		if ($("#ddlUchiZeiShori").val() != null) resultArray.push($("#ddlUchiZeiShori").val().trim());
		// 集金有無
		if ($("#ddlShuukinUmu").val() != null) resultArray.push($("#ddlShuukinUmu").val().trim());
		// 現金集金マーク印字
		if ($("#ddlGenkinShuukinMark").val() != null) resultArray.push($("#ddlGenkinShuukinMark").val().trim());
		// 集計出力FLG
		if ($("#ddlShuukinOutputFlag").val() != null) resultArray.push($("#ddlShuukinOutputFlag").val().trim());
		// 手入力伝票発行
		if ($("#ddlManualInputBilling").val() != null) resultArray.push($("#ddlManualInputBilling").val().trim());
		// 手入力出荷伝票
		if ($("#ddlManualInputDelivery").val() != null) resultArray.push($("#ddlManualInputDelivery").val().trim());
		// 伝票行計算金額まるめ
		if ($("#ddlSlipLineCalculationAmountRounding").val() != null) resultArray.push($("#ddlSlipLineCalculationAmountRounding").val().trim());
		// 出荷伝票出力品コード
		if ($("#ddlDeliveryOutputProductCode").val() != null) resultArray.push($("#ddlDeliveryOutputProductCode").val().trim());
		// 請求先名称
		resultArray.push($("#txtBillingName").val().trim());
		// 請求先名称カナ
		resultArray.push($("#txtBillingNameKana").val().trim());
		// 請求先略称
		resultArray.push($("#txtBillingNameR").val().trim());
		// 請求先郵便番号
		resultArray.push($("#txtBillingZipCode1").val().trim());
		resultArray.push($("#txtBillingZipCode2").val().trim());
		// 住所１
		resultArray.push($("#txtBillingAddress1").val().trim());
		// 住所２
		resultArray.push($("#txtBillingAddress1").val().trim());
		// 電話番号
		resultArray.push($("#txtBillingTel1").val().trim());
		resultArray.push($("#txtBillingTel2").val().trim());
		resultArray.push($("#txtBillingTel3").val().trim());
		// FAX番号
		resultArray.push($("#txtBillingFax1").val().trim());
		resultArray.push($("#txtBillingFax2").val().trim());
		resultArray.push($("#txtBillingFax3").val().trim());
		// 請求単位
		if ($("#ddlBillingUnit").val() != null) resultArray.push($("#ddlBillingUnit").val().trim());
		// 請求単価
		if ($("#ddlBillingUnitPrice").val() != null) resultArray.push($("#ddlBillingUnitPrice").val().trim());
		// 請求書種類
		if ($("#ddlBillingType").val() != null) resultArray.push($("#ddlBillingType").val().trim());
		// 請求書パターン
		if ($("#ddlBillingPattern").val() != null) resultArray.push($("#ddlBillingPattern").val().trim());
		// 請求書住所印字
		if ($("#ddlBillingAddressPrint").val() != null) resultArray.push($("#ddlBillingAddressPrint").val().trim());
		// 請求集計表区分
		if ($("#ddlBillingShuukeiHyouKubun").val() != null) resultArray.push($("#ddlBillingShuukeiHyouKubun").val().trim());
		// 消費税計算単位
		if ($("#ddlBillingShouhizeiCalculationUnit").val() != null) resultArray.push($("#ddlBillingShouhizeiCalculationUnit").val().trim());
		// 請求チェックリスト 出力対象
		if ($("#ddlBillingCheckListOutputTarget").val() != null) resultArray.push($("#ddlBillingCheckListOutputTarget").val().trim());
		// 消費税端数処理
		if ($("#ddlBillingShouhizeiShori").val() != null) resultArray.push($("#ddlBillingShouhizeiShori").val().trim());
		// 請求チェックリスト 出力順
		if ($("#ddlBillingCheckListOutputOrder").val() != null) resultArray.push($("#ddlBillingCheckListOutputOrder").val().trim());
		// 取纏め請求
		if ($("#ddlBillingToriMatomeUMu").val() != null) resultArray.push($("#ddlBillingToriMatomeUMu").val().trim());
		// 取纏め請求事業所
		if ($("#ddlBillingToriMatomeJigyouSho").val() != null) resultArray.push($("#ddlBillingToriMatomeJigyouSho").val().trim());
		// 締日１
		resultArray.push($("#txtCloseDay1").val().trim());
		// 回収月区分１
		if ($("#ddlRecoveryMonthKubun1").val() != null) resultArray.push($("#ddlRecoveryMonthKubun1").val().trim());
		// 回収日１
		resultArray.push($("#txtRecoveryDay1").val().trim());
		// 締日２
		resultArray.push($("#txtCloseDay2").val().trim());
		// 回収月区分２
		if ($("#ddlRecoveryMonthKubun2").val() != null) resultArray.push($("#ddlRecoveryMonthKubun2").val().trim());
		// 回収日２
		resultArray.push($("#txtRecoveryDay2").val().trim());
		// 入金種別
		if ($("#ddlPaymentType").val() != null) resultArray.push($("#ddlPaymentType").val().trim());
		// 入金口座
		if ($("#ddlPaymentAccount").val() != null) resultArray.push($("#ddlPaymentAccount").val().trim());
     	// 手形サイト
		resultArray.push($("#txtNoteSite").val().trim());
		// 取引コード
		resultArray.push($("#txtTorihikiCode").val().trim());
		// 分類コード（定番、店直）
		resultArray.push($("#txtBunruiCodeTeibanShop").val().trim());
		// 伝票区分（定番、店直）
		resultArray.push($("#txtDenpyouKubunTeibanShop").val().trim());
		// 分類コード（定番、センター）
		resultArray.push($("#txtBunruiCodeTeibanCenter").val().trim());
		// 伝票区分（定番、センター）
		resultArray.push($("#txtCustomerCode").val().trim());
		// 分類コード（特売、店直）
		resultArray.push($("#txtBunruiCodeTokubaiShop").val().trim());
		// 伝票区分（特売、店直）
		resultArray.push($("#txtDenpyouKubunTokubaiShop").val().trim());
		// 分類コード（特売、センター）
		resultArray.push($("#txtBunruiCodeTokubaiCenter").val().trim());
		// 伝票区分（特売、センター）
		resultArray.push($("#txtDenpyouKubunTokubaiCenter").val().trim());
		// 受領データ突合区分
		if ($("#ddlJuryouDataKubun").val() != null) resultArray.push($("#ddlJuryouDataKubun").val().trim());
		// 請求データ区分
		if ($("#ddlBillingDataKubun").val() != null) resultArray.push($("#ddlBillingDataKubun").val().trim());
		// 修正データ突合区分
		if ($("#ddlModifyDataKubun").val() != null) resultArray.push($("#ddlModifyDataKubun").val().trim());
		// 請求先支払い案内データ区分
		if ($("#ddlBillingPaymentDataKubun").val() != null) resultArray.push($("#ddlBillingPaymentDataKubun").val().trim());
		// （修正種別）返品データ
		resultArray.push($("#chkModifyTypeShuuseiData").is(":checked") ? true : false);
		// （修正種別）欠品データ
		resultArray.push($("#chkModifyTypeKetsuhinData").is(":checked") ? true : false);
     	// （修正種別）修正データ
		resultArray.push($("#chkModifyTypeShuuseiData").is(":checked") ? true : false);
		// 請求データ配信ＩＤ
		resultArray.push($("#txtBillingDataDeliveryId").val().trim());
		// 集計コード１
		resultArray.push($("#txtShuukeiCode1").val().trim());
		// 集計コード２
		resultArray.push($("#txtShuukeiCode2").val().trim());
		// 使用中止日
		resultArray.push($("#txtShiyouChuushiDay").val().trim());
		// 状況コード
		resultArray.push($("#txtStatusCode").val().trim());

		return resultArray;
	}
});

/**
 * 請求先検索のCallback
 * 
 * @param data ： 取得されたデータ
 */
function getCom0102d02(data) {
	// 取得されたデータをフォームに設定する
	for (var i = 0; i < data.length; i++) {
		$("#txtBillingCode").val(data[i].custCode);
		$("#txtBillingSearchName").text(data[i].custNmR);
		$("#txtBillingSearchNameHidden").val(data[i].custNmR);
	}
	$("#txtCustomerName").focus();
	// 請求先情報を取得する
	billingCodeChangedProcess();
	reTurnCheckTab();
}

/**
 * チェーンコード検索のCallback
 * 
 * @param chaincd ： チェーンコード
 * @param chainEda ： チェーン枝番
 * @param chainMei ： チェーン名称
 */
function getCom0102d01(chaincd, chainEda, chainMei) {
	// 取得されたデータをフォームに設定する
	$("#txtChainCode").val(chaincd);
	$("#txtChainEda").val(chainEda);
	$("#txtChainName").text(chainMei);
	$("#txtChainNameHidden").val(chainMei);
	//  納品センター選択肢再設定する
	chainCodeManagerJigyoCodeChangedProcess();
	// 営業担当者コードにフォーカスする
	$("#txtCustomerName").focus();
	// エラークラスを削除する
	$("#txtChainCode").removeClass("form-error-field");
	$("#txtChainEda").removeClass("form-error-field");
	$("#txtMess").html("");
	reTurnCheckTab();
}

/**
 * 担当者コード検索のCallback
 * 
 * @param data ： 取得されたデータ
 */
function getCom0102d05(data) {
	// 取得されたデータをフォームに設定する
	var showDataCnt = data.length;
	$("#dialogStoreFinder").fadeOut(100);
	$("#overlay").fadeOut(100);
	// 取得されたデータをフォームに設定する
	for (var i = 0; i < showDataCnt; i++) {
		if (userCodeDialogDisplayFlg == "1") { // 営業担当者コードのダイアログの場合	
			$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(2).find("input").eq(0).val(data[i].userCode);
			$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(2).find("input").eq(1).val(data[i].userNm);
			$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(3).find("span").text(data[i].userNm);
		} else if (userCodeDialogDisplayFlg == "2") { // 事務当者コードのダイアログの場合
			$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(4).find("input").eq(0).val(data[i].userCode);
			$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(4).find("input").eq(1).val(data[i].userNm);
			$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(5).find("span").text(data[i].userNm);
		}
	}
	if (userCodeDialogDisplayFlg == "1") { // 営業担当者コードのダイアログの場合
		// 事務当者コードにフォーカスする
		$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(4).find("input").eq(0).focus();
		$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(2)
				.find("input").eq(0).removeClass("form-error-field");
		$("#txtMess").html("");
	} else if (userCodeDialogDisplayFlg == "2") { // 事務当者コードのダイアログの場合
		// 幹事事業所にフォーカスする
		$("#ddlKanjiJigyouSho").focus();
		$("#tbl_toriatsukai tbody").find("tr").eq(userCodeDialogSelectedIndex).find("td").eq(4)
				.find("input").eq(0).removeClass("form-error-field");
		$("#txtMess").html("");
	}
	// Tabインデックスを再設定する
	reTurnCheckTab();	
}

/**
 * 請求先情報を取得処理
 */
function billingCodeChangedProcess() {
	// 請求先コード.桁数 ≠ NULL の場合、請求先コードの不足桁をゼロ編集する
	if ($("#txtBillingCode").val() != null && $("#txtBillingCode").val().trim() != "") {
		var currentBillingCode = $("#txtBillingCode").val().trim();
		$("#txtBillingCode").val(addLeadingChar(currentBillingCode, 7));
	}

	// 入力チェック
	var viewMode = $("#viewMode").val().trim();
	if (viewMode == SHINKI_MODE || viewMode == TEISEI_MODE) { // 新規／訂正モード
		var errorID = chkItem($("#txtBillingCode").val(), false, TYPE_NUM_ALPHA, false, null);
		if (errorID != null) {
			$("#txtMess").html($("#" + errorID).val().replace("{1}", "請求先コード"));
			$("#txtBillingCode").addClass("form-error-field");
		} else {
			$("#txtMess").html("");
			$("#txtBillingCode").removeClass("form-error-field");
		}
	}

	// 請求先コード ≠ [変数]請求先コード(初期値) の場合、データ取得を行う
	var isBillingCodeValid = true;
	currentBillingCode = $("#txtBillingCode").val().trim();
	if (currentBillingCode == "" && currentBillingCode != txtBillingCodeValue) {
		// フラグ値を設定する
		isBillingCodeValid = false;
		// エラークリア
		$("#txtMess").html("");
	}
	if (currentBillingCode != "" && currentBillingCode != txtBillingCodeValue) {
		// 得意先マスタから検索条件に合致する請求先情報を取得する
		$.ajax({
			type: "POST",
			url: "getBillingData",
			data: { "billingCode": currentBillingCode },
			async: false,
			success: function(returnedJsonData) {
				// エラークリア
				$("#txtMess").html("");

				// データの存在をチェックする
				if (returnedJsonData != "") {
					// データの存在をチェックする
					if (returnedJsonData.bildCode.trim() != AJAX_GETDATA_ERROR) {
						// [変数]請求先情報格納クラスを画面に表示する
						$("#ddlKanjiJigyouSho").val(returnedJsonData.managerJigyoCode);
						$("#txtKanjiJigyouShoHidden").val(returnedJsonData.managerJigyoCode);
						$("#ddlCustomerType").val(returnedJsonData.custCls);
						$("#txtCustomerTypeHidden").val(returnedJsonData.custCls);
						$("#ddlTenpoKubun").val(returnedJsonData.shopKb);
						$("#txtTenpoKubunHidden").val(returnedJsonData.shopKb);
						$("#ddlGyoutaiKubun").val(returnedJsonData.gyotaiKb);
						$("#txtGyoutaiKubunHidden").val(returnedJsonData.gyotaiKb);
						if (returnedJsonData.deliCenterCode != null) {
							$("#ddlDeliveryCenter").val(returnedJsonData.deliCenterCode);
							$("#txtDeliveryCenterHidden").val(returnedJsonData.deliCenterCode);
						}
						$("#ddlKankeiKaishaType").val(returnedJsonData.relComCls);
						$("#txtKankeiKaishaTypeHidden").val(returnedJsonData.relComCls);
						if (returnedJsonData.relComSub != null) {
							$("#ddlHojoKamoku").val(returnedJsonData.relComSub);
							$("#txtHojoKamokuHidden").val(returnedJsonData.relComSub);
						}
						$("#ddlSaibanKubun").val(returnedJsonData.datidxKb);
						$("#txtSaibanKubunHidden").val(returnedJsonData.datidxKb);
						$("#ddlYgTorihikiKubun").val(returnedJsonData.ygKb);
						$("#txtYgTorihikiKubunHidden").val(returnedJsonData.ygKb);
						$("#ddlUchiZeiKoKyakuKubun").val(returnedJsonData.taxIncKb);
						$("#txtUchiZeiKoKyakuKubunHidden").val(returnedJsonData.taxIncKb);
						if (returnedJsonData.taxIncFrcKb != null) {
							$("#ddlUchiZeiShori").val(returnedJsonData.taxIncFrcKb);
							$("#txtUchiZeiShoriHidden").val(returnedJsonData.taxIncFrcKb);
						}
						$("#ddlShuukinUmu").val(returnedJsonData.colMonKb);
						$("#txtShuukinUmuHidden").val(returnedJsonData.colMonKb);
						$("#ddlGenkinShuukinMark").val(returnedJsonData.colMmrkKb);
						$("#txtGenkinShuukinMarkHidden").val(returnedJsonData.colMmrkKb);
						$("#ddlShuukinOutputFlag").val(returnedJsonData.sumsFlg);
						$("#txtShuukinOutputFlagHidden").val(returnedJsonData.sumsFlg);
						$("#ddlManualInputBilling").val(returnedJsonData.shipsKb);
						$("#txtManualInputBillingHidden").val(returnedJsonData.shipsKb);
						$("#ddlManualInputDelivery").val(returnedJsonData.shipsTypCls
								+ DELIMITER + returnedJsonData.shipsTypId);
						$("#txtManualInputDeliveryHidden").val(returnedJsonData.shipsTypCls
								+ DELIMITER + returnedJsonData.shipsTypId);
						$("#ddlSlipLineCalculationAmountRounding").val(returnedJsonData.shipsRudKb);
						$("#txtSlipLineCalculationAmountRoundingHidden").val(returnedJsonData.shipsRudKb);
						$("#ddlDeliveryOutputProductCode").val(returnedJsonData.shipsCodeKb);
						$("#txtDeliveryOutputProductCodeHidden").val(returnedJsonData.shipsCodeKb);

						// 画面制御
						$("#ddlKanjiJigyouSho").prop("disabled", true).removeClass("form-error-field");
						$("#ddlCustomerType").prop("disabled", true).removeClass("form-error-field");
						$("#ddlTenpoKubun").prop("disabled", true).removeClass("form-error-field");
						$("#ddlGyoutaiKubun").prop("disabled", true).removeClass("form-error-field");
						$("#ddlDeliveryCenter").prop("disabled", true).removeClass("form-error-field");
						$("#ddlKankeiKaishaType").prop("disabled", true).removeClass("form-error-field");
						$("#ddlHojoKamoku").prop("disabled", true).removeClass("form-error-field");
						$("#ddlSaibanKubun").prop("disabled", true).removeClass("form-error-field");
						$("#ddlYgTorihikiKubun").prop("disabled", true).removeClass("form-error-field");
						$("#ddlUchiZeiKoKyakuKubun").prop("disabled", true).removeClass("form-error-field");
						$("#ddlUchiZeiShori").prop("disabled", true).removeClass("form-error-field");
						$("#ddlShuukinUmu").prop("disabled", true).removeClass("form-error-field");
						$("#ddlGenkinShuukinMark").prop("disabled", true).removeClass("form-error-field");
						$("#ddlShuukinOutputFlag").prop("disabled", true).removeClass("form-error-field");
						$("#ddlManualInputBilling").prop("disabled", true).removeClass("form-error-field");
						$("#ddlManualInputDelivery").prop("disabled", true).removeClass("form-error-field");
						$("#ddlSlipLineCalculationAmountRounding").prop("disabled", true).removeClass("form-error-field");
						$("#ddlDeliveryOutputProductCode").prop("disabled", true).removeClass("form-error-field");
					} else {
						// エラーメッセージを表示する
						var messageContent = returnedJsonData.custCode;
						$("#txtMess").append(messageContent);
						// フラグ値を設定する
						isBillingCodeValid = false;
					}
				} else {
					// エラーメッセージを表示する
					var messageContent = EXCEPTION_ERROR;
					$("#txtMess").append(messageContent);
				}
			},
			error: function(e) { // データ取得はエラーが発生した場合
				// エラークリア
				$("#txtMess").html("");
				// エラーメッセージを表示する
				var messageContent = EXCEPTION_ERROR;
				$("#txtMess").append(messageContent);
			},
			complete: function(jqXHR, textStatus) {
				
			}
		});
	}
	// 不正な請求先コードの場合、画面制御を行う
	if (!isBillingCodeValid) {
		// [変数]請求先情報格納クラスを画面に表示する
		$("#ddlKanjiJigyouSho").val("");
		$("#txtKanjiJigyouShoHidden").val("");
		$("#ddlCustomerType").val("");
		$("#txtCustomerTypeHidden").val("");
		$("#ddlTenpoKubun").val("");
		$("#txtTenpoKubunHidden").val("");
		$("#ddlGyoutaiKubun").val("");
		$("#txtGyoutaiKubunHidden").val("");
		$("#ddlDeliveryCenter").val("");
		$("#txtDeliveryCenterHidden").val("");
		$("#ddlKankeiKaishaType").val("");
		$("#txtKankeiKaishaTypeHidden").val("");
		$("#ddlHojoKamoku").val("");
		$("#txtHojoKamokuHidden").val("");
		$("#ddlSaibanKubun").val("");
		$("#txtSaibanKubunHidden").val("");
		$("#ddlYgTorihikiKubun").val("");
		$("#txtYgTorihikiKubunHidden").val("");
		$("#ddlUchiZeiKoKyakuKubun").val("");
		$("#txtUchiZeiKoKyakuKubunHidden").val("");
		$("#ddlUchiZeiShori").val("");
		$("#txtUchiZeiShoriHidden").val("");
		$("#ddlShuukinUmu").val("");
		$("#txtShuukinUmuHidden").val("");
		$("#ddlGenkinShuukinMark").val("");
		$("#txtGenkinShuukinMarkHidden").val("");
		$("#ddlShuukinOutputFlag").val("");
		$("#txtShuukinOutputFlagHidden").val("");
		$("#ddlManualInputBilling").val("");
		$("#txtManualInputBillingHidden").val("");
		$("#ddlManualInputDelivery").val("");
		$("#txtManualInputDeliveryHidden").val("");
		$("#ddlSlipLineCalculationAmountRounding").val("");
		$("#txtSlipLineCalculationAmountRoundingHidden").val("");
		$("#ddlDeliveryOutputProductCode").val("");
		$("#txtDeliveryOutputProductCodeHidden").val("");

		// 画面制御
		$("#ddlKanjiJigyouSho").prop("disabled", false);
		$("#ddlCustomerType").prop("disabled", false);
		$("#ddlTenpoKubun").prop("disabled", false);
		$("#ddlGyoutaiKubun").prop("disabled", false);
		$("#ddlDeliveryCenter").prop("disabled", false);
		$("#ddlKankeiKaishaType").prop("disabled", false);
		$("#ddlHojoKamoku").prop("disabled", false);
		$("#ddlSaibanKubun").prop("disabled", false);
		$("#ddlYgTorihikiKubun").prop("disabled", false);
		$("#ddlUchiZeiKoKyakuKubun").prop("disabled", false);
		$("#ddlUchiZeiShori").prop("disabled", false);
		$("#ddlShuukinUmu").prop("disabled", false);
		$("#ddlGenkinShuukinMark").prop("disabled", false);
		$("#ddlShuukinOutputFlag").prop("disabled", false);
		$("#ddlManualInputBilling").prop("disabled", false);
		$("#ddlManualInputDelivery").prop("disabled", false);
		$("#ddlSlipLineCalculationAmountRounding").prop("disabled", false);
		$("#ddlDeliveryOutputProductCode").prop("disabled", false);
	}
}

/**
 * 納品センター選択肢再設定処理
 */
function chainCodeManagerJigyoCodeChangedProcess() {
	if (!$("#ddlKanjiJigyouSho").is(":disabled")) {
		var businessDate = $("#businessDate").val();
		var jigyoushoCode = $("#ddlKanjiJigyouSho").val();
		var chainCode = $("#txtChainCode").val();
		var chainEda = $("#txtChainEda").val();
		// 納品先マスタから検索条件に合致する納品先情報を取得する
		$.ajax({
			type: "POST",
			url: "getDeliveryData",
			data: { "businessDate": businessDate, "jigyoushoCode": jigyoushoCode, "chainCode": chainCode, "chainEda": chainEda },
			async: false,
			success: function(returnedJsonData) {
				// データの存在をチェックする
				if (returnedJsonData != "") { // データがある場合
					var nohinData = [];
					// 納品センターコンボボックスのオプションを作成する
					$("#ddlDeliveryCenter").html("").prop("disabled", false);
					for (var i = 0; i < returnedJsonData.length; i++) {
						nohinData.push("<option value='" + returnedJsonData[i].code.trim() + "'>"
								+ returnedJsonData[i].name.trim() + "</option>");
					}
					document.getElementById("ddlDeliveryCenter").innerHTML = nohinData.join('');
					$("#txtDeliveryCenterDataChanged").val("true");
				} else { // データが無い場合
					// 納品センターを非活性化する
					$("#ddlDeliveryCenter").html("").prop("disabled", true);
				}
			},
			error: function(e) { // データ取得はエラーが発生した場合
				// エラークリア
				$("#txtMess").html("");
				// エラーメッセージを表示する
				var messageContent = EXCEPTION_ERROR;
				$("#txtMess").append(messageContent);
			},
			complete: function(jqXHR, textStatus) {
				
			}
		});
	}
}