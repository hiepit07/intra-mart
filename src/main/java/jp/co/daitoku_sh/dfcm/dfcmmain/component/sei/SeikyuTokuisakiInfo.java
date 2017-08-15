/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:SeikyuTokuisakiInfo.java
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
 * 自社請求書 得意先計 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class SeikyuTokuisakiInfo {

  private String chainCd;           // チェーンコード
  private String chainIdx;          // チェーン枝番
  private String seikyusakiCd;      // 請求先コード
  private String tokuisakiCd;       // 得意先コード
  private String torihikiCd;        // 取引コード
  private String tokuisakiName;     // 得意先名
  private String seikyusakiName;    // 請求先名
  private String seikyusakiZipCode; // 請求先郵便番号
  private String seikyusakiAddr1;   // 請求先住所1
  private String seikyusakiAddr2;   // 請求先住所2

  private int uriageGaku;   // 売上額
  private int tax;          // 消費税
  private int pageNo;       // ページ番号
  private int pageSum;      // ページ計
  private int maxPageNo;    // 最大ページ番号

  /**
   * chainCd getter.
   * 
   */
  public String getChainCd() {
    return chainCd;
  }

  /**
   * chainCd seter.
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
   * torihikiCd getter.
   * 
   */
  public String getTorihikiCd() {
    return torihikiCd;
  }

  /**
   * torihikiCd setter.
   * 
   */
  public void setTorihikiCd(String torihikiCd) {
    this.torihikiCd = torihikiCd;
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
   * seikyusakiZipCode getter.
   * 
   */
  public String getSeikyusakiZipCode() {
    return seikyusakiZipCode;
  }

  /**
   * seikyusakiZipCode setter.
   * 
   */
  public void setSeikyusakiZipCode(String seikyusakiZipCode) {
    this.seikyusakiZipCode = seikyusakiZipCode;
  }

  /**
   * seikyusakiAddr1 getter.
   * 
   */
  public String getSeikyusakiAddr1() {
    return seikyusakiAddr1;
  }

  /**
   * seikyusakiAddr1 setter.
   * 
   */
  public void setSeikyusakiAddr1(String seikyusakiAddr1) {
    this.seikyusakiAddr1 = seikyusakiAddr1;
  }

  /**
   * seikyusakiAddr2 getter.
   * 
   */
  public String getSeikyusakiAddr2() {
    return seikyusakiAddr2;
  }

  /**
   * seikyusakiAddr2 setter.
   * 
   */
  public void setSeikyusakiAddr2(String seikyusakiAddr2) {
    this.seikyusakiAddr2 = seikyusakiAddr2;
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
