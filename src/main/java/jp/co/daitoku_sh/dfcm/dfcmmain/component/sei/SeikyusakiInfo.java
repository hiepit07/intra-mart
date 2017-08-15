/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:SeikyusakiInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/10/27
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/27 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.sei;

/**
 * 請求共通 請求先情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SeikyusakiInfo {

  private boolean checkBoxFlag;     // チェックボックス

  private short chainCode;          // チェーンコード
  private short chainIdx;           // チェーン枝番
  private String seikyusakiCd;       // 請求先コード
  private String seikyusakiNameR;    // 請求先略称

  private String jigyoshoCd;         // 事業所コード
  private String jigyoshoName;       // 事業所名
  private String tantoshaCd;         // 担当者コード
  private String tantoshaName;       // 担当者名

  private String seikyuDataKbn;        // 請求データ区分
  private String taxKeisanTani;        // 消費税計算単位
  private String taxHasuShori;         // 消費税端数処理
  private String seikyushoTanka;       // 請求書単価
  private String tokuisakiCls;         // 得意先種別
  private String seikyushoCls;         // 請求書種別
  private String kaishuTsukiKbn;       // 回収月区分
  private String kaishubi;             // 回収日
  private String torimatomeJigyoshoCd; // 取り纏め事業所コード
  private String seikyuSoshinId;       // 請求送信ID

  private int zenkaiSeikyuGakuUrikake;   // 前回請求額（売掛）
  private int zenkaiSeikyuGakuMishu;     // 前回請求額（未収）
  private int konkaiUriageUrikake;       // 今回売上（売掛）
  private int konkaiUriageMishu;         // 今回売上（未収）
  private int konkaiNyukinUrikake;       // 今回入金（売掛）
  private int konkaiNyukinMishu;         // 今回入金（未収）
  private int konkaiSosaiUrikake;        // 今回相殺（売掛）
  private int konkaiSosaiMishu;          // 今回相殺（未収）
  private int konkaiChoseiUrikake;       // 今回調整（売掛）
  private int konkaiChoseiMishu;         // 今回調整（未収）
  private int kurikoshiGakuUrikake;      // 繰越額（売掛）
  private int kurikoshiGakuMishu;        // 繰越額（未収）
  private int urikakeMSu;                // 売掛明細レコード数
  private int mishuMSu;                  // 未収明細レコード数

  private String konkaiSeikyuId;             // 今回請求ID
  private String konkaiSeikyuShimeNichiji;   // 今回請求締め日時
  private String konkaiSeikyuDataRenkeiFlag; // 今回請求データ連携フラグ
  private String konkaiSeikyuShimeKbn;       // 今回請求締め区分
  private String nyukinKakuninFlag;          // 入金確認フラグ
  private String zenkaiSeikyuId;             // 前回請求ID
  private String zenkaiSeikyuShimebi;        // 前回請求締日
  private String zenkaiSeikyuDataRenkeiFlag; // 前回請求データ連携フラグ
  private String zenkaiSeikyuShimeKbn;       // 前回請求締め区分

  /**
   * checkBoxFlag getter.
   * 
   */
  public boolean isCheckBoxFlag() {
    return checkBoxFlag;
  }

  /**
   * checkBoxFlag setter.
   * 
   */
  public void setCheckBoxFlag(boolean checkBoxFlag) {
    this.checkBoxFlag = checkBoxFlag;
  }

  /**
   * chainCode getter.
   * 
   */
  public short getChainCode() {
    return chainCode;
  }

  /**
   * chainCode setter.
   * 
   */
  public void setChainCode(short chainCode) {
    this.chainCode = chainCode;
  }

  /**
   * chainIdx getter.
   * 
   */
  public short getChainIdx() {
    return chainIdx;
  }

  /**
   * chainIdx setter.
   * 
   */
  public void setChainIdx(short chainIdx) {
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
   * tantoshaCd getter.
   * 
   */
  public String getTantoshaCd() {
    return tantoshaCd;
  }

  /**
   * tantoshaCd setter.
   * 
   */
  public void setTantoshaCd(String tantoshaCd) {
    this.tantoshaCd = tantoshaCd;
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
   * seikyuDataKbn getter.
   * 
   */
  public String getSeikyuDataKbn() {
    return seikyuDataKbn;
  }

  /**
   * seikyuDataKbn setter.
   * 
   */
  public void setSeikyuDataKbn(String seikyuDataKbn) {
    this.seikyuDataKbn = seikyuDataKbn;
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
   * tokuisakiCls getter.
   * 
   */
  public String getTokuisakiCls() {
    return tokuisakiCls;
  }

  /**
   * tokuisakiCls setter.
   * 
   */
  public void setTokuisakiCls(String tokuisakiCls) {
    this.tokuisakiCls = tokuisakiCls;
  }

  /**
   * seikyushoCls getter.
   * 
   */
  public String getSeikyushoCls() {
    return seikyushoCls;
  }

  /**
   * seikyushoCls setter.
   * 
   */
  public void setSeikyushoCls(String seikyushoCls) {
    this.seikyushoCls = seikyushoCls;
  }

  /**
   * kaishuTsukiKbn getter.
   * 
   */
  public String getKaishuTsukiKbn() {
    return kaishuTsukiKbn;
  }

  /**
   * kaishuTsukiKbn setter.
   * 
   */
  public void setKaishuTsukiKbn(String kaishuTsukiKbn) {
    this.kaishuTsukiKbn = kaishuTsukiKbn;
  }

  /**
   * kaishubi getter.
   * 
   */
  public String getKaishubi() {
    return kaishubi;
  }

  /**
   * kaishubi setter.
   * 
   */
  public void setKaishubi(String kaishubi) {
    this.kaishubi = kaishubi;
  }

  /**
   * torimatomeJigyoshoCd getter.
   * 
   */
  public String getTorimatomeJigyoshoCd() {
    return torimatomeJigyoshoCd;
  }

  /**
   * torimatomeJigyoshoCd setter.
   * 
   */
  public void setTorimatomeJigyoshoCd(String torimatomeJigyoshoCd) {
    this.torimatomeJigyoshoCd = torimatomeJigyoshoCd;
  }

  /**
   * seikyuSoshinId getter.
   * 
   */
  public String getSeikyuSoshinId() {
    return seikyuSoshinId;
  }

  /**
   * seikyuSoshinId setter.
   * 
   */
  public void setSeikyuSoshinId(String seikyuSoshinId) {
    this.seikyuSoshinId = seikyuSoshinId;
  }

  /**
   * zenkaiSeikyuGakuUrikake getter.
   * 
   */
  public int getZenkaiSeikyuGakuUrikake() {
    return zenkaiSeikyuGakuUrikake;
  }

  /**
   * zenkaiSeikyuGakuUrikake setter.
   * 
   */
  public void setZenkaiSeikyuGakuUrikake(int zenkaiSeikyuGakuUrikake) {
    this.zenkaiSeikyuGakuUrikake = zenkaiSeikyuGakuUrikake;
  }

  /**
   * zenkaiSeikyuGakuMishu getter.
   * 
   */
  public int getZenkaiSeikyuGakuMishu() {
    return zenkaiSeikyuGakuMishu;
  }

  /**
   * zenkaiSeikyuGakuMishu setter.
   * 
   */
  public void setZenkaiSeikyuGakuMishu(int zenkaiSeikyuGakuMishu) {
    this.zenkaiSeikyuGakuMishu = zenkaiSeikyuGakuMishu;
  }

  /**
   * konkaiUriageUrikake getter.
   * 
   */
  public int getKonkaiUriageUrikake() {
    return konkaiUriageUrikake;
  }

  /**
   * konkaiUriageUrikake setter.
   * 
   */
  public void setKonkaiUriageUrikake(int konkaiUriageUrikake) {
    this.konkaiUriageUrikake = konkaiUriageUrikake;
  }

  /**
   * konkaiUriageMishu getter.
   * 
   */
  public int getKonkaiUriageMishu() {
    return konkaiUriageMishu;
  }

  /**
   * konkaiUriageMishu setter.
   * 
   */
  public void setKonkaiUriageMishu(int konkaiUriageMishu) {
    this.konkaiUriageMishu = konkaiUriageMishu;
  }

  /**
   * konkaiNyukinUrikake getter.
   * 
   */
  public int getKonkaiNyukinUrikake() {
    return konkaiNyukinUrikake;
  }

  /**
   * konkaiNyukinUrikake setter.
   * 
   */
  public void setKonkaiNyukinUrikake(int konkaiNyukinUrikake) {
    this.konkaiNyukinUrikake = konkaiNyukinUrikake;
  }

  /**
   * konkaiNyukinMishu getter.
   * 
   */
  public int getKonkaiNyukinMishu() {
    return konkaiNyukinMishu;
  }

  /**
   * konkaiNyukinMishu setter.
   * 
   */
  public void setKonkaiNyukinMishu(int konkaiNyukinMishu) {
    this.konkaiNyukinMishu = konkaiNyukinMishu;
  }

  /**
   * konkaiSosaiUrikake getter.
   * 
   */
  public int getKonkaiSosaiUrikake() {
    return konkaiSosaiUrikake;
  }

  /**
   * konkaiSosaiUrikake setter.
   * 
   */
  public void setKonkaiSosaiUrikake(int konkaiSosaiUrikake) {
    this.konkaiSosaiUrikake = konkaiSosaiUrikake;
  }

  /**
   * konkaiSosaiMishu getter.
   * 
   */
  public int getKonkaiSosaiMishu() {
    return konkaiSosaiMishu;
  }

  /**
   * konkaiSosaiMishu setter.
   * 
   */
  public void setKonkaiSosaiMishu(int konkaiSosaiMishu) {
    this.konkaiSosaiMishu = konkaiSosaiMishu;
  }

  /**
   * konkaiChoseiUrikake getter.
   * 
   */
  public int getKonkaiChoseiUrikake() {
    return konkaiChoseiUrikake;
  }

  /**
   * konkaiChoseiUrikake setter.
   * 
   */
  public void setKonkaiChoseiUrikake(int konkaiChoseiUrikake) {
    this.konkaiChoseiUrikake = konkaiChoseiUrikake;
  }

  /**
   * konkaiChoseiMishu getter.
   * 
   */
  public int getKonkaiChoseiMishu() {
    return konkaiChoseiMishu;
  }

  /**
   * konkaiChoseiMishu setter.
   * 
   */
  public void setKonkaiChoseiMishu(int konkaiChoseiMishu) {
    this.konkaiChoseiMishu = konkaiChoseiMishu;
  }

  /**
   * kurikoshiGakuUrikake getter.
   * 
   */
  public int getKurikoshiGakuUrikake() {
    return kurikoshiGakuUrikake;
  }

  /**
   * kurikoshiGakuUrikake setter.
   * 
   */
  public void setKurikoshiGakuUrikake(int kurikoshiGakuUrikake) {
    this.kurikoshiGakuUrikake = kurikoshiGakuUrikake;
  }

  /**
   * kurikoshiGakuMishu getter.
   * 
   */
  public int getKurikoshiGakuMishu() {
    return kurikoshiGakuMishu;
  }

  /**
   * kurikoshiGakuMishu setter.
   * 
   */
  public void setKurikoshiGakuMishu(int kurikoshiGakuMishu) {
    this.kurikoshiGakuMishu = kurikoshiGakuMishu;
  }

  /**
   * urikakeMSu getter.
   * 
   */
  public int getUrikakeMSu() {
    return urikakeMSu;
  }

  /**
   * urikakeMSu setter.
   * 
   */
  public void setUrikakeMSu(int urikakeMSu) {
    this.urikakeMSu = urikakeMSu;
  }

  /**
   * mishuMSu getter.
   * 
   */
  public int getMishuMSu() {
    return mishuMSu;
  }

  /**
   * mishuMSu setter.
   * 
   */
  public void setMishuMSu(int mishuMSu) {
    this.mishuMSu = mishuMSu;
  }

  /**
   * konkaiSeikyuId getter.
   * 
   */
  public String getKonkaiSeikyuId() {
    return konkaiSeikyuId;
  }

  /**
   * konkaiSeikyuId setter.
   * 
   */
  public void setKonkaiSeikyuId(String konkaiSeikyuId) {
    this.konkaiSeikyuId = konkaiSeikyuId;
  }

  /**
   * konkaiSeikyuShimeNichiji getter.
   * 
   */
  public String getKonkaiSeikyuShimeNichiji() {
    return konkaiSeikyuShimeNichiji;
  }

  /**
   * konkaiSeikyuShimeNichiji setter.
   * 
   */
  public void setKonkaiSeikyuShimeNichiji(String konkaiSeikyuShimeNichiji) {
    this.konkaiSeikyuShimeNichiji = konkaiSeikyuShimeNichiji;
  }

  /**
   * konkaiSeikyuDataRenkeiFlag getter.
   * 
   */
  public String getKonkaiSeikyuDataRenkeiFlag() {
    return konkaiSeikyuDataRenkeiFlag;
  }

  /**
   * konkaiSeikyuDataRenkeiFlag setter.
   * 
   */
  public void setKonkaiSeikyuDataRenkeiFlag(String konkaiSeikyuDataRenkeiFlag) {
    this.konkaiSeikyuDataRenkeiFlag = konkaiSeikyuDataRenkeiFlag;
  }

  /**
   * konkaiSeikyuShimeKbn getter.
   * 
   */
  public String getKonkaiSeikyuShimeKbn() {
    return konkaiSeikyuShimeKbn;
  }

  /**
   * konkaiSeikyuShimeKbn setter.
   * 
   */
  public void setKonkaiSeikyuShimeKbn(String konkaiSeikyuShimeKbn) {
    this.konkaiSeikyuShimeKbn = konkaiSeikyuShimeKbn;
  }

  /**
   * nyukinKakuninFlag getter.
   * 
   */
  public String getNyukinKakuninFlag() {
    return nyukinKakuninFlag;
  }

  /**
   * nyukinKakuninFlag setter.
   * 
   */
  public void setNyukinKakuninFlag(String nyukinKakuninFlag) {
    this.nyukinKakuninFlag = nyukinKakuninFlag;
  }

  /**
   * zenkaiSeikyuId getter.
   * 
   */
  public String getZenkaiSeikyuId() {
    return zenkaiSeikyuId;
  }

  /**
   * zenkaiSeikyuId setter.
   * 
   */
  public void setZenkaiSeikyuId(String zenkaiSeikyuId) {
    this.zenkaiSeikyuId = zenkaiSeikyuId;
  }

  /**
   * zenkaiSeikyuShimebi getter.
   * 
   */
  public String getZenkaiSeikyuShimebi() {
    return zenkaiSeikyuShimebi;
  }

  /**
   * zenkaiSeikyuShimebi setter.
   * 
   */
  public void setZenkaiSeikyuShimebi(String zenkaiSeikyuShimebi) {
    this.zenkaiSeikyuShimebi = zenkaiSeikyuShimebi;
  }

  /**
   * zenkaiSeikyuDataRenkeiFlag getter.
   * 
   */
  public String getZenkaiSeikyuDataRenkeiFlag() {
    return zenkaiSeikyuDataRenkeiFlag;
  }

  /**
   * zenkaiSeikyuDataRenkeiFlag setter.
   * 
   */
  public void setZenkaiSeikyuDataRenkeiFlag(String zenkaiSeikyuDataRenkeiFlag) {
    this.zenkaiSeikyuDataRenkeiFlag = zenkaiSeikyuDataRenkeiFlag;
  }

  /**
   * zenkaiSeikyuShimeKbn getter.
   * 
   */
  public String getZenkaiSeikyuShimeKbn() {
    return zenkaiSeikyuShimeKbn;
  }

  /**
   * zenkaiSeikyuShimeKbn setter.
   * 
   */
  public void setZenkaiSeikyuShimeKbn(String zenkaiSeikyuShimeKbn) {
    this.zenkaiSeikyuShimeKbn = zenkaiSeikyuShimeKbn;
  }

}
