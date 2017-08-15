/**
 * ファイル名:utils.js
 * 概要:共通関数
 * 
 * 作成者:ABV）コイー
 * 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00                  ABV）コイー        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

/**
 * エンターキーの処理
 * 
 * @param event
 *            handler
 * @param element
 *            イベント
 * @param className
 * @return
 * @exception
 */
function EnterKey(event, element, className) {
	var next_idx = $(className).find("input[type^=text]:enabled:not([readonly])").index(element) + 1;
	if (next_idx == 0) {
		return;
	}
	// get number of text input element in a html document	
	var tot_idx = $(className)
			.find("input[type^=text]:enabled:not([readonly])").length;
	if (tot_idx == next_idx) {
		// go to the first text element if focused in the last text input
		// element
		$(className).find("input[type^=text]:enabled:not([readonly]):eq(0)").focus().select();
	} else {
		// go to the next text input element
		$(className).find("input[type^=text]:enabled:not([readonly]):eq(" + next_idx + ")").focus().select();
	}

}

/**
 * null値は""に変換する。
 * @param　item 項目
 * @return 
 * @exception
 */
function ReplaceNullReturnBlank(item) {
    return item = (item == null) ? '' : item;
}

/**
 * Check whether an element disabled
 * 
 * @param id The id attribute specifies a unique id for an HTML element
 * @returns Boolean true if the element is disabled; false otherwise
 */
function isDisabled(id) {
	return $("#" + id).is(':disabled');
}

/**
 * Check whether an element is enabled.
 * 
 * @param id The id attribute specifies a unique id for an HTML element
 * @returns Boolean true if the element is enabled; false otherwise
 */
function isEnabled (id) {
	return $("#" + id).is(':enabled');
}

/**
 * Check whether an image button is enabled.
 * 
 * @param id The id attribute for an HTML element
 * @returns {Boolean} true if the element is enabled; false otherwise
 */
function isBtnImgEnabled (id) {
	return ($("#" + id).attr("src").indexOf("dis") < LENGTH_ZERO) 
}

/**
 * Change the button's image.
 *  
 * @param id The id attribute for an HTML element
 */

function disableImgButton (id) {
	var src = $("#" + id).attr("src").replace("search.png", "search_dis.png");
	$("#" + id).attr("src", src).removeClass("cursor_pointer").addClass("cursor_default");
}

/**
 * Change the button's image.
 * 
 * @param id The id attribute for an HTML element
 */
function enableImgButton (id) {
	var src = $("#" + id).attr("src").replace("search_dis.png", "search.png");
	$("#" + id).attr("src", src).removeClass("cursor_default").addClass("cursor_pointer");
}

/**
 * Display messages 
 * 
 * @param messageContent
 * @param messageType
 */
function displayMessages (messageContent, messageType) {
	if (messageType != null) {
		if (messageType == MSG_INFO) {
			addInfoClass();
		} else if(messageType == MSG_ERR) {
			removeInfoClass();
		}
	}
	$("#txtMess").html(messageContent);
}

/**
 * Sanitizer htmlEntities
 * @param str
 * @returns
 */
function htmlEntities(str) {
	if (typeof str == "undefined" || str == null || str == ""){
		return "";
	}
	return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#039;');
}

/**
 * Finding out whether a browser window has a scroll bar visible
 */
function checkScrollBar() {
	var hContent = $("html").height(); // get the height of your content
	var hWindow = $(window).height();  // get the height of the visitor's browser window

	// if the height of your content is bigger than the height of the 
	// browser window, we have a scroll bar
	if(hContent> hWindow) { 
		return true;
	}
	return false;
}