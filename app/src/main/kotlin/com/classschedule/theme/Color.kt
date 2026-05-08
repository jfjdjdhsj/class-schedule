package com.classschedule.theme

import androidx.compose.ui.graphics.Color

// Material 3 Light scheme
val PrimaryLight = Color(0xFF4A6741)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFCBF0BD)
val OnPrimaryContainerLight = Color(0xFF082100)

val SecondaryLight = Color(0xFF54634D)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFD7E8CD)
val OnSecondaryContainerLight = Color(0xFF121F0E)

val BackgroundLight = Color(0xFFF8FAF0)
val OnBackgroundLight = Color(0xFF1A1C18)
val SurfaceLight = Color(0xFFF8FAF0)
val OnSurfaceLight = Color(0xFF1A1C18)

// Material 3 Dark scheme
val PrimaryDark = Color(0xFFB0D4A2)
val OnPrimaryDark = Color(0xFF1D3713)
val PrimaryContainerDark = Color(0xFF334E2B)
val OnPrimaryContainerDark = Color(0xFFCBF0BD)

val SecondaryDark = Color(0xFFBCCBB2)
val OnSecondaryDark = Color(0xFF273421)
val SecondaryContainerDark = Color(0xFF3D4B37)
val OnSecondaryContainerDark = Color(0xFFD7E8CD)

val BackgroundDark = Color(0xFF1A1C18)
val OnBackgroundDark = Color(0xFFE2E3D9)
val SurfaceDark = Color(0xFF1A1C18)
val OnSurfaceDark = Color(0xFFE2E3D9)

// Subject-specific colors
object SubjectColors {
    // Primary colors for each subject
    val YuWen = Color(0xFF7B1FA2)       // Purple - 语文
    val ShuXue = Color(0xFF1565C0)      // Blue - 数学
    val YingYu = Color(0xFF2E7D32)      // Green - 英语
    val TiYu = Color(0xFFEF6C00)        // Orange - 体育
    val PS = Color(0xFFC62828)          // Red - PS
    val WangYe = Color(0xFF00838F)      // Cyan - 网页
    val ShuJu = Color(0xFFBF360C)       // DeepOrange - 数据
    val ZuZhuang = Color(0xFF4E342E)    // Brown - 组装
    val SheYing = Color(0xFFF9A825)     // Yellow - 摄影
    val XinLi = Color(0xFF00695C)       // Teal - 心理健康

    // Light container colors (card backgrounds)
    val YuWenLight = Color(0xFFF3E5F5)
    val ShuXueLight = Color(0xFFE3F2FD)
    val YingYuLight = Color(0xFFE8F5E9)
    val TiYuLight = Color(0xFFFFF3E0)
    val PSLight = Color(0xFFFFEBEE)
    val WangYeLight = Color(0xFFE0F7FA)
    val ShuJuLight = Color(0xFFFBE9E7)
    val ZuZhuangLight = Color(0xFFEFEBE9)
    val SheYingLight = Color(0xFFFFFDE7)
    val XinLiLight = Color(0xFFE0F2F1)

    // Dark container colors (card backgrounds in dark mode)
    val YuWenDark = Color(0xFF311B5E)
    val ShuXueDark = Color(0xFF0D2744)
    val YingYuDark = Color(0xFF0A3012)
    val TiYuDark = Color(0xFF3E2000)
    val PSDark = Color(0xFF3B0707)
    val WangYeDark = Color(0xFF003B40)
    val ShuJuDark = Color(0xFF3B1800)
    val ZuZhuangDark = Color(0xFF2C1B12)
    val SheYingDark = Color(0xFF3E2F00)
    val XinLiDark = Color(0xFF00302B)
}
