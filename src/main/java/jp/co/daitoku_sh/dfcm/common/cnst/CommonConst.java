/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.cnst
 * ファイル名:CommonConst.java
 *
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/01 1.00 ACT)福田 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.cnst;

/**
 * 定義クラス
 *
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonConst {

  /**
   * 戻り値.
   */
  public static final int SUCCESS = 0;
  public static final int ERROR = -1;

  /**
   * 汎用値.
   */
  /** 成功値. */
  public static final int COM_SUCCESS_INT_VAL = 0;
  /** エラー値. */
  public static final int COM_ERR_INT_VAL = -1;
  public static final Short COM_ERR_SHORT_VAL = -1;
  public static final String JOBTYP = "0";
  public static final String REFLG_YES = "1";
  public static final String REFLG_RE_PROCESSED= "2";
  /**
   * メッセージコード.
   */
  /** {1}に{2}が存在しません. */
  public static final String MSG_CD_COM009E = "COM009-E";
  /** 納品金額上限（{1}円）を超過しました. */
  public static final String MSG_CD_COM004E = "COM004-E";

  /**
   * チェック種別.
   */
  /** 無条件. */
  public static final int CHK_TYPE_UN_COND = 0;

  /**
   * 品目コード区分.
   */
  /** 自社. */
  public static final int ITEM_CD_KBN_JISHA = 0;
  /** 得意先. */
  public static final int ITEM_CD_KBN_CUST = 1;

  /**
   * 店舗区分.
   */
  /** 店有り. */
  public static final int SHOP_KBN_ARI = 1;
  /** 店無し. */
  public static final int SHOP_KBN_NA = 2;

  /**
   * 適用中止フラグ.
   */
  /** 有効. */
  public static final Short DEL_FLG_OFF = 0;
  /** 中止. */
  public static final Short DEL_FLG_ON = 1;

  /**
   * 汎用マスタ区分.
   */
  /** 検索上限値. */
  public static final String CODE_SEARCH_MAX = "Code_Search_Max";
  /** 納品金額上限. */
  public static final String DELI_PRICE_MAX = "Deli_Price_Max_aaaaaaaa";
  /** 便区分取得. */
  public static final String BIN_KB = "Bin_Kb";
  /** 消費税率取得. */
  public static final String TAX_RATE = "Tax_Rate";
  /** 送料品目. */
  public static final String POSTEGE_ITEM = "Postege_Item";
  /** 手数料品目. */
  public static final String FEE_ITEM = "Fee_Item";

  /**
   * 日付フラグ.
   */
  /** システム日付. */
  public static final String DATE_FLG_SYS = "0";
  /** 業務日付. */
  public static final String DATE_FLG_APL = "1";

  /**
   * 丸め種別.
   */
  /** 四捨五入. */
  public static final String ROUND_HALF_UP = "1";
  /** 切り捨て. */
  public static final String ROUND_DOWN = "2";
  /** 切り上げ. */
  public static final String ROUND_UP = "3";

  /**
   * 内税顧客区分.
   */
  /** 外税顧客. */
  public static final String TAX_INC_KB_OUT = "1";
  /** 内税顧客. */
  public static final String TAX_INC_KB_IN = "2";

  /**
   * 伝票行計算金額丸め.
   */
  /** 小数点第一位四捨五入. */
  public static final String SHIPS_RUD_KB_HALF_UP = "1";
  /** 小数点第一位切捨て. */
  public static final String SHIPS_RUD_KB_DOWN = "2";
  /** 小数点第一位切上げ. */
  public static final String SHIPS_RUD_KB_UP = "3";

  /**
   * 伝票区分.
   */
  /** 得意先マスタ. */
  public static final String GENE_MST_KB_CUST = "1";
  /** 伝票採番マスタ. */
  public static final String GENE_MST_KB_IDX = "2";

  /**
   * 採番区分.
   */
  /** 請求先毎. */
  public static final String NUMBERING_KB_BILD = "1";
  /** 得意先毎. */
  public static final String NUMBERING_KB_CUST = "2";
  /** 店舗毎. */
  public static final String NUMBERING_KB_SHOP = "3";

  /**
   * 商品分類.
   */
  /** 商品 */
  public static final String ITEM_CLASS_ITEM = "1";
  /** 送料品目. */
  public static final String ITEM_CLASS_POSTEGE = "2";
  /** 手数料品目. */
  public static final String ITEM_CLASS_FEE = "3";

  /**
   * テーブル更新時の共通項目更新用定数（共通部品は画面ID無いため、クラス名を定義）.
   */
  /** 送料品目. */
  public static final String UPD_PGID = "CommonGetSystemCom";

  /**
   * 日付フォーマット.
   */
  /** 日付フォーマット（YYYYMMDD）. */
  public static final String DATE_FORMAT_YMD = "yyyyMMdd";
  /** 日付フォーマット（YYYY/MM/DD）. */
  public static final String DATE_FORMAT_Y_M_D = "yyyy/MM/dd";
  /** 日付フォーマット（YYYYMM）. */
  public static final String DATE_FORMAT_YM = "yyyyMM";
  /** 日付フォーマット（YYYY/MM）. */
  public static final String DATE_FORMAT_Y_M = "yyyy/MM";
  /** 日付フォーマット（MMDD）. */
  public static final String DATE_FORMAT_MD = "MMdd";
  /** 日付フォーマット（YYYYMMDDHHMMSS）. */
  public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
  /** 日付フォーマット（HHMMSS）. */
  public static final String DATE_FORMAT_HMS = "HHmmss";
  /** 日付フォーマット（HHmmssSSS）. */
  public static final String DATE_FORMAT_HMSS = "HHmmssSSS";

  /**
   * 状況コード.
   */
  /** 登録. */
  public static final String STS_CD_ENTRY = "1";
  /** 無効. */
  public static final String STS_CD_INVALID = "9";

  /**
   * 得意先フラグ.
   */
  /** 対象. */
  public static final String CST_FLG = "1";

  /**
   * 請求先フラグ.
   */
  /** 対象. */
  public static final String BILL_FLG = "1";

  /**
   * 取纏め請求.
   */
  /** する. */
  public static final String BILL_SUM_FLG = "2";

  /**
   * 店舗コードWK.
   */
  /** 店無し. */
  public static final String SHOP_CD_NONE = "NONE";

  /**
   * 発生マスタ区分.
   */
  /** 商品変換マスタ. */
  public static final String GEN_MASTER_KBN_ITEM_MASTER = "1";
  /** 製品マスタ. */
  public static final String GEN_MASTER_KBN_PRODUCT_MASTER = "2";
  /** 製品事業所マスタ. */
  public static final String GEN_MASTER_KBN_PRODUCT_PLANT_MASTER = "3";
  /** 品目価格マスタ. */
  public static final String GEN_MASTER_KBN_MATERIAL_PRICE_MASTER = "4";
  /** 受注製品履歴. */
  public static final String GEN_MASTER_KBN_ORDER_PRODUCT_HISTORY = "5";

  /** トップページ. */
  public static final String COM0101D02_SCREEN_NAME = "トップページ";

    /** パスワード変更. */
  public static final String COM0101D03_SCREEN_NAME = "パスワード変更";
  public static final String COM0103D01_SCREEN_NAME = "ジョブ実行状況照会";
  public static final String MST0102D01_SCREEN_NAME = "得意先マスタ";
  public static final String MST0103D01_SCREEN_NAME = "店舗マスタメンテナンス";
  public static final String MST0199D00_SCREEN_NAME = "汎用マスタ";
  public static final String MST0101D00_SCREEN_NAME = "担当者マスタ";
  public static final String JUC0101D00_SCREEN_NAME = "受注登録";
  public static final String URI0101D00_SCREEN_NAME = "売上登録";
  public static final String URI0102D00_SCREEN_NAME = "入金調整売上登録";
  public static final String URI0103D00_SCREEN_NAME = "再請求売上登録";
  public static final String URI0105D00_SCREEN_NAME = "修正データ照合";
  public static final String SEI0101D00_SCREEN_NAME = "請求チェックリスト印刷";
  public static final String SEI0102D00_SCREEN_NAME = "請求締め処理";
  public static final String SEI0103D00_SCREEN_NAME = "臨時請求締め処理";
  public static final String SEI0104D00_SCREEN_NAME = "請求締め取消処理";
  public static final String SEI0105D00_SCREEN_NAME = "取り纏め請求";
  public static final String SEI0106D00_SCREEN_NAME = "請求一覧";
  public static final String SEI0107D00_SCREEN_NAME = "請求集計表";
  public static final String SEI0108D00_SCREEN_NAME = "請求集計表";
  public static final String NYU0102D00_SCREEN_NAME = "入金登録";
  public static final String NYU0104D00_SCREEN_NAME = "入金予定一覧";
  public static final String NYU0106D00_SCREEN_NAME = "会計入金実績作成";
  public static final String MST0104D00_SCREEN_NAME = "コースマスタメンテナンス";
  public static final String MST0105D00_SCREEN_NAME = "オンライン得意先変換マスタ";
  public static final String MST0108D00_SCREEN_NAME = "得意先品目価格マスタ";
  public static final String MST0109D00_SCREEN_NAME = "訂正区分マスタ";
  public static final String GET0101D00_SCREEN_NAME = "事業所月次確定";
  public static final String GET0105D00_SCREEN_NAME = "売上債権状況出力";

  /** CSVでデリミタ 定義. */
  // Delimiter used in CSV file
  public static final String NEW_LINE_SEPARATOR = "\n";

  ////////// ACT追加分 start ////////////////////////////////////

  /**
   * 共通部品＿データ取得：品目明細情報の出荷伝票出力品コード.
   */
  /** 自社品目コード. */
  public static final String DELIVERY_OUTPUT_CD_JISHA = "1";
  /** 相手商品コード. */
  public static final String DELIVERY_OUTPUT_CD_AITE = "2";

  /**
   * 共通部品＿データ取得：納品策情報のエントリ日付WK.
   */
  /** 当日. */
  public static final short ENTRY_DATE_KB_CURRENT_DAY = 1;
  /** 前日. */
  public static final short ENTRY_DATE_KB_PREV_DAY = 2;
  /** 前々日. */
  public static final short ENTRY_DATE_KB_PREV_2_DAY = 3;
  /** 便なし. */
  public static final short ENTRY_DATE_KB_NOT_SET = 9;

  /**
   * 勘定科目マスタの科目区分.
   */
  /** 関係会社. */
  public static final String SUBJECT_KB_REL_COM = "03";

  /**
   * 汎用マスタの区分.
   */
  /** Slip_Type区分. */
  public static final String GEN_KB_SLIP_TYPE = "Slip_Type";
  /** Bild_Type区分. */
  public static final String GEN_KB_BILD_TYPE = "Bild_Type";
  /** Deli_Kb区分. */
  public static final String GEN_KB_DELI_TYPE = "Deli_Kb";

  /**
   * 帳票定義マスタの伝票区分.
   */
  /** 出荷伝票. */
  public static final String LIST_FORM_SLIP_KB_DELIVERY = "1";
  /** 請求書. */
  public static final String LIST_FORM_SLIP_KB_BILL = "2";

  /**
   * 共通部品【データ取得.得意先情報取得】の検索対象区分.
   */
  /** 請求先. */
  public static final int GET_CUSTOMER_DATA_BILLING_TARGET = 2;

  /**
   * 送信済みフラグ（新サンデリカシステムへの送信フラグ）.
   */
  /** 未送信. */
  public static final String SEND_FLAG_UNSEND = "0";
  /** 送信済. */
  public static final String SEND_FLAG_SENT = "1";
  ////////// ACT追加分 end ////////////////////////////////////
  public static final String EMPTY = "";
  public static final String COOKIE_KEY = "ck-COM01-01D01-01";
  public static final int COOKIE_MAX_AGE = 1209600; // 2 weeks
  public static final String SYS_ADMIN = "1";
  public static final String GEN_USERS = "0";
  public static final String ACTIVE_STATUS = "1";
  public static final String SUSPENSION_STATUS = "2";
  public static final String LOCKOUT_STATUS = "3";
  public static final int MAX_NUM_ERR_LOGIN = 5;

  public static final String REGISTRATION_STSCODE = "1";

  public static final String DB_DATE_FORMAT_YMD = "yyyy-MM-dd";
  public static final String LOG_DATE_FOMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss.SSS";

  public static final String AUTH_CODE_NOT_APPLICABLE = "0";
  public static final String AUTH_CODE_APPLICABLE = "1";

  /** GEN.行数  */
  public static final String GEN_NUMBER_LINE_1 = "1";
  public static final String GEN_NUMBER_LINE_2 = "2";
  public static final String GEN_NUMBER_LINE_3 = "3";
  public static final String GEN_NUMBER_LINE_4 = "4";
  public static final String GEN_NUMBER_LINE_5 = "5";
  public static final String GEN_NUMBER_LINE_6 = "6";
  public static final String GEN_NUMBER_LINE_7 = "7";
  public static final String GEN_NUMBER_LINE_8 = "8";
  public static final String GEN_NUMBER_LINE_9 = "9";
  public static final String GEN_NUMBER_LINE_10 = "10";

  /** ジョブ監視更新.  */
  public static final String NO_JOB = "05";
  public static final long NUMBER_CNT = 1;
  public static final String TYPE_ORDINARY = "1";
  public static final String TYPE_ANY_TIME = "2";
  public static final String TYPE_FIXED = "3";
  public static final String JOB_TYPE_ORDINARY = "0";
  public static final String JOB_TYPE_ANY_TIME = "1";
  public static final String COMPANY_WIDE = "00";

  /** システム共通エラーリスト.  */
  public static final Short ZERO_VALUE = 0;
  public static final String ISSUE_TYPE = "1";
  public static final int VALUE_TYPE_ERROR_1 = 1;
  public static final int VALUE_TYPE_ERROR_2 = 2;
  public static final int VALUE_TYPE_ERROR_3 = 3;

  /** 処理区分（新規登録） */
  public static final String SHORI_KB_ADD = "1";
  /** 処理区分（訂正） */
  public static final String SHORI_KB_MNT = "2";
  /** 処理区分（取消） */
  public static final String SHORI_KB_DEL = "3";
  /** 処理区分（照会） */
  public static final String SHORI_KB_REF = "4";

  /** システム管理者区分(システム管理者) */
  public static final String SYS_ADMIN_FLG_VALID = "1";
  /** システム管理者区分(システム管理者以外) */
  public static final String SYS_ADMIN_FLG_INVALID = "0";

  /** ログイン情報取得キー(ユーザコード) */
  public static final String LOGIN_USER_CODE = "UserCode";
  /** ログイン情報取得キー(ユーザ名) */
  public static final String LOGIN_USER_NAME = "UserNm";
  /** ログイン情報取得キー(ユーザ名カナ) */
  public static final String LOGIN_USER_NAME_KANA = "UserNmKana";
  /** ログイン情報取得キー(利用権限コード) */
  public static final String LOGIN_USER_AUTH_CODE = "AuthCode";
  /** ログイン情報取得キー(システム管理者フラグ) */
  public static final String LOGIN_USER_SYS_ADMIN_FLG = "SysAdminFlg";
  /** ログイン情報取得キー(事業所コード) */
  public static final String LOGIN_USER_JIGYOSHO_CODE = "JigyoshoCode";
  /** ログイン情報取得キー(事業所名) */
  public static final String LOGIN_USER_JIGYOSHO_MEI = "jgymei";  
  /** ログイン情報取得キー(業務日付) */
  public static final String LOGIN_USER_BUSSINES_DATE = "currDate";

  /** 伝票区分（自社伝票）*/
  public static final String DNP_KBN_JISHA = "01";

  /** 未収金区分（通常） */
  public static final String ACC_DUE_KB_NORMAL = "0";
  /** 未収金区分（未収金） */
  public static final String ACC_DUE_KB_UNCOLLECTED = "1";

  /** レコード区分（黒） */
  public static final String REC_KB_BLACK = "0";
  /** レコード区分（赤） */
  public static final String REC_KB_RED = "1";

  /** 受注売上判定フラグ（受注） */
  public static final String JUC_URI_JUDGE_FLG_JUC = "1";

  /** 先日付データフラグ（当日日付） */
  public static final String FWD_DELI_DATE_TODAY = "0";
  /** 先日付データフラグ（先日付） */
  public static final String FWD_DELI_DATE_FWD = "1";

 }
