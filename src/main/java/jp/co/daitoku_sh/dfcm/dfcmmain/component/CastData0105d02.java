/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component
 * ファイル名:CastData0105d02.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/11/19
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/19 1.00 ABV)QuanTran
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component;

import java.util.ArrayList;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.OlCustConvMasterData;

/**
 * A class is used to send and receive data
 * in same ajax request with jquery
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class CastData0105d02 {

  private String haitaDate;
  private String haitaTime;
  private String message;
  /** 店舗コード_未指定 */
  private String tenCodeNone;
  /** オンライン取引先コード_未指定 */
  private String olTorihikiCodeNone;
  /** 画面表示モード */
  private String screenMode;
  /** オンライン得意先変換マスタ一覧 */
  private ArrayList<OlCustConvMasterData> olCustConvList;

  public ArrayList<OlCustConvMasterData> getOlCustConvList() {
    return olCustConvList;
  }

  public void setOlCustConvList(
      ArrayList<OlCustConvMasterData> olCustConvList) {
    this.olCustConvList = olCustConvList;
  }

  public String getScreenMode() {
    return screenMode;
  }

  public void setScreenMode(String screenMode) {
    this.screenMode = screenMode;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getOlTorihikiCodeNone() {
    return olTorihikiCodeNone;
  }

  public void setOlTorihikiCodeNone(String olTorihikiCodeNone) {
    this.olTorihikiCodeNone = olTorihikiCodeNone;
  }

  public String getTenCodeNone() {
    return tenCodeNone;
  }

  public void setTenCodeNone(String tenCodeNone) {
    this.tenCodeNone = tenCodeNone;
  }
}
