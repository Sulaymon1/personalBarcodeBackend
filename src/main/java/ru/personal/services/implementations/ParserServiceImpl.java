package ru.personal.services.implementations;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.personal.dto.Vkontakte.VkUser;
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
    public VkUser parseVK(String profileId) {
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
            VkUser vkUser = new VkUser();
            vkUser.setFullName(fullName);
            vkUser.setLastSeen(lastSeen);
            vkUser.setProfilePhoto(avatarImgUrl);
            return vkUser;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
