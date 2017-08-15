/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Sei0101d00Mapper.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)ヒエップ 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;

/**
 * 一覧画面でのマップ
 * 
 * @author hieptl
 * @version 1.0.0
 * @since 1.0.0
 */

public interface Sei0101d00Mapper {
  List<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);

}
