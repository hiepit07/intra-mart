/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:SearchMst0108d01.java
 * 
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/11/30
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)グエン リユオン ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * コントローラクラス
 * 
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */

public class SearchMst0108d01 {

  private String searchJigyouShoCode;
  private String searchCstCode;
  private String searchItemCode;
  private String searchShopCode;
  private String searchValidFrom;
  private String searchValidTo;
  private String searchBunruiCode;
  private String masutaKubunClassList;
  private boolean checkDay;
  private boolean checkCancleData;
  private String searchShozokuClassList;

  public String getSearchcstCode() {
    return searchCstCode;
  }

  public void setSearchcstCode(String searchcstCode) {
    searchCstCode = searchcstCode;
  }

  public String getSearchitemCode() {
    return searchItemCode;
  }

  public void setSearchitemCode(String searchitemCode) {
    searchItemCode = searchitemCode;
  }

  public String getSearchshopCode() {
    return searchShopCode;
  }

  public void setSearchshopCode(String searchshopCode) {
    searchShopCode = searchshopCode;
  }

  public String getSearchvalidFrom() {
    return searchValidFrom;
  }

  public void setSearchvalidFrom(String searchvalidFrom) {
    searchValidFrom = searchvalidFrom;
  }

  public String getSearchvalidTo() {
    return searchValidTo;
  }

  public void setSearchvalidTo(String searchvalidTo) {
    searchValidTo = searchvalidTo;
  }

  public String getSearchbunruiCode() {
    return searchBunruiCode;
  }

  public void setSearchbunruiCode(String searchbunruiCode) {
    searchBunruiCode = searchbunruiCode;
  }

  public String getMasutaKubunClassList() {
    return masutaKubunClassList;
  }

  public void setMasutaKubunClassList(String masutaKubunClassList) {
    this.masutaKubunClassList = masutaKubunClassList;
  }

  public String getSearchJigyouShoCode() {
    return searchJigyouShoCode;
  }

  public void setSearchJigyouShoCode(String searchJigyouShoCode) {
    this.searchJigyouShoCode = searchJigyouShoCode;
  }

  public boolean isCheckDay() {
    return checkDay;
  }

  public void setCheckDay(boolean checkDay) {
    this.checkDay = checkDay;
  }

  public boolean isCheckCancleData() {
    return checkCancleData;
  }

  public void setCheckCancleData(boolean checkCancleData) {
    this.checkCancleData = checkCancleData;
  }

  public String getSearchShozokuClassList() {
    return searchShozokuClassList;
  }

  public void setSearchShozokuClassList(String searchShozokuClassList) {
    this.searchShozokuClassList = searchShozokuClassList;
  }

}
