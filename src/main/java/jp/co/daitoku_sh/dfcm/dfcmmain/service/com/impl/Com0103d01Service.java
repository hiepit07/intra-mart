/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl
 * ファイル名:Com0103d01Service.java
 *
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TramChu 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.db.model.TblCom01JobExec;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl.Com0103d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.Com01Job;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.FormCom0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ReprocessJobLst;

/**
 * サービスクラス
 * 
 * @author TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Com0103d01Service extends AbsService {

  @Autowired
  @Qualifier("com0103d01Dao")
  private Com0103d01Dao com0103d01Dao;
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;

  // DAOの定義
  public Com0103d01Dao getCom0103d01Dao() {
    return com0103d01Dao;
  }

  public void setCom0103d01Dao(Com0103d01Dao com0103d01Dao) {
    this.com0103d01Dao = com0103d01Dao;
  }

  /**
   * Defaultメッセージの取得
   * @exception
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<DefaultMessages> defaultMsgLst = null;
    defaultMsgLst = new ArrayList<DefaultMessages>();
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess = new ArrayList<String>();
    paramMess.add("再処理実行");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM016-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM033-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM033-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM024-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM024-E", null));
    defaultMsgLst.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  public void initScreenWithConditional(FormCom0103d01 formCom0103d01Back,
      FormCom0103d01 formCom0103d01) {

  }

  /**
   * 事業所情報検索
   * 
   * @param formCom0103d01 form display
   * @param model big
   * @return boolean return
   */
  public boolean setDataDiaJigyo(FormCom0103d01 formCom0103d01, Model model) {
    ArrayList<ObjCombobox> lstDiaJigyo = new ArrayList<ObjCombobox>();
    ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = new ArrayList<MstSJigyoInfo>();
    Map<String, Object> params = new HashMap<String, Object>();
    String strCurrDate = DateUtil.getSysDate();
    params.put("systemDate", strCurrDate);
    lstMstSJigyoInfo = com0103d01Dao.getCom0103d01Mapper().getDiaJigyo(params);
    if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
      ObjCombobox objCombobox = new ObjCombobox();
      objCombobox.setCode("");
      objCombobox.setName("");
      lstDiaJigyo.add(objCombobox);
      for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
        objCombobox = new ObjCombobox();
        objCombobox.setCode(mstSJigyoInfo.getJgycd());
        objCombobox.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
            .getJgymei());
        lstDiaJigyo.add(objCombobox);
      }
      model.addAttribute("ddlDiaJigyoLst", lstDiaJigyo);
      return true;
    }
    return false;
  }

  /**
   * set data ddlDiaJobExec
   * 
   * @param formCom0103d01 form display
   * @param model A holder for model attributes
   * @return boolean set data success
   */
  public boolean setDataDiaJobExec(FormCom0103d01 formCom0103d01, Model model) {
    ArrayList<ObjCombobox> lstDiaJobExec = null;
    lstDiaJobExec = new ArrayList<ObjCombobox>();
    List<MstGeneralData> lstMstSJigyoInfo = null;
    CommonGetSystemCom com = new CommonGetSystemCom(commonDao, txManager,
        appContext, readPropertiesFileService);

    lstMstSJigyoInfo = com.getMstGeneralConf("Job_Type", null);
    if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
      ObjCombobox objCombobox = new ObjCombobox();
      objCombobox.setCode("");
      objCombobox.setName("");
      lstDiaJobExec.add(objCombobox);
      for (MstGeneralData mstGeneralData : lstMstSJigyoInfo) {
        objCombobox = new ObjCombobox();
        objCombobox.setCode(mstGeneralData.getGlCode());
        objCombobox.setName(mstGeneralData.getGlCode() + " : " + mstGeneralData
            .getTarget1());
        lstDiaJobExec.add(objCombobox);
      }
      model.addAttribute("ddlDiaJobExecLst", lstDiaJobExec);
      return true;
    }
    return false;
  }

  /**
   * set data ddlDiaTypExec
   * 
   * @param formCom0103d01 form display
   * @param model A holder for model attributes
   */
  public void setDataDiaTypExec(FormCom0103d01 formCom0103d01, Model model) {
    ArrayList<ObjCombobox> lstDiaTypExec = new ArrayList<ObjCombobox>();
    ObjCombobox objCombobox = new ObjCombobox();
    objCombobox.setCode("0");
    objCombobox.setName("");
    lstDiaTypExec.add(objCombobox);
    objCombobox = new ObjCombobox();
    objCombobox.setCode("1");
    objCombobox.setName("1 : 定時");
    lstDiaTypExec.add(objCombobox);
    objCombobox = new ObjCombobox();
    objCombobox.setCode("2");
    objCombobox.setName("2 : 随時");
    lstDiaTypExec.add(objCombobox);
    model.addAttribute("ddlDiaTypExecLst", lstDiaTypExec);
  }

  /**
   * 担当者マスタから担当者氏名を取得する
   * 
   * @param userCodeWk userCodeWK
   */
  public String getUserNm(String userCodeWk) {
    ArrayList<MstUser> lstMstUser = null;
    String userName = "";
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userCodeWK", userCodeWk);
    lstMstUser = com0103d01Dao.getCom0103d01Mapper().getMstUser(params);
    if (lstMstUser != null && lstMstUser.size() > 0) {
      userName = lstMstUser.get(0).getUserNm();
    }
    return userName;
  }

  /**
   * get list Com1JobExec
   * 
   * @param params Map
   * @return list Com1JobExe
   */
  public ArrayList<Com01Job> getCom1JobExec(Map<String, Object> params) {
    ArrayList<Com01Job> lstCom01Job = null;
    lstCom01Job = com0103d01Dao.getCom0103d01Mapper().getCom01Job(params);
    return lstCom01Job;
  }

  /**
   * データ呼出条件の初期化
   * 
   * @param formCom0103d01 form display
   * @param model A holder for model attributes
   * @param loginJgymei セッション.ログイン事業所名
   * @param loginUserId セッション.ログインユーザID
   */
  public void initDataCallCondition(FormCom0103d01 formCom0103d01, Model model,
      String loginJgymei, String loginUserId, String loginJigyouShoCode) {
    // 事業所
    formCom0103d01.setDdlDiaJigyo(loginJigyouShoCode);
    String strCurrDate = "";
    Date date = new Date();
    strCurrDate = DateUtil.setFormat(date, CommonConst.DATE_FORMAT_Y_M_D);
    // 処理日付From
    formCom0103d01.setTxtDiaDateFromExec(strCurrDate);
    // 処理時間From
    formCom0103d01.setTxtDiaTimeFromExec("0000");
    // 処理日付To
    formCom0103d01.setTxtDiaDateToExec(strCurrDate);
    // 処理時間To
    formCom0103d01.setTxtDiaTimeToExec("2359");
    // 処理担当者
    formCom0103d01.setTxtDiaUserCodeExec(loginUserId);
    // 処理担当者名称
    model.addAttribute("DiaUserNmExec", "");
    // 「配信ジョブを対象とする」チェックボックス
    formCom0103d01.setChkDiaRsJob(true);
    // 「集信ジョブを対象とする」チェックボックス
    formCom0103d01.setChkDiaSsJob(true);
    // 「異常ジョブのみ表示」チェックボックス
    formCom0103d01.setChkDiaIjJob(false);
    // set hidden
    formCom0103d01.setSystemDate(strCurrDate);
    formCom0103d01.setLoginOfficeName(loginJgymei);
    formCom0103d01.setLoginUserId(loginUserId);
  }

  /**
   * convert json
   * 
   * @param com01JobLst list Com01Job
   * @return json
   * @throws JsonProcessingException ER
   */
  public String convertJson(ArrayList<Com01Job> com01JobLst) throws JsonProcessingException {
    String jsonData = "";
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();    
      jsonData = ow.writeValueAsString(com01JobLst);
    return jsonData;
  }

  /**
   * 
   * @param reprocessJobLst list ReprocessJob
   * @return Error
   * @throws IOException ER
   */
  public String getReprocessJobLst(ReprocessJobLst reprocessJobLst)
      throws IOException {
    String getReprocessJobLst = "";
    String commandLine;
    DefaultTransactionDefinition def = null;
    TransactionStatus status = null;
    for (int i = 0; i < reprocessJobLst.size(); i++) {
      Long jobNo = reprocessJobLst.get(i).getJobNo();
      String haitaDate = reprocessJobLst.get(i).getStartDate();
      String haitaTime = reprocessJobLst.get(i).getStartTime();
      String userLogin = reprocessJobLst.get(i).getType();
      String jobName = reprocessJobLst.get(i).getJobNm();
      TblCom01JobExec tblCom01JobExec = new TblCom01JobExec();
      // ジョブ実行状況テーブル取得
      tblCom01JobExec = com0103d01Dao.getTblCom01JobExecMapper()
          .selectByPrimaryKey(jobNo);
      // (1) CJE.再処理可能フラグ = '1'（可）の場合、
      if (tblCom01JobExec.getReFlg().equals(CommonConst.REFLG_YES)) {
        if (!checkHaita(haitaDate, haitaTime, tblCom01JobExec.getUpdYmd(),
            tblCom01JobExec.getUpdTime())) {
          TblCom01JobExec tblCom01JobExecUpdate = tblCom01JobExec;
          tblCom01JobExecUpdate = setDateTimeUpdate(tblCom01JobExecUpdate,
              userLogin);
          try {
            def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            com0103d01Dao.getTblCom01JobExecMapper().updateByPrimaryKey(
                tblCom01JobExecUpdate);
            txManager.commit(status);
          } catch (MyBatisSystemException e) {
            txManager.rollback(status);
            logger.error(e.getMessage());
            throw e;
          }
          // コマンドライン生成
          commandLine = reprocessJobLst.get(i).getFileNmExec() + " "
              + reprocessJobLst.get(i).getParameters();
          // 非同期にて[変数]コマンドラインを起動
          ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", commandLine);
          pb.start();
        } else {
          ArrayList<String> paramMess = new ArrayList<String>();
          paramMess = new ArrayList<String>();
          paramMess.add(jobName);
          String msg = readPropertiesFileService.getMessage("COM006-W",
              paramMess);
          if (!getReprocessJobLst.equals("")) {
            getReprocessJobLst = getReprocessJobLst + "<br>" + msg;
          } else {
            getReprocessJobLst = msg;
          }
        }
      } else if (tblCom01JobExec.getReFlg().equals(
          CommonConst.REFLG_RE_PROCESSED)) {
        ArrayList<String> paramMess = new ArrayList<String>();
        paramMess = new ArrayList<String>();
        paramMess.add(jobName);
        String msg = readPropertiesFileService.getMessage("COM006-W",
            paramMess);
        if (!getReprocessJobLst.equals("")) {
          getReprocessJobLst = getReprocessJobLst + "<br>" + msg;
        } else {
          getReprocessJobLst = msg;
        }
      }
    }
    return getReprocessJobLst;
  }

  /**
   * 排他チェック.
   * 
   * @param strUserCode:担当者コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaita(String strHaitaDate, String strHaitaTime,
      String dateDb, String timeDb) {
    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(dateDb);
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(timeDb);
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param tblCom01JobExec old
   * @param userLogin userLogin
   * @return tblCom01JobExec new
   */
  public TblCom01JobExec setDateTimeUpdate(TblCom01JobExec tblCom01JobExec,
      String userLogin) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    tblCom01JobExec.setReFlg(CommonConst.REFLG_RE_PROCESSED);
    tblCom01JobExec.setUpdPgid("COM01-03D01");
    tblCom01JobExec.setUpdUserid(userLogin);
    tblCom01JobExec.setUpdTime(currentTime);
    tblCom01JobExec.setUpdYmd(currentDate);
    return tblCom01JobExec;
  }

  /**
   * set haita date, time
   * @param formCom0103d01 form data
   */
  public void setHaitaDate(FormCom0103d01 formCom0103d01) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    formCom0103d01.setHaitaDate(currentDate);
    formCom0103d01.setHaitaTime(currentTime);
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }
}