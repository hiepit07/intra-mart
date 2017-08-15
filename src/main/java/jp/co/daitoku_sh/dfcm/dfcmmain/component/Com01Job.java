/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component;
 * ファイル名:JigyoData.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/10/06
 * 
 * <p>履歴 
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/06 1.00 ABV)TramChu 新規開発
 * -------------------------------------------------------------------------
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 担当者氏名格納クラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
public class Com01Job {

  private long jobNo;
  private String jobTyp;
  private String jobId;
  private String jobNm;
  private String userCodeExec;
  private String userNmExec;  
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private String fileNmExec;
  private String parameters;
  private String result;
  private String reFlg;
  private String message;
  // エラーメッセージ
  private String messageEr;
  private String type;
  private String focus;

  public long getJobNo() {
    return jobNo;
  }

  public void setJobNo(long jobNo) {
    this.jobNo = jobNo;
  }

  public String getJobTyp() {
    return Util.convertSanitize(jobTyp);
  }

  public void setJobTyp(String jobTyp) {
    this.jobTyp = jobTyp;
  }

  public String getJobNm() {
    return Util.convertSanitize(jobNm);
  }

  public void setJobNm(String jobNm) {
    this.jobNm = jobNm;
  }

  public String getUserNmExec() {
    return Util.convertSanitize(userNmExec);
  }

  public void setUserNmExec(String userNmExec) {
    this.userNmExec = userNmExec;
  }

  public String getStartDate() {
    return Util.convertSanitize(startDate);
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getStartTime() {
    return Util.convertSanitize(startTime);
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndDate() {
    return Util.convertSanitize(endDate);
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getEndTime() {
    return Util.convertSanitize(endTime);
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getFileNmExec() {
    return Util.convertSanitize(fileNmExec);
  }

  public void setFileNmExec(String fileNmExec) {
    this.fileNmExec = fileNmExec;
  }

  public String getParameters() {
    return Util.convertSanitize(parameters);
  }

  public void setParameters(String parameters) {
    this.parameters = parameters;
  }

  public String getResult() {
    return Util.convertSanitize(result);
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getReFlg() {
    return Util.convertSanitize(reFlg);
  }

  public void setReFlg(String reFlg) {
    this.reFlg = reFlg;
  }

  public String getMessage() {
    return Util.convertSanitize(message);
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return Util.convertSanitize(type);
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFocus() {
    return Util.convertSanitize(focus);
  }

  public void setFocus(String focus) {
    this.focus = focus;
  }

  public String getJobId() {
    return Util.convertSanitize(jobId);
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getMessageEr() {
    return messageEr;
  }

  public void setMessageEr(String messageEr) {
    this.messageEr = messageEr;
  }

  public String getUserCodeExec() {
    return userCodeExec;
  }

  public void setUserCodeExec(String userCodeExec) {
    this.userCodeExec = userCodeExec;
  }

}
