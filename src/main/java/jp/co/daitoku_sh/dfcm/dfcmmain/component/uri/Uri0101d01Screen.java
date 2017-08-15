/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:Uri0101d01Screen.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

import jp.co.daitoku_sh.dfcm.common.component.ItemDetailInfo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourse;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSChain;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;

/**
 * AJAXで取得した値を画面に返すために格納するクラス
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */

public class Uri0101d01Screen {

  /** メッセージ */
  private String strMessage;
  /** 事業所コード */
  private String strJigyoCode;
  /** 事業所名 */
  private String strJigyoName;
  /** 得意先情報 */
  private MstCustomer mstCustomer;
  /** 店舗情報 */
  private MstShop mstShop;
  /** コース情報 */
  private MstCourse mstCourse;
  /** コース情報 */
  private MstSChain mstSchain;
  /** 納品先情報 */
  private MstSNohin mstSnohin;
  /** 伝票名称 */
  private String strListName;
  /** 伝票行数 */
  private String strListLine;
  /** 品目明細情報 */
  private ItemDetailInfo mstItemDetail;
  /** 納品金額上限値 */
  private String deliPriceMax;
  /** 売上情報 */
  private UriageData uriData;
  /** 伝票No */
  private long lngSlipNo;
  /** 伝票枝番 */
  private short shtSlipIdx;
  /** 得意先伝票No */
  private String strCustomerSlipNo;

  /**
   * strMessageを取得する.
   *
   * @return String strMessage
   */
  public String getStrMessage() {
    return strMessage;
  }

  /**
   * strMessageをセットする.
   *
   * @param strMessage strMessage
   */
  public void setStrMessage(String strMessage) {
    this.strMessage = strMessage;
  }

  /**
   * strJigyoCodeを取得する.
   *
   * @return String strJigyoCode
   */
  public String getStrJigyoCode() {
    return strJigyoCode;
  }

  /**
   * strJigyoCodeをセットする.
   *
   * @param strJigyoCode strJigyoCode
   */
  public void setStrJigyoCode(String strJigyoCode) {
    this.strJigyoCode = strJigyoCode;
  }

  /**
   * strJigyoNameを取得する.
   *
   * @return String strJigyoName
   */
  public String getStrJigyoName() {
    return strJigyoName;
  }

  /**
   * strJigyoNameをセットする.
   *
   * @param strJigyoName strJigyoName
   */
  public void setStrJigyoName(String strJigyoName) {
    this.strJigyoName = strJigyoName;
  }

  /**
   * mstCustomerを取得する.
   *
   * @return MstCustomer mstCustomer
   */
  public MstCustomer getMstCustomer() {
    return mstCustomer;
  }

  /**
   * mstCustomerをセットする.
   *
   * @param mstCustomer mstCustomer
   */
  public void setMstCustomer(MstCustomer mstCustomer) {
    this.mstCustomer = mstCustomer;
  }

  /**
   * mstShopを取得する.
   *
   * @return MstShop mstShop
   */
  public MstShop getMstShop() {
    return mstShop;
  }

  /**
   * mstShopをセットする.
   *
   * @param mstShop mstShop
   */
  public void setMstShop(MstShop mstShop) {
    this.mstShop = mstShop;
  }

  /**
   * mstCourseを取得する.
   *
   * @return MstCourse mstCourse
   */
  public MstCourse getMstCourse() {
    return mstCourse;
  }

  /**
   * mstCourseをセットする.
   *
   * @param mstCourse mstCourse
   */
  public void setMstCourse(MstCourse mstCourse) {
    this.mstCourse = mstCourse;
  }

  /**
   * mstSchainを取得する.
   *
   * @return MstSChain mstSchain
   */
  public MstSChain getMstSchain() {
    return mstSchain;
  }

  /**
   * mstSchainをセットする.
   *
   * @param mstSchain mstSchain
   */
  public void setMstSchain(MstSChain mstSchain) {
    this.mstSchain = mstSchain;
  }

  /**
   * mstSnohinを取得する.
   *
   * @return MstSNohin mstSnohin
   */
  public MstSNohin getMstSnohin() {
    return mstSnohin;
  }

  /**
   * mstSnohinをセットする.
   *
   * @param mstSnohin mstSnohin
   */
  public void setMstSnohin(MstSNohin mstSnohin) {
    this.mstSnohin = mstSnohin;
  }

  /**
   * strListNameを取得する.
   *
   * @return String strListName
   */
  public String getStrListName() {
    return strListName;
  }

  /**
   * strListNameをセットする.
   *
   * @param strListName strListName
   */
  public void setStrListName(String strListName) {
    this.strListName = strListName;
  }

  /**
   * strListLineを取得する.
   *
   * @return String strListLine
   */
  public String getStrListLine() {
    return strListLine;
  }

  /**
   * strListLineをセットする.
   *
   * @param strListLine strListLine
   */
  public void setStrListLine(String strListLine) {
    this.strListLine = strListLine;
  }

  /**
   * mstItemDetailを取得する.
   *
   * @return ItemDetailInfo mstItemDetail
   */
  public ItemDetailInfo getMstItemDetail() {
    return mstItemDetail;
  }

  /**
   * mstItemDetailをセットする.
   *
   * @param mstItemDetail mstItemDetail
   */
  public void setMstItemDetail(ItemDetailInfo mstItemDetail) {
    this.mstItemDetail = mstItemDetail;
  }

  /**
   * deliPriceMaxを取得する.
   *
   * @return String deliPriceMax
   */
  public String getDeliPriceMax() {
    return deliPriceMax;
  }

  /**
   * deliPriceMaxをセットする.
   *
   * @param deliPriceMax deliPriceMax
   */
  public void setDeliPriceMax(String deliPriceMax) {
    this.deliPriceMax = deliPriceMax;
  }

  /**
   * uriDataを取得する.
   *
   * @return UriageData uriData
   */
  public UriageData getUriData() {
    return uriData;
  }

  /**
   * uriDataをセットする.
   *
   * @param uriData uriData
   */
  public void setUriData(UriageData uriData) {
    this.uriData = uriData;
  }

  /**
   * lngSlipNoを取得する.
   *
   * @return long lngSlipNo
   */
  public long getLngSlipNo() {
    return lngSlipNo;
  }

  /**
   * lngSlipNoをセットする.
   *
   * @param lngSlipNo lngSlipNo
   */
  public void setLngSlipNo(long lngSlipNo) {
    this.lngSlipNo = lngSlipNo;
  }

  /**
   * shtSlipIdxを取得する.
   *
   * @return short shtSlipIdx
   */
  public short getShtSlipIdx() {
    return shtSlipIdx;
  }

  /**
   * shtSlipIdxをセットする.
   *
   * @param shtSlipIdx shtSlipIdx
   */
  public void setShtSlipIdx(short shtSlipIdx) {
    this.shtSlipIdx = shtSlipIdx;
  }

  /**
   * strCustomerSlipNoを取得する.
   *
   * @return String strCustomerSlipNo
   */
  public String getStrCustomerSlipNo() {
    return strCustomerSlipNo;
  }

  /**
   * strCustomerSlipNoをセットする.
   *
   * @param strCustomerSlipNo strCustomerSlipNo
   */
  public void setStrCustomerSlipNo(String strCustomerSlipNo) {
    this.strCustomerSlipNo = strCustomerSlipNo;
  }

}