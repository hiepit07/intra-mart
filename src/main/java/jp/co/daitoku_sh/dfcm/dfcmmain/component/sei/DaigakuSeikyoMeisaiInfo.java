/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:DaigakuSeikyoMeisaiInfo.java
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
 * 大学生協請求書 明細情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class DaigakuSeikyoMeisaiInfo {

  private String tokuisakiCd;    // 得意先コード
  private String seikyusakiName; // 請求先名
  private String tokuisakiName;  // 得意先名
  private String tempoCd;        // 店舗コード
  private String tempoName;      // 店舗名
  private int nohinKingaku;      // 納品金額
  private int nohinDempyoSu;     // 納品伝票数
  private int hempinKingaku;     // 返品金額
  private int hempinDempyoSu;    // 返品伝票数

  /**
   * tokuisakiCd getter.
   * 
   */
  public String getTokuisakiCd() {
    return tokuisakiCd;
  }

  /**
   * tokuisakiCd setter.
   * 
   */
  public void setTokuisakiCd(String tokuisakiCd) {
    this.tokuisakiCd = tokuisakiCd;
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
   * tokuisakiName getter.
   * 
   */
  public String getTokuisakiName() {
    return tokuisakiName;
  }

  /**
   * tokuisakiName setter.
   * 
   */
  public void setTokuisakiName(String tokuisakiName) {
    this.tokuisakiName = tokuisakiName;
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
   * nohinKingaku getter.
   * 
   */
  public int getNohinKingaku() {
    return nohinKingaku;
  }

  /**
   * nohinKingaku setter.
   * 
   */
  public void setNohinKingaku(int nohinKingaku) {
    this.nohinKingaku = nohinKingaku;
  }

  /**
   * nohinDempyoSu getter.
   * 
   */
  public int getNohinDempyoSu() {
    return nohinDempyoSu;
  }

  /**
   * nohinDempyoSu setter.
   * 
   */
  public void setNohinDempyoSu(int nohinDempyoSu) {
    this.nohinDempyoSu = nohinDempyoSu;
  }

  /**
   * hempinKingaku getter.
   * 
   */
  public int getHempinKingaku() {
    return hempinKingaku;
  }

  /**
   * hempinKingaku setter.
   * 
   */
  public void setHempinKingaku(int hempinKingaku) {
    this.hempinKingaku = hempinKingaku;
  }

  /**
   * hempinDempyoSu getter.
   * 
   */
  public int getHempinDempyoSu() {
    return hempinDempyoSu;
  }

  /**
   * hempinDempyoSu setter.
   * 
   */
  public void setHempinDempyoSu(int hempinDempyoSu) {
    this.hempinDempyoSu = hempinDempyoSu;
  }

}
