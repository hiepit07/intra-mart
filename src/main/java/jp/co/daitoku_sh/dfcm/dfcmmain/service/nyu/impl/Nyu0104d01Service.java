/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl
 * ファイル名:Nyu0104d01Service.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/12/16
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/16 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.NyuConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl.Nyu0104d01Dao;

/**
 * 入金登録用 Service
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Nyu0104d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private Nyu0104d01Dao nyu0104d01Dao;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * DEFAULT MESSAGE の取得.
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
    
    DefaultMessages defaultMsg = new DefaultMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    List<DefaultMessages> defaultMsgList = new ArrayList<DefaultMessages>();
    
    defaultMsg = new DefaultMessages();
    paramMess = new ArrayList<String>();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    paramMess.add("入金予定日");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", paramMess));
    defaultMsgList.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM008-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM008-E", null));
    defaultMsgList.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgList);
  }

  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する。
   * 
   * @param formNyu0104d01:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo(FormNyu0104d01 formNyu0104d01, Model model) {
    ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();
    List<MstSJigyo> lstMstSJigyoInfo = new ArrayList<MstSJigyo>();
    lstMstSJigyoInfo = listSJigyo(formNyu0104d01);
    if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
      ObjCombobox objectCombobox = new ObjCombobox();
      objectCombobox.setCode("");
      objectCombobox.setName("");
      lstMstSJigyoInfoReturn.add(objectCombobox);
      for (MstSJigyo mstSJigyoInfo : lstMstSJigyoInfo) {
        objectCombobox = new ObjCombobox();
        objectCombobox.setCode(mstSJigyoInfo.getJgycd());
        objectCombobox.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
            .getJgymei());
        lstMstSJigyoInfoReturn.add(objectCombobox);
      }
      model.addAttribute("lstJigyoInfo", lstMstSJigyoInfoReturn);
      return true;
    }
    return false;
  }

  /**
   * list data of MstSJigyo.
   * 
   * @param formNyu0104d01:フォーム
   * @return list MstSJigyo
   */
  private List<MstSJigyo> listSJigyo(FormNyu0104d01 formNyu0104d01) {
    List<MstSJigyo> lstMstJigy = null;
    try {
      MstSJigyoExample mstSJigyoExample = new MstSJigyoExample();
      MstSJigyoExample.Criteria criteria = mstSJigyoExample.createCriteria();
      criteria.andDelflgEqualTo(NyuConst.DEL_FLAG);
      criteria.andStrymdLessThanOrEqualTo(formNyu0104d01
          .getBusinessDate());
      criteria.andEndymdGreaterThanOrEqualTo(formNyu0104d01
          .getBusinessDate());
      mstSJigyoExample.setOrderByClause("JGYCD");
      lstMstJigy = nyu0104d01Dao.getMstSJgyoMapper().selectByExample(
          mstSJigyoExample);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstMstJigy;
  }

  /**
   * ヘッダ部を表示し、以下の初期値をセットする
   * 
   * @param formNyu0104d01:フォーム
   * @param model:モデル
   */
  public void setViewItem(FormNyu0104d01 formNyu0104d01, Model model) {
    if (formNyu0104d01.getSysAdminFlag().equalsIgnoreCase("1")) {
      formNyu0104d01.setDdlJigyoCode(formNyu0104d01.getLoginJigyoshoCode());
      formNyu0104d01.setJigyoshoCodeWk(formNyu0104d01
          .getLoginJigyoshoCode());
    }
    String date = formNyu0104d01.getBusinessDate().toString();

    formNyu0104d01.setTxtPaymentDateFrom(date.substring(0, 4) + "/" + date
        .substring(4, 6) + "/" + date.substring(6));
    formNyu0104d01.setTxtPaymentDateTo(date.substring(0, 4) + "/" + date
        .substring(4, 6) + "/" + date.substring(6));
  }
}