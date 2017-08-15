/**
 * ファイル名:mst0102d01.js
 * 概要:担当者マスタ一覧画面
 * 
 * 作成者:ABV）コアー
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00                  ABV）コアー        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

// 営業担当者コード／事務担当者コードのダイアログ表示フラグ
var userCodeDialogDisplayFlg = "1"; // 1: 営業担当者コード, 2: 事務担当者コード

jQuery(document).ready(function($) {
	/** jStorageの画面キー */
	var SCREEN_NAME = "MST0102D02";
	/** Controlエラーの受信デリミター */
	var DELIMITER = "##";
	/** 変数定義 */
	var isFromButtonSearch = false;
	var isDisableControl = true;
	var currentShowingColId = "";
	var isTableMenuShowing = false;
	var strSelectedUserCode = "";
	var strSelectedChainCode = "";
	var strSelectedChainEda = "";
	var txtChainCodeValue = "";
	var txtChainEdaValue = "";
	var txtEigyouTantoushaCode = "";
	var txtJimuTantoushaCode = "";
	// 一度に複数のボタンクリックを防止する
	var isClickButton = false;

	// Dialogのadd_clickイベントの作成
	loadDialogCom0102d01();	
	loadDialogCom0102d05();

	// 得意先一覧作成
	if ($("#txtMess").text().trim() == "") {
		searchData();
	}
	// ボタン、画面、フォーカス制御初期化
	initControlState();
	// jStorageのクリア
	clearStorage(SCREEN_NAME);

	/**
	 * 得意先コードからフォーカスが外れた場合の処理。入力値の不足桁をゼロ編集する（７桁）
	 */
	$("#txtCustomerCode").bind("focusout", function() {
		// 得意先コード.桁数 ≠ NULL の場合、得意先コードの不足桁をゼロ編集する
		if ($(this).val() != null && $(this).val().trim() != "") {
			var currentCustomerCode = $(this).val().trim();
			$(this).val(addLeadingChar(currentCustomerCode, 7));
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
		// チェーンコード ≠ [変数]チェーンコード(初期値) or チェーン枝番 ≠ [変数]チェーン枝番(初期値) の場合
		if ($(this).val().trim() != txtChainCodeValue || $("#txtChainEda").val().trim() != txtChainEdaValue) {
			// チェーン名称 = null
			$("#txtChainName").html("");
		}
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
		// チェーンコード ≠ [変数]チェーンコード(初期値) or チェーン枝番 ≠ [変数]チェーン枝番(初期値) の場合
		if ($("#txtChainCode").val().trim() != txtChainCodeValue || $(this).val().trim() != txtChainEdaValue) {
			// チェーン名称 = null
			$("#txtChainName").html("");
		}
	});

	/**
	 * チェーンコードの検索ボタンをクリックされた際の処理。チェーンコード検索画面を呼び出す
	 */
	$("#btnSearchChainCode").bind("click", function() {
		// 変数値の情報を取得する
		// チェーンコード
		var chainCode = $("#txtChainCode").val().trim();
		chainCode = replaceText(chainCode);
		// チェーン枝番
		var chainBranch = $("#txtChainEda").val().trim();
		chainBranch = replaceText(chainBranch);
		// ダイアログの表示
		showCom0102d01(chainCode, chainBranch);
	});

	/**
	 * 営業担当者コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$("#txtEigyouTantoushaCode").bind("focus", function() {
		// 営業担当者コード ≠ NULL の場合、営業担当者コードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			txtEigyouTantoushaCode = $(this).val().trim();
		}
	});

	/**
	 * 営業担当者からフォーカスが外れた場合の処理。自項目の初期化を行う
	 */
	$("#txtEigyouTantoushaCode").bind("focusout", function() {
		// 営業担当者コード ≠ [変数]営業担当者コード(初期値) の場合
		if ($(this).val().trim() != txtEigyouTantoushaCode) {
			// 営業担当者氏名 = null
			$("#txtEigyouTantoushaName").html("");
		}
	});

	/**
	 * 営業担当者コードの検索ボタンをクリックされた際の処理。営業担当者コード検索画面を呼び出す
	 */
	$("#btnSearchEigyouTantoushaCode").bind("click", function() {
		// システム管理者フラグを取得する
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		// ダイアログ表示フラグを設定する
		userCodeDialogDisplayFlg = "1"; // 営業担当者コード
		// 変数値の情報を取得する
		// 担当者コード
		var userCode = $("#txtEigyouTantoushaCode").val().trim();
		userCode = replaceText(userCode);
		// 利用権限コード
		var userRole = "";
		// 所属事業所コード
		var jigyoushoCode = "";
		// システム管理者フラグ ＝ '1'（システム管理者）の場合
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			jigyoushoCode = $("#ddlJigyouSho").val().trim();
		}
		// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
		else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
			jigyoushoCode = loginJigyouShoCode;
		}
		jigyoushoCode = replaceText(jigyoushoCode);
		showCom0102d05(userCode, userRole, jigyoushoCode);
	});

	/**
	 * 事務担当者コードにフォーカスが当たった場合の処理。入力値を退避する
	 */
	$("#txtJimuTantoushaCode").bind("focus", function() {
		// 事務担当者コード ≠ NULL の場合、事務担当者コードを退避する
		if ($(this).val() != null && $(this).val().trim() != "") {
			txtJimuTantoushaCode = $(this).val().trim();
		}
	});

	/**
	 * 事務担当者からフォーカスが外れた場合の処理。自項目の初期化を行う
	 */
	$("#txtJimuTantoushaCode").bind("focusout", function() {
		// 事務担当者コード ≠ [変数]事務担当者コード(初期値) の場合
		if ($(this).val().trim() != txtJimuTantoushaCode) {
			// 事務担当者氏名 = null
			$("#txtJimuTantoushaName").html("");
		}
	});

	/**
	 * 事務担当者コードの検索ボタンをクリックされた際の処理。事務担当者コード検索画面を呼び出す
	 */
	$("#btnSearchJimuTantoushaCode").bind("click", function() {
		// システム管理者フラグを取得する
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		// ダイアログ表示フラグを設定する
		userCodeDialogDisplayFlg = "2"; // 営業担当者コード
		// 変数値の情報を取得する
		// 担当者コード
		var userCode = $("#txtJimuTantoushaCode").val().trim();
		userCode = replaceText(userCode);
		// 利用権限コード
		var userRole = "";
		// 所属事業所コード
		var jigyoushoCode = "";
		// システム管理者フラグ ＝ '1'（システム管理者）の場合
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			jigyoushoCode = $("#ddlJigyouSho").val().trim();
		}
		// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
		else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
			jigyoushoCode = loginJigyouShoCode;
		}
		jigyoushoCode = replaceText(jigyoushoCode);
		showCom0102d05(userCode, userRole, jigyoushoCode);
	});

	/**
	 * 検索ボタンをクリックされた時の処理。検索条件に該当するマスタデータを一覧に表示する
	 */
	$("#btnSearch").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnSearch");
		// 変数定義
		var jigyouShoCode = "";
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		// 入力チェックを行う
		if (!checkInput()) {
			// システム管理者フラグ ＝ '1'（システム管理者）の場合
			if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
				jigyouShoCode = $("#ddlJigyouSho").val().trim();
			}
			// システム管理者フラグ ＝ '0'（一般ユーザ）の場合
			else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
				var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
				jigyouShoCode = loginJigyouShoCode;
			}
			// 検索関数を呼出して、得意先一覧テーブルを作成する
			isFromButtonSearch = true;
			searchData();
		} else {
			// ボタンを押下可能
			isClickButton = false;
			// ボタンを有効にする
			enableButton("btnSearch");
		}
	});

	/**
	 * 条件クリアボタンをクリックされた時の処理。検索条件エリア画面の初期化を行う
	 */
	$("#btnConditionClear").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnConditionClear");
		// 検索条件入力エリアを初期化する
		initSearchConditionInput();
		// ボタンを押下可能
		isClickButton = false;
		// ボタンを有効にする
		enableButton("btnConditionClear");
	});

	/**
	 * 新規ボタンをクリックされた時の処理。新規登録モードにて得意先登録画面を呼び出す
	 */
	$("#btnNew").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnNew");
		submitForm(SHINKI_MODE, "");
	});

	/**
	 * CSVボタンをクリックされた時の処理。一覧に表示されているマスタデータをCSVファイルに出力する
	 */
	$("#btnCsv").bind("click", function() {
		isClickButton = true;
		// ボタンを無効にする
		disableButton("btnCsv");
		// 確認メッセージを画面に表示する
		jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
			if (retVal) {
				// 入力チェックを行う
				if (!checkInput()) {
					// 画面のデータを取得する
					var jigyouShoCode = $("#ddlJigyouSho").val().trim();
					var chainCode = $("#txtChainCode").val().trim();
					var chainEda = $("#txtChainEda").val().trim();
					var customerCode = $("#txtCustomerCode").val().trim();
					var customerName = $("#txtCustomerName").val().trim();
					var eigyouTantoushaCode = $("#txtEigyouTantoushaCode").val().trim();
					var jimuTantoushaCode = $("#txtJimuTantoushaCode").val().trim();
					var customerType = $("#ddlCustomerType").val().trim();
					var uchizeiKokyakuKubun = $("#ddlUchiZeiKoKyakuKubun").val().trim();
					var chkCancelData = $("#chkCancelData").is(":checked") ? true : false;
					var chkCustomer = $("#chkCustomer").is(":checked") ? true : false;
					var chkBilling = $("#chkBilling").is(":checked") ? true : false;
					var chkCustomerBilling = $("#chkCustomerBilling").is(":checked") ? true : false;
					var businessDate = $("#businessDate").val().trim();
					var sysAdminFlag = $("#sysAdminFlag").val().trim();
					var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
					var form0102d01Obj = {
							ddlJigyouSho: jigyouShoCode,
							txtCustomerCode: customerCode,
							txtCustomerName: customerName,
							txtChainCode: chainCode,
							txtChainEda: chainEda,
							txtEigyouTantoushaCode: eigyouTantoushaCode,
							txtJimuTantoushaCode: jimuTantoushaCode,
							ddlCustomerType: customerType,
							ddlUchiZeiKoKyakuKubun: uchizeiKokyakuKubun,
							chkCancelData: chkCancelData,
							chkCustomer: chkCustomer,
							chkBilling: chkBilling,
							chkCustomerBilling: chkCustomerBilling,
							businessDate: businessDate,
							sysAdminFlag: sysAdminFlag,
							loginJigyouShoCode: loginJigyouShoCode
					};
					// データ出力開始
					$.ajax({
						type : "POST",
						url : "exportCsv",
						contentType: 'application/json',
						data: JSON.stringify(form0102d01Obj),
						async: false,
						success : function(returnedJsonData) {
							if (returnedJsonData != "") {
								// エラークリア
								$("#txtMess").html("");
								// データの存在をチェックする
								if (returnedJsonData.searchResult != AJAX_GETDATA_ERROR
										&& returnedJsonData.searchResult != AJAX_INPUTCHECK_ERROR) {
									// CSVダウンロード
									var rootPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));								
									var path = rootPath + returnedJsonData.csvPath;
									window.open(path);
									// 画面の項目を更新する
									// チェーンコード
									if (returnedJsonData.chainCode != null) $("#txtChainCode").val(returnedJsonData.chainCode);
									// チェーン枝番
									if (returnedJsonData.chainEda != null) $("#txtChainEda").val(returnedJsonData.chainEda);
									// チェーン名称
									if (returnedJsonData.chainName != null) $("#txtChainName").html(returnedJsonData.chainName);
									// 営業担当者コード
									if (returnedJsonData.eigyouTantoushaCode != null) $("#txtEigyouTantoushaCode").val(returnedJsonData.eigyouTantoushaCode);
									// 営業担当者名称
									if (returnedJsonData.eigyouTantoushaName != null) $("#txtEigyouTantoushaName").html(returnedJsonData.eigyouTantoushaName);
									// 事務担当者コード
									if (returnedJsonData.jimuTantoushaCode != null) $("#txtJimuTantoushaCode").val(returnedJsonData.jimuTantoushaCode);
									// 事務担当者名称
									if (returnedJsonData.jimuTantoushaName != null) $("#txtJimuTantoushaName").html(returnedJsonData.jimuTantoushaName);
								} else {
									// 編集されたデータを画面に設定する
									$("#txtChainCode").val(returnedJsonData.chainCode);
									$("#txtChainEda").val(returnedJsonData.chainEda);
									if (returnedJsonData.chainName != null) $("#txtChainName").html(returnedJsonData.chainName);
									$("#txtEigyouTantoushaCode").val(returnedJsonData.eigyouTantoushaCode);
									if (returnedJsonData.eigyouTantoushaName != null) $("#txtEigyouTantoushaName").html(returnedJsonData.eigyouTantoushaName);
									$("#txtJimuTantoushaCode").val(returnedJsonData.jimuTantoushaCode);
									if (returnedJsonData.jimuTantoushaName != null) $("#txtJimuTantoushaName").html(returnedJsonData.jimuTantoushaName);
									if (returnedJsonData.searchResult == AJAX_INPUTCHECK_ERROR) {
										// 入力チェックエラー
										var lstError = returnedJsonData.errorIdString.split(DELIMITER);
										addClassErrorToControl(lstError);
										// 最上位の項目（タブ順が最も若い項目）にフォーカスをセットする
										$(".form-error-field").eq(0).focus().select();
									} else {
										// エラーメッセージを表示する
										var messageContent = returnedJsonData.errorMessage;
										$("#txtMess").html(messageContent);
									}
								}
							} else {
								
							}
						},
						error : function(e) { // データ取得はエラーが発生した場合
							// エラークリア
							$("#txtMess").html("");
							// エラーメッセージを表示する
							var messageContent = EXCEPTION_ERROR;
							$("#txtMess").append(messageContent);
						},
						complete : function(jqXHR, textStatus) {
							// ボタンを押下可能
							isClickButton = false;
							// ボタンを有効にする
							enableButton("btnCsv");
						}
					});
				} else {
					// ボタンを押下可能
					isClickButton = false;
					// ボタンを有効にする
					enableButton("btnCsv");
				}
			} else {
				// ボタンを有効にする
				enableButton("btnCsv");
			}
		});
	});

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
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
		var currentJigyouShoCode = $(this).parent().find("td").eq(6).attr("name").trim();
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
		strSelectedUserCode = $(parentRow).find("td").eq(4).text();
		strSelectedChainCode = $(parentRow).find("td").eq(1).text();
		strSelectedChainEda = $(parentRow).find("td").eq(2).text();
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
		$(this).css("background-color", "#FFFFFF").css("color", "#003366");
	});
	/**
	 * 編集メニューの項目をクリックされた時の処理
	 */
	$(".sub-menu a").bind("click", function() {
		if ($(this).attr("id") == "view") {
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
	 * 検索条件入力エリアを初期化する
	 */
	function initSearchConditionInput() {
		$("#txtChainCode").val("");
		$("#txtChainEda").val("");
		$("#txtChainName").html("");
		// システム管理者フラグ ＝ '1'(システム管理者)の場合、
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
			$("#ddlJigyouSho").val(loginJigyouShoCode);
		}
		$("#txtCustomerCode").val("");
		$("#txtCustomerName").val("");
		$("#txtEigyouTantoushaCode").val("");
		$("#txtEigyouTantoushaName").html("");
		$("#txtJimuTantoushaCode").val("");
		$("#txtJimuTantoushaName").html("");
		$("#ddlCustomerType").prop("selectedIndex", 0);
		$("#ddlUchiZeiKoKyakuKubun").prop("selectedIndex", 0);
		$("#chkCancelData").prop("checked", false);
		$("#chkCustomer").prop("checked", true);
		$("#chkBilling").prop("checked", true);
		$("#chkCustomerBilling").prop("checked", true);
		// フォーカス制御
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			// 事業所へフォーカスをセットする
			$("#ddlJigyouSho").focus();
		} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			// チェーンコードへフォーカスをセットする
			$("#txtCustomerCode").focus();
		}
		// エラーコントロールクリア
		$("#txtMess").html("");
		$(".form-error-field").removeClass("form-error-field");
	}

	/**
	 * 得意先一覧情報を取得する
	 */
	function searchData() {
		// 画面のデータを取得する
		var searchMax = $("#searchMax").val().trim();
		var jigyouShoCode = $("#ddlJigyouSho").val() != null ? $("#ddlJigyouSho").val().trim() : "";
		var chainCode = $("#txtChainCode").val().trim();
		var chainEda = $("#txtChainEda").val().trim();
		var customerCode = $("#txtCustomerCode").val().trim();
		var customerName = $("#txtCustomerName").val().trim();
		var eigyouTantoushaCode = $("#txtEigyouTantoushaCode").val().trim();
		var jimuTantoushaCode = $("#txtJimuTantoushaCode").val().trim();
		var customerType = $("#ddlCustomerType").val() != null ? $("#ddlCustomerType").val().trim() : "";
		var uchizeiKokyakuKubun = $("#ddlUchiZeiKoKyakuKubun").val() != null ? $("#ddlUchiZeiKoKyakuKubun").val().trim() : "";
		var chkCancelData = $("#chkCancelData").is(":checked") ? true : false;
		var chkCustomer = $("#chkCustomer").is(":checked") ? true : false;
		var chkBilling = $("#chkBilling").is(":checked") ? true : false;
		var chkCustomerBilling = $("#chkCustomerBilling").is(":checked") ? true : false;
		var businessDate = $("#businessDate").val().trim();
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		var loginJigyouShoCode = $("#loginJigyouShoCode").val().trim();
		var form0102d01Obj = {
				searchMax: searchMax,
				ddlJigyouSho: jigyouShoCode,
				txtCustomerCode: customerCode,
				txtCustomerName: customerName,
				txtChainCode: chainCode,
				txtChainEda: chainEda,
				txtEigyouTantoushaCode: eigyouTantoushaCode,
				txtJimuTantoushaCode: jimuTantoushaCode,
				ddlCustomerType: customerType,
				ddlUchiZeiKoKyakuKubun: uchizeiKokyakuKubun,
				chkCancelData: chkCancelData,
				chkCustomer: chkCustomer,
				chkBilling: chkBilling,
				chkCustomerBilling: chkCustomerBilling,
				businessDate: businessDate,
				sysAdminFlag: sysAdminFlag,
				loginJigyouShoCode: loginJigyouShoCode
		};
		// データ取得開始
		$.ajax({
			type: "POST",
			url: isFromButtonSearch ? "searchCustomerData" : "getCustomerList",
			contentType: 'application/json',
			data: JSON.stringify(form0102d01Obj),
			async: false,
			success: function(returnedJsonData) {
				// 得意先一覧をクリアする
				$("#tbl_tbody").html("");
				// エラークリア
				$("#txtMess").html("");

				// データの存在をチェックする
				if (returnedJsonData != "") {
					// データの存在をチェックする
					if (returnedJsonData[0].searchResult != AJAX_GETDATA_ERROR
							&& returnedJsonData[0].searchResult != AJAX_INPUTCHECK_ERROR) {
						// 編集された値をフォームにセットする
						if (isFromButtonSearch) {
							$("#txtChainCode").val(returnedJsonData[returnedJsonData.length - 1].chainCode);
							$("#txtChainEda").val(returnedJsonData[returnedJsonData.length - 1].chainEda);
							if (returnedJsonData[returnedJsonData.length - 1].chainName != null) {
								$("#txtChainName").html(returnedJsonData[returnedJsonData.length - 1].chainName);
							}
							$("#txtEigyouTantoushaCode").val(returnedJsonData[returnedJsonData.length - 1].eigyouTantoushaCode);
							if (returnedJsonData[returnedJsonData.length - 1].eigyouTantoushaName != null) {
								$("#txtEigyouTantoushaName").html(returnedJsonData[returnedJsonData.length - 1].eigyouTantoushaName);
							}
							$("#txtJimuTantoushaCode").val(returnedJsonData[returnedJsonData.length - 1].jimuTantoushaCode);
							if (returnedJsonData[returnedJsonData.length - 1].jimuTantoushaName != null) {
								$("#txtJimuTantoushaName").html(returnedJsonData[returnedJsonData.length - 1].jimuTantoushaName);
							}
						}
						$("#haitaDate").val(returnedJsonData[returnedJsonData.length - 1].haitaDate);
						$("#haitaTime").val(returnedJsonData[returnedJsonData.length - 1].haitaTime);
						var searchMaxMessage = returnedJsonData[returnedJsonData.length - 1].errorMessage;

						// 得意先一覧の作成を開始する
						var tableBody = [];
						var searchMax = parseInt($("#searchMax").val());
						var dataLength = returnedJsonData.length - 1;
						if (searchMax <= dataLength) {
							dataLength = searchMax;
						}
						for (var i = 0; i < dataLength; i++) {
							// 背景色判定
							if (returnedJsonData[i].stsCode == "9") {
								// 背景色 ＝ 取消色
								tableBody.push("<tr class='tbl_del'>");
							} else {
								tableBody.push("<tr>");
							}
							tableBody.push("<td id='" + (i + 1) + "' class='align_center contextMenu'>" + (i + 1) + "</td>");
							// チェーンコード
							tableBody.push("<td class='align_center'>" + (returnedJsonData[i].chainCode != null ? returnedJsonData[i].chainCode : "") + "</td>");
							// チェーン枝番
							tableBody.push("<td class='align_center'>" + (returnedJsonData[i].chainEda != null ? returnedJsonData[i].chainEda : "") + "</td>");
							// チェーン名
							tableBody.push("<td>" + (returnedJsonData[i].chainName != null ? returnedJsonData[i].chainName : "") + "</td>");
							// 得意先コード
							tableBody.push("<td class='align_center'>" + (returnedJsonData[i].customerCode != null ? returnedJsonData[i].customerCode : "") + "</td>");
							// 得意先略称
							tableBody.push("<td>" + (returnedJsonData[i].customerName != null ? returnedJsonData[i].customerName : "") + "</td>");
							// 事業所名
							tableBody.push("<td name='" + returnedJsonData[i].jigyouShoCode + "'>"
									+ (returnedJsonData[i].jigyouShoName != null ? returnedJsonData[i].jigyouShoName : "") + "</td>");
							// 営業担当者コード
							tableBody.push("<td>" + (returnedJsonData[i].eigyouTantoushaCode != null ? returnedJsonData[i].eigyouTantoushaCode : "") + "</td>");
							// 営業担当者氏名
							tableBody.push("<td>" + (returnedJsonData[i].eigyouTantoushaName != null ? returnedJsonData[i].eigyouTantoushaName : "") + "</td>");
							// 事務担当者コード
							tableBody.push("<td>" + (returnedJsonData[i].jimuTantoushaCode != null ? returnedJsonData[i].jimuTantoushaCode : "") + "</td>");
							// 事務担当者氏名
							tableBody.push("<td>" + (returnedJsonData[i].jimuTantoushaName != null ? returnedJsonData[i].jimuTantoushaName : "") + "</td>");
							// 得意先種別名称
							tableBody.push("<td class='align_center'>" + (returnedJsonData[i].customerType != null ? returnedJsonData[i].customerType : "") + "</td>");
							// 内税顧客区分名称
							tableBody.push("<td class='align_center'>" + (returnedJsonData[i].uchizeiKokyakuKubun != null ? returnedJsonData[i].uchizeiKokyakuKubun : "") + "</td>");
							tableBody.push("</tr>");
						}
						document.getElementById("tbl_tbody").innerHTML = tableBody.join('');
						// 一番上に移動する
						$("#divBody").scrollTop(0);
						resetTableSize();
						// 最大検索件数チェック
						if (searchMaxMessage != null && searchMaxMessage.trim() != "") {
							isDisableControl = false;
							$("#txtMess").html(searchMaxMessage);
						}
					} else {
						// 編集されたデータを画面に設定する
						if (isFromButtonSearch) {
							$("#txtChainCode").val(returnedJsonData[0].chainCode);
							$("#txtChainEda").val(returnedJsonData[0].chainEda);
							if (returnedJsonData[0].chainName != null) {
								$("#txtChainName").html(returnedJsonData[0].chainName);
							}
							$("#txtEigyouTantoushaCode").val(returnedJsonData[0].eigyouTantoushaCode);
							if (returnedJsonData[0].eigyouTantoushaName != null) {
								$("#txtEigyouTantoushaName").html(returnedJsonData[0].eigyouTantoushaName);
							}
							$("#txtJimuTantoushaCode").val(returnedJsonData[0].jimuTantoushaCode);
							if (returnedJsonData[0].jimuTantoushaName != null) {
								$("#txtJimuTantoushaName").html(returnedJsonData[0].jimuTantoushaName);
							}
						}
						if (returnedJsonData[0].searchResult == AJAX_INPUTCHECK_ERROR) {
							// 入力チェックエラー
							var lstError = returnedJsonData[0].errorIdString.split(DELIMITER);
							addClassErrorToControl(lstError);
							// 最上位の項目（タブ順が最も若い項目）にフォーカスをセットする
							$(".form-error-field").eq(0).focus().select();
						} else {
							// データが無いエラー
							if (isFromButtonSearch) {
								// エラーメッセージを表示する
								var messageContent = returnedJsonData[0].errorMessage;
								$("#tbl_tbody").html(messageContent);
							}
						}
					}
					// ボタン制御
					enableButton("btnSearch");
					enableButton("btnConditionClear");
					enableButton("btnNew");
					if (returnedJsonData.length - 1 == 0) {
						// CSVボタンを無効にする
						disableButton("btnCsv");
					} else if (returnedJsonData.length > 0) {
						// CSVボタンを有効にする
						enableButton("btnCsv");
					}
				} else {
					// エラーメッセージを表示する
					var messageContent = EXCEPTION_ERROR;
					$("#txtMess").html(messageContent);
				}
			},
			error: function(e) { // データ取得はエラーが発生した場合
				// 得意先一覧をクリアする
				$("#tbl_tbody").html("");
				// エラークリア
				$("#txtMess").html("");
				// エラーメッセージを表示する
				var messageContent = EXCEPTION_ERROR;
				$("#txtMess").html(messageContent);
			},
			complete: function(jqXHR, textStatus) {
				// ボタンを押下可能
				isClickButton = false;
				// ボタンを有効にする
				enableButton("btnSearch");
			}
		});
	}

	/**
	 * エンターキー押下時の遷移
	 */
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
			EnterKey(e, this, ".condition_cont");
		}
	});

	/**
	 * ボタン、画面、フォーカス制御初期化
	 */
	function initControlState() {
		// ボタン制御
		enableButton("btnSearch");
		enableButton("btnConditionClear");
		enableButton("btnNew");
		if ($("#tblBody").find("tr").length == 0) {
			// CSVボタンを無効にする
			disableButton("btnCsv");
		} else if ($("#tblBody").find("tr").length > 0) {
			// CSVボタンを有効にする
			enableButton("btnCsv");
		}

		// 画面制御
		var sysAdminFlag = $("#sysAdminFlag").val().trim();
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			// 事業所を表示する
			$("#tblDataCondition tbody").find("tr").eq(0).removeClass("display_none");
		} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			// 事業所を非表示する
			$("#tblDataCondition tbody").find("tr").eq(0).addClass("display_none");
		}

		// フォーカス制御
		if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			// 事業所へフォーカスをセットする
			$("#ddlJigyouSho").focus();
		} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
			// チェーンコードへフォーカスをセットする
			$("#txtChainCode").focus();
		}

		// エラー時のコントロール制御
		if ($("#txtMess").text().trim() != "") {
			if (isDisableControl) {
				errorControl();
			}
		}
	}

	/**
	 * 全項目入力チェック処理
	 */
	function checkInput() {
		var errorMessage = "";
		var inputCheckError = false;
		// 得意先コード
		var errorID = chkItem($("#txtCustomerCode").val().trim(), false, TYPE_NUM, false, null);
		if (errorID != null) {
			errorMessage += $("#" + errorID).val().replace("{1}", "得意先コード") + "<br/>";
			$("#txtCustomerCode").addClass("form-error-field");
			inputCheckError = true;
		} else {
			$("#txtCustomerCode").removeClass("form-error-field");
		}
		// 得意先名称
		errorID = chkItem($("#txtCustomerName").val().trim(), false, TYPE_EM, false, null);
		if (errorID != null) {
			errorMessage += $("#" + errorID).val().replace("{1}", "得意先名称") + "<br/>";
			$("#txtCustomerName").addClass("form-error-field");
			inputCheckError = true;
		} else {
			$("#txtCustomerName").removeClass("form-error-field");
		}
		// チェーンコード
		errorID = chkItem($("#txtChainCode").val().trim(), false, TYPE_NUM, false, null);
		if (errorID != null) {
			errorMessage += $("#" + errorID).val().replace("{1}", "チェーンコード") + "<br/>";
			$("#txtChainCode").addClass("form-error-field");
			inputCheckError = true;
		} else {
			$("#txtChainCode").removeClass("form-error-field");
		}
		// チェーン枝番
		errorID = chkItem($("#txtChainEda").val().trim(), false, TYPE_NUM, false, null);
		if (errorID != null) {
			errorMessage += $("#" + errorID).val().replace("{1}", "チェーン枝番") + "<br/>";
			$("#txtChainEda").addClass("form-error-field");
			inputCheckError = true;
		} else {
			$("#txtChainEda").removeClass("form-error-field");
		}
		// 営業担当者コード
		errorID = chkItem($("#txtEigyouTantoushaCode").val().trim(), false, TYPE_NUM, false, null);
		if (errorID != null) {
			errorMessage += $("#" + errorID).val().replace("{1}", "営業担当者コード") + "<br/>";
			$("#txtEigyouTantoushaCode").addClass("form-error-field");
			inputCheckError = true;
		} else {
			$("#txtEigyouTantoushaCode").removeClass("form-error-field");
		}
		// 事務担当者コード
		errorID = chkItem($("#txtJimuTantoushaCode").val().trim(), false, TYPE_NUM, false, null);
		if (errorID != null) {
			errorMessage += $("#" + errorID).val().replace("{1}", "事務担当者コード") + "<br/>";
			$("#txtJimuTantoushaCode").addClass("form-error-field");
			inputCheckError = true;
		} else {
			$("#txtJimuTantoushaCode").removeClass("form-error-field");
		}
		// 検索対象条件
		// 得意先を対象とする　＝　チェックなし and 請求先を対象とする　＝　チェックなし　and　得意先かつ請求先を対象とする　＝　チェックなし の場合エラーとする
		if (!$("#chkCustomer").is(":checked") && !$("#chkBilling").is(":checked") && !$("#chkCustomerBilling").is(":checked")) {
			var messageContent = $("#COM008-E").val().trim().replace("{1}", "検索対象条件") + "<br/>";
			errorMessage += messageContent;
			inputCheckError = true;
		}

		if (inputCheckError) {
			// エラーメッセージを表示する
			$("#txtMess").html(errorMessage);
			// 最上位の項目（タブ順が最も若い項目）にフォーカスをセットする
			$(".form-error-field").eq(0).focus().select();
		}
		return inputCheckError;
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
			$("#selectUserCode").val(strSelectedUserCode);
			$("#selectChainCode").val(strSelectedChainCode);
			$("#selectChainEda").val(strSelectedChainEda);
		} else if (strMode == TEISEI_MODE) { // 訂正モード
			$("#viewMode").val(TEISEI_MODE);
			$("#selectUserCode").val(strSelectedUserCode);
			$("#selectChainCode").val(strSelectedChainCode);
			$("#selectChainEda").val(strSelectedChainEda);
		} else if (strMode == TORIKESI_MODE) { // 取消モード
			$("#viewMode").val(TORIKESI_MODE);
			$("#selectUserCode").val(strSelectedUserCode);
			$("#selectChainCode").val(strSelectedChainCode);
			$("#selectChainEda").val(strSelectedChainEda);
		}
		$("#formMst0102d01").submit();
	}
});

/**
 * 以下のdialogの処理
 */
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
	// 営業担当者コードにフォーカスする
	$("#txtEigyouTantoushaCode").focus();
	reTurnCheckTab();
}

/**
 * 担当者コード検索のCallback
 * 
 * @param data ： 取得されたデータ
 */
function getCom0102d05(data) {
	var showDataCnt = data.length;
	// 取得されたデータをフォームに設定する
	for (var i = 0; i < showDataCnt; i++) {
		if (userCodeDialogDisplayFlg == "1") { // 営業担当者コードのダイアログの場合
			$("#txtEigyouTantoushaCode").val(data[i].userCode);
			$("#txtEigyouTantoushaName").text(data[i].userNm);
		} else if (userCodeDialogDisplayFlg == "2") { // 事務当者コードのダイアログの場合
			$("#txtJimuTantoushaCode").val(data[i].userCode);
			$("#txtJimuTantoushaName").text(data[i].userNm);
		}
	}
	if (userCodeDialogDisplayFlg == "1") { // 営業担当者コードのダイアログの場合
		// 事務当者コードにフォーカスする
		$("#txtJimuTantoushaCode").focus();
	} else if (userCodeDialogDisplayFlg == "2") { // 事務当者コードのダイアログの場合
		// 得意先種別にフォーカスする
		$("#ddlCustomerType").focus();
	}
	reTurnCheckTab();	
}