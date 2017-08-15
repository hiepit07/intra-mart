/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.get
 * ファイル名:FormGet0101d00.java
 * 
 * 作成者:HiepTruong
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * フォーム(jspの modelAttribute="FormGet0101d00" とリンク）
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormGet0101d00 {

  // ヘッダ部
  // 事業所
  private String ddlJigyouSho;
  // 得意先コード
  private String txtCustomerCode;
  // 得意先名称
  private String txtCustomerName;
  // チェーンコード
  private String txtChainCode;
  // チェーン枝番
  private String txtChainEda;
  // チェーン名称
  private String txtChainName;
  // 営業担当者コード
  private String txtEigyouTantoushaCode;
  // 営業担当者名称
  private String txtEigyouTantoushaName;
  // 事務担当者コード
  private String txtJimuTantoushaCode;
  // 事務担当者名称
  private String txtJimuTantoushaName;
  // 得意先種別
  private String ddlCustomerType;
  // 内税顧客区分
  private String ddlUchiZeiKoKyakuKubun;
  // 取消データを対象とする
  private boolean chkCancelData;
  // 得意先を対象とする
  private boolean chkCustomer;
  // 請求先を対象とする
  private boolean chkBilling;
  // 得意先かつ請求先を対象とする
  private boolean chkCustomerBilling;

  // [画面_hidden]業務日付
  // 業務日付
  private Integer businessDate;
  // 画面表示モード
  private String viewMode;
  // 得意先コード
  private String selectUserCode;
  // チェーンコード
  private String selectChainCode;
  // チェーン枝番
  private String selectChainEda;
  // システム管理者フラグ
  private String sysAdminFlag;
  // ログイン事業所コード
  private String loginJigyouShoCode;
  // 排他日付
  private String haitaDate;
  // 排他時間
  private String haitaTime;
  // 最大検索件数
  private int searchMax;

  private String lblConfirmAccMonth;
  // ログイン事業所コード
  private String lblProcStartTime;
  // 排他日付
  private String lblProcEndTime;
  // 排他時間
  private String lblCurrAccMonth;
  
  public String getLblConfirmAccMonth() {
    return lblConfirmAccMonth;
}

public void setLblConfirmAccMonth(String lblConfirmAccMonth) {
    this.lblConfirmAccMonth = lblConfirmAccMonth;
}

public String getLblProcStartTime() {
    return lblProcStartTime;
}

public void setLblProcStartTime(String lblProcStartTime) {
    this.lblProcStartTime = lblProcStartTime;
}

public String getLblProcEndTime() {
    return lblProcEndTime;
}

public void setLblProcEndTime(String lblProcEndTime) {
    this.lblProcEndTime = lblProcEndTime;
}

public String getLblCurrAccMonth() {
    return lblCurrAccMonth;
}

public void setLblCurrAccMonth(String lblCurrAccMonth) {
    this.lblCurrAccMonth = lblCurrAccMonth;
}
  public String getDdlJigyouSho() {
    return ddlJigyouSho;
  }

  public void setDdlJigyouSho(String ddlJigyouSho) {
    this.ddlJigyouSho = ddlJigyouSho;
  }

  public String getTxtCustomerCode() {
    return txtCustomerCode;
  }

  public void setTxtCustomerCode(String txtCustomerCode) {
    this.txtCustomerCode = txtCustomerCode;
  }

  public String getTxtCustomerName() {
    return txtCustomerName;
  }

  public void setTxtCustomerName(String txtCustomerName) {
    this.txtCustomerName = txtCustomerName;
  }

  public String getTxtChainCode() {
    return txtChainCode;
  }

  public void setTxtChainCode(String txtChainCode) {
    this.txtChainCode = txtChainCode;
  }

  public String getTxtChainEda() {
    return txtChainEda;
  }

  public void setTxtChainEda(String txtChainEda) {
    this.txtChainEda = txtChainEda;
  }

  public String getTxtChainName() {
    return txtChainName;
  }

  public void setTxtChainName(String txtChainName) {
    this.txtChainName = txtChainName;
  }

  public String getTxtEigyouTantoushaCode() {
    return txtEigyouTantoushaCode;
  }

  public void setTxtEigyouTantoushaCode(String txtEigyouTantoushaCode) {
    this.txtEigyouTantoushaCode = txtEigyouTantoushaCode;
  }

  public String getTxtEigyouTantoushaName() {
    return txtEigyouTantoushaName;
  }

  public void setTxtEigyouTantoushaName(String txtEigyouTantoushaName) {
    this.txtEigyouTantoushaName = txtEigyouTantoushaName;
  }

  public String getTxtJimuTantoushaCode() {
    return txtJimuTantoushaCode;
  }

  public void setTxtJimuTantoushaCode(String txtJimuTantoushaCode) {
    this.txtJimuTantoushaCode = txtJimuTantoushaCode;
  }

  public String getTxtJimuTantoushaName() {
    return txtJimuTantoushaName;
  }

  public void setTxtJimuTantoushaName(String txtJimuTantoushaName) {
    this.txtJimuTantoushaName = txtJimuTantoushaName;
  }

  public String getDdlCustomerType() {
    return ddlCustomerType;
  }

  public void setDdlCustomerType(String ddlCustomerType) {
    this.ddlCustomerType = ddlCustomerType;
  }

  public String getDdlUchiZeiKoKyakuKubun() {
    return ddlUchiZeiKoKyakuKubun;
  }

  public void setDdlUchiZeiKoKyakuKubun(String ddlUchiZeiKoKyakuKubun) {
    this.ddlUchiZeiKoKyakuKubun = ddlUchiZeiKoKyakuKubun;
  }

  public boolean isChkCancelData() {
    return chkCancelData;
  }

  public void setChkCancelData(boolean chkCancelData) {
    this.chkCancelData = chkCancelData;
  }

  public boolean isChkCustomer() {
    return chkCustomer;
  }

  public void setChkCustomer(boolean chkCustomer) {
    this.chkCustomer = chkCustomer;
  }

  public boolean isChkBilling() {
    return chkBilling;
  }

  public void setChkBilling(boolean chkBilling) {
    this.chkBilling = chkBilling;
  }

  public boolean isChkCustomerBilling() {
    return chkCustomerBilling;
  }

  public void setChkCustomerBilling(boolean chkCustomerBilling) {
    this.chkCustomerBilling = chkCustomerBilling;
  }

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

  public String getSelectUserCode() {
    return selectUserCode;
  }

  public void setSelectUserCode(String selectUserCode) {
    this.selectUserCode = selectUserCode;
  }

  public String getSelectChainCode() {
    return selectChainCode;
  }

  public void setSelectChainCode(String selectChainCode) {
    this.selectChainCode = selectChainCode;
  }

  public String getSelectChainEda() {
    return selectChainEda;
  }

  public void setSelectChainEda(String selectChainEda) {
    this.selectChainEda = selectChainEda;
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