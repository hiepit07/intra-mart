/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ErrorInfo.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/10/05
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/05 1.00 ABV)Nhungma 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * コース情報格納クラス
 * 
 * @author ABV)Nhungma
 * @version 1.0.0
 * @since 1.0.0
 */
public class ErrorInfo {

  /** 受信日時. */
  private String dateTime;

  /** 受信データ名. */
  private String dataName;

  /** 事業所コード. */
  private String officeCode;

  /** 得意先コード. */
  private String customerCode;

  /** 納品日. */
  private String deadline;

  /** 便. */
  private String flight;

  /** 店舗コード. */
  private String shopCode;

  /** 店名. */
  private String shopName;

  /** 品目コード. */
  private String itemCode;

  /** 品名. */
  private String itemName;

  /** 発生行数. */
  private String lineNumber;

  /** エラーメッセージ. */
  private String errorMessage;

  /**
   * getter/setter.
   */

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getDataName() {
    return dataName;
  }

  public void setDataName(String dataName) {
    this.dataName = dataName;
  }

  public String getOfficeCode() {
    return officeCode;
  }

  public void setOfficeCode(String officeCode) {
    this.officeCode = officeCode;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public String getFlight() {
    return flight;
  }

  public void setFlight(String flight) {
    this.flight = flight;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(String lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

}