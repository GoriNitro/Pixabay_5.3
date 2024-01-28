package com.example.pixabay

data class PixabayModel(
    var hits: ArrayList<ImageModel>
)

data class ImageModel(
    var largeImageURL: String,
    var likes: Int
)
