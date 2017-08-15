/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:OkuwaMeisaiInfo.java
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
 * オークワ請求書 明細情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class OkuwaMeisaiInfo {

  private String tempoCd;           // 店舗コード
  private String tempoName;         // 店舗名
  private String nohinbi;           // 納品日
  private String tokuisakiDempyoNo; // 得意先伝票No
  private int uriageGaku;           // 売上額
  private int tax;                  // 消費税

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
   * tempoName getter.
   * 
   */
  public String getTempoName() {
    return tempoName;
  }

  /**
   * tempoName setter.
   * 
   */
  public void setTempoName(String tempoName) {
    this.tempoName = tempoName;
  }

  /**
   * nohinbi getter.
   * 
   */
  public String getNohinbi() {
    return nohinbi;
  }

  /**
   * nohinbi setter.
   * 
   */
  public void setNohinbi(String nohinbi) {
    this.nohinbi = nohinbi;
  }

  /**
   * tokuisakiDempyoNo getter.
   * 
   */
  public String getTokuisakiDempyoNo() {
    return tokuisakiDempyoNo;
  }

  /**
   * tokuisakiDempyoNo setter.
   * 
   */
  public void setTokuisakiDempyoNo(String tokuisakiDempyoNo) {
    this.tokuisakiDempyoNo = tokuisakiDempyoNo;
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

}
