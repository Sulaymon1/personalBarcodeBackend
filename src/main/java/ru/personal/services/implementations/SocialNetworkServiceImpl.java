package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.dto.SocialUser;
import ru.personal.models.SocialNetwork;
import ru.personal.models.User;
import ru.personal.repositories.UserRepository;
import ru.personal.services.interfaces.SocialNetworkService;
import ru.personal.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Date 24.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class SocialNetworkServiceImpl implements SocialNetworkService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private UserService userService;

    @Override
    public SocialUser getInfo(String token){
        User user = userService.getUserByToken(token);
        List<SocialNetwork> socialNetworkList = user.getSocialNetwork();
        SocialUser socialUser = new SocialUser();
        List<String> socialId = new ArrayList();
        List<String> socialType = new ArrayList();
        for (SocialNetwork socialNetwork: socialNetworkList){
            socialId.add(socialNetwork.getSocialId());
            socialType.add(socialNetwork.getSocialType());
        }
        socialUser.setId(socialId);
        socialUser.setSocialType(socialType);
        return socialUser;
    }

    @Override
    public void add(User user, String profileId, String socialType) {
        List<SocialNetwork> socialNetworkList = user.getSocialNetwork();
        if (socialNetworkList == null){
            socialNetworkList = new ArrayList<>();
        }

        Optional<SocialNetwork> optionalSocialNetwork = socialNetworkList.stream().filter(o -> o.getSocialType().equals(socialType)).findFirst();
        SocialNetwork socialNetwork ;
        if (optionalSocialNetwork.isPresent()){
            socialNetwork = optionalSocialNetwork.get();
            socialNetwork.setSocialId(profileId);
        }else {
            socialNetwork = new SocialNetwork();
            socialNetwork.setSocialType(socialType);
            socialNetwork.setSocialId(profileId);
        }
        socialNetworkList.add(socialNetwork);
        user.setSocialNetwork(socialNetworkList);
        userRepository.save(user);
    }
}
