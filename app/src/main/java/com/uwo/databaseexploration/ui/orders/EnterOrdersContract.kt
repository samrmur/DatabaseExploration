package com.uwo.databaseexploration.ui.orders

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EnterOrdersContract : ActivityResultContract<Unit, EnterOrdersState?>() {
    companion object {
        const val RESULT_CODE = 101
        const val QUERY_TYPE_EXTRA = "QUERY_TYPE"
        const val NUM_ORDERS_EXTRA = "NUM_ORDERS"
    }

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, EnterOrdersActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): EnterOrdersState? {
        return intent?.run {
            EnterOrdersState(
                queryType = getSerializableExtra(QUERY_TYPE_EXTRA) as OrderQueryType,
                numOrders = getIntExtra(NUM_ORDERS_EXTRA, 0)
            )
        }
    }
}