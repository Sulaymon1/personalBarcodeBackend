package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.personal.dto.SocialNetworkData;
import ru.personal.dto.Vkontakte.VkUser;
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

    private VkUser getVk(String profileId) {
       return parserService.parseVK(profileId);
    }

    private void getFB(User user, String profileId) {

    }

    @Override
    public SocialNetworkData getInfo(User user){
        SocialNetworkData socialNetworkData = new SocialNetworkData();
        SocialNetwork socialNetwork = user.getSocialNetwork();
        String vkId = socialNetwork.getVkId();
        System.out.println(vkId);
        VkUser vk = getVk(vkId);
        socialNetworkData.setVkUser(vk);
        return socialNetworkData;
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
