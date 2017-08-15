/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:GetDeterminationInfoGet0101d00.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/07
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * 事業所月次確定情報のクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */

public class GetDeterminationInfoGet0101d00 {

  // [変数]事業所コードwk
  private String officeCodewk;
  // [変数]事業所名称wk
  private String officeNamewk;
  // [変数]確定売掛月度wk
  private String monthlyFixwk;
  // [変数]当月売掛月度wk
  private String thisMonthFixwk;
  // [変数]月次確定フラグwk
  private String monthlyFixFlagwk;
  // [変数]処理開始日時wk
  private String processStartDateTimewk;
  // [変数]処理終了日時wk
  private String processEndDateTimewk;
  // [変数]事務担当者wk
  private String jimuTantoNamewk;

  public String getOfficeCodewk() {
    return officeCodewk;
  }

  public void setOfficeCodewk(String officeCodewk) {
    this.officeCodewk = officeCodewk;
  }

  public String getOfficeNamewk() {
    return officeNamewk;
  }

  public void setOfficeNamewk(String officeNamewk) {
    this.officeNamewk = officeNamewk;
  }

  public String getMonthlyFixwk() {
    return monthlyFixwk;
  }

  public void setMonthlyFixwk(String monthlyFixwk) {
    this.monthlyFixwk = monthlyFixwk;
  }

  public String getThisMonthFixwk() {
    return thisMonthFixwk;
  }

  public void setThisMonthFixwk(String thisMonthFixwk) {
    this.thisMonthFixwk = thisMonthFixwk;
  }

  public String getMonthlyFixFlagwk() {
    return monthlyFixFlagwk;
  }

  public void setMonthlyFixFlagwk(String monthlyFixFlagwk) {
    this.monthlyFixFlagwk = monthlyFixFlagwk;
  }

  public String getProcessStartDateTimewk() {
    return processStartDateTimewk;
  }

  public void setProcessStartDateTimewk(String processStartDateTimewk) {
    this.processStartDateTimewk = processStartDateTimewk;
  }

  public String getProcessEndDateTimewk() {
    return processEndDateTimewk;
  }

  public void setProcessEndDateTimewk(String processEndDateTimewk) {
    this.processEndDateTimewk = processEndDateTimewk;
  }

  public String getJimuTantoNamewk() {
    return jimuTantoNamewk;
  }

  public void setJimuTantoNamewk(String jimuTantoNamewk) {
    this.jimuTantoNamewk = jimuTantoNamewk;
  }

}