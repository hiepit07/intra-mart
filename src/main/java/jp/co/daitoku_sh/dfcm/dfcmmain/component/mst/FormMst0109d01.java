/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0109d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/20
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/20 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * フォーム(jspの modelAttribute="FormMst0109d01" とリンク）
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0109d01 {

  // [画面_hidden]
  private String custCodeNone;
  private Integer businessDate;
  private String errorControl;
  private String sysAdminFlag;
  private String loginJigyouShoCode;
  private String lblCustNmRHidden;
  private int searchMax;
  private String viewMode;
  private String haitaDate;
  private String haitaTime;
  private String selectCorrectKb;
  private String selectCustCode;
  
  // 検索条件の項目
  private String ddlOyaShozoku;
  private String txtCustCode;
  private String txtCustNmR;
  private String txtCorrectKb;
  private String txtZeroDlvdatFlg;
  private boolean chkStsCode;
  private boolean chkStKb;
  
  public String getCustCodeNone() {
    return custCodeNone;
  }
  
  public void setCustCodeNone(String custCodeNone) {
    this.custCodeNone = custCodeNone;
  }
  
  public Integer getBusinessDate() {
    return businessDate;
  }
  
  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }
  
  public String getErrorControl() {
    return errorControl;
  }
  
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
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
  
  public String getLblCustNmRHidden() {
    return lblCustNmRHidden;
  }
  
  public void setLblCustNmRHidden(String lblCustNmRHidden) {
    this.lblCustNmRHidden = lblCustNmRHidden;
  }
  
  public int getSearchMax() {
    return searchMax;
  }
  
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }
  
  public String getViewMode() {
    return viewMode;
  }
  
  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
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
  
  public String getSelectCorrectKb() {
    return selectCorrectKb;
  }
  
  public void setSelectCorrectKb(String selectCorrectKb) {
    this.selectCorrectKb = selectCorrectKb;
  }
  
  public String getSelectCustCode() {
    return selectCustCode;
  }
  
  public void setSelectCustCode(String selectCustCode) {
    this.selectCustCode = selectCustCode;
  }
  
 
  
  
  public String getDdlOyaShozoku() {
    return ddlOyaShozoku;
  }

  
  public void setDdlOyaShozoku(String ddlOyaShozoku) {
    this.ddlOyaShozoku = ddlOyaShozoku;
  }

  public String getTxtCustCode() {
    return txtCustCode;
  }
  
  public void setTxtCustCode(String txtCustCode) {
    this.txtCustCode = txtCustCode;
  } 
  
  public String getTxtCustNmR() {
    return txtCustNmR;
  }
  
  public void setTxtCustNmR(String txtCustNmR) {
    this.txtCustNmR = txtCustNmR;
  }
  
  public String getTxtCorrectKb() {
    return txtCorrectKb;
  }
  
  public void setTxtCorrectKb(String txtCorrectKb) {
    this.txtCorrectKb = txtCorrectKb;
  }
  
  public String getTxtZeroDlvdatFlg() {
    return txtZeroDlvdatFlg;
  }
  
  public void setTxtZeroDlvdatFlg(String txtZeroDlvdatFlg) {
    this.txtZeroDlvdatFlg = txtZeroDlvdatFlg;
  }
  
  public boolean isChkStsCode() {
    return chkStsCode;
  }
  
  public void setChkStsCode(boolean chkStsCode) {
    this.chkStsCode = chkStsCode;
  }
  
  public boolean isChkStKb() {
    return chkStKb;
  }
  
  public void setChkStKb(boolean chkStKb) {
    this.chkStKb = chkStKb;
  }
  
   
 
}