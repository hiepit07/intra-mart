/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl
 * ファイル名:Com0102d04Controller.java
 * 
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/１０/１０
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 ABV)グエン リユオン ギア 新規開発
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

import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d04;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d04Service;

/**
 * URLにて指定されたサービスを呼び出すコントローラークラス。
 *
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/com/COM01-02D04/")
public class Com0102d04Controller extends AbsController {

  @Autowired
  @Qualifier("com0102d04Service")
  private Com0102d04Service com0102d04Service;

  /**
   * 
   * @param request リクエスト
   * @param model モデル
   * @param formCom0102d04 formCom0102d04
   * @param changeCodeWk 変更CodeWk
   * @param changeBranchWk 変更BranchWk
   * @param ourCompanyItemCodeWk 当社ItemCodeWk
   * @param customerCodeWk 顧客CodeWk
   * @param saleTypeWk 販売TypeWk
   * @param deadlineWk 死んlineWk
   * @param flightWk flightWk
   * @return screenMapping
   */
  @RequestMapping(value = "/")
  public String top(WebRequest request, Model model,
      FormCom0102d04 formCom0102d04,
      @RequestParam("changeCodeWk") String changeCodeWk,
      @RequestParam("changeBranchWk") String changeBranchWk,
      @RequestParam("ourCompanyItemCodeWk") String ourCompanyItemCodeWk,
      @RequestParam("customerCodeWk") String customerCodeWk,
      @RequestParam("saleTypeWk") String saleTypeWk,
      @RequestParam("deadlineWk") String deadlineWk,
      @RequestParam("flightWk") String flightWk) {

    // default メッセージを取得する。
    com0102d04Service.getDefaultMess(model);
    // 引数取得
    formCom0102d04.setChangeCodeWk(changeCodeWk);
    formCom0102d04.setChangeBranchWk(changeBranchWk);
    formCom0102d04.setOurCompanyItemCodeWk(ourCompanyItemCodeWk);
    formCom0102d04.setCustomerCodeWk(customerCodeWk);
    formCom0102d04.setSaleTypeWk(saleTypeWk);
    formCom0102d04.setDeadlineWk(deadlineWk);
    formCom0102d04.setFlightWk(flightWk);
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d04Service.getCommonDao(), null, null, null);
    int isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d04.setSearchMax(isearchMax);
    com0102d04Service.init(formCom0102d04);

    // 画面マッピング
    final String screenMapping = "com/com0102d04";

    return screenMapping;
  }

  /**
   * com0102d04getSearchResult.
   * 
   * @param request リクエスト
   * @param model モデル
   * @param formCom0102d04 formCom0102d04
   * @param changeCodeWk 変更CodeWk
   * @param changeBranchWk 変更BranchWk
   * @param ourCompanyItemCodeWk 当社ItemCodeWk
   * @param customerCodeWk 顧客CodeWk
   * @param saleTypeWk 販売TypeWk
   * @param deadlineWk 死んlineWk
   * @param flightWk flightWk
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/com0102d04getSearchInitResult", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d04getSearchInitResult(WebRequest request,
      Model model,
      FormCom0102d04 formCom0102d04,
      @RequestParam("changeCodeWk") String changeCodeWk,
      @RequestParam("changeBranchWk") String changeBranchWk,
      @RequestParam("ourCompanyItemCodeWk") String ourCompanyItemCodeWk,
      @RequestParam("customerCodeWk") String customerCodeWk,
      @RequestParam("saleTypeWk") String saleTypeWk,
      @RequestParam("deadlineWk") String deadlineWk,
      @RequestParam("flightWk") String flightWk,
      @RequestParam("searchMax") int searchMax)  
          throws JsonProcessingException {

    // 引数取得
    formCom0102d04.setChangeCodeWk(changeCodeWk);
    formCom0102d04.setChangeBranchWk(changeBranchWk);
    formCom0102d04.setOurCompanyItemCodeWk(ourCompanyItemCodeWk);
    formCom0102d04.setCustomerCodeWk(customerCodeWk);
    formCom0102d04.setSaleTypeWk(saleTypeWk);
    formCom0102d04.setDeadlineWk(deadlineWk);
    formCom0102d04.setFlightWk(flightWk);
    formCom0102d04.setSearchMax(searchMax);
    // 検索処理
    String jsonData = com0102d04Service.initSearchAction(formCom0102d04);
    // 検索結果返却
    return jsonData;
  }

  /**
   * com0102d04getSearchResult.
   * 
   * @param request リクエスト
   * @param model モデル
   * @param formCom0102d04 formCom0102d04
   * @param changeCodeWk 変更CodeWk
   * @param changeBranchWk 変更BranchWk
   * @param ourCompanyItemCodeWk 当社ItemCodeWk
   * @param customerCodeWk 顧客CodeWk
   * @param saleTypeWk 販売TypeWk
   * @param deadlineWk 死んlineWk
   * @param flightWk flightWk
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/com0102d04getSearchResult", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d04getSearchResult(WebRequest request,
      Model model,
      FormCom0102d04 formCom0102d04,
      @RequestParam("changeCodeWk") String changeCodeWk,
      @RequestParam("changeBranchWk") String changeBranchWk,
      @RequestParam("ourCompanyItemCodeWk") String ourCompanyItemCodeWk,
      @RequestParam("ourCompanyItemNameWk") String ourCompanyItemNameWk,
      @RequestParam("customerCodeWk") String customerCodeWk,
      @RequestParam("saleTypeWk") String saleTypeWk,
      @RequestParam("deadlineWk") String deadlineWk,
      @RequestParam("flightWk") String flightWk)
          throws JsonProcessingException {

    // 引数取得
    formCom0102d04.setChangeCodeWk(changeCodeWk);
    formCom0102d04.setChangeBranchWk(changeBranchWk);
    formCom0102d04.setOurCompanyItemCodeWk(ourCompanyItemCodeWk);
    formCom0102d04.setOurCompanyItemNameWk(ourCompanyItemNameWk);
    formCom0102d04.setCustomerCodeWk(customerCodeWk);
    formCom0102d04.setSaleTypeWk(saleTypeWk);
    formCom0102d04.setDeadlineWk(deadlineWk);
    formCom0102d04.setFlightWk(flightWk);
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d04Service.getCommonDao(), null, null, null);
    int isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d04.setSearchMax(isearchMax);
    // 検索処理
    String jsonData = com0102d04Service.getSearchResult(model, formCom0102d04);
    
    // 検索結果返却
    return jsonData;
  }

  /**
   * com0102d04closeSupplierSearch.
   * 
   * @param model モデル
   * @param formCom0102d04 formCom0102d04
   * @param changeCodeWk 変更CodeWk
   * @param changeBranchWk 変更BranchWk
   * @param ourCompanyItemCodeWk 当社ItemCodeWk
   * @param customerCodeWk 顧客CodeWk
   * @param saleTypeWk 販売TypeWk
   * @param deadlineWk 死んlineWk
   * @param flightWk flightWk
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/com0102d04closeSupplierSearch", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d04closeSupplierSearch(Model model,
      FormCom0102d04 formCom0102d04,
      @RequestParam("changeCodeWk") String changeCodeWk,
      @RequestParam("changeBranchWk") String changeBranchWk,
      @RequestParam("ourCompanyItemCodeWk") String ourCompanyItemCodeWk,
      @RequestParam("ourCompanyItemNameWk") String ourCompanyItemNameWk,
      @RequestParam("customerCodeWk") String customerCodeWk,
      @RequestParam("saleTypeWk") String saleTypeWk,
      @RequestParam("deadlineWk") String deadlineWk,
      @RequestParam("flightWk") String flightWk)
          throws JsonProcessingException {

    formCom0102d04.setOurCompanyItemCodeWk(ourCompanyItemCodeWk);
    formCom0102d04.setOurCompanyItemNameWk(ourCompanyItemNameWk);
    // 検索処理
    String jsonData = com0102d04Service.getDataInfo(formCom0102d04);

    // 検索結果返却
    return jsonData;
  }
}
