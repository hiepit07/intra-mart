/**
 * ファイル名:uri0103d01.js 概要:再請求売上登録画面
 * 
 * 作成者:AB）前田 作成日:2015/12/10
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 AB）前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
var loginJigyoCode = ""; // ログイン事業所コード
var loginJigyoName = ""; // ログイン事業所名
var loginUserCode = ""; // ログイン担当者コード
var loginUserName = ""; // ログイン担当者名
var loginUserNameKana = ""; // ログイン担当者名カナ
var updUserName = ""; // 更新担当者名

var maxRow = 0; // 伝票行数
var initValue = []; // 品目明細情報初期値

var OPERATE_REGIST = "1"; // 処理区分：新規登録
var OPERATE_MODIFY = "2"; // 処理区分：訂正
var OPERATE_CANCEL = "3"; // 処理区分：取消
var OPERATE_VIEW = "4"; // 処理区分：照会
var operateMode = OPERATE_REGIST;

var CLS_ERROR_FIELD = "form-error-field"; // エラー発生クラス
var strErrorMessage = ""; // エラーメッセージ

var flgPriceMaxError = false; // 金額上限エラーフラグ
var flgClassCode = false; // 分類コードフラグ
var classCodeHead = ""; // 分類コードヘッダ
var classCodeItem = ""; // 分類コード品目価格情報

var isTableMenuShowing = false; // 削除メニュー表示フラグ
var currentShowingColId = ""; // 削除メニュー表示ID

var jigyoCodeWK = ""; // 事業所コードWK
var customerCodeWK = ""; // 得意先コードWK
var shopCodeWK = ""; // 店舗コードWK
var itemCodeWK = ""; // 自社品目コードWK
var customerItemCodeWK = "" // 得意先品目コードWK
var accountDayWK = "32"; // 計上日WK（初期値：32）

var flgAccount = false; // 計上日付発生フラグ
var flgShift = false; // Shiftキー押下フラグ
var flgErrorEx = false;
var flgEntry = true;
var deliDateFocus = ""; // 納品日Focus対象
var itemSearchStatus = -1; // -1：未検索、0：自社検索中、1：得意先検索中
var listInputStatus = -1; // -1：未入力、0：受注数量（ケース）、1：受注数量（バラ）、2：納品単価、3：販売単価

var mstCustomer = null; // 得意先マスタ
var mstShop = null; // 添付マスタ
var mstCourse = null; // コースマスタ
var mstItemDetail = null; // 品目明細情報
var mstSnohin = null; // 納品マスタ
var mstSchain = null; // チェーンマスタ
var uriHead = null; // 売上ヘッダ
var uriBodyList = null; // 売上明細リスト
var uriOriginHead = null; // 売上ヘッダ（返品／欠品元）
var uriOriginBodyList = null; // 売上明細（返品／欠品元）
var lstModify = null; // 修正区分
var selectedRow = 0; // 選択行番号

var listName = ""; // 伝票名称
var listLine = ""; // 伝票行数

var strContextPath = ""; // コンテキストパス
var btnSearchEnabled = ""; // 検索ボタン（活性化）
var btnSearchDisabled = ""; // 検索ボタン（非活性化）
var screenStatus = ""; // 画面表示状態

/** 画面表示状態 */
var CONST_SCREEN_HEAD = 0; // ヘッダ部のみ
var CONST_SCREEN_BODY1 = 1; // ボディ部（上段）
var CONST_SCREEN_BODY2 = 2; // ボディ部（下段）
var CONST_SCREEN_LIST = 3; // リスト部
var CONST_SCREEN_SEARCH = 4; // 検索結果

var cancelMode = -1; // 取消モード

var arrayItem = []; // 品目明細情報（配列）

jQuery(document)
		.ready(
				function($) {
					// 画面色の書換え
					/* もう１ランク、濃いメの緑は「#76933c」 */
					$("body").css('background-color', '#ebf1de');
					$(".title").css('background-color', '#d8e4bc');
					$(".title_cont").css('background-color', '#c4d79b');

					// 請求先選択ダイアログload
					loadDialogUriBillSource();

					screenStatus = CONST_SCREEN_BODY1;

					loginJigyoCode = $("#txtJigyoCode").val(); // ログイン事業所コード
					loginJigyoName = $("#lblJigyoNm").html(); // ログイン事業所名
					loginUserCode = $("#lblRegistUserCode").html(); // ログイン担当者コード
					loginUserName = $("#lblRegistUserName").html(); // ログイン担当者名
					loginUserNameKana = $("#hdnRegistUserNameKana").val(); // ログイン担当者名カナ

					// エラー表示域初期化
					removeInfoClass();

					if ($("#txtMess").html().trim() == "") {
						// 処理区分 = 新規登録
						$("#rad_New").prop("checked", true);

						// 画面表示制御
						showScreen(operateMode);

						// ボタン部コントロール
						controlButtons(screenStatus);

						// フォーカスセット
						setFocus($("#hdnSysAdminFlg").val());
					} else {
						// 画面表示制御
						showScreen("99");
					}

					// イベント作成
					/***********************************************************
					 * ヘッダ部
					 **********************************************************/
					/**
					 * 処理区分変更
					 */
					// TODO 処理区分
					$("input[name='optionR']:radio").bind("change", function() {
						// 画面表示制御
						operateMode = $(this).val();
						$("#hdnOperateMode").val(operateMode);
						showScreen(operateMode);

						$("#txtSlipNo").removeClass(CLS_ERROR_FIELD);
						$("#txtMess").html("");

						$("#txtJigyoCode").val($("#hdnLoginJigyoCode").val());
						$("#hdnJigyoCode").val($("#hdnLoginJigyoCode").val());
					});

					/**
					 * 伝票No GetFocus
					 */
					// TODO 自社伝票No
					$("#txtSlipNo").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});

					/**
					 * 伝票No LostFocus
					 */
					$("#txtSlipNo").bind("blur", function() {
						if ($(this).val() == "" || $(this).val() == null) {
							$("#txtMess").html("");
							$(this).removeClass(CLS_ERROR_FIELD);
						} else {
							$("#hdnSlipNo").val($(this).val());
							if(!execGetUriageData()){
								return;
							};
						}
					});

					/***********************************************************
					 * ボディ部（上段）
					 **********************************************************/
					/**
					 * 事業所コードGetFocus
					 */
					// TODO 事業所コード
					$("#txtJigyoCode").bind("focus", function() {
						jigyoCodeWK = $(this).val();
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});

					/**
					 * 事業所コードLostFocus
					 */
					$("#txtJigyoCode")
							.bind(
									"blur",
									function() {
										$("#hdnJigyoCode").val($(this).val());
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);

										// 事業所コードがブランクの場合、事業所名をブランクにする
										if ($(this).val() == ""
												|| $(this).val() == null) {
											$("#lblJigyoNm").html("");
										} else {
											// 属性チェック
											var result = chkType($(this).val(),
													TYPE_NUM, false, null)
											if (result != null) {
												strErrorMessage = $("#COM001-E")
														.val().replace("{1}",
																"事業所コード");
												$("#lblJigyoNm").html("");
												switchCustomerCode(false); /* 得意先 */
												switchShopCode(false); /* 店舗 */
												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();
												return;
											} else {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}

											// focus時に退避した事業所コードと異なる場合、事業所名取得
											if ($(this).val() != jigyoCodeWK) {
												if (getJigyoNm()) {
													// 成功
													$(this).removeClass(
															CLS_ERROR_FIELD);
													$("#txtMess").html("");
													$("#txtBillSrcNo").focus()
															.select();
												} else {
													// 失敗
													$("#txtMess").html(
															strErrorMessage);
													$("#lblJigyoNm").html("");
													$(this).focus().select();
													$(this).addClass(
															CLS_ERROR_FIELD);
												}
											}
										}
									});

					// TODO:請求元伝票No
					$("#txtBillSrcNo").bind(
							"blur",
							function() {
								$("#hdnBillSrcNo").val($(this).val());
								if ($(this).val() == "") {
									$(this).removeClass(CLS_ERROR_FIELD);
									$("#txtMess").html("");
									return;
								}
								if ($("#txtJigyoCode").val() == "") {
									$("#txtMess").html(
											$("#COM006-E").val().replace("{1}",
													"事業所コード"));
									$("#txtJigyoCode").focus().select();
									$("#txtJigyoCode")
											.addClass(CLS_ERROR_FIELD);
									return;
								}
								// alert("#txtBillSrcNo = " + $(this).val());
								showUriBillSource($(this).val());
							});

					/***********************************************************
					 * ボディ部（下段）
					 **********************************************************/

					/***********************************************************
					 * リスト部
					 **********************************************************/
					/**
					 * tblBodyの<input>にフォーカスが当たった
					 */
					$("#tblBody").on(
							"focus",
							"input",
							function() {
								// 編集中（フォーカスが当たった）行のIndexを取得
								var tmpId = $(":focus").attr("id");
								selectedRow = tmpId.substring(tmpId
										.indexOf("[") + 1, tmpId.indexOf("]"));
							});

					/**
					 * 受注数量（ケース）GetFocus
					 */
					// TODO 受注数量（ケース）
					$("#tblBody").on(
							"focus",
							"input[name='lngJucCase']",
							function() {
								// 受注数量（ケース）から「,」を除去する
								if ($(this).val() != ""
										|| $(this).val() != null) {
									var removeVal = removeDcmlSeparate($(this)
											.val());
									$(this).val(removeVal);
								}
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});

					/**
					 * 受注数量（ケース）LostFocus
					 */
					$("#tblBody").on(
							"blur",
							"input[name='lngJucCase']",
							function() {
								if (listInputStatus != -1) {
									return;
								}
								// 受注数量（ケース）がブランクの場合、処理終了
								if ($(this).val() == ""
										|| $(this).val() == null) {
									if (!flgPriceMaxError) {
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);
									}
									return;
								} else {
									// 明細行設定
									var irisu = $("td[name='lblIrisu']").eq(
											selectedRow).html(); // 入数
									var jucCase = removeDcmlSeparate($(this)
											.val()); // 受注数量（ケース）
									var jucCaseInit = jucCase; // 受注数量（ケース）初期値
									var jucSeparate = removeDcmlSeparate($(
											"input[name='lngJucSeparate']").eq(
											selectedRow).val()); // 受注数量（バラ）
									var jucSeparateInit = jucSeparate; // 受注数量（バラ）初期値
									var deliPrice = removeDcmlSeparate($(
											"td[name='lblDeliPrice']").eq(
											selectedRow).html()); // 納品単価
									var salesPrice = removeDcmlSeparate($(
											"td[name='lblSalesPrice']").eq(
											selectedRow).html()); // 販売単価
									var result = createDetailRow(irisu,
											jucCase, jucCaseInit, jucSeparate,
											jucSeparateInit, deliPrice,
											salesPrice, 0);
									if (result == 0) {
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);

										// 受注数量（ケース）に「,」を付与する
										var addVal = addDcmlSeparate($(this)
												.val());
										$(this).val(addVal);
									} else if (result == -1) {
										$(this).addClass(CLS_ERROR_FIELD);
										$(this).focus().select();
									} else if (result == -2) {
										// 納品金額上限エラー
										execPriceMaxError($(this));
									}
								}
								listInputStatus = -1;
							});

					/**
					 * 受注数量（バラ）GetFocus
					 */
					// TODO 受注数量（バラ）
					$("#tblBody").on(
							"focus",
							"input[name='lngJucSeparate']",
							function() {
								// 受注数量（バラ）から「,」を除去する
								if ($(this).val() != ""
										|| $(this).val() != null) {
									var removeVal = removeDcmlSeparate($(this)
											.val());
									$(this).val(removeVal);
								}
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});

					/**
					 * 受注数量（バラ）LostFocus
					 */
					$("#tblBody")
							.on(
									"blur",
									"input[name='lngJucSeparate']",
									function() {
										if (listInputStatus != -1) {
											return;
										}
										// 受注数量（バラ）がブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											if (!flgPriceMaxError) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}
											// 納品金額に「0」＆合計納品金額再計算
											var totalDeliAmt = 0;
											$("td[name='lblDeliAmt']").eq(
													selectedRow).html("0");
											for (var idx = 0; idx < maxRow; idx++) {
												totalDeliAmt = Number(totalDeliAmt)
														+ Number(removeDcmlSeparate($(
																"td[name='lblDeliAmt']")
																.eq(idx).html()));
											}
											$("#txtTotalDeliAmt")
													.html(
															addDcmlSeparate(totalDeliAmt));
											$("#hdnTotalDeliAmt").val(
													totalDeliAmt);
										} else {
											// 明細行設定
											var irisu = $("td[name='lblIrisu']")
													.eq(selectedRow).html(); // 入数
											var jucCase = removeDcmlSeparate($(
													"input[name='lngJucCase']")
													.eq(selectedRow).val()); // 受注数量（ケース）
											var jucCaseInit = jucCase; // 受注数量（ケース）初期値
											var jucSeparate = removeDcmlSeparate($(
													this).val()); // 受注数量（バラ）
											var jucSeparateInit = jucSeparate; // 受注数量（バラ）初期値
											var deliPrice = removeDcmlSeparate($(
													"td[name='lblDeliPrice']")
													.eq(selectedRow).html()); // 納品単価
											var salesPrice = removeDcmlSeparate($(
													"td[name='lblSalesPrice']")
													.eq(selectedRow).html()); // 販売単価
											var result = createDetailRow(irisu,
													jucCase, jucCaseInit,
													jucSeparate,
													jucSeparateInit, deliPrice,
													salesPrice, 1);
											if (result == 0) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);

												// 受注数量（バラ）に「,」を付与する
												var addVal = addDcmlSeparate($(
														this).val());
												$(this).val(addVal);
											} else if (result == -1) {
												// 通常エラー
												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();
											} else if (result == -2) {
												// 納品金額上限エラー
												execPriceMaxError($(this));
											}
										}
										listInputStatus = -1;
									});

					/***********************************************************
					 * ボタン部
					 **********************************************************/
					/**
					 * 登録ボタンクリック
					 */
					// TODO 登録ボタン
					$("#btnRegist")
							.bind(
									"click",
									function() {
										// ボタンがDisable状態なら処理終了
										if ($(this).hasClass(
												"btn_button_disabled")) {
											return;
										} else {
											disableButton("btnRegist");
										}

										// 訂正の場合、
										// alert(operateMode);
										if (operateMode == OPERATE_MODIFY) {
											// 編集されていない場合、
											if (!compare()) {
												enableButton("btnRegist");
												$("#txtMess").html(
														$("#COM034-E").val());
												return;
											}
										}

										// 確認メッセージ
										var confirmMessage = "";
										var operateText = "";
										if (operateMode == OPERATE_REGIST) {
											// 新規登録
											operateText = "登録";
										} else if (operateMode == OPERATE_MODIFY) {
											// 訂正
											operateText = "訂正";
										} else if (operateMode == OPERATE_CANCEL) {
											// 取消
											operateText = "取消";
										}
										confirmMessage = $("#COM001-I").val()
												.replace("{1}", operateText);

										// ダイアログ表示
										jQuestion_confirm(
												confirmMessage,
												$("#COM001-I").attr("title"),
												function(retVal) {
													if (retVal) {
														// 処理区分 = '取消' の場合、
														if (operateMode == OPERATE_CANCEL) {
															// 登録処理実行
															execRegistry(operateText);
														} else {
															// リスト部チェック
															if (!checkList()) {
																return;
															} else {
																// 登録処理実行
																execRegistry(operateText);
															}
														}
													} else {
														enableButton("btnRegist");
														return;
													}
												});
										// 登録ボタン活性化
										enableButton("btnRegist");
									});

					/**
					 * 入力戻りボタンクリック
					 */
					// TODO 入力戻りボタン
					$("#btnInputReturn").click(function() {
						// ボタンがDisable状態なら処理終了
						if ($(this).hasClass("btn_button_disabled")) {
							return;
						} else {
							disableButton("btnInputReturn");
						}

						// エラー回収
						$("#txtMess").html("");
						removeInfoClass();

						switch (screenStatus) {
						case CONST_SCREEN_HEAD:
							// NOP
							break;
						case CONST_SCREEN_BODY1:
							// NOP
							break;
						case CONST_SCREEN_BODY2:
							// NOP
							break;
						case CONST_SCREEN_LIST:
							// リスト部全行クリア
							for (var idx = 0; idx < maxRow; idx++) {
								clearItemDetail(idx);
							}
							$("#tblBodyRow").html("");

							// ボディ部（上段）活性化
							switchBodyOver(true);
							$("#lblCustomerCode").html("");
							$("#lblCustomerNameR").html("");
							$("#lblShopCode").html("");
							$("#lblShopNmR").html("");

							// フォーカスセット
							setFocus($("#hdnSysAdminFlg").val());

							// ボディ部（下段）非表示
							$(".tbl_data_condition2").hide();

							// リスト部非表示
							$(".data_result_area").hide(); // リスト部

							screenStatus = CONST_SCREEN_BODY1;

							break;
						}
						// ボタン部コントロール
						controlButtons(screenStatus);
					});

					/**
					 * 金額エラー解除ボタンクリック
					 */
					// TODO 金額エラー解除ボタン
					$("#btnAmountError")
							.bind(
									"click",
									function() {
										// ボタンがDisable状態なら処理終了
										if ($(this).hasClass(
												"btn_button_disabled")) {
											return;
										} else {
											disableButton("btnAmountError");
										}

										// 納品金額上限値を再取得
										var result = getDeliPriceMax();
										if (result) {
											var deliPriceMax = Number($(
													"#hdnPriceMax").val());
											var deliPrice = Number(removeDcmlSeparate($(
													"td[name='lblDeliAmt']")
													.eq(selectedRow).html()));
											// 上限値が正 and 納品金額＞納品金額上限値の場合、エラーとする
											if (deliPriceMax > 0
													&& deliPrice > deliPriceMax) {
												// alert("$('#hdnPriceMax').val()
												// = " + $("#hdnPriceMax").val()
												// + ", deliAmt = " + deliAmt);
												// COM004-E=納品金額上限（{1}円）を超過しました。
												strErrorMessage = $("#COM004-E")
														.val()
														.replace(
																"{1}",
																addDcmlSeparate($(
																		"#hdnPriceMax")
																		.val()));
												$("#txtMess").html(
														strErrorMessage);
												// 金額エラー解除ボタン非活性化
												enableButton("btnAmountError");
											} else {
												// 納品金額上限エラー解除
												clearPriceMaxError();

											}
										}
									});

					/**
					 * クリアボタンクリック
					 */
					// TODO クリアボタン
					$("#btnClear")
							.click(
									function() {
										// ボタンがDisable状態なら処理終了
										if ($(this).hasClass(
												"btn_button_disabled")) {
											return;
										} else {
											disableButton("btnClear");
										}

										// 確認メッセージ
										// ダイアログ表示
										var confirmMessage = $("#COM001-I")
												.val().replace("{1}", "クリア");
										jQuestion_confirm(
												confirmMessage,
												$("#COM001-I").attr("title"),
												function(retVal) {
													if (retVal) {
														flgEntry = false;

														// エラーメッセージクリア
														$("#txtMess").html("");
														removeInfoClass();
														// 処理区分：新規に変更
														$("#rad_New")
																.prop(
																		"checked",
																		true);
														operateMode = OPERATE_REGIST;

														// 真っ先に自社伝票Noをクリアする必要あり
														$("#txtSlipNo").val("");
														$("#hdnSlipNo").val("");

														// オブジェクト類クリア
														uriHead = null; // 売上ヘッダ
														uriBodyList = null; // 売上明細リスト

														// リスト部全行クリア
														if (screenStatus == CONST_SCREEN_LIST) {
															for (var idx = 0; idx < maxRow; idx++) {
																clearItemDetail(idx);
															}
														}

														// ボディ部（上段）クリア
														clearBodyOver();

														// リスト部非表示
														$("#tblBodyRow").html(
																"");
														$(".data_result_area")
																.hide(); // リスト部

														// ボディ部（下段）非表示
														$(
																".tbl_data_condition2")
																.hide();

														// ボディ部（上部）表示
														$(
																".tbl_data_condition1")
																.show();
														switchBodyOver(true);
														$("#lblCustomerCode")
																.html("");
														$("#lblCustomerNameR")
																.html("");
														$("#lblShopCode").html(
																"");
														$("#lblShopNmR").html(
																"");

														$(".slip_no").hide() // 自社伝票No
														$("#lblRegistUserCode")
																.html(
																		loginUserCode);
														$("#lblRegistUserName")
																.html(
																		loginUserName);

														screenStatus = CONST_SCREEN_BODY1;

														// ボタン部コントロール
														controlButtons(screenStatus);

														// フォーカスセット
														setFocus($(
																"#hdnSysAdminFlg")
																.val());
													} else {
														enableButton("btnClear");
														return;
													}
												});
										enableButton("btnClear");
									});

					/**
					 * Keyイベント
					 */
					// TODO Keyイベント
					$(document).on(
							"keydown",
							"input[type=text]",
							function(e) {
								if (e.keyCode == 13) {
									if (screenStatus == CONST_SCREEN_LIST) {
										EnterKey(e, this, ".data_result_area");
									} else {
										EnterKey(e, this,
												".data_conditions_area_cont");
									}
								}
							});
					$(document)
							.on(
									"keydown",
									"#txtDeliDateDay",
									function(e) {
										if (e.keyCode == 13) {
											// 計上日表示中
											if (flgAccount) {
												$("#txtAccountDay").focus()
														.select();
											} else {
												if ($("#ddlSlipKb").val() == "1"
														|| $("#ddlSlipKb")
																.val() == "4") {
													$("#txtBinKb").focus()
															.select();
												} else {
													$("#txtOriginSlipNo")
															.focus().select();
												}
											}
										}
									});
					$(document).on("keydown", function(e) {
						if (e.keyCode == 16) {
							flgShift = true;
						}
					});
					$(document).on("keyup", function(e) {
						if (e.keyCode == 16) {
							flgShift = false;
						}
					});
				});

/*******************************************************************************
 * 関数
 ******************************************************************************/
/**
 * リスト部に入力された明細情報を配列に格納後、JSON化し、JSPのhidden項目へセットする
 */
function list2Json() {
	var arrayItem = [];

	for (var idx = 0; idx < maxRow; idx++) {
		var itemInfo = {};
		// 自社品目コード
		itemInfo.itemCode = $("td[name='lblItemCode']").eq(idx).html();
		// 得意先品目コード
		itemInfo.customerItemCode = $("td[name='lblCustomerItemCode']").eq(idx)
				.html();
		// 品名略称
		itemInfo.itemNameR = $("td[name='lblItemNameR']").eq(idx).html();
		// 量目
		itemInfo.ryomoku = $("td[name='lblRyomoku']").eq(idx).html();
		// 入数
		itemInfo.irisu = Number($("td[name='lblIrisu']").eq(idx).html());
		// 受注数量（ケース）
		itemInfo.jucCase = Number(removeDcmlSeparate($(
				"input[name='lngJucCase']").eq(idx).val()));
		// 受注数量（バラ)
		itemInfo.jucSeparate = Number(removeDcmlSeparate($(
				"input[name='lngJucSeparate']").eq(idx).val()));
		// 納品単価
		itemInfo.deliPrice = Number(removeDcmlSeparate($(
				"td[name='lblDeliPrice']").eq(idx).html()));
		// 販売単価
		itemInfo.salesPrice = Number(removeDcmlSeparate($(
				"td[name='lblSalesPrice']").eq(idx).html()));
		// 納品金額
		itemInfo.deliAmt = Number(removeDcmlSeparate($("td[name='lblDeliAmt']")
				.eq(idx).html()));

		// itemInfoを配列に格納
		arrayItem.push(itemInfo);
	}
	// JSON変換
	var jsonData = JSON.stringify(arrayItem);

	// 隠し変数に格納
	$("#hdnItemJson").val(jsonData);
}

/**
 * リスト部チェック。登録処理前にリスト部の項目を再チェック
 * 
 * @param row
 *            チェック対象の行No
 * @return チェック結果（null：正常、null以外（エラーコード）：異常）
 */
function checkList() {
	strErrorMessage = "";
	var rtnFlg = true;
	var errorMsg = "";
	var elmFocus = null;
	var result = null;

	// エラーメッセージ重複防止フラグ
	var flgCase = false;
	var flgSeparate_COM001E = false;
	var flgSeparate_COM005E = false;
	var flgSeparate_COM006E = false;
	var flgSeparate_COM007E = false;
	var flgOriginSeparate = false;

	for (var idx = 0; idx < maxRow; idx++) {
		var itemCode = $("td[name='lblItemCode']").eq(idx).html().trim();
		if (itemCode != "") {
			var uriBodyIdx = $("input[name='hdnUriBodyIdx'").eq(idx).val();
			var jucCase = removeDcmlSeparate($("input[name='lngJucCase']").eq(
					idx).val().trim());
			var jucSeparate = removeDcmlSeparate($(
					"input[name='lngJucSeparate']").eq(idx).val().trim());
			var irisu = removeDcmlSeparate($("td[name='lblIrisu']").eq(idx)
					.html().trim());
			var deliPrice = removeDcmlSeparate($("td[name='lblDeliPrice']").eq(
					idx).html().trim());
			var salesPrice = removeDcmlSeparate($("td[name='lblSalesPrice']")
					.eq(idx).html().trim());
			var jucCaseInit = jucCase;
			var jucSeparateInit = jucSeparate;

			// 受注数量（ケース）
			// alert("chkJucSuCase(" + jucCase + ", " + irisu + ", " + deliPrice
			// + ", " + jucCaseInit + ");");
			var result = chkJucSuCase(jucCase, irisu, deliPrice, jucCaseInit);
			// alert("result = " + result);
			if (result != null) {
				rtnFlg = false;
				if (elmFocus == null) {
					elmFocus = $("input[name='lngJucCase']").eq(idx);
				}
				$("input[name='lngJucCase']").eq(idx).addClass(CLS_ERROR_FIELD);
				if (!flgCase) {
					errorMsg = $("#" + result).val()
							.replace("{1}", "受注数量（ケース）");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgCase = true;
				}
			}

			// 受注数量（バラ）
			// alert("chkJucSuBara(" + jucSeparate + ", " + deliPrice + ", " +
			// jucSeparateInit + ");");
			var result = chkJucSuBara(jucSeparate, deliPrice, jucSeparateInit);
			// alert("result = " + result);
			if (result != null) {
				if (result == "COM001-E" && !flgSeparate_COM001E) {
					// COM001-E : {1}の値が正しくありません。
					errorMsg = $("#" + result).val().replace("{1}", "受注数量（バラ）");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgSeparate_COM001E = true;

					// COM007-E && 新規登録以外の場合、エラーでないため個別設定
					rtnFlg = false;
					if (elmFocus == null) {
						elmFocus = $("input[name='lngJucSeparate']").eq(idx);
					}
					$("input[name='lngJucSeparate']").eq(idx).addClass(
							CLS_ERROR_FIELD);
				} else if (result == "COM005-E" && !flgSeparate_COM005E) {
					// COM005-E : 元の{1}を超える{1}は指定できません。
					errorMsg = $("#" + result).val().replace("{1}", "受注数量（バラ）")
							.replace("{1}", "受注数量（バラ）");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgSeparate_COM005E = true;

					// COM007-E && 新規登録以外の場合、エラーでないため個別設定
					rtnFlg = false;
					if (elmFocus == null) {
						elmFocus = $("input[name='lngJucSeparate']").eq(idx);
					}
					$("input[name='lngJucSeparate']").eq(idx).addClass(
							CLS_ERROR_FIELD);
				} else if (result == "COM006-E" && !flgSeparate_COM006E) {
					// COM006-E : {1}は必須項目です。
					errorMsg = $("#" + result).val().replace("{1}", "受注数量（バラ）");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgSeparate_COM006E = true;

					// COM007-E && 新規登録以外の場合、エラーでないため個別設定
					rtnFlg = false;
					if (elmFocus == null) {
						elmFocus = $("input[name='lngJucSeparate']").eq(idx);
					}
					$("input[name='lngJucSeparate']").eq(idx).addClass(
							CLS_ERROR_FIELD);
				} else if (result == "COM007-E" && !flgSeparate_COM007E) {
					if (operateMode == OPERATE_REGIST) {
						// COM007-E : {1}に{2}は指定できません。
						errorMsg = $("#" + result).val().replace("{1}",
								"受注数量（バラ）").replace("{2}", "0");
						strErrorMessage = strErrorMessage + "<br>" + errorMsg;
						flgSeparate_COM007E = true;

						// COM007-E && 新規登録以外の場合、エラーでないため個別設定
						rtnFlg = false;
						if (elmFocus == null) {
							elmFocus = $("input[name='lngJucSeparate']")
									.eq(idx);
						}
						$("input[name='lngJucSeparate']").eq(idx).addClass(
								CLS_ERROR_FIELD);
					}
				}
			}

			// 請求元伝票の明細行分、繰返す
			for (var idx2 = 0; idx2 < uriOriginBodyList.length; idx2++) {
				// 同一アイテムあり
				if (itemCode == uriOriginBodyList[idx2].itemCode) {
					// 受注数量（バラ） ＞ 請求元伝票.受注数量（バラ） の場合、
					if (jucSeparate > uriOriginBodyList[idx2].baraSu) {
						rtnFlg = false;
						if (elmFocus == null) {
							elmFocus = $("input[name='lngJucSeparate']")
									.eq(idx);
						}
						$("input[name='lngJucSeparate']").eq(idx).addClass(
								CLS_ERROR_FIELD);

						// メッセージ
						if (!flgOriginSeparate) {
							// URI016-E={1}元伝票より{2}を増やす事はできません。
							errorMsg = $("#URI016-E").val()
									.replace("{1}", "請求").replace("{2}",
											"受注数量（バラ）");
							strErrorMessage = strErrorMessage + "<br>"
									+ errorMsg;
							flgOriginSeparate = true;
						}
					}
				}
			}
		}
	}

	// エラーが発生していない場合、
	if (rtnFlg) {
		// リスト部をJSON化
		list2Json();
	}

	// strErrorMessageが空ではない場合、
	if (strErrorMessage != "") {
		// 先頭の「<br>」を除去
		strErrorMessage = strErrorMessage.substr(4);
	}

	// elmFocusがnullではない場合、
	if (elmFocus != null) {
		// 先頭のエラー項目にフォーカスをセット
		elmFocus.focus().select();
	}

	return rtnFlg;
}
/**
 * ボタン表示制御
 * 
 * @param status
 *            画面ステータス
 */
function controlButtons(status) {
	switch (status) {
	case CONST_SCREEN_HEAD:
		/** 登録ボタン */
		disableButton("btnRegist");
		/** 入力戻りボタン */
		disableButton("btnInputReturn");
		/** 金額エラー解除ボタン */
		disableButton("btnAmountError");
		/** クリアボタン */
		enableButton("btnClear");

		break;
	case CONST_SCREEN_BODY1:
		/** 登録ボタン */
		disableButton("btnRegist");
		/** 入力戻りボタン */
		disableButton("btnInputReturn");
		/** 金額エラー解除ボタン */
		disableButton("btnAmountError");
		/** クリアボタン */
		enableButton("btnClear");

		break;
	case CONST_SCREEN_LIST:
		/** 登録ボタン */
		enableButton("btnRegist");
		/** 入力戻りボタン */
		enableButton("btnInputReturn");
		/** 金額エラー解除ボタン */
		disableButton("btnAmountError");
		/** クリアボタン */
		enableButton("btnClear");

		break;
	case CONST_SCREEN_SEARCH:
		/** 登録ボタン */
		enableButton("btnRegist");
		/** 入力戻りボタン */
		disableButton("btnInputReturn");
		/** 金額エラー解除ボタン */
		disableButton("btnAmountError");
		/** クリアボタン */
		enableButton("btnClear");

		break;
	}
}

/**
 * 画面表示制御
 * 
 * @param operateKb
 *            処理区分（0:新規登録、1:訂正、2:取消、3:照会）
 */
function showScreen(operateKb) {
	$(".data_result_area").hide(); // リスト部
	$(".txt_tax").hide(); // 内税顧客
	$(".cor_class").hide(); // 修正区分

	if (operateKb == OPERATE_REGIST) {
		// 新規登録
		// ボディ部（上段）
		$(".tbl_data_condition1").show();
		$(".slip_no").hide() // 自社伝票No
		$("#hdnSlipIdx").val(1); // 自社伝票枝番

		clearBodyOver();
		switchBodyOver(true);

		$(".tbl_data_condition2").hide(); // ボディ部（下段）

		// 事業所表示制御
		showJigyosho($("#hdnSysAdminFlg").val());

		// 画面表示状態
		screenStatus = CONST_SCREEN_BODY1;

		// フォーカスセット
		if ($("#hdnSysAdminFlg").val() == "0") {
			$("#txtBillSrcNo").focus().select();
		} else {
			$("#txtJigyoCode").focus().select();
		}
	} else if (operateKb == OPERATE_MODIFY || operateKb == OPERATE_CANCEL
			|| operateKb == OPERATE_VIEW) {
		// 訂正・取消・照会
		$(".tbl_data_condition1").hide(); // ボディ部（上段）
		$(".tbl_data_condition2").hide(); // ボディ部（下段）
		$("#txtSlipNo").val("");
		$("#hdnSlipNo").val("");
		$(".slip_no").show(); // 自社伝票No
		// 画面表示状態
		screenStatus = CONST_SCREEN_HEAD;

		$("#txtSlipNo").focus();
	}
}

/**
 * 明細行生成（各種入力チェック＆金額計算）
 * 
 * @param irisu
 *            入数
 * @param jucCase
 *            受注数量（ケース）
 * @param jucCaseInit
 *            受注数量（ケース）初期値
 * @param jucSeparate
 *            受注数量（バラ）
 * @param jucSeparateInit
 *            受注数量（バラ）初期値
 * @param deliPrice
 *            納品金額
 * @param salesPrice
 *            販売金額
 * @param status
 *            入力ステータス（0：受注数量（ケース）、1：受注数量（バラ）、2：納品単価、3：販売単価）
 * 
 * @return 0：正常、-1：異常、-2：納品金額上限エラー
 */
function createDetailRow(irisu, jucCase, jucCaseInit, jucSeparate,
		jucSeparateInit, deliPrice, salesPrice, status) {
	// alert("irisu = " + irisu + ", jucCase = " + jucCase + ", jucCaseInit = "
	// + jucCaseInit + ", jucSeparate = " + jucSeparate + ", jucSeparateInit = "
	// + jucSeparateInit + ", deliPrice = " + deliPrice + ", salesPrice = " +
	// salesPrice + ", status = " + status);
	listInputStatus = status;
	switch (listInputStatus) {
	case 0: // 受注数量（ケース）
		// ケースチェック
		// alert("chkJucSuCase(" + jucCase + ", " + irisu + ", " + deliPrice +
		// ", " + jucCaseInit + ");");
		var result = chkJucSuCase(jucCase, irisu, deliPrice, jucCaseInit);
		// alert("result = " + result);
		if (result != null) {
			strErrorMessage = $("#" + result).val().replace("{1}", "受注数量（ケース）");
			return -1;
		}
		// 受注数量（バラ）算出
		jucSeparate = calcJucSeparate(jucCase, irisu);
		$("input[name='lngJucSeparate']").eq(selectedRow).val(
				addDcmlSeparate(jucSeparate));

		break;
	case 1: // 受注数量（バラ）
		// 受注数量（バラ）チェック
		// alert("chkJucSuBara(" + jucSeparate + ", " + deliPrice + ", " +
		// jucSeparateInit + ");");
		var result = chkJucSuBara(jucSeparate, deliPrice, jucSeparateInit);
		// alert("result = " + result);
		if (result == "COM001-E" || result == "COM005-E"
				|| result == "COM006-E") {
			// COM001-E : {1}の値が正しくありません。
			// COM005-E : 元の{1}を超える{1}は指定できません。
			// COM006-E : {1}は必須項目です。
			strErrorMessage = $("#" + result).val().replace("{1}", "受注数量（バラ）");
			return -1;
		} else if (result == "COM007-E") {
			// COM007-E : {1}に{2}は指定できません。
			// strErrorMessage = $("#" + result).val().replace("{1}",
			// "受注数量（バラ）")
			// .replace("{2}", "0");
			// return -1;
		}

		break;
	}

	// 受注数量（バラ） != "" の場合、納品金額、納品金額合計算出
	if ((jucSeparate != "" || jucSeparate != null)) {
		var totalDeliAmt = 0;
		var deliAmt = calcDeliAmt(jucSeparate, deliPrice, uriHead.shipsRudKb
				.trim());
		$("td[name='lblDeliAmt']").eq(selectedRow).html(
				addDcmlSeparate(deliAmt));
		if (Number($("#hdnPriceMax").val()) > 0
				&& deliAmt > Number($("#hdnPriceMax").val())) {
			// alert("$('#hdnPriceMax').val() = " + $("#hdnPriceMax").val()
			// + ", deliAmt = " + deliAmt);
			// COM004-E=納品金額上限（{1}円）を超過しました。
			strErrorMessage = $("#COM004-E").val().replace("{1}",
					addDcmlSeparate($("#hdnPriceMax").val()));
			return -2;
		}

		for (var idx = 0; idx < maxRow; idx++) {
			totalDeliAmt = Number(totalDeliAmt)
					+ Number(removeDcmlSeparate($("td[name='lblDeliAmt']").eq(
							idx).html()));
		}
		$("#txtTotalDeliAmt").html(addDcmlSeparate(totalDeliAmt));
		$("#hdnTotalDeliAmt").val(totalDeliAmt);
	}
	if (flgPriceMaxError) {
		return -2;
	} else {
		return 0;
	}
}

/**
 * ボディ部（上段）の活性化状態を切り替える
 * 
 * @param flgSwitch
 *            true 活性化する、false 非活性化する
 */
function switchBodyOver(flgSwitch) {
	if (flgSwitch) {
		/** 処理区分 */
		$(".option_regis").prop("disabled", false);
		/** 自社伝票No */
		$("#txtSlipNo").prop("disabled", false);
		/** 事業所コード */
		$("#txtJigyoCode").prop("disabled", false);
		/** 請求元伝票No */
		$("#txtBillSrcNo").prop("disabled", false);
	} else {
		/** 処理区分 */
		$(".option_regis").prop("disabled", true);
		/** 自社伝票No */
		$("#txtSlipNo").prop("disabled", true);
		/** 事業所コード */
		$("#txtJigyoCode").prop("disabled", true);
		/** 請求元伝票No */
		$("#txtBillSrcNo").prop("disabled", true);
	}
}
/**
 * ボディ部（上段）を初期化する
 */
function clearBodyOver() {
	// 処理区分：新規に変更
	$("#rad_New").prop("checked", true);

	// 内税顧客
	$(".txt_tax").hide();

	// 事業所コード
	$("#txtJigyoCode").val(loginJigyoCode);
	$("#hdnJigyoCode").val(loginJigyoCode);
	$("#txtJigyoCode").removeClass(CLS_ERROR_FIELD);
	jigyoCodeWK = "";
	// 事業所名
	$("#lblJigyoNm").html(loginJigyoName);
	showJigyosho($("#hdnSysAdminFlg").val());

	// 請求元伝票No
	$("#txtBillSrcNo").val("");
	$("#hdnBillSrcNo").val("");
}

/**
 * リスト部の活性化/非活性化切替
 * 
 * @param flgSwitch
 */
function switchList(flgSwitch) {
	var flgDisabled = true;
	if (flgSwitch) {
		flgDisabled = false;
	}
	/**
	 * 画面表示項目
	 */
	for (var idx = 0; idx < maxRow; idx++) {
		// 受注数量（ケース）
		$("input[name='lngJucCase']").eq(idx).prop("disabled", flgDisabled);
		// 受注数量（バラ）
		$("input[name='lngJucSeparate']").eq(idx).prop("disabled", flgDisabled);
	}
}

/**
 * 納品金額上限エラー発生
 * 
 * @param elm
 *            発生項目
 */
function execPriceMaxError(elm) {
	flgPriceMaxError = true;

	// 該当項目をエラー状態
	$("input[name='lngJucCase']").eq(selectedRow).addClass(CLS_ERROR_FIELD);
	$("input[name='lngJucSeparate']").eq(selectedRow).addClass(CLS_ERROR_FIELD);

	// 他の行を非活性化
	for (var idx = 0; idx < maxRow; idx++) {
		if (idx != selectedRow) {
			// 受注数量（ケース）
			$("input[name='lngJucCase']").eq(idx).prop("disabled", true);
			// 受注数量（バラ)
			$("input[name='lngJucSeparate']").eq(idx).prop("disabled", true);
		}
	}

	// 金額エラー解除ボタン活性化
	enableButton("btnAmountError");
	// 登録ボタン非活性化
	disableButton("btnRegist");

	// 「,」を付与する
	var addVal = addDcmlSeparate(elm.val());
	elm.val(addVal);
	// elm.focus().select();
}

/**
 * 納品金額上限エラーを解除する
 */
function clearPriceMaxError() {
	flgPriceMaxError = false;

	// エラーメッセージ非表示
	$("#txtMess").html("");

	// 該当項目をエラー状態を解除
	$("input[name='lngJucCase']").eq(selectedRow).removeClass(CLS_ERROR_FIELD);
	$("input[name='lngJucSeparate']").eq(selectedRow).removeClass(
			CLS_ERROR_FIELD);

	// 他の行（明細情報表示）を活性化
	for (var idx = 0; idx < maxRow; idx++) {
		// 品目情報入力済の行のみ
		if ($("td[name='lblItemCode']").eq(idx).html() != "") {
			// 受注数量（ケース）
			$("input[name='lngJucCase']").eq(idx).prop("disabled", false);
			// 受注数量（バラ)
			$("input[name='lngJucSeparate']").eq(idx).prop("disabled", false);
		}
	}

	// 金額エラー解除ボタン非活性化
	disableButton("btnAmountError");

	// 登録ボタン活性化
	enableButton("btnRegist");
}

/**
 * 所属事業所の状態の表示
 * 
 * @param [セッション]システム管理者フラグ
 * @return
 * @exception
 */
function showJigyosho(strSysAdminFlag) {
	if (strSysAdminFlag == "1") {
		$('#jigyo_area').show();
	} else {
		$('#jigyo_area').hide()
	}
}

/**
 * フォーカス処理
 * 
 * @param [セッション]システム管理者フラグ
 * @return
 * @exception
 */
function setFocus(strSysAdminFlag) {
	if (strSysAdminFlag == "1") {
		$('#txtJigyoCode').focus().select();
	} else {
		$('#txtBillSrcNo').focus().select();
	}
}

/**
 * リスト部データの退避
 * 
 * @return array 退避結果（配列）
 */
function saveData() {
	// alert("saveData!! START!")
	var tmpValue = [];
	for (var idx = 0; idx < maxRow; idx++) {
		// 受注数量（ケース）
		tmpValue.push($("input[name='lngJucCase']").eq(idx).val());
		// 受注数量（バラ)
		tmpValue.push($("input[name='lngJucSeparate']").eq(idx).val());
	}
	return tmpValue;
}

/**
 * 変更有無チェック
 * 
 * @return boolean true:変更あり、false:変更なし
 * @exception
 */
function compare() {
	$("#txtMess").html("");
	var newValue = [];
	newValue = saveData();

	for (var idx = 0; idx < newValue.length; idx++) {
		if (initValue[idx] != newValue[idx]) {
			return true;
		}
	}
	return false;
}

/**
 * 登録処理
 * 
 * @param operateText
 *            処理区分文字列
 */
function execRegistry(operateText) {
	$("#hdnOperateMode").val(operateMode); // 処理区分

	// 売上データ登録
	var result = registryUriageData();
	if (result) {
		// 成功
		flgEntry = true;
		switchList(false);
		if (operateMode == OPERATE_REGIST) {
			// 新規登録の場合、伝票No表示
			$("#lblSlipNo").html(uriHead.custDenNo.trim());
		} else if (operateMode == OPERATE_CANCEL) {
			// 取消の場合、修正区分非活性化
			$("#ddlModifyKb").prop("disabled", true);
		}
		// メッセージ
		var msgSuccess = "";
		if (operateMode == OPERATE_REGIST) {
			// 新規登録の場合、自社伝票No {1}が登録されました。
			msgSuccess = $("#COM014-I").val().replace("{1}", uriHead.denNo);
		} else {
			// 新規登録以外の場合、{1}が完了しました
			msgSuccess = $("#COM002-I").val().replace("{1}", operateText);
		}
		addInfoClass();
		$("#txtMess").html(msgSuccess);
		$("#messError").removeClass("error").addClass("info");
		disableButton("btnRegist");
	} else {
		// 失敗
		$("#txtMess").html(strErrorMessage)
	}
}

/**
 * 売上情報取得実施
 * 
 * @return true 正常、false 異常
 */
function execGetUriageData() {
	if (getUriageData()) {
		$("#txtMess").html("");
		$("#txtSlipNo").removeClass(CLS_ERROR_FIELD);

		// 処理区分 = '1'（訂正） or '2'（取消）の場合、
		if (operateMode == OPERATE_MODIFY || operateMode == OPERATE_CANCEL) {
			// 締め処理フラグ != "0"（未処理）
			if (uriHead.billFlg.trim() != "0") {
				// URI001-E
				strErrorMessage = $("#URI001-E").val();
				$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
				$("#txtSlipNo").focus().select();

				return false;
			}
		}
		// データ区分 = '09'（再請求売上登録）以外の場合、
		if (uriHead.datKb.trim() != "09" && operateMode != OPERATE_REGIST) {
			// 画面名称
			var screenName = "売上登録";
			if (uriHead.datKb.trim() == "08") {
				screenName = "入金調整売上登録";
			}
			// 処理名称
			var operateName = "訂正";
			if (operateMode == OPERATE_CANCEL) {
				operateName = "取消";
			} else if (operateMode == OPERATE_VIEW) {
				operateName = "照会";
			} 
			// URI017-E 該当の売上情報は{1}画面から{2}してください
			strErrorMessage = $("#URI017-E").val().replace("{1}", screenName).replace("{2}", operateName);
			$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
			$("#txtSlipNo").focus().select();

			return false;
		}

		if (operateMode != OPERATE_REGIST) {
			// 検索結果表示
			setSearchResult();

			// 処理区分 = '訂正'の場合、請求元売上情報の取得
			if (operateMode == OPERATE_MODIFY) {
				if (!getBillSrcUriageData()) {
					$("#txtMess").html(strErrorMessage);
				}
			}
			// ボディ部（上段）非活性化
			switchBodyOver(false);

			// 処理区分 = '取消' or '照会'の場合、
			if (operateMode == OPERATE_CANCEL || operateMode == OPERATE_VIEW) {
				// リスト部非活性化
				switchList(false)
			} else {
				// リスト部データ退避
				initValue = [];
				initValue = saveData()
			}
	
			// ボタン部コントロール
			// 処理区分 = '取消' or '照会'の場合、
			screenStatus = CONST_SCREEN_SEARCH
			controlButtons(screenStatus);
			if (operateMode == OPERATE_VIEW) {
				disableButton("btnRegist");
			}
		} else {
			uriHead = null;
			uriBodyList = null;
			lstModify = null;
			updUserName = "";
		}
	} else {
		$("#txtMess").html(strErrorMessage);
		$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
		$("#txtSlipNo").focus().select();
		// 処理区分 = '新規登録'の場合、処理区分変更でラジオボタンがクリックされたタイミングで
		// 自社伝票NoからLostFocusされた場合なので、エラーメッセージを消す
		if (operateMode == OPERATE_REGIST) {
			$("#txtMess").html("");
		}
	}
	return true;
}

/**
 * 請求元売上情報選択ダイアログのcallback関数
 * 
 * @param returnData
 *            復帰値
 */
function callbackUriBillSource(returnData) {
	// 再請求済の場合、
	// → 同一得意先、店舗、便、納品日の売上情報のうち、最新の売上情報が締め処理未実施
	if (returnData == "#URI019-E") {
		strErrorMessage = $(returnData).val();
		$("#txtMess").html(strErrorMessage);
		$("#txtBillSrcNo").addClass(CLS_ERROR_FIELD);
		$("#txtBillSrcNo").focus().select();
		return;
	}

	$("#hdnJsonBillSrc").val(returnData);
	var data = JSON.parse(returnData);
	// 該当の売上情報が存在しない場合、
	if (data == null) {
		strErrorMessage = $("#COM009-E").val().replace("{1}", "売上テーブル")
				.replace("{2}", "請求元売上情報");
		$("#txtMess").html(strErrorMessage);
		$("#txtBillSrcNo").addClass(CLS_ERROR_FIELD);
		$("#txtBillSrcNo").focus().select();
		return;
	}
	uriHead = data[0].uriData.uriHead;
	// 該当の売上情報が締め処理未実施の場合、
	// → 同一得意先、店舗、便、納品日の売上情報が１件のみ
	if (uriHead.billFlg == "0") {
		if (uriHead.datKb == "09") {
			// データ区分 = 再請求売上登録
			strErrorMessage = $("#URI019-E").val();
		} else {
			// データ区分 = 再請求売上登録以外
			strErrorMessage = $("#URI018-E").val();
		}
		$("#txtMess").html(strErrorMessage);
		$("#txtBillSrcNo").addClass(CLS_ERROR_FIELD);
		$("#txtBillSrcNo").focus().select();
		uriHead = null;
		return;
	}

	$("#txtMess").html("");
	$("#txtBillSrcNo").removeClass(CLS_ERROR_FIELD);
	uriBodyList = JSON.parse(data[0].uriData.uriBodyJson);
	uriOriginHead = uriHead;
	uriOriginBodyList = uriBodyList;

	// 画面セット
	setSearchResult();

	// ボディ部（上段）非活性化
	switchBodyOver(false);

	// ボタン部コントロール
	controlButtons(screenStatus);

	// 先頭の入力行の受注数量（ケース）
	for (var idx = 0; idx < maxRow; idx++) {
		if ($("td[name='lblItemCode'").eq(idx).html() != "") {
			$("input[name='lngJucCase']").eq(idx).focus().select();
			break;
		}
	}
}

/**
 * 検索結果の表示
 */
function setSearchResult() {
	// uriHead = data[0].uriData.uriHead;
	// uriBodyList = JSON.parse(data[0].uriData.uriBodyJson);

	$(".tbl_data_condition1").show(); // ボディ部（上段）
	$(".tbl_data_condition2").show(); // ボディ部（下段）
	$(".data_result_area").show(); // リスト部
	$(".tbl_list th").css('background-color', '#76933c');
	$(".bg_aa5930").css('background-color', '#76933c');

	// 処理区分 = '新規登録' 以外の場合、
	if (operateMode != OPERATE_REGIST) {
		$("#txtBillSrcNo").val(uriHead.billDenNo); // 請求元伝票No
		$("#hdnBillSrcNo").val(uriHead.billDenNo); // 請求元伝票No
	}
	$("#lblCustomerCode").html(uriHead.custCode); // 得意先コード
	$("#hdnCustomerCode").val(uriHead.custCode); // 得意先コード
	$("#lblCustomerNameR").html(uriHead.custNmR); // 得意先略称
	// 内税顧客
	if (uriHead.taxIncKb == "1") {
		$(".txt_tax").hide();
	} else {
		$(".txt_tax").show();
	}

	$("#lblShopCode").html(uriHead.shopCode); // 店舗コード
	$("#hdnShopCode").val(uriHead.shopCode); // 店舗コード
	$("#lblShopNmR").html(uriHead.shopNmR); // 店舗略称

	// 納品日
	var deliDate = addDateSeparate(uriHead.deliDate);
	$("#lblDeliDate").html(deliDate);
	$("#hdnDeliDate").val(uriHead.deliDate);
	$("#lblBinKb").html(uriHead.bin); // 便区分
	$("#hdnBinKb").val(uriHead.bin); // 便区分
	$("#lblDeliKb").html(uriHead.deliKb); // 納品区分
	$("#lblSalesKb").html(uriHead.saleKb) // 販売区分
	$("#lblCourseCode").html(uriHead.courseCode); // コースコード
	$("#lblCourseNameR").html(uriHead.cosNmR); // コース略称
	$("#lblSlipType").html(uriHead.shipsNm); // 出荷伝票名称
	// 出荷伝票行数
	maxRow = uriHead.shipsLine;
	var line = "000" + maxRow;
	$("#lblSlipLine").html(line.substr(line.length - 3, 3));

	$("#txtTotalDeliAmt").html(addDcmlSeparate(uriHead.sumDeliAmt));
	// リスト部表示
	var listHtml = "";
	var flgExist = false;
	for (var idx = 0; idx < maxRow; idx++) {
		flgExist = false;
		for (var idx2 = 0; idx2 < uriBodyList.length; idx2++) {
			// リスト部のidx + 1が売上明細の行Noと一致した場合、
			if (uriBodyList[idx2].custDenRow == idx + 1) {
				// 明細情報を元に明細行を生成
				listHtml = listHtml + createSearchRow(uriBodyList[idx2], idx2);
				flgExist = true;
			}
		}
		// 明細行を生成しなかった場合、
		if (!flgExist) {
			// 空行を生成
			listHtml = listHtml + createBlankRow(idx);
		}
	}
	screenStatus = CONST_SCREEN_LIST;
	// HTMLセット
	$("#tblBodyRow").html(listHtml);

	// 更新日時
	$("#hdnUpdateDateTime").val(uriHead.updYmd + uriHead.updTime);
}

/**
 * 売上明細の情報を元にした明細行を生成する
 * 
 * @param uriBody
 *            売上明細情報
 * @param uriBodyIdx
 *            売掛明細情報リストのインデックス（行トバし対応）
 */
function createSearchRow(uriBody, uriBodyIdx) {
	var rowHtml = "";

	// 量目丸め処理
	var weight = getNumberRound(uriBody.ryomoku, 1, 2) + "g";
	var idx = uriBody.custDenRow - 1;
	rowHtml = "<tr>" + "<td id='"
			+ (idx + 1)
			+ "' name='lblNumber' class='contextMenu align_right'>"
			+ "<span>"
			+ (idx + 1)
			+ "</span></td>"
			// 自社品目コード
			+ "<td id='lblItemCode["
			+ idx
			+ "]' name='lblItemCode' class='color_blue'>"
			+ uriBody.itemCode.trim()
			+ "</td>"
			// 得意先品目コード
			+ "<td id='lblCustomerItemCode["
			+ idx
			+ "]' name='lblCustomerItemCode' class='color_blue'>"
			+ uriBody.itemCodeCust.trim()
			+ "</td>"
			// 品名略称
			+ "<td id='lblItemNameR["
			+ idx
			+ "]' name='lblItemNameR' class='color_blue'>"
			+ uriBody.itemNmR.trim()
			+ "</td>"
			// 量目
			+ "<td id='lblRyomoku["
			+ idx
			+ "]' name='lblRyomoku' class='color_blue align_center'>"
			+ weight
			+ "</td>"
			// 入数
			+ "<td id='lblIrisu["
			+ idx
			+ "]' name='lblIrisu' class='color_blue align_right'>"
			+ uriBody.ireme
			+ "</td>"
			// 受注数量（ケース）
			+ "<td class='align_right'>"
			+ "	<input id='lngJucCase["
			+ idx
			+ "]' name='lngJucCase' type='text' class='txt_width75per align_right border_none' value='"
			+ addDcmlSeparate(uriBody.caseSu)
			+ "' maxlength='6' />"
			+ "</td>"
			// 受注数量（バラ)
			+ "<td class='align_right'>"
			+ "	<input id='lngJucSeparate["
			+ idx
			+ "]' name='lngJucSeparate' type='text' class='txt_width75per align_right border_none' value='"
			+ addDcmlSeparate(uriBody.baraSu) + "' maxlength='6' />" + "</td>"
			// 納品単価
			+ "<td id='lblDeliPrice[" + idx
			+ "]' name='lblDeliPrice' class='color_blue align_right'>"
			+ addDcmlSeparate(uriBody.deliPrice.toFixed(2)) + "</td>"
			// 販売単価
			+ "<td id='lblSalesPrice[" + idx
			+ "]' name='lblSalesPrice' class='color_blue align_right'>"
			+ addDcmlSeparate(uriBody.salePrice) + "</td>"
			// 納品金額
			+ "<td id='lblDeliAmt[" + idx
			+ "]' name='lblDeliAmt' class='align_right'>"
			+ addDcmlSeparate(uriBody.deliAmt) + "</td>"
			// 売上明細Index（hidden）
			+ "<input id='hdnUriBodyIdx[" + idx
			+ "]' name='hdnUriBodyIdx' type='hidden' value='" + uriBodyIdx
			+ "' />" + "</tr>";

	return rowHtml;
}

/**
 * 空の明細行を生成する
 * 
 * @param idx
 *            行No - 1
 */
function createBlankRow(idx) {
	var rowHtml = "";
	rowHtml = "<tr>" + "<td id='"
			+ (idx + 1)
			+ "' name='lblNumber' class='contextMenu align_right'>"
			+ "<span>"
			+ (idx + 1)
			+ "</span></td>"
			// 自社品目コード
			+ "<td id='lblItemCode["
			+ idx
			+ "]' name='lblItemCode' class='color_blue'></td>"
			// 得意先品目コード
			+ "<td id='lblCustomerItemCode["
			+ idx
			+ "]' name='lblCustomerItemCode' class='color_blue'></td>"
			// 品名略称
			+ "<td id='lblItemNameR["
			+ idx
			+ "]' name='lblItemNameR' class='color_blue'></td>"
			// 量目
			+ "<td id='lblRyomoku["
			+ idx
			+ "]' name='lblRyomoku' class='color_blue align_center'></td>"
			// 入数
			+ "<td id='lblIrisu["
			+ idx
			+ "]' name='lblIrisu' class='color_blue align_right'></td>"
			// 受注数量（ケース）
			+ "<td class='align_right'>"
			+ "	<input id='lngJucCase["
			+ idx
			+ "]' name='lngJucCase' type='text' class='txt_width75per align_right border_none' maxlength='6' disabled='disabled' />"
			+ "</td>"
			// 受注数量（バラ)
			+ "<td class='align_right'>"
			+ "	<input id='lngJucSeparate["
			+ idx
			+ "]' name='lngJucSeparate' type='text' class='txt_width75per align_right border_none' maxlength='6' disabled='disabled' />"
			+ "</td>"
			// 納品単価
			+ "<td id='lblDeliPrice[" + idx
			+ "]' name='lblDeliPrice' class='color_blue align_right'></td>"
			// 販売単価
			+ "<td id='lblSalesPrice[" + idx
			+ "]' name='lblSalesPrice' class='color_blue align_right'></td>"
			// 納品金額
			+ "<td id='lblDeliAmt[" + idx
			+ "]' name='lblDeliAmt' class='align_right'></td>"
			// 売上明細Index（hidden）
			+ "<input id='hdnUriBodyIdx[" + idx
			+ "]' name='hdnUriBodyIdx' type='hidden' value='' />" + "</tr>";

	return rowHtml;
}

/**
 * リスト部の品目明細情報をクリアする
 * 
 * @param row
 *            値をクリアしたい行
 */
function clearItemDetail(row) {
	/**
	 * 画面表示項目
	 */
	// 自社品目コード
	$("td[name='lblItemCode']").eq(row).html("");
	// 得意先品目コード
	$("td[name='lblCustomerItemCode']").eq(row).html("");
	// 品名（品目略称）
	$("td[name='lblItemNameR']").eq(row).html("");
	// 量目
	$("td[name='lblRyomoku']").eq(row).html("");
	// 入数
	$("td[name='lblIrisu']").eq(row).html("");
	// 受注数量（ケース）
	$("input[name='lngJucCase']").eq(row).val("");
	$("input[name='lngJucCase']").eq(row).prop("disabled", true);
	$("input[name='lngJucCase']").eq(row).removeClass(CLS_ERROR_FIELD);
	// 受注数量（バラ）
	$("input[name='lngJucSeparate']").eq(row).val("");
	$("input[name='lngJucSeparate']").eq(row).prop("disabled", true);
	$("input[name='lngJucSeparate']").eq(row).removeClass(CLS_ERROR_FIELD);
	// 納品単価
	$("td[name='lblDeliPrice']").eq(row).html("");
	// 販売単価
	$("td[name='lblSalesPrice']").eq(row).html("");
	// 納品金額
	$("td[name='lblDeliAmt']").eq(row).html("");
	// 売上明細Index（hidden）
	$("input[name='hdnUriBodyIdx']").eq(row).val("");

	// 納品金額合計再計算
	var totalDeliAmt = 0;
	for (var idx = 0; idx < maxRow; idx++) {
		totalDeliAmt = Number(totalDeliAmt)
				+ Number(removeDcmlSeparate($("td[name='lblDeliAmt']").eq(idx)
						.html()));
	}
	$("#txtTotalDeliAmt").html(addDcmlSeparate(totalDeliAmt));
	$("#hdnTotalDeliAmt").val(totalDeliAmt);

	if (flgPriceMaxError) {
		clearPriceMaxError();
	}
}
/*******************************************************************************
 * Ajax処理
 ******************************************************************************/
/**
 * 事業所名取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getJigyoNm() {
	var rtnVal = false;
	$("#tbl_tbody").html("");
	$.ajax({
		type : "POST",
		url : "getJigyoNm",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strJigyoCode != null) {
				$("#lblJigyoNm").html(data[0].strJigyoName);
				$("#hdnJigyoName").val(data[0].strJigyoName);
				rtnVal = true;
			} else {
				strErrorMessage = data[0].strMessage;
				rtnVal = false;
			}
		},
		error : function(jqXHR) {
			document.head.innerHTML = "";
			document.body.innerHTML = jqXHR.responseText;
		},
		complete : function(jqXHR, textStatus) {
		}
	});
	return rtnVal;
}

/**
 * 金額上限値取得＠Ajax
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getDeliPriceMax() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "getDeliPriceMax",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			$("#hdnPriceMax").val(data[0].deliPriceMax);
			rtnVal = true;
		},
		error : function(jqXHR) {
			document.head.innerHTML = "";
			document.body.innerHTML = jqXHR.responseText;
		},
		complete : function(jqXHR, textStatus) {
		}
	});
	return rtnVal;
}

/**
 * 売上情報取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getUriageData() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "getUriageData",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
				uriHead = data[0].uriData.uriHead;
				uriBodyList = JSON.parse(data[0].uriData.uriBodyJson);
				updUserName = data[0].uriData.updUserName;
				rtnVal = true;
			} else {
				strErrorMessage = data[0].strMessage;
				rtnVal = false;
			}
		},
		error : function(jqXHR) {
			document.head.innerHTML = "";
			document.body.innerHTML = jqXHR.responseText;
		},
		complete : function(jqXHR, textStatus) {
		}
	});
	return rtnVal;
}

/**
 * 売上情報登録
 * 
 * @return 処理結果（true：正常、false：異常）
 */
function registryUriageData() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "registryUriageData",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
				uriHead = data[0].uriData.uriHead;
				rtnVal = true;
			} else {
				strErrorMessage = data[0].strMessage;
				rtnVal = false;
			}
		},
		error : function(jqXHR) {
			document.head.innerHTML = "";
			document.body.innerHTML = jqXHR.responseText;
		},
		complete : function(jqXHR, textStatus) {
		}
	});
	return rtnVal;
}

/**
 * 請求元売上情報取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getBillSrcUriageData() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "getBillSrcUriageData",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
				$("#hdnJsonBillSrc").val(returnData);
				uriOriginHead = data[0].uriData.uriHead;
				uriOriginBodyList = JSON.parse(data[0].uriData.uriBodyJson);
				rtnVal = true;
			} else {
				strErrorMessage = data[0].strMessage;
				rtnVal = false;
			}
		},
		error : function(jqXHR) {
			document.head.innerHTML = "";
			document.body.innerHTML = jqXHR.responseText;
		},
		complete : function(jqXHR, textStatus) {
		}
	});
	return rtnVal;
}
