/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0103d01Mapper.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00                  ABV)コイー        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/
package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstStoreInfoMst0103d01;

/**
 *　一覧画面でのマップ
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0103d01Mapper {
	ArrayList<MstStoreInfoMst0103d01> getMstStoreInfoMst0103d01(Map<String, Object> parms);
	ArrayList<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);
	void getMstStoreInfoMst0103d01(Map<String, Object> parms, ResultHandler handler);
}