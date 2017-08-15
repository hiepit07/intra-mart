/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:Nyu0106d01ShiwakeInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 仕訳情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class Nyu0106d01ShiwakeInfo {

  private String nyukinKeijobi;     // 入金計上日
  private String tokuisakiName;     // 得意先名
  private String drJigyoshoCd;      // 借方事業所
  private String drHasseiBasho;     // 借方発生場所
  private String drKanjoKamoku;    // 借方勘定科目
  private String drKanjoKamokuCd;  // 借方勘定科目コード
  private String drHojoKamoku;     // 借方補助科目
  private String drTaxKbn;         // 借方税区分
  private String drTaxKbnName;     // 借方税区分名称
  private String crJigyoshoCd;     // 貸方事業所
  private String crHasseiBasho;    // 貸方発生場所
  private String crKanjoKamoku;    // 貸方勘定科目
  private String crKanjoKamokuCd;  // 貸方勘定科目コード
  private String crHojoKamoku;     // 貸方補助科目
  private String crTaxKbn;         // 貸方税区分
  private String crTaxKbnName;     // 貸方税区分名称
  private int kingaku;             // 金額
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getNyukinKeijobi() {
    return nyukinKeijobi;
  }
  
  public void setNyukinKeijobi(String nyukinKeijobi) {
    this.nyukinKeijobi = nyukinKeijobi;
  }
  
  public String getTokuisakiName() {
    return tokuisakiName;
  }
  
  public void setTokuisakiName(String tokuisakiName) {
    this.tokuisakiName = tokuisakiName;
  }
  
  public String getDrJigyoshoCd() {
    return drJigyoshoCd;
  }
  
  public void setDrJigyoshoCd(String drJigyoshoCd) {
    this.drJigyoshoCd = drJigyoshoCd;
  }
  
  public String getDrHasseiBasho() {
    return drHasseiBasho;
  }
  
  public void setDrHasseiBasho(String drHasseiBasho) {
    this.drHasseiBasho = drHasseiBasho;
  }
  
  public String getDrKanjoKamoku() {
    return drKanjoKamoku;
  }
  
  public void setDrKanjoKamoku(String drKanjoKamoku) {
    this.drKanjoKamoku = drKanjoKamoku;
  }
  
  public String getDrKanjoKamokuCd() {
    return drKanjoKamokuCd;
  }
  
  public void setDrKanjoKamokuCd(String drKanjoKamokuCd) {
    this.drKanjoKamokuCd = drKanjoKamokuCd;
  }
  
  public String getDrHojoKamoku() {
    return drHojoKamoku;
  }
  
  public void setDrHojoKamoku(String drHojoKamoku) {
    this.drHojoKamoku = drHojoKamoku;
  }
  
  public String getDrTaxKbn() {
    return drTaxKbn;
  }
  
  public void setDrTaxKbn(String drTaxKbn) {
    this.drTaxKbn = drTaxKbn;
  }
  
  public String getDrTaxKbnName() {
    return drTaxKbnName;
  }
  
  public void setDrTaxKbnName(String drTaxKbnName) {
    this.drTaxKbnName = drTaxKbnName;
  }
  
  public String getCrJigyoshoCd() {
    return crJigyoshoCd;
  }
  
  public void setCrJigyoshoCd(String crJigyoshoCd) {
    this.crJigyoshoCd = crJigyoshoCd;
  }
  
  public String getCrHasseiBasho() {
    return crHasseiBasho;
  }
  
  public void setCrHasseiBasho(String crHasseiBasho) {
    this.crHasseiBasho = crHasseiBasho;
  }
  
  public String getCrKanjoKamoku() {
    return crKanjoKamoku;
  }
  
  public void setCrKanjoKamoku(String crKanjoKamoku) {
    this.crKanjoKamoku = crKanjoKamoku;
  }
  
  public String getCrKanjoKamokuCd() {
    return crKanjoKamokuCd;
  }
  
  public void setCrKanjoKamokuCd(String crKanjoKamokuCd) {
    this.crKanjoKamokuCd = crKanjoKamokuCd;
  }
  
  public String getCrHojoKamoku() {
    return crHojoKamoku;
  }
  
  public void setCrHojoKamoku(String crHojoKamoku) {
    this.crHojoKamoku = crHojoKamoku;
  }
  
  public String getCrTaxKbn() {
    return crTaxKbn;
  }
  
  public void setCrTaxKbn(String crTaxKbn) {
    this.crTaxKbn = crTaxKbn;
  }
  
  public String getCrTaxKbnName() {
    return crTaxKbnName;
  }
  
  public void setCrTaxKbnName(String crTaxKbnName) {
    this.crTaxKbnName = crTaxKbnName;
  }
  
  public int getKingaku() {
    return kingaku;
  }
  
  public void setKingaku(int kingaku) {
    this.kingaku = kingaku;
  }

}
