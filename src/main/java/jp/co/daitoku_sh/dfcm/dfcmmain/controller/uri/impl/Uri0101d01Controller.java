/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.uri.impl
 * ファイル名:Uri0101d01Controller.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.uri.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.ChainData;
import jp.co.daitoku_sh.dfcm.common.component.CourseData;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.ItemBodyData;
import jp.co.daitoku_sh.dfcm.common.component.JigyoData;
import jp.co.daitoku_sh.dfcm.common.component.ListFormData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.NohinData;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.component.SlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01MshBody;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkBody;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Body;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Head;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Journal;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.UriConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.FormUri0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.SubjectData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.Uri0101d01Screen;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UrkMshHeads;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl.Uri0101d01Service;

/**
 * コントローラクラス
 * 
 * @author
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/uri/URI01-01D00/")
public class Uri0101d01Controller extends AbsController {

  @Autowired
  @Qualifier("uri0101d01Service")
  private Uri0101d01Service uri0101d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Resource
  private HttpServletRequest srvReq;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 画面表示時に実行されるController.
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * @param httpRequest HTTPリクエスト
   * 
   * @return String
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormUri0101d01 formUri0101d01,
      HttpServletRequest httpRequest) {
    ArrayList<String> params = new ArrayList<String>();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // JavaScriptで表示するメッセージを取得する。
    uri0101d01Service.getDefaultMess(model);

    try {
      // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
      String strPath = Util.checkSession(model, httpRequest,
          CommonConst.URI0101D00_SCREEN_NAME);
      if (!strPath.equalsIgnoreCase("")) {
        return strPath;
      }

      String strScreen = "uri/uri0101d01";

      // 業務日付取得
      String strAplDate = uri0101d01Service.getAplDate();
      if (strAplDate == null) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        params = new ArrayList<String>();
        params.add("業務日付");
        params.add("取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM023-E", params));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }

      // 伝票区分取得 (Slip_Kb, Null)
      List<MstGeneralData> lstSlip = uri0101d01Service.setDdlFromMstGeneral(
          model, "Slip_Kb", null, "SlipKbList");
      if (lstSlip == null) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        params = new ArrayList<String>();
        params.add("汎用マスタ");
        params.add("伝票区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", params));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }

      // 修正区分取得 (Modify_Kb, Null)
      List<MstGeneralData> lstModify = uri0101d01Service.setDdlFromMstGeneral(
          model, "Modify_Kb", null, "ModifyKbList");
      if (lstModify == null) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        params = new ArrayList<String>();
        params.add("汎用マスタ");
        params.add("修正区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", params));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }

      // 納品金額上限取得 (Deli_Price_Max, Null)
      List<MstGeneralData> lstPriceMax = uri0101d01Service.getMstGeneralConf(
          "Deli_Price_Max", null);
      String strPriceMax = lstPriceMax.get(0).getTarget1();
      if (strPriceMax.equals("")) {
        strPriceMax = "-1";
      }

      // 納品日加算日数取得 (Deli_Date, Null)
      List<MstGeneralData> lstDeliDate = uri0101d01Service.getMstGeneralConf(
          "Deli_Date", null);
      int intAddDay = -1;
      String strDeliDay = lstDeliDate.get(0).getTarget1();
      if (strDeliDay.equals("")) {
        intAddDay = 0;
      }
      intAddDay = new Integer(strDeliDay).intValue();

      // 会計月取得
      Map<String, Object> mapSession = Util.getContentsFromSession(httpRequest);
      String strJigyoCode = mapSession.get(CommonConst.LOGIN_USER_JIGYOSHO_CODE)
          .toString();
      String strDetermMonth = uri0101d01Service.getDetermMonth(strJigyoCode);
      if (strDetermMonth == null) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        params = new ArrayList<String>();
        params.add("事業所月次確定情報");
        params.add("確定対象年月");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", params));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }

      // ログイン担当者名カナ取得
      String strUserCode = mapSession.get(CommonConst.LOGIN_USER_CODE)
          .toString();
      String strUserNameKana = "";
      UserData userData = uri0101d01Service.getUser(strJigyoCode, strUserCode);
      if (userData.getMsgCd() == null) {
        strUserNameKana = userData.getUsr().getUserNmKana();
      }

      // 画面表示項目セット
      model.addAttribute("UserCode", mapSession.get(
          CommonConst.LOGIN_USER_CODE)); // ログイン担当者コード
      model.addAttribute("UserNm", mapSession.get(CommonConst.LOGIN_USER_NAME)); // ログイン担当者名
      model.addAttribute("UserNmKana", strUserNameKana); // ログイン担当者名カナ
      model.addAttribute("JigyoCode", mapSession.get(
          CommonConst.LOGIN_USER_JIGYOSHO_CODE)); // ログイン事業所コード
      model.addAttribute("JigyoNm", mapSession.get(
          CommonConst.LOGIN_USER_JIGYOSHO_MEI)); // ログイン事業所名

      // 隠し項目セット
      formUri0101d01.setHdnSysAdminFlg(mapSession.get(
          CommonConst.LOGIN_USER_SYS_ADMIN_FLG).toString()); // システム管理者フラグ
      formUri0101d01.setHdnLoginJigyoCode(mapSession.get(
          CommonConst.LOGIN_USER_JIGYOSHO_CODE).toString()); // ログイン事業所コード
      formUri0101d01.setHdnPriceMax(strPriceMax); // 納品金額上限
      formUri0101d01.setHdnAddDay(new Integer(intAddDay).toString()); // 納品日加算日数
      formUri0101d01.setHdnAplDate(strAplDate); // 業務日付
      formUri0101d01.setHdnDetermMonth(strDetermMonth); // 会計月
      formUri0101d01.setHdnPrmDenNo(httpRequest.getParameter(
          UriConst.PRM_DEN_NO)); // 自社伝票No（売上照会）
      formUri0101d01.setHdnPrmShoriKb(httpRequest.getParameter(
          UriConst.PRM_SHORI_KB)); // 処理区分（売上照会）

      return strScreen;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 入力された事業所コードを元に事業所名を取得する.
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 事業所名（JSON型）
   */
  @RequestMapping(value = "/getJigyoNm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getJigyoName(Model model, FormUri0101d01 formUri0101d01) {
    String strJigyoCode = ""; // 事業所コード
    String strJsonData = ""; // JSON文字列（戻り値）

    // 事業所コード取得
    if (formUri0101d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0101d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0101d01.getHdnJigyoCode();
    }

    try {
      // 事業所情報取得
      JigyoData jigyoData = uri0101d01Service.getJigyoName(strJigyoCode);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 事業所情報が存在しない
      if (jigyoData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("事業所マスタ");
        params.add("事業所");
        screen.setStrJigyoCode(null);
        screen.setStrJigyoName(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(jigyoData
            .getMsgCd(), params));
      } else {
        // クライアントに事業所名を送信する。
        screen.setStrJigyoCode(strJigyoCode);
        screen.setStrJigyoName(jigyoData.getJgy().getJgymei());
        screen.setStrMessage(null);
      }
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 入力された得意先コードを元に得意先情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 得意先情報（JSON型）
   */
  @RequestMapping(value = "/getCustomerInfo", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getCustomerInfo(Model model, FormUri0101d01 formUri0101d01) {
    Uri0101d01Screen screen = new Uri0101d01Screen();
    String strJigyoCode = ""; // 事業所コード
    String strCustomerCode = ""; // 得意先コード
    String strJsonData = ""; // JSON文字列（戻り値）

    // 事業所コード取得
    if (formUri0101d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0101d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0101d01.getHdnJigyoCode();
    }

    // 得意先コード取得
    strCustomerCode = formUri0101d01.getHdnCustomerCode();

    try {
      // 得意先情報取得
      CustomerData customerData = uri0101d01Service.getCustomer(strJigyoCode,
          strCustomerCode);
      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      // 得意先情報が存在しない
      if (customerData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("得意先マスタ");
        params.add("得意先情報");
        screen.setMstCustomer(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(customerData
            .getMsgCd(), params));

        lstScreen.add(screen);
        strJsonData = this.convertJson(lstScreen);

        return strJsonData;
      }

      // クライアントに得意先名を送信する。
      screen.setMstCustomer(customerData.getCst());

      // 出荷伝票情報取得
      ListFormData listFormData = uri0101d01Service.getListForm(customerData
          .getCst().getShipsTypId());
      // データが存在しない
      if (listFormData.getListNmR() == null
          || listFormData.getListNmR().equals("")
          || listFormData.getLineNumber() == CommonConst.COM_ERR_SHORT_VAL) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("帳票定義マスタ");
        params.add("出荷伝票情報");
        screen.setStrMessage(readPropertiesFileService.getMessage("COM009-E",
            params));
        screen.setStrListLine(null);
        screen.setStrListName(null);

        lstScreen.add(screen);
        strJsonData = this.convertJson(lstScreen);

        return strJsonData;
      }
      // 伝票行数が不備
      if (listFormData.getLineNumber().equals((short) -1)) {
        // return "MSG_CD_COM001E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("出荷伝票行数");
        screen.setStrListLine(null);
        screen.setStrListName(null);

        lstScreen.add(screen);
        strJsonData = this.convertJson(lstScreen);

        return strJsonData;
      }
      screen.setStrListName(listFormData.getListNmR());
      String tmpLineNo = "000".concat(listFormData.getLineNumber().toString());
      screen.setStrListLine(tmpLineNo.substring(tmpLineNo.length() - 3,
          tmpLineNo.length()));

      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 得意先マスタ.手入力出荷伝票帳票IDから出荷伝票情報を取得する
   * 
   * @param model 売上登録model
   * @param strShipsTypId 手入力出荷伝票帳票ID
   * 
   * @return String 出荷伝票情報（JSON型）
   */
  @RequestMapping(value = "/getListForm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getListForm(Model model,
      @ModelAttribute("prmShipsTypId") String strShipsTypId) {
    Uri0101d01Screen screen = new Uri0101d01Screen();
    List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
    String strJsonData = ""; // JSON文字列（戻り値）

    try {
      // 出荷伝票情報取得
      ListFormData listFormData = uri0101d01Service.getListForm(strShipsTypId);
      // データが存在しない
      if (listFormData.getListNmR() == null
          || listFormData.getListNmR().equals("")
          || listFormData.getLineNumber() == CommonConst.COM_ERR_SHORT_VAL) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("帳票定義マスタ");
        params.add("出荷伝票情報");
        screen.setStrMessage(readPropertiesFileService.getMessage("COM009-E",
            params));
        screen.setStrListLine(null);
        screen.setStrListName(null);

        lstScreen.add(screen);
        strJsonData = this.convertJson(lstScreen);

        return strJsonData;
      }
      // 伝票行数が不備
      if (listFormData.getLineNumber().equals((short) -1)) {
        // return "MSG_CD_COM001E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("出荷伝票行数");
        screen.setStrMessage(readPropertiesFileService.getMessage("COM001-E",
            params));
        screen.setStrListLine(null);
        screen.setStrListName(null);

        lstScreen.add(screen);
        strJsonData = this.convertJson(lstScreen);

        return strJsonData;
      }
      screen.setStrListName(listFormData.getListNmR());
      String tmpLineNo = "000".concat(listFormData.getLineNumber().toString());
      screen.setStrListLine(tmpLineNo.substring(tmpLineNo.length() - 3,
          tmpLineNo.length()));

      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 入力された得意先コード、店舗コードを元に店舗情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 店舗情報（JSON型）
   */
  @RequestMapping(value = "/getShopInfo", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getShopInfo(Model model, FormUri0101d01 formUri0101d01) {
    String strCustomerCode = ""; // 得意先コード
    String strShopCode = ""; // 店舗コード
    String strJsonData = ""; // JSON文字列（戻り値）

    // 得意先コード取得
    strCustomerCode = formUri0101d01.getHdnCustomerCode();
    // 店舗コード取得
    strShopCode = formUri0101d01.getHdnShopCode();

    try {
      // 店舗情報取得
      ShopData shopData = uri0101d01Service.getShop(strCustomerCode,
          strShopCode);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 店舗情報が存在しない
      if (shopData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("店舗マスタ");
        params.add("店舗情報");
        screen.setMstCustomer(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(shopData
            .getMsgCd(), params));
      } else {
        // クライアントに店舗情報を送信する。
        screen.setMstShop(shopData.getShp());
      }
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 事業所コード、得意先コード、店舗コード、納品区分、便区分、店舗区分を元にコース情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String コース情報（JSON型）
   */
  @RequestMapping(value = "/getCourseData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getCourseData(Model model, FormUri0101d01 formUri0101d01) {
    String strJsonData = ""; // JSON文字列（戻り値）

    // 事業所コード取得
    String strJigyoCode = ""; // 事業所コード
    if (formUri0101d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0101d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0101d01.getHdnJigyoCode();
    }

    // 得意先コード取得
    String strCustomerCode = formUri0101d01.getHdnCustomerCode();
    // 店舗コード取得
    String strShopCode = formUri0101d01.getHdnShopCode();
    // 便区分取得
    String strBinKb = formUri0101d01.getHdnBinKb();
    // 納品区分取得
    String strDeliKb = formUri0101d01.getHdnDeliKb();
    // 店舗区分取得
    int intShopKb = new Integer(formUri0101d01.getHdnShopKb()).intValue();

    try {
      // コース情報取得
      CourseData courseData = uri0101d01Service.getCourseData(strJigyoCode,
          strCustomerCode, strShopCode, strDeliKb, strBinKb, intShopKb);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 店舗情報が存在しない
      if (courseData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("コースマスタ");
        params.add("コース情報");
        screen.setMstCourse(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(courseData
            .getMsgCd(), params));
      } else {
        // クライアントに店舗情報を送信する。
        screen.setMstCourse(courseData.getCrs());
      }
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * チェーンコード、チェーン枝番、事業所コード、 納品先コード、納品日、便区分を元に納品先情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 納品先情報（JSON型）
   */
  @RequestMapping(value = "/getDeliData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getDeliData(Model model, FormUri0101d01 formUri0101d01) {
    String strJsonData = ""; // JSON文字列（戻り値）

    // 事業所コード取得
    String strJigyoCode = ""; // 事業所コード
    if (formUri0101d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0101d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0101d01.getHdnJigyoCode();
    }

    // チェーンコード取得
    Short shtChainCode = formUri0101d01.getHdnChainCode();
    // チェーン枝番取得
    Short shtChainIdx = formUri0101d01.getHdnChainIdx();
    // 納品先コード取得
    Short shtDeliCode = formUri0101d01.getHdnDeliCode();
    // 納品年月日取得
    String strDeliYmd = formUri0101d01.getHdnDeliYmd();
    // 便区分取得
    String strBinKb = formUri0101d01.getHdnBinKb();

    try {
      // 納品情報取得
      NohinData deliData = uri0101d01Service.getDeliData(shtChainCode,
          shtChainIdx, new Short(strJigyoCode).shortValue(), new Integer(
              strDeliYmd).intValue(), new Short(strBinKb).shortValue(),
          shtDeliCode);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 納品先情報が存在しない
      if (deliData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("納品先マスタ");
        params.add("納品先情報");
        screen.setMstSnohin(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(deliData
            .getMsgCd(), params));
      } else {
        // クライアントに納品先情報を送信する。
        screen.setMstSnohin(deliData.getNhn());

        // チェーン情報取得
        ChainData chainData = uri0101d01Service.getChainData(shtChainCode,
            shtChainIdx);
        if (chainData == null || chainData.getMsgCd() != null) {
          // return "MSG_CD_COM009E";
          ArrayList<String> params = new ArrayList<String>();
          params.add("チェーンマスタ");
          params.add("チェーン情報");
          screen.setMstSchain(null);
          screen.setStrMessage(readPropertiesFileService.getMessage(chainData
              .getMsgCd(), params));
        } else {
          // クライアントにチェーン情報を送信する。
          screen.setMstSchain(chainData.getMstSchain());
        }
      }

      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 自社伝票Noから売上情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 売上情報（JSON型）
   * @throws JsonProcessingException JSON例外
   */
  @RequestMapping(value = "/getUriageData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getUriageData(Model model, FormUri0101d01 formUri0101d01)
      throws JsonProcessingException {
    String strJsonData = ""; // JSON文字列（戻り値）
    String strOperateMode = formUri0101d01.getHdnOperateMode(); // 処理区分

    // 自社伝票No取得hdnPrmDenNo

    // long lngSlipNo = new Long(formUri0101d01.getHdnPrmDenNo()).longValue();
    long lngSlipNo = new Long(formUri0101d01.getHdnSlipNo()).longValue();

    String strJigyoCode = null;
    if (formUri0101d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0101d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、他事業所のデータも訂正/取消/照会可能なのでNull
      strJigyoCode = null;
      // strJigyoCode = formUri0101d01.getHdnJigyoCode();
    }

    try {
      // 売上情報取得
      UriageData uriData = uri0101d01Service.getUriageData(strJigyoCode,
          lngSlipNo, strOperateMode);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 売上情報が存在しない
      if (uriData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上情報テーブル");
        params.add("売上情報");
        screen.setUriData(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(uriData
            .getMsgCd(), params));
      } else {
        String uriBodyJson = "";
        ObjectWriter ow = new ObjectMapper().writer()
            .withDefaultPrettyPrinter();
        uriBodyJson = ow.writeValueAsString(uriData.getLstUriBody());
        uriData.setUriBodyJson(uriBodyJson);

        // 処理区分：修正用に修正区分を取得（明細部をDynamicに生成するため、top()で取得したデータは使用不可）
        String modifyJson = "";
        List<MstGeneralData> lstModify = uri0101d01Service.setDdlFromMstGeneral(
            model, "Modify_Kb", null,
            "ModifyKbList");
        if (lstModify != null) {
          ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
          modifyJson = ow.writeValueAsString(lstModify);
        }
        uriData.setModifyJson(modifyJson);

        // クライアントに売上情報を送信する。
        screen.setUriData(uriData);
        screen.setStrMessage(null);
      }

      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 返品／欠品元伝票情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 売上情報（JSON型）
   * @throws JsonProcessingException JSON例外
   */
  @RequestMapping(value = "/getOriginUriage", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getOriginUriage(Model model, FormUri0101d01 formUri0101d01)
      throws JsonProcessingException {
    String strJsonData = ""; // JSON文字列（戻り値）

    // 事業所コード取得
    String strJigyoCode = ""; // 事業所コード
    if (formUri0101d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0101d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0101d01.getHdnJigyoCode();
    }

    // 得意先コード取得
    String strCustomerCode = formUri0101d01.getHdnCustomerCode();
    // 店舗コード取得
    String strShopCode = formUri0101d01.getHdnShopCode();
    // 納品年月日取得
    String strDeliYmd = formUri0101d01.getHdnDeliYmd();
    // 便区分取得
    String strBinKb = formUri0101d01.getHdnBinKb();
    // 返品／欠品元伝票No取得
    String strOriginSlipNo = formUri0101d01.getHdnOriginSlipNo();
    try {
      // 返品／欠品元伝票取得
      UriageData uriData = uri0101d01Service.getUriageFromCustomer(strJigyoCode,
          strCustomerCode, strShopCode, strDeliYmd, strBinKb, strOriginSlipNo);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 返品／欠品元伝票情報が存在しない
      if (uriData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上テーブル");
        params.add("返品／欠品元伝票情報");
        screen.setStrMessage(readPropertiesFileService.getMessage(uriData
            .getMsgCd(), params));
      } else {
        String uriBodyJson = "";
        ObjectWriter ow = new ObjectMapper().writer()
            .withDefaultPrettyPrinter();
        uriBodyJson = ow.writeValueAsString(uriData.getLstUriBody());
        uriData.setUriBodyJson(uriBodyJson);

        screen.setStrMessage(null);
        screen.setUriData(uriData);
      }
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 便区分をチェックする
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 便区分情報（JSON型）
   */
  @RequestMapping(value = "/checkBinKb", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String checkBinKb(Model model, FormUri0101d01 formUri0101d01) {
    String strJsonData = ""; // JSON文字列（戻り値）

    // 便区分取得
    String strBinKb = formUri0101d01.getHdnBinKb();
    try {
      String strResult = uri0101d01Service.checkBinKb(strBinKb);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 店舗情報が存在しない
      if (strResult != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("汎用マスタ");
        params.add("便区分情報");
        screen.setStrMessage(readPropertiesFileService.getMessage(strResult,
            params));
      } else {
        screen.setStrMessage(null);
      }
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 品目情報を取得する。
   * 
   * @param model 売上登録model
   * @param strItemCode 品目コード
   * @param strCustCode 得意先コード
   * @param strShopCode 店舗コード
   * @param strSalesKb 販売区分
   * @param intDeliYmd 納品日
   * @param shtBinKb 便区分
   * @param shtChainCode チェーンコード
   * @param shtChainIdx チェーン枝番
   * @param intItemKb 品目コード区分（0：自社品目コード、1：得意先品目コード）
   * @param strJigyoCode 事業所コード
   * 
   * @return String 便区分情報（JSON型）
   */
  @RequestMapping(value = "/getItemInfo", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getItemInfo(Model model,
      @ModelAttribute("prmItemCode") String strItemCode,
      @ModelAttribute("prmCustCode") String strCustCode,
      @ModelAttribute("prmShopCode") String strShopCode,
      @ModelAttribute("prmSalesKb") String strSalesKb,
      @ModelAttribute("prmDeliYmd") int intDeliYmd,
      @ModelAttribute("prmBinKb") short shtBinKb,
      @ModelAttribute("prmChainCode") short shtChainCode,
      @ModelAttribute("prmChainIdx") short shtChainIdx,
      @ModelAttribute("prmItemKb") int intItemKb,
      @ModelAttribute("prmJigyoCode") String strJigyoCode) {
    String strJsonData = ""; // JSON文字列（戻り値）

    try {
      // 品目明細情報取得
      ItemBodyData itemData = uri0101d01Service.getItemBodyData(strItemCode,
          strCustCode, strShopCode, strSalesKb,
          intDeliYmd, shtBinKb, shtChainCode, shtChainIdx, intItemKb,
          strJigyoCode);

      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      // 品目明細情報が存在しない
      if (itemData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        String strMasterKb = itemData.getOrderKb();
        if (strMasterKb.equalsIgnoreCase(
            CommonConst.GEN_MASTER_KBN_ITEM_MASTER)) {
          params.add("商品変換マスタ");
        } else if (strMasterKb.equalsIgnoreCase(
            CommonConst.GEN_MASTER_KBN_ORDER_PRODUCT_HISTORY)) {
          params.add("受注製品履歴");
        } else if (strMasterKb.equalsIgnoreCase(
            CommonConst.GEN_MASTER_KBN_PRODUCT_MASTER)) {
          params.add("製品マスタ");
        } else if (strMasterKb.equalsIgnoreCase(
            CommonConst.GEN_MASTER_KBN_PRODUCT_PLANT_MASTER)) {
          params.add("製品事業所マスタ");
        } else if (strMasterKb.equalsIgnoreCase(
            CommonConst.GEN_MASTER_KBN_MATERIAL_PRICE_MASTER)) {
          params.add("得意先品目価格マスタ");
        } else {
          params.add("");
        }
        params.add("品目情報");
        screen.setMstItemDetail(null);
        screen.setStrMessage(readPropertiesFileService.getMessage(itemData
            .getMsgCd(), params));
      } else {
        // クライアントに店舗情報を送信する。
        screen.setMstItemDetail(itemData.getItemDetailInfo());
      }
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 納品金額上限値を取得する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String 納品金額上限値情報（JSON型）
   */
  @RequestMapping(value = "/getDeliPriceMax", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getDeliPriceMax(Model model, FormUri0101d01 formUri0101d01) {
    String strJsonData = ""; // JSON文字列（戻り値）

    try {
      // 納品金額上限取得 (Deli_Price_Max, Null)
      List<MstGeneralData> lstPriceMax = uri0101d01Service.getMstGeneralConf(
          "Deli_Price_Max", null);
      String strPriceMax = lstPriceMax.get(0).getTarget1();
      if (strPriceMax.equals("")) {
        strPriceMax = "-1";
      }
      List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
      Uri0101d01Screen screen = new Uri0101d01Screen();
      screen.setDeliPriceMax(strPriceMax);
      lstScreen.add(screen);
      strJsonData = this.convertJson(lstScreen);

      return strJsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 売上情報を登録する
   * 
   * @param model 売上登録model
   * @param formUri0101d01 売上登録form
   * 
   * @return String
   * @throws Exception 例外
   */
  @RequestMapping(value = "/registryUriageData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String registryUriageData(Model model, FormUri0101d01 formUri0101d01)
      throws Exception {
    String strOperateMode = formUri0101d01.getHdnOperateMode(); // 処理区分
    // String strSlipKb = formUri0101d01.getHdnSlipKb(); // 伝票区分

    MstCustomer tblMstCustomer = null; // 得意先マスタ
    MstShop tblMstShop = null; // 店舗マスタ
    long lngSlipNo = 0; // 自社伝票No
    short shtSlipIdx = 0; // 自社伝票枝番
    String strCustomerSlipNo = ""; // 得意先伝票No

    String strJigyoCode = formUri0101d01.getHdnJigyoCode(); // 事業所コード
    String strCustomerCode = formUri0101d01.getHdnCustomerCode(); // 得意先コード
    String strShopCode = formUri0101d01.getHdnShopCode(); // 店舗コード

    // 得意先情報取得
    try {
      CustomerData customerData = uri0101d01Service.getCustomer(strJigyoCode,
          strCustomerCode);
      if (customerData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("得意先マスタ");
        params.add("得意先情報");

        return this.createErrorJson("COM009-E", params);
      }
      tblMstCustomer = customerData.getCst();
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    // 店舗情報取得
    try {
      if (strShopCode != null && !strShopCode.trim().equalsIgnoreCase("")) {
        ShopData shopData = uri0101d01Service.getShop(strCustomerCode,
            strShopCode);
        if (shopData.getMsgCd() != null) {
          // return "MSG_CD_COM009E";
          ArrayList<String> params = new ArrayList<String>();
          params.add("店舗マスタ");
          params.add("店舗情報");

          return this.createErrorJson("COM009-E", params);
        }
        tblMstShop = shopData.getShp();
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_ADD)) {
      /** 処理区分 = '新規登録'の場合、 */
      // 得意先伝票No採番
      if (!formUri0101d01.getHdnSnpSlipNo().trim().equalsIgnoreCase("")) {
        strCustomerSlipNo = formUri0101d01.getHdnSnpSlipNo().trim();
      } else {
        try {
          List<SlipNumberingData> lstCustomerSlipNo = uri0101d01Service
              .getCustomerSlipNo(strCustomerCode,
                  strShopCode, 1, tblMstCustomer.getDatidxKb());
          if (lstCustomerSlipNo == null || lstCustomerSlipNo.isEmpty()) {
            // return "MSG_CD_COM009E";
            ArrayList<String> params = new ArrayList<String>();
            params.add("採番テーブル");
            params.add("得意先伝票No情報");

            return this.createErrorJson("COM009-E", params);
          }
          strCustomerSlipNo = lstCustomerSlipNo.get(0).getSlipNumber();
        } catch (MyBatisSystemException e) {
          e.printStackTrace();
          logger.error(e.getMessage());
          throw e;
        }
      }

      // 自社伝票No採番、枝番取得
      try {
        List<OwnSlipNumberingData> lstSlipNo = uri0101d01Service.getSlipNo(
            UriConst.SLIP_KB_OWN, (long) 1);
        if (lstSlipNo == null || lstSlipNo.isEmpty()) {
          // return "MSG_CD_COM009E";
          ArrayList<String> params = new ArrayList<String>();
          params.add("採番テーブル");
          params.add("自社伝票No情報");

          return this.createErrorJson("COM009-E", params);
        }
        lngSlipNo = lstSlipNo.get(0).getOwnSlipNumber();
        shtSlipIdx = 1;
      } catch (MyBatisSystemException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        throw e;
      }
    } else if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)
        || strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_DEL)) {
      /** 処理区分 = '新規登録'以外の場合、 */
      strCustomerSlipNo = formUri0101d01.getHdnCustomerSlipNo().trim(); // 得意先伝票No
      lngSlipNo = new Long(formUri0101d01.getHdnSlipNo()).longValue(); // 自社伝票No
      shtSlipIdx = formUri0101d01.getHdnSlipIdx(); // 自社伝票枝番
    }

    // 登録処理に必要な変数をセット
    Uri0101d01Screen screen = new Uri0101d01Screen();
    screen.setMstCustomer(tblMstCustomer);
    screen.setMstShop(tblMstShop);
    screen.setLngSlipNo(lngSlipNo);
    screen.setShtSlipIdx(shtSlipIdx);
    screen.setStrCustomerSlipNo(strCustomerSlipNo);

    UriageData uriData = null; // 売上情報（黒伝票）
    UriageData uriDataRed = null; // 売上情報（赤伝票）
    List<TblUri01Journal> lstUriJournal = new ArrayList<TblUri01Journal>(); // 売上仕訳情報リスト
    List<TblSei01UrkBody> lstSeiUrkBody = new ArrayList<TblSei01UrkBody>(); // 売掛明細情報リスト
    List<TblSei01MshBody> lstSeiMshBody = new ArrayList<TblSei01MshBody>(); // 未収明細情報リスト

    String strCancelMode = formUri0101d01.getHdnCancelMode(); // 取消モード
    /** 処理区分 = '修正' or '取消'の場合、 */
    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)
        || (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_DEL)
    // && strCancelMode.equalsIgnoreCase(UriConst.CANCEL_MODE_CANCEL)
    )) {
      // 売上情報（赤伝票）生成
      uriDataRed = uri0101d01Service.createUriageDataRed(model, formUri0101d01,
          screen);
      if (uriDataRed.getMsgCd() == "COM022-E") {
        // return "MSG_CD_COM022E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上情報");
        params.add("登録");

        return this.createErrorJson(uriDataRed.getMsgCd(), params);
      } else if (uriDataRed.getMsgCd() == "COM014-E") {
        // return "MSG_CD_COM014E";
        return this.createErrorJson(uriDataRed.getMsgCd(), null);
      }
      for (TblUri01Body tblUriBodyRed : uriDataRed.getLstUriBody()) {
        /** 売上仕訳情報（赤伝票）生成 */
        // 勘定科目情報マップ取得
        Map<String, SubjectData> mapSubjectData = uri0101d01Service
            .getMapSubject(tblUriBodyRed, tblMstCustomer);

        // 売上仕訳No.取得
        long lngUriJournalNo = 0;
        try {
          List<OwnSlipNumberingData> lstSlipNo = uri0101d01Service.getSlipNo(
              UriConst.SLIP_KB_URI_JOURNAL, (long) 1);
          if (lstSlipNo == null || lstSlipNo.isEmpty()) {
            // return "MSG_CD_COM022E";
            ArrayList<String> params = new ArrayList<String>();
            params.add("売上情報");
            params.add("登録");

            return this.createErrorJson("COM022-E", params);
          }
          lngUriJournalNo = lstSlipNo.get(0).getOwnSlipNumber();
        } catch (MyBatisSystemException e) {
          e.printStackTrace();
          logger.error(e.getMessage());
          throw e;
        }

        // 売上仕訳情報（赤伝票）リスト（本体＆消費税）生成
        List<TblUri01Journal> lstUriJournalRedTemp = uri0101d01Service
            .createUriJournalDual(uriDataRed.getUriHead(), tblUriBodyRed,
                mapSubjectData, lngUriJournalNo);
        if (lstUriJournalRedTemp == null || lstUriJournalRedTemp.isEmpty()) {
          // return "MSG_CD_COM022E";
          ArrayList<String> params = new ArrayList<String>();
          params.add("売上情報");
          params.add("登録");

          return this.createErrorJson("COM022-E", params);
        }
        lstUriJournal.addAll(lstUriJournalRedTemp);

        /** 売掛・未収明細（赤伝票）生成 */
        if (tblUriBodyRed.getItemCtgr().trim().equalsIgnoreCase(
            UriConst.ITEM_CLASS_ITEM)) {
          // 売掛No.採番
          long lngUrkNo = 0;
          List<OwnSlipNumberingData> lstUrkNo = uri0101d01Service.getSlipNo(
              UriConst.SLIP_KB_RECEIVED, 1);
          if (lstUrkNo == null || lstUrkNo.isEmpty()) {
            return null;
          }
          lngUrkNo = lstUrkNo.get(0).getOwnSlipNumber();

          // 商品分類 = '商品'の場合、売掛明細情報生成
          TblSei01UrkBody tblSeiUrkBody = uri0101d01Service.createSeiUrkBody(
              uriDataRed.getUriHead(), tblUriBodyRed, tblMstCustomer,
              tblMstShop, lngUrkNo, lngUriJournalNo);
          lstSeiUrkBody.add(tblSeiUrkBody);
        } else {
          // 未収No.採番
          long lngMshNo = 0;
          List<OwnSlipNumberingData> lstMshNo = uri0101d01Service.getSlipNo(
              UriConst.SLIP_KB_ACCRUED, 1);
          if (lstMshNo == null || lstMshNo.isEmpty()) {
            return null;
          }
          lngMshNo = lstMshNo.get(0).getOwnSlipNumber();

          // 商品分類 = '送料' or '手数料'の場合、未収明細情報生成
          TblSei01MshBody tblSeiMshBody = uri0101d01Service.createSeiMshBody(
              uriDataRed.getUriHead(), tblUriBodyRed, tblMstCustomer,
              tblMstShop, lngMshNo, lngUriJournalNo);
          lstSeiMshBody.add(tblSeiMshBody);
        }
      }
    }

    /** 処理区分 = '新規登録' or '修正'の場合、 */
    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_ADD)
        || strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)) {
      // 売上情報（黒伝票）生成
      uriData = uri0101d01Service.createUriageData(model, formUri0101d01,
          screen);
      if (uriData.getMsgCd() != null) {
        // return "MSG_CD_COM022E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上情報");
        params.add("登録");

        return this.createErrorJson("COM022-E", params);
      }
      // 売上仕訳情報（黒伝票）生成
      for (TblUri01Body tblUriBody : uriData.getLstUriBody()) {
        // 勘定科目情報マップ取得
        Map<String, SubjectData> mapSubjectData = uri0101d01Service
            .getMapSubject(tblUriBody, tblMstCustomer);

        // 売上仕訳No.取得
        long lngUriJournalNo = 0;
        try {
          List<OwnSlipNumberingData> lstSlipNo = uri0101d01Service.getSlipNo(
              UriConst.SLIP_KB_URI_JOURNAL,
              (long) 1);
          if (lstSlipNo == null || lstSlipNo.isEmpty()) {
            // return "MSG_CD_COM022E";
            ArrayList<String> params = new ArrayList<String>();
            params.add("売上情報");
            params.add("登録");

            return this.createErrorJson("COM022-E", params);
          }
          lngUriJournalNo = lstSlipNo.get(0).getOwnSlipNumber();
        } catch (MyBatisSystemException e) {
          e.printStackTrace();
          logger.error(e.getMessage());
          throw e;
        }

        // 売上仕訳情報（黒伝票）リスト（本体＆消費税）生成
        List<TblUri01Journal> lstUriJournalTemp = uri0101d01Service
            .createUriJournalDual(
                uriData.getUriHead(), tblUriBody, mapSubjectData,
                lngUriJournalNo);
        if (lstUriJournalTemp == null || lstUriJournalTemp.isEmpty()) {
          // return "MSG_CD_COM022E";
          ArrayList<String> params = new ArrayList<String>();
          params.add("売上情報");
          params.add("登録");

          return this.createErrorJson("COM022-E", params);
        }
        lstUriJournal.addAll(lstUriJournalTemp);

        /** 売掛・未収明細（黒伝票）生成 */
        if (tblUriBody.getItemCtgr().trim().equalsIgnoreCase(
            UriConst.ITEM_CLASS_ITEM)) {
          // 売掛No.採番
          long lngUrkNo = 0;
          List<OwnSlipNumberingData> lstUrkNo = uri0101d01Service.getSlipNo(
              UriConst.SLIP_KB_RECEIVED, 1);
          if (lstUrkNo == null || lstUrkNo.isEmpty()) {
            return null;
          }
          lngUrkNo = lstUrkNo.get(0).getOwnSlipNumber();

          // 商品分類 = '商品'の場合、売掛明細情報生成
          TblSei01UrkBody tblSeiUrkBody = uri0101d01Service.createSeiUrkBody(
              uriData.getUriHead(), tblUriBody, tblMstCustomer, tblMstShop,
              lngUrkNo, lngUriJournalNo);
          lstSeiUrkBody.add(tblSeiUrkBody);
        } else {
          // 未収No.採番
          long lngMshNo = 0;
          List<OwnSlipNumberingData> lstMshNo = uri0101d01Service.getSlipNo(
              UriConst.SLIP_KB_ACCRUED, 1);
          if (lstMshNo == null || lstMshNo.isEmpty()) {
            return null;
          }
          lngMshNo = lstMshNo.get(0).getOwnSlipNumber();

          // 商品分類 = '送料' or '手数料'の場合、未収明細情報生成
          TblSei01MshBody tblSeiMshBody = uri0101d01Service.createSeiMshBody(
              uriData.getUriHead(), tblUriBody, tblMstCustomer, tblMstShop,
              lngMshNo, lngUriJournalNo);
          lstSeiMshBody.add(tblSeiMshBody);
        }
      }
    }

    // 処理区分毎の登録/更新処理
    TblUri01Head uriHead = null;
    try {
      if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_ADD)) {
        /** 処理区分 = '新規登録'の場合、 */
        // 新規登録処理実行
        uri0101d01Service.execRegistry(uriData, lstUriJournal, lstSeiUrkBody,
            lstSeiMshBody);
        uriHead = uriData.getUriHead();
      } else if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)) {
        // 訂正処理実行
        uri0101d01Service.execModify(uriDataRed, uriData, lstUriJournal,
            lstSeiUrkBody, lstSeiMshBody);
        uriHead = uriData.getUriHead();
      } else if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_DEL)) {
        // 取消処理実行
        uri0101d01Service.execCancel(uriDataRed, lstUriJournal, lstSeiUrkBody,
            lstSeiMshBody, strCancelMode);
        uriHead = uriDataRed.getUriHead();
      }

      // 売掛・未収ヘッダ情報登録処理
      if (uriHead != null) {
        UrkMshHeads urkMshHeads = uri0101d01Service.registryUrkMshHead(
            lstSeiUrkBody,
            lstSeiMshBody, uriHead, tblMstCustomer, tblMstShop);

        TblSei01UrkMshHead tblShop = urkMshHeads.getTblShopUrkMshHead();
        TblSei01UrkMshHead tblCustomer = urkMshHeads.getTblCustomerUrkMshHead();
        if (tblShop != null) {
          if (urkMshHeads.isFlgInsertShop()) {
            uri0101d01Service.insertSeiUrkMshHead(tblShop);
          } else {
            uri0101d01Service.updateSeiUrkMshHead(tblShop);
          }
        }
        if (urkMshHeads.isFlgInsertCustomer()) {
          uri0101d01Service.insertSeiUrkMshHead(tblCustomer);
        } else {
          uri0101d01Service.updateSeiUrkMshHead(tblCustomer);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    UriageData uriDataRtn = new UriageData();
    uriDataRtn.setUriHead(uriHead);
    screen.setUriData(uriDataRtn);
    screen.setStrMessage(null);

    List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();
    lstScreen.add(screen);
    String strJsonData = this.convertJson(lstScreen);

    return strJsonData;
  }

  /**
   * エラー情報（JSON型）を生成する
   * 
   * @param strErrorCode エラーコード（メッセージID）
   * @param params 代替文字列パラメータ
   * 
   * @return String JSON型エラー情報
   */
  private String createErrorJson(String strErrorCode,
      ArrayList<String> params) {
    String strJsonData = null;
    Uri0101d01Screen screen = new Uri0101d01Screen();
    List<Uri0101d01Screen> lstScreen = new ArrayList<Uri0101d01Screen>();

    screen.setStrMessage(readPropertiesFileService.getMessage(strErrorCode,
        params));
    lstScreen.add(screen);
    strJsonData = this.convertJson(lstScreen);

    return strJsonData;
  }

  /**
   * List&lt;Uri0101d01Screen&gt;型のリストオブジェクトをJSPに戻せるJSON型に変換する
   * 
   * @param lstScreen 変換対象のList&lt;Uri0101d01Screen&gt;型オブジェクト
   * 
   * @return String 変換後の文字列
   */
  private String convertJson(List<Uri0101d01Screen> lstScreen) {
    String strJsonData = null;
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      strJsonData = ow.writeValueAsString(lstScreen);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }
    return strJsonData;
  }
}
