/**
 * ファイル名:uri01func.js 概要:売上サブシステム共通関数
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

/**
 * 伝票発行チェック
 * 
 * @param val
 *            伝票発行
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常）
 */
function checkSlipOut(val) {
	// 必須チェック
	var result = chkEmpty(val)
	if (result != null) {
		// 失敗
		return result;
	}

	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		// 失敗
		return result;
	}
	// 伝票発行チェック
	var result = chkDenpyoHakko(val);
	if (result != null) {
		// 失敗
		return result;
	}
	return null;
}

/**
 * 納品日チェック
 * 
 * @param date
 *            納品年月日
 * @param aplDate
 *            業務日付
 * @param determMon
 *            会計月
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkDeliDate(date, aplDate, determMon) {
	// 必須チェック
	var result = chkEmpty(date);
	if (result != null) {
		return result;
	}
	// 日付フォーマットチェック
	var result = chkDate(date, DATE_FORMAT_YMD);
	if (result != null) {
		return result;
	}
	// 納品日 ＞ 業務日付 or 納品日 ＜ 会計月の１日
	if (date > aplDate || date < determMon + "01") {
		return "COM003-E";
	}
	return null;
}

/**
 * 計上日チェック
 * 
 * @param year
 *            計上年
 * @param month
 *            計上月
 * @param date
 *            計上日
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkAccDate(year, month, day) {
	// 計上日がブランク or Null or 32の場合、処理終了
	if (day == "32") {
		return null;
	}

	// 必須チェック
	var result = chkEmpty(day);
	if (result != null) {
		return result;
	}

	// 日付フォーマットチェック
	var result = chkDate(year + month + day, DATE_FORMAT_YMD);
	if (result != null) {
		return result;
	}
	return null;
}

/**
 * 返品／欠品元伝票Noチェック
 * 
 * @param val
 *            返品／欠品元伝票No
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkOriginSlipNo(val) {
	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		return result;
	}
	if (val != "" && val != null) {
		// 返品／欠品元伝票Noチェック＠server
		if (!getOriginUriage()) {
			return "COM009-E";
		}
	}

	return null;

}

/**
 * 便区分チェック
 * 
 * @param val
 *            便区分
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkBinKb(val) {
	// 必須チェック
	var result = chkEmpty(val)
	if (result != null) {
		return result;
	}

	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		return result;
	}

	// 便区分チェック＠server
	if (!checkBinKbServer()) {
		return "COM009-E";
	}

	return null;
}

/**
 * 納品区分チェック
 * 
 * @param val
 *            納品区分
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkDeliKb(val) {
	// 必須チェック
	var result = chkEmpty(val)
	if (result != null) {
		return result;
	}

	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		return result;
	}

	// 共通関数：納品区分チェック
	var result = chkNohinKbn(val);
	if (result != null) {
		return result;
	}

	return null;
}

/**
 * 販売区分チェック
 * 
 * @param val
 *            販売区分
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkSalesKb(val) {
	// 必須チェック
	var result = chkEmpty(val)
	if (result != null) {
		return result;
	}

	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		return result;
	}

	// 共通関数：納品区分チェック
	var result = chkHanbaiKbn(val);
	if (result != null) {
		return result;
	}

	return null;
}

/**
 * 先方伝票Noチェック
 * 
 * @param val
 *            先方伝票No
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkSnpSlipNo(val) {
	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		return result;
	}

	return null;
}

/**
 * 品目コードチェック
 * 
 * @param val
 *            品目コード
 * 
 * @returns チェック結果（null：正常、null以外（エラーコード）：異常
 */
function checkItemCode(val) {
	// 属性チェック
	var result = chkType(val, TYPE_NUM, false, null)
	if (result != null) {
		return result;
	}

	return null;
}

/**
 * 小数桁のフォーマットチェック
 * 
 * @param val チェック対象
 * @param precision 総桁数
 * @param scale 小数点以下の桁数
 */
function checkDcmlFormat(val, precision, scale){
	var numData = val.split(".");
	var integer = numData[0];
	var decimal = "";
	if (numData.length == 2) {
		decimal = numData[1];
	} else if(numData.length > 2) {
		return "COM001-E";
	}
	
	if (integer.length > precision - scale) {
		return "COM001-E";
	}
	
	if (decimal.length > scale) {
		return "COM001-E";
	}
	return null;
}
/**
 * ３桁区切りのカンマ(,)を加える
 * 
 * @param val
 *            変換対象
 * 
 * @returns 変換後の文字列
 */
function addDcmlSeparate(val) {
	var rtnVal = "";
	var integral = "";
	var decimal = "";

	if (String(val).indexOf(".") == -1) {
		integral = val;
	} else {
		integral = String(val).split(".")[0];
		decimal = "." + String(val).split(".")[1];
	}
	rtnVal = String(integral).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,')
			+ decimal;
	return rtnVal;
}
/**
 * ３桁区切りのカンマ(,)を除去する
 * 
 * @param val
 *            変換対象
 * 
 * @returns 除去後の文字列
 */
function removeDcmlSeparate(val) {
	return val.replace(/,/g, "");
}

function addDateSeparate(val) {
	var year = val.substr(0, 4);
	var month = val.substr(4, 2);
	var day = "";

	if (val.length > 6) {
		day = "/" + val.substr(6, 2);
	}
	
	return year + "/" + month + day;
}

function removeDateSeparate(val) {
	var lst = val.split("/");
	var rtnVal = "";

	for(var idx = 0; idx < lst.length; idx++){
		rtnVal = rtnVal + lst[idx];
	}
	return rtnVal;
}

/**
 * 受注数量（バラ）算出
 * 
 * @param jucCase
 *            受注数量（ケース）
 * @param irisu
 *            入数
 * 
 * @returns 受注数量（バラ）
 */
function calcJucSeparate(jucCase, irisu) {
	return jucCase * irisu;
}

/**
 * 納品金額算出
 * 
 * @param jucSeparate
 *            受注数量（バラ）
 * @param deliPrice
 *            納品単価
 * @param roundKb
 *            丸め種別
 * 
 * @returns 納品金額
 */
function calcDeliAmt(jucSeparate, deliPrice, roundKb) {
	var tmpDeli = jucSeparate * deliPrice;
	var deliAmt = getNumberRound(tmpDeli, 1, roundKb);
	return deliAmt;
}
