/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:SeikyuMeisaiInfo.java
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
 * 自社請求書 明細情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SeikyuMeisaiInfo {

  private String chainCd;           // チェーンコード
  private String chainIdx;          // チェーン枝番
  private String seikyusakiCd;      // 請求先コード
  private String tokuisakiCd;       // 得意先コード
  private String tempoCd;           // 店舗コード
  private String nohinbi;           // 納品日
  private String tokuisakiDempyoNo; // 得意先伝票No

  private int uriageGaku; // 売上額
  private int tax;        // 消費税
  private int pageNo;     // ページ番号
  private int pageSum;    // ページ計
  private int maxPageNo;  // 最大ページ番号

  /**
   * chainCd getter.
   * 
   */
  public String getChainCd() {
    return chainCd;
  }

  /**
   * chainCd setter.
   * 
   */
  public void setChainCd(String chainCd) {
    this.chainCd = chainCd;
  }

  /**
   * chainIdx getter.
   * 
   */
  public String getChainIdx() {
    return chainIdx;
  }

  /**
   * chainIdx setter.
   * 
   */
  public void setChainIdx(String chainIdx) {
    this.chainIdx = chainIdx;
  }

  /**
   * seikyusakiCd getter.
   * 
   */
  public String getSeikyusakiCd() {
    return seikyusakiCd;
  }

  /**
   * seikyusakiCd setter.
   * 
   */
  public void setSeikyusakiCd(String seikyusakiCd) {
    this.seikyusakiCd = seikyusakiCd;
  }

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

  /**
   * pageNo getter.
   * 
   */
  public int getPageNo() {
    return pageNo;
  }

  /**
   * pageNo setter.
   * 
   */
  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  /**
   * pageSum getter.
   * 
   */
  public int getPageSum() {
    return pageSum;
  }

  /**
   * pageSum setter.
   * 
   */
  public void setPageSum(int pageSum) {
    this.pageSum = pageSum;
  }

  /**
   * maxPageNo getter.
   * 
   */
  public int getMaxPageNo() {
    return maxPageNo;
  }

  /**
   * maxPageNo setter.
   * 
   */
  public void setMaxPageNo(int maxPageNo) {
    this.maxPageNo = maxPageNo;
  }

}
