/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.cnst
 * ファイル名:UriConst.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.cnst;

/**
 * 売上サブシステム定義クラス
 * 
 * @author AB)前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class UriConst {

//  /* 処理区分 */
//  /** 処理区分：新規登録 */
//  public static final int OPERATE_REGIST = 0;
//  /** 処理区分：修正 */
//  public static final int OPERATE_MODIFY = 1;
//  /** 処理区分：取消 */
//  public static final int OPERATE_CANCEL = 2;
//  /** 処理区分：照会 */
//  public static final int OPERATE_VIEW = 3;

  /* 売上照会から渡されるパラメータのキー */
  /** 売上照会からのパラメータ：自社伝票No */
  public static final String PRM_DEN_NO = "denNo";
  /** 売上照会からのパラメータ：処理区分 */
  public static final String PRM_SHORI_KB = "shoriKb";
 
  /* 取消区分 */
  /** 取消区分：取消 */
  public static final String CANCEL_MODE_CANCEL = "0";
  /** 取消区分：データ無効 */
  public static final String CANCEL_MODE_INVALID = "1";
  
  /* メッセージ種別 */
  /** メッセージ種別：確認メッセージ */
  public static final String MESSAGE_TITLE_CONFIRM = "確認メッセージ";
  /** メッセージ種別：エラーメッセージ */
  public static final String MESSAGE_TITLE_ERROR = "エラーメッセージ";
  /** メッセージ種別：確認メッセージ */
  public static final String MESSAGE_TITLE_WARNING = "警告メッセージ";

  /* システム管理者フラグ */
  /** システム管理者フラグ：一般ユーザー */
  public static final String SYS_ADMIN_FLG_INVALID = "0";
  /** システム管理者フラグ：システム管理者 */
  public static final String SYS_ADMIN_FLG_VALID = "1";

  /* 得意先情報取得チェック種別 */
  /** 得意先情報取得チェック種別：無条件 */
  public static final int CHK_TYPE_UN_COND = 0;
  /** 得意先情報取得チェック種別：得意先 */
  public static final int CHK_TYPE_CUSTOMER = 1;
  /** 得意先情報取得チェック種別：請求先 */
  public static final int CHK_TYPE_BILL = 2;
  /** 得意先情報取得チェック種別：取り纏め請求先 */
  public static final int CHK_TYPE_COMPILE = 3;

  /* レコード区分 */
  /** レコード区分：黒 */
  public static final String RECORD_KB_BLACK = "0";
  /** レコード区分：赤 */
  public static final String RECORD_KB_RED = "1";

  /* 商品分類コード */
  /** 商品分類コード：商品 */
  public static final String ITEM_CLASS_ITEM = "1";
  /** 商品分類コード：送料 */
  public static final String ITEM_CLASS_POSTAGE = "2";
  /** 商品分類コード：手数料 */
  public static final String ITEM_CLASS_FEE = "3";

  /* 商品分類コード */
  /** 商品分類コード：商品（propertiesファイルのキー） */
  public static final String ITEM_FORM_CODE = "ITEM_FORM_CODE";

  /* 機能詳細区分 */
  /** 機能詳細区分：製品売上 */
  public static final String FUNC_DTL_KB_PRODUCT = "1";
  /** 機能詳細区分：商品売上 */
  public static final String FUNC_DTL_KB_ITEM = "2";
  /** 機能詳細区分：売掛金 */
  public static final String FUNC_DTL_KB_RECEIVEBLE = "3";
  /** 機能詳細区分：未収金 */
  public static final String FUNC_DTL_KB_ACCRUED = "4";
  /** 機能詳細区分：配送費 */
  public static final String FUNC_DTL_KB_SHIPMENT = "5";
  /** 機能詳細区分：代引手数料 */
  public static final String FUNC_DTL_KB_COD = "6";
  /** 機能詳細区分：仮受消費税 */
  public static final String FUNC_DTL_KB_INPUT_TAX = "7";
  /** 機能詳細区分：仮払消費税 */
  public static final String FUNC_DTL_KB_OUTPUT_TAX = "8";

  /* 科目区分 */
  /** 科目区分：勘定科目 */
  public static final String SUBJECT_KB_ACCOUNT = "01";
  
  /* 補助科目設定区分 */
  /** 補助科目設定区分：なし */
  public static final String SUPSET_KB_NONE = "1";
  /** 補助科目設定区分：固定 */
  public static final String SUPSET_KB_FIXED = "2";
  /** 補助科目設定区分：関係会社コード */
  public static final String SUPSET_KB_RELATE = "3";

  /* 機能区分 */
  /** 機能区分：売上 */
  public static final String FUNC_KB_EARNING = "1";
  
  /* 勘定科目情報マップキー */
  /** 勘定科目情報マップキー：本体貸方 */
  public static final String SUBJECT_MAP_KEY_BODY_CREDIT = "BodyCredit";
  /** 勘定科目情報マップキー：本体借方 */
  public static final String SUBJECT_MAP_KEY_BODY_DEBIT = "BodyDebit";
  /** 勘定科目情報マップキー：消費税貸方 */
  public static final String SUBJECT_MAP_KEY_TAX_CREDIT = "TaxCredit";
  /** 勘定科目情報マップキー：消費税借方 */
  public static final String SUBJECT_MAP_KEY_TAX_DEBIT = "TaxDebit";

  /* 本体・消費税区分 */
  /** 本体・消費税区分：本体 */
  public static final short BODY_TAX_KB_BODY = 0;
  /** 本体・消費税区分：消費税 */
  public static final short BODY_TAX_KB_TAX = 1;

  /* 未収金区分 */
  /** 未収金区分：通常 */
  public static final String ACCRUED_KB_NORMAL = "0";
  /** 未収金区分：未収金 */
  public static final String ACCRUED_KB_ACCRUED = "1";

  /* 自社伝票No区分 */
  /** 自社伝票No区分：自社伝票No */
  public static final String SLIP_KB_OWN = "01";
  /** 自社伝票No区分：モニター入力No */
  public static final String SLIP_KB_MONITOR = "02";
  /** 自社伝票No区分：請求書No */
  public static final String SLIP_KB_BILL = "03";
  /** 自社伝票No区分：入金伝票No */
  public static final String SLIP_KB_PAYMENT = "04";
  /** 自社伝票No区分：ジョブ実行No */
  public static final String SLIP_KB_JOB_EXEC = "05";
  /** 自社伝票No区分：売掛・未収集計組織No */
  public static final String SLIP_KB_AGGREGATE = "06";
  /** 自社伝票No区分：売掛No */
  public static final String SLIP_KB_RECEIVED = "07";
  /** 自社伝票No区分：未収No */
  public static final String SLIP_KB_ACCRUED = "08";
  /** 自社伝票No区分：請求ID */
  public static final String SLIP_KB_BILL_ID = "09";
  /** 自社伝票No区分：入金伝票No（"04"と同様） */
//  public static final String SLIP_KB_PAYMENT = "10";
  /** 自社伝票No区分：入金仕訳No */
  public static final String SLIP_KB_NYU_JOURNAL = "11";
  /** 自社伝票No区分：売上仕訳No */
  public static final String SLIP_KB_URI_JOURNAL = "12";
  /** 自社伝票No区分：手形管理ID */
  public static final String SLIP_KB_URI_TEGATA = "13";
  /** 自社伝票No区分：会計連携ID */
  public static final String SLIP_KB_URI_CORPORATE = "14";

  /* 伝票区分 */
  /** 伝票区分：売上 */
  public static final String DEN_KB_URIAGE = "1";
  /** 伝票区分：返品 */
  public static final String DEN_KB_HENPIN = "2";
  /** 伝票区分：欠品 */
  public static final String DEN_KB_KEPPIN = "3";
  /** 伝票区分：値引 */
  public static final String DEN_KB_NEBIKI = "4";

  /* 店舗区分 */
  /** 店舗区分：店有り */
  public static final String SHOP_KB_TRUE = "1";
  /** 店舗区分：店無し */
  public static final String SHOP_KB_FALSE = "2";

  /* 取引区分 */
  /** 取引区分：売上 */
  public static final String TRANSACTION_KB_URIAGE = "01";
  /** 取引区分：返品 */
  public static final String TRANSACTION_KB_HENPIN = "02";
  /** 取引区分：欠品 */
  public static final String TRANSACTION_KB_KEPPIN = "03";
  /** 取引区分：値引 */
  public static final String TRANSACTION_KB_NEBIKI = "04";

  /* 入力形態 */
  /** 入力形態：売上登録 */
  public static final String REG_TYPE_NORMAL = "1";
  /** 入力形態：入金調整売上登録 */
  public static final String REG_TYPE_PAYMENT = "2";
  /** 入力形態：再請求売上登録 */
  public static final String REG_TYPE_RECLAIM = "3";
  /** 入力形態：その他 */
  public static final String REG_TYPE_OTHER = "4";

  /* データ区分 */
  /** データ区分：オンライン受注データ */
  public static final String DATA_KB_ONLINE = "01";
  /** データ区分：受注登録データ */
  public static final String DATA_KB_JUC = "02";
  /** データ区分：モニター受注登録データ */
  public static final String DATA_KB_MONITOR = "03";
  /** データ区分： 店別一括受注登録 */
  public static final String DATA_KB_SHOP = "04";
  /** データ区分：売上登録データ */
  public static final String DATA_KB_URI = "05";
  /** データ区分：修正登録データ */
  public static final String DATA_KB_MODIFY = "06";
  /** データ区分：受領登録データ */
  public static final String DATA_KB_RECEIVE = "07";
  /** データ区分：入金調整売上登録データ */
  public static final String DATA_KB_PAYMENT = "08";
  /** データ区分：再請求売上登録データ */
  public static final String DATA_KB_RECLAIM = "09";

  /* 修正データ種別 */
  /** 修正データ種別：未修正 */
  public static final String MODIFY_TYPE_NONE = "0";
  /** 修正データ種別：受領 */
  public static final String MODIFY_TYPE_RECEIVE = "1";
  /** 修正データ種別：返品 */
  public static final String MODIFY_TYPE_HENPIN = "2";
  /** 修正データ種別：欠品 */
  public static final String MODIFY_TYPE_KEPPIN = "3";
  /** 修正データ種別：修正 */
  public static final String MODIFY_TYPE_MODIFY = "4";

  /* 連携フラグ */
  /** 連携フラグ：未連携 */
  public static final String PROCESSING_NOTYET = "0";
  /** 連携フラグ：連携済 */
  public static final String PROCESSING_COMPLETE = "1";

  /* 先日付フラグ */
  /** 先日付フラグ：当日日付 */
  public static final String DATE_FLG_TODAY = "1";
  /** 先日付フラグ：先日付 */
  public static final String DATE_FLG_FUTURE = "2";

  /* 取消区分 */
  /** 取消区分：通常 */
  public static final String CANCEL_KB_NORMAL = "0";
  /** 取消区分：取消明細 */
  public static final String CANCEL_KB_CANCEL = "1";

  /* 内税消費税端数処理 */
  /** 内税消費税端数処理：小数点以下切捨て */
  public static final int TIFK_1ST_ROUND_DOWN = 1;
  /** 内税消費税端数処理：小数点以下切上げ */
  public static final int TIFK_1ST_ROUND_UP = 2;
  /** 内税消費税端数処理：小数点第一位四捨五入 */
  public static final int TIFK_1ST_ROUND_HALF_UP = 3;
  /** 内税消費税端数処理：小数点第一位未満切捨て */
  public static final int TIFK_2ND_ROUND_DOWN = 4;
  /** 内税消費税端数処理：小数点第一位未満切上げ */
  public static final int TIFK_2ND_ROUND_UP = 5;
  /** 内税消費税端数処理：小数点第二位四捨五入 */
  public static final int TIFK_2ND_ROUND_HALF_UP = 6;
  /** 内税消費税端数処理：小数点第二位未満切捨て */
  public static final int TIFK_3RD_ROUND_DOWN = 7;
  /** 内税消費税端数処理：小数点第二位未満切上げ */
  public static final int TIFK_3RD_ROUND_UP = 8;
  /** 内税消費税端数処理：小数点第三位四捨五入 */
  public static final int TIFK_3RD_ROUND_HALF_UP = 9;

  /* 締め処理フラグ */
  /** 締め処理フラグ：未処理 */
  public static final String BILL_FLG_NONE = "0";
}
