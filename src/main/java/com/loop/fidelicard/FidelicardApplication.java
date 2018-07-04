package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.service.OfferService;

@SpringBootApplication
public class FidelicardApplication {
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	FinalClientService finalClientService;
	@Autowired
	OfferService offerService;
	@Autowired
	CardService cardService;

	public static void main(String[] args) {
		SpringApplication.run(FidelicardApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			LoginUser loginUser = loginUserService.findByEmail("admin@gmail.com");
			if (loginUser == null) {
				LoginUser loginUser1 = LoginUser.builder().email("admin@gmail.com").password("123")
						.userRole(UserRole.ADMIN).build();
				loginUserService.save(loginUser1);
			}
//			history29();
//			history3();
			System.out.println(loginUserService.findAll());
		};
	}

	// private void history29() {
	// createLoginUserAsEnterprise("enterprise@gmail.com", "123",
	// UserRole.ENTERPRISE);
	// loginUserService.findAll().forEach(e -> System.out.println(e.getId()));
	// createEnterprise("super acai", 2l);
	// createOffer("compre 10 ganhe 1", "na compra de 10 acais o proximo eh de graca
	// :)", 10, 3);
	// }
	//
	// private void history3() {
	// createFinalClient("00321592344", "edimo@gmail.com");
	// addStamp(5l, 3l);
	// }

	// private void addStamp(Long finalClientId, Long enterpriseId) {
	// ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new
	// ClientIDAndEnterpriseIdDTO();
	// clientIDAndEnterpriseIdDTO.setEnterpriseId(enterpriseId);
	// clientIDAndEnterpriseIdDTO.setFinalClientId(finalClientId);
	// cardService.createCardFromClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
	// }
	//
	// private void createFinalClient(String uniqueIdentifier, String email) {
	// FinalClient finalClient =
	// FinalClient.builder().uniqueIdentifier(uniqueIdentifier).email(email).build();
	// finalClientService.save(finalClient);
	// }
	//
	// private void createOffer(String name, String description, int quantity, long
	// enterpriseId) {
	// Enterprise enterprise = enterpriseService.findById(enterpriseId);
	// Offer offer =
	// Offer.builder().name(name).description(description).quantity(quantity).enterprise(enterprise)
	// .build();
	// offerService.save(offer);
	//
	// }
	//
	//
	// private void createEnterprise(String enterpriseName, long finalCLientId) {
	// List<FinalClient> finalClients = new ArrayList<FinalClient>();
	// finalClients.add(finalClientService.findById(finalCLientId));
	// Enterprise enterprise =
	// Enterprise.builder().name(enterpriseName).finalClients(finalClients).build();
	// enterpriseService.save(enterprise);
	// }
	//
	// private void createLoginUserAsEnterprise(String email, String password,
	// UserRole userRole) {
	// LoginUser loginUser1 =
	// LoginUser.builder().email(email).password(password).userRole(userRole).build();
	// loginUserService.save(loginUser1);
	// }
}
