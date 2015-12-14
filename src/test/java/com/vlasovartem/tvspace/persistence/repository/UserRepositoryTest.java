package com.vlasovartem.tvspace.persistence.repository;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.vlasovartem.tvspace.config.TestAppConfig;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by artemvlasov on 13/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@ActiveProfiles("test")
@UsingDataSet(locations = "user-data-test.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class UserRepositoryTest {

    @Rule
    public MongoDbRule remoteMongoDbRule = new MongoDbRule(mongoDb().databaseName("testpmdb").build());

    @Autowired
    private UserRepository userRepository;

    @Test
    public void loginUserTest () {
        assertNotNull(userRepository.loginUser("testusername"));
    }

    @Test
    public void loginUserWithDeletedUserTest () {
        assertNull(userRepository.loginUser("testmail1@gmail.com"));
    }

    @Test
    public void countByUsernameIgnoreCaseTest () {
        assertThat(userRepository.countByUsernameIgnoreCase("testusername"), is(1));
    }

    @Test
    public void countByUsernameIgnoreCaseWithInvalidUsernameTest () {
        assertThat(userRepository.countByUsernameIgnoreCase("invalid"), is(0));
    }


    @Test
    public void countByEmailIgnoreCaseTest () {
        assertThat(userRepository.countByEmailIgnoreCase("testmail1@gmail.com"), is(1));
    }

    @Test
    public void countByEmailIgnoreCaseWithInvalidEmailTest () {
        assertThat(userRepository.countByEmailIgnoreCase("invalid@gmail.com"), is(0));
    }

    @Test
    public void countByUserSeriesSeriesIdAndIdTest () {
        assertThat(userRepository.countByUserSeriesSeriesIdAndId("tt2193021", "566725ab0364e7312e89b6b3"), is(1));
    }

    @Test
    public void countByUserSeriesSeriesIdAndIdWithoutMatchesDataTest () {
        assertThat(userRepository.countByUserSeriesSeriesIdAndId("tt2193021", "566725ab0364e7312e89b6b5"), is(0));
    }
}
