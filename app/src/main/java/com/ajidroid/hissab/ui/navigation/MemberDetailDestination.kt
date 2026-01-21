package com.ajidroid.hissab.ui.navigation

object MemberDetailDestination : HissabDestination {
    const val MEMBER_ID_ARG = "memberId"

    override val route = "member_detail/{$MEMBER_ID_ARG}"

    fun createRoute(memberId: Int): String {
        return "member_detail/$memberId"
    }
}
