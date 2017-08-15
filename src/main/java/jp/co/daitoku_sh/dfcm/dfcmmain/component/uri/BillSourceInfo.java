package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;


public class BillSourceInfo {
  /** 事業所コード */
  private String jigyoCode;
  /** 納品日 */
  private String deliDate;
  /** 便 */
  private String binKb;
  /** 得意先コード */
  private String custCode;
  /** 得意先名称 */
  private String custName;
  /** 店舗コード */
  private String shopCode;
  /** 店舗名称 */
  private String shopName;
  /** 最新伝票No */
  private long denNo;
  /** 最新締め処理フラグ */
  private String billFlg;
  /** 重複件数 */
  private int count;
  
  /**
   * jigyoCodeを取得する.
   *
   * @return String jigyoCode
   */
  public String getJigyoCode() {
    return jigyoCode;
  }
  
  /**
   * jigyoCodeをセットする.
   *
   * @param jigyoCode jigyoCode
   */
  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = jigyoCode;
  }
  
  /**
   * deliDateを取得する.
   *
   * @return String deliDate
   */
  public String getDeliDate() {
    return deliDate;
  }
  
  /**
   * deliDateをセットする.
   *
   * @param deliDate deliDate
   */
  public void setDeliDate(String deliDate) {
    this.deliDate = deliDate;
  }
  
  /**
   * binKbを取得する.
   *
   * @return String binKb
   */
  public String getBinKb() {
    return binKb;
  }
  
  /**
   * binKbをセットする.
   *
   * @param binKb binKb
   */
  public void setBinKb(String binKb) {
    this.binKb = binKb;
  }
  
  /**
   * custCodeを取得する.
   *
   * @return String custCode
   */
  public String getCustCode() {
    return custCode;
  }
  
  /**
   * custCodeをセットする.
   *
   * @param custCode custCode
   */
  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }
  
  /**
   * custNameを取得する.
   *
   * @return String custName
   */
  public String getCustName() {
    return custName;
  }
  
  /**
   * custNameをセットする.
   *
   * @param custName custName
   */
  public void setCustName(String custName) {
    this.custName = custName;
  }
  
  /**
   * shopCodeを取得する.
   *
   * @return String shopCode
   */
  public String getShopCode() {
    return shopCode;
  }
  
  /**
   * shopCodeをセットする.
   *
   * @param shopCode shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
  
  /**
   * shopNameを取得する.
   *
   * @return String shopName
   */
  public String getShopName() {
    return shopName;
  }
  
  /**
   * shopNameをセットする.
   *
   * @param shopName shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  
  /**
   * denNoを取得する.
   *
   * @return long denNo
   */
  public long getDenNo() {
    return denNo;
  }
  
  /**
   * denNoをセットする.
   *
   * @param denNo denNo
   */
  public void setDenNo(long denNo) {
    this.denNo = denNo;
  }
  
  /**
   * billFlgを取得する.
   *
   * @return String billFlg
   */
  public String getBillFlg() {
    return billFlg;
  }
  
  /**
   * billFlgをセットする.
   *
   * @param billFlg billFlg
   */
  public void setBillFlg(String billFlg) {
    this.billFlg = billFlg;
  }
  
  /**
   * countを取得する.
   *
   * @return int count
   */
  public int getCount() {
    return count;
  }
  
  /**
   * countをセットする.
   *
   * @param count count
   */
  public void setCount(int count) {
    this.count = count;
  }

  
}
