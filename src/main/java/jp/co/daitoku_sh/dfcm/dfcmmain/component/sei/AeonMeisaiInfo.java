/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:AeonMeisaiInfo.java
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
 * イオン請求書 明細情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class AeonMeisaiInfo {

  private String nohinbi;           // 納品日
  private String tempoCd;           // 店舗コード
  private String bunruiCdStShop;    // 分類コード（定番・店直）
  private String bunruiCdStCenter;  // 分類コード（定番・センター）
  private String bunruiCdSpShop;    // 分類コード（特売・店直）
  private String bunruiCdSpCenter;  // 分類コード（特売・センター）
  private String nohinKbn;          // 納品区分
  private String hambaiKbn;         // 販売区分
  private String tokuisakiDempyoNo; // 得意先伝票No
  private int uriageGaku;           // 売上額
  private int tax;                  // 消費税

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
   * bunruiCdStShop getter.
   * 
   */
  public String getBunruiCdStShop() {
    return bunruiCdStShop;
  }

  /**
   * bunruiCdStShop setter.
   * 
   */
  public void setBunruiCdStShop(String bunruiCdStShop) {
    this.bunruiCdStShop = bunruiCdStShop;
  }

  /**
   * bunruiCdStCenter getter.
   * 
   */
  public String getBunruiCdStCenter() {
    return bunruiCdStCenter;
  }

  /**
   * bunruiCdStCenter setter.
   * 
   */
  public void setBunruiCdStCenter(String bunruiCdStCenter) {
    this.bunruiCdStCenter = bunruiCdStCenter;
  }

  /**
   * bunruiCdSpShop getter.
   * 
   */
  public String getBunruiCdSpShop() {
    return bunruiCdSpShop;
  }

  /**
   * bunruiCdSpShop setter.
   * 
   */
  public void setBunruiCdSpShop(String bunruiCdSpShop) {
    this.bunruiCdSpShop = bunruiCdSpShop;
  }

  /**
   * bunruiCdSpCenter getter.
   * 
   */
  public String getBunruiCdSpCenter() {
    return bunruiCdSpCenter;
  }

  /**
   * bunruiCdSpCenter setter.
   * 
   */
  public void setBunruiCdSpCenter(String bunruiCdSpCenter) {
    this.bunruiCdSpCenter = bunruiCdSpCenter;
  }

  /**
   * nohinKbn getter.
   * 
   */
  public String getNohinKbn() {
    return nohinKbn;
  }

  /**
   * nohinKbn setter.
   * 
   */
  public void setNohinKbn(String nohinKbn) {
    this.nohinKbn = nohinKbn;
  }

  /**
   * hambaiKbn getter.
   * 
   */
  public String getHambaiKbn() {
    return hambaiKbn;
  }

  /**
   * hambaiKbn setter.
   * 
   */
  public void setHambaiKbn(String hambaiKbn) {
    this.hambaiKbn = hambaiKbn;
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
