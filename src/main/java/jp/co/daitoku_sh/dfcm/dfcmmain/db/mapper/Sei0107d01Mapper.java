/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Sei0107d01Mapper.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/12/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/15 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0107d01;

/**
 * 請求一覧画面用SQL Mapper
 * 
 * @author NghiaNguyen
 * @version 1.0.0
 * @since 1.0.0
 *
 */

public interface Sei0107d01Mapper {
  /**
   * divisionChange.
   * 
   * @param parms parms1
   * @return list
   */
  List<RstSei0107d01> divisionChange(Map<String, Object> parms);
  
  /**
   * bildInformationList.
   * 
   * @param parms parms1
   * @return list
   */
  List<RstSei0107d01> bildInformationList(Map<String, Object> parms);
}
