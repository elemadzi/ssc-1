package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;

import rewards.RewardNetwork;
import rewards.TestInfrastructureConfig;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;


@Configuration
//This import allows us to access Datasource infrustructure, Using Autowired annotation.
@Import(TestInfrastructureConfig.class)
public class RewardsConfig {
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	@Description("Repository Bean to access reward")
	public RewardRepository rewardRepository() {
		JdbcRewardRepository rewardRepository = new JdbcRewardRepository();
		rewardRepository.setDataSource(dataSource);
		return rewardRepository;
	}
	
	@Bean
	@Description("Repository Bean to access customer account")
	public AccountRepository accountRepository() {
		JdbcAccountRepository  accountRepository = new JdbcAccountRepository();
		accountRepository.setDataSource(dataSource);
		return accountRepository;
		
	}
	
	@Bean
	@Description("Repository Bean to access resturant")
	public RestaurantRepository restaurantRepository() {
		JdbcRestaurantRepository restaurantRepository = new JdbcRestaurantRepository();
		restaurantRepository.setDataSource(dataSource);
		return restaurantRepository;
	}
	
	@Bean
	@Description("Bean to interface with the reward bean functionality")
	public RewardNetwork rewardNetwork() {
		RewardRepository rewardRepository = rewardRepository();
		AccountRepository accountRepository = accountRepository();
		RestaurantRepository restaurantRepository = restaurantRepository();
		RewardNetwork rewardNetwork = new RewardNetworkImpl(accountRepository, restaurantRepository, rewardRepository);
		return rewardNetwork;
		
		
	}
		
	
}
