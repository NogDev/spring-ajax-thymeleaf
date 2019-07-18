/**
 * 
 */
package com.mballem.demoajax.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mballem.demoajax.domain.SocialMetaTag;

/**
 * @author andersonnogueira
 * @since Jul 17, 2019
 */

@Service
public class SocialMetaTagService {

	private static Logger log = LoggerFactory.getLogger(SocialMetaTag.class);
	
	public SocialMetaTag getSocialMetatagByUrl(String url) {
		SocialMetaTag twitter = getTwitterCardByUrl(url);
		if(!isEmpty(twitter)) {
			return twitter;
		}
		
		SocialMetaTag openGraph = getOpenGraphByUrl(url);
		if(!isEmpty(openGraph)) {
			return openGraph;
		}
		return null;
	}
	
	private SocialMetaTag getTwitterCardByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();

		try {
			Document doc = Jsoup.connect(url).get();
			tag.setTitle(doc.head().select("meta[name=twitter:title]").attr("content"));
			tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content"));
			tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
			tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
		} catch (IOException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return tag;
	}

	private SocialMetaTag getOpenGraphByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();

		try {
			Document doc = Jsoup.connect(url).get();
			tag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
			tag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
			tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
			tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
		} catch (IOException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return tag;
	}
	
	private boolean isEmpty(SocialMetaTag tag) {
		if(tag.getImage().isEmpty() ||
		   tag.getSite().isEmpty() ||
		   tag.getTitle().isEmpty() ||
		   tag.getUrl().isEmpty()) return true;
		return false;
	}

}
