/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstCourseInfoMst0104d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/28
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/28 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * コース納入先明細マスタ
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstCourseInfoMst0104d01 {

  private String jigyoCode;
  private String jgyMei;
  private String cosCode;
  private String cosNm;
  private String cosNmR;
  private String haisoTime;
  private String shipUpdtTime;
  private String haisoKb;
  private String shippingTypeName;
  private String stsCode;

  // エラーメッセージ
  private String message;
  private String type;
  private String haitaDate;
  private String haitaTime;

  public String getJigyoCode() {
    return Util.convertSanitize(jigyoCode);
  }

  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = jigyoCode;
  }

  public String getJgyMei() {
    return Util.convertSanitize(jgyMei);
  }

  public void setJgyMei(String jgyMei) {
    this.jgyMei = jgyMei;
  }

  public String getCosCode() {
    return Util.convertSanitize(cosCode);
  }

  public void setCosCode(String cosCode) {
    this.cosCode = cosCode;
  }

  public String getCosNm() {
    return Util.convertSanitize(cosNm);
  }

  public void setCosNm(String cosNm) {
    this.cosNm = cosNm;
  }

  public String getCosNmR() {
    return Util.convertSanitize(cosNmR);
  }

  public void setCosNmR(String cosNmR) {
    this.cosNmR = cosNmR;
  }

  public String getHaisoTime() {
    return Util.convertSanitize(haisoTime);
  }

  public void setHaisoTime(String haisoTime) {
    this.haisoTime = haisoTime;
  }

  public String getShipUpdtTime() {
    return Util.convertSanitize(shipUpdtTime);
  }

  public void setShipUpdtTime(String shipUpdtTime) {
    this.shipUpdtTime = shipUpdtTime;
  }

  public String getHaisoKb() {
    return Util.convertSanitize(haisoKb);
  }

  public void setHaisoKb(String haisoKb) {
    this.haisoKb = haisoKb;
  }

  public String getShippingTypeName() {
    return Util.convertSanitize(shippingTypeName);
  }

  public void setShippingTypeName(String shippingTypeName) {
    this.shippingTypeName = shippingTypeName;
  }

  public String getStsCode() {
    return Util.convertSanitize(stsCode);
  }

  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
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

}