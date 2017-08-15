/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl
 * ファイル名:Sei0101d01Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/27
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)ヒエップ 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.Sei0101d01Service;

/**
 * コントローラクラス
 * 
 * @author hieptl
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/sei/SEI01-01D00/")
public class Sei0101d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("sei0101d01Service")
  private Sei0101d01Service sei0101d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0102d02 formMst0102d02,
      @ModelAttribute("formMst0102d01") FormMst0102d01 formMst0102d01,
      @ModelAttribute("userCode") String userCode,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("chainCode") String chainCode,
      @ModelAttribute("chainEda") String chainEda) {
 
    String path = Util.checkSession(model, request,
        CommonConst.SEI0101D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    String strScreen = "sei/sei0101d01";
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get("SysAdminFlg"));
    formMst0102d01.setSysAdminFlag(sysAdminFlag);

    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get("JigyoshoCode"));
    formMst0102d01.setLoginJigyouShoCode(loginJigyouShoCode);

    // ビュー画面を呼ぶ

    // 画面のデータを初期化
    sei0101d01Service.init(model, formMst0102d01, sysAdminFlag);

    return strScreen;
  }
}
