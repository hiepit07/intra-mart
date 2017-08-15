/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0105d02.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.ArrayList;

/**
 * フォーム(jspの modelAttribute="FormMst0105d02" とリンク）
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormMst0105d02 {

  /** オンラインセンターコード */
  private String txtOnlineCenterCode;
  /** オンラインセンターコード */
  private String txtOnlineTorihikiCode;
  /** 相手取引先コード */
  private String txtAtTorihikiCode;
  /** 相手取引先コード */
  private String txtAtTorihikiCode02;
  /** 相手店コード */
  private String txtAtTenCode;
  /** 自社得意先コード */
  private String txtCustomerCode;
  /** 自社店舗コード */
  private String txtShopCode;
  /** 納品区分 */
  private String ddlDeliKb;
  /** 状況コード */
  private String txtStsCode;
  /**
   * The date that retrieve data from database of the previous screen
   */
  private String haitaDate;
  /**
   * The time that retrieve data from database of the previous screen
   */
  private String haitaTime;
  /** 画面表示モード */
  private String screenMode;

  private boolean errorControl;
  /** 店舗コード_未指定 */
  private String olTorihikiCodeNone;
  /** オンライン取引先コード_未指定 */
  private String tenCodeNone;

  /** 業務日付 */
  private String businessDate;

  /** オンラインセンターコード */
  private String preOnlineCenterCode;
  /** オンライン取引先コード */
  private String preOnlineTorihikiCode;
  /** 相手取引先コード */
  private String preAtTorihikiCode;
  /** 取消データを対象とする */
  private boolean chkCancellationData;

  /** オンラインセンター名称 */
  private String lblCustomerNameR01;
  /** 相手取引先名称 */
  private String lblCustomerNameR02;

  private String lblCustomerNameR;

  private String lblShopNameR;

  private String errMessage;

  private String messageType;

  private String errItemIds;
  /** セッション.システム管理者フラグ */
  private String sysAdminFlag;
  /** セッション.ログイン事業所コード */
  private String plantCode;

  /** 編集モード */
  private String editMode;
  
  /** オンライン得意先変換マスタ一覧 */
  private ArrayList<OlCustConvMasterData> olCustConvList;
  
  public String getEditMode() {
    return editMode;
  }

  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  public ArrayList<OlCustConvMasterData> getOlCustConvList() {
    return olCustConvList;
  }

  public void setOlCustConvList(
      ArrayList<OlCustConvMasterData> olCustConvList) {
    this.olCustConvList = olCustConvList;
  }

  public String getLblCustomerNameR() {
    return lblCustomerNameR;
  }

  public void setLblCustomerNameR(String lblCustomerNameR) {
    this.lblCustomerNameR = lblCustomerNameR;
  }

  public String getLblShopNameR() {
    return lblShopNameR;
  }

  public void setLblShopNameR(String lblShopNameR) {
    this.lblShopNameR = lblShopNameR;
  }

  public String getSysAdminFlag() {
    return sysAdminFlag;
  }

  public void setSysAdminFlag(String sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }

  public String getErrItemIds() {
    return errItemIds;
  }

  public void setErrItemIds(String errItemIds) {
    this.errItemIds = errItemIds;
  }

  public String getLblCustomerNameR01() {
    return lblCustomerNameR01;
  }

  public void setLblCustomerNameR01(String lblCustomerNameR01) {
    this.lblCustomerNameR01 = lblCustomerNameR01;
  }

  public String getLblCustomerNameR02() {
    return lblCustomerNameR02;
  }

  public void setLblCustomerNameR02(String lblCustomerNameR02) {
    this.lblCustomerNameR02 = lblCustomerNameR02;
  }

  public String getPreOnlineCenterCode() {
    return preOnlineCenterCode;
  }

  public void setPreOnlineCenterCode(String preOnlineCenterCode) {
    this.preOnlineCenterCode = preOnlineCenterCode;
  }

  public String getPreOnlineTorihikiCode() {
    return preOnlineTorihikiCode;
  }

  public void setPreOnlineTorihikiCode(String preOnlineTorihikiCode) {
    this.preOnlineTorihikiCode = preOnlineTorihikiCode;
  }

  public String getPreAtTorihikiCode() {
    return preAtTorihikiCode;
  }

  public void setPreAtTorihikiCode(String preAtTorihikiCode) {
    this.preAtTorihikiCode = preAtTorihikiCode;
  }

  public boolean isChkCancellationData() {
    return chkCancellationData;
  }

  public void setChkCancellationData(boolean chkCancellationData) {
    this.chkCancellationData = chkCancellationData;
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

  public String getScreenMode() {
    return screenMode;
  }

  public void setScreenMode(String screenMode) {
    this.screenMode = screenMode;
  }

  public String getTxtOnlineTorihikiCode() {
    return txtOnlineTorihikiCode;
  }

  public void setTxtOnlineTorihikiCode(String txtOnlineTorihikiCode) {
    this.txtOnlineTorihikiCode = txtOnlineTorihikiCode;
  }

  public String getTxtAtTorihikiCode() {
    return txtAtTorihikiCode;
  }

  public void setTxtAtTorihikiCode(String txtAtTorihikiCode) {
    this.txtAtTorihikiCode = txtAtTorihikiCode;
  }

  public String getTxtAtTorihikiCode02() {
    return txtAtTorihikiCode02;
  }

  public void setTxtAtTorihikiCode02(String txtAtTorihikiCode02) {
    this.txtAtTorihikiCode02 = txtAtTorihikiCode02;
  }

  public String getTxtAtTenCode() {
    return txtAtTenCode;
  }

  public void setTxtAtTenCode(String txtAtTenCode) {
    this.txtAtTenCode = txtAtTenCode;
  }

  public String getTxtCustomerCode() {
    return txtCustomerCode;
  }

  public void setTxtCustomerCode(String txtCustomerCode) {
    this.txtCustomerCode = txtCustomerCode;
  }

  public String getDdlDeliKb() {
    return ddlDeliKb;
  }

  public void setDdlDeliKb(String ddlDeliKb) {
    this.ddlDeliKb = ddlDeliKb;
  }

  public String getTxtShopCode() {
    return txtShopCode;
  }

  public void setTxtShopCode(String txtShopCode) {
    this.txtShopCode = txtShopCode;
  }

  public String getTxtStsCode() {
    return txtStsCode;
  }

  public void setTxtStsCode(String txtStsCode) {
    this.txtStsCode = txtStsCode;
  }

  public String getTxtOnlineCenterCode() {
    return txtOnlineCenterCode;
  }

  public void setTxtOnlineCenterCode(String txtOnlineCenterCode) {
    this.txtOnlineCenterCode = txtOnlineCenterCode;
  }

  public String getOlTorihikiCodeNone() {
    return olTorihikiCodeNone;
  }

  public void setOlTorihikiCodeNone(String olTorihikiCodeNone) {
    this.olTorihikiCodeNone = olTorihikiCodeNone;
  }

  public String getTenCodeNone() {
    return tenCodeNone;
  }

  public void setTenCodeNone(String tenCodeNone) {
    this.tenCodeNone = tenCodeNone;
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

  
  public String getPlantCode() {
    return plantCode;
  }

  
  public void setPlantCode(String plantCode) {
    this.plantCode = plantCode;
  }

}
