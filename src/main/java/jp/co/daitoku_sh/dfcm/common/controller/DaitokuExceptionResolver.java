/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.controller
 * ファイル名:DaitokuExceptionResolver.java
 *
 * <p>
 * 作成者:都築電気株式会社
 * 作成日:2015/12/07
 *
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/07 1.00 都築）安延 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;

/**
 * システム全体の例外ハンドラクラス
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class DaitokuExceptionResolver implements HandlerExceptionResolver {

  Dfcmlogger logger = new Dfcmlogger();

  /**
   * 例外ハンドラクラス
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param handler Object
   * @param ex Exception
   */
  @Override
  public ModelAndView resolveException(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex) {

    Calendar cal1 = Calendar.getInstance();
    int year = cal1.get(Calendar.YEAR);
    int month = cal1.get(Calendar.MONTH) + 1;
    int day = cal1.get(Calendar.DATE);
    int hour = cal1.get(Calendar.HOUR_OF_DAY);
    int minute = cal1.get(Calendar.MINUTE);
    int second = cal1.get(Calendar.SECOND);
   String sysDateTime = year + "/" + month + "/" + day + " "
                          + " " + hour + ":" + minute + ":" + second;

    logger.error("▼共通エラー処理開始▼");
    logger.error("発生エラー： " + ex.toString());
    StackTraceElement[] ste = ex.getStackTrace();
    for (int i = 0; i < ste.length; i++) {
      logger.error("  at " + ste[i].getClassName() + "." + ste[i]
          .getMethodName() + "(" + ste[i].getFileName() + ":" + ste[i]
              .getLineNumber() + ")");
    }
    ModelAndView mav = new ModelAndView();
    mav.addObject("message", "システムエラーが発生しました。画面のハードコピーを取得し、システム管理者へ連絡してください。");
    mav.addObject("sysDateTime", sysDateTime);
    mav.setViewName("com/error");

    logger.error("▲共通エラー処理終了▲");
    return mav;
  }

}
