/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:SubjectData.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

/**
 * 勘定科目情報管理クラス
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */

public class SubjectData {

  /** 発生場所 */
  private String secCode;

  /** 勘定科目 */
  private String subNm;

  /** 勘定科目コード */
  private String subCode;

  /** 補助科目設定区分 */
  private String supSetKb;

  /** 補助科目固定値 */
  private String supSetVal;

  /** 税区分 */
  private String taxKb;

  
  /**
   * secCodeを取得する.
   *
   * @return String secCode
   */
  public String getSecCode() {
    return secCode;
  }

  
  /**
   * secCodeをセットする.
   *
   * @param secCode secCode
   */
  public void setSecCode(String secCode) {
    this.secCode = secCode;
  }

  
  /**
   * subNmを取得する.
   *
   * @return String subNm
   */
  public String getSubNm() {
    return subNm;
  }

  
  /**
   * subNmをセットする.
   *
   * @param subNm subNm
   */
  public void setSubNm(String subNm) {
    this.subNm = subNm;
  }

  
  /**
   * subCodeを取得する.
   *
   * @return String subCode
   */
  public String getSubCode() {
    return subCode;
  }

  
  /**
   * subCodeをセットする.
   *
   * @param subCode subCode
   */
  public void setSubCode(String subCode) {
    this.subCode = subCode;
  }

  
  /**
   * supSetKbを取得する.
   *
   * @return String supSetKb
   */
  public String getSupSetKb() {
    return supSetKb;
  }


  /**
   * supSetKbをセットする.
   *
   * @param supSetKb supSetKb
   */
  public void setSupSetKb(String supSetKb) {
    this.supSetKb = supSetKb;
  }


  /**
   * supSetValを取得する.
   *
   * @return String supSetVal
   */
  public String getSupSetVal() {
    return supSetVal;
  }

  
  /**
   * supSetValをセットする.
   *
   * @param supSetVal supSetVal
   */
  public void setSupSetVal(String supSetVal) {
    this.supSetVal = supSetVal;
  }

  
  /**
   * taxKbを取得する.
   *
   * @return String taxKb
   */
  public String getTaxKb() {
    return taxKb;
  }

  
  /**
   * taxKbをセットする.
   *
   * @param taxKb taxKb
   */
  public void setTaxKb(String taxKb) {
    this.taxKb = taxKb;
  }

}