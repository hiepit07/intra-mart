/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstCorrectKbMst0109d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/22
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/22 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 担当者情報のクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstCorrectKbMst0109d01 {

  private String stKb;
  private String jigyoCode;
  private String custCode;
  private String custNmR;
  private String correctKb;
  private String correctCause;
  private String zeroDlvdatFlg;
  private String biKou;
  private String stsCode;

  // エラーメッセージ
  private String message;
  private String lstId;
  private String type;

  // エラーメッセージ
  private String haitaDate;
  private String haitaTime;

  public String getStKb() {
    return stKb;
  }

  public void setStKb(String stKb) {
    this.stKb = Util.convertSanitize(stKb);
  }

  public String getJigyoCode() {
    return jigyoCode;
  }

  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = Util.convertSanitize(jigyoCode);
  }

  public String getCustCode() {
    return custCode;
  }

  public void setCustCode(String custCode) {
    this.custCode = Util.convertSanitize(custCode);
  }

  public String getCustNmR() {
    return custNmR;
  }

  public void setCustNmR(String custNmR) {
    this.custNmR = Util.convertSanitize(custNmR);
  }

  public String getCorrectKb() {
    return correctKb;
  }

  public void setCorrectKb(String correctKb) {
    this.correctKb = Util.convertSanitize(correctKb);
  }

  public String getCorrectCause() {
    return correctCause;
  }

  public void setCorrectCause(String correctCause) {
    this.correctCause = Util.convertSanitize(correctCause);
  }

  public String getZeroDlvdatFlg() {
    return zeroDlvdatFlg;
  }

  public void setZeroDlvdatFlg(String zeroDlvdatFlg) {
    this.zeroDlvdatFlg = Util.convertSanitize(zeroDlvdatFlg);
  }

  public String getBiKou() {
    return biKou;
  }

  public void setBiKou(String biKou) {
    this.biKou = Util.convertSanitize(biKou);
  }

  public String getStsCode() {
    return stsCode;
  }

  public void setStsCode(String stsCode) {
    this.stsCode = Util.convertSanitize(stsCode);
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

  public String getLstId() {
    return lstId;
  }

  public void setLstId(String lstId) {
    this.lstId = lstId;
  }
}
