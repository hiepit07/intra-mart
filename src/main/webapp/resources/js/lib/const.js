/**
 * ファイル名:const.js 
 * 概要:定義値
 * 
 * 作成者:ABV）コイー 作成日:2015/08/24
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV）コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

/**
 * 表示モード定義
 */
// jStorageの受信デリミター
COMMON_DELIMITER = "##";
// 新規モード
SHINKI_MODE = 1;
SHINKI_MODE_CHAR = "1";
// 照会モード
SHOUKAI_MODE = 2;
SHOUKAI_MODE_CHAR = "2";
// 訂正モード
TEISEI_MODE = 3;
TEISEI_MODE_CHAR = "3";
// 取消モード
TORIKESI_MODE = 4;
TORIKESI_MODE_CHAR = "4";
//コピー
COPY_MODE = 5;
COPY_MODE_CHAR = "5"
/**
 * システム管理者フラグ
 */
// 一般ユーザ
SYS_ADMIN_FLAG_USER = "0";
// システム管理者
SYS_ADMIN_FLAG_ADMIN = "1";
//チェック値
CHECK_OFF = "0";
CHECK_ON = "1";
/**
 * AJAXデータを取得する時、エラーが発生する場合、エラー文字を戻す
 */
AJAX_GETDATA_ERROR = "error";
AJAX_INPUTCHECK_ERROR = "input_error";

/**
 * Exceptionが発生する時、エラー内容を表示する
 */
EXCEPTION_ERROR = "例外エラー";

/**
 * フォーカスが外れた場合のTimeout(milliseconds)
 */
FOCUSOUT_TIMEOUT = 500;

/**
 * 請求先検索の検索対象
 */
// 請求先
BILL_SEARCH_TARGET_BILLING = "2";

CHARACTER = '1';
NUMERICAL_VALUE = '2';


ADD_MODE = '1';

UPDATE_MODE = '2';
THERE_STORE = '1';
CUSTOMERS = '1';
NORMAL = '0';
ABNORMALITY = '1';
CAVEAT = '2';
WHETHER = '0';
RE_PROCESSED = '2';
YES = '1';

FLAG_ENABLE = 1;
FLAG_ENABLE_CHAR = '1';
FLAG_DISABLE = 0;
FLAG_DISABLE_CHAR = '0';

LENGTH_COURSE_CODE = 5;
LENGTH_ZERO = 0;
JISHA_HAISO = '1';
TAKUHAIBIN = '2';
TOROKU = '1';
TORIKESHI = '9';
FILL_ZERO = '0';
CHECK_BINKB_VALUE_1 = "1";
CHECK_BINKB_VALUE_9 = "9";
ADD_FLAG = "1";

MSG_INFO = "info";
MSG_ERR = "error";

SEARCH_KB = "1";

/** [画面]出力形式（請求先）*/
OUTPUT_FORMAT_BILLCUSTFLAG = "0";
/** [画面]出力形式（得意先/店舗毎）*/
OUTPUT_FORMAT_CUSTSHOPFLAG = "1";
/** [画面]出力形式（伝票毎）*/
OUTPUT_FORMAT_DENPYOFLAG = "2";