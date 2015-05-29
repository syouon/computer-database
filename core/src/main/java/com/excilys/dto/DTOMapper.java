package com.excilys.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.excilys.model.Company;
import com.excilys.model.Computer;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * The Class Mapper. Provides static conversion methods between Computer and
 * ComputerDTO.
 */
public class DTOMapper {

	/**
	 * Converts a computer to a dto.
	 *
	 * @param computer
	 *            the computer to convert
	 * @return the computer dto
	 */
	public static ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO dto = new ComputerDTO();
		dto.setId(computer.getId());
		dto.setName(computer.getName());

		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		Company company = computer.getCompany();

		// Gets the current locale
		Locale locale = LocaleContextHolder.getLocale();
		DateTimeFormatter formatter = null;
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", locale);
		} else { // if Locale is fr
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

	/**
	 * Converts a computer dto to a computer.
	 *
	 * @param dto
	 *            the dto to convert
	 * @return the computer
	 */
	public static Computer toComputer(ComputerDTO dto) {
		String dtoIntroduced = dto.getIntroduced();
		String dtoDiscontinued = dto.getDiscontinued();
		DateTimeFormatter englishFormatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd");
		DateTimeFormatter frenchFormatter = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

		Locale locale = LocaleContextHolder.getLocale();

		LocalDate introduced = null;
		if (dtoIntroduced != null) {
			// if in french format, we convert it in english format
			if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
				if (!dtoIntroduced.isEmpty()) {
					dtoIntroduced = englishFormatter.format(frenchFormatter
							.parse(dtoIntroduced));
				}
			}

			introduced = dtoIntroduced.isEmpty() ? null : LocalDate
					.parse(dtoIntroduced);
		}

		LocalDate discontinued = null;
		if (dtoDiscontinued != null) {
			// if in french format, we convert it in english format
			if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
				if (!dtoDiscontinued.isEmpty()) {
					dtoDiscontinued = englishFormatter.format(frenchFormatter
							.parse(dtoDiscontinued));
				}
			}

			discontinued = dtoDiscontinued.isEmpty() ? null : LocalDate
					.parse(dtoDiscontinued);
		}

		Company company = null;
		long companyId = dto.getCompanyId();
		if (companyId > 0) {
			company = new Company(companyId, dto.getCompanyName());
		}

		Computer computer = new Computer.Builder(dto.getName())
				.setId(dto.getId()).setIntroduced(introduced)
				.setDiscontinued(discontinued).setCompany(company).build();

		return computer;
	}
}
