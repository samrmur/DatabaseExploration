package com.uwo.databaseexploration.ui.name

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EnterNameContract : ActivityResultContract<Unit, EnterNameState?>() {
    companion object {
        const val RESULT_CODE = 101
        const val FIRST_NAME_EXTRA = "FIRST_NAME"
        const val LAST_TIME_EXTRA = "LAST_NAME"
    }

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, EnterNameActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): EnterNameState? {
        return intent?.run {
            if (resultCode == RESULT_CODE) {
                EnterNameState(
                    firstName = getStringExtra(FIRST_NAME_EXTRA) ?: "",
                    lastName = getStringExtra(LAST_TIME_EXTRA) ?: ""
                )
            } else {
                null
            }
        }
    }
}