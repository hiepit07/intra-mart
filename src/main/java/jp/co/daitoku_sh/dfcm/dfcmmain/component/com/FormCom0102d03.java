/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.com
 * ファイル名:FormCom0102d03.java
 * 
 * <p>　作成者:アクトブレーンベトナム
 * 作成日:2015/09/25
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 ABV)Nghianguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.com;

/**
 * 
 * @author グエン　リユオン　ギア。
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormCom0102d03 {

  private String shopCode;
  private String shopNm;  
  private String custCode;  
  private int searchMax;

  /**
   * 店舗コードgetter。
   * 
   * @return shopCode 店舗コード
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * 店舗コードsetter。
   * 
   * @param shopCode 店舗コード
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * 店舗略称 getter。
   * 
   * @return shopNm 店舗略称
   */
  public String getShopNm() {
    return shopNm;
  }

  /**
   * 店舗略称 setter。
   * 
   * @param shopNm 店舗略称
   */
  public void setShopNm(String shopNm) {
    this.shopNm = shopNm;
  }  

  /**
   * マックスを検索 getter。
   * 
   * @return searchMax マックスを検索
   */
  public int getSearchMax() {
    return searchMax;
  }

  /**
   * マックスを検索 setter。
   * 
   * @param searchMax マックスを検索
   */
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

  public String getCustCode() {
    return custCode;
  }

  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }
}
