package com.kkh.multimodule.core.domain.model

data class RecallItemModel(
    val id: String,
    val type: String,
    val subTitle: String,
    val date : String,
    val focusTime : String,
    val percent: Int?,
    val isRecalled: Boolean,
)

// 실제 UI에서 쓸 임시 mock 데이터
fun mockRecallItems() = listOf(
    RecallItemModel("1", "학습", "포트폴리오 작업하기", "0730", focusTime = "90분",20, true),
    RecallItemModel("2", "학습", "", "0730", focusTime = "90분",20, true),
    RecallItemModel("3", "학습", "포트폴리오 작업하기", "0730",focusTime = "90분", null, true),
    RecallItemModel("4", "학습", "", "0730", focusTime = "90분",null, false),
)