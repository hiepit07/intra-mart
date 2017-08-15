/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.controller.impl
 * ファイル名:AuthController.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/11/09
 *  
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/09 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 *  
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.controller.impl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * 
 * The AuthController is an controller layer that
 * simple logout user from the system.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/")
public class AuthController extends AbsController {

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * Process the logout event
   * 
   * @return The login screen.
   */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      Calendar cal = Calendar.getInstance();
      String now = DateUtil.setFormat(cal.getTime(),
          CommonConst.LOG_DATE_FOMAT_YMDHMS);
      logger.loginLog(readPropertiesFileService, "ログアウトをしました（" + now
          + "　担当者コード：" + session.getAttribute("UserCode")
          + "　IPアドレス：" + request.getRemoteAddr() + "）");
      session.invalidate();
    }
    return "redirect:/";
  }
}
