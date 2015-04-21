package mapper;

import java.time.LocalDate;

import model.Company;
import model.Computer;
import servlet.ComputerDTO;

/* Fait le lien entre le modele objet et
 * les resultats obtenus par une requete
 */
/**
 * The Class Mapper.
 */
public class DTOMapper {

	public static ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO dto = new ComputerDTO();
		dto.setId(computer.getId());
		dto.setName(computer.getName());

		LocalDate introduced = computer.getIntroductionDate();
		LocalDate discontinued = computer.getDiscontinuationDate();
		Company company = computer.getCompany();

		if (introduced != null) {
			dto.setIntroduced(introduced.toString());
		}

		if (discontinued != null) {
			dto.setDiscontinued(discontinued.toString());
		}

		if (company != null) {
			dto.setCompanyId(company.getId());
			dto.setCompanyName(company.getName());
		}

		return dto;
	}
}
