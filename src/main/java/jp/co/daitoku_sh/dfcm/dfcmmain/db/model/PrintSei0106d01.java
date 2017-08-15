/**
 * パッケージ名:jp.jp.co.daitoku_sh.dfcm.dfcmmain.db.model
 * ファイル名:PrintSei0106d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/12/11
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.db.model;

/**
 * Model class
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class PrintSei0106d01 {

  private String custCode;
  private String custNmR;
  private String bildNmR;
  private String shopCode;
  private String shopNmR;
  private String deliDate;
  private String denNo;
  private Integer trdPrice;
  
  public String getCustCode() {
    return custCode;
  }
  
  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }
  
  public String getCustNmR() {
    return custNmR;
  }
  
  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }
  
  public String getBildNmR() {
    return bildNmR;
  }
  
  public void setBildNmR(String bildNmR) {
    this.bildNmR = bildNmR;
  }
  
  public String getShopCode() {
    return shopCode;
  }
  
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
  
  public String getShopNmR() {
    return shopNmR;
  }
  
  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }
  
  public String getDeliDate() {
    return deliDate;
  }
  
  public void setDeliDate(String deliDate) {
    this.deliDate = deliDate;
  }
  
  public Integer getTrdPrice() {
    return trdPrice;
  }
  
  public void setTrdPrice(Integer trdPrice) {
    this.trdPrice = trdPrice;
  }

  
  public String getDenNo() {
    return denNo;
  }

  
  public void setDenNo(String denNo) {
    this.denNo = denNo;
  }
}
