/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ChainData.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/11/11
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/11 1.00 ACT)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

import jp.co.daitoku_sh.dfcm.common.db.model.MstSChain;

/**
 * チェーン情報格納クラス
 * 
 * @author ACT)前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class ChainData {

  /** チェーンマスタ */
  private MstSChain mstSchain;

  /** メッセージコード */
  private String msgCd;

  /**
   * @return the mstSchain
   */
  public MstSChain getMstSchain() {
    return mstSchain;
  }

  /**
   * @param mstSchain the mstSchain to set
   */
  public void setMstSchain(MstSChain mstSchain) {
    this.mstSchain = mstSchain;
  }

  /**
   * @return the msgCd
   */
  public String getMsgCd() {
    return msgCd;
  }

  /**
   * @param msgCd the msgCd to set
   */
  public void setMsgCd(String msgCd) {
    this.msgCd = msgCd;
  }

}
