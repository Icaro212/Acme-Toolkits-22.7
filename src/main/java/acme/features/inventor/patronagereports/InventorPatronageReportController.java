package acme.features.inventor.patronagereports;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.patronagereports.PatronageReport;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorPatronageReportController extends AbstractController<Inventor, PatronageReport>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPatronageReportListService listService;
	
	@Autowired
	protected InventorPatronageReportShowService showService;
	
	// Constructors -----------------------------------------------------------
	
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
		
		super.addCommand("list-mine", "list", this.listService);
	}
	
	
}