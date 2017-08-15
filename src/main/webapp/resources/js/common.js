/**
 * パッケージ名: ファイル名: common.js
 * 
 * 作成者:アクトブレーンベトナム 作成日:2015/09/17
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

var rootPath = getContextPath();
function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}

function resetTableSize() {
	// Handle width Table Scroll
	var tblBodyHeight = $("#tblBody").height();
	var divBodyHeight = $("#divBody").height();
	if (tblBodyHeight > divBodyHeight) {
		$("#divBody").css("overflow-y", "scroll");
		$("#divHead").width($("#divBody").width() - 17);
		$("#divFooter").width($("#divBody").width() - 17);
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width() - 17);
			$("#divFooter").width($("#divBody").width() - 17);
		});
	} else {
		$("#divBody").css("overflow-y", "hidden");
	}
}

/**
 * 指定された文字を数字に追加する
 * Ｅｘ：　addLeadingChar(10, 4) --> 0010, addLeadingChar(10, 4, '-') --> --10
 * 
 * @param number：　編集したい値
 * @param width：　最大の長さ
 * @param charToAdd：　追加したい文字　 
 * @return 編集された値
 */
function addLeadingChar(number, width, charToAdd) {
	charToAdd = charToAdd || '0';
	number = number + '';
	return number.length >= width ? number : new Array(width - number.length + 1).join(charToAdd) + number;
}

/**
 * ControlIDListにエラークラスを追加する。
 * 
 * @param idArray:ControlIDList
 */
function addClassErrorToControl(idArray) {
	for (var i = 0 ; i < idArray.length ; i++) {
		$("#" + idArray[i]).addClass("form-error-field");
	}
}

/**
 * ボタン有効にする
 * 
 * @param id: ControlID
 */
function enableButton(id) {
	$("#" + id).prop("disabled", false).removeClass("btn_button_disabled").addClass("btn_button");
}

/**
 * ボタン無効にする
 * 
 * @param id: ControlID
 */
function disableButton(id) {
	$("#" + id).prop("disabled", true).removeClass("btn_button").addClass("btn_button_disabled");
}

/**
 * 戻るボタン有効にする
 * 
 * @param id: ControlID
 */
function enableReturnButton(id) {
	$("#" + id).prop("disabled", false).removeClass("btn_back_disabled").addClass("btn_back");
}

/**
 * 戻るボタン無効にする
 * 
 * @param id: ControlID
 */
function disableReturnButton(id) {
	$("#" + id).prop("disabled", true).removeClass("btn_back").addClass("btn_back_disabled");
}

/**
 * お知らせメッセージのclassの設定 
 * 
 */
function addInfoClass() {
	$("#messError").removeClass("messError_error").addClass("messError_info");
}

/**
 * お知らせメッセージのclassの削除 
 * 
 */
function removeInfoClass() {
	$("#messError").removeClass("messError_info").addClass("messError_error");
}

/**
 * 文字から「／」を削除する
 * 
 * @param val ： 編集したい値
 * @returns 編集された値
 */
function removeSlashChar(val) {
	return val.replace(/\//g, "");
}

/**
 * 日付フォーマットチェック
 *
 * @param val ： チェック値
 * @param format ： 日付フォーマット(※形式は定数参照)
 * @returns チェック結果
 */
function checkDate(val, format) {
	val = removeSlashChar(val).trim();
	return chkDate(val, format);
}

/**
 * 文字から「／」を追加する
 * 
 * @param val ： 編集したい値
 * @returns 編集された値
 */
function addSlashChar(val) {
	val = removeSlashChar(val).trim();
	if (val.length == 0) return "";
	else if (val.length >= 1 && val.length <= 4) return val + "//";
	else if (val.length >= 5 && val.length <= 6) return val.substring(0, 4) + "/" + val.substring(4) + "/";
	else if (val.length >= 7) return val.substring(0, 4) + "/" + val.substring(4, 6) + "/" + val.substring(6);
	else return val;
}

/**
 * empty文字の削除
 * @param 
 * @return
 * @exception
 */
function replaceText(text) {
	text = text.replace(/ /g,"");
	return text;
}

/**
 * 文字の削除
 * @param 
 * @return
 * @exception
 */
function replaceNum(num) {
	var str = "";
	var str1 = num;
	num = num.replace(/[a-zA-z]/gi,"");
	str = str + num;
	if (str1.length - str.length > 0) {
		return str1;
	} else {
		return str1;
	}
}

/**
 * jStorageの指定した「key」の値の存在チェック
 * 
 * @param key ： チェックしたい「key」値
 * @returns チェック結果 ： true(データが存在)/false(データが無い)
 */
function checkStorageExist(key) {
	// 状態を保存されているデータをチェックする
	var value = $.jStorage.get(key, null);
	if (value == null) {
		// データが無い場合
		return false;
	} else {
		// データがある場合
		return true;
	}
}

/**
 * jStorageの格納処理を行う
 * 
 * @param key ： 格納したい「key」
 * @param valueArray ： 格納したい「valueArray」
 */
function saveToStorage(key, valueArray) {
	var value = valueArray.join(COMMON_DELIMITER);
	$.jStorage.set(key, value);
}

/**
 * jStorageのデータと現在のデータを比べる
 * 
 * @param key ： jStorageの格納した「key」
 * @param valueArray ： 比べたい「valueArray」
 * @returns チェック結果 ： true(データがマッチ)/false(データがマッチしない)
 */
function compareWithStorage(key, valueArray) {
	// 状態を保存されているデータをチェックする
	var dataInStorage = $.jStorage.get(key, null);
	if (dataInStorage != null) {
		var dataToCompare = valueArray.join(COMMON_DELIMITER);
		// 比べる
		if (dataInStorage == dataToCompare) {
			// データがマッチする
			return true;
		} else {
			// データがマッチしない
			return false;
		}
	} else {
		// データが無い場合
		return false;
	}
}

/**
 * jStorageのクリア処理
 * 
 * @param key ： jStorageのクリアしたい「key」
 */
function clearStorage(key) {
	$.jStorage.set(key, null);
}