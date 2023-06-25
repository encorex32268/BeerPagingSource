package com.example.beerpagingsource.domain.mapper

import com.example.beerpagingsource.data.remote.BeerDto
import com.example.beerpagingsource.domain.Beer

fun BeerDto.toBeer() : Beer{
    return Beer(
        id=id,
        name=name,
        tagline = tagline,
        firstBrewed = first_brewed,
        description = description,
        imageUrl = image_url
    )
}