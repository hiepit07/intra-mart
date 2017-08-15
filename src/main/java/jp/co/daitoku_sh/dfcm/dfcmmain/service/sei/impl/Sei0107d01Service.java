/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0107d01Service.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/12/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/15 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0107d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0107d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0107d01;

/**
 * サービスクラス
 * 
 * @author NghiaNguyen
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Sei0107d01Service extends AbsService{
  
  @Autowired
  @Qualifier("sei0107d01Dao")
  private Sei0107d01Dao sei0107d01Dao;
  
  /** メッセージPropertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  

  
  @Autowired
  private ApplicationContext appContext;

  /** The operational database management object */
  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;
  
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /**
   * 業務日付取得.
   * 
   * @return 業務日付
   */
  public String getBusinessDate() {
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);
    return sysCom.getAplDate();
  }
  
  public List<MstGeneralData> getGeneralInfor() {
    // 共通部品初期化
    final CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao,
        null, null, readPropertiesFileService);
    //////////// 共通部品を使って、請求集計表区分名称を取得する START ////////////
    List<MstGeneralData> generalDataBillingShuukeiHyouKubun = systemCom
        .getMstGeneralConf("Bild_Sum_Kb", null);
    return generalDataBillingShuukeiHyouKubun;
  }
  
  public void init (Model model, FormSei0107d01 formSei0107d01) {
    //共通部品【汎用マスタ取得.汎用マスタ設定取得】
    List<MstGeneralData> clsGeneralMasterName = getGeneralInfor();
    ArrayList<String> paramMess = new ArrayList<String>();
    if (clsGeneralMasterName == null || clsGeneralMasterName.size() == 0) {
      //エラーメッセージを画面に表示する
      final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("請求集計表区分");
      DefaultMessages defaultMsg = new DefaultMessages();
      defaultMsg.setMessageCode("COM009-E");
      defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      defaultMsgLst.add(defaultMsg);
      model.addAttribute("errorMessages", defaultMsgLst);
      formSei0107d01.setIsErrorControl("1");
    } else {
      ObjCombobox element = new ObjCombobox();
      element.setCode("");
      element.setName("");
      ArrayList<ObjCombobox> comboboxs = new ArrayList<ObjCombobox>();
      comboboxs.add(element);
      for (MstGeneralData gen : clsGeneralMasterName) {
        element = new ObjCombobox();
        element.setCode(gen.getGlCode());
        element.setName(gen.getGlCode() + " : " + gen.getTarget1());
        comboboxs.add(element);
      }
      model.addAttribute("ddlSumBillList", comboboxs);
    }
    
    //【事業所コード取得】
    /**
     * Call Function get 5 Key Office to set into Form.
     */
    /*プロパティファイルから各事業所のコードを取得する*/
    formSei0107d01.setNaraJigyoCode("1");
    formSei0107d01.setChukyoJigyoCode("1");
    formSei0107d01.setFujinomiyaJigyoCode("1");
    formSei0107d01.setSapporoJigyoCode("1");
    formSei0107d01.setUtsunomiyaJigyoCode("1");
    //TODO: Waitting QA.
    if (formSei0107d01.getNaraJigyoCode().equalsIgnoreCase("")
        || formSei0107d01.getChukyoJigyoCode().equalsIgnoreCase("")
        || formSei0107d01.getFujinomiyaJigyoCode().equalsIgnoreCase("")
        || formSei0107d01.getSapporoJigyoCode().equalsIgnoreCase("")
        || formSei0107d01.getUtsunomiyaJigyoCode().equalsIgnoreCase("")) {
    //エラーメッセージを画面に表示する
      final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
      paramMess = new ArrayList<String>();
      paramMess.add("プロパティファイル");
      paramMess.add("事業所コード");
      DefaultMessages defaultMsg = new DefaultMessages();
      defaultMsg.setMessageCode("COM009-E");
      defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      defaultMsgLst.add(defaultMsg);
      model.addAttribute("errorMessages", defaultMsgLst);
      formSei0107d01.setIsErrorControl("1");
    }
  }
  
  /**
   * changeDivision 請求集計表区分.
   * @param model model
   * @param formSei0107d01 formSei0107d01 エンティティ
   * @return List
   */
  public List<RstSei0107d01> changeDivision(Model model, FormSei0107d01 formSei0107d01) {
    
    Map<String, Object> parm = new HashMap<String, Object>(); // Mapper
    List<RstSei0107d01> lst = null;
    String ddlSumBill = formSei0107d01.getDdlSumBill();
    //請求集計表区分 = "" の場合
    if (ddlSumBill.equalsIgnoreCase("")) {
        return lst;
    } else {
      //締め日一覧を取得する
      parm.put("BIZ_DATE", formSei0107d01.getBusinessDate());
      parm.put("SEIKYU_SHUKEI_HYO_KBN", ddlSumBill);
      lst = sei0107d01Dao.getSei0107d01Mapper().divisionChange(parm);
      ObjCombobox element = new ObjCombobox();
      element.setCode("");
      element.setName("");
      ArrayList<ObjCombobox> comboboxs = new ArrayList<ObjCombobox>();
      comboboxs.add(element);
      for (RstSei0107d01 jgyoCode : lst) {
        element = new ObjCombobox();
        element.setCode(jgyoCode.getShiMeBi());
        element.setName(jgyoCode.getShiMeBi());
        comboboxs.add(element);
      }
      return lst;
    }
  }
  
}
