
package com.example.linksShortener;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Link {

	private @Id @GeneratedValue Long id;
	private String shortUrl;
	private String longUrl;

	// default constructor. don't remove
	public Link() {}

	public Link(String shortUrl, String longUrl) {
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}
}