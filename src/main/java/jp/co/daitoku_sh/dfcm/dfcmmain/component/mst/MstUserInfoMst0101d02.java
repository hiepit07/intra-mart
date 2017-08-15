/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstUserInfoMst0101d02.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.Date;
import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 担当者情報のクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstUserInfoMst0101d02 {

  private String userCode;
  private String userNm;
  private String userNmKana;
  private String jigyoshoCode;
  private String jgymei;
  private String authCode;
  private String targetAuth;
  private String userPost;
  private String mailAdr;
  private String telNo;
  private String faxNo;
  private String userStatus;
  private String targetUserStatus;
  private Short loginErrCnt;
  private Date lockoutDatetime;
  private Date lastLoginDatetime;
  private String stsCode;
  private String insUserid;
  private String insPgid;
  private String insYmd;
  private String insTime;
  private String updUserid;
  private String updPgid;
  private String updYmd;
  private String updTime;

  // エラーメッセージ
  private String message;
  private String type;
  
  //エラーメッセージ
  private String haitaDate;
  private String haitaTime;

  public void setUserCode(String userCode) {
    this.userCode = Util.convertSanitize(userCode);
  }

  public void setUserNm(String userNm) {
    this.userNm = Util.convertSanitize(userNm);
  }

  public void setUserNmKana(String userNmKana) {
    this.userNmKana = Util.convertSanitize(userNmKana);
  }

  public void setJigyoshoCode(String jigyoshoCode) {
    this.jigyoshoCode = Util.convertSanitize(jigyoshoCode);
  }

  public void setJgymei(String jgymei) {
    this.jgymei = Util.convertSanitize(jgymei);
  }

  public void setAuthCode(String authCode) {
    this.authCode = Util.convertSanitize(authCode);
  }

  public void setTargetAuth(String targetAuth) {
    this.targetAuth = Util.convertSanitize(targetAuth);
  }

  public void setUserPost(String userPost) {
    this.userPost = Util.convertSanitize(userPost);
  }

  public void setMailAdr(String mailAdr) {
    this.mailAdr = Util.convertSanitize(mailAdr);
  }

  public void setTelNo(String telNo) {
    this.telNo = Util.convertSanitize(telNo);
  }

  public void setFaxNo(String faxNo) {
    this.faxNo = Util.convertSanitize(faxNo);
  }

  public void setUserStatus(String userStatus) {
    this.userStatus = Util.convertSanitize(userStatus);
  }

  public void setTargetUserStatus(String targetUserStatus) {
    this.targetUserStatus = Util.convertSanitize(targetUserStatus);
  }

  public void setLoginErrCnt(Short loginErrCnt) {
    this.loginErrCnt = loginErrCnt;
  }

  public void setLockoutDatetime(Date lockoutDatetime) {
    this.lockoutDatetime = lockoutDatetime;
  }

  public void setLastLoginDatetime(Date lastLoginDatetime) {
    this.lastLoginDatetime = lastLoginDatetime;
  }

  public void setStsCode(String stsCode) {
    this.stsCode = Util.convertSanitize(stsCode);
  }

  public void setInsUserid(String insUserid) {
    this.insUserid = Util.convertSanitize(insUserid);
  }

  public void setInsPgid(String insPgid) {
    this.insPgid = Util.convertSanitize(insPgid);
  }

  public void setInsYmd(String insYmd) {
    this.insYmd = Util.convertSanitize(insYmd);
  }

  public void setInsTime(String insTime) {
    this.insTime = Util.convertSanitize(insTime);
  }

  public void setUpdUserid(String updUserid) {
    this.updUserid = Util.convertSanitize(updUserid);
  }

  public void setUpdPgid(String updPgid) {
    this.updPgid = Util.convertSanitize(updPgid);
  }

  public void setUpdYmd(String updYmd) {
    this.updYmd = Util.convertSanitize(updYmd);
  }

  public void setUpdTime(String updTime) {
    this.updTime = Util.convertSanitize(updTime);
  }

  public String getUserCode() {
    return userCode;
  }

  public String getUserNm() {
    return userNm;
  }

  public String getUserNmKana() {
    return userNmKana;
  }

  public String getJigyoshoCode() {
    return jigyoshoCode;
  }

  public String getJgymei() {
    return jgymei;
  }

  public String getAuthCode() {
    return authCode;
  }

  public String getTargetAuth() {
    return targetAuth;
  }

  public String getUserPost() {
    return userPost;
  }

  public String getMailAdr() {
    return mailAdr;
  }

  public String getTelNo() {
    return telNo;
  }

  public String getFaxNo() {
    return faxNo;
  }

  public String getUserStatus() {
    return userStatus;
  }

  public String getTargetUserStatus() {
    return targetUserStatus;
  }

  public Short getLoginErrCnt() {
    return loginErrCnt;
  }

  public Date getLockoutDatetime() {
    return lockoutDatetime;
  }

  public Date getLastLoginDatetime() {
    return lastLoginDatetime;
  }

  public String getStsCode() {
    return stsCode;
  }

  public String getInsUserid() {
    return insUserid;
  }

  public String getInsPgid() {
    return insPgid;
  }

  public String getInsYmd() {
    return insYmd;
  }

  public String getInsTime() {
    return insTime;
  }

  public String getUpdUserid() {
    return updUserid;
  }

  public String getUpdPgid() {
    return updPgid;
  }

  public String getUpdYmd() {
    return updYmd;
  }

  public String getUpdTime() {
    return updTime;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
