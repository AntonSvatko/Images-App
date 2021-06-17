package com.tony.imagemvvm.functions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.whileSelect
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.*

fun EditText.onTextChanged(): ReceiveChannel<String> =
    Channel<String>(capacity = Channel.UNLIMITED).also { channel ->
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                editable?.toString().orEmpty().let(channel::offer)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

fun <T> ReceiveChannel<T>.debounce(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS): ReceiveChannel<T> =
    Channel<T>(capacity = Channel.CONFLATED).also { channel ->
        GlobalScope.launch {
            var value = receive()
            whileSelect {
                onTimeout(time) {
                    channel.offer(value)
                    value = receive()
                    true
                }
                onReceive {
                    value = it
                    true
                }
            }
        }
    }