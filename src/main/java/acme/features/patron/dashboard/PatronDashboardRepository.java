package acme.features.patron.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronages.Status;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface PatronDashboardRepository extends AbstractRepository {

	@Query("select count(p) from Patronage p where p.status = :status ")
	int numberOfPatronages(Status status);

	@Query("select distinct p.budget.currency from Patronage p")
	Collection<String> currencies();
	
	@Query("select avg(p.budget.amount) from Patronage p where p.status = :status and p.budget.currency = :currency")
	Double averagePatronage(Status status,String currency);
	
	@Query("select stddev(p.budget.amount) from Patronage p where p.status = :status and p.budget.currency = :currency ")
	Double deviationPatronage(Status status,String currency);
	
	@Query("select min(p.budget.amount) from Patronage p where p.status = :status and p.budget.currency = :currency ")
	Double minimunPatronage(Status status,String currency);
	
	@Query("select max(p.budget.amount) from Patronage p where p.status = :status and p.budget.currency = :currency ")
	Double maximunPatronage(Status status,String currency);

}
