package kr.co.lion.memoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import kr.co.lion.memoproject.databinding.ActivityInputMemoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class InputMemoActivity : AppCompatActivity() {

    lateinit var activityInputMemoBinding: ActivityInputMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputMemoBinding = ActivityInputMemoBinding.inflate(layoutInflater)
        setContentView(activityInputMemoBinding.root)

        setToolbar()
        setView()
    }

    // Toolbar
    fun setToolbar(){
        activityInputMemoBinding.apply {
            toolbarInputMemo.apply {
                title = "메모 작성"

                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                inflateMenu(R.menu.menu_input)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_input_complete_memo -> {
                            processInputDone()
                        }
                    }
                    true
                }
            }
        }
    }

    // RecyclerView
    fun setView(){
        activityInputMemoBinding.apply {
            textFieldInputTitle.requestFocus()

            showSoftInput(textFieldInputTitle)

            textFieldInputContent.setOnEditorActionListener { v, actionId, event ->
                processInputDone()
                true
            }
        }
    }

    fun processInputDone(){
        activityInputMemoBinding.apply {
            val title = textFieldInputTitle.text.toString()
            val content = textFieldInputContent.text.toString()

            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val memoData = MemoInfo(title, content, currentDate)

            val resultIntent = Intent()

            resultIntent.putExtra("memo", memoData)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    fun showSoftInput(focusView: TextInputEditText) {
        thread {
            SystemClock.sleep(500)
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(focusView, 0)
        }
    }
}