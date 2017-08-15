/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.cnst
 * ファイル名:SeiConst.java
 * 
 * <p>作成者:都築電気
 * 作成日:2015/11/06
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/06 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.cnst;

/**
 * 請求 定数定義クラス
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SeiConst {

  /**
   * ボタン名.
   */

  /** 検索. */
  public static final String BUTTON_NAME_SEARCH = "btnSearch";

  /** 実行. */
  public static final String BUTTON_NAME_PROC = "btnProc";

  /** 納品明細書出力. */
  public static final String BUTTON_NAME_PRINT_NOHIN_M = "btnPrintNohinMeisai";

  /** 再印刷. */
  public static final String BUTTON_NAME_RE_PRINT_SEIKYUSHO = "btnRePrintSeikyusho";

  /** 請求データ送信. */
  public static final String BUTTON_NAME_SEND_SEIKYU_DATA = "btnSeikyuSend";

  /** クリア. */
  public static final String BUTTON_NAME_CLEAR = "btnClear";

  /** 取消. */
  public static final String BUTTON_NAME_TORIKESHI = "btnTorikeshi";

  /**
   * 請求データ区分.
   */

  /** 請求書. */
  public static final String SEIKYU_DATA_KBN_SEIKYUSHO = "1";

  /** オンライン. */
  public static final String SEIKYU_DATA_KBN_ONLINE = "2";

  /**
   * 帳票ID.
   */

  /** 自社請求書. */
  public static final String CHOHYO_ID_JISHA_SEIKYUSHO = "SEI01-02L01";

  /** イオン請求書. */
  public static final String CHOHYO_ID_AEON_SEIKYUSHO = "SEI01-02L02";

  /** オークワ請求書. */
  public static final String CHOHYO_ID_OKUWA_SEIKYUSHO = "SEI01-02L03";

  /** 大学生協請求書. */
  public static final String CHOHYO_ID_DAIGAKU_SEIKYO_SEIKYUSHO = "SEI01-02L04";

  /** 納品明細書. */
  public static final String CHOHYO_ID_NOHIN_MEISAISHO = "SEI01-02L06";
  
  public static final String CHOHYO_ID_BILL_SEIKYU_ICHIRAN = "SEI01-06L01";
  
  public static final String CHOHYO_ID_CUST_SHOP_SEIKYU_ICHIRAN = "SEI01-06L02";
  
  public static final String CHOHYO_ID_DENPYO_SEIKYU_ICHIRAN = "SEI01-06L03";
  
  /**
   * 帳票発行区分.
   */

  /** 即時. */
  public static final String CHOHYO_HAKKO_SOKUJI = "1";

  /** 溜め置き. */
  public static final String CHOHYO_HAKKO_TAMEOKI = "2";

  /**
   * 請求書種別（帳票定義マスタ.伝票種別）.
   */
  public static final String CHOHYO_CLS_JISHA_SEIKYUSHO = "1";

  public static final String CHOHYO_CLS_AEON_SEIKYUSHO = "2";

  public static final String CHOHYO_CLS_OKUWA_SEIKYUSHO = "3";

  public static final String CHOHYO_CLS_DAIGAKU_SEIKYO_SEIKYUSHO = "4";

  /**
   * 請求書単価.
   */

  /** 納品単価. */
  public static final String SEIKYUSHO_TANKA_NOHIN = "1";

  /** 先方仕切単価. */
  public static final String SEIKYUSHO_TANKA_SEMPO_SHIKIRI = "2";

  /**
   * 消費税計算単位.
   */

  /** 無し. */
  public static final String TAX_KEISAN_TANI_NASHI = "01";

  /** 請求先. */
  public static final String TAX_KEISAN_TANI_SEIKYUSAKI = "02";

  /** 請求先・得意先. */
  public static final String TAX_KEISAN_TANI_SEIKYU_TOKUISAKI = "03";

  /** 請求先・得意先・店舗. */
  public static final String TAX_KEISAN_TANI_SEIKYU_TOKUI_TEMPO = "04";

  /** 伝票No（請求先）. */
  public static final String TAX_KEISAN_TANI_DEN_SEIKYUSAKI = "05";

  /** 伝票No（請求先・得意先）. */
  public static final String TAX_KEISAN_TANI_DEN_SEIKYU_TOKUISAKI = "06";

  /** 伝票No（請求先・得意先・店舗）. */
  public static final String TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO = "07";

  /** 伝票No（請求先）（印字無し）. */
  public static final String TAX_KEISAN_TANI_DEN_SEIKYUSAKI_NOT_PRINT = "08";

  /** 伝票No（請求先・得意先）（印字無し）. */
  public static final String TAX_KEISAN_TANI_DEN_SEIKYU_TOKUISAKI_NOT_PRINT = "09";

  /** 伝票No（請求先・得意先・店舗）（印字無し）. */
  public static final String TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO_NOT_PRINT = "10";

  /**
   * 請求書パターン.
   */

  /** 請求先. */
  public static final String SEIKYUSHO_PATTERN_SEIKYUSAKI = "1";

  /** 請求先・得意先. */
  public static final String SEIKYUSHO_PATTERN_SEIKYU_TOKUISAKI = "2";

  /** 請求先・店舗. */
  public static final String SEIKYUSHO_PATTERN_SEIKYU_TEMPO = "3";

  /** 請求先（残印字なし）. */
  public static final String SEIKYUSHO_PATTERN_SEIKYUSAKI_NOT_ZAN_PRINT = "4";

  /** 請求先・得意先（残印字なし）. */
  public static final String SEIKYUSHO_PATTERN_SEIKYU_TOKUISAKI_NOT_ZAN_PRINT = "5";

  /** 請求先・店舗（残印字なし）. */
  public static final String SEIKYUSHO_PATTERN_SEIKYU_TEMPO_NOT_ZAN_PRINT = "6";

  /** 請求先・得意先・店舗. */
  public static final String SEIKYUSHO_PATTERN_SEIKYU_TOKUI_TEMPO = "7";

  /** 請求先・得意先・店舗（残印字なし）. */
  public static final String SEIKYUSHO_PATTERN_SEIKYU_TOKUI_TEMPO_NOT_ZAN_PRINT = "8";

  /**
   * 回収月区分.
   */

  /** 当月. */
  public static final String KAISHU_TSUKI_KBN_TOGETSU = "1";

  /** 翌月. */
  public static final String KAISHU_TSUKI_KBN_YOKU_1_GETSU = "2";

  /** 翌々月. */
  public static final String KAISHU_TSUKI_KBN_YOKU_2_GETSU = "3";

  /** 翌々々月. */
  public static final String KAISHU_TSUKI_KBN_YOKU_3_GETSU = "4";

  /** 翌々々々月. */
  public static final String KAISHU_TSUKI_KBN_YOKU_4_GETSU = "5";

  /**
   * 回収日月末.
   */
  public static final String KAISHUBI_GETSUMATSU = "31";

  /**
   * 自社伝票番号採番.
   */
  public static final String OWN_SLIP_NO_DEMPYO_KBN_SEIKYU_ID = "09";

  /**
   * 改ページ行数.
   */

  /** 自社請求書. */
  public static final String GL_CD_LINES_PER_PAGE_JISHA_SEIKYUSHO = "01";

  /** オークワ請求書. */
  public static final String GL_CD_LINES_PER_PAGE_OKUWA_SEIKYUSHO = "02";

  /**
   * 自社請求書 制御部.
   */

  /** 得意先計・店舗計. */
  public static final String JISHA_SEIKYUSHO_CTRL_AREA_KEI = "1";

  /** 明細. */
  public static final String JISHA_SEIKYUSHO_CTRL_AREA_MEISAI = "2";

  /**
   * 入金確認フラグ.
   */

  /** 未入金. */
  public static final String NYUKIN_KAKUNIN_FLAG_MI_NYUKIN = "0";

  /** 入金済み. */
  public static final String NYUKIN_KAKUNIN_FLAG_NYUKIN_ZUMI = "1";

  /**
   * 請求締区分.
   */

  /** 通常. */
  public static final String SEIKYU_SHIME_KBN_TSUJO = "1";

  /** 臨時. */
  public static final String SEIKYU_SHIME_KBN_RINJI = "2";

  /** 取り纏め元. */
  public static final String SEIKYU_SHIME_KBN_TORIMATOME_MOTO = "3";

  /** 取り纏め先. */
  public static final String SEIKYU_SHIME_KBN_TORIMATOME_SAKI = "4";

  /** 都度請求. */
  public static final String SEIKYU_SHIME_KBN_TSUDO = "5";

  /**
   * 請求データ連携フラグ.
   */

  /** 未送信. */
  public static final String SEIKYU_DATA_RENKEI_MI_SOSHIN = "0";

  /** 送信済み. */
  public static final String SEIKYU_DATA_RENKEI_SOSHIN_ZUMI = "1";

  /** エラー. */
  public static final String SEIKYU_DATA_RENKEI_ERROR = "2";

  /** 送信中. */
  public static final String SEIKYU_DATA_RENKEI_SOSHIN_CHU = "3";

  /**
   * 請求最新フラグ.
   */

  /** 最新. */
  public static final String SEIKYU_SAISHIN_FLAG_SAISHIN = "1";

  /** 最新でない. */
  public static final String SEIKYU_SAISHIN_FLAG_NOT_SAISHIN = "0";

  /**
   * 入力形態.
   */

  /** 売上登録. */
  public static final String NYURYOKU_KEITAI_URIAGE = "1";

  /** 入金調整売上登録. */
  public static final String NYURYOKU_KEITAI_NYUKIN_CHOSEI_URIAGE = "2";

  /** 再請求売上登録. */
  public static final String NYURYOKU_KEITAI_SAI_SEIKYU_URIAGE = "3";

  /** その他. */
  public static final String NYURYOKU_KEITAI_SONOTA = "0";

  /**
   * 集約店舗区分.
   */

  /** 通常. */
  public static final String SHUYAKU_TEMPO_KBN_TSUJO = "1";

  /** 集約先. */
  public static final String SHUYAKU_TEMPO_KBN_SHUYAKU_SAKI = "2";

  /**
   * 店舗集約条件.
   */

  /** 店舗. */
  public static final String TEMPO_SHUYAKU_JOKEN_TEMPO = "1";

  /** 伝票No. */
  public static final String TEMPO_SHUYAKU_JOKEN_DEMPYO = "2";

  /**
   * 納品区分.
   */

  /** 店直. */
  public static final String NOHIN_KBN_MISE_CHOKU = "1";

  /** センター. */
  public static final String NOHIN_KBN_CENTER = "2";

  /**
   * 販売区分.
   */

  /** 定番. */
  public static final String HAMBAI_KBN_TEIBAN = "1";

  /** 特売. */
  public static final String HAMBAI_KBN_TOKUBAI = "2";

  /** チラシ. */
  public static final String HAMBAI_KBN_CHIRASHI = "3";

  /** サービス. */
  public static final String HAMBAI_KBN_SERVICE = "4";

  /**
   * バッチ 戻り値.
   */

  /** 終了コード 0：正常終了. */
  public static final String RETURN_CODE_NORMAL = "0";
  /** 終了コード 1：エラー終了. */
  public static final String RETURN_CODE_ERROR = "1";

  /**
   * プログラムID.
   */

  /** 請求チェックリスト印刷. */
  public static final String PG_ID_SEIKYU_CHECK_LIST = "SEI01-01D00";
  /** 請求締め処理. */
  public static final String PG_ID_SEIKYU_SHIME_SHORI = "SEI01-02D00";
  /** 臨時請求締め処理. */
  public static final String PG_ID_RINJI_SHIME_SHORI = "SEI01-03D00";
  /** 請求締め取消処理. */
  public static final String PG_ID_SEIKYU_SHIME_TORIKESHI = "SEI01-04D00";
  /** 取り纏め請求. */
  public static final String PG_ID_TORIMATOME_SEIKYU = "SEI01-05D00";
  /** 請求一覧. */
  public static final String PG_ID_SEIKYU_ICHIRAN = "SEI01-06D00";
  
  /** [画面]出力形式（請求先）*/
  public static final String OUTPUT_FORMAT_BILLCUSTFLAG = "0";
  /** [画面]出力形式（得意先/店舗毎）*/
  public static final String OUTPUT_FORMAT_CUSTSHOPFLAG = "1";
  /** [画面]出力形式（伝票毎）*/
  public static final String OUTPUT_FORMAT_DENPYOFLAG = "2";
}
