/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.service.impl
 * ファイル名:Com0101d02Service.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/17
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/17 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.FormCom0101d02;
import jp.co.daitoku_sh.dfcm.common.component.MatterContact;
import jp.co.daitoku_sh.dfcm.common.dao.impl.Com0101d02Dao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstFunction;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;

/**
 * サービスクラス.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0101d02Service extends AbsService {

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  @Autowired
  @Qualifier("com0101d02Dao")
  private Com0101d02Dao com0101d02Dao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 業務情報取得.
   * 
   * @param formCom0101d02 A model attribute
   * @param model A holder for model attributes
   * @param authCode 利用権限コード
   * @return 機能マスタから業務権限情報を取得する
   */
  public ArrayList<MstFunction> getBusinessInfo(FormCom0101d02 formCom0101d02,
      Model model, String authCode) {
    // 利用権限マスタ、機能マスタから業務権限情報を取得する
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Auth_Code", authCode);
    ArrayList<MstFunction> mstFunctions = null;
    try {
      mstFunctions = com0101d02Dao.getCom0101d02Mapper().getBusinessAuthoInfo(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return mstFunctions;
  }

  /**
   * 機能情報取得.
   * 
   * @param formCom0101d02 A model attribute
   * @param model A holder for model attributes
   * @param funcIdList 機能ID List
   * @param authCode 利用権限コード
   * @return 機能マスタから業務権限情報を取得する
   */
  public ArrayList<MstFunction> getFuncInfo(FormCom0101d02 formCom0101d02,
      Model model, ArrayList<String> funcIdList, String authCode) {
    // 利用権限マスタ、機能マスタから業務権限情報を取得する
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Auth_Code", authCode);
    parms.put("funcIdList", funcIdList);
    ArrayList<MstFunction> mstFunctions = null;
    try {
      mstFunctions = com0101d02Dao.getCom0101d02Mapper().getFuncInfo(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return mstFunctions;
  }


  /**
   * 【関数】連絡事項取得.
   * 
   * @return 連絡事項格納クラス
   */
  public MatterContact getMatterContact() {
    MatterContact contact = new MatterContact();
    Map<String, Object> parms = new HashMap<String, Object>();
    // 連絡事項取得
    ArrayList<MstGeneral> mstGenerals = null;
    try {
      mstGenerals = com0101d02Dao.getCom0101d02Mapper().getMatterContact(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    boolean isExist = false;
    for (MstGeneral general : mstGenerals) {
      // 行数
      String glCode = general.getGlCode();
      String matterContact = general.getTarget1();
      if (matterContact != null) {
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_1)) {
          contact.setMatterContact1(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_2)) {
          contact.setMatterContact2(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_3)) {
          contact.setMatterContact3(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_4)) {
          contact.setMatterContact4(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_5)) {
          contact.setMatterContact5(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_6)) {
          contact.setMatterContact6(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_7)) {
          contact.setMatterContact7(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_8)) {
          contact.setMatterContact8(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_9)) {
          contact.setMatterContact9(matterContact);
          isExist = true;
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_10)) {
          contact.setMatterContact10(matterContact);
          isExist = true;
        }
      } else {
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_1)) {
          contact.setMatterContact1("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_2)) {
          contact.setMatterContact2("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_3)) {
          contact.setMatterContact3("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_4)) {
          contact.setMatterContact4("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_5)) {
          contact.setMatterContact5("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_6)) {
          contact.setMatterContact6("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_7)) {
          contact.setMatterContact7("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_8)) {
          contact.setMatterContact8("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_9)) {
          contact.setMatterContact9("");
        }
        if (glCode.equalsIgnoreCase(CommonConst.GEN_NUMBER_LINE_10)) {
          contact.setMatterContact10("");
        }
      }
    }
    // 全ての連絡事項が ''（空文字）の場合
    if (!isExist) {
      contact.setMatterContact1(null);
      contact.setMatterContact2(null);
      contact.setMatterContact3(null);
      contact.setMatterContact4(null);
      contact.setMatterContact5(null);
      contact.setMatterContact6(null);
      contact.setMatterContact7(null);
      contact.setMatterContact8(null);
      contact.setMatterContact9(null);
      contact.setMatterContact10(null);
    }
    return contact;
  }
  
  /**
   * 初期表示.
   * 
   * @param matterContact 連絡事項格納クラス
   * @return 連絡事項格納クラス
   */
  public ArrayList<String> getMatterContactList(MatterContact matterContact) {
    ArrayList<String> list = null;
    if (matterContact != null) {
      list = new ArrayList<String>();
      list.add(matterContact.getMatterContact1());
      list.add(matterContact.getMatterContact2());
      list.add(matterContact.getMatterContact3());
      list.add(matterContact.getMatterContact4());
      list.add(matterContact.getMatterContact5());
      list.add(matterContact.getMatterContact6());
      list.add(matterContact.getMatterContact7());
      list.add(matterContact.getMatterContact8());
      list.add(matterContact.getMatterContact9());
      list.add(matterContact.getMatterContact10());
    }
    return list;
  }
}
