/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0101d01.java
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
 * フォーム(jspの modelAttribute="FormMst0101d01" とリンク）
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0101d01 {

  // [画面_hidden]
  private Integer businessDate;
  private String viewMode;
  private String selectUserCode;
  private int searchMax;
  private String errorControl;
  private String haitaDate;
  private String haitaTime;
  private String sysAdminFlag;
  private String loginJigyouShoCode;

  // 検索条件の項目
  private String txtUserCode;
  private String txtUserName;
  private String ddlShozoku;
  private String ddlRiyoKengen;
  private boolean chkTorikeshiData;
  private boolean chkLock;

  public Integer getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

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

  public String getDdlShozoku() {
    return ddlShozoku;
  }

  public void setDdlShozoku(String ddlShozoku) {
    this.ddlShozoku = ddlShozoku;
  }

  public String getDdlRiyoKengen() {
    return ddlRiyoKengen;
  }

  public void setDdlRiyoKengen(String ddlRiyoKengen) {
    this.ddlRiyoKengen = ddlRiyoKengen;
  }

  public boolean isChkTorikeshiData() {
    return chkTorikeshiData;
  }

  public void setChkTorikeshiData(boolean chkTorikeshiData) {
    this.chkTorikeshiData = chkTorikeshiData;
  }

  public boolean isChkLock() {
    return chkLock;
  }

  public void setChkLock(boolean chkLock) {
    this.chkLock = chkLock;
  }

  public String getViewMode() {
    return viewMode;
  }

  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  public String getSelectUserCode() {
    return selectUserCode;
  }

  public void setSelectUserCode(String selectUserCode) {
    this.selectUserCode = selectUserCode;
  }

  public int getSearchMax() {
    return searchMax;
  }

  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
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