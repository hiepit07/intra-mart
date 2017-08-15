/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:NyukinGamenMeisaiInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/04
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/04 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 入金登録画面 明細情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class NyukinGamenMeisaiInfo {
  
  private String urikakeNyukinShiwakeNo;  // 売掛金入金仕訳No
  private String mishuNyukinShiwakeNo;    // 未収金入金仕訳No
  private String kinoShosaiKbn;           // 機能詳細区分
  private String hojoKamokuSetteiKbn;     // 補助科目設定区分
  private String hojoKamokuKoteichi;      // 補助科目固定値
  private String gamenTitle;              // 画面タイトル
  private String kamokuSentakuKahi;       // 科目選択可否
  private String seishikiKamokuName;      // 正式科目名
  private String taxKbn;                  // 税区分
  private String renkeiId;                // 会計データ連携ID
  private String kamokuMstKamokuCd;       // 科目マスタ（機能）科目コード
  
  private String no;                      // No
  private String taishakuCd;              // 貸借コード
  private String taishakuKbn;             // 貸借区分
  private String jigyoshoCd;              // 事業所コード
  private String jigyoshoName;            // 事業所名
  private String selectedHasseiBasho;     // 発生場所.選択値
  private String kamokuCd;                // 科目コード
  private String hojoCd;                  // 補助コード
  private String kamokuName;              // 科目名
  private String selectedKankeiKaisha;    // 関係会社.選択値
  private String urikakeKeshikomiKingaku; // 売掛金消込金額
  private String mishuKeshikomiKingaku;   // 未収金消込金額
  private String tegataNo;                // 手形No
  private String tegataKijitsu;           // 手形期日
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getUrikakeNyukinShiwakeNo() {
    return urikakeNyukinShiwakeNo;
  }
  
  public void setUrikakeNyukinShiwakeNo(String urikakeNyukinShiwakeNo) {
    this.urikakeNyukinShiwakeNo = urikakeNyukinShiwakeNo;
  }
  
  public String getMishuNyukinShiwakeNo() {
    return mishuNyukinShiwakeNo;
  }
  
  public void setMishuNyukinShiwakeNo(String mishuNyukinShiwakeNo) {
    this.mishuNyukinShiwakeNo = mishuNyukinShiwakeNo;
  }
  
  public String getKinoShosaiKbn() {
    return kinoShosaiKbn;
  }
  
  public void setKinoShosaiKbn(String kinoShosaiKbn) {
    this.kinoShosaiKbn = kinoShosaiKbn;
  }
  
  public String getHojoKamokuSetteiKbn() {
    return hojoKamokuSetteiKbn;
  }
  
  public void setHojoKamokuSetteiKbn(String hojoKamokuSetteiKbn) {
    this.hojoKamokuSetteiKbn = hojoKamokuSetteiKbn;
  }
  
  public String getHojoKamokuKoteichi() {
    return hojoKamokuKoteichi;
  }
  
  public void setHojoKamokuKoteichi(String hojoKamokuKoteichi) {
    this.hojoKamokuKoteichi = hojoKamokuKoteichi;
  }
  
  public String getGamenTitle() {
    return gamenTitle;
  }

  
  public void setGamenTitle(String gamenTitle) {
    this.gamenTitle = gamenTitle;
  }

  
  public String getKamokuSentakuKahi() {
    return kamokuSentakuKahi;
  }

  
  public void setKamokuSentakuKahi(String kamokuSentakuKahi) {
    this.kamokuSentakuKahi = kamokuSentakuKahi;
  }

  public String getSeishikiKamokuName() {
    return seishikiKamokuName;
  }
  
  public void setSeishikiKamokuName(String seishikiKamokuName) {
    this.seishikiKamokuName = seishikiKamokuName;
  }
  
  public String getTaxKbn() {
    return taxKbn;
  }
  
  public void setTaxKbn(String taxKbn) {
    this.taxKbn = taxKbn;
  }
  
  public String getRenkeiId() {
    return renkeiId;
  }
  
  public void setRenkeiId(String renkeiId) {
    this.renkeiId = renkeiId;
  }
  
  public String getKamokuMstKamokuCd() {
    return kamokuMstKamokuCd;
  }
  
  public void setKamokuMstKamokuCd(String kamokuMstKamokuCd) {
    this.kamokuMstKamokuCd = kamokuMstKamokuCd;
  }
  
  public String getNo() {
    return no;
  }
  
  public void setNo(String no) {
    this.no = no;
  }
  
  public String getTaishakuCd() {
    return taishakuCd;
  }
  
  public void setTaishakuCd(String taishakuCd) {
    this.taishakuCd = taishakuCd;
  }
  
  public String getTaishakuKbn() {
    return taishakuKbn;
  }
  
  public void setTaishakuKbn(String taishakuKbn) {
    this.taishakuKbn = taishakuKbn;
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
  
  public String getSelectedHasseiBasho() {
    return selectedHasseiBasho;
  }
  
  public void setSelectedHasseiBasho(String selectedHasseiBasho) {
    this.selectedHasseiBasho = selectedHasseiBasho;
  }
  
  public String getKamokuCd() {
    return kamokuCd;
  }
  
  public void setKamokuCd(String kamokuCd) {
    this.kamokuCd = kamokuCd;
  }
  
  public String getHojoCd() {
    return hojoCd;
  }
  
  public void setHojoCd(String hojoCd) {
    this.hojoCd = hojoCd;
  }
  
  public String getKamokuName() {
    return kamokuName;
  }
  
  public void setKamokuName(String kamokuName) {
    this.kamokuName = kamokuName;
  }
  
  public String getSelectedKankeiKaisha() {
    return selectedKankeiKaisha;
  }
  
  public void setSelectedKankeiKaisha(String selectedKankeiKaisha) {
    this.selectedKankeiKaisha = selectedKankeiKaisha;
  }
  
  public String getUrikakeKeshikomiKingaku() {
    return urikakeKeshikomiKingaku;
  }
  
  public void setUrikakeKeshikomiKingaku(String urikakeKeshikomiKingaku) {
    this.urikakeKeshikomiKingaku = urikakeKeshikomiKingaku;
  }
  
  public String getMishuKeshikomiKingaku() {
    return mishuKeshikomiKingaku;
  }
  
  public void setMishuKeshikomiKingaku(String mishuKeshikomiKingaku) {
    this.mishuKeshikomiKingaku = mishuKeshikomiKingaku;
  }
  
  public String getTegataNo() {
    return tegataNo;
  }
  
  public void setTegataNo(String tegataNo) {
    this.tegataNo = tegataNo;
  }
  
  public String getTegataKijitsu() {
    return tegataKijitsu;
  }
  
  public void setTegataKijitsu(String tegataKijitsu) {
    this.tegataKijitsu = tegataKijitsu;
  }

}
