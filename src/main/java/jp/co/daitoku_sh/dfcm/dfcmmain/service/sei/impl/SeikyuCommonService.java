/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:SeikyuCommonService.java
 * 
 * <p>作成者:都築電気
 * 作成日:2015/10/28
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/28 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl;

import com.fujitsu.systemwalker.outputassist.connector.ConnectorException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.TaxRateData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01Head;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01SeiBody;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.NumberUtil;
import jp.co.daitoku_sh.dfcm.common.util.PrintUtil;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.AeonMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.DaigakuSeikyoMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.OkuwaMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.OkuwaTempoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuHeaderInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuMeisaiMotoData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuTempoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuTokuisakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.SeikyuCommonDao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 請求共通 Service.
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class SeikyuCommonService extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private SeikyuCommonDao seikyuCommonDao;

  @Autowired
  private ApplicationContext appContext;

  private Dfcmlogger logger = new Dfcmlogger();

  // *************************************************************************
  // **
  // ** PUBLIC METHOD
  // **
  // ************************************************************************/

  /**
   * 事業所リストの取得.
   * 
   * @param businessDate 業務日付
   * @return 事業所リスト
   */
  public List<ObjCombobox> getJigyoshoList(String businessDate) {

    try {

      List<Map<String, Object>> list = seikyuCommonDao.getSeikyuCommonMapper()
          .getJigyoshoList(businessDate);

      if (list != null && list.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");

        List<ObjCombobox> rtnList = new ArrayList<ObjCombobox>();
        rtnList.add(firstObject);

        for (Map<String, Object> map : list) {
          String jigyoshoCd = String.valueOf(map.get("JIGYOSHO_CD"));
          String jigyoshoName = String.valueOf(map.get("JIGYOSHO_NAME"));

          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(jigyoshoCd);
          tempObj.setName(jigyoshoCd + " : " + jigyoshoName);
          rtnList.add(tempObj);
        }

        return rtnList;
      } else {
        return null;
      }

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 会計月の取得.
   * 
   * @param jigyoshoCd 事業所コード
   * @return 会計月
   */
  public String getAccountMonth(String jigyoshoCd) {

    try {
      String accountMonth = seikyuCommonDao.getSeikyuCommonMapper()
          .getTogetsuUrikakeGetsudo(jigyoshoCd);
      return accountMonth;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 請求データ作成.
   * 
   * @param seikyusakiInfo 請求先情報
   * @param seikyuShimebi 請求締日
   * @param seikyuShimeKbn 請求締区分
   * @param nyukinYoteibi 入金予定日
   * @param businessDate 業務日付
   * @param haitaDate 排他日付
   * @param haitaTime 排他時刻
   * @param errorList 画面表示用エラーリスト
   * @param userId ユーザーID
   * @param programId プログラムID
   * @return 請求ID
   */
  public Long createSeikyuData(SeikyusakiInfo seikyusakiInfo,
      String seikyuShimebi, String seikyuShimeKbn,
      String nyukinYoteibi, String businessDate, String haitaDate,
      String haitaTime,
      List<ErrorMessages> errorList, String userId, String programId) {

    // システム共通部品
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        appContext,
        readPropertiesFileService);
    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

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

      logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求締め処理");
      params.add(seikyusakiInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return null;
    }

    if (idList == null || idList.size() == 0) {

      logger.error("請求ID 採番エラー idList == empty");
      logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求締め処理");
      params.add(seikyusakiInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return null;
    }

    Long seikyuId = idList.get(0).getOwnSlipNumber();

    logger.debug("請求ID=[" + seikyuId + "]");

    // ---------------------------------------------------------------------
    // 消費税率の取得
    // ---------------------------------------------------------------------

    TaxRateData taxRateData = commonGetSysCom.getTaxRate(seikyuShimebi);

    if (!taxRateData.getProcResult()) {

      logger.error("消費税取得エラー");
      logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求締め処理");
      params.add(seikyusakiInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);
      return null;
    }

    double taxRate = taxRateData.getTaxRate();

    logger.debug("消費税=[" + String.valueOf(taxRate) + "]");

    try {

      logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
      status = txManager.getTransaction(def);
      logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

      // -----------------------------------------------------------------
      // 請求明細登録
      // -----------------------------------------------------------------

      logger.debug("■■■■■ 請求明細登録");

      // Result Handler
      SeikyuMeisaiInsertResultHandler handler = new SeikyuMeisaiInsertResultHandler();

      handler.seikyuId = seikyuId;
      handler.seikyuShimebi = seikyuShimebi;
      handler.taxRate = taxRate;
      handler.commonGetSysCom = commonGetSysCom;
      handler.seikyusakiInfo = seikyusakiInfo;

      handler.userId = userId;
      handler.programId = programId;

      Map<String, Object> params = new HashMap<String, Object>();

      params.put("SEIKYUSAKI_CD", seikyusakiInfo.getSeikyusakiCd());
      params.put("JIGYOSHO_CD", seikyusakiInfo.getJigyoshoCd());
      params.put("SEIKYU_SHIMEBI", seikyuShimebi);
      params.put("BIZ_DATE", businessDate);

      // 逐次処理呼び出し
      seikyuCommonDao.getSeikyuCommonMapper().getSeikyuMeisaiMotoDataList(
          params, handler);

      logger.debug("請求明細 登録件数=[" + handler.recordCount
          + "]");

      // 売上額サマリー
      int uriageGakuSummary = handler.uriageGakuSummary;
      // 消費税サマリー
      int taxSummary = handler.taxSummary;

      logger.debug("売上額サマリー =[" + uriageGakuSummary + "]");
      logger.debug("消費税サマリー =[" + taxSummary + "]");

      // -----------------------------------------------------------------
      // 請求ヘッダ登録
      // -----------------------------------------------------------------

      logger.debug("■■■■■ 請求ヘッダ登録");

      insertSeikyuHeader(seikyusakiInfo, seikyuId, seikyuShimebi,
          seikyuShimeKbn, nyukinYoteibi,
          uriageGakuSummary, taxSummary, taxRate, commonGetSysCom, userId,
          programId);

      // -----------------------------------------------------------------
      // 請求ヘッダ消費税更新
      // -----------------------------------------------------------------

      logger.debug("■■■■■ 請求ヘッダ消費税更新");

      if (seikyusakiInfo.getSeikyushoCls().equals(
          SeiConst.CHOHYO_CLS_JISHA_SEIKYUSHO)) {
        // 自社請求書
        if (seikyusakiInfo.getTaxKeisanTani().equals(
            SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUISAKI)
            || seikyusakiInfo.getTaxKeisanTani().equals(
                SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUI_TEMPO)) {

          // ---------------------------------------------------------
          // 請求先・得意先 OR 請求先・得意先・店舗
          // ---------------------------------------------------------

          // 消費税の取得
          params.clear();
          params.put("TAX_RATE", taxRate);
          params.put("SEIKYU_ID", seikyuId);
          if (seikyusakiInfo.getTaxKeisanTani().equals(
              SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUISAKI)) {
            params.put("TEMPO_FLAG", false);
          } else {
            params.put("TEMPO_FLAG", true);
          }
          params.put("TAX_HASU_SHORI", seikyusakiInfo.getTaxHasuShori());

          logger.debug("□ getTaxTokuisakiOrTempoSummary");
          logger.debug("消費税端数処理 = [" + seikyusakiInfo.getTaxHasuShori() + "]");

          taxSummary = seikyuCommonDao.getSeikyuCommonMapper()
              .getTaxTokuisakiOrTempoSummary(params);
          logger.debug("取得した消費税 =[" + taxSummary + "]");

          if (taxSummary > 0) {

            // 消費税の更新
            params.clear();
            params.put("TAX", taxSummary);
            params.put("SEIKYU_ID", seikyuId);
            // 共通項目
            String currentDate = DateUtil.getSysDate();
            String currentTime = DateUtil.getSysTime();
            params.put("USER_ID", userId);
            params.put("PROGRAM_ID", programId);
            params.put("CURRENT_DATE", currentDate);
            params.put("CURRENT_TIME", currentTime);

            logger.debug("▽▽▽ 請求ヘッダUPDATE（消費税更新） ▽▽▽");
            int updCount = seikyuCommonDao.updateTaxOnSeikyuHeader(params);
            logger.debug("△△△ 請求ヘッダUPDATE（消費税更新） △△△ updCount=[" + updCount
                + "]");
          }
        }
      }

      // -----------------------------------------------------------------
      // 関連テーブル更新
      // -----------------------------------------------------------------

      logger.debug("■■■■■ 関連テーブル更新");

      boolean isUpdOk = updateSeikyuKanrenTable(seikyusakiInfo, seikyuId,
          seikyuShimebi, businessDate, haitaDate,
          haitaTime, errorList, userId, programId);

      if (isUpdOk) {

        logger.debug("▼▼▼ コミット ▼▼▼");
        txManager.commit(status);
        logger.debug("▲▲▲ コミット ▲▲▲");

      } else {

        logger.debug("▼▼▼ ロールバック ▼▼▼");
        txManager.rollback(status);
        logger.debug("▲▲▲ ロールバック ▲▲▲");

        seikyuId = null;
      }

    } catch (Exception ex) {

      logger.error("請求データ作成エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");

      seikyuId = null;

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求締め処理");
      params.add(seikyusakiInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);
    }

    return seikyuId;

  }

  /**
   * 請求書出力.
   * 
   * @param seikyuId 請求ID
   * @param seikyusakiCd 請求先コード
   * @param errorList 画面表示用エラーリスト
   */
  public boolean printSeikyusho(Long seikyuId, String seikyusakiCd,
      String businessDate, List<ErrorMessages> errorList,
      String userId, String programId) {

    boolean isOk = false;

    // ---------------------------------------------------------------------
    // 請求ヘッダ情報の取得
    // ---------------------------------------------------------------------

    Map<String, Object> sqlParams = new HashMap<String, Object>();
    sqlParams.put("BIZ_DATE", businessDate);
    sqlParams.put("SEIKYU_ID", seikyuId);

    SeikyuHeaderInfo headInfo = seikyuCommonDao.getSeikyuCommonMapper()
        .getSeikyuHeaderInfo(sqlParams);

    if (headInfo == null) {
      logger.error("請求ヘッダ NOT FOUND");
      logger.error("請求先コード = [" + seikyusakiCd + "]");
      logger.error("請求ID = [" + seikyuId + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求書の出力");
      params.add(seikyusakiCd);

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return isOk;
    }

    // ---------------------------------------------------------------------
    // 請求書出力
    // ---------------------------------------------------------------------

    String chohyoDir = readPropertiesFileService.getSetting(
        "LIST_DAT_OUT_TEMP");
    String tsvFilePath = "";
    String dempyoCls = headInfo.getDempyoCls();

    boolean isPrintOk = false;

    if (dempyoCls.equals(SeiConst.CHOHYO_CLS_JISHA_SEIKYUSHO)) {

      logger.debug("▼▼▼▼▼ 自社請求書出力 ▼▼▼▼▼");

      tsvFilePath = chohyoDir + "\\" + SeiConst.CHOHYO_ID_JISHA_SEIKYUSHO + "_"
          + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
          + ".tsv";
      logger.debug("tsvFilePath=[" + tsvFilePath + "]");

      isPrintOk = printJishaSeikyushoMain(headInfo, tsvFilePath, errorList);

      logger.debug("▲▲▲▲▲ 自社請求書出力 ▲▲▲▲▲");
      logger.debug("isPrintOk=[" + isPrintOk + "]");

    } else if (dempyoCls.equals(SeiConst.CHOHYO_CLS_AEON_SEIKYUSHO)) {

      logger.debug("▼▼▼▼▼ イオン請求書出力 ▼▼▼▼▼");

      tsvFilePath = chohyoDir + "\\" + SeiConst.CHOHYO_ID_AEON_SEIKYUSHO + "_"
          + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
          + ".tsv";
      logger.debug("tsvFilePath=[" + tsvFilePath + "]");

      isPrintOk = printAeonSeikyusho(headInfo, tsvFilePath, errorList);

      logger.debug("▲▲▲▲▲ イオン請求書出力 ▲▲▲▲▲");
      logger.debug("isPrintOk=[" + isPrintOk + "]");

    } else if (dempyoCls.equals(SeiConst.CHOHYO_CLS_OKUWA_SEIKYUSHO)) {

      logger.debug("▼▼▼▼▼ オークワ請求書出力 ▼▼▼▼▼");

      tsvFilePath = chohyoDir + "\\" + SeiConst.CHOHYO_ID_OKUWA_SEIKYUSHO + "_"
          + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
          + ".tsv";
      logger.debug("tsvFilePath=[" + tsvFilePath + "]");

      isPrintOk = printOkuwaSeikyusho(headInfo, tsvFilePath, errorList);

      logger.debug("▲▲▲▲▲ オークワ請求書出力 ▲▲▲▲▲");
      logger.debug("isPrintOk=[" + isPrintOk + "]");

    } else if (dempyoCls.equals(SeiConst.CHOHYO_CLS_DAIGAKU_SEIKYO_SEIKYUSHO)) {

      logger.debug("▼▼▼▼▼ 大学生協請求書出力 ▼▼▼▼▼");

      tsvFilePath = chohyoDir + "\\"
          + SeiConst.CHOHYO_ID_DAIGAKU_SEIKYO_SEIKYUSHO + "_"
          + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
          + ".tsv";
      logger.debug("tsvFilePath=[" + tsvFilePath + "]");

      isPrintOk = printDaigakuSeikyoSeikyusho(headInfo, tsvFilePath, errorList);

      logger.debug("▲▲▲▲▲ 大学生協請求書出力 ▲▲▲▲▲");
      logger.debug("isPrintOk=[" + isPrintOk + "]");

    } else {

      logger.error("伝票種別が不正 dempyoCls=[" + dempyoCls + "]");
      logger.error("請求先コード = [" + seikyusakiCd + "]");
      logger.error("請求ID = [" + seikyuId + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求書の出力");
      params.add(seikyusakiCd);

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return isOk;
    }

    if (!isPrintOk) {
      return isOk;
    }

    // ---------------------------------------------------------------------
    // LIST WORKS 連携
    // ---------------------------------------------------------------------

    PrintUtil printUtil = new PrintUtil(commonDao);

    File dataFile = new File(tsvFilePath);
    // 帳票文字列 = [日付 + ' ' + 帳票略称 + ' ' 請求先略称]
    String chohyoMojiretsu = headInfo.getSeikyuShimebi().substring(6, 8) + " "
        + headInfo.getChohyoNameR() + " "
        + headInfo.getSeikyusakiNameR();

    // 出力指示
    try {
      logger.debug("▼▼▼ LIST WORKS ▼▼▼");
      String rtnCode = printUtil.exePrint(headInfo.getSeikyushoShuruiChohyoId(),
          SeiConst.CHOHYO_HAKKO_TAMEOKI,
          headInfo.getJigyoshoCd(), chohyoMojiretsu, dataFile);
      logger.debug("▲▲▲ LIST WORKS ▲▲▲");

      if (rtnCode != null && !rtnCode.equals("")) {
        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(rtnCode,
            null));
        errorList.add(errorMsg);

      } else {
        isOk = true;
      }

    } catch (ConnectorException ex) {

      logger.error("請求書出力 LIST WORKS 連携エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + seikyusakiCd + "]");
      logger.error("請求ID = [" + seikyuId + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求書の出力");
      params.add(seikyusakiCd);

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return isOk;
    }

    // ---------------------------------------------------------------------
    // 請求書印刷フラグ更新
    // ---------------------------------------------------------------------

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
    status = txManager.getTransaction(def);
    logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

    try {

      // 請求書印刷フラグ更新
      updateSeikyushoInsatsuFlag(headInfo.getSeikyuId(), userId, programId);

      logger.debug("▼▼▼ コミット ▼▼▼");
      txManager.commit(status);
      logger.debug("▲▲▲ コミット ▲▲▲");

    } catch (Exception ex) {

      logger.error("請求書印刷フラグ更新エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + seikyusakiCd + "]");
      logger.error("請求ID = [" + seikyuId + "]");

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");
    }
    return isOk;

  }

  /**
   * 請求データ送信.
   * 
   * @param seikyuId 請求ID
   * @param soshinId 請求送信ID
   * @param errorList 画面表示用エラーリスト
   */
  public boolean sendSeikyuData(String seikyuId, String soshinId,
      List<ErrorMessages> errorList, String userId,
      String programId) {

    boolean isOk = false;

    logger.debug("seikyuId = [" + seikyuId + "]");
    logger.debug("soshinId = [" + soshinId + "]");

    // バッチパス
    String batchPath = seikyuCommonDao.getSeikyuCommonMapper()
        .getSeikyuDataHaishinBatchPath(soshinId);
    if (batchPath == null || batchPath.equals("")) {
      logger.error("ジョブ管理マスタにデータなし");

      // COM023-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求データ");
      params.add("送信");

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM023-E", params));
      errorList.add(errorMsg);

    } else {

      // コマンド生成
      String cmd = "cmd.exe /c call " + batchPath + " " + seikyuId + " "
          + userId;
      logger.debug("cmd=[" + cmd + "]");

      try {

        // バッチ実行
        Process pro = Runtime.getRuntime().exec(cmd);
        // 結果取得
        String ftResult = String.valueOf(pro.waitFor());

        if (ftResult.equals(SeiConst.RETURN_CODE_NORMAL)) {
          // 正常終了
          isOk = true;
        } else {
          // エラー
          logger.error("請求データ送信エラー");
          logger.debug("請求ID = [" + seikyuId + "]");

          // COM023-E
          ArrayList<String> params = new ArrayList<String>();
          params.add("請求データ");
          params.add("送信");

          ErrorMessages errorMsg = new ErrorMessages();
          errorMsg.setMessageContent(readPropertiesFileService.getMessage(
              "COM023-E", params));
          errorList.add(errorMsg);
        }
      } catch (IOException ioex) {

        logger.error("請求データ送信エラー [" + ioex.getMessage() + "]");
        ioex.printStackTrace();

        logger.debug("請求ID = [" + seikyuId + "]");

        // COM023-E
        ArrayList<String> params = new ArrayList<String>();
        params.add("請求データ");
        params.add("送信");

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "COM023-E", params));
        errorList.add(errorMsg);

      } catch (InterruptedException intex) {

        logger.error("請求データ送信エラー [" + intex.getMessage() + "]");
        intex.printStackTrace();

        logger.debug("請求ID = [" + seikyuId + "]");

        // COM023-E
        ArrayList<String> params = new ArrayList<String>();
        params.add("請求データ");
        params.add("送信");

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "COM023-E", params));
        errorList.add(errorMsg);
      }
    }

    // ---------------------------------------------------------------------
    // 請求データ連携フラグ更新
    // ---------------------------------------------------------------------

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
    status = txManager.getTransaction(def);
    logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

    try {

      Map<String, Object> params = new HashMap<String, Object>();

      params.put("FLAG", isOk ? SeiConst.SEIKYU_DATA_RENKEI_SOSHIN_CHU
          : SeiConst.SEIKYU_DATA_RENKEI_ERROR);
      params.put("SEIKYU_ID", seikyuId);
      // 共通項目
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", DateUtil.getSysDate());
      params.put("CURRENT_TIME", DateUtil.getSysTime());

      logger.debug("▽▽▽ 請求ヘッダ UPDATE ▽▽▽");
      int updCount = seikyuCommonDao.updateSeikyuSendFlagOnSeikyuHeader(params);
      logger.debug("△△△ 請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      logger.debug("▼▼▼ コミット ▼▼▼");
      txManager.commit(status);
      logger.debug("▲▲▲ コミット ▲▲▲");

    } catch (Exception ex) {

      logger.error("請求データ連携フラグ更新エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.debug("請求ID = [" + seikyuId + "]");

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");
    }

    return isOk;
  }

  // *************************************************************************
  // **
  // ** PRIVATE METHOD
  // **
  // ************************************************************************/

  /**
   * 請求ヘッダの登録.
   * 
   * @param seikyusakiInfo 請求先情報
   * @param seikyuId 請求ID
   * @param seikyuShimebi 請求締日
   * @param seikyuShimeKbn 請求締区分
   * @param nyukinYoteibi 入金予定日
   * @param uriageGakuSummary 売上額サマリー
   * @param taxSummary 消費税サマリー
   * @param taxRate 消費税率
   * @param commonGetSysCom 共通部品
   * @param userId ユーザID
   * @param programId プログラムID
   */
  private void insertSeikyuHeader(SeikyusakiInfo seikyusakiInfo, long seikyuId,
      String seikyuShimebi,
      String seikyuShimeKbn, String nyukinYoteibi, int uriageGakuSummary,
      int taxSummary, double taxRate,
      CommonGetSystemCom commonGetSysCom, String userId, String programId) {

    // ---------------------------------------------------------------------
    // 消費税計算
    // ---------------------------------------------------------------------

    BigDecimal bdTax = null; // 消費税（BigDecimal）
    double tempTax = 0; // 消費税計算用WORK

    if (seikyusakiInfo.getSeikyushoCls().equals(
        SeiConst.CHOHYO_CLS_JISHA_SEIKYUSHO)) {
      // 自社請求書
      if (seikyusakiInfo.getTaxKeisanTani().equals(
          SeiConst.TAX_KEISAN_TANI_NASHI)) {
        // 消費税なし

        taxSummary = 0;

      } else if (seikyusakiInfo.getTaxKeisanTani().equals(
          SeiConst.TAX_KEISAN_TANI_SEIKYUSAKI)) {
        // 請求先

        // 消費税計算
        tempTax = NumberUtil.multiply(String.valueOf(uriageGakuSummary), String
            .valueOf(taxRate));
        tempTax = NumberUtil.divide(tempTax, 100);
        // 端数処理
        bdTax = commonGetSysCom.getNumberRound(tempTax, 1, seikyusakiInfo
            .getTaxHasuShori());
        taxSummary = bdTax.intValue();

      }
    }

    // 未収金消費税
    tempTax = NumberUtil.multiply(String.valueOf(seikyusakiInfo
        .getKonkaiUriageMishu()), String.valueOf(taxRate));
    tempTax = NumberUtil.divide(tempTax, 100);
    // 端数処理
    bdTax = commonGetSysCom.getNumberRound(tempTax, 1, seikyusakiInfo
        .getTaxHasuShori());
    int taxMishu = bdTax.intValue();

    // 売掛金消費税
    int taxUrikake = taxSummary - taxMishu;

    logger.debug("<INSERT> 消費税       = [" + taxSummary + "]");
    logger.debug("<INSERT> 売掛金消費税 = [" + taxUrikake + "]");
    logger.debug("<INSERT> 未収金消費税 = [" + taxMishu + "]");

    // ---------------------------------------------------------------------
    // 入金予定日
    // ---------------------------------------------------------------------

    String nyukinYoteibiWork = "";

    if (seikyuShimeKbn.equals(SeiConst.SEIKYU_SHIME_KBN_TSUJO)) {
      // 通常締め
      String kaishuTsukiKbn = seikyusakiInfo.getKaishuTsukiKbn();
      String kaishuBi = seikyusakiInfo.getKaishubi();
      Date dateSeikyuShimebi = DateUtil.toDate(seikyuShimebi,
          CommonConst.DATE_FORMAT_YMD);
      Date dateNyukinYoteibi = null;

      // 回収月設定
      if (kaishuTsukiKbn.equals(SeiConst.KAISHU_TSUKI_KBN_TOGETSU)) {
        // 当月
        dateNyukinYoteibi = dateSeikyuShimebi;
      } else if (kaishuTsukiKbn.equals(
          SeiConst.KAISHU_TSUKI_KBN_YOKU_1_GETSU)) {
        // 翌月
        dateNyukinYoteibi = DateUtil.getSpecMonth(dateSeikyuShimebi, 1);
      } else if (kaishuTsukiKbn.equals(
          SeiConst.KAISHU_TSUKI_KBN_YOKU_2_GETSU)) {
        // 翌々月
        dateNyukinYoteibi = DateUtil.getSpecMonth(dateSeikyuShimebi, 2);
      } else if (kaishuTsukiKbn.equals(
          SeiConst.KAISHU_TSUKI_KBN_YOKU_3_GETSU)) {
        // 翌々々月
        dateNyukinYoteibi = DateUtil.getSpecMonth(dateSeikyuShimebi, 3);
      } else {
        // 翌々々々月
        dateNyukinYoteibi = DateUtil.getSpecMonth(dateSeikyuShimebi, 4);
      }

      // 回収日設定
      if (kaishuBi.equals(SeiConst.KAISHUBI_GETSUMATSU)) {
        // 月末日付を入金予定日とする
        dateNyukinYoteibi = DateUtil.getLastDayOfMonth(dateNyukinYoteibi);
        nyukinYoteibiWork = DateUtil.setFormat(dateNyukinYoteibi,
            CommonConst.DATE_FORMAT_YMD);
      } else {
        // 回収日を入金予定日とする
        nyukinYoteibiWork = DateUtil.setFormat(dateNyukinYoteibi,
            CommonConst.DATE_FORMAT_YM) + kaishuBi;
      }
    } else {
      // 臨時締め
      nyukinYoteibiWork = nyukinYoteibi;
    }
    logger.debug("入金予定日=[" + nyukinYoteibiWork + "]");

    // ---------------------------------------------------------------------
    // 請求ヘッダ項目セット
    // ---------------------------------------------------------------------

    TblSei01Head seiHead = new TblSei01Head();

    // 請求ID
    seiHead.setBildId(seikyuId);
    // チェーンコード
    seiHead.setChainCode(seikyusakiInfo.getChainCode());
    // チェーン枝番
    seiHead.setChainIdx(seikyusakiInfo.getChainIdx());
    // 請求先コード
    seiHead.setCustCode(seikyusakiInfo.getSeikyusakiCd());
    // 事業所コード
    seiHead.setJigyoCode(seikyusakiInfo.getJigyoshoCd());
    // 前回請求締日
    seiHead.setPrevBildDate(seikyusakiInfo.getZenkaiSeikyuShimebi());
    // 請求締日
    seiHead.setBildDate(seikyuShimebi);
    // 請求締め区分
    seiHead.setBildSimeKb(seikyuShimeKbn);
    // 入金確認フラグ
    seiHead.setNyuFlag(SeiConst.NYUKIN_KAKUNIN_FLAG_MI_NYUKIN);
    // 入金予定日
    seiHead.setNyuDate(nyukinYoteibiWork);
    // 請求締め処理日時
    String seikyuShimeNichiji = DateUtil.getSysDate() + DateUtil.getSysTime();
    seiHead.setSimeDatetime(seikyuShimeNichiji);

    // 前回請求額（売掛）
    seiHead.setPrevUamt(seikyusakiInfo.getZenkaiSeikyuGakuUrikake());
    // 前回請求額（未収）
    seiHead.setPrevMamt(seikyusakiInfo.getZenkaiSeikyuGakuMishu());
    // 今回入金（売掛）
    seiHead.setCurUnyu(seikyusakiInfo.getKonkaiNyukinUrikake());
    // 今回入金（未収）
    seiHead.setCurMnyu(seikyusakiInfo.getKonkaiNyukinMishu());
    // 今回相殺（売掛）
    seiHead.setCurUsou(seikyusakiInfo.getKonkaiSosaiUrikake());
    // 今回相殺（未収）
    seiHead.setCurMsou(seikyusakiInfo.getKonkaiSosaiMishu());
    // 今回調整（売掛）
    seiHead.setCurUcho(seikyusakiInfo.getKonkaiChoseiUrikake());
    // 今回調整（未収）
    seiHead.setCurMcho(seikyusakiInfo.getKonkaiChoseiMishu());
    // 繰越額（売掛）
    seiHead.setCurUkur(seikyusakiInfo.getKurikoshiGakuUrikake());
    // 繰越額（未収）
    seiHead.setCurMkur(seikyusakiInfo.getKurikoshiGakuMishu());

    // 今回売上（売掛）
    seiHead.setCurUuri(seikyusakiInfo.getKonkaiUriageUrikake());
    // 今回売上（未収）
    seiHead.setCurMuri(seikyusakiInfo.getKonkaiUriageMishu());
    // 消費税
    seiHead.setCurTax(taxSummary);
    // 消費税（売掛）
    seiHead.setCurUtax(taxUrikake);
    // 消費税（未収）
    seiHead.setCurMtax(taxMishu);

    // 請求額（売掛） [ 今回売上（売掛） + 消費税（売掛） + 繰越額（売掛） ]
    seiHead.setCurUamt(
        seikyusakiInfo.getKonkaiUriageUrikake() + taxUrikake + seikyusakiInfo
            .getKurikoshiGakuUrikake());
    // 請求額（未収） [ 今回売上（未収） + 消費税（未収） + 繰越額（未収） ]
    seiHead.setCurMamt(seikyusakiInfo.getKonkaiUriageMishu() + taxMishu
        + seikyusakiInfo.getKurikoshiGakuMishu());
    // 売掛残高 [ 今回売上（売掛） + 消費税（売掛） ]
    seiHead.setUriZan(seikyusakiInfo.getKonkaiUriageUrikake() + taxUrikake);
    // 未収残高 [ 今回売上（未収） + 消費税（未収） ]
    seiHead.setMisZan(seikyusakiInfo.getKonkaiUriageMishu() + taxMishu);

    // 請求データ連携フラグ
    seiHead.setBildDatFlg(SeiConst.SEIKYU_DATA_RENKEI_MI_SOSHIN);
    // 前回請求ID
    String zenkaiSeikyuId = seikyusakiInfo.getZenkaiSeikyuId();
    if (zenkaiSeikyuId == null || zenkaiSeikyuId.equals("")) {
      seiHead.setPrevBildId(0L);
    } else {
      seiHead.setPrevBildId(Long.valueOf(seikyusakiInfo.getZenkaiSeikyuId()));
    }
    // 得意先種別
    seiHead.setCustCls(seikyusakiInfo.getTokuisakiCls());
    // 請求最新フラグ
    seiHead.setBildNewFlg(SeiConst.SEIKYU_SAISHIN_FLAG_SAISHIN);
    // 状況コード
    seiHead.setStsCode(CommonConst.STS_CD_ENTRY);

    // 共通項目
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    seiHead.setInsUserid(userId);
    seiHead.setInsPgid(programId);
    seiHead.setInsYmd(currentDate);
    seiHead.setInsTime(currentTime);
    seiHead.setUpdUserid(userId);
    seiHead.setUpdPgid(programId);
    seiHead.setUpdYmd(currentDate);
    seiHead.setUpdTime(currentTime);

    logger.debug("▽▽▽ 請求ヘッダ INSERT ▽▽▽");
    seikyuCommonDao.getTblSei01HeadMapper().insert(seiHead);
    logger.debug("△△△ 請求ヘッダ INSERT △△△");

  }

  /**
   * 請求関連テーブルの更新.
   * 
   * @param seikyusakiInfo 請求先情報
   * @param seikyuId 請求ID
   * @param seikyuShimebi 請求締日
   * @param businessDate 業務日付
   * @param haitaDate 排他日付
   * @param haitaTime 排他時刻
   * @param errorList 画面表示用エラーリスト
   * @param userId ユーザID
   * @param programId プログラムID
   * @return 更新結果
   */
  private boolean updateSeikyuKanrenTable(SeikyusakiInfo seikyusakiInfo,
      long seikyuId, String seikyuShimebi,
      String businessDate, String haitaDate, String haitaTime,
      List<ErrorMessages> errorList, String userId,
      String programId) {

    Map<String, Object> params = new HashMap<String, Object>();

    // 共通項目
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    // ---------------------------------------------------------------------
    // 売掛明細の更新
    // ---------------------------------------------------------------------

    params.clear();
    params.put("SEIKYU_ID", seikyuId);
    params.put("JIGYOSHO_CD", seikyusakiInfo.getJigyoshoCd());
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);
    params.put("BIZ_DATE", businessDate);
    params.put("SEIKYUSAKI_CD", seikyusakiInfo.getSeikyusakiCd());
    params.put("HAITA_CHECK_NICHIJI", haitaDate + haitaTime);
    // 共通項目
    params.put("USER_ID", userId);
    params.put("PROGRAM_ID", programId);
    params.put("CURRENT_DATE", currentDate);
    params.put("CURRENT_TIME", currentTime);

    logger.debug("▽▽▽ 売掛明細 UPDATE ▽▽▽");
    int updCount = seikyuCommonDao.updateUrikakeMeisai(params);
    logger.debug("△△△ 売掛明細 UPDATE △△△ updCount=[" + updCount + "]");

    if (updCount != seikyusakiInfo.getUrikakeMSu()) {
      // 排他エラー
      logger.error("売掛明細 排他エラー");
      logger.error("更新対象件数=[" + seikyusakiInfo.getUrikakeMSu() + "] 更新結果件数=["
          + updCount + "]");
      logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

      // SEI015-E
      ArrayList<String> errorParams = new ArrayList<String>();
      errorParams.add("請求データの更新");
      errorParams.add(seikyusakiInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI015-E", errorParams));
      errorList.add(errorMsg);

      return false;
    }

    // ---------------------------------------------------------------------
    // 未収明細の更新
    // ---------------------------------------------------------------------

    params.clear();
    params.put("SEIKYU_ID", seikyuId);
    params.put("JIGYOSHO_CD", seikyusakiInfo.getJigyoshoCd());
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);
    params.put("BIZ_DATE", businessDate);
    params.put("SEIKYUSAKI_CD", seikyusakiInfo.getSeikyusakiCd());
    params.put("HAITA_CHECK_NICHIJI", haitaDate + haitaTime);
    // 共通項目
    params.put("USER_ID", userId);
    params.put("PROGRAM_ID", programId);
    params.put("CURRENT_DATE", currentDate);
    params.put("CURRENT_TIME", currentTime);

    logger.debug("▽▽▽ 未収明細 UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateMishuMeisai(params);
    logger.debug("△△△ 未収明細 UPDATE △△△ updCount=[" + updCount + "]");

    if (updCount != seikyusakiInfo.getMishuMSu()) {
      // 排他エラー
      logger.error("未収明細 排他エラー");
      logger.error("更新対象件数=[" + seikyusakiInfo.getUrikakeMSu() + "] 更新結果件数=["
          + updCount + "]");
      logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

      // SEI015-E
      ArrayList<String> errorParams = new ArrayList<String>();
      errorParams.add("請求データの更新");
      errorParams.add(seikyusakiInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI015-E", errorParams));
      errorList.add(errorMsg);

      return false;
    }

    // ---------------------------------------------------------------------
    // 前回請求ヘッダの更新
    // ---------------------------------------------------------------------

    String zenkaiSeikyuId = seikyusakiInfo.getZenkaiSeikyuId();
    if (zenkaiSeikyuId == null || zenkaiSeikyuId.equals("")) {
      // 前回請求なし

      params.clear();
      params.put("SEIKYUSAKI_CD", seikyusakiInfo.getSeikyusakiCd());
      params.put("JIGYOSHO_CD", seikyusakiInfo.getJigyoshoCd());
      params.put("HAITA_CHECK_NICHIJI", haitaDate + haitaTime);

      logger.debug("▽▽▽ 前回請求ヘッダ SEARCH（0件=OK） ▽▽▽");
      int searchCount = seikyuCommonDao.getSeikyuCommonMapper()
          .getSeikyuHeaderCount(params);
      logger.debug("△△△ 前回請求ヘッダ SEARCH（0件=OK） △△△ searchCount=[" + searchCount
          + "]");

      if (searchCount != 0) {
        // 排他エラー
        logger.error("前回請求ヘッダ 排他エラー");
        logger.error("searchCount=[" + searchCount + "]");
        logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

        // SEI015-E
        ArrayList<String> errorParams = new ArrayList<String>();
        errorParams.add("請求データの更新");
        errorParams.add(seikyusakiInfo.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI015-E", errorParams));
        errorList.add(errorMsg);

        return false;
      }
    } else {
      // 前回請求あり

      params.clear();
      params.put("SEIKYU_ID", zenkaiSeikyuId);
      params.put("HAITA_CHECK_NICHIJI", haitaDate + haitaTime);
      // 共通項目
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);

      logger.debug("▽▽▽ 前回請求ヘッダ UPDATE ▽▽▽");
      updCount = seikyuCommonDao.updatePrevSeikyuHeader(params);
      logger.debug("△△△ 前回請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      if (updCount != 1) {
        // 排他エラー
        logger.error("前回請求ヘッダ 排他エラー");
        logger.error("更新対象件数=[1] 更新結果件数=[" + updCount + "]");
        logger.error("請求先コード = [" + seikyusakiInfo.getSeikyusakiCd() + "]");

        // SEI015-E
        ArrayList<String> errorParams = new ArrayList<String>();
        errorParams.add("請求データの更新");
        errorParams.add(seikyusakiInfo.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI015-E", errorParams));
        errorList.add(errorMsg);

        return false;
      }
    }

    // 以下更新
    params.clear();
    params.put("SEIKYU_ID", seikyuId);
    // 共通項目
    params.put("USER_ID", userId);
    params.put("PROGRAM_ID", programId);
    params.put("CURRENT_DATE", currentDate);
    params.put("CURRENT_TIME", currentTime);

    // ---------------------------------------------------------------------
    // 売上明細（売掛）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上明細（売掛） UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateUriageMeisaiUrikake(params);
    logger.debug("△△△ 売上明細（売掛） UPDATE △△△ updCount=[" + updCount + "]");

    // ---------------------------------------------------------------------
    // 売上明細（未収）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上明細（未収）UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateUriageMeisaiMishu(params);
    logger.debug("△△△ 売上明細（未収）UPDATE △△△ updCount=[" + updCount + "]");

    // ---------------------------------------------------------------------
    // 売上ヘッダ（売掛）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上ヘッダ（売掛） UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateUriageHeaderUrikake(params);
    logger.debug("△△△ 売上ヘッダ（売掛） UPDATE △△△ updCount=[" + updCount + "]");

    // ---------------------------------------------------------------------
    // 売上ヘッダ（未収）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上ヘッダ（未収） UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateUriageHeaderMishu(params);
    logger.debug("△△△ 売上ヘッダ（未収） UPDATE △△△ updCount=[" + updCount + "]");

    return true;

  }

  /**
   * 自社請求書出力メイン.
   * 
   * @param headerInfo 請求ヘッダ情報
   * @param filePath TSVファイルパス
   * @param errorList 画面表示用エラーリスト
   * @return 処理結果
   */
  private boolean printJishaSeikyushoMain(SeikyuHeaderInfo headerInfo,
      String filePath,
      List<ErrorMessages> errorList) {

    boolean isPrintOk = false;

    // システム共通部品
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        appContext,
        readPropertiesFileService);

    // ---------------------------------------------------------------------
    // 消費税率の取得
    // ---------------------------------------------------------------------

    TaxRateData taxRateData = commonGetSysCom.getTaxRate(headerInfo
        .getSeikyuShimebi());

    if (!taxRateData.getProcResult()) {

      logger.error("消費税取得エラー");
      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("自社請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return isPrintOk;
    }

    double taxRate = taxRateData.getTaxRate();

    logger.debug("消費税 = [" + taxRate + "]");

    // ---------------------------------------------------------------------
    // 改ページ行数の取得
    // ---------------------------------------------------------------------

    List<MstGeneralData> genList = commonGetSysCom.getMstGeneralConf(
        "Lines_Per_Page",
        SeiConst.GL_CD_LINES_PER_PAGE_JISHA_SEIKYUSHO);

    if (genList == null || genList.size() == 0) {

      logger.error("改ページ行数取得エラー");
      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("自社請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

      return isPrintOk;
    }

    String strKaiPageGyosu = genList.get(0).getTarget1();
    logger.debug("改ページ行数 strKaiPageGyosu=[" + strKaiPageGyosu + "]");
    int kaiPageGyosu = Integer.valueOf(strKaiPageGyosu);

    // ---------------------------------------------------------------------
    // 残印字出力判定
    // ---------------------------------------------------------------------

    // 請求書出力パターン
    String seikyushoPattern = headerInfo.getSeikyushoPattern();
    // 消費税計算単位
    String taxKeisanTani = headerInfo.getTaxKeisanTani();

    boolean isZanPrint = false;
    if (seikyushoPattern.equals(SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI)
        || seikyushoPattern.equals(SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TOKUISAKI)
        || seikyushoPattern.equals(SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TEMPO)
        || seikyushoPattern.equals(
            SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TOKUI_TEMPO)) {
      // 出力する
      isZanPrint = true;
    }

    // ---------------------------------------------------------------------
    // 明細消費税出力判定
    // ---------------------------------------------------------------------

    boolean isMeisaiTaxPrint = false;
    if (taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_DEN_SEIKYUSAKI)
        || taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUISAKI)
        || taxKeisanTani.equals(
            SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO)) {
      // 出力する
      isMeisaiTaxPrint = true;
    }

    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;
    
    try {

      // -----------------------------------------------------------------
      // ファイルオープン
      // -----------------------------------------------------------------

      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          filePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR).withQuote(null);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);

      // -----------------------------------------------------------------
      // 請求書パターン判定
      // -----------------------------------------------------------------

      if (seikyushoPattern.equals(SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI_NOT_ZAN_PRINT)) {

        logger.debug("▽▽▽▽▽ 請求書パターン = [ 請求先 ] ▽▽▽▽▽");
        printJishaSeikyushoSeikyusaki(tsvPrinter, headerInfo, kaiPageGyosu,
            taxRate, isZanPrint,
            isMeisaiTaxPrint);
        logger.debug("△△△△△ 請求書パターン = [ 請求先 ] △△△△△");

      } else if (seikyushoPattern.equals(
          SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TOKUISAKI)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TOKUISAKI_NOT_ZAN_PRINT)) {

        logger.debug("▽▽▽▽▽ 請求書パターン = [ 請求先・得意先 ] ▽▽▽▽▽");
        printJishaSeikyushoSeikyusakiTokuisaki(tsvPrinter, headerInfo,
            kaiPageGyosu, taxRate, isZanPrint,
            isMeisaiTaxPrint);
        logger.debug("△△△△△ 請求書パターン = [ 請求先・得意先 ] △△△△△");

      } else if (seikyushoPattern.equals(
          SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TEMPO)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TEMPO_NOT_ZAN_PRINT)) {

        logger.debug("▽▽▽▽▽ 請求書パターン = [ 請求先・店舗 ] ▽▽▽▽▽");
        printJishaSeikyushoSeikyusakiTempo(tsvPrinter, headerInfo, kaiPageGyosu,
            taxRate, isZanPrint,
            isMeisaiTaxPrint);
        logger.debug("△△△△△ 請求書パターン = [ 請求先・店舗 ] △△△△△");

      } else if (seikyushoPattern.equals(
          SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TOKUI_TEMPO)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TOKUI_TEMPO_NOT_ZAN_PRINT)) {

        logger.debug("▽▽▽▽▽ 請求書パターン = [ 請求先・得意先・店舗 ] ▽▽▽▽▽");
        printJishaSeikyushoSeikyusakiTokuisakiTempo(tsvPrinter, headerInfo,
            kaiPageGyosu, taxRate, isZanPrint,
            isMeisaiTaxPrint);
        logger.debug("△△△△△ 請求書パターン = [ 請求先・得意先・店舗 ] △△△△△");

      } else {

        logger.error("請求書パターンが不正 seikyushoPattern=[" + seikyushoPattern + "]");
        logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
        logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

        // SEI016-E
        ArrayList<String> params = new ArrayList<String>();
        params.add("自社請求書の出力");
        params.add(headerInfo.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI016-E", params));
        errorList.add(errorMsg);

        return isPrintOk;
      }

      isPrintOk = true;

    } catch (Exception ex) {

      logger.error("自社請求書出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("自社請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

    } finally {

      // -----------------------------------------------------------------
      // ファイルクローズ
      // -----------------------------------------------------------------

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
    return isPrintOk;
  }

  /**
   * 自社請求書（請求先）.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param kaiPageGyosu 改ページ行数
   * @param taxRate 消費税率
   * @param isZanPrint 残印字フラグ
   * @param isMeisaiTaxPrint 明細消費税表示フラグ
   */
  private void printJishaSeikyushoSeikyusaki(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo, int kaiPageGyosu,
      double taxRate, boolean isZanPrint, boolean isMeisaiTaxPrint) {

    Map<String, Object> params = new HashMap<String, Object>();

    // ---------------------------------------------------------------------
    // 請求明細取得 & 逐次処理（明細出力）
    // ---------------------------------------------------------------------

    // 明細ハンドラ
    SeikyuMeisaiPrintResultHandler handler = new SeikyuMeisaiPrintResultHandler();

    // HANDLER 設定
    handler.tsvPrinter = tsvPrinter; // ファイルハンドル
    handler.headerInfo = headerInfo; // 請求ヘッダ情報

    handler.outPageNo = 1; // 出力ページ番号

    if (isMeisaiTaxPrint) {
      handler.kaiPageGyosu = kaiPageGyosu; // 改ページ行数
    } else {
      handler.kaiPageGyosu = (kaiPageGyosu * 2); // 改ページ行数
    }

    handler.isZanPrint = isZanPrint; // 残印字フラグ
    handler.isMeisaiTaxPrint = isMeisaiTaxPrint; // 明細消費税出力フラグ

    // 請求先のあて先設定
    handler.zipCode = headerInfo.getSeikyusakiZipCode(); // 郵便番号
    handler.addr1 = headerInfo.getSeikyusakiAddr1(); // 住所1
    handler.addr2 = headerInfo.getSeikyusakiAddr2(); // 住所2
    handler.seikyusakiName = headerInfo.getSeikyusakiName(); // 請求先名
    handler.tempoName = ""; // 店舗名

    handler.uriageGaku = headerInfo.getKonkaiUriage(); // 売上
    handler.tax = headerInfo.getKonkaiTax(); // 消費税
    
    handler.tempoCodeNone = readPropertiesFileService.getSetting("TEN_CODE_NONE");

    // PARAMETER 設定
    if (isMeisaiTaxPrint) {
      params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
    } else {
      params.put("KAIPAGE_GYOSU", (kaiPageGyosu * 2)); // 改ページ行数
    }
    params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID
    params.put("TOKUISAKI_CD", null); // 得意先コード
    params.put("TEMPO_CD", null); // 店舗コード

    logger.debug("□ getSeikyuMeisaiInfoList");
    logger.debug("得意先コード = [" + null + "]");
    logger.debug("店舗コード = [" + null + "]");

    // CALL
    logger.debug("□ 自社請求書 明細 ---------->>>>>>>>>>");
    seikyuCommonDao.getSeikyuCommonMapper().getSeikyuMeisaiInfoList(params,
        handler);
    logger.debug("□ 自社請求書 明細 <<<<<<<<<<----------");

    logger.debug("recordCount = [" + handler.recordCount + "]");

    if (handler.recordCount == 0) {
      // 鑑のみ出力
      logger.debug("□ 自社請求書 鑑のみ ---------->>>>>>>>>>");
      outputJishaSeikyushoKagami(tsvPrinter, headerInfo, isZanPrint);
      logger.debug("□ 自社請求書 鑑のみ <<<<<<<<<<----------");
    }

    logger.debug("出力ページ数=[" + handler.outPageNo + "]");
  }

  /**
   * 自社請求書（請求先・得意先）.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param kaiPageGyosu 改ページ行数
   * @param taxRate 消費税率
   * @param isZanPrint 残印字フラグ
   * @param isMeisaiTaxPrint 明細消費税表示フラグ
   */
  private void printJishaSeikyushoSeikyusakiTokuisaki(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo,
      int kaiPageGyosu, double taxRate, boolean isZanPrint,
      boolean isMeisaiTaxPrint) {

    Map<String, Object> params = new HashMap<String, Object>();

    // ---------------------------------------------------------------------
    // 得意先取得
    // ---------------------------------------------------------------------

    // PARAMETER 設定
    params.put("TAX_KEISAN_TANI", headerInfo.getTaxKeisanTani()); // 消費税計算単位
    params.put("TAX_HASU_SHORI", headerInfo.getTaxHasuShori()); // 消費税端数処理
    params.put("TAX_RATE", taxRate); // 消費税率
    params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
    params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID

    logger.debug("□ getSeikyuTokuisakiInfoList");
    logger.debug("消費税端数処理 = [" + headerInfo.getTaxHasuShori() + "]");
    logger.debug("消費税計算単位 = [" + headerInfo.getTaxKeisanTani() + "]");

    List<SeikyuTokuisakiInfo> tokuisakiList = seikyuCommonDao
        .getSeikyuCommonMapper()
        .getSeikyuTokuisakiInfoList(params);

    if (tokuisakiList.size() == 0) {

      // 鑑のみ出力
      logger.debug("□ 自社請求書 鑑のみ ---------->>>>>>>>>>");
      outputJishaSeikyushoKagami(tsvPrinter, headerInfo, isZanPrint);
      logger.debug("□ 自社請求書 鑑のみ <<<<<<<<<<----------");

    } else {

      int outPageNo = 1;

      // -----------------------------------------------------------------
      // 得意先計出力
      // -----------------------------------------------------------------

      logger.debug("□ 自社請求書 得意先計 ---------->>>>>>>>>>");
      outPageNo = outputJishaSeikyushoTokuisakiKei(tsvPrinter, headerInfo,
          tokuisakiList, isZanPrint, outPageNo,
          kaiPageGyosu);
      logger.debug("□ 自社請求書 得意先計 <<<<<<<<<<----------");

      // 次ページへ
      outPageNo++;

      for (SeikyuTokuisakiInfo tokuisakiInfo : tokuisakiList) {

        // ---------------------------------------------------------------------
        // 請求明細取得 & 逐次処理（明細出力）
        // ---------------------------------------------------------------------

        // 明細ハンドラ
        SeikyuMeisaiPrintResultHandler handler = new SeikyuMeisaiPrintResultHandler();

        // HANDLER 設定
        handler.tsvPrinter = tsvPrinter; // ファイルハンドル
        handler.headerInfo = headerInfo; // 請求ヘッダ情報

        handler.outPageNo = outPageNo; // 出力ページ番号

        if (isMeisaiTaxPrint) {
          handler.kaiPageGyosu = kaiPageGyosu; // 改ページ行数
        } else {
          handler.kaiPageGyosu = (kaiPageGyosu * 2); // 改ページ行数
        }

        handler.isZanPrint = isZanPrint; // 残印字フラグ
        handler.isMeisaiTaxPrint = isMeisaiTaxPrint; // 明細消費税出力フラグ

        // 得意先のあて先設定
        handler.zipCode = tokuisakiInfo.getSeikyusakiZipCode(); // 郵便番号
        handler.addr1 = tokuisakiInfo.getSeikyusakiAddr1(); // 住所1
        handler.addr2 = tokuisakiInfo.getSeikyusakiAddr2(); // 住所2
        handler.seikyusakiName = tokuisakiInfo.getSeikyusakiName(); // 請求先名
        handler.tempoName = ""; // 店舗名

        handler.uriageGaku = tokuisakiInfo.getUriageGaku(); // 売上
        handler.tax = tokuisakiInfo.getTax(); // 消費税

        handler.tempoCodeNone = readPropertiesFileService.getSetting("TEN_CODE_NONE");
        
        // PARAMETER 設定
        params.clear();
        if (isMeisaiTaxPrint) {
          params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
        } else {
          params.put("KAIPAGE_GYOSU", (kaiPageGyosu * 2)); // 改ページ行数
        }
        params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID
        params.put("TOKUISAKI_CD", tokuisakiInfo.getTokuisakiCd()); // 得意先コード
        params.put("TEMPO_CD", null); // 店舗コード

        logger.debug("□ getSeikyuMeisaiInfoList");
        logger.debug("得意先コード = [" + tokuisakiInfo.getTokuisakiCd() + "]");
        logger.debug("店舗コード = [" + null + "]");

        // CALL
        logger.debug("□ 自社請求書 明細 ---------->>>>>>>>>>");
        seikyuCommonDao.getSeikyuCommonMapper().getSeikyuMeisaiInfoList(params,
            handler);
        logger.debug("□ 自社請求書 明細 <<<<<<<<<<----------");

        logger.debug("recordCount = [" + handler.recordCount + "]");

        outPageNo = handler.outPageNo;
        // 次ページへ
        outPageNo++;

      }
      logger.debug("出力ページ数=[" + outPageNo + "]");
    }
  }

  /**
   * 自社請求書（請求先・店舗）.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param kaiPageGyosu 改ページ行数
   * @param taxRate 消費税率
   * @param isZanPrint 残印字フラグ
   * @param isMeisaiTaxPrint 明細消費税表示フラグ
   */
  private void printJishaSeikyushoSeikyusakiTempo(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo,
      int kaiPageGyosu, double taxRate, boolean isZanPrint,
      boolean isMeisaiTaxPrint) {

    Map<String, Object> params = new HashMap<String, Object>();

    // ---------------------------------------------------------------------
    // 店舗取得
    // ---------------------------------------------------------------------

    // PARAMETER 設定
    params.put("TAX_KEISAN_TANI", headerInfo.getTaxKeisanTani()); // 消費税計算単位
    params.put("TAX_HASU_SHORI", headerInfo.getTaxHasuShori()); // 消費税端数処理
    params.put("TAX_RATE", taxRate); // 消費税率

    params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
    params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID
    params.put("TOKUISAKI_CD", headerInfo.getSeikyusakiCd()); // 得意先コード（=請求先コード）

    logger.debug("□ getSeikyuTempoInfoList");
    logger.debug("消費税端数処理 = [" + headerInfo.getTaxHasuShori() + "]");
    logger.debug("消費税計算単位 = [" + headerInfo.getTaxKeisanTani() + "]");

    List<SeikyuTempoInfo> tempoList = seikyuCommonDao.getSeikyuCommonMapper()
        .getSeikyuTempoInfoList(params);

    if (tempoList.size() == 0) {

      // 鑑のみ出力
      logger.debug("□ 自社請求書 鑑のみ ---------->>>>>>>>>>");
      outputJishaSeikyushoKagami(tsvPrinter, headerInfo, isZanPrint);
      logger.debug("□ 自社請求書 鑑のみ <<<<<<<<<<----------");

    } else {

      int outPageNo = 1;

      // -----------------------------------------------------------------
      // 店舗計出力
      // -----------------------------------------------------------------

      logger.debug("□ 自社請求書 店舗計 ---------->>>>>>>>>>");
      outPageNo = outputJishaSeikyushoTempoKei(tsvPrinter, headerInfo,
          tempoList, isZanPrint, outPageNo,
          kaiPageGyosu, headerInfo.getSeikyusakiZipCode(), headerInfo
              .getSeikyusakiAddr1(),
          headerInfo.getSeikyusakiAddr2(), headerInfo.getSeikyusakiName(),
          headerInfo.getKonkaiUriage(),
          headerInfo.getKonkaiTax());
      logger.debug("□ 自社請求書 店舗計 <<<<<<<<<<----------");

      // 次ページへ
      outPageNo++;

      for (SeikyuTempoInfo tempoInfo : tempoList) {

        // ---------------------------------------------------------------------
        // 請求明細取得 & 逐次処理（明細出力）
        // ---------------------------------------------------------------------

        // 明細ハンドラ
        SeikyuMeisaiPrintResultHandler handler = new SeikyuMeisaiPrintResultHandler();

        // HANDLER 設定
        handler.tsvPrinter = tsvPrinter; // ファイルハンドル
        handler.headerInfo = headerInfo; // 請求ヘッダ情報

        handler.outPageNo = outPageNo; // 出力ページ番号

        if (isMeisaiTaxPrint) {
          handler.kaiPageGyosu = kaiPageGyosu; // 改ページ行数
        } else {
          handler.kaiPageGyosu = (kaiPageGyosu * 2); // 改ページ行数
        }

        handler.isZanPrint = isZanPrint; // 残印字フラグ
        handler.isMeisaiTaxPrint = isMeisaiTaxPrint; // 明細消費税出力フラグ

        // 請求先のあて先設定
        handler.zipCode = headerInfo.getSeikyusakiZipCode(); // 郵便番号
        handler.addr1 = headerInfo.getSeikyusakiAddr1(); // 住所1
        handler.addr2 = headerInfo.getSeikyusakiAddr2(); // 住所2
        handler.seikyusakiName = headerInfo.getSeikyusakiName(); // 請求先名
        handler.tempoName = tempoInfo.getTempoName(); // 店舗名

        handler.uriageGaku = tempoInfo.getUriageGaku(); // 売上
        handler.tax = tempoInfo.getTax(); // 消費税
        
        handler.tempoCodeNone = readPropertiesFileService.getSetting("TEN_CODE_NONE");

        // PARAMETER 設定
        params.clear();
        if (isMeisaiTaxPrint) {
          params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
        } else {
          params.put("KAIPAGE_GYOSU", (kaiPageGyosu * 2)); // 改ページ行数
        }
        params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID
        params.put("TOKUISAKI_CD", null); // 得意先コード
        params.put("TEMPO_CD", tempoInfo.getTempoCd()); // 店舗コード

        logger.debug("□ getSeikyuMeisaiInfoList");
        logger.debug("得意先コード = [" + null + "]");
        logger.debug("店舗コード = [" + tempoInfo.getTempoCd() + "]");

        logger.debug("□ 自社請求書 明細 ---------->>>>>>>>>>");
        seikyuCommonDao.getSeikyuCommonMapper().getSeikyuMeisaiInfoList(params,
            handler);
        logger.debug("□ 自社請求書 明細 <<<<<<<<<<----------");

        logger.debug("       handler.recordCount = [" + handler.recordCount
            + "]");

        outPageNo = handler.outPageNo;
        // 次ページへ
        outPageNo++;
      }
      logger.debug("出力ページ数=[" + outPageNo + "]");
    }
  }

  /**
   * 自社請求書（請求先・得意先・店舗）.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param kaiPageGyosu 改ページ行数
   * @param taxRate 消費税率
   * @param isZanPrint 残印字フラグ
   * @param isMeisaiTaxPrint 明細消費税表示フラグ
   */
  private void printJishaSeikyushoSeikyusakiTokuisakiTempo(
      CSVPrinter tsvPrinter, SeikyuHeaderInfo headerInfo,
      int kaiPageGyosu, double taxRate, boolean isZanPrint,
      boolean isMeisaiTaxPrint) {

    Map<String, Object> params = new HashMap<String, Object>();

    // ---------------------------------------------------------------------
    // 得意先取得
    // ---------------------------------------------------------------------

    // PARAMETER 設定
    params.put("TAX_KEISAN_TANI", headerInfo.getTaxKeisanTani()); // 消費税計算単位
    params.put("TAX_HASU_SHORI", headerInfo.getTaxHasuShori()); // 消費税端数処理
    params.put("TAX_RATE", taxRate); // 消費税率

    params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
    params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID

    logger.debug("□ getSeikyuTokuisakiInfoList");
    logger.debug("消費税端数処理 = [" + headerInfo.getTaxHasuShori() + "]");
    logger.debug("消費税計算単位 = [" + headerInfo.getTaxKeisanTani() + "]");

    List<SeikyuTokuisakiInfo> tokuisakiList = seikyuCommonDao
        .getSeikyuCommonMapper()
        .getSeikyuTokuisakiInfoList(params);

    if (tokuisakiList.size() == 0) {

      // 鑑のみ出力
      logger.debug("□ 自社請求書 鑑のみ ---------->>>>>>>>>>");
      outputJishaSeikyushoKagami(tsvPrinter, headerInfo, isZanPrint);
      logger.debug("□ 自社請求書 鑑のみ <<<<<<<<<<----------");

    } else {

      int outPageNo = 1;

      // -----------------------------------------------------------------
      // 得意先計出力
      // -----------------------------------------------------------------
      logger.debug("□ 自社請求書 得意先計 ---------->>>>>>>>>>");
      outPageNo = outputJishaSeikyushoTokuisakiKei(tsvPrinter, headerInfo,
          tokuisakiList, isZanPrint, outPageNo,
          kaiPageGyosu);
      logger.debug("□ 自社請求書 得意先計 <<<<<<<<<<----------");

      // 次ページへ
      outPageNo++;

      for (SeikyuTokuisakiInfo tokuisakiInfo : tokuisakiList) {

        // ---------------------------------------------------------------------
        // 店舗取得
        // ---------------------------------------------------------------------

        // PARAMETER 設定
        params.clear();
        params.put("TAX_KEISAN_TANI", headerInfo.getTaxKeisanTani()); // 消費税計算単位
        params.put("TAX_HASU_SHORI", headerInfo.getTaxHasuShori()); // 消費税端数処理
        params.put("TAX_RATE", taxRate); // 消費税率

        params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
        params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID
        params.put("TOKUISAKI_CD", tokuisakiInfo.getTokuisakiCd()); // 得意先コード

        logger.debug("□ getSeikyuTempoInfoList");
        logger.debug("消費税端数処理 = [" + headerInfo.getTaxHasuShori() + "]");
        logger.debug("消費税計算単位 = [" + headerInfo.getTaxKeisanTani() + "]");

        List<SeikyuTempoInfo> tempoList = seikyuCommonDao
            .getSeikyuCommonMapper()
            .getSeikyuTempoInfoList(params);

        // -----------------------------------------------------------------
        // 店舗計出力
        // -----------------------------------------------------------------

        logger.debug("□ 自社請求書 店舗計 ---------->>>>>>>>>>");
        outPageNo = outputJishaSeikyushoTempoKei(tsvPrinter, headerInfo,
            tempoList, isZanPrint, outPageNo,
            kaiPageGyosu, tokuisakiInfo.getSeikyusakiZipCode(), tokuisakiInfo
                .getSeikyusakiAddr1(),
            tokuisakiInfo.getSeikyusakiAddr2(), tokuisakiInfo
                .getSeikyusakiName(),
            tokuisakiInfo.getUriageGaku(), tokuisakiInfo.getTax());
        logger.debug("□ 自社請求書 店舗計 <<<<<<<<<<----------");

        // 次ページへ
        outPageNo++;

        for (SeikyuTempoInfo tempoInfo : tempoList) {

          // ---------------------------------------------------------------------
          // 請求明細取得 & 逐次処理（明細出力）
          // ---------------------------------------------------------------------

          // 明細ハンドラ
          SeikyuMeisaiPrintResultHandler handler = new SeikyuMeisaiPrintResultHandler();

          // HANDLER 設定
          handler.tsvPrinter = tsvPrinter; // ファイルハンドル
          handler.headerInfo = headerInfo; // 請求ヘッダ情報

          handler.outPageNo = outPageNo; // 出力ページ番号

          if (isMeisaiTaxPrint) {
            handler.kaiPageGyosu = kaiPageGyosu; // 改ページ行数
          } else {
            handler.kaiPageGyosu = (kaiPageGyosu * 2); // 改ページ行数
          }

          handler.isZanPrint = isZanPrint; // 残印字フラグ
          handler.isMeisaiTaxPrint = isMeisaiTaxPrint; // 明細消費税出力フラグ

          // 得意先のあて先設定
          handler.zipCode = tokuisakiInfo.getSeikyusakiZipCode(); // 郵便番号
          handler.addr1 = tokuisakiInfo.getSeikyusakiAddr1(); // 住所1
          handler.addr2 = tokuisakiInfo.getSeikyusakiAddr2(); // 住所2
          handler.seikyusakiName = tokuisakiInfo.getSeikyusakiName(); // 請求先名
          handler.tempoName = tempoInfo.getTempoName(); // 店舗名

          handler.uriageGaku = tempoInfo.getUriageGaku(); // 売上
          handler.tax = tempoInfo.getTax(); // 消費税
          
          handler.tempoCodeNone = readPropertiesFileService.getSetting("TEN_CODE_NONE");

          // PARAMETER 設定
          params.clear();
          if (isMeisaiTaxPrint) {
            params.put("KAIPAGE_GYOSU", kaiPageGyosu); // 改ページ行数
          } else {
            params.put("KAIPAGE_GYOSU", (kaiPageGyosu * 2)); // 改ページ行数
          }
          params.put("SEIKYU_ID", headerInfo.getSeikyuId()); // 請求ID
          params.put("TOKUISAKI_CD", tokuisakiInfo.getTokuisakiCd()); // 得意先コード
          params.put("TEMPO_CD", tempoInfo.getTempoCd()); // 店舗コード

          logger.debug("□ getSeikyuMeisaiInfoList");
          logger.debug("得意先コード = [" + tokuisakiInfo.getTokuisakiCd() + "]");
          logger.debug("店舗コード = [" + tempoInfo.getTempoCd() + "]");

          logger.debug("□ 自社請求書 明細 ---------->>>>>>>>>>");
          seikyuCommonDao.getSeikyuCommonMapper().getSeikyuMeisaiInfoList(
              params, handler);
          logger.debug("□ 自社請求書 明細 <<<<<<<<<<----------");

          logger.debug("       handler.recordCount = [" + handler.recordCount
              + "]");

          outPageNo = handler.outPageNo;
          // 次ページへ
          outPageNo++;
        }
        logger.debug("出力ページ数=[" + outPageNo + "]");
      }
    }
  }

  /**
   * 自社請求書 得意先計出力.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param tokuisakiList 得意先情報リスト
   * @param isZanPrint 残印字フラグ
   * @param outPageNo 出力ページ番号
   * @param kaiPageGyosu 改ページ行数
   * @return 出力ページ番号
   */
  private int outputJishaSeikyushoTokuisakiKei(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo,
      List<SeikyuTokuisakiInfo> tokuisakiList, boolean isZanPrint,
      int outPageNo, int kaiPageGyosu) {

    // 消費税出力フラグ
    boolean isTaxPrint = false;

    String taxKeisanTani = headerInfo.getTaxKeisanTani();
    if (taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUISAKI)
        || taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUI_TEMPO)
        || taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUISAKI)
        || taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO)
        || taxKeisanTani.equals(
            SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUISAKI_NOT_PRINT)
        || taxKeisanTani.equals(
            SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO_NOT_PRINT)) {

      isTaxPrint = true;
    }

    try {

      int idx = 0;
      for (SeikyuTokuisakiInfo tokuisakiInfo : tokuisakiList) {

        // -------------------------------------------------------------
        // ページ判定
        // -------------------------------------------------------------

        if (idx != 0 && (idx % kaiPageGyosu == 0)) {
          logger.debug("改ページ");
          outPageNo++;
        }

        // -------------------------------------------------------------
        // 制御部
        // -------------------------------------------------------------

        // 帳票ID
        tsvPrinter.print(headerInfo.getSeikyushoShuruiChohyoId());
        // 明細区分
        tsvPrinter.print(SeiConst.JISHA_SEIKYUSHO_CTRL_AREA_KEI);

        // -------------------------------------------------------------
        // ヘッダー部
        // -------------------------------------------------------------

        // 請求書番号
        tsvPrinter.print(headerInfo.getSeikyuId() + "-" + outPageNo);
        // 郵便番号
        tsvPrinter.print(headerInfo.getSeikyusakiZipCode());
        // 住所
        tsvPrinter.print(headerInfo.getSeikyusakiAddr1() + headerInfo
            .getSeikyusakiAddr2());
        // 請求先名
        tsvPrinter.print(headerInfo.getSeikyusakiName());
        // 店舗名
        tsvPrinter.print("");
        // 請求先コード
        tsvPrinter.print(headerInfo.getSeikyusakiCd());
        // 締日（月）
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 締日（日）
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 入金種別
        tsvPrinter.print(headerInfo.getNyukinClsName());
        // 発行者
        tsvPrinter.print(headerInfo.getTantoshaName());

        if (outPageNo == 1) {
          // 鑑

          // 今回入金対象金額
          tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

          if (isZanPrint) {
            // 前回請求額
            tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
            // 入金額
            tsvPrinter.print(headerInfo.getKonkaiNyukin());
            // 調整額
            tsvPrinter.print(headerInfo.getKonkaiChosei());
            // 繰越額
            tsvPrinter.print(headerInfo.getKurikoshiGaku());
          } else {
            // 前回請求額
            tsvPrinter.print("");
            // 入金額
            tsvPrinter.print("");
            // 調整額
            tsvPrinter.print("");
            // 繰越額
            tsvPrinter.print("");
          }

          // 売上額
          tsvPrinter.print(headerInfo.getKonkaiUriage());
          // 消費税
          tsvPrinter.print(headerInfo.getKonkaiTax());
          // 請求額
          tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

        } else {
          // 鑑でない

          // 今回入金対象金額
          tsvPrinter.print("");
          // 前回請求額
          tsvPrinter.print("");
          // 入金額
          tsvPrinter.print("");
          // 調整額
          tsvPrinter.print("");
          // 繰越額
          tsvPrinter.print("");
          // 売上額
          tsvPrinter.print(headerInfo.getKonkaiUriage());
          // 消費税
          if (isTaxPrint) {
            tsvPrinter.print(headerInfo.getKonkaiTax());
          } else {
            tsvPrinter.print("");
          }
          // 請求額
          tsvPrinter.print("");
        }

        // -------------------------------------------------------------
        // ヘッダー部（タイトル）
        // -------------------------------------------------------------

        // タイトル1
        tsvPrinter.print("分類");
        // タイトル2
        tsvPrinter.print("取引ＣＤ");
        // タイトル3
        tsvPrinter.print("得意先コード");
        // タイトル4
        tsvPrinter.print("得意先名");
        // タイトル5
        tsvPrinter.print("");
        // タイトル6
        tsvPrinter.print("");
        // タイトル7
        tsvPrinter.print("売上金額");
        // タイトル8
        tsvPrinter.print("消費税額");

        // -------------------------------------------------------------
        // フッター部
        // -------------------------------------------------------------

        // ページ計
        tsvPrinter.print(tokuisakiInfo.getPageSum());
        // 合計
        if (tokuisakiInfo.getPageNo() == tokuisakiInfo.getMaxPageNo()) {
          // 最終ページ
          tsvPrinter.print(headerInfo.getKonkaiUriage());
        } else {
          tsvPrinter.print("");
        }
        // 消費税
        if (tokuisakiInfo.getPageNo() == tokuisakiInfo.getMaxPageNo()) {
          // 最終ページ
          if (isTaxPrint) {
            tsvPrinter.print(headerInfo.getKonkaiTax());
          } else {
            tsvPrinter.print("");
          }
        } else {
          tsvPrinter.print("");
        }

        // -------------------------------------------------------------
        // 明細部
        // -------------------------------------------------------------

        NumberFormat numFormat = NumberFormat.getNumberInstance();

        // 値1
        tsvPrinter.print("");
        // 値2
        tsvPrinter.print(tokuisakiInfo.getTorihikiCd());
        // 値3
        tsvPrinter.print(tokuisakiInfo.getTokuisakiCd());
        // 値4
        tsvPrinter.print(tokuisakiInfo.getTokuisakiName());
        // 値5
        tsvPrinter.print("");
        // 値6
        tsvPrinter.print("");
        // 値7：売上
        tsvPrinter.print(numFormat.format(tokuisakiInfo.getUriageGaku()));
        // 値8：消費税
        if (isTaxPrint) {
          tsvPrinter.print(numFormat.format(tokuisakiInfo.getTax()));
        } else {
          tsvPrinter.print("");
        }

        // -------------------------------------------------------------
        // 改行
        // -------------------------------------------------------------

        tsvPrinter.println();

        // 次へ
        idx++;

      }

      return outPageNo;

    } catch (IOException ex) {

      logger.error("自社請求書 得意先計 TSV項目出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

      throw new RuntimeException(ex);
    }
  }

  /**
   * 自社請求書 店舗計出力.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param tempoList 店舗情報リスト
   * @param isZanPrint 残印字フラグ
   * @param outPageNo 出力ページ番号
   * @param kaiPageGyosu 改ページ行数
   * @param zipCode 郵便番号
   * @param addr1 住所1
   * @param addr2 住所2
   * @param seikyusakiName 請求先名
   * @param uriageGaku 売上額
   * @param tax 消費税
   * @return 出力ページ番号
   */
  private int outputJishaSeikyushoTempoKei(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo,
      List<SeikyuTempoInfo> tempoList, boolean isZanPrint, int outPageNo,
      int kaiPageGyosu, String zipCode,
      String addr1, String addr2, String seikyusakiName, int uriageGaku,
      int tax) {

    // 消費税出力フラグ
    boolean isTaxPrint = false;

    String taxKeisanTani = headerInfo.getTaxKeisanTani();
    if (taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_SEIKYU_TOKUI_TEMPO)
        || taxKeisanTani.equals(SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO)
        || taxKeisanTani.equals(
            SeiConst.TAX_KEISAN_TANI_DEN_SEIKYU_TOKUI_TEMPO_NOT_PRINT)) {
      isTaxPrint = true;
    }

    try {

      int idx = 0;

      for (SeikyuTempoInfo tempoInfo : tempoList) {

        // -------------------------------------------------------------
        // ページ判定
        // -------------------------------------------------------------

        if (idx != 0 && (idx % kaiPageGyosu == 0)) {
          logger.debug("改ページ");
          outPageNo++;
        }

        // -------------------------------------------------------------
        // 制御部
        // -------------------------------------------------------------

        // 帳票ID
        tsvPrinter.print(headerInfo.getSeikyushoShuruiChohyoId());
        // 明細区分
        tsvPrinter.print(SeiConst.JISHA_SEIKYUSHO_CTRL_AREA_KEI);

        // -------------------------------------------------------------
        // ヘッダー部
        // -------------------------------------------------------------

        // 請求書番号
        tsvPrinter.print(headerInfo.getSeikyuId() + "-" + outPageNo);
        // 郵便番号
        tsvPrinter.print(zipCode);
        // 住所
        tsvPrinter.print(addr1 + addr2);
        // 請求先名
        tsvPrinter.print(seikyusakiName);
        // 店舗名
        tsvPrinter.print("");
        // 請求先コード
        tsvPrinter.print(headerInfo.getSeikyusakiCd());
        // 締日（月）
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 締日（日）
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 入金種別
        tsvPrinter.print(headerInfo.getNyukinClsName());
        // 発行者
        tsvPrinter.print(headerInfo.getTantoshaName());

        if (outPageNo == 1) {
          // 鑑

          // 今回入金対象金額
          tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

          if (isZanPrint) {
            // 前回請求額
            tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
            // 入金額
            tsvPrinter.print(headerInfo.getKonkaiNyukin());
            // 調整額
            tsvPrinter.print(headerInfo.getKonkaiChosei());
            // 繰越額
            tsvPrinter.print(headerInfo.getKurikoshiGaku());
          } else {
            // 前回請求額
            tsvPrinter.print("");
            // 入金額
            tsvPrinter.print("");
            // 調整額
            tsvPrinter.print("");
            // 繰越額
            tsvPrinter.print("");
          }

          // 売上額
          tsvPrinter.print(uriageGaku);
          // 消費税
          tsvPrinter.print(tax);
          // 請求額
          tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

        } else {
          // 鑑でない

          // 今回入金対象金額
          tsvPrinter.print("");
          // 前回請求額
          tsvPrinter.print("");
          // 入金額
          tsvPrinter.print("");
          // 調整額
          tsvPrinter.print("");
          // 繰越額
          tsvPrinter.print("");
          // 売上額
          tsvPrinter.print(uriageGaku);
          // 消費税
          if (isTaxPrint) {
            tsvPrinter.print(tax);
          } else {
            tsvPrinter.print("");
          }
          // 請求額
          tsvPrinter.print("");
        }

        // -------------------------------------------------------------
        // ヘッダー部（タイトル）
        // -------------------------------------------------------------

        // タイトル1
        tsvPrinter.print("");
        // タイトル2
        tsvPrinter.print("");
        // タイトル3
        tsvPrinter.print("店コード");
        // タイトル4
        tsvPrinter.print("店      名");
        // タイトル5
        tsvPrinter.print("");
        // タイトル6
        tsvPrinter.print("");
        // タイトル7
        tsvPrinter.print("売上金額");
        // タイトル8
        tsvPrinter.print("消費税額");

        // -------------------------------------------------------------
        // フッター部
        // -------------------------------------------------------------

        // ページ計
        tsvPrinter.print(tempoInfo.getPageSum());
        // 合計
        if (tempoInfo.getPageNo() == tempoInfo.getMaxPageNo()) {
          // 最終ページ
          tsvPrinter.print(uriageGaku);
        } else {
          tsvPrinter.print("");
        }
        // 消費税
        if (tempoInfo.getPageNo() == tempoInfo.getMaxPageNo()) {
          // 最終ページ
          if (isTaxPrint) {
            tsvPrinter.print(tax);
          } else {
            tsvPrinter.print("");
          }
        } else {
          tsvPrinter.print("");
        }

        // -------------------------------------------------------------
        // 明細部
        // -------------------------------------------------------------

        NumberFormat numFormat = NumberFormat.getNumberInstance();

        // 値1
        tsvPrinter.print("");
        // 値2
        tsvPrinter.print("");
        // 値3
        tsvPrinter.print(tempoInfo.getTempoCd());
        // 値4
        tsvPrinter.print(tempoInfo.getTempoName());
        // 値5
        tsvPrinter.print("");
        // 値6
        tsvPrinter.print("");
        // 値7：売上
        tsvPrinter.print(numFormat.format(tempoInfo.getUriageGaku()));
        // 値8：消費税
        if (isTaxPrint) {
          tsvPrinter.print(numFormat.format(tempoInfo.getTax()));
        } else {
          tsvPrinter.print("");
        }

        // -------------------------------------------------------------
        // 改行
        // -------------------------------------------------------------

        tsvPrinter.println();

        // 次へ
        idx++;

      }

      return outPageNo;

    } catch (IOException ex) {

      logger.error("自社請求書 店舗計 TSV項目出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

      throw new RuntimeException(ex);
    }
  }

  /**
   * 自社請求書 鑑出力.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param isZanPrint 残印字フラグ
   */
  private void outputJishaSeikyushoKagami(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo, boolean isZanPrint) {

    // 請求書パターン
    String seikyushoPattern = headerInfo.getSeikyushoPattern();

    try {

      // -------------------------------------------------------------
      // 制御部
      // -------------------------------------------------------------

      // 明細区分
      if (seikyushoPattern.equals(SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI_NOT_ZAN_PRINT)) {
        // 帳票ID
        tsvPrinter.print(headerInfo.getMultiChohyoId());
        // 明細
        tsvPrinter.print("2");
      } else {
        // 帳票ID
        tsvPrinter.print(headerInfo.getSeikyushoShuruiChohyoId());
        // 得意先計・店舗計
        tsvPrinter.print("1");
      }

      // -------------------------------------------------------------
      // ヘッダー部
      // -------------------------------------------------------------

      // 請求書番号
      tsvPrinter.print(headerInfo.getSeikyuId() + "-" + 1);
      // 郵便番号
      tsvPrinter.print(headerInfo.getSeikyusakiZipCode());
      // 住所
      tsvPrinter.print(headerInfo.getSeikyusakiAddr1() + headerInfo
          .getSeikyusakiAddr2());
      // 請求先名
      tsvPrinter.print(headerInfo.getSeikyusakiName());
      // 店舗名
      tsvPrinter.print("");
      // 請求先コード
      tsvPrinter.print(headerInfo.getSeikyusakiCd());
      // 締日（月）
      tsvPrinter.print(headerInfo.getSeikyuShimebi());
      // 締日（日）
      tsvPrinter.print(headerInfo.getSeikyuShimebi());
      // 入金種別
      tsvPrinter.print(headerInfo.getNyukinClsName());
      // 発行者
      tsvPrinter.print(headerInfo.getTantoshaName());
      // 今回入金対象金額
      tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

      if (isZanPrint) {
        // 前回請求額
        tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
        // 入金額
        tsvPrinter.print(headerInfo.getKonkaiNyukin());
        // 調整額
        tsvPrinter.print(headerInfo.getKonkaiChosei() + headerInfo
            .getKonkaiSosai());
        // 繰越額
        tsvPrinter.print(headerInfo.getKurikoshiGaku());
      } else {
        // 前回請求額
        tsvPrinter.print("");
        // 入金額
        tsvPrinter.print("");
        // 調整額
        tsvPrinter.print("");
        // 繰越額
        tsvPrinter.print("");
      }

      // 売上額
      tsvPrinter.print(headerInfo.getKonkaiUriage());
      // 消費税
      tsvPrinter.print(headerInfo.getKonkaiTax());
      // 請求額
      tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

      // -------------------------------------------------------------
      // ヘッダー部（タイトル）
      // -------------------------------------------------------------

      if (seikyushoPattern.equals(SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYUSAKI_NOT_ZAN_PRINT)) {
        // 明細

        // タイトル1
        tsvPrinter.print("日付");
        // タイトル2
        tsvPrinter.print("店コード");
        // タイトル3
        tsvPrinter.print("伝票No");
        // タイトル4
        tsvPrinter.print("金      額");
        // タイトル5
        tsvPrinter.print("日付");
        // タイトル6
        tsvPrinter.print("店コード");
        // タイトル7
        tsvPrinter.print("伝票No");
        // タイトル8
        tsvPrinter.print("金      額");

      } else if (seikyushoPattern.equals(
          SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TEMPO)
          || seikyushoPattern.equals(
              SeiConst.SEIKYUSHO_PATTERN_SEIKYU_TEMPO_NOT_ZAN_PRINT)) {
        // 店舗計

        // タイトル1
        tsvPrinter.print("");
        // タイトル2
        tsvPrinter.print("");
        // タイトル3
        tsvPrinter.print("店コード");
        // タイトル4
        tsvPrinter.print("店      名");
        // タイトル5
        tsvPrinter.print("");
        // タイトル6
        tsvPrinter.print("");
        // タイトル7
        tsvPrinter.print("売上金額");
        // タイトル8
        tsvPrinter.print("消費税額");

      } else {
        // 得意先計

        // タイトル1
        tsvPrinter.print("分類");
        // タイトル2
        tsvPrinter.print("取引ＣＤ");
        // タイトル3
        tsvPrinter.print("得意先コード");
        // タイトル4
        tsvPrinter.print("得意先名");
        // タイトル5
        tsvPrinter.print("");
        // タイトル6
        tsvPrinter.print("");
        // タイトル7
        tsvPrinter.print("売上金額");
        // タイトル8
        tsvPrinter.print("消費税額");
      }

      // -------------------------------------------------------------
      // フッター部
      // -------------------------------------------------------------

      // ページ計
      tsvPrinter.print("");
      // 合計
      tsvPrinter.print("");
      // 消費税
      tsvPrinter.print("");

      // -------------------------------------------------------------
      // 明細部
      // -------------------------------------------------------------

      // 値1
      tsvPrinter.print("");
      // 値2
      tsvPrinter.print("");
      // 値3
      tsvPrinter.print("");
      // 値4
      tsvPrinter.print("");
      // 値5
      tsvPrinter.print("");
      // 値6
      tsvPrinter.print("");
      // 値7
      tsvPrinter.print("");
      // 値8
      tsvPrinter.print("");

      // -------------------------------------------------------------
      // 改行
      // -------------------------------------------------------------

      tsvPrinter.println();

    } catch (IOException ex) {

      logger.error("自社請求書（鑑） TSV項目出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      throw new RuntimeException(ex);
    }
  }

  /**
   * イオン請求書出力.
   * 
   * @param headerInfo 請求ヘッダ情報
   * @param filePath TSVファイルパス
   * @param errorList 画面表示用エラーリスト
   * @return 出力結果
   */
  private boolean printAeonSeikyusho(SeikyuHeaderInfo headerInfo,
      String filePath, List<ErrorMessages> errorList) {

    boolean isPrintOk = false;

    // ---------------------------------------------------------------------
    // 当月納品・当月返品の取得
    // ---------------------------------------------------------------------

    String nohinKingaku = "";
    String hempinKingaku = "";

    Map<String, Object> map = seikyuCommonDao.getSeikyuCommonMapper()
        .getAeonTogetsuNohinHempinInfo(headerInfo.getSeikyuId());

    if (map == null || map.size() == 0) {
      logger.error("イオン請求書出力エラー [ 当月納品・当月返品取得件数 = 0 ]");
      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("イオン請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);
      return isPrintOk;
    }

    if (headerInfo.getSeikyushoTanka().equals(SeiConst.SEIKYUSHO_TANKA_NOHIN)) {
      // 納品単価

      nohinKingaku = String.valueOf(map.get("NOHIN_KINGAKU"));
      hempinKingaku = String.valueOf(map.get("HEMPIN_KINGAKU"));
    } else {
      // 先方仕切単価

      nohinKingaku = String.valueOf(map.get("NOHIN_SEMPO_SHIKIRI_KINGAKU"));
      hempinKingaku = String.valueOf(map.get("HEMPIN_SEMPO_SHIKIRI_KINGAKU"));
    }

    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;

    try {

      // -----------------------------------------------------------------
      // ファイルオープン
      // -----------------------------------------------------------------

      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          filePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR).withQuote(null);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);

      // -----------------------------------------------------------------
      // イオン明細出力
      // -----------------------------------------------------------------

      // イオン明細ハンドラ
      AeonSeikyushoPrintResultHandler handler = new AeonSeikyushoPrintResultHandler();

      handler.nohinKingaku = nohinKingaku;
      handler.hempinKingaku = hempinKingaku;

      handler.headerInfo = headerInfo;
      handler.tsvPrinter = tsvPrinter;

      logger.debug("□ イオン請求書 ---------->>>>>>>>>>");
      seikyuCommonDao.getSeikyuCommonMapper().getAeonMeisaiInfoList(headerInfo
          .getSeikyuId(), handler);
      logger.debug("□ イオン請求書 <<<<<<<<<<----------");
      logger.debug("       handler.recordCount = [" + handler.recordCount
          + "]");

      if (handler.recordCount == 0) {
        logger.error("イオン請求書出力エラー [ 出力件数 = 0 ]");
        logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
        logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

        // SEI016-E
        ArrayList<String> params = new ArrayList<String>();
        params.add("イオン請求書の出力");
        params.add(headerInfo.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI016-E", params));
        errorList.add(errorMsg);

      } else {
        isPrintOk = true;
      }
    } catch (Exception ex) {

      logger.error("イオン請求書出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("イオン請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

    } finally {

      // -----------------------------------------------------------------
      // ファイルクローズ
      // -----------------------------------------------------------------

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
    return isPrintOk;
  }

  /**
   * オークワ請求書出力.
   * 
   * @param headerInfo 請求ヘッダ情報
   * @param filePath TSVファイルパス
   * @param errorList 画面表示用エラーリスト
   * @return 出力結果
   */
  private boolean printOkuwaSeikyusho(SeikyuHeaderInfo headerInfo,
      String filePath, List<ErrorMessages> errorList) {

    boolean isPrintOk = false;

    // システム共通部品
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        appContext,
        readPropertiesFileService);

    // ---------------------------------------------------------------------
    // 改ページ行数の取得
    // ---------------------------------------------------------------------

    List<MstGeneralData> genList = commonGetSysCom.getMstGeneralConf(
        "Lines_Per_Page",
        SeiConst.GL_CD_LINES_PER_PAGE_OKUWA_SEIKYUSHO);

    if (genList == null || genList.size() == 0) {
      logger.error("改ページ行数取得エラー");
      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("オークワ請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);
      return isPrintOk;
    }

    String strKaiPageGyosu = genList.get(0).getTarget1();
    logger.debug("改ページ行数 strKaiPageGyosu=[" + strKaiPageGyosu + "]");
    int kaiPageGyosu = Integer.valueOf(strKaiPageGyosu);

    // ---------------------------------------------------------------------
    // 店舗一覧の取得
    // ---------------------------------------------------------------------

    List<OkuwaTempoInfo> tempoList = seikyuCommonDao.getSeikyuCommonMapper()
        .getOkuwaTempoInfoList(headerInfo.getSeikyuId());

    if (tempoList == null || tempoList.size() == 0) {
      logger.error("オークワ請求書エラー [ 店舗一覧取得 == 0 ]");
      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("オークワ請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);
      return isPrintOk;
    }

    // ---------------------------------------------------------------------
    // 総ページ数算出
    // ---------------------------------------------------------------------

    int allPageSu = 0;
    for (OkuwaTempoInfo info : tempoList) {
      allPageSu += (info.getRecordSu() / kaiPageGyosu);
      if ((info.getRecordSu() % kaiPageGyosu) > 0) {
        allPageSu++;
      }
    }
    logger.debug("総ページ数 allPageSu=[" + allPageSu + "]");

    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;

    try {

      // -----------------------------------------------------------------
      // ファイルオープン
      // -----------------------------------------------------------------

      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          filePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR).withQuote(null);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);

      // -----------------------------------------------------------------
      // オークワ請求書（鑑）出力
      // -----------------------------------------------------------------

      logger.debug("□ オークワ請求書（鑑） ---------->>>>>>>>>>");
      outputOkuwaSeikyushoKagami(tsvPrinter, headerInfo, tempoList, allPageSu);
      logger.debug("□ オークワ請求書（鑑） <<<<<<<<<<----------");

      // -----------------------------------------------------------------
      // オークワ請求書（明細）出力
      // -----------------------------------------------------------------

      // オークワ明細ハンドラ
      OkuwaSeikyushoPrintResultHandler handler = new OkuwaSeikyushoPrintResultHandler();

      handler.headerInfo = headerInfo;
      handler.tsvPrinter = tsvPrinter;

      handler.outPageNo = 0; // ゼロスタート
      handler.wrkTempoCd = ""; // 店舗コード（ブレークキー）

      handler.kaiPageGyosu = kaiPageGyosu;
      handler.allPageSu = allPageSu;

      logger.debug("□ オークワ請求書（明細） ---------->>>>>>>>>>");
      seikyuCommonDao.getSeikyuCommonMapper().getOkuwaMeisaiInfoList(headerInfo
          .getSeikyuId(), handler);
      logger.debug("□ オークワ請求書（明細） <<<<<<<<<<----------");
      logger.debug("       handler.recordCount = [" + handler.recordCount
          + "]");

      isPrintOk = true;

    } catch (Exception ex) {

      logger.error("オークワ請求書出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("オークワ請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

    } finally {

      // -----------------------------------------------------------------
      // ファイルクローズ
      // -----------------------------------------------------------------

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

    return isPrintOk;

  }

  /**
   * オークワ請求書鑑出力.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param tempoList オークワ店舗一覧（鑑情報）
   * @param allPageSu 総ページ数
   */
  private void outputOkuwaSeikyushoKagami(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo,
      List<OkuwaTempoInfo> tempoList, int allPageSu) {

    try {

      for (OkuwaTempoInfo info : tempoList) {

        // -----------------------------------------------------------------
        // ヘッダ部
        // -----------------------------------------------------------------

        // 請求先名
        tsvPrinter.print(headerInfo.getSeikyusakiName());
        // 得意先名
        tsvPrinter.print("");
        // ページ番号
        tsvPrinter.print("0");
        // 総ページ数
        tsvPrinter.print(allPageSu);
        // 仕入先コード
        tsvPrinter.print(headerInfo.getTorihikiCd());
        // 伝票区分
        tsvPrinter.print(headerInfo.getDempyoKbn());
        // 自由欄
        tsvPrinter.print("");
        // 請求締日
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 仕入先住所
        tsvPrinter.print(headerInfo.getJigyoshoAddr1() + headerInfo
            .getJigyoshoAddr2());
        // 仕入先電話番号
        tsvPrinter.print(headerInfo.getJigyoshoTelNo());
        // 仕入先名
        tsvPrinter.print(headerInfo.getJigyoshoName());

        // -----------------------------------------------------------------
        // フッタ部
        // -----------------------------------------------------------------

        // 前月請求高
        tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
        // 前月入金高
        tsvPrinter.print(headerInfo.getKonkaiNyukin());
        // 当月請求高
        tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

        // 社店コード
        tsvPrinter.print("");

        // -----------------------------------------------------------------
        // 明細部
        // -----------------------------------------------------------------

        // 納品年月日
        tsvPrinter.print("");
        // 社店コード
        tsvPrinter.print(info.getTempoCd());

        // 伝票No
        String dempyoNo = String.format(("%1$0" + info.getValidDigit() + "d"),
            0);
        logger.debug("伝票No dempyoNo=[" + dempyoNo + "]");

        tsvPrinter.print(dempyoNo);

        // 請求額
        tsvPrinter.print(java.lang.Math.abs(info.getUriageGaku() + info
            .getTax()));
        // 符号
        if ((info.getUriageGaku() + info.getTax()) < 0) {
          // マイナス
          tsvPrinter.print("-");
        } else {
          tsvPrinter.print("");
        }
        // 備考
        tsvPrinter.print("");

        // -----------------------------------------------------------------
        // 改行
        // -----------------------------------------------------------------

        tsvPrinter.println();
      }

    } catch (IOException ex) {

      logger.error("オークワ請求書（鑑） TSV項目出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

      throw new RuntimeException(ex);
    }
  }

  /**
   * 大学生協請求書.
   * 
   * @param headerInfo 請求ヘッダ情報
   * @param filePath TSVファイルパス
   * @param errorList 画面表示用エラーリスト
   * @return 出力結果
   */
  private boolean printDaigakuSeikyoSeikyusho(SeikyuHeaderInfo headerInfo,
      String filePath,
      List<ErrorMessages> errorList) {

    boolean isPrintOk = false;

    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;

    try {

      // -----------------------------------------------------------------
      // ファイルオープン
      // -----------------------------------------------------------------

      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          filePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR).withQuote(null);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);

      // -----------------------------------------------------------------
      // 大学生協明細出力
      // -----------------------------------------------------------------

      // 大学生協明細ハンドラ
      DaigakuSeikyoSeikyushoPrintResultHandler handler = null;
      handler = new DaigakuSeikyoSeikyushoPrintResultHandler();

      handler.headerInfo = headerInfo;
      handler.tsvPrinter = tsvPrinter;

      logger.debug("□ 大学生協請求書 ---------->>>>>>>>>>");
      seikyuCommonDao.getSeikyuCommonMapper().getDaigakuSeikyoMeisaiInfoList(
          headerInfo.getSeikyuId(), handler);
      logger.debug("□ 大学生協請求書 <<<<<<<<<<----------");
      logger.debug("       handler.recordCount = [" + handler.recordCount
          + "]");

      // -----------------------------------------------------------------
      // 大学生協（鑑）情報取得
      // -----------------------------------------------------------------

      Map<String, Object> kagamiMap = seikyuCommonDao.getSeikyuCommonMapper()
          .getDaigakuSeikyoKagamiInfo(headerInfo.getSeikyuId());

      // -----------------------------------------------------------------
      // 大学生協請求書（鑑）出力
      // -----------------------------------------------------------------

      logger.debug("□ 大学生協請求書（鑑） ---------->>>>>>>>>>");
      outputDaigakuSeikyoSeikyushoKagami(tsvPrinter, headerInfo, kagamiMap);
      logger.debug("□ 大学生協請求書（鑑） <<<<<<<<<<----------");

      isPrintOk = true;

    } catch (Exception ex) {

      logger.error("大学生協請求書出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      logger.error("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.error("請求ID = [" + headerInfo.getSeikyuId() + "]");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("大学生協請求書の出力");
      params.add(headerInfo.getSeikyusakiCd());

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI016-E", params));
      errorList.add(errorMsg);

    } finally {

      // -----------------------------------------------------------------
      // ファイルクローズ
      // -----------------------------------------------------------------

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

    return isPrintOk;

  }

  /**
   * 大学生協請求書（鑑）出力.
   * 
   * @param tsvPrinter ファイルハンドル
   * @param headerInfo 請求ヘッダ情報
   * @param kagamiMap 鑑情報MAP
   */
  private void outputDaigakuSeikyoSeikyushoKagami(CSVPrinter tsvPrinter,
      SeikyuHeaderInfo headerInfo,
      Map<String, Object> kagamiMap) {

    int uriageGaku = Integer.valueOf(String.valueOf(kagamiMap.get(
        "KINGAKU_URIAGE")));
    int uriageTax = Integer.valueOf(String.valueOf(kagamiMap.get(
        "TAX_URIAGE")));
    int hempinGaku = Integer.valueOf(String.valueOf(kagamiMap.get(
        "KINGAKU_HEMPIN")));
    int hempinTax = Integer.valueOf(String.valueOf(kagamiMap.get(
        "TAX_HEMPIN")));
    int nebikiGaku = Integer.valueOf(String.valueOf(kagamiMap.get(
        "KINGAKU_NEBIKI")));
    int nebikiTax = Integer.valueOf(String.valueOf(kagamiMap.get(
        "TAX_NEBIKI")));

    try {

      // -----------------------------------------------------------------
      // 制御部
      // -----------------------------------------------------------------

      // 帳票ID
      tsvPrinter.print(headerInfo.getMultiChohyoId());

      // -----------------------------------------------------------------
      // ヘッダ部
      // -----------------------------------------------------------------

      // 自社住所
      tsvPrinter.print(headerInfo.getJigyoshoAddr1() + headerInfo
          .getJigyoshoAddr2());
      // 自社名
      tsvPrinter.print("");
      // 自事業所名
      tsvPrinter.print(headerInfo.getJigyoshoName());

      // -----------------------------------------------------------------
      // 金額欄
      // -----------------------------------------------------------------

      // 当月納品高
      tsvPrinter.print(uriageGaku);
      tsvPrinter.print(uriageTax);
      tsvPrinter.print(uriageGaku + uriageTax);
      // 前月請求高
      tsvPrinter.print("");
      tsvPrinter.print("");
      tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
      // 当月入金高
      tsvPrinter.print("");
      tsvPrinter.print("");
      tsvPrinter.print(headerInfo.getKonkaiNyukin());
      // 差引計
      tsvPrinter.print("");
      tsvPrinter.print("");
      tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku() - headerInfo
          .getKonkaiNyukin());
      // 値引額
      tsvPrinter.print(nebikiGaku);
      tsvPrinter.print(nebikiTax);
      tsvPrinter.print(nebikiGaku + nebikiTax);
      // 当月納品高
      tsvPrinter.print(uriageGaku + hempinGaku + nebikiGaku);
      tsvPrinter.print(uriageTax + hempinTax + nebikiTax);
      tsvPrinter.print(uriageGaku + hempinGaku + nebikiGaku + uriageTax
          + hempinTax + nebikiTax);
      // 当月請求合計
      tsvPrinter.print("");
      tsvPrinter.print("");
      tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

      // -----------------------------------------------------------------
      // 改行
      // -----------------------------------------------------------------

      tsvPrinter.println();

    } catch (IOException ex) {

      logger.error("大学生協請求書（鑑） TSV項目出力エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
      logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

      throw new RuntimeException(ex);
    }

  }

  private void updateSeikyushoInsatsuFlag(String seikyuId, String userId,
      String programId) {

    Map<String, Object> params = new HashMap<String, Object>();

    params.clear();
    params.put("SEIKYU_ID", seikyuId);
    // 共通項目
    params.put("USER_ID", userId);
    params.put("PROGRAM_ID", programId);
    params.put("CURRENT_DATE", DateUtil.getSysDate());
    params.put("CURRENT_TIME", DateUtil.getSysTime());

    // ---------------------------------------------------------------------
    // 売上明細（売掛）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上明細（売掛） UPDATE ▽▽▽");
    int updCount = seikyuCommonDao.updateInsatsuFlagOnUriageMeisaiUrikake(
        params);
    logger.debug("△△△ 売上明細（売掛） UPDATE △△△ updCount=[" + updCount + "]");

    // ---------------------------------------------------------------------
    // 売上明細（未収）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上明細（未収）UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateInsatsuFlagOnUriageMeisaiMishu(params);
    logger.debug("△△△ 売上明細（未収）UPDATE △△△ updCount=[" + updCount + "]");

    // ---------------------------------------------------------------------
    // 売上ヘッダ（売掛）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上ヘッダ（売掛） UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateInsatsuFlagOnUriageHeaderUrikake(params);
    logger.debug("△△△ 売上ヘッダ（売掛） UPDATE △△△ updCount=[" + updCount + "]");

    // ---------------------------------------------------------------------
    // 売上ヘッダ（未収）の更新
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽ 売上ヘッダ（未収） UPDATE ▽▽▽");
    updCount = seikyuCommonDao.updateInsatsuFlagOnUriageHeaderMishu(params);
    logger.debug("△△△ 売上ヘッダ（未収） UPDATE △△△ updCount=[" + updCount + "]");

  }

  // *************************************************************************
  // **
  // ** INNER CLASS
  // **
  // *************************************************************************

  /**
   * 請求明細登録用 ResultHandler Class.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   * 
   */
  private class SeikyuMeisaiInsertResultHandler implements ResultHandler {

    private int recordCount = 0; // レコード数

    private int uriageGakuSummary = 0; // 売上額サマリー
    private int taxSummary = 0; // 消費税サマリー
    private long seikyuId = 0; // 請求ID
    private String seikyuShimebi = null; // 請求締日
    private double taxRate = 0; // 消費税リスト

    private CommonGetSystemCom commonGetSysCom; // 共通部品
    private SeikyusakiInfo seikyusakiInfo; // 請求先情報

    private String userId; // ユーザーID
    private String programId; // プログラムID

    private int torihikiGakuWork = 0; // 取引額WORK

    private String wrkTempoCd = ""; // 店舗コード退避用
    private String wrkTokuisakiDempyoNo = ""; // 得意先伝票No退避用

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      // 請求明細元データ
      SeikyuMeisaiMotoData motoData = (SeikyuMeisaiMotoData) context
          .getResultObject();

      // -----------------------------------------------------------------
      // 入金調整チェック
      // -----------------------------------------------------------------

      if (motoData.getNyuryokuKeitai().equals(
          SeiConst.NYURYOKU_KEITAI_NYUKIN_CHOSEI_URIAGE)) {
        logger.debug("入金調整 -> return");
        return;
      }

      // -----------------------------------------------------------------
      // 納品単価 / 先方仕切単価
      // -----------------------------------------------------------------

      if (seikyusakiInfo.getSeikyushoTanka().equals(
          SeiConst.SEIKYUSHO_TANKA_NOHIN)) {
        // 納品単価
        torihikiGakuWork = motoData.getTorihikiGaku();
      } else {
        // 先方仕切単価
        torihikiGakuWork = motoData.getSempoShikiriKingaku();
      }

      // -----------------------------------------------------------------
      // 集約店舗チェック
      // -----------------------------------------------------------------

      logger.debug("店舗コード    = [" + motoData.getTempoCd() + "]");
      logger.debug("WRK店舗コード = [" + motoData.getWrkTempoCd() + "]");
      logger.debug("得意先伝票No  = [" + motoData.getTokuisakiDempyoNo() + "]");
      logger.debug("集約先店舗区分= [" + motoData.getShuyakusakiTempoKbn() + "]");
      logger.debug("店舗店舗条件  = [" + motoData.getTempoShuyakuJoken() + "]");

      if (motoData.getShuyakusakiTempoKbn().equals(
          SeiConst.SHUYAKU_TEMPO_KBN_SHUYAKU_SAKI)
          && motoData.getTempoShuyakuJoken().equals(
              SeiConst.TEMPO_SHUYAKU_JOKEN_DEMPYO)) {

        logger.debug("1.集約先の店舗（伝票単位で集約）");

        if (wrkTempoCd.equals(motoData.getWrkTempoCd())
            && wrkTokuisakiDempyoNo.equals(motoData.getTokuisakiDempyoNo())) {
          // UPDATE
          Map<String, Object> params = new HashMap<String, Object>();

          params.put("TORIHIKI_GAKU", torihikiGakuWork);
          params.put("TAX_HASU_SHORI", seikyusakiInfo.getTaxHasuShori());
          params.put("TAX_RATE", taxRate);
          params.put("SEIKYU_ID", seikyuId);
          params.put("TEMPO_CD", wrkTempoCd);
          params.put("TOKUISAKI_DEMPYO_NO", wrkTokuisakiDempyoNo);

          logger.debug("□ updateSeikyuMeisaiForShuyakuTempo");
          logger.debug("消費税端数処理 = [" + seikyusakiInfo.getTaxHasuShori() + "]");

          logger.debug("▽▽▽ 請求明細（集約店舗） UPDATE ▽▽▽");
          int updCount = seikyuCommonDao.updateSeikyuMeisaiForShuyakuTempo(
              params);
          logger.debug("△△△ 請求明細（集約店舗） UPDATE 更新件数=[" + updCount + "] △△△");

          return;

        } else {
          wrkTempoCd = motoData.getWrkTempoCd();
          wrkTokuisakiDempyoNo = motoData.getTokuisakiDempyoNo();
        }
      } else if (motoData.getShuyakusakiTempoKbn().equals(
          SeiConst.SHUYAKU_TEMPO_KBN_TSUJO)
          && !motoData.getShuyakuTempoCode().equals("")
          && motoData.getTempoShuyakuJoken().equals(
              SeiConst.TEMPO_SHUYAKU_JOKEN_DEMPYO)) {

        logger.debug("2.集約先に集約される店舗（伝票単位で集約）");

        if (wrkTempoCd.equals(motoData.getWrkTempoCd())
            && wrkTokuisakiDempyoNo.equals(motoData.getTokuisakiDempyoNo())) {
          // UPDATE
          Map<String, Object> params = new HashMap<String, Object>();

          params.put("TORIHIKI_GAKU", torihikiGakuWork);
          params.put("TAX_HASU_SHORI", seikyusakiInfo.getTaxHasuShori());
          params.put("TAX_RATE", taxRate);
          params.put("SEIKYU_ID", seikyuId);
          params.put("TEMPO_CD", wrkTempoCd);
          params.put("TOKUISAKI_DEMPYO_NO", wrkTokuisakiDempyoNo);

          logger.debug("□ updateSeikyuMeisaiForShuyakuTempo");
          logger.debug("消費税端数処理 = [" + seikyusakiInfo.getTaxHasuShori() + "]");

          logger.debug("▽▽▽ 請求明細（集約店舗） UPDATE ▽▽▽");
          int updCount = seikyuCommonDao.updateSeikyuMeisaiForShuyakuTempo(
              params);
          logger.debug("△△△ 請求明細（集約店舗） UPDATE 更新件数=[" + updCount + "] △△△");

          return;

        } else {
          wrkTempoCd = motoData.getWrkTempoCd();
          wrkTokuisakiDempyoNo = motoData.getTokuisakiDempyoNo();
        }
      }

      // -----------------------------------------------------------------
      // 消費税計算
      // -----------------------------------------------------------------

      double tempTax = NumberUtil.multiply(String.valueOf(torihikiGakuWork),
          String.valueOf(taxRate));
      tempTax = NumberUtil.divide(tempTax, 100);
      // 端数処理
      BigDecimal bdTax = commonGetSysCom.getNumberRound(tempTax, 1,
          seikyusakiInfo.getTaxHasuShori());
      int intTax = bdTax.intValue();

      logger.debug("売上額=[" + torihikiGakuWork + "]");
      logger.debug("消費税=[" + intTax + "]");

      // -----------------------------------------------------------------
      // 請求明細項目セット
      // -----------------------------------------------------------------

      TblSei01SeiBody seiBody = new TblSei01SeiBody();

      // 請求ID
      seiBody.setBildId(seikyuId);
      // 行No
      short bildRow = (short) context.getResultCount();
      seiBody.setBildRow(bildRow);
      // チェーンコード
      seiBody.setChainCode(motoData.getChainCode());
      // チェーン枝番
      seiBody.setChainIdx(motoData.getChainIdx());
      // 請求先CD
      seiBody.setBildCode(seikyusakiInfo.getSeikyusakiCd());
      // 得意先CD
      seiBody.setCustCode(motoData.getTokuisakiCd());
      // 店舗CD
      seiBody.setShopCode(motoData.getWrkTempoCd());
      // 事業所CD
      seiBody.setJigyoCode(motoData.getJigyoshoCd());
      // 納品日
      seiBody.setDeliDate(motoData.getNohinBi());
      // 請求締日
      seiBody.setBildDate(seikyuShimebi);
      // 売上額
      seiBody.setTrdPrice(torihikiGakuWork);
      // 消費税
      seiBody.setTrdTax(intTax);
      // 販売金額
      seiBody.setSaleAmt(motoData.getSumHanbaiKingaku());
      // 得意先伝票No
      seiBody.setCustDenNo(motoData.getTokuisakiDempyoNo());
      // 税区分
      seiBody.setTaxKb(motoData.getUchizeiKokyakuKbn());
      // 税率
      seiBody.setTaxRate(BigDecimal.valueOf(taxRate));
      // 販売区分
      seiBody.setSaleKb(motoData.getHambaiKbn());
      // 分類コード
      seiBody.setBnCode(motoData.getBunruiCd());
      // データ区分
      seiBody.setDatKb(motoData.getDataKbn());
      // 自社伝票No
      seiBody.setDenNo(motoData.getJishaDempyoNo());
      // 自社伝票No枝番
      seiBody.setDenIdx(motoData.getJishaDempyoNoIdx());

      // 共通項目
      String currentDate = DateUtil.getSysDate();
      String currentTime = DateUtil.getSysTime();

      seiBody.setInsUserid(userId);
      seiBody.setInsPgid(programId);
      seiBody.setInsYmd(currentDate);
      seiBody.setInsTime(currentTime);
      seiBody.setUpdUserid(userId);
      seiBody.setUpdPgid(programId);
      seiBody.setUpdYmd(currentDate);
      seiBody.setUpdTime(currentTime);

      logger.debug("▽▽▽ 請求明細 INSERT ▽▽▽");
      seikyuCommonDao.getTblSei01SeiBodyMapper().insert(seiBody);
      logger.debug("△△△ 請求明細 INSERT △△△");

      // レコード数カウントアップ
      recordCount++;

      if (!motoData.getNyuryokuKeitai().equals(
          SeiConst.NYURYOKU_KEITAI_SAI_SEIKYU_URIAGE)) {

        logger.debug("再請求売上登録でない");

        uriageGakuSummary += torihikiGakuWork;
        taxSummary += intTax;

      } else {

        logger.debug("再請求売上登録である");

      }
    }
  }

  /**
   * 自社請求書 明細出力用 ResultHandler Class.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   *
   */
  private class SeikyuMeisaiPrintResultHandler implements ResultHandler {

    private int recordCount; // レコード数
    private int outPageNo; // 出力ページ番号

    private CSVPrinter tsvPrinter; // ファイルハンドル
    private SeikyuHeaderInfo headerInfo; // 請求ヘッダ情報

    private int kaiPageGyosu; // 改ページ行数

    private boolean isZanPrint; // 残印字フラグ
    private boolean isMeisaiTaxPrint; // 明細消費税出力フラグ

    private String zipCode; // 郵便番号
    private String addr1; // 住所1
    private String addr2; // 住所2
    private String seikyusakiName; // 請求先名
    private String tempoName; // 店舗名

    private int uriageGaku; // 売上額
    private int tax; // 消費税

    private String tempoCodeNone;
    
    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      // 行No（1始まり）
      short rowNo = (short) context.getResultCount();

      // 請求明細情報
      SeikyuMeisaiInfo meisaiInfo = (SeikyuMeisaiInfo) context
          .getResultObject();

      // レコード数カウントアップ
      recordCount++;

      // 出力ページ番号
      if (rowNo != 1 && ((rowNo - 1) % kaiPageGyosu) == 0) {
        logger.debug("改ページ");
        outPageNo++;
      }

      // TSV出力
      outputTsvItems(meisaiInfo);
    }

    /**
     * TSV項目出力.
     * 
     * @param meisaiInfo 明細情報
     * @throws IOException IOエラー
     */
    private void outputTsvItems(SeikyuMeisaiInfo meisaiInfo) {

      // ループ回数（消費税表示ありの場合、2行表示）
      int loopCount = isMeisaiTaxPrint ? 2 : 1;

      try {

        for (int i = 0; i < loopCount; i++) {

          // -------------------------------------------------------------
          // 制御部
          // -------------------------------------------------------------

          // 帳票ID
          tsvPrinter.print(headerInfo.getMultiChohyoId());
          // 明細区分
          tsvPrinter.print(SeiConst.JISHA_SEIKYUSHO_CTRL_AREA_MEISAI);

          // -------------------------------------------------------------
          // ヘッダー部
          // -------------------------------------------------------------

          // 請求書番号
          tsvPrinter.print(headerInfo.getSeikyuId() + "-" + outPageNo);
          // 郵便番号
          tsvPrinter.print(zipCode);
          // 住所
          tsvPrinter.print(addr1 + addr2);
          // 請求先名
          tsvPrinter.print(seikyusakiName);
          // 店舗名
          tsvPrinter.print(tempoName);
          // 請求先コード
          tsvPrinter.print(headerInfo.getSeikyusakiCd());
          // 締日（月）
          tsvPrinter.print(headerInfo.getSeikyuShimebi());
          // 締日（日）
          tsvPrinter.print(headerInfo.getSeikyuShimebi());
          // 入金種別
          tsvPrinter.print(headerInfo.getNyukinClsName());
          // 発行者
          tsvPrinter.print(headerInfo.getTantoshaName());

          if (outPageNo == 1) {
            // 鑑

            // 今回入金対象金額
            tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

            if (isZanPrint) {
              // 前回請求額
              tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
              // 入金額
              tsvPrinter.print(headerInfo.getKonkaiNyukin());
              // 調整額
              tsvPrinter.print(headerInfo.getKonkaiChosei() + headerInfo
                  .getKonkaiSosai());
              // 繰越額
              tsvPrinter.print(headerInfo.getKurikoshiGaku());
            } else {
              // 前回請求額
              tsvPrinter.print("");
              // 入金額
              tsvPrinter.print("");
              // 調整額
              tsvPrinter.print("");
              // 繰越額
              tsvPrinter.print("");
            }

            // 売上額
            tsvPrinter.print(uriageGaku);
            // 消費税
            tsvPrinter.print(tax);
            // 請求額
            tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

          } else {
            // 鑑でない

            // 今回入金対象金額
            tsvPrinter.print("");
            // 前回請求額
            tsvPrinter.print("");
            // 入金額
            tsvPrinter.print("");
            // 調整額
            tsvPrinter.print("");
            // 繰越額
            tsvPrinter.print("");
            // 売上額
            tsvPrinter.print(uriageGaku);
            // 消費税
            if (isMeisaiTaxPrint) {
              tsvPrinter.print(tax);
            } else {
              tsvPrinter.print("");
            }
            // 請求額
            tsvPrinter.print("");
          }

          // -------------------------------------------------------------
          // ヘッダー部（タイトル）
          // -------------------------------------------------------------

          // タイトル1
          tsvPrinter.print("日付");
          // タイトル2
          tsvPrinter.print("店コード");
          // タイトル3
          tsvPrinter.print("伝票No");
          // タイトル4
          tsvPrinter.print("金      額");
          // タイトル5
          tsvPrinter.print("日付");
          // タイトル6
          tsvPrinter.print("店コード");
          // タイトル7
          tsvPrinter.print("伝票No");
          // タイトル8
          tsvPrinter.print("金      額");

          // -------------------------------------------------------------
          // フッター部
          // -------------------------------------------------------------

          // ページ計
          tsvPrinter.print(meisaiInfo.getPageSum());
          // 合計
          if (meisaiInfo.getPageNo() == meisaiInfo.getMaxPageNo()) {
            // 最終ページ
            tsvPrinter.print(uriageGaku);
          } else {
            tsvPrinter.print("");
          }
          // 消費税
          if (meisaiInfo.getPageNo() == meisaiInfo.getMaxPageNo()) {
            // 最終ページ
            if (isMeisaiTaxPrint) {
              tsvPrinter.print(tax);
            } else {
              tsvPrinter.print("");
            }
          } else {
            tsvPrinter.print("");
          }

          // -------------------------------------------------------------
          // 明細部
          // -------------------------------------------------------------

          NumberFormat numFormat = NumberFormat.getNumberInstance();

          if (i == 0) {
            // 値1
            String tmpNohinbi = meisaiInfo.getNohinbi();

            String nohinbi = tmpNohinbi.substring(4, 6) + "." + tmpNohinbi
                .substring(6);

            tsvPrinter.print(nohinbi);
            // 値2
            if (meisaiInfo.getTempoCd() != null && !meisaiInfo.getTempoCd().equals(tempoCodeNone)) {
              tsvPrinter.print(meisaiInfo.getTempoCd());
            } else {
              tsvPrinter.print("");
            }
            // 値3
            tsvPrinter.print(meisaiInfo.getTokuisakiDempyoNo());
            // 値4：売上
            tsvPrinter.print(numFormat.format(meisaiInfo.getUriageGaku()));
          } else {
            // 値1
            tsvPrinter.print("");
            // 値2
            tsvPrinter.print("");
            // 値3
            tsvPrinter.print("");
            // 値4：消費税
            tsvPrinter.print(numFormat.format(meisaiInfo.getTax()));
          }

          // 値5
          tsvPrinter.print("");
          // 値6
          tsvPrinter.print("");
          // 値7
          tsvPrinter.print("");
          // 値8
          tsvPrinter.print("");

          // -------------------------------------------------------------
          // 改行
          // -------------------------------------------------------------

          tsvPrinter.println();

        }

      } catch (IOException ex) {

        logger.error("自社請求書 明細 TSV項目出力エラー [" + ex.getMessage() + "]");
        ex.printStackTrace();
        logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
        logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

        throw new RuntimeException(ex);
      }
    }

  }

  /**
   * イオン請求書 出力用 ResultHandler Class.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   *
   */
  private class AeonSeikyushoPrintResultHandler implements ResultHandler {

    private int recordCount; // レコード数

    private String nohinKingaku;
    private String hempinKingaku;

    private SeikyuHeaderInfo headerInfo;
    private CSVPrinter tsvPrinter;

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      // イオン明細情報
      AeonMeisaiInfo meisaiInfo = (AeonMeisaiInfo) context.getResultObject();

      // レコード数カウントアップ
      recordCount++;

      try {

        // -------------------------------------------------------------
        // ヘッダ部
        // -------------------------------------------------------------

        // 請求先名
        tsvPrinter.print(headerInfo.getSeikyusakiName());
        // 自社住所
        tsvPrinter.print(headerInfo.getJigyoshoAddr1() + headerInfo
            .getJigyoshoAddr2());
        // 自社名
        tsvPrinter.print(headerInfo.getJigyoshoName());
        // 取引コード
        tsvPrinter.print(headerInfo.getTorihikiCd());
        // 請求締日
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 入金予定日
        tsvPrinter.print(headerInfo.getNyukinYoteibi());

        // -------------------------------------------------------------
        // フッタ部
        // -------------------------------------------------------------

        // 前月請求額
        tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
        // 前月入金高
        tsvPrinter.print(headerInfo.getKonkaiNyukin());
        // 当月納品
        tsvPrinter.print(nohinKingaku);
        // 当月返品
        tsvPrinter.print(hempinKingaku);
        // 当月請求高
        tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

        // -------------------------------------------------------------
        // 明細部
        // -------------------------------------------------------------

        // 納品日
        tsvPrinter.print(meisaiInfo.getNohinbi());
        // 店コード
        tsvPrinter.print(meisaiInfo.getTempoCd());
        // 分類コード
        String nohinKbn = meisaiInfo.getNohinKbn();
        String hambaiKbn = meisaiInfo.getHambaiKbn();

        if (nohinKbn.equals(SeiConst.NOHIN_KBN_MISE_CHOKU) && hambaiKbn.equals(
            SeiConst.HAMBAI_KBN_TEIBAN)) {
          // 定番・店直
          tsvPrinter.print(meisaiInfo.getBunruiCdStShop());

        } else if (nohinKbn.equals(SeiConst.NOHIN_KBN_MISE_CHOKU)
            && hambaiKbn.equals(SeiConst.HAMBAI_KBN_TOKUBAI)) {
          // 特売・店直
          tsvPrinter.print(meisaiInfo.getBunruiCdSpShop());

        } else if (nohinKbn.equals(SeiConst.NOHIN_KBN_CENTER) && hambaiKbn
            .equals(SeiConst.HAMBAI_KBN_TEIBAN)) {
          // 定番・センター
          tsvPrinter.print(meisaiInfo.getBunruiCdStCenter());

        } else if (nohinKbn.equals(SeiConst.NOHIN_KBN_CENTER)
            && hambaiKbn.equals(SeiConst.HAMBAI_KBN_TOKUBAI)) {
          // 特売・センター
          tsvPrinter.print(meisaiInfo.getBunruiCdSpCenter());

        } else {
          // その他
          tsvPrinter.print("");

        }

        // 伝票No
        tsvPrinter.print(meisaiInfo.getTokuisakiDempyoNo());
        // 金額
        tsvPrinter.print(meisaiInfo.getUriageGaku() + meisaiInfo.getTax());

        // -------------------------------------------------------------
        // 改行
        // -------------------------------------------------------------

        tsvPrinter.println();

      } catch (IOException ex) {

        logger.error("イオン請求書 TSV項目出力エラー [" + ex.getMessage() + "]");
        ex.printStackTrace();
        logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
        logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

        throw new RuntimeException(ex);
      }
    }
  }

  /**
   * オークワ請求書 出力用 Result Handler.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   *
   */
  private class OkuwaSeikyushoPrintResultHandler implements ResultHandler {

    private int recordCount; // レコード数

    private SeikyuHeaderInfo headerInfo;
    private CSVPrinter tsvPrinter;

    private int outPageNo; // 出力ページ番号
    private String wrkTempoCd; // 店舗コード（ブレークキー）

    private int kaiPageGyosu; // 改ページ行数
    private int allPageSu; // 総ページ数

    private int idx = 0;

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      // オークワ明細情報
      OkuwaMeisaiInfo meisaiInfo = (OkuwaMeisaiInfo) context.getResultObject();

      // レコード数カウントアップ
      recordCount++;

      // ページ番号
      if (!wrkTempoCd.equals(meisaiInfo.getTempoCd())) {

        logger.debug("店舗 - 改ページ");

        outPageNo++;
        wrkTempoCd = meisaiInfo.getTempoCd();
        idx = 0;

      } else if ((idx % kaiPageGyosu) == 0) {

        logger.debug("計算 - 改ページ");
        outPageNo++;

      }

      try {

        // -----------------------------------------------------------------
        // ヘッダ部
        // -----------------------------------------------------------------

        // 請求先名
        tsvPrinter.print(headerInfo.getSeikyusakiName());
        // 得意先名
        tsvPrinter.print(meisaiInfo.getTempoName());
        // ページ番号
        tsvPrinter.print(outPageNo);
        // 総ページ数
        tsvPrinter.print(allPageSu);
        // 仕入先コード
        tsvPrinter.print(headerInfo.getTorihikiCd());
        // 伝票区分
        tsvPrinter.print(headerInfo.getDempyoKbn());
        // 自由欄
        tsvPrinter.print("");
        // 請求締日
        tsvPrinter.print(headerInfo.getSeikyuShimebi());
        // 仕入先住所
        tsvPrinter.print(headerInfo.getJigyoshoAddr1() + headerInfo
            .getJigyoshoAddr2());
        // 仕入先電話番号
        tsvPrinter.print(headerInfo.getJigyoshoTelNo());
        // 仕入先名
        tsvPrinter.print(headerInfo.getJigyoshoName());

        // -----------------------------------------------------------------
        // フッタ部
        // -----------------------------------------------------------------

        // 前月請求高
        tsvPrinter.print(headerInfo.getZenkaiSeikyuGaku());
        // 前月入金高
        tsvPrinter.print(headerInfo.getKonkaiNyukin());
        // 当月請求高
        tsvPrinter.print(headerInfo.getKonkaiSeikyuGaku());

        // 社店コード
        tsvPrinter.print(meisaiInfo.getTempoCd());

        // -----------------------------------------------------------------
        // 明細部
        // -----------------------------------------------------------------

        // 納品年月日
        tsvPrinter.print(meisaiInfo.getNohinbi());
        // 社店コード
        tsvPrinter.print(meisaiInfo.getTempoCd());
        // 伝票No
        tsvPrinter.print(meisaiInfo.getTokuisakiDempyoNo());
        // 請求額
        tsvPrinter.print(java.lang.Math.abs(meisaiInfo.getUriageGaku()
            + meisaiInfo.getTax()));
        // 符号
        if ((meisaiInfo.getUriageGaku() + meisaiInfo.getTax()) < 0) {
          // マイナス
          tsvPrinter.print("-");
        } else {
          tsvPrinter.print("");
        }
        // 備考
        tsvPrinter.print("");

        // -----------------------------------------------------------------
        // 改行
        // -----------------------------------------------------------------

        tsvPrinter.println();

        // 次へ
        idx++;

      } catch (IOException ex) {

        logger.error("オークワ請求書 TSV項目出力エラー [" + ex.getMessage() + "]");
        ex.printStackTrace();
        logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
        logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

        throw new RuntimeException(ex);
      }
    }
  }

  /**
   * 大学生協請求書 出力用 Result Handler.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   *
   */
  private class DaigakuSeikyoSeikyushoPrintResultHandler implements
      ResultHandler {

    private int recordCount; // レコード数

    private SeikyuHeaderInfo headerInfo;
    private CSVPrinter tsvPrinter;

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      // 大学生協明細情報
      DaigakuSeikyoMeisaiInfo meisaiInfo = (DaigakuSeikyoMeisaiInfo) context
          .getResultObject();

      // レコード数カウントアップ
      recordCount++;

      try {

        // -------------------------------------------------------------
        // 制御部
        // -------------------------------------------------------------

        // 帳票ID
        tsvPrinter.print(headerInfo.getSeikyushoShuruiChohyoId());

        // -------------------------------------------------------------
        // ヘッダ部
        // -------------------------------------------------------------

        // 請求先名
        tsvPrinter.print(meisaiInfo.getSeikyusakiName());
        // 得意先名
        tsvPrinter.print(meisaiInfo.getTokuisakiName());
        // 自社住所
        tsvPrinter.print(headerInfo.getJigyoshoAddr1() + headerInfo
            .getJigyoshoAddr2());
        // 自社名
        tsvPrinter.print("");
        // 自事業所名
        tsvPrinter.print(headerInfo.getJigyoshoName());
        // コード番号
        tsvPrinter.print(headerInfo.getTorihikiCd());

        // -------------------------------------------------------------
        // 明細部
        // -------------------------------------------------------------

        // 社店
        tsvPrinter.print(meisaiInfo.getTempoCd());
        // 店名
        tsvPrinter.print(meisaiInfo.getTempoName());
        // 納品金額
        tsvPrinter.print(meisaiInfo.getNohinKingaku());
        // 枚数1
        tsvPrinter.print(meisaiInfo.getNohinDempyoSu());
        // 返品金額
        tsvPrinter.print(meisaiInfo.getHempinKingaku());
        // 枚数2
        tsvPrinter.print(meisaiInfo.getHempinDempyoSu());
        // お買い上げ額
        tsvPrinter.print(meisaiInfo.getNohinKingaku() - meisaiInfo
            .getHempinKingaku());

        // -------------------------------------------------------------
        // 改行
        // -------------------------------------------------------------

        tsvPrinter.println();

      } catch (IOException ex) {

        logger.error("大学生協請求書 TSV項目出力エラー [" + ex.getMessage() + "]");
        ex.printStackTrace();
        logger.debug("請求先コード = [" + headerInfo.getSeikyusakiCd() + "]");
        logger.debug("請求ID = [" + headerInfo.getSeikyuId() + "]");

        throw new RuntimeException(ex);
      }
    }
  }

}
