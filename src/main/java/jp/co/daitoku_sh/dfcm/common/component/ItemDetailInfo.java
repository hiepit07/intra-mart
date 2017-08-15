/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ItemDetailInfo.java
 * 
 * 作成者:株式会社アクト・ブレーン
 * 作成日:2015/10/08
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/08 1.00 アクトブレーンベトナム)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

import java.math.BigDecimal;

/**
 * 品目明細情報
 * 
 * @author アクトブレーンベトナム)コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class ItemDetailInfo {

  /** 品目コード（自社） */
  private String companyItemCd;

  /** 品目コード（得意先） */
  private String customerItemCd;

  /** 品目名 */
  private String hinmei;

  /** 品目名カナ */
  private String hinkana;

  /** 品目名略称 */
  private String hinryaku;

  /** 入数 */
  private Short ireme;

  /** 量目 */
  private BigDecimal weight1;

  /** 納品単価 */
  private BigDecimal custDeliTanka;

  /** 販売単価 */
  private Integer custSellTanka;

  /** 先方仕切単価 */
  private BigDecimal custBildTanka;

  /** 部門コード */
  private Short bmncd;

  /** 製品形態コード */
  private Short seikeicd;

  /** 温度帯コード */
  private Short ondocd;

  /** 仕切価 */
  private BigDecimal sikirika;

  /** 分類コード */
  private String bunruiCode;

  /**
   * getter/setter
   */
  public String getCompanyItemCd() {
    return companyItemCd;
  }

  public void setCompanyItemCd(String companyItemCd) {
    this.companyItemCd = companyItemCd;
  }

  public String getCustomerItemCd() {
    return customerItemCd;
  }

  public void setCustomerItemCd(String customerItemCd) {
    this.customerItemCd = customerItemCd;
  }

  public String getHinmei() {
    return hinmei;
  }

  public void setHinmei(String hinmei) {
    this.hinmei = hinmei;
  }

  public String getHinkana() {
    return hinkana;
  }

  public void setHinkana(String hinkana) {
    this.hinkana = hinkana;
  }

  public String getHinryaku() {
    return hinryaku;
  }

  public void setHinryaku(String hinryaku) {
    this.hinryaku = hinryaku;
  }

  public Short getIreme() {
    return ireme;
  }

  public void setIreme(Short ireme) {
    this.ireme = ireme;
  }

  public BigDecimal getWeight1() {
    return weight1;
  }

  public void setWeight1(BigDecimal weight1) {
    this.weight1 = weight1;
  }

  public BigDecimal getCustDeliTanka() {
    return custDeliTanka;
  }

  public void setCustDeliTanka(BigDecimal custDeliTanka) {
    this.custDeliTanka = custDeliTanka;
  }

  public Integer getCustSellTanka() {
    return custSellTanka;
  }

  public void setCustSellTanka(Integer custSellTanka) {
    this.custSellTanka = custSellTanka;
  }

  public BigDecimal getCustBildTanka() {
    return custBildTanka;
  }

  public void setCustBildTanka(BigDecimal custBildTanka) {
    this.custBildTanka = custBildTanka;
  }

  public Short getBmncd() {
    return bmncd;
  }

  public void setBmncd(Short bmncd) {
    this.bmncd = bmncd;
  }

  public Short getSeikeicd() {
    return seikeicd;
  }

  public void setSeikeicd(Short seikeicd) {
    this.seikeicd = seikeicd;
  }

  public Short getOndocd() {
    return ondocd;
  }

  public void setOndocd(Short ondocd) {
    this.ondocd = ondocd;
  }

  public BigDecimal getSikirika() {
    return sikirika;
  }

  public void setSikirika(BigDecimal sikirika) {
    this.sikirika = sikirika;
  }

  public String getBunruiCode() {
    return bunruiCode;
  }

  public void setBunruiCode(String bunruiCode) {
    this.bunruiCode = bunruiCode;
  }
}
