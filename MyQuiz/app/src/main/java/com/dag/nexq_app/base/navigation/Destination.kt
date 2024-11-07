package com.dag.nexq_app.base.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Splash: Destination

    @Serializable
    data object HomeGraph: Destination

    @Serializable
    data object AuthGraph: Destination

    @Serializable
    data object LoginScreen: Destination

    @Serializable
    data object HomeScreen: Destination

    @Serializable
    data object Onboard: Destination

}