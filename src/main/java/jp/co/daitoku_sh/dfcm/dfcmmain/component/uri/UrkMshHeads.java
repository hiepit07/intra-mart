/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:UrkMshHeads.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/12/11
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/11 1.00 ACT)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;

/**
 * 売掛・未収ヘッダ情報格納クラス
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class UrkMshHeads {
  /** 売掛・未収ヘッダ（得意先集計） */
  private TblSei01UrkMshHead tblCustomerUrkMshHead;
  /** 売掛・未収ヘッダ（店舗集計） */
  private TblSei01UrkMshHead tblShopUrkMshHead;
  /** 売掛・未収ヘッダ（得意先集計）登録フラグ：true insert()、false update() */
  private boolean flgInsertCustomer;
  /** 売掛・未収ヘッダ（店舗集計）登録フラグ：true insert()、false update() */
  private boolean flgInsertShop;

  /**
   * tblCustomerUrkMshHeadを取得する.
   *
   * @return TblSei01UrkMshHead tblCustomerUrkMshHead
   */
  public TblSei01UrkMshHead getTblCustomerUrkMshHead() {
    return tblCustomerUrkMshHead;
  }
  
  /**
   * tblCustomerUrkMshHeadをセットする.
   *
   * @param tblCustomerUrkMshHead tblCustomerUrkMshHead
   */
  public void setTblCustomerUrkMshHead(TblSei01UrkMshHead tblCustomerUrkMshHead) {
    this.tblCustomerUrkMshHead = tblCustomerUrkMshHead;
  }
  
  /**
   * tblShopUrkMshHeadを取得する.
   *
   * @return TblSei01UrkMshHead tblShopUrkMshHead
   */
  public TblSei01UrkMshHead getTblShopUrkMshHead() {
    return tblShopUrkMshHead;
  }
  
  /**
   * tblShopUrkMshHeadをセットする.
   *
   * @param tblShopUrkMshHead tblShopUrkMshHead
   */
  public void setTblShopUrkMshHead(TblSei01UrkMshHead tblShopUrkMshHead) {
    this.tblShopUrkMshHead = tblShopUrkMshHead;
  }
  
  /**
   * flgInsertCustomerを取得する.
   *
   * @return boolean flgInsertCustomer
   */
  public boolean isFlgInsertCustomer() {
    return flgInsertCustomer;
  }
  
  /**
   * flgInsertCustomerをセットする.
   *
   * @param flgInsertCustomer flgInsertCustomer
   */
  public void setFlgInsertCustomer(boolean flgInsertCustomer) {
    this.flgInsertCustomer = flgInsertCustomer;
  }
  
  /**
   * flgInsertShopを取得する.
   *
   * @return boolean flgInsertShop
   */
  public boolean isFlgInsertShop() {
    return flgInsertShop;
  }
  
  /**
   * flgInsertShopをセットする.
   *
   * @param flgInsertShop flgInsertShop
   */
  public void setFlgInsertShop(boolean flgInsertShop) {
    this.flgInsertShop = flgInsertShop;
  }
}
