/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0102d02.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.ArrayList;

/**
 * フォーム(jspの modelAttribute="FormMst0102d02" とリンク）
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormMst0102d02 {

  // ヘッダ部
  // 得意先コード
  private String txtCustomerCode;
  // 得意先フラグ
  private boolean chkCustomer;
  // 請求先フラグ
  private boolean chkBilling;
  // 請求先コード
  private String txtBillingCode;
  // 請求先名称
  private String txtBillingSearchNameHidden;
  // チェーンコード
  private String txtChainCode;
  // チェーン枝番
  private String txtChainEda;
  // チェーン名称
  private String txtChainNameHidden;

  // ボディ部
  // 得意先名称
  private String txtCustomerName;
  // 得意先名称カナ
  private String txtCustomerNameKana;
  // 得意先略称
  private String txtCustomerNameR;
  // 郵便番号
  private String txtPostalCode1;
  private String txtPostalCode2;
  private String txtPostalCodeResult;
  // 住所１
  private String txtAddress1;
  // 住所２
  private String txtAddress2;
  // 電話番号
  private String txtTel1;
  private String txtTel2;
  private String txtTel3;
  private String txtTelResult;
  // FAX番号
  private String txtFax1;
  private String txtFax2;
  private String txtFax3;
  private String txtFaxResult;
  // 得意先担当者
  private String txtCustomerTantousha;
  // メールアドレス
  private String txtMailAddress;
  // 取扱事業所
  private ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho;
  private String toriatsukaiJigyouShoId;
  // 幹事事業所
  private String ddlKanjiJigyouSho;
  private String txtKanjiJigyouShoHidden;
  // 得意先種別
  private String ddlCustomerType;
  private String txtCustomerTypeHidden;
  // 店舗区分
  private String ddlTenpoKubun;
  private String txtTenpoKubunHidden;
  // 業態区分
  private String ddlGyoutaiKubun;
  private String txtGyoutaiKubunHidden;
  // 納品センター
  private String ddlDeliveryCenter;
  private String txtDeliveryCenterHidden;
  private String txtDeliveryCenterDataChanged;
  // 関係会社種別
  private String ddlKankeiKaishaType;
  private String txtKankeiKaishaTypeHidden;
  // 補助科目
  private String ddlHojoKamoku;
  private String txtHojoKamokuHidden;
  // 採番区分
  private String ddlSaibanKubun;
  private String txtSaibanKubunHidden;
  // YG取引区分
  private String ddlYgTorihikiKubun;
  private String txtYgTorihikiKubunHidden;
  // 内税顧客区分
  private String ddlUchiZeiKoKyakuKubun;
  private String txtUchiZeiKoKyakuKubunHidden;
  // 内税消費税端数処理
  private String ddlUchiZeiShori;
  private String txtUchiZeiShoriHidden;
  // 集金有無
  private String ddlShuukinUmu;
  private String txtShuukinUmuHidden;
  // 現金集金マーク印字
  private String ddlGenkinShuukinMark;
  private String txtGenkinShuukinMarkHidden;
  // 集計出力FLG
  private String ddlShuukinOutputFlag;
  private String txtShuukinOutputFlagHidden;
  // 手入力伝票発行
  private String ddlManualInputBilling;
  private String txtManualInputBillingHidden;
  // 手入力出荷伝票
  private String ddlManualInputDelivery;
  private String txtManualInputDeliveryHidden;
  // 伝票行計算金額まるめ
  private String ddlSlipLineCalculationAmountRounding;
  private String txtSlipLineCalculationAmountRoundingHidden;
  // 出荷伝票出力品コード
  private String ddlDeliveryOutputProductCode;
  private String txtDeliveryOutputProductCodeHidden;
  // 請求先名称
  private String txtBillingName;
  // 請求先名称カナ
  private String txtBillingNameKana;
  // 請求先略称
  private String txtBillingNameR;
  // 請求先郵便番号
  private String txtBillingZipCode1;
  private String txtBillingZipCode2;
  private String txtBillingZipCodeResult;
  // 住所１
  private String txtBillingAddress1;
  // 住所２
  private String txtBillingAddress2;
  // 電話番号
  private String txtBillingTel1;
  private String txtBillingTel2;
  private String txtBillingTel3;
  private String txtBillingTelResult;
  // FAX番号
  private String txtBillingFax1;
  private String txtBillingFax2;
  private String txtBillingFax3;
  private String txtBillingFaxResult;
  // 請求単位
  private String ddlBillingUnit;
  private String txtBillingUnitHidden;
  // 請求書単価
  private String ddlBillingUnitPrice;
  private String txtBillingUnitPriceHidden;
  // 請求書種類
  private String ddlBillingType;
  private String txtBillingTypeHidden;
  // 取纏め請求有無
  private String ddlBillingToriMatomeUMu;
  private String txtBillingToriMatomeUMuHidden;
  // 取纏め請求事業所
  private String ddlBillingToriMatomeJigyouSho;
  private String txtBillingToriMatomeJigyouShoHidden;
  // 請求書パターン
  private String ddlBillingPattern;
  private String txtBillingPatternHidden;
  // 請求書住所印字
  private String ddlBillingAddressPrint;
  private String txtBillingAddressPrintHidden;
  // 請求集計表区分
  private String ddlBillingShuukeiHyouKubun;
  private String txtBillingShuukeiHyouKubunHidden;
  // 消費税計算単位
  private String ddlBillingShouhizeiCalculationUnit;
  private String txtBillingShouhizeiCalculationUnitHidden;
  // 請求チェックリスト 出力対象
  private String ddlBillingCheckListOutputTarget;
  private String txtBillingCheckListOutputTargetHidden;
  // 消費税端数処理
  private String ddlBillingShouhizeiShori;
  private String txtBillingShouhizeiShoriHidden;
  // 請求チェックリスト 出力順
  private String ddlBillingCheckListOutputOrder;
  private String txtBillingCheckListOutputOrderHidden;
  // 締日１
  private String txtCloseDay1;
  // 回収月区分１
  private String ddlRecoveryMonthKubun1;
  private String txtRecoveryMonthKubun1Hidden;
  // 回収日１
  private String txtRecoveryDay1;
  // 締日２
  private String txtCloseDay2;
  // 回収月区分２
  private String ddlRecoveryMonthKubun2;
  private String txtRecoveryMonthKubun2Hidden;
  // 回収日２
  private String txtRecoveryDay2;
  // 入金種別
  private String ddlPaymentType;
  private String txtPaymentTypeHidden;
  // 入金口座
  private String ddlPaymentAccount;
  private String txtPaymentAccountHidden;
  // 手形サイト
  private String txtNoteSite;
  // 取引コード
  private String txtTorihikiCode;
  // 分類コード（定番、店直）
  private String txtBunruiCodeTeibanShop;
  // 伝票区分（定番、店直）
  private String txtDenpyouKubunTeibanShop;
  // 分類コード（定番、センター）
  private String txtBunruiCodeTeibanCenter;
  // 伝票区分（定番、センター）
  private String txtDenpyouKubunTeibanCenter;
  // 分類コード（特売、店直）
  private String txtBunruiCodeTokubaiShop;
  // 伝票区分（特売、店直）
  private String txtDenpyouKubunTokubaiShop;
  // 分類コード（特売、センター）
  private String txtBunruiCodeTokubaiCenter;
  // 伝票区分（特売、センター）
  private String txtDenpyouKubunTokubaiCenter;
  // 受領データ突合区分
  private String ddlJuryouDataKubun;
  private String txtJuryouDataKubunHidden;
  // 請求データ区分
  private String ddlBillingDataKubun;
  private String txtBillingDataKubunHidden;
  // 修正データ突合区分
  private String ddlModifyDataKubun;
  private String txtModifyDataKubunHidden;
  // 請求先支払いデータ区分
  private String ddlBillingPaymentDataKubun;
  private String txtBillingPaymentDataKubunHidden;
  // （修正種別）返品データ
  private boolean chkModifyTypeHenpinData;
  // （修正種別）欠品データ
  private boolean chkModifyTypeKetsuhinData;
  // （修正種別）修正データ
  private boolean chkModifyTypeShuuseiData;
  // 請求データ配信ＩＤ
  private String txtBillingDataDeliveryId;
  // 集計コード１
  private String txtShuukeiCode1;
  // 集計コード２
  private String txtShuukeiCode2;
  // 使用中止日
  private String txtShiyouChuushiDay;
  // 状況コード
  private String txtStatusCode;
  // コース情報
  private ArrayList<MstCourseInformation> arrListCourseInformation;

  // Mst0102d01のフォーム
  // 事業所
  private String form1DdlJigyouSho;
  // 得意先コード
  private String form1TxtCustomerCode;
  // 得意先名称
  private String form1TxtCustomerName;
  // チェーンコード
  private String form1TxtChainCode;
  // チェーン枝番
  private String form1TxtChainEda;
  // 営業担当者コード
  private String form1TxtEigyouTantoushaCode;
  // 事務担当者コード
  private String form1TxtJimuTantoushaCode;
  // 得意先種別
  private String form1DdlCustomerType;
  // 内税顧客区分
  private String form1DdlUchiZeiKoKyakuKubun;
  // 取消データを対象とする
  private boolean form1ChkCancelData;
  // 得意先を対象とする
  private boolean form1ChkCustomer;
  // 請求先を対象とする
  private boolean form1ChkBilling;
  // 得意先かつ請求先を対象とする
  private boolean form1ChkCustomerBilling;

  // hidden項目
  // 業務日付
  private Integer businessDate;
  // 表示モード
  private String viewMode;
  // システム管理者フラグ
  private String sysAdminFlag;
  // ログイン事業所コード
  private String loginJigyouShoCode;
  // 排他日付
  private String haitaDate;
  // 排他時間
  private String haitaTime;

  public String getTxtCustomerCode() {
    return txtCustomerCode;
  }

  public void setTxtCustomerCode(String txtCustomerCode) {
    this.txtCustomerCode = txtCustomerCode;
  }

  public boolean isChkCustomer() {
    return chkCustomer;
  }

  public void setChkCustomer(boolean chkCustomer) {
    this.chkCustomer = chkCustomer;
  }

  public boolean isChkBilling() {
    return chkBilling;
  }

  public void setChkBilling(boolean chkBilling) {
    this.chkBilling = chkBilling;
  }

  public String getTxtBillingCode() {
    return txtBillingCode;
  }

  public void setTxtBillingCode(String txtBillingCode) {
    this.txtBillingCode = txtBillingCode;
  }

  public String getTxtBillingSearchNameHidden() {
    return txtBillingSearchNameHidden;
  }

  public void setTxtBillingSearchNameHidden(String txtBillingSearchNameHidden) {
    this.txtBillingSearchNameHidden = txtBillingSearchNameHidden;
  }

  public String getTxtChainCode() {
    return txtChainCode;
  }

  public void setTxtChainCode(String txtChainCode) {
    this.txtChainCode = txtChainCode;
  }

  public String getTxtChainEda() {
    return txtChainEda;
  }

  public void setTxtChainEda(String txtChainEda) {
    this.txtChainEda = txtChainEda;
  }

  public String getTxtChainNameHidden() {
    return txtChainNameHidden;
  }

  public void setTxtChainNameHidden(String txtChainNameHidden) {
    this.txtChainNameHidden = txtChainNameHidden;
  }

  public String getTxtCustomerName() {
    return txtCustomerName;
  }

  public void setTxtCustomerName(String txtCustomerName) {
    this.txtCustomerName = txtCustomerName;
  }

  public String getTxtCustomerNameKana() {
    return txtCustomerNameKana;
  }

  public void setTxtCustomerNameKana(String txtCustomerNameKana) {
    this.txtCustomerNameKana = txtCustomerNameKana;
  }

  public String getTxtCustomerNameR() {
    return txtCustomerNameR;
  }

  public void setTxtCustomerNameR(String txtCustomerNameR) {
    this.txtCustomerNameR = txtCustomerNameR;
  }

  public String getTxtPostalCode1() {
    return txtPostalCode1;
  }

  public void setTxtPostalCode1(String txtPostalCode1) {
    this.txtPostalCode1 = txtPostalCode1;
  }

  public String getTxtPostalCode2() {
    return txtPostalCode2;
  }

  public void setTxtPostalCode2(String txtPostalCode2) {
    this.txtPostalCode2 = txtPostalCode2;
  }

  public String getTxtPostalCodeResult() {
    return txtPostalCodeResult;
  }

  public void setTxtPostalCodeResult(String txtPostalCodeResult) {
    this.txtPostalCodeResult = txtPostalCodeResult;
  }

  public String getTxtAddress1() {
    return txtAddress1;
  }

  public void setTxtAddress1(String txtAddress1) {
    this.txtAddress1 = txtAddress1;
  }

  public String getTxtAddress2() {
    return txtAddress2;
  }

  public void setTxtAddress2(String txtAddress2) {
    this.txtAddress2 = txtAddress2;
  }

  public String getTxtTel1() {
    return txtTel1;
  }

  public void setTxtTel1(String txtTel1) {
    this.txtTel1 = txtTel1;
  }

  public String getTxtTel2() {
    return txtTel2;
  }

  public void setTxtTel2(String txtTel2) {
    this.txtTel2 = txtTel2;
  }

  public String getTxtTel3() {
    return txtTel3;
  }

  public void setTxtTel3(String txtTel3) {
    this.txtTel3 = txtTel3;
  }

  public String getTxtTelResult() {
    return txtTelResult;
  }

  public void setTxtTelResult(String txtTelResult) {
    this.txtTelResult = txtTelResult;
  }

  public String getTxtFax1() {
    return txtFax1;
  }

  public void setTxtFax1(String txtFax1) {
    this.txtFax1 = txtFax1;
  }

  public String getTxtFax2() {
    return txtFax2;
  }

  public void setTxtFax2(String txtFax2) {
    this.txtFax2 = txtFax2;
  }

  public String getTxtFax3() {
    return txtFax3;
  }

  public void setTxtFax3(String txtFax3) {
    this.txtFax3 = txtFax3;
  }

  public String getTxtFaxResult() {
    return txtFaxResult;
  }

  public void setTxtFaxResult(String txtFaxResult) {
    this.txtFaxResult = txtFaxResult;
  }

  public String getTxtCustomerTantousha() {
    return txtCustomerTantousha;
  }

  public void setTxtCustomerTantousha(String txtCustomerTantousha) {
    this.txtCustomerTantousha = txtCustomerTantousha;
  }

  public String getTxtMailAddress() {
    return txtMailAddress;
  }

  public void setTxtMailAddress(String txtMailAddress) {
    this.txtMailAddress = txtMailAddress;
  }

  public ArrayList<MstToriatsukaiJigyouSho> getArrListToriatsukaiJigyouSho() {
    return arrListToriatsukaiJigyouSho;
  }

  public void setArrListToriatsukaiJigyouSho(
      ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho) {
    this.arrListToriatsukaiJigyouSho = arrListToriatsukaiJigyouSho;
  }

  public String getToriatsukaiJigyouShoId() {
    return toriatsukaiJigyouShoId;
  }

  public void setToriatsukaiJigyouShoId(String toriatsukaiJigyouShoId) {
    this.toriatsukaiJigyouShoId = toriatsukaiJigyouShoId;
  }

  public String getDdlKanjiJigyouSho() {
    return ddlKanjiJigyouSho;
  }

  public void setDdlKanjiJigyouSho(String ddlKanjiJigyouSho) {
    this.ddlKanjiJigyouSho = ddlKanjiJigyouSho;
  }

  public String getTxtKanjiJigyouShoHidden() {
    return txtKanjiJigyouShoHidden;
  }

  public void setTxtKanjiJigyouShoHidden(String txtKanjiJigyouShoHidden) {
    this.txtKanjiJigyouShoHidden = txtKanjiJigyouShoHidden;
  }

  public String getDdlCustomerType() {
    return ddlCustomerType;
  }

  public void setDdlCustomerType(String ddlCustomerType) {
    this.ddlCustomerType = ddlCustomerType;
  }

  public String getTxtCustomerTypeHidden() {
    return txtCustomerTypeHidden;
  }

  public void setTxtCustomerTypeHidden(String txtCustomerTypeHidden) {
    this.txtCustomerTypeHidden = txtCustomerTypeHidden;
  }

  public String getDdlTenpoKubun() {
    return ddlTenpoKubun;
  }

  public void setDdlTenpoKubun(String ddlTenpoKubun) {
    this.ddlTenpoKubun = ddlTenpoKubun;
  }

  public String getTxtTenpoKubunHidden() {
    return txtTenpoKubunHidden;
  }

  public void setTxtTenpoKubunHidden(String txtTenpoKubunHidden) {
    this.txtTenpoKubunHidden = txtTenpoKubunHidden;
  }

  public String getDdlGyoutaiKubun() {
    return ddlGyoutaiKubun;
  }

  public void setDdlGyoutaiKubun(String ddlGyoutaiKubun) {
    this.ddlGyoutaiKubun = ddlGyoutaiKubun;
  }

  public String getTxtGyoutaiKubunHidden() {
    return txtGyoutaiKubunHidden;
  }

  public void setTxtGyoutaiKubunHidden(String txtGyoutaiKubunHidden) {
    this.txtGyoutaiKubunHidden = txtGyoutaiKubunHidden;
  }

  public String getDdlDeliveryCenter() {
    return ddlDeliveryCenter;
  }

  public void setDdlDeliveryCenter(String ddlDeliveryCenter) {
    this.ddlDeliveryCenter = ddlDeliveryCenter;
  }

  public String getTxtDeliveryCenterHidden() {
    return txtDeliveryCenterHidden;
  }

  public void setTxtDeliveryCenterHidden(String txtDeliveryCenterHidden) {
    this.txtDeliveryCenterHidden = txtDeliveryCenterHidden;
  }

  public String getTxtDeliveryCenterDataChanged() {
    return txtDeliveryCenterDataChanged;
  }

  public void setTxtDeliveryCenterDataChanged(
      String txtDeliveryCenterDataChanged) {
    this.txtDeliveryCenterDataChanged = txtDeliveryCenterDataChanged;
  }

  public String getDdlKankeiKaishaType() {
    return ddlKankeiKaishaType;
  }

  public void setDdlKankeiKaishaType(String ddlKankeiKaishaType) {
    this.ddlKankeiKaishaType = ddlKankeiKaishaType;
  }

  public String getTxtKankeiKaishaTypeHidden() {
    return txtKankeiKaishaTypeHidden;
  }

  public void setTxtKankeiKaishaTypeHidden(String txtKankeiKaishaTypeHidden) {
    this.txtKankeiKaishaTypeHidden = txtKankeiKaishaTypeHidden;
  }

  public String getDdlHojoKamoku() {
    return ddlHojoKamoku;
  }

  public void setDdlHojoKamoku(String ddlHojoKamoku) {
    this.ddlHojoKamoku = ddlHojoKamoku;
  }

  public String getTxtHojoKamokuHidden() {
    return txtHojoKamokuHidden;
  }

  public void setTxtHojoKamokuHidden(String txtHojoKamokuHidden) {
    this.txtHojoKamokuHidden = txtHojoKamokuHidden;
  }

  public String getDdlSaibanKubun() {
    return ddlSaibanKubun;
  }

  public void setDdlSaibanKubun(String ddlSaibanKubun) {
    this.ddlSaibanKubun = ddlSaibanKubun;
  }

  public String getTxtSaibanKubunHidden() {
    return txtSaibanKubunHidden;
  }

  public void setTxtSaibanKubunHidden(String txtSaibanKubunHidden) {
    this.txtSaibanKubunHidden = txtSaibanKubunHidden;
  }

  public String getDdlYgTorihikiKubun() {
    return ddlYgTorihikiKubun;
  }

  public void setDdlYgTorihikiKubun(String ddlYgTorihikiKubun) {
    this.ddlYgTorihikiKubun = ddlYgTorihikiKubun;
  }

  public String getTxtYgTorihikiKubunHidden() {
    return txtYgTorihikiKubunHidden;
  }

  public void setTxtYgTorihikiKubunHidden(String txtYgTorihikiKubunHidden) {
    this.txtYgTorihikiKubunHidden = txtYgTorihikiKubunHidden;
  }

  public String getDdlUchiZeiKoKyakuKubun() {
    return ddlUchiZeiKoKyakuKubun;
  }

  public void setDdlUchiZeiKoKyakuKubun(String ddlUchiZeiKoKyakuKubun) {
    this.ddlUchiZeiKoKyakuKubun = ddlUchiZeiKoKyakuKubun;
  }

  public String getTxtUchiZeiKoKyakuKubunHidden() {
    return txtUchiZeiKoKyakuKubunHidden;
  }

  public void setTxtUchiZeiKoKyakuKubunHidden(
      String txtUchiZeiKoKyakuKubunHidden) {
    this.txtUchiZeiKoKyakuKubunHidden = txtUchiZeiKoKyakuKubunHidden;
  }

  public String getDdlUchiZeiShori() {
    return ddlUchiZeiShori;
  }

  public void setDdlUchiZeiShori(String ddlUchiZeiShori) {
    this.ddlUchiZeiShori = ddlUchiZeiShori;
  }

  public String getTxtUchiZeiShoriHidden() {
    return txtUchiZeiShoriHidden;
  }

  public void setTxtUchiZeiShoriHidden(String txtUchiZeiShoriHidden) {
    this.txtUchiZeiShoriHidden = txtUchiZeiShoriHidden;
  }

  public String getDdlShuukinUmu() {
    return ddlShuukinUmu;
  }

  public void setDdlShuukinUmu(String ddlShuukinUmu) {
    this.ddlShuukinUmu = ddlShuukinUmu;
  }

  public String getTxtShuukinUmuHidden() {
    return txtShuukinUmuHidden;
  }

  public void setTxtShuukinUmuHidden(String txtShuukinUmuHidden) {
    this.txtShuukinUmuHidden = txtShuukinUmuHidden;
  }

  public String getDdlGenkinShuukinMark() {
    return ddlGenkinShuukinMark;
  }

  public void setDdlGenkinShuukinMark(String ddlGenkinShuukinMark) {
    this.ddlGenkinShuukinMark = ddlGenkinShuukinMark;
  }

  public String getTxtGenkinShuukinMarkHidden() {
    return txtGenkinShuukinMarkHidden;
  }

  public void setTxtGenkinShuukinMarkHidden(String txtGenkinShuukinMarkHidden) {
    this.txtGenkinShuukinMarkHidden = txtGenkinShuukinMarkHidden;
  }

  public String getDdlShuukinOutputFlag() {
    return ddlShuukinOutputFlag;
  }

  public void setDdlShuukinOutputFlag(String ddlShuukinOutputFlag) {
    this.ddlShuukinOutputFlag = ddlShuukinOutputFlag;
  }

  public String getTxtShuukinOutputFlagHidden() {
    return txtShuukinOutputFlagHidden;
  }

  public void setTxtShuukinOutputFlagHidden(String txtShuukinOutputFlagHidden) {
    this.txtShuukinOutputFlagHidden = txtShuukinOutputFlagHidden;
  }

  public String getDdlManualInputBilling() {
    return ddlManualInputBilling;
  }

  public void setDdlManualInputBilling(String ddlManualInputBilling) {
    this.ddlManualInputBilling = ddlManualInputBilling;
  }

  public String getTxtManualInputBillingHidden() {
    return txtManualInputBillingHidden;
  }

  public void setTxtManualInputBillingHidden(
      String txtManualInputBillingHidden) {
    this.txtManualInputBillingHidden = txtManualInputBillingHidden;
  }

  public String getDdlManualInputDelivery() {
    return ddlManualInputDelivery;
  }

  public void setDdlManualInputDelivery(String ddlManualInputDelivery) {
    this.ddlManualInputDelivery = ddlManualInputDelivery;
  }

  public String getTxtManualInputDeliveryHidden() {
    return txtManualInputDeliveryHidden;
  }

  public void setTxtManualInputDeliveryHidden(
      String txtManualInputDeliveryHidden) {
    this.txtManualInputDeliveryHidden = txtManualInputDeliveryHidden;
  }

  public String getDdlSlipLineCalculationAmountRounding() {
    return ddlSlipLineCalculationAmountRounding;
  }

  public void setDdlSlipLineCalculationAmountRounding(
      String ddlSlipLineCalculationAmountRounding) {
    this.ddlSlipLineCalculationAmountRounding = ddlSlipLineCalculationAmountRounding;
  }

  public String getTxtSlipLineCalculationAmountRoundingHidden() {
    return txtSlipLineCalculationAmountRoundingHidden;
  }

  public void setTxtSlipLineCalculationAmountRoundingHidden(
      String txtSlipLineCalculationAmountRoundingHidden) {
    this.txtSlipLineCalculationAmountRoundingHidden = txtSlipLineCalculationAmountRoundingHidden;
  }

  public String getDdlDeliveryOutputProductCode() {
    return ddlDeliveryOutputProductCode;
  }

  public void setDdlDeliveryOutputProductCode(
      String ddlDeliveryOutputProductCode) {
    this.ddlDeliveryOutputProductCode = ddlDeliveryOutputProductCode;
  }

  public String getTxtDeliveryOutputProductCodeHidden() {
    return txtDeliveryOutputProductCodeHidden;
  }

  public void setTxtDeliveryOutputProductCodeHidden(
      String txtDeliveryOutputProductCodeHidden) {
    this.txtDeliveryOutputProductCodeHidden = txtDeliveryOutputProductCodeHidden;
  }

  public String getTxtBillingName() {
    return txtBillingName;
  }

  public void setTxtBillingName(String txtBillingName) {
    this.txtBillingName = txtBillingName;
  }

  public String getTxtBillingNameKana() {
    return txtBillingNameKana;
  }

  public void setTxtBillingNameKana(String txtBillingNameKana) {
    this.txtBillingNameKana = txtBillingNameKana;
  }

  public String getTxtBillingNameR() {
    return txtBillingNameR;
  }

  public void setTxtBillingNameR(String txtBillingNameR) {
    this.txtBillingNameR = txtBillingNameR;
  }

  public String getTxtBillingZipCode1() {
    return txtBillingZipCode1;
  }

  public void setTxtBillingZipCode1(String txtBillingZipCode1) {
    this.txtBillingZipCode1 = txtBillingZipCode1;
  }

  public String getTxtBillingZipCode2() {
    return txtBillingZipCode2;
  }

  public void setTxtBillingZipCode2(String txtBillingZipCode2) {
    this.txtBillingZipCode2 = txtBillingZipCode2;
  }

  public String getTxtBillingZipCodeResult() {
    return txtBillingZipCodeResult;
  }

  public void setTxtBillingZipCodeResult(String txtBillingZipCodeResult) {
    this.txtBillingZipCodeResult = txtBillingZipCodeResult;
  }

  public String getTxtBillingAddress1() {
    return txtBillingAddress1;
  }

  public void setTxtBillingAddress1(String txtBillingAddress1) {
    this.txtBillingAddress1 = txtBillingAddress1;
  }

  public String getTxtBillingAddress2() {
    return txtBillingAddress2;
  }

  public void setTxtBillingAddress2(String txtBillingAddress2) {
    this.txtBillingAddress2 = txtBillingAddress2;
  }

  public String getTxtBillingTel1() {
    return txtBillingTel1;
  }

  public void setTxtBillingTel1(String txtBillingTel1) {
    this.txtBillingTel1 = txtBillingTel1;
  }

  public String getTxtBillingTel2() {
    return txtBillingTel2;
  }

  public void setTxtBillingTel2(String txtBillingTel2) {
    this.txtBillingTel2 = txtBillingTel2;
  }

  public String getTxtBillingTel3() {
    return txtBillingTel3;
  }

  public void setTxtBillingTel3(String txtBillingTel3) {
    this.txtBillingTel3 = txtBillingTel3;
  }

  public String getTxtBillingTelResult() {
    return txtBillingTelResult;
  }

  public void setTxtBillingTelResult(String txtBillingTelResult) {
    this.txtBillingTelResult = txtBillingTelResult;
  }

  public String getTxtBillingFax1() {
    return txtBillingFax1;
  }

  public void setTxtBillingFax1(String txtBillingFax1) {
    this.txtBillingFax1 = txtBillingFax1;
  }

  public String getTxtBillingFax2() {
    return txtBillingFax2;
  }

  public void setTxtBillingFax2(String txtBillingFax2) {
    this.txtBillingFax2 = txtBillingFax2;
  }

  public String getTxtBillingFax3() {
    return txtBillingFax3;
  }

  public void setTxtBillingFax3(String txtBillingFax3) {
    this.txtBillingFax3 = txtBillingFax3;
  }

  public String getTxtBillingFaxResult() {
    return txtBillingFaxResult;
  }

  public void setTxtBillingFaxResult(String txtBillingFaxResult) {
    this.txtBillingFaxResult = txtBillingFaxResult;
  }

  public String getDdlBillingUnit() {
    return ddlBillingUnit;
  }

  public void setDdlBillingUnit(String ddlBillingUnit) {
    this.ddlBillingUnit = ddlBillingUnit;
  }

  public String getTxtBillingUnitHidden() {
    return txtBillingUnitHidden;
  }

  public void setTxtBillingUnitHidden(String txtBillingUnitHidden) {
    this.txtBillingUnitHidden = txtBillingUnitHidden;
  }

  public String getDdlBillingUnitPrice() {
    return ddlBillingUnitPrice;
  }

  public void setDdlBillingUnitPrice(String ddlBillingUnitPrice) {
    this.ddlBillingUnitPrice = ddlBillingUnitPrice;
  }

  public String getTxtBillingUnitPriceHidden() {
    return txtBillingUnitPriceHidden;
  }

  public void setTxtBillingUnitPriceHidden(String txtBillingUnitPriceHidden) {
    this.txtBillingUnitPriceHidden = txtBillingUnitPriceHidden;
  }

  public String getDdlBillingType() {
    return ddlBillingType;
  }

  public void setDdlBillingType(String ddlBillingType) {
    this.ddlBillingType = ddlBillingType;
  }

  public String getTxtBillingTypeHidden() {
    return txtBillingTypeHidden;
  }

  public void setTxtBillingTypeHidden(String txtBillingTypeHidden) {
    this.txtBillingTypeHidden = txtBillingTypeHidden;
  }

  public String getDdlBillingToriMatomeUMu() {
    return ddlBillingToriMatomeUMu;
  }

  public void setDdlBillingToriMatomeUMu(String ddlBillingToriMatomeUMu) {
    this.ddlBillingToriMatomeUMu = ddlBillingToriMatomeUMu;
  }

  public String getTxtBillingToriMatomeUMuHidden() {
    return txtBillingToriMatomeUMuHidden;
  }

  public void setTxtBillingToriMatomeUMuHidden(
      String txtBillingToriMatomeUMuHidden) {
    this.txtBillingToriMatomeUMuHidden = txtBillingToriMatomeUMuHidden;
  }

  public String getDdlBillingToriMatomeJigyouSho() {
    return ddlBillingToriMatomeJigyouSho;
  }

  public void setDdlBillingToriMatomeJigyouSho(
      String ddlBillingToriMatomeJigyouSho) {
    this.ddlBillingToriMatomeJigyouSho = ddlBillingToriMatomeJigyouSho;
  }

  public String getTxtBillingToriMatomeJigyouShoHidden() {
    return txtBillingToriMatomeJigyouShoHidden;
  }

  public void setTxtBillingToriMatomeJigyouShoHidden(
      String txtBillingToriMatomeJigyouShoHidden) {
    this.txtBillingToriMatomeJigyouShoHidden = txtBillingToriMatomeJigyouShoHidden;
  }

  public String getDdlBillingPattern() {
    return ddlBillingPattern;
  }

  public void setDdlBillingPattern(String ddlBillingPattern) {
    this.ddlBillingPattern = ddlBillingPattern;
  }

  public String getTxtBillingPatternHidden() {
    return txtBillingPatternHidden;
  }

  public void setTxtBillingPatternHidden(String txtBillingPatternHidden) {
    this.txtBillingPatternHidden = txtBillingPatternHidden;
  }

  public String getDdlBillingAddressPrint() {
    return ddlBillingAddressPrint;
  }

  public void setDdlBillingAddressPrint(String ddlBillingAddressPrint) {
    this.ddlBillingAddressPrint = ddlBillingAddressPrint;
  }

  public String getTxtBillingAddressPrintHidden() {
    return txtBillingAddressPrintHidden;
  }

  public void setTxtBillingAddressPrintHidden(
      String txtBillingAddressPrintHidden) {
    this.txtBillingAddressPrintHidden = txtBillingAddressPrintHidden;
  }

  public String getDdlBillingShuukeiHyouKubun() {
    return ddlBillingShuukeiHyouKubun;
  }

  public void setDdlBillingShuukeiHyouKubun(String ddlBillingShuukeiHyouKubun) {
    this.ddlBillingShuukeiHyouKubun = ddlBillingShuukeiHyouKubun;
  }

  public String getTxtBillingShuukeiHyouKubunHidden() {
    return txtBillingShuukeiHyouKubunHidden;
  }

  public void setTxtBillingShuukeiHyouKubunHidden(
      String txtBillingShuukeiHyouKubunHidden) {
    this.txtBillingShuukeiHyouKubunHidden = txtBillingShuukeiHyouKubunHidden;
  }

  public String getDdlBillingShouhizeiCalculationUnit() {
    return ddlBillingShouhizeiCalculationUnit;
  }

  public void setDdlBillingShouhizeiCalculationUnit(
      String ddlBillingShouhizeiCalculationUnit) {
    this.ddlBillingShouhizeiCalculationUnit = ddlBillingShouhizeiCalculationUnit;
  }

  public String getTxtBillingShouhizeiCalculationUnitHidden() {
    return txtBillingShouhizeiCalculationUnitHidden;
  }

  public void setTxtBillingShouhizeiCalculationUnitHidden(
      String txtBillingShouhizeiCalculationUnitHidden) {
    this.txtBillingShouhizeiCalculationUnitHidden = txtBillingShouhizeiCalculationUnitHidden;
  }

  public String getDdlBillingCheckListOutputTarget() {
    return ddlBillingCheckListOutputTarget;
  }

  public void setDdlBillingCheckListOutputTarget(
      String ddlBillingCheckListOutputTarget) {
    this.ddlBillingCheckListOutputTarget = ddlBillingCheckListOutputTarget;
  }

  public String getTxtBillingCheckListOutputTargetHidden() {
    return txtBillingCheckListOutputTargetHidden;
  }

  public void setTxtBillingCheckListOutputTargetHidden(
      String txtBillingCheckListOutputTargetHidden) {
    this.txtBillingCheckListOutputTargetHidden = txtBillingCheckListOutputTargetHidden;
  }

  public String getDdlBillingShouhizeiShori() {
    return ddlBillingShouhizeiShori;
  }

  public void setDdlBillingShouhizeiShori(String ddlBillingShouhizeiShori) {
    this.ddlBillingShouhizeiShori = ddlBillingShouhizeiShori;
  }

  public String getTxtBillingShouhizeiShoriHidden() {
    return txtBillingShouhizeiShoriHidden;
  }

  public void setTxtBillingShouhizeiShoriHidden(
      String txtBillingShouhizeiShoriHidden) {
    this.txtBillingShouhizeiShoriHidden = txtBillingShouhizeiShoriHidden;
  }

  public String getDdlBillingCheckListOutputOrder() {
    return ddlBillingCheckListOutputOrder;
  }

  public void setDdlBillingCheckListOutputOrder(
      String ddlBillingCheckListOutputOrder) {
    this.ddlBillingCheckListOutputOrder = ddlBillingCheckListOutputOrder;
  }

  public String getTxtBillingCheckListOutputOrderHidden() {
    return txtBillingCheckListOutputOrderHidden;
  }

  public void setTxtBillingCheckListOutputOrderHidden(
      String txtBillingCheckListOutputOrderHidden) {
    this.txtBillingCheckListOutputOrderHidden = txtBillingCheckListOutputOrderHidden;
  }

  public String getTxtCloseDay1() {
    return txtCloseDay1;
  }

  public void setTxtCloseDay1(String txtCloseDay1) {
    this.txtCloseDay1 = txtCloseDay1;
  }

  public String getDdlRecoveryMonthKubun1() {
    return ddlRecoveryMonthKubun1;
  }

  public void setDdlRecoveryMonthKubun1(String ddlRecoveryMonthKubun1) {
    this.ddlRecoveryMonthKubun1 = ddlRecoveryMonthKubun1;
  }

  public String getTxtRecoveryMonthKubun1Hidden() {
    return txtRecoveryMonthKubun1Hidden;
  }

  public void setTxtRecoveryMonthKubun1Hidden(
      String txtRecoveryMonthKubun1Hidden) {
    this.txtRecoveryMonthKubun1Hidden = txtRecoveryMonthKubun1Hidden;
  }

  public String getTxtRecoveryDay1() {
    return txtRecoveryDay1;
  }

  public void setTxtRecoveryDay1(String txtRecoveryDay1) {
    this.txtRecoveryDay1 = txtRecoveryDay1;
  }

  public String getTxtCloseDay2() {
    return txtCloseDay2;
  }

  public void setTxtCloseDay2(String txtCloseDay2) {
    this.txtCloseDay2 = txtCloseDay2;
  }

  public String getDdlRecoveryMonthKubun2() {
    return ddlRecoveryMonthKubun2;
  }

  public void setDdlRecoveryMonthKubun2(String ddlRecoveryMonthKubun2) {
    this.ddlRecoveryMonthKubun2 = ddlRecoveryMonthKubun2;
  }

  public String getTxtRecoveryMonthKubun2Hidden() {
    return txtRecoveryMonthKubun2Hidden;
  }

  public void setTxtRecoveryMonthKubun2Hidden(
      String txtRecoveryMonthKubun2Hidden) {
    this.txtRecoveryMonthKubun2Hidden = txtRecoveryMonthKubun2Hidden;
  }

  public String getTxtRecoveryDay2() {
    return txtRecoveryDay2;
  }

  public void setTxtRecoveryDay2(String txtRecoveryDay2) {
    this.txtRecoveryDay2 = txtRecoveryDay2;
  }

  public String getDdlPaymentType() {
    return ddlPaymentType;
  }

  public void setDdlPaymentType(String ddlPaymentType) {
    this.ddlPaymentType = ddlPaymentType;
  }

  public String getTxtPaymentTypeHidden() {
    return txtPaymentTypeHidden;
  }

  public void setTxtPaymentTypeHidden(String txtPaymentTypeHidden) {
    this.txtPaymentTypeHidden = txtPaymentTypeHidden;
  }

  public String getDdlPaymentAccount() {
    return ddlPaymentAccount;
  }

  public void setDdlPaymentAccount(String ddlPaymentAccount) {
    this.ddlPaymentAccount = ddlPaymentAccount;
  }

  public String getTxtPaymentAccountHidden() {
    return txtPaymentAccountHidden;
  }

  public void setTxtPaymentAccountHidden(String txtPaymentAccountHidden) {
    this.txtPaymentAccountHidden = txtPaymentAccountHidden;
  }

  public String getTxtNoteSite() {
    return txtNoteSite;
  }

  public void setTxtNoteSite(String txtNoteSite) {
    this.txtNoteSite = txtNoteSite;
  }

  public String getTxtTorihikiCode() {
    return txtTorihikiCode;
  }

  public void setTxtTorihikiCode(String txtTorihikiCode) {
    this.txtTorihikiCode = txtTorihikiCode;
  }

  public String getTxtBunruiCodeTeibanShop() {
    return txtBunruiCodeTeibanShop;
  }

  public void setTxtBunruiCodeTeibanShop(String txtBunruiCodeTeibanShop) {
    this.txtBunruiCodeTeibanShop = txtBunruiCodeTeibanShop;
  }

  public String getTxtDenpyouKubunTeibanShop() {
    return txtDenpyouKubunTeibanShop;
  }

  public void setTxtDenpyouKubunTeibanShop(String txtDenpyouKubunTeibanShop) {
    this.txtDenpyouKubunTeibanShop = txtDenpyouKubunTeibanShop;
  }

  public String getTxtBunruiCodeTeibanCenter() {
    return txtBunruiCodeTeibanCenter;
  }

  public void setTxtBunruiCodeTeibanCenter(String txtBunruiCodeTeibanCenter) {
    this.txtBunruiCodeTeibanCenter = txtBunruiCodeTeibanCenter;
  }

  public String getTxtDenpyouKubunTeibanCenter() {
    return txtDenpyouKubunTeibanCenter;
  }

  public void setTxtDenpyouKubunTeibanCenter(
      String txtDenpyouKubunTeibanCenter) {
    this.txtDenpyouKubunTeibanCenter = txtDenpyouKubunTeibanCenter;
  }

  public String getTxtBunruiCodeTokubaiShop() {
    return txtBunruiCodeTokubaiShop;
  }

  public void setTxtBunruiCodeTokubaiShop(String txtBunruiCodeTokubaiShop) {
    this.txtBunruiCodeTokubaiShop = txtBunruiCodeTokubaiShop;
  }

  public String getTxtDenpyouKubunTokubaiShop() {
    return txtDenpyouKubunTokubaiShop;
  }

  public void setTxtDenpyouKubunTokubaiShop(String txtDenpyouKubunTokubaiShop) {
    this.txtDenpyouKubunTokubaiShop = txtDenpyouKubunTokubaiShop;
  }

  public String getTxtBunruiCodeTokubaiCenter() {
    return txtBunruiCodeTokubaiCenter;
  }

  public void setTxtBunruiCodeTokubaiCenter(String txtBunruiCodeTokubaiCenter) {
    this.txtBunruiCodeTokubaiCenter = txtBunruiCodeTokubaiCenter;
  }

  public String getTxtDenpyouKubunTokubaiCenter() {
    return txtDenpyouKubunTokubaiCenter;
  }

  public void setTxtDenpyouKubunTokubaiCenter(
      String txtDenpyouKubunTokubaiCenter) {
    this.txtDenpyouKubunTokubaiCenter = txtDenpyouKubunTokubaiCenter;
  }

  public String getDdlJuryouDataKubun() {
    return ddlJuryouDataKubun;
  }

  public void setDdlJuryouDataKubun(String ddlJuryouDataKubun) {
    this.ddlJuryouDataKubun = ddlJuryouDataKubun;
  }

  public String getTxtJuryouDataKubunHidden() {
    return txtJuryouDataKubunHidden;
  }

  public void setTxtJuryouDataKubunHidden(String txtJuryouDataKubunHidden) {
    this.txtJuryouDataKubunHidden = txtJuryouDataKubunHidden;
  }

  public String getDdlBillingDataKubun() {
    return ddlBillingDataKubun;
  }

  public void setDdlBillingDataKubun(String ddlBillingDataKubun) {
    this.ddlBillingDataKubun = ddlBillingDataKubun;
  }

  public String getTxtBillingDataKubunHidden() {
    return txtBillingDataKubunHidden;
  }

  public void setTxtBillingDataKubunHidden(String txtBillingDataKubunHidden) {
    this.txtBillingDataKubunHidden = txtBillingDataKubunHidden;
  }

  public String getDdlModifyDataKubun() {
    return ddlModifyDataKubun;
  }

  public void setDdlModifyDataKubun(String ddlModifyDataKubun) {
    this.ddlModifyDataKubun = ddlModifyDataKubun;
  }

  public String getTxtModifyDataKubunHidden() {
    return txtModifyDataKubunHidden;
  }

  public void setTxtModifyDataKubunHidden(String txtModifyDataKubunHidden) {
    this.txtModifyDataKubunHidden = txtModifyDataKubunHidden;
  }

  public String getDdlBillingPaymentDataKubun() {
    return ddlBillingPaymentDataKubun;
  }

  public void setDdlBillingPaymentDataKubun(String ddlBillingPaymentDataKubun) {
    this.ddlBillingPaymentDataKubun = ddlBillingPaymentDataKubun;
  }

  public String getTxtBillingPaymentDataKubunHidden() {
    return txtBillingPaymentDataKubunHidden;
  }

  public void setTxtBillingPaymentDataKubunHidden(
      String txtBillingPaymentDataKubunHidden) {
    this.txtBillingPaymentDataKubunHidden = txtBillingPaymentDataKubunHidden;
  }

  public boolean isChkModifyTypeHenpinData() {
    return chkModifyTypeHenpinData;
  }

  public void setChkModifyTypeHenpinData(boolean chkModifyTypeHenpinData) {
    this.chkModifyTypeHenpinData = chkModifyTypeHenpinData;
  }

  public boolean isChkModifyTypeKetsuhinData() {
    return chkModifyTypeKetsuhinData;
  }

  public void setChkModifyTypeKetsuhinData(boolean chkModifyTypeKetsuhinData) {
    this.chkModifyTypeKetsuhinData = chkModifyTypeKetsuhinData;
  }

  public boolean isChkModifyTypeShuuseiData() {
    return chkModifyTypeShuuseiData;
  }

  public void setChkModifyTypeShuuseiData(boolean chkModifyTypeShuuseiData) {
    this.chkModifyTypeShuuseiData = chkModifyTypeShuuseiData;
  }

  public String getTxtBillingDataDeliveryId() {
    return txtBillingDataDeliveryId;
  }

  public void setTxtBillingDataDeliveryId(String txtBillingDataDeliveryId) {
    this.txtBillingDataDeliveryId = txtBillingDataDeliveryId;
  }

  public String getTxtShuukeiCode1() {
    return txtShuukeiCode1;
  }

  public void setTxtShuukeiCode1(String txtShuukeiCode1) {
    this.txtShuukeiCode1 = txtShuukeiCode1;
  }

  public String getTxtShuukeiCode2() {
    return txtShuukeiCode2;
  }

  public void setTxtShuukeiCode2(String txtShuukeiCode2) {
    this.txtShuukeiCode2 = txtShuukeiCode2;
  }

  public String getTxtShiyouChuushiDay() {
    return txtShiyouChuushiDay;
  }

  public void setTxtShiyouChuushiDay(String txtShiyouChuushiDay) {
    this.txtShiyouChuushiDay = txtShiyouChuushiDay;
  }

  public String getTxtStatusCode() {
    return txtStatusCode;
  }

  public void setTxtStatusCode(String txtStatusCode) {
    this.txtStatusCode = txtStatusCode;
  }

  public ArrayList<MstCourseInformation> getArrListCourseInformation() {
    return arrListCourseInformation;
  }

  public void setArrListCourseInformation(
      ArrayList<MstCourseInformation> arrListCourseInformation) {
    this.arrListCourseInformation = arrListCourseInformation;
  }

  public String getForm1DdlJigyouSho() {
    return form1DdlJigyouSho;
  }

  public void setForm1DdlJigyouSho(String form1DdlJigyouSho) {
    this.form1DdlJigyouSho = form1DdlJigyouSho;
  }

  public String getForm1TxtCustomerCode() {
    return form1TxtCustomerCode;
  }

  public void setForm1TxtCustomerCode(String form1TxtCustomerCode) {
    this.form1TxtCustomerCode = form1TxtCustomerCode;
  }

  public String getForm1TxtCustomerName() {
    return form1TxtCustomerName;
  }

  public void setForm1TxtCustomerName(String form1TxtCustomerName) {
    this.form1TxtCustomerName = form1TxtCustomerName;
  }

  public String getForm1TxtChainCode() {
    return form1TxtChainCode;
  }

  public void setForm1TxtChainCode(String form1TxtChainCode) {
    this.form1TxtChainCode = form1TxtChainCode;
  }

  public String getForm1TxtChainEda() {
    return form1TxtChainEda;
  }

  public void setForm1TxtChainEda(String form1TxtChainEda) {
    this.form1TxtChainEda = form1TxtChainEda;
  }

  public String getForm1TxtEigyouTantoushaCode() {
    return form1TxtEigyouTantoushaCode;
  }

  public void setForm1TxtEigyouTantoushaCode(
      String form1TxtEigyouTantoushaCode) {
    this.form1TxtEigyouTantoushaCode = form1TxtEigyouTantoushaCode;
  }

  public String getForm1TxtJimuTantoushaCode() {
    return form1TxtJimuTantoushaCode;
  }

  public void setForm1TxtJimuTantoushaCode(String form1TxtJimuTantoushaCode) {
    this.form1TxtJimuTantoushaCode = form1TxtJimuTantoushaCode;
  }

  public String getForm1DdlCustomerType() {
    return form1DdlCustomerType;
  }

  public void setForm1DdlCustomerType(String form1DdlCustomerType) {
    this.form1DdlCustomerType = form1DdlCustomerType;
  }

  public String getForm1DdlUchiZeiKoKyakuKubun() {
    return form1DdlUchiZeiKoKyakuKubun;
  }

  public void setForm1DdlUchiZeiKoKyakuKubun(
      String form1DdlUchiZeiKoKyakuKubun) {
    this.form1DdlUchiZeiKoKyakuKubun = form1DdlUchiZeiKoKyakuKubun;
  }

  public boolean isForm1ChkCancelData() {
    return form1ChkCancelData;
  }

  public void setForm1ChkCancelData(boolean form1ChkCancelData) {
    this.form1ChkCancelData = form1ChkCancelData;
  }

  public boolean isForm1ChkCustomer() {
    return form1ChkCustomer;
  }

  public void setForm1ChkCustomer(boolean form1ChkCustomer) {
    this.form1ChkCustomer = form1ChkCustomer;
  }

  public boolean isForm1ChkBilling() {
    return form1ChkBilling;
  }

  public void setForm1ChkBilling(boolean form1ChkBilling) {
    this.form1ChkBilling = form1ChkBilling;
  }

  public boolean isForm1ChkCustomerBilling() {
    return form1ChkCustomerBilling;
  }

  public void setForm1ChkCustomerBilling(boolean form1ChkCustomerBilling) {
    this.form1ChkCustomerBilling = form1ChkCustomerBilling;
  }

  public Integer getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(Integer businessDate) {
    this.businessDate = businessDate;
  }

  public String getViewMode() {
    return viewMode;
  }

  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  public String getSysAdminFlag() {
    return sysAdminFlag;
  }

  public void setSysAdminFlag(String sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }

  public String getLoginJigyouShoCode() {
    return loginJigyouShoCode;
  }

  public void setLoginJigyouShoCode(String loginJigyouShoCode) {
    this.loginJigyouShoCode = loginJigyouShoCode;
  }

  public String getHaitaDate() {
    return haitaDate;
  }

  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }

  public String getHaitaTime() {
    return haitaTime;
  }

  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }

}