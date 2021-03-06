package com.malygos.gnemesuser.domin

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Document(collection = "GnemesUser")
data class GnemesUser(
        @Id
        var userId: String,
        var avatar: String,
        @NotBlank
        @Size(max = 20)
        var userName: String,
        @NotBlank
        @Size(max = 20)
        @Email
        @Indexed(unique = true)
        var email: String,
        var userPwd: String,
        var sex: Byte,
        var age: String,
        var registryDate: Date,
        var lastLoginTime: Date,
        var likedCollection: List<String>?,
        var savedCollection: List<String>?,
        var loginDevices: List<String>?,
        var readGnemesHistory: List<ReadingHistory>?,
        var isEnabledFlag: Boolean = true,
        var isCredentialsNonExpiredFlag: Boolean = true,
        var isAccountNonLockedLag: Boolean = true,
        var isAccountNonExpiredFlag: Boolean = true,
        var roleType: String = "ROLE_USER"
) : UserDetails {
    constructor(email: String, userPwd: String, userName: String) : this("", "", userName, email, userPwd, 1, "", Date(), Date(), null, null, null, null)


    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return AuthorityUtils.createAuthorityList(this.roleType)
    }

    override fun isEnabled(): Boolean = isEnabledFlag

    override fun getUsername(): String = this.userName

    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpiredFlag

    override fun getPassword(): String = this.userPwd

    override fun isAccountNonExpired(): Boolean = isAccountNonExpiredFlag

    override fun isAccountNonLocked(): Boolean = isAccountNonLockedLag
}