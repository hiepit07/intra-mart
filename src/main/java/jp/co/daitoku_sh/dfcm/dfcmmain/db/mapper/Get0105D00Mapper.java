/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Get0105D00Mapper.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/21
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/21 1.00 ABV)HiepTruong
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetZandaka;

/**
 * Get0105D00Mapperクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */

public interface Get0105D00Mapper {
  /**
   * 売上債権状況情報取得.
   *
   * @param params データ取得条件
   * @return 売上債権状況情報
   */
  void getUrikake(Map<String, Object> params, ResultHandler handler);
  /**
   * 残高理由取得取得.
   *
   * @param params データ取得条件
   * @return残高理由取得
   */
  ArrayList<GetZandaka> getZandaka(Map<String, Object> params);
}
