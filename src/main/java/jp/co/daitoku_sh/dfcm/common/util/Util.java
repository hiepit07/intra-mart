/**
 * パッケージ名: jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:Util.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Component
 * 共通関数
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

@Component
public class Util {


  /**
   * タグを無害化します.
   * @param str:データ変換
   * @return　変換したデータ
   */
  public static String convertSanitize(String str) {
    if (str == null) {
      return str;
    }
    str = str.replaceAll("&", "&amp;");
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    str = str.replaceAll("\"", "&quot;");
    str = str.replaceAll("'", "&#39;");
    str = str.replaceAll("%", "&#36;");
    return str;
  }

  /**
   * 無害化されたタグを元に戻します.
   * 
   * @param str:データ変換
   * @return 変換したデータ
   */
  public static String convertUnsanitize(String str) {
    if (str == null) {
      return str;
    }
    str = str.replaceAll("&#39;", "'");
    str = str.replaceAll("&quot;", "\"");
    str = str.replaceAll("&gt;", ">");
    str = str.replaceAll("&lt;", "<");
    str = str.replaceAll("&amp;", "&");
    str = str.replaceAll("&#36;", "%");
    return str;
  }

  /**
   * 文字の前に ０ を追加する.
   * 
   * @param str：編集したい文字
   * @param size：０数
   * @return String：”０”を追加された文字
   */
  public static String addLeadingZeros(String str, int size) {
    while (str.length() < size) {
      str = "0" + str;
    }
    return str;
  }

  /**
   * MD5でパスワードを作成する.
   * @param source:パスワード
   * @return　MD5を変換したパスワード
   * @throws NoSuchAlgorithmException エラー画面
   */
  public static String createDigest(String source) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");

    byte[] data = source.getBytes();
    md.update(data);

    byte[] digest = md.digest();

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < digest.length; i++) {
      int intDigest = (0xFF & digest[i]);
      if (intDigest < 16) {
        sb.append("0");
      }        
      sb.append(Integer.toHexString(intDigest));
    }
    return sb.toString();
  }
  
  /**
   * Checking session data.
   * @param model:モデル
   * @param request:httpリクエスト
   * @param com0101d03ScreenName:画面名
   * @return 画面名
   */
  public static String checkSession(Model model, HttpServletRequest request,
      String com0101d03ScreenName) {
    String path = "";
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("UserCode") == null) {
      path = "redirect:/";
    } else {
      model.addAttribute("persName", session.getAttribute("UserNm"));
      model.addAttribute("compName", session.getAttribute("jgymei"));
      model.addAttribute("screenName", com0101d03ScreenName);
    }
    return path;
  }
  
  /**
   * Get contents from session.
   * @param request:httpリクエスト
   * @return セッションの情報
   */
  public static Map<String, Object> getContentsFromSession(
      HttpServletRequest request) {
    Map<String, Object> map = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      map = new HashMap<String, Object>();
      map.put("UserCode", session.getAttribute("UserCode") == null ? ""
          : session.getAttribute("UserCode").toString());
      map.put("UserNm", session.getAttribute("UserNm") == null ? ""
          : session.getAttribute("UserNm").toString());
      map.put("UserNmKana", session.getAttribute("UserNmKana") == null ? ""
          : session.getAttribute("UserNmKana").toString());
      map.put("AuthCode", session.getAttribute("AuthCode") == null ? ""
          : session.getAttribute("AuthCode").toString());
      map.put("SysAdminFlg", session.getAttribute("SysAdminFlg") == null ? ""
          : session.getAttribute("SysAdminFlg").toString());
      map.put("JigyoshoCode", session.getAttribute("JigyoshoCode") == null ? ""
          : session.getAttribute("JigyoshoCode").toString());
      map.put("jgymei", session.getAttribute("jgymei") == null ? ""
          : session.getAttribute("jgymei").toString());
      map.put("currDate", session.getAttribute("currDate") == null ? ""
          : session.getAttribute("currDate"));
    }
    return map;
  }
  
  /**
   * check if a String is a numeric type in.
   * @param str:数値入力
   * @return　true 普通 false エラー
   */
  public static boolean isNumeric(String str) {
    try {
      Double.parseDouble(str);
    } catch (NumberFormatException nfe) {
      return false;  
    }  
    return true;  
  }
  
  /**
   * 入力チェック処理.
   * 
   * @param val:チェックデータ
   * @param emptyFlg:必須チェックフラグ
   * @param type:型
   * @param len:桁数
   * @return null: エラー
   */
  public static String checkItem(String val, boolean emptyFlg, int type, int len) {
    String error = null;
    if (emptyFlg) {
      error = InputCheckCom.chkEmpty(val);
      if (error != null) {
        return error;
      }
    }
    error = InputCheckCom.chkType(val, type);
    if (error != null) {
      return error;
    }
    error = InputCheckCom.chkLength(val, len);
    if (error != null) {
      return error;
    }
    return error;
  }

  /**
   * 文字の空白を削除する
   * 
   * @param val ： 編集したい文字
   * @return String ： 編集された文字
   */
  public static String removeWhitespaces(String val) {
    val = val.trim().replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
    return val;
  }
  
  
}
