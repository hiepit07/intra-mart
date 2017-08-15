/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0103d02Mapper.java
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

import jp.co.daitoku_sh.dfcm.common.db.model.MstDatIdx;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInfoMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSpecificationsConfirmationMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstUserInfoMst0103d02;

/**
 *　一覧画面でのマップ
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0103d02Mapper {
	ArrayList<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);
	MstListForm getMstListForm(Map<String, Object> parms);
	MstShop getMstShop(Map<String, Object> parms);
	MstDatIdx getMstDatIdx(Map<String, Object> parms);
	MstUserInfoMst0103d02 getStoreMaster(Map<String, Object> parms);	 
	ArrayList<MstSNohin> getDataAccess(Map<String, Object> parms);
	ArrayList<MstCourseInfoMst0103d02> getCourseInformationLst(Map<String, Object> parms);
	ArrayList<MstSpecificationsConfirmationMst0103d02> getCommonSpecificationsConfirmation(Map<String, Object> parms);
}
