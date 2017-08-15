/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:Mst0102d02ListForm.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/14
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/14 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * Mst0102d02の帳票定義データオブジェクト
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mst0102d02ListForm {

  // 帳票ID
  private String listId;
  // 帳票名
  private String listName;
  // 伝票種別
  private String denCls;
  // 出荷伝票名称／請求書名称
  private String targetName;

  public String getListId() {
    return listId;
  }

  public void setListId(String listId) {
    this.listId = listId;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public String getDenCls() {
    return denCls;
  }

  public void setDenCls(String denCls) {
    this.denCls = denCls;
  }

  public String getTargetName() {
    return targetName;
  }

  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }
}