/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:CrsLst0104d02Data.java
 * 
 * <p>作成者:NhungMa
 * 作成日:2015/10/30
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/30 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstDeliveryMst0104d02;

/**
 * Data each row in 納入先
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
public class CrsLst0104d02Data extends MstDeliveryMst0104d02 {

  private String renBan;
  private String addFlg;
  private String updateFlg;

  public String getRenBan() {
    return renBan;
  }

  public void setRenBan(String renBan) {
    this.renBan = renBan;
  }

  public void setAddFlg(String addFlg) {
    this.addFlg = addFlg;
  }

  public String getAddFlg() {
    return addFlg;
  }

  public String getUpdateFlg() {
    return updateFlg;
  }

  public void setUpdateFlg(String updateFlg) {
    this.updateFlg = updateFlg;
  }

}