/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:Mst0102d01CustomerList.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * Mst0102d01の得意先一覧データオブジェクト
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mst0102d01CustomerList {

  // チェーンコード
  private String chainCode;
  // チェーン枝番
  private String chainEda;
  // チェーン名
  private String chainName;
  // 得意先コード
  private String customerCode;
  // 得意先名称
  private String customerName;
  // 事業所コード
  private String jigyouShoCode;
  // 事業所名
  private String jigyouShoName;
  // 営業担当者コード
  private String eigyouTantoushaCode;
  // 営業担当者氏名
  private String eigyouTantoushaName;
  // 事務担当者コード
  private String jimuTantoushaCode;
  // 事務担当者氏名
  private String jimuTantoushaName;
  // 得意先種別名称
  private String customerType;
  // 内税顧客区分名称
  private String uchizeiKokyakuKubun;
  // 状況コード
  private String stsCode;
  // 排他日付
  private String haitaDate;
  // 排他時間
  private String haitaTime;
  // 検索結果
  private String searchResult;
  // エラーメッセージ
  private String errorMessage;
  // エラーＩＤ
  private String errorIdString;

  public String getChainCode() {
    return chainCode;
  }

  public void setChainCode(String chainCode) {
    this.chainCode = Util.convertSanitize(chainCode);
  }

  public String getChainEda() {
    return chainEda;
  }

  public void setChainEda(String chainEda) {
    this.chainEda = Util.convertSanitize(chainEda);
  }

  public String getChainName() {
    return chainName;
  }

  public void setChainName(String chainName) {
    this.chainName = Util.convertSanitize(chainName);
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = Util.convertSanitize(customerCode);
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = Util.convertSanitize(customerName);
  }

  public String getJigyouShoCode() {
    return jigyouShoCode;
  }

  public void setJigyouShoCode(String jigyouShoCode) {
    this.jigyouShoCode = Util.convertSanitize(jigyouShoCode);
  }

  public String getJigyouShoName() {
    return jigyouShoName;
  }

  public void setJigyouShoName(String jigyouShoName) {
    this.jigyouShoName = Util.convertSanitize(jigyouShoName);
  }

  public String getEigyouTantoushaCode() {
    return eigyouTantoushaCode;
  }

  public void setEigyouTantoushaCode(String eigyouTantoushaCode) {
    this.eigyouTantoushaCode = Util.convertSanitize(eigyouTantoushaCode);
  }

  public String getEigyouTantoushaName() {
    return eigyouTantoushaName;
  }

  public void setEigyouTantoushaName(String eigyouTantoushaName) {
    this.eigyouTantoushaName = Util.convertSanitize(eigyouTantoushaName);
  }

  public String getJimuTantoushaCode() {
    return jimuTantoushaCode;
  }

  public void setJimuTantoushaCode(String jimuTantoushaCode) {
    this.jimuTantoushaCode = Util.convertSanitize(jimuTantoushaCode);
  }

  public String getJimuTantoushaName() {
    return jimuTantoushaName;
  }

  public void setJimuTantoushaName(String jimuTantoushaName) {
    this.jimuTantoushaName = Util.convertSanitize(jimuTantoushaName);
  }

  public String getCustomerType() {
    return customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = Util.convertSanitize(customerType);
  }

  public String getUchizeiKokyakuKubun() {
    return uchizeiKokyakuKubun;
  }

  public void setUchizeiKokyakuKubun(String uchizeiKokyakuKubun) {
    this.uchizeiKokyakuKubun = Util.convertSanitize(uchizeiKokyakuKubun);
  }

  public String getStsCode() {
    return stsCode;
  }

  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
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

  public String getSearchResult() {
    return searchResult;
  }

  public void setSearchResult(String searchResult) {
    this.searchResult = searchResult;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorIdString() {
    return errorIdString;
  }

  public void setErrorIdString(String errorIdString) {
    this.errorIdString = errorIdString;
  }

}