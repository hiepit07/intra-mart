/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl
 * ファイル名:Com0103d01Controller.java
 *
 * <p>作成者:ABV)TramChu
 * 作成日:2015/09/03
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/03 1.00 ABV)TramChu 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.Com01Job;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.FormCom0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ReprocessJobLst;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0103d01Service;

/**
 * URLにて指定されたサービスを呼び出すコントローラークラス
 *
 * @author TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/com/COM01-03D01")
public class Com0103d01Controller extends AbsController {

  @Autowired
  @Qualifier("com0103d01Service")
  private Com0103d01Service com0103d01Service;
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理
   * 
   * @param request request
   * @param model A holder for model attributes
   * @param formCom0103d01 form display
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormCom0103d01 formCom0103d01) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.COM0103D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    // ビュー画面を呼ぶ
    String strScreen = "com/com0103d01";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // default メッセージを取得する。
    com0103d01Service.getDefaultMess(model);

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get("SysAdminFlg"));
    formCom0103d01.setSysAdminFlag(sysAdminFlag);

    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get("JigyoshoCode"));
    formCom0103d01.setLoginJigyouShoCode(loginJigyouShoCode);
    // 事業所情報取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0103d01Service.getCommonDao(),
        null, null, readPropertiesFileService);
    int searchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0103d01.setSearchMax(searchMax);
    if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
      if (!com0103d01Service.setDataDiaJigyo(formCom0103d01, model)) {
        // エラーメッセージを画面に表示する
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタ");
        paramMess.add("事業所情報");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }
    }
    // 処理ジョブ設定
    if (!com0103d01Service.setDataDiaJobExec(formCom0103d01, model)) {
      // メッセージを表示
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("ジョブ種別情報");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      return strScreen;
    }
    String loginjgymei = "";
    loginjgymei = String.valueOf(sessionData.get("jgymei"));
    String officeCodeWk = "";
    // 画面表示
    if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // [画面]事業所を非表示とする
      model.addAttribute("displayDdlDiaJigyo", "display_none");
      officeCodeWk = loginJigyouShoCode;
    } else if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
      // [画面]事業所をコンボボックス（選択可能）とし、事業所情報リストの値
      formCom0103d01.setDdlDiaJigyo(loginJigyouShoCode);
      officeCodeWk = loginJigyouShoCode;
    }
    formCom0103d01.setOfficeCodeWk(officeCodeWk);
    // 処理種別コンボボックス
    com0103d01Service.setDataDiaTypExec(formCom0103d01, model);
    // 初期表示
    String loginUserId = String.valueOf(sessionData.get("UserCode"));
    com0103d01Service.initDataCallCondition(formCom0103d01, model, loginjgymei,
        loginUserId, loginJigyouShoCode);
    // 表示するJSPファイル名を返却
    return strScreen;
  }

  /**
   * データ呼び出し条件を入力し「表示」ボタン・クリック
   * 
   * @param formCom0103d01 form display
   * @return list Com01Job
   * @throws JsonProcessingException ER
   */
  @RequestMapping(value = "/getSearchResult", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResult(FormCom0103d01 formCom0103d01) throws JsonProcessingException {
    String jsonData;
    jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    ArrayList<Com01Job> com01JobLst = new ArrayList<Com01Job>();
    com01JobLst = new ArrayList<Com01Job>();
    // 定時バッチ担当者コード取得
    String batchUserCode = readPropertiesFileService.getSetting(
        "BAT_USER_CODE");
    String processStartDateWk = "";
    processStartDateWk = formCom0103d01.getTxtDiaDateFromExec()
        .replaceAll("/", "");
    String processStartTimeWk = "";
    processStartTimeWk = formCom0103d01.getTxtDiaTimeFromExec();
    String processEndDateWk = "";
    processEndDateWk = formCom0103d01.getTxtDiaDateToExec().replaceAll(
        "/", "");
    String processEndTimeWk = "";
    processEndTimeWk = formCom0103d01.getTxtDiaTimeToExec();
    String processTypeWk = "";
    processTypeWk = formCom0103d01.getDdlDiaTypExec();
    String processJobWk = "";
    processJobWk = formCom0103d01.getDdlDiaJobExec();
    String userCodeWk = "";
    userCodeWk = formCom0103d01.getTxtDiaUserCodeExec();
    boolean sendFlag = false;
    sendFlag = formCom0103d01.getChkDiaRsJob();
    boolean concentrateFlag = false;
    concentrateFlag = formCom0103d01.getChkDiaSsJob();
    boolean abnormalFlag = false;
    abnormalFlag = formCom0103d01.getChkDiaIjJob();
    // 処理日時生成
    String processStartDateTimeWk = "";
    processStartDateTimeWk = processStartDateWk + processStartTimeWk + "00";
    String processEndDateTimeWk = "";
    processEndDateTimeWk = processEndDateWk + processEndTimeWk + "59";
    // レコード取得
    String userNameWk = "";
    userNameWk = com0103d01Service.getUserNm(userCodeWk);
    String searchMax = "";
    searchMax = "TOP " + String.valueOf(formCom0103d01.getSearchMax() + 1);
    // レコード取得
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("searchMax", searchMax);
    params.put("processStartDateTimeWK", processStartDateTimeWk);
    params.put("processEndDateTimeWK", processEndDateTimeWk);
    if (!processTypeWk.equals("0")) {
      params.put("processTypeWK", processTypeWk);
    }   
    params.put("processJobWK", processJobWk);
    if (!userCodeWk.equals("")) {
      params.put("userCodeWK", userCodeWk);
    }
    String officeCodeWk = "";
    officeCodeWk = formCom0103d01.getOfficeCodeWk();
    params.put("officeCodeWk", officeCodeWk);
    params.put("concentrateFlag", concentrateFlag);
    params.put("sendFlag", sendFlag);
    params.put("abnormalFlag", abnormalFlag);
    com01JobLst = com0103d01Service.getCom1JobExec(params);
    int intSearchMax = formCom0103d01.getSearchMax();
    if (com01JobLst != null && com01JobLst.size() > 0) {
      for (int i = 0; i < com01JobLst.size(); i++) {
        if (com01JobLst.get(i).getJobTyp().equals(CommonConst.JOBTYP)) {
          com01JobLst.get(i).setUserNmExec(batchUserCode);
        }
      }
    }
    if (com01JobLst != null && com01JobLst.size() <= 0) {
      com01JobLst = new ArrayList<Com01Job>();
      Com01Job error = new Com01Job();
      paramMess = new ArrayList<String>();
      paramMess.add("");
      error.setType("body");
      error.setMessageEr(readPropertiesFileService.getMessage("COM025-E",
          paramMess));
      com01JobLst.add(error);
    } else if (com01JobLst != null && com01JobLst
        .size() >= intSearchMax) {
      Com01Job error = new Com01Job();
      paramMess = new ArrayList<String>();
      paramMess.add(String.valueOf(intSearchMax));
      error.setType("searchMax");
      error.setMessageEr(readPropertiesFileService.getMessage("COM005-W",
          paramMess));
      com01JobLst.add(error);
    }
    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    com0103d01Service.setHaitaDate(formCom0103d01);
    Com01Job addHaita = new Com01Job();
    addHaita.setStartDate(formCom0103d01.getHaitaDate());
    addHaita.setType("haita");
    addHaita.setStartTime(formCom0103d01.getHaitaTime());
    Com01Job com01Job = new Com01Job();
    com01Job.setType("userNameWk");
    com01Job.setUserNmExec(userNameWk);
    if (com01JobLst != null) {
      com01JobLst.add(com01Job);
      com01JobLst.add(addHaita);
    } else {
      com01JobLst = new ArrayList<Com01Job>();
      com01JobLst.add(com01Job);
    }
    jsonData = com0103d01Service.convertJson(com01JobLst);
    return jsonData;
  }

  /**
   * 得意先一覧情報を取得する
   * 
   * @param searchCondition：検索条件オブジェクト
   * @return Mst0102d01CustomerListオブジェクト
   * @throws IOException ER
   */
  @RequestMapping(value = "/reprocessJob", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ArrayList<Com01Job> getCustomerList(
      @RequestBody ReprocessJobLst reprocessJobLst) throws IOException {
    String result = "";
    result = com0103d01Service.getReprocessJobLst(reprocessJobLst);
    ArrayList<Com01Job> com01JobLst = null;
    com01JobLst = new ArrayList<Com01Job>();
    Com01Job error = new Com01Job();
    error.setType("msgWaring");
    error.setMessageEr(result);
    com01JobLst.add(error);
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    Com01Job addHaita = new Com01Job();
    addHaita.setStartDate(currentDate);
    addHaita.setType("haita");
    addHaita.setStartTime(currentTime);
    com01JobLst.add(addHaita);
    return com01JobLst;
  }
}
