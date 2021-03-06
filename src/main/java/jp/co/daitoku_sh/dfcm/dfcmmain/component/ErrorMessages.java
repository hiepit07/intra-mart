/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component
 * ファイル名:ErrorMessages.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/18
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/18 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component;

/**
 * defaultメッセージのクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class ErrorMessages {

  private String messageContent;
  private String itemId;  

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

}