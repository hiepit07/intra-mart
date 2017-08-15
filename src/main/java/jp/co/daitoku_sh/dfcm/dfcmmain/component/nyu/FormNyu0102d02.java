/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:FormNyu0102d02.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

import java.util.List;

/**
 * 締め請求未回収設定面用 FORM
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class FormNyu0102d02 {

  /*
   * 画面HIDDEN
   */
  private String btnName;            // ボタン名
  private boolean sysAdminFlag;      // システム管理者フラグ
  private String authKbn;            // 権限区分
  private String loginJigyoshoCd;    // ログイン事業所コード
  private String businessDate;       // 業務日付
  private int searchMax;             // 検索上限値
  private String haitaDate;          // 排他日付
  private String haitaTime;          // 排他時刻
  private String errorControl;       // エラー制御フラグ
  private boolean showListFlag;      // 明細部表示フラグ
  private boolean notFoundFlag;      // 検索結果0件フラグ
  private String selectIdx;           // 選択行
  
  private boolean msgInfoLevel;      // エラーレベル情報
  private boolean msgWarningLevel;   // エラーレベル警告
  private boolean msgErrorLevel;     // エラーレベルエラー
  
  /*
   * 検索条件
   */
  private String selectedJigyoshoCd;   // 事業所セレクトボックス選択値
  private String seikyusakiCdFrom;     // 請求先コード From
  private String seikyusakiCdTo;       // 請求先コード To
  private String nyukinYoteibiFrom;    // 入金予定日 From
  private String nyukinYoteibiTo;      // 入金予定日 To
  
  /*
   * 検索条件HIDDEN
   */
  private String condSelectedJigyoshoCd;   // 事業所セレクトボックス選択値
  private String condSeikyusakiCdFrom;     // 請求先コード From
  private String condSeikyusakiCdTo;       // 請求先コード To
  private String condNyukinYoteibiFrom;    // 入金予定日 From
  private String condNyukinYoteibiTo;      // 入金予定日 To 
  
  /*
   * 明細部
   */
  private List<NyuSeikyusakiInfo> nyuSeikyusakiList;
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getBtnName() {
    return btnName;
  }
  
  public void setBtnName(String btnName) {
    this.btnName = btnName;
  }
  
  public boolean isSysAdminFlag() {
    return sysAdminFlag;
  }
  
  public void setSysAdminFlag(boolean sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }
  
  
  public String getAuthKbn() {
    return authKbn;
  }

  
  public void setAuthKbn(String authKbn) {
    this.authKbn = authKbn;
  }

  public String getLoginJigyoshoCd() {
    return loginJigyoshoCd;
  }
  
  public void setLoginJigyoshoCd(String loginJigyoshoCd) {
    this.loginJigyoshoCd = loginJigyoshoCd;
  }
  
  public String getBusinessDate() {
    return businessDate;
  }
  
  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }
  
  public int getSearchMax() {
    return searchMax;
  }
  
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
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
  
  public String getErrorControl() {
    return errorControl;
  }
  
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }
  
  public boolean isShowListFlag() {
    return showListFlag;
  }
  
  public void setShowListFlag(boolean showListFlag) {
    this.showListFlag = showListFlag;
  }
  
  public boolean isNotFoundFlag() {
    return notFoundFlag;
  }
  
  public void setNotFoundFlag(boolean notFoundFlag) {
    this.notFoundFlag = notFoundFlag;
  }
  
  public boolean isMsgInfoLevel() {
    return msgInfoLevel;
  }
  
  public void setMsgInfoLevel(boolean msgInfoLevel) {
    this.msgInfoLevel = msgInfoLevel;
  }
  
  public boolean isMsgWarningLevel() {
    return msgWarningLevel;
  }
  
  public void setMsgWarningLevel(boolean msgWarningLevel) {
    this.msgWarningLevel = msgWarningLevel;
  }
  
  public boolean isMsgErrorLevel() {
    return msgErrorLevel;
  }
  
  public void setMsgErrorLevel(boolean msgErrorLevel) {
    this.msgErrorLevel = msgErrorLevel;
  }

  
  public String getSelectedJigyoshoCd() {
    return selectedJigyoshoCd;
  }

  
  public void setSelectedJigyoshoCd(String selectedJigyoshoCd) {
    this.selectedJigyoshoCd = selectedJigyoshoCd;
  }

  
  public String getSeikyusakiCdFrom() {
    return seikyusakiCdFrom;
  }

  
  public void setSeikyusakiCdFrom(String seikyusakiCdFrom) {
    this.seikyusakiCdFrom = seikyusakiCdFrom;
  }

  
  public String getSeikyusakiCdTo() {
    return seikyusakiCdTo;
  }

  
  public void setSeikyusakiCdTo(String seikyusakiCdTo) {
    this.seikyusakiCdTo = seikyusakiCdTo;
  }

  
  public String getNyukinYoteibiFrom() {
    return nyukinYoteibiFrom;
  }

  
  public void setNyukinYoteibiFrom(String nyukinYoteibiFrom) {
    this.nyukinYoteibiFrom = nyukinYoteibiFrom;
  }

  
  public String getNyukinYoteibiTo() {
    return nyukinYoteibiTo;
  }

  
  public void setNyukinYoteibiTo(String nyukinYoteibiTo) {
    this.nyukinYoteibiTo = nyukinYoteibiTo;
  }

  
  public String getCondSelectedJigyoshoCd() {
    return condSelectedJigyoshoCd;
  }

  
  public void setCondSelectedJigyoshoCd(String condSelectedJigyoshoCd) {
    this.condSelectedJigyoshoCd = condSelectedJigyoshoCd;
  }

  
  public String getCondSeikyusakiCdFrom() {
    return condSeikyusakiCdFrom;
  }

  
  public void setCondSeikyusakiCdFrom(String condSeikyusakiCdFrom) {
    this.condSeikyusakiCdFrom = condSeikyusakiCdFrom;
  }

  
  public String getCondSeikyusakiCdTo() {
    return condSeikyusakiCdTo;
  }

  
  public void setCondSeikyusakiCdTo(String condSeikyusakiCdTo) {
    this.condSeikyusakiCdTo = condSeikyusakiCdTo;
  }

  
  public String getCondNyukinYoteibiFrom() {
    return condNyukinYoteibiFrom;
  }

  
  public void setCondNyukinYoteibiFrom(String condNyukinYoteibiFrom) {
    this.condNyukinYoteibiFrom = condNyukinYoteibiFrom;
  }

  
  public String getCondNyukinYoteibiTo() {
    return condNyukinYoteibiTo;
  }

  
  public void setCondNyukinYoteibiTo(String condNyukinYoteibiTo) {
    this.condNyukinYoteibiTo = condNyukinYoteibiTo;
  }

  
  public List<NyuSeikyusakiInfo> getNyuSeikyusakiList() {
    return nyuSeikyusakiList;
  }

  
  public void setNyuSeikyusakiList(
      List<NyuSeikyusakiInfo> nyuSeikyusakiList) {
    this.nyuSeikyusakiList = nyuSeikyusakiList;
  }

  
  public String getSelectIdx() {
    return selectIdx;
  }

  
  public void setSelectIdx(String selectIdx) {
    this.selectIdx = selectIdx;
  }
  
}
