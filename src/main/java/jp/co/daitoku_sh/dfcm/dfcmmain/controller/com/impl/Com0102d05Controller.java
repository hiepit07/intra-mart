/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl
 * ファイル名:Com0102d05Controller.java
 *
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/10/14
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/14 1.00 TDK)グエン　リユオン　ギア　 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl;

/**
 * URLにて指定されたサービスを呼び出すコントローラークラス
 *
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
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
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d05;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d05Service;

@Controller
@RequestMapping(value = "/com/COM01-02D05/")
public class Com0102d05Controller extends AbsController {

  @Autowired
  @Qualifier("com0102d05Service")
  private Com0102d05Service com0102d05Service;

  /**
   * 画面表示処理.
   * 
   * @param model:モデル
   * @param formCom0102d05:フォム
   * @param userCodeWk:ユザコド
   * @param userRoleWk:ユザ　の　役割
   * @param myOfficeWk:私　の　事務所
   * @return　screenMapping
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormCom0102d05 formCom0102d05,
      @RequestParam("userCodeWk") String userCodeWk,
      @RequestParam("userRodeWk") String userRodeWk,
      @RequestParam("myOfficeWk") String myOfficeWk) {

    // default メッセージを取得する。
    com0102d05Service.getDefaultMess(model);

    formCom0102d05.setUserCodeWk(userCodeWk);
    formCom0102d05.setUserRoleWk(userRodeWk);
    formCom0102d05.setMyOfficeWk(myOfficeWk);
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d05Service.getCommonDao(), null, null, null);
    int isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d05.setSearchMax(isearchMax);
    // 画面マッピング
    final String screenMapping = "com/com0102d05";

    return screenMapping;
  }

  /**
   * 
   * @param request:request
   * @param formCom0102d05:フォム
   * @param userCodeWk:ユザコド
   * @param userNameWk:username
   * @param searchMax:searchMax
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
     value = "/com0102d05getSearchResult", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d05getSearchResult(WebRequest request,
      FormCom0102d05 formCom0102d05,
      @RequestParam("userCodeWk") String userCodeWk,
      @RequestParam("userNameWk") String userNameWk,
      @RequestParam("searchMax") int searchMax)
          throws JsonProcessingException {

    // 最大件数値を取得
    formCom0102d05.setSearchMax(searchMax);

    // 引数取得
    formCom0102d05.setUserCodeWk(userCodeWk); // 担当者コード
    formCom0102d05.setUserNameWk(userNameWk);// 担当者名

    // 検索処理
    String jsonData = com0102d05Service.searchMstUser(formCom0102d05);

    // 検索結果返却
    return jsonData;
  }
  
  /**
   * 
   * @param request:request
   * @param formCom0102d05:フォム
   * @param userCodeWk:ユザコド
   * @param userNameWk:username
   * @param searchMax:searchMax
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
     value = "/com0102d05getSearchInitResult", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d05getSearchInitResult(WebRequest request,
      FormCom0102d05 formCom0102d05,      
      @RequestParam("userCodeWk") String userCodeWk,
      @RequestParam("userRodeWk") String userRoleWk,
      @RequestParam("myOfficeWk") String myOfficeWk,
      @RequestParam("searchMax") int searchMax)
          throws JsonProcessingException {
   
    // 引数取得
    formCom0102d05.setUserCodeWk(userCodeWk);
    formCom0102d05.setUserRoleWk(userRoleWk);
    formCom0102d05.setMyOfficeWk(myOfficeWk);
    formCom0102d05.setSearchMax(searchMax);    

    // 検索処理
    String jsonData = com0102d05Service.initSearchAction(formCom0102d05);

    // 検索結果返却
    return jsonData;
  }
  
  /**
   * com0102d05closeSupplierSearch.
   * @param model:モデル
   * @param formCom0102d05:form
   * @param userCodeWk : userCode
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/com0102d05closeSupplierSearch", method = RequestMethod.POST)
  @ResponseBody
  public  String com0102d05closeSupplierSearch(Model model,
      FormCom0102d05 formCom0102d05, 
      @RequestParam("userCodeWk") String userCodeWk)
          throws JsonProcessingException {

    formCom0102d05.setUserCodeWk(userCodeWk);
    // 検索処理
    String jsonData = com0102d05Service.selectUser(model, formCom0102d05);

    // 検索結果返却
    return jsonData;
  }
}
