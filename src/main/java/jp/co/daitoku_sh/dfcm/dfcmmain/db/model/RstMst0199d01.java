/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.model
 * ファイル名:RstMst0199d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.model;

/**
 * 管理区分一覧データを取得する
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class RstMst0199d01 {

  /** 区分 */
  private String glCode;
  /**  */
  private String kbNm;
  /** 分類コード */
  private String category;
  /** 分類名称 */
  private String target1;
  /** 最終登録者コード */
  private String updUserid;
  /** 最終登録者氏名 */
  private String userNm;
  /** 更新年月日 */
  private String updYmd;
  /** 更新時刻 */
  private String updTime;
  /**  */
  private String dispNumeric;

  public String getGlCode() {
    return glCode;
  }

  public void setGlCode(String glCode) {
    this.glCode = glCode;
  }

  public String getKbNm() {
    return kbNm;
  }

  public void setKbNm(String kbNm) {
    this.kbNm = kbNm;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getTarget1() {
    return target1;
  }

  public void setTarget1(String target1) {
    this.target1 = target1;
  }

  public String getUpdUserid() {
    return updUserid;
  }

  public void setUpdUserid(String updUserid) {
    this.updUserid = updUserid;
  }

  public String getUserNm() {
    return userNm;
  }

  public void setUserNm(String userNm) {
    this.userNm = userNm;
  }

  public String getUpdYmd() {
    return updYmd;
  }

  public void setUpdYmd(String updYmd) {
    this.updYmd = updYmd;
  }

  public String getUpdTime() {
    return updTime;
  }

  public void setUpdTime(String updTime) {
    this.updTime = updTime;
  }

  public String getDispNumeric() {
    return dispNumeric;
  }

  public void setDispNumeric(String dispNumeric) {
    this.dispNumeric = dispNumeric;
  }

}
