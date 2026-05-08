package com.classschedule.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.classschedule.model.Subject
import com.classschedule.theme.SubjectColors

@Composable
fun SubjectCard(
    subject: Subject?,
    modifier: Modifier = Modifier
) {
    if (subject == null) {
        Card(
            modifier = modifier
                .width(60.dp)
                .height(64.dp)
                .padding(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {}
        return
    }

    val isDark = isSystemInDarkTheme()
    val containerColor = getSubjectContainerColor(subject, isDark)
    val contentColor = if (isDark) Color.White else getSubjectPrimaryColor(subject)

    Card(
        modifier = modifier
            .width(60.dp)
            .height(64.dp)
            .padding(2.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = subject.displayName,
                style = MaterialTheme.typography.titleSmall,
                color = contentColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subject.teacher,
                style = MaterialTheme.typography.bodySmall,
                color = contentColor.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun getSubjectContainerColor(subject: Subject, isDark: Boolean): Color = when (subject) {
    Subject.YU_WEN -> if (isDark) SubjectColors.YuWenDark else SubjectColors.YuWenLight
    Subject.SHU_XUE -> if (isDark) SubjectColors.ShuXueDark else SubjectColors.ShuXueLight
    Subject.YING_YU -> if (isDark) SubjectColors.YingYuDark else SubjectColors.YingYuLight
    Subject.TI_YU -> if (isDark) SubjectColors.TiYuDark else SubjectColors.TiYuLight
    Subject.PS -> if (isDark) SubjectColors.PSDark else SubjectColors.PSLight
    Subject.WANG_YE -> if (isDark) SubjectColors.WangYeDark else SubjectColors.WangYeLight
    Subject.SHU_JU -> if (isDark) SubjectColors.ShuJuDark else SubjectColors.ShuJuLight
    Subject.ZU_ZHUANG -> if (isDark) SubjectColors.ZuZhuangDark else SubjectColors.ZuZhuangLight
    Subject.SHE_YING -> if (isDark) SubjectColors.SheYingDark else SubjectColors.SheYingLight
    Subject.XIN_LI -> if (isDark) SubjectColors.XinLiDark else SubjectColors.XinLiLight
}

fun getSubjectPrimaryColor(subject: Subject): Color = when (subject) {
    Subject.YU_WEN -> SubjectColors.YuWen
    Subject.SHU_XUE -> SubjectColors.ShuXue
    Subject.YING_YU -> SubjectColors.YingYu
    Subject.TI_YU -> SubjectColors.TiYu
    Subject.PS -> SubjectColors.PS
    Subject.WANG_YE -> SubjectColors.WangYe
    Subject.SHU_JU -> SubjectColors.ShuJu
    Subject.ZU_ZHUANG -> SubjectColors.ZuZhuang
    Subject.SHE_YING -> SubjectColors.SheYing
    Subject.XIN_LI -> SubjectColors.XinLi
}
