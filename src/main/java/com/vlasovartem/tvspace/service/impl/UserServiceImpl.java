package com.vlasovartem.tvspace.service.impl;

import com.vlasovartem.tvspace.entity.*;
import com.vlasovartem.tvspace.entity.enums.UserRole;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import com.vlasovartem.tvspace.persistence.repository.UserRepository;
import com.vlasovartem.tvspace.service.UserService;
import com.vlasovartem.tvspace.utils.exception.UserRegistrationException;
import com.vlasovartem.tvspace.utils.exception.UserSeriesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.vlasovartem.tvspace.utils.security.AuthenticatedUserPrincipalUtil.*;

/**
 * Created by artemvlasov on 08/12/15.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private SeriesRepository seriesRepository;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Qualifier("seriesRepository") SeriesRepository seriesRepository) {
        this.userRepository = userRepository;
        this.seriesRepository = seriesRepository;
    }

    @Override
    public void signup(User user) throws UserRegistrationException {
        user.setRole(UserRole.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if(userRepository.countByUsernameIgnoreCase(user.getUsername()) > 0 ||
                userRepository.countByEmailIgnoreCase(user.getEmail()) > 0) {
            throw new UserRegistrationException("User with submitted email or username is exists");
        }
        userRepository.save(user);
    }

    @Override
    public List<UserSeries> getUserSeries() {
        if(isAuthenticated()) {
            return userRepository.findOne(getAuthenticationId()).getUserSeries();
        }
        return null;
    }

    /**
     * Add Series with provided id to user series list. Check if provided season number is exists in list of season of
     * specified series, and check if provided episode number is exists in season.
     * @param id id of the Series
     * @param season number of the season
     * @param episode number of the episode
     * @return true if user series is successfully added to authenticated user, otherwise throw UserSeriesException
     */
    @Override
    public boolean markSeriesAsWatching(String id, int season, int episode) {
        String errorMessage = "Series id cannot be null";
        if(Objects.nonNull(id)) {
            Series series = seriesRepository.findOne(id);
            if(Objects.nonNull(series) && Objects.nonNull(series.getSeasons())) {
                List<Episode> episodes = series.getSeasons()
                        .stream().filter(s -> s.getSeasonNumber() == season).findFirst().orElseThrow(() ->
                        new UserSeriesException("Provided season number is greater than the last season of the " +
                                "series")).getEpisodes();
                if(Objects.nonNull(episodes) && episodes.stream().anyMatch(e -> e.getEpisodeNumber() == episode)) {
                    UserSeries userSeries = new UserSeries(id, season, episode);
                    User user = userRepository.findOne(getAuthenticationId());
                    if(Objects.nonNull(user)) {
                        if(Objects.nonNull(user.getUserSeries())) {
                            user.getUserSeries().add(userSeries);
                        } else {
                            user.setUserSeries(Collections.singletonList(userSeries));
                        }
                        userRepository.save(user);
                        return true;
                    }
                } else {
                    errorMessage = "Provided episode number is not exists in provided series season " +
                            "number.";
                }
            } else {
                errorMessage = "Series with provided id is not found or series does not contains any " +
                        "season";
            }
        }
        throw new UserSeriesException(errorMessage);
    }

    @Override
    public boolean markSeriesAsNotWatching(String seriesId) {
        if(userRepository.countByUserSeriesSeriesIdAndId(seriesId, getAuthenticationId()) > 0) {
            User user = userRepository.findOne(getAuthenticationId());
            user.getUserSeries().removeIf(us -> us.getSeriesId().equals(seriesId));
            userRepository.save(user);
        }
        return false;
    }
}
