/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:Url.java
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

/**
 * 各機能のリンク先URLとする
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class Url {

  /** [変数]機能ID */
  private String funcId;
  /** [画面]機能名 */
  private String funcName;
  /** [変数]URL */
  private String url;

  public String getFuncId() {
    return funcId;
  }

  public void setFuncId(String funcId) {
    this.funcId = funcId;
  }

  public String getFuncName() {
    return funcName;
  }

  public void setFuncName(String funcName) {
    this.funcName = funcName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
