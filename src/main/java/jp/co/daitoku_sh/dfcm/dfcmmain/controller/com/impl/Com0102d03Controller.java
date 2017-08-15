/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl
 * ファイル名:Com0102d03Controller.java
 * 
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/09/25
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 ABV)グエン　リユオン　ギア 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d03;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d03Service;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * 
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/com/COM01-02D03/")
public class Com0102d03Controller {

  // Serviceの定義
  @Autowired
  @Qualifier("com0102d03Service")
  private Com0102d03Service com0102d03Service;

  /**
   * 初期表示処理.
   * 
   * @param FormCom0102d03:formCom0102d03
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(WebRequest request, Model model,
      FormCom0102d03 formCom0102d03,
      @RequestParam("custCode") String custCode,
      @RequestParam("shopCode") String shopCode) {

    // default メッセージを取得する。
    com0102d03Service.getDefaultMess(model);
    // 引数取得
    formCom0102d03.setCustCode(custCode);// 得意先コード
    formCom0102d03.setShopCode(shopCode);// 店舗コード
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d03Service.getCommonDao(), null, null,
        null);
    int isearchMax = 0;
    isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d03.setSearchMax(isearchMax);
    // 画面マッピング
    com0102d03Service.initialize(model, custCode, formCom0102d03);
    final String screenMapping = "com/com0102d03";
    return screenMapping;
  }

  /**
   * com0102d01getSearchResult 検索結果を取得com0102d01.
   * 
   * @param formCom0102d03:formCom0102d03
   * @param custCode:custCode
   * @param shopCode:shopCode
   * @param shopName:shopName
   * @return jsonData
   * @throws JsonProcessingException:e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8",
      value = "/com0102d03getSearchResult", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d01getSearchResult(FormCom0102d03 formCom0102d03,
      @RequestParam("custCode") String custCode,
      @RequestParam("shopCode") String shopCode,
      @RequestParam("shopName") String shopName)
          throws JsonProcessingException {

    // 引数取得
    formCom0102d03.setCustCode(custCode); // 得意先コード
    formCom0102d03.setShopCode(shopCode);// 店舗コード
    formCom0102d03.setShopNm(shopName);// 店舗名
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d03Service.getCommonDao(), null, null,
        null);
    int isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d03.setSearchMax(isearchMax);
    // 検索処理
    String jsonData = com0102d03Service.searchMstShop(formCom0102d03);

    // 検索結果返却
    return jsonData;
  }

  /**
   * com0102d02closeSupplierSearch com0102d02近いサプライヤー検索.
   * 
   * @param formCom0102d03:formCom0102d03
   * @param custCode:custCode
   * @param shopCode:shopCode
   * @return jsonData
   * @throws JsonProcessingException:e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8",
      value = "/com0102d03closeSupplierSearch", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d02closeSupplierSearch(FormCom0102d03 formCom0102d03,
      @RequestParam("custCode") String custCode,
      @RequestParam("shopCode") String shopCode)
          throws JsonProcessingException {

    formCom0102d03.setShopCode(shopCode);
    formCom0102d03.setCustCode(custCode);
    // 検索処理
    String jsonData = com0102d03Service.getMstShopInfo(formCom0102d03);

    // 検索結果返却
    return jsonData;
  }
}