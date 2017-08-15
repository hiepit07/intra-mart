/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:UriageItem.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 ACT)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

import java.math.BigDecimal;

/**
 * フォーム(modelAttribute="FormUri0101d01"(売上登録) とリンク）
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class UriageItem {

  /** 自社品目コード */
  private String itemCode;
  /** 得意先品目コード */
  private String customerItemCode;
  /** 品名略称 */
  private String itemNameR;
  /** 量目 */
  private String ryomoku;
  /** 入数 */
  private short irisu;
  /** 修正区分 */
  private String mofifyKbList;
  /** 受注数量（ケース） */
  private int jucCase;
  /** 受注数量（バラ) */
  private int jucSeparate;
  /** 納品単価 */
  private BigDecimal deliPrice;
  /** 販売単価 */
  private int salesPrice;
  /** 納品金額 */
  private int deliAmt;

  /** 入力フラグ */
  private String flgInput;
  /** 品目名（hidden） */
  private String itemName;
  /** 量目（実数値） */
  private BigDecimal ryomokuReal;
  /** 品目名カナ */
  private String itemNameKana;
  /** 先方仕切単価 */
  private BigDecimal custBillPrice;
  /** 部門コード */
  private short bmnCd;
  /** 製品形態コード */
  private short itemTypeCd;
  /** 温度帯コード */
  private short tempCd;
  /** 仕切価 */
  private BigDecimal billPrice;
  /** 分類コード */
  private String classCd;

  /**
   * itemCodeを取得する.
   *
   * @return String itemCode
   */
  public String getItemCode() {
    return itemCode;
  }

  /**
   * itemCodeをセットする.
   *
   * @param itemCode itemCode
   */
  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  /**
   * customerItemCodeを取得する.
   *
   * @return String customerItemCode
   */
  public String getCustomerItemCode() {
    return customerItemCode;
  }

  /**
   * customerItemCodeをセットする.
   *
   * @param customerItemCode customerItemCode
   */
  public void setCustomerItemCode(String customerItemCode) {
    this.customerItemCode = customerItemCode;
  }

  /**
   * itemNameRを取得する.
   *
   * @return String itemNameR
   */
  public String getItemNameR() {
    return itemNameR;
  }

  /**
   * itemNameRをセットする.
   *
   * @param itemNameR itemNameR
   */
  public void setItemNameR(String itemNameR) {
    this.itemNameR = itemNameR;
  }

  /**
   * ryomokuを取得する.
   *
   * @return String ryomoku
   */
  public String getRyomoku() {
    return ryomoku;
  }

  /**
   * ryomokuをセットする.
   *
   * @param ryomoku ryomoku
   */
  public void setRyomoku(String ryomoku) {
    this.ryomoku = ryomoku;
  }

  /**
   * irisuを取得する.
   *
   * @return short irisu
   */
  public short getIrisu() {
    return irisu;
  }

  /**
   * irisuをセットする.
   *
   * @param irisu irisu
   */
  public void setIrisu(short irisu) {
    this.irisu = irisu;
  }

  /**
   * mofifyKbListを取得する.
   *
   * @return String mofifyKbList
   */
  public String getMofifyKbList() {
    return mofifyKbList;
  }

  /**
   * mofifyKbListをセットする.
   *
   * @param mofifyKbList mofifyKbList
   */
  public void setMofifyKbList(String mofifyKbList) {
    this.mofifyKbList = mofifyKbList;
  }

  /**
   * jucCaseを取得する.
   *
   * @return int jucCase
   */
  public int getJucCase() {
    return jucCase;
  }

  /**
   * jucCaseをセットする.
   *
   * @param jucCase jucCase
   */
  public void setJucCase(int jucCase) {
    this.jucCase = jucCase;
  }

  /**
   * jucSeparateを取得する.
   *
   * @return int jucSeparate
   */
  public int getJucSeparate() {
    return jucSeparate;
  }

  /**
   * jucSeparateをセットする.
   *
   * @param jucSeparate jucSeparate
   */
  public void setJucSeparate(int jucSeparate) {
    this.jucSeparate = jucSeparate;
  }

  /**
   * deliPriceを取得する.
   *
   * @return BigDecimal deliPrice
   */
  public BigDecimal getDeliPrice() {
    return deliPrice;
  }

  /**
   * deliPriceをセットする.
   *
   * @param deliPrice deliPrice
   */
  public void setDeliPrice(BigDecimal deliPrice) {
    this.deliPrice = deliPrice;
  }

  /**
   * salesPriceを取得する.
   *
   * @return int salesPrice
   */
  public int getSalesPrice() {
    return salesPrice;
  }

  /**
   * salesPriceをセットする.
   *
   * @param salesPrice salesPrice
   */
  public void setSalesPrice(int salesPrice) {
    this.salesPrice = salesPrice;
  }

  /**
   * deliAmtを取得する.
   *
   * @return int deliAmt
   */
  public int getDeliAmt() {
    return deliAmt;
  }

  /**
   * deliAmtをセットする.
   *
   * @param deliAmt deliAmt
   */
  public void setDeliAmt(int deliAmt) {
    this.deliAmt = deliAmt;
  }

  /**
   * flgInputを取得する.
   *
   * @return String flgInput
   */
  public String getFlgInput() {
    return flgInput;
  }

  /**
   * flgInputをセットする.
   *
   * @param flgInput flgInput
   */
  public void setFlgInput(String flgInput) {
    this.flgInput = flgInput;
  }

  /**
   * itemNameを取得する.
   *
   * @return String itemName
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * itemNameをセットする.
   *
   * @param itemName itemName
   */
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  /**
   * ryomokuRealを取得する.
   *
   * @return BigDecimal ryomokuReal
   */
  public BigDecimal getRyomokuReal() {
    return ryomokuReal;
  }

  /**
   * ryomokuRealをセットする.
   *
   * @param ryomokuReal ryomokuReal
   */
  public void setRyomokuReal(BigDecimal ryomokuReal) {
    this.ryomokuReal = ryomokuReal;
  }

  /**
   * itemNameKanaを取得する.
   *
   * @return String itemNameKana
   */
  public String getItemNameKana() {
    return itemNameKana;
  }

  /**
   * itemNameKanaをセットする.
   *
   * @param itemNameKana itemNameKana
   */
  public void setItemNameKana(String itemNameKana) {
    this.itemNameKana = itemNameKana;
  }

  /**
   * custBillPriceを取得する.
   *
   * @return BigDecimal custBillPrice
   */
  public BigDecimal getCustBillPrice() {
    return custBillPrice;
  }

  /**
   * custBillPriceをセットする.
   *
   * @param custBillPrice custBillPrice
   */
  public void setCustBillPrice(BigDecimal custBillPrice) {
    this.custBillPrice = custBillPrice;
  }

  /**
   * bmnCdを取得する.
   *
   * @return short bmnCd
   */
  public short getBmnCd() {
    return bmnCd;
  }

  /**
   * bmnCdをセットする.
   *
   * @param bmnCd bmnCd
   */
  public void setBmnCd(short bmnCd) {
    this.bmnCd = bmnCd;
  }

  /**
   * itemTypeCdを取得する.
   *
   * @return short itemTypeCd
   */
  public short getItemTypeCd() {
    return itemTypeCd;
  }

  /**
   * itemTypeCdをセットする.
   *
   * @param itemTypeCd itemTypeCd
   */
  public void setItemTypeCd(short itemTypeCd) {
    this.itemTypeCd = itemTypeCd;
  }

  /**
   * tempCdを取得する.
   *
   * @return short tempCd
   */
  public short getTempCd() {
    return tempCd;
  }

  /**
   * tempCdをセットする.
   *
   * @param tempCd tempCd
   */
  public void setTempCd(short tempCd) {
    this.tempCd = tempCd;
  }

  /**
   * billPriceを取得する.
   *
   * @return BigDecimal billPrice
   */
  public BigDecimal getBillPrice() {
    return billPrice;
  }

  /**
   * billPriceをセットする.
   *
   * @param billPrice billPrice
   */
  public void setBillPrice(BigDecimal billPrice) {
    this.billPrice = billPrice;
  }

  /**
   * classCdを取得する.
   *
   * @return String classCd
   */
  public String getClassCd() {
    return classCd;
  }

  /**
   * classCdをセットする.
   *
   * @param classCd classCd
   */
  public void setClassCd(String classCd) {
    this.classCd = classCd;
  }
}
