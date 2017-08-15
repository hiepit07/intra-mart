/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.sei
 * ファイル名:FormSei0104d01.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/11/29
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/29 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.sei;

import java.util.List;

/**
 * 請求締め取消処理画面用 FORM
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class FormSei0104d01 {

  /*
   * 画面HIDDEN
   */
  private String btnName;            // ボタン名
  private boolean sysAdminFlag;      // システム管理者フラグ
  private String loginJigyoshoCd;    // ログイン事業所コード
  private String businessDate;       // 業務日付
  private String accountMonth;       // 会計月
  private String haitaDate;          // 排他日付
  private String haitaTime;          // 排他時刻
  private String errorControl;       // エラー制御フラグ
  private boolean showListFlag;      // 明細部表示フラグ
  private boolean notFoundFlag;      // 検索結果0件フラグ

  private boolean msgInfoLevel;      // エラーレベル情報
  private boolean msgWarningLevel;   // エラーレベル警告
  private boolean msgErrorLevel;     // エラーレベルエラー

  /*
   * 検索条件
   */
  private String selectedJigyoshoCd;   // 事業所セレクトボックス選択値
  private String seikyuShimebi;        // 請求締め日
  private String seikyusakiCd;         // 請求先コード
  private String seikyusakiName;       // 請求先名

  /*
   * 検索条件HIDDEN
   */
  private String condSelectedJigyoshoCd;   // 事業所セレクトボックス選択値
  private String condSeikyuShimebi;        // 請求締め日
  private String condSeikyusakiCd;         // 請求先コード

  /*
   * 明細部
   */
  private List<SeikyusakiInfo> seikyusakiInfoList;

  // ---------------------------------------------------------------------------
  // getter / setter
  // ---------------------------------------------------------------------------

  /**
   * btnName getter
   */
  public String getBtnName() {
    return btnName;
  }

  /**
   * btnName setter
   */
  public void setBtnName(String btnName) {
    this.btnName = btnName;
  }

  /**
   * sysAdminFlag getter
   */
  public boolean isSysAdminFlag() {
    return sysAdminFlag;
  }

  /**
   * sysAdminFlag setter
   */
  public void setSysAdminFlag(boolean sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }

  /**
   * loginJigyoshoCd getter
   */
  public String getLoginJigyoshoCd() {
    return loginJigyoshoCd;
  }

  /**
   * loginJigyoshoCd setter
   */
  public void setLoginJigyoshoCd(String loginJigyoshoCd) {
    this.loginJigyoshoCd = loginJigyoshoCd;
  }

  /**
   * businessDate getter
   */
  public String getBusinessDate() {
    return businessDate;
  }

  /**
   * businessDate setter
   */
  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  /**
   * accountMonth getter
   */
  public String getAccountMonth() {
    return accountMonth;
  }

  /**
   * accountMonth setter
   */
  public void setAccountMonth(String accountMonth) {
    this.accountMonth = accountMonth;
  }

  /**
   * haitaDate getter
   */
  public String getHaitaDate() {
    return haitaDate;
  }

  /**
   * haitaDate setter
   */
  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }

  /**
   * haitaTime getter
   */
  public String getHaitaTime() {
    return haitaTime;
  }

  /**
   * haitaTime setter
   */
  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }

  /**
   * errorControl getter
   */
  public String getErrorControl() {
    return errorControl;
  }

  /**
   * errorControl setter
   */
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }

  /**
   * showListFlag getter
   */
  public boolean isShowListFlag() {
    return showListFlag;
  }

  /**
   * showListFlag setter
   */
  public void setShowListFlag(boolean showListFlag) {
    this.showListFlag = showListFlag;
  }

  /**
   * notFoundFlag getter
   */
  public boolean isNotFoundFlag() {
    return notFoundFlag;
  }

  /**
   * notFoundFlag setter
   */
  public void setNotFoundFlag(boolean notFoundFlag) {
    this.notFoundFlag = notFoundFlag;
  }

  /**
   * msgInfoLevel getter
   */
  public boolean isMsgInfoLevel() {
    return msgInfoLevel;
  }

  /**
   * msgInfoLevel setter
   */
  public void setMsgInfoLevel(boolean msgInfoLevel) {
    this.msgInfoLevel = msgInfoLevel;
  }

  /**
   * msgWarningLevel getter
   */
  public boolean isMsgWarningLevel() {
    return msgWarningLevel;
  }

  /**
   * msgWarningLevel setter
   */
  public void setMsgWarningLevel(boolean msgWarningLevel) {
    this.msgWarningLevel = msgWarningLevel;
  }

  /**
   * msgErrorLevel getter
   */
  public boolean isMsgErrorLevel() {
    return msgErrorLevel;
  }

  /**
   * msgErrorLevel setter
   */
  public void setMsgErrorLevel(boolean msgErrorLevel) {
    this.msgErrorLevel = msgErrorLevel;
  }

  /**
   * selectedJigyoshoCd getter
   */
  public String getSelectedJigyoshoCd() {
    return selectedJigyoshoCd;
  }

  /**
   * selectedJigyoshoCd setter
   */
  public void setSelectedJigyoshoCd(String selectedJigyoshoCd) {
    this.selectedJigyoshoCd = selectedJigyoshoCd;
  }

  /**
   * seikyuShimebi getter
   */
  public String getSeikyuShimebi() {
    return seikyuShimebi;
  }

  /**
   * seikyuShimebi setter
   */
  public void setSeikyuShimebi(String seikyuShimebi) {
    this.seikyuShimebi = seikyuShimebi;
  }

  /**
   * seikyusakiCd getter
   */
  public String getSeikyusakiCd() {
    return seikyusakiCd;
  }

  /**
   * seikyusakiCd setter
   */
  public void setSeikyusakiCd(String seikyusakiCd) {
    this.seikyusakiCd = seikyusakiCd;
  }

  /**
   * seikyusakiName getter
   */
  public String getSeikyusakiName() {
    return seikyusakiName;
  }

  /**
   * seikyusakiName setter
   */
  public void setSeikyusakiName(String seikyusakiName) {
    this.seikyusakiName = seikyusakiName;
  }

  /**
   * condSelectedJigyoshoCd getter
   */
  public String getCondSelectedJigyoshoCd() {
    return condSelectedJigyoshoCd;
  }

  /**
   * condSelectedJigyoshoCd setter
   */
  public void setCondSelectedJigyoshoCd(String condSelectedJigyoshoCd) {
    this.condSelectedJigyoshoCd = condSelectedJigyoshoCd;
  }

  /**
   * condSeikyuShimebi getter
   */
  public String getCondSeikyuShimebi() {
    return condSeikyuShimebi;
  }

  /**
   * condSeikyuShimebi setter
   */
  public void setCondSeikyuShimebi(String condSeikyuShimebi) {
    this.condSeikyuShimebi = condSeikyuShimebi;
  }

  /**
   * condSeikyusakiCd getter
   */
  public String getCondSeikyusakiCd() {
    return condSeikyusakiCd;
  }

  /**
   * condSeikyusakiCd setter
   */
  public void setCondSeikyusakiCd(String condSeikyusakiCd) {
    this.condSeikyusakiCd = condSeikyusakiCd;
  }

  /**
   * seikyusakiInfoList getter
   */
  public List<SeikyusakiInfo> getSeikyusakiInfoList() {
    return seikyusakiInfoList;
  }

  /**
   * seikyusakiInfoList setter
   */
  public void setSeikyusakiInfoList(List<SeikyusakiInfo> seikyusakiInfoList) {
    this.seikyusakiInfoList = seikyusakiInfoList;
  }

}
