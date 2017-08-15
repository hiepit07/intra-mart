/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:OlCustConvMasterData.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/30
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/30 1.00 ABV)QuanTran 新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCustConv;

/**
 * オンライン得意先変換マスタ一覧
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */

public class OlCustConvMasterData extends MstCustConv {

  /** CST.得意先略称 */
  private String custNmR;
  /** SHP.店舗略称 */
  private String shopNmR;
  /** GEN.項目1 */
  private String target1;
  
  /** [画面]［画面］オンライン得意先変換マスタ一覧.追加フラグ */
  private String addFlg;
  /** [画面]オンライン得意先変換マスタ一覧.更新フラグ */
  private String updateFlg;
  
  private String deleteFlg;
  
  public String getAddFlg() {
    return addFlg;
  }

  
  public void setAddFlg(String addFlg) {
    this.addFlg = addFlg;
  }

  
  public String getUpdateFlg() {
    return updateFlg;
  }

  
  public void setUpdateFlg(String updateFlg) {
    this.updateFlg = updateFlg;
  }

  
  public String getDeleteFlg() {
    return deleteFlg;
  }

  
  public void setDeleteFlg(String deleteFlg) {
    this.deleteFlg = deleteFlg;
  }

  public String getCustNmR() {
    return custNmR;
  }

  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }

  public String getShopNmR() {
    return shopNmR;
  }

  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }

  public String getTarget1() {
    return target1;
  }

  public void setTarget1(String target1) {
    this.target1 = target1;
  }
}
