/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0105d01Service.java
 * 
 * 作成者:都築電気
 * 作成日:2015/11/29
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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.TaxRateData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.TorimatomeSeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0105d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.SeikyuCommonDao;

/**
 * 取り纏め請求用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Sei0105d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private Sei0105d01Dao sei0105d01Dao;

  @Autowired
  private SeikyuCommonDao seikyuCommonDao;

  @Autowired
  private ApplicationContext appContext;

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
  public boolean checkInputParamsForSearch(Model model, FormSei0105d01 form) {

    String errorCode;
    String errorId = "";
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    CommonGetData commonGetData = null;

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
    // 請求締日（必須）
    // ---------------------------------------------------------------------

    String seikyuShimebi = form.getSeikyuShimebi();

    seikyuShimebi = (seikyuShimebi != null && seikyuShimebi.length() > 0)
        ? seikyuShimebi.replace("/", "")
        : seikyuShimebi;

    // 単項目チェック
    errorCode = checkItem(seikyuShimebi, true, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("請求締日:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求締日");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;
    } else {
      // 日付形式チェック
      errorCode = InputCheckCom.chkDate(seikyuShimebi,
          CommonConst.DATE_FORMAT_YMD);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("請求締日:日付チェックエラー（COM001-E）");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("請求締日");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "seikyuShimebi" + MstConst.DELIMITER_ERROR;
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
        // 請求先情報の取得（type = 3）
        CustomerData custData = commonGetData.getCustomerData(seikyusakiCd,
            jigyoshoCd, 3);
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
        }
      } else {
        // 名前のクリア
        form.setSeikyusakiName("");
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
  public boolean checkInputParamsForExecute(Model model, FormSei0105d01 form) {
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();

    if (torimatome.getSeikyuId() != null && !torimatome.getSeikyuId().equals(
        "")) {
      logger.debug("SEI012-E=請求締め処理済みのため締め処理できません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI012-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    if (!torimatome.isKonakiUriageFlag() && !torimatome.isKonkaiNyukinFlag()
        && !torimatome.isSeikyuZanFlag()) {
      logger.debug("SEI013-E=今回売上・今回入金・請求残高がないため締め処理できません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI013-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    if (!torimatome.isShimeSumiFlag()) {
      logger.debug("SEI014-E=締め処理が行われていない事業所があるため、取り纏め請求できません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI014-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    if (torimatome.isNyukinSumiFlag()) {
      logger.debug("SEI018-E=入金済みの請求があるため、取り纏め請求できません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI018-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    if (form.getHaitaCount() == 0) {
      logger.debug("SEI020-E=取り纏め対象の請求がありません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI020-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    return true;
  }

  /**
   * 入力データチェック（取消）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForTorikeshi(Model model,
      FormSei0105d01 form) {
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();

    if (torimatome.getSeikyuId() == null || torimatome.getSeikyuId().equals(
        "")) {
      logger.debug("SEI005-E=請求締め処理が行われていません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI005-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    if (torimatome.isNyukinSumiFlag()) {
      logger.debug("SEI006-E=入金済みのため、請求締め取消できません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI006-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    return true;
  }

  /**
   * 入力データチェック（再印刷）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForRePrint(Model model, FormSei0105d01 form) {
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();

    if (torimatome.getSeikyuId() == null || torimatome.getSeikyuId().equals(
        "")) {
      logger.debug("SEI005-E=請求締め処理が行われていません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI005-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      return false;
    }

    return true;
  }

  /**
   * 請求先情報リストの取得.
   * 
   * @param form フォーム
   * @return 請求先情報リスト
   */
  public List<SeikyusakiInfo> getSeikyusakiInfoList(FormSei0105d01 form) {

    // パラメータ取得
    String seikyuShimebi = form.getCondSeikyuShimebi();
    String jigyoshoCd = form.isSysAdminFlag() ? form.getCondSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();

    // 締日設定
    Date shimeDate = DateUtil.toDate(seikyuShimebi,
        CommonConst.DATE_FORMAT_YMD);
    Date lastDate = DateUtil.getLastDayOfMonth(shimeDate);
    String strlastDate = DateUtil.setFormat(lastDate,
        CommonConst.DATE_FORMAT_YMD);

    String shimebiWk;
    if (seikyuShimebi.equals(strlastDate)) {
      shimebiWk = "31";
    } else {
      shimebiWk = seikyuShimebi.substring(6);
    }

    // パラメータ設定
    Map<String, Object> params = new HashMap<String, Object>();
    // 請求締日
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);
    // 業務日付
    String businessDate = form.getBusinessDate();
    params.put("BIZ_DATE", businessDate);
    // 締日WK
    params.put("SHIMEBI_WK", shimebiWk);
    // 請求先コード
    String seikyusakiCd = form.getCondSeikyusakiCd();
    params.put("SEIKYUSAKI_CD", seikyusakiCd);
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);

    // 検索実行
    List<SeikyusakiInfo> list = sei0105d01Dao.getSei0105d01Mapper()
        .getSeikyusakiInfoList(params);

    return list;
  }

  /**
   * 取り纏め請求ヘッダの取得.
   * 
   * @param seikyusakiCd 請求先CD
   * @param jigyoshoCd 事業所CD
   * @param seikyuShimebi 請求締日
   * 
   * @return 取り纏め請求ヘッダ
   */
  public Map<String, Object> getTorimatomeSeikyuHeader(String seikyusakiCd,
      String jigyoshoCd, String seikyuShimebi) {

    Map<String, Object> params = new HashMap<String, Object>();

    // 請求先コード
    params.put("SEIKYUSAKI_CD", seikyusakiCd);
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);
    // 請求締め日
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);

    Map<String, Object> resultMap = sei0105d01Dao.getSei0105d01Mapper()
        .getTorimatomeSeikyuHeader(params);

    return resultMap;
  }

  /**
   * 取り纏め請求の排他件数取得.
   * 
   * @param seikyusakiCd 請求先CD
   * @param seikyuShimebi 請求締日
   * 
   * @return 件数
   */
  public int getHaitaRecordCount(String seikyusakiCd, String seikyuShimebi) {

    Map<String, Object> params = new HashMap<String, Object>();

    // 請求先コード
    params.put("SEIKYUSAKI_CD", seikyusakiCd);
    // 請求締め日
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);

    int result = sei0105d01Dao.getSei0105d01Mapper().getTorimatomeHaitaCount(
        params);

    return result;
  }

  /**
   * 取り纏め請求 実行.
   * 
   * @param form フォーム
   * @param errorList エラーリスト
   * @param userId ユーザーID
   * 
   * @return 処理結果
   */
  public boolean execute(FormSei0105d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

    boolean isOk = false;

    // システム共通部品
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        appContext,
        readPropertiesFileService);

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();

    // ---------------------------------------------------------------------
    // 請求IDの取得
    // ---------------------------------------------------------------------

    List<OwnSlipNumberingData> idList = null;
    try {

      idList = commonGetSysCom.ownSlipNumbering(
          SeiConst.OWN_SLIP_NO_DEMPYO_KBN_SEIKYU_ID, 1L);

    } catch (Exception ex) {

      logger.error("請求ID 採番エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

      // SEI016-E
      ArrayList<String> msgParams = new ArrayList<String>();
      msgParams.add("取り纏め請求");
      msgParams.add(torimatome.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", msgParams));
      errorList.add(errorMsg);

      return isOk;
    }

    if (idList == null || idList.size() == 0) {

      logger.error("請求ID 採番エラー idList == empty");
      logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

      // SEI016-E
      ArrayList<String> msgParams = new ArrayList<String>();
      msgParams.add("取り纏め請求");
      msgParams.add(torimatome.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", msgParams));
      errorList.add(errorMsg);

      return isOk;
    }

    Long seikyuId = idList.get(0).getOwnSlipNumber();

    logger.debug("請求ID=[" + seikyuId + "]");

    try {

      logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
      status = txManager.getTransaction(def);
      logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

      Map<String, Object> params = new HashMap<String, Object>();
      int updCount = 0;
      String seikyuShimebi = form.getCondSeikyuShimebi();

      // 共通項目
      String currentDate = DateUtil.getSysDate();
      String currentTime = DateUtil.getSysTime();
      String programId = SeiConst.PG_ID_TORIMATOME_SEIKYU;

      // -----------------------------------------------------------------------
      // 請求明細の登録
      // -----------------------------------------------------------------------

      params.clear();
      params.put("SEIKYU_ID", seikyuId);
      params.put("JIGYOSHO_CD", torimatome.getJigyoshoCd());
      params.put("SEIKYU_SHIME_NICHIJI", currentDate + currentTime);
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);
      params.put("SEIKYUSAKI_CD", torimatome.getSeikyusakiCd());
      params.put("SEIKYU_SHIMEBI", seikyuShimebi);

      logger.debug("▽▽▽ 請求明細 INSERT ▽▽▽");
      updCount = sei0105d01Dao.insertTorimatomeSeikyuMeisai(params);
      logger.debug("△△△ 請求明細 INSERT △△△ updCount=[" + updCount + "]");

      // -----------------------------------------------------------------------
      // 請求ヘッダの登録
      // -----------------------------------------------------------------------

      logger.debug("▽▽▽ 請求ヘッダ INSERT ▽▽▽");
      updCount = sei0105d01Dao.insertTorimatomeSeikyuHeader(params);
      logger.debug("△△△ 請求ヘッダ INSERT △△△ updCount=[" + updCount + "]");

      // -----------------------------------------------------------------------
      // 消費税の更新
      // -----------------------------------------------------------------------

      String chohyoCls = torimatome.getSeikyushoCls();
      String taxKeisanTani = torimatome.getTaxKeisanTani();

      logger.debug("chohyoCls = [" + chohyoCls + "] / taxKeisanTani = ["
          + taxKeisanTani + "] ( 02, 03, 04は消費税更新 )");

      if (chohyoCls.equals(SeiConst.CHOHYO_CLS_JISHA_SEIKYUSHO)
          && (taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_SEIKYUSAKI)
              || taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUISAKI)
              || taxKeisanTani.equals(
                  SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUI_TEMPO))) {

        // ---------------------------------------------------------------------
        // 消費税率の取得
        // ---------------------------------------------------------------------

        TaxRateData taxRateData = commonGetSysCom.getTaxRate(seikyuShimebi);

        if (!taxRateData.getProcResult()) {

          logger.error("消費税取得エラー");
          logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

          // SEI016-E
          ArrayList<String> msgParams = new ArrayList<String>();
          msgParams.add("取り纏め請求");
          msgParams.add(torimatome.getSeikyusakiCd());

          ErrorMessages errorMsg = new ErrorMessages();
          errorMsg.setMessageContent(readPropertiesFileService.getMessage(
              "SEI016-E", msgParams));
          errorList.add(errorMsg);
          return isOk;
        }

        double taxRate = taxRateData.getTaxRate();

        logger.debug("消費税率=[" + String.valueOf(taxRate) + "]");

        int taxSummary = 0;

        if (taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUISAKI)
            || taxKeisanTani.equals(
                SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUI_TEMPO)) {

          // ---------------------------------------------------------
          // 請求先・得意先 OR 請求先・得意先・店舗
          // ---------------------------------------------------------

          // 消費税の取得
          params.clear();
          params.put("TAX_RATE", taxRate);
          params.put("SEIKYU_ID", seikyuId);
          if (taxKeisanTani.equals(
              SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUISAKI)) {
            params.put("TEMPO_FLAG", false);
          } else {
            params.put("TEMPO_FLAG", true);
          }
          params.put("TAX_HASU_SHORI", torimatome.getTaxHasuShori());

          logger.debug("□ getTaxTokuisakiOrTempoSummary");
          logger.debug("消費税端数処理 = [" + torimatome.getTaxHasuShori() + "]");

          taxSummary = seikyuCommonDao.getSeikyuCommonMapper()
              .getTaxTokuisakiOrTempoSummary(params);

        } else {

          // -------------------------------------------------------------------
          // 請求先
          // -------------------------------------------------------------------

          // 消費税の取得
          params.clear();
          params.put("TAX_RATE", taxRate);
          params.put("SEIKYU_ID", seikyuId);
          params.put("TAX_HASU_SHORI", torimatome.getTaxHasuShori());

          logger.debug("□ getTaxTorimatomeHeader");
          logger.debug("消費税端数処理 = [" + torimatome.getTaxHasuShori() + "]");

          taxSummary = sei0105d01Dao.getSei0105d01Mapper()
              .getTaxTorimatomeHeader(params);

        }

        logger.debug("取得した消費税 =[" + taxSummary + "]");

        // 消費税の更新
        params.clear();
        params.put("TAX", taxSummary);
        params.put("SEIKYU_ID", seikyuId);
        params.put("USER_ID", userId);
        params.put("PROGRAM_ID", programId);
        params.put("CURRENT_DATE", currentDate);
        params.put("CURRENT_TIME", currentTime);

        logger.debug("▽▽▽ 請求ヘッダUPDATE（消費税更新） ▽▽▽");
        updCount = seikyuCommonDao.updateTaxOnSeikyuHeader(params);
        logger.debug("△△△ 請求ヘッダUPDATE（消費税更新） △△△ updCount=[" + updCount
            + "]");

      }

      // -----------------------------------------------------------------------
      // 関連テーブルの更新
      // -----------------------------------------------------------------------

      params.clear();
      params.put("SEIKYUSAKI_CD", torimatome.getSeikyusakiCd());
      params.put("SEIKYU_SHIMEBI", seikyuShimebi);
      params.put("HAITA_CHECK_NICHIJI", currentDate + currentTime);
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);

      logger.debug("▽▽▽ 売上明細 UPDATE ▽▽▽");
      updCount = sei0105d01Dao.updateUriageMeisaiToTorimatome(params);
      logger.debug("△△△ 売上明細 UPDATE △△△ updCount=[" + updCount + "]");

      logger.debug("▽▽▽ 売上ヘッダ UPDATE ▽▽▽");
      updCount = sei0105d01Dao.updateUriageHeaderToTorimatome(params);
      logger.debug("△△△ 売上ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      logger.debug("▽▽▽ 請求ヘッダ UPDATE ▽▽▽");
      updCount = sei0105d01Dao.updateKobetsuSeikyuHeader(params);
      logger.debug("△△△ 請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      if (updCount != form.getHaitaCount()) {

        // 排他エラー
        logger.error("請求ヘッダ 排他エラー");
        logger.error("更新対象件数=[" + form.getHaitaCount() + "] 更新結果件数=[" + updCount
            + "]");
        logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

        // SEI015-E
        ArrayList<String> errorParams = new ArrayList<String>();
        errorParams.add("請求データの更新");
        errorParams.add(torimatome.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI015-E", errorParams));
        errorList.add(errorMsg);

        logger.debug("▼▼▼ ロールバック ▼▼▼");
        txManager.rollback(status);
        logger.debug("▲▲▲ ロールバック ▲▲▲");

      } else {

        logger.debug("▼▼▼ コミット ▼▼▼");
        txManager.commit(status);
        logger.debug("▲▲▲ コミット ▲▲▲");

        torimatome.setSeikyuId(String.valueOf(seikyuId));
        isOk = true;
      }
    } catch (Exception ex) {

      logger.error("取り纏め請求エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");

      // SEI016-E
      ArrayList<String> msgParams = new ArrayList<String>();
      msgParams.add("取り纏め請求");
      msgParams.add(torimatome.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", msgParams));
      errorList.add(errorMsg);

    }

    return isOk;
  }

  /**
   * 取り纏め請求 取消.
   * 
   * @param form フォーム
   * @param errorList エラーリスト
   * @param userId ユーザーID
   * 
   * @return 処理結果
   */
  public boolean torikeshi(FormSei0105d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

    boolean isOk = false;

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();

    try {

      logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
      status = txManager.getTransaction(def);
      logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

      Map<String, Object> params = new HashMap<String, Object>();
      int updCount = 0;
      String seikyuId = torimatome.getSeikyuId();

      // 共通項目
      String currentDate = DateUtil.getSysDate();
      String currentTime = DateUtil.getSysTime();
      String programId = SeiConst.PG_ID_TORIMATOME_SEIKYU;

      // -----------------------------------------------------------------------
      // 取り纏め請求ヘッダの更新
      // -----------------------------------------------------------------------

      params.clear();
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);
      params.put("SEIKYU_ID", seikyuId);
      params.put("HAITA_CHECK_NICHIJI", currentDate + currentTime);

      logger.debug("▽▽▽ 取り纏め請求ヘッダ UPDATE ▽▽▽");
      updCount = sei0105d01Dao.updateTorimatomeSeikyuHeaderToTorikeshi(params);
      logger.debug("△△△ 取り纏め請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      if (updCount != 1) {

        // 排他エラー
        logger.error("請求ヘッダ 排他エラー");
        logger.error("更新対象件数=[1] 更新結果件数=[" + updCount
            + "]");
        logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

        // SEI015-E
        ArrayList<String> errorParams = new ArrayList<String>();
        errorParams.add("請求データの更新");
        errorParams.add(torimatome.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI015-E", errorParams));
        errorList.add(errorMsg);

        logger.debug("▼▼▼ ロールバック ▼▼▼");
        txManager.rollback(status);
        logger.debug("▲▲▲ ロールバック ▲▲▲");

        return isOk;
      }

      // -----------------------------------------------------------------------
      // 各事業所請求データの更新
      // -----------------------------------------------------------------------

      params.clear();
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);

      for (SeikyusakiInfo info : form.getSeikyusakiInfoList()) {

        logger.debug("事業所=[" + info.getJigyoshoCd() + "] 請求ID=[" + info
            .getKonkaiSeikyuId() + "] 締め区分=[" + info.getKonkaiSeikyuShimeKbn()
            + "] ( 03:取り纏め元 )");

        params.put("SEIKYU_ID", info.getKonkaiSeikyuId());

        logger.debug("▽▽▽ 売上明細 UPDATE ▽▽▽");
        updCount = sei0105d01Dao.updateUriageMeisaiToTorimatomeTorikeshi(
            params);
        logger.debug("△△△ 売上明細 UPDATE △△△ updCount=[" + updCount + "]");

        logger.debug("▽▽▽ 売上ヘッダ UPDATE ▽▽▽");
        updCount = sei0105d01Dao.updateUriageHeaderToTorimatomeTorikeshi(
            params);
        logger.debug("△△△ 売上ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

        logger.debug("▽▽▽ 個別請求ヘッダ UPDATE ▽▽▽");
        updCount = sei0105d01Dao.updateKobetsuSeikyuHeaderReturn(params);
        logger.debug("△△△ 個別請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      }

      logger.debug("▼▼▼ コミット ▼▼▼");
      txManager.commit(status);
      logger.debug("▲▲▲ コミット ▲▲▲");

      isOk = true;

    } catch (Exception ex) {

      logger.error("取り纏め請求 取消エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.error("請求先コード = [" + torimatome.getSeikyusakiCd() + "]");

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");

      // SEI016-E
      ArrayList<String> msgParams = new ArrayList<String>();
      msgParams.add("取り纏め請求の取消");
      msgParams.add(torimatome.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", msgParams));
      errorList.add(errorMsg);

    }

    return isOk;
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
