/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0104d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.ArrayList;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;

/**
 * フォーム(jspの modelAttribute="FormMst0104d01" とリンク）
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0104d01 {
  // [画面_hidden]業務日付
  private Integer businessDate;
  private String viewMode;
  private String selectCourseCode;
  private String selectJigyoCode;

  private int searchMax;

  // 事業所
  private String ddlJigyoCode;
  // コースコード
  private String txtCourseCode;
  // コース名称
  private String txtCourseName;
  // 配送区分
  private String txtHaisoKb;
  // 取消データを対象とする
  private boolean chkCancellationData;

  // システム管理者フラグ
  private String sysAdminFlag;
  // ログイン事業所コード
  private String loginJigyouShoCode;

  private ArrayList<ErrorMessages> errorMessage;
  private String strId;

  // メッセージ
  private String txtMessage;
  private String errorControl;
  private String haitaDate;
  private String haitaTime;

  public Integer getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

  public String getViewMode() {
    return viewMode;
  }

  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  public String getSelectCourseCode() {
    return selectCourseCode;
  }

  public void setSelectCourseCode(String selectCourseCode) {
    this.selectCourseCode = selectCourseCode;
  }

  public String getSelectJigyoCode() {
    return selectJigyoCode;
  }

  public void setSelectJigyoCode(String selectJigyoCode) {
    this.selectJigyoCode = selectJigyoCode;
  }

  public int getSearchMax() {
    return searchMax;
  }

  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

  public String getDdlJigyoCode() {
    return ddlJigyoCode;
  }

  public void setDdlJigyoCode(String ddlJigyoCode) {
    this.ddlJigyoCode = ddlJigyoCode;
  }

  public String getTxtCourseCode() {
    return txtCourseCode;
  }

  public void setTxtCourseCode(String txtCourseCode) {
    this.txtCourseCode = txtCourseCode;
  }

  public String getTxtCourseName() {
    return txtCourseName;
  }

  public void setTxtCourseName(String txtCourseName) {
    this.txtCourseName = txtCourseName;
  }

  public String getTxtHaisoKb() {
    return txtHaisoKb;
  }

  public void setTxtHaisoKb(String txtHaisoKb) {
    this.txtHaisoKb = txtHaisoKb;
  }

  public boolean isChkCancellationData() {
    return chkCancellationData;
  }

  public void setChkCancellationData(boolean chkCancellationData) {
    this.chkCancellationData = chkCancellationData;
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

  public ArrayList<ErrorMessages> getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(ArrayList<ErrorMessages> errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getStrId() {
    return strId;
  }

  public void setStrId(String strId) {
    this.strId = strId;
  }

  public String getTxtMessage() {
    return txtMessage;
  }

  public void setTxtMessage(String txtMessage) {
    this.txtMessage = txtMessage;
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

}