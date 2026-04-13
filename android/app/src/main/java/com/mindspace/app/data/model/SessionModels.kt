package com.mindspace.app.data.model

data class ProcessRequest(
    val input: String,
    val isPrivate: Boolean,
    val energyOverride: String? = null
)

data class PlanResponse(
    val type: String,
    val header: String,
    val sections: List<PlanSection>,
    val closing: String,
    val sessionId: String?,
    val classification: ClassificationResult
)

data class PlanSection(
    val title: String,
    val icon: String,
    val items: List<String>
)

data class ClassificationResult(
    val intent: String,
    val energy: String
)
