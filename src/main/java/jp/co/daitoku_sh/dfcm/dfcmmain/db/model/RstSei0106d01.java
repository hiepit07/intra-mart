/**
 * パッケージ名:jp.jp.co.daitoku_sh.dfcm.dfcmmain.db.model
 * ファイル名:RstSei0106d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/12/10
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

import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01Head;

/**
 * Model class
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class RstSei0106d01 extends TblSei01Head {
  /** 税抜き金額*/
  private Integer taxAmount;
  
  /** 消費税*/
  private Integer saleTax;
  
  /** 税込み金額*/
  private Integer incTaxAmount;
  
  /** 請求額*/
  private Integer billAmount;
  
  /** 事業所コード*/
  private String bildNmR;
  /** 事業所名*/
  private String jgymei;
  /** 担当者コード*/
  private String userCode;
  /** 担当者氏名*/
  private String userNm;
  
  public Integer getTaxAmount() {
    return taxAmount;
  }
  
  public void setTaxAmount(Integer taxAmount) {
    this.taxAmount = taxAmount;
  }
  
  public Integer getSaleTax() {
    return saleTax;
  }
  
  public void setSaleTax(Integer saleTax) {
    this.saleTax = saleTax;
  }
  
  public Integer getIncTaxAmount() {
    return incTaxAmount;
  }
  
  public void setIncTaxAmount(Integer incTaxAmount) {
    this.incTaxAmount = incTaxAmount;
  }
  
  public Integer getBillAmount() {
    return billAmount;
  }
  
  public void setBillAmount(Integer billAmount) {
    this.billAmount = billAmount;
  }
  
  public String getBildNmR() {
    return bildNmR;
  }
  
  public void setBildNmR(String bildNmR) {
    this.bildNmR = bildNmR;
  }
  
  public String getJgymei() {
    return jgymei;
  }
  
  public void setJgymei(String jgymei) {
    this.jgymei = jgymei;
  }
  
  public String getUserCode() {
    return userCode;
  }
  
  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }
  
  public String getUserNm() {
    return userNm;
  }
  
  public void setUserNm(String userNm) {
    this.userNm = userNm;
  }
  
  

}
