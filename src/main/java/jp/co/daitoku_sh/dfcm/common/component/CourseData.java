/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:CourseData.java
 * 
 * 作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/01 1.00   ACT)福田    新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.common.component;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCourse;

/**
 * コース情報格納クラス
 * 
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public class CourseData {
	
	/** コースマスタ */
	private MstCourse crs;
	
	/** メッセージコード */
	private String msgCd;

	/**
	 * getter/setter
	 */
	public MstCourse getCrs() {
		return crs;
	}

	public void setCrs(MstCourse crs) {
		this.crs = crs;
	}

	public String getMsgCd() {
		return msgCd;
	}

	public void setMsgCd(String msgCd) {
		this.msgCd = msgCd;
	}

}
