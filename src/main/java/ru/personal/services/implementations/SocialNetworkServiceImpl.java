package ru.personal.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.personal.dto.SocialNetworkData;
import ru.personal.dto.Vkontakte.VkResponse;
import ru.personal.dto.Vkontakte.VkUser;
import ru.personal.models.SocialNetwork;
import ru.personal.models.User;
import ru.personal.repositories.UserRepository;
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

    private VkUser getVk(String profileId) {
        RestTemplate restTemplate = new RestTemplate();
        String http = "https://api.vk.com/method/users.get?user_ids=";
        String accessToken = "access_token=36a5cdb23b7b3b779177bad9e66d7f1b51fd7da5c841b7eb58fef0883bc6df2f08da500fc1d73bf5ce34f";
        String fields = "fields=bdate,photo_100,last_seen";
        String version = "v=5.85";
        String url = http + profileId + "&" + fields + "&" + accessToken + "&" + version;
        return restTemplate.getForEntity(url, VkResponse.class).getBody().getResponse().get(0);
    }

    private void getFB(User user, String profileId) {

    }

    @Override
    public SocialNetworkData getInfo(User user){
        SocialNetworkData socialNetworkData = new SocialNetworkData();
        SocialNetwork socialNetwork = user.getSocialNetwork();
        VkUser vk = getVk(socialNetwork.getVkId());
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
