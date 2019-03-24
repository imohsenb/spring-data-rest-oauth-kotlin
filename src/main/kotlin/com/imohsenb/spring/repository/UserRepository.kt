package com.imohsenb.spring.repository

import com.imohsenb.spring.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(@Param("username") username: String): User?
}