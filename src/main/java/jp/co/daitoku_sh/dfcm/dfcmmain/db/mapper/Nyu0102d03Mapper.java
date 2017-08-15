/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Nyu0102d03Mapper.java
 * 
 * 作成者:都築電気
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.NyuSeikyusakiInfo;

import java.util.List;
import java.util.Map;

/**
 * 都度請求未回収設定画面用SQL Mapper.
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface Nyu0102d03Mapper {

  /**
   * 都度請求先リストの取得.
   * 
   * @param params パラメータ
   * @return 検索結果
   */
  List<NyuSeikyusakiInfo> getTsudoSeikyusakiList(Map<String, Object> params);

}
