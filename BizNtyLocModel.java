package com.insilicoss.bizNty;

import com.insilicoss.App;
import com.insilicoss.codegen.anotation.TrackChanges;
import com.insilicoss.database.DBManager;
import com.insilicoss.eventManager.OperationResponse;
import com.insilicoss.exception.SystemException;
import com.insilicoss.validation.AlphaDigitSpacePunch;
import com.insilicoss.validation.Constant;
import com.insilicoss.validation.CoreValidator;
import java.time.LocalDate;
import com.insilicoss.validation.AppRule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.insilicoss.util.Util;
import com.insilicoss.validation.Expression;
/**
 *
 * @author Alaknanda
 */
@TrackChanges
public class BizNtyLocModel {
  
  public String cvsBizNtyLocSts;
  
  @AlphaDigitSpacePunch(len = 50, name = "Idy", order = 1)
  public String  cvsBizNtyLocIdy;
  
  @AlphaDigitSpacePunch(len = 50, name = "Name", order = 2)
  public String cvsBizNtyLocName;
  
  public LocalDate cvdBizNtyLocFrom;
  
  public LocalDate cvdBizNtyLocTo = App.DEFAULT_MAX_DATE; 
  
  
  @AppRule(message="",order=3)
  public OperationResponse validateFromToDate(){
    if (Util.isBefore(cvdBizNtyLocFrom, cvdBizNtyLocTo)) {
      return new OperationResponse(true,"");
    } 
    return new OperationResponse(false,"Deactive/to date should be after creation/from date."); 
}
  
  @AlphaDigitSpacePunch(len = 150, name = "Adrs Line 1", order = 4)
  public String cvsBizNtyLocAdrsLine1;
  
  @AlphaDigitSpacePunch(len = 150, name = "Adrs Line 2", order = 5)
  public String cvsBizNtyLocAdrsLine2;
      
  @AlphaDigitSpacePunch(len = 10, name = "Post Code", order = 6)
  public String cvsBizNtyLocPostCode;
      
  @AlphaDigitSpacePunch(len = 20, name = "State", order = 7)
  public String cvsBizNtyLocState;
  
  @AlphaDigitSpacePunch(len = 20, name = "Country", order = 8)
  public String cvsBizNtyLocCntry;
      
  @Expression(regExp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", message ="" ,len = 20, name = "GST IN", order = 9)
  public String cvsBizNtyLocSalesTaxIdy;
  
  @Constant(constantsClass = CoreValidator.ActiveContant.class, name = "Sez?")
  private String cvbizNtyLocIsSez;
 
  @com.insilicoss.validation.Number(name="Credit Period(Days)", min = "0", max = "999")
  public int cviBizNtyLocCrdtPrid;
  
  @Constant(constantsClass = CoreValidator.ActiveContant.class, name="Registered Office")
  public String cvbBizNtyLocIsRegOff;
  
  @AlphaDigitSpacePunch(len = 15, name = "PT Authority", order = 10)
  public String cvbBizNtyLocPtAthry;
  
  @Constant(constantsClass = CoreValidator.ActiveContant.class, name="Metropolitan Region")
  public String cvbBizNtyLocIsMtrReg;
  
  @Constant(constantsClass = CoreValidator.ActiveContant.class, name="Location Sell")
  public String cvbBizNtySysHostLocCanSell;
  
  @Constant(constantsClass = CoreValidator.ActiveContant.class, name="Location Purchase")
  public String cvbBizNtySysHostLocCanPchs;
  
  public int cviBizNtyD, cviBizNtyLocD;
  
  public int cviBizNtyLocF,cviBizNtyLocG;
  public int cviBizNtyHldyGrpD;
  
  private final DBManager cvoDBManager;
   private final Logger cvoLogger;
   private BizNtyLocModel(DBManager dBManager){
    this.cvoDBManager = dBManager;
    this.cvoLogger = LogManager.getLogger();
    this.cviBizNtyLocD = -1;
  }
  
  @AppRule(message = "duplicate location Idy")
  public OperationResponse validateIsBizNtyLocIdyUnique()throws SQLException{
    int count=0;
    cvoDBManager.addContextParam("rvsBizNtyLocIdy",cvsBizNtyLocIdy);
    cvoDBManager.addContextParam("rviBizNtyLocD",cviBizNtyLocD);
    ResultSet lvoBizNtyLocIdy =  cvoDBManager.selectResultSet("BizNtty/sarCountBizNtyLocIdy");
    lvoBizNtyLocIdy.next();
    count = lvoBizNtyLocIdy.getInt("svsBizNtyLocIdy");
    if(count!=0){
       return new OperationResponse(false, "This business Idy \"" + cvsBizNtyLocIdy + "\" is already exist. or add new");
    }
      return new OperationResponse(true,"");
  } 

  public static BizNtyLocModel load(int pviBizNtyD, int pviBizNtyLocD, DBManager dbManager)throws SQLException{ 
    if(pviBizNtyD == -1){ 
      throw new SystemException("BizNtyD can't be -1"); 
    } 

    BizNtyLocModel bizNtyLocModel = new BizNtyLocModel(dbManager); 
    bizNtyLocModel.cviBizNtyD     = pviBizNtyD; 
    bizNtyLocModel.cviBizNtyLocD  = pviBizNtyLocD; 
    
    if(pviBizNtyLocD != -1)
    {
      dbManager.addContextParam("rviBizNtyD", pviBizNtyD);
      dbManager.addContextParam("rviBizNtyLocD", pviBizNtyLocD);
      dbManager.addContextParam("rviBizNtyLocG",1);
      ResultSet resultSet = dbManager.selectResultSet("BizNtty/sarBizNtyLocIdy");
      if(resultSet.first())
      {
        bizNtyLocModel.cviBizNtyD                 = resultSet.getInt("sviBizNtyD");
        bizNtyLocModel.cvsBizNtyLocIdy            = resultSet.getString("svsBizNtyLocIdy");
        bizNtyLocModel.cvsBizNtyLocName           = resultSet.getString("svsBizNtyLocName");
        bizNtyLocModel.cvdBizNtyLocFrom           = resultSet.getDate("svdBizNtyLocFrom").toLocalDate();
        bizNtyLocModel.cvdBizNtyLocTo             = resultSet.getDate("svdBizNtyLocTo").toLocalDate();
        bizNtyLocModel.cvsBizNtyLocAdrsLine1      = resultSet.getString("svsBizNtyLocAdrsLine1");
        bizNtyLocModel.cvsBizNtyLocAdrsLine2      = resultSet.getString("svsBizNtyLocAdrsLine2");
        bizNtyLocModel.cvsBizNtyLocPostCode       = resultSet.getString("svsBizNtyLocPostCode");
        bizNtyLocModel.cvsBizNtyLocState          = resultSet.getString("svsLocState");
        bizNtyLocModel.cvsBizNtyLocCntry          = resultSet.getString("svsLocCntry");
        bizNtyLocModel.cvsBizNtyLocSalesTaxIdy    = resultSet.getString("svsLocSalesTaxIdy");
        bizNtyLocModel.cvbizNtyLocIsSez           = resultSet.getString("svsBizNtyLocIsSez");
        bizNtyLocModel.cviBizNtyLocCrdtPrid       = resultSet.getInt("sviBizNtyLocCrdtPrid");
        bizNtyLocModel.cvbBizNtyLocIsRegOff       = resultSet.getString("svsLocIsRegOff");
        bizNtyLocModel.cvbBizNtyLocPtAthry        = resultSet.getString("svsBizNtyLocPtAthry");
        bizNtyLocModel.cvbBizNtyLocIsMtrReg       = resultSet.getString("svsBizNtyLocIsMtrReg");
        bizNtyLocModel.cvbBizNtySysHostLocCanSell = resultSet.getString("svsBizNtySysHostLocCanSell");
        bizNtyLocModel.cvbBizNtySysHostLocCanPchs = resultSet.getString("svsBizNtySysHostLocCanPchs");
        bizNtyLocModel.cviBizNtyHldyGrpD          = resultSet.getInt("svsHldyGrpDesc");
        int lviBizNtyLocD                         = resultSet.getInt("sviBizNtyLocD");
        bizNtyLocModel.cviBizNtyLocD              = lviBizNtyLocD;
        int lviBizNtyLocF                         = resultSet.getInt("sviBizNtyLocF");
        bizNtyLocModel.cviBizNtyLocF              = lviBizNtyLocF;
        int lviBizNtyLocG                         = resultSet.getInt("sviBizNtyLocG");
        bizNtyLocModel.cviBizNtyLocG              = lviBizNtyLocG;
      } 
    }
    return bizNtyLocModel; 
  }
  
  public void save(){
    
    if(!isChanged){
     return ;
   }
    
    CoreValidator.validateAutoFlag(this); 
    cvoDBManager.addContextParam("rvsBizNtyLocIdy",cvsBizNtyLocIdy);
    cvoDBManager.addContextParam("rviBizNtyD",cviBizNtyD);
    cvoDBManager.addContextParam("rvsBizNtyLocName",cvsBizNtyLocName);
    cvoDBManager.addContextParam("rvdBizNtyLocFrom",cvdBizNtyLocFrom);
    cvoDBManager.addContextParam("rvdBizNtyLocTo",cvdBizNtyLocTo);
    cvoDBManager.addContextParam("rvsBizNtyLocLocAdrsLine1",cvsBizNtyLocAdrsLine1);
    cvoDBManager.addContextParam("rvsBizNtyLocLocAdrsLine2",cvsBizNtyLocAdrsLine2);
    cvoDBManager.addContextParam("rvsBizNtyLocPostCode",cvsBizNtyLocPostCode);
    cvoDBManager.addContextParam("rvsBizNtyLocState",cvsBizNtyLocState);
    cvoDBManager.addContextParam("rvsBizNtyLocCntry",cvsBizNtyLocCntry);
    cvoDBManager.addContextParam("rvsBizNtyLLococSalesTaxIdy",cvsBizNtyLocSalesTaxIdy);
    cvoDBManager.addContextParam("rvsBizNtyLocIsSez",cvbizNtyLocIsSez);
    cvoDBManager.addContextParam("rviBizNtyLocCrdtPrid",cviBizNtyLocCrdtPrid);
    cvoDBManager.addContextParam("rvsBizNtyLocIsRegOff",cvbBizNtyLocIsRegOff);
    cvoDBManager.addContextParam("rvsBizNtyLocPtAthry",cvbBizNtyLocPtAthry );
    cvoDBManager.addContextParam("rvsBizNtyLocIsMtrReg",cvbBizNtyLocIsMtrReg);
    cvoDBManager.addContextParam("rvsBizNtySysHostLocCanSell",cvbBizNtySysHostLocCanSell);
    cvoDBManager.addContextParam("rvsBizNtySysHostLocCanPchs",cvbBizNtySysHostLocCanPchs);
    cvoDBManager.addContextParam("rviBizNtyHldyGrpD",cviBizNtyHldyGrpD);
    cvoDBManager.addContextParam("rviBizNtyLocD",cviBizNtyLocD);
    cvoDBManager.addContextParam("rviBizNtyLocF",cviBizNtyLocF+1);
    cvoDBManager.addContextParam("rviBizNtyLocG",1);

    if(cviBizNtyLocD > 0){ 
      cvoDBManager.update("BizNtty\\uarBizNtyLocG"); 
      cvoDBManager.update("BizNtty\\iarBizNtyLoc"); 
    }  
    else{ 
      cviBizNtyLocD = cvoDBManager.getNextTxnD("tciBizNtyLocD"); 
      cvoDBManager.addContextParam("rviBizNtyLocD", cviBizNtyLocD); 
      cvoDBManager.update("BizNtty\\iarBizNtyLoc"); 
    } 
  } 
} 
