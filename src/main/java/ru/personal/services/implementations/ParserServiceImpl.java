package ru.personal.services.implementations;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.personal.dto.SocialUser;
import ru.personal.services.interfaces.ParserService;

import java.io.IOException;

/**
 * Date 25.09.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class ParserServiceImpl implements ParserService {

    @Override
    public SocialUser getSocialUser() {
        return socialUser;
    }

    @Override
    public void setSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
    }

    private SocialUser socialUser;


    @Override
    public void parseVK(String profileId) {
        try {
            String url = "https://vk.com/"+profileId;
            Document document = Jsoup.connect(url).get();
            String fullName = document.getElementsByClass("page_name").get(0).text();
            Elements profile_deleted_text = document.getElementsByClass("profile_deleted_text");
            String lastSeen = null;
            String avatarImgUrl = null;
            if (profile_deleted_text.isEmpty()){
                Elements profile_time_lv = document.getElementsByClass("profile_time_lv");
                if (!profile_time_lv.isEmpty()){
                    lastSeen = profile_time_lv.get(0).text();
                }
                avatarImgUrl = document.getElementsByClass("page_avatar_img").get(0).attr("src");
            }
            socialUser.getFullName().add(fullName);
            socialUser.getLastSeen().add(lastSeen);
            socialUser.getProfilePhoto().add(avatarImgUrl);
            socialUser.getId().add(profileId);
            socialUser.getSocialType().add("vk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseFB(String profileId) {
        String url = "https://www.facebook.com/" + profileId;
        String fullName = null;
        String avatarImgUrl = null;
        try {
            Document document = Jsoup.connect(url).get();
            Elements isClosedPage = document.getElementsByClass("_585r _50f4");
            if (isClosedPage.isEmpty()) {
                fullName = document.getElementsByClass("_2nlw _2nlv").get(0).text();
                avatarImgUrl = document.getElementsByClass("_11kf img").get(0).attr("src");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        socialUser.getSocialType().add("fb");
        socialUser.getId().add(profileId);
        socialUser.getLastSeen().add(null);
        socialUser.getFullName().add(fullName);
        socialUser.getProfilePhoto().add(avatarImgUrl);
    }
}
