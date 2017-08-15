/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.com
 * ファイル名:FormCom0102d01.java
 *
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/09/10
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/10 1.00 ABV)グエン　リユオン　ギア 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.com;

/**
 * 変数格納用クラス
 *
 * @author グエン　リユオン　ギア
 *
 * @version 1.0.0
 *
 * @since 1.0.0
 */
public class FormCom0102d01 {

  private String txtChainCode;
  private String txtChainBranch;
  private String txtChainName;
  private int searchMax;

  /**
   * チェーンコード getter。
   * 
   * @return changeCode チェーンコード
   */
  public String gettxtChainCode() {
    return txtChainCode;
  }

  /**
   * チェーンコード setter。
   * 
   * @param changeCode チェーンコード
   */
  public void settxtChainCode(String txtChainCode) {
    this.txtChainCode = txtChainCode;
  }

  /**
   * チェーン枝番 getter。
   * 
   * @return changeBranch チェーン枝番
   */
  public String gettxtChainBranch() {
    return txtChainBranch;
  }

  /**
   * チェーン枝番 setter。
   * 
   * @param changeBranch チェーン枝番
   */
  public void settxtChainBranch(String txtChainBranch) {
    this.txtChainBranch = txtChainBranch;
  }

  /**
   * チェーン名 getter。
   * 
   * @return txtChainName チェーン名
   */
  public String gettxtChainName() {
    return txtChainName;
  }

  /**
   * チェーン名 setter。
   * 
   * @param txtChainName チェーン名
   */
  public void settxtChainName(String txtChainName) {
    this.txtChainName = txtChainName;
  }  

  /**
   * マックスを検索 getter。
   * 
   * @return searchMax マックスを検索
   */
  public int getSearchMax() {
    return searchMax;
  }

  /**
   * マックスを検索 setter。
   * 
   * @param searchMax マックスを検索
   */
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

}
