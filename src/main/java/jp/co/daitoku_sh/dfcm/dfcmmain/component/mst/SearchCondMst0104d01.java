/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:SearchCondMst0104d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/25
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * 検索条件のクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

public class SearchCondMst0104d01 {

  private String jigyoCode;
  private String courseCode;
  private String courseName;
  private String haisoKb;
  private String cancelData;

  public String getJigyoCode() {
    return jigyoCode;
  }

  public void setJigyoCode(String jigyoCode) {
    this.jigyoCode = jigyoCode;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }
  
  public String getHaisoKb() {
    return haisoKb;
  }
  
  public void setHaisoKb(String haisoKb) {
    this.haisoKb = haisoKb;
  }

  public String getCancelData() {
    return cancelData;
  }

  public void setCancelData(String cancelData) {
    this.cancelData = cancelData;
  }

}