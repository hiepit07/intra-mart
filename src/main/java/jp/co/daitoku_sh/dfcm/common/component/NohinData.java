/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ListFormData.java
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

import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;

/**
 * 納品先情報格納クラス
 * 
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public class NohinData {

  /** 納品先マスタ */
  private MstSNohin nhn;

  /** 生産日 */
  private Integer productionDate;

  /** メッセージコード */
  private String msgCd;

  /**
   * getter/setter
   */
  public MstSNohin getNhn() {
    return nhn;
  }

  public void setNhn(MstSNohin nhn) {
    this.nhn = nhn;
  }

  public Integer getProductionDate() {
    return productionDate;
  }

  public void setProductionDate(Integer productionDate) {
    this.productionDate = productionDate;
  }

  public String getMsgCd() {
    return msgCd;
  }

  public void setMsgCd(String msgCd) {
    this.msgCd = msgCd;
  }
}
