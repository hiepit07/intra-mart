/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:Menu.java
 * 
 * 作成者:QuanTran
 * 作成日:2015/09/18
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/18 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.common.component;

import java.util.ArrayList;

/**
 * メニュー
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class Menu {

  /** [画面]業務名 */
  private String businessFuncName;

  private ArrayList<Url> urlList;

  public String getBusinessFuncName() {
    return businessFuncName;
  }

  public void setBusinessFuncName(String businessFuncName) {
    this.businessFuncName = businessFuncName;
  }

  public ArrayList<Url> getUrlList() {
    return urlList;
  }

  public void setUrlList(ArrayList<Url> urlList) {
    this.urlList = urlList;
  }

}
