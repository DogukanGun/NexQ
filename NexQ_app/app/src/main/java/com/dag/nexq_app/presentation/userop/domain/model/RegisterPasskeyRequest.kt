package com.dag.nexq_app.presentation.userop.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterPasskeyResponse(
    val challenge: String,
    val rp: Rp,
    val user: User,
    val pubKeyCredParams: List<PubKeyCredParam>,
    val timeout: Long,
    val attestation: String,
    val excludeCredentials: List<ExcludeCredential>,
    val authenticatorSelection: AuthenticatorSelection,
)

@Serializable
data class Rp(
    val name: String,
    val id: String,
)

@Serializable
data class User(
    val id: Long,
    val name: String,
    val displayName: String,
)

@Serializable
data class Permission(
    val id: Long,
    val permission: String,
    val users: List<String>,
)

@Serializable
data class PubKeyCredParam(
    val type: String,
    val alg: Long,
)

@Serializable
data class ExcludeCredential(
    val id: String,
    val type: String,
)

@Serializable
data class AuthenticatorSelection(
    val authenticatorAttachment: String,
    val requireResidentKey: Boolean,
    val residentKey: String,
    val userVerification: String,
)
