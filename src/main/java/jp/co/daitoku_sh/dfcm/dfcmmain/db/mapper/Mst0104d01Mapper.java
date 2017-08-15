/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0104d01Mapper.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/25
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/25 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInfoMst0104d01;

/**
 * 一覧画面でのマップ
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0104d01Mapper {

  ArrayList<MstSJigyo> getSJigyoInfo(Map<String, Object> parms);

  List<MstCourseInfoMst0104d01> searchCourseList(Map<String, Object> parms);

  void getSearchResultCsv(Map<String, Object> parms, ResultHandler handler);
}