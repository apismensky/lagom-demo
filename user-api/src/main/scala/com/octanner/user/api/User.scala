package com.octanner.user.api

import com.octanner.image.api.Image

case class User(userId: Long,
                firstName: String,
                lastName: String,
                email: String,
                image: Image)