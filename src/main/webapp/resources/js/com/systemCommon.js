/**
 * 数値丸め
 *
 * @param varValue
 *            数値（丸め処理前）
 * @param varPoint
 *            丸め小数桁（丸めを行う小数点第○位）
 * @param varClass
 *            丸め種別（１：四捨五入、２：切り捨て、３：切り上げ）
 * @returns 数値（丸め処理後）
 */
function getNumberRound(varInValue, varPoint, varClass) {

	var varOutValue = null; // 戻り値
	var flg = 0; // 負数フラグ(0:正、1:負)

	// 入力チェック
	if (getPointMax(varInValue) < varPoint) {
		// [入力]数値の小数桁 < [入力]丸め小数桁の場合、[入力]数値で復帰する
		return varInValue;
	} else if (varClass != 1 && varClass != 2 && varClass != 3) {
		// [入力]丸め種別 ≠ 1 or 2 or 3 の場合、[入力]数値で復帰する
		return varInValue;
	}

	// 入力値判定
	if (varInValue < 0) {
		// 負の場合、正に変換
		varInValue = -1 * varInValue;
		flg = 1; // 負数フラグON
	}

	// 丸め処理
	var dig = Math.pow(10, (varPoint - 1)); // 桁数に応じた累乗を取得

	if (varClass == "1") {
		// 四捨五入（[入力]丸め種別 = '1'）の場合
		varOutValue = Math.round(varInValue * dig) / dig
	} else if (varClass == 2) {
		// 切り捨て（[入力]丸め種別 = '2'）の場合
		varOutValue = Math.floor(varInValue * dig) / dig
	} else {
		// 切り上げ（[入力]丸め種別 = '3'）の場合
		varOutValue = Math.ceil(varInValue * dig) / dig
	}

	// 入力値が負の場合、返却時に負に戻す
	if (flg == 1) {
		varOutValue = varOutValue * -1;
	}

	// ０を補充（末尾０の場合、省略されるため）
	varOutValue = varOutValue.toFixed((varPoint - 1));

	return varOutValue;
}

/**
 * 小数点以下の桁数取得用
 *
 * @param value
 *            数値（小数点以下桁数を確認したい数値）
 *
 * @returns 数値（小数点以下桁数）
 */
function getPointMax(value) {
	var list = (value + '').split('.'), result = 0;
	if (list[1] !== undefined && list[1].length > 0) {
		result = list[1].length;
	}
	return result;
}

/**
 * エラー時のコントロール制御
 *
 */
function errorControl() {

	// input属性を全て無効化
	$("form").find(':input').attr('disabled', true);

	// JQueryイベントを無効化
	// TODO イベントを足す場合はunbindを後ろに追加していく
	$("*").unbind("mouseover").unbind("mouseout").unbind("click");

}

/**
 * 単価消費税計算
 *
 * @param varUnitPrice
 *            単価
 * @param varTaxRate
 *            消費税率
 * @param varTaxIncFrcKb
 *            内税消費税端数処理
 *
 * @returns JSON 単価情報格納クラス
 *
 */
function getUnitTaxPrice(varUnitPrice, varTaxRate, varTaxIncFrcKb) {

	// 単価消費税を算出
	var dig = Math.pow(10, getPointMax(varUnitPrice) - 1); // 桁数に応じた累乗を取得
	var unitTaxPriceWk = ((varUnitPrice * dig) * varTaxRate)
			/ (100 + eval(varTaxRate)); // 累乗を乗算して小数点を排除
	unitTaxPriceWk = unitTaxPriceWk / dig; // 小数点を元に戻す

	// 共通部品【システム共通.数値丸め】向け引数設定
	var roundPoint; // 丸め小数桁変数
	var roundClass; // 丸め種別変数
	if (varTaxIncFrcKb == 1) {
		roundPoint = 1;
		roundClass = 2;
	} else if (varTaxIncFrcKb == 2) {
		roundPoint = 1;
		roundClass = 3;
	} else if (varTaxIncFrcKb == 3) {
		roundPoint = 1;
		roundClass = 1;
	} else if (varTaxIncFrcKb == 4) {
		roundPoint = 2;
		roundClass = 2;
	} else if (varTaxIncFrcKb == 5) {
		roundPoint = 2;
		roundClass = 3;
	} else if (varTaxIncFrcKb == 6) {
		roundPoint = 2;
		roundClass = 1;
	} else if (varTaxIncFrcKb == 7) {
		roundPoint = 3;
		roundClass = 2;
	} else if (varTaxIncFrcKb == 8) {
		roundPoint = 3;
		roundClass = 3;
	} else {
		roundPoint = 3;
		roundClass = 1;
	}

	// 数値丸め処理
	unitTaxPriceWk = getNumberRound(unitTaxPriceWk, roundPoint, roundClass);

	// 税抜き単価を算出
	var withoutTaxWk = varUnitPrice - unitTaxPriceWk;

	// 戻り値生成
	var foo = {};
	foo.unitTaxPrice = unitTaxPriceWk; // 単価消費税
	foo.withoutTax = withoutTaxWk; // 税抜き単価

	var JSONfoo = window.JSON.stringify(foo); // 戻り値（JSON）

	return JSONfoo;
}

/**
 * １明細当たり金額算出
 *
 * @param varUnitPrice
 *            単価
 * @param varOrderCnt
 *            受注数
 * @param varTaxRate
 *            消費税率
 * @param varTaxIncKb
 *            内税顧客区分
 * @param varTaxIncFrcKb
 *            内税消費税端数処理
 * @param varShipsRudKb
 *            伝票行計算金額まるめ
 * @return JSON １明細当たり金額情報格納クラス
 */
function getOneSpecPrice(varUnitPrice, varOrderCnt, varTaxRate, varTaxIncKb,
		varTaxIncFrcKb, varShipsRudKb) {

	// 変数宣言
	var flgSuccess = 0; // 処理結果格納用フラグ（0:処理失敗、1:処理成功）
	var priceWk = 0; // 金額
	var unitPriceWk = 0; // 単価（本体）
	var bodyPriceWk = 0; // 金額（本体）
	var taxPriceWk = 0; // 消費税額
	var JSONfooWk; // 単価消費税計算結果取得用
	var foo = {}; // 戻り値（JSON変換前）
	var JSONfoo; // 戻り値（JSON）

	try {

		// 金額算出
		// 1. 単価無編集にて、金額を算出する
		var dig = Math.pow(10, getPointMax(varUnitPrice) - 1); // 桁数に応じた累乗を取得
		priceWk = (varUnitPrice * dig) * varOrderCnt; // 正に変換
		priceWk = priceWk / dig; // 小数点を元に戻す

		// 2. 本体価格算出
		if (varTaxIncKb == 1) {
			// 変数[内税顧客区分] = 1の場合（外税顧客）
			unitPriceWk = varUnitPrice; // 変数[単価(本体)] = 入力[単価]
			bodyPriceWk = priceWk; // 変数[金額(本体)] = 変数[金額]
		} else {
			// 変数[内税顧客区分] = 2の場合（内税顧客）
			// 変数[単価(本体)] = 共通部品【単価消費税計算(モジュール名)】
			JSONfooWk = getUnitTaxPrice(varUnitPrice, varTaxRate, varTaxIncFrcKb);
			JSONfooWk = JSON.parse(JSONfooWk);
			unitPriceWk = JSONfooWk.withoutTax; // 税抜き単価取得
			dig = Math.pow(10, getPointMax(unitPriceWk) - 1); // 桁数に応じた累乗を取得
			bodyPriceWk = (unitPriceWk * dig) * varOrderCnt; // 変数[単価(本体)] × 入力[受注数]
			bodyPriceWk = bodyPriceWk / dig; // 小数点を元に戻す
		}

		// 3. 消費税額算出
		if (varTaxIncKb == 1) {
			// 変数[内税顧客区分] = 1の場合（外税顧客）
			// 変数[消費税額] = 変数[金額(本体)] × 入力[消費税率] / 100
			var dig1 = Math.pow(10, getPointMax(bodyPriceWk) - 1); // 桁数に応じた累乗を取得
			var dig2 = Math.pow(10, getPointMax(varTaxRate) - 1); // 桁数に応じた累乗を取得
			taxPriceWk = (bodyPriceWk * dig1) * (varTaxRate * dig2) / (100 * dig1 * dig2); // 小数点を元に戻す
		} else {
			// 変数[内税顧客区分] = 2の場合（内税顧客）
			// 変数[消費税額] = 変数[単価消費税] × 入力[受注数]
			dig = Math.pow(10, getPointMax(JSONfooWk.unitTaxPrice) - 1); // 桁数に応じた累乗を取得
			taxPriceWk = (JSONfooWk.unitTaxPrice * dig) * varOrderCnt / dig; // 小数点を元に戻す
		}


		// 4. 端数処理
		// 引数［伝票行計算金額丸め］に応じて、端数処理を行う。
		if (varShipsRudKb == 1 || varShipsRudKb == 2 || varShipsRudKb == 3) {
			priceWk = getNumberRound(priceWk, 1, varShipsRudKb); // 金額
			bodyPriceWk = getNumberRound(bodyPriceWk, 1, varShipsRudKb); // 金額（本体）
			taxPriceWk = getNumberRound(taxPriceWk, 1, varShipsRudKb); // 消費税額
		}

		// 処理結果設定
		flgSuccess = 1;

		// 戻り値生成
		foo.price = priceWk; // 金額
		foo.unitPrice = unitPriceWk; // 単価（本体）
		foo.bodyPrice = bodyPriceWk; // 金額（本体）
		foo.taxPrice = taxPriceWk; // 消費税額

	} catch (e) {
	} finally {

		if (flgSuccess == 1) {
			JSONfoo = window.JSON.stringify(foo);
		} else {
			// 変数[WK処理結果] = Falseの場合、Null で復帰する
			JSONfoo = null;
		}

		return JSONfoo;

	}

}

/**
 * 納品金額計算
 *
 * @param varBara
 *            バラ
 * @param varDeliUnitPricet
 *            納品単価
 * @param varTaxRate
 *            消費税率
 * @param varTaxIncKb
 *            内税顧客区分
 * @param varTaxIncFrcKb
 *            内税消費税端数処理
 * @param varShipsRudKb
 *            伝票行計算金額まるめ
 * @param varSumDeliPrice
 *            該当行以外の納品金額総和
 * @return JSON 納品金額情報格納クラス
 */
function getDeliPrice(varBara, varDeliUnitPricet, varTaxRate, varTaxIncKb,
		varTaxIncFrcKb, varShipsRudKb, varSumDeliPrice) {

	// 変数宣言
	var deliPriceWk; // 納品金額
	var bodyDeliPriceWk; // 納品単価（本体）
	var taxPriceWk; // 消費税額
	var sumDeliPrice; // 納品金額合計

	// 納品金額算出
	var JSONfooWk = getOneSpecPrice(varDeliUnitPricet, varBara, varTaxRate,
			varTaxIncKb, varTaxIncFrcKb, varShipsRudKb);

	// [変数]処理結果 = Null の場合、エラーとする
	if (JSONfooWk == null) {
		return null;
	}

	// 出力項目セット
	JSONfooWk = JSON.parse(JSONfooWk);
	deliPriceWk = JSONfooWk.price; // 金額
	bodyDeliPriceWk = JSONfooWk.bodyPrice; // 金額（本体）
	taxPriceWk = JSONfooWk.taxPrice; // 消費税額

	// 納品金額合計算出
	sumDeliPrice = eval(varSumDeliPrice) + eval(deliPriceWk);

	// 戻り値生成
	var foo = {};
	foo.deliPrice = deliPriceWk; // 納品金額
	foo.bodyDeliPrice = bodyDeliPriceWk; // 納品金額（本体）
	foo.taxPrice = taxPriceWk; // 消費税額
	foo.sumDeliPrice = sumDeliPrice; // 納品額合計

	var JSONfoo = window.JSON.stringify(foo);
	; // 戻り値（JSON）

	return JSONfoo;

}

/**
 * 年月日文字列と加算日からn日後 or n日前の日付を求める
 *
 * @param targetDate 対象日付（YYYYMMDD)
 * @param addDay 加算・減算日数
 * @returns {Date} Date型
 */
function computeDate(targetDate, addDays){
	//引数の日付文字列からDate型へ変換
	var dt = new Date(targetDate.substr(0, 4), targetDate.substr(4, 2)-1, targetDate.substr(6, 2));
	 var baseSec = dt.getTime();
	 var addSec = addDays * 86400000;//日数 * 1日のミリ秒数
	 var targetSec = baseSec + addSec;
	 dt.setTime(targetSec);
	 return dt;
}

/**
 * Date型の変数をYYYY/MM/DD形式に変換する。
 *
 * @param date Date型
 * @returns YYYY/MM/DD形式文字列
 */
function dateFormatYMD(date){
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();

	if ( month < 10 ) {
		month = '0' + month;
	}
	if ( day < 10 ) {
		day = '0' + day;
	}
	return  year + '/' + month + '/' + day;
}

/**
 * 月、日に0を補完し、日付文字列を作成する。(YYYYMMDD形式)
 *
 * @param year 年
 * @param month 月
 * @param day 日
 * @returns YYYYMMDD形式文字列
 */
function compDateZero(year, month, day){
	// 仕様変更：桁数が１桁の場合、０を補充する
	if(month.length == 1) {
		month = '0' + month;
	}
	if(day.length == 1) {
		day = '0' + day;
	}
	return  year + month +  day;
}

/**
 * YYYYMMDD形式の文字列をスラッシュ区切りに変換する。
 *
 * @param ymd 対象文字列
 * @return 変換後文字列
 */
function addSlash(ymd){
	var year = ymd.substr(0,4);
	var month = ymd.substr(4,2);
	var day = ymd.substr(6,2);
	return  year + '/' + month + '/' + day;
}

/**
 * 文字列からスラッシュを除去する。
 *
 * @param ymd 対象文字列
 * @return 変換後文字列
 */
function delSlash(ymd){
	return ymd.replace(/\u002f/g,'');
}


/**
 * 小数と整数の掛け算を行う。
 *
 * @param decimalVal 小数
 * @param intVal 整数
 * @retun 計算結果
 */
function multiply(decimalVal, intVal){
	var dig = Math.pow(10, getPointMax(decimalVal) - 1); // 桁数に応じた累乗を取得
	result = (decimalVal * dig) * intVal; // 正に変換
	result = result / dig; // 小数点を元に戻す
	return result;
}

/**
 * 数値を3桁づつカンマ区切りに変換する。
 *
 * @param str	対象数値
 * @returns	カンマ区切り変換数値
 */
function addFigure(str) {
	var num = new String(str).replace(/,/g, "");
	while(num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
	return num;
}

/**
 * 数値からカンマを除去する。
 *
 * @param str	対象数値
 * @returns	カンマ区切除去後数値
 */
function delFigure(str){
	var num = str = str.replace(/,/g, '');
	return num;
}

/**
 * 指定桁数まで左側に半角0を付与する。
 *
 * @param value 対象文字列
 * @param digit 桁数
 * @return 変換後文字列
 */
function addLeftZero(value, digit) {
    var val = new String(value);

    if (val == null) {
        val = "";
    }
    if (val.length < digit) {
        //値が桁数に足りない場合
        while (val.length < digit) {
            //左側に0を加算する。
            val = "0" + val;
        }
    }
    return val;
}
