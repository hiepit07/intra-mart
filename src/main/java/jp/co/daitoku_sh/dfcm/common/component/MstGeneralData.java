/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:MstGeneralData
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/11
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/11 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 汎用マスタ情報格納クラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class MstGeneralData {

  private String glCode; // 汎用マスタ.コード
  private String target1; // 汎用マスタ.項目１

  /**
   * 汎用マスタコードgetter
   *
   * @return glCode 汎用マスタコード
   */
  public String getGlCode() {
    return glCode;
  }

  /**
   * 汎用マスタコードsetter
   *
   * @param glCode 汎用マスタコード
   */
  public void setGlCode(String glCode) {
    this.glCode = glCode;
  }


  /**
   * 汎用マスタ項目１getter
   *
   * @return target1 汎用マスタ項目１
   */
  public String getTarget1() {
    return target1;
  }

  /**
   * 汎用マスタコードsetter
   *
   * @param target1 汎用マスタ項目１
   */
  public void setTarget1(String target1) {
    this.target1 = target1;
  }

}
