/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:FormUri0105d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/12/9
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

/**
 * フォーム(jspの modelAttribute="FormUri0105d01" とリンク）
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormUri0105d01 {

  // [画面_hidden]
  private Integer businessDate;
  private String lblCustNmRHidden;
  private String lblShopNmRHidden;  
  private String sysAdminFlag;
  private String loginJigyouShoCode;
  private String errorControl;
  private String loginBussinessDate;

  // 検索条件の項目
  // 事業所
  private String ddlOyaShozoku;
  
  //データ受信日
  private String txtJuDateFrom;
  private String txtJuDateTo;
  
  //得意先コード
  private String txtCustCode;  
  
  //店舗コード
  private String txtShopCode;
  
  //伝票No
  private String txtDenCode;
  
  //処理済みデータを対象とする
  private boolean chkSyori;
  
  //受領データ 
  private boolean chkReceiptData;
  
  //返品データ  
  private boolean chkReturnedData;
  
  //欠品データ  
  private boolean chkLackOfData;
  
  //修正データ   
  private boolean chkModifyData;

  
  public Integer getBusinessDate() {
    return businessDate;
  }

  
  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

  
  public String getLblCustNmRHidden() {
    return lblCustNmRHidden;
  }

  
  public void setLblCustNmRHidden(String lblCustNmRHidden) {
    this.lblCustNmRHidden = lblCustNmRHidden;
  }

  
  public String getLblShopNmRHidden() {
    return lblShopNmRHidden;
  }

  
  public void setLblShopNmRHidden(String lblShopNmRHidden) {
    this.lblShopNmRHidden = lblShopNmRHidden;
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

  
  public String getErrorControl() {
    return errorControl;
  }

  
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }

  
  public String getLoginBussinessDate() {
    return loginBussinessDate;
  }

  
  public void setLoginBussinessDate(String loginBussinessDate) {
    this.loginBussinessDate = loginBussinessDate;
  }

  
  public String getDdlOyaShozoku() {
    return ddlOyaShozoku;
  }

  
  public void setDdlOyaShozoku(String ddlOyaShozoku) {
    this.ddlOyaShozoku = ddlOyaShozoku;
  }

  
  public String getTxtJuDateFrom() {
    return txtJuDateFrom;
  }

  
  public void setTxtJuDateFrom(String txtJuDateFrom) {
    this.txtJuDateFrom = txtJuDateFrom;
  }

  
  public String getTxtJuDateTo() {
    return txtJuDateTo;
  }

  
  public void setTxtJuDateTo(String txtJuDateTo) {
    this.txtJuDateTo = txtJuDateTo;
  }

  
  public String getTxtCustCode() {
    return txtCustCode;
  }

  
  public void setTxtCustCode(String txtCustCode) {
    this.txtCustCode = txtCustCode;
  }

  
  public String getTxtShopCode() {
    return txtShopCode;
  }

  
  public void setTxtShopCode(String txtShopCode) {
    this.txtShopCode = txtShopCode;
  }

  
  public String getTxtDenCode() {
    return txtDenCode;
  }

  
  public void setTxtDenCode(String txtDenCode) {
    this.txtDenCode = txtDenCode;
  }

  
  public boolean isChkSyori() {
    return chkSyori;
  }

  
  public void setChkSyori(boolean chkSyori) {
    this.chkSyori = chkSyori;
  }

  
  public boolean isChkReceiptData() {
    return chkReceiptData;
  }

  
  public void setChkReceiptData(boolean chkReceiptData) {
    this.chkReceiptData = chkReceiptData;
  }

  
  public boolean isChkReturnedData() {
    return chkReturnedData;
  }

  
  public void setChkReturnedData(boolean chkReturnedData) {
    this.chkReturnedData = chkReturnedData;
  }

  
  public boolean isChkLackOfData() {
    return chkLackOfData;
  }

  
  public void setChkLackOfData(boolean chkLackOfData) {
    this.chkLackOfData = chkLackOfData;
  }

  
  public boolean isChkModifyData() {
    return chkModifyData;
  }

  
  public void setChkModifyData(boolean chkModifyData) {
    this.chkModifyData = chkModifyData;
  }

  
 
}