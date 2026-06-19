package com.mayur.testapplicatiom.test.data.mappers

import com.mayur.testapplicatiom.test.data.remote.dto.UserListItem
import com.mayur.testapplicatiom.test.domain.model.User

fun UserListItem.toEntity() : User{
   return User(
    email = email,
    id = id,
    name = name,
    phone = phone,
    username = username,
    website = website
   )

}