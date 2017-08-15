/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:Mst0102d02CustomerJigyo.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/14
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/14 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * Mst0102d02の得意先事業所データオブジェクト
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mst0102d02CustomerJigyo {

  // 得意先コード
  private String customerCode;
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

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getJigyouShoCode() {
    return jigyouShoCode;
  }

  public void setJigyouShoCode(String jigyouShoCode) {
    this.jigyouShoCode = jigyouShoCode;
  }

  public String getJigyouShoName() {
    return jigyouShoName;
  }

  public void setJigyouShoName(String jigyouShoName) {
    this.jigyouShoName = jigyouShoName;
  }

  public String getEigyouTantoushaCode() {
    return eigyouTantoushaCode;
  }

  public void setEigyouTantoushaCode(String eigyouTantoushaCode) {
    this.eigyouTantoushaCode = eigyouTantoushaCode;
  }

  public String getEigyouTantoushaName() {
    return eigyouTantoushaName;
  }

  public void setEigyouTantoushaName(String eigyouTantoushaName) {
    this.eigyouTantoushaName = eigyouTantoushaName;
  }

  public String getJimuTantoushaCode() {
    return jimuTantoushaCode;
  }

  public void setJimuTantoushaCode(String jimuTantoushaCode) {
    this.jimuTantoushaCode = jimuTantoushaCode;
  }

  public String getJimuTantoushaName() {
    return jimuTantoushaName;
  }

  public void setJimuTantoushaName(String jimuTantoushaName) {
    this.jimuTantoushaName = jimuTantoushaName;
  }
}