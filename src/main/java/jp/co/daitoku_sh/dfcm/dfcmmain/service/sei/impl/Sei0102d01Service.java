/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0102d01Service.java
 * 
 * 作成者:都築電気
 * 作成日:2015/10/28
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/28 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl;

import com.fujitsu.systemwalker.outputassist.connector.ConnectorException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.PrintUtil;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.NohinMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0102d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.SeikyuCommonDao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 請求締め処理用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Sei0102d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private Sei0102d01Dao sei0102d01Dao;

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
  public boolean checkInputParamsForSearch(Model model, FormSei0102d01 form) {

    String errorCode;
    String errorId = "";
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    CommonGetData commonGetData = null;

    // ---------------------------------------------------------------------
    // 事業所コード（必須）
    // ---------------------------------------------------------------------

    String jigyoshoCd = form.isSysAdminFlag() ? form.getSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();

    boolean isJigyoshoCdOk = true;

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

        isJigyoshoCdOk = false;
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
    // 事務担当者コード
    // ---------------------------------------------------------------------

    String jimuTantoshaCd = form.getJimuTantoshaCd();
    // 単項目チェック
    errorCode = checkItem(jimuTantoshaCd, false, InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("事務担当者コード:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("事務担当者");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "jimuTantoshaCd" + MstConst.DELIMITER_ERROR;

      // 名前のクリア
      form.setJimuTantoshaName("");

    } else {
      // 担当者存在チェック
      if (jimuTantoshaCd != null && !jimuTantoshaCd.equals("")
          && isJigyoshoCdOk) {

        // 共通部品初期化
        commonGetData = new CommonGetData(commonDao, readPropertiesFileService);
        // 担当者情報の取得
        UserData userData = commonGetData.getUserData(jimuTantoshaCd,
            jigyoshoCd);
        // エラーコードの取得
        errorCode = userData.getMsgCd();
        if (errorCode != null && !errorCode.equals("")) {
          logger.debug("担当者:存在チェックエラー（COM009-E）");
          ErrorMessages errorMsg = new ErrorMessages();
          ArrayList<String> params = new ArrayList<String>();

          params.add("担当者マスタ");
          params.add("事務担当者");
          errorMsg.setMessageContent(readPropertiesFileService.getMessage(
              errorCode, params));

          errorList.add(errorMsg);
          errorId += "jimuTantoshaCd" + MstConst.DELIMITER_ERROR;

          // 名前のクリア
          form.setJimuTantoshaName("");

        } else {
          // 名前の取得
          String name = userData.getUsr().getUserNm();
          form.setJimuTantoshaName(name);
        }
      } else {
        // 名前のクリア
        form.setJimuTantoshaName("");
      }
    }

    // ---------------------------------------------------------------------
    // 請求先コード
    // ---------------------------------------------------------------------

    String seikyusakiCd = form.getSeikyusakiCd();
    // 単項目チェック
    errorCode = checkItem(seikyusakiCd, false, InputCheckCom.TYPE_NUM, 8);
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
  public boolean checkInputParamsForExecute(Model model, FormSei0102d01 form) {

    boolean isChecked = false;
    boolean isShimeSumi = false;
    boolean isTaishogai = false;

    String seikyuId;

    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    for (SeikyusakiInfo info : list) {
      if (info.isCheckBoxFlag()) {
        // チェックあり
        isChecked = true;

        seikyuId = info.getKonkaiSeikyuId();
        if (seikyuId != null && !seikyuId.equals("")) {
          // すでに締め処理済みあり
          isShimeSumi = true;
        }
        if (info.getKonkaiUriageUrikake() == 0 && info
            .getKonkaiUriageMishu() == 0
            && info.getKonkaiNyukinUrikake() == 0 && info
                .getKonkaiNyukinMishu() == 0
            && info.getKonkaiSosaiUrikake() == 0 && info
                .getKonkaiSosaiMishu() == 0
            && info.getKonkaiChoseiUrikake() == 0 && info
                .getKonkaiChoseiMishu() == 0
            && info.getKurikoshiGakuUrikake() == 0 && info
                .getKurikoshiGakuMishu() == 0) {
          // 売上・入金・繰越なしのものあり
          isTaishogai = true;
        }
      }
    }

    if (!isChecked) {
      logger.debug("チェックなしエラー（COM008-E）");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM008-E", params));

      errorList.add(errorMsg);
    }
    if (isShimeSumi) {
      logger.debug("締め処理済みありエラー（SEI001-E）");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI001-E", null));

      errorList.add(errorMsg);
    }
    if (isTaishogai) {
      logger.debug("対象外ありエラー（SEI002-E）");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI002-E", null));

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
   * 入力チェック（納品明細書）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForPrintNohinMeisai(Model model,
      FormSei0102d01 form) {

    boolean isChecked = false;
    boolean isEmptySeikyuId = false;
    boolean isUriageZero = false;

    String seikyuId;

    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    for (SeikyusakiInfo info : list) {
      if (info.isCheckBoxFlag()) {
        // チェックあり
        isChecked = true;

        seikyuId = info.getKonkaiSeikyuId();
        if (seikyuId == null || seikyuId.equals("")) {
          // 締め処理未あり
          isEmptySeikyuId = true;
        }
        if (info.getKonkaiUriageUrikake() == 0 && info
            .getKonkaiUriageMishu() == 0) {
          // 売上なしの請求あり
          isUriageZero = true;
        }
      }
    }

    if (!isChecked) {
      logger.debug("チェックなしエラー（COM008-E）");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM008-E", params));

      errorList.add(errorMsg);
    }
    if (isEmptySeikyuId) {
      logger.debug("締め処理未ありエラー（SEI003-E）");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI003-E", null));

      errorList.add(errorMsg);
    }
    if (isUriageZero) {
      logger.debug("売上なしの請求ありエラー（SEI004-E）");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI004-E", null));

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
   * 入力チェック（再印刷）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForRePrintSeikyusho(Model model,
      FormSei0102d01 form) {

    boolean isChecked = false;
    boolean isEmptySeikyuId = false;

    String seikyuId;

    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    for (SeikyusakiInfo info : list) {
      if (info.isCheckBoxFlag()) {
        // チェックあり
        isChecked = true;

        seikyuId = info.getKonkaiSeikyuId();
        if (seikyuId == null || seikyuId.equals("")) {
          // 締め処理未あり
          isEmptySeikyuId = true;
        }
      }
    }

    if (!isChecked) {
      logger.debug("チェックなしエラー（COM008-E）");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM008-E", params));

      errorList.add(errorMsg);
    }
    if (isEmptySeikyuId) {
      logger.debug("締め処理未ありエラー（SEI003-E）");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI003-E", null));

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
  public List<SeikyusakiInfo> getSeikyusakiInfoList(FormSei0102d01 form) {

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
    // 締日WK
    params.put("SHIMEBI_WK", shimebiWk);
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);
    // 請求締日
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);
    // 業務日付
    String businessDate = form.getBusinessDate();
    params.put("BIZ_DATE", businessDate);
    // 事務担当者コード
    String jimuTantoshaCd = form.getCondJimuTantoshaCd();
    params.put("JIMU_TANTOSHA_CD", jimuTantoshaCd);
    // 請求先コード
    String seikyusakiCd = form.getCondSeikyusakiCd();
    params.put("SEIKYUSAKI_CD", seikyusakiCd);
    // 他社顧客フラグ
    boolean tashaKokyakuFlag = form.isCondTashaKokyakuFlag();
    params.put("TASHA_KOKYAKU_FLAG", tashaKokyakuFlag);
    // 請求データ未送信フラグ
    boolean seikyuMisoshinFlag = form.isCondSeikyuMisoshinFlag();
    params.put("SEIKYU_MISOSHIN_FLAG", seikyuMisoshinFlag);
    // 処理対象フラグ
    boolean shoriTaishoFlag = form.isCondShoriTaishoFlag();
    params.put("SHORI_TAISHO_FLAG", shoriTaishoFlag);
    // 検索上限値
    String topClause = "TOP " + String.valueOf(form.getSearchMax() + 1);
    params.put("TOP_CLAUSE", topClause);

    // 検索実行
    List<SeikyusakiInfo> list = sei0102d01Dao.getSei0102d01Mapper()
        .getSeikyusakiInfoList(params);

    return list;
  }

  /**
   * 納品明細書出力.
   * 
   * @param info
   *          請求先情報
   * @param seikyuShimebi
   *          請求締日
   * @param errorList
   *          画面表示用エラーリスト
   * @throws IOException IOエラー
   * @throws ConnectorException コネクターエラー
   */
  public boolean printNohinMeisai(SeikyusakiInfo info, String seikyuShimebi,
      List<ErrorMessages> errorList) {

    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;
    boolean isOk = false;

    // ---------------------------------------------------------------------
    // 帳票管理マスタ取得
    // ---------------------------------------------------------------------

    // 帳票名
    Map<String, Object> chohyoMap = seikyuCommonDao.getSeikyuCommonMapper()
        .getChohyoInfo(SeiConst.CHOHYO_ID_NOHIN_MEISAISHO);
    String chohyoName = String.valueOf(chohyoMap.get("LIST_NAME"));
    // 帳票格納Dir
    String chohyoDir = readPropertiesFileService.getSetting(
        "LIST_DAT_OUT_TEMP");

    // ファイルパス
    String tsvFilePath = chohyoDir + "\\" + SeiConst.CHOHYO_ID_NOHIN_MEISAISHO
        + "_"
        + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
        + ".tsv";
    logger.debug("tsvFilePath=[" + tsvFilePath + "]");

    // 納品明細書ハンドラ
    NohinMeisaishoPrintResultHandler handler = new NohinMeisaishoPrintResultHandler();

    try {

      // -----------------------------------------------------------------
      // ファイルオープン
      // -----------------------------------------------------------------

      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          tsvFilePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);

      // -----------------------------------------------------------------
      // 納品明細書出力
      // -----------------------------------------------------------------

      handler.tsvPrinter = tsvPrinter;
      handler.seikyuShimebi = seikyuShimebi;

      logger.debug(
          "■■■ 納品明細書 ----------------------------------------"
              + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      sei0102d01Dao.getSei0102d01Mapper().getNohinMeisaiInfoList(info
          .getKonkaiSeikyuId(), handler);
      logger.debug(
          "■■■ 納品明細書 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
              + "----------------------------------------");
      logger.debug("       handler.recordCount = [" + handler.recordCount
          + "]");

    } catch (IOException ex) {

      logger.error("納品明細書出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.debug("請求先コード = [" + info.getSeikyusakiCd() + "]");
      logger.debug("請求ID = [" + info.getKonkaiSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("納品明細書の出力");
      params.add(info.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

    } finally {

      // ----------------------------------------------------------------
      // ファイルクローズ
      // ----------------------------------------------------------------

      try {
        if (outputStreamWriter != null) {
          outputStreamWriter.close();
        }
        if (tsvPrinter != null) {
          tsvPrinter.close();
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    if (handler.recordCount > 0) {

      // ---------------------------------------------------------------------
      // LIST WORKS 連携
      // ---------------------------------------------------------------------

      PrintUtil printUtil = new PrintUtil(commonDao);

      File dataFile = new File(tsvFilePath);

      // 出力指示
      try {

        logger.debug("▼▼▼ LIST WORKS ▼▼▼");
        String rtnCode = printUtil.exePrint(SeiConst.CHOHYO_ID_NOHIN_MEISAISHO,
            SeiConst.CHOHYO_HAKKO_SOKUJI,
            info.getJigyoshoCd(), chohyoName, dataFile);
        logger.debug("▲▲▲ LIST WORKS ▲▲▲");

        if (rtnCode != null && !rtnCode.equals("")) {
          ErrorMessages errorMsg = new ErrorMessages();
          errorMsg.setMessageContent(readPropertiesFileService.getMessage(
              rtnCode, null));
          errorList.add(errorMsg);

        } else {
          isOk = true;
        }

      } catch (ConnectorException ex) {

        logger.error("納品明細書 LIST WORKS 連携エラー [" + ex.getMessage() + "]");
        ex.printStackTrace();

        logger.debug("請求先コード = [" + info.getSeikyusakiCd() + "]");
        logger.debug("請求ID = [" + info.getKonkaiSeikyuId() + "]");

        // SEI016-E
        ArrayList<String> params = new ArrayList<String>();
        params.add("納品明細書の出力");
        params.add(info.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI016-E", params));
        errorList.add(errorMsg);

      }
    } else {

      logger.error("納品明細書出力 0件");
      logger.debug("請求先コード = [" + info.getSeikyusakiCd() + "]");
      logger.debug("請求ID = [" + info.getKonkaiSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("納品明細書の出力");
      params.add(info.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
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
   * @param val
   *          チェック項目
   * @param required
   *          必須チェックフラグ
   * @param type
   *          型
   * @param len
   *          LENGTH
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

  // *************************************************************************
  // **
  // ** INNER CLASS
  // **
  // ************************************************************************/

  /**
   * 納品明細書出力用 ResultHandler Class.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   */
  private class NohinMeisaishoPrintResultHandler implements ResultHandler {

    private int recordCount;
    private CSVPrinter tsvPrinter;
    private String seikyuShimebi;

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      // 納品明細情報
      NohinMeisaiInfo meisaiInfo = (NohinMeisaiInfo) context.getResultObject();

      // レコード数カウントアップ
      recordCount++;

      try {

        // -------------------------------------------------------------
        // ヘッダ部
        // -------------------------------------------------------------

        // 請求月
        tsvPrinter.print(seikyuShimebi);
        // 請求先
        tsvPrinter.print(meisaiInfo.getSeikyusakiName());

        // -------------------------------------------------------------
        // 明細部
        // -------------------------------------------------------------

        // 品目コード
        tsvPrinter.print(meisaiInfo.getHimmokuCd());
        // 品目名
        tsvPrinter.print(meisaiInfo.getHimmokuName());
        // 納品日
        tsvPrinter.print(meisaiInfo.getNohinBi());
        // 数量
        tsvPrinter.print(meisaiInfo.getSuryo());
        // 単価
        tsvPrinter.print(meisaiInfo.getTanka());
        // 金額
        tsvPrinter.print(meisaiInfo.getKingaku());

        // -------------------------------------------------------------
        // 改行
        // -------------------------------------------------------------

        tsvPrinter.println();

      } catch (IOException ex) {
        ex.printStackTrace();
        logger.error("TSV項目出力エラー [" + ex.getMessage() + "]");
        throw new RuntimeException(ex);
      }
    }
  }
}
