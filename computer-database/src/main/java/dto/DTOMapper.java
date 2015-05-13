package dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import model.Company;
import model.Computer;

import org.springframework.context.i18n.LocaleContextHolder;

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

		Locale locale = LocaleContextHolder.getLocale();
		DateTimeFormatter formatter = null;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", locale);
		} else { // Locale fr
			formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", locale);
		}

		if (introduced != null) {
			dto.setIntroduced(introduced.format(formatter));
		}

		if (discontinued != null) {
			dto.setDiscontinued(discontinued.format(formatter));
		}

		if (company != null) {
			dto.setCompanyId(company.getId());
			dto.setCompanyName(company.getName());
		}

		return dto;
	}

	/* Assume que tous les champs du dto sont valides */
	public static Computer toComputer(ComputerDTO dto) {
		String dtoIntroduced = dto.getIntroduced();
		String dtoDiscontinued = dto.getDiscontinued();
		DateTimeFormatter englishFormatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd");
		DateTimeFormatter frenchFormatter = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

		// Si les string de date sont en francais, on les convertit en anglais
		Locale locale = LocaleContextHolder.getLocale();
		if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
			if (!dtoIntroduced.isEmpty()) {
				dtoIntroduced = englishFormatter.format(frenchFormatter
						.parse(dtoIntroduced));
			}
			if (!dtoDiscontinued.isEmpty()) {
				dtoDiscontinued = englishFormatter.format(frenchFormatter
						.parse(dtoDiscontinued));
			}
		}

		LocalDate introduced = dtoIntroduced.isEmpty() ? null : LocalDate
				.parse(dtoIntroduced);
		LocalDate discontinued = dtoDiscontinued.isEmpty() ? null : LocalDate
				.parse(dtoDiscontinued);

		Company company = null;
		long companyId = dto.getCompanyId();
		if (companyId > 0) {
			company = new Company(companyId, dto.getCompanyName());
		}

		Computer computer = new Computer.Builder(dto.getName())
				.setIntroduced(introduced).setDiscontinued(discontinued)
				.setCompany(company).build();

		return computer;
	}
}
