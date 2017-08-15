/**
 * ファイル名:sei0102d01.js
 * 概要:担当者マスタ一覧画面
 * 
 * 作成者:ABV）コアー
 * 作成日:2015/09/28
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

jQuery(document).ready(function($) {

	/** 変数定義 */
	initControlState();
});
/**
 * ボタン、画面、フォーカス制御初期化
 */
function initControlState() {
	
	// 画面制御
	var sysAdminFlag = $("#sysAdminFlag").val().trim();
	if (sysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
		// 事業所を表示する
		$("#tblDataCondition tbody").find("tr").eq(0).removeClass("display_none");
	} else if (sysAdminFlag == SYS_ADMIN_FLAG_USER) {
		// 事業所を非表示する
		$("#tblDataCondition tbody").find("tr").eq(0).addClass("display_none");
	}
}