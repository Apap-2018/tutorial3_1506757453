package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

/*
 * Pilot Controller Class
 */
@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					  @RequestParam(value = "licenseNumber", required = true) String licenseNumber,
					  @RequestParam(value = "name", required = true) String name,
					  @RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping(value= {"/pilot/view/license-number/{licenseNumber}"})
	public String viewPath(@PathVariable String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if (archive == null) {
			return "error";
		}
		else {
			model.addAttribute("pilot", archive);
			return "view-pilot";
		}
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping(value= {"/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
	public String updateFlyHour(@PathVariable String licenseNumber, @PathVariable int flyHour, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if (archive == null) {
			return "error";
		}
		else {
			archive.setFlyHour(flyHour);
			model.addAttribute("pilot", archive);
			return "update";
		}
	}
	
	@RequestMapping(value= {"/pilot/delete/id/{id}"})
	public String deletePilot(@PathVariable String id, Model model) {
		PilotModel archive = pilotService.getPilotDetailById(id);
		
		if (archive == null) {
			return "error-2";
		}
		else {
			for (int i = 0; i < pilotService.getPilotList().size(); i++) {
				if (archive.equals(pilotService.getPilotList().get(i))) {
					pilotService.getPilotList().remove(i);
					break;
				}
			}
			return "delete";
		}
	}
}
