/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.uri
 * ファイル名:FormUri0101d01.java
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

/**
 * フォーム(modelAttribute="FormUri0101d01"(売上登録) とリンク）
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormUri0101d01 {

  /** システム管理者フラグ */
  private String hdnSysAdminFlg;

  /** ログイン事業所コード */
  private String hdnLoginJigyoCode;

  /** 納品上限金額 */
  private String hdnPriceMax;

  /** 納品日加算日数 */
  private String hdnAddDay;

  /** 業務日付 */
  private String hdnAplDate;

  /** 会計月 */
  private String hdnDetermMonth;

  /** コンテキストパス */
  private String hdnContextPath;

  /** エラーコントロール */
  private String errorControl;

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

  /** 得意先伝票No */
  private String hdnCustomerSlipNo;
  
  /** 事業所コード */
  private String txtJigyoCode;
  private String hdnJigyoCode;
  private String hdnJigyoName;

  /** 伝票種別 */
  private String txtSlipOut;
  private String hdnSlipOut;

  /** 得意先コード */
  private String txtCustomerCode;
  private String hdnCustomerCode;
  private String hdnCustomerName;
  private String hdnCustomerNameR;
  private String hdnCustomerNameK;
  private String hdnShopKb;

  /** 店舗コード */
  private String txtShopCode;
  private String hdnShopCode;
  private String hdnShopName;
  private String hdnShopNameR;
  private String hdnShopNameK;

  /** 伝票区分 */
  private String ddlSlipKb;
  private String hdnSlipKb;

  /** 修正区分 */
  private String ddlModifyKb;

  /** 出荷伝票 */
  private String slipType;
  private String hdnSlipId;
  private String hdnSlipType;
  private short hdnSlipLine;
  private String hdnRegistUserName;
  private String hdnRegistUserNameKana;

  /** 納品日 */
  private String txtDeliDateYear;
  private String txtDeliDateMonth;
  private String txtDeliDateDay;
  private String txtDeliDate;
  private String hdnDeliYear;
  private String hdnDeliMonth;
  private String hdnDeliDay;
  private String hdnDeliYmd;

  /** 計上日 */
  private String txtAccountDay;
  private String hdnAccountDay;
  private String hdnAccountYmd;

  /** 返品／欠品元伝票No */
  private String txtOriginSlipNo;
  private String hdnOriginSlipNo;

  /** 便区分 */
  private String txtBinKb;
  private String hdnBinKb;

  /** 納品区分 */
  private String txtDeliKb;
  private String hdnDeliKb;

  /** 販売区分 */
  private String txtSalesKb;
  private String hdnSalesKb;

  /** コース */
  private String hdnCourseCode;
  private String hdnCourseName;
  private String hdnCourseNameR;

  /** 先方伝票No */
  private String txtSnpSlipNo;
  private String hdnSnpSlipNo;

  /** チェーンコード */
  private short hdnChainCode;
  /** チェーン枝番 */
  private short hdnChainIdx;
  /** チェーン名 */
  private String hdnChainName;
  /** 納品先コード */
  private short hdnDeliCode;
  /** 納品先名 */
  private String hdnDeliName;
  /** 納品先名カナ */
  private String hdnDeliNameKana;
  /** 金額丸め区分 */
  private String hdnBillAmtRudKb;
  /** 仕切丸め区分 */
  private String hdnBillRudKb;
  /** 仕切丸め少数桁 */
  private String hdnBillRudPoint;
  /** チェーン販売区分 */
  private String hdnChainSalesKb;
  /** 分類コード */
  private String hdnClassCodeHead;
  /** 分類コード_品目価格情報 */
  private String hdnClassCodeItem;
  /** 更新日時 */
  private String hdnUpdateDateTime;
  /** 取消区分（0：受注取消、1：受注データ無効化） */
  private String hdnCancelMode;
  /** 自社伝票No（売上照会からのパラメータ） */
  private String hdnPrmDenNo;
  /** 処理区分（売上照会からのパラメータ） */
  private String hdnPrmShoriKb;
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
   * hdnAddDayを取得する.
   *
   * @return String hdnAddDay
   */
  public String getHdnAddDay() {
    return hdnAddDay;
  }
  
  /**
   * hdnAddDayをセットする.
   *
   * @param hdnAddDay hdnAddDay
   */
  public void setHdnAddDay(String hdnAddDay) {
    this.hdnAddDay = hdnAddDay;
  }
  
  /**
   * hdnAplDateを取得する.
   *
   * @return String hdnAplDate
   */
  public String getHdnAplDate() {
    return hdnAplDate;
  }
  
  /**
   * hdnAplDateをセットする.
   *
   * @param hdnAplDate hdnAplDate
   */
  public void setHdnAplDate(String hdnAplDate) {
    this.hdnAplDate = hdnAplDate;
  }
  
  /**
   * hdnDetermMonthを取得する.
   *
   * @return String hdnDetermMonth
   */
  public String getHdnDetermMonth() {
    return hdnDetermMonth;
  }
  
  /**
   * hdnDetermMonthをセットする.
   *
   * @param hdnDetermMonth hdnDetermMonth
   */
  public void setHdnDetermMonth(String hdnDetermMonth) {
    this.hdnDetermMonth = hdnDetermMonth;
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
   * errorControlを取得する.
   *
   * @return String errorControl
   */
  public String getErrorControl() {
    return errorControl;
  }
  
  /**
   * errorControlをセットする.
   *
   * @param errorControl errorControl
   */
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
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
   * @return long hdnSlipNo
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
   * hdnCustomerSlipNoを取得する.
   *
   * @return String hdnCustomerSlipNo
   */
  public String getHdnCustomerSlipNo() {
    return hdnCustomerSlipNo;
  }

  /**
   * hdnCustomerSlipNoをセットする.
   *
   * @param hdnCustomerSlipNo hdnCustomerSlipNo
   */
  public void setHdnCustomerSlipNo(String hdnCustomerSlipNo) {
    this.hdnCustomerSlipNo = hdnCustomerSlipNo;
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
   * txtSlipOutを取得する.
   *
   * @return String txtSlipOut
   */
  public String getTxtSlipOut() {
    return txtSlipOut;
  }
  
  /**
   * txtSlipOutをセットする.
   *
   * @param txtSlipOut txtSlipOut
   */
  public void setTxtSlipOut(String txtSlipOut) {
    this.txtSlipOut = txtSlipOut;
  }
  
  /**
   * hdnSlipOutを取得する.
   *
   * @return String hdnSlipOut
   */
  public String getHdnSlipOut() {
    return hdnSlipOut;
  }
  
  /**
   * hdnSlipOutをセットする.
   *
   * @param hdnSlipOut hdnSlipOut
   */
  public void setHdnSlipOut(String hdnSlipOut) {
    this.hdnSlipOut = hdnSlipOut;
  }
  
  /**
   * txtCustomerCodeを取得する.
   *
   * @return String txtCustomerCode
   */
  public String getTxtCustomerCode() {
    return txtCustomerCode;
  }
  
  /**
   * txtCustomerCodeをセットする.
   *
   * @param txtCustomerCode txtCustomerCode
   */
  public void setTxtCustomerCode(String txtCustomerCode) {
    this.txtCustomerCode = txtCustomerCode;
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
   * hdnCustomerNameを取得する.
   *
   * @return String hdnCustomerName
   */
  public String getHdnCustomerName() {
    return hdnCustomerName;
  }
  
  /**
   * hdnCustomerNameをセットする.
   *
   * @param hdnCustomerName hdnCustomerName
   */
  public void setHdnCustomerName(String hdnCustomerName) {
    this.hdnCustomerName = hdnCustomerName;
  }
  
  /**
   * hdnCustomerNameRを取得する.
   *
   * @return String hdnCustomerNameR
   */
  public String getHdnCustomerNameR() {
    return hdnCustomerNameR;
  }
  
  /**
   * hdnCustomerNameRをセットする.
   *
   * @param hdnCustomerNameR hdnCustomerNameR
   */
  public void setHdnCustomerNameR(String hdnCustomerNameR) {
    this.hdnCustomerNameR = hdnCustomerNameR;
  }
  
  /**
   * hdnCustomerNameKを取得する.
   *
   * @return String hdnCustomerNameK
   */
  public String getHdnCustomerNameK() {
    return hdnCustomerNameK;
  }
  
  /**
   * hdnCustomerNameKをセットする.
   *
   * @param hdnCustomerNameK hdnCustomerNameK
   */
  public void setHdnCustomerNameK(String hdnCustomerNameK) {
    this.hdnCustomerNameK = hdnCustomerNameK;
  }
  
  /**
   * hdnShopKbを取得する.
   *
   * @return String hdnShopKb
   */
  public String getHdnShopKb() {
    return hdnShopKb;
  }
  
  /**
   * hdnShopKbをセットする.
   *
   * @param hdnShopKb hdnShopKb
   */
  public void setHdnShopKb(String hdnShopKb) {
    this.hdnShopKb = hdnShopKb;
  }
  
  /**
   * txtShopCodeを取得する.
   *
   * @return String txtShopCode
   */
  public String getTxtShopCode() {
    return txtShopCode;
  }
  
  /**
   * txtShopCodeをセットする.
   *
   * @param txtShopCode txtShopCode
   */
  public void setTxtShopCode(String txtShopCode) {
    this.txtShopCode = txtShopCode;
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
   * hdnShopNameを取得する.
   *
   * @return String hdnShopName
   */
  public String getHdnShopName() {
    return hdnShopName;
  }
  
  /**
   * hdnShopNameをセットする.
   *
   * @param hdnShopName hdnShopName
   */
  public void setHdnShopName(String hdnShopName) {
    this.hdnShopName = hdnShopName;
  }
  
  /**
   * hdnShopNameRを取得する.
   *
   * @return String hdnShopNameR
   */
  public String getHdnShopNameR() {
    return hdnShopNameR;
  }
  
  /**
   * hdnShopNameRをセットする.
   *
   * @param hdnShopNameR hdnShopNameR
   */
  public void setHdnShopNameR(String hdnShopNameR) {
    this.hdnShopNameR = hdnShopNameR;
  }
  
  /**
   * hdnShopNameKを取得する.
   *
   * @return String hdnShopNameK
   */
  public String getHdnShopNameK() {
    return hdnShopNameK;
  }
  
  /**
   * hdnShopNameKをセットする.
   *
   * @param hdnShopNameK hdnShopNameK
   */
  public void setHdnShopNameK(String hdnShopNameK) {
    this.hdnShopNameK = hdnShopNameK;
  }
  
  /**
   * ddlSlipKbを取得する.
   *
   * @return String ddlSlipKb
   */
  public String getDdlSlipKb() {
    return ddlSlipKb;
  }
  
  /**
   * ddlSlipKbをセットする.
   *
   * @param ddlSlipKb ddlSlipKb
   */
  public void setDdlSlipKb(String ddlSlipKb) {
    this.ddlSlipKb = ddlSlipKb;
  }
  
  /**
   * hdnSlipKbを取得する.
   *
   * @return String hdnSlipKb
   */
  public String getHdnSlipKb() {
    return hdnSlipKb;
  }
  
  /**
   * hdnSlipKbをセットする.
   *
   * @param hdnSlipKb hdnSlipKb
   */
  public void setHdnSlipKb(String hdnSlipKb) {
    this.hdnSlipKb = hdnSlipKb;
  }
  
  /**
   * ddlModifyKbを取得する.
   *
   * @return String ddlModifyKb
   */
  public String getDdlModifyKb() {
    return ddlModifyKb;
  }
  
  /**
   * ddlModifyKbをセットする.
   *
   * @param ddlModifyKb ddlModifyKb
   */
  public void setDdlModifyKb(String ddlModifyKb) {
    this.ddlModifyKb = ddlModifyKb;
  }
  
  /**
   * slipTypeを取得する.
   *
   * @return String slipType
   */
  public String getSlipType() {
    return slipType;
  }
  
  /**
   * slipTypeをセットする.
   *
   * @param slipType slipType
   */
  public void setSlipType(String slipType) {
    this.slipType = slipType;
  }
  
  /**
   * hdnSlipIdを取得する.
   *
   * @return String hdnSlipId
   */
  public String getHdnSlipId() {
    return hdnSlipId;
  }
  
  /**
   * hdnSlipIdをセットする.
   *
   * @param hdnSlipId hdnSlipId
   */
  public void setHdnSlipId(String hdnSlipId) {
    this.hdnSlipId = hdnSlipId;
  }
  
  /**
   * hdnSlipTypeを取得する.
   *
   * @return String hdnSlipType
   */
  public String getHdnSlipType() {
    return hdnSlipType;
  }
  
  /**
   * hdnSlipTypeをセットする.
   *
   * @param hdnSlipType hdnSlipType
   */
  public void setHdnSlipType(String hdnSlipType) {
    this.hdnSlipType = hdnSlipType;
  }
  
  /**
   * hdnSlipLineを取得する.
   *
   * @return short hdnSlipLine
   */
  public short getHdnSlipLine() {
    return hdnSlipLine;
  }
  
  /**
   * hdnSlipLineをセットする.
   *
   * @param hdnSlipLine hdnSlipLine
   */
  public void setHdnSlipLine(short hdnSlipLine) {
    this.hdnSlipLine = hdnSlipLine;
  }
  
  /**
   * hdnRegistUserNameを取得する.
   *
   * @return String hdnRegistUserName
   */
  public String getHdnRegistUserName() {
    return hdnRegistUserName;
  }
  
  /**
   * hdnRegistUserNameをセットする.
   *
   * @param hdnRegistUserName hdnRegistUserName
   */
  public void setHdnRegistUserName(String hdnRegistUserName) {
    this.hdnRegistUserName = hdnRegistUserName;
  }
  
  /**
   * hdnRegistUserNameKanaを取得する.
   *
   * @return String hdnRegistUserNameKana
   */
  public String getHdnRegistUserNameKana() {
    return hdnRegistUserNameKana;
  }
  
  /**
   * hdnRegistUserNameKanaをセットする.
   *
   * @param hdnRegistUserNameKana hdnRegistUserNameKana
   */
  public void setHdnRegistUserNameKana(String hdnRegistUserNameKana) {
    this.hdnRegistUserNameKana = hdnRegistUserNameKana;
  }
  
  /**
   * txtDeliDateYearを取得する.
   *
   * @return String txtDeliDateYear
   */
  public String getTxtDeliDateYear() {
    return txtDeliDateYear;
  }
  
  /**
   * txtDeliDateYearをセットする.
   *
   * @param txtDeliDateYear txtDeliDateYear
   */
  public void setTxtDeliDateYear(String txtDeliDateYear) {
    this.txtDeliDateYear = txtDeliDateYear;
  }
  
  /**
   * txtDeliDateMonthを取得する.
   *
   * @return String txtDeliDateMonth
   */
  public String getTxtDeliDateMonth() {
    return txtDeliDateMonth;
  }
  
  /**
   * txtDeliDateMonthをセットする.
   *
   * @param txtDeliDateMonth txtDeliDateMonth
   */
  public void setTxtDeliDateMonth(String txtDeliDateMonth) {
    this.txtDeliDateMonth = txtDeliDateMonth;
  }
  
  /**
   * txtDeliDateDayを取得する.
   *
   * @return String txtDeliDateDay
   */
  public String getTxtDeliDateDay() {
    return txtDeliDateDay;
  }
  
  /**
   * txtDeliDateDayをセットする.
   *
   * @param txtDeliDateDay txtDeliDateDay
   */
  public void setTxtDeliDateDay(String txtDeliDateDay) {
    this.txtDeliDateDay = txtDeliDateDay;
  }
  
  /**
   * txtDeliDateを取得する.
   *
   * @return String txtDeliDate
   */
  public String getTxtDeliDate() {
    return txtDeliDate;
  }
  
  /**
   * txtDeliDateをセットする.
   *
   * @param txtDeliDate txtDeliDate
   */
  public void setTxtDeliDate(String txtDeliDate) {
    this.txtDeliDate = txtDeliDate;
  }
  
  /**
   * hdnDeliYearを取得する.
   *
   * @return String hdnDeliYear
   */
  public String getHdnDeliYear() {
    return hdnDeliYear;
  }
  
  /**
   * hdnDeliYearをセットする.
   *
   * @param hdnDeliYear hdnDeliYear
   */
  public void setHdnDeliYear(String hdnDeliYear) {
    this.hdnDeliYear = hdnDeliYear;
  }
  
  /**
   * hdnDeliMonthを取得する.
   *
   * @return String hdnDeliMonth
   */
  public String getHdnDeliMonth() {
    return hdnDeliMonth;
  }
  
  /**
   * hdnDeliMonthをセットする.
   *
   * @param hdnDeliMonth hdnDeliMonth
   */
  public void setHdnDeliMonth(String hdnDeliMonth) {
    this.hdnDeliMonth = hdnDeliMonth;
  }
  
  /**
   * hdnDeliDayを取得する.
   *
   * @return String hdnDeliDay
   */
  public String getHdnDeliDay() {
    return hdnDeliDay;
  }
  
  /**
   * hdnDeliDayをセットする.
   *
   * @param hdnDeliDay hdnDeliDay
   */
  public void setHdnDeliDay(String hdnDeliDay) {
    this.hdnDeliDay = hdnDeliDay;
  }
  
  /**
   * hdnDeliYmdを取得する.
   *
   * @return String hdnDeliYmd
   */
  public String getHdnDeliYmd() {
    return hdnDeliYmd;
  }
  
  /**
   * hdnDeliYmdをセットする.
   *
   * @param hdnDeliYmd hdnDeliYmd
   */
  public void setHdnDeliYmd(String hdnDeliYmd) {
    this.hdnDeliYmd = hdnDeliYmd;
  }
  
  /**
   * txtAccountDayを取得する.
   *
   * @return String txtAccountDay
   */
  public String getTxtAccountDay() {
    return txtAccountDay;
  }
  
  /**
   * txtAccountDayをセットする.
   *
   * @param txtAccountDay txtAccountDay
   */
  public void setTxtAccountDay(String txtAccountDay) {
    this.txtAccountDay = txtAccountDay;
  }
  
  /**
   * hdnAccountDayを取得する.
   *
   * @return String hdnAccountDay
   */
  public String getHdnAccountDay() {
    return hdnAccountDay;
  }
  
  /**
   * hdnAccountDayをセットする.
   *
   * @param hdnAccountDay hdnAccountDay
   */
  public void setHdnAccountDay(String hdnAccountDay) {
    this.hdnAccountDay = hdnAccountDay;
  }
  
  /**
   * hdnAccountYmdを取得する.
   *
   * @return String hdnAccountYmd
   */
  public String getHdnAccountYmd() {
    return hdnAccountYmd;
  }
  
  /**
   * hdnAccountYmdをセットする.
   *
   * @param hdnAccountYmd hdnAccountYmd
   */
  public void setHdnAccountYmd(String hdnAccountYmd) {
    this.hdnAccountYmd = hdnAccountYmd;
  }
  
  /**
   * txtOriginSlipNoを取得する.
   *
   * @return String txtOriginSlipNo
   */
  public String getTxtOriginSlipNo() {
    return txtOriginSlipNo;
  }
  
  /**
   * txtOriginSlipNoをセットする.
   *
   * @param txtOriginSlipNo txtOriginSlipNo
   */
  public void setTxtOriginSlipNo(String txtOriginSlipNo) {
    this.txtOriginSlipNo = txtOriginSlipNo;
  }
  
  /**
   * hdnOriginSlipNoを取得する.
   *
   * @return String hdnOriginSlipNo
   */
  public String getHdnOriginSlipNo() {
    return hdnOriginSlipNo;
  }
  
  /**
   * hdnOriginSlipNoをセットする.
   *
   * @param hdnOriginSlipNo hdnOriginSlipNo
   */
  public void setHdnOriginSlipNo(String hdnOriginSlipNo) {
    this.hdnOriginSlipNo = hdnOriginSlipNo;
  }
  
  /**
   * txtBinKbを取得する.
   *
   * @return String txtBinKb
   */
  public String getTxtBinKb() {
    return txtBinKb;
  }
  
  /**
   * txtBinKbをセットする.
   *
   * @param txtBinKb txtBinKb
   */
  public void setTxtBinKb(String txtBinKb) {
    this.txtBinKb = txtBinKb;
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
   * txtDeliKbを取得する.
   *
   * @return String txtDeliKb
   */
  public String getTxtDeliKb() {
    return txtDeliKb;
  }
  
  /**
   * txtDeliKbをセットする.
   *
   * @param txtDeliKb txtDeliKb
   */
  public void setTxtDeliKb(String txtDeliKb) {
    this.txtDeliKb = txtDeliKb;
  }
  
  /**
   * hdnDeliKbを取得する.
   *
   * @return String hdnDeliKb
   */
  public String getHdnDeliKb() {
    return hdnDeliKb;
  }
  
  /**
   * hdnDeliKbをセットする.
   *
   * @param hdnDeliKb hdnDeliKb
   */
  public void setHdnDeliKb(String hdnDeliKb) {
    this.hdnDeliKb = hdnDeliKb;
  }
  
  /**
   * txtSalesKbを取得する.
   *
   * @return String txtSalesKb
   */
  public String getTxtSalesKb() {
    return txtSalesKb;
  }
  
  /**
   * txtSalesKbをセットする.
   *
   * @param txtSalesKb txtSalesKb
   */
  public void setTxtSalesKb(String txtSalesKb) {
    this.txtSalesKb = txtSalesKb;
  }
  
  /**
   * hdnSalesKbを取得する.
   *
   * @return String hdnSalesKb
   */
  public String getHdnSalesKb() {
    return hdnSalesKb;
  }
  
  /**
   * hdnSalesKbをセットする.
   *
   * @param hdnSalesKb hdnSalesKb
   */
  public void setHdnSalesKb(String hdnSalesKb) {
    this.hdnSalesKb = hdnSalesKb;
  }
  
  /**
   * hdnCourseCodeを取得する.
   *
   * @return String hdnCourseCode
   */
  public String getHdnCourseCode() {
    return hdnCourseCode;
  }
  
  /**
   * hdnCourseCodeをセットする.
   *
   * @param hdnCourseCode hdnCourseCode
   */
  public void setHdnCourseCode(String hdnCourseCode) {
    this.hdnCourseCode = hdnCourseCode;
  }
  
  /**
   * hdnCourseNameを取得する.
   *
   * @return String hdnCourseName
   */
  public String getHdnCourseName() {
    return hdnCourseName;
  }
  
  /**
   * hdnCourseNameをセットする.
   *
   * @param hdnCourseName hdnCourseName
   */
  public void setHdnCourseName(String hdnCourseName) {
    this.hdnCourseName = hdnCourseName;
  }
  
  /**
   * hdnCourseNameRを取得する.
   *
   * @return String hdnCourseNameR
   */
  public String getHdnCourseNameR() {
    return hdnCourseNameR;
  }
  
  /**
   * hdnCourseNameRをセットする.
   *
   * @param hdnCourseNameR hdnCourseNameR
   */
  public void setHdnCourseNameR(String hdnCourseNameR) {
    this.hdnCourseNameR = hdnCourseNameR;
  }
  
  /**
   * txtSnpSlipNoを取得する.
   *
   * @return String txtSnpSlipNo
   */
  public String getTxtSnpSlipNo() {
    return txtSnpSlipNo;
  }
  
  /**
   * txtSnpSlipNoをセットする.
   *
   * @param txtSnpSlipNo txtSnpSlipNo
   */
  public void setTxtSnpSlipNo(String txtSnpSlipNo) {
    this.txtSnpSlipNo = txtSnpSlipNo;
  }
  
  /**
   * hdnSnpSlipNoを取得する.
   *
   * @return String hdnCustomSlipNo
   */
  public String getHdnSnpSlipNo() {
    return hdnSnpSlipNo;
  }
  
  /**
   * hdnSnpSlipNoをセットする.
   *
   * @param hdnSnpSlipNo hdnSnpSlipNo
   */
  public void setHdnSnpSlipNo(String hdnSnpSlipNo) {
    this.hdnSnpSlipNo = hdnSnpSlipNo;
  }
  
  /**
   * hdnChainCodeを取得する.
   *
   * @return short hdnChainCode
   */
  public short getHdnChainCode() {
    return hdnChainCode;
  }
  
  /**
   * hdnChainCodeをセットする.
   *
   * @param hdnChainCode hdnChainCode
   */
  public void setHdnChainCode(short hdnChainCode) {
    this.hdnChainCode = hdnChainCode;
  }
  
  /**
   * hdnChainIdxを取得する.
   *
   * @return short hdnChainIdx
   */
  public short getHdnChainIdx() {
    return hdnChainIdx;
  }
  
  /**
   * hdnChainIdxをセットする.
   *
   * @param hdnChainIdx hdnChainIdx
   */
  public void setHdnChainIdx(short hdnChainIdx) {
    this.hdnChainIdx = hdnChainIdx;
  }
  
  /**
   * hdnChainNameを取得する.
   *
   * @return String hdnChainName
   */
  public String getHdnChainName() {
    return hdnChainName;
  }
  
  /**
   * hdnChainNameをセットする.
   *
   * @param hdnChainName hdnChainName
   */
  public void setHdnChainName(String hdnChainName) {
    this.hdnChainName = hdnChainName;
  }
  
  /**
   * hdnDeliCodeを取得する.
   *
   * @return short hdnDeliCode
   */
  public short getHdnDeliCode() {
    return hdnDeliCode;
  }
  
  /**
   * hdnDeliCodeをセットする.
   *
   * @param hdnDeliCode hdnDeliCode
   */
  public void setHdnDeliCode(short hdnDeliCode) {
    this.hdnDeliCode = hdnDeliCode;
  }
  
  /**
   * hdnDeliNameを取得する.
   *
   * @return String hdnDeliName
   */
  public String getHdnDeliName() {
    return hdnDeliName;
  }
  
  /**
   * hdnDeliNameをセットする.
   *
   * @param hdnDeliName hdnDeliName
   */
  public void setHdnDeliName(String hdnDeliName) {
    this.hdnDeliName = hdnDeliName;
  }
  
  /**
   * hdnDeliNameKanaを取得する.
   *
   * @return String hdnDeliNameKana
   */
  public String getHdnDeliNameKana() {
    return hdnDeliNameKana;
  }
  
  /**
   * hdnDeliNameKanaをセットする.
   *
   * @param hdnDeliNameKana hdnDeliNameKana
   */
  public void setHdnDeliNameKana(String hdnDeliNameKana) {
    this.hdnDeliNameKana = hdnDeliNameKana;
  }
  
  /**
   * hdnBillAmtRudKbを取得する.
   *
   * @return String hdnBillAmtRudKb
   */
  public String getHdnBillAmtRudKb() {
    return hdnBillAmtRudKb;
  }
  
  /**
   * hdnBillAmtRudKbをセットする.
   *
   * @param hdnBillAmtRudKb hdnBillAmtRudKb
   */
  public void setHdnBillAmtRudKb(String hdnBillAmtRudKb) {
    this.hdnBillAmtRudKb = hdnBillAmtRudKb;
  }
  
  /**
   * hdnBillRudKbを取得する.
   *
   * @return String hdnBillRudKb
   */
  public String getHdnBillRudKb() {
    return hdnBillRudKb;
  }
  
  /**
   * hdnBillRudKbをセットする.
   *
   * @param hdnBillRudKb hdnBillRudKb
   */
  public void setHdnBillRudKb(String hdnBillRudKb) {
    this.hdnBillRudKb = hdnBillRudKb;
  }
  
  /**
   * hdnBillRudPointを取得する.
   *
   * @return String hdnBillRudPoint
   */
  public String getHdnBillRudPoint() {
    return hdnBillRudPoint;
  }
  
  /**
   * hdnBillRudPointをセットする.
   *
   * @param hdnBillRudPoint hdnBillRudPoint
   */
  public void setHdnBillRudPoint(String hdnBillRudPoint) {
    this.hdnBillRudPoint = hdnBillRudPoint;
  }
  
  /**
   * hdnChainSalesKbを取得する.
   *
   * @return String hdnChainSalesKb
   */
  public String getHdnChainSalesKb() {
    return hdnChainSalesKb;
  }
  
  /**
   * hdnChainSalesKbをセットする.
   *
   * @param hdnChainSalesKb hdnChainSalesKb
   */
  public void setHdnChainSalesKb(String hdnChainSalesKb) {
    this.hdnChainSalesKb = hdnChainSalesKb;
  }
  
  /**
   * hdnClassCodeHeadを取得する.
   *
   * @return String hdnClassCodeHead
   */
  public String getHdnClassCodeHead() {
    return hdnClassCodeHead;
  }
  
  /**
   * hdnClassCodeHeadをセットする.
   *
   * @param hdnClassCodeHead hdnClassCodeHead
   */
  public void setHdnClassCodeHead(String hdnClassCodeHead) {
    this.hdnClassCodeHead = hdnClassCodeHead;
  }
  
  /**
   * hdnClassCodeItemを取得する.
   *
   * @return String hdnClassCodeItem
   */
  public String getHdnClassCodeItem() {
    return hdnClassCodeItem;
  }
  
  /**
   * hdnClassCodeItemをセットする.
   *
   * @param hdnClassCodeItem hdnClassCodeItem
   */
  public void setHdnClassCodeItem(String hdnClassCodeItem) {
    this.hdnClassCodeItem = hdnClassCodeItem;
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
   * hdnCancelModeを取得する.
   *
   * @return String hdnCancelMode
   */
  public String getHdnCancelMode() {
    return hdnCancelMode;
  }

  /**
   * hdnCancelModeをセットする.
   *
   * @param hdnCancelMode hdnCancelMode
   */
  public void setHdnCancelMode(String hdnCancelMode) {
    this.hdnCancelMode = hdnCancelMode;
  }

  /**
   * hdnPrmDenNoを取得する.
   *
   * @return String hdnPrmDenNo
   */
  public String getHdnPrmDenNo() {
    return hdnPrmDenNo;
  }

  /**
   * hdnPrmDenNoをセットする.
   *
   * @param hdnPrmDenNo hdnPrmDenNo
   */
  public void setHdnPrmDenNo(String hdnPrmDenNo) {
    this.hdnPrmDenNo = hdnPrmDenNo;
  }

  /**
   * hdnPrmShoriKbを取得する.
   *
   * @return String hdnPrmShoriKb
   */
  public String getHdnPrmShoriKb() {
    return hdnPrmShoriKb;
  }

  /**
   * hdnPrmShoriKbをセットする.
   *
   * @param hdnPrmShoriKb hdnPrmShoriKb
   */
  public void setHdnPrmShoriKb(String hdnPrmShoriKb) {
    this.hdnPrmShoriKb = hdnPrmShoriKb;
  }
}