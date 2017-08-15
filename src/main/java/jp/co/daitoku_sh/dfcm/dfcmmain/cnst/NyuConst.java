/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.cnst
 * ファイル名:NyuConst.java
 * 
 * <p>作成者:都築電気
 * 作成日:2015/12/06
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/06 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.cnst;

/**
 * 入金 定数定義クラス
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class NyuConst {

  /**
   * ボタン名.
   */

  /** 登録 or 実行. */
  public static final String BUTTON_NAME_PROC = "btnProc";
  
  /** 入金内訳書. */
  public static final String BUTTON_NAME_PRINT = "btnPrint";
  
  /** 承認. */
  public static final String BUTTON_NAME_SHONIN = "btnShonin";
  
  /** 否認. */
  public static final String BUTTON_NAME_HININ = "btnHinin";
  
  /** クリア. */
  public static final String BUTTON_NAME_CLEAR = "btnClear";

  /** 表示. */
  public static final String BUTTON_NAME_SEARCH = "btnSearch";
  
  /** 選択. */
  public static final String BUTTON_NAME_SELECT = "btnSelect";
  
  /**
   * プログラムID.
   */
  
  /** 入金登録. **/
  public static final String PG_ID_NYUKIN_TOROKU = "NYU01-02D00";
  
  /** 会計入金実績作成 **/
  public static final String PG_ID_KAIKEI_NYUKIN_JISSEKI_SAKUSEI = "NYU01-06D00";
  
  /**
   * 権限区分.
   */
  
  /** システム管理者 **/
  public static final String AUTH_KBN_SYS_ADMIN = "1";
  
  /** 経理管理者 **/
  public static final String AUTH_KBN_KEIRI_ADMIN = "2";
  
  /** 事業所管理者 **/
  public static final String AUTH_KBN_JIGYOSHO_ADMIN = "3";
  
  /** 担当者 **/
  public static final String AUTH_KBN_TANTOSHA = "0";
  
  /**
   * 処理区分
   */
  
  /** 承認 **/
  public static final String SHORI_KBN_SHONIN = "1";
  
  /** 新規登録 **/
  public static final String SHORI_KBN_SHINKI = "2";
  
  /** 訂正 **/
  public static final String SHORI_KBN_TEISEI = "3";
  
  /** 取消 **/
  public static final String SHORI_KBN_TORIKESHI = "4";
  
  /** 照会 **/
  public static final String SHORI_KBN_SHOKAI = "5";
  
  /** システム管理者フラグ. */
  public static final String SYS_ADMIN_FLG_INVALID = "0";
  public static final String SYS_ADMIN_FLG_VALID = "1";
  
  /** 適用中止フラグ. */
  public static final Short DEL_FLAG = 0;
}
