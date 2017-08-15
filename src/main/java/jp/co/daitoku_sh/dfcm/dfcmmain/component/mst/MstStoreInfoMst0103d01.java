/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstStoreInfoMst0103d01.java
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

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 *  
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstStoreInfoMst0103d01 {

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
  private String stCodeSts;
  private String stCodeStc;
  private String stCodeSps;
  private String stCodeSpc;
  private String closeDate;
  private String stsCode;
  private String insuserId;
  private String inspgId;
  private String insymd;
  private String instime;
  private String upduserId;
  private String updpgId;
  private String updymd;
  private String updtime;
  // エラーメッセージ
  private String message;
  private String type;
  private String idClear;

  public String getIdClear() {
    return idClear;
  }

  public void setIdClear(String idClear) {
    this.idClear = idClear;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCustCode() {
    return Util.convertSanitize(custCode);
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
    return Util.convertSanitize(shopCode);
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getShopNm() {
    return Util.convertSanitize(shopNm);
  }

  public void setShopNm(String shopNm) {
    this.shopNm = shopNm;
  }

  public String getShopNmKana() {
    return Util.convertSanitize(shopNmKana);
  }

  public void setShopNmKana(String shopNmKana) {
    this.shopNmKana = shopNmKana;
  }

  public String getShopNmR() {
    return Util.convertSanitize(shopNmR);
  }

  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }

  public String getZipCode() {
    return Util.convertSanitize(zipCode);
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getAdr1() {
    return Util.convertSanitize(adr1);
  }

  public void setAdr1(String adr1) {
    this.adr1 = adr1;
  }

  public String getAdr2() {
    return Util.convertSanitize(adr2);
  }

  public void setAdr2(String adr2) {
    this.adr2 = adr2;
  }

  public String getTelNo() {
    return Util.convertSanitize(telNo);
  }

  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }

  public String getJigyoCode() {
    return Util.convertSanitize(jigyoCode);
  }

  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = jigyoCode;
  }

  public String getjgymei() {
    return Util.convertSanitize(jgymei);
  }

  public void setjgymei(String jgymei) {
    this.jgymei = jgymei;
  }

  public String getSumShopKb() {
    return Util.convertSanitize(sumShopKb);
  }

  public void setSumShopKb(String sumShopKb) {
    this.sumShopKb = sumShopKb;
  }

  public String getSumShopJkn() {
    return Util.convertSanitize(sumShopJkn);
  }

  public void setSumShopJkn(String sumShopJkn) {
    this.sumShopJkn = sumShopJkn;
  }

  public String getDeliCenterCode() {
    return Util.convertSanitize(deliCenterCode);
  }

  public void setDeliCenterCode(String deliCenterCode) {
    this.deliCenterCode = deliCenterCode;
  }

  public String getSunksKb() {
    return Util.convertSanitize(sunksKb);
  }

  public void setSunksKb(String sunksKb) {
    this.sunksKb = sunksKb;
  }

  public String getSumShopCode() {
    return Util.convertSanitize(sumShopCode);
  }

  public void setSumShopCode(String sumShopCode) {
    this.sumShopCode = sumShopCode;
  }

  public String getstCodeSts() {
    return Util.convertSanitize(stCodeSts);
  }

  public void setstCodeSts(String stCodeSts) {
    this.stCodeSts = stCodeSts;
  }

  public String getstCodeStc() {
    return Util.convertSanitize(stCodeStc);
  }

  public void setstCodeStc(String stCodeStc) {
    this.stCodeStc = stCodeStc;
  }

  public String getstCodeSps() {
    return Util.convertSanitize(stCodeSps);
  }

  public void setstCodeSps(String stCodeSps) {
    this.stCodeSps = stCodeSps;
  }

  public String getstCodeSpc() {
    return Util.convertSanitize(stCodeSpc);
  }

  public void setstCodeSpc(String stCodeSpc) {
    this.stCodeSpc = stCodeSpc;
  }

  public String getCloseDate() {
    return Util.convertSanitize(closeDate);
  }

  public void setCloseDate(String closeDate) {
    this.closeDate = closeDate;
  }

  public String getstsCode() {
    return Util.convertSanitize(stsCode);
  }

  public void setstsCode(String stsCode) {
    this.stsCode = stsCode;
  }

  public String getinsuserId() {
    return Util.convertSanitize(insuserId);
  }

  public void setinsuserId(String insuserId) {
    this.insuserId = insuserId;
  }

  public String getinspgId() {
    return Util.convertSanitize(inspgId);
  }

  public void setinspgId(String inspgId) {
    this.inspgId = inspgId;
  }

  public String getinsymd() {
    return Util.convertSanitize(insymd);
  }

  public void setinsymd(String insymd) {
    this.insymd = insymd;
  }

  public String getinstime() {
    return Util.convertSanitize(instime);
  }

  public void setinstime(String instime) {
    this.instime = instime;
  }

  public String getupduserId() {
    return Util.convertSanitize(upduserId);
  }

  public void setupduserId(String upduserId) {
    this.upduserId = upduserId;
  }

  public String getupdpgId() {
    return Util.convertSanitize(updpgId);
  }

  public void setupdpgId(String updpgId) {
    this.updpgId = updpgId;
  }

  public String getupdymd() {
    return Util.convertSanitize(updymd);
  }

  public void setupdymd(String updymd) {
    this.updymd = updymd;
  }

  public String getupdtime() {
    return Util.convertSanitize(updtime);
  }

  public void setupdtime(String updtime) {
    this.updtime = updtime;
  }

  public String getFaxNo() {
    return Util.convertSanitize(faxNo);
  }

  public void setFaxNo(String faxNo) {
    this.faxNo = faxNo;
  }

}