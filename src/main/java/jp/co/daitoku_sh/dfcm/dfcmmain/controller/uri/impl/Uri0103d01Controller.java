/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.uri.impl
 * ファイル名:Uri0103d01Controller.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/12/10
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 AB)前田 新規開発
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.JigyoData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Head;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.UriConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.FormUri0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.Uri0103d01Screen;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageData;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl.Uri0103d01Service;

/**
 * コントローラクラス
 * 
 * @author
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/uri/URI01-03D00/")
public class Uri0103d01Controller extends AbsController {

  @Autowired
  @Qualifier("uri0103d01Service")
  private Uri0103d01Service uri0103d01Service;

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
   * @param formUri0103d01 売上登録form
   * @param httpRequest HTTPリクエスト
   * 
   * @return String
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormUri0103d01 formUri0103d01,
      HttpServletRequest httpRequest) {

    // JavaScriptで表示するメッセージを取得する。
    uri0103d01Service.getDefaultMess(model);

    try {
      // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
      String strPath = Util.checkSession(model, httpRequest,
          CommonConst.URI0103D00_SCREEN_NAME);
      if (!strPath.equalsIgnoreCase("")) {
        return strPath;
      }

      // 納品金額上限取得 (Deli_Price_Max, Null)
      List<MstGeneralData> lstPriceMax = uri0103d01Service.getMstGeneralConf(
          "Deli_Price_Max", null);
      String strPriceMax = lstPriceMax.get(0).getTarget1();
      if (strPriceMax.equals("")) {
        strPriceMax = "-1";
      }

      // ログイン担当者名カナ取得
      Map<String, Object> mapSession = Util.getContentsFromSession(httpRequest);
      String strJigyoCode = mapSession.get(CommonConst.LOGIN_USER_JIGYOSHO_CODE)
          .toString();
      String strUserCode = mapSession.get(CommonConst.LOGIN_USER_CODE)
          .toString();
      String strUserNameKana = "";
      UserData userData = uri0103d01Service.getUser(strJigyoCode, strUserCode);
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
      formUri0103d01.setHdnSysAdminFlg(mapSession.get(
          CommonConst.LOGIN_USER_SYS_ADMIN_FLG).toString()); // システム管理者フラグ
      formUri0103d01.setHdnLoginJigyoCode(mapSession.get(
          CommonConst.LOGIN_USER_JIGYOSHO_CODE).toString()); // ログイン事業所コード
      formUri0103d01.setHdnRegistUserCode(mapSession.get(
          CommonConst.LOGIN_USER_CODE).toString()); // 登録ユーザコード
      formUri0103d01.setHdnPriceMax(strPriceMax); // 納品金額上限

      return "uri/uri0103d01";
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 入力された事業所コードを元に事業所名を取得する.
   * 
   * @param model 再請求売上登録model
   * @param formUri0103d01 再請求売上登録form
   * 
   * @return String 事業所名（JSON型）
   */
  @RequestMapping(value = "/getJigyoNm", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getJigyoName(Model model, FormUri0103d01 formUri0103d01) {
    String strJigyoCode = ""; // 事業所コード
    String strJsonData = ""; // JSON文字列（戻り値）

    // 事業所コード取得
    if (formUri0103d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0103d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0103d01.getHdnJigyoCode();
    }

    try {
      // 事業所情報取得
      JigyoData jigyoData = uri0103d01Service.getJigyoName(strJigyoCode);

      List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();
      Uri0103d01Screen screen = new Uri0103d01Screen();
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
   * 納品金額上限値を取得する
   * 
   * @param model 売上登録model
   * @param formUri0103d01 売上登録form
   * 
   * @return String 納品金額上限値情報（JSON型）
   */
  @RequestMapping(value = "/getDeliPriceMax", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getDeliPriceMax(Model model, FormUri0103d01 formUri0103d01) {
    String strJsonData = ""; // JSON文字列（戻り値）

    try {
      // 納品金額上限取得 (Deli_Price_Max, Null)
      List<MstGeneralData> lstPriceMax = uri0103d01Service.getMstGeneralConf(
          "Deli_Price_Max", null);
      String strPriceMax = lstPriceMax.get(0).getTarget1();
      if (strPriceMax.equals("")) {
        strPriceMax = "-1";
      }
      List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();
      Uri0103d01Screen screen = new Uri0103d01Screen();
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
   * 自社伝票Noから売上情報を取得する
   * 
   * @param model 売上登録model
   * @param formUri0103d01 売上登録form
   * 
   * @return String 売上情報（JSON型）
   * @throws JsonProcessingException JSON例外
   */
  @RequestMapping(value = "/getUriageData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getUriageData(Model model, FormUri0103d01 formUri0103d01)
      throws JsonProcessingException {
    String strJsonData = ""; // JSON文字列（戻り値）
    String strOperateMode = formUri0103d01.getHdnOperateMode(); // 処理区分

    // 自社伝票No取得hdnPrmDenNo
    long lngSlipNo = 0;
    if (!formUri0103d01.getHdnSlipNo().equalsIgnoreCase("")) {
      lngSlipNo = new Long(formUri0103d01.getHdnSlipNo()).longValue();
    }
    String strJigyoCode = null;
    if (formUri0103d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // 一般ユーザーの場合、ログイン事業所コード
      strJigyoCode = formUri0103d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、他事業所のデータも訂正/取消/照会可能なのでNull
      strJigyoCode = null;
      // strJigyoCode = formUri0103d01.getHdnJigyoCode();
    }

    try {
      // 売上情報取得
      UriageData uriData = uri0103d01Service.getUriageData(strJigyoCode,
          lngSlipNo, strOperateMode);

      List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();
      Uri0103d01Screen screen = new Uri0103d01Screen();
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
   * 得意先コード、店舗コード、便区分、納品日、請求元伝票Noから請求元の売上情報を取得する
   * 
   * @param model 再請求売上登録model
   * @param formUri0103d01 再請求売上登録form
   * 
   * @return String 請求元売上情報（JSON型）
   * @throws JsonProcessingException JSON例外
   */
  @RequestMapping(value = "/getBillSrcUriageData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getBillSrcUriageData(Model model, FormUri0103d01 formUri0103d01)
      throws JsonProcessingException {
    String strJsonData = ""; // JSON文字列（戻り値）

    // 請求元伝票No
    String strBillSrcNo = formUri0103d01.getHdnBillSrcNo();
    // 得意先コード
    String strCustCode = formUri0103d01.getHdnCustomerCode();
    // 店舗コード
    String strShopCode = formUri0103d01.getHdnShopCode();
    // 便区分
    String strBinKb = formUri0103d01.getHdnBinKb();
    // 納品日
    String strDeliDate = formUri0103d01.getHdnDeliDate();

    try {
      // 請求元売上情報取得
      UriageData uriData = uri0103d01Service.getBillSrcUriageData(strBillSrcNo,
          strCustCode, strShopCode, strBinKb, strDeliDate);

      List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();
      Uri0103d01Screen screen = new Uri0103d01Screen();
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
   * 売上情報を登録する
   * 
   * @param model 売上登録model
   * @param formUri0103d01 売上登録form
   * 
   * @return String
   * @throws Exception 例外
   */
  @RequestMapping(value = "/registryUriageData", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded; charset=utf-8", produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String registryUriageData(Model model, FormUri0103d01 formUri0103d01)
      throws Exception {
    String strOperateMode = formUri0103d01.getHdnOperateMode(); // 処理区分
    // String strSlipKb = formUri0103d01.getHdnSlipKb(); // 伝票区分

    long lngSlipNo = 0; // 自社伝票No
    short shtSlipIdx = 0; // 自社伝票枝番

    // 得意先マスタ.消費税端数処理取得
    try {
      String strJigyoCode = formUri0103d01.getHdnJigyoCode();
      String strCustomerCode = formUri0103d01.getHdnCustomerCode();
      CustomerData customerData = uri0103d01Service.getCustomer(strJigyoCode,
          strCustomerCode);
      if (customerData.getMsgCd() != null) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("得意先マスタ");
        params.add("得意先情報");

        return this.createErrorJson("COM009-E", params);
      }
      formUri0103d01.setStrTaxFrcKb(customerData.getCst().getTaxFrcKb());
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_ADD)) {
      /** 処理区分 = '新規登録'の場合、 */
      // 自社伝票No採番、枝番取得
      try {
        List<OwnSlipNumberingData> lstSlipNo = uri0103d01Service.getSlipNo(
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
        formUri0103d01.setHdnSlipNo(new Long(lngSlipNo).toString());
        formUri0103d01.setHdnSlipIdx(shtSlipIdx);
      } catch (MyBatisSystemException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        throw e;
      }
    }

    UriageData uriData = null; // 売上情報（黒伝票）
    UriageData uriDataRed = null; // 売上情報（赤伝票）

    /** 処理区分 = '修正' or '取消'の場合、 */
    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)
        || (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_DEL))) {
      // 売上情報（赤伝票）生成
      uriDataRed = uri0103d01Service.createUriageDataRed(model, formUri0103d01);
      if (uriDataRed.getMsgCd() == "COM023-E") {
        // return "MSG_CD_COM022E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上情報");
        params.add("登録");

        return this.createErrorJson(uriDataRed.getMsgCd(), params);
      } else if (uriDataRed.getMsgCd() == "COM014-E") {
        // return "MSG_CD_COM014E";
        return this.createErrorJson(uriDataRed.getMsgCd(), null);
      }
    }
    /** 処理区分 = '新規登録' or '修正'の場合、 */
    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_ADD)
        || strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)) {
      // 売上情報（黒伝票）生成
      uriData = uri0103d01Service.createUriageData(model, formUri0103d01);
      if (uriData.getMsgCd() != null) {
        // return "MSG_CD_COM022E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上情報");
        params.add("登録");

        return this.createErrorJson("COM023-E", params);
      }
    }

    // 処理区分毎の登録/更新処理
    TblUri01Head uriHead = null;
    try {
      if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_ADD)) {
        /** 処理区分 = '新規登録'の場合、 */
        // 新規登録処理実行
        uri0103d01Service.execRegistry(uriData, null, null, null);
        uriHead = uriData.getUriHead();
      } else if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)) {
        // 訂正処理実行
        uri0103d01Service.execModify(uriDataRed, uriData, null, null, null);
        uriHead = uriData.getUriHead();
      } else if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_DEL)) {
        // 取消処理実行
        uri0103d01Service.execCancel(uriDataRed, null, null, null, null);
        uriHead = uriDataRed.getUriHead();
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    UriageData uriDataRtn = new UriageData();
    uriDataRtn.setUriHead(uriHead);

    Uri0103d01Screen rtnScreen = new Uri0103d01Screen();
    rtnScreen.setUriData(uriDataRtn);
    rtnScreen.setStrMessage(null);

    List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();
    lstScreen.add(rtnScreen);
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
    Uri0103d01Screen screen = new Uri0103d01Screen();
    List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();

    screen.setStrMessage(readPropertiesFileService.getMessage(strErrorCode,
        params));
    lstScreen.add(screen);
    strJsonData = this.convertJson(lstScreen);

    return strJsonData;
  }

  /**
   * List&lt;Uri0103d01Screen&gt;型のリストオブジェクトをJSPに戻せるJSON型に変換する
   * 
   * @param lstScreen 変換対象のList&lt;Uri0103d01Screen&gt;型オブジェクト
   * 
   * @return String 変換後の文字列
   */
  private String convertJson(List<Uri0103d01Screen> lstScreen) {
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
