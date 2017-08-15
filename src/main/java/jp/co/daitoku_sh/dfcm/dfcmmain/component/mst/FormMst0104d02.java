/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0104d02.java
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
 * フォーム(jspの modelAttribute="FormMst0104d02" とリンク）
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0104d02 {
  // hidden項目
  private Integer businessDate;
  private String form1CancelData;
  private String form1JigyoCode;
  private String form1CourseCode;
  private String form1CourseName;
  private String form1HaisoKb;
  private String selectJigyoCodeHidden;
  // 事業所
  private String ddlJigyoCode;

  // コースコード
  private String txtCourseCode;

  // コース名称
  private String txtCourseName;

  // コース略称
  private String txtCourseNameR;

  // 配送時間 01
  private String txtHaisoTime01;

  // 配送時間 02
  private String txtHaisoTime02;
  private String txtHaisoTime;
  
  // 出荷更新時間 01
  private String txtShipUpdateTime01;

  // 出荷更新時間 02
  private String txtShipUpdateTime02;
  private String txtShipUpdateTime;
  // 配送区分
  private String txtHaisoKb;

  // 状況コード(ヘッダ部)
  private String txtStsCode01;

  // 状況コード(編集エリア)
  private String txtStsCode02;

  // 得意先コード
  private String txtCustomerCode;
  private String lblCustomerNameR;

  // 店舗コード
  private String txtShopCode;
  private String lblShopNameR;

  // 便区分
  private String txtBinKb;

  // 納品区分
  private String ddlDeliKb;
  private String mode;
  
  private String sysAdminFlag;
  private String loginJigyouShoCode;
  private ArrayList<ErrorMessages> errorMessage;
  private String strId;
  private String txtMessage;
  private String msgExist;

  private String haitaDate;

  private String haitaTime;

  private String errorControl;

  public Integer getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

  public String getForm1CancelData() {
    return form1CancelData;
  }

  public void setForm1CancelData(String form1CancelData) {
    this.form1CancelData = form1CancelData;
  }

  public String getForm1JigyoCode() {
    return form1JigyoCode;
  }

  public void setForm1JigyoCode(String form1JigyoCode) {
    this.form1JigyoCode = form1JigyoCode;
  }

  public String getForm1CourseCode() {
    return form1CourseCode;
  }

  public void setForm1CourseCode(String form1CourseCode) {
    this.form1CourseCode = form1CourseCode;
  }

  public String getForm1CourseName() {
    return form1CourseName;
  }

  public void setForm1CourseName(String form1CourseName) {
    this.form1CourseName = form1CourseName;
  }

  public String getForm1HaisoKb() {
    return form1HaisoKb;
  }

  public void setForm1HaisoKb(String form1HaisoKb) {
    this.form1HaisoKb = form1HaisoKb;
  }

  public String getSelectJigyoCodeHidden() {
    return selectJigyoCodeHidden;
  }

  public void setSelectJigyoCodeHidden(String selectJigyoCodeHidden) {
    this.selectJigyoCodeHidden = selectJigyoCodeHidden;
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

  public String getTxtCourseNameR() {
    return txtCourseNameR;
  }

  public void setTxtCourseNameR(String txtCourseNameR) {
    this.txtCourseNameR = txtCourseNameR;
  }

  public String getTxtHaisoTime01() {
    return txtHaisoTime01;
  }

  public void setTxtHaisoTime01(String txtHaisoTime01) {
    this.txtHaisoTime01 = txtHaisoTime01;
  }

  public String getTxtHaisoTime02() {
    return txtHaisoTime02;
  }

  public void setTxtHaisoTime02(String txtHaisoTime02) {
    this.txtHaisoTime02 = txtHaisoTime02;
  }
  
  public String getTxtHaisoTime() {
    return txtHaisoTime;
  }
  
  public void setTxtHaisoTime(String txtHaisoTime) {
    this.txtHaisoTime = txtHaisoTime;
  }

  public String getTxtShipUpdateTime01() {
    return txtShipUpdateTime01;
  }

  public void setTxtShipUpdateTime01(String txtShipUpdateTime01) {
    this.txtShipUpdateTime01 = txtShipUpdateTime01;
  }

  public String getTxtShipUpdateTime02() {
    return txtShipUpdateTime02;
  }

  public void setTxtShipUpdateTime02(String txtShipUpdateTime02) {
    this.txtShipUpdateTime02 = txtShipUpdateTime02;
  }
  
  public String getTxtShipUpdateTime() {
    return txtShipUpdateTime;
  }
  
  public void setTxtShipUpdateTime(String txtShipUpdateTime) {
    this.txtShipUpdateTime = txtShipUpdateTime;
  }

  public String getTxtHaisoKb() {
    return txtHaisoKb;
  }

  public void setTxtHaisoKb(String txtHaisoKb) {
    this.txtHaisoKb = txtHaisoKb;
  }
  
  public String getTxtStsCode01() {
    return txtStsCode01;
  }
  
  public void setTxtStsCode01(String txtStsCode01) {
    this.txtStsCode01 = txtStsCode01;
  }
  
  public String getTxtStsCode02() {
    return txtStsCode02;
  }
  
  public void setTxtStsCode02(String txtStsCode02) {
    this.txtStsCode02 = txtStsCode02;
  }

  public String getTxtCustomerCode() {
    return txtCustomerCode;
  }

  public void setTxtCustomerCode(String txtCustomerCode) {
    this.txtCustomerCode = txtCustomerCode;
  }

  public String getLblCustomerNameR() {
    return lblCustomerNameR;
  }

  public void setLblCustomerNameR(String lblCustomerNameR) {
    this.lblCustomerNameR = lblCustomerNameR;
  }

  public String getTxtShopCode() {
    return txtShopCode;
  }

  public void setTxtShopCode(String txtShopCode) {
    this.txtShopCode = txtShopCode;
  }

  public String getLblShopNameR() {
    return lblShopNameR;
  }

  public void setLblShopNameR(String lblShopNameR) {
    this.lblShopNameR = lblShopNameR;
  }

  public String getTxtBinKb() {
    return txtBinKb;
  }

  public void setTxtBinKb(String txtBinKb) {
    this.txtBinKb = txtBinKb;
  }

  public String getDdlDeliKb() {
    return ddlDeliKb;
  }

  public void setDdlDeliKb(String ddlDeliKb) {
    this.ddlDeliKb = ddlDeliKb;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
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

  public String getMsgExist() {
    return msgExist;
  }

  public void setMsgExist(String msgExist) {
    this.msgExist = msgExist;
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

}