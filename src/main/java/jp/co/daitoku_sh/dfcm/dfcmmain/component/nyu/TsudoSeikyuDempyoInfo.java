/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:ZandakaRiyuInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/06
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/06 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 都度請求伝票情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class TsudoSeikyuDempyoInfo {

  private String dempyoNo; // 伝票No
  private int uriageGakuUrikake; // 売上額（売掛）
  private int uriageGakuMishu; // 売上額（未収）
  private int taxUrikake; // 消費税（売掛）
  private int taxMishu; // 消費税（未収）
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getDempyoNo() {
    return dempyoNo;
  }
  
  public void setDempyoNo(String dempyoNo) {
    this.dempyoNo = dempyoNo;
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
