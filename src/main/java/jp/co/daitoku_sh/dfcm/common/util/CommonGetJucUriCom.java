/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:CommonGetJucUriCom.java
 *
 * <p>
 * 作成者:都築電気株式会社
 * 作成日:2015/10/21
 *
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/21 1.00 都築）関口 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohinExample;

/**
 * 受注・売上共通部品
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonGetJucUriCom {

  /** CommonDao */
  CommonDao commonDao;

  /**
   * コンストラクタ.
   *
   * @param init 共通DAO
   */
  public CommonGetJucUriCom(CommonDao init) {
    this.commonDao = init;
  }

  /**
   * 生産日取得
   *
   * @param chainCode チェーンコード
   * @param chainIdx チェーン枝番
   * @param deliCode 納品先コード
   * @param jigyoCode 事業所コード
   * @param businessDate 業務日付
   * @param deliDate 納品日
   * @param bin 便
   * @param deliKb 納品区分
   * @return productDate 生産日
   * @throws ParseException 変換エラー
   */
  public String getProductDate(Short chainCode, Short chainIdx,
      String deliCode, Short jigyoCode, Integer businessDate, String deliDate,
      String bin, String deliKb) throws ParseException {

    // 変数宣言
    Short entryDateKb; // エントリ日付区分
    String entryCloseDate; // エントリ締め日
    String productDate; // 生産日（戻り値）


    // 納品先マスタより、生産日算出に必要となるエントリ日付区分を取得する。
    MstSNohinExample mstNohinExample = new MstSNohinExample();

    // 検索条件セット
    mstNohinExample.createCriteria().andChncdEqualTo(chainCode)
        .andChnedaEqualTo(chainIdx).andNhscdEqualTo(deliCode).andJgycdEqualTo(
            jigyoCode).andDelflgEqualTo(Short.valueOf("0"))
        .andStrymdLessThanOrEqualTo(businessDate).andEndymdGreaterThanOrEqualTo(
            businessDate);

    // SQL実行
    List<MstSNohin> lstMstSNohin = commonDao.getMstSNohinMapper()
        .selectByExample(mstNohinExample);

    // 件数チェック
    if (lstMstSNohin.size() == 0) {
      // 取得に失敗した場合は、nullを返却
      return null;
    }

    // [入力]便に応じ、対応するエントリ日付区分を取得する。
    if (bin.equals("1")) {
      // [入力]便 ＝ '1' の場合 （1便）
      // [変数]エントリ日付区分 ＝ NHN.エントリ日付区分１
      entryDateKb = lstMstSNohin.get(0).getEntymdkbn1();

    } else if (bin.equals("2")) {
      // [入力]便 ＝ '2' の場合 （2便）
      // [変数]エントリ日付区分 ＝ NHN.エントリ日付区分２
      entryDateKb = lstMstSNohin.get(0).getEntymdkbn2();

    } else if (bin.equals("3")) {
      // [入力]便 ＝ '3' の場合 （3便）
      // [変数]エントリ日付区分 ＝ NHN.エントリ日付区分３
      entryDateKb = lstMstSNohin.get(0).getEntymdkbn3();

    } else {
      // [入力]便 ＝ 上記以外 の場合
      // Null を returnする。
      return null;

    }

    // 納品日、エントリ日付区分にて、エントリ締め日を算出する。
    DateFormat df = new SimpleDateFormat("yyyyMMdd"); // 日付フォーマットクラス
    Date datDeliDate = df.parse(deliDate); // [引数]納品日を日付に変換
    Calendar calDeli = Calendar.getInstance(); // カレンダークラス
    calDeli.setTime(datDeliDate); // 納品日を設定

    if (entryDateKb == 1) {
      // [変数]エントリ日付区分 ＝ '1' （当日）の場合
      // [変数]エントリ締め日　＝　納品日
      entryCloseDate = df.format(calDeli.getTime());

    } else if (entryDateKb == 2) {
      // [変数]エントリ日付区分 ＝ '2' （前日）の場合
      // [変数]エントリ締め日　＝　納品日　－ 1
      calDeli.add(Calendar.DATE, -1);
      entryCloseDate = df.format(calDeli.getTime());

    } else if (entryDateKb == 3) {
      // [変数]エントリ日付区分 ＝ '3' （前々日）の場合
      // [変数]エントリ締め日　＝　納品日　－ 2
      calDeli.add(Calendar.DATE, -2);
      entryCloseDate = df.format(calDeli.getTime());

    } else {
      // [変数]エントリ日付区分 ＝ 上記以外の場合
      // Null を returnする。
      return null;

    }

    // 納品区分、エントリ締め日にて、生産日を算出する。
    Date datEntryCloseDate = df.parse(entryCloseDate); // [変数]エントリ締め日を日付に変換
    Calendar calEntry = Calendar.getInstance(); // カレンダークラス
    calEntry.setTime(datEntryCloseDate); // エントリ締め日を設定

    if (deliKb.equals("2")) {
      // [入力]納品区分　=　'2'（センター）の場合

      // [変数]生産日WK　=　[変数]エントリ締め日
      productDate = entryCloseDate;

    } else {
      // [入力]納品区分　=　'1'（店直）の場合

      if (calDeli.after(calEntry)) {
        // [入力]納品日　>　[変数]エントリ締め日の場合
        // [変数]生産日WK　=　[入力]納品日
        productDate = df.format(calDeli.getTime());

      } else {
        // [入力]納品日　<=　[変数]エントリ締め日
        // [変数]生産日WK　=　[変数]エントリ締め日
        productDate = df.format(calEntry.getTime());

      }

    }

    return productDate;

  }

}
