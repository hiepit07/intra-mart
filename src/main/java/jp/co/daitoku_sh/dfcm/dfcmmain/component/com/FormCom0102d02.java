/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component
 * ファイル名:FormCom0102d02.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/10
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/10 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.com;

/**
 * 得意先検索子画面用エンティティ
 *
 * @author TSUZUKI DENKI
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public class FormCom0102d02 {

  private String jigyoshoCode; // 事業所コード（親画面引数）
  private String custCode; // 得意先コード
  private String custNm; // 得意先名称
  private String shopKb; // 店舗区分（親画面引数）
  private String searchKb; // 検索区分（親画面引数）
  private String businessDate; // 業務日付
  private int searchMax; // 最大表示件数
  private String txtMessage; // エラーメッセージ
  private String ddlShozoku; // 事業所コンボの事業所コード

  /**
   * 事業所コードgetter
   *
   * @return jigyoshoCode 事業所コード
   */
  public String getJigyoshoCode() {
    return jigyoshoCode;
  }

  /**
   * 事業所コードsetter
   *
   * @param jigyoshoCode 事業所コード
   */
  public void setJigyoshoCode(String jigyoshoCode) {
    this.jigyoshoCode = jigyoshoCode;
  }

  /**
   * 得意先コードgetter
   *
   * @return custCode 得意先コード
   */
  public String getCustCode() {
    return custCode;
  }

  /**
   * 得意先コードsetter
   *
   * @param custCode 得意先コード
   */
  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  /**
   * 得意先名称getter
   *
   * @return custNm 得意先名称
   */
  public String getCustNm() {
    return custNm;
  }

  /**
   * 得意先名称setter
   *
   * @param custNm 得意先名称
   */
  public void setCustNm(String custNm) {
    this.custNm = custNm;
  }

  /**
   * 店舗区分getter
   *
   * @return shopKb 店舗区分
   */
  public String getShopKb() {
    return shopKb;
  }

  /**
   * 店舗区分setter
   *
   * @param shopKb 店舗区分
   */
  public void setShopKb(String shopKb) {
    this.shopKb = shopKb;
  }

  /**
   * 検索対象区分getter
   *
   * @return searchKb 検索対象区分
   */
  public String getSearchKb() {
    return searchKb;
  }

  /**
   * 検索対象区分setter
   *
   * @param searchKb 検索対象区分
   */
  public void setSearchKb(String searchKb) {
    this.searchKb = searchKb;
  }

  /**
   * 業務日付getter
   *
   * @return businessDate 業務日付
   */
  public String getBusinessDate() {
    return businessDate;
  }

  /**
   * 業務日付setter
   *
   * @param businessDate 業務日付
   */
  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  /**
   * 最大表示件数getter
   *
   * @return searchMax 最大表示件数
   */
  public int getSearchMax() {
    return searchMax;
  }

  /**
   * 最大表示件数setter
   *
   * @param searchMax 最大表示件数
   */
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

  /**
   * メッセージgetter
   *
   * @return txtMessage メッセージ
   */
  public String getTxtMessage() {
    return txtMessage;
  }

  /**
   * メッセージsetter
   *
   * @param txtMessage メッセージ
   */
  public void setTxtMessage(String txtMessage) {
    this.txtMessage = txtMessage;
  }

  /**
   * 事業所コンボValue値getter
   *
   * @return ddlShozoku 事業所コンボ（事業所コード）
   */
  public String getDdlShozoku() {
    return ddlShozoku;
  }

  /**
   * 事業所コンボValue値setter
   *
   * @param ddlShozoku 事業所コンボ（事業所コード）
   */
  public void setDdlShozoku(String ddlShozoku) {
    this.ddlShozoku = ddlShozoku;
  }
}
