/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstDeliveryMst0104d02.java
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
 * 納入先一覧表示
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstDeliveryMst0104d02 {

  private String custCode;
  private String custNmR;
  private String shopCode;
  private String shopNmR;
  private String binKb;
  private String deliKb;
  private String deliveryDivisionName;
  private String stsCode;

  public String getCustCode() {
    return Util.convertSanitize(custCode);
  }

  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  public String getCustNmR() {
    return Util.convertSanitize(custNmR);
  }

  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }

  public String getShopCode() {
    return Util.convertSanitize(shopCode);
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getShopNmR() {
    return Util.convertSanitize(shopNmR);
  }

  public void setShopNmR(String shopNmR) {
    this.shopNmR = shopNmR;
  }

  public String getBinKb() {
    return Util.convertSanitize(binKb);
  }

  public void setBinKb(String binKb) {
    this.binKb = binKb;
  }

  public String getDeliKb() {
    return Util.convertSanitize(deliKb);
  }

  public void setDeliKb(String deliKb) {
    this.deliKb = deliKb;
  }

  public String getDeliveryDivisionName() {
    return Util.convertSanitize(deliveryDivisionName);
  }

  public void setDeliveryDivisionName(String deliveryDivisionName) {
    this.deliveryDivisionName = deliveryDivisionName;
  }

  public String getStsCode() {
    return Util.convertSanitize(stsCode);
  }

  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
  }

}