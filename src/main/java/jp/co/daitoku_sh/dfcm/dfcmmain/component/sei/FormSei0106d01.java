/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormSei0106d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/12/07
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/07 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.sei;

import java.util.ArrayList;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0106d01;

/**
 * 請求一覧画面用 FORM
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class FormSei0106d01 {

  /** 事業所コード */
  private String ddlJigyoCode;
  /** 請求月*/
  private String txtBildDate;
  /** 事務担当者コード*/
  private String txtJgycd;
  /** 事務担当者名*/
  private String lblJgycdNm;
  /** 締日*/
  private String ddlJgymei;
  /** 請求先コード*/
  private String txtUserCode;
  /** 請求先名*/
  private String lblUserNm;
  /** 請求一覧出力形式*/
  private String rdOutputFormat;
  /** [HIDDEN]業務日付*/
  private String businessDate;
  /** セッション.ログイン事業所コード*/
  private String loginJigyouShoCode;
  
  private boolean errorControl;
  
  private String errMessage;

  private String messageType;

  private String errItemIds;
  
  /** セッション.システム管理者フラグ*/
  private String sysAdminFlag;
  
  /** 事業所名*/
  private String jigyoName;
  
  /** 検索上限値*/
  private int searchMax;
  
  private String filePath;
  
  private ArrayList<RstSei0106d01> billInfoList;
  
  public String getDdlJigyoCode() {
    return ddlJigyoCode;
  }
  
  public void setDdlJigyoCode(String ddlJigyoCode) {
    this.ddlJigyoCode = ddlJigyoCode;
  }
  
  public String getTxtBildDate() {
    return txtBildDate;
  }
  
  public void setTxtBildDate(String txtBildDate) {
    this.txtBildDate = txtBildDate;
  }
  
  public String getTxtJgycd() {
    return txtJgycd;
  }
  
  public void setTxtJgycd(String txtJgycd) {
    this.txtJgycd = txtJgycd;
  }
  
  public String getLblJgycdNm() {
    return lblJgycdNm;
  }
  
  public void setLblJgycdNm(String lblJgycdNm) {
    this.lblJgycdNm = lblJgycdNm;
  }
  
  public String getDdlJgymei() {
    return ddlJgymei;
  }

  
  public void setDdlJgymei(String ddlJgymei) {
    this.ddlJgymei = ddlJgymei;
  }

  public String getTxtUserCode() {
    return txtUserCode;
  }
  
  public void setTxtUserCode(String txtUserCode) {
    this.txtUserCode = txtUserCode;
  }
  
  public String getLblUserNm() {
    return lblUserNm;
  }
  
  public void setLblUserNm(String lblUserNm) {
    this.lblUserNm = lblUserNm;
  }
  
  public String getRdOutputFormat() {
    return rdOutputFormat;
  }
  
  public void setRdOutputFormat(String rdOutputFormat) {
    this.rdOutputFormat = rdOutputFormat;
  }

  
  public String getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  
  public boolean isErrorControl() {
    return errorControl;
  }

  
  public void setErrorControl(boolean errorControl) {
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

  
  public String getErrMessage() {
    return errMessage;
  }

  
  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }

  
  public String getMessageType() {
    return messageType;
  }

  
  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  
  public String getErrItemIds() {
    return errItemIds;
  }

  
  public void setErrItemIds(String errItemIds) {
    this.errItemIds = errItemIds;
  }

  
  public String getJigyoName() {
    return jigyoName;
  }

  
  public void setJigyoName(String jigyoName) {
    this.jigyoName = jigyoName;
  }

  
  public int getSearchMax() {
    return searchMax;
  }

  
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

  
  public ArrayList<RstSei0106d01> getBillInfoList() {
    return billInfoList;
  }

  
  public void setBillInfoList(ArrayList<RstSei0106d01> billInfoList) {
    this.billInfoList = billInfoList;
  }

  
  public String getFilePath() {
    return filePath;
  }

  
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
}
