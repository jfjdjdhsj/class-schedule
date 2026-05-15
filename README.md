# Class Schedule（课程表）

一个基于 **Kotlin + Jetpack Compose + Material 3** 的 Android 课程表应用。  
支持课表总览、课程详情弹窗、按当前时间自动识别课程并弹窗提醒（应用内弹窗，不是系统通知）。

---

## 目录

- [1. 项目概览](#1-项目概览)
- [2. 核心功能](#2-核心功能)
- [3. 自动弹窗规则（重点）](#3-自动弹窗规则重点)
- [4. 通知功能现状](#4-通知功能现状)
- [5. 技术栈与版本](#5-技术栈与版本)
- [6. 项目结构](#6-项目结构)
- [7. 数据模型与课程时间](#7-数据模型与课程时间)
- [8. 本地开发与运行](#8-本地开发与运行)
- [9. 签名与发布](#9-签名与发布)
- [10. GitHub Actions 云端构建](#10-github-actions-云端构建)
- [11. 版本号策略（项目约定）](#11-版本号策略项目约定)
- [12. 常见问题排查](#12-常见问题排查)
- [13. 最近关键更新](#13-最近关键更新)

---

## 1. 项目概览

- **应用名称**：课程表（Class Schedule）
- **包名**：`com.classschedule`
- **最低系统**：Android 8.0（API 26）
- **目标系统**：Android 16（API 36）
- **当前版本**：`2.0.0`（`versionCode = 4`）

本项目以“**打开即看、少操作、信息直达**”为目标：
- 主界面直接展示完整周课表；
- 点击任意科目可查看该节课上下课时间；
- 打开 App 时自动识别“当前节次”并弹出当前课程详情；
- 同一节课只弹一次，避免重复打扰。

---

## 2. 核心功能

### 2.1 课程表网格展示
- 按周一到周五横向展示。
- 按第 1~7 节纵向展示。
- 上午/下午分段显示。
- 当前星期列高亮。

### 2.2 科目卡片
- 每个单元格显示：
  - 科目名称
  - 任课老师
- 不同科目使用不同主题色。

### 2.3 点击科目弹窗
- 点击科目卡片弹出详情：
  - 科目名称
  - 老师 + 节次
  - 上课时间
  - 下课时间
- 第 4 节额外提示：
  - 第一轮 11:35
  - 第二轮 12:00（不能提前下课）

### 2.4 打开应用自动弹窗（应用内）
- 打开应用时自动根据“当前日期 + 当前时间”匹配是否正在上课。
- 命中课程后自动弹出同款详情弹窗。
- 周六周日不弹。

### 2.5 同一节课只弹一次（跨重开有效）
- 记录“日期 + 节次”作为去重键。
- 同一节课期间反复打开 App，不会重复弹出。
- 到下一节课会重新允许弹出一次。

---

## 3. 自动弹窗规则（重点）

自动弹窗逻辑位于：
- `app/src/main/kotlin/com/classschedule/ui/ScheduleGrid.kt`

规则如下：

1. 仅在工作日（周一~周五）参与匹配。  
2. 读取当前时间，与每节课 `startTime/endTime` 区间比较。  
3. 匹配到当前节次后，查课表单元格是否有科目。  
4. 生成去重键：`yyyy-MM-dd-节次`（例如 `2026-05-14-3`）。  
5. 若该键与上次已弹键不同，则弹窗并写入本地存储。  
6. 若相同，则不再重复弹。

> 说明：为兼容 `8:20` 这种时间格式，时间解析使用 `H:mm`（不是 `HH:mm`）。

---

## 4. 通知功能现状

项目当前仍保留了 WorkManager 的每日通知能力（历史功能），主要文件：
- `app/src/main/kotlin/com/classschedule/notification/WorkScheduler.kt`
- `app/src/main/kotlin/com/classschedule/notification/ScheduleNotificationWorker.kt`
- `app/src/main/kotlin/com/classschedule/notification/NotificationHelper.kt`
- `app/src/main/kotlin/com/classschedule/notification/NotificationMessageBuilder.kt`

通知行为（现状）：
- 07:30 上午课程通知
- 13:30 下午课程通知
- 周五下午无课时文案：“放假了，开心吗”

> 如果你只想保留“应用内自动弹窗”，可在后续版本中移除通知调度入口。

---

## 5. 技术栈与版本

### 5.1 语言与框架
- Kotlin `2.1.0`
- Android Gradle Plugin `8.7.3`
- Jetpack Compose（BOM `2024.12.01`）
- Material 3 `1.3.1`

### 5.2 主要依赖
- `androidx.core:core-ktx:1.15.0`
- `androidx.lifecycle:lifecycle-runtime-ktx:2.8.7`
- `androidx.activity:activity-compose:1.9.3`
- `androidx.compose.material3:material3:1.3.1`
- `androidx.work:work-runtime-ktx:2.10.1`

依赖集中管理文件：
- `gradle/libs.versions.toml`

---

## 6. 项目结构

```text
classschedule/
├─ app/
│  ├─ build.gradle.kts
│  └─ src/main/
│     ├─ AndroidManifest.xml
│     ├─ kotlin/com/classschedule/
│     │  ├─ MainActivity.kt
│     │  ├─ data/
│     │  │  └─ ScheduleRepository.kt
│     │  ├─ model/
│     │  │  └─ ScheduleModels.kt
│     │  ├─ ui/
│     │  │  ├─ ScheduleScreen.kt
│     │  │  ├─ ScheduleGrid.kt
│     │  │  ├─ SubjectCard.kt
│     │  │  └─ PeriodIndicator.kt
│     │  ├─ theme/
│     │  │  ├─ Color.kt
│     │  │  ├─ Theme.kt
│     │  │  └─ Type.kt
│     │  └─ notification/
│     │     ├─ WorkScheduler.kt
│     │     ├─ ScheduleNotificationWorker.kt
│     │     ├─ NotificationHelper.kt
│     │     └─ NotificationMessageBuilder.kt
│     └─ res/
│        ├─ values/
│        ├─ values-night/
│        ├─ mipmap-anydpi-v26/
│        └─ drawable-nodpi/
├─ gradle/libs.versions.toml
├─ build.gradle.kts
├─ settings.gradle.kts
└─ README.md
```

---

## 7. 数据模型与课程时间

### 7.1 关键模型
文件：`app/src/main/kotlin/com/classschedule/model/ScheduleModels.kt`

- `Subject(displayName, teacher)`：科目及老师
- `SchoolDay(displayName, shortName)`：周一到周五
- `Period(number, label, isAfternoon, startTime, endTime)`：节次与时间
- `ScheduleItem(day, period, subject)`：某天某节课的科目

### 7.2 节次时间表

| 节次 | 时间 | 时段 |
|---|---|---|
| 第1节 | 08:20 - 09:05 | 上午 |
| 第2节 | 09:15 - 10:00 | 上午 |
| 第3节 | 10:20 - 11:05 | 上午 |
| 第4节 | 11:15 - 12:00 | 上午 |
| 第5节 | 15:10 - 15:55 | 下午 |
| 第6节 | 16:05 - 16:50 | 下午 |
| 第7节 | 17:00 - 17:45 | 下午 |

### 7.3 课程数据源
文件：`app/src/main/kotlin/com/classschedule/data/ScheduleRepository.kt`

- 使用内存静态表维护一周课表。
- 提供按单元格查询、上午课程查询、下午课程查询等方法。

---

## 8. 本地开发与运行

### 8.1 环境要求
- Android Studio（推荐最新稳定版）
- JDK 11
- Android SDK 36

### 8.2 拉取与运行

```bash
git clone https://github.com/jfjdjdhsj/class-schedule.git
cd class-schedule
./gradlew :app:assembleDebug
```

安装 `app/build/outputs/apk/debug/*.apk` 到设备后即可运行。

---

## 9. 签名与发布

`app/build.gradle.kts` 已支持通过 `key.properties` 注入签名配置（可选）。

`key.properties` 示例：

```properties
storeFile=keystore/your-release-key.jks
storePassword=******
keyAlias=******
keyPassword=******
storeType=PKCS12
```

说明：
- 若 `key.properties` 不存在，release 配置不会强制签名字段。
- 请勿将真实密钥信息提交到公开仓库。

---

## 10. GitHub Actions 云端构建

仓库已配置 `Build APK` 工作流。  
推送到 `main` 后会自动触发云端打包。

你可以通过 GitHub Actions 页面查看：
- 构建状态（in_progress / success / failed）
- 失败日志
- 产物 APK（若流程中有上传产物步骤）

---

## 11. 版本号策略（项目约定）

当前项目采用以下规则：

1. **修复 Bug**：升级小版本号（例如 `1.0.1 -> 1.0.2`）
2. **新增功能**：升级大版本号（例如 `1.0.2 -> 2.0.0`）

并同步递增 `versionCode`。

当前：
- `versionCode = 4`
- `versionName = 2.0.0`

---

## 12. 常见问题排查

### 12.1 打开应用闪退
可能原因：时间解析格式不兼容（例如 `8:20`）。  
现已使用 `H:mm` 解析，若再次出现请重点检查：
- `ScheduleModels.kt` 里的时间字符串是否为合法 `H:mm`。

### 12.2 图标资源构建失败
如果出现 AAPT 报错（图标文件无法编译）：
- 确认图标文件真实格式与后缀一致（PNG 内容必须真的是 PNG）。
- 检查：`app/src/main/res/drawable-nodpi/ic_launcher_foreground_image.png`

### 12.3 自动弹窗不出现
按顺序检查：
1. 当前是否周一~周五；
2. 当前时间是否落在某节课区间；
3. 当前节次是否有课程；
4. 是否该节次已弹过（同日期同节次只弹一次）。

---

## 13. 最近关键更新

- `feat`: 打开应用自动弹出当前科目信息
- `fix`: 修复启动闪退（时间解析）
- `feat`: 同一节课仅自动弹窗一次（跨重开去重）
- `fix`: 更新语文老师姓名为“孙傲”

---

## License

当前仓库未声明开源许可证。  
如需开源发布，建议补充 `LICENSE` 文件（如 MIT/Apache-2.0）。
