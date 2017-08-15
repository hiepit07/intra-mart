/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:FormNyu0102d01.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

import java.util.List;

/**
 * 入金登録画面用 FORM
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class FormNyu0102d01 {

  /*
   * 画面HIDDEN
   */
  private String btnName;            // ボタン名
  private boolean sysAdminFlag;      // システム管理者フラグ
  private String authKbn;            // 権限区分
  private String loginJigyoshoCd;    // ログイン事業所コード
  private String businessDate;       // 業務日付
  private String haitaDate;          // 排他日付
  private String haitaTime;          // 排他時刻
  private String errorControl;       // エラー制御フラグ
  private boolean showFlag;         // 表示フラグ
  private String shoriKbn;            // 処理区分
  
  private boolean msgInfoLevel;      // エラーレベル情報
  private boolean msgWarningLevel;   // エラーレベル警告
  private boolean msgErrorLevel;     // エラーレベルエラー
  
  /*
   * ヘッダー部
   */
  private String nyukinDempyoNo;    // 入金伝票番号
  private String chainCd;           // チェーンコード
  private String chainIdx;          // チェーン枝番
  private String seikyusakiCd;      // 請求先コード
  private String seikyusakiName;    // 請求先名
  private String jigyoshoCd;        // 事業所コード
  private String jigyoshoName;      // 事業所名
  private String seikyuShimebi;     // 請求締日
  private String shoninNichiji;     // 承認日時
  private String nyukinYoteibi;     // 入金予定日
  private String nyukinbiYY;        // 入金日（年）
  private String nyukinbiMM;        // 入金日（月）
  private String nyukinbiDD;        // 入金日（日）
  private String selectedOkureRiyu; // 遅れ理由
  
  private String seikyuTani;             // 請求単位
  private String tokuisakiCls;           // 得意先種別
  private String kankeiKaishaCls;        // 関係会社種別
  private String kankeiKaishaHojoKamoku; // 関係会社補助科目
  private String seikyuId;               // 請求ID
  
  // 都度請求伝票情報
  private List<TsudoSeikyuDempyoInfo> tusoSeikyuDempyoList;
  
  /*
   * 明細部
   */
  private List<NyukinGamenMeisaiInfo> meisaiList;
  private int choseiGaku;
  
  /*
   * フッタ部
   */
  private int mikaishuZandaka;   // 未回収残高
  private int mishukinZandaka;   // 未収金残高
  private int urikakekinZandaka; // 売掛金残高
  
  private int nyukinKinGaku;  // 入金金額
  private int sosaiKingaku;   // 相殺金額
  
  private int konkaiMikaishuZandaka; // 今回未回収残高
  
  private List<ZandakaRiyuInfo> zandakaRiyuList; // 残高理由
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getBtnName() {
    return btnName;
  }
  
  public void setBtnName(String btnName) {
    this.btnName = btnName;
  }
  
  public boolean isSysAdminFlag() {
    return sysAdminFlag;
  }
  
  public void setSysAdminFlag(boolean sysAdminFlag) {
    this.sysAdminFlag = sysAdminFlag;
  }
  
  
  public String getAuthKbn() {
    return authKbn;
  }

  
  public void setAuthKbn(String authKbn) {
    this.authKbn = authKbn;
  }

  public String getLoginJigyoshoCd() {
    return loginJigyoshoCd;
  }
  
  public void setLoginJigyoshoCd(String loginJigyoshoCd) {
    this.loginJigyoshoCd = loginJigyoshoCd;
  }
  
  public String getBusinessDate() {
    return businessDate;
  }
  
  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }
  
  public String getHaitaDate() {
    return haitaDate;
  }
  
  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }
  
  public String getHaitaTime() {
    return haitaTime;
  }
  
  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }
  
  public String getErrorControl() {
    return errorControl;
  }
  
  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }
  
  public boolean isShowFlag() {
    return showFlag;
  }
  
  public void setShowFlag(boolean showFlag) {
    this.showFlag = showFlag;
  }
  
  public boolean isMsgInfoLevel() {
    return msgInfoLevel;
  }
  
  public void setMsgInfoLevel(boolean msgInfoLevel) {
    this.msgInfoLevel = msgInfoLevel;
  }
  
  public boolean isMsgWarningLevel() {
    return msgWarningLevel;
  }
  
  public void setMsgWarningLevel(boolean msgWarningLevel) {
    this.msgWarningLevel = msgWarningLevel;
  }
  
  public boolean isMsgErrorLevel() {
    return msgErrorLevel;
  }
  
  public void setMsgErrorLevel(boolean msgErrorLevel) {
    this.msgErrorLevel = msgErrorLevel;
  }

  public String getShoriKbn() {
    return shoriKbn;
  }

  
  public void setShoriKbn(String shoriKbn) {
    this.shoriKbn = shoriKbn;
  }

  
  public String getNyukinDempyoNo() {
    return nyukinDempyoNo;
  }

  
  public void setNyukinDempyoNo(String nyukinDempyoNo) {
    this.nyukinDempyoNo = nyukinDempyoNo;
  }

  
  public String getSeikyusakiCd() {
    return seikyusakiCd;
  }

  
  public void setSeikyusakiCd(String seikyusakiCd) {
    this.seikyusakiCd = seikyusakiCd;
  }

  
  public String getSeikyusakiName() {
    return seikyusakiName;
  }

  
  public void setSeikyusakiName(String seikyusakiName) {
    this.seikyusakiName = seikyusakiName;
  }

  
  public String getJigyoshoCd() {
    return jigyoshoCd;
  }

  
  public void setJigyoshoCd(String jigyoshoCd) {
    this.jigyoshoCd = jigyoshoCd;
  }

  
  public String getJigyoshoName() {
    return jigyoshoName;
  }

  
  public void setJigyoshoName(String jigyoshoName) {
    this.jigyoshoName = jigyoshoName;
  }

  
  public String getSeikyuShimebi() {
    return seikyuShimebi;
  }

  
  public void setSeikyuShimebi(String seikyuShimebi) {
    this.seikyuShimebi = seikyuShimebi;
  }

  
  public String getShoninNichiji() {
    return shoninNichiji;
  }

  
  public void setShoninNichiji(String shoninNichiji) {
    this.shoninNichiji = shoninNichiji;
  }

  
  public String getNyukinYoteibi() {
    return nyukinYoteibi;
  }

  
  public void setNyukinYoteibi(String nyukinYoteibi) {
    this.nyukinYoteibi = nyukinYoteibi;
  }

  
  public String getNyukinbiYY() {
    return nyukinbiYY;
  }

  
  public void setNyukinbiYY(String nyukinbiYY) {
    this.nyukinbiYY = nyukinbiYY;
  }

  
  public String getNyukinbiMM() {
    return nyukinbiMM;
  }

  
  public void setNyukinbiMM(String nyukinbiMM) {
    this.nyukinbiMM = nyukinbiMM;
  }

  
  public String getNyukinbiDD() {
    return nyukinbiDD;
  }

  
  public void setNyukinbiDD(String nyukinbiDD) {
    this.nyukinbiDD = nyukinbiDD;
  }

  
  public String getSelectedOkureRiyu() {
    return selectedOkureRiyu;
  }

  
  public void setSelectedOkureRiyu(String selectedOkureRiyu) {
    this.selectedOkureRiyu = selectedOkureRiyu;
  }

  
  
  public String getChainCd() {
    return chainCd;
  }

  
  public void setChainCd(String chainCd) {
    this.chainCd = chainCd;
  }

  
  public String getChainIdx() {
    return chainIdx;
  }

  
  public void setChainIdx(String chainIdx) {
    this.chainIdx = chainIdx;
  }

  
  public String getSeikyuTani() {
    return seikyuTani;
  }

  
  public void setSeikyuTani(String seikyuTani) {
    this.seikyuTani = seikyuTani;
  }

  
  public String getTokuisakiCls() {
    return tokuisakiCls;
  }

  
  public void setTokuisakiCls(String tokuisakiCls) {
    this.tokuisakiCls = tokuisakiCls;
  }

  
  public String getKankeiKaishaCls() {
    return kankeiKaishaCls;
  }

  
  public void setKankeiKaishaCls(String kankeiKaishaCls) {
    this.kankeiKaishaCls = kankeiKaishaCls;
  }

  
  public String getKankeiKaishaHojoKamoku() {
    return kankeiKaishaHojoKamoku;
  }

  
  public void setKankeiKaishaHojoKamoku(String kankeiKaishaHojoKamoku) {
    this.kankeiKaishaHojoKamoku = kankeiKaishaHojoKamoku;
  }

  
  public String getSeikyuId() {
    return seikyuId;
  }

  
  public void setSeikyuId(String seikyuId) {
    this.seikyuId = seikyuId;
  }

  
  public List<TsudoSeikyuDempyoInfo> getTusoSeikyuDempyoList() {
    return tusoSeikyuDempyoList;
  }

  
  public void setTusoSeikyuDempyoList(
      List<TsudoSeikyuDempyoInfo> tusoSeikyuDempyoList) {
    this.tusoSeikyuDempyoList = tusoSeikyuDempyoList;
  }

  public int getMikaishuZandaka() {
    return mikaishuZandaka;
  }

  
  public void setMikaishuZandaka(int mikaishuZandaka) {
    this.mikaishuZandaka = mikaishuZandaka;
  }

  
  public int getMishukinZandaka() {
    return mishukinZandaka;
  }

  
  public void setMishukinZandaka(int mishukinZandaka) {
    this.mishukinZandaka = mishukinZandaka;
  }

  
  public int getUrikakekinZandaka() {
    return urikakekinZandaka;
  }

  
  public void setUrikakekinZandaka(int urikakekinZandaka) {
    this.urikakekinZandaka = urikakekinZandaka;
  }

  
  public int getNyukinKinGaku() {
    return nyukinKinGaku;
  }

  
  public void setNyukinKinGaku(int nyukinKinGaku) {
    this.nyukinKinGaku = nyukinKinGaku;
  }

  
  public int getSosaiKingaku() {
    return sosaiKingaku;
  }

  
  public void setSosaiKingaku(int sosaiKingaku) {
    this.sosaiKingaku = sosaiKingaku;
  }

  
  public int getKonkaiMikaishuZandaka() {
    return konkaiMikaishuZandaka;
  }

  
  public void setKonkaiMikaishuZandaka(int konkaiMikaishuZandaka) {
    this.konkaiMikaishuZandaka = konkaiMikaishuZandaka;
  }

  
  public List<NyukinGamenMeisaiInfo> getMeisaiList() {
    return meisaiList;
  }

  
  public void setMeisaiList(List<NyukinGamenMeisaiInfo> meisaiList) {
    this.meisaiList = meisaiList;
  }

  
  public int getChoseiGaku() {
    return choseiGaku;
  }

  
  public void setChoseiGaku(int choseiGaku) {
    this.choseiGaku = choseiGaku;
  }

  
  public List<ZandakaRiyuInfo> getZandakaRiyuList() {
    return zandakaRiyuList;
  }

  
  public void setZandakaRiyuList(List<ZandakaRiyuInfo> zandakaRiyuList) {
    this.zandakaRiyuList = zandakaRiyuList;
  }
  
}
