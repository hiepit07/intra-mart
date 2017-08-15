/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0199d01.java
 * 
 * 作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.ArrayList;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstMst0199d01;

/**
 * フォーム(jspの modelAttribute="FormMst0199d01" とリンク）
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormMst0199d01 {

  /** [画面]分類.分類コード */
  private String category;
  /** [画面]区分名称 */
  private String kbNm;

  private String viewMode;

  private String selectGlCode;

  private String errMessage;

  private String messageType;

  private String filePath;

  private String haitaDate;

  private String haitaTime;
  
  /** The number of records to return */
  private int searchMax;

  public int getSearchMax() {
    return searchMax;
  }

  public String getHaitaDate() {
    return haitaDate;
  }

  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
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

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getErrMessage() {
    return errMessage;
  }

  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }

  private ArrayList<RstMst0199d01> rstMst0199d01List;

  public ArrayList<RstMst0199d01> getRstMst0199d01List() {
    return rstMst0199d01List;
  }

  public void setRstMst0199d01List(ArrayList<RstMst0199d01> rstMst0199d01List) {
    this.rstMst0199d01List = rstMst0199d01List;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getKbNm() {
    return kbNm;
  }

  public void setKbNm(String kbNm) {
    this.kbNm = kbNm;
  }

  public String getViewMode() {
    return viewMode;
  }

  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  public String getSelectGlCode() {
    return selectGlCode;
  }

  public void setSelectGlCode(String selectGlCode) {
    this.selectGlCode = selectGlCode;
  }

}
