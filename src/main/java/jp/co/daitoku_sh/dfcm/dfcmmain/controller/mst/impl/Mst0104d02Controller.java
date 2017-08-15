/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0104d02Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourse;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.CastData0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0104d02Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;

/**
 * コントローラクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-04D02/")
public class Mst0104d02Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0104d02Service")
  private Mst0104d02Service mst0104d02Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理.
   * @param model:モデル
   * @param request:httpリクエスト
   * @param formMst0104d02:フォーム
   * @param searchCondMst0104d01:一覧画面の情報
   * @param courseCode:コースコード
   * @param jigyoCode:事業所
   * @param viewMode:モード
   * @param haitaDate:排他日付
   * @param haitaTime:排他時間
   * @return ページ表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request,
      FormMst0104d02 formMst0104d02,
      @ModelAttribute("searchCondMst0104d01") SearchCondMst0104d01 searchCondMst0104d01,
      @ModelAttribute("courseCode") String courseCode,
      @ModelAttribute("jigyoCode") String jigyoCode,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0104D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // システム管理者フラグ値を取得する
    formMst0104d02.setSysAdminFlag(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG)));
    formMst0104d02.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
    formMst0104d02.setSelectJigyoCodeHidden(jigyoCode);
    formMst0104d02.setDdlJigyoCode(searchCondMst0104d01.getJigyoCode());
    // 設定情報ファイル）店舗コード_未指定
    String configShopCode = readPropertiesFileService.getSetting(
        "TEN_CODE_NONE");
    model.addAttribute("hiddenConfig", configShopCode);

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0104d02";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // default メッセージを取得する。
    mst0104d02Service.getDefaultMess(model);

    // 排他設定
    formMst0104d02.setHaitaDate(haitaDate);
    formMst0104d02.setHaitaTime(haitaTime);

    // 初期処理 一覧画面から値を取得する。
    mst0104d02Service.init(formMst0104d02, searchCondMst0104d01, viewMode);

    // (1-1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
    if (viewMode.equalsIgnoreCase("")) {
      // [変数]メッセージコード（COM026-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("画面表示モード");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM026-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通仕様 3-6適用
      formMst0104d02.setErrorControl("errorControl");
      return strScreen;
    }
    // モード設定
    model.addAttribute("modeView", formMst0104d02.getMode());

    // [入力]画面表示モード = '2'（照会） or '3'（訂正） or '4'（取消） の時のみ、後続の引数チェックを行う。
    if (viewMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE) || viewMode
        .equalsIgnoreCase(MstConst.TEISEI_MODE) || viewMode.equalsIgnoreCase(
            MstConst.TORIKESI_MODE)) {
      if (jigyoCode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通仕様 3-6適用
        formMst0104d02.setErrorControl("errorControl");
        return strScreen;
      }
      if (courseCode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通仕様 3-6適用
        formMst0104d02.setErrorControl("errorControl");
        return strScreen;
      }
    }

    MstCourse mstCourse = null;
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // [変数]コース情報格納クラス = [関数]コースマスタデータ取得（[入力]事業所コード、[入力]コースコード）
      mstCourse = mst0104d02Service.keyMstCourse(jigyoCode, courseCode);
      // [変数]コース情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
      if (mstCourse == null) {
        // [変数]メッセージコード（COM009-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("コースマスタ");
        paramMess.add("編集対象のコース");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通仕様 3-6適用
        formMst0104d02.setErrorControl("errorControl");
        return strScreen;
      }
    }

    // -----------------------------
    // 共通部品を使って、業務日付を取得する
    // -----------------------------
    String bussinessDate;
    bussinessDate = "";

    CommonGetSystemCom sysCom = new CommonGetSystemCom(mst0104d02Service
        .getCommonDao(), null, null, readPropertiesFileService);

    bussinessDate = sysCom.getAplDate();

    if (bussinessDate == null || bussinessDate.equalsIgnoreCase("")) {
      // [変数]メッセージコード（COM015-E）
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      formMst0104d02.setErrorControl("errorControl");
      return strScreen;
    } else {
      // hidden設定
      formMst0104d02.setBusinessDate(Integer.parseInt(bussinessDate));
    }

    // [変数]事業所情報格納クラス ＝ NULL の場合、エラーとする
    if (!mst0104d02Service.setMstSJigyo(model, formMst0104d02)) {
      // [変数]メッセージコード（COM015-E）
      paramMess = new ArrayList<String>();
      paramMess.add("事業所マスタの取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      formMst0104d02.setErrorControl("errorControl");
      return strScreen;
    }

    // 汎用マスタから納品区分情報を取得する
    if (!mst0104d02Service.setDeliveryGen(model, formMst0104d02)) {
      // [変数]メッセージコード（COM009-E）
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("納品区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      formMst0104d02.setErrorControl("errorControl");
      return strScreen;
    }

    // [入力]事業所コード、[入力]コースコードにてコース情報を取得する
    mst0104d02Service.setView(model, viewMode, formMst0104d02, mstCourse);

    return strScreen;
  }

  /**
   * 戻るボタンを押した処理
   * @param rattrs:送信パラメーターの設定
   * @param formMst0104d02:フォーム
   * @return 画面表示
   */
  @RequestMapping(value = "proc", params = "Return", method = RequestMethod.POST)
  public String returnPage(RedirectAttributes rattrs,
      FormMst0104d02 formMst0104d02) {
    // [変数]検索条件保持クラス
    SearchCondMst0104d01 searchCondMst0104d01 = new SearchCondMst0104d01();
    searchCondMst0104d01.setJigyoCode(formMst0104d02.getForm1JigyoCode());
    searchCondMst0104d01.setCourseCode(formMst0104d02.getForm1CourseCode());
    searchCondMst0104d01.setCourseName(formMst0104d02.getForm1CourseName());
    searchCondMst0104d01.setHaisoKb(formMst0104d02.getForm1HaisoKb());
    searchCondMst0104d01.setCancelData(formMst0104d02.getForm1CancelData());
    rattrs.addFlashAttribute("searchCondMst0104d01", searchCondMst0104d01);

    return "redirect:/mst/MST01-04D00/";
  }

  /**
   * 入力エリアと納入先一覧を表示する
   * @param formMst0104d02:フォーム
   * @return message error
   */
  @RequestMapping(value = "/config", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0104d02 config(@Valid FormMst0104d02 formMst0104d02) {
    mst0104d02Service.checkExistHeader(formMst0104d02);
    return formMst0104d02;
  }

  /**
   * コースマスタ情報を再取得する
   * @param formMst0104d02:フォーム
   * @param jigyoCode:事業所
   * @param cosCode:コースコード
   * @return data MST_COURSE
   */
  @RequestMapping(value = "/getDataCourse", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0104d02 getDataCourse(@Valid FormMst0104d02 formMst0104d02,
      @RequestParam(value = "jigyoCode") String jigyoCode,
      @RequestParam(value = "cosCode") String cosCode) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    MstCourse mstCourse = new MstCourse();
    mstCourse = mst0104d02Service.keyMstCourse(jigyoCode, cosCode);
    if (mstCourse == null) {
      paramMess = new ArrayList<String>();
      paramMess.add("コースマスタ");
      paramMess.add("指定されたコース");
      formMst0104d02.setTxtMessage(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
    } else {
      mst0104d02Service.setViewDataClearFooter(formMst0104d02, mstCourse);
    }
    return formMst0104d02;
  }

  /**
   * 入力されたコース情報をDBへ登録及び更新を行う
   * @param castData0104d02 :class save information 
   * @param request:httpリクエスト
   * @return 画面表示
   * @throws Exception  エラー画面
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
      + ";charset=utf-8")
  @ResponseBody
  public CastData0104d02 register(@RequestBody CastData0104d02 castData0104d02,
      HttpServletRequest request) throws Exception {
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    String userCode = String.valueOf(sessionData.get("UserCode"));
    ErrorMessages errMess = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();

    String result = "";
    // check input when register data to database
    if (mst0104d02Service.checkInputRegister(castData0104d02)) {
      return castData0104d02;
    }
    result = mst0104d02Service.register(castData0104d02, userCode);
    if (!result.equalsIgnoreCase("")) {
      
      return castData0104d02;
    } else {
      mst0104d02Service.setHaitaDate(castData0104d02);
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("コースマスタの登録");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      castData0104d02.setType("sucess");
      castData0104d02.setErrorMessage(errMess);
    }
    return castData0104d02;
  }

  /**
   * 入力エリアの情報を納入先一覧に追加及び更新する
   * @param formMst0104d02:フォーム
   * @return message
   * @throws JsonProcessingException:エラー画面
   */
  @RequestMapping(value = "/addUpdate", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
      + ";charset=utf-8")
  @ResponseBody
  public FormMst0104d02 addUpdate(@RequestBody FormMst0104d02 formMst0104d02)
      throws JsonProcessingException {
    mst0104d02Service.checkExistEditArea(formMst0104d02);
    return formMst0104d02;
  }
}