package com.classschedule.model

enum class Subject(val displayName: String, val teacher: String) {
    YU_WEN("语文", "孙敏"),
    SHU_XUE("数学", "李铭"),
    YING_YU("英语", "陶舒冉"),
    TI_YU("体育", "王本清"),
    PS("PS", "刘国娟"),
    WANG_YE("网页", "吉敏"),
    SHU_JU("数媒", "裴文彪"),
    ZU_ZHUANG("组装", "吴巍"),
    SHE_YING("摄影", "于仓真"),
    XIN_LI("心理健康", "梁素佩");
}

enum class SchoolDay(val displayName: String, val shortName: String) {
    MONDAY("星期一", "一"),
    TUESDAY("星期二", "二"),
    WEDNESDAY("星期三", "三"),
    THURSDAY("星期四", "四"),
    FRIDAY("星期五", "五");
}

enum class Period(val number: Int, val label: String, val isAfternoon: Boolean) {
    PERIOD_1(1, "第1节", false),
    PERIOD_2(2, "第2节", false),
    PERIOD_3(3, "第3节", false),
    PERIOD_4(4, "第4节", false),
    PERIOD_5(5, "第5节", true),
    PERIOD_6(6, "第6节", true),
    PERIOD_7(7, "第7节", true);
}

data class ScheduleItem(
    val day: SchoolDay,
    val period: Period,
    val subject: Subject?
)
