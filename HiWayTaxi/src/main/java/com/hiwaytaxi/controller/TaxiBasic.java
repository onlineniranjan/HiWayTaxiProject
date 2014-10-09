package com.hiwaytaxi.controller;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppBindException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;

@Controller
public class TaxiBasic {
	private Logger logger = Logger.getLogger(TaxiBasic.class);
	String message = "Welcome to Spring MVC!";
	 
	@RequestMapping("/SendMessage")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in Taxi Basic controller");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		logger.info("This is message from logger" + message);
		return mv;
	}
	@RequestMapping("/BindSMSC")
	public void bindSmsc(){
		
		logger.info("hit bind..........******");
		DefaultSmppClient client = new DefaultSmppClient();
		
		SmppSessionConfiguration  sessionConfig = new SmppSessionConfiguration();
		
							sessionConfig.setType(SmppBindType.TRANSCEIVER);
							sessionConfig.setHost("127.0.0.1");
							sessionConfig.setPort(8006);
							sessionConfig.setSystemId("smppclient1");
							sessionConfig.setPassword("password");
							
							try {
								SmppSession session = client.bind(sessionConfig);
								
								SubmitSm sm = createSubmitSm("Test","6073727800","Test Niranjan","UCS-2");
								
								session.submit(sm, TimeUnit.SECONDS.toMillis(60));
								
								logger.info("Message Sent and waiting.....");
								
								TimeUnit.SECONDS.sleep(10);
								
								logger.info("Destroy session");
								session.close();
								
								session.destroy();
								
								logger.info("Destroy client");
								
								client.destroy();
								
								logger.info("....and done");
								
								
							} catch (SmppBindException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SmppTimeoutException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SmppChannelException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (UnrecoverablePduException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (RecoverablePduException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
	}
	
	public  SubmitSm createSubmitSm(String src, String dst, String text, String charset){
		
		SubmitSm sm = new SubmitSm();
		
		// For alpha numeric will use
	     // TON = 5
	     // NPI = 0
		sm.setSourceAddress(new Address ((byte) 5, (byte) 0, src ));
		
		// For national numbers will use
	     // TON = 1
	     // NPI = 1
		
		sm.setDestAddress(new Address((byte) 1, (byte) 1, dst));
		
		// Set datacoding to UCS-2
		sm.setDataCoding((byte) 8);
		
		try {
			
			
			sm.setShortMessage(CharsetUtil.encode(text, charset));
			
			
		} catch (SmppInvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sm;
		
		
	}
}
