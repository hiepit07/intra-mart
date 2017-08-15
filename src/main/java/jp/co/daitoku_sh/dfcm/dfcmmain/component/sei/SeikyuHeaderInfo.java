/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:SeikyuHeaderInfo.java
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
 * 請求ヘッダ情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SeikyuHeaderInfo {

  private String seikyuId;                // 請求ID
  private String seikyuShimebi;           // 請求締日
  private String nyukinYoteibi;           // 入金予定日
  private String seikyusakiCd;            // 請求先CD
  private String jigyoshoCd;              // 事業所CD
  private String tokuisakiName;           // 得意先名
  private String seikyusakiName;          // 請求先名
  private String seikyusakiNameR;         // 請求先略称
  private String seikyusakiZipCode;       // 請求先郵便番号
  private String seikyusakiAddr1;         // 請求先住所1
  private String seikyusakiAddr2;         // 請求先住所2
  private String nyukinCls;               // 入金種別
  private String nyukinClsName;           // 入金種別名称
  private String seikyushoTanka;          // 請求書単価
  private String seikyushoShuruiChohyoId; // 請求書種類帳票ID
  private String seikyushoPattern;        // 請求書パターン
  private String taxKeisanTani;           // 消費税計算単位
  private String taxHasuShori;            // 消費税端数処理
  private String torihikiCd;              // 取引先CD
  private String dempyoKbn;               // 伝票区分
  private String chohyoSrvName;           // 帳票サーバ名
  private String chohyoDir;               // 帳票ディレクトリ
  private String chohyoNameR;             // 帳票略称
  private String dempyoCls;               // 伝票種別
  private String multiChohyoId;           // マルチ帳票ID
  private String jigyoshoName;            // 事業所名
  private String jigyoshoTelNo;           // 事業所TEL
  private String jigyoshoAddr1;           // 事業所住所1
  private String jigyoshoAddr2;           // 事業所住所2
  private String tantoshaName;            // 担当者名

  private int zenkaiSeikyuGaku;         // 前回請求額
  private int konkaiNyukin;             // 今回入金
  private int konkaiSosai;              // 今回相殺
  private int konkaiChosei;             // 今回調整
  private int kurikoshiGaku;            // 繰越額
  private int konkaiUriage;             // 今回売上
  private int konkaiTax;                // 今回消費税
  private int konkaiSeikyuGaku;         // 今回請求額

  /**
   * seikyuId getter.
   * 
   */
  public String getSeikyuId() {
    return seikyuId;
  }

  /**
   * seikyuId setter.
   * 
   */
  public void setSeikyuId(String seikyuId) {
    this.seikyuId = seikyuId;
  }

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
   * nyukinYoteibi getter.
   * 
   */
  public String getNyukinYoteibi() {
    return nyukinYoteibi;
  }

  /**
   * nyukinYoteibi setter.
   * 
   */
  public void setNyukinYoteibi(String nyukinYoteibi) {
    this.nyukinYoteibi = nyukinYoteibi;
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
   * seikyusakiNameR getter.
   * 
   */
  public String getSeikyusakiNameR() {
    return seikyusakiNameR;
  }

  /**
   * seikyusakiNameR setter.
   * 
   */
  public void setSeikyusakiNameR(String seikyusakiNameR) {
    this.seikyusakiNameR = seikyusakiNameR;
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
   * nyukinCls getter.
   * 
   */
  public String getNyukinCls() {
    return nyukinCls;
  }

  /**
   * nyukinCls setter.
   * 
   */
  public void setNyukinCls(String nyukinCls) {
    this.nyukinCls = nyukinCls;
  }

  /**
   * nyukinClsName getter.
   * 
   */
  public String getNyukinClsName() {
    return nyukinClsName;
  }

  /**
   * nyukinClsName setter.
   * 
   */
  public void setNyukinClsName(String nyukinClsName) {
    this.nyukinClsName = nyukinClsName;
  }

  /**
   * seikyushoTanka getter.
   * 
   */
  public String getSeikyushoTanka() {
    return seikyushoTanka;
  }

  /**
   * seikyushoTanka setter.
   * 
   */
  public void setSeikyushoTanka(String seikyushoTanka) {
    this.seikyushoTanka = seikyushoTanka;
  }

  /**
   * seikyushoShuruiChohyoId getter.
   * 
   */
  public String getSeikyushoShuruiChohyoId() {
    return seikyushoShuruiChohyoId;
  }

  /**
   * seikyushoShuruiChohyoId setter.
   * 
   */
  public void setSeikyushoShuruiChohyoId(String seikyushoShuruiChohyoId) {
    this.seikyushoShuruiChohyoId = seikyushoShuruiChohyoId;
  }

  /**
   * seikyushoPattern getter.
   * 
   */
  public String getSeikyushoPattern() {
    return seikyushoPattern;
  }

  /**
   * seikyushoPattern setter.
   * 
   */
  public void setSeikyushoPattern(String seikyushoPattern) {
    this.seikyushoPattern = seikyushoPattern;
  }

  /**
   * taxKeisanTani getter.
   * 
   */
  public String getTaxKeisanTani() {
    return taxKeisanTani;
  }

  /**
   * taxKeisanTani setter.
   * 
   */
  public void setTaxKeisanTani(String taxKeisanTani) {
    this.taxKeisanTani = taxKeisanTani;
  }

  /**
   * taxHasuShori getter.
   * 
   */
  public String getTaxHasuShori() {
    return taxHasuShori;
  }

  /**
   * taxHasuShori setter.
   * 
   */
  public void setTaxHasuShori(String taxHasuShori) {
    this.taxHasuShori = taxHasuShori;
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
   * dempyoKbn getter.
   * 
   */
  public String getDempyoKbn() {
    return dempyoKbn;
  }

  /**
   * dempyoKbn setter.
   * 
   */
  public void setDempyoKbn(String dempyoKbn) {
    this.dempyoKbn = dempyoKbn;
  }

  /**
   * chohyoSrvName getter.
   * 
   */
  public String getChohyoSrvName() {
    return chohyoSrvName;
  }

  /**
   * chohyoSrvName setter.
   * 
   */
  public void setChohyoSrvName(String chohyoSrvName) {
    this.chohyoSrvName = chohyoSrvName;
  }

  /**
   * chohyoDir getter.
   * 
   */
  public String getChohyoDir() {
    return chohyoDir;
  }

  /**
   * chohyoDir setter.
   * 
   */
  public void setChohyoDir(String chohyoDir) {
    this.chohyoDir = chohyoDir;
  }

  /**
   * chohyoNameR getter.
   * 
   */
  public String getChohyoNameR() {
    return chohyoNameR;
  }

  /**
   * chohyoNameR setter.
   * 
   */
  public void setChohyoNameR(String chohyoNameR) {
    this.chohyoNameR = chohyoNameR;
  }

  /**
   * dempyoCls getter.
   * 
   */
  public String getDempyoCls() {
    return dempyoCls;
  }

  /**
   * dempyoCls setter.
   * 
   */
  public void setDempyoCls(String dempyoCls) {
    this.dempyoCls = dempyoCls;
  }

  /**
   * multiChohyoId getter.
   * 
   */
  public String getMultiChohyoId() {
    return multiChohyoId;
  }

  /**
   * multiChohyoId setter.
   * 
   */
  public void setMultiChohyoId(String multiChohyoId) {
    this.multiChohyoId = multiChohyoId;
  }

  /**
   * jigyoshoName getter.
   * 
   */
  public String getJigyoshoName() {
    return jigyoshoName;
  }

  /**
   * jigyoshoName setter.
   * 
   */
  public void setJigyoshoName(String jigyoshoName) {
    this.jigyoshoName = jigyoshoName;
  }

  /**
   * jigyoshoTelNo getter.
   * 
   */
  public String getJigyoshoTelNo() {
    return jigyoshoTelNo;
  }

  /**
   * jigyoshoTelNo setter.
   * 
   */
  public void setJigyoshoTelNo(String jigyoshoTelNo) {
    this.jigyoshoTelNo = jigyoshoTelNo;
  }

  /**
   * jigyoshoAddr1 getter.
   * 
   */
  public String getJigyoshoAddr1() {
    return jigyoshoAddr1;
  }

  /**
   * jigyoshoAddr1 setter.
   * 
   */
  public void setJigyoshoAddr1(String jigyoshoAddr1) {
    this.jigyoshoAddr1 = jigyoshoAddr1;
  }

  /**
   * jigyoshoAddr2 getter.
   * 
   */
  public String getJigyoshoAddr2() {
    return jigyoshoAddr2;
  }

  /**
   * jigyoshoAddr2 setter.
   * 
   */
  public void setJigyoshoAddr2(String jigyoshoAddr2) {
    this.jigyoshoAddr2 = jigyoshoAddr2;
  }

  /**
   * tantoshaName getter.
   * 
   */
  public String getTantoshaName() {
    return tantoshaName;
  }

  /**
   * tantoshaName setter.
   * 
   */
  public void setTantoshaName(String tantoshaName) {
    this.tantoshaName = tantoshaName;
  }

  /**
   * zenkaiSeikyuGaku getter.
   * 
   */
  public int getZenkaiSeikyuGaku() {
    return zenkaiSeikyuGaku;
  }

  /**
   * zenkaiSeikyuGaku setter.
   * 
   */
  public void setZenkaiSeikyuGaku(int zenkaiSeikyuGaku) {
    this.zenkaiSeikyuGaku = zenkaiSeikyuGaku;
  }

  /**
   * konkaiNyukin getter.
   * 
   */
  public int getKonkaiNyukin() {
    return konkaiNyukin;
  }

  /**
   * konkaiNyukin setter.
   * 
   */
  public void setKonkaiNyukin(int konkaiNyukin) {
    this.konkaiNyukin = konkaiNyukin;
  }

  /**
   * konkaiSosai getter.
   * 
   */
  public int getKonkaiSosai() {
    return konkaiSosai;
  }

  /**
   * konkaiSosai setter.
   * 
   */
  public void setKonkaiSosai(int konkaiSosai) {
    this.konkaiSosai = konkaiSosai;
  }

  /**
   * konkaiChosei getter.
   * 
   */
  public int getKonkaiChosei() {
    return konkaiChosei;
  }

  /**
   * konkaiChosei setter.
   * 
   */
  public void setKonkaiChosei(int konkaiChosei) {
    this.konkaiChosei = konkaiChosei;
  }

  /**
   * kurikoshiGaku getter.
   * 
   */
  public int getKurikoshiGaku() {
    return kurikoshiGaku;
  }

  /**
   * kurikoshiGaku setter.
   * 
   */
  public void setKurikoshiGaku(int kurikoshiGaku) {
    this.kurikoshiGaku = kurikoshiGaku;
  }

  /**
   * konkaiUriage getter.
   * 
   */
  public int getKonkaiUriage() {
    return konkaiUriage;
  }

  /**
   * konkaiUriage setter.
   * 
   */
  public void setKonkaiUriage(int konkaiUriage) {
    this.konkaiUriage = konkaiUriage;
  }

  /**
   * konkaiTax getter.
   * 
   */
  public int getKonkaiTax() {
    return konkaiTax;
  }

  /**
   * konkaiTax setter.
   * 
   */
  public void setKonkaiTax(int konkaiTax) {
    this.konkaiTax = konkaiTax;
  }

  /**
   * konkaiSeikyuGaku getter.
   * 
   */
  public int getKonkaiSeikyuGaku() {
    return konkaiSeikyuGaku;
  }

  /**
   * konkaiSeikyuGaku setter.
   * 
   */
  public void setKonkaiSeikyuGaku(int konkaiSeikyuGaku) {
    this.konkaiSeikyuGaku = konkaiSeikyuGaku;
  }

}
