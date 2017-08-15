/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.db.mapper
 * ファイル名:BildCodeMapper.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/24
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/24 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.Map;

/**
 * 請求先コード取得用Mapper
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BildCodeMapper {

  /** 請求先コード取得 */
  String getBildCode(Map<String, Object> parms);

}
