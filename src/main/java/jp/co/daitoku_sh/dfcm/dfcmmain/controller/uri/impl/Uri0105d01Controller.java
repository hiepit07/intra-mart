/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Uri0105d01Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/12/08
 * 
 * <p> 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.uri.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.FormUri0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl.Uri0105d01Service;

/**
 * コントローラクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/uri/URI01-05D01/")
public class Uri0105d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("uri0105d01Service")
  private Uri0105d01Service uri0105d01Service;

  /**
   * 初期表示処理(メニュー画面で「修正データ照会」が選択された場合など).
   * 
   * @param model:モデル
   * @param request:httpリクエスト
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request, FormUri0105d01 formUri0105d01) {

    // ビュー画面を呼ぶ
    String strScreen = "uri/uri0105d01";

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String strPath = Util.checkSession(model, request,
        CommonConst.URI0105D00_SCREEN_NAME);
    if (!strPath.equalsIgnoreCase("")) {
      return strPath;
    }

    uri0105d01Service.init(request, formUri0105d01, model);

    return strScreen;
  }
}
