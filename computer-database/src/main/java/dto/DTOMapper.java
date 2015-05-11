package dto;

import java.time.LocalDate;

import model.Company;
import model.Computer;

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

	/* Assume que tous les champs du dto sont valides */
	public static Computer toComputer(ComputerDTO dto) {
		LocalDate introduced = LocalDate.parse(dto.getIntroduced());
		LocalDate discontinued = LocalDate.parse(dto.getDiscontinued());
		Company company = new Company(dto.getCompanyId(), dto.getCompanyName());

		Computer computer = new Computer.Builder(dto.getName())
				.setIntroduced(introduced).setDiscontinued(discontinued)
				.setCompany(company).build();

		return computer;
	}
}
