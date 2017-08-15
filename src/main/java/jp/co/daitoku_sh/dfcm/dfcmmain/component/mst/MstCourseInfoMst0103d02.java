/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstCourseInfoMst0103d02.java
 * 
 * 作成者:ABV)TramChu
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00                  ABV)TramChu      新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/
package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstCourseInfoMst0103d02 {
	private String cosCode;
	private String cosNmR;
	private String binKb;
	private String deliKb;
	private String target1;
	
	public String getCosCode() {
		return Util.convertSanitize(cosCode);
	}
	public void setCosCode(String cosCode) {
		this.cosCode = cosCode;
	}
	public String getCosNmR() {
		return Util.convertSanitize(cosNmR);
	}
	public void setCosNmR(String cosNmR) {
		this.cosNmR = cosNmR;
	}
	public String getBinKb() {
		return Util.convertSanitize(binKb);
	}
	public void setBinKb(String binKb) {
		this.binKb = binKb;
	}
	public String getDeliKb() {
		return Util.convertSanitize(deliKb);
	}
	public void setDeliKb(String deliKb) {
		this.deliKb = deliKb;
	}
	public String getTarget1() {
		return Util.convertSanitize(target1);
	}
	public void setTarget1(String target1) {
		this.target1 = target1;
	}
	
}