package jp.co.daitoku_sh.dfcm.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

/**
 * パッケージ名: jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:Dfcmlogger.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/08 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

/**
 * Log4j wrapper
 * 共通関数
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class Dfcmlogger {

  private static Logger logger = Logger.getLogger(Dfcmlogger.class);

  private SimpleDateFormat dateFormat;
  private String logFolderPath = "";
  private String logFilePath = "";

  public void error(String message) {
    logger.error(message);
  }

  public void warning(String message) {
    logger.warn(message);
  }

  public void debug(String message) {
    logger.debug(message);
  }

  public void info(String message) {
    logger.info(message);
  }

  /**
   * システムローグの書き込む。
   * 
   * @param readPropertiesFileService : メッセージPropertiesファイルを読む
   * @param functionId : 機能ID
   * @param messageCode : メッセージコード
   */
  public void sysLog(ReadPropertiesFileService readPropertiesFileService,
      String functionId, String messageCode, ArrayList<String> repStrList) {
    if (initializeLogFile(readPropertiesFileService, functionId)) {
      writeLogFile(readPropertiesFileService, messageCode, repStrList);
    }
  }

  /**
   * ログフォルダとファイルの存在をチェックして、存在しない場合、作成する.
   * 
   * @param readPropertiesFileService ： メッセージPropertiesファイルを読む
   * @param functionId ： 機能ＩＤ
   * @param messageCode ： メッセージコード
   * @return boolean ： ファイルの作成が成功／失敗
   */
  private boolean initializeLogFile(
      ReadPropertiesFileService readPropertiesFileService, String functionId) {
    // 変数定義
    boolean retVal = false;

    // 現在の日付を取得する
    Calendar cal = Calendar.getInstance();
    Date now = cal.getTime();
    dateFormat = new SimpleDateFormat("yyyyMMdd");
    String nowString = dateFormat.format(now);

    // フォルダのパスを作成する
    try {
      if (logFolderPath.equals("")) {
        logFolderPath = readPropertiesFileService.getSetting("PATH_SYS_LOG")
            + "\\" + functionId + "\\";
      }
      File folder = new File(logFolderPath);
      if (!folder.exists()) {
        folder.mkdirs();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }

    // ログファイルを作成する
    if (!logFolderPath.equals("")) {
      logFilePath = logFolderPath + functionId + "_" + nowString + ".log";
      File file = new File(logFilePath);
      try {
        if (file.exists()) {
          retVal = true;
        } else {
          retVal = file.createNewFile();
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }

    return retVal;
  }

  /**
   * ログファイルを指定されたパスに書き込み
   * 
   * @param messageCode ： メッセージコード
   */
  private void writeLogFile(ReadPropertiesFileService readPropertiesFileService,
      String messageCode, ArrayList<String> repStrList) {
    File file = new File(logFilePath);
    // ファイルの存在をチェックする
    if (file.exists()) {
      try {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
            logFilePath, true)));
        Calendar cal = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        String now = dateFormat.format(cal.getTime());
        out.println(now + " " + messageCode + " " + readPropertiesFileService
            .getMessage(messageCode, repStrList));
        out.close();
      } catch (IOException e) {
        // エラー
        logger.error("Cannot write to log file! Path: " + logFilePath);
      }
    } else {
      // エラー
      logger.error("Cannot open log file! Path: " + logFilePath);
    }
  }
  
  /**
   * ログインローグの書き込む。
   * 
   * 
   * @param readPropertiesFileService : メッセージPropertiesファイルを読む
   * @param message : メッセージコード
   */
  public void loginLog(ReadPropertiesFileService readPropertiesFileService, String message) {
    if (initLoginLogFile(readPropertiesFileService)) {
      writeLoginLogFile(message);
    }
  }

  /**
   * ログフォルダとファイルの存在をチェックして、存在しない場合、作成する.
   * 
   * @param readPropertiesFileService ： メッセージPropertiesファイルを読む
   * @param functionId ： 機能ＩＤ
   * @param messageCode ： メッセージコード
   * @return boolean ： ファイルの作成が成功／失敗
   */
  private boolean initLoginLogFile(
      ReadPropertiesFileService readPropertiesFileService) {
    // 変数定義
    boolean retVal = false;
    // フォルダのパスを作成する
    try {
      if (logFolderPath.equals("")) {
        logFolderPath = readPropertiesFileService.getSetting("PATH_LOGIN_LOG")
            + "\\";
      }
      File folder = new File(logFolderPath);
      if (!folder.exists()) {
        folder.mkdirs();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }

    // ログファイルを作成する
    if (!logFolderPath.equals("")) {
      logFilePath = logFolderPath + "login.log";
      File file = new File(logFilePath);
      try {
        if (file.exists()) {
          retVal = true;
        } else {
          retVal = file.createNewFile();
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }

    return retVal;
  }

  /**
   * ログファイルを指定されたパスに書き込み
   * 
   * @param message ： メッセージコード
   */
  private void writeLoginLogFile(String message) {
    File file = new File(logFilePath);
    // ファイルの存在をチェックする
    if (file.exists()) {
      try {
        OutputStreamWriter out  = new OutputStreamWriter(new FileOutputStream(
            file, true), "UTF-8");
        out.write(message+CommonConst.NEW_LINE_SEPARATOR);
        out.close();
      } catch (IOException e) {
        // エラー
        logger.error("Cannot write to log file! Path: " + logFilePath);
      }
    } else {
      // エラー
      logger.error("Cannot open log file! Path: " + logFilePath);
    }
  }
}
