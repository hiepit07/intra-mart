/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0104d01Service.java
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
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0104d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.SeikyuCommonDao;

/**
 * 請求締め取消処理用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Sei0104d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private Sei0104d01Dao sei0104d01Dao;

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
  public boolean checkInputParamsForSearch(Model model, FormSei0104d01 form) {

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
  public boolean checkInputParamsForExecute(Model model, FormSei0104d01 form) {

    boolean isShimeSumi = true;
    boolean isNyukinSumi = false;
    boolean isTorimatome = false;
    boolean isMiraiShimeSumi = false;

    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    for (SeikyusakiInfo info : list) {

      String seikyuId = info.getKonkaiSeikyuId();
      if (seikyuId == null || seikyuId.equals("")) {
        // 締め処理未あり
        isShimeSumi = false;
      } else if (info.getNyukinKakuninFlag().equals(
          SeiConst.NYUKIN_KAKUNIN_FLAG_NYUKIN_ZUMI)) {
        // 入金済みあり
        isNyukinSumi = true;
      } else if (info.getKonkaiSeikyuShimeKbn().equals(
          SeiConst.SEIKYU_SHIME_KBN_TORIMATOME_MOTO)) {
        // 取り纏めあり
        isNyukinSumi = true;
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
        // 請求締日
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

    if (!isShimeSumi) {
      logger.debug("SEI005-E=請求締め処理が行われていません");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI005-E", null));

      errorList.add(errorMsg);
    }
    if (isNyukinSumi) {
      logger.debug("SEI006-E=入金済みのため、請求締め取消できません");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI006-E", null));

      errorList.add(errorMsg);
    }
    if (isTorimatome) {
      logger.debug("SEI007-E=取り纏め請求済みのため、請求締め取消できません");
      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI007-E", null));

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
  public List<SeikyusakiInfo> getSeikyusakiInfoList(FormSei0104d01 form) {

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
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);
    // 請求締日
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);
    // 業務日付
    String businessDate = form.getBusinessDate();
    params.put("BIZ_DATE", businessDate);
    // 請求先コード
    String seikyusakiCd = form.getCondSeikyusakiCd();
    params.put("SEIKYUSAKI_CD", seikyusakiCd);
    // 締日WK
    params.put("SHIMEBI_WK", shimebiWk);

    // 検索実行
    List<SeikyusakiInfo> list = sei0104d01Dao.getSei0104d01Mapper()
        .getSeikyusakiInfoList(params);

    return list;
  }

  /**
   * 請求締め取消.
   * 
   * @param form フォーム
   * @param 請求先info
   * @param エラーリスト
   * @param ユーザーID
   * @return 処理結果
   */
  public boolean executeTorikeshi(FormSei0104d01 form, SeikyusakiInfo info,
      ArrayList<ErrorMessages> errorList, String userId) {

    boolean isOk = false;

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    try {

      logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
      status = txManager.getTransaction(def);
      logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

      Map<String, Object> params = new HashMap<String, Object>();
      int updCount = 0;

      // 共通項目
      String currentDate = DateUtil.getSysDate();
      String currentTime = DateUtil.getSysTime();
      String programId = SeiConst.PG_ID_SEIKYU_SHIME_TORIKESHI;

      // ---------------------------------------------------------------------
      // 請求ヘッダの更新
      // ---------------------------------------------------------------------

      params.clear();
      params.put("SEIKYU_ID", info.getKonkaiSeikyuId());
      params.put("HAITA_CHECK_NICHIJI", form.getHaitaDate() + form
          .getHaitaTime());
      // 共通項目
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);

      logger.debug("▽▽▽ 請求ヘッダ UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateSeikyuHeader(params);
      logger.debug("△△△ 請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");

      if (updCount != 1) {
        // 排他エラー
        logger.error("請求ヘッダ 排他エラー");
        logger.error("更新対象件数=[1] 更新結果件数=[" + updCount + "]");
        logger.error("請求先コード = [" + info.getSeikyusakiCd() + "]");

        // SEI015-E
        ArrayList<String> errorParams = new ArrayList<String>();
        errorParams.add("請求データの更新");
        errorParams.add(info.getSeikyusakiCd());

        ErrorMessages errorMsg = new ErrorMessages();
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "SEI015-E", errorParams));
        errorList.add(errorMsg);

        return false;
      }

      // ---------------------------------------------------------------------
      // 前回請求ヘッダの更新
      // ---------------------------------------------------------------------

      String prevSeikyuId = info.getZenkaiSeikyuId();

      if (prevSeikyuId != null && !prevSeikyuId.equals("")) {
        params.clear();
        params.put("SEIKYU_ID", prevSeikyuId);
        // 共通項目
        params.put("USER_ID", userId);
        params.put("PROGRAM_ID", programId);
        params.put("CURRENT_DATE", currentDate);
        params.put("CURRENT_TIME", currentTime);

        logger.debug("▽▽▽ 前回請求ヘッダ UPDATE ▽▽▽");
        updCount = sei0104d01Dao.updatePrevSeikyuHeader(params);
        logger.debug("△△△ 前回請求ヘッダ UPDATE △△△ updCount=[" + updCount + "]");
      }

      params.clear();
      params.put("SEIKYU_ID", info.getKonkaiSeikyuId());
      // 共通項目
      params.put("USER_ID", userId);
      params.put("PROGRAM_ID", programId);
      params.put("CURRENT_DATE", currentDate);
      params.put("CURRENT_TIME", currentTime);

      // ---------------------------------------------------------------------
      // 売上明細（売掛）の更新
      // ---------------------------------------------------------------------

      logger.debug("▽▽▽ 売上明細（売掛） UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateUriageMeisaiUrikake(params);
      logger.debug("△△△ 売上明細（売掛） UPDATE △△△ updCount=[" + updCount + "]");

      // ---------------------------------------------------------------------
      // 売上明細（未収）の更新
      // ---------------------------------------------------------------------

      logger.debug("▽▽▽ 売上明細（未収）UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateUriageMeisaiMishu(params);
      logger.debug("△△△ 売上明細（未収）UPDATE △△△ updCount=[" + updCount + "]");

      // ---------------------------------------------------------------------
      // 売上ヘッダ（売掛）の更新
      // ---------------------------------------------------------------------

      logger.debug("▽▽▽ 売上ヘッダ（売掛） UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateUriageHeaderUrikake(params);
      logger.debug("△△△ 売上ヘッダ（売掛） UPDATE △△△ updCount=[" + updCount + "]");

      // ---------------------------------------------------------------------
      // 売上ヘッダ（未収）の更新
      // ---------------------------------------------------------------------

      logger.debug("▽▽▽ 売上ヘッダ（未収） UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateUriageHeaderMishu(params);
      logger.debug("△△△ 売上ヘッダ（未収） UPDATE △△△ updCount=[" + updCount + "]");

      // ---------------------------------------------------------------------
      // 売掛明細の更新
      // ---------------------------------------------------------------------

      logger.debug("▽▽▽ 売掛明細 UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateUrikakeMeisai(params);
      logger.debug("△△△ 売掛明細 UPDATE △△△ updCount=[" + updCount + "]");

      // ---------------------------------------------------------------------
      // 未収明細の更新
      // ---------------------------------------------------------------------

      logger.debug("▽▽▽ 未収明細 UPDATE ▽▽▽");
      updCount = sei0104d01Dao.updateMishuMeisai(params);
      logger.debug("△△△ 未収明細 UPDATE △△△ updCount=[" + updCount + "]");

      logger.debug("▼▼▼ コミット ▼▼▼");
      txManager.commit(status);
      logger.debug("▲▲▲ コミット ▲▲▲");

      isOk = true;

    } catch (Exception ex) {

      logger.error("請求締め取消エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();
      logger.error("請求先コード = [" + info.getSeikyusakiCd() + "]");

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");

      // SEI016-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求締め取消処理");
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
