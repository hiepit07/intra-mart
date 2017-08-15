/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.get.impl
 * ファイル名:Get0101D00Controller.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/07
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.get.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirm;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirmExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHeadExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHeadKey;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.FormGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetDeterminationInfoGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetHeaderUirageGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetNextMonthAccountGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.get.impl.Get0101D00Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Get0101D00Mapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.get.impl.Get0101D00Service;

/**
 * コントローラクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/get/GET01-01D00/")
public class Get0101D00Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("get0101D00Service")
  private Get0101D00Service get0101D00Service;

  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /* ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  private TransactionStatus status;

  /**
   * メニュー画面で「事業所月次確定」が選択された場合の初期表示処理
   * 
   * @param model Model
   * @param request HttpServletRequest
   * @return String
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request,
      @ModelAttribute("formGet0101d00") FormGet0101d00 formGet0101d00) {

    // ビュー画面を呼ぶ
    String strScreen = "get/get0101d00";

    // 1 start - セッション取得
    // (1) セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01
    String path = Util.checkSession(model, request,
        CommonConst.GET0101D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // 画面のデータを初期化
    get0101D00Service.init(model, request, formGet0101d00);

    return strScreen;
  }

  /**
   * 事業所が変更された場合の処理。ログインユーザ権限がシステム管理者権限の場合のみ発生
   * 
   * @param request HttpServletRequest.
   * @param reqOfficeCodewk RequestParam
   * @return String
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/get0101D00changeOfficeCode", method = RequestMethod.POST)
  @ResponseBody
  public String get0101D00changeOfficeCode(
      HttpServletRequest request,
      @Valid FormGet0101d00 formGet0101d00,
      @RequestParam("officeCode") String reqOfficeCodewk,
      @RequestParam("haitaDate") String haitaDate,
      @RequestParam("haitaTime") String haitaTime)
          throws JsonProcessingException {

    // store lasted haita
    Map<String, Object> map = Util.getContentsFromSession(request);
    String userCode = String.valueOf(map.get(CommonConst.LOGIN_USER_CODE));
    get0101D00Service.setHaitaInsuserid(userCode);
    get0101D00Service.setHaitaUpduserid(userCode);

    // Json data store
    Map<String, String> returnMap = new HashMap<String, String>();
    returnMap.put("haitaDate", get0101D00Service.getHaitaInsymd());
    returnMap.put("haitaTime", get0101D00Service.getHaitaInstime());
    returnMap.put("isEnableBtnConfirm", "true");
    // 変数更新
    String officeCodewk;
    if (null == reqOfficeCodewk || reqOfficeCodewk.trim().compareToIgnoreCase(
        "") == 0) {
      officeCodewk = "";
    } else {
      officeCodewk = reqOfficeCodewk;
    }
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    // json return string
    String jsonData = "";

    if (officeCodewk.equalsIgnoreCase("")) {
      returnMap.put("lblConfirmAccMonth", "");
      returnMap.put("lblProcStartTime", "");
      returnMap.put("lblProcEndTime", "");
      returnMap.put("lblCurrAccMonth", "");
      jsonData = ow.writeValueAsString(returnMap);
      return jsonData;
    }
    // query Store
    TblGet01JigConfirm tblGet01JigConfirm = null;
    GetDeterminationInfoGet0101d00 determinationInfo = new GetDeterminationInfoGet0101d00();
    // 月次確定情報取得
    // 2-1 事業所月次確定情報から月次確定情報を取得する取得
    tblGet01JigConfirm = get0101D00Service.getCommonDao().getGetJigMapper()
        .selectByPrimaryKey(officeCodewk);

    // (1) 該当データが存在しない場合、エラーとする
    if (tblGet01JigConfirm == null) {
      returnMap.put("Error", getErrorString_2_2_4_2());
      returnMap.put("isEnableBtnConfirm", "false");
      returnMap.put("lblConfirmAccMonth", "");
      returnMap.put("lblProcStartTime", "");
      returnMap.put("lblProcEndTime", "");
      returnMap.put("lblCurrAccMonth", "");
      jsonData = ow.writeValueAsString(returnMap);
      return jsonData;

      // (2) GJC.月次確定フラグ = '1'（確定）の場合、エラーとする
    } else if (tblGet01JigConfirm.getGetsujiFlg().compareToIgnoreCase(
        "1") == 0) {
      returnMap.put("isEnableBtnConfirm", "false");
      returnMap.put("GetsujiFlg", "true");
      returnMap.put("Error", getErrorString_2_2_4_4());
      jsonData = ow.writeValueAsString(returnMap);
      return jsonData;

      // (3) 該当データが存在する場合、取得項目を変数にセットする
    } else {
      determinationInfo.setMonthlyFixwk(tblGet01JigConfirm.getDetermMon());
      determinationInfo.setMonthlyFixFlagwk(tblGet01JigConfirm.getGetsujiFlg());
      determinationInfo.setProcessStartDateTimewk(tblGet01JigConfirm
          .getStartDatetime());
      determinationInfo.setProcessEndDateTimewk(tblGet01JigConfirm
          .getEndDatetime());
      determinationInfo.setThisMonthFixwk(tblGet01JigConfirm.getPresentMon());
    }

    // 画面項目に変数の値をセットする

    String lblConfirmAccMonth = determinationInfo.getMonthlyFixwk().substring(0,
        4) + "/"
        + determinationInfo.getMonthlyFixwk().substring(4,
            determinationInfo.getMonthlyFixwk().length());
    formGet0101d00.setLblConfirmAccMonth(lblConfirmAccMonth);
    returnMap.put("lblConfirmAccMonth", lblConfirmAccMonth);
    String lblProcStartTime = determinationInfo.getProcessStartDateTimewk()
        .substring(0, 4) + "/"
        + determinationInfo.getProcessStartDateTimewk().substring(4, 6) + "/"
        + determinationInfo.getProcessStartDateTimewk().substring(6, 8) + " "
        + determinationInfo.getProcessStartDateTimewk().substring(8, 10) + ":"
        + determinationInfo.getProcessStartDateTimewk().substring(10, 12);
    formGet0101d00.setLblProcStartTime(lblProcStartTime);
    returnMap.put("lblProcStartTime", lblProcStartTime);
    String lblProcEndTime = determinationInfo.getProcessEndDateTimewk()
        .substring(0, 4) + "/"
        + determinationInfo.getProcessEndDateTimewk().substring(4, 6) + "/"
        + determinationInfo.getProcessEndDateTimewk().substring(6, 8) + " "
        + determinationInfo.getProcessEndDateTimewk().substring(8, 10) + ":"
        + determinationInfo.getProcessEndDateTimewk().substring(10, 12);
    formGet0101d00.setLblProcEndTime(lblProcEndTime);
    returnMap.put("lblProcEndTime", lblProcEndTime);
    String lblCurrAccMonth = determinationInfo.getThisMonthFixwk().substring(0,
        4) + "/"
        + determinationInfo.getThisMonthFixwk().substring(4, determinationInfo
            .getThisMonthFixwk().length());
    formGet0101d00.setLblCurrAccMonth(lblCurrAccMonth);
    returnMap.put("lblCurrAccMonth", lblCurrAccMonth);
    jsonData = ow.writeValueAsString(returnMap);

    // 3-1 (1) [変数]セッション.ログイン事業所コード <>
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("Office_Code", determinationInfo.getOfficeCodewk());
    params.put("Bill_Date", determinationInfo.getThisMonthFixwk());
    params.put("Bill_Flg_or1", "0");
    params.put("Bill_Flg_or2", "9");
    params.put("Sts_Code", "1");
    List<GetHeaderUirageGet0101d00> lstHeaderUri = get0101D00Service
        .getGet0101D00Dao().getGet0101D00Mapper()
        .getHeaderUirageHeader(params);

    // (1-1) 該当データが存在していた場合、エラーとする
    if (null != lstHeaderUri && lstHeaderUri.size() > 0) {
      returnMap.put("isEnableBtnConfirm", "false");
      returnMap.put("Error", getErrorString_2_2_4_3());
      jsonData = ow.writeValueAsString(returnMap);
      return jsonData;

      // (1-2) 該当データが存在しない場合、[画面]確定ボタンを活性化する
    } else {
      returnMap.put("isEnableBtnConfirm", "true");
    }
    // [画面]確定ボタンへフォーカスをセットする
    returnMap.put("isSuccess", "true");
    returnMap.put("setFocus", "#btnConfirm");
    jsonData = ow.writeValueAsString(returnMap);
    return jsonData;
  }

  /**
   * 事業所が変更された場合の処理。ログインユーザ権限がシステム管理者権限の場合のみ発生
   * 
   * @param request HttpServletRequest
   * @param reqBusinessDate String
   * @param reqThisMonthFixwk String
   * @param regOfficeCodewk String
   * @return String
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/get0101D00confirm", method = RequestMethod.POST)
  @ResponseBody
  public String get0101D00confirm(HttpServletRequest request,
      @Valid FormGet0101d00 formGet0101d00,
      @RequestParam("businessDate") String reqBusinessDate,
      @RequestParam("thisMonthFixWK") String reqThisMonthFixwk,
      @RequestParam("officeCode") String regOfficeCodewk,
      @RequestParam("haitaDate") String haitaDate,
      @RequestParam("haitaTime") String haitaTime)
          throws JsonProcessingException {

    // store lasted haita
    Map<String, Object> map = Util.getContentsFromSession(request);
    String userCode = String.valueOf(map.get("UserCode"));
    get0101D00Service.setHaitaInsuserid(userCode);
    get0101D00Service.setHaitaUpduserid(userCode);
    // json writer
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    Map<String, String> returnMap = new HashMap<String, String>();

    // restore original string
    if (reqThisMonthFixwk.length() >= 7) {
      reqThisMonthFixwk = reqThisMonthFixwk.substring(0, 4) + reqThisMonthFixwk
          .substring(5, 7);
    }

    // [変数]事業所コードWK = '' の場合、エラーとする, 5-2-1 エラー処理①へ
    if (regOfficeCodewk.trim().compareTo("") == 0) {
      // 1-1
      returnMap.put("isEnableBtnConfirm", "true");
      returnMap.put("Error", getErrorString_3_5_2_1());
      // [画面]確定ボタンへフォーカスをセットする
      returnMap.put("setFocus", "#ddlJigyouSho");
      String jsonData = ow.writeValueAsString(returnMap);
      return jsonData;
    }

    // 画面の項目の現在値を作成する
    returnFormState(returnMap, formGet0101d00);
    // 変数を初期化する
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String currentProcessStartDateTimewk = sdf.format(date);
    // String currentProcessEndDateTimewk = "";

    // 2 売掛・未収ヘッダー情報更新
    int imonthOfThisMonthFixwk = Integer.parseInt(reqThisMonthFixwk.substring(4,
        6));

    // 次月取得
    String nextmonthwk;
    int newMonth = imonthOfThisMonthFixwk + 1;
    if (imonthOfThisMonthFixwk == 12) {
      int currentYear = Integer.parseInt(reqThisMonthFixwk.substring(0, 4)) + 1;
      nextmonthwk = String.valueOf(currentYear) + "01";
    } else if (imonthOfThisMonthFixwk == 9) {
      nextmonthwk = reqThisMonthFixwk.substring(0, 4) + "10";
    } else if (imonthOfThisMonthFixwk < 10) {
      nextmonthwk = reqThisMonthFixwk.substring(0, 4) + "0" + String.valueOf(
          (newMonth));
    } else {
      nextmonthwk = reqThisMonthFixwk.substring(0, 4) + String.valueOf(
          (newMonth));
    }
    String reqBusinessDatewk = reqBusinessDate;
    // 売掛・未収ヘッダ（当月）取得, 月次処理対象となった事業所の売掛・未収ヘッダー情報を全て取得する
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("jigyocodecond", regOfficeCodewk);
    params.put("accountmonthcond", reqThisMonthFixwk);
    params.put("businessDateWK", reqBusinessDatewk);
    Get0101D00Dao get0101D00Dao = get0101D00Service.getGet0101D00Dao();
    Get0101D00Mapper mapperGet0101D00 = get0101D00Dao.getGet0101D00Mapper();
    GetNextMonthAccountGet0101d00 nextMonthAccount = mapperGet0101D00
        .getNextMonthAccountGet0101d00(params);

    // 2-1-1 (1) 該当データが存在しない場合、エラーとする, ＜5-3-1 エラー処理②へ＞
    if (null == nextMonthAccount) {
      // [画面]確定ボタンへフォーカスをセットする
      returnMap.put("isEnableBtnConfirm", "true");
      returnMap.put("setFocus", "#btnConfirm");
      returnMap.put("Error", getErrorString_3_5_3_1());
      String jsonData = ow.writeValueAsString(returnMap);
      return jsonData;
    }

    // (2) 該当データが存在する場合、取得項目を変数にセットする（取得件数分、2-1-2～2-4-1を繰返す）
    params = new HashMap<String, Object>();
    String officeCodewk = nextMonthAccount.getJigyocode();
    String chainCodewk = nextMonthAccount.getChaincode();
    String chainBranchwk = nextMonthAccount.getChainidx();
    String curMishuTaxwk = nextMonthAccount.getCurmishutax();
    // 2-2 取得した売掛・未収ヘッダー情報に対する次月分のヘッダ情報を取得する
    params.put("Chain_IdxWK", chainBranchwk);
    params.put("Jigyo_CodeWK", officeCodewk);
    params.put("Chain_CodeWK", chainCodewk);

    // (2) 該当データが存在する場合、取得項目を変数にセットする（取得件数分、2-1-2～2-4-1を繰返す）
    String customerCodewk = nextMonthAccount.getCustcode();
    String shopCodewk = nextMonthAccount.getShopcode();
    String currentUrikakewk = nextMonthAccount.getCururikakettl();
    String currentUrikakeTaxwk = nextMonthAccount.getCururikaketax();
    String curMishuTtlwk = nextMonthAccount.getCurmishuttl();
    // 2-2 取得した売掛・未収ヘッダー情報に対する次月分のヘッダ情報を取得する
    params.put("Cust_CodeWK", customerCodewk);
    params.put("Shop_CodeWK", shopCodewk);
    params.put("businessDateWK", reqBusinessDatewk);
    params.put("nextmonthWK", nextmonthwk);

    GetNextMonthAccountGet0101d00 nextMonthAccountReceivable = get0101D00Service
        .getGet0101D00Dao()
        .getGet0101D00Mapper().getNextMonthAccountReceivableGet0101d00(params);

    // 2-3 (1) 該当データが存在しない場合、次月分の売掛・未収ヘッダ情報を登録する（Select - Insert）
    if (nextMonthAccountReceivable == null) {
      TblSei01UrkMshHeadExample tblSei01UrkMshHeadExample = new TblSei01UrkMshHeadExample();
      tblSei01UrkMshHeadExample.createCriteria().andJigyoCodeEqualTo(
          officeCodewk);
      tblSei01UrkMshHeadExample.createCriteria().andChainCodeEqualTo(Short
          .parseShort(chainCodewk));
      tblSei01UrkMshHeadExample.createCriteria().andChainIdxEqualTo(Short
          .parseShort(chainBranchwk));
      tblSei01UrkMshHeadExample.createCriteria().andCustCodeEqualTo(
          customerCodewk);
      tblSei01UrkMshHeadExample.createCriteria().andShopCodeEqualTo(shopCodewk);
      tblSei01UrkMshHeadExample.createCriteria().andAccountMonthEqualTo(
          reqThisMonthFixwk);
      tblSei01UrkMshHeadExample.createCriteria().andCloseDateLike(
          reqBusinessDatewk);
      List<TblSei01UrkMshHead> lstTblSei01UrkMshHead = null;
      lstTblSei01UrkMshHead = get0101D00Service.getTblSei01UrkMshHeadMapper()
          .selectByExample(tblSei01UrkMshHeadExample);
      for (TblSei01UrkMshHead tblSei01UrkMshHead : lstTblSei01UrkMshHead) {
        tblSei01UrkMshHead.setAccountMonth(nextmonthwk);
        tblSei01UrkMshHead.setMonUriageTtl(0);
        tblSei01UrkMshHead.setMonUriageTax(0);
        tblSei01UrkMshHead.setMonHenpinTtl(0);
        tblSei01UrkMshHead.setMonHenpinTax(0);
        tblSei01UrkMshHead.setMonTaxAdj(0);
        tblSei01UrkMshHead.setMonShikiriTtl(0);
        tblSei01UrkMshHead.setMonUnyukinTtl(0);
        tblSei01UrkMshHead.setMonShohinTtl(0);
        tblSei01UrkMshHead.setMonShohinDsc(0);
        tblSei01UrkMshHead.setMonMishuTtl(0);
        tblSei01UrkMshHead.setMonMnyukinTax(0);

        // new haita data
        tblSei01UrkMshHead.setInsPgid(get0101D00Service.getHaitaInspgid());
        tblSei01UrkMshHead.setInsTime(get0101D00Service.getHaitaInstime());
        tblSei01UrkMshHead.setInsUserid(get0101D00Service.getHaitaInsuserid());
        tblSei01UrkMshHead.setInsYmd(get0101D00Service.getHaitaInsymd());
        tblSei01UrkMshHead.setUpdPgid(get0101D00Service.getHaitaUpdpgid());
        tblSei01UrkMshHead.setUpdTime(get0101D00Service.getHaitaUpdtime());
        tblSei01UrkMshHead.setUpdUserid(get0101D00Service.getHaitaUpduserid());
        tblSei01UrkMshHead.setUpdYmd(get0101D00Service.getHaitaUpdymd());

        // transaction
        status = txManager.getTransaction(new DefaultTransactionDefinition());
        try {
          get0101D00Service.getTblSei01UrkMshHeadMapper().insert(
              tblSei01UrkMshHead);
        } catch (MyBatisSystemException me) {
          // 2-4-3 次月分の売掛・未収ヘッダー情報の更新が異常終了した場合、エラーとする。
          txManager.rollback(status);
          logger.error(me.getMessage());
          // returnMap = new HashMap<String, String>();
          // [画面]確定ボタンへフォーカスをセットする
          returnMap.put("isEnableBtnConfirm", "true");
          returnMap.put("setFocus", "#btnConfirm");
          returnMap.put("Error", getErrorString_3_5_5_1());
          String jsonData = ow.writeValueAsString(returnMap);
          return jsonData;
        } catch (Exception e) {
          // 2-4-3 次月分の売掛・未収ヘッダー情報の更新が異常終了した場合、エラーとする。
          txManager.rollback(status);
          logger.error(e.getMessage());
          returnMap = new HashMap<String, String>();
          // [画面]確定ボタンへフォーカスをセットする
          returnMap.put("isEnableBtnConfirm", "true");
          returnMap.put("setFocus", "#btnConfirm");
          returnMap.put("Error", getErrorString_3_5_5_1());
          String jsonData = ow.writeValueAsString(returnMap);
          return jsonData;
        }
        txManager.commit(status);

        break;
      }

      // 2-4 (2) 該当データが存在する場合、次月分の売掛・未収ヘッダ情報を更新する
    } else {

      String urikakewk = currentUrikakewk + nextMonthAccountReceivable
          .getCururikakettl();
      String urikakeTaxwk = currentUrikakeTaxwk + nextMonthAccountReceivable
          .getCururikaketax();
      String mishuTtlwk = curMishuTtlwk + nextMonthAccountReceivable
          .getCurmishuttl();
      String mishuTaxwk = curMishuTaxwk + nextMonthAccountReceivable
          .getCurmishutax();
      TblSei01UrkMshHead data = new TblSei01UrkMshHead();

      data.setBgnUrikakeTtl(Integer.parseInt(urikakewk));
      data.setBgnUrikakeTax(Integer.parseInt(urikakeTaxwk));
      data.setBgnMishuTtl(Integer.parseInt(mishuTtlwk));
      data.setBgnMishuTax(Integer.parseInt(mishuTaxwk));
      data.setCurUrikakeTtl(Long.parseLong(currentUrikakewk));
      data.setCurUrikakeTax(Integer.parseInt(currentUrikakeTaxwk));
      data.setCurMishuTtl(Long.parseLong(curMishuTtlwk));
      data.setCurMishuTax(Integer.parseInt(curMishuTaxwk));

      // new haita data
      data.setInsPgid(get0101D00Service.getHaitaInspgid());
      data.setInsTime(get0101D00Service.getHaitaInstime());
      data.setInsUserid(get0101D00Service.getHaitaInsuserid());
      data.setInsYmd(get0101D00Service.getHaitaInsymd());
      data.setUpdPgid(get0101D00Service.getHaitaUpdpgid());
      data.setUpdTime(get0101D00Service.getHaitaUpdtime());
      data.setUpdUserid(get0101D00Service.getHaitaUpduserid());
      data.setUpdYmd(get0101D00Service.getHaitaUpdymd());

      TblSei01UrkMshHeadExample exam = new TblSei01UrkMshHeadExample();
      exam.createCriteria().andJigyoCodeEqualTo(officeCodewk);
      exam.createCriteria().andChainCodeEqualTo(Short.parseShort(chainCodewk));
      exam.createCriteria().andChainIdxEqualTo(Short.parseShort(chainBranchwk));
      exam.createCriteria().andCustCodeEqualTo(customerCodewk);
      exam.createCriteria().andShopCodeEqualTo(shopCodewk);
      exam.createCriteria().andAccountMonthEqualTo(nextmonthwk);
      exam.createCriteria().andCloseDateLike(reqBusinessDatewk);

      TblSei01UrkMshHeadKey key = new TblSei01UrkMshHeadKey();
      key.setAccountMonth(data.getAccountMonth());
      key.setChainCode(data.getChainCode());
      key.setChainIdx(data.getChainIdx());
      key.setCustCode(data.getCustCode());
      key.setJigyoCode(data.getJigyoCode());
      key.setShopCode(data.getShopCode());
      if (get0101D00Service.checkHaitaTblSei01UrkMshHeadMapper(key, haitaDate,
          haitaTime)) {
        status = txManager.getTransaction(new DefaultTransactionDefinition());
        try {
          get0101D00Service.updateUrikakeMishu(data, exam);
        } catch (MyBatisSystemException me) {
          // 2-4-3 次月分の売掛・未収ヘッダー情報の更新が異常終了した場合、エラーとする。
          txManager.rollback(status);
          logger.error(me.getMessage());
          returnMap.put("setFocus", "#btnConfirm");
          returnMap.put("isEnableBtnConfirm", "true");
          returnMap.put("Error", getErrorString_3_5_6_1());
          String jsonData = ow.writeValueAsString(returnMap);
          return jsonData;
        } catch (Exception e) {
          txManager.rollback(status);
          returnMap.put("setFocus", "#btnConfirm");
          returnMap.put("isEnableBtnConfirm", "true");
          returnMap.put("Error", getErrorString_3_5_6_1());
          String jsonData = ow.writeValueAsString(returnMap);
          return jsonData;
        }
        txManager.commit(status);
      } else {
        returnMap.put("Error", getHaitaErrorString());
        String jsonData = ow.writeValueAsString(returnMap);
        return jsonData;
      }
    }
    // end 2

    // 3-1 変数を初期化する
    String systemDateTime = sdf.format(date);
    String currentProcessEndDatewk = systemDateTime;

    // 4-1 事業所月次確定情報レコード更新
    TblGet01JigConfirm tblGet01JigConfirm = new TblGet01JigConfirm();
    String presentMon = "";
    String determMon = "";
    String tempMonthFormat = reqThisMonthFixwk.substring(0, 4) + ""
        + reqThisMonthFixwk.substring(4, 6);
    int thisMonthFixwkint = Integer.parseInt(tempMonthFormat.substring(0, 4));
    String month = tempMonthFormat.substring(4, tempMonthFormat.length());
    int newmonth = Integer.parseInt(month) + 1;
    int newmonthW = Integer.parseInt(month);
    if (newmonthW == 12) {
      int yearNew = thisMonthFixwkint + 1;
      presentMon = String.valueOf(yearNew) + "01";
      determMon = tempMonthFormat;
    } else if (newmonthW == 9) {
      presentMon = tempMonthFormat.substring(0, 4) + "10";
      determMon = tempMonthFormat.substring(0, 4) + "09";
    } else if (newmonthW < 10 && newmonthW != 9) {
      determMon = tempMonthFormat.substring(0, 4) + "0" + String.valueOf(
          (newmonthW));
      presentMon = tempMonthFormat.substring(0, 4) + "0" + String.valueOf(
          (newmonth));
    } else {
      presentMon = tempMonthFormat.substring(0, 4) + String.valueOf((newmonth));
      determMon = tempMonthFormat.substring(0, 4) + String.valueOf((newmonthW));
    }
    if (get0101D00Service.checkHaitaTblGet01JigConfirm(officeCodewk, haitaDate,
        haitaTime)) {
      // new haita data
      tblGet01JigConfirm.setInsPgid(get0101D00Service.getHaitaInspgid());
      tblGet01JigConfirm.setInsTime(get0101D00Service.getHaitaInstime());
      tblGet01JigConfirm.setInsUserid(get0101D00Service.getHaitaInsuserid());
      tblGet01JigConfirm.setInsYmd(get0101D00Service.getHaitaInsymd());
      tblGet01JigConfirm.setUpdPgid(get0101D00Service.getHaitaUpdpgid());
      tblGet01JigConfirm.setUpdTime(get0101D00Service.getHaitaUpdtime());
      tblGet01JigConfirm.setUpdUserid(get0101D00Service.getHaitaUpduserid());
      tblGet01JigConfirm.setUpdYmd(get0101D00Service.getHaitaUpdymd());

      tblGet01JigConfirm.setDetermMon(determMon);
      tblGet01JigConfirm.setPresentMon(presentMon);
      tblGet01JigConfirm.setStartDatetime(currentProcessStartDateTimewk);
      tblGet01JigConfirm.setEndDatetime(currentProcessEndDatewk);
      tblGet01JigConfirm.setGetsujiFlg("1");
      TblGet01JigConfirmExample tblGet01JigConfirmExample = new TblGet01JigConfirmExample();
      tblGet01JigConfirmExample.createCriteria().andJigyoCodeEqualTo(
          officeCodewk);

      status = txManager.getTransaction(new DefaultTransactionDefinition());
      try {

        get0101D00Service.getTblGet01JigConfirmMapper()
            .updateByExampleSelective(
                tblGet01JigConfirm, tblGet01JigConfirmExample);
      } catch (MyBatisSystemException e) {
        txManager.rollback(status);
        logger.error(e.getMessage());
        // 売上明細情報の更新が異常終了した場合、エラーとする。
        returnMap.put("Error", getErrorString_3_5_7_1());
        // [画面]確定ボタンへフォーカスをセットする
        returnMap.put("setFocus", "#btnConfirm");
        returnMap.put("isEnableBtnConfirm", "true");
        String jsonData = ow.writeValueAsString(returnMap);
        return jsonData;
      } catch (Exception e) {
        txManager.rollback(status);
        txManager.rollback(status);
        logger.error(e.getMessage());
        // 売上明細情報の更新が異常終了した場合、エラーとする。
        returnMap.put("Error", getErrorString_3_5_7_1());
        // [画面]確定ボタンへフォーカスをセットする
        returnMap.put("setFocus", "#btnConfirm");
        returnMap.put("isEnableBtnConfirm", "true");
      }
      txManager.commit(status);
    } else {
      // 売上明細情報の更新が異常終了した場合、エラーとする。
      // returnMap = new HashMap<String, String>();
      returnMap.put("Error", getHaitaErrorString());
      String jsonData = ow.writeValueAsString(returnMap);
      return jsonData;
    }
    returnMap = new HashMap<String, String>();

    // 5-1-2 [画面]確定ボタンを非活性化する
    returnMap.put("isEnableBtnConfirm", "false");

    // 5-2-2 [画面]事業所へフォーカスをセットする
    returnMap.put("setFocus", "#ddlJigyouSho");

    // 5-1-3 画面項目の表示内容を変更する
    returnMap.put("lblConfirmAccMonth", determMon.substring(0, 4) + "/"
        + determMon.substring(4, determMon.length()));
    returnMap.put("lblProcStartTime", currentProcessStartDateTimewk.substring(0,
        4) + "/"
        + currentProcessStartDateTimewk.substring(4, 6) + "/"
        + currentProcessStartDateTimewk.substring(6, 8) + " "
        + currentProcessStartDateTimewk.substring(8, 10) + ":"
        + currentProcessStartDateTimewk.substring(10, 12));
    returnMap.put("lblProcEndTime", currentProcessEndDatewk.substring(0, 4)
        + "/"
        + currentProcessEndDatewk.substring(4, 6) + "/"
        + currentProcessEndDatewk.substring(6, 8) + " "
        + currentProcessEndDatewk.substring(8, 10) + ":"
        + currentProcessEndDatewk.substring(10, 12));
    returnMap.put("lblCurrAccMonth", presentMon.substring(0, 4) + "/"
        + presentMon.substring(4, presentMon.length()));
    // 終了メッセージを画面に表示する
    returnMap.put("isSuccess", "true");
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("月次確定");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM002-I", paramMess));
    returnMap.put("Info", errorMessage.getMessageContent());
    String jsonData = ow.writeValueAsString(returnMap);
    return jsonData;
  }

  /**
   * フォームの現在の状態を初期する.
   * @param returnMap Map
   * @param formGet0101d00 FormGet0101d00
   */
  private void returnFormState(Map<String, String> returnMap,
      FormGet0101d00 formGet0101d00) {
    returnMap.put("lblConfirmAccMonth", formGet0101d00.getLblConfirmAccMonth());
    returnMap.put("lblProcStartTime", formGet0101d00.getLblProcStartTime());
    returnMap.put("lblProcEndTime", formGet0101d00.getLblProcEndTime());
    returnMap.put("lblCurrAccMonth", formGet0101d00.getLblCurrAccMonth());
  }

  /**
   * Return haita error string
   * 
   * @return String
   */
  private String getHaitaErrorString() {
    ErrorMessages errorMessage = new ErrorMessages();
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM031-E", null));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 5-5-1, sheet3 in String
   * 
   * @return String
   */
  private String getErrorString_3_5_5_1() {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("売掛・未収ヘッダー月別情報");
    paramMess.add("登録");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM023-E", paramMess));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 5-7-1, sheet3 in String
   * 
   * @return String
   */
  private String getErrorString_3_5_7_1() {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("事業所月次確定データ");
    paramMess.add("更新");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM023-E", paramMess));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 5-6-1, sheet3 in String
   * 
   * @return String
   */
  private String getErrorString_3_5_6_1() {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("売掛・未収ヘッダー月別情報");
    paramMess.add("更新");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM023-E", paramMess));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 5-2-1, sheet3 in String
   * 
   * @return String
   */
  private String getErrorString_3_5_2_1() {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("事業所");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", paramMess));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 5-3-1, sheet3 in String
   * 
   * @return String
   */
  private String getErrorString_3_5_3_1() {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("売掛・未収ヘッダー情報");
    paramMess.add("事業所に対する得意先・店舗集計の売掛・未収情報");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 2-4-2, sheet2 in String
   * 
   * @return String
   */
  private String getErrorString_2_2_4_2() {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("事業所月次確定情報");
    paramMess.add("事業所月次情報");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 2-4-3, sheet2 in String
   * 
   * @return String
   */
  private String getErrorString_2_2_4_3() {
    ErrorMessages errorMessage = new ErrorMessages();
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "GET001-E", null));
    return errorMessage.getMessageContent();
  }

  /**
   * Return error 2-4-4, sheet2 in String
   * 
   * @return String
   */
  private String getErrorString_2_2_4_4() {
    ErrorMessages errorMessage = new ErrorMessages();
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "GET001-E", null));
    return errorMessage.getMessageContent();
  }

}
