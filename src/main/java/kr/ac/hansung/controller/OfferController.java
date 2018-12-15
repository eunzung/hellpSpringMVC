package kr.ac.hansung.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.hansung.model.Offer;
import kr.ac.hansung.service.OfferService;

@Controller
public class OfferController {

	@Autowired
	private OfferService offerService;

	@RequestMapping("/link")
	public String showOffers4(@RequestParam("year") int year, @RequestParam("term") int term, Model model) {

		List<Offer> offers = offerService.getLink(year, term);
		model.addAttribute("offer", offers);

		return "link";
	}

	@RequestMapping("/refer")
	public String showOffers2(Model model) {

		List<Offer> offers = offerService.getYear();
		model.addAttribute("offer", offers);

		return "refer";
	}


	@RequestMapping("/offers")
	public String showOffers(Model model) {
		List<Offer> offers = offerService.getCurrent();
		model.addAttribute("offers", offers);

		return "offers";
	}

	@RequestMapping("/createoffer")
	public String createOffer(Model model) {

		model.addAttribute("offer", new Offer());

		return "createoffer";
	}

	@RequestMapping("/next")
	public String futureOffer(Model model) {

		List<Offer> offer = offerService.getNext();
		model.addAttribute("offer", offer);

		return "next";
	}

	@RequestMapping("/docreate")
	public String createOffer(Model model, @Valid Offer offer, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("==Form data does not validated");
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage());
			}
			return "createoffer";
		}
		offerService.insert(offer);

		return "offercreated";
	}

}