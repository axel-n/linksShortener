package com.example.links_shortener.controller.links;

import com.example.links_shortener.core.dto.LinkDto;
import com.example.links_shortener.core.model.Link;
import com.example.links_shortener.core.model.User;
import com.example.links_shortener.core.service.ILinkService;
import com.example.links_shortener.core.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class APIController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String templateForGuest = "{\"shortUrl\": \"%s\", \"longUrl\": \"%s\"}";
    private Link link;

    @Autowired
    private ILinkService linkService;

    @Autowired
    private IUserService userService;

    @PostMapping(value = "${spring.data.rest.base-path}/link", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addLink(@Valid final LinkDto linkDto, Authentication authentication) {

        link = linkService.addLink(linkDto.getLongUrl(), authentication);

        if (authentication == null) {
            return String.format(templateForGuest,
                    link.getShortUrl(), link.getLongUrl());
        }

        return link.toString();
    }

    @GetMapping(value = "${spring.data.rest.base-path}/user")
    public List<Link> viewLinksByUser(Authentication authentication, HttpServletResponse response) {

        // security config don't work...
        if (authentication != null) {

            User authorizedUser = userService.findByEmail(authentication.getName());

            log.info("authorized {}", authorizedUser);

            return linkService.findByUserId(authorizedUser.getId());
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

}


