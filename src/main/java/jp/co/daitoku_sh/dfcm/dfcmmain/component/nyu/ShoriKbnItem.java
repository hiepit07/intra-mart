/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:ShoriKbnItem.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/10
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 処理区分 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class ShoriKbnItem {

  private String title;
  private String value;

  public ShoriKbnItem(String title, String value) {
    this.title = title;
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
