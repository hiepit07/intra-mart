/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:InputCheckJucUri.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/17
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/17 1.00 TDK)安延 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirm;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

/**
 * 入力チェック（受注・売上共通）
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */

public class InputCheckJucUri {

  /** 共通DAO */
  private CommonDao commonDao;
  /** ロガー */
  private static Logger logger = Logger.getLogger(InputCheckJuc.class);
  /** メッセージ取得 */
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * コンストラクタ
   *
   * @param in CommonDao
   * @param msg ReadPropertiesFileService
   */
  public InputCheckJucUri(CommonDao in, ReadPropertiesFileService msg) {
    commonDao = in;
    readPropertiesFileService = msg;
  }

  /**
   * 納品日チェック
   *
   * @param deliDate 納品日
   * @param jigyoCode 事業所コード
   * @return チェック結果
   */
  public String chkDeliDate(String deliDate, String jigyoCode) {

    int postDateSu = 0; // 先日付範囲日数

    // 汎用マスタから先日付範囲日数を取得
    CommonGetSystemCom com = new CommonGetSystemCom(commonDao, null, null,
        null);
    List<MstGeneralData> haniList = com.getMstGeneralConf("Date_Forward", null);

    if (haniList == null || haniList.get(0).getTarget1() == null
        || haniList.get(0).getTarget1().equals("")) {

      ArrayList<String> paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("先日付範囲日数");
      // ログ出力
      logger.error(readPropertiesFileService.getMessage("COM009-E", paramMess));

      postDateSu = 0;
    } else {
      postDateSu = Integer.valueOf(haniList.get(0).getTarget1()).intValue();
    }

    // 納品日チェック実行
    String result = chkDeliDate(deliDate, jigyoCode, postDateSu);

    return result;
  }

  /**
   * 納品日チェック
   *
   * @param deliDate 納品日
   * @param jigyoCode 事業所コード
   * @param postDateSu 先日付範囲日数
   * @return チェック結果
   */
  public String chkDeliDate(String deliDate, String jigyoCode, int postDateSu) {

    String result = null;

    // 日付フォーマットチェック
    result = InputCheckCom.chkDate(deliDate, CommonConst.DATE_FORMAT_YMD);
    if (result != null) {
      return result;
    }

    // 事業所月次確定情報 取得
    TblGet01JigConfirm jigConf = commonDao.getGetJigMapper().selectByPrimaryKey(
        jigyoCode);
    // 会計年月
    String kaikeiMon = jigConf.getDetermMon();

    // 業務日付取得
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);
    String aplDate = sysCom.getAplDate();

    // 業務日付 + 先日付範囲日数
    java.util.Date postDate = DateUtil.getSpecDay(
        DateUtil.toDate(aplDate, CommonConst.DATE_FORMAT_YMD), postDateSu);

    // 納品日が業務日付 + 先日付範囲日数より大きい場合はエラー
    if (deliDate.compareTo(DateUtil.setFormat(postDate,
        CommonConst.DATE_FORMAT_YMD)) > 0) {
      result = "COM003-E";
    }

    // 納品日が会計月+01より小さい場合はエラー
    if (deliDate.compareTo(kaikeiMon + "01") < 0) {
      return "COM003-E";
    }

    return result;
  }

  /**
   * 便区分チェック
   *
   * @param val 便区分
   * @return チェック結果
   */
  public String chkBinKb(String val) {
    String result = null;

    // 共通部品（システム共通）便区分取得 実行
    CommonGetSystemCom com = new CommonGetSystemCom(commonDao, null, null,
        null);
    String binkb = com.getBinKb(val);

    if (binkb == null) {
      result = "COM009-E";
    }

    return result;
  }
}
