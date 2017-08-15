/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:FormNyu0106d01.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

import java.util.List;

/**
 * 会計入金実績作成用 FORM
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class FormNyu0106d01 {

  /*
   * 画面HIDDEN
   */
  private String btnName;            // ボタン名
  private boolean sysAdminFlag;      // システム管理者フラグ
  private String loginJigyoshoCd;    // ログイン事業所コード
  private String businessDate;       // 業務日付
  private String errorControl;       // エラー制御フラグ
  private boolean showCsvLink;       // CSVリンク表示フラグ
  private String downloadUrl;         // ダウンロードURL
  
  private boolean msgInfoLevel;      // エラーレベル情報
  private boolean msgWarningLevel;   // エラーレベル警告
  private boolean msgErrorLevel;     // エラーレベルエラー
  
  /*
   * CSV出力
   */
  private String csvRenkeiId;      // 連携ID
  private String csvShubetsu;      // 種別
  private String csvCreateDate;    // 作成日付
  private String csvCreateTime;    // 作成時刻
  
  /*
   * 画面項目
   */
  private String prevCreateDate;  // 前回作成日付
  private String prevCreateTime;  // 前回作成時刻
  private int curNyukinCount;     // 今回入金件数
  private int curShiwakeCount;    // 今回仕訳件数
  List<String> csvLinkList;                             // CSVリンクリスト
  List<Nyu0106d01ShiwakeSakuseiRirekiInfo> rirekiList;  // 履歴リスト
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public boolean isSysAdminFlag() {
    return sysAdminFlag;
  }
  
  public void setSysAdminFlag(boolean sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
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
  
  public String getErrorControl() {
    return errorControl;
  }
  
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }
  
  public boolean isShowCsvLink() {
    return showCsvLink;
  }
  
  public void setShowCsvLink(boolean showCsvLink) {
    this.showCsvLink = showCsvLink;
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
  
  public String getPrevCreateDate() {
    return prevCreateDate;
  }

  
  public void setPrevCreateDate(String prevCreateDate) {
    this.prevCreateDate = prevCreateDate;
  }

  
  public String getPrevCreateTime() {
    return prevCreateTime;
  }

  
  public void setPrevCreateTime(String prevCreateTime) {
    this.prevCreateTime = prevCreateTime;
  }

  
  public int getCurNyukinCount() {
    return curNyukinCount;
  }

  
  public void setCurNyukinCount(int curNyukinCount) {
    this.curNyukinCount = curNyukinCount;
  }

  
  public int getCurShiwakeCount() {
    return curShiwakeCount;
  }

  
  public void setCurShiwakeCount(int curShiwakeCount) {
    this.curShiwakeCount = curShiwakeCount;
  }

  
  public List<String> getCsvLinkList() {
    return csvLinkList;
  }

  
  public void setCsvLinkList(List<String> csvLinkList) {
    this.csvLinkList = csvLinkList;
  }

  
  public List<Nyu0106d01ShiwakeSakuseiRirekiInfo> getRirekiList() {
    return rirekiList;
  }

  
  public void setRirekiList(List<Nyu0106d01ShiwakeSakuseiRirekiInfo> rirekiList) {
    this.rirekiList = rirekiList;
  }

  
  public String getBtnName() {
    return btnName;
  }

  
  public void setBtnName(String btnName) {
    this.btnName = btnName;
  }

  
  public String getCsvRenkeiId() {
    return csvRenkeiId;
  }

  
  public void setCsvRenkeiId(String csvRenkeiId) {
    this.csvRenkeiId = csvRenkeiId;
  }

  
  public String getCsvShubetsu() {
    return csvShubetsu;
  }

  
  public void setCsvShubetsu(String csvShubetsu) {
    this.csvShubetsu = csvShubetsu;
  }

  
  public String getCsvCreateDate() {
    return csvCreateDate;
  }

  
  public void setCsvCreateDate(String csvCreateDate) {
    this.csvCreateDate = csvCreateDate;
  }

  
  public String getCsvCreateTime() {
    return csvCreateTime;
  }

  
  public void setCsvCreateTime(String csvCreateTime) {
    this.csvCreateTime = csvCreateTime;
  }

  
  public String getDownloadUrl() {
    return downloadUrl;
  }

  
  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }
  
}
