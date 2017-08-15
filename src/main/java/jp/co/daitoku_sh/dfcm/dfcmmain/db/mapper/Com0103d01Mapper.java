/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Com0103d01Mapper.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TramChu   新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.Com01Job;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;

/**
 * 一覧画面でのマップ
 * 
 * @author TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0103d01Mapper {

  ArrayList<MstSJigyoInfo> getDiaJigyo(Map<String, Object> parms);

  ArrayList<MstUser> getMstUser(Map<String, Object> parms);

  ArrayList<Com01Job> getCom01Job(Map<String, Object> parms);
}