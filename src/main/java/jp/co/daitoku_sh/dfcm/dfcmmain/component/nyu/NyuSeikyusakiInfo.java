/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:NyuSeikyusakiInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 入金対象請求先情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class NyuSeikyusakiInfo {

  private boolean checkBoxFlag;               // チェックボックス

  private String chainCd;                     // チェーンコード
  private String chainIdx;                    // チェーン枝番
  private String seikyusakiCd;                // 請求先コード
  private String seikyusakiName;              // 請求先名
  private String seikyuTani;                  // 請求単位
  private String tokuisakiCls;                // 得意先種別
  private String kankeiKaishaCls;             // 関係会社種別
  private String kankeiKaishaHojoKamoku;      // 関係会社補助科目
  private String jigyoshoCd;                  // 事業所コード
  private String jigyoshoName;                // 事業所名
  private String seikyuId;                    // 請求ID
  private String seikyuShimebi;               // 請求締日
  private String nyukinYoteibi;               // 入金予定日

  private String dempyoNo;                    // 伝票No
  private String nohinbi;                     // 納品日
  private String uriageKejobi;                // 売上計上日

  private int seikyuGaku;                     // 請求額
  private int uriageGaku;                     // 売上額
  private int mikaishuGaku;                   // 未回収額

  private int uriageGakuUrikake;              // 売上額（売掛）
  private int uriageGakuMishu;                // 売上額（未収）
  private int taxUrikake;                     // 消費税（売掛）
  private int taxMishu;                       // 消費税（未収）
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  
  public boolean isCheckBoxFlag() {
    return checkBoxFlag;
  }

  
  public void setCheckBoxFlag(boolean checkBoxFlag) {
    this.checkBoxFlag = checkBoxFlag;
  }

  public String getChainCd() {
    return chainCd;
  }
  
  public void setChainCd(String chainCd) {
    this.chainCd = chainCd;
  }
  
  public String getChainIdx() {
    return chainIdx;
  }
  
  public void setChainIdx(String chainIdx) {
    this.chainIdx = chainIdx;
  }
  
  public String getSeikyusakiCd() {
    return seikyusakiCd;
  }
  
  public void setSeikyusakiCd(String seikyusakiCd) {
    this.seikyusakiCd = seikyusakiCd;
  }
  
  public String getSeikyusakiName() {
    return seikyusakiName;
  }
  
  public void setSeikyusakiName(String seikyusakiName) {
    this.seikyusakiName = seikyusakiName;
  }
  
  public String getSeikyuTani() {
    return seikyuTani;
  }
  
  public void setSeikyuTani(String seikyuTani) {
    this.seikyuTani = seikyuTani;
  }
  
  public String getTokuisakiCls() {
    return tokuisakiCls;
  }
  
  public void setTokuisakiCls(String tokuisakiCls) {
    this.tokuisakiCls = tokuisakiCls;
  }
  
  public String getKankeiKaishaCls() {
    return kankeiKaishaCls;
  }
  
  public void setKankeiKaishaCls(String kankeiKaishaCls) {
    this.kankeiKaishaCls = kankeiKaishaCls;
  }
  
  public String getKankeiKaishaHojoKamoku() {
    return kankeiKaishaHojoKamoku;
  }
  
  public void setKankeiKaishaHojoKamoku(String kankeiKaishaHojoKamoku) {
    this.kankeiKaishaHojoKamoku = kankeiKaishaHojoKamoku;
  }
  
  public String getJigyoshoCd() {
    return jigyoshoCd;
  }
  
  public void setJigyoshoCd(String jigyoshoCd) {
    this.jigyoshoCd = jigyoshoCd;
  }
  
  public String getJigyoshoName() {
    return jigyoshoName;
  }
  
  public void setJigyoshoName(String jigyoshoName) {
    this.jigyoshoName = jigyoshoName;
  }
  
  public String getSeikyuId() {
    return seikyuId;
  }
  
  public void setSeikyuId(String seikyuId) {
    this.seikyuId = seikyuId;
  }
  
  public String getSeikyuShimebi() {
    return seikyuShimebi;
  }
  
  public void setSeikyuShimebi(String seikyuShimebi) {
    this.seikyuShimebi = seikyuShimebi;
  }
  
  public String getNyukinYoteibi() {
    return nyukinYoteibi;
  }
  
  public void setNyukinYoteibi(String nyukinYoteibi) {
    this.nyukinYoteibi = nyukinYoteibi;
  }
  
  public int getSeikyuGaku() {
    return seikyuGaku;
  }
  
  public void setSeikyuGaku(int seikyuGaku) {
    this.seikyuGaku = seikyuGaku;
  }
  
  public int getUriageGaku() {
    return uriageGaku;
  }
  
  public void setUriageGaku(int uriageGaku) {
    this.uriageGaku = uriageGaku;
  }
  
  public int getMikaishuGaku() {
    return mikaishuGaku;
  }
  
  public void setMikaishuGaku(int mikaishuGaku) {
    this.mikaishuGaku = mikaishuGaku;
  }

  
  public String getDempyoNo() {
    return dempyoNo;
  }

  
  public void setDempyoNo(String dempyoNo) {
    this.dempyoNo = dempyoNo;
  }

  
  public String getNohinbi() {
    return nohinbi;
  }

  
  public void setNohinbi(String nohinbi) {
    this.nohinbi = nohinbi;
  }

  
  public String getUriageKejobi() {
    return uriageKejobi;
  }

  
  public void setUriageKejobi(String uriageKejobi) {
    this.uriageKejobi = uriageKejobi;
  }

  
  public int getUriageGakuUrikake() {
    return uriageGakuUrikake;
  }

  
  public void setUriageGakuUrikake(int uriageGakuUrikake) {
    this.uriageGakuUrikake = uriageGakuUrikake;
  }

  
  public int getUriageGakuMishu() {
    return uriageGakuMishu;
  }

  
  public void setUriageGakuMishu(int uriageGakuMishu) {
    this.uriageGakuMishu = uriageGakuMishu;
  }

  
  public int getTaxUrikake() {
    return taxUrikake;
  }

  
  public void setTaxUrikake(int taxUrikake) {
    this.taxUrikake = taxUrikake;
  }

  
  public int getTaxMishu() {
    return taxMishu;
  }

  
  public void setTaxMishu(int taxMishu) {
    this.taxMishu = taxMishu;
  }

}
