/**
 * ファイル名:uri0101d01.js 概要:売上登録画面
 * 
 * 作成者:AB）前田 作成日:2015/10/15
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB）前田 新規開発
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
var deliDateFocus = null; // 納品日Focus対象
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
var uriOriginBody = null; // 売上明細（返品／欠品元）
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
					screenStatus = CONST_SCREEN_BODY1;

					strContextPath = $("#hdnContextPath").val();
					btnSearchDisabled = strContextPath
							+ "/resources/css/images/search_dis.png";
					btnSearchEnabled = strContextPath
							+ "/resources/css/images/search.png";

					loginJigyoCode = $("#txtJigyoCode").val(); // ログイン事業所コード
					loginJigyoName = $("#lblJigyoNm").html(); // ログイン事業所名
					loginUserCode = $("#lblRegistUserCode").html(); // ログイン担当者コード
					loginUserName = $("#lblRegistUserName").html(); // ログイン担当者名
					loginUserNameKana = $("#hdnRegistUserNameKana").val(); // ログイン担当者名カナ
					$("#hdnJigyoName").val(loginJigyoName);

					// エラー表示域初期化
					removeInfoClass();

					// エラー表示
					var err = $(".checkInputError").html();
					if (err != null) {
						$("#txtMess").html(err);
						errorControl();
					}

					var errControl = $('#errorControl').val();
					if (errControl != "") {
						alert(errControl);
						// errorControl();
					}

					/** コード検索画面 */
					loadDialogCom0102d02(); // 得意先検索
					loadDialogCom0102d03(); // 店舗検索
					loadDialogCom0102d04(); // 品番検索

					/** 取消処理モード選択ダイアログ */
					loadDialogDelDialogJucUri();

					// 処理区分取得
					operateMode = $("#hdnPrmShoriKb").val();
					if (operateMode == "") {
						operateMode = OPERATE_REGIST;
					}
					// alert("operateMode = " + operateMode);

					if ($("#txtMess").html().trim() == "") {
						if (operateMode == OPERATE_REGIST) {
							// 処理区分 = 新規登録
							$("#rad_New").prop("checked", true);

							// 画面表示制御
							showScreen(operateMode);

							// ボタン部コントロール
							controlButtons(screenStatus);

							// フォーカスセット
							setFocus($("#hdnSysAdminFlg").val());
						} else {
							if (operateMode == OPERATE_MODIFY) {
								// 処理区分 = 訂正
								$("#rad_Correct").prop("checked", true);
							} else if (operateMode == OPERATE_CANCEL) {
								// 処理区分 = 取消
								$("#rad_Cancel").prop("checked", true);
							} else if (operateMode == OPERATE_VIEW) {
								// 処理区分 = 照会
								$("#rad_Query").prop("checked", true);
							}
							// 画面表示制御
							showScreen(operateMode);

							// 自社伝票No
							$("#txtSlipNo").val($("#hdnPrmDenNo").val());
							$("#hdnSlipNo").val($("#hdnPrmDenNo").val());

							// 売上情報取得
							if (!execGetUriageData()) {
								$("#txtMess").html(strErrorMessage);
							}

							// ボタン部コントロール
							controlButtons(screenStatus);
						}
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

						$("#txtSlipNo").removeClass(CLS_ERROR_FIELD);
						$("#txtMess").html("");

						showScreen(operateMode);

						$("#txtJigyoCode").val($("#hdnLoginJigyoCode").val());
						$("#hdnJigyoCode").val($("#hdnLoginJigyoCode").val());
					});

					/**
					 * 修正区分
					 */
					// TODO 修正区分 GetFoCus
					$("#ddlModifyKb").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});

					/**
					 * 修正区分 Change
					 */
					$("#ddlModifyKb").bind("change", function() {
						$(this).removeClass(CLS_ERROR_FIELD);
						$("#txtMess").html("");
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
							if (!execGetUriageData()) {
								return;
							}
							;
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
					});

					/**
					 * 事業所コードLostFocus
					 */
					$("#txtJigyoCode")
							.bind(
									"blur",
									function() {
										$("#hdnJigyoCode").val($(this).val());
										$("#ddlSlipKb").removeClass(
												CLS_ERROR_FIELD);
										$("#txtMess").html("");

										// 事業所コードがブランクの場合、事業所名をブランクにする
										if ($(this).val() == ""
												|| $(this).val() == null) {
											$("#lblJigyoNm").html("");
											switchCustomerCode(false);
											switchShopCode(false);
										} else {
											if ($(this).val() != jigyoCodeWK) {
												$("#txtCustomerCode").val("");
												$("#hdnCustomerCode").val("");
												$("#hdnCustomerName").val("");
												$("#hdnCustomerNameR").val("");
												$("#hdnCustomerNameK").val("");
												$("#lblCustomerNameR").html("");
												mstCustomer = null;
											}
											// 属性チェック
											var result = chkType($(this).val(),
													TYPE_NUM, false, null)
											if (result != null) {
												$("#txtMess")
														.html(
																$("#COM001-E")
																		.val()
																		.replace(
																				"{1}",
																				"事業所コード"));
												$("#lblJigyoNm").html("");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												switchCustomerCode(false); /* 得意先 */
												switchShopCode(false); /* 店舗 */
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
													switchCustomerCode(true); /* 得意先 */
													switchShopCode(false); /* 店舗 */
													$("#txtCustomerCode")
															.focus().select();
												} else {
													// 失敗
													$("#txtMess").html(
															strErrorMessage);
													$("#lblJigyoNm").html("");
													$(this).focus().select();
													$(this).addClass(
															CLS_ERROR_FIELD);
													switchCustomerCode(false); /* 得意先 */
													switchShopCode(false); /* 店舗 */
												}
											}
										}
									});

					/**
					 * 伝票区分Change
					 */
					// TODO 伝票区分
					$("#ddlSlipKb")
							.bind(
									"change",
									function() {
										$("#hdnSlipKb").val($(this).val());

										if ($(this).val() != "") {
											$("#txtMess").html("");
											if (mstCustomer != null) {
												if ((mstCustomer.shopKb.trim() == "1" && mstShop != null)
														|| mstCustomer.shopKb
																.trim() == "2") {
													$(this).removeClass(
															'form-error-field');
													switchBodyOver(false);
													showBodyUnder(mstCustomer,
															mstShop)
												}
											}
										}
									});

					/**
					 * 得意先コードGetFocus
					 */
					// TODO 得意先コード
					$("#txtCustomerCode").bind("focus", function() {
						customerCodeWK = $(this).val();
					});

					/**
					 * 得意先コードLostFocus
					 */
					$("#txtCustomerCode")
							.bind(
									"blur",
									function() {
										$("#hdnCustomerCode")
												.val($(this).val());

										// 得意先コードがブランクの場合、得意先名をブランクにし、店舗を非活性化
										if ($(this).val() == ""
												|| $(this).val() == null) {
											if (!$("#ddlSlipKb").hasClass(
													CLS_ERROR_FIELD)) {
												$("#txtMess").html("");
											}
											$(this)
													.removeClass(
															CLS_ERROR_FIELD);
											$("#lblCustomerNameR").html("");
											$("#hdnCustomerName").val("");
											$("#hdnCustomerNameR").val("");
											$("#hdnCustomerNameK").val("");
											$(".txt_tax").hide();
											switchShopCode(false);
											mstCustomer = null;
										} else {
											// 属性チェック
											var result = chkType($(this).val(),
													TYPE_NUM, false, null)
											if (result != null) {
												strErrorMessage = $("#COM001-E")
														.val().replace("{1}",
																"得意先コード");
												$("#txtMess").html(
														strErrorMessage);
												$("#lblCustomerNameR").html("");
												$("#hdnCustomerName").val("");
												$("#hdnCustomerNameR").val("");
												$("#hdnCustomerNameK").val("");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												return;
											} else {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}

											// 得意先情報取得
											if (getCustomerInfo()) {
												$(this).removeClass(
														CLS_ERROR_FIELD);
												$("#lblCustomerNameR").html(
														mstCustomer.custNmR
																.trim());
												$("#hdnCustomerName").val(
														mstCustomer.custNm
																.trim());
												$("#hdnCustomerNameR").val(
														mstCustomer.custNmR
																.trim());
												$("#hdnCustomerNameK").val(
														mstCustomer.custNmKana
																.trim());
												$("#txtMess").html("");
												$("#hdnShopKb").val(
														mstCustomer.shopKb
																.trim());
												$("#hdnChainCode").val(
														mstCustomer.cainCode);
												$("#hdnChainIdx").val(
														mstCustomer.cainIdx);
												if (mstCustomer.taxIncKb.trim() == "1") {
													// 内税顧客区分 = "1"（外税）の場合、
													$(".txt_tax").hide();
												} else {
													// 内税顧客区分 = "2"（内税）の場合、
													$(".txt_tax").show();
												}
												if (mstCustomer.shopKb.trim() == "1") {
													// 店舗区分 = "1"（店有り）の場合、
													switchShopCode(true); /* 店舗 */
													$("#txtShopCode").focus()
															.select();
												} else {
													// 店舗区分 = "2"（店無し）の場合、
													$("#hdnDeliCode")
															.val(
																	mstCustomer.deliCenterCode
																			.trim());
													showBodyUnder(mstCustomer,
															null);
												}
											} else {
												$("#txtMess").html(
														strErrorMessage);
												$("#lblCustomerNameR").html("");
												$("#hdnCustomerName").val("");
												$("#hdnCustomerNameR").val("");
												$("#hdnCustomerNameK").val("");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												switchShopCode(false); /* 店舗 */
											}
										}
									});

					/**
					 * 得意先検索ボタンClick
					 */
					$("#btnSearchCustomer").on("click", function() {
						var jigyoCode = $("#hdnJigyoCode").val(); // 事業所コード
						var custCode = $("#hdnCustomerCode").val(); // 得意先コード

						// ダイアログの表示（店舗区分 = 店有り＆店無し ＆ 検索対象区分 = 得意先）
						showCom0102d02(jigyoCode, custCode, "", 1);
					});

					/**
					 * 店舗コードGetFocus
					 */
					// TODO 店舗コード
					$("#txtShopCode").bind("focus", function() {
						shopCodeWK = $(this).val();
					});

					/**
					 * 店舗コードLostFocus
					 */
					$("#txtShopCode").bind(
							"blur",
							function() {
								$("#hdnShopCode").val($(this).val());

								// 店舗コードがブランクの場合、店舗名をブランクにする
								if ($(this).val() == ""
										|| $(this).val() == null) {
									if (!$("#ddlSlipKb").hasClass(
											CLS_ERROR_FIELD)) {
										$("#txtMess").html("");
									}
									$(this).removeClass(CLS_ERROR_FIELD);
									$("#lblShopNm").html("");
									$("#hdnShopName").val("");
									$("#hdnShopNameR").val("");
									$("#hdnShopNameK").val("");
									mstShop = null;
								} else {
									// 属性チェック
									var result = chkType($(this).val(),
											TYPE_NUM, false, null)
									if (result != null) {
										strErrorMessage = $("#COM001-E").val()
												.replace("{1}", "店舗コード");
										$("#txtMess").html(strErrorMessage);
										$("#lblShopNm").html("");
										$("#hdnShopName").val("");
										$("#hdnShopNameR").val("");
										$("#hdnShopNameK").val("");
										$(this).focus().select();
										$(this).addClass(CLS_ERROR_FIELD);
										return;
									} else {
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);
									}

									// 店舗情報取得
									if (getShopInfo()) {
										$(this).removeClass(CLS_ERROR_FIELD);
										$("#lblShopNm").html(
												mstShop.shopNmR.trim());
										$("#hdnShopName").val(
												mstShop.shopNm.trim());
										$("#hdnShopNameR").val(
												mstShop.shopNmR.trim());
										$("#hdnShopNameK").val(
												mstShop.shopNmKana.trim());
										$("#hdnDeliCode").val(
												mstShop.deliCenterCode.trim());
										$("#txtMess").html("");

										showBodyUnder(mstCustomer, mstShop);
									} else {
										$("#txtMess").html(strErrorMessage);
										$("#lblShopNm").html("");
										$("#hdnShopName").val("");
										$("#hdnShopNameR").val("");
										$("#hdnShopNameK").val("");
										$(this).focus().select();
										$(this).addClass(CLS_ERROR_FIELD);
									}
								}
							});

					/**
					 * 店舗検索ボタンClick
					 */
					$("#btnSearchShop").on("click", function() {
						var custCode = $("#hdnCustomerCode").val(); // 得意先コード
						var shopCode = $("#hdnShopCode").val(); // 店舗コード

						// ダイアログの表示
						showCom0102d03(custCode, shopCode);
					});

					/***********************************************************
					 * ボディ部（下段）
					 **********************************************************/
					/**
					 * 伝票発行GetFocus
					 */
					// TODO 伝票発行
					$("#txtSlipOut").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});
					/**
					 * 伝票発行LostFocus
					 */
					$("#txtSlipOut").bind(
							"blur",
							function() {
								$("#hdnSlipOut").val($(this).val());

								// 伝票発行がブランクの場合、処理終了
								if ($(this).val() == ""
										|| $(this).val() == null) {
									if (!flgErrorEx) {
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);
									}
								} else {
									// 伝票発行チェック
									var result = checkSlipOut($(this).val());
									if (result == null) {
										if (!flgErrorEx) {
											// 成功
											$("#txtMess").html("");
										}
										$(this).removeClass(CLS_ERROR_FIELD);
									} else {
										// 失敗
										strErrorMessage = $("#" + result).val()
												.replace("{1}", "伝票発行");
										$(this).focus().select();
										$(this).addClass(CLS_ERROR_FIELD);
									}
								}
							});

					/**
					 * 納品日GetFocus
					 */
					// TODO 納品日
					$("#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay")
							.bind("focus", function() {
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});
					/**
					 * 納品日LostFocus
					 */
					$("#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay")
							.bind(
									"blur",
									function() {
										$("#hdnDeliDateYear").val(
												$("#txtDeliDateYear").val());
										$("#hdnDeliDateMonth").val(
												$("#txtDeliDateMonth").val());
										$("#hdnDeliDateDay").val(
												$("#txtDeliDateDay").val());

										if ($("#txtDeliDateYear").val() == ""
												|| $("#txtDeliDateMonth").val() == ""
												|| $("#txtDeliDateDay").val() == "") {
											// 納品年月日、エラーメッセージクリア
											$("#hdnDeliYmd").val("");
											if (!flgErrorEx) {
												$("#txtMess").html("");
												$(
														"#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay")
														.removeClass(
																CLS_ERROR_FIELD);
											}
										}

										if ($("#txtDeliDateYear").val() != ""
												&& $("#txtDeliDateMonth").val() != ""
												&& $("#txtDeliDateDay").val() != "") {
											var year = $("#txtDeliDateYear")
													.val();
											var month = "";
											var day = "";

											// 0埋め処理
											if ($("#txtDeliDateMonth").val().length == 1) {
												month = "0"
														+ $("#txtDeliDateMonth")
																.val();
											} else {
												month = $("#txtDeliDateMonth")
														.val();
											}
											if ($("#txtDeliDateDay").val().length == 1) {
												day = "0"
														+ $("#txtDeliDateDay")
																.val();
											} else {
												day = $("#txtDeliDateDay")
														.val();
											}

											// 納品日生成
											var deliDate = year + month + day;

											// 納品年月日と納品日が異なれば、値をセットし、changeイベント発火
											if (deliDate != $("#hdnDeliYmd")
													.val()) {
												deliDateFocus = $(this);
												$("#hdnDeliYmd").val(deliDate)
														.change();
											}

											// 納品年月 != hdnAplDate（年月） && 納品年月 =
											// hdnDetermMonth（月跨ぎ）の場合、
											// 計上日を納品年月 ＋ 32日（入力可）に変更
											if ($("#hdnDetermMonth").val() != $(
													"#hdnAplDate").val()
													.substr(0, 6)) {
												if ($("#hdnDetermMonth").val() == (year + month)) {
													$("#lblAccountDate").html(
															year + "/" + month
																	+ "/");
													$("#txtAccountDay").val(
															accountDayWK)
															.show();
													$("#orangeLineAccount")
															.addClass(
																	"orange_line");
													flgAccount = true;
												} else {
													$("#lblAccountDate")
															.html(
																	createAccountDate(
																			$(
																					"#hdnAplDate")
																					.val(),
																			$(
																					"#hdnAddDay")
																					.val()));
													$("#txtAccountDay").hide();
													$("#orangeLineAccount")
															.removeClass(
																	"orange_line");
													flgAccount = false;
												}
											}
										} else {
										}
									});

					/**
					 * 納品日（年）LostFocus 表示/非表示を動的に切り替える計上日にFocusが当たらないための措置
					 */
					$("#txtDeliDateYear").bind("blur", function() {
						// 納品日エラー中は納品日（年）に戻る
						if ($(this).hasClass(CLS_ERROR_FIELD) && !flgErrorEx) {
							if (flgShift) {
								$("#txtDeliDateDay").focus().select();
							}
						}
					});
					/**
					 * 納品日（日）LostFocus 表示/非表示を動的に切り替える計上日にFocusが当たらないための措置
					 */
					$("#txtDeliDateDay").bind("blur", function() {
						// 納品日エラー中は納品日（年）に戻る
						if ($(this).hasClass(CLS_ERROR_FIELD) && !flgErrorEx) {
							if (flgShift) {
								$("#txtDeliDateMonth").focus().select();
							} else {
								$("#txtDeliDateYear").focus().select();
							}
						} else {
							// 計上日表示中
							if (flgAccount) {
								// Shift KeyDown
								if (flgShift) {
									// 納品日（月）
									$("#txtDeliDateMonth").focus().select();
								} else {
									// 計上日
									$("#txtAccountDay").focus().select();
								}
							} else {
								// Shift KeyDown
								if (flgShift) {
									// 納品日（月）
									$("#txtDeliDateMonth").focus().select();
								} else {
									// 返品／欠品元伝票No.
									$("#txtOriginSlipNo").focus().select();
								}
							}
						}
					})

					/**
					 * 納品年月日（隠し項目）Change
					 */
					$("#hdnDeliYmd")
							.bind(
									"change",
									function() {
										var aplDate = $("#hdnAplDate").val(); // 業務日付
										var determMon = $("#hdnDetermMonth")
												.val(); // 会計月

										// 納品日チェック
										var result = checkDeliDate($(this)
												.val(), aplDate, determMon);
										if (result == null) {
											if (!flgErrorEx) {
												// エラーメッセージクリア
												$("#txtMess").html("");
											}
											$(
													"#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay")
													.removeClass(
															CLS_ERROR_FIELD);
										} else if (result == "COM001-E") {
											strErrorMessage = $("#" + result)
													.val()
													.replace("{1}", "納品日");
											// $("#txtMess").html(strErrorMessage);
											$(
													"#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay")
													.addClass(CLS_ERROR_FIELD);
											deliDateFocus.focus().select();
											// alert("deliDateFocus = " +
											// deliDateFocus.val());
										} else if (result == "COM003-E") {
											var fromY = determMon.substring(0,
													4);
											var fromM = determMon.substring(4,
													6);
											var fromD = "01";
											var from = [ fromY, fromM, fromD ]
													.join("/");
											var toY = aplDate.substring(0, 4);
											var toM = aplDate.substring(4, 6);
											var toD = aplDate.substring(6, 8);
											var to = [ toY, toM, toD ]
													.join("/");

											strErrorMessage = $("#" + result)
													.val().replace("{1}", from)
													.replace("{2}", to);
											// $("#txtMess").html(strErrorMessage);
											$(
													"#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay")
													.addClass(CLS_ERROR_FIELD);
											deliDateFocus.focus().select();
										}
									});

					/**
					 * 計上日GetFocus
					 */
					// TODO 計上日
					$("#txtAccountDay").bind("focus", function() {
						accountDayWK = $(this).val();
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});
					/**
					 * 計上日LostFocus
					 */
					$("#txtAccountDay").bind(
							"blur",
							function() {
								$("#hdnAccountDay").val($(this).val());

								// 計上日がブランクの場合、処理終了
								if ($(this).val() == ""
										|| $(this).val() == null) {
									$("#txtMess").html("");
									$(this).removeClass(CLS_ERROR_FIELD);
								} else {
									// 属性チェック
									var result = chkType($(this).val(),
											TYPE_NUM, false, null)
									if (result != null) {
										strErrorMessage = $("#COM001-E").val()
												.replace("{1}", "計上日");
										$(this).focus().select();
										$(this).addClass(CLS_ERROR_FIELD);
										return;
									} else {
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);
									}

									// 計上年月を年・月に分割
									var lst = $("#lblAccountDate").html()
											.split("/");
									// 計上日0埋め
									if ($(this).val().length == 1) {
										var day = "0" + $(this).val();
									} else {
										var day = $(this).val();
									}
									// 計上日チェック
									var result = checkAccDate(lst[0], lst[1],
											day);
									if (result != null) {
										strErrorMessage = $("#" + result).val()
												.replace("{1}", "計上日");
										$(this).addClass(CLS_ERROR_FIELD);
										$(this).focus().select();
									} else {
										// エラーメッセージクリア
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);
									}
								}
							});

					/**
					 * 返品／欠品元伝票No LostFocus
					 */
					// TODO 返品／欠品元伝票No
					$("#txtOriginSlipNo").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});
					/**
					 * 返品／欠品元伝票No LostFocus
					 */
					$("#txtOriginSlipNo").bind("blur", function() {
						$("#hdnOriginSlipNo").val($(this).val());
						$(this).removeClass(CLS_ERROR_FIELD);
						$("#txtMess").html("");
					});
					/**
					 * 便区分GetFocus
					 */
					// TODO 便区分
					$("#txtBinKb").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});
					/**
					 * 便区分LostFocus
					 */
					$("#txtBinKb")
							.bind(
									"blur",
									function() {
										$("#hdnBinKb").val($(this).val());

										// 便区分がブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											if (!flgErrorEx) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}
											$("#lblCourseCode").html("");
											$("#lblCourseNameR").html("");
											$("#hdnCourseCode").val("");
											$("#hdnCourseName").val("");
											$("#hdnCourseNameR").val("");
										} else {
											// 便区分チェック
											var result = checkBinKb($(this)
													.val())
											if (result == null) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											} else if (result == "COM001-E") {
												strErrorMessage = $(
														"#" + result).val()
														.replace("{1}", "便区分");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												return;
											} else if (result == "COM009-E") {
												strErrorMessage = $(
														"#" + result)
														.val()
														.replace("{1}", "汎用マスタ")
														.replace("{2}", "便区分情報");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												return;
											}

											// 便区分、納品区分が未入力でない時、コース情報取得（事業所・得意先・店舗は取得済）
											if ($("#hdnBinKb").val() != ""
													&& $("#hdnDeliKb").val() != "") {
												// コース情報取得
												if (getCourseData()) {
													if (!flgErrorEx) {
														$("#txtMess").html("");
													}
													$("#txtBinKb").removeClass(
															CLS_ERROR_FIELD);
													$("#txtDeliKb")
															.removeClass(
																	CLS_ERROR_FIELD);
													$("#lblCourseCode").html(
															mstCourse.cosCode
																	.trim());
													$("#lblCourseNameR").html(
															mstCourse.cosNmR
																	.trim());
													$("#hdnCourseCode").val(
															mstCourse.cosCode
																	.trim());
													$("#hdnCourseName").val(
															mstCourse.cosNm
																	.trim());
													$("#hdnCourseNameR").val(
															mstCourse.cosNmR
																	.trim());
												} else {
													strErrorMessage = $(
															"#COM009-E").val()
															.replace("{1}",
																	"コースマスタ")
															.replace("{2}",
																	"コース情報");
													$("#txtBinKb").addClass(
															CLS_ERROR_FIELD);
													$("#txtDeliKb").addClass(
															CLS_ERROR_FIELD);
													$("#txtMess").html(
															strErrorMessage);
													$("#lblCourseCode")
															.html("");
													$("#lblCourseNameR").html(
															"");
													$("#hdnCourseCode").val("");
													$("#hdnCourseName").val("");
													$("#hdnCourseNameR")
															.val("");
													$("#txtBinKb").focus()
															.select();
												}
											}
										}
									});

					/**
					 * 納品区分GetFocus
					 */
					// TODO 納品区分
					$("#txtDeliKb").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});
					/**
					 * 納品区分LostFocus
					 */
					$("#txtDeliKb")
							.bind(
									"blur",
									function() {
										$("#hdnDeliKb").val($(this).val());

										// 納品区分がブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											if (!flgErrorEx) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);

											}
											$("#lblCourseCode").html("");
											$("#lblCourseNameR").html("");
											$("#hdnCourseCode").val("");
											$("#hdnCourseName").val("");
											$("#hdnCourseNameR").val("");
										} else {
											// 納品区分チェック
											var result = checkDeliKb($(this)
													.val())
											if (result != null) {
												strErrorMessage = $(
														"#" + result).val()
														.replace("{1}", "納品区分");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												return;
											} else {
												if (!flgErrorEx) {
													$("#txtMess").html("");
												}
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}

											// 便区分、納品区分が未入力でない時、コース情報取得（事業所・得意先・店舗は取得済）
											if ($("#hdnBinKb").val() != ""
													&& $("#hdnDeliKb").val() != "") {
												// コース情報取得
												if (getCourseData()) {
													if (!flgErrorEx) {
														$("#txtMess").html("");
													}
													$("#txtBinKb").removeClass(
															CLS_ERROR_FIELD);
													$("#txtDeliKb")
															.removeClass(
																	CLS_ERROR_FIELD);
													$("#lblCourseCode").html(
															mstCourse.cosCode
																	.trim());
													$("#lblCourseNameR").html(
															mstCourse.cosNmR
																	.trim());
													$("#hdnCourseCode").val(
															mstCourse.cosCode
																	.trim());
													$("#hdnCourseName").val(
															mstCourse.cosNm
																	.trim());
													$("#hdnCourseNameR").val(
															mstCourse.cosNmR
																	.trim());
												} else {
													strErrorMessage = $(
															"#COM009-E").val()
															.replace("{1}",
																	"コースマスタ")
															.replace("{2}",
																	"コース情報");
													$("#txtMess").html(
															strErrorMessage);
													$("#txtBinKb").addClass(
															CLS_ERROR_FIELD);
													$("#txtDeliKb").addClass(
															CLS_ERROR_FIELD);
													$("#lblCourseCode")
															.html("");
													$("#lblCourseNameR").html(
															"");
													$("#hdnCourseCode").val("");
													$("#hdnCourseName").val("");
													$("#hdnCourseNameR")
															.val("");
													$("#txtBinKb").focus()
															.select();
												}
											}
										}
									});

					/**
					 * 販売区分GetFocus
					 */
					// TODO 販売区分
					$("#txtSalesKb").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});

					/**
					 * 販売区分LostFocus
					 */
					$("#txtSalesKb").bind(
							"blur",
							function() {
								$("#hdnSalesKb").val($(this).val());

								// 販売区分がブランクの場合、処理終了
								if ($(this).val() == ""
										|| $(this).val() == null) {
									if (!flgErrorEx) {
										$("#txtMess").html("");
										$(this).removeClass(CLS_ERROR_FIELD);
									}
								} else {
									// 販売区分チェック
									var result = checkSalesKb($(this).val())
									if (result != null) {
										strErrorMessage = $("#" + result).val()
												.replace("{1}", "販売区分");
										$(this).focus().select();
										$(this).addClass(CLS_ERROR_FIELD);
										return;
									} else {
										if (!flgErrorEx) {
											$("#txtMess").html("");
										}
										$(this).removeClass(CLS_ERROR_FIELD);
									}
								}
							});

					/**
					 * 先方伝票No GetFocus
					 */
					// TODO 先方伝票No
					$("#txtSnpSlipNo").bind("focus", function() {
						// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
						// でエラー判別し、focus内でメッセージ表示
						if ($(this).hasClass(CLS_ERROR_FIELD)) {
							$("#txtMess").html(strErrorMessage);
						}
					});

					/**
					 * 先方伝票No LostFocus
					 */
					$("#txtSnpSlipNo")
							.bind(
									"blur",
									function() {
										$("#hdnSnpSlipNo").val($(this).val());
										// 販売区分がブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											$("#txtMess").html("");
											$(this)
													.removeClass(
															CLS_ERROR_FIELD);
										} else {
											// 先方伝票Noチェック
											var result = checkSnpSlipNo($(this)
													.val());
											if (result != null) {
												strErrorMessage = $(
														"#" + result).val()
														.replace("{1}",
																"先方伝票No");
												$(this).focus().select();
												$(this).addClass(
														CLS_ERROR_FIELD);
												return;
											} else {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}
										}

										// ボディ部（下段）チェック
										if (!checkBodyUnder()) {
											return;
										} else {
											flgErrorEx = false;
										}

										// 納品先情報取得
										if (getDeliData()) {
											// 納品先情報格納
											$("#hdnDeliName").val(
													mstSnohin.nhsmei.trim());
											$("#hdnDeliNameKana").val(
													mstSnohin.nhskana.trim());
											// alert("[mstSnohin]nhsmei = " +
											// $("#hdnDeliName").val() + ",
											// nhskana = " +
											// $("#hdnDeliNameKana").val());

											// チェーン情報格納
											$("#hdnChainName").val(
													mstSchain.chnmei.trim());
											$("#hdnChainSalesKb").val(
													mstSchain.hanbaikbn);
											$("#hdnBillRudKb").val(
													mstSchain.sikirikbn);
											$("#hdnBillRudPoint").val(
													mstSchain.sikiriketa);
											$("#hdnBillAmtRudKb").val(
													mstSchain.kinmarukbn);
											$("#txtMess").html("");
										} else {
											$("#txtMess").html(strErrorMessage);
											return;
										}

										// ボディ部（下段）非活性化
										switchBodyUnder(false);

										// リスト部表示
										var listHtml = "";
										// 伝票行数分のテーブル行を生成する
										for (var idx = 0; idx < maxRow; idx++) {
											listHtml = listHtml
													+ createBlankRow(idx, false);
										}
										$(".data_result_area").show(); // リスト部可視化
										$("#tblBodyRow").html(listHtml); // HTMLセット
										$("#tblHead [id = 'headMofifyKbList']")
												.hide(); // テーブル見出し部

										// 画面表示状態
										screenStatus = CONST_SCREEN_LIST;

										// ボタンコントロール
										controlButtons(screenStatus);

										// 先頭行の品目コードへフォーカスセット
										$("input[name='txtItemCode']").eq(0)
												.focus();
									});

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
					 * 自社品目コードGetFocus
					 */
					// TODO 自社品目コード
					$("#tblBody").on("focus", "input[name='txtItemCode']",
							function() {
								itemCodeWK = $(this).val();
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});
					/**
					 * 自社品目コードLostFocus
					 */
					$("#tblBody")
							.on(
									"blur",
									"input[name='txtItemCode']",
									function() {
										// 金額エラー発生中 and 品目コードが変更なしの場合、
										if (flgPriceMaxError
												&& $(this).val() == itemCodeWK) {
											return;
										}
										// 品目検索状況が未検索でない場合は処理終了
										if (itemSearchStatus != -1) {
											return;
										}
										// 自社品目コードがブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											$("#txtMess").html("");
											$(this)
													.removeClass(
															CLS_ERROR_FIELD);

											// 得意先品目コードでエラーが発生していない場合、品目明細情報項目クリア
											if (!$(
													"input[name='txtCustomerItemCode']")
													.eq(selectedRow).hasClass(
															CLS_ERROR_FIELD)
													&& !$(
															"input[name='lngSalesPrice']")
															.eq(selectedRow)
															.hasClass(
																	CLS_ERROR_FIELD)) {
												clearItemDetail(selectedRow);
											}
										} else {
											if ($(this).val() == itemCodeWK) {
												focusFromItemCode();
												return;
											}
											// 品目コードチェック
											var result = checkItemCode($(this)
													.val());
											if (result != null) {
												strErrorMessage = $(
														"#" + result).val()
														.replace("{1}",
																"自社品目コード");
												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();

												return;
											} else {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}

											// 品目情報クリア（品目コードは一旦退避し、クリア後に再セット）
											var itemCd = $(this).val();
											clearItemDetail(selectedRow);
											$(this).val(itemCd);

											// 品目情報取得
											if (getItemInfo($(this).val(), 0)) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
												$(
														"input[name='txtCustomerItemCode']")
														.eq(selectedRow)
														.removeClass(
																CLS_ERROR_FIELD);

												// 品目明細情報項目セット
												setItemDetail(selectedRow, "1");

												// 分類コードチェック
												if (checkClassCode()) {
													// 自社品目コード活性化、得意先品目コード非活性化
													$(
															"input[name='txtItemCode']")
															.eq(selectedRow)
															.prop("disabled",
																	false);
													$(
															"input[name='txtCustomerItemCode']")
															.eq(selectedRow)
															.prop("disabled",
																	true);
													$(
															"img[name='btnSearchItem']")
															.eq(selectedRow)
															.prop("disabled",
																	false);
													$(
															"img[name='btnSearchItem']")
															.eq(selectedRow)
															.attr("src",
																	btnSearchEnabled);

													// 受注数量（ケース）にフォーカスセット
													$(
															"input[name='lngJucCase']")
															.eq(selectedRow)
															.focus();
												} else {
													// 品目情報クリア（品目コードは一旦退避し、クリア後に再セット）
													var itemCd = $(this).val();
													clearItemDetail(selectedRow);
													$(this).val(itemCd);

													// JUC001-E
													strErrorMessage = $(
															"#JUC001-E").val();
													$(this).addClass(
															CLS_ERROR_FIELD);
													$(this).focus().select();
												}
											} else {
												// 品目情報クリア（品目コードは一旦退避し、クリア後に再セット）
												var itemCd = $(this).val();
												clearItemDetail(selectedRow);
												$(this).val(itemCd);

												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();
											}
											// 品目検索状況をリセット
											itemSearchStatus = -1;
										}
										focusFromItemCode();
									});

					/**
					 * 品目検索ボタンClick btnSearchItem
					 */
					$("#tblBody").on(
							"click",
							"img[name='btnSearchItem']",
							function() {
								// selectedRowの更新
								var tmpId = $(this).attr("id");
								selectedRow = tmpId.substring(tmpId
										.indexOf("[") + 1, tmpId.indexOf("]"));

								// 編集中（フォーカスが当たった）行のIndexを取得
								var changeCodeWk = $("#hdnChainCode").val(); // チェーンコード
								var changeBranchWk = $("#hdnChainIdx").val(); // チェーン枝番
								var ourCompanyItemCodeWk = $(
										"input[name='txtItemCode']").eq(
										selectedRow).val(); // 自社品目コード
								var customerCodeWk = $("#hdnCustomerCode")
										.val(); // 得意先コード
								var saleTypeWk = $("#hdnSalesKb").val(); // 販売区分
								var deadlineWk = $("#hdnDeliYmd").val(); // 納品日
								var flightWk = $("#hdnBinKb").val(); // 便

								// ダイアログの表示
								showCom0102d04(changeCodeWk, changeBranchWk,
										ourCompanyItemCodeWk, customerCodeWk,
										saleTypeWk, deadlineWk, flightWk);
							});

					/**
					 * 得意先品目コードGetFocus
					 */
					// TODO 得意先品目コード
					$("#tblBody").on("focus",
							"input[name='txtCustomerItemCode']", function() {
								customerItemCodeWK = $(this).val();
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});

					/**
					 * 得意先品目コードLostFocus
					 */
					$("#tblBody")
							.on(
									"blur",
									"input[name='txtCustomerItemCode']",
									function() {
										if (itemSearchStatus != -1) {
											return;
										}
										// 金額エラー発生中 and 品目コードが変更なしの場合、
										if (flgPriceMaxError
												&& $(this).val() == customerItemCodeWK) {
											return;
										}
										// 得意先品目コードがブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											$("#txtMess").html("");
											$(this)
													.removeClass(
															CLS_ERROR_FIELD);

											// 自社品目コードでエラーが発生していない場合、品目明細情報項目クリア
											if (!$("input[name='txtItemCode']")
													.eq(selectedRow).hasClass(
															CLS_ERROR_FIELD)) {
												clearItemDetail(selectedRow);
											}
										} else {
											if ($(this).val() == customerItemCodeWK) {
												focusFromItemCode();
												return;
											}
											// 品目コードチェック
											var result = checkItemCode($(this)
													.val());
											if (result != null) {
												strErrorMessage = $(
														"#" + result).val()
														.replace("{1}",
																"得意先品目コード");
												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();

												return;
											} else {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);
											}

											// focus時に退避した得意先品目コードと異なり、品目名が未設定の場合、品目明細情報取得
											if ($(this).val() != customerItemCodeWK
													|| $(
															"td[name='lblItemNameR']")
															.eq(selectedRow)
															.html().trim() == "") {
												// 品目情報クリア（品目コードは一旦退避し、クリア後に再セット）
												var itemCd = $(this).val();
												clearItemDetail(selectedRow);
												$(this).val(itemCd);

												// 品目情報取得
												if (getItemInfo($(this).val(),
														1)) {
													$("#txtMess").html("");
													$(this).removeClass(
															CLS_ERROR_FIELD);
													$(
															"input[name='txtItemCode']")
															.eq(selectedRow)
															.removeClass(
																	CLS_ERROR_FIELD);

													// 品目明細情報項目セット
													setItemDetail(selectedRow,
															"2");

													// 分類コードチェック
													if (checkClassCode()) {
														// 自社品目コード非活性化、得意先品目コード活性化
														$(
																"input[name='txtItemCode']")
																.eq(selectedRow)
																.prop(
																		"disabled",
																		true);
														$(
																"input[name='txtCustomerItemCode']")
																.eq(selectedRow)
																.prop(
																		"disabled",
																		false);
														$(
																"img[name='btnSearchItem']")
																.eq(selectedRow)
																.prop(
																		"disabled",
																		true);
														$(
																"img[name='btnSearchItem']")
																.eq(selectedRow)
																.attr("src",
																		btnSearchDisabled);
														// 受注数量（ケース）にフォーカスセット
														$(
																"input[name='lngJucCase']")
																.eq(selectedRow)
																.focus()
																.select();
													} else {
														// 品目情報クリア（品目コードは一旦退避し、クリア後に再セット）
														var itemCd = $(this)
																.val();
														clearItemDetail(selectedRow);
														$(this).val(itemCd);

														// JUC001-E
														strErrorMessage = $(
																"#JUC001-E")
																.val();

														$(this)
																.addClass(
																		CLS_ERROR_FIELD);
														$(this).focus()
																.select();
													}
												} else {
													// 品目情報クリア（品目コードは一旦退避し、クリア後に再セット）
													var itemCd = $(this).val();
													clearItemDetail(selectedRow);
													$(this).val(itemCd);

													$(this).addClass(
															CLS_ERROR_FIELD);
													$(this).focus().select();
												}

												// 品目検索状況をリセット
												itemSearchStatus = -1;
											}
										}
										// focusFromItemCode();
									});

					/**
					 * 修正区分GetFocus
					 */
					// TODO 修正区分
					$("#tblBody").on("focus", "select[name='ddlMofifyKbList']",
							function() {
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});

					/**
					 * 修正区分Change
					 */
					$("#tblBody").on("change",
							"select[name='ddlMofifyKbList']", function() {
								$(this).removeClass(CLS_ERROR_FIELD);
								$("#txtMess").html("");
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
											"input[name='lngDeliPrice']").eq(
											selectedRow).val()); // 納品単価
									var salesPrice = removeDcmlSeparate($(
											"input[name='lngSalesPrice']").eq(
											selectedRow).val()); // 販売単価
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
													"input[name='lngDeliPrice']")
													.eq(selectedRow).val()); // 納品単価
											var salesPrice = removeDcmlSeparate($(
													"input[name='lngSalesPrice']")
													.eq(selectedRow).val()); // 販売単価
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

					/**
					 * 納品単価GetFocus
					 */
					// TODO 納品単価
					$("#tblBody").on(
							"focus",
							"input[name='lngDeliPrice']",
							function() {
								// 納品単価から「,」を除去する
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
					 * 納品単価LostFocus
					 */
					$("#tblBody")
							.on(
									"blur",
									"input[name='lngDeliPrice']",
									function() {
										if (listInputStatus != -1) {
											return;
										}
										// 納品単価がブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											if (!flgPriceMaxError) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);

												// 納品金額に「0」＆合計納品金額再計算
												var totalDeliAmt = 0;
												$("td[name='lblDeliAmt']").eq(
														selectedRow).html("0");
												for (var idx = 0; idx < maxRow; idx++) {
													totalDeliAmt = Number(totalDeliAmt)
															+ Number(removeDcmlSeparate($(
																	"td[name='lblDeliAmt']")
																	.eq(idx)
																	.html()));
												}
												$("#txtTotalDeliAmt")
														.html(
																addDcmlSeparate(totalDeliAmt));
												$("#hdnTotalDeliAmt").val(
														totalDeliAmt);
											}
										} else {
											// 明細行設定
											var irisu = $("td[name='lblIrisu']")
													.eq(selectedRow).html(); // 入数
											var jucCase = removeDcmlSeparate($(
													"input[name='lngJucCase']")
													.eq(selectedRow).val()); // 受注数量（ケース）
											var jucCaseInit = jucCase; // 受注数量（ケース）初期値
											var jucSeparate = removeDcmlSeparate($(
													"input[name='lngJucSeparate']")
													.eq(selectedRow).val()); // 受注数量（バラ）
											var jucSeparateInit = jucSeparate; // 受注数量（バラ）初期値
											var deliPrice = removeDcmlSeparate($(
													this).val()); // 納品単価
											var salesPrice = removeDcmlSeparate($(
													"input[name='lngSalesPrice']")
													.eq(selectedRow).val()); // 販売単価
											var result = createDetailRow(irisu,
													jucCase, jucCaseInit,
													jucSeparate,
													jucSeparateInit, deliPrice,
													salesPrice, 2);
											if (result == 0) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);

												// 納品単価に「,」を付与する
												var addVal = addDcmlSeparate(Number(
														$(this).val()).toFixed(
														2));
												// addVal = addVal + ".00";
												$(this).val(addVal);
											} else if (result == -1) {
												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();
											} else if (result == -2) {
												// 納品金額上限エラー
												execPriceMaxError($(this));

												// 小数桁を２桁にするため、一旦「,」を除外
												addVal = removeDcmlSeparate($(
														this).val());
												addVal = addDcmlSeparate(Number(
														addVal).toFixed(2));
												$(this).val(addVal);
											}
										}
										listInputStatus = -1;
									});

					/**
					 * 販売単価GetFocus
					 */
					// TODO 販売単価
					$("#tblBody").on(
							"focus",
							"input[name='lngSalesPrice']",
							function() {
								// 販売単価から「,」を除去する
								if ($(this).val() != ""
										|| $(this).val() != null) {
									var removeVal = removeDcmlSeparate($(this)
											.val());
									$(this).val(removeVal);
								}

								if (flgPriceMaxError) {
									$("#txtMess").html(strErrorMessage);

									// 受注数量（バラ）にフォーカスセット
									// $("input[name='lngJucCase']").eq(selectedRow).focus().select();
								}
								// 何故かblur内でエラーメッセージ表示をすると上手く出来ないので、form-error-fieldクラスの有無
								// でエラー判別し、focus内でメッセージ表示
								if ($(this).hasClass(CLS_ERROR_FIELD)) {
									$("#txtMess").html(strErrorMessage);
								}
							});

					/**
					 * 販売単価LostFocus
					 */
					$("#tblBody")
							.on(
									"blur",
									"input[name='lngSalesPrice']",
									function() {
										if (listInputStatus != -1) {
											return;
										}
										// 販売単価がブランクの場合、処理終了
										if ($(this).val() == ""
												|| $(this).val() == null) {
											$("#txtMess").html("");
											$(this)
													.removeClass(
															CLS_ERROR_FIELD);
										} else {
											// 明細行設定
											var irisu = $("td[name='lblIrisu']")
													.eq(selectedRow).html(); // 入数
											var jucCase = removeDcmlSeparate($(
													"input[name='lngJucCase']")
													.eq(selectedRow).val()); // 受注数量（ケース）
											var jucCaseInit = jucCase; // 受注数量（ケース）初期値
											var jucSeparate = removeDcmlSeparate($(
													"input[name='lngJucSeparate']")
													.eq(selectedRow).val()); // 受注数量（バラ）
											var jucSeparateInit = jucSeparate; // 受注数量（バラ）初期値
											var deliPrice = removeDcmlSeparate($(
													"input[name='lngDeliPrice']")
													.eq(selectedRow).val()); // 納品単価
											var salesPrice = removeDcmlSeparate($(
													this).val());
											var result = createDetailRow(irisu,
													jucCase, jucCaseInit,
													jucSeparate,
													jucSeparateInit, deliPrice,
													salesPrice, 3);
											if (result == 0) {
												$("#txtMess").html("");
												$(this).removeClass(
														CLS_ERROR_FIELD);

												// 販売単価に「,」を付与する
												var addVal = addDcmlSeparate(Number($(
														this).val()));
												$(this).val(addVal);
											} else if (result == -1) {
												$(this).addClass(
														CLS_ERROR_FIELD);
												$(this).focus().select();
											} else if (result == -2) {
												// 納品金額上限エラー
												execPriceMaxError($(this));
											}
											// フォーカスセット（最終行の次は１行目にフォーカスをセットする）
											if (flgShift) {
												$("input[name='lngDeliPrice']")
														.eq(selectedRow)
														.focus().select();
											} else {
												if (flgPriceMaxError) {
													if ($(
															"input[name='txtItemCode']")
															.eq(selectedRow)
															.prop("disabled")) {
														$(
																"input[name='txtCustomerItemCode']")
																.eq(selectedRow)
																.focus()
																.select();
													} else {
														$(
																"input[name='txtItemCode']")
																.eq(selectedRow)
																.focus()
																.select();
													}
												} else {
													if (selectedRow == maxRow - 1) {
														if ($(
																"input[name='txtItemCode']")
																.eq(0)
																.prop(
																		"disabled")) {
															$(
																	"input[name='txtCustomerItemCode']")
																	.eq(0)
																	.focus()
																	.select();
														} else {
															$(
																	"input[name='txtItemCode']")
																	.eq(0)
																	.focus()
																	.select();
														}
													}
												}
											}
										}
										listInputStatus = -1;
									});

					/**
					 * No列へのマウスオーバー
					 */
					// TODO ポップアップメニュー
					$(document).on(
							"mouseover",
							".contextMenu",
							function() {
								// 新規登録以外処理終了
								if (operateMode != OPERATE_REGIST
										&& operateMode != OPERATE_MODIFY) {
									return;
								}

								var id = $(this).attr("id");

								// focusが当たった行以降に売上明細情報がセットされた行があるかチェック
								var flgEdit = false;
								for (var idx = id; idx < maxRow; idx++) {
									if ($("input[name='hdnFlgInput']").eq(idx)
											.val() == "1"
											|| $("input[name='hdnFlgInput']")
													.eq(idx).val() == "2") {
										flgEdit = true;
									}
								}
								// 該当行に品目明細情報がセットされていない and 以降に品目明細がセットされている
								// or 該当行に品目明細情報がセットされている
								if (($("input[name='hdnFlgInput']").eq(id - 1)
										.val() == "0" && flgEdit)
										|| $("input[name='hdnFlgInput']").eq(
												id - 1).val() == "1"
										|| $("input[name='hdnFlgInput']").eq(
												id - 1).val() == "2") {
									// change text to "▼" when user is hovering
									// on a No column
									$(this).find("span").text("▼");
									// if menu is showing, hide menu when user
									// is hovering on other
									// No columns
									if (isTableMenuShowing) {
										// only hide menu if user is hovering on
										// other No columns,
										// not the column that is showing menu
										if (id != currentShowingColId) {
											// hide menu
											$("#tableMenu").fadeOut("fast");
											// switch flag value
											isTableMenuShowing = false;
											// change the text back from "▼" to
											// number
											$("#" + currentShowingColId).find(
													"span").text(
													currentShowingColId);
										}
									}
								}
								// is menu is not showing, change the text back
								// from "▼" to
								// number
								if (id != currentShowingColId) {
									$("#" + currentShowingColId).find("span")
											.text(currentShowingColId);
									currentShowingColId = id;
									// hide menu
									$("#tableMenu").fadeOut("fast");
								}
							});

					/**
					 * No列でのマウスクリック
					 */
					$(document).on(
							"click",
							".contextMenu",
							function(e) {
								if ($(this).find("span").text() == "▼") {
									// click event on No column
									// get current coordinates of mouse
									var x = e.pageX;
									var y = e.pageY;
									// show menu at current mouse's coordinates
									$("#tableMenu").css("position", "absolute")
											.css("left", x + "px").css("top",
													y + "px").fadeIn("fast");
									// switch flag value
									isTableMenuShowing = true;
									// strSelectedUserCode =
									// $(this).attr("name");
								}
							});

					/**
					 * 「▼」になっていないNo列へのマウスオーバー
					 */
					$(document).on(
							"mouseover",
							"td",
							function() {
								if (!$(this).hasClass("contextMenu")) {
									// if menu is not showing, change the text
									// back from "▼" to
									// number
									if (!isTableMenuShowing) {
										$("#" + currentShowingColId).find(
												"span").text(
												currentShowingColId);
									}
								}
							});

					/**
					 * ポップアップメニューへのマウスオーバー
					 */
					$(".sub-menu a").bind(
							"mouseover",
							function() {
								$(this).css("background-color", "#003366").css(
										"color", "#FFFFFF");
							}).bind(
							"mouseout",
							function() {
								$(this).css("background-color", "#FFFFFF").css(
										"color", "#003366");
							});

					/**
					 * 「▼」ボタンでのマウスクリック
					 */
					$("#tbl_tbody")
							.bind(
									"click",
									function(e) {
										var container1 = $(".contextMenu");
										// if the target of the click isn't the
										// container
										if (!container1.is(e.target)
												&& container1.has(e.target).length === 0
												&& $("#tableMenu").is(
														":visible")) { // nor a
											// descendant of the container
											$("#tableMenu").fadeOut("fast");
											$("#" + currentShowingColId).find(
													"span").text(
													currentShowingColId);
											isTableMenuShowing = false;
										}
									});

					/**
					 * 削除＠ポップアップメニュー
					 */
					$("#removeSubMenu").bind(
							"click",
							function() {
								// 該当列の削除および行詰め
								removeRow(currentShowingColId - 1);
								// メニュー非表示＆行番号表示
								$("#tableMenu").fadeOut("fast");
								$("#" + currentShowingColId).find("span").text(
										currentShowingColId);
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
															// 修正区分が未選択の場合、
															if ($(
																	"#ddlModifyKb")
																	.val() == ""
																	|| $(
																			"#ddlModifyKb")
																			.val() == null) {
																// COM008-E={1}が選択されていません。
																strErrorMessage = $(
																		"#COM008-E")
																		.val()
																		.replace(
																				"{1}",
																				"修正区分");
																$(
																		"#ddlModifyKb")
																		.addClass(
																				CLS_ERROR_FIELD);
																$(
																		"#ddlModifyKb")
																		.focus()
																		.select();
																return;
															}

															// システム管理者フラグ =
															// '1'の場合、
															if ($(
																	"#hdnSysAdminFlg")
																	.val() == "1") {
																// 取消処理モード選択ダイアログ表示
																showDelDialogJucUri("2");
															} else {
																// 一般ユーザの場合、'0'(受注取消)固定
																$(
																		"#hdnCancelMode")
																		.val(
																				"0");

																// 登録処理実行
																execRegistry(operateText);
															}
														} else {
															// リスト部チェック
															if (!checkList()) {
																return;
															} else {
																// Warning発生
																if (strErrorMessage != "") {
																	confirmMessage = strErrorMessage
																			+ "<br><br>"
																			+ confirmMessage;
																	// 確認メッセージ表示
																	jQuestion_confirm(
																			confirmMessage,
																			$(
																					"#COM001-W")
																					.attr(
																							"title"),
																			function(
																					retVal) {
																				if (retVal) {
																					// 登録処理実行
																					execRegistry(operateText);
																				} else {
																					enableButton("btnRegist");
																				}
																			});
																} else {
																	// 登録処理実行
																	execRegistry(operateText);
																}
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
							// ボディ部（下段）クリア
							clearBodyUnder();
							// ボディ部（上段）活性化
							switchBodyOver(true);
							// ボタンコントロール
							controlButtons(screenStatus);

							// 店舗区分 = '2'（店無し）の場合、店舗コード非活性化
							if (mstCustomer.shopKb.trim() == "2") {
								switchShopCode(false);
							}

							// フォーカスセット
							if ($("#hdnSysAdminFlg").val() == "0") {
								$("#ddlSlipKb").focus().select();
							} else {
								$("#txtJigyoCode").focus().select();
							}

							// ボディ部（下段）非表示
							$(".tbl_data_condition2").hide();

							screenStatus = CONST_SCREEN_BODY1;
							break;
						case CONST_SCREEN_LIST:
							// リスト部全行クリア
							for (var idx = 0; idx < maxRow; idx++) {
								clearItemDetail(idx);
							}
							$("#tblBodyRow").html("");

							// ボディ部（下段）活性化
							switchBodyUnder(true);
							// 先方伝票No
							if (flgEntry) {
								$("#txtSnpSlipNo").val("");
								$("#hdnSnpSlipNo").val("");
							}
							flgEntry = false;
							// 伝票No
							$("#lblSlipNo").html("");
							$("#hdnCustomerSlipNo").val("");

							// フォーカスセット
							$("#txtSlipOut").focus().select();

							// リスト部非表示
							$(".data_result_area").hide(); // リスト部

							screenStatus = CONST_SCREEN_BODY2;

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

														// 真っ先に自社伝票Noをクリアする必要あり。
														$("#txtSlipNo").val("");
														$("#hdnSlipNo").val("");

														// オブジェクト類クリア
														mstCustomer = null; // 得意先マスタ
														mstShop = null; // 添付マスタ
														mstCourse = null; // コースマスタ
														mstItemDetail = null; // 品目明細情報
														mstSnohin = null; // 納品マスタ
														mstSchain = null; // チェーンマスタ
														uriHead = null; // 売上ヘッダ
														uriBodyList = null; // 売上明細リスト
														uriOriginHead = null; // 売上ヘッダ（返品／欠品元）
														uriOriginBody = null; // 売上明細（返品／欠品元）

														// リスト部全行クリア
														if (screenStatus == CONST_SCREEN_LIST) {
															for (var idx = 0; idx < maxRow; idx++) {
																clearItemDetail(idx);
															}
														}
														// ボディ部（下段）クリア
														if (screenStatus == CONST_SCREEN_BODY2
																|| screenStatus == CONST_SCREEN_LIST) {
															clearBodyUnder();
														}
														// ボディ部（上段）クリア
														clearBodyOver();

														// 修正区分
														$("#ddlModifyKb").val(
																"");
														$("#ddlModifyKb").prop(
																"disabled",
																false);
														$("#ddlModifyKb")
																.removeClass(
																		CLS_ERROR_FIELD);

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
														if ($("#hdnSysAdminFlg")
																.val() == "0") {
															switchCustomerCode(true); /* 得意先 */
														}
														switchShopCode(false); /* 店舗 */

														$(".slip_no").hide() // 自社伝票No
														$(".cor_class").hide(); // 修正区分（ヘッダ）
														$("#lblRegistUserCode")
																.html(
																		loginUserCode);
														$("#lblRegistUserName")
																.html(
																		loginUserName);
														$("#hdnRegistUserCode")
																.val(
																		loginUserCode);
														$("#hdnRegistUserName")
																.val(
																		loginUserName);
														$(
																"#hdnRegistUserNameKana")
																.val(
																		loginUserNameKana);
														// $("#txtShopCode").
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
	var rtnVal = "";

	for (var idx = 0; idx < maxRow; idx++) {
		var itemInfo = {};
		// 自社品目コード
		itemInfo.itemCode = $("input[name='txtItemCode']").eq(idx).val();
		// 得意先品目コード
		itemInfo.customerItemCode = $("input[name='txtCustomerItemCode']").eq(
				idx).val();
		// 品名略称
		itemInfo.itemNameR = $("td[name='lblItemNameR']").eq(idx).html();
		// 量目
		itemInfo.ryomoku = $("td[name='lblRyomoku']").eq(idx).html();
		// 入数
		itemInfo.irisu = Number($("td[name='lblIrisu']").eq(idx).html());
		// 修正区分
		itemInfo.mofifyKbList = $("select[name='ddlMofifyKbList']").eq(idx)
				.val();
		// 受注数量（ケース）
		itemInfo.jucCase = Number(removeDcmlSeparate($(
				"input[name='lngJucCase']").eq(idx).val()));
		// 受注数量（バラ)
		itemInfo.jucSeparate = Number(removeDcmlSeparate($(
				"input[name='lngJucSeparate']").eq(idx).val()));
		// 納品単価
		itemInfo.deliPrice = Number(removeDcmlSeparate($(
				"input[name='lngDeliPrice']").eq(idx).val()));
		// 販売単価
		itemInfo.salesPrice = Number(removeDcmlSeparate($(
				"input[name='lngSalesPrice']").eq(idx).val()));
		// 納品金額
		itemInfo.deliAmt = Number(removeDcmlSeparate($("td[name='lblDeliAmt']")
				.eq(idx).html()));

		// 入力フラグ
		itemInfo.flgInput = $("input[name='hdnFlgInput']").eq(idx).val();
		// 品目名（hidden）
		itemInfo.itemName = $("input[name='hdnItemName']").eq(idx).val();
		// 品目名カナ（hidden）
		itemInfo.itemNameKana = $("input[name='hdnItemNameKana']").eq(idx)
				.val();
		// 量目（実値）（hidden）
		itemInfo.ryomokuReal = Number($("input[name='hdnRyomokuReal']").eq(idx)
				.val());
		// 先方仕切単価（hidden）
		itemInfo.custBillPrice = Number($("input[name='hdnCustBillPrice']").eq(
				idx).val());
		// 部門コード
		itemInfo.bmnCd = Number($("input[name='hdnBmnCd']").eq(idx).val());
		// 製品形態コード
		itemInfo.itemTypeCd = Number($("input[name='hdnItemTypeCd']").eq(idx)
				.val());
		// 温度帯コード
		itemInfo.tempCd = Number($("input[name='hdnTempCd']").eq(idx).val());
		// 仕切価
		itemInfo.billPrice = Number($("input[name='hdnBillPrice']").eq(idx)
				.val());
		// 分類コード
		itemInfo.classCd = $("input[name='hdnClassCd']").eq(idx).val();

		// itemInfoを配列に格納
		arrayItem.push(itemInfo);
		// 行飛ばしチェック
		if (itemInfo.itemCode.trim() == "") {
			for (var idx2 = idx; idx2 < maxRow; idx2++) {
				if ($("input[name='txtItemCode']").eq(idx2).val().trim() != "") {
					rtnVal = rtnVal + "、" + (idx + 1);
					break;
				}
			}
		}
	}
	if (rtnVal != "") {
		// 先頭の「、」を除去
		rtnVal = rtnVal.substr(1);
	}
	// JSON変換
	var jsonData = JSON.stringify(arrayItem);

	// 隠し変数に格納
	$("#hdnItemJson").val(jsonData);
	return rtnVal;
}

/**
 * ボディ部（下段）チェック。リスト部表示前にボディ部（下段）の項目を再チェック
 * 
 * @return チェック結果（null：正常、null以外（エラーコード）：異常）
 */
function checkBodyUnder() {
	strErrorMessage = "";
	var rtnFlg = true;
	var errorMsg = "";
	var elmFocus = null;
	var result = null;
	// 伝票発行チェック
	result = checkSlipOut($("#txtSlipOut").val());
	if (result != null) {
		rtnFlg = false;
		if (elmFocus == null) {
			elmFocus = $("#txtSlipOut");
		}
		$("#txtSlipOut").addClass(CLS_ERROR_FIELD);
		errorMsg = $("#" + result).val().replace("{1}", "伝票発行");
		strErrorMessage = strErrorMessage + "<br>" + errorMsg;
	}

	// 納品日チェック
	var deliYmd = $("#hdnDeliYmd").val();
	var aplDate = $("#hdnAplDate").val();
	var determMon = $("#hdnDetermMonth").val();
	result = null;
	result = checkDeliDate(deliYmd, aplDate, determMon);
	if (result != null) {
		rtnFlg = false;
		if (elmFocus == null) {
			elmFocus = $("#txtDeliDateYear");
		}
		$("#txtDeliDateYear, #txtDeliDateMonth, #txtDeliDateDay").addClass(
				CLS_ERROR_FIELD);
		if (result == "COM001-E" || result == "COM006-E") {
			errorMsg = $("#" + result).val().replace("{1}", "納品日");
		} else if (result == "COM003-E") {
			var fromY = determMon.substring(0, 4);
			var fromM = determMon.substring(4, 6);
			var fromD = "01";
			var from = [ fromY, fromM, fromD ].join("/");
			var toY = aplDate.substring(0, 4);
			var toM = aplDate.substring(4, 6);
			var toD = aplDate.substring(6, 8);
			var to = [ toY, toM, toD ].join("/");

			errorMsg = $("#" + result).val().replace("{1}", from).replace(
					"{2}", to);
		}
		strErrorMessage = strErrorMessage + "<br>" + errorMsg;
	}

	// 計上日チェック
	if (flgAccount) {
		var lst = $("#lblAccountDate").html().split("/");
		// 計上日0埋め
		if ($("#txtAccountDay").val().length == 1) {
			var day = "0" + $("#txtAccountDay").val();
		} else {
			var day = $("#txtAccountDay").val();
		}

		// 計上日チェック
		result = null
		result = checkAccDate(lst[0], lst[1], day);
		if (result != null) {
			rtnFlg = false;
			if (elmFocus == null) {
				elmFocus = $("#txtAccountDay");
			}
			$("#txtAccountDay").addClass(CLS_ERROR_FIELD);
			errorMsg = $("#" + result).val().replace("{1}", "計上日");
			strErrorMessage = strErrorMessage + "<br>" + errorMsg;
		}
	}

	// 返品／欠品元伝票No
	result = null;
	result = checkOriginSlipNo($("#txtOriginSlipNo").val());
	if (result != null) {
		rtnFlg = false;
		if (elmFocus == null) {
			elmFocus = $("#txtOriginSlipNo");
		}
		$("#txtOriginSlipNo").addClass(CLS_ERROR_FIELD);
		if (result == "COM001-E") {
			errorMsg = $("#" + result).val().replace("{1}", "返品／欠品元伝票No");
		} else if (result == "COM009-E") {
			errorMsg = $("#" + result).val().replace("{1}", "売上テーブル").replace(
					"{2}", "返品／欠品元情報");
		}
		strErrorMessage = strErrorMessage + "<br>" + errorMsg;
	}

	// 便区分
	result = null;
	result = checkBinKb($("#txtBinKb").val());
	if (result != null) {
		rtnFlg = false;
		if (elmFocus == null) {
			elmFocus = $("#txtBinKb");
		}
		$("#txtBinKb").addClass(CLS_ERROR_FIELD);
		if (result == "COM001-E" || result == "COM006-E") {
			errorMsg = $("#" + result).val().replace("{1}", "便");
		} else if (result == "COM009-E") {
			errorMsg = $("#" + result).val().replace("{1}", "汎用マスタ").replace(
					"{2}", "便区分情報");
		}
		strErrorMessage = strErrorMessage + "<br>" + errorMsg;
	}

	// 納品区分
	result = null;
	result = checkDeliKb($("#txtDeliKb").val());
	if (result != null) {
		rtnFlg = false;
		if (elmFocus == null) {
			elmFocus = $("#txtDeliKb");
		}
		$("#txtDeliKb").addClass(CLS_ERROR_FIELD);
		errorMsg = $("#" + result).val().replace("{1}", "納品区分");
		strErrorMessage = strErrorMessage + "<br>" + errorMsg;
	}

	// 便区分、納品区分が入力されている場合、コース情報
	if ($("#hdnBinKb").val() != "" && $("#hdnDeliKb").val() != "") {
		if (!getCourseData()) {
			rtnFlg = false;
			$("#txtBinKb").addClass(CLS_ERROR_FIELD);
			$("#txtDeliKb").addClass(CLS_ERROR_FIELD);
			errorMsg = $("#COM009-E").val().replace("{1}", "コースマスタ").replace(
					"{2}", "コース情報");
			strErrorMessage = strErrorMessage + "<br>" + errorMsg;
		}
	}

	// 販売区分
	result = null;
	result = checkSalesKb($("#txtSalesKb").val());
	if (result != null) {
		rtnFlg = false;
		if (elmFocus == null) {
			elmFocus = $("#txtSalesKb");
		}
		$("#txtSalesKb").addClass(CLS_ERROR_FIELD);
		errorMsg = $("#" + result).val().replace("{1}", "販売区分");
		strErrorMessage = strErrorMessage + "<br>" + errorMsg;
	}

	// strErrorMessageが空ではない場合、
	if (strErrorMessage != "") {
		// 先頭の「<br>」を除去
		strErrorMessage = strErrorMessage.substr(4);
		flgErrorEx = true;
	}

	// elmFocusがnullではない場合、
	if (elmFocus != null) {
		// 先頭のエラー項目にフォーカスをセット
		elmFocus.focus().select();
	}

	return rtnFlg;
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
	var flgDeliPrice_COM001E = false;
	var flgDeliPrice_COM001W = false;
	var flgSalesPrice = false;
	var flgOnlineIncrease = false;
	var flgModifyKb = false;
	var flgOriginItem = false;
	var flgOriginSeparate = false;
	var flgItemExist = false;

	for (var idx = 0; idx < maxRow; idx++) {
		var itemCode = $("input[name='txtItemCode']").eq(idx).val().trim();
		if (itemCode != "") {
			flgItemExist = true;
			var uriBodyIdx = $("input[name='hdnUriBodyIdx'").eq(idx).val();
			var jucCase = removeDcmlSeparate($("input[name='lngJucCase']").eq(
					idx).val().trim());
			var jucSeparate = removeDcmlSeparate($(
					"input[name='lngJucSeparate']").eq(idx).val().trim());
			var irisu = removeDcmlSeparate($("td[name='lblIrisu']").eq(idx)
					.html().trim());
			var deliPrice = removeDcmlSeparate($("input[name='lngDeliPrice']")
					.eq(idx).val().trim());
			var salesPrice = removeDcmlSeparate($("input[name='lngSalesPrice']")
					.eq(idx).val().trim());
			var jucCaseInit = jucCase;
			var jucSeparateInit = jucSeparate;
			// 売上明細情報取得済の場合、
			// if (uriBodyIdx != "") {
			// jucCaseInit = uriBodyList[uriBodyIdx].caseSu.toString();
			// jucSeparateInit = uriBodyList[uriBodyIdx].baraSu.toString();
			// }

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

			// 納品単価
			// alert("chkNohinTanka(" + deliPrice + ", " + jucSeparate + ");");
			var result = chkNohinTanka(deliPrice, jucSeparate);
			// alert("result = " + result);
			if (result != null) {
				if (elmFocus == null) {
					elmFocus = $("input[name='lngDeliPrice']").eq(idx);
				}
				$("input[name='lngDeliPrice']").eq(idx).addClass(
						CLS_ERROR_FIELD);

				if (result == "COM001-E" && !flgDeliPrice_COM001E) {
					rtnFlg = false;
					// COM001-E : {1}の値が正しくありません。
					errorMsg = $("#" + result).val().replace("{1}", "納品単価");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgDeliPrice_COM001E = true;
				} else if (result == "COM001-W" && !flgDeliPrice_COM001W) {
					// COM001-W : {1}に{2}が指定されています。
					errorMsg = $("#" + result).val().replace("{1}", "納品単価")
							.replace("{2}", "0 またはブランク");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgDeliPrice_COM001W = true;
				}
			}

			// 販売単価
			// alert("chkNum(" + salesPrice + ", false);");
			var result = chkNum(salesPrice, false);
			// alert("result = " + result);
			if (result != null) {
				rtnFlg = false;
				if (elmFocus == null) {
					elmFocus = $("input[name='lngSalesPrice']").eq(idx);
				}
				$("input[name='lngSalesPrice']").eq(idx).addClass(
						CLS_ERROR_FIELD);

				if (!flgSalesPrice) {
					// COM001-E : {1}の値が正しくありません。
					errorMsg = $("#" + result).val().replace("{1}", "販売単価");
					strErrorMessage = strErrorMessage + "<br>" + errorMsg;
					flgSalesPrice = true;
				}
			}

			// データ区分 = "01"（オンライン受注データ）の場合、
			if (uriHead != null && uriHead.datKb.trim() == "01") {
				// 数量増加ならエラー
				if (uriBodyList[uriBodyIdx].caseSu < jucCase
						|| uriBodyList[uriBodyIdx].baraSu < jucSeparate) {
					rtnFlg = false;
					// 受注数量（ケース）
					if (uriBodyList[uriBodyIdx].caseSu < jucCase) {
						if (elmFocus == null) {
							elmFocus = $("input[name='lngJucCase']").eq(idx);
						}
						$("input[name='lngJucCase']").eq(idx).addClass(
								CLS_ERROR_FIELD);
					}

					// 受注数量（バラ）
					if (uriBodyList[uriBodyIdx].baraSu < jucSeparate) {
						if (elmFocus == null) {
							elmFocus = $("input[name='lngJucSeparate']")
									.eq(idx);
						}
						$("input[name='lngJucSeparate']").eq(idx).addClass(
								CLS_ERROR_FIELD);
					}

					// メッセージ
					if (!flgOnlineIncrease) {
						// URI014-E=オンライン受注データに対して、{1}はできません。
						errorMsg = $("#URI014-E").val().replace("{1}", "数量増加");
						strErrorMessage = strErrorMessage + "<br>" + errorMsg;
						flgOnlineIncrease = true;
					}
				} else {
					// 修正区分
					if (uriBodyList[uriBodyIdx].caseSu != jucCase
							|| uriBodyList[uriBodyIdx].baraSu != jucSeparate
							|| uriBodyList[uriBodyIdx].deliPrice != deliPrice
							|| uriBodyList[uriBodyIdx].salePrice != salesPrice) {
						if ($("select[name='ddlMofifyKbList']").eq(idx).val() == "") {
							rtnFlg = false;
							if (elmFocus == null) {
								elmFocus = $("select[name='ddlMofifyKbList']")
										.eq(idx);
							}
							$("select[name='ddlMofifyKbList']").eq(idx)
									.addClass(CLS_ERROR_FIELD);

							// メッセージ
							if (!flgModifyKb) {
								// COM008-E={1}が選択されていません。
								errorMsg = $("#COM008-E").val().replace("{1}",
										"修正区分");
								strErrorMessage = strErrorMessage + "<br>"
										+ errorMsg;
								flgModifyKb = true;
							}
						}
					}
				}
			} else {
				// 品目追加（uriBodyList[idx] == null） or 受注数量・単価修正の場合、
				// if(uriBodyIdx == "") {
				// alert("uriBodyIdx == \"\"");
				// }
				// alert("uriBodyList[" + uriBodyIdx + "].caseSu = " +
				// uriBodyList[uriBodyIdx].caseSu + ", jucCase = " + jucCase);
				// alert("uriBodyList[" + uriBodyIdx + "].baraSu = " +
				// uriBodyList[uriBodyIdx].baraSu + ", jucSeparate = " +
				// jucSeparate);
				// alert("uriBodyList[" + uriBodyIdx + "].deliPrice = " +
				// uriBodyList[uriBodyIdx].deliPrice + ", deliPrice = " +
				// deliPrice);
				// alert("uriBodyList[" + uriBodyIdx + "].salePrice = " +
				// uriBodyList[uriBodyIdx].salePrice + ", salesPrice = " +
				// salesPrice);
				if (uriBodyIdx == ""
						|| uriBodyList[uriBodyIdx].caseSu != jucCase
						|| uriBodyList[uriBodyIdx].baraSu != jucSeparate
						|| uriBodyList[uriBodyIdx].deliPrice != deliPrice
						|| uriBodyList[uriBodyIdx].salePrice != salesPrice) {
					// 修正区分が未選択ならエラー
					if ($("select[name='ddlMofifyKbList']").eq(idx).val() == "") {
						rtnFlg = false;
						if (elmFocus == null) {
							elmFocus = $("select[name='ddlMofifyKbList']").eq(
									idx);
						}
						$("select[name='ddlMofifyKbList']").eq(idx).addClass(
								CLS_ERROR_FIELD);

						// メッセージ
						if (!flgModifyKb) {
							// COM008-E={1}が選択されていません。
							errorMsg = $("#COM008-E").val().replace("{1}",
									"修正区分");
							strErrorMessage = strErrorMessage + "<br>"
									+ errorMsg;
							flgModifyKb = true;
						}
					}
				}
			}

			// 伝票種別 = "2"（返品） or "3"（欠品）の場合、
			if (uriOriginBody != null
					&& ($("#ddlSlipKb").val() == "2" || $("#ddlSlipKb").val() == "3")) {
				var flgOriginExist = false;
				var slipType = "欠品";
				if ($("#ddlSlipKb").val() == "2") {
					slipType = "返品";
				}

				// 返品／欠品元伝票の明細行分、繰返す
				for (var idx2 = 0; idx2 < uriOriginBody.length; idx2++) {
					// 同一アイテムあり
					if (itemCode == uriOriginBody[idx2].itemCode) {
						flgOriginExist = true;
						// 受注数量（バラ） ＞ 返品／欠品元伝票.受注数量（バラ） の場合、
						if (jucSeparate > uriOriginBody[idx2].baraSu) {
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
								errorMsg = $("#URI016-E").val().replace("{1}",
										slipType).replace("{2}", "受注数量（バラ）");
								strErrorMessage = strErrorMessage + "<br>"
										+ errorMsg;
								flgOriginSeparate = true;
							}
						}
					}
				}
				// 返品／欠品元伝票の明細行に同一アイテム無し
				if (!flgOriginExist) {
					// alert($("input[name='hdnFlgInput']").eq(idx).val());
					rtnFlg = false;
					if ($("input[name='hdnFlgInput']").eq(idx).val() == "1") {
						if (elmFocus == null) {
							elmFocus = $("input[name='txtItemCode']").eq(idx);
						}
						$("input[name='txtItemCode']").eq(idx).addClass(
								CLS_ERROR_FIELD);
					} else if ($("input[name='hdnFlgInput']").eq(idx).val() == "2") {
						if (elmFocus == null) {
							elmFocus = $("input[name='txtCustomerItemCode']")
									.eq(idx);
						}
						$("input[name='txtCustomerItemCode']").eq(idx)
								.addClass(CLS_ERROR_FIELD);
					}

					// メッセージ
					if (!flgOriginItem) {
						// URI015-E={1}元伝票に存在しない{2}を追加する事はできません。
						errorMsg = $("#URI016-E").val()
								.replace("{1}", slipType).replace("{2}", "品目");
						strErrorMessage = strErrorMessage + "<br>" + errorMsg;
						flgOriginItem = true;
					}
				}
			}
		}
	}
	if (!flgItemExist) {
		rtnFlg = false;
		errorMsg = $("#COM006-E").val().replace("{1}", "品目情報");
		$("#txtMess").html(errorMsg);
		if (elmFocus == null) {
			elmFocus = $("input[name='txtItemCode']").eq(0);
		}
	}

	// エラーが発生していない場合、
	if (rtnFlg) {
		// リスト部をJSON化
		var result = list2Json();
		if (result != "") {
			// 行トバし有り
			errorMsg = $("#COM013-I").val().replace("{1}", result);
			strErrorMessage = strErrorMessage + "<br>" + errorMsg;
		}
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
	case CONST_SCREEN_BODY2:
		/** 登録ボタン */
		disableButton("btnRegist");
		/** 入力戻りボタン */
		enableButton("btnInputReturn");
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
 * 業務日付から計上日を生成する
 * 
 * @param aplDate
 *            業務日付
 * @param addDay
 *            加算日数
 */
function createAccountDate(aplDate, addDay) {
	var year = "";
	var month = "";
	var day = "";
	var dt = new Date(parseInt(aplDate.substring(0, 4)), parseInt(aplDate
			.substring(4, 6)) - 1, parseInt(aplDate.substring(6, 8)));
	dt.setDate(dt.getDate() + Number(addDay));
	year = dt.getFullYear();
	if (dt.getMonth() + 1 < 10) {
		month = "0" + (dt.getMonth() + 1);
	} else {
		month = dt.getMonth() + 1;
	}
	if (dt.getDate() < 10) {
		day = "0" + dt.getDate();
	} else {
		day = dt.getDate();
	}

	return [ year, month, day ].join("/");
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
		if ($("#hdnSysAdminFlg").val() == "0") {
			switchCustomerCode(true); /* 得意先 */
		}
		switchShopCode(false); /* 店舗 */

		$(".tbl_data_condition2").hide(); // ボディ部（下段）

		// 事業所表示制御
		showJigyosho($("#hdnSysAdminFlg").val());

		// 画面表示状態
		screenStatus = CONST_SCREEN_BODY1;

		// フォーカスセット
		if ($("#hdnSysAdminFlg").val() == "0") {
			$("#ddlSlipKb").focus().select();
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
	} else if (operateKb == "99") {
		// 初期表示エラー（処理続行不可）
		$("input[name='optionR']:radio").prop("disabled", true);
		$(".tbl_data_condition1").show(); // ボディ部（上段）
		$(".tbl_data_condition1").prop("disabled", true); // ボディ部（上段）
		$(".tbl_data_condition2").hide(); // ボディ部（下段）

		// 事業所表示制御
		showJigyosho($("#hdnSysAdminFlg").val());

		$(".slip_no").hide() // 自社伝票No
	}
}

/**
 * 検索結果表示（ボディ部）
 */
function viewSearchResult() {
	var flgModify = false;

	$(".tbl_data_condition1").show(); // ボディ部（上段）
	switchBodyOver(false);

	$(".tbl_data_condition2").show(); // ボディ部（下段）
	switchBodyUnder(false);
	$("#txtAccountDay").hide(); // 計上日付

	$(".data_result_area").show(); // リスト部

	/**
	 * 画面項目セット
	 */
	// 事業所
	$("#txtJigyoCode").val(uriHead.officeCode.trim());
	$("#hdnJigyoCode").val(uriHead.officeCode.trim());
	$("#lblJigyoNm").html(uriHead.jigyoNm.trim());
	$("#hdnJigyoName").val(uriHead.jigyoNm.trim());
	showJigyosho($("#hdnSysAdminFlg").val());

	// 伝票区分
	$("#ddlSlipKb").val(uriHead.denKb.trim());
	$("#hdnSlipKb").val(uriHead.denKb.trim());
	// 返品／欠品元伝票No
	if (uriHead.denKb.trim() == "1" || uriHead.denKb.trim() == "4") {
		$("#ttlOriginSlipNo").hide();
		$("#txtOriginSlipNo").hide();
	} else {
		$("#ttlOriginSlipNo").show();
		$("#txtOriginSlipNo").show();
	}

	// 内税顧客
	if (uriHead.taxIncKb.trim() == "2") {
		$(".txt_tax").show();
	} else {
		$(".txt_tax").hide();
	}

	// 自社伝票No
	$("#hdnSlipNo").val(uriHead.denNo);
	$("#hdnSlipIdx").val(uriHead.denIdx);

	// 得意先伝票No
	$("#lblSlipNo").html(uriHead.custDenNo.trim());
	$("#hdnCustomerSlipNo").val(uriHead.custDenNo.trim());

	// 得意先
	$("#txtCustomerCode").val(uriHead.custCode.trim());
	$("#hdnCustomerCode").val(uriHead.custCode.trim());
	$("#lblCustomerNameR").html(uriHead.custNmR.trim());
	$("#hdnCustomerName").val(uriHead.custNm.trim());
	$("#hdnCustomerNameR").val(uriHead.custNmR.trim());
	$("#hdnCustomerNameK").val(uriHead.custNmKana.trim());

	// 店舗
	if (uriHead.shopCode.trim() != "NONE") {
		$("#txtShopCode").val(uriHead.shopCode.trim());
		$("#hdnShopCode").val(uriHead.shopCode.trim());
		$("#lblShopNm").html(uriHead.shopNmR.trim());
		$("#hdnShopName").val(uriHead.shopNm.trim());
		$("#hdnShopNameR").val(uriHead.shopNmR.trim());
		$("#hdnShopNameK").val(uriHead.shopNmKana.trim());
	}

	// 伝票発行
	$("#txtSlipOut").val(uriHead.denPrint.trim());
	$("#hdnSlipOut").val(uriHead.denPrint.trim());

	// 納品日
	$("#txtDeliDateYear").val(uriHead.deliDate.trim().substr(0, 4));
	$("#txtDeliDateMonth").val(uriHead.deliDate.trim().substr(4, 2));
	$("#txtDeliDateDay").val(uriHead.deliDate.trim().substr(6, 2));
	$("#hdnDeliYmd").val(uriHead.deliDate.trim());
	$("#hdnDeliYear").val(uriHead.deliDate.trim().substr(0, 4));
	$("#hdnDeliMonth").val(uriHead.deliDate.trim().substr(4, 2));
	$("#hdnDeliDay").val(uriHead.deliDate.trim().substr(6, 2));

	// 計上日
	var accYear = uriHead.adupDate.trim().substr(0, 4);
	var accMonth = uriHead.adupDate.trim().substr(4, 2);
	var accDay = uriHead.adupDate.trim().substr(6, 2);
	$("#lblAccountDate").html(accYear + "/" + accMonth + "/" + accDay);

	// 返品／欠品元伝票No
	$("#txtOriginSlipNo").val(uriHead.denNoOrigin.trim());
	$("#hdnOriginSlipNo").val(uriHead.denNoOrigin.trim());

	// 便区分
	$("#txtBinKb").val(uriHead.bin.trim());
	$("#hdnBinKb").val(uriHead.bin.trim());

	// 納品区分
	$("#txtDeliKb").val(uriHead.deliKb.trim());
	$("#hdnDeliKb").val(uriHead.deliKb.trim());

	// 販売区分
	$("#txtSalesKb").val(uriHead.saleKb.trim());
	$("#hdnSalesKb").val(uriHead.saleKb.trim());

	// コース
	$("#lblCourseCode").html(uriHead.courseCode.trim());
	$("#lblCourseNameR").html(uriHead.cosNmR.trim());
	$("#hdnCourseCode").val(uriHead.courseCode.trim());
	$("#hdnCourseName").val(uriHead.cosNm.trim());
	$("#hdnCourseNameR").val(uriHead.cosNmR.trim());

	// 先方伝票No
	$("#txtSnpSlipNo").val(uriHead.snpDenNo.trim());
	$("#hdnSnpSlipNo").val(uriHead.snpDenNo.trim());

	// 出荷伝票
	maxRow = uriHead.shipsLine;
	var line = "000" + maxRow;
	$("#lblSlipType").html(uriHead.shipsNm.trim());
	$("#lblSlipLine").html(line.substr(line.length - 3, 3));
	$("#hdnSlipId").val(uriHead.shipsTypId.trim());
	$("#hdnSlipType").val(uriHead.shipsNm.trim());
	$("#hdnSlipLine").val(uriHead.shipsLine);

	// 納品先・チェーン情報
	$("#hdnChainCode").val(uriHead.chainCode);
	$("#hdnChainIdx").val(uriHead.chainIdx);
	$("#hdnChainName").val(uriHead.chainNm.trim());
	$("#hdnDeliCode").val(uriHead.deliCode);
	$("#hdnDeliName").val(uriHead.deliNm.trim());
	$("#hdnDeliNameKana").val(uriHead.deliNmKana.trim());
	$("#hdnChainSalesKb").val(uriHead.cSaleKb);
	$("#hdnBillRudKb").val(uriHead.skrRndKb);
	$("#hdnBillRudPoint").val(uriHead.skrRndKeta);
	$("#hdnBillAmtRudKb").val(uriHead.amtRndKb);

	// 分類コード
	classCodeHead = uriHead.bunruiCode;
	classCodeItem = uriHead.hinKakakuBunruiCode;

	// 納品金額合計
	$("#txtTotalDeliAmt").html(addDcmlSeparate(uriHead.sumDeliAmt));
	$("#hdnTotalDeliAmt").val(uriHead.sumDeliAmt);

	// 得意先情報取得
	if (!getCustomerInfo()) {
		$("#txtMess").html(strErrorMessage);
		$("#txtSlipNo").prop("disabled", true);
		$("#txtSlipNo").focus().select();
		$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
	}

	// 店舗情報取得
	if (mstCustomer.shopKb.trim() == "1") {
		if (!getShopInfo()) {
			$("#txtMess").html(strErrorMessage);
			$("#txtSlipNo").prop("disabled", true);
			$("#txtSlipNo").focus().select();
			$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
		}
	} else {
		$("#txtSlipNo").prop("disabled", true);
		$("#txtSlipNo").removeClass(CLS_ERROR_FIELD);
	}

	// 更新日時
	$("#hdnUpdateDateTime").val(uriHead.updYmd + uriHead.updTime);

	// 処理区分により設定値が異なる項目
	if (operateMode == OPERATE_MODIFY) {
		// 訂正
		// 修正区分 表示
		flgModify = true;

		// 入力担当者
		$("#lblRegistUserCode").html(loginUserCode);
		$("#lblRegistUserName").html(loginUserName);
		$("#hdnRegistUserCode").val(loginUserCode);
		$("#hdnRegistUserName").val(loginUserName);
		$("#hdnRegistUserNameKana").val(loginUserNameKana);
	} else if (operateMode == OPERATE_CANCEL) {
		// 取消
		// 修正区分（売上明細.修正区分が設定されているレコードが１つでもある場合、表示）
		for (var idx = 0; idx < uriBodyList.length; idx++) {
			if (uriBodyList[idx].collectKb != ""
					&& uriBodyList[idx].collectKb != null) {
				flgModify = true;
			}
		}

		// 入力担当者
		$("#lblRegistUserCode").html(loginUserCode);
		$("#lblRegistUserName").html(loginUserName);
		$("#hdnRegistUserCode").val(loginUserCode);
		$("#hdnRegistUserName").val(loginUserName);
		$("#hdnRegistUserNameKana").val(loginUserNameKana);

		// 修正区分（ヘッダ）
		$(".cor_class").show();
		if (uriHead.collectKb != "" && uriHead.collectKb != null) {
			$("#ddlModifyKb").val(uriHead.collectKb);
		}
	} else if (operateMode == OPERATE_VIEW) {
		// 照会
		// 修正区分（売上明細.修正区分が設定されているレコードが１つでもある場合、表示）
		for (var idx = 0; idx < uriBodyList.length; idx++) {
			if (uriBodyList[idx].collectKb != ""
					&& uriBodyList[idx].collectKb != null) {
				flgModify = true;
			}
		}

		// 修正区分（ヘッダ）
		if (uriHead.collectKb.trim() != "" && uriHead.collectKb != null) {
			$(".cor_class").show();
			$("#ddlModifyKb").val(uriHead.collectKb);
		}

		// 入力担当者
		$("#lblRegistUserCode").html(uriHead.userCodeReg.trim());
		$("#lblRegistUserName").html(uriHead.userNmReg.trim());
		$("#hdnRegistUserCode").val(uriHead.userCodeReg.trim());
		$("#hdnRegistUserName").val(uriHead.userNmReg.trim());
		$("#hdnRegistUserNameKana").val(uriHead.userNmKanaReg.trim());
	}

	// リスト部表示
	var listHtml = "";
	var flgExist = false;
	for (var idx = 0; idx < maxRow; idx++) {
		flgExist = false;
		for (var idx2 = 0; idx2 < uriBodyList.length; idx2++) {
			// リスト部のidx + 1が売上明細の行Noと一致した場合、
			if (uriBodyList[idx2].custDenRow == idx + 1) {
				// 明細情報を元に明細行を生成
				listHtml = listHtml
						+ createSearchRow(uriBodyList[idx2], idx2, flgModify);
				flgExist = true;
			}
		}
		// 明細行を生成しなかった場合、
		if (!flgExist) {
			// 空行を生成
			listHtml = listHtml + createBlankRow(idx, flgModify);
		}
	}
	// HTMLセット
	$("#tblBodyRow").html(listHtml);

	// データ区分 = '01'（オンライン受注データ）の場合、
	if (uriHead != null && uriHead.datKb.trim() == "01") {
		// 項目追加不可なので、自社品目コード・コード検索ボタン・得意先品目コード・修正区分を非活性化する
		for (var idx = 0; idx < maxRow; idx++) {
			if ($("input[name='hdnUriBodyIdx']").eq(idx).val() == "") {
				// 自社品目コード
				$("input[name='txtItemCode']").eq(idx).prop("disabled", true);
				// コード検索ボタン
				$("img[name='btnSearchItem']").eq(idx).prop("disabled", true);
				$("img[name='btnSearchItem']").eq(idx).attr("src",
						btnSearchDisabled);
				// 得意先品目コード
				$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
						true);
				// 修正区分
				$("select[name='ddlMofifyKbList']").eq(idx).prop("disabled",
						true);
			}
		}
	}
	// 画面表示状態
	screenStatus = CONST_SCREEN_LIST;

	if (flgModify) {
		// 修正区分セット
		for (var idx = 0; idx < uriBodyList.length; idx++) {
			var rowNo = uriBodyList[idx].custDenRow - 1;
			if (uriBodyList[idx].collectKb != ""
					&& uriBodyList[idx].collectKb != null) {
				$("select[name='ddlMofifyKbList']").eq(rowNo).val(
						uriBodyList[idx].collectKb);
			}
		}
		$("#tblHead [id = 'headMofifyKbList']").show();
	} else {
		// テーブル見出し部 非表示
		$("#tblHead [id = 'headMofifyKbList']").hide();
	}

	// 処理区分 = '取消' or '照会'の場合、項目非活性化
	if (operateMode == OPERATE_CANCEL || operateMode == OPERATE_VIEW) {
		for (var idx = 0; idx < maxRow; idx++) {
			// 自社品目コード
			$("input[name='txtItemCode']").eq(idx).prop("disabled", true);
			// コード検索ボタン
			$("img[name='btnSearchItem']").eq(idx).prop("disabled", true);
			$("img[name='btnSearchItem']").eq(idx).attr("src",
					btnSearchDisabled);
			// 得意先品目コード
			$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
					true);
			// 修正区分
			$("select[name='ddlMofifyKbList']").eq(idx).prop("disabled", true);
			// 受注数量（ケース）
			$("input[name='lngJucCase']").eq(idx).prop("disabled", true);
			// 受注数量（バラ）
			$("input[name='lngJucSeparate']").eq(idx).prop("disabled", true);
			// 納品単価
			$("input[name='lngDeliPrice']").eq(idx).prop("disabled", true);
			// 販売単価
			$("input[name='lngSalesPrice']").eq(idx).prop("disabled", true);
		}
	}
	// 処理区分 = '照会'の場合、項目非活性化
	if (operateMode == OPERATE_VIEW) {
		$("#ddlModifyKb").prop("disabled", true);
	}

	// ボタンコントロール
	if (operateMode == OPERATE_MODIFY || operateMode == OPERATE_CANCEL) {
		controlButtons(CONST_SCREEN_SEARCH)
	}

	// フォーカスセット
	if (operateMode == OPERATE_MODIFY) {
		// $("input[name='lngJucCase']").eq(0).focus().select();
		$("select[name='ddlMofifyKbList']").eq(0).focus().select();
	} else if (operateMode == OPERATE_CANCEL) {
		$("#ddlModifyKb").focus();
	}
}

/**
 * 売上明細の情報を元にした明細行を生成する
 * 
 * @param uriBody
 *            売上明細情報
 * @param uriBodyIdx
 *            売掛明細情報リストのインデックス（行トバし対応）
 * @param flgModify
 *            修正区分フラグ（True あり、False なし）
 */
function createSearchRow(uriBody, uriBodyIdx, flgModify) {
	var rowHtml = "";

	// 量目丸め処理
	var weight = getNumberRound(uriBody.ryomoku, 1, 2) + "g";
	var idx = uriBody.custDenRow - 1;
	rowHtml = "<tr>" + "<td id='"
			+ (idx + 1)
			+ "' name='lblNumber' class='contextMenu align_right'><span>"
			+ (idx + 1)
			+ "</span></td>"
			// 自社品目コード
			+ "<td>"
			+ "	<input id='txtItemCode["
			+ idx
			+ "]' name='txtItemCode' type='text' class='txt_width75per border_none' value='"
			+ uriBody.itemCode.trim()
			+ "' maxlength='6' disabled='disabled' />"
			+ "	<img id='btnSearchItem["
			+ idx
			+ "]' name='btnSearchItem' class='searchIcon' alt='検索' src='"
			+ btnSearchDisabled
			+ "' disabled='disabled'>"
			+ "</td>"
			// 得意先品目コード
			+ "<td>"
			+ "	<input id='txtCustomerItemCode["
			+ idx
			+ "]' name='txtCustomerItemCode' type='text' class='txt_width75per border_none' value='"
			+ uriBody.itemCodeCust.trim()
			+ "' maxlength='13' disabled='disabled' />" + "</td>"
			// 品名略称
			+ "<td id='lblItemNameR[" + idx
			+ "]' name='lblItemNameR' class='color_blue'>"
			+ uriBody.itemNmR.trim() + "</td>"
			// 量目
			+ "<td id='lblRyomoku[" + idx
			+ "]' name='lblRyomoku' class='color_blue align_center'>" + weight
			+ "</td>"
			// 入数
			+ "<td id='lblIrisu[" + idx
			+ "]' name='lblIrisu' class='color_blue align_right'>"
			+ uriBody.ireme + "</td>";

	// 修正区分
	if (flgModify) {
		rowHtml = rowHtml
				+ "<td class='bodyModifyKbList'>"
				+ "	<select id='ddlMofifyKbList["
				+ idx
				+ "]' name='ddlMofifyKbList' class='cbx_w100per corClass border_none'>"
				+ "		<option value=''></option>";
		for (var idx2 = 0; idx2 < lstModify.length; idx2++) {
			rowHtml = rowHtml + "		<option value='" + lstModify[idx2].glCode
					+ "'>" + lstModify[idx2].glCode + ":"
					+ lstModify[idx2].target1 + "</option>";
		}
		rowHtml = rowHtml + "	</select>";
	}

	rowHtml = rowHtml
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
			+ addDcmlSeparate(uriBody.baraSu)
			+ "' maxlength='6' />"
			+ "</td>"
			// 納品単価
			+ "<td class='align_right'>"
			+ "	<input id='lngDeliPrice["
			+ idx
			+ "]' name='lngDeliPrice' type='text' class='txt_width75per align_right border_none' value='"
			+ addDcmlSeparate(uriBody.deliPrice.toFixed(2))
			+ "' maxlength='9' />"
			+ "</td>"
			// 販売単価
			+ "<td class='align_right'>"
			+ "	<input id='lngSalesPrice["
			+ idx
			+ "]' name='lngSalesPrice' type='text' class='txt_width75per align_right border_none' value='"
			+ addDcmlSeparate(uriBody.salePrice) + "' maxlength='9' />"
			+ "</td>"
			// 納品金額
			+ "<td id='lblDeliAmt[" + idx
			+ "]' name='lblDeliAmt' class='align_right'>"
			+ addDcmlSeparate(uriBody.deliAmt) + "</td>"
			// 入力フラグ（hidden）
			+ "<input id='hdnFlgInput[" + idx
			+ "]' name='hdnFlgInput' type='hidden' value='3' />"
			// 売上明細Index（hidden）
			+ "<input id='hdnUriBodyIdx[" + idx
			+ "]' name='hdnUriBodyIdx' type='hidden' value='" + uriBodyIdx
			+ "' />"
			// 品目名（hidden）
			+ "<input id='hdnItemName[" + idx
			+ "]' name='hdnItemName' type='hidden' value='"
			+ uriBody.itemNm.trim() + "' />"
			// 品目名カナ（hidden）
			+ "<input id='hdnItemNameKana[" + idx
			+ "]' name='hdnItemNameKana' type='hidden' value='"
			+ uriBody.itemNmKana.trim() + "' />"
			// 量目（実値）（hidden）
			+ "<input id='hdnRyomokuReal[" + idx
			+ "]' name='hdnRyomokuReal' type='hidden' value='"
			+ uriBody.ryomoku + "' />"
			// 先方仕切単価（hidden）
			+ "<input id='hdnCustBillPrice[" + idx
			+ "]' name='hdnCustBillPrice' type='hidden' value='"
			+ uriBody.snpSkriPrice + "' />"
			// 部門コード
			+ "<input id='hdnBmnCd[" + idx
			+ "]' name='hdnBmnCd' type='hidden' value='" + uriBody.departCode
			+ "' />"
			// 製品形態コード
			+ "<input id='hdnItemTypeCd[" + idx
			+ "]' name='hdnItemTypeCd' type='hidden' value='"
			+ uriBody.itemFrmCode + "' />"
			// 温度帯コード
			+ "<input id='hdnTempCd[" + idx
			+ "]' name='hdnTempCd' type='hidden' value='" + uriBody.tempCode
			+ "' />"
			// 仕切価
			+ "<input id='hdnBillPrice[" + idx
			+ "]' name='hdnBillPrice' type='hidden' value='"
			+ uriBody.skriPrice + "' />"
			// 分類コード
			+ "<input id='hdnClassCd[" + idx
			+ "]' name='hdnClassCd' type='hidden' value='" + uriHead.bunruiCode
			+ "' />" + "</tr>";

	return rowHtml;
}

/**
 * 空の明細行を生成する
 * 
 * @param idx
 *            行No - 1
 * @param flgModify
 *            修正区分フラグ（True あり、False なし）
 */
function createBlankRow(idx, flgModify) {
	var rowHtml = "";
	rowHtml = "<tr>" + "<td id='"
			+ (idx + 1)
			+ "' name='lblNumber' class='contextMenu align_right'><span>"
			+ (idx + 1)
			+ "</span></td>"
			// 自社品目コード
			+ "<td>"
			+ "	<input id='txtItemCode["
			+ idx
			+ "]' name='txtItemCode' type='text' class='txt_width75per border_none' value='' maxlength='6'/>"
			+ "	<img id='btnSearchItem["
			+ idx
			+ "]' name='btnSearchItem' class='searchIcon' alt='検索' src='"
			+ btnSearchEnabled
			+ "'>"
			+ "</td>"
			// 得意先品目コード
			+ "<td>"
			+ "	<input id='txtCustomerItemCode["
			+ idx
			+ "]' name='txtCustomerItemCode' type='text' class='txt_width75per border_none' value='' maxlength='13'/>"
			+ "</td>"
			// 品名略称
			+ "<td id='lblItemNameR[" + idx
			+ "]' name='lblItemNameR' class='color_blue'></td>"
			// 量目
			+ "<td id='lblRyomoku[" + idx
			+ "]' name='lblRyomoku' class='color_blue align_center'></td>"
			// 入数
			+ "<td id='lblIrisu[" + idx
			+ "]' name='lblIrisu' class='color_blue align_right'></td>";

	// 修正区分
	if (flgModify) {
		rowHtml = rowHtml
				+ "<td class='bodyModifyKbList'>"
				+ "	<select id='ddlMofifyKbList["
				+ idx
				+ "]' name='ddlMofifyKbList' class='cbx_w100per corClass border_none' disabled='disabled' >"
				+ "		<option value=''></option>";
		for (var idx2 = 0; idx2 < lstModify.length; idx2++) {
			rowHtml = rowHtml + "		<option value='" + lstModify[idx2].glCode
					+ "'>" + lstModify[idx2].glCode + ":"
					+ lstModify[idx2].target1 + "</option>";
		}
		rowHtml = rowHtml + "	</select>";
	}
	rowHtml = rowHtml
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
			+ "<td class='align_right'>"
			+ "	<input id='lngDeliPrice["
			+ idx
			+ "]' name='lngDeliPrice' type='text' class='txt_width75per align_right border_none' maxlength='9' disabled='disabled' />"
			+ "</td>"
			// 販売単価
			+ "<td class='align_right'>"
			+ "	<input id='lngSalesPrice["
			+ idx
			+ "]' name='lngSalesPrice' type='text' class='txt_width75per align_right border_none' maxlength='9' disabled='disabled' />"
			+ "</td>"
			// 納品金額
			+ "<td id='lblDeliAmt[" + idx
			+ "]' name='lblDeliAmt' class='align_right'></td>"
			// 入力フラグ（hidden）
			+ "<input id='hdnFlgInput[" + idx
			+ "]' name='hdnFlgInput' type='hidden' value='0' />"
			// 売上明細Index（hidden）
			+ "<input id='hdnUriBodyIdx[" + idx
			+ "]' name='hdnUriBodyIdx' type='hidden' value='' />"
			// 品目名（hidden）
			+ "<input id='hdnItemName[" + idx
			+ "]' name='hdnItemName' type='hidden' />"
			// 品目名カナ（hidden）
			+ "<input id='hdnItemNameKana[" + idx
			+ "]' name='hdnItemNameKana' type='hidden' />"
			// 量目（実値）（hidden）
			+ "<input id='hdnRyomokuReal[" + idx
			+ "]' name='hdnRyomokuReal' type='hidden' />"
			// 先方仕切単価（hidden）
			+ "<input id='hdnCustBillPrice[" + idx
			+ "]' name='hdnCustBillPrice' type='hidden' />"
			// 部門コード
			+ "<input id='hdnBmnCd[" + idx
			+ "]' name='hdnBmnCd' type='hidden' />"
			// 製品形態コード
			+ "<input id='hdnItemTypeCd[" + idx
			+ "]' name='hdnItemTypeCd' type='hidden' />"
			// 温度帯コード
			+ "<input id='hdnTempCd[" + idx
			+ "]' name='hdnTempCd' type='hidden' />"
			// 仕切価
			+ "<input id='hdnBillPrice[" + idx
			+ "]' name='hdnBillPrice' type='hidden' />"
			// 分類コード
			+ "<input id='hdnClassCd[" + idx
			+ "]' name='hdnClassCd' type='hidden' />" + "</tr>";

	return rowHtml;
}

/**
 * リスト部に品目明細情報をセットする
 * 
 * @param row
 *            値をセットしたい行
 * @param flgInput
 *            入力フラグ
 */
function setItemDetail(row, flgInput) {
	/**
	 * 画面表示項目
	 */
	// 自社品目コード
	$("input[name='txtItemCode']").eq(row).val(
			mstItemDetail.companyItemCd.trim());
	// 得意先品目コード
	$("input[name='txtCustomerItemCode']").eq(row).val(
			mstItemDetail.customerItemCd.trim());
	// 品名（品目略称）
	$("td[name='lblItemNameR']").eq(row).html(mstItemDetail.hinryaku.trim());
	// 量目
	var weight = getNumberRound(mstItemDetail.weight1, 1, 2);
	$("td[name='lblRyomoku']").eq(row).html(weight + "g");
	// 入数
	$("td[name='lblIrisu']").eq(row).html(mstItemDetail.ireme);
	// 修正区分
	$("select[name='ddlMofifyKbList']").eq(row).prop("disabled", false);
	// 受注数量（ケース）
	$("input[name='lngJucCase']").eq(row).prop("disabled", false);
	// 受注数量（バラ）
	$("input[name='lngJucSeparate']").eq(row).prop("disabled", false);
	// 納品単価
	var deliPrice = addDcmlSeparate(String(mstItemDetail.custDeliTanka
			.toFixed(2)));
	$("input[name='lngDeliPrice']").eq(row).val(deliPrice);
	$("input[name='lngDeliPrice']").eq(row).prop("disabled", false);
	// 販売単価
	var salesPrice = addDcmlSeparate(String(mstItemDetail.custSellTanka));
	$("input[name='lngSalesPrice']").eq(row).val(salesPrice);
	$("input[name='lngSalesPrice']").eq(row).prop("disabled", false);
	// 納品金額
	$("td[name='lblDeliAmt']").eq(row).html("0");

	/**
	 * hidden項目
	 */
	// 入力フラグ
	$("input[name='hdnFlgInput']").eq(row).val(flgInput);
	// 品目名
	$("input[name='hdnItemName']").eq(row).val(mstItemDetail.hinmei.trim());
	// 品目名カナ
	$("input[name='hdnItemNameKana']").eq(row).val(mstItemDetail.hinkana);
	// 量目（実値）
	$("input[name='hdnRyomokuReal']").eq(row).val(mstItemDetail.weight1);
	// 先方仕切価
	$("input[name='hdnCustBillPrice']").eq(row)
			.val(mstItemDetail.custBildTanka);
	// 部門コード
	$("input[name='hdnBmnCd']").eq(row).val(mstItemDetail.bmncd);
	// 製品形態コード
	$("input[name='hdnItemTypeCd']").eq(row).val(mstItemDetail.seikeicd);
	// 温度帯コード
	$("input[name='hdnTempCd']").eq(row).val(mstItemDetail.ondocd);
	// 仕切価
	$("input[name='hdnBillPrice']").eq(row).val(mstItemDetail.sikirika);
	// 分類コード
	$("input[name='hdnClassCd']").eq(row).val(mstItemDetail.bunruiCode);
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
	case 2: // 納品単価
		// 納品単価チェック
		// alert("chkNohinTanka(" + deliPrice + ", " + jucSeparate + ");");
		var result = chkNohinTanka(deliPrice, jucSeparate);
		// alert("result = " + result);
		if (result == "COM001-E") {
			// COM001-E : {1}の値が正しくありません。
			strErrorMessage = $("#" + result).val().replace("{1}", "納品単価");
			return -1;
		} else if (result == "COM001-W") {
			// COM001-W : {1}に{2}が指定されています。
			strErrorMessage = $("#" + result).val().replace("{1}", "納品単価")
					.replace("{2}", "0またはブランク");
			// return -1;
		}
		result = checkDcmlFormat(deliPrice, 8, 2);
		if (result == "COM001-E") {
			// COM001-E : {1}の値が正しくありません。
			strErrorMessage = $("#" + result).val().replace("{1}", "納品単価");
			return -1;
		}

		break;
	case 3:
		// 販売単価
		// alert("chkNum(" + salesPrice + ", false);");
		var result = chkNum(salesPrice, false);
		// alert("result = " + result);
		if (result != null) {
			// COM001-E : {1}の値が正しくありません。
			strErrorMessage = $("#" + result).val().replace("{1}", "販売単価");
			return -1;
		}

		break;
	}

	if (listInputStatus != 3) {
		// 受注数量（バラ） != "" and 納品単価 != ""の場合、納品金額、納品金額合計算出
		if ((jucSeparate != "" || jucSeparate != null)
				&& (deliPrice != "" || deliPrice != null)) {
			var totalDeliAmt = 0;
			var deliAmt = calcDeliAmt(jucSeparate, deliPrice,
					mstCustomer.shipsRudKb.trim());
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
						+ Number(removeDcmlSeparate($("td[name='lblDeliAmt']")
								.eq(idx).html()));
			}
			$("#txtTotalDeliAmt").html(addDcmlSeparate(totalDeliAmt));
			$("#hdnTotalDeliAmt").val(totalDeliAmt);
		}
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
		$(".option_regis").prop("disabled", false);
		/** 処理区分 */
		$("#txtSlipNo").prop("disabled", false);
		/** 自社伝票No */
		$("#txtJigyoCode").prop("disabled", false);
		/** 事業所コード */
		$("#ddlSlipKb").prop("disabled", false);
		/** 伝票区分 */
		$("#txtCustomerCode").prop("disabled", false);
		/** 得意先コード */
		$("#btnSearchCustomer").prop("disabled", false);
		/** 得意先検索ボタン */
		$("#btnSearchCustomer").attr("src", btnSearchEnabled);
		$("#txtShopCode").prop("disabled", false);
		/** 店舗コード */
		$("#btnSearchShop").prop("disabled", false);
		/** 店舗検索ボタン */
		$("#btnSearchShop").attr("src", btnSearchEnabled);
	} else {
		$(".option_regis").prop("disabled", true);
		/** 処理区分 */
		$("#txtSlipNo").prop("disabled", true);
		/** 自社伝票No */
		$("#txtJigyoCode").prop("disabled", true);
		/** 事業所コード */
		$("#ddlSlipKb").prop("disabled", true);
		/** 伝票区分 */
		$("#txtCustomerCode").prop("disabled", true);
		/** 得意先コード */
		$("#btnSearchCustomer").prop("disabled", true);
		/** 得意先検索ボタン */
		$("#btnSearchCustomer").attr("src", btnSearchDisabled);
		$("#txtShopCode").prop("disabled", true);
		/** 店舗コード */
		$("#btnSearchShop").prop("disabled", true);
		/** 店舗検索ボタン */
		$("#btnSearchShop").attr("src", btnSearchDisabled);
	}
}
/**
 * ボディ部（上段）を初期化する
 */
function clearBodyOver() {
	// 処理区分：新規に変更
	$("#rad_New").prop("checked", true);

	// 伝票区分
	$('#ddlSlipKb').val("");
	$('#hdnSlipKb').val("");

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

	// 得意先コード
	$("#txtCustomerCode").val("");
	$("#hdnCustomerCode").val("");
	$("#txtCustomerCode").removeClass(CLS_ERROR_FIELD);
	customerCodeWK = "";
	mstCustomer = null;
	// 得意先検索ボタン
	$("#btnSearchCustomer").prop("disabled", false);
	$("#btnSearchCustomer").attr("src", btnSearchEnabled);
	// 得意先名
	$("#lblCustomerNameR").html("");
	$("#hdnCustomerName").val("");
	$("#hdnCustomerNameR").val("");
	$("#hdnCustomerNameK").val("");

	// 店舗コード
	$("#txtShopCode").val("");
	$("#hdnShopCode").val("");
	$("#txtShopCode").removeClass(CLS_ERROR_FIELD);
	shopCodeWK = "";
	mstShop = null;
	// 店舗検索ボタン
	$("#btnSearchShop").prop("disabled", false);
	$("#btnSearchShop").attr("src", btnSearchEnabled);
	// 店舗名
	$("#lblShopNm").html("");
	$("#hdnShopName").val("");
	$("#hdnShopNameR").val("");
	$("#hdnShopNameK").val("");
	// 伝票No
	$("#lblSlipNo").html("");
}

/**
 * ボディ部（下段）の活性化状態を切り替える
 * 
 * @param flgSwitch
 *            true 活性化する、false 非活性化する
 */
function switchBodyUnder(flgSwitch) {
	if (flgSwitch) {
		$("#txtSlipOut").prop("disabled", false);
		/** 伝票発行 */
		$("#txtDeliDateYear").prop("disabled", false);
		/** 納品日（年） */
		$("#txtDeliDateMonth").prop("disabled", false);
		/** 納品日（月） */
		$("#txtDeliDateDay").prop("disabled", false);
		/** 納品日（日） */
		if (flgAccount) {
			$("#txtAccountDay").prop("disabled", false);
			/** 計上日 */
		}
		$("#txtOriginSlipNo").prop("disabled", false);
		/** 返品／欠品元伝票No */
		$("#txtBinKb").prop("disabled", false);
		/** 便区分 */
		$("#txtDeliKb").prop("disabled", false);
		/** 納品区分 */
		$("#txtSalesKb").prop("disabled", false);
		/** 販売区分 */
		$("#txtSnpSlipNo").prop("disabled", false);
		/** 先方伝票No */
	} else {
		$("#txtSlipOut").prop("disabled", true);
		/** 伝票発行 */
		$("#txtDeliDateYear").prop("disabled", true);
		/** 納品日（年） */
		$("#txtDeliDateMonth").prop("disabled", true);
		/** 納品日（月） */
		$("#txtDeliDateDay").prop("disabled", true);
		/** 納品日（日） */
		if (flgAccount) {
			$("#txtAccountDay").prop("disabled", true);
			/** 計上日 */
		}
		$("#txtOriginSlipNo").prop("disabled", true);
		/** 返品／欠品元伝票No */
		$("#txtBinKb").prop("disabled", true);
		/** 便区分 */
		$("#txtDeliKb").prop("disabled", true);
		/** 納品区分 */
		$("#txtSalesKb").prop("disabled", true);
		/** 販売区分 */
		$("#txtSnpSlipNo").prop("disabled", true);
		/** 先方伝票No */
	}
}

/**
 * ボディ部（下段）表示制御
 * 
 * @param mstCustomer
 *            得意先マスタ
 * @param mstShop
 *            店舗マスタ
 */
function showBodyUnder(mstCustomer, mstShop) {
	var errorID = chkEmpty($('#ddlSlipKb').val());
	if (errorID != null) {
		$("#txtMess").html($("#" + errorID).val().replace("{1}", "伝票区分"));
		$("#ddlSlipKb").addClass(CLS_ERROR_FIELD);
		$("#ddlSlipKb").focus().select();
		return;
	} else {
		$("#txtMess").html("");
		$("#ddlSlipKb").removeClass(CLS_ERROR_FIELD);
	}
	// ボディ部（上段）を非活性化
	switchBodyOver(false);

	// ボディ部（下段）を非活性化
	switchBodyUnder(true);
	$(".tbl_data_condition2").show();
	// 伝票区分
	if (mstCustomer.shipsKb.trim() == "1") {
		// 得意先マスタ.手入力伝票発行 = "1"（有り）の場合、
		$("#txtSlipOut").val("1");
		$("#txtSlipOut").prop("disabled", false);
	} else {
		// 得意先マスタ.手入力伝票発行 = "2"（無し）の場合、
		$("#txtSlipOut").val("3");
		$("#txtSlipOut").prop("disabled", true);
	}

	// 納品日
	$("#hdnDeliYmd").val($("#hdnAplDate").val());
	$("#txtDeliDateYear").val($("#hdnAplDate").val().substring(0, 4));
	$("#txtDeliDateMonth").val($("#hdnAplDate").val().substring(4, 6));
	$("#txtDeliDateDay").val($("#hdnAplDate").val().substring(6, 8));

	// 計上日
	$("#lblAccountDate").html(
			createAccountDate($("#hdnAplDate").val(), $("#hdnAddDay").val()));
	$("#txtAccountDay").hide();
	$("#orangeLineAccount").removeClass("orange_line");
	flgAccount = false;

	// 返品／欠品元伝票No
	if ($("#ddlSlipKb").val() == "1" || $("#ddlSlipKb").val() == "4") {
		$("#ttlOriginSlipNo").hide();
		$("#txtOriginSlipNo").hide();
	} else {
		$("#ttlOriginSlipNo").show();
		$("#txtOriginSlipNo").show();
	}

	// 出荷伝票
	maxRow = Number(listLine);
	$("#lblSlipType").html(listName); // 種別
	$("#hdnSlipType").val(listName);
	$("#lblSlipLine").html(listLine); // 行数
	$("#hdnSlipLine").val(listLine);

	// 画面表示状態
	screenStatus = CONST_SCREEN_BODY2;

	// ボタン部コントロール
	controlButtons(screenStatus);

	// フォーカスセット
	if (mstCustomer.shipsKb.trim() == "1") {
		// 得意先マスタ.手入力伝票発行 = "1"（有り）の場合、
		$("#txtSlipOut").focus().select();
	} else {
		// 得意先マスタ.手入力伝票発行 = "2"（無し）の場合、
		$("#txtDeliDateYear").focus().select();
	}
}

/**
 * ボディ部（下段）クリア
 */
function clearBodyUnder() {
	// 伝票区分
	$("#txtSlipOut").val("");
	$("#hdnSlipOut").val("");
	$("#txtSlipOut").removeClass(CLS_ERROR_FIELD);

	// 納品日
	$("#hdnDeliYmd").val("");
	$("#txtDeliDateYear").val("");
	$("#hdnDeliDateYear").val("");
	$("#txtDeliDateYear").removeClass(CLS_ERROR_FIELD);
	$("#txtDeliDateMonth").val("");
	$("#hdnDeliDateMonth").val("");
	$("#txtDeliDateMonth").removeClass(CLS_ERROR_FIELD);
	$("#txtDeliDateDay").val("");
	$("#hdnDeliDateDay").val("");
	$("#txtDeliDateDay").removeClass(CLS_ERROR_FIELD);

	// 計上日
	$("#lblAccountDate").html(
			createAccountDate($("#hdnAplDate").val(), $("#hdnAddDay").val()));
	$("#txtAccountDay").val("");
	$("#hdnAccountDay").val("");
	$("#txtAccountDay").removeClass(CLS_ERROR_FIELD);
	$("#txtAccountDay").hide();
	$("#orangeLineAccount").removeClass("orange_line");
	flgAccount = false;

	// 返品／欠品元伝票No
	uriOriginHead = null;
	uriOriginBody = null;
	$("#txtOriginSlipNo").show();
	$("#ttlOriginSlipNo").show();
	$("#txtOriginSlipNo").val("");
	$("#hdnOriginSlipNo").val("");
	$("#txtOriginSlipNo").removeClass(CLS_ERROR_FIELD);

	// 便区分
	$("#txtBinKb").val("");
	$("#hdnBinKb").val("");
	$("#txtBinKb").removeClass(CLS_ERROR_FIELD);

	// 納品区分
	$("#txtDeliKb").val("");
	$("#hdnDeliKb").val("");
	$("#txtDeliKb").removeClass(CLS_ERROR_FIELD);

	// 販売区分
	$("#txtSalesKb").val("");
	$("#hdnSalesKb").val("");
	$("#txtSalesKb").removeClass(CLS_ERROR_FIELD);

	// 先方伝票No
	$("#txtSnpSlipNo").val("");
	$("#hdnSnpSlipNo").val("");
	$("#txtSnpSlipNo").removeClass(CLS_ERROR_FIELD);

	// コース情報
	$("#lblCourseCode").html("");
	$("#lblCourseNameR").html("");
	$("#hdnCourseCode").val("");
	$("#hdnCourseName").val("");
	$("#hdnCourseNameR").val("");

	// 出荷伝票
	$("#lblSlipType").html(""); // 種別
	$("#lblSlipLine").html(""); // 行数
	maxRow = 0;
}

/**
 * 分類コードをチェックする（同一伝票内に異なる分類コードの品目は存在ＮＧ）
 * 
 * @return true：正常、false：異常
 */
function checkClassCode() {
	var classCode = $("input[name='hdnClassCd']").eq(selectedRow).val();
	// alert("classCode = " + classCode + ", classCodeHead = " + classCodeHead +
	// ", classCodeItem = " + classCodeItem);
	if (flgClassCode) {
		if (classCode != classCodeItem) {
			return false;
		} else {
			return true;
		}
	} else {
		flgClassCode = true;

		if (classCode != "" || classCode != null) {
			classCodeHead = classCode;
			classCodeItem = classCode;
		} else {
			var salesKb = $("#hdnSalesKb").val();
			var deliKb = $("#hdnDeliKb").val();
			// alert("salesKb = " + salesKb + ", deliKb = " + deliKb);

			if (salesKb == "1") {
				// 販売区分 = "1"（店直）
				if (deliKb == "1") {
					// 納品区分 = "1"（定番）
					classCodeHead = mstCustomer.bnCodeStS.trim();
				} else if (deliKb == "2") {
					// 納品区分 = "2"（特売）
					classCodeHead = mstCustomer.bnCodeSpS.trim();
				} else {
					// それ以外
					classCodeHead = "";
				}
			} else if (salesKb == "2") {
				// 販売区分 = "2"（センター）
				if (deliKb == "1") {
					// 納品区分 = "1"（定番）
					classCodeHead = mstCustomer.bnCodeStC.trim();
				} else if (deliKb == "2") {
					// 納品区分 = "2"（特売）
					classCodeHead = mstCustomer.bnCodeSpC.trim();
				} else {
					// それ以外
					classCodeHead = "";
				}
			} else {
				// それ以外
				classCodeHead = "";
			}
		}
		return true;
	}
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
	$("input[name='txtItemCode']").eq(row).val("");
	// 自社品目コード活性化
	$("input[name='txtItemCode']").eq(row).prop("disabled", false);
	// 品目検索ボタン
	$("img[name='btnSearchItem']").eq(row).attr("src", btnSearchEnabled);
	// 得意先品目コード
	$("input[name='txtCustomerItemCode']").eq(row).val("");
	// 得意先品目コード活性化
	$("input[name='txtCustomerItemCode']").eq(row).prop("disabled", false);
	// 品名（品目略称）
	$("td[name='lblItemNameR']").eq(row).html("");
	// 量目
	$("td[name='lblRyomoku']").eq(row).html("");
	// 入数
	$("td[name='lblIrisu']").eq(row).html("");
	// 修正区分
	$("select[name='ddlMofifyKbList']").eq(row).val("");
	$("select[name='ddlMofifyKbList']").eq(row).prop("disabled", true);
	$("select[name='ddlMofifyKbList']").eq(row).removeClass(CLS_ERROR_FIELD);
	// 受注数量（ケース）
	$("input[name='lngJucCase']").eq(row).val("");
	$("input[name='lngJucCase']").eq(row).prop("disabled", true);
	$("input[name='lngJucCase']").eq(row).removeClass(CLS_ERROR_FIELD);
	// 受注数量（バラ）
	$("input[name='lngJucSeparate']").eq(row).val("");
	$("input[name='lngJucSeparate']").eq(row).prop("disabled", true);
	$("input[name='lngJucSeparate']").eq(row).removeClass(CLS_ERROR_FIELD);
	// 納品単価
	$("input[name='lngDeliPrice']").eq(row).val("");
	$("input[name='lngDeliPrice']").eq(row).prop("disabled", true);
	$("input[name='lngDeliPrice']").eq(row).removeClass(CLS_ERROR_FIELD);
	// 販売単価
	$("input[name='lngSalesPrice']").eq(row).val("");
	$("input[name='lngSalesPrice']").eq(row).prop("disabled", true);
	// 納品金額
	$("td[name='lblDeliAmt']").eq(row).html("");

	// 納品金額合計再計算
	var totalDeliAmt = 0;
	for (var idx = 0; idx < maxRow; idx++) {
		totalDeliAmt = Number(totalDeliAmt)
				+ Number(removeDcmlSeparate($("td[name='lblDeliAmt']").eq(idx)
						.html()));
	}
	$("#txtTotalDeliAmt").html(addDcmlSeparate(totalDeliAmt));
	$("#hdnTotalDeliAmt").val(totalDeliAmt);

	/**
	 * hidden項目
	 */
	// 入力フラグ
	$("input[name='hdnFlgInput']").eq(selectedRow).val("0");
	// 品目名
	$("input[name='hdnItemName']").eq(selectedRow).val("");
	// 品目名カナ
	$("input[name='hdnItemNameKana']").eq(selectedRow).val("");
	// 先方仕切価
	$("input[name='hdnCustBillPrice']").eq(selectedRow).val("");
	// 部門コード
	$("input[name='hdnBmnCd']").eq(selectedRow).val("");
	// 製品形態コード
	$("input[name='hdnItemTypeCd']").eq(selectedRow).val("");
	// 温度帯コード
	$("input[name='hdnTempCd']").eq(selectedRow).val("");
	// 仕切価
	$("input[name='hdnBillPrice']").eq(selectedRow).val("");
	// 分類コード
	$("input[name='hdnClassCd']").eq(selectedRow).val("");

	if (flgPriceMaxError) {
		clearPriceMaxError();
	}
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
		// 自社品目コード
		$("input[name='txtItemCode']").eq(idx).prop("disabled", flgDisabled);
		// 得意先品目コード非活性化
		$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
				flgDisabled);
		// 修正区分
		$("select[name='ddlMofifyKbList']").eq(idx).prop("disabled",
				flgDisabled);
		// 受注数量（ケース）
		$("input[name='lngJucCase']").eq(idx).prop("disabled", flgDisabled);
		// 受注数量（バラ）
		$("input[name='lngJucSeparate']").eq(idx).prop("disabled", flgDisabled);
		// 納品単価
		$("input[name='lngDeliPrice']").eq(idx).prop("disabled", flgDisabled);
		// 販売単価
		$("input[name='lngSalesPrice']").eq(idx).prop("disabled", flgDisabled);
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
	$("input[name='lngDeliPrice']").eq(selectedRow).addClass(CLS_ERROR_FIELD);

	// 他の行を非活性化
	for (var idx = 0; idx < maxRow; idx++) {
		if (idx != selectedRow) {
			// 自社品目コード
			$("input[name='txtItemCode']").eq(idx).prop("disabled", true);
			// 得意先品目コード
			$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
					true);
			// 受注数量（ケース）
			$("input[name='lngJucCase']").eq(idx).prop("disabled", true);
			// 受注数量（バラ)
			$("input[name='lngJucSeparate']").eq(idx).prop("disabled", true);
			// 納品単価
			$("input[name='lngDeliPrice']").eq(idx).prop("disabled", true);
			// 販売単価
			$("input[name='lngSalesPrice']").eq(idx).prop("disabled", true);
		}
		// 修正区分（発生行も非活性化）
		$("select[name='ddlMofifyKbList']").eq(idx).prop("disabled", true);
	}

	// 金額エラー解除ボタン活性化
	enableButton("btnAmountError");
	// 登録ボタン非活性化
	disableButton("btnRegist");

	// 「,」を付与する
	var addVal = addDcmlSeparate(elm.val());
	elm.val(addVal);
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
	$("input[name='lngDeliPrice']").eq(selectedRow)
			.removeClass(CLS_ERROR_FIELD);

	// 他の行（明細情報表示）を活性化
	for (var idx = 0; idx < maxRow; idx++) {
		// 選択行以外
		if (idx != selectedRow) {
			if ($("input[name='hdnFlgInput']").eq(idx).val() == "0") {
				// 自社品目コード
				$("input[name='txtItemCode']").eq(idx).prop("disabled", false);
				// 得意先品目コード
				$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
						false);
			} else if ($("input[name='hdnFlgInput']").eq(idx).val() == "1") {
				// 自社品目コード
				$("input[name='txtItemCode']").eq(idx).prop("disabled", false);
				// 得意先品目コード
				$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
						true);
			} else if ($("input[name='hdnFlgInput']").eq(idx).val() == "2") {
				// 自社品目コード
				$("input[name='txtItemCode']").eq(idx).prop("disabled", true);
				// 得意先品目コード
				$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
						false);
			} else if ($("input[name='hdnFlgInput']").eq(idx).val() == "3") {
				// 自社品目コード
				$("input[name='txtItemCode']").eq(idx).prop("disabled", true);
				// 得意先品目コード
				$("input[name='txtCustomerItemCode']").eq(idx).prop("disabled",
						true);
			}
		}

		// 品目情報入力済の行のみ
		if ($("input[name='hdnFlgInput']").eq(idx).val() != "0") {
			// 修正区分
			$("select[name='ddlMofifyKbList']").eq(idx).prop("disabled", false);
			// 受注数量（ケース）
			$("input[name='lngJucCase']").eq(idx).prop("disabled", false);
			// 受注数量（バラ)
			$("input[name='lngJucSeparate']").eq(idx).prop("disabled", false);
			// 納品単価
			$("input[name='lngDeliPrice']").eq(idx).prop("disabled", false);
			// 販売単価
			$("input[name='lngSalesPrice']").eq(idx).prop("disabled", false);
		}
	}

	// 金額エラー解除ボタン非活性化
	disableButton("btnAmountError");

	// 登録ボタン活性化
	enableButton("btnRegist");
}

/**
 * 行削除処理 該当する行のレコードをクリアし、後続の行のレコードを詰めるの
 * 
 * @param row
 *            削除する行
 */
function removeRow(row) {
	for (var idx = row + 1; idx < maxRow; idx++) {
		// 自社品目コード
		$("input[name='txtItemCode']").eq(idx - 1).val(
				$("input[name='txtItemCode']").eq(idx).val());
		$("input[name='txtItemCode']").eq(idx - 1).prop("disabled",
				$("input[name='txtItemCode']").eq(idx).prop("disabled"));
		// 品目検索ボタン
		$("img[name='btnSearchItem']").eq(idx - 1).attr("src",
				$("img[name='btnSearchItem']").eq(idx).attr("src"));
		$("img[name='btnSearchItem']").eq(idx - 1).prop("disabled",
				$("img[name='btnSearchItem']").eq(idx).prop("disabled"));
		// 得意先品目コード
		$("input[name='txtCustomerItemCode']").eq(idx - 1).val(
				$("input[name='txtCustomerItemCode']").eq(idx).val());
		$("input[name='txtCustomerItemCode']").eq(idx - 1)
				.prop(
						"disabled",
						$("input[name='txtCustomerItemCode']").eq(idx).prop(
								"disabled"));
		// 品名略称
		$("td[name='lblItemNameR']").eq(idx - 1).html(
				$("td[name='lblItemNameR']").eq(idx).html());
		// 量目
		$("td[name='lblRyomoku']").eq(idx - 1).html(
				$("td[name='lblRyomoku']").eq(idx).html());
		// 入数
		$("td[name='lblIrisu']").eq(idx - 1).html(
				$("td[name='lblIrisu']").eq(idx).html());
		// 修正区分
		$("select[name='ddlMofifyKbList']").eq(idx - 1).val(
				$("select[name='ddlMofifyKbList']").eq(idx).val());
		// 受注数量（ケース）
		$("input[name='lngJucCase']").eq(idx - 1).val(
				$("input[name='lngJucCase']").eq(idx).val());
		$("input[name='lngJucCase']").eq(idx - 1).prop("disabled",
				$("input[name='lngJucCase']").eq(idx).prop("disabled"));
		// 受注数量（バラ)
		$("input[name='lngJucSeparate']").eq(idx - 1).val(
				$("input[name='lngJucSeparate']").eq(idx).val());
		$("input[name='lngJucSeparate']").eq(idx - 1).prop("disabled",
				$("input[name='lngJucSeparate']").eq(idx).prop("disabled"));
		// 納品単価
		$("input[name='lngDeliPrice']").eq(idx - 1).val(
				$("input[name='lngDeliPrice']").eq(idx).val());
		$("input[name='lngDeliPrice']").eq(idx - 1).prop("disabled",
				$("input[name='lngDeliPrice']").eq(idx).prop("disabled"));
		// 販売単価
		$("input[name='lngSalesPrice']").eq(idx - 1).val(
				$("input[name='lngSalesPrice']").eq(idx).val());
		$("input[name='lngSalesPrice']").eq(idx - 1).prop("disabled",
				$("input[name='lngSalesPrice']").eq(idx).prop("disabled"));
		// 納品金額
		$("td[name='lblDeliAmt']").eq(idx - 1).html(
				$("td[name='lblDeliAmt']").eq(idx).html());

		// 入力フラグ
		$("input[name='hdnFlgInput']").eq(idx - 1).val(
				$("input[name='hdnFlgInput']").eq(idx).val());
		// 品目名（hidden）
		$("input[name='hdnItemName']").eq(idx - 1).val(
				$("input[name='hdnItemName']").eq(idx).val());
		// 品目名カナ（hidden）
		$("input[name='hdnItemNameKana']").eq(idx - 1).val(
				$("input[name='hdnItemNameKana']").eq(idx).val());
		// 先方仕切単価（hidden）
		$("input[name='hdnCustBillPrice']").eq(idx - 1).val(
				$("input[name='hdnCustBillPrice']").eq(idx).val());
		// 部門コード
		$("input[name='hdnBmnCd']").eq(idx - 1).val(
				$("input[name='hdnBmnCd']").eq(idx).val());
		// 製品形態コード
		$("input[name='hdnItemTypeCd']").eq(idx - 1).val(
				$("input[name='hdnItemTypeCd']").eq(idx).val());
		// 温度帯コード
		$("input[name='hdnTempCd']").eq(idx - 1).val(
				$("input[name='hdnTempCd']").eq(idx).val());
		// 仕切価
		$("input[name='hdnBillPrice']").eq(idx - 1).val(
				$("input[name='hdnBillPrice']").eq(idx).val());
		// 分類コード
		$("input[name='hdnClassCd']").eq(idx - 1).val(
				$("input[name='hdnClassCd']").eq(idx).val());
	}
	clearItemDetail(maxRow - 1);
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
 * 得意先コード、得意先検索ボタンの状態を切り替える 非活性化の際、表示しているコード、名称はクリアする
 * 
 * @param flgSwitch
 *            true 活性化する、false 非活性化する
 */
function switchCustomerCode(flgSwitch) {
	if (flgSwitch) {
		$("#txtCustomerCode").prop("disabled", false); /* 得意先コード */
		$("#btnSearchCustomer").prop("disabled", false); /* 得意先検索ボタン */
		$("#btnSearchCustomer").attr("src", btnSearchEnabled);
	} else {
		$("#txtCustomerCode").prop("disabled", true); /* 得意先コード */
		$("#btnSearchCustomer").prop("disabled", true); /* 得意先検索ボタン */
		$("#btnSearchCustomer").attr("src", btnSearchDisabled);
		$("#txtCustomerCode").val("");
		$("#txtCustomerCode").removeClass(CLS_ERROR_FIELD);
		$("#lblCustomerNameR").html("");
		$("#hdnCustomerName").val("");
		$("#hdnCustomerNameR").val("");
		$("#hdnCustomerNameK").val("");
		mstCustomer = null;
	}
}

/**
 * 店舗コード、店舗検索ボタンの状態を切り替える 非活性化の際、表示しているコード、名称はクリアする
 * 
 * @param flgSwitch
 *            true 活性化する、false 非活性化する
 */
function switchShopCode(flgSwitch) {
	if (flgSwitch) {
		$("#txtShopCode").prop("disabled", false); /* 店舗コード */
		$("#btnSearchShop").prop("disabled", false); /* 店舗検索ボタン */
		$("#btnSearchShop").attr("src", btnSearchEnabled);
	} else {
		$("#txtShopCode").prop("disabled", true); /* 店舗コード */
		$("#btnSearchShop").prop("disabled", true); /* 店舗検索ボタン */
		$("#btnSearchShop").attr("src", btnSearchDisabled);
		$("#txtShopCode").val("");
		$("#txtShopCode").removeClass(CLS_ERROR_FIELD);
		$("#lblShopNm").html("");
		$("#hdnShopName").val("");
		$("#hdnShopNameR").val("");
		$("#hdnShopNameK").val("");
		mstShop = null;
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
		switchCustomerCode(true);
		$('#ddlSlipKb').focus().select();
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
		// 自社品目コード
		tmpValue.push($("input[name='txtItemCode']").eq(idx).val());
		// 得意先品目コード
		tmpValue.push($("input[name='txtCustomerItemCode']").eq(idx).val());
		// 修正区分
		tmpValue.push($("select[name='ddlMofifyKbList']").eq(idx).val());
		// 受注数量（ケース）
		tmpValue.push($("input[name='lngJucCase']").eq(idx).val());
		// 受注数量（バラ)
		tmpValue.push($("input[name='lngJucSeparate']").eq(idx).val());
		// 納品単価
		tmpValue.push($("input[name='lngDeliPrice']").eq(idx).val());
		// 販売単価
		tmpValue.push($("input[name='lngSalesPrice']").eq(idx).val());
	}
	// alert("saveData!! END! : size = " + tmpValue.length);
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
		// alert("initValue[" + idx + "] = " + initValue[idx] + ", newValue[" +
		// idx + "] = " + newValue[idx]);
		if (initValue[idx] != newValue[idx]) {
			return true;
		}
	}
	return false;
}

/**
 * 
 */
function focusFromItemCode() {
	// フォーカスセット（１行目の前は最終行にフォーカスをセットする）
	if (flgShift) {
		if (flgPriceMaxError) {
			$("input[name='lngSalesPrice']").eq(selectedRow).focus().select();
		} else {
			if (selectedRow == 0) {
				if ($("input[name='lngSalesPrice']").eq(maxRow - 1).prop(
						"disabled")) {
					$("input[name='txtCustomerItemCode']").eq(maxRow - 1)
							.focus().select();
				} else {
					$("input[name='lngSalesPrice']").eq(maxRow - 1).focus()
							.select();
				}
			}
		}
	}
}

/**
 * 登録処理
 * 
 * @param operateText
 *            処理区分文字列
 */
function execRegistry(operateText) {
	// 計上年月日生成
	var accountYmd = "";
	if (flgAccount) {
		var lst = $("#lblAccountDate").html().split("/");
		// 計上日0埋め
		if ($("#txtAccountDay").val().length == 1) {
			var day = "0" + $("#txtAccountDay").val();
		} else {
			var day = $("#txtAccountDay").val();
		}
		accountYmd = lst[0] + lst[1] + day;
	} else {
		var lst = $("#lblAccountDate").html().split("/");
		accountYmd = lst[0] + lst[1] + lst[2];
	}
	$("#hdnAccountYmd").val(accountYmd);

	$("#hdnOperateMode").val(operateMode); // 処理区分
	// 分類コード
	if (classCodeHead != "") {
		$("#hdnClassCodeHead").val(classCodeHead);
	} else {
		if ($("#hdnSalesKb").val() == "1") {
			// 販売区分 = '1'（定番）の場合、
			if ($("#hdnDeliKb").val() == "1") {
				// 納品区分 = '1'（店直）の場合、得意先マスタ.分類コード（定番・店直）
				$("#hdnClassCodeHead").val(mstCustomer.bnCodeStS);
			} else if ($("#hdnDeliKb").val() == "2") {
				// 納品区分 = '2'（センター）の場合、得意先マスタ.分類コード（定番・センター）
				$("#hdnClassCodeHead").val(mstCustomer.bnCodeStC);

			}
		} else if ($("#hdnSalesKb").val() == "2") {
			// 販売区分 = '2'（特売）の場合、
			if ($("#hdnDeliKb").val() == "1") {
				// 納品区分 = '1'（店直）の場合、得意先マスタ.分類コード（特売・店直）
				$("#hdnClassCodeHead").val(mstCustomer.bnCodeSpS);
			} else if ($("#hdnDeliKb").val() == "2") {
				// 納品区分 = '2'（センター）の場合、得意先マスタ.分類コード（特売・センター）
				$("#hdnClassCodeHead").val(mstCustomer.bnCodeSpC);
			}
		} else {
			$("#hdnClassCodeHead").val("");
		}
		alert($("#hdnClassCodeHead").val());
	}
	$("#hdnClassCodeItem").val(classCodeItem); // 分類コード

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

function execGetUriageData() {
	if (getUriageData()) {
		$("#txtMess").html("");
		$("#txtSlipNo").removeClass(CLS_ERROR_FIELD);

		// データ区分 = '08'（入金調整売上登録） or '09'（再請求売上登録）の場合、
		if ((uriHead.datKb.trim() == "08" || uriHead.datKb.trim() == "09")
				&& (operateMode == OPERATE_MODIFY
						|| operateMode == OPERATE_CANCEL || operateMode == OPERATE_VIEW)) {
			// 画面名称
			var screenName = "入金調整売上登録";
			if (uriHead.datKb.trim() == "09") {
				screenName = "再請求売上登録";
			}
			// 処理名称
			var operateName = "訂正";
			if (operateMode == OPERATE_CANCEL) {
				operateName = "取消";
			} else if (operateMode == OPERATE_VIEW) {
				operateName = "照会";
			}
			// URI001-E
			strErrorMessage = $("#URI017-E").val().replace("{1}", screenName)
					.replace("{2}", operateName);
			$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
			$("#txtSlipNo").focus().select();

			return false;
		}
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
		// 処理区分 = '2'（取消） and データ区分 =
		// '01'（オンライン受注データ）の場合、
		if (operateMode == OPERATE_CANCEL && uriHead.datKb.trim() == "01") {
			// URI014-E
			strErrorMessage = $("#URI014-E").val().replace("{1}", "取消");
			$("#txtSlipNo").addClass(CLS_ERROR_FIELD);
			$("#txtSlipNo").focus().select();
			return false;
		}
		if (operateMode != OPERATE_REGIST) {
			// 検索結果表示
			viewSearchResult();

			// リスト部データ退避
			initValue = [];
			initValue = saveData()
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
 * 得意先コード検索画面callback関数
 * 
 * @param data
 *            得意先マスタ情報
 */
function getCom0102d02(data) {
	reTurnCheckTab(); // TabIndex有効化

	mstCustomer = data[0]; // 得意先情報
	// 出荷伝票情報取得
	if (!getListForm()) {
		alert("strErrorMessage = " + strErrorMessage);
		$("#txtCustomerCode").val(mstCustomer.custCode.trim());
		$("#txtCustomerCode").addClass(CLS_ERROR_FIELD);
		$("#lblCustomerNameR").html(mstCustomer.custNmR.trim()); // 得意先略称（表示）
		$("#txtMess").html(strErrorMessage);
		$("#txtCustomerCode").focus().select();
		return;
	}

	$("#txtCustomerCode").removeClass(CLS_ERROR_FIELD);
	$("#txtMess").html("");

	$("#txtCustomerCode").val(mstCustomer.custCode.trim()); // 得意先コード（表示）
	$("#hdnCustomerCode").val(mstCustomer.custCode.trim()); // 得意先コード（隠し）
	$("#lblCustomerNameR").html(mstCustomer.custNmR.trim()); // 得意先略称（表示）
	$("#hdnCustomerNameR").val(mstCustomer.custNmR.trim()); // 得意先略称（隠し）
	$("#hdnCustomerName").val(mstCustomer.custNm.trim()); // 得意先名称
	$("#hdnCustomerNameK").val(mstCustomer.custNmKana.trim()); // 得意先名称カナ
	$("#hdnShopKb").val(mstCustomer.shopKb.trim()); // 店舗区分
	$("#hdnChainCode").val(mstCustomer.cainCode); // チェーンコード
	$("#hdnChainIdx").val(mstCustomer.cainIdx); // チェーン枝番
	if (mstCustomer.taxIncKb.trim() == "1") {
		// 内税顧客区分 = "1"（外税）の場合、
		$(".txt_tax").hide();
	} else {
		// 内税顧客区分 = "2"（内税）の場合、
		$(".txt_tax").show();
	}
	if (mstCustomer.shopKb.trim() == "1") {
		// 店舗区分 = "1"（店有り）の場合、
		switchShopCode(true); /* 店舗 */
		$("#txtShopCode").focus().select();
	} else {
		// 店舗区分 = "2"（店無し）の場合、
		$("#hdnDeliCode").val(mstCustomer.deliCenterCode.trim()); // 納品先コード
		showBodyUnder(mstCustomer, null);
	}
}

/**
 * 店舗コード検索画面callback関数
 * 
 * @param data
 *            店舗マスタ情報
 */
function getCom0102d03(data) {
	mstShop = data[0]; // 店舗情報

	reTurnCheckTab(); // TabIndex有効化
	$("#txtShopCode").removeClass(CLS_ERROR_FIELD);
	$("#txtMess").html("");

	$("#txtShopCode").val(mstShop.shopCode.trim()); // 店舗コード（表示）
	$("#hdnShopCode").val(mstShop.shopCode.trim()); // 店舗コード（隠し）
	$("#lblShopNm").html(mstShop.shopNmR.trim()); // 店舗名（表示）
	$("#hdnShopName").val(mstShop.shopNm.trim()); // 店舗名（隠し）
	$("#hdnShopNameR").val(mstShop.shopNmR.trim()); // 店舗略称
	$("#hdnShopNameK").val(mstShop.shopNmKana.trim()); // 店舗名カナ
	$("#hdnDeliCode").val(mstShop.deliCenterCode.trim()); // 納品先コード

	showBodyUnder(mstCustomer, mstShop);
}

/**
 * 品目コード検索画面callback関数
 * 
 * @param itemCodeWk
 *            品目コード
 * @param itemNameWk
 *            品目略称
 */
function getCom0102d04(itemCodeWk, itemNameWk) {
	// 取得されたデータをフォームに設定する
	// LostFocusで品目情報取得が走るよう、フォーカスを当てた後で品目コードをセットする
	$("input[name='txtItemCode']").eq(selectedRow).focus().select();
	$("input[name='txtItemCode']").eq(selectedRow).val(itemCodeWk); // 自社品目コード
	$("td[name='lblItemNameR']").eq(selectedRow).html(itemNameWk); // 品目略称

	reTurnCheckTab(); // TabIndex有効化
}

/**
 * 取消処理モード選択ダイアログcallback関数
 * 
 * @param result
 *            取消処理モード（0：売上取消、1：売上データ無効化）
 */
function getDelDialogJucUri(result) {
	$("#hdnCancelMode").val(result);

	execRegistry("取消");
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
 * 得意先名取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getCustomerInfo() {
	var rtnVal = false;
	$("#tbl_tbody").html("");
	$.ajax({
		type : "POST",
		url : "getCustomerInfo",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].mstCustomer != null && data[0].strListName != null) {
				mstCustomer = data[0].mstCustomer;
				listName = data[0].strListName;
				listLine = data[0].strListLine;
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
 * 出荷伝票情報取得
 */
function getListForm() {
	$("#tbl_tbody").html("");
	$.ajax({
		type : "POST",
		url : "getListForm",
		async : false,
		data : {
			prmShipsTypId : mstCustomer.shipsTypId
		// 手入力出荷伝票帳票ID
		},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
				listName = data[0].strListName;
				listLine = data[0].strListLine;
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
 * 店舗名取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getShopInfo() {
	var rtnVal = false;
	$("#tbl_tbody").html("");
	$.ajax({
		type : "POST",
		url : "getShopInfo",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].mstShop != null) {
				mstShop = data[0].mstShop;
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
 * コース情報取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getCourseData() {
	var rtnVal = false;
	$("#tbl_tbody").html("");
	$.ajax({
		type : "POST",
		url : "getCourseData",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].mstCourse != null) {
				mstCourse = data[0].mstCourse;
				rtnVal = true;
			} else {
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
 * 納品先情報取得
 * 
 * @return 取得結果（true：正常、false：異常）
 */
function getDeliData() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "getDeliData",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
				mstSnohin = data[0].mstSnohin;
				mstSchain = data[0].mstSchain;
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
 * 品目情報取得
 * 
 * @param val
 *            品目コード
 * @param itemKb
 *            品目コード区分（0：自社品目コード、1：得意先品目コード）
 */
function getItemInfo(val, item) {
	if (itemSearchStatus != -1) {
		return true;
	}
	itemSearchStatus = item;
	var itemCode = val; // 品目コード
	var custCode = mstCustomer.custCode.trim(); // 得意先コード
	var shopCode = null;
	if (mstShop != null) {
		shopCode = mstShop.shopCode.trim(); // 店舗コード
	}
	var salesKb = $("#hdnSalesKb").val(); // 販売区分
	var deliYmd = Number($("#hdnDeliYmd").val()); // 納品区分
	var binKb = Number($("#hdnBinKb").val()); // 便区分
	var chainCode = Number(mstCustomer.cainCode); // チェーンコード
	var chainIdx = Number(mstCustomer.cainIdx); // チェーン枝番
	var itemKb = Number(item) // 品目コード区分
	var jigyoCode = $("#hdnJigyoCode").val(); // 事業所コード
	var rtnVal = false;

	$("#tbl_tbody").html("");

	$.ajax({
		type : "POST",
		url : "getItemInfo",
		async : false,
		data : {
			prmItemCode : itemCode, // 品目コード
			prmCustCode : custCode, // 得意先コード
			prmShopCode : shopCode, // 店舗コード
			prmSalesKb : salesKb, // 販売区分
			prmDeliYmd : deliYmd, // 納品区分
			prmBinKb : binKb, // 便区分
			prmChainCode : chainCode, // チェーンコード
			prmChainIdx : chainIdx, // チェーン枝番
			prmItemKb : itemKb, // 品目コード区分
			prmJigyoCode : jigyoCode
		// 事業所コード
		},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].mstItemDetail != null) {
				mstItemDetail = data[0].mstItemDetail;
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
 * 返品／欠品元伝票Noチェック＠Ajax
 * 
 * @return チェック結果（true：正常、false：異常）
 */
function getOriginUriage() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "getOriginUriage",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
				uriOriginHead = data[0].uriData.uriHead;
				uriOriginBody = JSON.parse(data[0].uriData.uriBodyJson);
				rtnVal = true;
			} else {
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
 * 便区分チェック＠Ajax
 * 
 * @return チェック結果（true：正常、false：異常）
 */
function checkBinKbServer() {
	var rtnVal = false;
	$.ajax({
		type : "POST",
		url : "checkBinKb",
		async : false,
		data : $("form").serialize(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(returnData) {
			var data = JSON.parse(returnData);
			if (data[0].strMessage == null) {
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
				if (data[0].uriData.modifyJson != "") {
					lstModify = JSON.parse(data[0].uriData.modifyJson);
				} else {
					lstModify = "";
				}
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