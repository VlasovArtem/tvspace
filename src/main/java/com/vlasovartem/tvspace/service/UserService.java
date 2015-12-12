package com.vlasovartem.tvspace.service;

import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.entity.UserSeries;
import com.vlasovartem.tvspace.utils.exception.UserRegistrationException;

import java.util.List;

/**
 * Created by artemvlasov on 08/12/15.
 */
public interface UserService {
    void signup (User user) throws UserRegistrationException;
    List<UserSeries> getUserSeries();

    boolean markSeriesAsWatching(String id, int season, int episode);

    boolean markSeriesAsNotWatching(String id);

}
