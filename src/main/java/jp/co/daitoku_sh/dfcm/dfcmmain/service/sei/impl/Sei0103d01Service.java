/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0103d01Service.java
 * 
 * 作成者:都築電気
 * 作成日:2015/11/27
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/27 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0103d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.SeikyuCommonDao;

/**
 * 臨時請求締め処理用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Sei0103d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private Sei0103d01Dao sei0103d01Dao;

  @Autowired
  private SeikyuCommonDao seikyuCommonDao;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * DEFAULT MESSAGE の取得.
   * 
   * @return メッセージリスト
   */
  public List<DefaultMessages> getDefaultMess() {

    DefaultMessages defaultMsg = new DefaultMessages();

    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", null));

    List<DefaultMessages> defaultMsgList = new ArrayList<DefaultMessages>();
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM008-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM008-E", null));
    defaultMsgList.add(defaultMsg);

    return defaultMsgList;
  }

  /**
   * 入力データチェック（検索）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForSearch(Model model, FormSei0103d01 form) {

    String errorCode;
    String errorId = "";
    boolean isDateOk = true;
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    CommonGetData commonGetData = null;
    int shimebi1 = 0;
    int shimebi2 = 0;

    // ---------------------------------------------------------------------
    // 事業所コード（必須）
    // ---------------------------------------------------------------------

    String jigyoshoCd = form.isSysAdminFlag() ? form.getSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();

    if (form.isSysAdminFlag()) {

      // 単項目チェック
      errorCode = checkItem(jigyoshoCd, true, InputCheckCom.TYPE_NUM, 8);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("事業所コード:単項目チェックエラー");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("事業所");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "selectedJigyoshoCd" + MstConst.DELIMITER_ERROR;

      }

    }

    // ---------------------------------------------------------------------
    // 請求先コード（必須）
    // ---------------------------------------------------------------------

    String seikyusakiCd = form.getSeikyusakiCd();
    // 単項目チェック
    errorCode = checkItem(seikyusakiCd, true, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("請求先コード:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先コード");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "seikyusakiCd" + MstConst.DELIMITER_ERROR;

      // 名前のクリア
      form.setSeikyusakiName("");

    } else {
      // 請求先存在チェック
      if (seikyusakiCd != null && !seikyusakiCd.equals("")) {
        // 共通部品初期化
        if (commonGetData == null) {
          commonGetData = new CommonGetData(commonDao,
              readPropertiesFileService);
        }
        // 請求先情報の取得（type = 2）
        CustomerData custData = commonGetData.getCustomerData(seikyusakiCd,
            jigyoshoCd, 2);
        // エラーコードの取得
        errorCode = custData.getMsgCd();
        if (errorCode != null && !errorCode.equals("")) {
          logger.debug("請求先:存在チェックエラー（COM009-E）");
          ErrorMessages errorMsg = new ErrorMessages();
          ArrayList<String> params = new ArrayList<String>();

          params.add("得意先マスタ");
          params.add("請求先コード");
          errorMsg.setMessageContent(readPropertiesFileService.getMessage(
              errorCode, params));

          errorList.add(errorMsg);
          errorId += "seikyusakiCd" + MstConst.DELIMITER_ERROR;

          // 名前のクリア
          form.setSeikyusakiName("");

        } else {
          // 名前の取得
          String name = custData.getCst().getCustNmR();
          form.setSeikyusakiName(name);

          Short tmpShimebi1 = custData.getCst().getTotalDate1();
          Short tmpShimebi2 = custData.getCst().getTotalDate2();
          
          shimebi1 = tmpShimebi1 != null ? tmpShimebi1 : 0;
          shimebi2 = tmpShimebi2 != null ? tmpShimebi2 : 0;
          
        }
      } else {
        // 名前のクリア
        form.setSeikyusakiName("");
      }
    }

    // ---------------------------------------------------------------------
    // 請求締日（必須）
    // ---------------------------------------------------------------------

    String seikyuShimebi = form.getSeikyuShimebi();

    seikyuShimebi = (seikyuShimebi != null && seikyuShimebi.length() > 0)
        ? seikyuShimebi.replace("/", "")
        : seikyuShimebi;

    // 単項目チェック
    errorCode = checkItem(seikyuShimebi, true, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("臨時締日:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("臨時締日");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;
      isDateOk = false;

    } else {
      // 日付形式チェック
      errorCode = InputCheckCom.chkDate(seikyuShimebi,
          CommonConst.DATE_FORMAT_YMD);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("臨時締日:日付チェックエラー（COM001-E）");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("臨時締日");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;
        isDateOk = false;
      } else {

        String businessMonth = form.getBusinessDate().substring(0, 6);
        String shimebiMonth = seikyuShimebi.substring(0, 6);

        if (!businessMonth.equals(shimebiMonth)) {
          logger.debug("SEI009-E=臨時締日は当月の日付を指定してください");
          ErrorMessages errorMsg = new ErrorMessages();
          errorMsg.setMessageContent(readPropertiesFileService.getMessage(
              "SEI009-E",
              null));

          errorList.add(errorMsg);
          errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;

          isDateOk = false;
        } else {

          // 次回締め日チェック

          // 臨時締め日の日付
          Date shimeDate = DateUtil.toDate(seikyuShimebi,
              CommonConst.DATE_FORMAT_YMD);
          Date lastDate = DateUtil.getLastDayOfMonth(shimeDate);
          String strlastDate = DateUtil.setFormat(lastDate,
              CommonConst.DATE_FORMAT_YMD);
          int shimebiDd;
          if (seikyuShimebi.equals(strlastDate)) {
            shimebiDd = 31;
          } else {
            shimebiDd = Integer.valueOf(seikyuShimebi.substring(6));
          }

          // 業務日付の日付
          int businessDd = Integer.valueOf(form.getBusinessDate().substring(6));

          // 次回締め日
          int nextShimeDd = 99;
          if (shimebi1 != 0 && businessDd <= shimebi1) {
            nextShimeDd = shimebi1;
          } else if (shimebi2 != 0 && businessDd <= shimebi2) {
            nextShimeDd = shimebi2;
          }

          logger.debug("shimebiDd   = [" + shimebiDd + "]");
          logger.debug("businessDd  = [" + businessDd + "]");
          logger.debug("shimebi1    = [" + shimebi1 + "]");
          logger.debug("shimebi2    = [" + shimebi2 + "]");
          logger.debug("nextShimeDd = [" + nextShimeDd + "]");

          if (shimebiDd >= nextShimeDd) {
            logger.debug("SEI010-E=臨時締日は次回締日より前の日付を指定してください");
            ErrorMessages errorMsg = new ErrorMessages();
            errorMsg.setMessageContent(readPropertiesFileService.getMessage(
                "SEI010-E",
                null));

            errorList.add(errorMsg);
            errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;

            isDateOk = false;
          }
        }
      }
    }

    // ---------------------------------------------------------------------
    // 入金予定日（必須）
    // ---------------------------------------------------------------------

    String nyukinYoteibi = form.getNyukinYoteibi();

    nyukinYoteibi = (nyukinYoteibi != null && nyukinYoteibi.length() > 0)
        ? nyukinYoteibi.replace("/", "")
        : nyukinYoteibi;

    // 単項目チェック
    errorCode = checkItem(nyukinYoteibi, true, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("入金予定日:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("入金予定日");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "nyukinYoteibi" + MstConst.DELIMITER_ERROR;

      isDateOk = false;

    } else {
      // 日付形式チェック
      errorCode = InputCheckCom.chkDate(nyukinYoteibi,
          CommonConst.DATE_FORMAT_YMD);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("入金予定日:日付チェックエラー（COM001-E）");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("入金予定日");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "nyukinYoteibi" + MstConst.DELIMITER_ERROR;

        isDateOk = false;
      }
    }

    // 臨時締日 >= 入金予定日
    if (isDateOk) {
      Date dateShimebi = DateUtil.toDate(seikyuShimebi,
          CommonConst.DATE_FORMAT_YMD);
      Date dateYoteibi = DateUtil.toDate(nyukinYoteibi,
          CommonConst.DATE_FORMAT_YMD);

      int diff = dateShimebi.compareTo(dateYoteibi);
      if (diff >= 0) {
        logger.debug("SEI011-E=入金予定日は臨時締日より後の日付を指定してください。");
        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI011-E",
            null));

        errorList.add(errorMsg);
        errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;
        errorId += "nyukinYoteibi" + MstConst.DELIMITER_ERROR;
      }

    }

    // チェック結果判定
    if (errorId.equals("")) {
      return true;
    } else {
      model.addAttribute("errorMessages", errorList);
      model.addAttribute("lstErrorID", errorId);
      return false;
    }
  }

  /**
   * 入力データチェック（実行）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForExecute(Model model, FormSei0103d01 form) {

    boolean isShimeSumi = false;
    boolean isTaishogai = false;
    boolean isMiraiShimeSumi = false;

    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    for (SeikyusakiInfo info : list) {

      String seikyuId = info.getKonkaiSeikyuId();
      if (seikyuId != null && !seikyuId.equals("")) {
        // すでに締め処理済みあり
        isShimeSumi = true;
      } else if (info.getKonkaiUriageUrikake() == 0
          && info.getKonkaiUriageMishu() == 0
          && info.getKonkaiNyukinUrikake() == 0
          && info.getKonkaiNyukinMishu() == 0
          && info.getKonkaiSosaiUrikake() == 0
          && info.getKonkaiSosaiMishu() == 0
          && info.getKonkaiChoseiUrikake() == 0
          && info.getKonkaiChoseiMishu() == 0
          && info.getKurikoshiGakuUrikake() == 0
          && info.getKurikoshiGakuMishu() == 0) {
        // 売上・入金・繰越なしのものあり
        isTaishogai = true;
      } else {

        // 指定された日付よりも未来日付で締め処理済みかどうか

        // パラメータ設定
        Map<String, Object> params = new HashMap<String, Object>();
        // 請求先コード
        String seikyusakiCd = form.getCondSeikyusakiCd();
        params.put("SEIKYUSAKI_CD", seikyusakiCd);
        // 事業所コード
        String jigyoshoCd = form.isSysAdminFlag() ? form
            .getCondSelectedJigyoshoCd()
            : form.getLoginJigyoshoCd();
        params.put("JIGYOSHO_CD", jigyoshoCd);
        // 臨時締日
        String seikyuShimebi = form.getCondSeikyuShimebi();
        params.put("SEIKYU_SHIMEBI", seikyuShimebi);

        // 検索実行
        String tmpSeikyuId = seikyuCommonDao.getSeikyuCommonMapper()
            .checkSeikyuShimeSumi(params);

        if (tmpSeikyuId != null && !tmpSeikyuId.equals("")) {
          isMiraiShimeSumi = true;
        }

      }

    }

    if (isShimeSumi) {
      logger.debug("SEI012-E=請求締め処理済みのため締め処理できません");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI012-E", null));

      errorList.add(errorMsg);
    }
    if (isTaishogai) {
      logger.debug("SEI013-E=今回売上・今回入金・請求残高がないため締め処理できません");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI013-E", null));

      errorList.add(errorMsg);
    }
    if (isMiraiShimeSumi) {
      logger.debug("未来日に締めあり（SEI019-E）");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI019-E", null));

      errorList.add(errorMsg);
    }

    if (errorList.size() > 0) {
      model.addAttribute("errorMessages", errorList);
      return false;
    } else {
      return true;
    }
  }

  /**
   * 請求先情報リストの取得.
   * 
   * @param form フォーム
   * @return 請求先情報リスト
   */
  public List<SeikyusakiInfo> getSeikyusakiInfoList(FormSei0103d01 form) {

    // パラメータ取得
    String seikyuShimebi = form.getCondSeikyuShimebi();
    String jigyoshoCd = form.isSysAdminFlag() ? form.getCondSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();

    // パラメータ設定
    Map<String, Object> params = new HashMap<String, Object>();
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);
    // 臨時締日
    params.put("RINJI_SHIMEBI", seikyuShimebi);
    // 業務日付
    String businessDate = form.getBusinessDate();
    params.put("BIZ_DATE", businessDate);
    // 請求先コード
    String seikyusakiCd = form.getCondSeikyusakiCd();
    params.put("SEIKYUSAKI_CD", seikyusakiCd);

    // 検索実行
    List<SeikyusakiInfo> list = sei0103d01Dao.getSei0103d01Mapper()
        .getSeikyusakiInfoList(params);

    return list;
  }

  // *************************************************************************
  // **
  // ** PRIVATE METHOD
  // **
  // ************************************************************************/

  /**
   * 単項目チェック.
   * 
   * @param val チェック項目
   * @param required 必須チェックフラグ
   * @param type 型
   * @param len LENGTH
   * @return 必須エラー :COM006-E 型エラー :COM001-E LENGTHエラー :COM001-E
   */
  private String checkItem(String val, boolean required, int type, int len) {

    String errorCode = null;

    // 必須チェック
    if (required) {
      errorCode = InputCheckCom.chkEmpty(val);
      if (errorCode != null) {
        return errorCode;
      }
    }
    // 型チェック
    errorCode = InputCheckCom.chkType(val, type);
    if (errorCode != null) {
      return errorCode;
    }
    // LENGTH チェック
    errorCode = InputCheckCom.chkLength(val, len);
    if (errorCode != null) {
      return errorCode;
    }

    return errorCode;
  }
}
