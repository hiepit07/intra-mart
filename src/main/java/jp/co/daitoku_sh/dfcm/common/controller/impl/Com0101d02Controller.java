/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.controller.impl
 * ファイル名:Com0101d02Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/15 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.controller.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.FormCom0101d02;
import jp.co.daitoku_sh.dfcm.common.component.MatterContact;
import jp.co.daitoku_sh.dfcm.common.component.Menu;
import jp.co.daitoku_sh.dfcm.common.component.Url;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstFunction;
import jp.co.daitoku_sh.dfcm.common.service.impl.Com0101d02Service;
import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * The Com0101d02Controller is an controller layer that
 * simple create the main menu for system.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/com/COM01-01D02/")
public class Com0101d02Controller extends AbsController{

  @Autowired
  @Qualifier("com0101d02Service")
  private Com0101d02Service com0101d02Service;

  /**
   * 初期表示
   * 
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, FormCom0101d02 formCom0101d02,
      Model model) {
    // 【セッション取得】
    // セッション情報を[変数]セッションにセットする
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.COM0101D02_SCREEN_NAME);
    if (!path.equalsIgnoreCase(CommonConst.EMPTY)) {
      return path;
    }
    Map<String, Object> map = Util.getContentsFromSession(request);
    String authCode = String.valueOf(map.get("AuthCode"));
    // 連絡事項を取得する
    MatterContact matterContact = com0101d02Service.getMatterContact();

    // 業務情報取得
    ArrayList<MstFunction> businessAuthoInfos = com0101d02Service.getBusinessInfo(
        formCom0101d02, model, authCode);
    
    ArrayList<String> funcIdList = null;
    if (businessAuthoInfos != null && businessAuthoInfos.size() > 0) {
      funcIdList = new ArrayList<String>();
      for (MstFunction func : businessAuthoInfos) {
        funcIdList.add(func.getFuncId());
      }
    }
    // 機能情報取得
    ArrayList<MstFunction> funcInfos = com0101d02Service.getFuncInfo(
        formCom0101d02, model, funcIdList, authCode);

    // 【画面セット】
    ArrayList<String> matterContactList = com0101d02Service
        .getMatterContactList(matterContact);

    // 初期表示
    model.addAttribute("matterContactList", matterContactList);

    // メニュー表示
    ArrayList<Menu> menuList = null;
    Menu menu = null;
    ArrayList<Url> urlList = null;
    Url url = null;
    // 取得した業務数分、3-2～3-3を繰返す
    if (businessAuthoInfos != null && businessAuthoInfos.size() > 0) {
      menuList = new ArrayList<Menu>();
      for (MstFunction busInfo : businessAuthoInfos) {
        if (funcInfos != null && funcInfos.size() > 0) {
          menu = new Menu();
          menu.setBusinessFuncName(busInfo.getGyFuncMenuNm());
          urlList = new ArrayList<Url>();
          for (MstFunction funcInfo : funcInfos) {
            url = new Url();
            // 取得した機能数分、3-3を繰返す
            if (busInfo.getFuncId().trim().equalsIgnoreCase(funcInfo
                .getGyBunrui().trim())) {
              // [変数]機能ID =FNC2.機能ID
              url.setFuncId(funcInfo.getFuncId());
              // 画面]機能名 =FNC2.業務機能名称（メニュー表示用）
              url.setFuncName(funcInfo.getGyFuncMenuNm());
              // [変数]URL=FNC2.パス+FNC2.プログラムID
              url.setUrl(request.getContextPath() + "/" + funcInfo.getPath() + "/"
                  + funcInfo.getPgId() + "/");
              urlList.add(url);
            }
          }
          menu.setUrlList(urlList);
          menuList.add(menu);
        }
      }
    }
    model.addAttribute("menuList", menuList);
    return "common/com0101d02";
  }
}
