/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0108d01.java
 * 
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/11/01
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

import java.util.ArrayList;

/**
 * コントローラクラス
 * 
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */

public class FormMst0108d01 {

  private String systemAdministratorFlag;
  private String jgyCd;
  private String jgyMei;
  private String shozokuClassList;
  private String masutaKubunClassList;
  private String jigyouShoCode;
  private String cstCode;
  private String custNmR;
  private String itemCode;
  private String hinRyaKu;
  private String shopCode;
  private String shopNmR;
  private String salesKb;
  private String salesNm;
  private String validFrom;
  private String validTo;
  private float custDeliTanka;
  private int custSellTanka;
  private float custBildTanka;
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
  private boolean checkUpdYmd;
  private boolean checkCancleData;
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
  private String errorMessages;
  private String errMessage;
  private String tenCodeNone;
  // 画面表示モード
  private String viewMode;
  // 排他日付
  private String haitaDate;
  // 排他時間
  private String haitaTime;
  private int searchMax;
  private String searchShozokuClassList;
  private String jisonData;
  ArrayList<Mst0108d01Model> mst0108d01Models;

  /**
   * getSystemAdministratorFlag.
   * 
   * @return systemAdministratorFlag
   */
  public String getSystemAdministratorFlag() {
    return systemAdministratorFlag;
  }

  /**
   * setSystemAdministratorFlag.
   * 
   * @param systemAdministratorFlag systemAdministratorFlag
   */
  public void setSystemAdministratorFlag(String systemAdministratorFlag) {
    this.systemAdministratorFlag = systemAdministratorFlag;
  }

  /**
   * getJgyCd.
   * 
   * @return jgyCd
   */
  public String getJgyCd() {
    return jgyCd;
  }

  /**
   * setJgyCd.
   * 
   * @param jgyCd jgyCd
   */
  public void setJgyCd(String jgyCd) {
    this.jgyCd = jgyCd;
  }

  /**
   * getJgyMei.
   * 
   * @return jgyMei
   */
  public String getJgyMei() {
    return jgyMei;
  }

  /**
   * setJgyMei.
   * 
   * @param jgyMei jgyMei
   */
  public void setJgyMei(String jgyMei) {
    this.jgyMei = jgyMei;
  }

  /**
   * getJigyouShoCode.
   * 
   * @return JigyouShoCode
   */
  public String getJigyouShoCode() {
    return jigyouShoCode;
  }

  /**
   * setJigyouShoCode.
   * 
   * @param jigyouShoCode jigyouShoCode
   */
  public void setJigyouShoCode(String jigyouShoCode) {
    this.jigyouShoCode = jigyouShoCode;
  }

  /**
   * getCstCode.
   * 
   * @return cstCode
   */
  public String getCstCode() {
    return cstCode;
  }

  /**
   * setCstCode.
   * 
   * @param cstCode cstCode
   */
  public void setCstCode(String cstCode) {
    this.cstCode = cstCode;
  }

  /**
   * getCustNmR.
   * 
   * @return custNmR
   */
  public String getCustNmR() {
    return custNmR;
  }

  /**
   * setCustNmR.
   * 
   * @param custNmR custNmR
   */
  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }

  /**
   * getItemCode.
   * 
   * @return itemCode
   */
  public String getItemCode() {
    return itemCode;
  }

  /**
   * setItemCode.
   * 
   * @param itemCode itemCode
   */
  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  /**
   * getHinRyaKu.
   * 
   * @return hinRyaKu
   */
  public String getHinRyaKu() {
    return hinRyaKu;
  }

  /**
   * setHinRyaKu.
   * 
   * @param hinRyaKu hinRyaKu
   */
  public void setHinRyaKu(String hinRyaKu) {
    this.hinRyaKu = hinRyaKu;
  }

  /**
   * getShopCode.
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * setShopCode.
   * 
   * @param shopCode shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * getShopNmR.
   * 
   * @return shopNmR
   */
  public String getShopNmR() {
    return shopNmR;
  }

  /**
   * setShopNmR.
   * 
   * @param shopNmR shopNmR
   */
  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }

  /**
   * getSalesKb.
   * 
   * @return salesKb
   */
  public String getSalesKb() {
    return salesKb;
  }

  /**
   * setSalesKb.
   * 
   * @param salesKb salesKb
   */
  public void setSalesKb(String salesKb) {
    this.salesKb = salesKb;
  }

  /**
   * getSalesNm.
   * 
   * @return salesNm
   */
  public String getSalesNm() {
    return salesNm;
  }

  /**
   * setSalesNm.
   * 
   * @param salesNm salesNm
   */
  public void setSalesNm(String salesNm) {
    this.salesNm = salesNm;
  }

  /**
   * getValidFrom.
   * 
   * @return validFrom
   */
  public String getValidFrom() {
    return validFrom;
  }

  /**
   * setValidFrom.
   * 
   * @param validFrom validFrom
   */
  public void setValidFrom(String validFrom) {
    this.validFrom = validFrom;
  }

  /**
   * getValidTo.
   * 
   * @return validTo
   */
  public String getValidTo() {
    return validTo;
  }

  /**
   * setValidTo.
   * 
   * @param validTo validTo
   */
  public void setValidTo(String validTo) {
    this.validTo = validTo;
  }

  /**
   * getCustDeliTanka.
   * 
   * @return custDeliTanka
   */
  public float getCustDeliTanka() {
    return custDeliTanka;
  }

  /**
   * setCustDeliTanka.
   * 
   * @param custDeliTanka custDeliTanka
   */
  public void setCustDeliTanka(float custDeliTanka) {
    this.custDeliTanka = custDeliTanka;
  }

  /**
   * getCustSellTanka.
   * 
   * @return custSellTanka
   */
  public int getCustSellTanka() {
    return custSellTanka;
  }

  /**
   * setCustSellTanka.
   * 
   * @param custSellTanka custSellTanka
   */
  public void setCustSellTanka(int custSellTanka) {
    this.custSellTanka = custSellTanka;
  }

  /**
   * getCustBildTanka.
   * 
   * @return custBildTanka
   */
  public float getCustBildTanka() {
    return custBildTanka;
  }

  /**
   * setCustBildTanka.
   * 
   * @param custBildTanka custBildTanka
   */
  public void setCustBildTanka(float custBildTanka) {
    this.custBildTanka = custBildTanka;
  }

  /**
   * getBunruiCode.
   * 
   * @return bunruiCode
   */
  public String getBunruiCode() {
    return bunruiCode;
  }

  /**
   * setBunruiCode.
   * 
   * @param bunruiCode bunruiCode
   */
  public void setBunruiCode(String bunruiCode) {
    this.bunruiCode = bunruiCode;
  }

  /**
   * getStsCode.
   * 
   * @return stsCode
   */
  public String getStsCode() {
    return stsCode;
  }

  /**
   * setStsCode.
   * 
   * @param stsCode stsCode
   */
  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
  }

  /**
   * getInsUserId.
   * 
   * @return insUserId
   */
  public String getInsUserId() {
    return insUserId;
  }

  /**
   * setInsUserId.
   * 
   * @param insUserId insUserId
   */
  public void setInsUserId(String insUserId) {
    this.insUserId = insUserId;
  }

  /**
   * getInsPgId.
   * 
   * @return insPgId
   */
  public String getInsPgId() {
    return insPgId;
  }

  /**
   * setInsPgId.
   * 
   * @param insPgId insPgId
   */
  public void setInsPgId(String insPgId) {
    this.insPgId = insPgId;
  }

  /**
   * getInsYmd.
   * 
   * @return insYmd
   */
  public String getInsYmd() {
    return insYmd;
  }

  /**
   * setInsYmd.
   * 
   * @param insYmd insYmd
   */
  public void setInsYmd(String insYmd) {
    this.insYmd = insYmd;
  }

  /**
   * getInsTime.
   * 
   * @return insTime
   */
  public String getInsTime() {
    return insTime;
  }

  /**
   * setInsTime.
   * 
   * @param insTime insTime
   */
  public void setInsTime(String insTime) {
    this.insTime = insTime;
  }

  /**
   * getUpdUserId.
   * 
   * @return updUserId
   */
  public String getUpdUserId() {
    return updUserId;
  }

  /**
   * setUpdUserId.
   * 
   * @param updUserId updUserId
   */
  public void setUpdUserId(String updUserId) {
    this.updUserId = updUserId;
  }

  /**
   * getUpdPgId.
   * 
   * @return updPgId
   */
  public String getUpdPgId() {
    return updPgId;
  }

  /**
   * setUpdPgId.
   * 
   * @param updPgId updPgId
   */
  public void setUpdPgId(String updPgId) {
    this.updPgId = updPgId;
  }

  /**
   * getUpdYmd.
   * 
   * @return updYmd
   */
  public String getUpdYmd() {
    return updYmd;
  }

  /**
   * setUpdYmd.
   * 
   * @param updYmd updYmd
   */
  public void setUpdYmd(String updYmd) {
    this.updYmd = updYmd;
  }

  /**
   * getUpdTime.
   * 
   * @return updTime
   */
  public String getUpdTime() {
    return updTime;
  }

  /**
   * setUpdTime.
   * 
   * @param updTime updTime
   */
  public void setUpdTime(String updTime) {
    this.updTime = updTime;
  }

  /**
   * getShozokuClassList.
   * 
   * @return ShozokuClassList
   */
  public String getShozokuClassList() {
    return shozokuClassList;
  }

  /**
   * setShozokuClassList.
   * 
   * @param shozokuClassList shozokuClassList
   */
  public void setShozokuClassList(String shozokuClassList) {
    this.shozokuClassList = shozokuClassList;
  }

  /**
   * getMasutaKubunClassList.
   * 
   * @return MasutaKubunClassList
   */
  public String getMasutaKubunClassList() {
    return masutaKubunClassList;
  }

  /**
   * setMasutaKubunClassList.
   * 
   * @param masutaKubunClassList masutaKubunClassList
   */
  public void setMasutaKubunClassList(String masutaKubunClassList) {
    this.masutaKubunClassList = masutaKubunClassList;
  }

  /**
   * getErrorControll.
   * 
   * @return errorControll
   */
  public String getErrorControll() {
    return errorControll;
  }

  /**
   * setErrorControll.
   * 
   * @param errorControll errorControll
   */
  public void setErrorControll(String errorControll) {
    this.errorControll = errorControll;
  }

  /**
   * getFlag.
   * 
   * @return flag
   */
  public int getFlag() {
    return flag;
  }

  /**
   * setFlag.
   * 
   * @param flag flag
   */
  public void setFlag(int flag) {
    this.flag = flag;
  }

  /**
   * getItemPriceInfo.
   * 
   * @return itemPriceInfo
   */
  public int getItemPriceInfo() {
    return itemPriceInfo;
  }

  /**
   * setItemPriceInfo.
   * 
   * @param itemPriceInfo itemPriceInfo
   */
  public void setItemPriceInfo(int itemPriceInfo) {
    this.itemPriceInfo = itemPriceInfo;
  }

  /**
   * getBussinessDate.
   * 
   * @return bussinessDate
   */
  public String getBussinessDate() {
    return bussinessDate;
  }

  /**
   * setBussinessDate.
   * 
   * @param bussinessDate bussinessDate
   */
  public void setBussinessDate(String bussinessDate) {
    this.bussinessDate = bussinessDate;
  }

  /**
   * getOfficeCode.
   * 
   * @return officeCode
   */
  public String getOfficeCode() {
    return officeCode;
  }

  /**
   * setOfficeCode.
   * 
   * @param officeCode officeCode
   */
  public void setOfficeCode(String officeCode) {
    this.officeCode = officeCode;
  }

  /**
   * getCheckType.
   * 
   * @return checkType
   */
  public int getCheckType() {
    return checkType;
  }

  /**
   * setCheckType.
   * 
   * @param checkType checkType
   */
  public void setCheckType(int checkType) {
    this.checkType = checkType;
  }

  /**
   * getRelationMasterErrorFlag.
   * 
   * @return relationMasterErrorFlag
   */
  public String getRelationMasterErrorFlag() {
    return relationMasterErrorFlag;
  }

  /**
   * setRelationMasterErrorFlag.
   * 
   * @param relationMasterErrorFlag relationMasterErrorFlag
   */
  public void setRelationMasterErrorFlag(String relationMasterErrorFlag) {
    this.relationMasterErrorFlag = relationMasterErrorFlag;
  }

  /**
   * getViewMode.
   * 
   * @return viewMode
   */
  public String getViewMode() {
    return viewMode;
  }

  /**
   * setViewMode.
   * 
   * @param viewMode viewMode
   */
  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  /**
   * getHaitaDate.
   * 
   * @return haitaDate
   */
  public String getHaitaDate() {
    return haitaDate;
  }

  /**
   * setHaitaDate.
   * 
   * @param haitaDate haitaDate
   */
  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }

  /**
   * getHaitaTime.
   * 
   * @return haitaTime
   */
  public String getHaitaTime() {
    return haitaTime;
  }

  /**
   * setHaitaTime.
   * 
   * @param haitaTime haitaTime
   */
  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }

  /**
   * getStrSelectedCustCode.
   * 
   * @return strSelectedCustCode
   */
  public String getStrSelectedCustCode() {
    return strSelectedCustCode;
  }

  /**
   * setStrSelectedCustCode.
   * 
   * @param strSelectedCustCode strSelectedCustCode
   */
  public void setStrSelectedCustCode(String strSelectedCustCode) {
    this.strSelectedCustCode = strSelectedCustCode;
  }

  /**
   * getStrSelectedItemCode.
   * 
   * @return strSelectedItemCode
   */
  public String getStrSelectedItemCode() {
    return strSelectedItemCode;
  }

  /**
   * setStrSelectedItemCode.
   * 
   * @param strSelectedItemCode strSelectedItemCode
   */
  public void setStrSelectedItemCode(String strSelectedItemCode) {
    this.strSelectedItemCode = strSelectedItemCode;
  }

  /**
   * getStrSelectedShopCode.
   * 
   * @return strSelectedShopCode
   */
  public String getStrSelectedShopCode() {
    return strSelectedShopCode;
  }

  /**
   * setStrSelectedShopCode.
   * 
   * @param strSelectedShopCode strSelectedShopCode
   */
  public void setStrSelectedShopCode(String strSelectedShopCode) {
    this.strSelectedShopCode = strSelectedShopCode;
  }

  /**
   * getStrSelectedSaleType.
   * 
   * @return strSelectedSaleType
   */
  public String getStrSelectedSaleType() {
    return strSelectedSaleType;
  }

  /**
   * setStrSelectedSaleType.
   * 
   * @param strSelectedSaleType strSelectedSaleType
   */
  public void setStrSelectedSaleType(String strSelectedSaleType) {
    this.strSelectedSaleType = strSelectedSaleType;
  }

  /**
   * getStrSelectedEndTime.
   * 
   * @return strSelectedEndTime
   */
  public String getStrSelectedEndTime() {
    return strSelectedEndTime;
  }

  /**
   * setStrSelectedEndTime.
   * 
   * @param strSelectedEndTime strSelectedEndTime
   */
  public void setStrSelectedEndTime(String strSelectedEndTime) {
    this.strSelectedEndTime = strSelectedEndTime;
  }

  /**
   * getSearchMax.
   * 
   * @return searchMax
   */
  public int getSearchMax() {
    return searchMax;
  }

  /**
   * setSearchMax.
   * 
   * @param searchMax searchMax
   */
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

  /**
   * isCheckUpdYmd.
   * 
   * @return checkUpdYmd
   */
  public boolean isCheckUpdYmd() {
    return checkUpdYmd;
  }

  /**
   * setCheckUpdYmd.
   * 
   * @param checkUpdYmd checkUpdYmd
   */
  public void setCheckUpdYmd(boolean checkUpdYmd) {
    this.checkUpdYmd = checkUpdYmd;
  }

  /**
   * isCheckCancleData.
   * 
   * @return checkCancleData
   */
  public boolean isCheckCancleData() {
    return checkCancleData;
  }

  /**
   * setCheckCancleData.
   * 
   * @param checkCancleData checkCancleData
   */
  public void setCheckCancleData(boolean checkCancleData) {
    this.checkCancleData = checkCancleData;
  }

  /**
   * getErrorMessages.
   * 
   * @return errorMessages
   */
  public String getErrorMessages() {
    return errorMessages;
  }

  /**
   * setErrorMessages.
   * 
   * @param errorMessages errorMessages
   */
  public void setErrorMessages(String errorMessages) {
    this.errorMessages = errorMessages;
  }

  /**
   * getSearchShozokuClassList.
   * 
   * @return searchShozokuClassList
   */
  public String getSearchShozokuClassList() {
    return searchShozokuClassList;
  }

  /**
   * setSearchShozokuClassList.
   * 
   * @param searchShozokuClassList searchShozokuClassList
   */
  public void setSearchShozokuClassList(String searchShozokuClassList) {
    this.searchShozokuClassList = searchShozokuClassList;
  }

  public String getErrMessage() {
    return errMessage;
  }

  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }

  public ArrayList<Mst0108d01Model> getMst0108d01Models() {
    return mst0108d01Models;
  }

  public void setMst0108d01Models(ArrayList<Mst0108d01Model> mst0108d01Models) {
    this.mst0108d01Models = mst0108d01Models;
  }

  public String getJisonData() {
    return jisonData;
  }

  public void setJisonData(String jisonData) {
    this.jisonData = jisonData;
  }

  public String getTenCodeNone() {
    return tenCodeNone;
  }

  public void setTenCodeNone(String tenCodeNone) {
    this.tenCodeNone = tenCodeNone;
  }

}
