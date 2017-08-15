/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0108d02.java
 * 
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/11/16
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)グエン リユオン ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * コントローラクラス
 * 
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0108d02 {


  // 表示モード
  private String viewMode;
  // システム管理者フラグ
  private String sysAdminFlag;
  // ログイン事業所コード
  private String loginJigyouShoCode;
  // 排他日付
  private String haitaDate;
  // 排他時間
  private String haitaTime;
  private String cstCode;
  private String itemCode;
  private String shopCode;
  private String salesKb;
  private String salesName;
  private String endTime;
  private String systemAdministratorFlag;
  private String jgyCd;
  private String jgyMei;
  private String shozokuClassList;
  private String masutaKubunClassList;
  private String jigyouShoCode;
  private String custNmR;
  private String hinRyaKu;
  private String shopNmR;
  private String salesNm;
  private String validFrom;
  private String validTo;
  private String custDeliTanka;
  private String custSellTanka;
  private String custBildTanka;
  private String bunruiCode;
  private String stsCode;
  private String insUserId;
  private String insPgId;
  private String insYmd;
  private String insTime;
  private String updUserId;
  private String updPgId;
  private String updYmd;
  private String updTime;
  private String checkUpdYmd;
  private String errorControll;
  private int flag;
  private int itemPriceInfo;
  private String bussinessDate;
  private String officeCode;
  private int checkType;
  private String relationMasterErrorFlag;
  private String strSelectedCustCode;
  private String strSelectedItemCode;
  private String strSelectedShopCode;
  private String strSelectedSaleType;
  private String strSelectedEndTime;
  private boolean masterCheckErrorFlag;
  private int searchMax;
  private String errMessage;
  private boolean masterExistRecordEditFlag;
  private int success;
  private int pass;

  private String searchCstCode;
  private String searchItemCode;
  private String searchShopCode;
  private String searchValidFrom;
  private String searchValidTo;
  private String searchBunruiCode;
  private String searchMasutaKubunClassList;
  private String searchJigyouShoCode;
  private boolean searchcheckDay;
  private boolean searchcheckCancleData;
  private String searchShozokuClassList;
  
  private String tenCodeNone;

  public String getViewMode() {
    return viewMode;
  }
  
  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
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
  
  public String getCstCode() {
    return cstCode;
  }
  
  public void setCstCode(String cstCode) {
    this.cstCode = cstCode;
  }
  
  public String getItemCode() {
    return itemCode;
  }
  
  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }
  
  public String getShopCode() {
    return shopCode;
  }
  
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
  
  public String getSalesKb() {
    return salesKb;
  }
  
  public void setSalesKb(String salesKb) {
    this.salesKb = salesKb;
  }
  
  public String getSalesName() {
    return salesName;
  }
  
  public void setSalesName(String salesName) {
    this.salesName = salesName;
  }
  
  public String getEndTime() {
    return endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public String getSystemAdministratorFlag() {
    return systemAdministratorFlag;
  }
  
  public void setSystemAdministratorFlag(String systemAdministratorFlag) {
    this.systemAdministratorFlag = systemAdministratorFlag;
  }
  
  public String getJgyCd() {
    return jgyCd;
  }
  
  public void setJgyCd(String jgyCd) {
    this.jgyCd = jgyCd;
  }
  
  public String getJgyMei() {
    return jgyMei;
  }
  
  public void setJgyMei(String jgyMei) {
    this.jgyMei = jgyMei;
  }
  
  public String getShozokuClassList() {
    return shozokuClassList;
  }
  
  public void setShozokuClassList(String shozokuClassList) {
    this.shozokuClassList = shozokuClassList;
  }
  
  public String getMasutaKubunClassList() {
    return masutaKubunClassList;
  }
  
  public void setMasutaKubunClassList(String masutaKubunClassList) {
    this.masutaKubunClassList = masutaKubunClassList;
  }
  
  public String getJigyouShoCode() {
    return jigyouShoCode;
  }
  
  public void setJigyouShoCode(String jigyouShoCode) {
    this.jigyouShoCode = jigyouShoCode;
  }
  
  public String getCustNmR() {
    return custNmR;
  }
  
  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }
  
  public String getHinRyaKu() {
    return hinRyaKu;
  }
  
  public void setHinRyaKu(String hinRyaKu) {
    this.hinRyaKu = hinRyaKu;
  }
  
  public String getShopNmR() {
    return shopNmR;
  }
  
  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }
  
  public String getSalesNm() {
    return salesNm;
  }
  
  public void setSalesNm(String salesNm) {
    this.salesNm = salesNm;
  }
  
  public String getValidFrom() {
    return validFrom;
  }
  
  public void setValidFrom(String validFrom) {
    this.validFrom = validFrom;
  }
  
  public String getValidTo() {
    return validTo;
  }
  
  public void setValidTo(String validTo) {
    this.validTo = validTo;
  }
  
  public String getCustDeliTanka() {
    return custDeliTanka;
  }
  
  public void setCustDeliTanka(String custDeliTanka) {
    this.custDeliTanka = custDeliTanka;
  }
  
  public String getCustSellTanka() {
    return custSellTanka;
  }
  
  public void setCustSellTanka(String custSellTanka) {
    this.custSellTanka = custSellTanka;
  }
  
  public String getCustBildTanka() {
    return custBildTanka;
  }
  
  public void setCustBildTanka(String custBildTanka) {
    this.custBildTanka = custBildTanka;
  }
  
  public String getBunruiCode() {
    return bunruiCode;
  }
  
  public void setBunruiCode(String bunruiCode) {
    this.bunruiCode = bunruiCode;
  }
  
  public String getStsCode() {
    return stsCode;
  }
  
  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
  }
  
  public String getInsUserId() {
    return insUserId;
  }
  
  public void setInsUserId(String insUserId) {
    this.insUserId = insUserId;
  }
  
  public String getInsPgId() {
    return insPgId;
  }
  
  public void setInsPgId(String insPgId) {
    this.insPgId = insPgId;
  }
  
  public String getInsYmd() {
    return insYmd;
  }
  
  public void setInsYmd(String insYmd) {
    this.insYmd = insYmd;
  }
  
  public String getInsTime() {
    return insTime;
  }
  
  public void setInsTime(String insTime) {
    this.insTime = insTime;
  }
  
  public String getUpdUserId() {
    return updUserId;
  }
  
  public void setUpdUserId(String updUserId) {
    this.updUserId = updUserId;
  }
  
  public String getUpdPgId() {
    return updPgId;
  }
  
  public void setUpdPgId(String updPgId) {
    this.updPgId = updPgId;
  }
  
  public String getUpdYmd() {
    return updYmd;
  }
  
  public void setUpdYmd(String updYmd) {
    this.updYmd = updYmd;
  }
  
  public String getUpdTime() {
    return updTime;
  }
  
  public void setUpdTime(String updTime) {
    this.updTime = updTime;
  }
  
  public String getCheckUpdYmd() {
    return checkUpdYmd;
  }
  
  public void setCheckUpdYmd(String checkUpdYmd) {
    this.checkUpdYmd = checkUpdYmd;
  }
  
  public String getErrorControll() {
    return errorControll;
  }
  
  public void setErrorControll(String errorControll) {
    this.errorControll = errorControll;
  }
  
  public int getFlag() {
    return flag;
  }
  
  public void setFlag(int flag) {
    this.flag = flag;
  }
  
  public int getItemPriceInfo() {
    return itemPriceInfo;
  }
  
  public void setItemPriceInfo(int itemPriceInfo) {
    this.itemPriceInfo = itemPriceInfo;
  }
  
  public String getBussinessDate() {
    return bussinessDate;
  }
  
  public void setBussinessDate(String bussinessDate) {
    this.bussinessDate = bussinessDate;
  }
  
  public String getOfficeCode() {
    return officeCode;
  }
  
  public void setOfficeCode(String officeCode) {
    this.officeCode = officeCode;
  }
  
  public int getCheckType() {
    return checkType;
  }
  
  public void setCheckType(int checkType) {
    this.checkType = checkType;
  }
  
  public String getRelationMasterErrorFlag() {
    return relationMasterErrorFlag;
  }
  
  public void setRelationMasterErrorFlag(String relationMasterErrorFlag) {
    this.relationMasterErrorFlag = relationMasterErrorFlag;
  }
  
  public String getStrSelectedCustCode() {
    return strSelectedCustCode;
  }
  
  public void setStrSelectedCustCode(String strSelectedCustCode) {
    this.strSelectedCustCode = strSelectedCustCode;
  }
  
  public String getStrSelectedItemCode() {
    return strSelectedItemCode;
  }
  
  public void setStrSelectedItemCode(String strSelectedItemCode) {
    this.strSelectedItemCode = strSelectedItemCode;
  }
  
  public String getStrSelectedShopCode() {
    return strSelectedShopCode;
  }
  
  public void setStrSelectedShopCode(String strSelectedShopCode) {
    this.strSelectedShopCode = strSelectedShopCode;
  }
  
  public String getStrSelectedSaleType() {
    return strSelectedSaleType;
  }
  
  public void setStrSelectedSaleType(String strSelectedSaleType) {
    this.strSelectedSaleType = strSelectedSaleType;
  }
  
  public String getStrSelectedEndTime() {
    return strSelectedEndTime;
  }
  
  public void setStrSelectedEndTime(String strSelectedEndTime) {
    this.strSelectedEndTime = strSelectedEndTime;
  }
  
  public boolean isMasterCheckErrorFlag() {
    return masterCheckErrorFlag;
  }
  
  public void setMasterCheckErrorFlag(boolean masterCheckErrorFlag) {
    this.masterCheckErrorFlag = masterCheckErrorFlag;
  }
  
  public int getSearchMax() {
    return searchMax;
  }
  
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }
  
  public String getErrMessage() {
    return errMessage;
  }
  
  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }
  
  public boolean isMasterExistRecordEditFlag() {
    return masterExistRecordEditFlag;
  }
  
  public void setMasterExistRecordEditFlag(boolean masterExistRecordEditFlag) {
    this.masterExistRecordEditFlag = masterExistRecordEditFlag;
  }
  
  public int getSuccess() {
    return success;
  }
  
  public void setSuccess(int success) {
    this.success = success;
  }
  
  public int getPass() {
    return pass;
  }
  
  public void setPass(int pass) {
    this.pass = pass;
  }
  
  public String getSearchCstCode() {
    return searchCstCode;
  }
  
  public void setSearchCstCode(String searchCstCode) {
    this.searchCstCode = searchCstCode;
  }
  
  public String getSearchItemCode() {
    return searchItemCode;
  }
  
  public void setSearchItemCode(String searchItemCode) {
    this.searchItemCode = searchItemCode;
  }
  
  public String getSearchShopCode() {
    return searchShopCode;
  }
  
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }
  
  public String getSearchValidFrom() {
    return searchValidFrom;
  }
  
  public void setSearchValidFrom(String searchValidFrom) {
    this.searchValidFrom = searchValidFrom;
  }
  
  public String getSearchValidTo() {
    return searchValidTo;
  }
  
  public void setSearchValidTo(String searchValidTo) {
    this.searchValidTo = searchValidTo;
  }
  
  public String getSearchBunruiCode() {
    return searchBunruiCode;
  }
  
  public void setSearchBunruiCode(String searchBunruiCode) {
    this.searchBunruiCode = searchBunruiCode;
  }
  
  public String getSearchMasutaKubunClassList() {
    return searchMasutaKubunClassList;
  }
  
  public void setSearchMasutaKubunClassList(String searchMasutaKubunClassList) {
    this.searchMasutaKubunClassList = searchMasutaKubunClassList;
  }
  
  public String getSearchJigyouShoCode() {
    return searchJigyouShoCode;
  }
  
  public void setSearchJigyouShoCode(String searchJigyouShoCode) {
    this.searchJigyouShoCode = searchJigyouShoCode;
  }
  
  public boolean isSearchcheckDay() {
    return searchcheckDay;
  }
  
  public void setSearchcheckDay(boolean searchcheckDay) {
    this.searchcheckDay = searchcheckDay;
  }
  
  public boolean isSearchcheckCancleData() {
    return searchcheckCancleData;
  }
  
  public void setSearchcheckCancleData(boolean searchcheckCancleData) {
    this.searchcheckCancleData = searchcheckCancleData;
  }
  
  public String getSearchShozokuClassList() {
    return searchShozokuClassList;
  }
  
  public void setSearchShozokuClassList(String searchShozokuClassList) {
    this.searchShozokuClassList = searchShozokuClassList;
  }

  
  public String getTenCodeNone() {
    return tenCodeNone;
  }

  
  public void setTenCodeNone(String tenCodeNone) {
    this.tenCodeNone = tenCodeNone;
  }
  
  
}
