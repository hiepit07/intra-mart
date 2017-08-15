/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0103d02.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)TramChu    新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * フォーム(jspの modelAttribute="FormMst0103d02" とリンク）
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0103d02 {

  // [画面_hidden]業務日付
  private Integer businessDate;
  private String frm1Office;
  private String frm1CustomerCode;
  private String frm1CustomerName;
  private String frm1StoreCode;
  private String frm1StoreName;
  private String frm1chkCancelData;
  private String sysAdminFlag;
  private String loginJigyouShoCode;
  private String loginJigyouShoName;
  private String haitaDate;
  private String haitaTime;
  private String deliveryCenterSelect;
  private String officeSelect;
  private String conditionSelect;
  private String thanksClassificationSelect;
  private String chkDestinationStoreDis;
  private String txtPostalCodeResult;
  private String txtPhoneNumberResult;
  private String txtFaxNumberResult;
  private String tenCodeNone;
  // Search condition item of
  private String txtCustomerCode;
  private String txtStoreCode;
  private String txtStoreName;
  private String txtStoreNameKana;
  private String txtStoreAbbreviation;
  private String txtPostalCode1;
  private String txtPostalCode2;
  private String txtAddress1;
  private String txtAddress2;
  private String txtPhoneNumber1;
  private String txtPhoneNumber2;
  private String txtPhoneNumber3;
  private String txtFaxNumber1;
  private String txtFaxNumber2;
  private String txtFaxNumber3;
  private String ddlOffice;
  private String ddlDeliveryCenter;
  private String ddlThanksClassification;
  private String txtFixMisejika;
  private String txtFixCenter;
  private String txtSaleMisejika;
  private String txtSaleCenter;
  private String ddlCondition;
  private String txtCollectionDestination;
  private String txtDate;
  private String txtStatusCode;
  private String mode;

  public String getDeliveryCenterSelect() {
    return deliveryCenterSelect;
  }

  public void setDeliveryCenterSelect(String deliveryCenterSelect) {
    this.deliveryCenterSelect = deliveryCenterSelect;
  }

  public String getOfficeSelect() {
    return officeSelect;
  }

  public void setOfficeSelect(String officeSelect) {
    this.officeSelect = officeSelect;
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

  public String getFrm1Office() {
    return frm1Office;
  }

  public void setFrm1Office(String frm1Office) {
    this.frm1Office = frm1Office;
  }

  public String getFrm1CustomerCode() {
    return frm1CustomerCode;
  }

  public void setFrm1CustomerCode(String frm1CustomerCode) {
    this.frm1CustomerCode = frm1CustomerCode;
  }

  public String getFrm1CustomerName() {
    return frm1CustomerName;
  }

  public void setFrm1CustomerName(String frm1CustomerName) {
    this.frm1CustomerName = frm1CustomerName;
  }

  public String getFrm1StoreCode() {
    return frm1StoreCode;
  }

  public void setFrm1StoreCode(String frm1StoreCode) {
    this.frm1StoreCode = frm1StoreCode;
  }

  public String getFrm1StoreName() {
    return frm1StoreName;
  }

  public void setFrm1StoreName(String frm1StoreName) {
    this.frm1StoreName = frm1StoreName;
  }

  public String getFrm1chkCancelData() {
    return frm1chkCancelData;
  }

  public void setFrm1chkCancelData(String frm1chkCancelData) {
    this.frm1chkCancelData = frm1chkCancelData;
  }

  private Boolean chkDestinationStore;
  // メッセージ
  private String txtMessage;

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

  public String getTxtStoreNameKana() {
    return txtStoreNameKana;
  }

  public void setTxtStoreNameKana(String txtStoreNameKana) {
    this.txtStoreNameKana = txtStoreNameKana;
  }

  public String getTxtStoreAbbreviation() {
    return txtStoreAbbreviation;
  }

  public void setTxtStoreAbbreviation(String txtStoreAbbreviation) {
    this.txtStoreAbbreviation = txtStoreAbbreviation;
  }

  public String getDdlDeliveryCenter() {
    return ddlDeliveryCenter;
  }

  public void setDdlDeliveryCenter(String ddlDeliveryCenter) {
    this.ddlDeliveryCenter = ddlDeliveryCenter;
  }

  public String getDdlThanksClassification() {
    return ddlThanksClassification;
  }

  public void setDdlThanksClassification(String ddlThanksClassification) {
    this.ddlThanksClassification = ddlThanksClassification;
  }

  public String getDdlCondition() {
    return ddlCondition;
  }

  public void setDdlCondition(String ddlCondition) {
    this.ddlCondition = ddlCondition;
  }

  public String getTxtPostalCode1() {
    return txtPostalCode1;
  }

  public void setTxtPostalCode1(String txtPostalCode1) {
    this.txtPostalCode1 = txtPostalCode1;
  }

  public String getTxtPostalCode2() {
    return txtPostalCode2;
  }

  public void setTxtPostalCode2(String txtPostalCode2) {
    this.txtPostalCode2 = txtPostalCode2;
  }

  public String getTxtAddress1() {
    return txtAddress1;
  }

  public void setTxtAddress1(String txtAddress1) {
    this.txtAddress1 = txtAddress1;
  }

  public String getTxtAddress2() {
    return txtAddress2;
  }

  public void setTxtAddress2(String txtAddress2) {
    this.txtAddress2 = txtAddress2;
  }

  public String getTxtPhoneNumber1() {
    return txtPhoneNumber1;
  }

  public void setTxtPhoneNumber1(String txtPhoneNumber1) {
    this.txtPhoneNumber1 = txtPhoneNumber1;
  }

  public String getTxtPhoneNumber2() {
    return txtPhoneNumber2;
  }

  public void setTxtPhoneNumber2(String txtPhoneNumber2) {
    this.txtPhoneNumber2 = txtPhoneNumber2;
  }

  public String getTxtPhoneNumber3() {
    return txtPhoneNumber3;
  }

  public void setTxtPhoneNumber3(String txtPhoneNumber3) {
    this.txtPhoneNumber3 = txtPhoneNumber3;
  }

  public String getTxtFaxNumber1() {
    return txtFaxNumber1;
  }

  public void setTxtFaxNumber1(String txtFaxNumber1) {
    this.txtFaxNumber1 = txtFaxNumber1;
  }

  public String getTxtFaxNumber2() {
    return txtFaxNumber2;
  }

  public void setTxtFaxNumber2(String txtFaxNumber2) {
    this.txtFaxNumber2 = txtFaxNumber2;
  }

  public String getTxtFaxNumber3() {
    return txtFaxNumber3;
  }

  public void setTxtFaxNumber3(String txtFaxNumber3) {
    this.txtFaxNumber3 = txtFaxNumber3;
  }

  public String getTxtFixMisejika() {
    return txtFixMisejika;
  }

  public void setTxtFixMisejika(String txtFixMisejika) {
    this.txtFixMisejika = txtFixMisejika;
  }

  public String getTxtFixCenter() {
    return txtFixCenter;
  }

  public void setTxtFixCenter(String txtFixCenter) {
    this.txtFixCenter = txtFixCenter;
  }

  public String getTxtSaleMisejika() {
    return txtSaleMisejika;
  }

  public void setTxtSaleMisejika(String txtSaleMisejika) {
    this.txtSaleMisejika = txtSaleMisejika;
  }

  public String getTxtSaleCenter() {
    return txtSaleCenter;
  }

  public void setTxtSaleCenter(String txtSaleCenter) {
    this.txtSaleCenter = txtSaleCenter;
  }

  public String getTxtCollectionDestination() {
    return txtCollectionDestination;
  }

  public void setTxtCollectionDestination(String txtCollectionDestination) {
    this.txtCollectionDestination = txtCollectionDestination;
  }

  public String getTxtDate() {
    return txtDate;
  }

  public void setTxtDate(String txtDate) {
    this.txtDate = txtDate;
  }

  public String getTxtStatusCode() {
    return txtStatusCode;
  }

  public void setTxtStatusCode(String txtStatusCode) {
    this.txtStatusCode = txtStatusCode;
  }

  public Boolean getChkDestinationStore() {
    return chkDestinationStore;
  }

  public void setChkDestinationStore(Boolean chkDestinationStore) {
    this.chkDestinationStore = chkDestinationStore;
  }

  public String getTxtMessage() {
    return txtMessage;
  }

  public void setTxtMessage(String txtMessage) {
    this.txtMessage = txtMessage;
  }

  public String getDdlOffice() {
    return ddlOffice;
  }

  public void setDdlOffice(String ddlOffice) {
    this.ddlOffice = ddlOffice;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public Integer getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
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

  public String getLoginJigyouShoName() {
    return loginJigyouShoName;
  }

  public void setLoginJigyouShoName(String loginJigyouShoName) {
    this.loginJigyouShoName = loginJigyouShoName;
  }

  public String getTxtPostalCodeResult() {
    return txtPostalCodeResult;
  }

  public void setTxtPostalCodeResult(String txtPostalCodeResult) {
    this.txtPostalCodeResult = txtPostalCodeResult;
  }

  public String getTxtPhoneNumberResult() {
    return txtPhoneNumberResult;
  }

  public void setTxtPhoneNumberResult(String txtPhoneNumberResult) {
    this.txtPhoneNumberResult = txtPhoneNumberResult;
  }

  public String getTxtFaxNumberResult() {
    return txtFaxNumberResult;
  }

  public void setTxtFaxNumberResult(String txtFaxNumberResult) {
    this.txtFaxNumberResult = txtFaxNumberResult;
  }

  public String getConditionSelect() {
    return conditionSelect;
  }

  public void setConditionSelect(String conditionSelect) {
    this.conditionSelect = conditionSelect;
  }

  public String getThanksClassificationSelect() {
    return thanksClassificationSelect;
  }

  public void setThanksClassificationSelect(
      String thanksClassificationSelect) {
    this.thanksClassificationSelect = thanksClassificationSelect;
  }

  public String getTenCodeNone() {
    return tenCodeNone;
  }

  public void setTenCodeNone(String tenCodeNone) {
    this.tenCodeNone = tenCodeNone;
  }

  public String getChkDestinationStoreDis() {
    return chkDestinationStoreDis;
  }

  public void setChkDestinationStoreDis(String chkDestinationStoreDis) {
    this.chkDestinationStoreDis = chkDestinationStoreDis;
  }
}