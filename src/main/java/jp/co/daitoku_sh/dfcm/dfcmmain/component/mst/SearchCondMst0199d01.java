/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:SearchCondMst0199d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran 新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * 検索条件保持クラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class SearchCondMst0199d01 {

  /** [画面]分類.分類コード */
  private String category;
  /** [画面]区分名称 */
  private String kbNm;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getKbNm() {
    return kbNm;
  }

  public void setKbNm(String kbNm) {
    this.kbNm = kbNm;
  }

}