/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.uri.impl
 * ファイル名:UriBillSourceController.java
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

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.BillSourceInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.FormUri0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.Uri0103d01Screen;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageData;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl.Uri0103d01Service;

/**
 * URLにて指定されたサービスを呼び出すコントローラークラス
 *
 * @author 前田
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/uri/uriBillSource/")
public class UriBillSourceController extends AbsController {

  @Autowired
  @Qualifier("uri0103d01Service")
  private Uri0103d01Service uri0103d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 画面表示処理.
   * 
   * @param request 再請求売上登録リクエスト
   * @param model 再請求売上登録モデル
   * @param formUri0103d01 再請求売上登録フォーム
   * @param txtBillSrcNo 請求元伝票No
   * 
   * @return String（JSPファイル）
   */
  @RequestMapping(value = "/")
  public String top(WebRequest request, Model model,
      FormUri0103d01 formUri0103d01,
      @RequestParam("billSrcNo") String txtBillSrcNo) {

    // default メッセージを取得する。
    uri0103d01Service.getDefaultMess(model);

    // 引数取得
    formUri0103d01.setTxtBillSrcNo(txtBillSrcNo); // 請求元伝票No

    // 表示するJSPファイル名を返却
    return "uri/uriBillSource";
  }

  /**
   * 請求元売上情報リスト取得
   * 
   * @param formUri0103d01 再請求売上登録フォーム
   * @return String 請求元売上情報リスト（JSON型）
   * @throws JsonProcessingException JSON例外
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", value = "/getBillSourceList", method = RequestMethod.POST)
  @ResponseBody
  public String getBillSourceList(FormUri0103d01 formUri0103d01)
      throws JsonProcessingException {

    // 事業所コード取得
    String strJigyoCode = "";
    if (formUri0103d01.getHdnSysAdminFlg().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // ログイン事業所コード
      strJigyoCode = formUri0103d01.getHdnLoginJigyoCode();
    } else {
      // システム管理者の場合、[画面]事業所コード
      strJigyoCode = formUri0103d01.getHdnJigyoCode();
    }
    // 請求元伝票No
    String strBillSrcNo = formUri0103d01.getTxtBillSrcNo();

    try {
      // 請求元売上情報リスト取得
      List<BillSourceInfo> lstBillSrcInfo = uri0103d01Service.getBillSourceList(
          strJigyoCode, strBillSrcNo);

      List<Uri0103d01Screen> lstScreen = new ArrayList<Uri0103d01Screen>();
      Uri0103d01Screen screen = new Uri0103d01Screen();
      // 請求元の売上情報が存在しない
      if (lstBillSrcInfo == null || lstBillSrcInfo.isEmpty()) {
        // return "MSG_CD_COM009E";
        ArrayList<String> params = new ArrayList<String>();
        params.add("売上テーブル");
        params.add("請求元売上情報");
        screen.setStrJsonUriHead(null);
        screen.setStrMessage(readPropertiesFileService.getMessage("COM009-E",
            params));
      } else {
        String jsonUriHead = "";
        ObjectWriter ow = new ObjectMapper().writer()
            .withDefaultPrettyPrinter();
        jsonUriHead = ow.writeValueAsString(lstBillSrcInfo);
        screen.setStrJsonUriHead(jsonUriHead);
      }
      lstScreen.add(screen);
      String jsonData = this.convertJson(lstScreen);

      return jsonData;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 請求元売上情報取得
   * 
   * @param jigyoCode 事業所コード
   * @param denNo 自社伝票No
   * @return String 請求元売上情報（JSON型）
   * @throws JsonProcessingException JSON例外
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", value = "/getBillSourceData", method = RequestMethod.POST)
  @ResponseBody
  public String getBillSourceData(
      @ModelAttribute("prmJigyoCode") String jigyoCode,
      @ModelAttribute("prmDenNo") long denNo) throws JsonProcessingException {
    // String , String binKb, String custCode, String shopCode)
    String jsonData = "";

    // long lngSlipNo = new Long(denNo).longValue();

    try {
      // 売上情報取得
      UriageData uriData = uri0103d01Service.getUriageData(jigyoCode, denNo,
          CommonConst.SHORI_KB_ADD);

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
      jsonData = this.convertJson(lstScreen);

      return jsonData;
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
