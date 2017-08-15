/*
 * ファイル名:inputChkCpm.js
 * 概要:入力チェック（システム共通）
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
// 日付フォーマット（YYYYMMDD）
var DATE_FORMAT_YMD = "YYYYMMDD";
// 日付フォーマット（YYYYMM）
var DATE_FORMAT_YM = "YYYYMM";
// 日付フォーマット（MMDD）
var DATE_FORMAT_MD = "MMDD";
// 日付フォーマット（YYYYMMDDHHMISS）
var DATE_FORMAT_YMDHMS = "YYYYMMDDHHMISS";

// 型 半角数値（0～9のみ）
var TYPE_NUM = "0";
// 型 半角英字(a～z,A～Z)
var TYPE_ALPHA = "1";
// 型 半角記号
var TYPE_SIGN = "2"
// 型 半角英数字(0～9,a～z,A～Z)
var TYPE_NUM_ALPHA = "3";
// 型 半角英数字記号(0～9,a～z,A～Z,記号)
var TYPE_NUM_ALPHA_SIGN = "4";
// 型 半角カタカナ
var TYPE_KANA = "5";
// 型 半角全て
var TYPE_HALF = "6";
// 型 全角全て
var TYPE_EM = "7";
// 型 数値フォーマットチェック
var TYP_NUM_FORMAT = "8";
// 型 日付フォーマットチェック
var TYP_DATE_FORMAT = "9";

// ------------------------------------------------
// ファンクション
// ------------------------------------------------
/**
 * 数値フォーマットチェック
 *
 * @param val
 *            チェック値
 * @param minusFlg
 *            マイナス許可フラグ（true:許可する,false:許可しない）
 * @returns チェック結果
 */
function chkNum(val, minusFlg) {
	var result = null;
	if (val == "") {
		return result;
	}
	if (minusFlg != true) {
		// マイナスを許可しない
		if (!val.match(/^([1-9]\d*|0)(\.\d+)?$/)) {
			result = "COM001-E";
		}
	} else {
		// マイナスを許可する
		if (!val.match(/^[-]?([1-9]\d*|0)(\.\d+)?$/)) {
			result = "COM001-E";
		}
	}
	return result;
}

/**
 * 日付フォーマットチェック
 *
 * @param val
 *            チェック値
 * @param format
 *            日付フォーマット(※形式は定数参照)
 * @returns チェック結果
 */
function chkDate(val, format) {
	var result = null;
	var dt = null;

	// 引数チェック
	if (val == "") {
		return result;
	}

	switch (format) {
	// YYYYMMDD
	case DATE_FORMAT_YMD:
		if (val.length != DATE_FORMAT_YMD.length) {
			result = "COM001-E";
			break;
		}
		dt = new Date(val.substr(0, 4), val.substr(4, 2)-1, val.substr(6, 2));
		if (dt.getFullYear() != val.substr(0, 4)
				|| dt.getMonth() != val.substr(4, 2)-1
				|| dt.getDate() != val.substr(6, 2)) {
			result = "COM001-E";
		}
		break;
	// YYYYMM
	case DATE_FORMAT_YM:
		if (val.length != DATE_FORMAT_YM.length) {
			result = "COM001-E";
			break;
		}
		dt = new Date(val.substr(0, 4), val.substr(4, 2)-1, "01");

		if (dt.getFullYear() != val.substr(0, 4)
				|| dt.getMonth() != eval(val.substr(4, 2)-1)) {
			result = "COM001-E";
		}
		break;
	// MMDD
	case DATE_FORMAT_MD:
		if (val.length != DATE_FORMAT_MD.length) {
			result = "COM001-E";
			break;
		}
		var mm = eval(val.substr(0, 2));
		var dd = eval(val.substr(2, 2));

		switch (mm) {
		case 4:
		case 6:
		case 9:
		case 11:
			if (dd < 1 || dd > 30) {
				result = "COM001-E";
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (dd < 1 || dd > 31) {
				result = "COM001-E";
			}
			break;
		case 2:
			if (dd < 1 || dd > 29) {
				result = "COM001-E";
			}
			break;
		default:
			result = "COM001-E";
			break;
		}
		break;
	// YYYYMMDDHHMISS
	case DATE_FORMAT_YMDHMS:
		if (val.length != DATE_FORMAT_YMDHMS.length) {
			result = "COM001-E";
			break;
		}
		// YYYYMMDDのチェック
		dt = new Date(val.substr(0, 4), val.substr(4, 2)-1, val.substr(6, 2));
		if (dt.getFullYear() != val.substr(0, 4)
				|| dt.getMonth() != val.substr(4, 2)-1
				|| dt.getDate() != val.substr(6, 2)) {
			result = "COM001-E";
			break;
		}
		// HHMISSのチェック
		var hh = eval(val.substr(8, 2));
		var mi = eval(val.substr(10, 2));
		var ss = eval(val.substr(12, 2));

		if (hh < 0 || hh > 23) {
			result = "COM001-E";
			break;
		}

		if (mi < 0 || mi > 59) {
			result = "COM001-E";
			break;
		}

		if (ss < 0 || ss > 59) {
			result = "COM001-E";
			break;
		}
		break;
	}

	return result;
}

/**
 * 日付From-Toチェック
 *
 * @param varFrom
 *            日付From
 * @param varTo
 *            日付To
 * @param format
 *            日付フォーマット (※形式は定数参照)
 * @returns チェック結果
 */
function chkFromTo(varFrom, varTo, format) {
	var result = null;
	var chk = null;

	// 入力項目Fromのフォーマットチェック
	if (varFrom != "") {
		chk = chkDate(varFrom, format);
		if (chk != null) {
			result = chk;
			return result;
		}
	}

	// 入力項目Toのフォーマットチェック
	if (varTo != "") {
		chk = chkDate(varTo, format);
		if (chk != null) {
			result = chk;
			return result;
		}
	}

	// 大小チェック
	if (varFrom > varTo) {
		result = "COM016-E";
	}

	return result;
}

/**
 * 必須チェック
 *
 * @param val
 *            チェック項目
 * @returns チェック結果
 */
function chkEmpty(val) {
	var result = null;
	if (val.trim() == "") {
		result = "COM006-E";
	}

	return result;
}

/**
 * 型チェック
 *
 * @param val
 *            チェック項目
 * @param type
 *            型（※形式は定数参照）
 * @param minusFlg
 *            マイナス許可フラグ（true:許可する,false:許可しない）※TYP_NUM_FORMATの場合のみ必須
 * @param format
 *            日付フォーマット (※形式は定数参照) ※TYP_DATE_FORMATの場合のみ必須
 * @returns チェック結果
 */
function chkType(val, type, minusFlg, format) {
	var result = null;

	if(val == ""){
		return result;
	}

	switch (type) {
	// 半角数値
	case TYPE_NUM:
		if (!val.match(/^[0-9]+$/)) {
			result = "COM001-E";
		}
		break;
	// 半角英字
	case TYPE_ALPHA:
		if (!val.match(/^[a-zA-Z]+$/)) {
			result = "COM001-E";
		}
		break;
	// 半角記号
	case TYPE_SIGN:
		if (!val.match(/^[ -/:-@\[-\`\{-\~]+$/)) {
			result = "COM001-E";
		}
		break;
	// 半角英数字
	case TYPE_NUM_ALPHA:
		if (!val.match(/^[a-zA-Z0-9]+$/)) {
			result = "COM001-E";
		}
		break;
	// 半角英数字記号
	case TYPE_NUM_ALPHA_SIGN:
		if (!val.match(/^[a-zA-Z0-9 -/:-@\[-\`\{-\~]+$/)) {
			result = "COM001-E";
		}
		break;
	// 半角カタカナ
	case TYPE_KANA:
		if (!val.match(/^[｡-ﾟ+]+$/)) {
			result = "COM001-E";
		}
		break;
	// 半角すべて
	case TYPE_HALF:
		if (!val.match(/^[ -~｡-ﾟ]+$/)) {
			result = "COM001-E";
		}
		break;
	// 全角すべて
	case TYPE_EM:
		if (!val.match(/^[^ -~｡-ﾟ]+$/)) {
			result = "COM001-E";
		}
		break;
	// 数値フォーマットチェック
	case TYP_NUM_FORMAT:
		var chk = chkNum(val, minusFlg);
		if (chk != null) {
			result = chk;
		}
		break;
	// 日付フォーマットチェック
	case TYP_DATE_FORMAT:
		if (format == "" || format == null) {
			result = "COM001-E";
		} else {
			var chk = chkDate(val, format);
			if (chk != null) {
				result = chk;
			}
		}
		break;
	}
	return result;
}

/**
 * 単項目チェック
 *
 * @param val
 *            チェック項目
 * @param emptyFlg
 *            必須チェックフラグ(true:必須チェックする、false:必須チェックしない）
 * @param type
 *            型（※形式は定数参照）
 * @param misusFlg
 *            マイナス許可フラグ（true:許可する,false:許可しない）
 * @param format
 *            日付フォーマット (※形式は定数参照)
 * @returns チェック結果
 */
function chkItem(val, emptyFlg, type, misusFlg, format) {
	var result = null;
	var chk = null;

	// 必須チェック
	if (emptyFlg == true) {
		chk = chkEmpty(val);
		if (chk != null) {
			result = chk;
			return result;
		}
	}

	// 型チェック
	chk = chkType(val, type, misusFlg, format);
	if (chk != null) {
		result = chk;
	}
	return result;
}
