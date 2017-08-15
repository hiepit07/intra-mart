/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl
 * ファイル名:Nyu0102d02Service.java
 * 
 * 作成者:都築電気
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.NyuSeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl.Nyu0102d02Dao;

/**
 * 締め請求未回収設定用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Nyu0102d02Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private Nyu0102d02Dao nyu0102d02Dao;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * DEFAULT MESSAGE の取得.
   * 
   * @return メッセージリスト
   */
  public List<DefaultMessages> getDefaultMess() {

    // TODO: 要修正
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
  public boolean checkInputParamsForSearch(Model model, FormNyu0102d02 form) {

    String errorCode;
    String errorId = "";
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    boolean isDateOk = true;

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

        params.add("所属事業所");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "selectedJigyoshoCd" + MstConst.DELIMITER_ERROR;

      }

    }

    // ---------------------------------------------------------------------
    // 請求先コード From
    // ---------------------------------------------------------------------

    String seikyusakiCdFrom = form.getSeikyusakiCdFrom();

    // 単項目チェック
    errorCode = checkItem(seikyusakiCdFrom, false, InputCheckCom.TYPE_NUM, 7);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("請求先コードFrom:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先コードFrom");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "seikyusakiCdFrom" + MstConst.DELIMITER_ERROR;

    }

    // ---------------------------------------------------------------------
    // 請求先コード To
    // ---------------------------------------------------------------------

    String seikyusakiCdTo = form.getSeikyusakiCdTo();

    // 単項目チェック
    errorCode = checkItem(seikyusakiCdTo, false, InputCheckCom.TYPE_NUM, 7);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("請求先コードTo:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先コードTo");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "seikyusakiCdTo" + MstConst.DELIMITER_ERROR;

    }

    // ---------------------------------------------------------------------
    // 請求先コード From To
    // ---------------------------------------------------------------------

    if ((seikyusakiCdFrom == null || seikyusakiCdFrom.equals(""))
        && (seikyusakiCdTo == null || seikyusakiCdTo.equals(""))) {
      logger.debug("請求先コードFromTo:必須チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先コードのどちらか");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM006-E",
          params));

      errorList.add(errorMsg);
      errorId += "seikyusakiCdFrom" + MstConst.DELIMITER_ERROR;
      errorId += "seikyusakiCdTo" + MstConst.DELIMITER_ERROR;

    }

    // ---------------------------------------------------------------------
    // 入金予定日 From
    // ---------------------------------------------------------------------

    String nyukinYoteibiFrom = form.getNyukinYoteibiFrom();
    nyukinYoteibiFrom = (nyukinYoteibiFrom != null && nyukinYoteibiFrom
        .length() > 0)
            ? nyukinYoteibiFrom.replace("/", "")
            : nyukinYoteibiFrom;

    // 単項目チェック
    errorCode = checkItem(nyukinYoteibiFrom, false, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("入金予定日From:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("入金予定日From");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "nyukinYoteibiFrom" + MstConst.DELIMITER_ERROR;
      isDateOk = false;

    } else {
      // 日付形式チェック
      errorCode = InputCheckCom.chkDate(nyukinYoteibiFrom,
          CommonConst.DATE_FORMAT_YMD);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("入金予定日From:日付チェックエラー（COM001-E）");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("入金予定日From");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "nyukinYoteibiFrom" + MstConst.DELIMITER_ERROR;
        isDateOk = false;
      }
    }

    // ---------------------------------------------------------------------
    // 入金予定日 To
    // ---------------------------------------------------------------------

    String nyukinYoteibiTo = form.getNyukinYoteibiTo();
    nyukinYoteibiTo = (nyukinYoteibiTo != null && nyukinYoteibiTo.length() > 0)
        ? nyukinYoteibiTo.replace("/", "")
        : nyukinYoteibiTo;

    // 単項目チェック
    errorCode = checkItem(nyukinYoteibiTo, false, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("入金予定日From:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("入金予定日To");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "nyukinYoteibiTo" + MstConst.DELIMITER_ERROR;
      isDateOk = false;
    } else {
      // 日付形式チェック
      errorCode = InputCheckCom.chkDate(nyukinYoteibiTo,
          CommonConst.DATE_FORMAT_YMD);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("入金予定日To:日付チェックエラー（COM001-E）");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("入金予定日To");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "nyukinYoteibiTo" + MstConst.DELIMITER_ERROR;
        isDateOk = false;
      }
    }

    // ---------------------------------------------------------------------
    // 入金予定日 From To
    // ---------------------------------------------------------------------

    if ((nyukinYoteibiFrom == null || nyukinYoteibiFrom.equals(""))
        && (nyukinYoteibiTo == null || nyukinYoteibiTo.equals(""))) {
      logger.debug("入金予定日FromTo:必須チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("入金予定日のどちらか");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM006-E",
          params));

      errorList.add(errorMsg);
      errorId += "nyukinYoteibiFrom" + MstConst.DELIMITER_ERROR;
      errorId += "nyukinYoteibiTo" + MstConst.DELIMITER_ERROR;
      isDateOk = false;

    }

    if (isDateOk
        && nyukinYoteibiFrom != null && !nyukinYoteibiFrom.equals("")
        && nyukinYoteibiTo != null && !nyukinYoteibiTo.equals("")) {
      
      Date dateFrom = DateUtil.toDate(nyukinYoteibiFrom, CommonConst.DATE_FORMAT_YMD);
      Date dateTo = DateUtil.toDate(nyukinYoteibiTo, CommonConst.DATE_FORMAT_YMD);
      
      int diff = dateFrom.compareTo(dateTo);
      if (diff > 0) {
        logger.debug("入金予定日FromTo:COM016-E={1}より{2}の方が未来日になっています。");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("入金予定日To");
        params.add("入金予定日From");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "COM016-E",
            params));

        errorList.add(errorMsg);
        errorId += "nyukinYoteibiFrom" + MstConst.DELIMITER_ERROR;
        errorId += "nyukinYoteibiTo" + MstConst.DELIMITER_ERROR;
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
   * 請求先情報リストの取得.
   * 
   * @param form フォーム
   * @return 請求先情報リスト
   */
  public List<NyuSeikyusakiInfo> getShimeSeikyusakiInfoList(
      FormNyu0102d02 form) {

    // パラメータ取得
    String jigyoshoCd = form.isSysAdminFlag() ? form.getCondSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();
    String businessDate = form.getBusinessDate();
    String seikyusakiCdFrom = form.getCondSeikyusakiCdFrom();
    String seikyusakiCdTo = form.getCondSeikyusakiCdTo();
    String nyukinYoteibiFrom = form.getCondNyukinYoteibiFrom();
    String nyukinYoteibiTo = form.getCondNyukinYoteibiTo();

    // パラメータ設定
    Map<String, Object> params = new HashMap<String, Object>();
    // 業務日付
    params.put("BIZ_DATE", businessDate);
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);
    // 請求先コードFrom
    params.put("SEIKYUSAKI_CD_FROM", seikyusakiCdFrom);
    // 請求先コードTo
    params.put("SEIKYUSAKI_CD_TO", seikyusakiCdTo);
    // 入金予定日From
    params.put("NYUKIN_YOTEIBI_FROM", nyukinYoteibiFrom);
    // 入金予定日To
    params.put("NYUKIN_YOTEIBI_TO", nyukinYoteibiTo);

    // 検索実行
    List<NyuSeikyusakiInfo> list = nyu0102d02Dao.getNyu0102d02Mapper()
        .getShimeSeikyusakiList(params);

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
