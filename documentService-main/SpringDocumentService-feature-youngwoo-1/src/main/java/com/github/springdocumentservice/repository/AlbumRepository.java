package com.github.springdocumentservice.repository;


import com.github.springdocumentservice.domain.Album;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {


    Optional<Album> findByAlbumName(String albumName);
}
