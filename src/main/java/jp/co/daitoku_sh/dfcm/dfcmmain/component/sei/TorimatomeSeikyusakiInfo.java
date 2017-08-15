/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:TorimatomeSeikyusakiInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/11/29
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/29 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.sei;

/**
 * 取り纏め請求先情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class TorimatomeSeikyusakiInfo {

  private String seikyuId;           // 請求ID

  private short chainCode;          // チェーンコード
  private short chainIdx;           // チェーン枝番
  private String seikyusakiCd;       // 請求先コード
  private String seikyusakiNameR;    // 請求先略称
  private String seikyuShimebi;      // 請求締め日
  private String seikyuShimeNichiji; // 請求締め日時
  private String jigyoshoCd;         // 事業所コード
  private String jigyoshoName;       // 事業所名
  private String tantoshaCd;         // 担当者コード
  private String tantoshaName;       // 担当者名

  private String taxKeisanTani;      // 消費税計算単位
  private String taxHasuShori;       // 消費税端数処理
  private String seikyushoCls;       // 請求書種類
  private String seikyuDataKbn;      // 請求データ区分

  private boolean konakiUriageFlag; // 今回売上フラグ
  private boolean konkaiNyukinFlag; // 今回入金フラグ
  private boolean seikyuZanFlag;    // 請求残フラグ
  private boolean shimeSumiFlag;    // 締め処理済みフラグ
  private boolean nyukinSumiFlag;   // 入金済みフラグ

  // ---------------------------------------------------------------------------
  // getter / setter
  // ---------------------------------------------------------------------------

  /**
   * seikyuId getter
   */
  public String getSeikyuId() {
    return seikyuId;
  }

  /**
   * seikyuId setter
   */
  public void setSeikyuId(String seikyuId) {
    this.seikyuId = seikyuId;
  }

  /**
   * chainCode getter
   */
  public short getChainCode() {
    return chainCode;
  }

  /**
   * chainCode setter
   */
  public void setChainCode(short chainCode) {
    this.chainCode = chainCode;
  }

  /**
   * chainIdx getter
   */
  public short getChainIdx() {
    return chainIdx;
  }

  /**
   * chainIdx setter
   */
  public void setChainIdx(short chainIdx) {
    this.chainIdx = chainIdx;
  }

  /**
   * seikyusakiCd getter
   */
  public String getSeikyusakiCd() {
    return seikyusakiCd;
  }

  /**
   * seikyusakiCd setter
   */
  public void setSeikyusakiCd(String seikyusakiCd) {
    this.seikyusakiCd = seikyusakiCd;
  }

  /**
   * seikyusakiNameR getter
   */
  public String getSeikyusakiNameR() {
    return seikyusakiNameR;
  }

  /**
   * seikyusakiNameR setter
   */
  public void setSeikyusakiNameR(String seikyusakiNameR) {
    this.seikyusakiNameR = seikyusakiNameR;
  }

  /**
   * seikyuShimebi getter
   */
  public String getSeikyuShimebi() {
    return seikyuShimebi;
  }

  /**
   * seikyuShimebi setter
   */
  public void setSeikyuShimebi(String seikyuShimebi) {
    this.seikyuShimebi = seikyuShimebi;
  }

  /**
   * seikyuShimeNichiji getter
   */
  public String getSeikyuShimeNichiji() {
    return seikyuShimeNichiji;
  }

  /**
   * seikyuShimeNichiji setter
   */
  public void setSeikyuShimeNichiji(String seikyuShimeNichiji) {
    this.seikyuShimeNichiji = seikyuShimeNichiji;
  }

  /**
   * jigyoshoCd getter
   */
  public String getJigyoshoCd() {
    return jigyoshoCd;
  }

  /**
   * jigyoshoCd setter
   */
  public void setJigyoshoCd(String jigyoshoCd) {
    this.jigyoshoCd = jigyoshoCd;
  }

  /**
   * jigyoshoName getter
   */
  public String getJigyoshoName() {
    return jigyoshoName;
  }

  /**
   * jigyoshoName setter
   */
  public void setJigyoshoName(String jigyoshoName) {
    this.jigyoshoName = jigyoshoName;
  }

  /**
   * tantoshaCd getter
   */
  public String getTantoshaCd() {
    return tantoshaCd;
  }

  /**
   * tantoshaCd setter
   */
  public void setTantoshaCd(String tantoshaCd) {
    this.tantoshaCd = tantoshaCd;
  }

  /**
   * tantoshaName getter
   */
  public String getTantoshaName() {
    return tantoshaName;
  }

  /**
   * tantoshaName setter
   */
  public void setTantoshaName(String tantoshaName) {
    this.tantoshaName = tantoshaName;
  }

  /**
   * taxKeisanTani getter
   */
  public String getTaxKeisanTani() {
    return taxKeisanTani;
  }

  /**
   * taxKeisanTani setter
   */
  public void setTaxKeisanTani(String taxKeisanTani) {
    this.taxKeisanTani = taxKeisanTani;
  }

  /**
   * taxHasuShori getter
   */
  public String getTaxHasuShori() {
    return taxHasuShori;
  }

  /**
   * taxHasuShori setter
   */
  public void setTaxHasuShori(String taxHasuShori) {
    this.taxHasuShori = taxHasuShori;
  }

  /**
   * seikyushoCls getter
   */
  public String getSeikyushoCls() {
    return seikyushoCls;
  }

  /**
   * seikyushoCls setter
   */
  public void setSeikyushoCls(String seikyushoCls) {
    this.seikyushoCls = seikyushoCls;
  }

  /**
   * seikyuDataKbn getter
   */
  public String getSeikyuDataKbn() {
    return seikyuDataKbn;
  }

  /**
   * seikyuDataKbn setter
   */
  public void setSeikyuDataKbn(String seikyuDataKbn) {
    this.seikyuDataKbn = seikyuDataKbn;
  }

  /**
   * konakiUriageFlag getter
   */
  public boolean isKonakiUriageFlag() {
    return konakiUriageFlag;
  }

  /**
   * konakiUriageFlag setter
   */
  public void setKonakiUriageFlag(boolean konakiUriageFlag) {
    this.konakiUriageFlag = konakiUriageFlag;
  }

  /**
   * konkaiNyukinFlag getter
   */
  public boolean isKonkaiNyukinFlag() {
    return konkaiNyukinFlag;
  }

  /**
   * konkaiNyukinFlag setter
   */
  public void setKonkaiNyukinFlag(boolean konkaiNyukinFlag) {
    this.konkaiNyukinFlag = konkaiNyukinFlag;
  }

  /**
   * seikyuZanFlag getter
   */
  public boolean isSeikyuZanFlag() {
    return seikyuZanFlag;
  }

  /**
   * seikyuZanFlag setter
   */
  public void setSeikyuZanFlag(boolean seikyuZanFlag) {
    this.seikyuZanFlag = seikyuZanFlag;
  }

  /**
   * shimeSumiFlag getter
   */
  public boolean isShimeSumiFlag() {
    return shimeSumiFlag;
  }

  /**
   * shimeSumiFlag setter
   */
  public void setShimeSumiFlag(boolean shimeSumiFlag) {
    this.shimeSumiFlag = shimeSumiFlag;
  }

  /**
   * nyukinSumiFlag getter
   */
  public boolean isNyukinSumiFlag() {
    return nyukinSumiFlag;
  }

  /**
   * nyukinSumiFlag setter
   */
  public void setNyukinSumiFlag(boolean nyukinSumiFlag) {
    this.nyukinSumiFlag = nyukinSumiFlag;
  }

}
