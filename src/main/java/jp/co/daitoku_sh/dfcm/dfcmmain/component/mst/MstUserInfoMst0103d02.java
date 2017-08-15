/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstUserInfoMst0103d02.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TramChu   新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 担当者情報のクラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstUserInfoMst0103d02 {

  private String custCode;
  private String custNmR;
  private String shopCode;
  private String shopNm;
  private String shopNmKana;
  private String shopNmR;
  private String zipCode;
  private String adr1;
  private String adr2;
  private String telNo;
  private String faxNo;
  private String jigyoCode;
  private String jgymei;
  private String sumShopKb;
  private String sumShopJkn;
  private String deliCenterCode;
  private String sunksKb;
  private String sumShopCode;
  private String shopNmSus;
  private String stCodeSts;
  private String stCodeStc;
  private String stCodeSps;
  private String stCodeSpc;
  private String closeDate;
  private String stsCode;

  public String getCustCode() {
    return custCode;
  }

  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  public String getCustNmR() {
    return Util.convertSanitize(custNmR);
  }

  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getShopNm() {
    return shopNm;
  }

  public void setShopNm(String shopNm) {
    this.shopNm = shopNm;
  }

  public String getShopNmKana() {
    return shopNmKana;
  }

  public void setShopNmKana(String shopNmKana) {
    this.shopNmKana = shopNmKana;
  }

  public String getShopNmR() {
    return shopNmR;
  }

  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getAdr1() {
    return adr1;
  }

  public void setAdr1(String adr1) {
    this.adr1 = adr1;
  }

  public String getAdr2() {
    return adr2;
  }

  public void setAdr2(String adr2) {
    this.adr2 = adr2;
  }

  public String getTelNo() {
    return telNo;
  }

  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }

  public String getFaxNo() {
    return faxNo;
  }

  public void setFaxNo(String faxNo) {
    this.faxNo = faxNo;
  }

  public String getJigyoCode() {
    return jigyoCode;
  }

  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = jigyoCode;
  }

  public String getSumShopKb() {
    return sumShopKb;
  }

  public void setSumShopKb(String sumShopKb) {
    this.sumShopKb = sumShopKb;
  }

  public String getSumShopJkn() {
    return sumShopJkn;
  }

  public void setSumShopJkn(String sumShopJkn) {
    this.sumShopJkn = sumShopJkn;
  }

  public String getDeliCenterCode() {
    return deliCenterCode;
  }

  public void setDeliCenterCode(String deliCenterCode) {
    this.deliCenterCode = deliCenterCode;
  }

  public String getSunksKb() {
    return sunksKb;
  }

  public void setSunksKb(String sunksKb) {
    this.sunksKb = sunksKb;
  }

  public String getSumShopCode() {
    return sumShopCode;
  }

  public void setSumShopCode(String sumShopCode) {
    this.sumShopCode = sumShopCode;
  }

  public String getstCodeSts() {
    return stCodeSts;
  }

  public void setstCodeSts(String stCodeSts) {
    this.stCodeSts = stCodeSts;
  }

  public String getstCodeStc() {
    return stCodeStc;
  }

  public void setstCodeStc(String stCodeStc) {
    this.stCodeStc = stCodeStc;
  }

  public String getstCodeSps() {
    return stCodeSps;
  }

  public void setstCodeSps(String stCodeSps) {
    this.stCodeSps = stCodeSps;
  }

  public String getstCodeSpc() {
    return stCodeSpc;
  }

  public void setstCodeSpc(String stCodeSpc) {
    this.stCodeSpc = stCodeSpc;
  }

  public String getCloseDate() {
    return closeDate;
  }

  public void setCloseDate(String closeDate) {
    this.closeDate = closeDate;
  }

  public String getstsCode() {
    return stsCode;
  }

  public void setstsCode(String stsCode) {
    this.stsCode = stsCode;
  }

  public String getjgymei() {
    return jgymei;
  }

  public void setjgymei(String jgymei) {
    this.jgymei = jgymei;
  }

  public String getShopNmSus() {
    return Util.convertSanitize(shopNmSus);
  }

  public void setShopNmSus(String shopNmSus) {
    this.shopNmSus = shopNmSus;
  }

}
