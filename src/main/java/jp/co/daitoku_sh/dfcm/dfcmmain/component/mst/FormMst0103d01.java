/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0103d01.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)TramChu  新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * フォーム(jspの modelAttribute="FormMst0103d01" とリンク）
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0103d01 {

  // [画面_hidden]業務日付
  private String businessDate;
  private String viewMode;
  private String selectCustomerCode;
  private String selectStoreCode;
  private String haitaDate;
  private String haitaTime;

  // Search condition item of
  private String ddlOffice;
  private String txtCustomerCode;
  private String txtCustomerName;
  private String txtStoreCode;
  private String txtStoreName;
  private Boolean chkCancelData;
  private String sysAdminFlag;
  private String loginJigyouShoCode;
  private int searchMax;

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

  public String getViewMode() {
    return viewMode;
  }

  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  public String getSelectCustomerCode() {
    return selectCustomerCode;
  }

  public void setSelectCustomerCode(String selectCustomerCode) {
    this.selectCustomerCode = selectCustomerCode;
  }

  public String getTxtCustomerName() {
    return txtCustomerName;
  }

  public void setTxtCustomerName(String txtCustomerName) {
    this.txtCustomerName = txtCustomerName;
  }

  public Boolean getChkCancelData() {
    return chkCancelData;
  }

  public void setChkCancelData(Boolean chkCancelData) {
    this.chkCancelData = chkCancelData;
  }

  public String getDdlOffice() {
    return ddlOffice;
  }

  public void setDdlOffice(String ddlOffice) {
    this.ddlOffice = ddlOffice;
  }

  public String getTxtCustomerCode() {
    return txtCustomerCode;
  }

  public void setTxtCustomerCode(String txtCustomerCode) {
    this.txtCustomerCode = txtCustomerCode;
  }

  public String getTxtStoreCode() {
    return txtStoreCode;
  }

  public void setTxtStoreCode(String txtStoreCode) {
    this.txtStoreCode = txtStoreCode;
  }

  public String getTxtStoreName() {
    return txtStoreName;
  }

  public void setTxtStoreName(String txtStoreName) {
    this.txtStoreName = txtStoreName;
  }

  public String getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  public String getSelectStoreCode() {
    return selectStoreCode;
  }

  public void setSelectStoreCode(String selectStoreCode) {
    this.selectStoreCode = selectStoreCode;
  }

  public int getSearchMax() {
    return searchMax;
  }

  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

}