// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
//        set("room_version", "2.6.0")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Add KSP plugin at project level
    alias(libs.plugins.ksp) apply false
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
}