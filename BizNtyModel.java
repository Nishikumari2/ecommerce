/*
 * InSilico Solutions And Services 
 * www.insilicoss.com
 */
package com.insilicoss.bizNty;

import com.insilicoss.App;
import com.insilicoss.codegen.anotation.TrackChanges;
import com.insilicoss.database.DBManager;
import com.insilicoss.eventManager.OperationResponse;
import com.insilicoss.exception.PresentableException;
import com.insilicoss.exception.SystemException;
import com.insilicoss.util.Util;
import com.insilicoss.validation.AlphaDigitSpacePunch;
import com.insilicoss.validation.AppRule;
import com.insilicoss.validation.Constant;
import com.insilicoss.validation.CoreValidator;
import com.insilicoss.validation.Expression;
import org.apache.logging.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Alaknanda
 */
@TrackChanges 
public class BizNtyModel {

  private String cvsBizNtySts;
  
  @AlphaDigitSpacePunch(len = 50, name = "Idy", order = 1)
  private String cvsBizNtyIdy;
  
  private LocalDate cvdBizNtyFrom;

  private LocalDate cvdBizNtyTo = App.DEFAULT_MAX_DATE; 
  
  @AlphaDigitSpacePunch(len = 150, name = "Name", order = 2)
  private String cvsBizNtyName;

  //@AlphaDigitSpacePunch(len = 20, name = "PAN", order = 3)
  @Expression(regExp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message ="Invalid PAN", len = 10, allowEmpty = true) 
  private String cvsBizNtyNcomTaxIdy;

  @AlphaDigitSpacePunch(len = 20, name = "TAN",allowEmpty = true)
  private String cvsBizNtyNcomTaxWthdgIdy;

  @AlphaDigitSpacePunch(len = 20, name = "MSME", group = "step-2")
  private String cvsBizNtyMSMEIdy;

  @AlphaDigitSpacePunch(len = 25, name = "CIN")
  private String cvsBizNtyLegalIdy;

  @Constant(constantsClass = CoreValidator.ActiveContant.class, name = "Business Owner?")
  private String cvbIsBizOnr;

  @Constant(constantsClass = CoreValidator.ActiveContant.class, name = "Customer?")
  private String  cvbIsCstmr;
  
  @Constant(constantsClass = CoreValidator.ActiveContant.class, name = "Vendor?")
  private String  cvbIsVndr;

  private int cviBizNtyD;

  private final DBManager cvoDBManager;
  private final Logger cvoLogger;
  
  @AppRule(message = "", order = 1)
  public boolean validateOnrCstmrVndr(){
    if(cvbIsBizOnr.equals("1") && (cvbIsCstmr.equals("1")  || cvbIsVndr.equals("1"))){
       cvbIsBizOnr = "0";
      throw new PresentableException("You can't be both i.e. onwer and customer at same time");
    }
    else if(cvbIsBizOnr.equals("1") && cvbIsCstmr.equals("1")){
        cvbIsCstmr = "0";
       throw new PresentableException("You can't be both i.e. onwer and customer at same time");
    }
    else if(cvbIsBizOnr.equals("1") && cvbIsVndr.equals("1")){
        cvbIsVndr = "0";
       throw new PresentableException("You can't be both i.e. onwer and customer at same time");
    }
    return true; 
  }
  
  @AppRule(message="",order = 3)
  public OperationResponse validateFromToDate(){
    if (Util.isBefore(cvdBizNtyFrom, cvdBizNtyTo)) {
      return new OperationResponse(true,"");
    } 
    return new OperationResponse(false,"Deactive/to date should be after creation/from date."); 
} 

  @AppRule(message ="Duplicate Idy") 
  public OperationResponse validateIsBizNtyIdyUnique() throws SQLException{
    int count=0;
    cvoDBManager.addContextParam("rvsBizNtyIdy", cvsBizNtyIdy);
    cvoDBManager.addContextParam("rviBizNtyD",cviBizNtyD);
    ResultSet lvoBizNtyIdy = cvoDBManager.selectResultSet("BizNtty\\sarCountBizNty"); 
    lvoBizNtyIdy.next(); 
    count = lvoBizNtyIdy.getInt("svsCountBizNty"); 
    if(count != 0){
      return new OperationResponse(false, "This business Idy \"" + cvsBizNtyIdy + "\" is already exist. or add new");
    }
    return new OperationResponse(true,"");
  } 

  @AppRule(message ="Duplicate CIN") 
  public OperationResponse validateIsBizNtyCin() throws SQLException{
    int count=0;
    cvoDBManager.addContextParam("rvsBizNtyLegalIdy",cvsBizNtyLegalIdy);
    cvoDBManager.addContextParam("rviBizNtyD",cviBizNtyD);
    ResultSet lvoBizNtyLegalIdy = cvoDBManager.selectResultSet("BizNtty\\sarCountBizNtyLegalIdy");
    lvoBizNtyLegalIdy.next(); 
    count = lvoBizNtyLegalIdy.getInt("svsCountLegalIdy"); 
    if(count != 0){
      return new OperationResponse(false, "This CIN  \"" + cvsBizNtyLegalIdy + "\" is already exist. or add new");
    }
    return new OperationResponse(true,"");
  }

  private BizNtyModel(DBManager dBManager){
    this.cvoDBManager = dBManager;
    this.cvoLogger = LogManager.getLogger();
    this.cviBizNtyD = -1;
  }
  
  public static BizNtyModel load(int pviBizNtyD, DBManager dBManager) throws SQLException{
    BizNtyModel bizNtyModel = new BizNtyModel(dBManager);
    bizNtyModel.cviBizNtyD = pviBizNtyD; 

    if(pviBizNtyD != -1)
    {
      dBManager.addContextParam("rviBizNtyD", pviBizNtyD);
      ResultSet resultSet = dBManager.selectResultSet("BizNtty/sarBizNtyIdy");
      if(resultSet.first()){
        bizNtyModel.cvsBizNtyIdy             = resultSet.getString("svsBizNtyIdy");
        bizNtyModel.cvdBizNtyFrom            = resultSet.getDate("svdBizNtyFrom").toLocalDate();
        bizNtyModel.cvdBizNtyTo              = resultSet.getDate("svdBizNtyTo").toLocalDate();
        bizNtyModel.cvsBizNtyName            = resultSet.getString("svsBizNtyName");
        bizNtyModel.cvsBizNtyNcomTaxIdy      = resultSet.getString("svsNcomTaxIdy");
        bizNtyModel.cvsBizNtyNcomTaxWthdgIdy = resultSet.getString("svsBizNtyNcomTaxWthdgIdy");
        bizNtyModel.cvsBizNtyMSMEIdy         = resultSet.getString("svsBizNtyMSMEIdy");
        bizNtyModel.cvsBizNtyLegalIdy        = resultSet.getString("svsLegalIdy");
        bizNtyModel.cvbIsBizOnr              = resultSet.getString("svsIsBizOnr");
        bizNtyModel.cvbIsCstmr               = resultSet.getString("svsIsCstmr");
        bizNtyModel.cvbIsVndr                = resultSet.getString("svsIsVndr");
        bizNtyModel.cviBizNtyD               = resultSet.getInt("sviBizNtyD");
      } 
    }
    return  bizNtyModel;
  }

  public BizNtyLocModel getBizNtyLocModel(int pviBizNtyLocD){ 
    try { 
      return BizNtyLocModel.load(cviBizNtyD, pviBizNtyLocD, cvoDBManager); 
    } 
    catch(SQLException se){ 
      throw new SystemException(se.getMessage(), se); 
    } 
  } 

  public void save(){ 
    if(!isChanged){
     return ;
    }
    
    CoreValidator.validateAutoFlag(this); 
    cvoDBManager.addContextParam("rvsBizNtyIdy",cvsBizNtyIdy);
    cvoDBManager.addContextParam("rvdBizNtyFrom",cvdBizNtyFrom);
    cvoDBManager.addContextParam("rvdBizNtyTo",cvdBizNtyTo);
    cvoDBManager.addContextParam("rvsBizNtyName",cvsBizNtyName);
    cvoDBManager.addContextParam("rvsBizNtyNcomTaxIdy",cvsBizNtyNcomTaxIdy);
    cvoDBManager.addContextParam("rvsBizNtyNcomTaxWthdgIdy",cvsBizNtyNcomTaxWthdgIdy);
    cvoDBManager.addContextParam("rvsBizNtyMSMEIdy",cvsBizNtyMSMEIdy);
    cvoDBManager.addContextParam("rvsBizNtyLegalIdy",cvsBizNtyLegalIdy);
    cvoDBManager.addContextParam("rvsBizNtyIsBizOnr",cvbIsBizOnr);
    cvoDBManager.addContextParam("rvsIsCstmr",cvbIsCstmr);
    cvoDBManager.addContextParam("rvsIsVndr",cvbIsVndr);
    
    if(cviBizNtyD > 0){ 
      cvoDBManager.update("BizNtty\\uarBizNty");
    }
    else{
      cvoDBManager.update("BizNtty\\iarBizNty", true);
      cviBizNtyD = cvoDBManager.cviRecentAutoGenKey; 
    } 
  }
} 