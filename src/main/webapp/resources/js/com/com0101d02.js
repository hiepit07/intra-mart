/**
 * ファイル名: com0101d02.js
 * 
 * 作成者:アクトブレーンベトナム 
 * 作成日:2015/09/17
 * 
 * 履歴
 *  -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/17 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

jQuery(document).ready(function($) {
	// Get the menu contents
	var menu = $('#menu').html();
	// Saves the menu contents to local storage.
	$.jStorage.set('menu', menu);
});