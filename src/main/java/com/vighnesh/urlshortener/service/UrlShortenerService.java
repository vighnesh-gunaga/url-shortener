package com.vighnesh.urlshortener.service;

import com.vighnesh.urlshortener.entity.UrlMapping;
import com.vighnesh.urlshortener.repository.UrlMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public String createShortUrl(String originalUrl) {

        Optional<UrlMapping> mapping =
                urlMappingRepository.findByOriginalUrl(originalUrl);

        if (mapping.isPresent()) {
            return mapping.get().getShortCode();
        }

        String shortCode = generateShortCode();

        while (urlMappingRepository.findByShortCode(shortCode).isPresent()) {
            shortCode = generateShortCode();
        }

        UrlMapping newMapping = new UrlMapping();
        newMapping.setOriginalUrl(originalUrl);
        newMapping.setShortCode(shortCode);

        urlMappingRepository.save(newMapping);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode)
    {
        Optional<UrlMapping> mapping = urlMappingRepository.findByShortCode(shortCode);

        if(mapping.isPresent())
        {
            return mapping.get().getOriginalUrl();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Short URL not found");
    }

    private String generateShortCode()
    {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder shortCode = new StringBuilder();
        java.util.Random random = new java.util.Random();

        for(int i=0;i<6;i++)
        {
            int index = random.nextInt(characters.length());
            shortCode.append(characters.charAt(index));
        }
        return shortCode.toString();
    }
}
