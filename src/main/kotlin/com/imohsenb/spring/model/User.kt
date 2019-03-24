package com.imohsenb.spring.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.FetchType
import javax.persistence.ManyToMany
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue


@Entity
@Table(name = "app_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    @JsonIgnore
    var password: String? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    /**
     * Roles are being eagerly loaded here because
     * they are a fairly small collection of items for this example.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: List<Role>? = null
}
