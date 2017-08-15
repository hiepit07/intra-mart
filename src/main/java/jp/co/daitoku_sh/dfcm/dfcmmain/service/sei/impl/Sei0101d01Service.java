/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0101d01Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/27
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)ヒエップ 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0101d00Dao;
/**
 * サービスクラス
 * 
 * @author hieptl
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Sei0101d01Service extends AbsService{
  /** メッセージPropertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  
  @Autowired
  @Qualifier("sei0101d00Dao")
  private Sei0101d00Dao sei0101d01Dao;
  
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  
  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }
  
  
  public Sei0101d00Dao getSei0101d01Dao() {
    return sei0101d01Dao;
  }

  
  public void setSei0101d01Dao(Sei0101d00Dao sei0101d01Dao) {
    this.sei0101d01Dao = sei0101d01Dao;
  }

  /**
   * 業務日付を取得する.
   * 
   * @return 業務日付
   */
  public String getBusinessDate() {
    String strResult = null;
    try {
      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 共通部品を呼ぶ
      strResult = systemCom.getAplDate();
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return strResult;
  }
  /**
   * Defaultメッセージの取得.
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
   
  }
  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する.
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo_Dll(FormMst0101d01 formMst0101d02, Model model) {
    try {
      ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();      
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", formMst0101d02.getBusinessDate().toString());
      params.put("strSystemAdminFlg", formMst0101d02.getSysAdminFlag());
      params.put("loginJigyoshoCode", formMst0101d02.getLoginJigyouShoCode());
      ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = null;
      lstMstSJigyoInfo = (ArrayList<MstSJigyoInfo>) sei0101d01Dao.getSei0101d01Mapper().getSJigyoInfo(
          params);
      if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstSJigyoInfoReturn.add(firstObject);
        for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstSJigyoInfo.getJgycd());
          tempObj.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
              .getJgymei());
          lstMstSJigyoInfoReturn.add(tempObj);
        }
        model.addAttribute("ShozokuClassList", lstMstSJigyoInfoReturn);
        return true;
      } else {
        return false;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }
  
  /**
   * フォームの初期化.
   * 
   * @param model ： フォームのModel
   * @param formMst0102d01 ： 画面のフォーム
   * @param sysManagerFlag ： システム管理者フラグ値
   */
  public void init(Model model, FormMst0102d01 formSei0101d01,
      String sysAdminFlag) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();

      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null,
          readPropertiesFileService);

      // 業務日付を取得する
      String businessDate = systemCom.getAplDate();
      if (businessDate == null) {
        // 業務日付 ＝ Nullの場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("システムコントロール");
        paramMess.add("業務日付");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E",
            paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return;
      }
      formSei0101d01.setBusinessDate(Integer.parseInt(businessDate));
    if (sysAdminFlag.equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_VALID)) {
      // 事業所マスタデータ取得
      MstSJigyoExample jigyouExample = new MstSJigyoExample();
      MstSJigyoExample.Criteria jigyouCriteria = jigyouExample
          .createCriteria();
      jigyouCriteria.andDelflgEqualTo(CommonConst.DEL_FLG_OFF);
      jigyouCriteria.andStrymdLessThan(formSei0101d01.getBusinessDate());
      jigyouCriteria.andEndymdGreaterThan(formSei0101d01.getBusinessDate());
      jigyouExample.setOrderByClause("JGYCD");
      List<MstSJigyo> jigyouData = sei0101d01Dao.getMstSJigyoMapper()
          .selectByExample(jigyouExample);
      if (jigyouData == null || jigyouData.isEmpty()) {
        // 事業所情報格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタ");
        paramMess.add("事業所情報");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return;
      }
      ArrayList<ObjCombobox> jigyouResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      ObjCombobox firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      jigyouResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < jigyouData.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(jigyouData.get(i).getJgycd());
        tempObj.setName(jigyouData.get(i).getJgymei());
        jigyouResultList.add(tempObj);
      }
      // 取得した[変数]事業所名称格納クラスを、事業所へセットする
      model.addAttribute("arrListJigyouSho", jigyouResultList);
    }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }
  }
}
