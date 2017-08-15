/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0105d01.java
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
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.ArrayList;

/**
 * The controller object.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormMst0105d01 {

  /** オンラインセンターコード */
  private String txtOnlineCenterCode;
  /** オンライン取引先コード */
  private String txtOnlineTorihikiCode;
  /** 相手取引先コード */
  private String txtAtTorihikiCode;
  /** オンラインセンター名称 */
  private String lblCustomerNameR01;
  /** 相手取引先名称 */
  private String lblCustomerNameR02;
  /** 取消データを対象とする */
  private boolean chkCancellationData;
  /** [画面_Hidden]業務日付 */
  private String businessDate;

  private String errMessage;

  private String messageType;

  private boolean errorControl;

  private String olTorihikiCodeNone;

  private String errorItemId;

  private ArrayList<OlCustConvMasterData> convMasterInfos;

  private String filePath;

  private String haitaDate;

  private String haitaTime;

  private String olCenterCode;

  private String olTorihikiCode;

  private String atTorihikiCode;

  /** The number of records to return */
  private int searchMax;

  /** 画面表示モード */
  private String screenMode;

  public String getOlCenterCode() {
    return olCenterCode;
  }

  public void setOlCenterCode(String olCenterCode) {
    this.olCenterCode = olCenterCode;
  }

  public String getOlTorihikiCode() {
    return olTorihikiCode;
  }

  public void setOlTorihikiCode(String olTorihikiCode) {
    this.olTorihikiCode = olTorihikiCode;
  }

  public String getAtTorihikiCode() {
    return atTorihikiCode;
  }

  public void setAtTorihikiCode(String atTorihikiCode) {
    this.atTorihikiCode = atTorihikiCode;
  }

  public ArrayList<OlCustConvMasterData> getConvMasterInfos() {
    return convMasterInfos;
  }

  public void setConvMasterInfos(
      ArrayList<OlCustConvMasterData> convMasterInfos) {
    this.convMasterInfos = convMasterInfos;
  }

  public String getScreenMode() {
    return screenMode;
  }

  public void setScreenMode(String screenMode) {
    this.screenMode = screenMode;
  }

  public String getErrorItemId() {
    return errorItemId;
  }

  public void setErrorItemId(String errorItemId) {
    this.errorItemId = errorItemId;
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

  public String getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  public String getTxtOnlineCenterCode() {
    return txtOnlineCenterCode;
  }

  public void setTxtOnlineCenterCode(String txtOnlineCenterCode) {
    this.txtOnlineCenterCode = txtOnlineCenterCode;
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

  public boolean isChkCancellationData() {
    return chkCancellationData;
  }

  public void setChkCancellationData(boolean chkCancellationData) {
    this.chkCancellationData = chkCancellationData;
  }

  public String getOlTorihikiCodeNone() {
    return olTorihikiCodeNone;
  }

  public void setOlTorihikiCodeNone(String olTorihikiCodeNone) {
    this.olTorihikiCodeNone = olTorihikiCodeNone;
  }

  public boolean isErrorControl() {
    return errorControl;
  }

  public void setErrorControl(boolean errorControl) {
    this.errorControl = errorControl;
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

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
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

  public int getSearchMax() {
    return searchMax;
  }

  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }
}
