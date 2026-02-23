package com.example.taskone.ui.screens.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskone.data.preferences.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    data class Skill(
        val id: String,
        @StringRes val labelRes: Int,
        val emoji: String
    )

    val skills: List<Skill> = listOf(
        Skill(id = "pronunciation", labelRes = com.example.taskone.R.string.onboarding_skill_pronunciation, emoji = "\uD83D\uDC44"),
        Skill(id = "confident_speaking", labelRes = com.example.taskone.R.string.onboarding_skill_confident_speaking, emoji = "\uD83D\uDDE3"),
        Skill(id = "vocabulary", labelRes = com.example.taskone.R.string.onboarding_skill_vocabulary, emoji = "\uD83D\uDCD6"),
        Skill(id = "listening", labelRes = com.example.taskone.R.string.onboarding_skill_listening, emoji = "\uD83D\uDC42"),
        Skill(id = "grammar", labelRes = com.example.taskone.R.string.onboarding_skill_grammar, emoji = "\u270F\uFE0F"),
        Skill(id = "writing", labelRes = com.example.taskone.R.string.onboarding_skill_writing, emoji = "\uD83D\uDD8A\uFE0F"),
        Skill(id = "reading", labelRes = com.example.taskone.R.string.onboarding_skill_reading, emoji = "\uD83D\uDC40")
    )

    private val _selectedSkillId = MutableStateFlow<String?>(null)
    val selectedSkillId: StateFlow<String?> = _selectedSkillId.asStateFlow()

    data class GoalDuration(
        val id: String,
        @StringRes val labelRes: Int
    )

    val goalDurations: List<GoalDuration> = listOf(
        GoalDuration(
            id = "5_min",
            labelRes = com.example.taskone.R.string.onboarding_goal_option_5_min
        ),
        GoalDuration(
            id = "10_min",
            labelRes = com.example.taskone.R.string.onboarding_goal_option_10_min
        ),
        GoalDuration(
            id = "15_min",
            labelRes = com.example.taskone.R.string.onboarding_goal_option_15_min
        ),
        GoalDuration(
            id = "30_min",
            labelRes = com.example.taskone.R.string.onboarding_goal_option_30_min
        )
    )

    private val _selectedGoalId = MutableStateFlow<String?>(null)
    val selectedGoalId: StateFlow<String?> = _selectedGoalId.asStateFlow()

    data class Review(
        val id: String,
        val userName: String,
        @StringRes val commentRes: Int,
        val rating: Int,
        val highlighted: Boolean,
        val avatarResName: String?
    )

    val reviews: List<Review> = listOf(
        Review(
            id = "anna",
            userName = "@anna.w1991",
            commentRes = com.example.taskone.R.string.onboarding_review_anna_comment,
            rating = 5,
            highlighted = false,
            avatarResName = "avatar1"
        ),
        Review(
            id = "safi",
            userName = "@safi.hana",
            commentRes = com.example.taskone.R.string.onboarding_review_safi_comment,
            rating = 5,
            highlighted = true,
            avatarResName = "avatar2"
        ),
        Review(
            id = "marek",
            userName = "@marek.f1",
            commentRes = com.example.taskone.R.string.onboarding_review_marek_comment,
            rating = 5,
            highlighted = false,
            avatarResName = "avatar3"
        )
    )

    data class Topic(
        val id: String,
        @StringRes val labelRes: Int
    )

    val topics: List<Topic> = listOf(
        Topic(id = "restaurant", labelRes = com.example.taskone.R.string.onboarding_topic_restaurant),
        Topic(id = "airport", labelRes = com.example.taskone.R.string.onboarding_topic_airport),
        Topic(id = "business", labelRes = com.example.taskone.R.string.onboarding_topic_business),
        Topic(id = "job_interview", labelRes = com.example.taskone.R.string.onboarding_topic_job_interview),
        Topic(id = "doctor", labelRes = com.example.taskone.R.string.onboarding_topic_doctor),
        Topic(id = "relationship", labelRes = com.example.taskone.R.string.onboarding_topic_relationship),
        Topic(id = "travel", labelRes = com.example.taskone.R.string.onboarding_topic_travel),
        Topic(id = "school", labelRes = com.example.taskone.R.string.onboarding_topic_school)
    )

    private val _selectedTopics = MutableStateFlow<List<String>>(emptyList())
    val selectedTopics: StateFlow<List<String>> = _selectedTopics.asStateFlow()

    val isOnboardingCompleted: StateFlow<Boolean> =
        preferenceManager.onboardingCompleted.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun onSkillSelected(id: String) {
        _selectedSkillId.value = id
    }

    fun onGoalSelected(id: String) {
        _selectedGoalId.value = id
    }

    fun onTopicToggled(id: String) {
        val current = _selectedTopics.value
        _selectedTopics.value = if (current.contains(id)) {
            current.filterNot { it == id }
        } else {
            current + id
        }
    }

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            preferenceManager.setOnboardingCompleted(true)
        }
    }
}
