package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.personal.dto.Vkontakte.SocialUser;
import ru.personal.models.SocialNetwork;
import ru.personal.models.User;
import ru.personal.repositories.UserRepository;
import ru.personal.services.interfaces.ParserService;
import ru.personal.services.interfaces.SocialNetworkService;

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
    private ParserService parserService;



    @Override
    public SocialUser getInfo(User user){
        SocialNetwork socialNetwork = user.getSocialNetwork();
        String vkId = socialNetwork.getVkId();
        String fbId = socialNetwork.getFbId();
        parserService.setSocialUser(new SocialUser());
        parserService.parseVK(vkId);
        parserService.parseFB(fbId);
        return parserService.getSocialUser();
    }

    @Override
    public void add(User user, String profileId, String socialType) {
        SocialNetwork socialNetwork = user.getSocialNetwork();
        if (socialNetwork == null){
            socialNetwork = new SocialNetwork();
        }
        if (socialType.equals("VK"))
            socialNetwork.setVkId(profileId);
        else if (socialType.equals("FB"))
            socialNetwork.setFbId(profileId);

        user.setSocialNetwork(socialNetwork);
        userRepository.save(user);
    }
}
