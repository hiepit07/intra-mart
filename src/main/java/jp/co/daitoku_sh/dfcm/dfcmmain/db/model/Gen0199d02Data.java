/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.model
 * ファイル名:Gen0199d02Data.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.model;

import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;

/**
 * 汎用区分設定一覧データ取得
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class Gen0199d02Data extends MstGeneral{
  /** [画面]汎用区分設定一覧[i].追加フラグ */
  private String addFlg;
  /** [画面]汎用区分設定一覧[i].更新フラグ */
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
  
  /**
   * Get the target.
   * @param colIndex The column index
   * @return The target
   */
  public String getTarget(int colIndex) {
    String target = null;
    switch (colIndex) {
    case 1:
      target = this.getTarget1();
      break;
    case 2:
      target = this.getTarget2();
      break;
    case 3:
      target = this.getTarget3();
      break;
    case 4:
      target = this.getTarget4();
      break;
    case 5:
      target = this.getTarget5();
      break;
    case 6:
      target = this.getTarget6();
      break;
    case 7:
      target = this.getTarget7();
      break;
    case 8:
      target = this.getTarget8();
      break;
    case 9:
      target = this.getTarget9();
      break;
    case 10:
      target = this.getTarget10();
      break;
    default:
      break;
    }
    return target;
  }
}
