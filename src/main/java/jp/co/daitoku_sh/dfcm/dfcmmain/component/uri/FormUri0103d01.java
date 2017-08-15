/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:FormUri0101d01.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/12/10
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.uri;

/**
 * フォーム(modelAttribute="FormUri0103d01"(再請求売上登録) とリンク）
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormUri0103d01 {

  /** 請求元伝票No */
  private String txtBillSrcNo;
  private String hdnBillSrcNo;
  
  /** 請求元選択ダイアログ */
  private String dlgTxtMessage;
  
  /** システム管理者フラグ */
  private String hdnSysAdminFlg;

  /** ログイン事業所コード */
  private String hdnLoginJigyoCode;
  
  /** 得意先コード */
  private String hdnCustomerCode;
  
  /** 店舗コード */
  private String hdnShopCode;
  
  /** 納品日 */
  private String hdnDeliDate;
  
  /** 便区分 */
  private String hdnBinKb;

  /** 納品金額上限 */
  private String hdnPriceMax;
  
  /** 請求元売上情報（JSON型） */
  private String hdnJsonBillSrc;

  /** コンテキストパス */
  private String hdnContextPath;

  /** JSON型品目情報 */
  private String hdnItemJson;

  /** 登録ユーザコード */
  private String hdnRegistUserCode;

  /** 処理区分 */
  private String hdnOperateMode;

  /** 合計納品金額 */
  private String hdnTotalDeliAmt;

  /** 自社伝票No */
  private String txtSlipNo;
  private String hdnSlipNo;
  private short hdnSlipIdx;

  /** 事業所コード */
  private String txtJigyoCode;
  private String hdnJigyoCode;
  private String hdnJigyoName;

  /** 更新日時 */
  private String hdnUpdateDateTime;

  /** 消費税端数処理 */
  private String strTaxFrcKb;

  
  /**
   * txtBillSrcNoを取得する.
   *
   * @return String txtBillSrcNo
   */
  public String getTxtBillSrcNo() {
    return txtBillSrcNo;
  }

  
  /**
   * txtBillSrcNoをセットする.
   *
   * @param txtBillSrcNo txtBillSrcNo
   */
  public void setTxtBillSrcNo(String txtBillSrcNo) {
    this.txtBillSrcNo = txtBillSrcNo;
  }

  
  /**
   * hdnBillSrcNoを取得する.
   *
   * @return String hdnBillSrcNo
   */
  public String getHdnBillSrcNo() {
    return hdnBillSrcNo;
  }

  
  /**
   * hdnBillSrcNoをセットする.
   *
   * @param hdnBillSrcNo hdnBillSrcNo
   */
  public void setHdnBillSrcNo(String hdnBillSrcNo) {
    this.hdnBillSrcNo = hdnBillSrcNo;
  }

  
  /**
   * dlgTxtMessageを取得する.
   *
   * @return String dlgTxtMessage
   */
  public String getDlgTxtMessage() {
    return dlgTxtMessage;
  }

  
  /**
   * dlgTxtMessageをセットする.
   *
   * @param dlgTxtMessage dlgTxtMessage
   */
  public void setDlgTxtMessage(String dlgTxtMessage) {
    this.dlgTxtMessage = dlgTxtMessage;
  }

  
  /**
   * hdnSysAdminFlgを取得する.
   *
   * @return String hdnSysAdminFlg
   */
  public String getHdnSysAdminFlg() {
    return hdnSysAdminFlg;
  }

  
  /**
   * hdnSysAdminFlgをセットする.
   *
   * @param hdnSysAdminFlg hdnSysAdminFlg
   */
  public void setHdnSysAdminFlg(String hdnSysAdminFlg) {
    this.hdnSysAdminFlg = hdnSysAdminFlg;
  }

  
  /**
   * hdnLoginJigyoCodeを取得する.
   *
   * @return String hdnLoginJigyoCode
   */
  public String getHdnLoginJigyoCode() {
    return hdnLoginJigyoCode;
  }

  
  /**
   * hdnLoginJigyoCodeをセットする.
   *
   * @param hdnLoginJigyoCode hdnLoginJigyoCode
   */
  public void setHdnLoginJigyoCode(String hdnLoginJigyoCode) {
    this.hdnLoginJigyoCode = hdnLoginJigyoCode;
  }

  
  /**
   * hdnCustomerCodeを取得する.
   *
   * @return String hdnCustomerCode
   */
  public String getHdnCustomerCode() {
    return hdnCustomerCode;
  }

  
  /**
   * hdnCustomerCodeをセットする.
   *
   * @param hdnCustomerCode hdnCustomerCode
   */
  public void setHdnCustomerCode(String hdnCustomerCode) {
    this.hdnCustomerCode = hdnCustomerCode;
  }

  
  /**
   * hdnShopCodeを取得する.
   *
   * @return String hdnShopCode
   */
  public String getHdnShopCode() {
    return hdnShopCode;
  }

  
  /**
   * hdnShopCodeをセットする.
   *
   * @param hdnShopCode hdnShopCode
   */
  public void setHdnShopCode(String hdnShopCode) {
    this.hdnShopCode = hdnShopCode;
  }

  
  /**
   * hdnDeliDateを取得する.
   *
   * @return String hdnDeliDate
   */
  public String getHdnDeliDate() {
    return hdnDeliDate;
  }

  
  /**
   * hdnDeliDateをセットする.
   *
   * @param hdnDeliDate hdnDeliDate
   */
  public void setHdnDeliDate(String hdnDeliDate) {
    this.hdnDeliDate = hdnDeliDate;
  }

  
  /**
   * hdnBinKbを取得する.
   *
   * @return String hdnBinKb
   */
  public String getHdnBinKb() {
    return hdnBinKb;
  }

  
  /**
   * hdnBinKbをセットする.
   *
   * @param hdnBinKb hdnBinKb
   */
  public void setHdnBinKb(String hdnBinKb) {
    this.hdnBinKb = hdnBinKb;
  }

  
  /**
   * hdnPriceMaxを取得する.
   *
   * @return String hdnPriceMax
   */
  public String getHdnPriceMax() {
    return hdnPriceMax;
  }

  
  /**
   * hdnPriceMaxをセットする.
   *
   * @param hdnPriceMax hdnPriceMax
   */
  public void setHdnPriceMax(String hdnPriceMax) {
    this.hdnPriceMax = hdnPriceMax;
  }

  
  /**
   * hdnJsonBillSrcを取得する.
   *
   * @return String hdnJsonBillSrc
   */
  public String getHdnJsonBillSrc() {
    return hdnJsonBillSrc;
  }

  
  /**
   * hdnJsonBillSrcをセットする.
   *
   * @param hdnJsonBillSrc hdnJsonBillSrc
   */
  public void setHdnJsonBillSrc(String hdnJsonBillSrc) {
    this.hdnJsonBillSrc = hdnJsonBillSrc;
  }

  
  /**
   * hdnContextPathを取得する.
   *
   * @return String hdnContextPath
   */
  public String getHdnContextPath() {
    return hdnContextPath;
  }

  
  /**
   * hdnContextPathをセットする.
   *
   * @param hdnContextPath hdnContextPath
   */
  public void setHdnContextPath(String hdnContextPath) {
    this.hdnContextPath = hdnContextPath;
  }

  
  /**
   * hdnItemJsonを取得する.
   *
   * @return String hdnItemJson
   */
  public String getHdnItemJson() {
    return hdnItemJson;
  }

  
  /**
   * hdnItemJsonをセットする.
   *
   * @param hdnItemJson hdnItemJson
   */
  public void setHdnItemJson(String hdnItemJson) {
    this.hdnItemJson = hdnItemJson;
  }

  
  /**
   * hdnRegistUserCodeを取得する.
   *
   * @return String hdnRegistUserCode
   */
  public String getHdnRegistUserCode() {
    return hdnRegistUserCode;
  }

  
  /**
   * hdnRegistUserCodeをセットする.
   *
   * @param hdnRegistUserCode hdnRegistUserCode
   */
  public void setHdnRegistUserCode(String hdnRegistUserCode) {
    this.hdnRegistUserCode = hdnRegistUserCode;
  }

  
  /**
   * hdnOperateModeを取得する.
   *
   * @return String hdnOperateMode
   */
  public String getHdnOperateMode() {
    return hdnOperateMode;
  }

  
  /**
   * hdnOperateModeをセットする.
   *
   * @param hdnOperateMode hdnOperateMode
   */
  public void setHdnOperateMode(String hdnOperateMode) {
    this.hdnOperateMode = hdnOperateMode;
  }

  
  /**
   * hdnTotalDeliAmtを取得する.
   *
   * @return String hdnTotalDeliAmt
   */
  public String getHdnTotalDeliAmt() {
    return hdnTotalDeliAmt;
  }

  
  /**
   * hdnTotalDeliAmtをセットする.
   *
   * @param hdnTotalDeliAmt hdnTotalDeliAmt
   */
  public void setHdnTotalDeliAmt(String hdnTotalDeliAmt) {
    this.hdnTotalDeliAmt = hdnTotalDeliAmt;
  }

  
  /**
   * txtSlipNoを取得する.
   *
   * @return String txtSlipNo
   */
  public String getTxtSlipNo() {
    return txtSlipNo;
  }

  
  /**
   * txtSlipNoをセットする.
   *
   * @param txtSlipNo txtSlipNo
   */
  public void setTxtSlipNo(String txtSlipNo) {
    this.txtSlipNo = txtSlipNo;
  }

  
  /**
   * hdnSlipNoを取得する.
   *
   * @return String hdnSlipNo
   */
  public String getHdnSlipNo() {
    return hdnSlipNo;
  }

  
  /**
   * hdnSlipNoをセットする.
   *
   * @param hdnSlipNo hdnSlipNo
   */
  public void setHdnSlipNo(String hdnSlipNo) {
    this.hdnSlipNo = hdnSlipNo;
  }

  
  /**
   * hdnSlipIdxを取得する.
   *
   * @return short hdnSlipIdx
   */
  public short getHdnSlipIdx() {
    return hdnSlipIdx;
  }

  
  /**
   * hdnSlipIdxをセットする.
   *
   * @param hdnSlipIdx hdnSlipIdx
   */
  public void setHdnSlipIdx(short hdnSlipIdx) {
    this.hdnSlipIdx = hdnSlipIdx;
  }

  
  /**
   * txtJigyoCodeを取得する.
   *
   * @return String txtJigyoCode
   */
  public String getTxtJigyoCode() {
    return txtJigyoCode;
  }

  
  /**
   * txtJigyoCodeをセットする.
   *
   * @param txtJigyoCode txtJigyoCode
   */
  public void setTxtJigyoCode(String txtJigyoCode) {
    this.txtJigyoCode = txtJigyoCode;
  }

  
  /**
   * hdnJigyoCodeを取得する.
   *
   * @return String hdnJigyoCode
   */
  public String getHdnJigyoCode() {
    return hdnJigyoCode;
  }

  
  /**
   * hdnJigyoCodeをセットする.
   *
   * @param hdnJigyoCode hdnJigyoCode
   */
  public void setHdnJigyoCode(String hdnJigyoCode) {
    this.hdnJigyoCode = hdnJigyoCode;
  }

  
  /**
   * hdnJigyoNameを取得する.
   *
   * @return String hdnJigyoName
   */
  public String getHdnJigyoName() {
    return hdnJigyoName;
  }

  
  /**
   * hdnJigyoNameをセットする.
   *
   * @param hdnJigyoName hdnJigyoName
   */
  public void setHdnJigyoName(String hdnJigyoName) {
    this.hdnJigyoName = hdnJigyoName;
  }

  
  /**
   * hdnUpdateDateTimeを取得する.
   *
   * @return String hdnUpdateDateTime
   */
  public String getHdnUpdateDateTime() {
    return hdnUpdateDateTime;
  }

  
  /**
   * hdnUpdateDateTimeをセットする.
   *
   * @param hdnUpdateDateTime hdnUpdateDateTime
   */
  public void setHdnUpdateDateTime(String hdnUpdateDateTime) {
    this.hdnUpdateDateTime = hdnUpdateDateTime;
  }

  
  /**
   * strTaxFrcKbを取得する.
   *
   * @return String strTaxFrcKb
   */
  public String getStrTaxFrcKb() {
    return strTaxFrcKb;
  }

  
  /**
   * strTaxFrcKbをセットする.
   *
   * @param strTaxFrcKb strTaxFrcKb
   */
  public void setStrTaxFrcKb(String strTaxFrcKb) {
    this.strTaxFrcKb = strTaxFrcKb;
  }

}