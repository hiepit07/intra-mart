/*
 * ファイル名:inputChkJucUri.js
 * 概要:入力チェック（受注・売上共通）
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/11
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/11 1.00                  TDK)安延        新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

//------------------------------------------------
// 定数
//------------------------------------------------
//伝票発行 1：即発行
var denHak_sok = 1;
// 伝票発行 2：指定後発行
var denHak_sit = 2;
// 伝票発行 3：発行しない
var denHak_no = 3;

// 納品区分 1：店着
var nouKbn_ten = 1;
// 納品区分 2：センター
var nouKbn_cen = 2;

// 販売区分 1：定番
var hanKbn_tei = 1;
// 販売区分 2：特売
var hanKbn_tok = 2;
// 販売区分 3：チラシ
var hanKbn_chi = 3;
// 販売区分 4：サービス
var hanKbn_ser = 4;

// ------------------------------------------------
// ファンクション
// ------------------------------------------------
/**
 * 伝票発行入力値チェック
 *
 * @param val
 *            伝票発行
 * @returns チェック結果
 */
function chkDenpyoHakko(val) {
	var result = null;

	if (val != "" && val != denHak_sok && val != denHak_sit && val != denHak_no) {
		result = "COM001-E";
	}
	return result;
}

/**
 * 納品区分入力値チェック
 *
 * @param val
 *            納品区分
 * @returns チェック結果
 */
function chkNohinKbn(val) {
	var result = null;

	if (val != "" && val != nouKbn_ten && val != nouKbn_cen) {
		result = "COM001-E";
	}
	return result;
}

/**
 * 販売区分入力値チェック
 *
 * @param val
 *            販売区分
 * @returns チェック結果
 */
function chkHanbaiKbn(val) {
	var result = null;

	if (val != "" && val != hanKbn_tei && val != hanKbn_tok
			&& val != hanKbn_chi && val != hanKbn_ser) {
		result = "COM001-E";
	}
	return result;
}

/**
 * 受注数量（ケース）チェック
 *
 * @param case_val
 *            ケース
 * @param irisu
 *            入数
 * @param tanka
 *            納品単価
 * @param case_ini
 *            ケース（初期取得値）
 * @returns チェック結果
 */
function chkJucSuCase(case_val, irisu, tanka, case_ini) {

	var result = null;

	// 引数チェック
	if (case_val == "" || irisu == "" || irisu == 0 || tanka == 0) {
		return result;
	}

	// ケース
	// 数値フォーマットチェック
	result = chkNum(case_val, false);
	if (result != null) {
		return result;
	}

	// 入数
	// 数値フォーマットチェック
	result = chkNum(irisu, false);
	if (result != null) {
		return result;
	}

	// 納品単価
	// 数値フォーマットチェック
	result = chkNum(tanka, false);
	if (result != null) {
		return result;
	}

	// ケース（初期取得値）が空でない場合のみチェック
	// ケース ＞ ケース（初期取得値）の場合、エラーとする
	if (case_ini != "" && case_val > case_ini) {
		result = "COM005-E";
		return result;
	}
	return result;
}

/**
 * 受注数量（バラ）チェック
 *
 * @param bara_val
 *            バラ数
 * @param tanka
 *            納品単価
 * @param bara_ini
 *            バラ数（初期取得値）
 * @returns チェック結果
 */
function chkJucSuBara(bara_val, tanka, bara_ini) {

	var result = null;

	// バラ数チェック
	if (bara_val == "") {
		result = "COM006-E";
	} else if (bara_val == 0) {
		result = "COM007-E";
	} else {
		// 数値フォーマットチェック
		result = chkNum(bara_val, false);
	}
	if (result != null) {
		return result;
	}

	// 納品単価チェック
	if (tanka == "") {
		return result;
	} else {
		// 数値フォーマットチェック
		result = chkNum(tanka, false);
	}
	if (result != null) {
		return result;
	}

	// バラ（初期取得値）が空でない場合のみチェック
	// バラ ＞ バラ（初期取得値）の場合、エラーとする
	if (bara_ini != "" && bara_val > bara_ini) {
		result = "COM005-E";
	}
	return result;
}

/**
 * 納品単価チェック
 *
 * @para tanka 納品単価
 * @para bara バラ
 * @return チェック結果
 */
function chkNohinTanka(tanka, bara) {

	var result = null;

	// 単価チェック
	if (tanka == "" || tanka == 0) {
		result = "COM001-W"
	} else {
		// 数値フォーマットチェック
		result = chkNum(tanka, false);
	}
	if (result != null) {
		return result;
	}

	// バラチェック
	if (bara != "") {
		// 数値フォーマットチェック
		result = chkNum(bara, false);
	}
	return result;
}