/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstSpecificationsConfirmationMst0103d02.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)TramChu  新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstSpecificationsConfirmationMst0103d02 {

  private String olCenterCode;
  private String atTorihikiCode;
  private String atTenCode;
  private String deliKb;
  private String target1;

  public String getolCenterCode() {
    return Util.convertSanitize(olCenterCode);
  }

  public void setolCenterCode(String olCenterCode) {
    this.olCenterCode = olCenterCode;
  }

  public String getatTorihikiCode() {
    return Util.convertSanitize(atTorihikiCode);
  }

  public void setatTorihikiCode(String atTorihikiCode) {
    this.atTorihikiCode = atTorihikiCode;
  }

  public String getatTenCode() {
    return Util.convertSanitize(atTenCode);
  }

  public void setatTenCode(String atTenCode) {
    this.atTenCode = atTenCode;
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