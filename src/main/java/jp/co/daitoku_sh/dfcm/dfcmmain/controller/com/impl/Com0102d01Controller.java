/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl
 * ファイル名:Com0102d01Controller.java
 *
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/09/03
 *
 * <p>履歴 
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/02 1.00 ABV)グエン　リユオン　ギア 新規開発
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
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d01Service;

/**
 * URLにて指定されたサービスを呼び出すコントローラークラス
 *
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/com/COM01-02D01/")
public class Com0102d01Controller extends AbsController {

  @Autowired
  @Qualifier("com0102d01Service")
  private Com0102d01Service com0102d01Service;

  /**
   * 画面表示処理.
   * 
   * @param request リクエスト
   * @param model モデル
   * @param formCom0102d01 フォームクラス
   * @param txtChainCode チェーンコード
   * @param txtChainBranch チェーン枝番
   * @return String（JSPファイル）
   */
  @RequestMapping(value = "/")
  public String top(WebRequest request, Model model,
      FormCom0102d01 formCom0102d01,
      @RequestParam("txtChainCode") String txtChainCode,
      @RequestParam("txtChainBranch") String txtChainBranch) {
    
    // default メッセージを取得する。
    com0102d01Service.getDefaultMess(model);
    
    // 引数取得
    formCom0102d01.settxtChainCode(txtChainCode); // チェーンコード
    formCom0102d01.settxtChainBranch(txtChainBranch);// チェーン枝番
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d01Service.getCommonDao(), null, null, null);
    int isearchMax = 0;
    isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d01.setSearchMax(isearchMax);
       
    String screenMapping = "com/com0102d01";
    // 表示するJSPファイル名を返却
    return screenMapping;
  }

  /**
   * 検索処理.
   * 
   * @param request:request
   * @param formCom0102d01:formCom0102d01
   * @param txtChainCode:txtChainCode
   * @param txtChainBranch:txtChainBranch
   * @param txtChainName:txtChainName
   * @param date:date
   * @return jsonData
   * @throws JsonProcessingException e
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/com0102d01getSearchResult", method = RequestMethod.POST)
  @ResponseBody
  public String com0102d01getSearchResult(WebRequest request,
      FormCom0102d01 formCom0102d01,Model model,
      @RequestParam("txtChainCode") String txtChainCode,
      @RequestParam("txtChainBranch") String txtChainBranch,
      @RequestParam("txtChainName") String txtChainName)
          throws JsonProcessingException {

    // 引数取得
    formCom0102d01.settxtChainCode(txtChainCode); // チェーンコード
    formCom0102d01.settxtChainBranch(txtChainBranch);// チェーン枝番
    formCom0102d01.settxtChainName(txtChainName);// チェーン名
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        com0102d01Service.getCommonDao(), null, null, null);
    int isearchMax = commonGetSystemCom.getCodeSearchMax();
    formCom0102d01.setSearchMax(isearchMax);
    
    // 検索処理
    String jsonData = com0102d01Service.searchMsChain(formCom0102d01,model);

    // 検索結果返却
    return jsonData;
  }


}
