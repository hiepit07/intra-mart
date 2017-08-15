/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.aop
 * ファイル名:MethodProgressLogAspect.java
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

package jp.co.daitoku_sh.dfcm.dfcmmain.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;

/**
 * AOP処理
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

@Aspect
@Component
public class MethodProgressLogAspect {

  private static Dfcmlogger dfcmlogger = new Dfcmlogger();

  // Startメッソドでログを記録する
  @Before("execution(* jp.co.daitoku_sh.dfcm..*.*(..))")
  public void before(JoinPoint jp) {
    StringBuilder sb = new StringBuilder("");
    sb.append("METHOD START: ");
    sb.append(jp.getTarget().getClass().getName());// クラス名
    sb.append(" - ");
    sb.append(jp.getSignature().getName());// メソッド名
    // パラメーター名
    sb.append(" ( ");
    Object[] objArray = jp.getArgs();
    for (Object obj : objArray) {
      if (obj != null) {
        sb.append(" | " + obj.toString() + " | ");
      }
    }
    sb.append(" ) ");
    dfcmlogger.info(new String(sb));
  }

  // Endメッソドでログを記録する
  @After("execution(* jp.co.daitoku_sh.dfcm..*.*(..))")
  public void after(JoinPoint jp) {
    StringBuilder sb = new StringBuilder("");
    sb.append("METHOD END: ");
    sb.append(jp.getTarget().getClass().getName());// クラス名
    sb.append(" - ");
    sb.append(jp.getSignature().getName());// メソッド名

    // パラメーター名
    sb.append(" ( ");
    Object[] objArray = jp.getArgs();
    for (Object obj : objArray) {
      if (obj != null) {
        sb.append(" | " + obj.toString() + " | ");
      }
    }
    sb.append(" ) ");

    dfcmlogger.info(new String(sb));
  }
}