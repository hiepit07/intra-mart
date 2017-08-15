/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.juc.impl
 * ファイル名:DelDialogJucUriController
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/11/30
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/30 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.juc.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * 取消処理ダイアログ画面コントローラー
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/juc/DelDialogJucUri")
public class DelDialogJucUriController {

  /**
   * 画面表示処理
   *
   * @param request リクエスト
   * @param model モデル
   * @param dspMode 画面表示モード（1：受注、2：売上）
   * @return String（JSPファイル）
   */

  @RequestMapping(value = "/")
  public String top(WebRequest request, Model model,
      @RequestParam("dspMode") String dspMode) {

    // 引数に応じて表示内容を制御
    String dspModeTitle = null; // 表示タイトル
    if (dspMode.equals("1")) {
      dspModeTitle = "受注"; // 受注モード
    } else {
      dspModeTitle = "売上"; // 売上モード
    }

    model.addAttribute("dspModeTitle", dspModeTitle); // 取得した表示タイトルをセット

    return "juc/delDialogJucUri";
  }

}
