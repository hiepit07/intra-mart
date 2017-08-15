/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:Mst0102d01ExportCsv.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/07
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * Mst0102d01のＣＳＶファイル出力データオブジェクト
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mst0102d01ExportCsv {

  // チェーンコード
  private String chainCode;
  // チェーン枝番
  private String chainEda;
  // チェーン名
  private String chainName;
  // 営業担当者コード
  private String eigyouTantoushaCode;
  // 営業担当者氏名
  private String eigyouTantoushaName;
  // 事務担当者コード
  private String jimuTantoushaCode;
  // 事務担当者氏名
  private String jimuTantoushaName;
  // 検索結果
  private String searchResult;
  // ＣＳＶファイルパス
  private String csvPath;
  // エラーメッセージ
  private String errorMessage;
  // エラーＩＤ
  private String errorIdString;

  public String getChainCode() {
    return chainCode;
  }

  public void setChainCode(String chainCode) {
    this.chainCode = Util.convertSanitize(chainCode);
  }

  public String getChainEda() {
    return chainEda;
  }

  public void setChainEda(String chainEda) {
    this.chainEda = Util.convertSanitize(chainEda);
  }

  public String getChainName() {
    return chainName;
  }

  public void setChainName(String chainName) {
    this.chainName = Util.convertSanitize(chainName);
  }

  public String getEigyouTantoushaCode() {
    return eigyouTantoushaCode;
  }

  public void setEigyouTantoushaCode(String eigyouTantoushaCode) {
    this.eigyouTantoushaCode = Util.convertSanitize(eigyouTantoushaCode);
  }

  public String getEigyouTantoushaName() {
    return eigyouTantoushaName;
  }

  public void setEigyouTantoushaName(String eigyouTantoushaName) {
    this.eigyouTantoushaName = Util.convertSanitize(eigyouTantoushaName);
  }

  public String getJimuTantoushaCode() {
    return jimuTantoushaCode;
  }

  public void setJimuTantoushaCode(String jimuTantoushaCode) {
    this.jimuTantoushaCode = Util.convertSanitize(jimuTantoushaCode);
  }

  public String getJimuTantoushaName() {
    return jimuTantoushaName;
  }

  public void setJimuTantoushaName(String jimuTantoushaName) {
    this.jimuTantoushaName = Util.convertSanitize(jimuTantoushaName);
  }

  public String getSearchResult() {
    return searchResult;
  }

  public void setSearchResult(String searchResult) {
    this.searchResult = searchResult;
  }

  public String getCsvPath() {
    return csvPath;
  }

  public void setCsvPath(String csvPath) {
    this.csvPath = csvPath;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorIdString() {
    return errorIdString;
  }

  public void setErrorIdString(String errorIdString) {
    this.errorIdString = errorIdString;
  }
}