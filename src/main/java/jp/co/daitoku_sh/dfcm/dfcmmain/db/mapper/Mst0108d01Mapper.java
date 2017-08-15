/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0108d01Mapper.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/11/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/01 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */


package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0108d01Model;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;

public interface Mst0108d01Mapper {
	/**
	 * 
	 * @param parms
	 * @return
	 */
	List<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);
	/**
	 * 
	 * @param parm
	 * @return
	 */
	List<Mst0108d01Model> getDefautData(Map<String, Object> parm);
	/**
	 * 
	 * @param parm
	 * @return
	 */
	List<Mst0108d01Model> getItemName(Map<String, Object> parm);
	/**
	 * 
	 * @param parm
	 * @return
	 */
	ArrayList<Mst0108d01Model> getCustName(Map<String, Object> parm);
	
	 void getSearchResultCsv(Map<String, Object> parms, ResultHandler handler);
}
