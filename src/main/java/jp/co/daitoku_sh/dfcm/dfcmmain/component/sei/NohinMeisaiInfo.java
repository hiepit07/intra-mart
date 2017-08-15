/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:NohinMeisaiInfo.java
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
 * 納品明細書 明細情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class NohinMeisaiInfo {

  private String seikyuShimebi;  // 請求締め日
  private String jigyoshoCd;     // 事業所コード
  private String seikyusakiName; // 請求先名
  private String himmokuCd;      // 品目コード
  private String himmokuName;    // 品目名
  private String nohinBi;        // 納品日
  private String suryo;          // 数量
  private String tanka;          // 単価
  private String kingaku;        // 金額

  /**
   * seikyuShimebi getter.
   * 
   */
  public String getSeikyuShimebi() {
    return seikyuShimebi;
  }

  /**
   * seikyuShimebi setter.
   * 
   */
  public void setSeikyuShimebi(String seikyuShimebi) {
    this.seikyuShimebi = seikyuShimebi;
  }

  /**
   * jigyoshoCd getter.
   * 
   */
  public String getJigyoshoCd() {
    return jigyoshoCd;
  }

  /**
   * jigyoshoCd setter.
   * 
   */
  public void setJigyoshoCd(String jigyoshoCd) {
    this.jigyoshoCd = jigyoshoCd;
  }

  /**
   * seikyusakiName getter.
   * 
   */
  public String getSeikyusakiName() {
    return seikyusakiName;
  }

  /**
   * seikyusakiName setter.
   * 
   */
  public void setSeikyusakiName(String seikyusakiName) {
    this.seikyusakiName = seikyusakiName;
  }

  /**
   * himmokuCd getter.
   * 
   */
  public String getHimmokuCd() {
    return himmokuCd;
  }

  /**
   * himmokuCd setter.
   * 
   */
  public void setHimmokuCd(String himmokuCd) {
    this.himmokuCd = himmokuCd;
  }

  /**
   * himmokuName getter.
   * 
   */
  public String getHimmokuName() {
    return himmokuName;
  }

  /**
   * himmokuName setter.
   * 
   */
  public void setHimmokuName(String himmokuName) {
    this.himmokuName = himmokuName;
  }

  /**
   * nohinBi getter.
   * 
   */
  public String getNohinBi() {
    return nohinBi;
  }

  /**
   * nohinBi setter.
   * 
   */
  public void setNohinBi(String nohinBi) {
    this.nohinBi = nohinBi;
  }

  /**
   * suryo getter.
   * 
   */
  public String getSuryo() {
    return suryo;
  }

  /**
   * suryo setter.
   * 
   */
  public void setSuryo(String suryo) {
    this.suryo = suryo;
  }

  /**
   * tanka getter.
   * 
   */
  public String getTanka() {
    return tanka;
  }

  /**
   * tanka setter.
   * 
   */
  public void setTanka(String tanka) {
    this.tanka = tanka;
  }

  /**
   * kingaku getter.
   * 
   */
  public String getKingaku() {
    return kingaku;
  }

  /**
   * kingaku setter.
   * 
   */
  public void setKingaku(String kingaku) {
    this.kingaku = kingaku;
  }

}
