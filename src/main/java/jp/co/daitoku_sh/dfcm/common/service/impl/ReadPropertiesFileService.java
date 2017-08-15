/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.service.impl
 * ファイル名:ReadPropertiesFileService.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.service.impl;

import java.util.ArrayList;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;

/**
 * サービスクラス
 * Propertiesファイルを読む
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ReadPropertiesFileService {

  @Autowired
  private Properties applicationProperties;

  @Autowired
  private Properties systemProperties;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * Propertiesファイルを読む.
   * 
   * @param strMessCode:メッセージコード
   * @param arrItems:array項目
   * @return メッセージ内容
   */
  public String getMessage(String strMessCode, ArrayList<String> arrItems) {
    String strMessage = "";
    try {
      strMessage = applicationProperties.getProperty(strMessCode);
      //メッセージファイル登録されていないメッセージコードが指定された場合、メッセージコード［COM999-E］を出力する。
      if (strMessage == null || strMessage.equalsIgnoreCase("")) {
        return applicationProperties.getProperty("COM999-E");
      }
      if (arrItems != null) {
        for (int i = 0; i < arrItems.size(); i++) {
          if (strMessage.indexOf("{0}") != -1) {
            strMessage = strMessage.replace("{0}", arrItems.get(i));
          }else {
            if (strMessage.indexOf("{" + (i + 1) + "}") != -1) {
              strMessage = strMessage.replace("{" + (i + 1) + "}", arrItems.get(i));
            }
          }
        }
      }      
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      return "例外エラー";
    }
    return strMessage;
  }

  /**
   * システムPropertiesファイルを読む.
   * @param strId:設定コード
   * @return 設定内容
   */
  public String getSetting(String strId) {
    String strSetting = "";
    try {
      strSetting = systemProperties.getProperty(strId);
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      return "例外エラー";
    }
    return strSetting;
  }
}