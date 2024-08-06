
package com.insilicoss.cab.saleBill;

import com.insilicoss.App;
import com.insilicoss.database.DBManager;
import com.insilicoss.validation.CoreValidator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import lombok.TrackChanges;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@TrackChanges 
public class SaleBilModel {
  
  private String cvsBilActv;
  
  private String cvsBilType = "invoi";
      
  private int cviBizNtyOnrLocD;
  
  private int cviTxctgPartyLocD;
  
  private int cviBizNtyOnrLocF;
  
  private int cviTxctgPartyLocF;
  
  private int cviBizNtyOnrShipLocD;
  
  private int cviBizNtyOnrShipLocF;
  
  private int cviTxctgPartyShipLocD; 
  
  private int cviTxctgPartyShipLocF;
  
  private String cvsBnfcrOnr = " ";
  
  private int  cviBnfcrOnrD;
  
  private int  cviBnfcrOnrF;
  
  private String cvsBilSts = " ";
  
  private String cvsBilIdy;
  
  private LocalDate  cvdBilDate;
  
  private String cvsBilAckDocIdy = " ";
  
  private LocalDate cvdBilAckDocDate = App.DEFAULT_MIN_DATE;
  
  private String   cvsPchsOrdrIdy = " "; 
  
  private LocalDate cvdPchsOrdrDate = App.DEFAULT_MIN_DATE;
     
  private LocalDate cvdBilAccDate = App.DEFAULT_MIN_DATE;
  
  private LocalDate cvdBilDueDate = App.DEFAULT_MIN_DATE;
  
  private String  cvsBilCrncIdy = " ";
  
  private int  cvnBilXchngRate = 1;
  
  private String cvsBillRCMAplbl = " ";
  
  private int cviBilPrprdUsrD = 1;
  
  private int cviBilAprvdUsrD = 1;
  
  private int cviTaxTmpltD = 1;
  
  private String  cvsBilNotes = " ";
     
  public int cviBilD;
  
  private final DBManager cvoDBManager;
//  private final Logger cvoLogger;
  private Logger cvoLogger = LogManager.getLogger(); 
  public ArrayList<SaleBilLineModel> cvoBilLineModelList; 
  
  private SaleBilModel(DBManager dBManager){
    this.cvoDBManager = dBManager;
    cvoBilLineModelList = new ArrayList<>();
  } 
  
  public static SaleBilModel load(int pviBilD,DBManager dBManager) throws SQLException{
    SaleBilModel  saleBilModel = new SaleBilModel(dBManager);
    saleBilModel.cviBilD = pviBilD;
    if(pviBilD != -1){
      dBManager.addContextParam("rviBilD",pviBilD);
      ResultSet lvoBilRs = saleBilModel.cvoDBManager.selectResultSet("cab\\saleBill\\sarBil");
      ResultSet lvoBilLineRs;
      SaleBilLineModel lvoBilLineModel;
      
      if(lvoBilRs.first()){
      saleBilModel.cvsBilType           = lvoBilRs.getString("svsBilType");
      saleBilModel.cviBizNtyOnrLocD     = lvoBilRs.getInt("sviBizNtyOnrLocD");
      saleBilModel.cviTxctgPartyLocD    = lvoBilRs.getInt("sviTxctgPartyLocD");
      saleBilModel.cviBizNtyOnrLocF     = lvoBilRs.getInt("sviBizNtyOnrLocF");
      saleBilModel.cviTxctgPartyLocF    = lvoBilRs.getInt("sviTxctgPartyLocF");
      saleBilModel.cviBizNtyOnrShipLocD = lvoBilRs.getInt("sviBizNtyOnrShipLocD");
      saleBilModel.cviBizNtyOnrShipLocF = lvoBilRs.getInt("sviBizNtyOnrShipLocF");
      saleBilModel.cvsBnfcrOnr          = lvoBilRs.getString("svsBnfcrOnr");
      saleBilModel.cviBnfcrOnrD         = lvoBilRs.getInt("sviBnfcrOnrD");
      saleBilModel.cvsBilSts            = lvoBilRs.getString("svsBilSts");
      saleBilModel.cvsBilIdy            = lvoBilRs.getString("svsBilIdy");
      saleBilModel.cvdBilDate           = lvoBilRs.getDate("svdBilDate").toLocalDate();
      saleBilModel.cvsBilAckDocIdy      = lvoBilRs.getString("svsBilAckDocIdy");
      saleBilModel.cvdBilAckDocDate     = lvoBilRs.getDate("svdBilAckDocDate").toLocalDate();
      saleBilModel.cvsPchsOrdrIdy       = lvoBilRs.getString("svsPchsOrdrIdy"); 
      saleBilModel.cvdPchsOrdrDate      = lvoBilRs.getDate("svdPchsOrdrDate").toLocalDate();
      saleBilModel.cvdBilAccDate        = lvoBilRs.getDate("svdBilAccDate").toLocalDate();
      saleBilModel.cvdBilDueDate        = lvoBilRs.getDate("svdBilDueDate").toLocalDate(); 
      saleBilModel.cvsBilCrncIdy        = lvoBilRs.getString("svsBilCrncIdy");
      saleBilModel.cvnBilXchngRate      = lvoBilRs.getInt("svnBilXchngRate");
      saleBilModel.cvsBillRCMAplbl      = lvoBilRs.getString("svsBillRCMAplbl"); 
      saleBilModel.cviBilPrprdUsrD      = lvoBilRs.getInt("sviBilPrprdUsrD");
      saleBilModel.cviBilAprvdUsrD      = lvoBilRs.getInt("sviBilAprvdUsrD");
      saleBilModel.cviTaxTmpltD         = lvoBilRs.getInt("sviTaxTmpltD");
      saleBilModel.cvsBilNotes          = lvoBilRs.getString("svsBilNotes"); 
      saleBilModel.cviBilD              = lvoBilRs.getInt("sviBilD"); 
      
      saleBilModel.cvoDBManager.addContextParam("rviBilD",pviBilD);
      lvoBilLineRs = saleBilModel.cvoDBManager.selectResultSet("cab\\saleBill\\sarBilLine"); 
      
      while(lvoBilLineRs.next()){
        String lvsBilLineActv         = lvoBilLineRs.getString("svsBilLineSts");
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
        lvoBilLineModel = SaleBilLineModel.load(lvsBilLineActv,lviBilD,lviItemD,lviItemF,lvsBilLineDesc,lvsBilLineUom,lvnBilLineQty,lvnBilLineUnitPrice,lvnBilLineDscnt,
                lvnBilLineVal,lvbBilLineHasSupotDoc,lvsBilLineDspsl,lvsCntxt,lviCntxtBizNtyD,lviCntxtNgmtD,lvsBilLineHSN,lviLnkdBilLineD,lvsBilLineNotes,
                lviBilLineD,saleBilModel.cvoDBManager);
       
        saleBilModel.cvoBilLineModelList.add(lvoBilLineModel);
      }
    }
   } else{
      saleBilModel.cviBilD = -1;
    }
    return saleBilModel;        
  } 
  
   public SaleBilLineModel getSaleBilLineModel(int pviBilLineD) {
   for (SaleBilLineModel saleBilLineModel : cvoBilLineModelList) { 
      if(saleBilLineModel.cviBilLineD == pviBilLineD){ 
        return saleBilLineModel; 
      } 
    } 
    return null;   
  } 

  public SaleBilLineModel createSaleBilLineModel(){ 
    SaleBilLineModel lvoSaleBilLineModel = SaleBilLineModel.createNew(cviBilD,cvoDBManager); 
    cvoBilLineModelList.add(lvoSaleBilLineModel); 
    return lvoSaleBilLineModel; 
  } 
  
  public void save() throws SQLException{
    CoreValidator.validateAutoFlag(this); 
    if(isChanged){
      cvoDBManager.addContextParam("rvsBilType",cvsBilType);
      cvoDBManager.addContextParam("rviBizNtyOnrLocD",cviBizNtyOnrLocD);
      cvoDBManager.addContextParam("rviTxctgPartyLocD",cviTxctgPartyLocD);
      cvoDBManager.addContextParam("rviBizNtyOnrLocF",cviBizNtyOnrLocF);
      cvoDBManager.addContextParam("rviTxctgPartyLocF",cviTxctgPartyLocF);
      cvoDBManager.addContextParam("rviBizNtyOnrShipLocD",cviBizNtyOnrShipLocD);
      cvoDBManager.addContextParam("rviBizNtyOnrShipLocF",cviBizNtyOnrShipLocF);
      cvoDBManager.addContextParam("rviTxctgPartyShipLocD",cviTxctgPartyShipLocD);
      cvoDBManager.addContextParam("rviTxctgPartyShipLocF",cviTxctgPartyShipLocF);
      cvoDBManager.addContextParam("rvsBnfcrOnr",cvsBnfcrOnr);
      cvoDBManager.addContextParam("rviBnfcrOnrD",cviBnfcrOnrD);
      cvoDBManager.addContextParam("rviBnfcrOnrF",cviBnfcrOnrF);
      cvoDBManager.addContextParam("rvsBilSts",cvsBilSts);
      cvoDBManager.addContextParam("rvsBilIdy",cvsBilIdy);
      cvoDBManager.addContextParam("rvdBilDate",cvdBilDate);
      cvoDBManager.addContextParam("rvsBilAckDocIdy",cvsBilAckDocIdy);
      cvoDBManager.addContextParam("rvdBilAckDocDate",cvdBilAckDocDate);
      cvoDBManager.addContextParam("rvsPchsOrdrIdy",cvsPchsOrdrIdy);
      cvoDBManager.addContextParam("rvdPchsOrdrDate",cvdPchsOrdrDate);
      cvoDBManager.addContextParam("rvdBilAccDate",cvdBilAccDate);
      cvoDBManager.addContextParam("rvdBilDueDate",cvdBilDueDate);
      cvoDBManager.addContextParam("rvsBilCrncIdy",cvsBilCrncIdy);
      cvoDBManager.addContextParam("rvnBilXchngRate",cvnBilXchngRate);
      cvoDBManager.addContextParam("rvsBillRCMAplbl",cvsBillRCMAplbl);
      cvoDBManager.addContextParam("rviBilPrprdUsrD",cviBilPrprdUsrD);
      cvoDBManager.addContextParam("rviBilAprvdUsrD",cviBilAprvdUsrD);
      cvoDBManager.addContextParam("rviTaxTmpltD",cviTaxTmpltD);
      cvoDBManager.addContextParam("rvsBilNotes",cvsBilNotes);
      
      if(cviBilD > 0){
        cvoDBManager.addContextParam("rviBilD",cviBilD);
        cvoDBManager.update("cab\\saleBill\\uarBil");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
       } else { 
        cvoDBManager.update("cab\\saleBill\\iarBil", true);  
        cviBilD = cvoDBManager.cviRecentAutoGenKey; 
      } 
      
   
   }
  }
}
