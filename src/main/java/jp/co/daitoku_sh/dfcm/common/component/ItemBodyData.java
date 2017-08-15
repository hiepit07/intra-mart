/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ItemBodyData.java
 * 
 * 作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/01 1.00 ACT)福田 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 品目明細情報格納クラス
 * 
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public class ItemBodyData {

  /** 品目明細情報 */
  private ItemDetailInfo itemDetailInfo;

  /** 発生マスタ区分 */
  private String orderKb;

  /** メッセージコード */
  private String msgCd;

  /**
   * getter/setter
   */
  public String getOrderKb() {
    return orderKb;
  }

  public ItemDetailInfo getItemDetailInfo() {
    return itemDetailInfo;
  }

  public void setItemDetailInfo(ItemDetailInfo itemDetailInfo) {
    this.itemDetailInfo = itemDetailInfo;
  }

  public void setOrderKb(String orderKb) {
    this.orderKb = orderKb;
  }

  public String getMsgCd() {
    return msgCd;
  }

  public void setMsgCd(String msgCd) {
    this.msgCd = msgCd;
  }
}
