/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0101d02.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * フォーム(jspの modelAttribute="FormMst0101d02" とリンク）
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0101d02 {

  // hidden項目
  private Integer businessDate;
  private String form1JigyoshoCode;
  private String form1UserCode;
  private String form1UserNm;
  private String form1AuthCode;
  private String form1CancelData;
  private String form1UserStatus;
  private String mode;
  private String errorControl;
  private String haitaDate;
  private String haitaTime;
  private String sysAdminFlag;
  private String loginJigyouShoCode;

  // 担当者コード
  private String txtUserCode;

  // 担当者氏名
  private String txtUserName;

  // 担当者氏名カナ
  private String txtUserNameKata;

  // 所属事業所
  private String ddlShozoku;
  private String shozokuSelect;

  // 利用権限
  private String ddlRiyoKengen;
  private String riyoKengenSelect;

  // 部署名
  private String txtDivisionName;

  // メールアドレス
  private String txtAddress;

  // 電話番号
  private String txtTel1;
  private String txtTel2;
  private String txtTel3;

  private String txtTelResult;

  // Fax番号
  private String txtFax1;
  private String txtFax2;
  private String txtFax3;

  private String txtFaxResult;

  // 状況コード
  private String txtStatusCode;

  // システム利用
  private boolean chkSystemUse;

  // パスワード
  private boolean chkPassword;

  // ロックアウト
  private boolean chkLogout;

  public String getTxtUserCode() {
    return txtUserCode;
  }

  public void setTxtUserCode(String txtUserCode) {
    this.txtUserCode = txtUserCode;
  }

  public String getTxtUserName() {
    return txtUserName;
  }

  public void setTxtUserName(String txtUserName) {
    this.txtUserName = txtUserName;
  }

  public String getTxtAddress() {
    return txtAddress;
  }

  public void setTxtAddress(String txtAddress) {
    this.txtAddress = txtAddress;
  }

  public String getForm1JigyoshoCode() {
    return form1JigyoshoCode;
  }

  public void setForm1JigyoshoCode(String form1JigyoshoCode) {
    this.form1JigyoshoCode = form1JigyoshoCode;
  }

  public String getForm1UserCode() {
    return form1UserCode;
  }

  public void setForm1UserCode(String form1UserCode) {
    this.form1UserCode = form1UserCode;
  }

  public String getForm1UserNm() {
    return form1UserNm;
  }

  public void setForm1UserNm(String form1UserNm) {
    this.form1UserNm = form1UserNm;
  }

  public String getForm1AuthCode() {
    return form1AuthCode;
  }

  public void setForm1AuthCode(String form1AuthCode) {
    this.form1AuthCode = form1AuthCode;
  }

  public String getForm1CancelData() {
    return form1CancelData;
  }

  public void setForm1CancelData(String form1CancelData) {
    this.form1CancelData = form1CancelData;
  }

  public String getForm1UserStatus() {
    return form1UserStatus;
  }

  public void setForm1UserStatus(String form1UserStatus) {
    this.form1UserStatus = form1UserStatus;
  }

  public Integer getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

  public String getDdlRiyoKengen() {
    return ddlRiyoKengen;
  }

  public void setDdlRiyoKengen(String ddlRiyoKengen) {
    this.ddlRiyoKengen = ddlRiyoKengen;
  }

  public String getDdlShozoku() {
    return ddlShozoku;
  }

  public void setDdlShozoku(String ddlShozoku) {
    this.ddlShozoku = ddlShozoku;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getTxtUserNameKata() {
    return txtUserNameKata;
  }

  public void setTxtUserNameKata(String txtUserNameKata) {
    this.txtUserNameKata = txtUserNameKata;
  }

  public String getTxtDivisionName() {
    return txtDivisionName;
  }

  public void setTxtDivisionName(String txtDivisionName) {
    this.txtDivisionName = txtDivisionName;
  }

  public String getTxtTel1() {
    return txtTel1;
  }

  public void setTxtTel1(String txtTel1) {
    this.txtTel1 = txtTel1;
  }

  public String getTxtTel2() {
    return txtTel2;
  }

  public void setTxtTel2(String txtTel2) {
    this.txtTel2 = txtTel2;
  }

  public String getTxtTel3() {
    return txtTel3;
  }

  public void setTxtTel3(String txtTel3) {
    this.txtTel3 = txtTel3;
  }

  public String getTxtTelResult() {
    return txtTelResult;
  }

  public void setTxtTelResult(String txtTelResult) {
    this.txtTelResult = txtTelResult;
  }

  public String getTxtFax1() {
    return txtFax1;
  }

  public void setTxtFax1(String txtFax1) {
    this.txtFax1 = txtFax1;
  }

  public String getTxtFax2() {
    return txtFax2;
  }

  public void setTxtFax2(String txtFax2) {
    this.txtFax2 = txtFax2;
  }

  public String getTxtFax3() {
    return txtFax3;
  }

  public void setTxtFax3(String txtFax3) {
    this.txtFax3 = txtFax3;
  }

  public String getTxtFaxResult() {
    return txtFaxResult;
  }

  public void setTxtFaxResult(String txtFaxResult) {
    this.txtFaxResult = txtFaxResult;
  }

  public String getTxtStatusCode() {
    return txtStatusCode;
  }

  public void setTxtStatusCode(String txtStatusCode) {
    this.txtStatusCode = txtStatusCode;
  }

  public boolean isChkSystemUse() {
    return chkSystemUse;
  }

  public void setChkSystemUse(boolean chkSystemUse) {
    this.chkSystemUse = chkSystemUse;
  }

  public boolean isChkPassword() {
    return chkPassword;
  }

  public void setChkPassword(boolean chkPassword) {
    this.chkPassword = chkPassword;
  }

  public boolean isChkLogout() {
    return chkLogout;
  }

  public void setChkLogout(boolean chkLogout) {
    this.chkLogout = chkLogout;
  }

  public String getShozokuSelect() {
    return shozokuSelect;
  }

  public void setShozokuSelect(String shozokuSelect) {
    this.shozokuSelect = shozokuSelect;
  }

  public String getRiyoKengenSelect() {
    return riyoKengenSelect;
  }

  public void setRiyoKengenSelect(String riyoKengenSelect) {
    this.riyoKengenSelect = riyoKengenSelect;
  }

  public String getErrorControl() {
    return errorControl;
  }

  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
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

  public String getSysAdminFlag() {
    return sysAdminFlag;
  }

  public void setSysAdminFlag(String sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }

  public String getLoginJigyouShoCode() {
    return loginJigyouShoCode;
  }

  public void setLoginJigyouShoCode(String loginJigyouShoCode) {
    this.loginJigyouShoCode = loginJigyouShoCode;
  }
}