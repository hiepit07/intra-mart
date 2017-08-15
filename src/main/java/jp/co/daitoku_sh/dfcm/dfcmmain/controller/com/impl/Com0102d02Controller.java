/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl
 * ファイル名:Com0102d02Controller.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/03
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/03 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
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

import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d02Service;

/**
 * 得意先検索子画面コントローラー
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/com/COM01-02D02/")
public class Com0102d02Controller extends AbsController {

  @Autowired
  @Qualifier("com0102d02Service")
  private Com0102d02Service com0102d02Service;

  /**
   * 画面表示処理
   *
   * @param request リクエスト
   * @param model モデル
   * @param formCom0102d02 フォームクラス
   * @param jigyoshoCode 事業所コード
   * @param custCode 得意先コード
   * @param shopKb 店舗区分
   * @param searchKb 検索対象区分
   * @return String（JSPファイル）
   */
  @RequestMapping(value = "/")
  public String top(WebRequest request, Model model,
      FormCom0102d02 formCom0102d02,
      @RequestParam("jigyoshoCode") String jigyoshoCode,
      @RequestParam("custCode") String custCode,
      @RequestParam("shopKb") String shopKb,
      @RequestParam("searchKb") String searchKb) {

    String screenMapping = "com/com0102d02";

    // 引数取得
    formCom0102d02.setJigyoshoCode(jigyoshoCode); // 事業所コード
    formCom0102d02.setCustCode(custCode); // 得意先コード
    formCom0102d02.setShopKb(shopKb); // 店舗区分
    formCom0102d02.setSearchKb(searchKb); // 検索対象区分

    // 初期処理
    com0102d02Service.initAction(formCom0102d02, model);

    // 表示するJSPファイル名を返却
    return screenMapping;
  }

  /**
   * 一覧表示データ取得
   *
   * @param formCom0102d02 フォームクラス
   * @return String（JSON）
   * @throws JsonProcessingException JSON変換エラー
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", value = "com0102d02getSearchResult", method = RequestMethod.POST)
  public @ResponseBody String com0102d02getSearchResult(
      FormCom0102d02 formCom0102d02) throws JsonProcessingException {

    // 検索処理
    String jsonData = com0102d02Service.searchCustomer(formCom0102d02);

    // 検索結果返却
    return jsonData;
  }

  /**
   * 一覧選択処理
   *
   * @param custCode 得意先コード
   * @return String（JSON）
   * @throws JsonProcessingException JSON変換エラー
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", value = "com0102d02closeSupplierSearch", method = RequestMethod.POST)
  public @ResponseBody String com0102d02closeSupplierSearch(String custCode)
      throws JsonProcessingException {

    // 検索処理
    String jsonData = com0102d02Service.selectCustomer(custCode);

    // 検索結果返却
    return jsonData;
  }

}
