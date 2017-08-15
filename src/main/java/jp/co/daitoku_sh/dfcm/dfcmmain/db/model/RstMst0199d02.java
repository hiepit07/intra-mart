/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.model
 * ファイル名:RstMst0199d02.java
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
 * 管理レコードデータ取得
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class RstMst0199d02 extends MstGeneral {

  /** カテゴリ名称 */
  private String categoryName;

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
