package acme.features.patron.patronage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronages.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Patron;

@Service
public class PatronPatronageUpdateService implements AbstractUpdateService<Patron, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronPatronageRepository repository;

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int masterId;
		final Patronage patronage;
		final Patron patron;

		masterId = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(masterId);
		patron = patronage.getPatron();
		result = patronage.isDraftMode() && request.isPrincipal(patron);

		return result;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("startDate")) {
			final Date creationDate = entity.getCreationDate();
			 final Date minimumStartDate = new Date();
			 if(creationDate.getMonth()<11) {
				 minimumStartDate.setHours(creationDate.getHours());
				 minimumStartDate.setMinutes(creationDate.getMinutes());
				 minimumStartDate.setSeconds(creationDate.getSeconds());
				 minimumStartDate.setYear(creationDate.getYear());
				 minimumStartDate.setMonth(creationDate.getMonth()+1);
			 }
			 else {
				 minimumStartDate.setHours(creationDate.getHours());
				 minimumStartDate.setMinutes(creationDate.getMinutes());
				 minimumStartDate.setSeconds(creationDate.getSeconds());
				 minimumStartDate.setYear(creationDate.getYear()+1);
				 minimumStartDate.setMonth(0); 
			 }
		
			 final Date startDate = entity.getStartDate();			
			errors.state(request, minimumStartDate.equals(startDate) || minimumStartDate.before(startDate), "startTime", "patron.patronage.form.error.too-close");
		}

		if (!errors.hasErrors("code")) {
			Patronage existing;

			existing = this.repository.findOnePatronageByCode(entity.getCode());
			errors.state(request, existing == null || existing.getId() == entity.getId(), "code", "patron.patronage.form.error.duplicated");
		}

		if (!errors.hasErrors("budget")) {
			
			errors.state(request, entity.getBudget().getAmount()>=0., "budget", "patron.patronage.form.error.negative-budget");
		}
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "legalStuff", "budget", "startDate", "endDate", "info");
		}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "status", "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "info","draftMode");
		
		final int masterId = request.getModel().getInteger("id");
		model.setAttribute("masterId", masterId);
		
		final String inventorName = entity.getInventor().getIdentity().getName();
		final String inventorSurname = entity.getInventor().getIdentity().getSurname();
		final String inventorEmail = entity.getInventor().getIdentity().getEmail();
		final String inventorCompany = entity.getInventor().getCompany();
		final String inventorStatement = entity.getInventor().getStatement();
		final String inventorInfo = entity.getInventor().getInfo();
		
		model.setAttribute("inventorName", inventorName);
		model.setAttribute("inventorSurname", inventorSurname);
		model.setAttribute("inventorEmail", inventorEmail);
		model.setAttribute("inventorCompany", inventorCompany);
		model.setAttribute("inventorStatement", inventorStatement);
		model.setAttribute("inventorInfo", inventorInfo);
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;

		Patronage result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);

		return result;
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
