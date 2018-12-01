package com.example.linksShortener;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {

}
