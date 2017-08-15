/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:FormNyu0104d01.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/12/16
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/16 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 入金予定一覧画面用 FORM
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class FormNyu0104d01 {
  
  private String sysAdminFlag;      // システム管理者フラグ
  private String loginJigyoshoCode;    // ログイン事業所コード
  private String jigyoshoCodeWk;  // 事業所名
  private Integer businessDate;       // 業務日付
  private String haitaDate;          // 排他日付
  private String haitaTime;          // 排他時刻
  private String errorControl;       // エラー制御フラグ
  
  private String ddlJigyoCode;        // 事業所
  private String txtPaymentDateFrom;  // 入金予定日(From)
  private String txtPaymentDateTo;    // 入金予定日(To)
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getSysAdminFlag() {
    return sysAdminFlag;
  }
  
  public void setSysAdminFlag(String sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }
  
  public String getLoginJigyoshoCode() {
    return loginJigyoshoCode;
  }
  
  public void setLoginJigyoshoCode(String loginJigyoshoCode) {
    this.loginJigyoshoCode = loginJigyoshoCode;
  }
  
  public String getJigyoshoCodeWk() {
    return jigyoshoCodeWk;
  }
  
  public void setJigyoshoCodeWk(String jigyoshoCodeWk) {
    this.jigyoshoCodeWk = jigyoshoCodeWk;
  }

  public Integer getBusinessDate() {
    return businessDate;
  }
  
  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

  public String getHaitaDate() {
    return haitaDate;
  }
  
  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }
  
  public String getHaitaTime() {
    return haitaTime;
  }
  
  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }
  
  public String getErrorControl() {
    return errorControl;
  }
  
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }
  
  public String getDdlJigyoCode() {
    return ddlJigyoCode;
  }
  
  public void setDdlJigyoCode(String ddlJigyoCode) {
    this.ddlJigyoCode = ddlJigyoCode;
  }
  
  public String getTxtPaymentDateFrom() {
    return txtPaymentDateFrom;
  }
  
  public void setTxtPaymentDateFrom(String txtPaymentDateFrom) {
    this.txtPaymentDateFrom = txtPaymentDateFrom;
  }
  
  public String getTxtPaymentDateTo() {
    return txtPaymentDateTo;
  }
  
  public void setTxtPaymentDateTo(String txtPaymentDateTo) {
    this.txtPaymentDateTo = txtPaymentDateTo;
  }
  
}