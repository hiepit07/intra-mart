/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:ErrorInfo.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/10/05
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/05 1.00 ABV)Nhungma 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemError;

import java.util.ArrayList;


/**
 * コース情報格納クラス
 * 
 * @author ABV)Nhungma
 * @version 1.0.0
 * @since 1.0.0
 */
public class ErrorList {

  private ArrayList<ErrorInfo> errorInfo;
  private CommonGetSystemError commonGetSystemError;

  /**
   * getter/setter.
   */

  public ErrorList(CommonDao commonDao,
      ReadPropertiesFileService readPropertiesFileService,
      ArrayList<ErrorInfo> errorInfo) {
    super();
    this.errorInfo = errorInfo;
    commonGetSystemError = new CommonGetSystemError(commonDao,
        readPropertiesFileService, errorInfo);
  }
  
  /**
   * addエラーリスト.
   * @param errorInfoItem :Object contain component of error list
   */
  public void add(ErrorInfo errorInfoItem) {
    if (errorInfoItem != null) {
      this.errorInfo.add(errorInfoItem);
    } else {
      return;
    }
  }

  /**
   * outputエラーリスト.
   * @return string
   * @throws Exception for function output
   */
  public String output() throws Exception {
    String returnResult = "";
    returnResult = commonGetSystemError.output();
    return returnResult;
  }
}