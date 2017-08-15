/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.com
 * ファイル名:Com0102d04Model.java
 * 作成者:　グエン　リユオン　ギア
 * 作成日:2015/10/09
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/09 1.00 ABV)グエン　リユオン　ギア　 新規開発
 * -------------------------------------------------------------------------
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.com;

/**
 * Com0102d04Model.
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
public class Com0102d04Model {

  // チェーンコード
  private String cainCode;
  // チェーン枝番
  private String cainIdx;
  // 使用中止日
  private String closeDate;
  // 変数]納品日WK
  private String deadLineWk;
  // 状況コード
  private String stsCode;
  // 品番
  private String hinCd;
  // 製品略称
  private String hinRyaKu;
  // チェーンコード - chnCd
  private String chnCd;
  // チェーン枝番 - chnEda
  private String chnEda;
  // 適用開始日 - strYmd - deadLineWk
  private String strYmd;
  // 適用開始便 - strBin - flightWK
  private String strBin;
  // 適用終了日 - endYmd - deadLineWk
  private String endYmd;
  // 適用終了便 - endBin - flightWK
  private String endBin;
  // 適用中止フラグ - delFlg
  private String delFlg;
  // 品目コード - itemCode
  private String itemCode;
  // 得意先コード - custCode
  private String custCode;
  // 適用期間 - validForm
  private String validForm;
  // 適用期間 - validTo
  private String validTo;
  // 販売区分 - salesKb
  private String salesKb;

  /**
   * チェーンコード getter.
   * 
   * @return チェーンコード cainCode
   */
  public String getCainCode() {
    return cainCode;
  }

  /**
   * チェーンコード setter.
   * 
   * @param cainCode チェーンコード
   */
  public void setCainCode(String cainCode) {
    this.cainCode = cainCode;
  }

  /**
   * チェーン枝番 getter.
   * 
   * @return チェーン枝番 cainIdx
   */
  public String getCainIdx() {
    return cainIdx;
  }

  /**
   * チェーン枝番 setter.
   * 
   * @param チェーン枝番 cainIdx
   */
  public void setCainIdx(String cainIdx) {
    this.cainIdx = cainIdx;
  }

  /**
   * 使用中止日 getter.
   * 
   * @return 使用中止日 closeDate
   */
  public String getCloseDate() {
    return closeDate;
  }

  /**
   * 使用中止日setter.
   * 
   * @param 使用中止日 closeDate
   */
  public void setCloseDate(String closeDate) {
    this.closeDate = closeDate;
  }

  /*
   * 変数]納品日WK getter.
   * 
   * @return 変数]納品日WK deadLineWk
   */
  public String getdeadLineWk() {
    return deadLineWk;
  }

  /*
   * 変数]納品日WK setter.
   * 
   * @param 変数]納品日WK deadLineWk
   */
  public void setdeadLineWk(String deadLineWk) {
    this.deadLineWk = deadLineWk;
  }

  /*
   * 状況コード getter.
   * 
   * @return 状況コード stsCode
   */
  public String getStsCode() {
    return stsCode;
  }

  /*
   * 状況コードsetter.
   * 
   * @param 状況コード stsCode
   */
  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
  }

  /*
   * 品番 getter.
   * 
   * @return 品番 hinCd
   */
  public String gethinCd() {
    return hinCd;
  }

  /*
   * 品番 setter.
   * 
   * @param 品番 hinCd
   */
  public void sethinCd(String hinCd) {
    this.hinCd = hinCd;
  }

  /*
   * 製品略称 getter.
   * 
   * @return 製品略称 hinRyaKu
   */
  public String gethinRyaKu() {
    return hinRyaKu;
  }

  /*
   * 製品略称 setter.
   * 
   * @param 製品略称 hinRyaKu
   */
  public void sethinRyaKu(String hinRyaKu) {
    this.hinRyaKu = hinRyaKu;
  }

  /*
   * チェーンコード getter.
   * 
   * @return チェーンコード chnCd
   */
  public String getchnCd() {
    return chnCd;
  }

  /*
   * チェーンコード setter.
   * 
   * @param チェーンコード chnCd
   */
  public void setchnCd(String chnCd) {
    this.chnCd = chnCd;
  }

  /*
   * チェーン枝番 getter.
   * 
   * @return チェーン枝番 chnEda
   */
  public String getchnEda() {
    return chnEda;
  }

  /*
   * チェーン枝番 setter.
   * 
   * @param チェーン枝番 chnEda
   */
  public void setchnEda(String chnEda) {
    this.chnEda = chnEda;
  }

  /*
   * 適用開始日 getter.
   * 
   * @return 適用開始日 strYmd
   */
  public String getstrYmd() {
    return strYmd;
  }

  /*
   * 適用開始日 setter.
   * 
   * @param 適用開始日 strYmd
   */
  public void setstrYmd(String strYmd) {
    this.strYmd = strYmd;
  }

  /*
   * 適用終了日 getter.
   * 
   * @return 適用終了日 strBin
   */
  public String getstrBin() {
    return strBin;
  }

  /*
   * 適用終了日 setter.
   * 
   * @param 適用終了日 strBin
   */
  public void setstrBin(String strBin) {
    this.strBin = strBin;
  }

  /*
   * 適用終了便 getter.
   * 
   * @return 適用終了便 endYmd
   */
  public String getendYmd() {
    return endYmd;
  }

  /*
   * 適用終了 setter.
   * 
   * @param endYmd
   */
  public void setendYmd(String endYmd) {
    this.endYmd = endYmd;
  }

  /*
   * 適用終了 getter.
   * 
   * @return 適用終了 endBin
   */
  public String getendBin() {
    return endBin;
  }

  /*
   * 適用終了 setter.
   * 
   * @param 適用終了 endBin
   */
  public void setendBin(String endBin) {
    this.endBin = endBin;
  }

  /*
   * 適用中止フラグ getter.
   * 
   * @return 適用中止フラグ delFlg
   */
  public String getdelFlg() {
    return delFlg;
  }

  /*
   * 適用中止フラグ setter.
   * 
   * @param 適用中止フラグ delFlg
   */
  public void setdelFlg(String delFlg) {
    this.delFlg = delFlg;
  }

  /*
   * 品目コード getter.
   * 
   * @return 品目コード itemCode
   */
  public String getitemCode() {
    return itemCode;
  }

  /*
   * 品目コード setter.
   * 
   * @param 品目コード itemCode
   */
  public void setitemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  /*
   * 得意先コード getter.
   * 
   * @return 得意先コード custCode
   */
  public String getcustCode() {
    return custCode;
  }

  /*
   * 得意先コード setter.
   * 
   * @param 得意先コード custCode
   */
  public void setcustCode(String custCode) {
    this.custCode = custCode;
  }

  /*
   * 適用期間 getter.
   * 
   * @return 適用期間 validForm
   */
  public String getvalidForm() {
    return validForm;
  }

  /*
   * 適用期間 setter.
   * 
   * @param 適用期間 validForm
   */
  public void setvalidForm(String validForm) {
    this.validForm = validForm;
  }

  /*
   * 適用期間 getter.
   * 
   * @return validTo
   */
  public String getvalidTo() {
    return validTo;
  }

  /*
   * 適用期間 setter.
   * 
   * @param 適用期間 validTo
   */
  public void setvalidTo(String validTo) {
    this.validTo = validTo;
  }

  /*
   * .販売区分 getter.
   * 
   * @return .販売区分 salesKb
   */
  public String getsalesKb() {
    return salesKb;
  }

  /*
   * .販売区分 setter.
   * 
   * @param .販売区分 salesKb
   */
  public void setsalesKb(String salesKb) {
    this.salesKb = salesKb;
  }

}
