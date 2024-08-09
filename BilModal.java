package com.insilicoss.cab.saleBill;

import com.insilicoss.App;
import com.insilicoss.database.DBManager;
import com.insilicoss.eventManager.Row;
import com.insilicoss.exception.PresentableException;
import com.insilicoss.exception.SystemException;
import com.insilicoss.validation.CoreValidator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.TrackChanges;
/**
 *
 * @author Alaknanda
 */
@TrackChanges
public class BilModal {
    private String svsBilActv;

    private String svsBilType = "invoi";

    private int sviBizNtyOnrLocD;

    private int sviTxctgPartyLocD;

    private int sviBizNtyOnrLocF;

    private int sviTxctgPartyLocF;

    private int sviBizNtyOnrShipLocD;

    private int sviBizNtyOnrShipLocF;

    private int sviTxctgPartyShipLocD;

    private int sviTxctgPartyShipLocF;

    private String svsBnfcrOnr = " ";

    private int  sviBnfcrOnrD;

    private int  sviBnfcrOnrF;

    private String svsBilSts = " ";

    private String svsBilIdy;

    private LocalDate  svdBilDate;

    private String svsBilAckDocIdy = " ";

    private LocalDate svdBilAckDocDate = App.DEFAULT_MIN_DATE;

    private String   svsPchsOrdrIdy = " ";

    private LocalDate svdPchsOrdrDate = App.DEFAULT_MIN_DATE;

    private LocalDate svdBilAccDate = App.DEFAULT_MIN_DATE;

    private LocalDate svdBilDueDate = App.DEFAULT_MIN_DATE;

    private String  svsBilCrncIdy = " ";

    private int  svnBilXchngRate = 1;

    private String svsBillRCMAplbl = " ";

    private int sviBilPrprdUsrD = 1;

    private int sviBilAprvdUsrD = 1;

    private int sviTaxTmpltD = 1;

    private String  svsBilNotes = " ";

    public int sviBilD = -1;

    private DBManager cvoDBManager;

    private List<BilLine> svoBilLine = new ArrayList<>();

    public BilModal(DBManager pvoDBManager){
        cvoDBManager = pvoDBManager;
    }

    public BilModal(int pviBilD, DBManager pvoDBManager){
        sviBilD      = pviBilD;
        cvoDBManager = pvoDBManager;
        _loadBilWithD();
    } 
    
    public BilModal(String pvsBilIdy, DBManager pvoDBManager, boolean pvbCreateNewIfVoucherIdyNotFound){ 
    svsBilIdy  = pvsBilIdy; 
    cvoDBManager = pvoDBManager; 

    if(svsBilIdy == null || svsBilIdy.isBlank()){ 
      if(pvbCreateNewIfVoucherIdyNotFound){ 
        svsBilIdy = cvoDBManager.getNextTxnIdy("BilIdy", 0); 
      } 
      else { 
        throw new PresentableException("Invalid Bil Id. "); 
      } 
    } 
    else { 
      _loadBilWithIdy(pvbCreateNewIfVoucherIdyNotFound); 
    } 
  }  
    
   private void _loadBilWithIdy(boolean pvbCreateNewIfVoucherIdyNotFound){ 
    if((svsBilIdy == null || svsBilIdy.isBlank())){ 
      if(!pvbCreateNewIfVoucherIdyNotFound)
        throw new PresentableException("Invalid voucher Id. "); 
    } 

    try { 
      cvoDBManager.addContextParam("rvsBilIdy", svsBilIdy); 
      ResultSet lvoBilRs = cvoDBManager.selectResultSet("cab\\saleBill\\sarBilIdy");
            if(lvoBilRs.next()){
                svsBilType           = lvoBilRs.getString("svsBilType");
                sviBizNtyOnrLocD     = lvoBilRs.getInt("sviBizNtyOnrLocD");
                sviTxctgPartyLocD    = lvoBilRs.getInt("sviTxctgPartyLocD");
                sviBizNtyOnrLocF     = lvoBilRs.getInt("sviBizNtyOnrLocF");
                sviTxctgPartyLocF    = lvoBilRs.getInt("sviTxctgPartyLocF");
                sviBizNtyOnrShipLocD = lvoBilRs.getInt("sviBizNtyOnrShipLocD");
                sviBizNtyOnrShipLocF = lvoBilRs.getInt("sviBizNtyOnrShipLocF");
                svsBnfcrOnr          = lvoBilRs.getString("svsBnfcrOnr");
                sviBnfcrOnrD         = lvoBilRs.getInt("sviBnfcrOnrD");
                svsBilSts            = lvoBilRs.getString("svsBilSts");
                svsBilIdy            = lvoBilRs.getString("svsBilIdy");
                svdBilDate           = lvoBilRs.getDate("svdBilDate").toLocalDate();
                svsBilAckDocIdy      = lvoBilRs.getString("svsBilAckDocIdy");
                svdBilAckDocDate     = lvoBilRs.getDate("svdBilAckDocDate").toLocalDate();
                svsPchsOrdrIdy       = lvoBilRs.getString("svsPchsOrdrIdy");
                svdPchsOrdrDate      = lvoBilRs.getDate("svdPchsOrdrDate").toLocalDate();
                svdBilAccDate        = lvoBilRs.getDate("svdBilAccDate").toLocalDate();
                svdBilDueDate        = lvoBilRs.getDate("svdBilDueDate").toLocalDate();
                svsBilCrncIdy        = lvoBilRs.getString("svsBilCrncIdy");
                svnBilXchngRate      = lvoBilRs.getInt("svnBilXchngRate");
                svsBillRCMAplbl      = lvoBilRs.getString("svsBillRCMAplbl");
                sviBilPrprdUsrD      = lvoBilRs.getInt("sviBilPrprdUsrD");
                sviBilAprvdUsrD      = lvoBilRs.getInt("sviBilAprvdUsrD");
                sviTaxTmpltD         = lvoBilRs.getInt("sviTaxTmpltD");
                svsBilNotes          = lvoBilRs.getString("svsBilNotes");
                sviBilD              = lvoBilRs.getInt("sviBilD"); 
                _loadBilLines(); 

      } 
      else { 
        if(!pvbCreateNewIfVoucherIdyNotFound)
          throw new PresentableException("Invalid voucher Id. "); 
      } 
    } 
    catch(SQLException se){ 
      throw new SystemException(se.getMessage(), se); 
    } 
  }
  
   private void _loadBilLines() throws SQLException {
    cvoDBManager.addContextParam("rviBilD", sviBilD); 
    ResultSet lvoRsBilLine = cvoDBManager.selectResultSet("cab\\saleBill\\sarBilLine");
    BilLine lvoBilLine;
    while(lvoRsBilLine.next()){
      lvoBilLine = new BilLine();
      lvoBilLine.sviBilD                 =     lvoRsBilLine.getInt("sviBilD");
       lvoBilLine.sviItemD               =     lvoRsBilLine.getInt("sviItemD");
       lvoBilLine.sviItemF               =     lvoRsBilLine.getInt("sviItemF");
       lvoBilLine.svsBilLineDesc         =     lvoRsBilLine.getString("svsBilLineDesc");
       lvoBilLine.svsBilLineUom          =     lvoRsBilLine.getString("svsBilLineUom");
       lvoBilLine.svnBilLineQty          =     lvoRsBilLine.getInt("svnBilLineQty");
       lvoBilLine.svnBilLineUnitPrice    =     lvoRsBilLine.getInt("svnBilLineUnitPrice");
       lvoBilLine.svnBilLineDscnt        =     lvoRsBilLine.getInt("svnBilLineDscnt");
       lvoBilLine.svnBilLineVal          =     lvoRsBilLine.getInt("svnBilLineVal");
       lvoBilLine.svbBilLineHasSupotDoc  =     lvoRsBilLine.getString("svbBilLineHasSupotDoc");
       lvoBilLine.svsBilLineDspsl        =     lvoRsBilLine.getString("svsBilLineDspsl");
       lvoBilLine.svsCntxt               =     lvoRsBilLine.getString("svsCntxt");
       lvoBilLine.sviCntxtBizNtyD        =     lvoRsBilLine.getInt("sviCntxtBizNtyD");
       lvoBilLine.sviCntxtNgmtD          =     lvoRsBilLine.getInt("sviCntxtNgmtD");
       lvoBilLine.svsBilLineHSN          =     lvoRsBilLine.getString("svsBilLineHSN");
       lvoBilLine.sviLnkdBilLineD        =     lvoRsBilLine.getInt("sviLnkdBilLineD");
       lvoBilLine.svsBilLineNotes        =     lvoRsBilLine.getString("svsBilLineNotes");
       lvoBilLine.sviBilLineD            =     lvoRsBilLine.getInt("sviBilLineD");
    }
 }
   
    private void _loadBilWithD(){
        try {
            if(sviBilD < 0) {
                throw new PresentableException("Invalid voucher reference. ");
            }
            cvoDBManager.addContextParam("rviBilD", sviBilD);
            ResultSet lvoBilRs = cvoDBManager.selectResultSet("cab\\saleBill\\sarBil");
            if(lvoBilRs.next()){
                svsBilType           = lvoBilRs.getString("svsBilType");
                sviBizNtyOnrLocD     = lvoBilRs.getInt("sviBizNtyOnrLocD");
                sviTxctgPartyLocD    = lvoBilRs.getInt("sviTxctgPartyLocD");
                sviBizNtyOnrLocF     = lvoBilRs.getInt("sviBizNtyOnrLocF");
                sviTxctgPartyLocF    = lvoBilRs.getInt("sviTxctgPartyLocF");
                sviBizNtyOnrShipLocD = lvoBilRs.getInt("sviBizNtyOnrShipLocD");
                sviBizNtyOnrShipLocF = lvoBilRs.getInt("sviBizNtyOnrShipLocF");
                svsBnfcrOnr          = lvoBilRs.getString("svsBnfcrOnr");
                sviBnfcrOnrD         = lvoBilRs.getInt("sviBnfcrOnrD"); 
                sviBnfcrOnrF         = lvoBilRs.getInt("sviBfcrOnrF");
                svsBilSts            = lvoBilRs.getString("svsBilSts");
                svsBilIdy            = lvoBilRs.getString("svsBilIdy");
                svdBilDate           = lvoBilRs.getDate("svdBilDate").toLocalDate();
                svsBilAckDocIdy      = lvoBilRs.getString("svsBilAckDocIdy");
                svdBilAckDocDate     = lvoBilRs.getDate("svdBilAckDocDate").toLocalDate();
                svsPchsOrdrIdy       = lvoBilRs.getString("svsPchsOrdrIdy");
                svdPchsOrdrDate      = lvoBilRs.getDate("svdPchsOrdrDate").toLocalDate();
                svdBilAccDate        = lvoBilRs.getDate("svdBilAccDate").toLocalDate();
                svdBilDueDate        = lvoBilRs.getDate("svdBilDueDate").toLocalDate();
                svsBilCrncIdy        = lvoBilRs.getString("svsBilCrncIdy");
                svnBilXchngRate      = lvoBilRs.getInt("svnBilXchngRate");
                svsBillRCMAplbl      = lvoBilRs.getString("svsBillRCMAplbl");
                sviBilPrprdUsrD      = lvoBilRs.getInt("sviBilPrprdUsrD");
                sviBilAprvdUsrD      = lvoBilRs.getInt("sviBilAprvdUsrD");
                sviTaxTmpltD         = lvoBilRs.getInt("sviTaxTmpltD");
                svsBilNotes          = lvoBilRs.getString("svsBilNotes");
                sviBilD              = lvoBilRs.getInt("sviBilD");
            }
            else {
                throw new PresentableException("Invalid Bill reference. ");
            }

            _loadBilLinesD();
        }
        catch(SQLException se){
            throw new SystemException(se.getMessage(), se);
        }
    }

    private void _loadBilLinesD()throws SQLException{
        cvoDBManager.addContextParam("rviBilD",sviBilD);
        ResultSet lvoBilLineRs = cvoDBManager.selectResultSet("cab\\saleBill\\sarBilLine");

        while(lvoBilLineRs.next()){
            int    lviBilD                = lvoBilLineRs.getInt("sviBilD");
            int    lviItemD               = lvoBilLineRs.getInt("sviItemD");
            int    lviItemF               = lvoBilLineRs.getInt("sviItemF");
            String lvsBilLineDesc         = lvoBilLineRs.getString("svsBilLineDesc");
            String lvsBilLineUom          = lvoBilLineRs.getString("svsBilLineUom");
            int    lvnBilLineQty          = lvoBilLineRs.getInt("svnBilLineQty");
            int    lvnBilLineUnitPrice    = lvoBilLineRs.getInt("svnBilLineUnitPrice");
            int    lvnBilLineDscnt        = lvoBilLineRs.getInt("svnBilLineDscnt");
            int    lvnBilLineVal          = lvoBilLineRs.getInt("svnBilLineVal");
            String lvbBilLineHasSupotDoc  = lvoBilLineRs.getString("svbBilLineHasSupotDoc");
            String lvsBilLineDspsl        = lvoBilLineRs.getString("svsBilLineDspsl");
            String lvsCntxt               = lvoBilLineRs.getString("svsCntxt");
            int    lviCntxtBizNtyD        = lvoBilLineRs.getInt("sviCntxtBizNtyD");
            int    lviCntxtNgmtD          = lvoBilLineRs.getInt("sviCntxtNgmtD");
            String lvsBilLineHSN          = lvoBilLineRs.getString("svsBilLineHSN");
            int    lviLnkdBilLineD        = lvoBilLineRs.getInt("sviLnkdBilLineD");
            String lvsBilLineNotes        = lvoBilLineRs.getString("svsBilLineNotes");
            int    lviBilLineD            = lvoBilLineRs.getInt("sviBilLineD");
        }
    }

    public void save(boolean pvbCommit){
        CoreValidator.validateAutoFlag(this);
        if(isChanged){
            cvoDBManager.addContextParam("rvsBilType",svsBilType);
            cvoDBManager.addContextParam("rviBizNtyOnrLocD",sviBizNtyOnrLocD);
            cvoDBManager.addContextParam("rviTxctgPartyLocD",sviTxctgPartyLocD);
            cvoDBManager.addContextParam("rviBizNtyOnrLocF",sviBizNtyOnrLocF);
            cvoDBManager.addContextParam("rviTxctgPartyLocF",sviTxctgPartyLocF);
            cvoDBManager.addContextParam("rviBizNtyOnrShipLocD",sviBizNtyOnrShipLocD);
            cvoDBManager.addContextParam("rviBizNtyOnrShipLocF",sviBizNtyOnrShipLocF);
            cvoDBManager.addContextParam("rviTxctgPartyShipLocD",sviTxctgPartyShipLocD);
            cvoDBManager.addContextParam("rviTxctgPartyShipLocF",sviTxctgPartyShipLocF);
            cvoDBManager.addContextParam("rvsBnfcrOnr",svsBnfcrOnr);
            cvoDBManager.addContextParam("rviBnfcrOnrD",sviBnfcrOnrD);
            cvoDBManager.addContextParam("rviBnfcrOnrF",sviBnfcrOnrF);
            cvoDBManager.addContextParam("rvsBilSts",svsBilSts);
            cvoDBManager.addContextParam("rvsBilIdy",svsBilIdy);
            cvoDBManager.addContextParam("rvdBilDate",svdBilDate);
            cvoDBManager.addContextParam("rvsBilAckDocIdy",svsBilAckDocIdy);
            cvoDBManager.addContextParam("rvdBilAckDocDate",svdBilAckDocDate);
            cvoDBManager.addContextParam("rvsPchsOrdrIdy",svsPchsOrdrIdy);
            cvoDBManager.addContextParam("rvdPchsOrdrDate",svdPchsOrdrDate);
            cvoDBManager.addContextParam("rvdBilAccDate",svdBilAccDate);
            cvoDBManager.addContextParam("rvdBilDueDate",svdBilDueDate);
            cvoDBManager.addContextParam("rvsBilCrncIdy",svsBilCrncIdy);
            cvoDBManager.addContextParam("rvnBilXchngRate",svnBilXchngRate);
            cvoDBManager.addContextParam("rvsBillRCMAplbl",svsBillRCMAplbl);
            cvoDBManager.addContextParam("rviBilPrprdUsrD",sviBilPrprdUsrD);
            cvoDBManager.addContextParam("rviBilAprvdUsrD",sviBilAprvdUsrD);
            cvoDBManager.addContextParam("rviTaxTmpltD",sviTaxTmpltD);
            cvoDBManager.addContextParam("rvsBilNotes",svsBilNotes);

            if(sviBilD == -1){
                cvoDBManager.update("cab\\saleBill\\iarBil", true);
                sviBilD = cvoDBManager.cviRecentAutoGenKey;
            } else {
                cvoDBManager.addContextParam("rviBilD",sviBilD);
                cvoDBManager.update("cab\\saleBill\\uarBil");

            }
        }

        for(BilLine lvoBilLine : svoBilLine){
            BilLine bilLine = new BilLine();
            if(lvoBilLine.isChanged){
                cvoDBManager.addContextParam("rviBilD",sviBilD);
                cvoDBManager.addContextParam("rviItemD",bilLine.sviItemD);
                cvoDBManager.addContextParam("rviItemF",bilLine.sviItemF);
                cvoDBManager.addContextParam("rvsBilLineDesc",bilLine.svsBilLineDesc);
                cvoDBManager.addContextParam("rvsBilLineUom",bilLine.svsBilLineUom);
                cvoDBManager.addContextParam("rvnBilLineQty",bilLine.svnBilLineQty);
                cvoDBManager.addContextParam("rvnBilLineUnitPrice",bilLine.svnBilLineUnitPrice);
                cvoDBManager.addContextParam("rvnBilLineDscnt",bilLine.svnBilLineDscnt);
                cvoDBManager.addContextParam("rvnBilLineVal",bilLine.svnBilLineVal);
                cvoDBManager.addContextParam("rvbBilLineHasSupotDoc",bilLine.svbBilLineHasSupotDoc);
                cvoDBManager.addContextParam("rvsBilLineDspsl",bilLine.svsBilLineDspsl);
                cvoDBManager.addContextParam("rvsCntxt",bilLine.svsCntxt);
                cvoDBManager.addContextParam("rviCntxtBizNtyD",bilLine.sviCntxtBizNtyD);
                cvoDBManager.addContextParam("rviCntxtNgmtD",bilLine.sviCntxtNgmtD);
                cvoDBManager.addContextParam("rvsBilLineHSN",bilLine.svsBilLineHSN);
                cvoDBManager.addContextParam("rviLnkdBilLineD",bilLine.sviLnkdBilLineD);
                cvoDBManager.addContextParam("rvsBilLineNotes",bilLine.svsBilLineNotes);

                if(bilLine.sviBilLineD > 0){
                    cvoDBManager.addContextParam("rviBilLineD",bilLine.sviBilLineD);
                    cvoDBManager.update("cab\\saleBill\\uarBilLine");
                } else {
                    bilLine.sviBilLineD = cvoDBManager.getNextTxnD("BilLineD");
                    cvoDBManager.addContextParam("rviBilLineD",bilLine.sviBilLineD);
                    cvoDBManager.update("cab\\saleBill\\iarBilLine");
                }
            }
        }
         if(pvbCommit){ 
        cvoDBManager.commitTrans(); 
    } 

    }

    public BilLine createNewBilLine(){
        BilLine lvoBilLine = new BilLine();
        svoBilLine.add(lvoBilLine);
        return lvoBilLine;
    }

    public BilLine getBilLineWithD(int pviBilLineD){
        if(pviBilLineD == -1){
            return null;
        }
        for (BilLine lvoBilLine : svoBilLine) {
            if(lvoBilLine.sviBilLineD == pviBilLineD){
                return lvoBilLine;
            }
        }
        return null;
    }

    @TrackChanges
    public class BilLine {
        private String svsBilLineActv;

        private int sviBilD;

        private int sviItemD;

        private int sviItemF;

        private String svsBilLineDesc = "Services";

        private String svsBilLineUom = "CMS";

        private int svnBilLineQty;

        private int svnBilLineUnitPrice;

        private int svnBilLineDscnt;

        private int svnBilLineVal;

        private String svbBilLineHasSupotDoc = "1";

        private String svsBilLineDspsl = " ";

        private String svsCntxt = " ";

        private int sviCntxtBizNtyD = -1;

        private int sviCntxtNgmtD = -1;

        private String svsBilLineHSN = "12345678";

        private int sviLnkdBilLineD = -1;

        private String svsBilLineNotes = " ";

        public int sviBilLineD; 
        
    }

}


