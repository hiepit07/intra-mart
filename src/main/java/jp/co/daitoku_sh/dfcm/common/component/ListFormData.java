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

/**
 * 伝票情報格納クラス
 * 
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public class ListFormData {

  /** 帳票ID */
  private String listId;
  /** 伝票種別 */
  private String denCls;
  /** 出荷伝票名称 */
  private String listNm;
  /** 出荷伝票略称 */
  private String listNmR;
  /** 出荷伝票行数 */
  private Short lineNumber;
  /** メッセージコード */
  private String msgCd;

  /**
   * getter/setter
   */
  public String getListId() {
    return listId;
  }

  public void setListId(String listId) {
    this.listId = listId;
  }

  public String getDenCls() {
    return denCls;
  }

  public void setDenCls(String denCls) {
    this.denCls = denCls;
  }

  public String getListNm() {
    return listNm;
  }

  public void setListNm(String listNm) {
    this.listNm = listNm;
  }

  public String getListNmR() {
    return listNmR;
  }

  public void setListNmR(String listNmR) {
    this.listNmR = listNmR;
  }

  public Short getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(Short lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getMsgCd() {
    return msgCd;
  }

  public void setMsgCd(String msgCd) {
    this.msgCd = msgCd;
  }
}
