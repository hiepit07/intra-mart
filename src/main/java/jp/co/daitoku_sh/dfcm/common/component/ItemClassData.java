/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ItemClassData.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/25
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 商品分類情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class ItemClassData {

  /** 商品分類 */
  private String strItemClass;

  /** 処理結果 */
  private boolean blnProcResult;

  /**
   * 商品分類getter
   *
   * @return strItemClass 商品分類
   */
  public String getItemClass() {
    return strItemClass;
  }

  /**
   * 商品分類setter
   *
   * @param strItemClass 商品分類
   */
  public void setItemClass(String strItemClass) {
    this.strItemClass = strItemClass;
  }

  /**
   * 処理結果getter
   *
   * @return blnProcResult 処理結果
   */
  public boolean getProcResult() {
    return blnProcResult;
  }

  /**
   * 処理結果setter
   *
   * @param blnProcResult 処理結果
   */
  public void setProcResult(boolean blnProcResult) {
    this.blnProcResult = blnProcResult;
  }

}
