package app.labs.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.labs.mybatis.model.BillResult;
import app.labs.mybatis.model.OutputResult;
import app.labs.mybatis.service.FeeSettlementService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FeeSettlementController {

	@Autowired
	FeeSettlementService service;
	
	@GetMapping("/egate/input")
    public String validateId(Model model) {
        return "thymeleaf/html/input";
    }

	@GetMapping("/egate/output")
    public String printInfo(@RequestParam("userName") String userName,
    						Model model, HttpSession session) {
		boolean isNameAvailable = service.loginAccount(userName);
        
		if (isNameAvailable) {
			OutputResult outputResult = service.getOutputResult(userName);
			
			model.addAttribute("gatemodel", outputResult);
			session.setMaxInactiveInterval(600);
			session.setAttribute("userName", userName);
			
			return "thymeleaf/html/output";
		} else {
			return "thymeleaf/html/error";
		}
    }
	
	@GetMapping("/egate/bill")
	public String getPayInfo(Model model, HttpSession session) {
		BillResult billResult = service.getBillResult(session.getAttribute("userName").toString());

		model.addAttribute("title", billResult.getExitTollgateName());
        model.addAttribute("vehicleNumber", billResult.getVehicleNumber());
        model.addAttribute("date", billResult.getTollgateEntryTime());
        model.addAttribute("vehicleType", billResult.getVehicleType());
        model.addAttribute("paymentAmount", billResult.getPaymentAmount());
        model.addAttribute("balance", billResult.getRemainingBalance());
        model.addAttribute("unpaidAmount", billResult.getUnpaidAmount());

		return "thymeleaf/html/bill";
	}
	
	@PostMapping("/egate/bill")
	public String paidReturn(Model model) {
		return "thymeleaf/html/bill";
	}

}
