/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.cnst
 * ファイル名:MstConst.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/15 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.cnst;

/**
 * 定義クラス
 * 
 * @author ABV)コイー
 * @version 1.0.0
 * @since 1.0.0
 */
public class MstConst {

  /** システム管理者フラグ. */
  public static final String SYS_ADMIN_FLG_INVALID = "0";
  public static final String SYS_ADMIN_FLG_VALID = "1";

  /** チェック値. */
  public static final String CHECK_OFF = "0";
  public static final String CHECK_ON = "1";

  /** 画面モード値 "新規" "照会" "訂正" "取消". */
  public static final String SHINKI_MODE = "1";
  public static final String SHOUKAI_MODE = "2";
  public static final String TEISEI_MODE = "3";
  public static final String TORIKESI_MODE = "4";
  public static final String COPY_MODE = "5";

  /** ユーザー状態 1:利用可能 2:利用停止 3:ロックアウト. */
  public static final String USER_STATUS_KANO = "1";
  public static final String USER_STATUS_TEISHI = "2";
  public static final String USER_STATUS_LOCKOUT = "3";
  
  public static final String DELIMITER_ERROR = "##";
  
  public static final String CHARACTER = "1";
  public static final String NUMERICAL_VALUE = "2";
  
  public static final String ADD_MODE = "1";
  public static final String UPDATE_MODE = "2";

  /** 店舗区分のデータ定義. */
  // 店無し
  public static final String SHOP_KUBUN_NO_SHOP = "2";

  /** 内税顧客区分のデータ定義. */
  // 内税
  public static final String TAX_CUSTOMER_KUBUN_INCLUDED = "2";

  /** 請求書種類のデータ定義. */
  // 自社請求書
  public static final String BILLING_TYPE_JISHA = "1";

  /** 請求チェックリスト　出力対象のデータ定義 . */
  // 対象
  public static final String BILLING_CHECKLIST_TARGET = "1";

  /** 請求単位のデータ定義 . */
  // 締め単位
  public static final String BILLING_UNIT_DEADLINE_UNIT = "1";

  /** 入金種別のデータ定義 . */
  // 口座振込
  public static final String PAYMENT_TYPE_ACCOUNT_TRANSFER = "2";
  // 普通振込
  public static final String PAYMENT_TYPE_NORMAL_TRANSFER = "3";

  /** 修正データ突合区分のデータ定義 . */
  // 対象
  public static final String MODIFY_DATA_KUBUN_TARGET = "1";

  /** 請求データ区分のデータ定義 . */
  // オンライン
  public static final String BILLING_DATA_KUBUN_ONLINE = "2";
  
  /** 1：連携対象、2：連携対象外. */
  public static final String RENKEI_TAISHO = "1";
  public static final String RENKEI_TAISHOGAI = "2";
  
  /** HTML改行. */
  public static final String HTML_NEWLINE = "<br />";

  /** 会社関係種別のデータ定義. */
  // 関係会社
  public static final String COMPANY_TYPE_RELATED = "1";

  /** 取纏め請求有無のデータ定義. */
  // する
  public static final String SUM_BILL_KUBUN_ON = "2";

  /** 締日のデータ定義. */
  // 1
  public static final int CLOSE_DAY_MIN = 1;
  // 31
  public static final int CLOSE_DAY_MAX = 31;

  /** 伝票採番のデータ定義. */
  // 請求先毎区分
  public static final String SAIBAN_KUBUN_BILL = "1";
  // 得意先毎区分
  public static final String SAIBAN_KUBUN_CUSTOMER = "2";
  // 上限値
  public static final long SAIBAN_MAX_IDX = 999999;
  // 下限値
  public static final long SAIBAN_MIN_IDX = 1;
  // 有効桁数
  public static final short SAIBAN_VALID_DIGIT = 6;
  // 0埋め
  public static final String SAIBAN_ZERO_SUPPRESS = "1";
  // 採番番号
  public static final long SAIBAN_DAT_IDX = 1;

  /** 郵便番号／電話番号／FAX番号のハイフンマーク. */
  public static final String HYPHEN_MARK = "-";
  
  public static final int PROCESS_TYPE_LIST = 1;
  
  public static final int PROCESS_TYPE_CSV = 2;
  
  public static final String Sale_Kb = "Sale_Kb";
  public static final String NORMAL_STORE = "0";
  public static final String COLLECTION_DESTINATION_STORE = "1";
  public static final String CANCELLATION = "9";
  
  /** 得意先コード_未指定. */
  public static final String CUST_HYPHEN_MARK = "-------";
   
  public static final int SIZE_ZERO = 0;
  public static final int ONE_FLAG = 1;
  public static final int ZERO_FLAG = 1;
  public static final Short DEL_FLAG = 0;
  // 状況コード
  public static final String TOROKU = "1";
  public static final String TORIKESHI = "9";
  // 配送区分
  public static final String JISHA_HAISO = "1";
  public static final String TAKUHAIBIN = "2";
  
  //数量ゼロ納品データ連携 連携対象/連携対象外
  public static final String ZERODLVDATFLG_ON = "1"; 
  public static final String ZERODLVDATFLG_OFF = "2";   
  public static final int CHECK_BINKB_VALUE_1 = 1;
  public static final int CHECK_BINKB_VALUE_9 = 9;
  public static final String SHOPKB_HAS_SHOP = "1";
  public static final String SHOPKB_HASNOT_SHOP = "2";
  public static final String ADD_FLAG = "0"; 
  public static final String UPDATE_FLAG = "1";
  public static final String UPDATE_FLAG_NOT = "0";
  public static final String NOSHOP = "2";
  public static final String CUSTOMERS = "1";
  public static final String CUSTOMER_MASTER_ERROR = "1";
  public static final String COLLECTION_DESTINATION_STORE_ERROR = "2";
  public static final String BILDFLG = "1";
  public static final String BILLING = "2";
  public static final String UNIVERSITY_CO_OP_BILL = "4";
  public static final String STORE_AGGREGATION_CONDITION_ERROR = "3";
  public static final String STORE = "2";
  public static final String NONE_ERROR = "0";
  public static final String FIXED_VALUE = "0";
  public static final String FLAG_ENABLE = "1";
  public static final String FLAG_DISABLE = "0";
  public static final String UNSENT = "0";
  public static final long MAX_IDX = 999999;
  public static final long MIN_IDX =  1;
  public static final short VALID_DIGIT = 6;
  public static final long DAT_IDX = 1;
  public static final String TARGET = "1";
  public static final String REGISTRATION = "1";
  public static final String ERRORCONTROLL = "1";
  public static final String RELATIONERRORFLAG_0 = "0";
  public static final String RELATIONERRORFLAG_1 = "1";
  public static final String RELATIONERRORFLAG_2 = "2";
  public static final String RELATIONERRORFLAG_3 = "3";
  // 共通設定 選択状態
  public static final String IS_COMMON = "isCommon";
  public static final String IS_COMMON_VAL = "1";
  // 得意先個別指定' 選択状態
  public static final String NOT_COMMON = "notCommon";
  public static final String NOT_COMMON_VAL = "2";
  //チェック種別              =   '1'（得意先）        
  public static final int CHECK_TYPE = 1;
  
}
