/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:OkuwaTempoInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/10/30
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/30 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.sei;

/**
 * オークワ請求書 店舗情報（鑑） DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class OkuwaTempoInfo {

  private String tempoCd;    // 店舗コード
  private int recordSu;      // レコード数
  private int uriageGaku;    // 売上額
  private int tax;           // 消費税
  private String validDigit; // 有効桁数

  /**
   * tempoCd getter.
   * 
   */
  public String getTempoCd() {
    return tempoCd;
  }

  /**
   * tempoCd setter.
   * 
   */
  public void setTempoCd(String tempoCd) {
    this.tempoCd = tempoCd;
  }

  /**
   * recordSu getter.
   * 
   */
  public int getRecordSu() {
    return recordSu;
  }

  /**
   * recordSu setter.
   * 
   */
  public void setRecordSu(int recordSu) {
    this.recordSu = recordSu;
  }

  /**
   * uriageGaku getter.
   * 
   */
  public int getUriageGaku() {
    return uriageGaku;
  }

  /**
   * uriageGaku setter.
   * 
   */
  public void setUriageGaku(int uriageGaku) {
    this.uriageGaku = uriageGaku;
  }

  /**
   * tax getter.
   * 
   */
  public int getTax() {
    return tax;
  }

  /**
   * tax setter.
   * 
   */
  public void setTax(int tax) {
    this.tax = tax;
  }

  /**
   * validDigit getter.
   * 
   */
  public String getValidDigit() {
    return validDigit;
  }

  /**
   * validDigit setter.
   * 
   */
  public void setValidDigit(String validDigit) {
    this.validDigit = validDigit;
  }

}
