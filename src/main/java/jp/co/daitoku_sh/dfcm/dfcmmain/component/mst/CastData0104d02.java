/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:CastData0104d02.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/29
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/29 1.00 ABV)NhungMa
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.util.ArrayList;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;

/**
 * A holder for screen elements
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
public class CastData0104d02 {

  private String jigyoCode;
  private String courseCode;
  private String courseName;
  private String courseNameR;
  private String haisoTime01;
  private String haisoTime02;
  private String haisoTime;
  private String shipUpdTime01;
  private String shipUpdTime02;
  private String shipUpdTime;
  private String haisoKb;
  private String stsCode01;
  private ArrayList<CrsLst0104d02Data> dataList;
  private String haitaDate;
  private String haitaTime;
  private ErrorMessages errorMessage;
  private String type;
  private String mode;
  private String errorControl;
  private ArrayList<ErrorMessages> errorMess;
  private String strId;
  
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

  public String getCourseNameR() {
    return courseNameR;
  }

  public void setCourseNameR(String courseNameR) {
    this.courseNameR = courseNameR;
  }
  
  public String getHaisoTime01() {
    return haisoTime01;
  }
  
  public void setHaisoTime01(String haisoTime01) {
    this.haisoTime01 = haisoTime01;
  }
  
  public String getHaisoTime02() {
    return haisoTime02;
  }
  
  public void setHaisoTime02(String haisoTime02) {
    this.haisoTime02 = haisoTime02;
  }

  public String getHaisoTime() {
    return haisoTime;
  }

  public void setHaisoTime(String haisoTime) {
    this.haisoTime = haisoTime;
  }
  
  public String getShipUpdTime01() {
    return shipUpdTime01;
  }
  
  public void setShipUpdTime01(String shipUpdTime01) {
    this.shipUpdTime01 = shipUpdTime01;
  }
  
  public String getShipUpdTime02() {
    return shipUpdTime02;
  }
  
  public void setShipUpdTime02(String shipUpdTime02) {
    this.shipUpdTime02 = shipUpdTime02;
  }

  public String getShipUpdTime() {
    return shipUpdTime;
  }

  public void setShipUpdTime(String shipUpdTime) {
    this.shipUpdTime = shipUpdTime;
  }
  
  public String getHaisoKb() {
    return haisoKb;
  }

  public void setHaisoKb(String haisoKb) {
    this.haisoKb = haisoKb;
  }

  public String getStsCode01() {
    return stsCode01;
  }

  public void setStsCode01(String stsCode01) {
    this.stsCode01 = stsCode01;
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

  public ArrayList<CrsLst0104d02Data> getDataList() {
    return dataList;
  }

  public void setDataList(ArrayList<CrsLst0104d02Data> dataList) {
    this.dataList = dataList;
  }

  public ErrorMessages getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(ErrorMessages errorMessage) {
    this.errorMessage = errorMessage;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getErrorControl() {
    return errorControl;
  }

  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }
  
  public ArrayList<ErrorMessages> getErrorMess() {
    return errorMess;
  }
  
  public void setErrorMess(ArrayList<ErrorMessages> errorMess) {
    this.errorMess = errorMess;
  }
  
  public String getStrId() {
    return strId;
  }
  
  public void setStrId(String strId) {
    this.strId = strId;
  }

}