/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.get
 * ファイル名:GetUrikakegatsudo.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/21
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 
 *<p>2015/10/21 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * 売上債権状況情報取得クラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
public class GetUrikakegatsudo {

  String bildId;
  String jigyoCode;
  String custCode;
  String ygkuBun;
  String custNm;
  String userNm;
  String monUriageTtl;
  String totalDate1;
  String colTermKb1;
  String colDate1;
  String totalDate2;
  String colTermKb2;
  String colDate2;
  String payment;
  String prevBildDate;
  String bildDate;
  String bildUAmt;
  String bildMAmt;
  String target1;
  String billingPeriod;
  String paymentPlanDate;
  String paymentDate;
  String site;
  String thismonthBillAmount;
  String paymentAmount;
  String nyuKinGaku;
  String paymentDateLost;
  String errorPayment;
  String uncollectedBalance;
  String finishDate;
  String kenshuMonth;
  String kenshuDate;
  String summary;
  String nyuDen;
  
  public String getnyuDen() {
    return nyuDen;
  }

  
  public void setnyuDen(String nyuDenNo) {
    nyuDen = nyuDenNo;
  }

  public String getSummary() {
    return summary;
  }

  public String getPayment() {
    return payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(String finishDate) {
    this.finishDate = finishDate;
  }

  public String getKenshuMonth() {
    return kenshuMonth;
  }

  public void setKenshuMonth(String kenshuMonth) {
    this.kenshuMonth = kenshuMonth;
  }

  public String getKenshuDate() {
    return kenshuDate;
  }

  public void setKenshuDate(String kenshuDate) {
    this.kenshuDate = kenshuDate;
  }

  public String getErrorPayment() {
    return errorPayment;
  }

  public void setErrorPayment(String errorPayment) {
    this.errorPayment = errorPayment;
  }

  public String getUncollectedBalance() {
    return uncollectedBalance;
  }

  public void setUncollectedBalance(String uncollectedBalance) {
    this.uncollectedBalance = uncollectedBalance;
  }

  public String getPaymentDateLost() {
    return paymentDateLost;
  }

  public void setPaymentDateLost(String paymentDateLost) {
    this.paymentDateLost = paymentDateLost;
  }

  public String getSouKingaku() {
    return souKingaku;
  }

  public void setSouKingaku(String souKingaku) {
    this.souKingaku = souKingaku;
  }

  String souKingaku;

  public String getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(String paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public String getThismonthBillAmount() {
    return thismonthBillAmount;
  }

  public void setThismonthBillAmount(String thismonthBillAmount) {
    this.thismonthBillAmount = thismonthBillAmount;
  }

  public String getPaymentPlanDate() {
    return paymentPlanDate;
  }

  public void setPaymentPlanDate(String paymentPlanDate) {
    this.paymentPlanDate = paymentPlanDate;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(String paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getBillingPeriod() {
    return billingPeriod;
  }

  public void setBillingPeriod(String billingPeriod) {
    this.billingPeriod = billingPeriod;
  }

  public String getMonUriageTtl() {
    return monUriageTtl;
  }

  public void setMonUriageTtl(String monUriageTtl) {
    this.monUriageTtl = monUriageTtl;
  }

  public String getTotalDate1() {
    return totalDate1;
  }

  public void setTotalDate1(String totalDate1) {
    this.totalDate1 = totalDate1;
  }

  public String getBildId() {
    return bildId;
  }

  public void setBildId(String bildId) {
    this.bildId = bildId;
  }

  public String getJigyoCode() {
    return jigyoCode;
  }

  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = jigyoCode;
  }

  public String getCustCode() {
    return custCode;
  }

  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  public String getCustNm() {
    return custNm;
  }

  public void setCustNm(String custNm) {
    this.custNm = custNm;
  }

  public String getUserNm() {
    return userNm;
  }

  public void setUserNm(String userNm) {
    this.userNm = userNm;
  }

  public String getColTermKb1() {
    return colTermKb1;
  }

  public void setColTermKb1(String colTermKb1) {
    this.colTermKb1 = colTermKb1;
  }

  public String getColDate1() {
    return colDate1;
  }

  public void setColDate1(String colDate1) {
    this.colDate1 = colDate1;
  }

  public String getTotalDate2() {
    return totalDate2;
  }

  public void setTotalDate2(String totalDate2) {
    this.totalDate2 = totalDate2;
  }

  public String getColTermKb2() {
    return colTermKb2;
  }

  public void setColTermKb2(String colTermKb2) {
    this.colTermKb2 = colTermKb2;
  }

  public String getColDate2() {
    return colDate2;
  }

  public void setColDate2(String colDate2) {
    this.colDate2 = colDate2;
  }

  public String getBildDate() {
    return bildDate;
  }

  public void setBildDate(String bildDate) {
    this.bildDate = bildDate;
  }

  public String getBildUAmt() {
    return bildUAmt;
  }

  public void setBildUAmt(String bildUAmt) {
    this.bildUAmt = bildUAmt;
  }

  public String getBildMAmt() {
    return bildMAmt;
  }

  public void setBildMAmt(String bildMAmt) {
    this.bildMAmt = bildMAmt;
  }

  public String getYgkuBun() {
    return ygkuBun;
  }

  public void setYgkuBun(String ygkuBun) {
    this.ygkuBun = ygkuBun;
  }

  public String getPrevBildDate() {
    return prevBildDate;
  }

  public void setPrevBildDate(String prevBildDate) {
    this.prevBildDate = prevBildDate;
  }

  public String getTarget1() {
    return target1;
  }

  public void setTarget1(String target1) {
    this.target1 = target1;
  }

  public String getNyuKinGaku() {
    return nyuKinGaku;
  }

  public void setNyuKinGaku(String nyuKinGaku) {
    this.nyuKinGaku = nyuKinGaku;
  }

}
