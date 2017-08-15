/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:SeikyuMeisaiMotoData.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/10/28
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/28 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.sei;

/**
 * 請求データ作成 請求明細元データ DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SeikyuMeisaiMotoData {

  private short chainCode;            // チェーンコード
  private short chainIdx;             // チェーン枝番
  private String tokuisakiCd;          // 得意先コード
  private String tempoCd;              // 店舗コード
  private String jigyoshoCd;           // 事業所コード
  private String nyuryokuKeitai;       // 入力形態
  private long jishaDempyoNo;         // 自社伝票番号
  private short jishaDempyoNoIdx;     // 自社伝票番号枝版
  private String nohinBi;              // 納品日
  private String tokuisakiDempyoNo;    // 得意先伝票番号
  private String uchizeiKokyakuKbn;    // 内税顧客区分
  private String taxRate;              // 消費税率
  private int sumHanbaiKingaku;       // 合計販売金額
  private String hambaiKbn;            // 販売区分
  private String bunruiCd;             // 分類コード
  private String dataKbn;              // データ区分
  private int torihikiGaku;           // 取引額
  private int sempoShikiriKingaku;    // 先方仕切金額
  private String tempoShuyakuJoken;   // 店舗集約条件
  private String shuyakusakiTempoKbn; // 集約先店舗コード
  private String shuyakuTempoCode;    // 集約店舗コード
  private String wrkTempoCd;          // 店舗コードWORK

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
   * nyuryokuKeitai getter.
   * 
   */
  public String getNyuryokuKeitai() {
    return nyuryokuKeitai;
  }

  /**
   * nyuryokuKeitai setter.
   * 
   */
  public void setNyuryokuKeitai(String nyuryokuKeitai) {
    this.nyuryokuKeitai = nyuryokuKeitai;
  }

  /**
   * jishaDempyoNo getter.
   * 
   */
  public long getJishaDempyoNo() {
    return jishaDempyoNo;
  }

  /**
   * jishaDempyoNo setter.
   * 
   */
  public void setJishaDempyoNo(long jishaDempyoNo) {
    this.jishaDempyoNo = jishaDempyoNo;
  }

  /**
   * jishaDempyoNoIdx getter.
   * 
   */
  public short getJishaDempyoNoIdx() {
    return jishaDempyoNoIdx;
  }

  /**
   * jishaDempyoNoIdx setter.
   * 
   */
  public void setJishaDempyoNoIdx(short jishaDempyoNoIdx) {
    this.jishaDempyoNoIdx = jishaDempyoNoIdx;
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
   * uchizeiKokyakuKbn getter.
   * 
   */
  public String getUchizeiKokyakuKbn() {
    return uchizeiKokyakuKbn;
  }

  /**
   * uchizeiKokyakuKbn setter.
   * 
   */
  public void setUchizeiKokyakuKbn(String uchizeiKokyakuKbn) {
    this.uchizeiKokyakuKbn = uchizeiKokyakuKbn;
  }

  /**
   * taxRate getter.
   * 
   */
  public String getTaxRate() {
    return taxRate;
  }

  /**
   * taxRate setter.
   * 
   */
  public void setTaxRate(String taxRate) {
    this.taxRate = taxRate;
  }

  /**
   * sumHanbaiKingaku getter.
   * 
   */
  public int getSumHanbaiKingaku() {
    return sumHanbaiKingaku;
  }

  /**
   * sumHanbaiKingaku setter.
   * 
   */
  public void setSumHanbaiKingaku(int sumHanbaiKingaku) {
    this.sumHanbaiKingaku = sumHanbaiKingaku;
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
   * bunruiCd getter.
   * 
   */
  public String getBunruiCd() {
    return bunruiCd;
  }

  /**
   * bunruiCd setter.
   * 
   */
  public void setBunruiCd(String bunruiCd) {
    this.bunruiCd = bunruiCd;
  }

  /**
   * dataKbn getter.
   * 
   */
  public String getDataKbn() {
    return dataKbn;
  }

  /**
   * dataKbn setter.
   * 
   */
  public void setDataKbn(String dataKbn) {
    this.dataKbn = dataKbn;
  }

  /**
   * torihikiGaku getter.
   * 
   */
  public int getTorihikiGaku() {
    return torihikiGaku;
  }

  /**
   * torihikiGaku setter.
   * 
   */
  public void setTorihikiGaku(int torihikiGaku) {
    this.torihikiGaku = torihikiGaku;
  }

  /**
   * sempoShikiriKingaku getter.
   * 
   */
  public int getSempoShikiriKingaku() {
    return sempoShikiriKingaku;
  }

  /**
   * sempoShikiriKingaku setter.
   * 
   */
  public void setSempoShikiriKingaku(int sempoShikiriKingaku) {
    this.sempoShikiriKingaku = sempoShikiriKingaku;
  }

  /**
   * tempoShuyakuJoken getter.
   * 
   */
  public String getTempoShuyakuJoken() {
    return tempoShuyakuJoken;
  }

  /**
   * tempoShuyakuJoken setter.
   * 
   */
  public void setTempoShuyakuJoken(String tempoShuyakuJoken) {
    this.tempoShuyakuJoken = tempoShuyakuJoken;
  }

  /**
   * shuyakusakiTempoKbn getter.
   * 
   */
  public String getShuyakusakiTempoKbn() {
    return shuyakusakiTempoKbn;
  }

  /**
   * shuyakusakiTempoKbn setter.
   * 
   */
  public void setShuyakusakiTempoKbn(String shuyakusakiTempoKbn) {
    this.shuyakusakiTempoKbn = shuyakusakiTempoKbn;
  }

  /**
   * shuyakuTempoCode getter.
   * 
   */
  public String getShuyakuTempoCode() {
    return shuyakuTempoCode;
  }

  /**
   * shuyakuTempoCode setter.
   * 
   */
  public void setShuyakuTempoCode(String shuyakuTempoCode) {
    this.shuyakuTempoCode = shuyakuTempoCode;
  }

  /**
   * wrkTempoCd getter.
   * 
   */
  public String getWrkTempoCd() {
    return wrkTempoCd;
  }

  /**
   * wrkTempoCd setter.
   * 
   */
  public void setWrkTempoCd(String wrkTempoCd) {
    this.wrkTempoCd = wrkTempoCd;
  }

}
