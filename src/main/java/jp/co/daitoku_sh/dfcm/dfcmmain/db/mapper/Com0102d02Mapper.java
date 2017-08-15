/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Com0102d02Mapper
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/03
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/03 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;

/**
 * 得意先検索子画面用Mapper
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0102d02Mapper {

  // 事業所コンボデータ取得用SQL
  List<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);

  // 一覧データ取得用SQL
  List<MstCustomer> getSearchResult(Map<String, Object> parms);
}
